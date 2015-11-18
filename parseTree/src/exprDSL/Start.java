package exprDSL;

public interface Start<T> {
	Intermediate<T> expr(double value);
	Intermediate<T> expr(T tree);
}
