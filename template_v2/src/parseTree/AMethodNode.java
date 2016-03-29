package parseTree;

/**
 * abstract super class for all classes that represent any type of method in a
 * {@link ParseTree}.
 * 
 * If a method has no argument, then this is the super class for the
 * class that will represent it. Otherwise, refer to {@link ASimpleMethodNode} or
 * {@link ANestedMethodNode}.
 * 
 * @author Daniel Fritz
 * @see ASimpleMethodNode
 * @see ANestedMethodNode
 *
 */
public abstract class AMethodNode implements Visitable {
	
	/**
	 * name of the method.
	 */
	private final String name;
	
	
	/**
	 * constructor that initializes <code>name</code> with a given String.
	 * @param methodName name that the method is given
	 */
	public AMethodNode(final String methodName) {
		name = methodName;
	}
	
	/**
	 * gets the method name.
	 * @return name of the method
	 */
	public final String getName() {
		return name;
	}
}
