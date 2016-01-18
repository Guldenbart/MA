package parseTree;

import visitor.Visitor;

public class SimpleMethodDivided extends ASimpleMethod {
	
	public SimpleMethodDivided(double value) {
		this.value = value;
		name = "divided";
	}
	
	@Override
	public void accept(Visitor visitor) {
		visitor.visit(this);
	}

}
