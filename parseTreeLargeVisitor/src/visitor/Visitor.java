package visitor;

import parseTree.MethodDividedNested;
import parseTree.MethodDividedSimple;
import parseTree.MethodEnd;
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
	
	
	default void visit(MethodExprSimple mes) {}
	default void visit(MethodExprNested men) {}
	
	default void visit(MethodPlusSimple mps) {}
	default void visit(MethodPlusNested mpn) {}
	
	default void visit(MethodMinusSimple mms) {}
	default void visit(MethodMinusNested mmn) {}
	
	default void visit(MethodTimesSimple mts) {}
	default void visit(MethodTimesNested mtn) {}
	
	default void visit(MethodDividedSimple mds) {}
	default void visit(MethodDividedNested mdn) {}
	
	default void visit(MethodEnd me) {}
	
}
