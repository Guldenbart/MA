package generator;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * class that wraps everything we need to know about a method scope
 * for code generation.
 * <p>
 * Each GeneratorScope object holds all {@link GeneratorMethod} objects that
 * represent a method that belongs to the scope this object represents.
 * 
 * @author Daniel Fritz
 * @see GeneratorMethod
 * @see ScopeNode
 */
final class GeneratorScope implements Iterable<GeneratorMethod> {

	/**
	 * Name of the interface that gives this scope its name.
	 */
	private String scopeName;
	
	/**
	 * Collection of all GeneratorMethod objects which belong to this scope.
	 */
	private List<GeneratorMethod> methods;
	
	
	/**
	 * Constructor that initializes <code>interfaceName</code> and
	 * <code>methods</code> of the object.
	 * @param iName interface name.
	 * @param list ArrayList of GeneratorMethod objects.
	 */
	GeneratorScope(final String iName, final List<GeneratorMethod> list) {
		scopeName = iName;
		methods = list;
	}
	
	
	/**
	 * gets the name of the interface.
	 * @return interface name
	 */
	public String getName() {
		return scopeName;
	}
	
	
	/**
	 * gets the list of all GeneratorMethod objects. 
	 * @return List of GeneratorMethod objects
	 */
	public List<GeneratorMethod> getAllMethods() {
		return methods;
	}
	
	
	/**
	 * gets a list of all <code>GeneratorMethod</code> objects with no argument.
	 * @return list of all <code>GeneratorMethod</code> objects without
	 * argument.
	 * @see AMethodNode
	 */
	public List<GeneratorMethod> getMethods() {
		List<GeneratorMethod> mList = new ArrayList<GeneratorMethod>();
		
		for (GeneratorMethod gm : methods) {
			if (gm.getIsMethod()) {
				mList.add(gm);
			}
		}
		
		return mList;
	}
	
	
	/**
	 * gets a list of all <code>GeneratorMethod</code> objects with a simple
	 * argument.
	 * @return list of all <code>GeneratorMethod</code> objects with a simple
	 * argument.
	 * @see ASimpleMethodNode
	 */
	public List<GeneratorMethod> getSimpleMethods() {
		List<GeneratorMethod> smList = new ArrayList<GeneratorMethod>();
		
		for (GeneratorMethod gm : methods) {
			if (gm.getIsSimpleMethod()) {
				smList.add(gm);
			}
		}
		
		return smList;
	}
	
	
	/**
	 * gets a list of all <code>GeneratorMethod</code> objects with a nested
	 * argument.
	 * @return list of all <code>GeneratorMethod</code> objects with a nested
	 * argument.
	 * @see ANestedMethodNode
	 */
	public List<GeneratorMethod> getNestedMethods() {
		List<GeneratorMethod> nmList = new ArrayList<GeneratorMethod>();
		
		for (GeneratorMethod gm : methods) {
			if (gm.getIsNestedMethod()) {
				nmList.add(gm);
			}
		}
		
		return nmList;
	}
	

	/**
	 * Composes the file path for the {@link ScopeNode} file that is generated
	 * from the information of the correspondingGeneratorScope object.
	 * @param basePath path that the file path is based on.
	 * @return composed path object
	 * @see ScopeNode
	 */
	public Path getScopeNodePath(final Path basePath) {
		return basePath.resolve(Paths.get("ScopeNode" + scopeName + ".java"));
	}
	
	
	/**
	 * gets the number of methods of this Scope.
	 * @return number of methods
	 */
	public int size() {
		return this.methods.size();
	}
	
	
	/**
	 * gets the method at the position given by <code>index</code>.
	 * @param index  index of the method you want 
	 * @return  method at position <code>index</code>.
	 */
	public GeneratorMethod get(final int index) {
		return this.methods.get(index);
	}
	
	
	// TODO only private?
	public boolean contains(final GeneratorMethod gm) {
		return this.methods.contains(gm);
	}
	
	public int lastIndexOf(final Object o) {
		return this.methods.lastIndexOf(o);
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		
		sb.append("Scope " + this.scopeName + ":\n");
		for (GeneratorMethod gm : methods) {
			sb.append("\t" + gm.toString() + "\n");
		}
		
		return sb.toString();
	}

	@Override
	public Iterator<GeneratorMethod> iterator() {
		return methods.iterator();
	}
	
	@Override
	public boolean equals(final Object o) {
		if (this == o) {
			return true;
		}
		
		if (o == null) {
			return false;
		}
		
		if (!(o instanceof GeneratorScope)) {
			return false;
		}
		
		GeneratorScope gs = GeneratorScope.class.cast(o);
		
		if (!this.scopeName.equals(gs.scopeName)) {
			return false;
		}
		
		for (GeneratorMethod gm : this.methods) {
			if (!gs.contains(gm)) {
				return false;
			}
		}
		
		return true;
	}
	
	@Override
	public int hashCode() {
		int result = 17;

		for (GeneratorMethod gm : methods) {
			result = 31 * result + gm.hashCode();
		}
		
		return result;
	}
}
