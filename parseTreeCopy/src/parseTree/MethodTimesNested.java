package parseTree;

import visitor.Visitor;

public class MethodTimesNested extends AMethodNested {
	
	public MethodTimesNested(ParseTree parseTree) {
		this.parseTree = parseTree;
		name = "times";
	}

	
	public void accept(Visitor visitor) {
		visitor.visit(this);
	}
}
