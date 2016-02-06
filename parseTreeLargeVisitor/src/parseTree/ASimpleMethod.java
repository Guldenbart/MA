package parseTree;

public abstract class ASimpleMethod extends AMethod {
	
	private double value;
	
	public ASimpleMethod(String name, double value) {
		super(name);
		this.value = value;
	}
	
	public double value() {
		return value;
	}
	
	@Override
	public String toString() {
		return name() + '(' + value + ").";
	}	

}
