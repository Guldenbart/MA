package parseTree;

public abstract class AMethodNested extends AMethod{

	protected ParseTree _parseTree;
	
	public ParseTree parseTree() {
		return _parseTree;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		
		sb.append(name + '(');
		sb.append(_parseTree.toString());
		sb.append(").");
		
		return sb.toString();
	}
}
