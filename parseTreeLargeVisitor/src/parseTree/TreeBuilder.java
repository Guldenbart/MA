package parseTree;

import java.util.ArrayList;

import exprDSL.Intermediate;
import exprDSL.Start;
import parseTree.ParseTree;

public class TreeBuilder {

	private ParseTree parseTree;
	private final IntermediateScope intermediateScope;
	private ArrayList<Scope> scopeList;
	
	private TreeBuilder() {
		this.intermediateScope = this.new IntermediateScope();
		this.scopeList = new ArrayList<Scope>();
	}
	
	public static StartScope begin() {
		return new TreeBuilder().new StartScope();
	}
	
	public class StartScope implements Start<ParseTree> {
		
		private ArrayList<AMethod> startList;
		
		private StartScope() {
			startList = new ArrayList<AMethod>();
		}

		@Override
		public IntermediateScope expr(double value) {
			AMethod m = new MethodExprSimple(value);
			startList.add(m);
			
			// new Scope
			Scope scopeStart = new ScopeStart(startList);
			TreeBuilder.this.scopeList.add(scopeStart);
			
			return TreeBuilder.this.intermediateScope;
		}

		@Override
		public IntermediateScope expr(ParseTree list) {
			AMethod m = new MethodExprNested(list);
			startList.add(m);
			
			// new Scope
			Scope scopeStart = new ScopeStart(startList);
			TreeBuilder.this.scopeList.add(scopeStart);
			
			return TreeBuilder.this.intermediateScope;
		}
		
	}
	
	public class IntermediateScope implements Intermediate <ParseTree> {
		
		private ArrayList<AMethod> intermediateList;
		
		private IntermediateScope() {
			intermediateList = new ArrayList<AMethod>();
		}

		@Override
		public Intermediate<ParseTree> plus(double value) {
			AMethod m = new MethodPlusSimple(value);
			intermediateList.add(m);
			
			return this;
		}

		@Override
		public Intermediate<ParseTree> plus(ParseTree list) {
			AMethod m = new MethodPlusNested(list);
			intermediateList.add(m);
			
			return this;
		}

		@Override
		public Intermediate<ParseTree> minus(double value) {
			AMethod m = new MethodMinusSimple(value);
			intermediateList.add(m);
			
			return this;
		}

		@Override
		public Intermediate<ParseTree> minus(ParseTree list) {
			AMethod m = new MethodMinusNested(list);
			intermediateList.add(m);
			
			return this;
		}

		@Override
		public Intermediate<ParseTree> times(double value) {
			AMethod m = new MethodTimesSimple(value);
			intermediateList.add(m);
			
			return this;
		}

		@Override
		public Intermediate<ParseTree> times(ParseTree list) {
			AMethod m = new MethodTimesNested(list);
			intermediateList.add(m);
			
			return this;
		}

		@Override
		public Intermediate<ParseTree> divided(double value) {
			AMethod m = new MethodDividedSimple(value);
			intermediateList.add(m);
			
			return this;
		}

		@Override
		public Intermediate<ParseTree> divided(ParseTree list) {
			AMethod m = new MethodDividedNested(list);
			intermediateList.add(m);
			
			return this;
		}
		
		@Override
		public ParseTree end() {
			AMethod m = new MethodEnd();
			intermediateList.add(m);
			
			// new Scope
			Scope scopeIntermediate = new ScopeIntermediate(intermediateList);
			scopeList.add(scopeIntermediate);
			
			// new ParseTree
			parseTree = new ParseTree(scopeList);
			
			return TreeBuilder.this.parseTree;
		}
		
	}
}
