package parseTree;

import visitor.Visitor;

public class MethodTimesNested extends AMethodNested {
	
	public MethodTimesNested(ParseTree parseTree) {
		_parseTree = parseTree;
		name = "times";
	}

	@Override
	public void accept(Visitor visitor) {
		visitor.visit(this);
	}

}
