package main;

import parseTree.ParseTree;
import parseTree.TreeBuilder;
import visitor.DoubleMathVisitor;

public class ParseTreeTest {

	public static void main(String[] args) {
		
		// arithmetischer Ausdruck ohne Klammerung:
        ParseTree x = TreeBuilder.begin().expr(1).plus(2).times(3).minus(4).divided(5).end();
        System.out.printf("%s = %s%n", "x", x.toString());


        // arithmetischer Ausdruck mit Klammerung:
        ParseTree y = 
        		TreeBuilder.begin().expr(
        			TreeBuilder.begin().expr(1).plus(2).end()
        		).times(3).minus(
        			TreeBuilder.begin().expr(4).plus(1).divided(5).end()
        		).end();
        y.toString();
        
        System.out.printf("%s = %s%n", "y", y.toString());
        
        DoubleMathVisitor visitorX = new DoubleMathVisitor();
        x.accept(visitorX);
        System.out.printf("%s = %f%n", "Ergebnis des Ausdrucks x:", visitorX.result());
        
        DoubleMathVisitor visitorY = new DoubleMathVisitor();
        y.accept(visitorY);
        System.out.printf("%s = %f%n", "Ergebnis des Ausdrucks y:", visitorY.result());

	}

}
