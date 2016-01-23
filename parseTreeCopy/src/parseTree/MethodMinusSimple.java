package parseTree;

import visitor.Visitor;

public class MethodMinusSimple extends AMethodSimple<Double> {
	
	public MethodMinusSimple(Double value) {
		this.value = value;
		name = "minus";
	}

	public void accept(Visitor visitor) {
		visitor.visit(this);
	}

}
