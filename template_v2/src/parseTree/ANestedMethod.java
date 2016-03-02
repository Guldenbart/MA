package parseTree;

/**
 * subclass of {@link AMethod} and abstract super class for all method classes
 * that have a ParseTree as argument.
 * 
 * 
 * 
 * @author Daniel Fritz
 *
 */
public abstract class ANestedMethod extends AMethod {

	/**
	 * 
	 */
	private final ParseTree parseTree;
	
	/**
	 * constructor that initializes name and parse tree of the method.
	 * @param name name that the method is given
	 * @param tree parse tree of the expression that is nested into this method
	 */
	public ANestedMethod(final String name, final ParseTree tree) {
		super(name);
		this.parseTree = tree;
	}	
	
	/**
	 * gets the method's parse tree.
	 * @return parse tree of the method
	 */
	public final ParseTree parseTree() {
		return parseTree;
	}
	
	@Override
	public final String toString() {
		StringBuilder sb = new StringBuilder();
		
		sb.append(name() + '(');
		sb.append(parseTree.toString());
		sb.append(").");
		
		return sb.toString();
	}
}
