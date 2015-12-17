package visitor;

import expressions.Expr;
import expressions.Operation;
import expressions.Value;
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
	public void visit(MethodExprSimple mes) {
		e = new Value(mes.value());		
	}

	@Override
	public void visit(MethodExprNested men) {
		ExpressionBuildVisitor nestedVisitor = new ExpressionBuildVisitor();
		nestedVisitor.visit(men.parseTree());
		e = nestedVisitor.expression();
	}

	@Override
	public void visit(MethodPlusSimple mps) {
		e = new Operation(e, '+', new Value(mps.value()));
	}

	@Override
	public void visit(MethodPlusNested mpn) {
		ExpressionBuildVisitor nestedVisitor = new ExpressionBuildVisitor();
		nestedVisitor.visit(mpn.parseTree());
		e = new Operation(e, '+', nestedVisitor.expression());
	}

	@Override
	public void visit(MethodMinusSimple mms) {
		e = new Operation(e, '-', new Value(mms.value()));
	}

	@Override
	public void visit(MethodMinusNested mmn) {
		ExpressionBuildVisitor nestedVisitor = new ExpressionBuildVisitor();
		nestedVisitor.visit(mmn.parseTree());
		e = new Operation(e, '-', nestedVisitor.expression());
	}

	@Override
	public void visit(MethodTimesSimple mts) {
		e = new Operation(e, '*', new Value(mts.value()));
	}

	@Override
	public void visit(MethodTimesNested mtn) {
		ExpressionBuildVisitor nestedVisitor = new ExpressionBuildVisitor();
		nestedVisitor.visit(mtn.parseTree());
		e = new Operation(e, '*', nestedVisitor.expression());
	}

	@Override
	public void visit(MethodDividedSimple mds) {
		e = new Operation(e, '/', new Value(mds.value()));
	}

	@Override
	public void visit(MethodDividedNested mdn) {
		ExpressionBuildVisitor nestedVisitor = new ExpressionBuildVisitor();
		nestedVisitor.visit(mdn.parseTree());
		e = new Operation(e, '*', nestedVisitor.expression());
	}

}
