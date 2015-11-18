package parseTree;

import interfaces.IMethod;

public class SimpleMethod implements IMethod{
	
	private String name;
	private Object[] arguments;
	
	public SimpleMethod(String mName, Object... mArguments) {
		name = mName;
		arguments = mArguments;
	}

	@Override
	public String toString(boolean isLastScope) {
		return (name + '(' + getArgumentString() + ')');
	}
	
	private String getArgumentString() {
		StringBuilder sb = new StringBuilder();
		
		for (int i=0; i<arguments.length; i++) {
			if (i==0 || i==(arguments.length-1)) {
				sb.append(arguments[i].toString());
			} else {
				sb.append(", ");
				sb.append(arguments[i].toString());
			}
		}
		
		return sb.toString();
	}

}
