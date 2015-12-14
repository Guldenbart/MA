package exprDSL;

/**
 * Interface with the methods an arithmetic expression can start with. 
 * @author Daniel Fritz
 *
 * @param <T> type parameter
 */
public interface Start<T> {
	
	/**
	 * 
	 * @param value
	 * @return
	 */
	Intermediate<T> expr(double value);
	Intermediate<T> expr(T tree);
}
