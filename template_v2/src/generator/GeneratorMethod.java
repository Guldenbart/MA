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
final class GeneratorMethod {
	
	/**
	 * constant value which indicates that this method has no parameter. 
	 */
	private static final int TYPE_METHOD = 0;
	
	/**
	 * constant value which indicates that this method has a parameter other than ParseTree. 
	 */
	private static final int TYPE_SIMPLE_METHOD = 1;
	
	/**
	 * constant value which indicates that this method has ParseTree as its parameter. 
	 */
	private static final int TYPE_NESTED_METHOD = 3;
	
	
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
	 * Parameter type of the method.
	 * This is an emtpy string if the method has no parameter.
	 */
	private String parameterType;
	
	/**
	 * Parameter name of the method.
	 * This is an empty string if the method has no parameter.
	 */
	private String parameterName;
	
	/**
	 * indicates the type of the method.
	 * @see #TYPE_METHOD
	 * @see #TYPE_SIMPLE_METHOD
	 * @see #TYPE_NESTED_METHOD
	 */
	private int type;
	
	/**
	 * Constructor that initializes all the instance variables of this class.
	 * This constructor is used for methods that have an argument.
	 * @param iName name of the interface that the method belongs to.
	 * @param mName name of the method.
	 * @param retType return type of the method.
	 * @param paramType type of the method argument.
	 * @param paramName name of the method argument.
	 */
	public GeneratorMethod(final String iName, final String mName,
			final String retType, final String paramType, final String paramName) {
		interfaceName = iName;
		name = mName;
		returnType = retType;
		parameterType = paramType;
		parameterName = paramName;
		if (parameterType.equals("ParseTree")) {
			this.type = TYPE_NESTED_METHOD;
		} /*else if (returnType.equals("")){
			this.type = TYPE_METHOD;
		}*/ else {
			this.type = TYPE_SIMPLE_METHOD;
		}
	}
	
	/**
	 * Constructor for methods without parameter.
	 * 
	 * <code>paramType</code> and <code>paramName</code> are set to an empty string.
	 * @param iName name of the interface that the method belongs to.
	 * @param mName name of the method.
	 * @param retType return type of the method.
	 */
	public GeneratorMethod(final String iName, final String mName,
			final String retType) {
		this(iName, mName, retType, "", "");
		this.type = TYPE_METHOD;
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
	public String getParameterType() {
		return parameterType;
	}
	
	/**
	 * Gets the method's argument name.
	 * @return argument name of the method
	 */
	public String getParameterName() {
		return parameterName;
	}
	
	/**
	 * Tells whether or not this method has a parameter.
	 * @return true if the method has a parameter, false otherwise.
	 */
	public boolean getHasParameter() {
		return this.type != TYPE_METHOD;
	}
	
	/**
	 * Tells the caller if, after calling this method, the current scope ends.
	 * This is currently only used inside the stringTemplates.
	 * @return true if the current scope ends after calling this method, false otherwise.
	 */
	public boolean getScopeEnds() {
		return !(interfaceName.equals(returnType));
	}
	
	/**
	 * Tells the caller if calling this method concludes a statement of the corresponding DSL.
	 * @return true if this method concludes a statement of the corresponding DSL, false otherwise.
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
		return this.type == TYPE_METHOD;
	}
	
	/**
	 * method to find out if this method is a SimpleMethod.
	 *
	 * @return true, if it is a SimpleMethod, false otherwise.
	 */
	public boolean getIsSimpleMethod() {
		return this.type == TYPE_SIMPLE_METHOD;
	}
	
	/**
	 * method to find out if this method is a NestedMethod.
	 * @return true, if it is a NestedMethod, false otherwise.
	 */
	public boolean getIsNestedMethod() {
		return this.type == TYPE_NESTED_METHOD;
	}

	/**
	 * Composes the file path for a {@link AMethodNode} file that is generated
	 * from the information of the corresponding GeneratorMethod object.
	 * @param basePath path that the file path is based on.
	 * @return composed path object
	 * @see AMethodNode
	 */
	public Path getMethodNodePath(final Path basePath) {
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
		if (getHasParameter()) {
			sb.append(parameterType);
			sb.append(" ");
			sb.append(parameterName);
		}
		sb.append(")");
		
		return sb.toString();
	}
	
	//TODO may comment on this?
	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		
		if (o == null) {
			return false;
		}
		if (!(o instanceof GeneratorMethod)) {
			return false;
		}
		GeneratorMethod gm = (GeneratorMethod)o;
		
		if (this.name.equals(gm.name)) {
			if (this.type == TYPE_NESTED_METHOD && gm.type == TYPE_NESTED_METHOD) {
				return true;
			}
			if (this.type != TYPE_NESTED_METHOD && gm.type != TYPE_NESTED_METHOD) {
				return true;
			}
		}
		
		return false;
	}
	
	@Override
	public int hashCode() {
		int result = 17;
		result = 31 * result + this.name.hashCode();
		
		// we do this because of how equals() is implemented.
		if (this.type == TYPE_METHOD) {
			result = 31 * result + TYPE_SIMPLE_METHOD;
		} else {
			result = 31 * result + this.type;
		}
		
		return result;
	}
	
	// TODO FRAGE: Gestaltung der Variablen-Namen (auch: *packageNAME, damit man weiß, dass es ein String und keine Variable ist?)
	// TODO <code> tags in javadoc: alles mit 'Node'
	// TODO FRAGE: GeneratorScope und GeneratorMethod als interne Klassen?
	// TODO alle restrictions reincoden
	// TODO werden finalizers benötigt? bzw. try-with
	// TODO immer this oder nie?
}
