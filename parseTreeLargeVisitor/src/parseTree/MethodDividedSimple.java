package parseTree;

import visitor.Visitor;

public class MethodDividedSimple extends MethodSimple {
	
	public MethodDividedSimple(double value) {
		_value = value;
		name = "divided";
	}
	
	@Override
	public void accept(Visitor visitor) {
		visitor.visit(this);
	}

}
