package generator;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class GeneratorMain {

	public static void main(String[] args) {
		
		// dsl name?
		final String dslName = "exprDSL";
		
		// path to the package where the dsl interfaces are:
		final Path dslPath = Paths.get("./src/exprDSL");
		
		// where should the classes related to parse tree be generated to?
		final Path parseTreeGenPath = Paths.get("./src-gen/parseTreeGen/");
		
		// where should the classes related to visitor be generated to?
		final Path visitorGenPath = Paths.get("./src-gen/visitorGen");
			
		Generator tr = new Generator(dslName, dslPath, parseTreeGenPath, visitorGenPath);
		try {
			tr.generate();
		} catch (IOException e) {
			System.err.println(e.getCause().toString());
			e.printStackTrace();
		}
	}
	
}
