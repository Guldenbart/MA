package parseTree;

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
public final class ParseTree implements Visitable {

	/**
	 * list of all {@link ScopeNode} objects of this tree.
	 */
	private List<ScopeNode> list;
	
	/**
	 * constructor that initializes 'list' with a given list of Lope objects.
	 * @param scopeNodeList value that list is set to.
	 */
	public ParseTree(final List<ScopeNode> scopeNodeList) {
		this.list = scopeNodeList;
	}
	
	/**
	 * returns the size of 'list'.
	 * @return size of list
	 */
	public int size() {
		return list.size();
	}
	//TODO Frage: Bei Methoden wie dieser Fehlerbehandlung nötig (z.B. list==null)?
	
	/**
	 * gets the item of 'methods' at position [index].
	 * @param index position in methodList from which you want to get the item 
	 * @return item (of type ScopeNode) at position [index]
	 */
	public ScopeNode get(final int index) {
		return list.get(index);
	}
	
	@Override
	public String toString() {		
		StringBuilder sb = new StringBuilder();
		
		sb.append("TreeBuilder.begin().");
		for (ScopeNode l : list) {
			sb.append(l.toString());
		}
		
		return sb.toString();
	}
	
	@Override
	public void accept(final AVisitor visitor) {
		visitor.visit(this);
	}
	
	// TODO Kann die Eigenschaft "generics" durch Reflection ausgelesen werden?
}
