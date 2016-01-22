package main;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;

import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STGroup;
import org.stringtemplate.v4.STGroupFile;

import templates.TemplateRunner;

public class TemplateTest {

	public static void main(String[] args) {
		
		final String dslName = "exprDSL";
		
		TemplateRunner tr = new TemplateRunner(dslName, "./src/");
		tr.runAll();
		
		String path = "./src/" + dslName;
		StringBuilder tempString = new StringBuilder();
		
		File actual = new File(path);
		for( File f : actual.listFiles()){
			if (f.getName().contentEquals("package-info.java")) {
				// nothing to do here
				continue;
			}
			// TODO andere Ausnahmen neben package-info?
			
			System.out.println( f.getName() );
			Class<?> c;
			try {
				String interfaceName = f.getName().substring(0, f.getName().lastIndexOf("."));
				c = Class.forName(dslName + '.' + interfaceName); // TODO geht das auch ohne package-Name?
				
				if (!c.isInterface()) {
					System.err.println("class " + c.getName() + " is not an interface!");
					continue;
				}
				
				STGroup group = new STGroupFile("./src/templates/parseTreeMethod.stg");
				ST st = group.getInstanceOf("class");
				
				//pw.write(st.render());
				
				
				Method[] methods = c.getDeclaredMethods();
				for (Method m : methods) {
					if (m.getParameters().length > 1) {
						System.err.println("only one argument per Method allowed!");
						continue;
					}
					ST methodTemp = group.getInstanceOf("method");
					
					methodTemp.add("dslName", toUC(dslName));
					methodTemp.add("iNameUC", toUC(interfaceName));
					methodTemp.add("iNameLC", toLC(interfaceName));
					methodTemp.add("scopeEnds", !interfaceName.equals(m.getReturnType().getSimpleName()));
					
					//return type
					methodTemp.add("returnType", m.getReturnType().getSimpleName());
					
					// später müsste man hier schauen, ob der Typ einem Interface der DSL entspricht oder nicht
					methodTemp.add("treeEnds", m.getReturnType().getSimpleName().equals("ParseTree"));
					
					// method name
					methodTemp.add("mNameUC", toUC(m.getName()));
					methodTemp.add("mNameLC", toLC(m.getName()));
					
					if (m.getParameters().length == 1) {
						// argument type
						methodTemp.add("argType", m.getParameters()[0].getType().getSimpleName());
							
						// argument name; make sure it's upper case
						String argName = m.getParameters()[0].getName(); 
						methodTemp.add("argName", argName);
					}
					
					tempString.append(methodTemp.render());
				}
				
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
        
        PrintWriter pw;
		try {
			pw = new PrintWriter("./src-gen/parseTreeGen/" + dslName + "ParseTree.java", "UTF-8");
			pw.write(tempString.toString());
			pw.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        // get classes from package
	}
	
	public static String toUC(String s) {
		if (s.length() == 0) {
			return "";
		} else if(s.length() == 1) {
			return s.toUpperCase();
		} else {
			return s.substring(0,1).toUpperCase() + s.substring(1);
		}
	}
	
	public static String toLC(String s) {
		if (s.length() == 0) {
			return "";
		} else if(s.length() == 1) {
			return s.toLowerCase();
		} else {
			return s.substring(0,1).toLowerCase() + s.substring(1);
		}
	}

}
