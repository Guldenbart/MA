package parseTree;

import exprDSL.Intermediate;
import exprDSL.Start;
import parseTree.ScopeList;


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
	
	public class StartScope implements Start<ScopeList> {

		@Override
		public IntermediateScope expr(double value) {
			checkLatestScope("Start");
			scopeList.get(scopeList.size()-1).addMethod("expr", value);
			
			return TreeBuilder.this.intermediateScope;
		}

		@Override
		public IntermediateScope expr(ScopeList list) {
			checkLatestScope("Start");
			scopeList.get(scopeList.size()-1).addMethod("expr", list);
			
			return TreeBuilder.this.intermediateScope;
		}
		
	}
	
	public class IntermediateScope implements Intermediate <ScopeList> {

		@Override
		public Intermediate<ScopeList> plus(double value) {
			checkLatestScope("Intermediate");
			scopeList.get(scopeList.size()-1).addMethod("plus", value);
			
			return this;
		}

		@Override
		public Intermediate<ScopeList> plus(ScopeList list) {
			checkLatestScope("Intermediate");
			scopeList.get(scopeList.size()-1).addMethod("plus", list);
			
			return this;
		}

		@Override
		public Intermediate<ScopeList> minus(double value) {
			checkLatestScope("Intermediate");
			scopeList.get(scopeList.size()-1).addMethod("minus", value);
			
			return this;
		}

		@Override
		public Intermediate<ScopeList> minus(ScopeList list) {
			checkLatestScope("Intermediate");
			scopeList.get(scopeList.size()-1).addMethod("minus", list);
			
			return this;
		}

		@Override
		public Intermediate<ScopeList> times(double value) {
			checkLatestScope("Intermediate");
			scopeList.get(scopeList.size()-1).addMethod("times", value);
			
			return this;
		}

		@Override
		public Intermediate<ScopeList> times(ScopeList list) {
			checkLatestScope("Intermediate");
			scopeList.get(scopeList.size()-1).addMethod("times", list);
			
			return this;
		}

		@Override
		public Intermediate<ScopeList> divided(double value) {
			checkLatestScope("Intermediate");
			scopeList.get(scopeList.size()-1).addMethod("divided", value);
			
			return this;
		}

		@Override
		public Intermediate<ScopeList> divided(ScopeList list) {
			checkLatestScope("Intermediate");
			scopeList.get(scopeList.size()-1).addMethod("divided", list);
			
			return this;
		}
		
		@Override
		public ScopeList end() {
			checkLatestScope("Intermediate");
			scopeList.get(scopeList.size()-1).addMethod("end");
			
			return TreeBuilder.this.scopeList;
		}
		
	}
}
