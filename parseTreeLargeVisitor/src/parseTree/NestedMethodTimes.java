package parseTree;

import visitor.Visitor;

public class NestedMethodTimes extends ANestedMethod {
	
	public NestedMethodTimes(ParseTree parseTree) {
		this.parseTree = parseTree;
		name = "times";
	}

	@Override
	public void accept(Visitor visitor) {
		visitor.visit(this);
	}

}
