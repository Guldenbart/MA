/*
 * Für die Method*Simple-Klassen böte sich eine gemeinsame Oberklasse SimpleMethod an,
 * die name und value samt getter aufnimmt, für die Method*Nested-Klassen entsprechend
 * eine Oberklasse NestedMethod, die name und parseTree samt getter aufnimmt.
 * Man hätte dann deutliche weniger Coderedundanz.
 */

package parseTree;

public abstract class MethodSimple implements Visitable {
	
	protected double _value;
	protected String name; // TODO kann/sollte der Name immer noch final sein?
	
	public double value() {
		return _value;
	}
	
	public String toString() {
		return name + '(' + _value + ").";
	}	

}
