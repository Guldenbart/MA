package parseTree;

import visitor.Visitor;

public class MethodMinusNested extends AMethodNested {
	
	public MethodMinusNested(ParseTree parseTree) {
		this.parseTree = parseTree;
		name = "minus";
	}

	public void accept(Visitor visitor) {
		visitor.visit(this);
	}

}
