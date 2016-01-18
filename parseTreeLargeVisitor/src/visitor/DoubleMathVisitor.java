package visitor;

import parseTree.NestedMethodDivided;
import parseTree.SimpleMethodDivided;
import parseTree.NestedMethodExpr;
import parseTree.SimpleMethodExpr;
import parseTree.NestedMethodMinus;
import parseTree.SimpleMethodMinus;
import parseTree.NestedMethodPlus;
import parseTree.SimpleMethodPlus;
import parseTree.NestedMethodTimes;
import parseTree.SimpleMethodTimes;
import parseTree.ParseTree;
import parseTree.ScopeIntermediate;
import parseTree.ScopeStart;

public class DoubleMathVisitor implements Visitor {
	
	private double result;
	
	public DoubleMathVisitor() {
		result = 0.0;
	}
	
	public double result() {
		return result;
	}
	
	@Override
	public void visit(ParseTree parseTree) {
		for (int i=0; i<parseTree.size(); i++) {
			parseTree.get(i).accept(this);
		}
	}

	@Override
	public void visit(ScopeStart scopeStart) {
		for (int i=0; i<scopeStart.size(); i++) {
			scopeStart.get(i).accept(this);
		}
	}
	
	@Override
	public void visit(ScopeIntermediate scopeIntermediate) {
		for (int i=0; i<scopeIntermediate.size(); i++) {
			scopeIntermediate.get(i).accept(this);
		}
	}
	
	@Override
	public void visit(SimpleMethodExpr mes) {
		result = mes.value();
	}
	
	@Override
	public void visit(NestedMethodExpr men) {
		DoubleMathVisitor nestedVisitor = new DoubleMathVisitor();
		men.parseTree().accept(nestedVisitor);
		result = nestedVisitor.result();
	}
	
	@Override
	public void visit(SimpleMethodPlus mps) {
		result += mps.value();
	}
	
	@Override
	public void visit(NestedMethodPlus mpn) {
		DoubleMathVisitor nestedVisitor = new DoubleMathVisitor();
		mpn.parseTree().accept(nestedVisitor);
		result += nestedVisitor.result();
	}
	
	@Override
	public void visit(SimpleMethodMinus mms) {
		result -= mms.value();
	}
	
	@Override
	public void visit(NestedMethodMinus mmn) {
		DoubleMathVisitor nestedVisitor = new DoubleMathVisitor();
		mmn.parseTree().accept(nestedVisitor);
		result -= nestedVisitor.result();
	}
	
	@Override
	public void visit(SimpleMethodTimes mts) {
		result *= mts.value();
	}
	
	@Override
	public void visit(NestedMethodTimes mtn) {
		DoubleMathVisitor nestedVisitor = new DoubleMathVisitor();
		mtn.parseTree().accept(nestedVisitor);
		result *= nestedVisitor.result();
	}
	
	@Override
	public void visit(SimpleMethodDivided mds) {
		result /= mds.value();
	}
	
	@Override
	public void visit(NestedMethodDivided mdn) {
		DoubleMathVisitor nestedVisitor = new DoubleMathVisitor();
		mdn.parseTree().accept(nestedVisitor);
		result /= nestedVisitor.result();
	}

}
