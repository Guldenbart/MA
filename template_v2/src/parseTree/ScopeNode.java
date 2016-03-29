package parseTree;

import java.util.ArrayList;

/**
 * every 'ScopeNode' class holds a list of all methods of the same scope that
 * were called consecutively.
 * 
 * @author Daniel Fritz
 */
public abstract class ScopeNode implements Visitable {
	
	/**
	 * name of the interface in the grammar definition all methods belong to.
	 */
	private final String interfaceName;
	
	/**
	 * list of all methods (that is, the method classes (see {@link AMethodNode}))
	 * that were called consecutively.
	 */
	private final ArrayList<AMethodNode> methods;
	
	/**
	 * constructor that initializes <code>interfaceName</code> and
	 * <code>methodList</code> with given arguments.
	 * 
	 * @param iName name of the interface
	 * @param methodList list of all AMethod objects that belong to this lope.
	 */
	public ScopeNode(final String iName, final ArrayList<AMethodNode> methodList) {
		this.interfaceName = iName;
		this.methods = methodList;
	}
	
	/**
	 * gets the interface name.
	 * @return name of the interface
	 */
	public final String getInterfaceName() {
		return this.interfaceName;
	}
	
	/**
	 * gets the list of all the AMethod objects that belong to this Lope.
	 * @return list of methods
	 */
	public final ArrayList<AMethodNode> methods() {
		return this.methods;
	}
	
	/**
	 * gets the size of the method list.
	 * @return method list size
	 */
	public final int size() {
		return methods.size();
	}
	
	/**
	 * gets the item of 'methods' at position [index].
	 * @param index position in methodList from which you want to get the item 
	 * @return item (of type AMethod) at position [index]
	 */
	public final Visitable get(final int index) {
		return methods.get(index);
	}

	@Override
	public final String toString() {
		StringBuilder sb = new StringBuilder();
		
		for (AMethodNode m : methods) {
			sb.append(m.toString());
		}
		
		return sb.toString();
	}

}
