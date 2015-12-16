package parseTree;

import visitor.Visitor;

public class MethodDividedNested extends MethodNested {
	
	public MethodDividedNested(ParseTree parseTree) {
		_parseTree = parseTree;
		name = "divided";
	}

	@Override
	public void accept(Visitor visitor) {
		visitor.visit(this);
	}

}
