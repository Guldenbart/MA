package parseTree;

import java.util.ArrayList;

public final class ScopeList extends ArrayList<Scope>{

	private static final long serialVersionUID = -2347749301870699299L;
	
	public void print() {
		print(false);
	}
	
	protected void print(boolean isNested) {
		for (int i=0; i<this.size(); i++) {
			this.get(i).print(i == this.size()-1);
		}
		if (!isNested) {
			System.out.print(";\n");
		}
	}
	
	/*
	public void printExpression() {
		for (int i=0; i<this.size(); i++) {
			this.get(i).printExpression();
		}
		System.out.print('\n');
	}
	*/
}
