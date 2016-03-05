package expressions;

/**
 * Operations are used to build arithmetic expressions (other than just simple
 * values).
 * <p>
 * Only the four basic arithmetic operations (+, -, *, /) are implemented by
 * this class 
 * 
 * @author Daniel Fritz
 * @see {@link Expr}
 */
public final class Operation implements Expr {

	/**
	 * expression on the left side of the operator.
	 */
	public final Expr left;
	
	/**
	 * char that defines the operator.
	 * 
	 * Only '+', '-', '*' and '/' work.
	 */
    public final char op;
    
    /**
     * expression on the right side of the operator.
     */
    public final Expr right;

    /**
     * constructor that initializes all fields.
     * @param leftExpr expression left of the operator
     * @param operator operator
     * @param rightExpr expression right of the operator
     */
    public Operation(final Expr leftExpr, final char operator,
    		final Expr rightExpr) {
        this.left = leftExpr;
        this.op = operator;
        this.right = rightExpr;
    }

    @Override
    public double getValue() {
        switch (this.op) {
        case '+': return ((Expr) this.left).getValue() + ((Expr) this.right).getValue();
        case '-': return ((Expr) this.left).getValue() - ((Expr) this.right).getValue();
        case '*': return ((Expr) this.left).getValue() * ((Expr) this.right).getValue();
        case '/': return ((Expr) this.left).getValue() / ((Expr) this.right).getValue();
        default: return Double.NaN;
        }
    }

}
