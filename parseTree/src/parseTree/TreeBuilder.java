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
			parseTree.appendMethod("expr", value);
			
			return TreeBuilder.this.intermediateScope;
		}

		@Override
		public IntermediateScope expr(ParseTree list) {
			parseTree.checkLatestScope("Start");
			parseTree.appendMethod("expr", list);
			
			return TreeBuilder.this.intermediateScope;
		}
		
	}
	
	public class IntermediateScope implements Intermediate <ParseTree> {

		@Override
		public Intermediate<ParseTree> plus(double value) {
			parseTree.checkLatestScope("Intermediate");
			parseTree.appendMethod("plus", value);
			
			return this;
		}

		@Override
		public Intermediate<ParseTree> plus(ParseTree list) {
			parseTree.checkLatestScope("Intermediate");
			parseTree.appendMethod("plus", list);
			
			return this;
		}

		@Override
		public Intermediate<ParseTree> minus(double value) {
			parseTree.checkLatestScope("Intermediate");
			parseTree.appendMethod("minus", value);
			
			return this;
		}

		@Override
		public Intermediate<ParseTree> minus(ParseTree list) {
			parseTree.checkLatestScope("Intermediate");
			parseTree.appendMethod("minus", list);
			
			return this;
		}

		@Override
		public Intermediate<ParseTree> times(double value) {
			parseTree.checkLatestScope("Intermediate");
			parseTree.appendMethod("times", value);
			
			return this;
		}

		@Override
		public Intermediate<ParseTree> times(ParseTree list) {
			parseTree.checkLatestScope("Intermediate");
			parseTree.appendMethod("times", list);
			
			return this;
		}

		@Override
		public Intermediate<ParseTree> divided(double value) {
			parseTree.checkLatestScope("Intermediate");
			parseTree.appendMethod("divided", value);
			
			return this;
		}

		@Override
		public Intermediate<ParseTree> divided(ParseTree list) {
			parseTree.checkLatestScope("Intermediate");
			parseTree.appendMethod("divided", list);
			
			return this;
		}
		
		@Override
		public ParseTree end() {
			parseTree.checkLatestScope("Intermediate");
			parseTree.appendMethod("end");
			
			return TreeBuilder.this.parseTree;
		}
		
	}
}
