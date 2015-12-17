package parseTree;

import visitor.Visitor;

public class MethodExprNested extends AMethodNested {
	
	public MethodExprNested(ParseTree parseTree) {
		_parseTree = parseTree;
		name = "expr";
	}

	@Override
	public void accept(Visitor visitor) {
		visitor.visit(this);
	}

}
