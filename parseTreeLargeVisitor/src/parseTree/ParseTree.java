package parseTree;

import java.util.ArrayList;

import visitor.Visitor;

public final class ParseTree implements Visitable{

	private ArrayList<Scope> list;
	
	public ParseTree() {
		list = new ArrayList<Scope>();
	}
	
	public int size() {
		return list.size();
	}
	
	public Visitable get(int index) {
		return list.get(index);
	}
	
	@Override
	public String toString() {		
		StringBuilder sb = new StringBuilder();
		
		sb.append("TreeBuilder.begin().");
		for (int i=0; i<list.size(); i++) {
			sb.append(list.get(i).toString());
		}
		
		return sb.toString();
	}
	
	public void appendVisitable(Visitable v) {
		list.get(list.size()-1).addVisitable(v);
	}
	
	public void checkLatestScope(String interfaceName) {
		if (list.isEmpty() || list.get(list.size()-1).getInterfaceName().compareTo(interfaceName) != 0) {
			Scope scope = new Scope(interfaceName);
			list.add(scope);
		}
	}
	
	@Override
	public void accept(Visitor visitor) {
		visitor.visit(this);
	}
}
