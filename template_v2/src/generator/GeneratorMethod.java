package generator;

import java.nio.file.Path;
import java.nio.file.Paths;

public class GeneratorMethod {
	
	private String interfaceName;
	private String name;
	private String returnType;
	private String argumentType;
	private String argumentName;
	
	private static final String type_noArg = "methods";
	private static final String type_simple = "simpleMethods";
	private static final String type_nested = "nestedMethods";
	
	public GeneratorMethod(String iName, String mName, String retType, String argType, String argName) {
		interfaceName = iName;
		name = mName;
		returnType = retType;
		argumentType = argType;
		argumentName = argName;
	}
	
	public GeneratorMethod(String iName, String mName, String retType) {
		this(iName, mName, retType, "", "");
	}
	
	public String getName() {
		return name;
	}
	
	public String getReturnType() {
		return returnType;
	}
	
	public String getArgumentType() {
		return argumentType;
	}
	
	public String getArgumentName() {
		return argumentName;
	}
	
	public boolean getHasArgument() {
		return !(argumentType.equals(""));
	}
	
	public boolean getScopeEnds() {
		return !(interfaceName.equals(returnType));
	}
	
	public boolean getTreeEnds() {
		return returnType.equals("ParseTree");
	}
	
	public boolean getIsSimpleMethod() {
		return (!argumentType.equals("ParseTree") && !argumentType.equals(""));
	}
	
	public boolean getIsNestedMethod() {
		return argumentType.equals("ParseTree");
	}

	public Path getVisitorClassPath(Path basePath) {
		if (argumentType.equals("")) {
			return basePath.resolve(Paths.get("Method" + name + ".java"));
		} else if (argumentType.equals("ParseTree")) {
			return basePath.resolve(Paths.get("NestedMethod" + name + ".java"));
		} else {
			return basePath.resolve(Paths.get("SimpleMethod" + name + ".java"));
		}
	}
	
	public String getTypeString() {
		if (argumentType.equals("")) {
			return type_noArg;
		} else if (argumentType.equals("ParseTree")) {
			return type_nested;
		} else {
			return type_simple;
		}
	}
	
	// TODO FRAGE: Gestaltung der Variablen-Namen (auch: *packageNAME, damit man weiß, dass es ein String und keine Variable ist?)
	// TODO FRAGE: Ist es in Ordnung, dass Methoden-Klassen ohne Argument einfach Method* heißen?
	// TODO FRAGE: zu try/catch/throw
	// TODO FRAGE: dslName wird mehrere Male zusammen mit Method gebraucht (visitorMethodClass.stg, String für visitorSuperClass). Als Klassen-Variable hinzufügen? Oder nicht, weil es immer dasselbe ist?
	// TODO FRAGE: Variante bei GeneratorScope: Methoden einzeln hinzufügen und in 3 versch. Listen einfügen; GeneratorScope.getMethods() fügt die Listen zusammen.
}
