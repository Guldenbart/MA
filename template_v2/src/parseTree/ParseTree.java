package parseTree;

import java.util.Iterator;
import java.util.List;

import visitor.AVisitor;

/**
 * This class is used to store all information about the structure of a
 * dsl expression.
 * 
 * TODO more information
 * 
 * @author Daniel Fritz
 */
public final class ParseTree implements Visitable, Iterable<AScopeNode> {

	/**
	 * list of all {@link AScopeNode} objects of this tree.
	 */
	private List<AScopeNode> scopes;
	
	/**
	 * constructor that initializes 'list' with a given list of ScopeNode
	 * objects.
	 * @param scopeNodeList value that list is set to.
	 */
	public ParseTree(final List<AScopeNode> scopeNodeList) {
		this.scopes = scopeNodeList;
	}
	
	@Override
	public String toString() {		
		StringBuilder sb = new StringBuilder();
		
		sb.append("begin().");
		for (AScopeNode sn : scopes) {
			sb.append(sn.toString());
		}
		// delete the last dot (easier than checking when last scope happens)
		sb.deleteCharAt(sb.length() - 1);
		
		return sb.toString();
	}
	
	@Override
	public void accept(final AVisitor visitor) {
		visitor.visit(this);
	}

	@Override
	public Iterator<AScopeNode> iterator() {
		return this.scopes.iterator();
	}
	
}
