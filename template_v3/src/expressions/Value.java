package expressions;

/**
 * The class represents a single double value.
 * <p>
 * A Value is part of an expression ({@see Expr}).
 * 
 * @author Daniel Fritz
 *
 */
public final class Value implements Expr {

	/**
	 * the value of the expression.
	 */
	private double value;

	/**
	 * Constructor that initializes <code>value</code>.
	 * @param val what value gets initialized with
	 */
    public Value(final double val) {
        this.value = val;
    }

    
    @Override
    public double getValue() {
        return this.value;
    }

}
