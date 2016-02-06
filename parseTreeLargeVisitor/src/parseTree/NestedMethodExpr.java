package parseTree;

import visitor.Visitor;

public class NestedMethodExpr extends ANestedMethod {
	
	public NestedMethodExpr(ParseTree parseTree) {
		super("expr", parseTree);
	}

	@Override
	public void accept(Visitor visitor) {
		visitor.visit(this);
	}

}
