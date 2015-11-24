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
	 * Processes objects of the class {@link Scope}
	 * 
	 * @param scope Scope that gets visited
	 */
	public void visit(Scope scope);
	
	/**
	 * Processes objects of the class 
	 * 
	 * @param sm
	 */
	public void visit(SimpleMethod sm);
	public void visit(NestedMethod nm);
}
