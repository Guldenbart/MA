package visitor;

import expressions.Expr;
import expressions.Operation;
import expressions.Value;
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

public class ExpressionBuildVisitor implements Visitor {
	
	private Expr e;
	
	public Expr expression() {
		return e;
	}

	// TODO Frage: auch als default-Implementierung ins Interface?
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
		e = new Value(mes.value());		
	}

	@Override
	public void visit(NestedMethodExpr men) {
		ExpressionBuildVisitor nestedVisitor = new ExpressionBuildVisitor();
		men.parseTree().accept(nestedVisitor);
		e = nestedVisitor.expression();
	}

	@Override
	public void visit(SimpleMethodPlus mps) {
		e = new Operation(e, '+', new Value(mps.value()));
	}

	@Override
	public void visit(NestedMethodPlus mpn) {
		ExpressionBuildVisitor nestedVisitor = new ExpressionBuildVisitor();
		mpn.parseTree().accept(nestedVisitor);
		e = new Operation(e, '+', nestedVisitor.expression());
	}

	@Override
	public void visit(SimpleMethodMinus mms) {
		e = new Operation(e, '-', new Value(mms.value()));
	}

	@Override
	public void visit(NestedMethodMinus mmn) {
		ExpressionBuildVisitor nestedVisitor = new ExpressionBuildVisitor();
		mmn.parseTree().accept(nestedVisitor);
		e = new Operation(e, '-', nestedVisitor.expression());
	}

	@Override
	public void visit(SimpleMethodTimes mts) {
		e = new Operation(e, '*', new Value(mts.value()));
	}

	@Override
	public void visit(NestedMethodTimes mtn) {
		ExpressionBuildVisitor nestedVisitor = new ExpressionBuildVisitor();
		mtn.parseTree().accept(nestedVisitor);
		e = new Operation(e, '*', nestedVisitor.expression());
	}

	@Override
	public void visit(SimpleMethodDivided mds) {
		e = new Operation(e, '/', new Value(mds.value()));
	}

	@Override
	public void visit(NestedMethodDivided mdn) {
		ExpressionBuildVisitor nestedVisitor = new ExpressionBuildVisitor();
		mdn.parseTree().accept(nestedVisitor);
		e = new Operation(e, '*', nestedVisitor.expression());
	}

}
