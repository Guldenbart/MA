package classes;

public class Method {
	
	private String name;
	private Object[] arguments;
	
	public Method(String mName, Object... mArguments) {
		name = mName;
		arguments = mArguments;
	}

	public void print() {
		System.out.print("Method: " + name + getArgumentString() + " | ");
	}
	
	private String getArgumentString() {
		StringBuilder sb = new StringBuilder();
		
		for (Object o : arguments) {
			sb.append(o.toString());
		}
		
		return sb.toString();
	}

}
