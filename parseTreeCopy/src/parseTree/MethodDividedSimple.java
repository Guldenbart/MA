package parseTree;

import visitor.Visitor;

public class MethodDividedSimple extends AMethodSimple<Double> {
	
	public MethodDividedSimple(Double value) {
		this.value = value;
		name = "divided";
	}
	
	public void accept(Visitor visitor) {
		visitor.visit(this);
	}

}
