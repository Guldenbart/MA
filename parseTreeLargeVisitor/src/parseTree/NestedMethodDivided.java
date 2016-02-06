package parseTree;

import visitor.Visitor;

public class NestedMethodDivided extends ANestedMethod {
	
	public NestedMethodDivided(ParseTree parseTree) {
		super("divided", parseTree);
	}

	@Override
	public void accept(Visitor visitor) {
		visitor.visit(this);
	}

}
