package exprDSL;

import parseTree.ParseTree;

public interface Intermediate<T> extends End{
	Intermediate<T> plus(T value);
	Intermediate<T> plus(ParseTree tree);
	
	Intermediate<T> minus(T value);
	Intermediate<T> minus(ParseTree tree);
	
	Intermediate<T> times(T value);
	Intermediate<T> times(ParseTree tree);
	
	Intermediate<T> divided(T value);
	Intermediate<T> divided(ParseTree tree);
}
