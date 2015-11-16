package classes;

import classes.ScopeList;

import interfaces.Intermediate;
import interfaces.Start;


public class TreeBuilder {

	private ScopeList scopeList;
	private final IntermediateScope intermediateScope;
	
	private TreeBuilder() {
		this.scopeList = new ScopeList();
		this.intermediateScope = this.new IntermediateScope();
	}
	
	public static StartScope begin() {
		return new TreeBuilder().new StartScope();
	}

	private void checkLatestScope(String interfaceName) {
		if (scopeList.isEmpty() || scopeList.get(scopeList.size()-1).getInterfaceName().compareTo(interfaceName) != 0) {
			Scope scope = new Scope(interfaceName);
			scopeList.add(scope);
		}
		//scopeList.get(scopeList.size()-1).addMethod(methodName, arguments);
	}
	
	public class StartScope implements Start< ScopeList > {

		@Override
		public IntermediateScope expr(double value) {
			String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
			
			Class<?>[] interfaces = this.getClass().getInterfaces();
			checkLatestScope(interfaces[0].toString());
			scopeList.get(scopeList.size()-1).addMethod(methodName, value);
			
			return TreeBuilder.this.intermediateScope;
		}

		@Override
		public IntermediateScope expr(ScopeList list) {
			String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
			
			Class<?>[] interfaces = this.getClass().getInterfaces();
			checkLatestScope(interfaces[0].toString());
			scopeList.get(scopeList.size()-1).addMethod(methodName, list);
			
			return TreeBuilder.this.intermediateScope;
		}
		
	}
	
	public class IntermediateScope implements Intermediate <ScopeList> {

		@Override
		public Intermediate<ScopeList> plus(double value) {
			String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
			
			checkLatestScope(this.getClass().getInterfaces()[0].toString());
			scopeList.get(scopeList.size()-1).addMethod(methodName, value);
			
			return this;
		}

		@Override
		public Intermediate<ScopeList> plus(ScopeList list) {
			String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
			
			checkLatestScope(this.getClass().getInterfaces()[0].toString());
			scopeList.get(scopeList.size()-1).addMethod(methodName, list);
			
			return this;
		}

		@Override
		public Intermediate<ScopeList> minus(double value) {
			String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
			
			checkLatestScope(this.getClass().getInterfaces()[0].toString());
			scopeList.get(scopeList.size()-1).addMethod(methodName, value);
			
			return this;
		}

		@Override
		public Intermediate<ScopeList> minus(ScopeList list) {
			String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
			
			checkLatestScope(this.getClass().getInterfaces()[0].toString());
			scopeList.get(scopeList.size()-1).addMethod(methodName, list);
			
			return this;
		}

		@Override
		public Intermediate<ScopeList> times(double value) {
			String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
			
			checkLatestScope(this.getClass().getInterfaces()[0].toString());
			scopeList.get(scopeList.size()-1).addMethod(methodName, value);
			
			return this;
		}

		@Override
		public Intermediate<ScopeList> times(ScopeList list) {
			String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
			
			checkLatestScope(this.getClass().getInterfaces()[0].toString());
			scopeList.get(scopeList.size()-1).addMethod(methodName, list);
			
			return this;
		}

		@Override
		public Intermediate<ScopeList> divided(double value) {
			String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
			
			checkLatestScope(this.getClass().getInterfaces()[0].toString());
			scopeList.get(scopeList.size()-1).addMethod(methodName, value);
			
			return this;
		}

		@Override
		public Intermediate<ScopeList> divided(ScopeList list) {
			String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
			
			checkLatestScope(this.getClass().getInterfaces()[0].toString());
			scopeList.get(scopeList.size()-1).addMethod(methodName, list);
			
			return this;
		}
		
		@Override
		public ScopeList end() {
			String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
			
			checkLatestScope(this.getClass().getInterfaces()[0].toString());
			scopeList.get(scopeList.size()-1).addMethod(methodName);
			return TreeBuilder.this.scopeList;
		}
		
	}
}
