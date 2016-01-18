package parseTree;

import visitor.Visitor;

public class SimpleMethodTimes extends ASimpleMethod {
	
	public SimpleMethodTimes(double value) {
		this.value = value;
		name = "times";
	}

	@Override
	public void accept(Visitor visitor) {
		visitor.visit(this);
	}

}
