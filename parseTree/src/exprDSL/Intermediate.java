package exprDSL;

public interface Intermediate<T> extends End<T>{
	Intermediate<T> plus(double value);
	Intermediate<T> plus(T tree);
	
	Intermediate<T> minus(double value);
	Intermediate<T> minus(T tree);
	
	Intermediate<T> times(double value);
	Intermediate<T> times(T tree);
	
	Intermediate<T> divided(double value);
	Intermediate<T> divided(T tree);
}
