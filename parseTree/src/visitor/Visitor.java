package visitor;

import parseTree.NestedMethod;
import parseTree.Scope;
import parseTree.SimpleMethod;

public interface Visitor {
	public void visit(Scope scope);
	public void visit(SimpleMethod sm);
	public void visit(NestedMethod nm);
}
