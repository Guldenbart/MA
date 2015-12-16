package parseTree;

import visitor.Visitable;

public interface IMethod extends Visitable{	
	public String toString(boolean isLastScope);

	public String name();
}
