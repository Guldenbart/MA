package parseTree;

import visitor.Visitor;

public class MethodPlusSimple extends MethodSimple {
	
	public MethodPlusSimple(double value) {
		_value = value;
		name = "plus";
	}

	@Override
	public void accept(Visitor visitor) {
		visitor.visit(this);
	}

}
