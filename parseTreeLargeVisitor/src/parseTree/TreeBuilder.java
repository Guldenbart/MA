package parseTree;

import exprDSL.Intermediate;
import exprDSL.Start;
import parseTree.ParseTree;


public class TreeBuilder {

	private ParseTree parseTree;
	private final IntermediateScope intermediateScope;
	
	private TreeBuilder() {
		this.parseTree = new ParseTree();
		this.intermediateScope = this.new IntermediateScope();
	}
	
	public static StartScope begin() {
		return new TreeBuilder().new StartScope();
	}
	
	public class StartScope implements Start<ParseTree> {

		@Override
		public IntermediateScope expr(double value) {
			parseTree.checkLatestScope("Start");
			Visitable v = new MethodExprSimple(value);
			parseTree.appendVisitable(v);
			
			return TreeBuilder.this.intermediateScope;
		}

		@Override
		public IntermediateScope expr(ParseTree list) {
			parseTree.checkLatestScope("Start");
			Visitable v = new MethodExprNested(list);
			parseTree.appendVisitable(v);
			
			return TreeBuilder.this.intermediateScope;
		}
		
	}
	
	public class IntermediateScope implements Intermediate <ParseTree> {

		@Override
		public Intermediate<ParseTree> plus(double value) {
			parseTree.checkLatestScope("Intermediate");
			Visitable v = new MethodPlusSimple(value);
			parseTree.appendVisitable(v);
			
			return this;
		}

		@Override
		public Intermediate<ParseTree> plus(ParseTree list) {
			parseTree.checkLatestScope("Intermediate");
			Visitable v = new MethodPlusNested(list);
			parseTree.appendVisitable(v);
			
			return this;
		}

		@Override
		public Intermediate<ParseTree> minus(double value) {
			parseTree.checkLatestScope("Intermediate");
			Visitable v = new MethodMinusSimple(value);
			parseTree.appendVisitable(v);
			
			return this;
		}

		@Override
		public Intermediate<ParseTree> minus(ParseTree list) {
			parseTree.checkLatestScope("Intermediate");
			Visitable v = new MethodMinusNested(list);
			parseTree.appendVisitable(v);
			
			return this;
		}

		@Override
		public Intermediate<ParseTree> times(double value) {
			parseTree.checkLatestScope("Intermediate");
			Visitable v = new MethodTimesSimple(value);
			parseTree.appendVisitable(v);
			
			return this;
		}

		@Override
		public Intermediate<ParseTree> times(ParseTree list) {
			parseTree.checkLatestScope("Intermediate");
			Visitable v = new MethodTimesNested(list);
			parseTree.appendVisitable(v);
			
			return this;
		}

		@Override
		public Intermediate<ParseTree> divided(double value) {
			parseTree.checkLatestScope("Intermediate");
			Visitable v = new MethodDividedSimple(value);
			parseTree.appendVisitable(v);
			
			return this;
		}

		@Override
		public Intermediate<ParseTree> divided(ParseTree list) {
			parseTree.checkLatestScope("Intermediate");
			Visitable v = new MethodDividedNested(list);
			parseTree.appendVisitable(v);
			
			return this;
		}
		
		@Override
		public ParseTree end() {
			parseTree.checkLatestScope("Intermediate");
			Visitable v = new MethodEnd();
			parseTree.appendVisitable(v);
			
			return TreeBuilder.this.parseTree;
		}
		
	}
}
