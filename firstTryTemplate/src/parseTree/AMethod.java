package parseTree;

public abstract class AMethod implements Visitable {
	private String name;
	
	public AMethod(String name) {
		this.name = name;
	}
	
	public String name() {
		return name;
	}
}
