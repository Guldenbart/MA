package parseTree;

import visitor.Visitor;

public class MethodMinusNested extends AMethodNested {
	
	public MethodMinusNested(ParseTree parseTree) {
		_parseTree = parseTree;
		name = "minus";
	}

	@Override
	public void accept(Visitor visitor) {
		visitor.visit(this);
	}

}
