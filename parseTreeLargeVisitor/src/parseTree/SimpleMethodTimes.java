package parseTree;

import visitor.Visitor;

public class SimpleMethodTimes extends ASimpleMethod {
	
	public SimpleMethodTimes(double value) {
		super("times", value);
	}

	@Override
	public void accept(Visitor visitor) {
		visitor.visit(this);
	}

}
