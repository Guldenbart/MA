package parseTree;

import visitor.AVisitor;

/**
 * @author Daniel Fritz
 * 
 * Visitable is the interface Interface for all classes
 * which are supposed to be handled by a {@link AVisitor}.
 * All the class that implements this interface has to do is accept the
 * visitor, which does all the work. 
 *
 */
public interface Visitable {
	
	/**
	 * Allows a visitor to perform actions on this class.
	 * 
	 * @param visitor the visitor that is accepted.
	 */
	void accept(AVisitor visitor);
}
