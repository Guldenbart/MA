package parseTree;

import visitor.Visitor;

public class MethodExprSimple extends AMethodSimple {

	public MethodExprSimple(double value) {
		_value = value;
		name = "expr";
	}

	@Override
	public void accept(Visitor visitor) {
		visitor.visit(this);
	}

}
