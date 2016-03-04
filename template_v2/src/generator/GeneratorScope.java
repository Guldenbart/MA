package generator;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public class GeneratorScope {

	private String interfaceName;
	private ArrayList<GeneratorMethod> methods;
	
	public GeneratorScope(String iName, ArrayList<GeneratorMethod> list) {
		interfaceName = iName;
		methods = list;
	}
	
	public String getInterfaceName() {
		return interfaceName;
	}
	
	public ArrayList<GeneratorMethod> getAllMethods() {
		return methods;
	}
	
	public ArrayList<GeneratorMethod> getMethods() {
		ArrayList<GeneratorMethod> methodList = new ArrayList<GeneratorMethod>();
		
		for(GeneratorMethod gm : methods) {
			if (!gm.getIsNestedMethod() && !gm.getIsSimpleMethod()) {
				methodList.add(gm);
			}
		}
		
		return methodList;
	}
	
	public ArrayList<GeneratorMethod> getSimpleMethods() {
		ArrayList<GeneratorMethod> simpleMethodList = new ArrayList<GeneratorMethod>();
		
		for(GeneratorMethod gm : methods) {
			if (gm.getIsSimpleMethod()) {
				simpleMethodList.add(gm);
			}
		}
		
		return simpleMethodList;
	}
	
	public ArrayList<GeneratorMethod> getNestedMethods() {
		ArrayList<GeneratorMethod> nestedMethodList = new ArrayList<GeneratorMethod>();
		
		for(GeneratorMethod gm : methods) {
			if (gm.getIsNestedMethod()) {
				nestedMethodList.add(gm);
			}
		}
		
		return nestedMethodList;
	}

	public Path getLopeClassPath(Path basePath) {
		return basePath.resolve(Paths.get("Lope" + interfaceName + ".java"));
	}
}
