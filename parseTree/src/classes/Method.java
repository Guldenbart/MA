package classes;

import interfaces.Tree;

public class Method implements Tree {
	
	private String name;
	Object[] arguments;

	@Override
	public void print() {
		System.out.print("Method: " + name + getArgumentString() + " | ");

	}
	
	private String getArgumentString() {
		StringBuilder sb = new StringBuilder();
		
		for (Object o : arguments) {
			sb.append(o.toString());
		}
		
		return sb.toString();
	}

}
