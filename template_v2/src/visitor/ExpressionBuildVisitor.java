package visitor;

import expressions.Expr;
import expressions.Operation;
import expressions.Value;
import parseTreeGen.LopeIntermediate;
import parseTreeGen.LopeStart;
import parseTreeGen.NestedMethodDivided;
import parseTreeGen.NestedMethodExpr;
import parseTreeGen.NestedMethodMinus;
import parseTreeGen.NestedMethodPlus;
import parseTreeGen.NestedMethodTimes;
import parseTreeGen.SimpleMethodDivided;
import parseTreeGen.SimpleMethodExpr;
import parseTreeGen.SimpleMethodMinus;
import parseTreeGen.SimpleMethodPlus;
import parseTreeGen.SimpleMethodTimes;
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
	public void visit(final LopeStart scopeStart) {
		for (int i = 0; i < scopeStart.size(); i++) {
			scopeStart.get(i).accept(this);
		}
	}
	
	@Override
	public void visit(final LopeIntermediate scopeIntermediate) {
		for (int i = 0; i < scopeIntermediate.size(); i++) {
			scopeIntermediate.get(i).accept(this);
		}
	}

	@Override
	public void visit(final SimpleMethodExpr mes) {
		e = new Value(mes.getValue());		
	}

	@Override
	public void visit(final NestedMethodExpr men) {
		ExpressionBuildVisitor nestedVisitor = new ExpressionBuildVisitor();
		men.getParseTree().accept(nestedVisitor);
		e = nestedVisitor.getExpression();
	}

	@Override
	public void visit(final SimpleMethodPlus mps) {
		e = new Operation(e, '+', new Value(mps.getValue()));
	}

	@Override
	public void visit(final NestedMethodPlus mpn) {
		ExpressionBuildVisitor nestedVisitor = new ExpressionBuildVisitor();
		mpn.getParseTree().accept(nestedVisitor);
		e = new Operation(e, '+', nestedVisitor.getExpression());
	}

	@Override
	public void visit(final SimpleMethodMinus mms) {
		e = new Operation(e, '-', new Value(mms.getValue()));
	}

	@Override
	public void visit(final NestedMethodMinus mmn) {
		ExpressionBuildVisitor nestedVisitor = new ExpressionBuildVisitor();
		mmn.getParseTree().accept(nestedVisitor);
		e = new Operation(e, '-', nestedVisitor.getExpression());
	}

	@Override
	public void visit(final SimpleMethodTimes mts) {
		e = new Operation(e, '*', new Value(mts.getValue()));
	}

	@Override
	public void visit(final NestedMethodTimes mtn) {
		ExpressionBuildVisitor nestedVisitor = new ExpressionBuildVisitor();
		mtn.getParseTree().accept(nestedVisitor);
		e = new Operation(e, '*', nestedVisitor.getExpression());
	}

	@Override
	public void visit(final SimpleMethodDivided mds) {
		e = new Operation(e, '/', new Value(mds.getValue()));
	}

	@Override
	public void visit(final NestedMethodDivided mdn) {
		ExpressionBuildVisitor nestedVisitor = new ExpressionBuildVisitor();
		mdn.getParseTree().accept(nestedVisitor);
		e = new Operation(e, '*', nestedVisitor.getExpression());
	}

}
