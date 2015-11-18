package parseTree;

import interfaces.IMethod;

public class NestedMethod implements IMethod {
	
	private String name;
	private ParseTree arguments;
	
	public NestedMethod(String methodName, ParseTree s) {
		name = methodName;
		arguments = s;
	}

	@Override
	public String toString(boolean printScopes) {
		StringBuilder sb = new StringBuilder();
		
		sb.append(name + '(');
		sb.append(arguments.toString(true));
		sb.append(')');
		
		return sb.toString();
	}

}
