package parseTree;

import visitor.Visitor;

public class MethodPlusNested extends AMethodNested {
	
	public MethodPlusNested(ParseTree parseTree) {
		_parseTree = parseTree;
		name = "plus";
	}

	@Override
	public void accept(Visitor visitor) {
		visitor.visit(this);
	}

}
