package visitor;

import parseTree.NestedMethod;
import parseTree.ParseTree;
import parseTree.Scope;
import parseTree.SimpleMethod;
import expressions.Expr;
import expressions.Operation;
import expressions.Value;

public class ExpressionBuildVisitor implements Visitor {
	
	private Expr e;
	
	public Expr expression() {
		return e;
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
	public void visit(SimpleMethod sm) {
		switch(sm.name()) {
		case "expr":
			e = new Value( (double)sm.arguments()[0]);
			break;
		case "plus":
			e = new Operation(e, '+', new Value( (double)sm.arguments()[0]));
			break;
		case "minus":
			e = new Operation(e, '-', new Value( (double)sm.arguments()[0]));
			break;
		case "times":
			e = new Operation(e, '*', new Value( (double)sm.arguments()[0]));
			break;
		case "divided":
			e = new Operation(e, '/', new Value( (double)sm.arguments()[0]));
			break;
		case "end":
			// TODO was soll bei 'end' getan werden?
		}
	}

	@Override
	public void visit(NestedMethod nm) {
		ExpressionBuildVisitor nestedVisitor = new ExpressionBuildVisitor();
		nm.parseTree().accept(nestedVisitor);
		
		switch(nm.name()) {
		case "expr":
			e = nestedVisitor.expression();
			break;
		case "plus":
			e = new Operation(e, '+', nestedVisitor.expression());
			break;
		case "minus":
			e = new Operation(e, '-', nestedVisitor.expression());
			break;
		case "times":
			e = new Operation(e, '*', nestedVisitor.expression());
			break;
		case "divided":
			e = new Operation(e, '/', nestedVisitor.expression());
			break;
		case "end":
			// TODO was soll bei 'end' getan werden?
		}
	}

}
