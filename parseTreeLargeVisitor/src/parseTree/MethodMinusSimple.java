package parseTree;

import visitor.Visitor;

public class MethodMinusSimple implements Visitable {
	
	public double _value;
	private final String name = "minus";
	
	public MethodMinusSimple(double value) {
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
