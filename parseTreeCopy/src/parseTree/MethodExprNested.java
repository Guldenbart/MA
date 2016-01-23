package parseTree;

import visitor.Visitor;

public class MethodExprNested extends AMethodNested {
	
	public MethodExprNested(ParseTree parseTree) {
		this.parseTree = parseTree;
		name = "expr";
	}

	public void accept(Visitor visitor) {
		visitor.visit(this);
	}

}
