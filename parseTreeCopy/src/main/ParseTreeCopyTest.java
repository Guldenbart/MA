package main;

import parseTree.ParseTree;
import parseTree.TreeBuilder;
import visitor.DoubleMathVisitor;
//import visitor.DoubleMathVisitor;
import visitor.ExpressionBuildVisitor;

public class ParseTreeCopyTest {

	public static void main(String[] args) {
		
		// arithmetischer Ausdruck ohne Klammerung:
        ParseTree x = TreeBuilder.begin().expr(1.0).plus(2.0).times(3.0).minus(4.0).divided(5.0).end();
        System.out.printf("%s = %s%n", "x", x.toString());


        // arithmetischer Ausdruck mit Klammerung:
        ParseTree y = 
        		TreeBuilder.begin().expr(
        			TreeBuilder.begin().expr(1.0).plus(2.0).end()
        		).times(3.0).minus(
        			TreeBuilder.begin().expr(4.0).plus(1.0).divided(5.0).end()
        		).end();
        y.toString();
        
        System.out.printf("%s = %s%n", "y", y.toString());
        
        // DoubleMathVisitor test
        System.out.println("DoubleMathVisitor:");
        
        
        DoubleMathVisitor dmvX = new DoubleMathVisitor();
        x.accept(dmvX);
        System.out.printf("%s = %f%n", "Ergebnis des Ausdrucks x:", dmvX.result());
        
        DoubleMathVisitor dmvY = new DoubleMathVisitor();
        y.accept(dmvY);
        System.out.printf("%s = %f%n", "Ergebnis des Ausdrucks y:", dmvY.result());
        
        
        // ExpressionBuildVisitor test
        System.out.println("ExpressionBuildVisitor:");
        
        ExpressionBuildVisitor ebvX = new ExpressionBuildVisitor();
        x.accept(ebvX);
        System.out.printf("%s = %f%n", "Ergebnis des Ausdrucks x:", ebvX.expression().getValue());
        
        ExpressionBuildVisitor ebvY = new ExpressionBuildVisitor();
        y.accept(ebvY);
        System.out.printf("%s = %f%n", "Ergebnis des Ausdrucks y:", ebvY.expression().getValue());        

	}

}
