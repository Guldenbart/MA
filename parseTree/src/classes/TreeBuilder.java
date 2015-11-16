package classes;

import java.util.ArrayList;

import interfaces.Intermediate;
import interfaces.Start;
import interfaces.Tree;


public class TreeBuilder {

	private ParseTree tree;
	private final IntermediateScope intermediateScope;
	
	private TreeBuilder() {
		this.intermediateScope = this.new IntermediateScope();
	}
	
	public static StartScope begin() {
		return new TreeBuilder().new StartScope();
	}

	private void checkLatestScope(String interfaceName, String methodName, Object...arguments) {
		if (tree.get(tree.size()-1).getInterfaceName().compareTo(interfaceName) != 0) {
			Scope scope = new Scope(interfaceName);
			tree.addScope(scope);
		}
		tree.get(tree.size()-1).addMethod(methodName, arguments);
	}
	
	public class StartScope implements Start<Tree> {

		@Override
		public Intermediate<Tree> expr(double value) {
			String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
			checkLatestScope(this.getClass().getInterfaces()[0].toString(), methodName, value);
			
			return TreeBuilder.this.intermediateScope;
		}

		@Override
		public Intermediate<Tree> expr(Tree t) {
			tree.addAll(t); // merge both lists
			
			return TreeBuilder.this.intermediateScope;
		}
		
	}
	
	public class IntermediateScope implements Intermediate <Tree> {

		@Override
		public Intermediate<Tree> plus(double value) {
			String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
			checkLatestScope(this.getClass().getInterfaces()[0].toString(), methodName, value);
			
			return this;
		}

		@Override
		public Intermediate<Tree> plus(ArrayList<Scope> tree) {
			tree.addAll(tree); // merge both lists
			
			return this;
		}

		@Override
		public Intermediate<Tree> minus(double value) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Intermediate<Tree> minus(ArrayList<Scope> tree) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Intermediate<Tree> times(double value) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Intermediate<Tree> times(ArrayList<Scope> tree) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Intermediate<Tree> divided(double value) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Intermediate<Tree> divided(ArrayList<Scope> tree) {
			// TODO Auto-generated method stub
			return null;
		}
		
		@Override
		public Tree end() {
			// TODO Auto-generated method stub
			return null;
		}
		
	}
}
