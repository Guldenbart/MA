package expressions;

public class ExpressionBuilder {
	
	private final IntermediateScope intermediateScope;
	private Expr e;
	
	private ExpressionBuilder() {
		this.intermediateScope = this.new IntermediateScope();
	}
	
	public static ExpressionScope begin() {
		return new ExpressionBuilder().new ExpressionScope();
	}	

	public class ExpressionScope implements Start<Expr> {
		@Override
		public IntermediateScope expr(final double value) {
			ExpressionBuilder.this.e = new Value(value);
			return ExpressionBuilder.this.intermediateScope;
		}
	
		@Override
		public IntermediateScope expr(Expr e) {
			ExpressionBuilder.this.e = e;
			return ExpressionBuilder.this.intermediateScope;
		}
	}

	
	public class IntermediateScope implements Intermediate<Expr> {

		@Override
		public Intermediate<Expr> plus(double value) {
			ExpressionBuilder.this.e = new Operation(ExpressionBuilder.this.e, '+', new Value(value));
			return this;
		}

		@Override
		public Intermediate<Expr> plus(Expr right) {
			ExpressionBuilder.this.e = new Operation(ExpressionBuilder.this.e, '+', right);
            return this;
		}

		@Override
		public Intermediate<Expr> minus(double value) {
			ExpressionBuilder.this.e = new Operation(ExpressionBuilder.this.e, '-', new Value(value));
			return this;
		}

		@Override
		public Intermediate<Expr> minus(Expr right) {
			ExpressionBuilder.this.e = new Operation(ExpressionBuilder.this.e, '-', right);
            return this;
		}

		@Override
		public Intermediate<Expr> times(double value) {
			ExpressionBuilder.this.e = new Operation(ExpressionBuilder.this.e, '*', new Value(value));
			return this;
		}

		@Override
		public Intermediate<Expr> times(Expr right) {
			ExpressionBuilder.this.e = new Operation(ExpressionBuilder.this.e, '*', right);
            return this;
		}

		@Override
		public Intermediate<Expr> divided(double value) {
			ExpressionBuilder.this.e = new Operation(ExpressionBuilder.this.e, '/', new Value(value));
			return this;
		}

		@Override
		public Intermediate<Expr> divided(Expr right) {
			ExpressionBuilder.this.e = new Operation(ExpressionBuilder.this.e, '/', right);
            return this;
		}
		
		@Override
		public Expr end() {
			return ExpressionBuilder.this.e;
		}
		
	}
}