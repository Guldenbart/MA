package classes;

import java.util.ArrayList;

import interfaces.IMethod;

public class Scope {
	
	private String interfaceName;
	private ArrayList<IMethod> methods;
	
	public Scope(String sInterfaceName) {
		interfaceName = sInterfaceName;
	}

	public void print(boolean printScopes) {
		if (printScopes) {
			
		} else {
			for (int i=0; i<methods.size(); i++) {
				methods.get(i).print();
				if (i < (methods.size()-1)) {
					System.out.print('.');
				}
			}
		}
		System.out.print("\n");
	}
	
	// TODO herausfinden, ob hier die richtige Funktion ausgewählt wird
	public void addMethod(String methodName, ArrayList<Scope> arguments) {
		methods.add(new NestedMethod(methodName, arguments));
	}
	
	public void addMethod(String methodName, Object...arguments) {
		methods.add(new SimpleMethod(methodName, arguments));
	}
	
	public String getInterfaceName() {
		return interfaceName;
	}

}
