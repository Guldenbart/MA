package parseTree;

import java.util.Iterator;
import java.util.List;

/**
 * every 'ScopeNode' class holds a list of all methods of the same scope that
 * were called consecutively.
 * 
 * @author Daniel Fritz
 */
public abstract class ScopeNode implements Visitable, Iterable<AMethodNode> {
	
	/**
	 * name of the interface in the grammar definition all methods belong to.
	 */
	private final String interfaceName;
	
	/**
	 * list of all methods (that is, the method classes (see {@link AMethodNode}))
	 * that were called consecutively.
	 */
	private final List<AMethodNode> methods;
	
	/**
	 * constructor that initializes <code>interfaceName</code> and
	 * <code>methodList</code> with given arguments.
	 * 
	 * @param iName name of the interface
	 * @param methodList list of all AMethod objects that belong to this lope.
	 */
	public ScopeNode(final String iName, final List<AMethodNode> methodList) {
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

	@Override
	public final String toString() {
		StringBuilder sb = new StringBuilder();
		
		sb.append("<" + this.interfaceName + ">");
		for (AMethodNode m : methods) {
			sb.append(m.toString());
		}
		
		return sb.toString();
	}
	
	@Override
	public Iterator<AMethodNode> iterator() {
		return this.methods.iterator();
	}

}
