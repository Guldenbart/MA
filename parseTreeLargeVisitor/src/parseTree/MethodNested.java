package parseTree;

public abstract class MethodNested implements Visitable {

	protected ParseTree _parseTree;
	protected String name;
	
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
}
