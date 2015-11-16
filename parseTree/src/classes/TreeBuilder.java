package classes;

import java.util.ArrayList;

import interfaces.Intermediate;
import interfaces.Start;


public class TreeBuilder {

	private ArrayList<Scope> scopeList;
	private final IntermediateScope intermediateScope;
	
	private TreeBuilder() {
		this.intermediateScope = this.new IntermediateScope();
	}
	
	public static StartScope begin() {
		return new TreeBuilder().new StartScope();
	}

	private void checkLatestScope(String interfaceName) {
		if (scopeList.get(scopeList.size()-1).getInterfaceName().compareTo(interfaceName) != 0) {
			Scope scope = new Scope(interfaceName);
			scopeList.add(scope);
		}
		//scopeList.get(scopeList.size()-1).addMethod(methodName, arguments);
	}
	
	public class StartScope implements Start< ArrayList<Scope> > {

		@Override
		public IntermediateScope expr(double value) {
			String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
			
			// TODO schauen, ob man hier das richtige Interface bekommt:
			checkLatestScope(this.getClass().getInterfaces()[0].toString());
			scopeList.get(scopeList.size()-1).addMethod(methodName, value);
			
			return TreeBuilder.this.intermediateScope;
		}

		@Override
		public IntermediateScope expr(ArrayList<Scope> list) {
			String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
			
			// TODO schauen, ob man hier das richtige Interface bekommt:
			checkLatestScope(this.getClass().getInterfaces()[0].toString());
			scopeList.get(scopeList.size()-1).addMethod(methodName, list);
			
			return TreeBuilder.this.intermediateScope;
		}
		
	}
	
	public class IntermediateScope implements Intermediate < ArrayList<Scope> > {

		@Override
		public Intermediate< ArrayList<Scope> > plus(double value) {
			String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
			
			checkLatestScope(this.getClass().getInterfaces()[0].toString());
			scopeList.get(scopeList.size()-1).addMethod(methodName, value);
			
			return this;
		}

		@Override
		public Intermediate< ArrayList<Scope> > plus(ArrayList<Scope> list) {
			String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
			
			checkLatestScope(this.getClass().getInterfaces()[0].toString());
			scopeList.get(scopeList.size()-1).addMethod(methodName, list);
			
			return this;
		}

		@Override
		public Intermediate< ArrayList<Scope> > minus(double value) {
			String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
			
			checkLatestScope(this.getClass().getInterfaces()[0].toString());
			scopeList.get(scopeList.size()-1).addMethod(methodName, value);
			
			return this;
		}

		@Override
		public Intermediate< ArrayList<Scope> > minus(ArrayList<Scope> list) {
			String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
			
			checkLatestScope(this.getClass().getInterfaces()[0].toString());
			scopeList.get(scopeList.size()-1).addMethod(methodName, list);
			
			return this;
		}

		@Override
		public Intermediate< ArrayList<Scope> > times(double value) {
			String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
			
			checkLatestScope(this.getClass().getInterfaces()[0].toString());
			scopeList.get(scopeList.size()-1).addMethod(methodName, value);
			
			return this;
		}

		@Override
		public Intermediate< ArrayList<Scope> > times(ArrayList<Scope> list) {
			String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
			
			checkLatestScope(this.getClass().getInterfaces()[0].toString());
			scopeList.get(scopeList.size()-1).addMethod(methodName, list);
			
			return this;
		}

		@Override
		public Intermediate< ArrayList<Scope> > divided(double value) {
			String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
			
			checkLatestScope(this.getClass().getInterfaces()[0].toString());
			scopeList.get(scopeList.size()-1).addMethod(methodName, value);
			
			return this;
		}

		@Override
		public Intermediate< ArrayList<Scope> > divided(ArrayList<Scope> list) {
			String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
			
			checkLatestScope(this.getClass().getInterfaces()[0].toString());
			scopeList.get(scopeList.size()-1).addMethod(methodName, list);
			
			return this;
		}
		
		@Override
		public ArrayList<Scope> end() {
			return TreeBuilder.this.scopeList;
		}
		
	}
}
