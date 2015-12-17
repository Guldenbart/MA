package parseTree;

import visitor.Visitor;

public class MethodTimesSimple extends AMethodSimple {
	
	public MethodTimesSimple(double value) {
		_value = value;
		name = "times";
	}

	@Override
	public void accept(Visitor visitor) {
		visitor.visit(this);
	}

}
