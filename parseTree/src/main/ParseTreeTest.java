package main;

import parseTree.ScopeList;
import parseTree.TreeBuilder;

public class ParseTreeTest {

	public static void main(String[] args) {
		
		// arithmetischer Ausdruck ohne Klammerung:
        ScopeList x = TreeBuilder.begin().expr(1).plus(2).times(3).minus(4).divided(5).end();
        x.print(false);


        // arithmetischer Ausdruck mit Klammerung:
        ScopeList y = TreeBuilder.begin()
        		.expr(TreeBuilder.begin().expr(1).plus(2).end())
        		.times(3)
        		.minus(TreeBuilder.begin().expr(4).divided(5).end())
        		.end();
        y.print(true);

	}

}
