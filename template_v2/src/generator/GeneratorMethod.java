package generator;

import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * class that wraps everything we need to know about a method
 * for code generation.
 * 
 * Each <code>GeneratorMethod</code> object is used for the generation of one
 * MethodNode object, one method in the <code>TreeBuilder</code> class and one
 * method in the abstract <code>Visitor</code> super class.
 * 
 * PLEASE NOTE: In the documentation of this class, the word 'method' always
 * refers to the method that an object of this class represents.
 * 
 * @author Daniel Fritz
 * @see AMethodNode
 */
public final class GeneratorMethod {
	
	/**
	 * Name of the interface that the method belongs to.
	 */
	private String interfaceName;
	
	/**
	 * Name of the method.
	 */
	private String name;
	
	/**
	 * Return type of the method.
	 */
	private String returnType;
	
	/**
	 * Argument type of the method.
	 * This is an emtpy string if the method has no argument.
	 */
	private String argumentType;
	
	/**
	 * argument name of the method.
	 * This is an empty string if the method has no argument.
	 */
	private String argumentName;
	
	/**
	 * Constructor that initializes all the instance variables of this class.
	 * This constructor is used for methods that have an argument.
	 * @param iName name of the interface that the method belongs to.
	 * @param mName name of the method.
	 * @param retType return type of the method.
	 * @param argType type of the method argument.
	 * @param argName name of the method argument.
	 */
	public GeneratorMethod(final String iName, final String mName,
			final String retType, final String argType, final String argName) {
		interfaceName = iName;
		name = mName;
		returnType = retType;
		argumentType = argType;
		argumentName = argName;
	}
	
	/**
	 * Constructor for methods without argument.
	 * 
	 * <code>argType</code> and <code>argName</code> are set to an empty
	 * string.
	 * @param iName name of the interface that the method belongs to.
	 * @param mName name of the method.
	 * @param retType return type of the method.
	 */
	public GeneratorMethod(final String iName, final String mName,
			final String retType) {
		this(iName, mName, retType, "", "");
	}
	
	/**
	 * gets the method's name.
	 * @return name of the method
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * gets the method's return type.
	 * @return return type of the method
	 */
	public String getReturnType() {
		return returnType;
	}
	
	/**
	 * gets the method's argument type.
	 * @return argument type of the method
	 */
	public String getArgumentType() {
		return argumentType;
	}
	
	/**
	 * Gets the method's argument name.
	 * @return argument name of the method
	 */
	public String getArgumentName() {
		return argumentName;
	}
	
	/**
	 * Tells whether or not this method has an argument.
	 * @return true if the method has an argument, false otherwise.
	 */
	public boolean getHasArgument() {
		return !(argumentType.equals(""));
	}
	
	/**
	 * Tells the caller if, after calling this method, the current scope ends.
	 * This is currently only used inside the stringTemplates.
	 * @return true if the current scope ends after calling this method,
	 * 		false otherwise.
	 */
	public boolean getScopeEnds() {
		return !(interfaceName.equals(returnType));
	}
	
	/**
	 * Tells the caller if calling this method concludes a statement of
	 * the corresponding DSL.
	 * @return true if this method concludes a statement of
	 * 		the corresponding DSL, false otherwise.
	 */
	public boolean getTreeEnds() {
		return returnType.equals("ParseTree");
	}
	
	/**
	 * finds out whether or not this method class is a direct subclass of
	 * AMethod.
	 * see {@link AMethodNode} for more information.
	 * @return true, if it is a a direct subclass of AMethod, false otherwise.
	 */
	public boolean getIsMethod() {
		return (argumentType.equals(""));
	}
	
	/**
	 * method to find out if this method is a SimpleMethod.
	 *
	 * @return true, if it is a SimpleMethod, false otherwise.
	 */
	public boolean getIsSimpleMethod() {
		return (!argumentType.equals("ParseTree") && !argumentType.equals(""));
	}
	
	/**
	 * method to find out if this method is a NestedMethod.
	 * @return true, if it is a NestedMethod, false otherwise.
	 */
	public boolean getIsNestedMethod() {
		return argumentType.equals("ParseTree");
	}

	/**
	 * Composes the file path for a {@link AMethodNode} file that is generated
	 * from the information of the corresponding GeneratorMethod object.
	 * @param basePath path that the file path is based on.
	 * @return composed path object
	 * @see AMethodNode
	 */
	public Path getVisitorClassPath(final Path basePath) {
		if (getIsMethod()) {
			return basePath.resolve(Paths.get("MethodNode" + name + ".java"));
		} else if (getIsNestedMethod()) {
			return basePath.resolve(Paths.get("NestedMethodNode" + name + ".java"));
		} else {
			return basePath.resolve(Paths.get("SimpleMethodNode" + name + ".java"));
		}
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		
		sb.append(returnType);
		sb.append(" ");
		sb.append(name);
		sb.append("(");
		if (getHasArgument()) {
			sb.append(argumentType);
			sb.append(" ");
			sb.append(argumentName);
		}
		sb.append(")");
		
		return sb.toString();
	}
	
	// TODO FRAGE: Gestaltung der Variablen-Namen (auch: *packageNAME, damit man weiß, dass es ein String und keine Variable ist?)
	// TODO <code> tags in javadoc: alles mit 'Node'
	// TODO FRAGE: GeneratorScope und GeneratorMethod als interne Klassen?
	// TODO test toString() von GeneratorScope und GeneratorMethod
	// TODO toString() von Method-Klassen überarbeiten
	// TODO alle restrictions reincoden (eine Methode, die alle Einschränkungen überprüft?)
	// TODO werden finalizers benötigt? bzw. try-with
	// TODO in GeneratorMethod: Muss visitorSuperClassTemp noch Instanzvariable sein? Im Moment wird alles in einer Methode gemacht
}
