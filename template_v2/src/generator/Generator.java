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
	 * 
	 * @param dslName name of the domain specific language
	 * @param srcPath path where the interfaces that define the DSL are.
	 * @param parseTreeGenPath path where all files related to the parse tree
	 * 	are generated to.
	 * @param visitorGenPath path where all files related to the visitor
	 * 	are generated to.
	 * @see ParseTree
	 * @see AVisitor
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
	
	/**
	 * method that invokes the code generation steps.
	 * <p>
	 * The steps are the following:
	 * <ul>
	 * <li>{@link #fillInterfaceMap()}: fill {@link #interfaceMap} with data according to the
	 * 	specification.
	 * <li>{@link #buildGenScopeList(): creates a nested list with <code>GeneratorScope</code>
	 * objects (including GeneratorMethod objects) that contains all information needed from
	 * the grammar interfaces for code generation.
	 * <li>{@link #checkAllRequirements(List)}: checks if all requirements that have been made
	 * have been met.
	 * <li>{@link #runTemplates(List)}: invokes the code generation.
	 * </ul><p>
	 * 
	 * @throws IOException
	 */
	public void generate() throws IOException {
		fillInterfaceMap();
		
		List<GeneratorScope> genScopeList = buildGenScopeList();
		if (!checkAllRequirements(genScopeList)) {
			System.err.println("Generator could not be started because the input didn't satisfy the specifications.");
		}
		runTemplates(genScopeList);
	}
	
	/**
	 * Method to populate the variable {@link #interfaceMap}.
	 * 
	 * @throws IOException
	 * @see #interfaceMap
	 */
	private void fillInterfaceMap() throws IOException {
		/*
		 * We remember which interfaces are extended by others,
		 * because we have to skip these.
		 */
		List<Class<?>> skipList = new ArrayList<Class<?>>();
		
		DirectoryStream<Path> stream = Files.newDirectoryStream(sourcePath); // throws IOException
		for (Path file : stream) {
			
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
					System.err.println("class " + c.getName()
						+ " is not an interface and will be ignored!");
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
				continue;
			}
        }
		stream.close();
        
		for (Class<?> clazz : skipList) {
			/* 
			 * We have to do this because we might add some classes to 'interfaceMap'
			 * initially only to later find out that we have to skip them.
			 */
			this.interfaceMap.remove(clazz);
		}
	}
	
	/**
	 * Method that is responsible for building a list of <code>GeneratorScope</code> objects.
	 * We store all information from the grammar defining interfaces that is needed to
	 * generate the desired code. The information is retrieved using the Java reflection API.
	 * <p>
	 * Most of the work is done by {@link #createGeneratorScope(String, List)}.
	 * 
	 * @return the list of GeneratorScope objects
	 * @see GeneratorScope
	 * @see GeneratorMethod
	 */
	private List<GeneratorScope> buildGenScopeList() {
		List<GeneratorScope> generatorScopeList = new ArrayList<GeneratorScope>();
		
		for (Entry<Class<?>, List<Class<?>>> entry : interfaceMap.entrySet()) {
			String curInterfaceName = entry.getKey().getSimpleName();
			
			// scope list zusammenstellen
			GeneratorScope genScope = createGeneratorScope(curInterfaceName, entry.getValue());			
			generatorScopeList.add(genScope);
		}
		
		return generatorScopeList;
	}
	
	/**
	 * Creates one <code>GeneratorScope</code> object based on the information
	 * passed to this method.
	 * 
	 * @param iName name of the interface that will give the scope generated
	 * from this <code>GeneratorScope</code> object its name.
	 * @param list list of all interfaces that the interface named <code>iName</code> extends,
	 * plus itself.
	 * @return <code>GeneratorScope</code> object with the given information.
	 * @see #buildGenScopeList()
	 * @see GeneratorScope
	 * @see GeneratorMethod
	 */
	private GeneratorScope createGeneratorScope(final String iName, final List<Class<?>> list) {
		// We give this to the GeneratorScope in the end
		List<GeneratorMethod> generatorMethodList = new ArrayList<GeneratorMethod>();
		
		for (Class<?> clazz : list) {
			for (Method method : clazz.getDeclaredMethods()) {
				if (method.getParameters().length > 1) {
					// TODO exception?
					System.err.println("only one argument per Method allowed!");
					continue;
				}
				
				GeneratorMethod genMethod;
				
				/*
				 *  we convert the method name to upper case so that in GeneratorMethod,
				 *  we don't have to add toUC just for GeneratorMethod.getVisitorClassPath.
				 */
				String mName = toUC(method.getName());
				String retType = method.getReturnType().getSimpleName();
				
				if (method.getParameterCount() == 1) {
					String argType = method.getParameters()[0].getType().getSimpleName();
					String argName = method.getParameters()[0].getName();
					
					genMethod = new GeneratorMethod(iName, mName, retType, argType, argName);
				} else {
					genMethod = new GeneratorMethod(iName, mName, retType);
				}
				
				generatorMethodList.add(genMethod);
			}
		}		
		return new GeneratorScope(iName, generatorMethodList);
	}
	
	/**
	 * This method generates code as specified in the templates, fed with the information
	 * form <code>genScopeList</code>, and writes them into files.
	 * 
	 * @param genScopeList list of <code>GeneratorScope</code> that contains all information needed
	 * from the grammar interfaces for code generation.
	 */
	private void runTemplates(List<GeneratorScope> genScopeList) {
		// ========== create ST variables
		// methodNode:
		STGroup methodNodeGroup = new STGroupFile("./src/templates/methodNodeClass.stg");
		methodNodeGroup.registerRenderer(String.class, new StringRenderer());
		
		//scopeNode:
		STGroup scopeNodeGroup = new STGroupFile("./src/templates/scopeNodeClass.stg");
		
		//treeBuilder:
		STGroup treeBuilderGroup = new STGroupFile("./src/templates/treeBuilderClass.stg");
		treeBuilderGroup.registerRenderer(String.class, new StringRenderer());
		ST treeBuilderTemp = treeBuilderGroup.getInstanceOf("class");
		
		//visitorSuperClass:
		STGroup visitorSuperClassGroup = new STGroupFile("./src/templates/visitorSuperClass.stg");
		visitorSuperClassGroup.registerRenderer(String.class, new StringRenderer());
		ST visitorSuperClassTemp = visitorSuperClassGroup.getInstanceOf("visitorSuperClass");
		
		
		// ========== add all information to the templates
		for (GeneratorScope gs : genScopeList) {
			if (!gs.getScopeName().equals(firstInterfaceName)) {
				/*
				 *  we must not add the interface with 'firstInterfaceName'
				 *  because it has to be handled separately
				 */
				treeBuilderTemp.add("interfaceNames", gs.getScopeName());
			}
			
			// scopeNode:
			ST scopeNodeTemp = scopeNodeGroup.getInstanceOf("scopeNode");
			scopeNodeTemp.add("dslNameUC", toUC(dslName));
			scopeNodeTemp.add("iName", gs.getScopeName());
			scopeNodeTemp.add("packageName", parseTreeDestPackage);
			scopeNodeTemp.add("visitorGenPackageName", visitorDestPackage);
			writeToFile(gs.getScopeNodePath(parseTreeDestPath), scopeNodeTemp.render());
			
			for (GeneratorMethod gm : gs) {
				// methodNode:
				ST methodNodeTemp = methodNodeGroup.getInstanceOf("methodNodeDispatch");
				methodNodeTemp.add("dslName", dslName);
				methodNodeTemp.add("parseTreeDestPackage", parseTreeDestPackage);
				methodNodeTemp.add("visitorDestPackage", visitorDestPackage);
				methodNodeTemp.add("method", gm);
				writeToFile(gm.getMethodNodePath(parseTreeDestPath), methodNodeTemp.render());
			}
		}
		
		// treeBuilder:
		treeBuilderTemp.add("dslName", dslName);
		treeBuilderTemp.add("packageName", parseTreeDestPackage);
		treeBuilderTemp.add("firstInterfaceName", firstInterfaceName);
		treeBuilderTemp.add("scopesList", genScopeList);
		
		//visitorSuperClass:
		visitorSuperClassTemp.add("dslName", dslName);
		visitorSuperClassTemp.add("package", visitorDestPackage);
		visitorSuperClassTemp.add("parseTreePackage", parseTreeDestPackage);
		visitorSuperClassTemp.add("scopesList", genScopeList);
		
		// ========== write templates to file
		// treeBuilder:
		Path tPath = parseTreeDestPath.resolve(Paths.get(toUC(dslName) + "TreeBuilder.java"));
		writeToFile(tPath, treeBuilderTemp.render());
		
		//visitorSuperClass:
		Path vPath = visitorDestPath.resolve(Paths.get("A" + toUC(dslName) + "Visitor.java"));
		writeToFile(vPath, visitorSuperClassTemp.render());
		
	}
	
	/**
	 * Writes a given string to a file defined by its path.
	 * 
	 * @param path path of the file the content will be written to.
	 * @param content string that is written to the given file.
	 */
	private void writeToFile(final Path path, final String content) {
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
	 * checks if all requirements that have been made in order for this program to work properly
	 * have been met.
	 * <p>
	 * The requirements are:
	 * <ul>
	 * <li>All interface types that define the grammar of a DSL have to start with an upper
	 * 	case letter. Although it is already discouraged in Java to use type names that
	 * 	start with a lower case letter, we have to make sure that none of the interfaces does.
	 * <li>There must not be two (or more) methods that have the same name AND argument type.
	 * <li>There has to be at least one method that has <code>ParseTree</code> as its return type
	 * 	and thus concludes a statement of the DSL.
	 * </ul>
	 * See {@link #checkInterfacesUpperCase()}, {@link #checkAllMethodsUnique(List)} and
	 * {@link #checkEndingMethod(List)} for more details.
	 * <p>
	 * There is one more requirement that is not checked by this method but directly in
	 * {@link #fillInterfaceMap()}: Every method cannot have more than one argument.
	 * 
	 * @param genScopeList list of all {@link GeneratorScope} objects needed for the generation
	 * of code later.
	 * @return true if all requirements have been met; false otherwise.
	 * @throws IOException
	 * @see ParseTree
	 */
	private boolean checkAllRequirements(List<GeneratorScope> genScopeList) throws IOException {
		if (!checkInterfacesUpperCase()) {
			return false;
		}
		
		if (!checkAllMethodsUnique(genScopeList)) {
			return false;
		}
		
		if (!checkEndingMethod(genScopeList)) {
			return false;
		}
		
		return true;
	}
	
	/**
	 * Checks if all interface type names that define the grammar of a DSL start with an
	 * upper case letter.
	 * 
	 * @return true, if all interface type names start with an upper case letter; false otherwise. 
	 * @throws IOException
	 * @see #checkAllRequirements(List)
	 */
	private boolean checkInterfacesUpperCase() throws IOException {

		// throws IOException
		DirectoryStream<Path> stream = Files.newDirectoryStream(sourcePath, "*.java");
		
		for (Path file : stream) {
			
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
			
			if (!Character.isUpperCase(file.getFileName().toString().charAt(0))) {
				StringBuilder message = new StringBuilder();
				message.append("Interface ");
				message.append(file.getFileName().toString());
				message.append(" does not start with an upper case letter!");
				System.err.println(message);
				
				stream.close();
				return false;
			}
		}
		stream.close();
		
		return true;
	}
	
	/**
	 * checks if all methods are unique, meaning that no two methods in the DSL grammar definition
	 * have the same name AND the same argument type.
	 * 
	 * @param genScopeList list of all {@link GeneratorScope} objects needed for the generation
	 * of code later.
	 * @return true, if no two methods have the same name AND the same argument type;
	 * false otherwise.
	 * @see #checkAllRequirements(List)
	 */
	private boolean checkAllMethodsUnique(List<GeneratorScope> genScopeList) {
		
		/* we use an old-fashioned loop here because
		 * we want to skip the elements we already visited */
		for (int s = 0; s < genScopeList.size(); s++) {
			for (GeneratorMethod gm : genScopeList.get(s)) {
				
				// look up if this methods name+argument type exists again
				for (int t = s; t < genScopeList.size(); t++) {
					int i = genScopeList.get(t).lastIndexOf(gm);
					if (i != -1 && genScopeList.get(t).get(i) != gm) {
						StringBuilder message = new StringBuilder();
						message.append("There are multiple methods with name ");
						message.append(gm.getName());
						message.append(" and argument type ");
						message.append(gm.getArgumentType());
						message.append("!");
						System.err.println(message.toString());
						return false;
					}
				}
			}
		}
		
		return true;
	}
	
	/**
	 * checks if at least one method exists that concludes a DSL statement.
	 * Therefore, it has to have the return type <code>ParseTree</code>.
	 * 
	 * @param genScopeList list of all {@link GeneratorScope} objects needed for the generation
	 * of code later.
	 * @return true, if at least one method with return type <code>ParseTree</code> is found.
	 * @see #checkAllRequirements(List)
	 */
	private boolean checkEndingMethod(List<GeneratorScope> genScopeList) {
		for (GeneratorScope gs : genScopeList) {
			for (GeneratorMethod gm : gs) {
				if (gm.getTreeEnds()) {
					return true;
				}
			}
		}
		
		System.err.println("There has to be at least one method that returns ParseTree"
				+ "and thus concludes a phrase of your language!");
		
		return false;
	}
	
	/**
	 * Converts the first letter of a string to its upper case equivalent.
	 * <p>
	 * If the given String is null or an empty string, an empty string is
	 * returned.
	 * 
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
