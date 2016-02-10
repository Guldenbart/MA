package parseTree;

/**
 * abstract super class for all classes that represent any type of method in a
 * {@link ParseTree}.
 * @author Daniel Fritz
 *
 */
public abstract class AMethod implements Visitable {
	
	/**
	 * name of the method.
	 */
	private final String name;
	
	
	/**
	 * constructor that initializes the name of the method with a given string.
	 * @param methodName name that the method is given
	 */
	public AMethod(final String methodName) {
		name = methodName;
	}
	
	/**
	 * gets the method name.
	 * @return name of the method
	 */
	public final String name() {
		return name;
	}
}
