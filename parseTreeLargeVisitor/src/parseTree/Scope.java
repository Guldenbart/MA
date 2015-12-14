package parseTree;

import java.util.ArrayList;

import visitor.Visitor;

public class Scope implements Visitable{
	
	private String interfaceName;
	private ArrayList<Visitable> methods;
	
	public Scope(String sInterfaceName) {
		interfaceName = sInterfaceName;
		methods = new ArrayList<Visitable>();
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
	
	public String getInterfaceName() {
		return interfaceName;
	}
	
	public void addVisitable(Visitable v) {
		methods.add(v);
	}

	
	@Override
	public void accept(Visitor visitor) {
		visitor.visit(this);
	}

}
