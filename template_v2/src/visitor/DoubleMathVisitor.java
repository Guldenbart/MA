package visitor;

import parseTree.AMethodNode;
import parseTreeGen.NestedMethodNodeDivided;
import parseTreeGen.NestedMethodNodeExpr;
import parseTreeGen.NestedMethodNodeMinus;
import parseTreeGen.NestedMethodNodePlus;
import parseTreeGen.NestedMethodNodeTimes;
import parseTreeGen.ScopeNodeOperator;
import parseTreeGen.ScopeNodeStart;
import parseTreeGen.SimpleMethodNodeDivided;
import parseTreeGen.SimpleMethodNodeExpr;
import parseTreeGen.SimpleMethodNodeMinus;
import parseTreeGen.SimpleMethodNodePlus;
import parseTreeGen.SimpleMethodNodeTimes;
import visitorGen.AExprDSLVisitor;

public class DoubleMathVisitor extends AExprDSLVisitor {
	
	private double result;
	
	public DoubleMathVisitor() {
		result = 0.0;
	}
	
	public double getResult() {
		return result;
	}

	@Override
	public void visit(ScopeNodeStart scopeStart) {
		for (AMethodNode m : scopeStart) {
			m.accept(this);
		}
	}
	
	@Override
	public void visit(ScopeNodeOperator scopeOperator) {
		for (AMethodNode m : scopeOperator) {
			m.accept(this);
		}
	}
	
	@Override
	public void visit(SimpleMethodNodeExpr mes) {
		result = mes.getValue();
	}
	
	@Override
	public void visit(NestedMethodNodeExpr men) {
		DoubleMathVisitor nestedVisitor = new DoubleMathVisitor();
		men.getParseTree().accept(nestedVisitor);
		result = nestedVisitor.getResult();
	}
	
	@Override
	public void visit(SimpleMethodNodePlus mps) {
		result += mps.getValue();
	}
	
	@Override
	public void visit(NestedMethodNodePlus mpn) {
		DoubleMathVisitor nestedVisitor = new DoubleMathVisitor();
		mpn.getParseTree().accept(nestedVisitor);
		result += nestedVisitor.getResult();
	}
	
	@Override
	public void visit(SimpleMethodNodeMinus mms) {
		result -= mms.getValue();
	}
	
	@Override
	public void visit(NestedMethodNodeMinus mmn) {
		DoubleMathVisitor nestedVisitor = new DoubleMathVisitor();
		mmn.getParseTree().accept(nestedVisitor);
		result -= nestedVisitor.getResult();
	}
	
	@Override
	public void visit(SimpleMethodNodeTimes mts) {
		result *= mts.getValue();
	}
	
	@Override
	public void visit(NestedMethodNodeTimes mtn) {
		DoubleMathVisitor nestedVisitor = new DoubleMathVisitor();
		mtn.getParseTree().accept(nestedVisitor);
		result *= nestedVisitor.getResult();
	}
	
	@Override
	public void visit(SimpleMethodNodeDivided mds) {
		result /= mds.getValue();
	}
	
	@Override
	public void visit(NestedMethodNodeDivided mdn) {
		DoubleMathVisitor nestedVisitor = new DoubleMathVisitor();
		mdn.getParseTree().accept(nestedVisitor);
		result /= nestedVisitor.getResult();
	}

}
