package expressions;

public interface Intermediate<T> extends End{
	Intermediate<T> plus(double value);
	Intermediate<T> plus(Expr s);
	
	Intermediate<T> minus(double value);
	Intermediate<T> minus(Expr s);
	
	Intermediate<T> times(double value);
	Intermediate<T> times(Expr s);
	
	Intermediate<T> divided(double value);
	Intermediate<T> divided(Expr s);
}
