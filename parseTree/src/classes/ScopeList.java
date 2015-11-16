package classes;

import java.util.ArrayList;

public final class ScopeList extends ArrayList<Scope>{

	private static final long serialVersionUID = -2347749301870699299L;
	
	public void print(boolean printScopes) {
		for (int i=0; i<this.size(); i++) {
			this.get(i).print(printScopes);
		}
		System.out.print('\n');
	}
}
