package parseTree;

/**
 * generic subclass of {@link AMethod} and abstract super class for all method
 * classes that have any other type than <code>ParseTree</code> as argument
 * type.
 * 
 * @author Daniel Fritz
 * @param <T> place holder for the argument type.
 * @see AMethod
 * @see ANestedMethod 
 */
public abstract class ASimpleMethod<T> extends AMethod {
	
	/**
	 * value of the parameter that was passed to this method.
	 */
	private final T value;
	
	/**
	 * constructor that initializes <code>name</code> and <code>value</code>
	 * of the object.
	 * @param methodName name that the method is given
	 * @param val value of the parameter
	 */
	public ASimpleMethod(final String methodName, final T val) {
		super(methodName);
		this.value = val;
	}
	
	/**
	 * gets the value of the parameter that was passed to this method.
	 * @return value of the parameter
	 */
	public final T getValue() {
		return value;
	}
	
	@Override
	public final String toString() {
		return getName() + '(' + value + ").";
	}	

}
