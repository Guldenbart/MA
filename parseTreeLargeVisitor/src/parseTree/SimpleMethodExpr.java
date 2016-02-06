package parseTree;

import visitor.Visitor;

public class SimpleMethodExpr extends ASimpleMethod {

	public SimpleMethodExpr(double value) {
		super("expr", value);
	}

	@Override
	public void accept(Visitor visitor) {
		visitor.visit(this);
	}

}
