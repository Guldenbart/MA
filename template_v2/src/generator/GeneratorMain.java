package generator;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class GeneratorMain {

	public static void main(String[] args) {
		
		// DSL name?
		final String dslName = "exprDSL";
		
		// Path to the package where the dsl interfaces are:
		final Path dslPath = Paths.get("./src/exprDSL");
		
		// Where should the classes related to parse tree be generated to?
		final Path parseTreeGenPath = Paths.get("./src-gen/parseTreeGen/");
		
		// Where should the classes related to visitor be generated to?
		final Path visitorGenPath = Paths.get("./src-gen/visitorGen");
		
		// What is the name of the interface that has the first method?
		final String firstIName = "Start";
			
		Generator g = new Generator(dslName, dslPath, parseTreeGenPath, visitorGenPath, firstIName);
		try {
			g.generate();
		} catch (IOException e) {
			System.err.println(e.getCause().toString());
			e.printStackTrace();
		}
	}
	
}
