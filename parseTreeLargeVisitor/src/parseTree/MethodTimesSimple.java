package parseTree;

import visitor.Visitor;

public class MethodTimesSimple implements Visitable {
	
	private double _value;
	private final String name = "times"; 
	
	public MethodTimesSimple(double value) {
		_value = value;
	}
	
	public double value() {
		return _value;
	}
	
	public String toString() {
		return name + '(' + _value + ").";
	}

	@Override
	public void accept(Visitor visitor) {
		visitor.visit(this);
	}

}
