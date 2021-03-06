package parseTree;

import visitor.Visitor;

public class SimpleMethodPlus extends ASimpleMethod {
	
	public SimpleMethodPlus(double value) {
		super("plus", value);
	}

	@Override
	public void accept(Visitor visitor) {
		visitor.visit(this);
	}

}
