package visitor;

import parseTree.NestedMethod;
import parseTree.Scope;
import parseTree.SimpleMethod;

public class DoubleMathVisitor implements Visitor {
	
	private double result;
	
	public DoubleMathVisitor() {
		result = 0.0;
	}
	
	public double result() {
		return result;
	}

	@Override
	public void visit(Scope scope) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(SimpleMethod sm) {
		switch (sm.name()) {
		case "expr":
			result = (double) sm.arguments()[0];
			break;
		case "plus":
			result += (double) sm.arguments()[0];
			break;
		case "minus":
			result -= (double) sm.arguments()[0];
			break;
		case "times":
			result *= (double) sm.arguments()[0];
			break;
		case "divided":
			result /= (double) sm.arguments()[0];
			break;
		}
	}

	@Override
	public void visit(NestedMethod nm) {
		DoubleMathVisitor nestedVisitor = new DoubleMathVisitor();
		nm.parseTree().accept(nestedVisitor);
		
		switch (nm.name()) {
		case "expr":
			result = nestedVisitor.result();
			break;
		case "plus":
			result += nestedVisitor.result();
			break;
		case "minus":
			result -= nestedVisitor.result();
			break;
		case "times":
			result *= nestedVisitor.result();
			break;
		case "divided":
			result /= nestedVisitor.result();
			break;
		}
	}

}
