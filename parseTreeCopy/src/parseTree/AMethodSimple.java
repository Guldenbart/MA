package parseTree;

public abstract class AMethodSimple<T> extends AMethod {
	
	protected T value;
	
	public T value() {
		return value;
	}
	
	@Override
	public String toString() {
		return name + '(' + value.toString() + ").";
	}	

}
