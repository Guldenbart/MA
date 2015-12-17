package parseTree;

import visitor.Visitor;

public class MethodDividedNested extends AMethodNested {
	
	public MethodDividedNested(ParseTree parseTree) {
		_parseTree = parseTree;
		name = "divided";
	}

	@Override
	public void accept(Visitor visitor) {
		visitor.visit(this);
	}

}
