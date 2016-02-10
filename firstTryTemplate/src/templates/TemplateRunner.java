package templates;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STGroup;
import org.stringtemplate.v4.STGroupFile;

public class TemplateRunner {
	
	private final String dslName;
	private final String path;
	private final String firstInterfaceName = "Start";
	
	/*
	 * If interface A extends interface B, the methods of B have to go
	 * into the Scope of interface A. We have to store these relations
	 * so that later, we put every method in the correct scope.
	 */
	private Map<Class<?>, ArrayList<Class<?>>> interfaceMap;
	
	/*
	 * We remember which interfaces are extended by others,
	 * because we have to skip these.
	 */
	private ArrayList<Class<?>> skipList;
	private ST visitorSuperClassTemp;
	
	public TemplateRunner(String dslName, String basePath) {
		this.dslName = dslName;
		this.path = basePath + dslName;
		interfaceMap = new HashMap<Class<?>, ArrayList<Class<?>> >();
		skipList = new ArrayList<Class<?>>();
		
		STGroup visitorSuperClassGroup = new STGroupFile("./src/templates/visitorSuperClass.stg");
		visitorSuperClassTemp = visitorSuperClassGroup.getInstanceOf("visitorSuperClass");
	}
	
	public void runAll() {
		fillInterfaceMap();
		
		String retVal = runClassTemplate();
		String treePath = "./src-gen/parseTreeGen/" + toUC(dslName) + "TreeBuilder.java";
		String visitorPath = "./src-gen/visitorGen/A" + toUC(dslName) + "Visitor.java";
		
		/*
		 * TODO FRAGE: Wenn eine Fallunterscheidung auftauchen würde, was ist wichtiger:
		 * PrintWriter lassen, um sich Zeilen zu sparen
		 * oder in die Klammer, damit so spät anlegen, wie möglich (kleinste Schleife)
		 * ?
		 */
		writeTemplateFile(treePath, retVal);
		writeTemplateFile(visitorPath, visitorSuperClassTemp.render());
	}
	
	void fillInterfaceMap() {
		File actual = new File(path);
		
		File[] files = actual.listFiles();
		if (files == null) {
			System.err.println("path " + path + " does not exist!");
			return;
		}
		
		for(File f : files){
			
			if (f == null) {
				return;
			}
			
			if (f.getName().contentEquals("package-info.java")) {
				// nothing to do here
				continue;
			}
			
			try {
				String interfaceName = f.getName().substring(0, f.getName().lastIndexOf("."));
				Class<?> c = Class.forName(dslName + '.' + interfaceName); // TODO geht das auch ohne package-Name?
				
				if (!c.isInterface()) {
					System.err.println("class " + c.getName() + " is not an interface and will be ignored!");
					continue;
				}
				
				Class<?>[] classArray = c.getInterfaces();
				
				ArrayList<Class<?>> interfaceMapValue = new ArrayList<Class<?>>();
				// add yourself first; this makes it easier when evaluating the list later
				interfaceMapValue.add(c);
				
				// now, add all interfaces that you extend
				for (Class<?> clazz : classArray) {
					interfaceMapValue.add(clazz);
					skipList.add(clazz);
				}
				interfaceMap.put(c, interfaceMapValue);
				
			} catch (ClassNotFoundException e) {
				System.err.println("There is no such class named " + e.getClass() + '!');
				e.printStackTrace();
			}
			
		}
	}
	
	private String runClassTemplate() {
		StringBuilder sb = new StringBuilder();
		
		STGroup classGroup = new STGroupFile("./src/templates/treeBuilderClass.stg");
		ST classTemp = classGroup.getInstanceOf("class");
		
		visitorSuperClassTemp.add("dslName", toUC(dslName));
		
		
		for(Entry<Class<?>, ArrayList<Class<?>>> entry : interfaceMap.entrySet()) {
			Class<?> curInterface = entry.getKey();
			
			if(skipList.contains(curInterface)) {
				// skip interfaces that are extended by others 
				continue;
			}
			
			visitorSuperClassTemp.add("interfaces", curInterface.getSimpleName());
			
			if(!curInterface.getSimpleName().equals(firstInterfaceName)) {
				/*
				 *  we must not add the interface with 'firstInterfaceName'
				 *  because it has to be handled separately
				 */
				classTemp.add("interfaceNamesUC", curInterface.getSimpleName());
				classTemp.add("interfaceNamesLC", toLC(curInterface.getSimpleName()));
			}
			
			sb.append(runScopeTemplate(entry.getKey().getSimpleName(), entry.getValue()));
		}
		
		classTemp.add("dslNameUC", toUC(dslName));
		classTemp.add("dslName", dslName);
		classTemp.add("firstInterfaceName", firstInterfaceName);
		classTemp.add("scopesString", sb.toString());
		
		return classTemp.render();
	}
	
	private String runScopeTemplate(String interfaceName, ArrayList<Class<?>> interfaceList){
		StringBuilder sb = new StringBuilder();
		
		// gather the methods of all interfaces that this scope includes
		for (Class<?> i : interfaceList) {
			sb.append(runMethodTemplate(interfaceName, i.getDeclaredMethods()));
		}
		
		// template for scopes in the parseTreeGen package
		STGroup scopeGroup = new STGroupFile("./src/templates/treeBuilderScope.stg");
		ST scopeTemp = scopeGroup.getInstanceOf("scope");
		scopeTemp.add("interfaceName", interfaceName);
		scopeTemp.add("methodsString", sb.toString());
		
		// template for 'lope' in the visitorGen package
		STGroup lopeGroup = new STGroupFile("./src/templates/lope.stg");
		ST lopeTemp = lopeGroup.getInstanceOf("lope");
		lopeTemp.add("dslName", toUC(dslName));
		lopeTemp.add("iNameUC", interfaceName);
		writeTemplateFile("./src-gen/parseTreeGen/Lope" + interfaceName + ".java", lopeTemp.render());
		
		return scopeTemp.render();
	}

	private String runMethodTemplate(String interfaceName, Method[] methods) {
		StringBuilder sb = new StringBuilder();
		STGroup treeBuilderMethodGroup = new STGroupFile("./src/templates/treeBuilderMethod.stg");
		STGroup visitorMethodGroup = new STGroupFile("./src/templates/visitorMethodClass.stg");

		for (Method m : methods) {
			if (m.getParameters().length > 1) {
				System.err.println("only one argument per Method allowed!");
				continue;
			}
			ST treeMethodTemp = treeBuilderMethodGroup.getInstanceOf("treeMethod");
			
			treeMethodTemp.add("dslName", toUC(dslName));
			treeMethodTemp.add("iNameUC", interfaceName);
			treeMethodTemp.add("iNameLC", toLC(interfaceName));
			treeMethodTemp.add("scopeEnds", !interfaceName.equals(m.getReturnType().getSimpleName()));
			
			//return type
			treeMethodTemp.add("returnTypeUC", toUC(m.getReturnType().getSimpleName()));
			treeMethodTemp.add("returnTypeLC", toLC(m.getReturnType().getSimpleName()));
			
			// später müsste man hier evtl schauen, ob der Typ einem Interface der DSL entspricht oder nicht
			treeMethodTemp.add("treeEnds", m.getReturnType().getSimpleName().equals("ParseTree"));
			
			// method name
			String mNameUC = toUC(m.getName());
			treeMethodTemp.add("mNameUC", mNameUC);
			treeMethodTemp.add("mNameLC", toLC(mNameUC));
			
			if (m.getParameters().length == 1) {
				// argument type
				String argType = m.getParameters()[0].getType().getSimpleName();
				treeMethodTemp.add("argType", argType);
				// argument name
				treeMethodTemp.add("argName", m.getParameters()[0].getName());
				
				if (argType.equals("ParseTree")) {
					// 'ParseTree' as argumentType means that he will have a nested method
					writeVisitorNestedMethodFile(mNameUC, visitorMethodGroup);
					visitorSuperClassTemp.add("nestedMethods", mNameUC);
					
				} else {
					// in any other case, it's a simple method
					writeVisitorSimpleMethodFile(mNameUC, argType, visitorMethodGroup);
					visitorSuperClassTemp.add("simpleMethods", mNameUC);
				}
					
			} else { // no arguments
				// having no argument at all means that it's just a 'Method'
				writeVisitorMethodFile(mNameUC, visitorMethodGroup);
				visitorSuperClassTemp.add("methods", mNameUC);
			}
			
			sb.append(treeMethodTemp.render());
			
		} // end foreach: method
		
		return sb.toString();
	}
	
	private void writeVisitorNestedMethodFile(String mNameUC, STGroup visitorMethodGroup) {		
		ST visitorMethodTemp = visitorMethodGroup.getInstanceOf("visitorNestedMethod");
		
		visitorMethodTemp.add("mNameUC", mNameUC);
		visitorMethodTemp.add("mNameLC", toLC(mNameUC));
		visitorMethodTemp.add("dslName", toUC(dslName));
		
		String path = "./src-gen/parseTreeGen/NestedMethod" + mNameUC + ".java";
		
		writeTemplateFile(path, visitorMethodTemp.render());
	}
	
	private void writeVisitorSimpleMethodFile(String mNameUC, String argType, STGroup visitorMethodGroup) {
		ST visitorMethodTemp = visitorMethodGroup.getInstanceOf("visitorSimpleMethod");
		
		visitorMethodTemp.add("mNameUC", mNameUC);
		visitorMethodTemp.add("mNameLC", toLC(mNameUC));
		visitorMethodTemp.add("argType", argType);
		visitorMethodTemp.add("dslName", toUC(dslName));
		
		String path = "./src-gen/parseTreeGen/SimpleMethod" + mNameUC + ".java";
		
		writeTemplateFile(path, visitorMethodTemp.render());
	}
	
	private void writeVisitorMethodFile(String mNameUC, STGroup visitorMethodGroup) {
		ST visitorMethodTemp = visitorMethodGroup.getInstanceOf("visitorMethod");
		
		visitorMethodTemp.add("mNameUC", mNameUC);
		visitorMethodTemp.add("mNameLC", toLC(mNameUC));
		visitorMethodTemp.add("dslName", toUC(dslName));
		
		String path = "./src-gen/parseTreeGen/Method" + mNameUC + ".java";
		
		writeTemplateFile(path, visitorMethodTemp.render());		
	}
	
	private void writeTemplateFile(String path, String content) {
		try {
			PrintWriter pw = new PrintWriter(new FileOutputStream(path, false));
			pw.write(content);
			
			pw.close();
		} catch (FileNotFoundException e) {
			System.err.println("Error when writing file " + path + "!");
			e.printStackTrace();
		}
	}
	
	private String toUC(String s) {
		if (s.length() == 0) {
			return "";
		} else if(s.length() == 1) {
			return s.toUpperCase();
		} else {
			return s.substring(0,1).toUpperCase() + s.substring(1);
		}
	}
	
	private String toLC(String s) {
		if (s.length() == 0) {
			return "";
		} else if(s.length() == 1) {
			return s.toLowerCase();
		} else {
			return s.substring(0,1).toLowerCase() + s.substring(1);
		}
	}

}
