package parseTree;

import visitor.Visitable;

public interface IMethod extends Visitable{	
	public String toString(boolean isLastScope);
	
	// TODO getter (und setter) im Interface?
	public String name();
}
