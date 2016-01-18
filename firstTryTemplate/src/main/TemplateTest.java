package main;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;

import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STGroup;
import org.stringtemplate.v4.STGroupFile;

public class TemplateTest {

	public static void main(String[] args) {
		
		final String dslName = "exprDSL";
		
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
            String interfaceName = f.getName().substring(0, f.getName().lastIndexOf("."));
            Class c;
			try {
				c = Class.forName("exprDSL." + interfaceName);
				if (!c.isInterface()) {
					System.err.println("class " + c.getName() + " is not an interface!");
					continue;
				}
				
				STGroup group = new STGroupFile("./src/templates/parseTreeTemplate.stg");
				ST st = group.getInstanceOf("class");
				
				//pw.write(st.render());
				
				
				Method[] methods = c.getDeclaredMethods();
				for (Method m : methods) {
					if (m.getParameters().length > 1) {
						System.err.println("only one argument per Method allowed!");
						continue;
					}
					ST methodTemp = group.getInstanceOf("method");
					
					methodTemp.add("interfaceName", interfaceName);
					methodTemp.add("interfaceChanges", interfaceName.equals(m.getReturnType().getSimpleName()));
					
					//return type
					System.out.println(m.getReturnType().getSimpleName());
					if (!m.getReturnType().getSimpleName().equals("Object")) {
						methodTemp.add("returnType", m.getReturnType().getSimpleName());
					}
					
					// method name
					methodTemp.add("name", m.getName());
					
					if (m.getParameters().length == 1) {
						// argument type
						if (!m.getParameters()[0].getType().equals(Class.forName("java.lang.Object"))) {
							methodTemp.add("argType", m.getParameters()[0].getType());
							System.out.println("  Argument Type:" + m.getParameters()[0].getType());
						}
							
						// argument name; make sure it's upper case
						String argName = m.getParameters()[0].getName().substring(0,1).toUpperCase() + m.getParameters()[0].getName().substring(1); 
						methodTemp.add("argName", argName);
					}
					
					tempString.append(methodTemp.render());
					//System.out.println("  " + m);
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

}
