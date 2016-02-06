package parseTree;

import visitor.Visitor;

public class NestedMethodPlus extends ANestedMethod {
	
	public NestedMethodPlus(ParseTree parseTree) {
		super("plus", parseTree);
	}

	@Override
	public void accept(Visitor visitor) {
		visitor.visit(this);
	}

}
