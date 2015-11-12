package interfaces;

public interface Intermediate<T> extends End{
	Intermediate<T> plus(double value);
	Intermediate<T> plus(Tree tree);
	
	Intermediate<T> minus(double value);
	Intermediate<T> minus(Tree tree);
	
	Intermediate<T> times(double value);
	Intermediate<T> times(Tree tree);
	
	Intermediate<T> divided(double value);
	Intermediate<T> divided(Tree tree);
}
