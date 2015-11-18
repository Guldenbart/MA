package parseTree;

import java.util.ArrayList;

import interfaces.IMethod;

public class Scope {
	
	private String interfaceName;
	private ArrayList<IMethod> methods;
	
	public Scope(String sInterfaceName) {
		interfaceName = sInterfaceName;
		methods = new ArrayList<IMethod>();
	}

	public void print(boolean isLastScope) {
		if (!isLastScope) {
			for (int i=0; i<methods.size(); i++) {
				methods.get(i).print(false);
				System.out.print('.');
			}
		} else {
			for (int i=0; i<methods.size(); i++) {
				methods.get(i).print(false);
				if (i < (methods.size()-1)) {
					System.out.print('.');
				}
			}
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
