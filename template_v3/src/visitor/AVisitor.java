package visitor;

import parseTree.ParseTree;
import parseTree.AScopeNode;

/**
 * abstract super class for all visitors.
 * 
 * You can use a visitor to give your language semantics.
 * Every visitor you create for your language has to extend the abstract
 * visitor that was generated according to your grammar 
 * (name pattern: A[languageName]Visitor).
 * 
 * Every ParseTree consists of one or multiple @link{ScopeNode} objects and
 * each ScopeNode consists of one or multiple {@link AMethodNode} objects.
 * Every ScopeNode and MethodNode object accepts your "visit" and you can
 * access their properties inside the respective visit method.
 * 
 * Not every visit method of one visitor has to implemented, as you might not
 * be interested in every method everytime.
 * 
 * @author Daniel Fritz
 *
 */
public abstract class AVisitor {
	
	/**
	 * visits every element (of type ScopeNode) in this parse tree.
	 * @param parseTree ParseTree that you want to visit
	 * @see AScopeNode
	 */
	public final void visit(final ParseTree parseTree) {
		for (AScopeNode s : parseTree) {
			s.accept(this);
		}
	}

}
