package exprDSL;

import parseTree.ParseTree;

/**
 * Interface with the methods an arithmetic expression can start with. 
 * @author Daniel Fritz
 *
 * @param <T> type parameter
 */
public interface Start {
	
	/**
	 * 
	 * @param value
	 * @return
	 */
	Intermediate expr(double value);
	Intermediate expr(ParseTree tree);
}
