package parseTree;

public abstract class MethodSimple implements Visitable {
	
	protected double _value;
	protected String name; // TODO kann/sollte der Name immer noch final sein?
	
	public double value() {
		return _value;
	}
	
	public String toString() {
		return name + '(' + _value + ").";
	}	

}
