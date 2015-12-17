package parseTree;

import java.util.ArrayList;

public abstract class Scope implements Visitable{
	
	protected String interfaceName;
	protected ArrayList<Visitable> methods;
	// TODO Frage: Wäre es sinnvoll, dafür zu sorgen, dass hier nur Methoden reinkönnen, oder kann ich mich einfach darauf verlassen, dass es so ist?
	
	public int size() {
		return methods.size();
	}
	
	public Visitable get(int index) {
		return methods.get(index);
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		
		for (int i=0; i<methods.size(); i++) {
			sb.append(methods.get(i).toString());
		}
		
		return sb.toString();
	}
	
	public String getInterfaceName() {
		return interfaceName;
	}
	
	public void addVisitable(Visitable v) {
		methods.add(v);
	}

}
