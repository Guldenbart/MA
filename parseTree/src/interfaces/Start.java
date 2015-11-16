package interfaces;

import java.util.ArrayList;

import classes.Scope;

public interface Start<T> {
	Intermediate<T> expr(double value);
	Intermediate<T> expr(Tree tree);
}
