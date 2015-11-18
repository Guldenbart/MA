package parseTree;

import java.util.ArrayList;

public final class ScopeList extends ArrayList<Scope>{

	private static final long serialVersionUID = -2347749301870699299L;
	
	public String toString() {
		return toString(false);
	}
	
	protected String toString(boolean isNested) {
		StringBuilder sb = new StringBuilder();
		
		sb.append("TreeBuilder.begin().");
		for (int i=0; i<this.size(); i++) {
			sb.append(this.get(i).toString(i == this.size()-1));
		}
		if (!isNested) {
			sb.append(";\n");
		}
		
		return sb.toString();
	}
}
