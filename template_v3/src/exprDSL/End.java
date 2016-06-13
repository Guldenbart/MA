package exprDSL;

import parseTree.ParseTree;

/**
 * Interface with all methods that end an expression.
 * 
 * @author Daniel Fritz
 */
public interface End {
	
	/**
	 * marks the end of an expression.
	 * @return the {@link ParseTree} that results from this expression.
	 */
	ParseTree end();
}
