package parseTree;

import visitor.Visitor;

public class SimpleMethodMinus extends ASimpleMethod {
	
	public SimpleMethodMinus(double value) {
		this.value = value;
		name = "minus";
	}

	@Override
	public void accept(Visitor visitor) {
		visitor.visit(this);
	}

}
