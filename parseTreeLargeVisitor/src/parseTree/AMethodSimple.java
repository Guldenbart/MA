package parseTree;

public abstract class AMethodSimple extends AMethod {
	
	protected double _value;
	
	public double value() {
		return _value;
	}
	
	@Override
	public String toString() {
		return name + '(' + _value + ").";
	}	

}
