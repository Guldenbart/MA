package classes;

import java.util.ArrayList;

public class Scope {
	
	private String interfaceName;
	private ArrayList<Method> methods;
	
	public Scope(String sInterfaceName) {
		interfaceName = sInterfaceName;
	}

	public void print() {
		System.out.println("used interface: " + interfaceName);
		for (Method m : methods) {
			m.print();
		}

	}
	
	public void addMethod(String methodName, Object...arguments) {
		methods.add(new Method(methodName, arguments));
	}
	
	public String getInterfaceName() {
		return interfaceName;
	}

}
