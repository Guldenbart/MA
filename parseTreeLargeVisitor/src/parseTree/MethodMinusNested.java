package parseTree;

import visitor.Visitor;

public class MethodMinusNested implements Visitable {
	
	private ParseTree _parseTree;
	private final String name = "minus";
	
	public MethodMinusNested(ParseTree parseTree) {
		_parseTree = parseTree;
	}
	
	public ParseTree parseTree() {
		return _parseTree;
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		
		sb.append(name + '(');
		sb.append(_parseTree.toString());
		sb.append(").");
		
		return sb.toString();
	}

	@Override
	public void accept(Visitor visitor) {
		visitor.visit(this);
	}

}
