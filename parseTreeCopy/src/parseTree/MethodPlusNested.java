package parseTree;

import visitor.Visitor;

public class MethodPlusNested extends AMethodNested {
	
	public MethodPlusNested(ParseTree parseTree) {
		this.parseTree = parseTree;
		name = "plus";
	}

	public void accept(Visitor visitor) {
		visitor.visit(this);
	}

}
