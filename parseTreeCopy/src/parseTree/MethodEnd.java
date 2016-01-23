package parseTree;

import visitor.Visitor;

public class MethodEnd extends AMethod {
	
	public MethodEnd() {
		name = "end";
	}
	
	public String toString() {
		return name + "()";
	}

	public void accept(Visitor visitor) {
		visitor.visit(this);
	}

}
