package parseTree;

import visitor.Visitor;

public class MethodExprSimple implements Visitable {
	
	private double _value;
	private final String name = "expr";

	public MethodExprSimple(double value) {
		_value = value;
	}
	
	public double value() {
		return _value;
	}
	
	public String toString() {
		return name + '(' + Double.toString(_value) + ").";
	}

	@Override
	public void accept(Visitor visitor) {
		visitor.visit(this);
	}

}
