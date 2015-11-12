package classes;

import interfaces.Tree;

public class Scope implements Tree {
	
	private String iface;
	private Method[] methods;

	@Override
	public void print() {
		System.out.println("used interface: " + iface);
		for (Method m : methods) {
			m.print();
		}

	}

}
