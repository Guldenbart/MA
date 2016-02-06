package parseTree;

import visitor.Visitor;

public class NestedMethodMinus extends ANestedMethod {
	
	public NestedMethodMinus(ParseTree parseTree) {
		super("minus", parseTree);
	}

	@Override
	public void accept(Visitor visitor) {
		visitor.visit(this);
	}

}
