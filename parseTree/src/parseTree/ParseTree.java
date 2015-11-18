package parseTree;

import java.util.ArrayList;

public final class ParseTree{

	private ArrayList<Scope> list;
	
	public ParseTree() {
		list = new ArrayList<Scope>();
	}
	
	public String toString() {
		return toString(false);
	}
	
	protected String toString(boolean isNested) {
		StringBuilder sb = new StringBuilder();
		
		sb.append("TreeBuilder.begin().");
		for (int i=0; i<list.size(); i++) {
			sb.append(list.get(i).toString(i == list.size()-1));
		}
		if (!isNested) {
			sb.append(";\n");
		}
		
		return sb.toString();
	}
	
	public void appendMethod(String methodName, ParseTree arguments) {
		list.get(list.size()-1).addMethod(methodName, arguments);
	}
	
	public void appendMethod(String methodName, Object...arguments) {
		list.get(list.size()-1).addMethod(methodName, arguments);
	}
	
	public void checkLatestScope(String interfaceName) {
		if (list.isEmpty() || list.get(list.size()-1).getInterfaceName().compareTo(interfaceName) != 0) {
			Scope scope = new Scope(interfaceName);
			list.add(scope);
		}
	}
}
