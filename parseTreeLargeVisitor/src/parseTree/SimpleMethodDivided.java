package parseTree;

import visitor.Visitor;

public class SimpleMethodDivided extends ASimpleMethod {
	
	public SimpleMethodDivided(double value) {
		super("divided", value);
	}
	
	@Override
	public void accept(Visitor visitor) {
		visitor.visit(this);
	}

}
