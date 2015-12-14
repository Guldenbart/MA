package expressions;

/**
 * Operations are used to build arithmetic expressions (other than just simple
 * values).
 * <p>
 * Operations consist of two {@link Value}s and one operator, for example:
 * <ul>
 * <li>2*4 => <code>expr(2).times(4)</code>
 * <li>15+4-7 => <code>expr(15).plus(4).minus(7)</code>
 * <li>
 * </ul>
 * Only the four basic arithmetic operations (+, -, *, /) are implemented by
 * this class 
 * 
 * 
 * @author Daniel Fritz
 *
 */
public class Operation implements Expr {

	public final Expr left;
    public final char op;
    public final Expr right;

    
    public Operation(Expr left, char op, Expr right) {
        this.left = left;
        this.op = op;
        this.right = right;
    }

    @Override
    public double getValue() {
        switch (this.op) {
        case '+': return ((Expr) this.left).getValue() + ((Expr) this.right).getValue();
        case '-': return ((Expr) this.left).getValue() - ((Expr) this.right).getValue();
        case '*': return ((Expr) this.left).getValue() * ((Expr) this.right).getValue();
        case '/': return ((Expr) this.left).getValue() / ((Expr) this.right).getValue();
        }

        return Double.NaN;
    }

}
