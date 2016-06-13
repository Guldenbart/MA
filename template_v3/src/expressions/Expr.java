package expressions;

/**
 * Interface for arithmetic expressions.
 * <p>
 * An expression can either be a simple {@link Value} or an {@link Operation}. 
 * @author Daniel Fritz
 *
 */
public interface Expr {
	
	/**
	 * Gets the value of the expression.
	 * @return value of the expression.
	 */
	double getValue();
	
}
