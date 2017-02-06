package generator;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

public class GeneratorMain {

	public static void main(String[] args) {
		
		Options options = new Options();
		
		Option input = new Option("i", "input", true, "file path for interface input files");
		input.setRequired(false);
		options.addOption(input);
		
		Option parseTree = new Option("p", "parse-tree", true, "file path for ParseTree related output files");
		input.setRequired(false);
		options.addOption(parseTree);
		
		Option visitor = new Option("p", "visitor", true, "file path for Visitor related output files");
		input.setRequired(false);
		options.addOption(visitor);
		
		Option first = new Option("f", "first", true, "name of the first interface");
		input.setRequired(true);
		options.addOption(first);
		
		
		CommandLineParser parser = new DefaultParser();
		HelpFormatter formatter = new HelpFormatter();
		CommandLine cmd;
		
		try {
			cmd = parser.parse(options, args);
		} catch (ParseException e) {
			System.out.println(e.getMessage());
			formatter.printHelp("utility-name", options);

			System.exit(1);
			return;
		}
		
		// initialize default values:
		// Path to the package where the dsl interfaces are (package name == DSL name):
		final Path dslPath;
		String inputString = cmd.getOptionValue("input");
		
		if (inputString == null) {
			dslPath = Paths.get("./src/exprDSL/");
		} else {
			dslPath = Paths.get(inputString);
		}
		
		
		// Where should the classes related to parse tree be generated to?
		final Path parseTreeGenPath;
		String parseTreeString = cmd.getOptionValue("parseTree");
		
		if (parseTreeString == null) {
			parseTreeGenPath = Paths.get("./src-gen/parseTreeGen");
		} else {
			parseTreeGenPath = Paths.get(parseTreeString);
		}
		
		
		// Where should the classes related to visitor be generated to?
		final Path visitorGenPath;
		String visitorString = cmd.getOptionValue("visitor");
		
		if (visitorString == null) {
			visitorGenPath = Paths.get("./src-gen/visitorGen");
		} else {
			visitorGenPath = Paths.get(visitorString);
		}
		
		// What is the name of the interface that has the first method?
		final String firstIName;
		String firstString = cmd.getOptionValue("first");
		
		if (firstString == null || firstString.equals("")) {
			firstIName = "Start";
		} else {
			firstIName = firstString;
		}
		
			
		Generator g = new Generator(dslPath, parseTreeGenPath, visitorGenPath, firstIName);
		try {
			g.generate();
		} catch (IOException e) {
			System.err.println(e.getCause().toString());
			e.printStackTrace();
		}
	}
	
}
