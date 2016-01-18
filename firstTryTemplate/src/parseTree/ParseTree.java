package parseTree;

import java.util.ArrayList;

import visitor.Visitor;

public final class ParseTree implements Visitable{

	private ArrayList<Lope> list;
	
	public ParseTree(ArrayList<Lope> list) {
		this.list = list;
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
	
	@Override
	public void accept(Visitor visitor) {
		visitor.visit(this);
	}
	
	// TODO Kann die Eigenschaft "generics" durch Reflection ausgelesen werden?
	// TODO Gliederung/Abschätzung
	// TODO abstract!
}
