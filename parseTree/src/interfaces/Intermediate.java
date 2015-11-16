package interfaces;

import classes.ScopeList;

public interface Intermediate<T> extends End{
	Intermediate<T> plus(double value);
	Intermediate<T> plus(ScopeList tree);
	
	Intermediate<T> minus(double value);
	Intermediate<T> minus(ScopeList tree);
	
	Intermediate<T> times(double value);
	Intermediate<T> times(ScopeList tree);
	
	Intermediate<T> divided(double value);
	Intermediate<T> divided(ScopeList tree);
}
