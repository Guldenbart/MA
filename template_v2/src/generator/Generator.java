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
import java.util.Map;
import java.util.Map.Entry;

import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STGroup;
import org.stringtemplate.v4.STGroupFile;
import org.stringtemplate.v4.StringRenderer;

public class Generator {
	
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
	private Map<Class<?>, ArrayList<Class<?>>> interfaceMap;
	
	private ST visitorSuperClassTemp;
	
	/**
	 * constructor that initializes all instance variables.
	 * @param dslName
	 * @param srcPath
	 * @param parseTreeGenPath
	 * @param visitorGenPath
	 */
	public Generator(String dslName, Path srcPath, Path parseTreeGenPath, Path visitorGenPath) {
		this.dslName = dslName;
		sourcePath = srcPath;
		parseTreeDestPath = parseTreeGenPath;
		visitorDestPath = visitorGenPath;
		interfaceMap = new HashMap<Class<?>, ArrayList<Class<?>> >();

		STGroup visitorSuperClassGroup = new STGroupFile("./src/templates/visitorSuperClass.stg");
		visitorSuperClassGroup.registerRenderer(String.class, new StringRenderer());
		visitorSuperClassTemp = visitorSuperClassGroup.getInstanceOf("visitorSuperClass");
		
		parseTreeDestPackage = (parseTreeDestPath.subpath(parseTreeDestPath.getNameCount()-1, parseTreeDestPath.getNameCount())).toString();
		visitorDestPackage = (visitorDestPath.subpath(visitorDestPath.getNameCount()-1, visitorDestPath.getNameCount())).toString();
	}
	
	public void runAll() throws IOException {
		fillInterfaceMap();
		
		String treeBuilderString = runClassTemplate();
		
		Path treePath = parseTreeDestPath.resolve(Paths.get(toUC(dslName) + "TreeBuilder.java"));
		Path visitorPath = visitorDestPath.resolve(Paths.get("A" + toUC(dslName) + "Visitor.java"));

		writeToFile(treePath, treeBuilderString);
		writeToFile(visitorPath, visitorSuperClassTemp.render());
	}
	
	void fillInterfaceMap() throws IOException {
		/*
		 * We remember which interfaces are extended by others,
		 * because we have to skip these.
		 */
		ArrayList<Class<?>> skipList = new ArrayList<Class<?>>();
		
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
				
				ArrayList<Class<?>> interfaceMapValue = new ArrayList<Class<?>>();
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
			 *  'interfaceMap' and later find out that we have to skip them.
			 */
			this.interfaceMap.remove(clazz);
		}
	}
	

	// TODO rename
	private String runClassTemplate() {
		visitorSuperClassTemp.add("dslName", dslName);
		visitorSuperClassTemp.add("package", visitorDestPackage);
		
		STGroup treeBuilderGroup = new STGroupFile("./src/templates/treeBuilderClass.stg");
		treeBuilderGroup.registerRenderer(String.class, new StringRenderer());
		ST treeBuilderTemp = treeBuilderGroup.getInstanceOf("class");
		
		STGroup visitorLopeGroup = new STGroupFile("./src/templates/visitorLopeClass.stg");
		
		ArrayList<GeneratorScope> generatorScopeList = new ArrayList<GeneratorScope>();
		
		for(Entry<Class<?>, ArrayList<Class<?>>> entry : interfaceMap.entrySet()) {
			String curInterfaceName = entry.getKey().getSimpleName();
			
			if(!curInterfaceName.equals(firstInterfaceName)) {
				/*
				 *  we must not add the interface with 'firstInterfaceName'
				 *  because it has to be handled separately
				 */
				treeBuilderTemp.add("interfaceNames", curInterfaceName);
			}
			
			// scopesList zusammenstellen
			GeneratorScope tempScope = createGeneratorScope(curInterfaceName, entry.getValue());
			
			// lope
			ST visitorLopeTemp = visitorLopeGroup.getInstanceOf("lope");
			visitorLopeTemp.add("dslNameUC", toUC(dslName));
			visitorLopeTemp.add("iName", curInterfaceName);
			visitorLopeTemp.add("packageName", parseTreeDestPackage);
			visitorLopeTemp.add("visitorGenPackageName", visitorDestPackage);
			writeToFile(tempScope.getLopeClassPath(parseTreeDestPath), visitorLopeTemp.render());
			
			generatorScopeList.add(tempScope);
			
		}
		
		treeBuilderTemp.add("dslName", dslName);
		treeBuilderTemp.add("packageName", parseTreeDestPackage);
		treeBuilderTemp.add("firstInterfaceName", firstInterfaceName);
		treeBuilderTemp.add("scopesList", generatorScopeList);
		
		visitorSuperClassTemp.add("scopesList", generatorScopeList);
		
		return treeBuilderTemp.render();
	}
	
	private GeneratorScope createGeneratorScope(String interfaceName, ArrayList<Class<?>> arrayList) {
		// we give this to the GeneratorScope in the end
		ArrayList<GeneratorMethod> generatorMethodList = new ArrayList<GeneratorMethod>();
		
		STGroup visitorMethodGroup = new STGroupFile("./src/templates/visitorMethodClass.stg");
		visitorMethodGroup.registerRenderer(String.class, new StringRenderer());
		
		for (Class<?> clazz : arrayList) {
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
				
				// visitorMethod
				ST visitorMethodTemp = visitorMethodGroup.getInstanceOf("visitorMethodDispatch");
				visitorMethodTemp.add("dslName", dslName);
				visitorMethodTemp.add("parseTreeDestPackage", parseTreeDestPackage);
				visitorMethodTemp.add("visitorDestPackage", visitorDestPackage);
				visitorMethodTemp.add("method", tempMethod);
				writeToFile(tempMethod.getVisitorClassPath(parseTreeDestPath), visitorMethodTemp.render());
				
				generatorMethodList.add(tempMethod);
			}
		}
		
		return new GeneratorScope(interfaceName, generatorMethodList);
	}
	
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
	
	private String toUC(String s) {
		if (s.length() == 0) {
			return "";
		} else if(s.length() == 1) {
			return s.toUpperCase();
		} else {
			return s.substring(0,1).toUpperCase() + s.substring(1);
		}
	}

}
