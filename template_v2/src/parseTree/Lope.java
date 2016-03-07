package parseTree;

import java.util.ArrayList;

/**
 * every 'Lope' class holds a list of all methods of the same scope that were
 * called consecutively.
 * 
 * @author Daniel Fritz
 */
public abstract class Lope implements Visitable {
	
	/**
	 * name of the interface of the grammar definition all methods belong to.
	 */
	private final String interfaceName;
	
	/**
	 * list of all methods (that is, the method classes (see {@link AMethod}))
	 * that were called consecutively.
	 */
	private final ArrayList<AMethod> methods;
	
	/**
	 * constructor that initializes <code>interfaceName</code> and
	 * <code>methodList</code> with given arguments.
	 * 
	 * @param iName name of the interface
	 * @param methodList list of all AMethod objects that belong to this lope.
	 */
	public Lope(final String iName, final ArrayList<AMethod> methodList) {
		this.interfaceName = iName;
		this.methods = methodList;
	}
	
	/**
	 * gets the interface name.
	 * @return name of the interface
	 */
	public final String interfaceName() {
		return this.interfaceName;
	}
	
	/**
	 * gets the list of all the AMethod objects that belong to this Lope.
	 * @return list of methods
	 */
	public final ArrayList<AMethod> methods() {
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
		
		for (AMethod m : methods) {
			sb.append(m.toString());
		}
		
		return sb.toString();
	}

}
