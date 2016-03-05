package main;

import parseTree.ParseTree;
import parseTreeGen.ExprDSLTreeBuilder;
import visitor.ExpressionBuildVisitor;

public class TemplateTest {

	public static void main(String[] args) {
		
		// arithmetischer Ausdruck ohne Klammerung:
        ParseTree x = ExprDSLTreeBuilder.begin().expr(1).plus(2).times(3).minus(4).divided(5).end();
        System.out.printf("%s = %s%n", "x", x.toString());


        // arithmetischer Ausdruck mit Klammerung:
        ParseTree y = 
        		ExprDSLTreeBuilder.begin().expr(
        			ExprDSLTreeBuilder.begin().expr(1).plus(2).end()
        		).times(3).minus(
        			ExprDSLTreeBuilder.begin().expr(4).plus(1).divided(5).end()
        		).end();
        y.toString();
        
        System.out.printf("%s = %s%n", "y", y.toString());
        
        
        // ExpressionBuildVisitor test
        System.out.println("ExpressionBuildVisitor:");
        
        ExpressionBuildVisitor ebvX = new ExpressionBuildVisitor();
        x.accept(ebvX);
        System.out.printf("%s = %f%n", "Ergebnis des Ausdrucks x:", ebvX.getExpression().getValue());
        
        ExpressionBuildVisitor ebvY = new ExpressionBuildVisitor();
        y.accept(ebvY);
        System.out.printf("%s = %f%n", "Ergebnis des Ausdrucks y:", ebvY.getExpression().getValue());
		
	}

}
