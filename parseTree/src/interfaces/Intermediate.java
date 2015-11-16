package interfaces;

import java.util.ArrayList;

import classes.Scope;

public interface Intermediate<T> extends End{
	Intermediate<T> plus(double value);
	Intermediate<T> plus(ArrayList<Scope> tree);
	
	Intermediate<T> minus(double value);
	Intermediate<T> minus(ArrayList<Scope> tree);
	
	Intermediate<T> times(double value);
	Intermediate<T> times(ArrayList<Scope> tree);
	
	Intermediate<T> divided(double value);
	Intermediate<T> divided(ArrayList<Scope> tree);
}
