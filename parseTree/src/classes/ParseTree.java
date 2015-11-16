package classes;

import java.util.ArrayList;

import interfaces.Tree;

public class ParseTree implements Tree {
	
	private ArrayList<Scope> tree;
	
	public ParseTree() {
		tree = new ArrayList<Scope>();
	}
	
	public ArrayList<Scope> getScopeList() {
		return this.tree;
	}
	
	public void addScope(Scope s) {
		tree.add(s);
	}
	
	public int size() {
		return tree.size();
	}
	
	public Scope get(int index) {
		return tree.get(index);
	}
	
	public void addAll(ParseTree t) {
		tree.addAll(t.getScopeList());
	}

	@Override
	public void print() {
		// TODO Auto-generated method stub

	}

}
