package parseTree;

import java.util.ArrayList;

public abstract class Lope implements Visitable {
	
	private final String interfaceName;
	private final ArrayList<AMethod> methods;
	
	public Lope(final String iName, final ArrayList<AMethod> methodList) {
		this.interfaceName = iName;
		this.methods = methodList;
	}
	
	public final String interfaceName() {
		return this.interfaceName;
	}
	
	public final ArrayList<AMethod> methods() {
		return this.methods;
	}
	
	public final int size() {
		return methods.size();
	}
	
	public final Visitable get(final int index) {
		return methods.get(index);
	}

	@Override
	public final String toString() {
		StringBuilder sb = new StringBuilder();
		
		for (int i=0; i<methods.size(); i++) {
			sb.append(methods.get(i).toString());
		}
		
		return sb.toString();
	}

}
