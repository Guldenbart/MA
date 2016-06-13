package generator;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * Root class for all Generator* objects.
 * @author Daniel Fritz
 *
 */
class GeneratorParseTree implements Iterable<GeneratorScope> {
	
	/**
	 * list of all {@link GeneratorScope} objects that belong to this tree.
	 */
	private List<GeneratorScope> scopes;
	
	/**
	 * constructor that initializes scopes.
	 * @param genScopes list of {@link GeneratorScope}s that is stored in this class.
	 */
	GeneratorParseTree(final List<GeneratorScope> genScopes) {
		scopes = genScopes;
	}
	
	/**
	 * gets the list of all {@link GeneratorScope} objects.
	 * @return the list.
	 */
	public List<GeneratorScope> getAllScopes() {
		return scopes;
	}
	
	/**
	 * Gets a unique set of all methods with no parameter.
	 * @return the set.
	 */
	public Set<GeneratorMethod> getMethodsUnique() {
		Set<GeneratorMethod> retVal = new HashSet<GeneratorMethod>();
		
		for (GeneratorScope gs : scopes) {
			retVal.addAll(gs.getMethods());
		}
		
		return retVal;
	}
	
	/**
	 * Gets a unique set of all methods with a simple parameter.
	 * @return the set.
	 */
	public Set<GeneratorMethod> getSimpleMethodsUnique() {
		Set<GeneratorMethod> retVal = new HashSet<GeneratorMethod>();
		
		for (GeneratorScope gs : scopes) {
			retVal.addAll(gs.getSimpleMethods());
		}
		
		return retVal;
	}
	
	/**
	 * Gets a unique set of all methods with a nested parameter.
	 * @return the set.
	 */
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
