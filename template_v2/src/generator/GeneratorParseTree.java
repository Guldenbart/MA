package generator;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

class GeneratorParseTree implements Iterable<GeneratorScope>{
	private List<GeneratorScope> scopes;
	
	public GeneratorParseTree(List<GeneratorScope> genScopes) {
		scopes = genScopes;
	}
	
	public List<GeneratorScope> getAllScopes() {
		return scopes;
	}
	
	public Set<GeneratorMethod> getMethodsUnique() {
		Set<GeneratorMethod> retVal = new HashSet<GeneratorMethod>();
		
		for (GeneratorScope gs : scopes) {
			retVal.addAll(gs.getMethods());
		}
		
		return retVal;
	}
	
	public Set<GeneratorMethod> getSimpleMethodsUnique() {
		Set<GeneratorMethod> retVal = new HashSet<GeneratorMethod>();
		
		for (GeneratorScope gs : scopes) {
			retVal.addAll(gs.getSimpleMethods());
		}
		
		return retVal;
	}
	
	public Set<GeneratorMethod> getNestedMethodsUnique() {
		Set<GeneratorMethod> retVal = new HashSet<GeneratorMethod>();
		
		for (GeneratorScope gs : scopes) {
			retVal.addAll(gs.getNestedMethods());
		}
		
		return retVal;
	}

	@Override
	public Iterator<GeneratorScope> iterator() {
		return scopes.iterator();
	}
}
