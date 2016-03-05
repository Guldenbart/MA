package visitor;

import parseTree.ParseTree;

/**
 * abstract super class for all visitors.
 * 
 * You can use a visitor to give your language semantics.
 * Every visitor you create for your language has to extend the abstract
 * visitor that was generated according to your grammar 
 * (name pattern: A[languageName]Visitor).
 * 
 * Every ParseTree consists of one or multiple @link{Lope} objects and each
 * Lope consists of one or multiple {@link AMethod} objects.
 * Every lope and method object accepts your "visit" and you can access their
 * properties inside the respective visit method.
 * 
 * Not every visit-method of one visitor has to implemented. 
 * @author Daniel Fritz
 *
 */
public abstract class AVisitor {
	
	/**
	 * visits every element (of type Lope) in this parse tree.
	 * @param parseTree ParseTree that you want to visit
	 */
	public final void visit(final ParseTree parseTree) {
		for (int i = 0; i < parseTree.size(); i++) {
			parseTree.get(i).accept(this);
		}
	}

}
