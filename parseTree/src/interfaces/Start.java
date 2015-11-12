package interfaces;

public interface Start<T> {
	Intermediate<T> expr(double value);
	Intermediate<T> expr(Tree tree);
}
