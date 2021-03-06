package expressions;

/**
 * Interface for arithmetic expressions.
 * <p>
 * An expression can either be a simple {@link Value} or an expression that
 * consists of one or multiple {@link Operation}s.
 * 
 * @author Daniel Fritz
 *
 */
public interface Expr {
	
	/**
	 * Returns the value of the expression.
	 * 
	 * @return value of the expression.
	 */
	double getValue();
	
}
