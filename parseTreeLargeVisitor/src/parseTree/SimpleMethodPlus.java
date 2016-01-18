package parseTree;

import visitor.Visitor;

public class SimpleMethodPlus extends ASimpleMethod {
	
	public SimpleMethodPlus(double value) {
		this.value = value;
		name = "plus";
	}

	@Override
	public void accept(Visitor visitor) {
		visitor.visit(this);
	}

}
