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
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STGroup;
import org.stringtemplate.v4.STGroupFile;
import org.stringtemplate.v4.StringRenderer;

/**
 * class that includes all methods necessary for code generation.
 * 
 * @author Daniel Fritz
 *
 */
public final class Generator {
	
	/**
	 * Name of the DSL, inferred from {@link #sourcePath}.
	 */
	private final String dslName;
	
	/**
	 * Path to the grammar defining interfaces.
	 */
	private final Path sourcePath;
	
	/**
	 * Desired path for generated parse tree nodes and the TreeBuilder class. 
	 */
	private final Path parseTreeDestPath;
	
	/**
	 * Desired path for the generated abstract visitor super class.
	 */
	private final Path visitorDestPath;
	
	/**
	 * Name of the interface that includes the first method of a language statement.
	 */
	private final String firstInterfaceName;
	
	/**
	 * Name of the package inferred from {@link #parseTreeDestPath}.
	 */
	private String parseTreeDestPackage;
	
	/**
	 * Name of the package inferred from {@link #visitorDestPath}.
	 */
	private String visitorDestPackage;
	
	/**
	 * Map to match interfaces with the interfaces they extend:
	 * 
	 * If interface A extends interface B, the methods of B have to go into the scope of
	 * interface A. We have to store these relations so that later, we put every method in the
	 * correct scope.
	 * 
	 * The key of each map entry is an interface and the value is
	 * a list of all interfaces that the <code>key</code> interface extends, plus itself.
	 */
	private Map<Class<?>, List<Class<?>>> interfaceMap;
	
	/**
	 * Set with all interface types that will generate a scope even if they appear in
	 * {@link #skipList}.
	 * <p>
	 * We add the return types of all methods to this set [except when the interface that the method
	 * is in extends the interface of the return type.] TODO stimmt das in der Klammer??
	 * 
	 * We need this for the following case:
	 * <p>
	 * <code>
	 * 	interface A {<br>
	 * 		&emsp;B a();<br>
	 *  }<br>
	 *  interface B extends C {<br>
	 *  	&emsp;C b();<br>
	 *  }<br>
	 *  interface C {<br>
	 *  	&emsp;End c();<br>
	 *  }<br>
	 * </code>
	 * 
	 * so that
	 * <br>
	 * <code>
	 * a().c().end();<br>
	 * a().b().c().end();<br>
	 * </code>
	 * 
	 * are the only allowed sequences. That means that <code>c()</code> has to be in
	 * <code>BScope</code> and in <code>CScope</code>.
	 * <p>
	 * In <code>keepList</code> we save the types of all methods so that we know which Scopes have
	 * to be generated:<br>
	 * If <code>b()</code> was to be allowed to be called repeatedly (<code>B b()</code>), there
	 * wouldn't be a need for <code>CScope</code>, because you can always call the <code>c()</code>
	 * in BScope after calling <code>B() n</code>times.<br>
	 * In the example above, you may only call <code>b()</code> once which means you have to leave
	 * <code>BScope</code> after that.<br>
	 * The information whether to generate<code>CScope</code> or not comes from looking at
	 * <code>{@link #skipList}</code> and <code>keepList</code>:<br>
	 * If an interface is in <code>skipList</code> and NOT in <code>keepList</code>, no Scope will
	 * be generated off it.
	 */
	private Set<String> keepList;
	
	/**
	 * list with all interface types that will not generate a scope if they don't appear in
	 * {@link #keepList}.
	 */
	private List<String> skipList;
	
	/**
	 * {@link GeneratorParseTree} object that holds all {@link GeneratorScope} and
	 * {@link GeneratorMethod} objects necessary for code generation.
	 */
	private GeneratorParseTree tree;
	
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
	public Generator(final Path srcPath, final Path parseTreeGenPath,
			final Path visitorGenPath, final String firstIName) {
		this.dslName = srcPath.getName(srcPath.getNameCount() - 1).toString();
		this.sourcePath = srcPath;
		this.parseTreeDestPath = parseTreeGenPath;
		this.visitorDestPath = visitorGenPath;
		this.firstInterfaceName = firstIName;
		this.interfaceMap = new HashMap<Class<?>, List<Class<?>> >();
		this.keepList = new HashSet<String>();
		this.skipList = new ArrayList<String>();
		
		this.parseTreeDestPackage = (this.parseTreeDestPath.subpath(
				this.parseTreeDestPath.getNameCount() - 1, this.parseTreeDestPath.getNameCount()
				)).toString();
		this.visitorDestPackage = (this.visitorDestPath.subpath(
				this.visitorDestPath.getNameCount() - 1, this.visitorDestPath.getNameCount()
				)).toString();
	}
	
	/**
	 * method that invokes the code generation steps.
	 * <p>
	 * The steps are the following:
	 * <ul>
	 * <li>{@link #fillInterfaceMap()}: fill {@link #interfaceMap} with data according to the
	 * 	specification.
	 * <li>{@link #buildGenScopeList(): creates a nested list with <code>GeneratorScope</code>
	 * 	objects (including GeneratorMethod objects) that contains all information needed from
	 * 	the grammar interfaces for code generation.
	 * <li>{@link #checkAllRequirements(List)}: checks if all requirements that have been made
	 * 	have been met.
	 * <li>
	 * <li>{@link #runTemplates(List)}: invokes the code generation.
	 * </ul><p>
	 * 
	 * @throws IOException
	 */
	public void generate() throws IOException {
		fillInterfaceMap();
		
		this.tree = new GeneratorParseTree(buildGenScopeList());
		if (!checkAllRequirements()) {
			System.err.println("Generator could not be started "
					+ "because the input didn't satisfy the specifications.");
			
			return;
		}
		
		createPackages();
		runTemplates();
	}
	
	/**
	 * Method to populate the variable {@link #interfaceMap}.
	 * 
	 * @throws IOException
	 * @see #interfaceMap
	 */
	private void fillInterfaceMap() throws IOException {
		
		// throws IOException
		DirectoryStream<Path> stream = Files.newDirectoryStream(this.sourcePath);
		
		for (Path file : stream) {			
			Path filePath = file.getFileName();
			if (filePath == null) {
				// TODO: just for findBugs; could it happen?
				continue;
			}
			
			//TODO Code dieser Methode bereinigen
			//String fileName = filePath.toString();
			String fileName = filePath.toFile().getName();
			//if (fileName.contentEquals("package-info.java")) {
			if (fileName.contentEquals("package-info")) {
				// nothing to do here
				continue;
			}
			
			try {
				//String interfaceName = fileName.substring(0, fileName.lastIndexOf("."));
				//Class<?> c = Class.forName(this.dslName + '.' + interfaceName);
				Class<?> c = Class.forName(this.dslName + '.' + fileName);
				
				if (!c.isInterface()) {
					System.err.println("class " + c.getName()
						+ " is not an interface and will be ignored!");
					continue;
				}
				
				Class<?>[] classArray = c.getInterfaces();
				
				List<Class<?>> interfaceMapValue = new ArrayList<Class<?>>();
				// add yourself first; this makes it easier when evaluating the list later | TODO why??
				interfaceMapValue.add(c);
				
				// now, add all interfaces that you extend
				for (Class<?> clazz : classArray) {
					interfaceMapValue.add(clazz);
					this.skipList.add(clazz.getSimpleName());
				}
				this.interfaceMap.put(c, interfaceMapValue);
				
			} catch (ClassNotFoundException e) {
				System.err.println("ClassNotFoundException: " + e.getCause());
				e.printStackTrace();
				continue;
			}
		}
		stream.close(); // TODO muss das auch im catch passieren?
	}
	
	/**
	 * Method that is responsible for building a list of <code>GeneratorScope</code> objects.
	 * <p>
	 * We store all information from the grammar defining interfaces that is needed to
	 * generate the desired code. The information is retrieved using the Java reflection API.
	 * <br>
	 * Most of the work is done by {@link #createGeneratorScope(String, List)}.
	 * 
	 * @return the list of GeneratorScope objects
	 * @see GeneratorScope
	 * @see GeneratorMethod
	 */
	private List<GeneratorScope> buildGenScopeList() {
		List<GeneratorScope> generatorScopeList = new ArrayList<GeneratorScope>();
		
		for (Entry<Class<?>, List<Class<?>>> entry : this.interfaceMap.entrySet()) {
			String curInterfaceName = entry.getKey().getSimpleName();
			
			// scope list zusammenstellen
			GeneratorScope genScope = createGeneratorScope(curInterfaceName, entry.getValue());
			generatorScopeList.add(genScope);
		}
		
		for (GeneratorScope gs : generatorScopeList) {
			if (this.skipList.contains(gs.getName()) && !this.keepList.contains(gs.getName())) {
				generatorScopeList.remove(gs);
			}
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
					System.err.println("only one argument per Method allowed!\nThe outcome of this"
							+ "generation can not be predicted but will be most likely wrong.");
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
				this.keepList.add(retType);
			}
		}

		return new GeneratorScope(iName, generatorMethodList);
	}
	
	/**
	 * Method that invokes creation of packages where the generated files should be saved.
	 * 
	 * It uses {@link #createFolder(Path)} to generate all folders recursively.
	 * @throws IOException
	 */
	private void createPackages() throws IOException {
		createFolder(this.parseTreeDestPath);
		createFolder(this.visitorDestPath);
	}
	
	/**
	 * Recursive function that creates every folder of <code>path</code> that doesn't exist yet.
	 * @param path Path that will be created if it doesn't exist.
	 * @throws IOException
	 */
	private void createFolder(final Path path) throws IOException {
		Path parent = path.getParent();
		// exit condition:
		if (parent == null) {
			if (!Files.exists(path)) {
				Files.createDirectory(path);
			}
			return;
		}
		
		createFolder(parent);
		if (!Files.exists(path)) {
			Files.createDirectory(path);
		}
		return;
	}
	
	/**
	 * This method generates code as specified in the templates, fed with the information
	 * form <code>genScopeList</code>, and writes them into files.
	 * 
	 * @param genScopeList list of <code>GeneratorScope</code> that contains all information needed
	 * from the grammar interfaces for code generation.
	 */
	private void runTemplates() {
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
		for (GeneratorScope gs : this.tree) {
			if (!gs.getName().equals(this.firstInterfaceName)) {
				/*
				 *  we must not add the interface with 'firstInterfaceName'
				 *  because it has to be handled separately
				 */
				treeBuilderTemp.add("interfaceNames", gs.getName());
			}
			
			// scopeNode:
			ST scopeNodeTemp = scopeNodeGroup.getInstanceOf("scopeNode");
			scopeNodeTemp.add("dslNameUC", toUC(this.dslName));
			scopeNodeTemp.add("iName", gs.getName());
			scopeNodeTemp.add("packageName", this.parseTreeDestPackage);
			scopeNodeTemp.add("visitorGenPackageName", this.visitorDestPackage);
			writeToFile(gs.getScopeNodePath(this.parseTreeDestPath), scopeNodeTemp.render());
			
			for (GeneratorMethod gm : gs) {
				// methodNode:
				ST methodNodeTemp = methodNodeGroup.getInstanceOf("methodNodeDispatch");
				methodNodeTemp.add("dslName", this.dslName);
				methodNodeTemp.add("parseTreeDestPackage", this.parseTreeDestPackage);
				methodNodeTemp.add("visitorDestPackage", this.visitorDestPackage);
				methodNodeTemp.add("method", gm);
				writeToFile(gm.getMethodNodePath(this.parseTreeDestPath), methodNodeTemp.render());
			}
		}
		
		// treeBuilder:
		treeBuilderTemp.add("dslName", this.dslName);
		treeBuilderTemp.add("packageName", this.parseTreeDestPackage);
		treeBuilderTemp.add("firstInterfaceName", this.firstInterfaceName);
		treeBuilderTemp.add("scopesList", this.tree);
		
		//visitorSuperClass:
		visitorSuperClassTemp.add("dslName", this.dslName);
		visitorSuperClassTemp.add("package", this.visitorDestPackage);
		visitorSuperClassTemp.add("parseTreePackage", this.parseTreeDestPackage);
		visitorSuperClassTemp.add("tree", this.tree);
		
		// ========== write templates to file
		// treeBuilder:
		Path tPath = parseTreeDestPath.resolve(Paths.get(toUC(this.dslName) + "TreeBuilder.java"));
		writeToFile(tPath, treeBuilderTemp.render());
		
		//visitorSuperClass:
		Path vPath = visitorDestPath.resolve(Paths.get("A" + toUC(this.dslName) + "Visitor.java"));
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
	 * <li>There must not be two (or more) methods that have the same name AND are both nested or
	 * not nested methods.
	 * <li>There has to be at least one method that has <code>ParseTree</code> as its return type
	 * 	and thus concludes a statement of the DSL.
	 * </ul>
	 * See {@link #checkInterfacesUpperCase()}and {@link #checkEndingMethod(List)}
	 * for more details.
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
	private boolean checkAllRequirements() throws IOException {
		if (!checkInterfacesUpperCase()) {
			return false;
		}
		
		if (!checkEndingMethod()) {
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
		DirectoryStream<Path> stream = Files.newDirectoryStream(this.sourcePath, "*.java");
		
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
			
			if (!Character.isUpperCase(filePath.toString().charAt(0))) {
				StringBuilder message = new StringBuilder();
				message.append("Interface ");
				message.append(filePath.toString());
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
	 * checks if at least one method exists that concludes a DSL statement.
	 * Therefore, it has to have the return type <code>ParseTree</code>.
	 * 
	 * @param genScopeList list of all {@link GeneratorScope} objects needed for the generation
	 * of code later.
	 * @return true, if at least one method with return type <code>ParseTree</code> is found.
	 * @see #checkAllRequirements(List)
	 */
	private boolean checkEndingMethod() {
		for (GeneratorScope gs : this.tree) {
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
	private String toUC(final String s) {
		if (s == null || s.length() == 0) {
			return "";
		} else if(s.length() == 1) {
			return s.toUpperCase();
		} else {
			return s.substring(0,1).toUpperCase() + s.substring(1);
		}
	}

}
