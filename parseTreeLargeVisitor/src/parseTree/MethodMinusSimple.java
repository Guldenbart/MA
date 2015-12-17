package parseTree;

import visitor.Visitor;

public class MethodMinusSimple extends AMethodSimple {
	
	public MethodMinusSimple(double value) {
		_value = value;
		name = "minus";
	}

	@Override
	public void accept(Visitor visitor) {
		visitor.visit(this);
	}

}
