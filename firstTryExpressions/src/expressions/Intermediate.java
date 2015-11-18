package expressions;

public interface Intermediate<T> extends End<T>{
	Intermediate<T> plus(double value);
	Intermediate<T> plus(T s);
	
	Intermediate<T> minus(double value);
	Intermediate<T> minus(T s);
	
	Intermediate<T> times(double value);
	Intermediate<T> times(T s);
	
	Intermediate<T> divided(double value);
	Intermediate<T> divided(T s);
}
