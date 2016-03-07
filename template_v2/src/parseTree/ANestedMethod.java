package parseTree;

/**
 * subclass of {@link AMethod} and abstract super class for all method classes
 * that have a ParseTree as argument.
 * 
 * @author Daniel Fritz
 * @see AMethod
 * @see ASimpleMethod
 */
public abstract class ANestedMethod extends AMethod {

	/**
	 * 
	 */
	private final ParseTree parseTree;
	
	/**
	 * constructor that initializes <code>name</code> and
	 * <code>parseTree</code> of the object.
	 * @param name name that the method is given
	 * @param tree parse tree of the expression that is nested into this method
	 */
	public ANestedMethod(final String name, final ParseTree tree) {
		super(name);
		this.parseTree = tree;
	}	
	
	/**
	 * gets the method's parseTree.
	 * @return parse tree of the method
	 */
	public final ParseTree getParseTree() {
		return parseTree;
	}
	
	@Override
	public final String toString() {
		StringBuilder sb = new StringBuilder();
		
		sb.append(getName() + '(');
		sb.append(parseTree.toString());
		sb.append(").");
		
		return sb.toString();
	}
}
