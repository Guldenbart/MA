package parseTree;

import visitor.Visitor;

public class MethodEnd extends AMethod {
	
	public MethodEnd() {
		super("end");
	}
	
	public String toString() {
		return name() + "()";
	}

	@Override
	public void accept(Visitor visitor) {
		visitor.visit(this);
	}

}
