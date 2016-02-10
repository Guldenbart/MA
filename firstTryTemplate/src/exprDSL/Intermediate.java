package exprDSL;

import parseTree.ParseTree;

/**
 * Interface with the operations you can use in an expression.
 *
 * @author Daniel Fritz
 */
public interface Intermediate extends End {
	
	/**
	 * add a simple value to your expression.
	 * @param value	the desired value
	 * @return	scope object of the same scope
	 */
	Intermediate plus(double value);
	
	/**
	 * add a complete expression to another expression.
	 * @param tree	the desired expression
	 * @return	scope object of the same scope
	 */
	Intermediate plus(ParseTree tree);
	
	
	/**
	 * subtract a simple value from your expression.
	 * @param value	the desired value
	 * @return	scope object of the same scope
	 */
	Intermediate minus(double value);
	
	/**
	 * subtract a complete expression from another expression.
	 * @param tree	the desired expression
	 * @return	scope object of the same scope
	 */
	Intermediate minus(ParseTree tree);
	
	/**
	 * multiply your expression with a simple value.
	 * @param value	the desired value
	 * @return	scope object of the same scope
	 */
	Intermediate times(double value);
	
	/**
	 * multiply an expression with another complete expression.
	 * @param tree	the desired expression
	 * @return	scope object of the same scope
	 */
	Intermediate times(ParseTree tree);
	
	/**
	 * divide your expression by a simple value.
	 * @param value	the desired value
	 * @return	scope object of the same scope
	 */
	Intermediate divided(double value);
	
	/**
	 * divide an expression by another complete expression.
	 * @param tree	the desired expression
	 * @return	scope object of the same scope
	 */
	Intermediate divided(ParseTree tree);
}
