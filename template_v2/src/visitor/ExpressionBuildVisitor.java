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

public class ExpressionBuildVisitor extends AExprDSLVisitor {
	
	private Expr e;
	
	public Expr expression() {
		return e;
	}

	@Override
	public final void visit(final LopeStart scopeStart) {
		for (int i = 0; i < scopeStart.size(); i++) {
			scopeStart.get(i).accept(this);
		}
	}
	
	@Override
	public final void visit(final LopeIntermediate scopeIntermediate) {
		for (int i = 0; i < scopeIntermediate.size(); i++) {
			scopeIntermediate.get(i).accept(this);
		}
	}

	@Override
	public final void visit(final SimpleMethodExpr mes) {
		e = new Value(mes.value());		
	}

	@Override
	public final void visit(final NestedMethodExpr men) {
		ExpressionBuildVisitor nestedVisitor = new ExpressionBuildVisitor();
		men.parseTree().accept(nestedVisitor);
		e = nestedVisitor.expression();
	}

	@Override
	public final void visit(final SimpleMethodPlus mps) {
		e = new Operation(e, '+', new Value(mps.value()));
	}

	@Override
	public final void visit(final NestedMethodPlus mpn) {
		ExpressionBuildVisitor nestedVisitor = new ExpressionBuildVisitor();
		mpn.parseTree().accept(nestedVisitor);
		e = new Operation(e, '+', nestedVisitor.expression());
	}

	@Override
	public final void visit(final SimpleMethodMinus mms) {
		e = new Operation(e, '-', new Value(mms.value()));
	}

	@Override
	public final void visit(final NestedMethodMinus mmn) {
		ExpressionBuildVisitor nestedVisitor = new ExpressionBuildVisitor();
		mmn.parseTree().accept(nestedVisitor);
		e = new Operation(e, '-', nestedVisitor.expression());
	}

	@Override
	public final void visit(final SimpleMethodTimes mts) {
		e = new Operation(e, '*', new Value(mts.value()));
	}

	@Override
	public final void visit(final NestedMethodTimes mtn) {
		ExpressionBuildVisitor nestedVisitor = new ExpressionBuildVisitor();
		mtn.parseTree().accept(nestedVisitor);
		e = new Operation(e, '*', nestedVisitor.expression());
	}

	@Override
	public final void visit(final SimpleMethodDivided mds) {
		e = new Operation(e, '/', new Value(mds.value()));
	}

	@Override
	public final void visit(final NestedMethodDivided mdn) {
		ExpressionBuildVisitor nestedVisitor = new ExpressionBuildVisitor();
		mdn.parseTree().accept(nestedVisitor);
		e = new Operation(e, '*', nestedVisitor.expression());
	}

}
