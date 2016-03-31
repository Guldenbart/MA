package generator;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STGroup;
import org.stringtemplate.v4.STGroupFile;
import org.stringtemplate.v4.StringRenderer;

public final class Generator {
	
	private final String dslName;
	private final Path sourcePath;
	private final Path parseTreeDestPath;
	private final Path visitorDestPath;
	private final String firstInterfaceName = "Start";
	private String parseTreeDestPackage;
	private String visitorDestPackage;
	
	/**
	 * Map to match interfaces with the interfaces they extend:
	 * 
	 * If interface A extends interface B, the methods of B have to go
	 * into the scope of interface A. We have to store these relations
	 * so that later, we put every method in the correct scope.
	 * 
	 * The key of each map entry is an interface and the value is
	 * a list of all interfaces that the <key> interface extends, plus itself.
	 */
	private Map<Class<?>, List<Class<?>>> interfaceMap;
	
	/**
	 * constructor that initializes all instance variables.
	 * @param dslName name of the domain specific language
	 * @param srcPath path where the interfaces that define the dsl are.
	 * @param parseTreeGenPath path where all files related to the parse tree
	 * 	are generated to.
	 * @param visitorGenPath path where all files related to the visitor
	 * 	are generated to.
	 */
	public Generator(final String dslName, final Path srcPath,
			final Path parseTreeGenPath, final Path visitorGenPath) {
		this.dslName = dslName;
		sourcePath = srcPath;
		parseTreeDestPath = parseTreeGenPath;
		visitorDestPath = visitorGenPath;
		interfaceMap = new HashMap<Class<?>, List<Class<?>> >();
		
		parseTreeDestPackage = (parseTreeDestPath.subpath(parseTreeDestPath.getNameCount()-1, parseTreeDestPath.getNameCount())).toString();
		visitorDestPackage = (visitorDestPath.subpath(visitorDestPath.getNameCount()-1, visitorDestPath.getNameCount())).toString();
	}
	
	public void generateAll() throws IOException {
		fillInterfaceMap();
		
		String treeBuilderString = runTemplates();
		
		Path treePath = parseTreeDestPath.resolve(Paths.get(toUC(dslName) + "TreeBuilder.java"));
		
		writeToFile(treePath, treeBuilderString);
	}
	
	/**
	 * Method to populate the variable @link{Generator#interfaceMap}.
	 * 
	 * @throws IOException
	 * @see {@link Generator#interfaceMap}
	 */
	private void fillInterfaceMap() throws IOException {
		/*
		 * We remember which interfaces are extended by others,
		 * because we have to skip these.
		 */
		List<Class<?>> skipList = new ArrayList<Class<?>>();
		
		DirectoryStream<Path> stream = Files.newDirectoryStream(sourcePath); // throws IOException
		for (Path file : stream) {
			
			if (Files.isDirectory(file)) {
				// no directories please!
				continue;
			}
			
			Path filePath = file.getFileName();
			if (filePath == null) {
				// TODO: just for findBugs; could it happen?
				continue;
			}
			String fileName = filePath.toString();
			if (fileName.contentEquals("package-info.java")) {
				// nothing to do here
				continue;
			}
			
			try {
				String interfaceName = fileName.substring(0, fileName.lastIndexOf("."));
				Class<?> c = Class.forName(dslName + '.' + interfaceName); // TODO geht das auch ohne package-Name?
				
				if (!c.isInterface()) {
					// TODO FRAGE: Sollte das eher eine Exception sein?
					System.err.println("class " + c.getName() + " is not an interface and will be ignored!");
					continue;
				}
				
				Class<?>[] classArray = c.getInterfaces();
				
				List<Class<?>> interfaceMapValue = new ArrayList<Class<?>>();
				// add yourself first; this makes it easier when evaluating the list later
				interfaceMapValue.add(c);
				
				// now, add all interfaces that you extend
				for (Class<?> clazz : classArray) {
					interfaceMapValue.add(clazz);
					skipList.add(clazz);
				}
				interfaceMap.put(c, interfaceMapValue);
				
			} catch (ClassNotFoundException e) {
				System.err.println("There is no such class named " + e.getClass() + '!');
				e.printStackTrace();
			}
        }
        
		for (Class<?> clazz : skipList) {
			/*
			 *  we have to do this because we might add some classes to
			 *  'interfaceMap' only to later find out that we have to skip
			 *  them.
			 */
			this.interfaceMap.remove(clazz);
		}
	}
	

	private String runTemplates() {
		
		STGroup treeBuilderGroup = new STGroupFile("./src/templates/treeBuilderClass.stg");
		treeBuilderGroup.registerRenderer(String.class, new StringRenderer());
		ST treeBuilderTemp = treeBuilderGroup.getInstanceOf("class");
		
		STGroup scopeNodeGroup = new STGroupFile("./src/templates/scopeNodeClass.stg");
		
		List<GeneratorScope> generatorScopeList = new ArrayList<GeneratorScope>();
		
		for (Entry<Class<?>, List<Class<?>>> entry : interfaceMap.entrySet()) {
			String curInterfaceName = entry.getKey().getSimpleName();
			
			if (!curInterfaceName.equals(firstInterfaceName)) {
				/*
				 *  we must not add the interface with 'firstInterfaceName'
				 *  because it has to be handled separately
				 */
				treeBuilderTemp.add("interfaceNames", curInterfaceName);
			}
			
			// scopesList zusammenstellen
			GeneratorScope genScope = createGeneratorScope(curInterfaceName, entry.getValue());
			
			// scopeNode
			ST scopeNodeTemp = scopeNodeGroup.getInstanceOf("scopeNode");
			scopeNodeTemp.add("dslNameUC", toUC(dslName));
			scopeNodeTemp.add("iName", curInterfaceName);
			scopeNodeTemp.add("packageName", parseTreeDestPackage);
			scopeNodeTemp.add("visitorGenPackageName", visitorDestPackage);
			writeToFile(genScope.getScopeNodePath(parseTreeDestPath), scopeNodeTemp.render());
			
			generatorScopeList.add(genScope);
		}
		
		// treeBuilder
		treeBuilderTemp.add("dslName", dslName);
		treeBuilderTemp.add("packageName", parseTreeDestPackage);
		treeBuilderTemp.add("firstInterfaceName", firstInterfaceName);
		treeBuilderTemp.add("scopesList", generatorScopeList);
		
		// visitorSuperClass
		STGroup visitorSuperClassGroup = new STGroupFile("./src/templates/visitorSuperClass.stg");
		visitorSuperClassGroup.registerRenderer(String.class, new StringRenderer());
		ST visitorSuperClassTemp = visitorSuperClassGroup.getInstanceOf("visitorSuperClass");

		visitorSuperClassTemp.add("dslName", dslName);
		visitorSuperClassTemp.add("package", visitorDestPackage);
		visitorSuperClassTemp.add("parseTreePackage", parseTreeDestPackage);
		visitorSuperClassTemp.add("scopesList", generatorScopeList);
		
		Path visitorPath = visitorDestPath.resolve(Paths.get("A" + toUC(dslName) + "Visitor.java"));
		writeToFile(visitorPath, visitorSuperClassTemp.render());
		
		return treeBuilderTemp.render();
	}
	
	private GeneratorScope createGeneratorScope(final String interfaceName, final List<Class<?>> list) {
		// we give this to the GeneratorScope in the end
		List<GeneratorMethod> generatorMethodList = new ArrayList<GeneratorMethod>();
		
		STGroup methodNodeGroup = new STGroupFile("./src/templates/methodNodeClass.stg");
		methodNodeGroup.registerRenderer(String.class, new StringRenderer());
		
		for (Class<?> clazz : list) {
			for (Method method : clazz.getDeclaredMethods()) {
				if (method.getParameters().length > 1) {
					// TODO exception?
					System.err.println("only one argument per Method allowed!");
					continue;
				}
				
				GeneratorMethod tempMethod;
				
				/*
				 *  we convert the method name to upper case so that in
				 *  GeneratorMethod, we don't have to add toUC just for
				 *  GeneratorMethod.getVisitorClassPath.
				 */
				String mName = toUC(method.getName());
				String retType = method.getReturnType().getSimpleName();
				
				if (method.getParameterCount() == 1) {
					String argType = method.getParameters()[0].getType().getSimpleName();
					String argName = method.getParameters()[0].getName();
					
					tempMethod = new GeneratorMethod(interfaceName, mName, retType, argType, argName);
				} else {
					tempMethod = new GeneratorMethod(interfaceName, mName, retType);
				}
				
				// methodNode
				ST methodNodeTemp = methodNodeGroup.getInstanceOf("methodNodeDispatch");
				methodNodeTemp.add("dslName", dslName);
				methodNodeTemp.add("parseTreeDestPackage", parseTreeDestPackage);
				methodNodeTemp.add("visitorDestPackage", visitorDestPackage);
				methodNodeTemp.add("method", tempMethod);
				writeToFile(tempMethod.getMethodNodePath(parseTreeDestPath), methodNodeTemp.render());
				
				generatorMethodList.add(tempMethod);
			}
		}
		
		return new GeneratorScope(interfaceName, generatorMethodList);
	}
	
	/**
	 * Writes a given string to a file defined by its path.
	 * @param path path of the file the content will be written to.
	 * @param content string that is written to the given file.
	 */
	private void writeToFile(Path path, String content) {
		try {
			PrintWriter pw = new PrintWriter(new FileOutputStream(path.toString(), false));
			pw.write(content);
			
			pw.close();
		} catch (FileNotFoundException e) {
			System.err.println("Error when writing file " + path + "!");
			e.printStackTrace();
		}
	}
	
	/**
	 * Converts the first letter of a string to its upper case equivalent.
	 * 
	 * If the given String is null or an empty string, an empty string is
	 * returned.
	 * @param s string whose first letter is converted
	 * @return empty String if <code>null</code> or an empty String was given.
	 * String with a capital first letter otherwise.
	 */
	private String toUC(String s) {
		if (s == null || s.length() == 0) {
			return "";
		} else if(s.length() == 1) {
			return s.toUpperCase();
		} else {
			return s.substring(0,1).toUpperCase() + s.substring(1);
		}
	}

}
