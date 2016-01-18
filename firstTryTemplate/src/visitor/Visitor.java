package visitor;

import parseTree.ParseTree;

public abstract class Visitor {
	
	public void visit(ParseTree parseTree) {
		for (int i=0; i<parseTree.size(); i++) {
			parseTree.get(i).accept(this);
		}
	}

}
