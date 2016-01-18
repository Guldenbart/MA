package parseTree;

public abstract class ASimpleMethod extends AMethod {
	
	protected double value;
	
	public double value() {
		return value;
	}
	
	@Override
	public String toString() {
		return name + '(' + value + ").";
	}	

}
