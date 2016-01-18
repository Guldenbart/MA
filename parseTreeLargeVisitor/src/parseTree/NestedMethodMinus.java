package parseTree;

import visitor.Visitor;

public class NestedMethodMinus extends ANestedMethod {
	
	public NestedMethodMinus(ParseTree parseTree) {
		this.parseTree = parseTree;
		name = "minus";
	}

	@Override
	public void accept(Visitor visitor) {
		visitor.visit(this);
	}

}
