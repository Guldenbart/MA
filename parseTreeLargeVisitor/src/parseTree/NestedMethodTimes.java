package parseTree;

import visitor.Visitor;

public class NestedMethodTimes extends ANestedMethod {
	
	public NestedMethodTimes(ParseTree parseTree) {
		super("times", parseTree);
	}

	@Override
	public void accept(Visitor visitor) {
		visitor.visit(this);
	}

}
