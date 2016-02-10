package parseTree;

import java.util.ArrayList;

public abstract class Lope implements Visitable{
	
	private String interfaceName;
	private ArrayList<AMethod> methods;
	
	public Lope(String interfaceName, ArrayList<AMethod> methods) {
		this.interfaceName = interfaceName;
		this.methods = methods;
	}
	
	public String interfaceName() {
		return this.interfaceName;
	}
	
	public ArrayList<AMethod> methods() {
		return this.methods;
	}
	
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
