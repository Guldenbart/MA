package visitor;

import parseTree.MethodDividedNested;
import parseTree.MethodDividedSimple;
import parseTree.MethodExprNested;
import parseTree.MethodExprSimple;
import parseTree.MethodMinusNested;
import parseTree.MethodMinusSimple;
import parseTree.MethodPlusNested;
import parseTree.MethodPlusSimple;
import parseTree.MethodTimesNested;
import parseTree.MethodTimesSimple;
import parseTree.ParseTree;
import parseTree.Scope;

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
	public void visit(Scope scope) {
		for (int i=0; i<scope.size(); i++) {
			scope.get(i).accept(this);
		}
	}
	
	@Override
	public void visit(MethodExprSimple mes) {
		result = mes.value();
	}
	
	@Override
	public void visit(MethodExprNested men) {
		DoubleMathVisitor nestedVisitor = new DoubleMathVisitor();
		men.parseTree().accept(nestedVisitor);
		
		result = nestedVisitor.result();
	}
	
	@Override
	public void visit(MethodPlusSimple mps) {
		result += mps.value();
	}
	
	@Override
	public void visit(MethodPlusNested mpn) {
		DoubleMathVisitor nestedVisitor = new DoubleMathVisitor();
		mpn.parseTree().accept(nestedVisitor);
		
		result += nestedVisitor.result();
	}
	
	@Override
	public void visit(MethodMinusSimple mms) {
		result -= mms.value();
	}
	
	@Override
	public void visit(MethodMinusNested mmn) {
		DoubleMathVisitor nestedVisitor = new DoubleMathVisitor();
		mmn.parseTree().accept(nestedVisitor);
		
		result -= nestedVisitor.result();
	}
	
	@Override
	public void visit(MethodTimesSimple mts) {
		result *= mts.value();
	}
	
	@Override
	public void visit(MethodTimesNested mtn) {
		DoubleMathVisitor nestedVisitor = new DoubleMathVisitor();
		mtn.parseTree().accept(nestedVisitor);
		
		result *= nestedVisitor.result();
	}
	
	@Override
	public void visit(MethodDividedSimple mds) {
		result /= mds.value();
	}
	
	@Override
	public void visit(MethodDividedNested mdn) {
		DoubleMathVisitor nestedVisitor = new DoubleMathVisitor();
		mdn.parseTree().accept(nestedVisitor);
		
		result /= nestedVisitor.result();
	}

}
