package parseTree;

import visitor.Visitor;

public class MethodEnd implements Visitable {
	
	private final String name = "end";
	
	public String toString() {
		return name + "()";
	}

	@Override
	public void accept(Visitor visitor) {
		visitor.visit(this);
	}

}
