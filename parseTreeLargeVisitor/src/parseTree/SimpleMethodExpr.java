package parseTree;

import visitor.Visitor;

public class SimpleMethodExpr extends ASimpleMethod {

	public SimpleMethodExpr(double value) {
		this.value = value;
		name = "expr";
	}

	@Override
	public void accept(Visitor visitor) {
		visitor.visit(this);
	}

}
