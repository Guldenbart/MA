package parseTree;

import visitor.Visitor;

public class MethodTimesSimple extends AMethodSimple<Double> {
	
	public MethodTimesSimple(Double value) {
		this.value = value;
		name = "times";
	}

	public void accept(Visitor visitor) {
		visitor.visit(this);
	}

}
