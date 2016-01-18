package parseTree;

import visitor.Visitor;

public class NestedMethodDivided extends ANestedMethod {
	
	public NestedMethodDivided(ParseTree parseTree) {
		this.parseTree = parseTree;
		name = "divided";
	}

	@Override
	public void accept(Visitor visitor) {
		visitor.visit(this);
	}

}
