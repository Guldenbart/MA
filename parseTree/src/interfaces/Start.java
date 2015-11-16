package interfaces;

import classes.ScopeList;

public interface Start<T> {
	Intermediate<T> expr(double value);
	Intermediate<T> expr(ScopeList tree);
}
