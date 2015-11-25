package visitor;

import parseTree.NestedMethod;
import parseTree.Scope;
import parseTree.SimpleMethod;

/**
 * Interface for all classes that want to implement a visitor.
 * <p>
 * This interface has one method for every class type that it wants to process.
 * 
 * @author Daniel Fritz
 */
public interface Visitor {
	
	/**
	 * Processes objects of the type {@link Scope}
	 * 
	 * @param scope Scope that gets visited
	 */
	public void visit(Scope scope);
	
	
	/**
	 * Processes objects of the type {@link SimpleMethod}
	 * 
	 * @param sm SimpleMethod that gets visited
	 */
	public void visit(SimpleMethod sm);
	
	
	/**
	 * Processes objects of the type {@link NestedMethod}
	 *
	 * @param nm NestedMethod that gets visited
	 */
	public void visit(NestedMethod nm);
}
