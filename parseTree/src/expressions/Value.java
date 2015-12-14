package expressions;

/**
 * The class represents a single double value.
 * <p>
 * A Value is part of an expression ({@see Expr}).
 * 
 * @author Daniel Fritz
 *
 */
public class Value implements Expr {

	/**
	 * the value of the expression
	 */
	private double value;

	/**
	 * Constuctor that initializes <code>value</code>
	 * @param value what value gets initialized with
	 */
    public Value(double value) {
        this.value = value;
    }

    
    /**
     * {@link Value#value}
     */
    @Override
    public double getValue() {
        return this.value;
    }

}
