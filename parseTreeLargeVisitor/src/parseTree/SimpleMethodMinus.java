package parseTree;

import visitor.Visitor;

public class SimpleMethodMinus extends ASimpleMethod {
	
	public SimpleMethodMinus(double value) {
		super("minus", value);
	}

	@Override
	public void accept(Visitor visitor) {
		visitor.visit(this);
	}

}
