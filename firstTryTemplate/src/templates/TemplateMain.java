package templates;

public class TemplateMain {

	public static void main(String[] args) {
		
		final String dslName = "exprDSL";
			
		TemplateRunner tr = new TemplateRunner(dslName, "./src/");
		tr.runAll();
	}
	
}
