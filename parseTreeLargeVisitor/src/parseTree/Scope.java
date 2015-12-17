package parseTree;

import java.util.ArrayList;

public abstract class Scope implements Visitable{
	
	protected String interfaceName;
	protected ArrayList<AMethod> methods;
	
	public int size() {
		return methods.size();
	}
	
	public Visitable get(int index) {
		return methods.get(index);
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		
		for (int i=0; i<methods.size(); i++) {
			sb.append(methods.get(i).toString());
		}
		
		return sb.toString();
	}

}
