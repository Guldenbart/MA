package parseTree;

import visitor.Visitor;

public class NestedMethod implements IMethod {
	
	private String name;
	private ParseTree parseTree;
	
	public NestedMethod(String methodName, ParseTree t) {
		name = methodName;
		parseTree = t;
	}
	
	@Override
	public String name() {
		return name;
	}

	public ParseTree parseTree() {
		return parseTree;
	}

	@Override
	public String toString(boolean printScopes) {
		StringBuilder sb = new StringBuilder();
		
		sb.append(name + '(');
		sb.append(parseTree.toString(true));
		sb.append(')');
		
		return sb.toString();
	}

	@Override
	public void accept(Visitor visitor) {
		visitor.visit(this);
	}
}
