package exprDSL;

import parseTree.ParseTree;

/**
 * Interface with the methods an arithmetic expression can start with. 
 * @author Daniel Fritz
 * @param <T>
 *
 * @param <T> type parameter
 */
public interface Start<T> {
	
	/**
	 * 
	 * @param value
	 * @return
	 */
	Intermediate<T> expr(T value);
	Intermediate<T> expr(ParseTree tree);
}
