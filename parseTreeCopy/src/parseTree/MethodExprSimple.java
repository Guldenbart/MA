package parseTree;

import visitor.Visitor;

public class MethodExprSimple extends AMethodSimple<Double> {

	public MethodExprSimple(Double value) {
		this.value = value;
		name = "expr";
	}

	public void accept(Visitor visitor) {
		visitor.visit(this);
	}

}
