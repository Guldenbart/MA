package parseTree;

import java.util.ArrayList;

import visitor.Visitor;

public class ScopeStart extends Scope {
	
	public ScopeStart(ArrayList<Visitable> list) {
		interfaceName = "Start";
		methods = list;
	}
	
	@Override
	public void accept(Visitor visitor) {
		visitor.visit(this);
	}

}
