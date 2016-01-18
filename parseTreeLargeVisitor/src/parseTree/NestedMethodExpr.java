package parseTree;

import visitor.Visitor;

public class NestedMethodExpr extends ANestedMethod {
	
	public NestedMethodExpr(ParseTree parseTree) {
		this.parseTree = parseTree;
		name = "expr";
	}

	@Override
	public void accept(Visitor visitor) {
		visitor.visit(this);
	}

}
