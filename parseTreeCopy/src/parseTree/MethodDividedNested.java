package parseTree;

import visitor.Visitor;

public class MethodDividedNested extends AMethodNested {
	
	public MethodDividedNested(ParseTree parseTree) {
		this.parseTree = parseTree;
		name = "divided";
	}

	public void accept(Visitor visitor) {
		visitor.visit(this);
	}

}
