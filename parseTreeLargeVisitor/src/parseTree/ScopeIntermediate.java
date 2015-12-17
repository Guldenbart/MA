package parseTree;

import java.util.ArrayList;

import visitor.Visitor;

public class ScopeIntermediate extends Scope {
	
	public ScopeIntermediate(ArrayList<AMethod> list) {
		interfaceName = "Intermediate";
		methods = list;
	}
	
	@Override
	public void accept(Visitor visitor) {
		visitor.visit(this);
	}

}
