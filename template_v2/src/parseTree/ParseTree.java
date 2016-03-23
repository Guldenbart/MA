package parseTree;

import java.util.ArrayList;

import visitor.AVisitor;

/**
 * This class is used to store all information about the structure of an
 * expression.
 * 
 * TODO more information
 * 
 * @author Daniel Fritz
 */
public final class ParseTree implements Visitable {

	/**
	 * list of all {@link ScopeNode} objects of this tree.
	 */
	private ArrayList<ScopeNode> list;
	
	/**
	 * constructor that initializes 'list' with a given list of Lope objects.
	 * @param lopeList value that list is set to.
	 */
	public ParseTree(final ArrayList<ScopeNode> lopeList) {
		this.list = lopeList;
	}
	
	/**
	 * returns the size of 'list'.
	 * @return size of list
	 */
	public int size() {
		return list.size();
	}
	
	/**
	 * gets the item of 'methods' at position [index].
	 * @param index position in methodList from which you want to get the item 
	 * @return item (of type Lope) at position [index]
	 */
	public Visitable get(final int index) {
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
