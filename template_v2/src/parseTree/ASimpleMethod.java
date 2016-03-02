package parseTree;

/**
 * subclass of {@link AMethod} and abstract super class for all method classes
 * that have any other type than ParseTree as argument type.
 * 
 * @author Daniel Fritz
 *
 * @param <T> place holder for the argument type.
 */
public abstract class ASimpleMethod<T> extends AMethod {
	
	/**
	 * value of the parameter that was passed to this method.
	 */
	private final T value;
	
	/**
	 * constructor that initializes name and value of the method.
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
	public final T value() {
		return value;
	}
	
	@Override
	public final String toString() {
		return name() + '(' + value + ").";
	}	

}
