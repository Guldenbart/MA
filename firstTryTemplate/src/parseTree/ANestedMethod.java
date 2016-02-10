package parseTree;

public abstract class ANestedMethod extends AMethod{

	private ParseTree parseTree;
	
	public ANestedMethod(String name, ParseTree parseTree) {
		super(name);
		this.parseTree = parseTree;
	}
	
	public ParseTree parseTree() {
		return parseTree;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		
		sb.append(name() + '(');
		sb.append(parseTree.toString());
		sb.append(").");
		
		return sb.toString();
	}
}
