package visitor;

import parseTree.NestedMethodDivided;
import parseTree.SimpleMethodDivided;
import parseTree.MethodEnd;
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

/**
 * Interface for all classes that want to implement a visitor.
 * <p>
 * This interface has one method for every class type that it wants to process.
 * 
 * @author Daniel Fritz
 */
public interface Visitor {
	
	default void visit(ParseTree parseTree) {}
	
	default void visit(ScopeStart scopeStart) {}
	default void visit(ScopeIntermediate scopeIntermediate) {}
	
	
	default void visit(SimpleMethodExpr mes) {}
	default void visit(NestedMethodExpr men) {}
	
	default void visit(SimpleMethodPlus mps) {}
	default void visit(NestedMethodPlus mpn) {}
	
	default void visit(SimpleMethodMinus mms) {}
	default void visit(NestedMethodMinus mmn) {}
	
	default void visit(SimpleMethodTimes mts) {}
	default void visit(NestedMethodTimes mtn) {}
	
	default void visit(SimpleMethodDivided mds) {}
	default void visit(NestedMethodDivided mdn) {}
	
	default void visit(MethodEnd me) {}
	
}
