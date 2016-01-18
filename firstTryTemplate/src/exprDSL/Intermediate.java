package exprDSL;

import parseTree.ParseTree;

public interface Intermediate extends End{
	Intermediate plus(double value);
	Intermediate plus(ParseTree tree);
	
	Intermediate minus(double value);
	Intermediate minus(ParseTree tree);
	
	Intermediate times(double value);
	Intermediate times(ParseTree tree);
	
	Intermediate divided(double value);
	Intermediate divided(ParseTree tree);
}
