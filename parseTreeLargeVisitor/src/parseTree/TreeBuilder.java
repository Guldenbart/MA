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
		
		private ArrayList<Visitable> startList;
		
		private StartScope() {
			startList = new ArrayList<Visitable>();
		}

		@Override
		public IntermediateScope expr(double value) {
			Visitable v = new MethodExprSimple(value);
			startList.add(v);
			
			// new Scope
			Scope scopeStart = new ScopeStart(startList);
			TreeBuilder.this.scopeList.add(scopeStart);
			
			return TreeBuilder.this.intermediateScope;
		}

		@Override
		public IntermediateScope expr(ParseTree list) {
			Visitable v = new MethodExprNested(list);
			startList.add(v);
			
			// new Scope
			Scope scopeStart = new ScopeStart(startList);
			TreeBuilder.this.scopeList.add(scopeStart);
			
			return TreeBuilder.this.intermediateScope;
		}
		
	}
	
	public class IntermediateScope implements Intermediate <ParseTree> {
		
		private ArrayList<Visitable> intermediateList;
		
		private IntermediateScope() {
			intermediateList = new ArrayList<Visitable>();
		}

		@Override
		public Intermediate<ParseTree> plus(double value) {
			Visitable v = new MethodPlusSimple(value);
			intermediateList.add(v);
			
			return this;
		}

		@Override
		public Intermediate<ParseTree> plus(ParseTree list) {
			Visitable v = new MethodPlusNested(list);
			intermediateList.add(v);
			
			return this;
		}

		@Override
		public Intermediate<ParseTree> minus(double value) {
			Visitable v = new MethodMinusSimple(value);
			intermediateList.add(v);
			
			return this;
		}

		@Override
		public Intermediate<ParseTree> minus(ParseTree list) {
			Visitable v = new MethodMinusNested(list);
			intermediateList.add(v);
			
			return this;
		}

		@Override
		public Intermediate<ParseTree> times(double value) {
			Visitable v = new MethodTimesSimple(value);
			intermediateList.add(v);
			
			return this;
		}

		@Override
		public Intermediate<ParseTree> times(ParseTree list) {
			Visitable v = new MethodTimesNested(list);
			intermediateList.add(v);
			
			return this;
		}

		@Override
		public Intermediate<ParseTree> divided(double value) {
			Visitable v = new MethodDividedSimple(value);
			intermediateList.add(v);
			
			return this;
		}

		@Override
		public Intermediate<ParseTree> divided(ParseTree list) {
			Visitable v = new MethodDividedNested(list);
			intermediateList.add(v);
			
			return this;
		}
		
		@Override
		public ParseTree end() {
			Visitable v = new MethodEnd();
			intermediateList.add(v);
			
			// new Scope
			Scope scopeIntermediate = new ScopeIntermediate(intermediateList);
			scopeList.add(scopeIntermediate);
			
			// new ParseTree
			parseTree = new ParseTree(scopeList);
			
			return TreeBuilder.this.parseTree;
		}
		
	}
}
