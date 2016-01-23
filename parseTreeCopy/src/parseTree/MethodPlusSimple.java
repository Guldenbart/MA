package parseTree;

import visitor.Visitor;

public class MethodPlusSimple extends AMethodSimple<Double> {
	
	public MethodPlusSimple(Double value) {
		this.value = value;
		name = "plus";
	}

	public void accept(Visitor visitor) {
		visitor.visit(this);
	}

}
