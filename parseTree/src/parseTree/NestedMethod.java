package parseTree;

import interfaces.IMethod;

public class NestedMethod implements IMethod {
	
	private String name;
	private ScopeList arguments;
	
	public NestedMethod(String methodName, ScopeList s) {
		name = methodName;
		arguments = s;
	}

	@Override
	public void print(boolean printScopes) {
		System.out.print(name + '(');
		arguments.print(printScopes);
		System.out.print(')');
	}

}
