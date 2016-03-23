package visitor;

import expressions.Expr;
import expressions.Operation;
import expressions.Value;
import parseTreeGen.ScopeNodeIntermediate;
import parseTreeGen.ScopeNodeStart;
import parseTreeGen.NestedMethodNodeDivided;
import parseTreeGen.NestedMethodNodeExpr;
import parseTreeGen.NestedMethodNodeMinus;
import parseTreeGen.NestedMethodNodePlus;
import parseTreeGen.NestedMethodNodeTimes;
import parseTreeGen.SimpleMethodNodeDivided;
import parseTreeGen.SimpleMethodNodeExpr;
import parseTreeGen.SimpleMethodNodeMinus;
import parseTreeGen.SimpleMethodNodePlus;
import parseTreeGen.SimpleMethodNodeTimes;
import visitorGen.AExprDSLVisitor;

/**
 * This is a visitor that builds one {@link Expr} object out of one statement
 * of the 'expr' dsl.
 * 
 * @author Daniel Fritz
 * @see AVisitor
 * @see AExprDSLVisitor
 * @see Expr
 */
public final class ExpressionBuildVisitor extends AExprDSLVisitor {
	
	/**
	 * Expr object that is built by this visitor by visiting every node of
	 * the parse tree of a exprDSL statement.
	 * 
	 * @see ParseTree
	 * TODO Referenz zu exprDSl.package-info.java
	 */
	private Expr e;
	
	/**
	 * gets the expression built by this visitor.
	 * @return Expr object built by this visitor
	 */
	public Expr getExpression() {
		return e;
	}

	@Override
	public void visit(final ScopeNodeStart scopeStart) {
		for (int i = 0; i < scopeStart.size(); i++) {
			scopeStart.get(i).accept(this);
		}
	}
	
	@Override
	public void visit(final ScopeNodeIntermediate scopeIntermediate) {
		for (int i = 0; i < scopeIntermediate.size(); i++) {
			scopeIntermediate.get(i).accept(this);
		}
	}

	@Override
	public void visit(final SimpleMethodNodeExpr mes) {
		e = new Value(mes.getValue());		
	}

	@Override
	public void visit(final NestedMethodNodeExpr men) {
		ExpressionBuildVisitor nestedVisitor = new ExpressionBuildVisitor();
		men.getParseTree().accept(nestedVisitor);
		e = nestedVisitor.getExpression();
	}

	@Override
	public void visit(final SimpleMethodNodePlus mps) {
		e = new Operation(e, '+', new Value(mps.getValue()));
	}

	@Override
	public void visit(final NestedMethodNodePlus mpn) {
		ExpressionBuildVisitor nestedVisitor = new ExpressionBuildVisitor();
		mpn.getParseTree().accept(nestedVisitor);
		e = new Operation(e, '+', nestedVisitor.getExpression());
	}

	@Override
	public void visit(final SimpleMethodNodeMinus mms) {
		e = new Operation(e, '-', new Value(mms.getValue()));
	}

	@Override
	public void visit(final NestedMethodNodeMinus mmn) {
		ExpressionBuildVisitor nestedVisitor = new ExpressionBuildVisitor();
		mmn.getParseTree().accept(nestedVisitor);
		e = new Operation(e, '-', nestedVisitor.getExpression());
	}

	@Override
	public void visit(final SimpleMethodNodeTimes mts) {
		e = new Operation(e, '*', new Value(mts.getValue()));
	}

	@Override
	public void visit(final NestedMethodNodeTimes mtn) {
		ExpressionBuildVisitor nestedVisitor = new ExpressionBuildVisitor();
		mtn.getParseTree().accept(nestedVisitor);
		e = new Operation(e, '*', nestedVisitor.getExpression());
	}

	@Override
	public void visit(final SimpleMethodNodeDivided mds) {
		e = new Operation(e, '/', new Value(mds.getValue()));
	}

	@Override
	public void visit(final NestedMethodNodeDivided mdn) {
		ExpressionBuildVisitor nestedVisitor = new ExpressionBuildVisitor();
		mdn.getParseTree().accept(nestedVisitor);
		e = new Operation(e, '*', nestedVisitor.getExpression());
	}

}
