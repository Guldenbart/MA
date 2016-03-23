package parseTree;

/**
 * subclass of {@link AMethodNode} and abstract super class for all method classes
 * that have <code>ParseTree</code> as their argument.
 * 
 * @author Daniel Fritz
 * @see AMethodNode
 * @see ASimpleMethodNode
 */
public abstract class ANestedMethodNode extends AMethodNode {

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
	public ANestedMethodNode(final String name, final ParseTree tree) {
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
