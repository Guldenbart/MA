package exprDSL;

import parseTree.ParseTree;

/**
 * Interface with the methods an arithmetic expression can start with.
 *
 * @author Daniel Fritz
 */
public interface Start {
	
	/**
	 * starts the expression with a single value.
	 * 
	 * @param	value	the desired value
	 * @return	scope object {@link Intermediate} which allows you to add
	 * 			operations or end the expression. 
	 */
	Intermediate expr(double value);
	
	/**
	 * starts the expression with another complete expression.
	 * This emulates a parenthesized expression.
	 * 
	 * @param	tree	the desired expression
	 * @return	scope object {@link Intermediate} which allows you to add
	 * 			operations or end the expression. 
	 */
	Intermediate expr(ParseTree tree);
}
