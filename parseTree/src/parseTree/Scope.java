package parseTree;

import java.util.ArrayList;

import visitor.Visitable;
import visitor.Visitor;

public class Scope implements Visitable{
	
	private String interfaceName;
	private ArrayList<IMethod> methods;
	
	public Scope(String sInterfaceName) {
		interfaceName = sInterfaceName;
		methods = new ArrayList<IMethod>();
	}

	public String toString(boolean isLastScope) {
		StringBuilder sb = new StringBuilder();
		
		if (!isLastScope) {
			for (int i=0; i<methods.size(); i++) {
				sb.append(methods.get(i).toString(false));
				sb.append('.');
			}
		} else {
			for (int i=0; i<methods.size(); i++) {
				sb.append(methods.get(i).toString(false));
				if (i < (methods.size()-1)) {
					sb.append('.');
				}
			}
		}
		
		return sb.toString();
	}
	
	public void addMethod(String methodName, ParseTree arguments) {
		methods.add(new NestedMethod(methodName, arguments));
	}
	
	public void addMethod(String methodName, Object...arguments) {
		methods.add(new SimpleMethod(methodName, arguments));
	}
	
	public String getInterfaceName() {
		return interfaceName;
	}

	@Override
	public void accept(Visitor visitor) {
		// TODO darf/sollte hier stattdessen nur 'visitor.visit(this);' stehen?
		for (IMethod method : methods) {
			method.accept(visitor);
		}
	}

}
