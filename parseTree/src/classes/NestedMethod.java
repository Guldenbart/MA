package classes;

import java.util.ArrayList;

import interfaces.IMethod;

public class NestedMethod implements IMethod {
	
	private String name;
	private ArrayList<Scope> arguments;
	
	public NestedMethod(String methodName, ArrayList<Scope> s) {
		name = methodName;
		arguments = s;
	}

	@Override
	public void print() {
		System.out.print(name + '(' + getArgumentString() + ')');
	}

}
