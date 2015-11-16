package classes;

import java.util.ArrayList;

import interfaces.IMethod;

public class Scope {
	
	private String interfaceName;
	private ArrayList<IMethod> methods;
	
	public Scope(String sInterfaceName) {
		interfaceName = sInterfaceName;
		methods = new ArrayList<IMethod>();
	}

	public void print(boolean printScopes) {
		if (printScopes) {
			System.out.print('<' + interfaceName + ">[");
		}
		for (int i=0; i<methods.size(); i++) {
			methods.get(i).print(printScopes);
			if (i < (methods.size()-1)) {
				System.out.print('.');
			}
		}
		if (printScopes) {
			System.out.print(']');
		}
	}
	
	public void addMethod(String methodName, ScopeList arguments) {
		methods.add(new NestedMethod(methodName, arguments));
	}
	
	public void addMethod(String methodName, Object...arguments) {
		methods.add(new SimpleMethod(methodName, arguments));
	}
	
	public String getInterfaceName() {
		return interfaceName;
	}

}
