package parseTree;

public abstract class ASimpleMethod<T> extends AMethod {
	
	private T value;
	
	public ASimpleMethod(String name, T value) {
		super(name);
		this.value = value;
	}
	
	public T value() {
		return value;
	}
	
	@Override
	public String toString() {
		return name() + '(' + value + ").";
	}	

}
