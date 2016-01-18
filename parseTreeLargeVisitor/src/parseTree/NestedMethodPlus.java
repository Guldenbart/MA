package parseTree;

import visitor.Visitor;

public class NestedMethodPlus extends ANestedMethod {
	
	public NestedMethodPlus(ParseTree parseTree) {
		this.parseTree = parseTree;
		name = "plus";
	}

	@Override
	public void accept(Visitor visitor) {
		visitor.visit(this);
	}

}
