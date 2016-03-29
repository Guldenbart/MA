package generator;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

/**
 * class that wraps everything we need to know about a method scope
 * for code generation.
 * Each GeneratorScope object holds all {@link GeneratorMethod} objects that
 * represent a method that belongs to the scope this object represents.
 * 
 * @author Daniel Fritz
 * @see GeneratorMethod
 * @see ScopeNode
 */
 // TODO Frage: ^ zu kompliziert oder wäre es kürzer zu ungenau/falsch?
public final class GeneratorScope {

	/**
	 * name of the interface that gives this scope its name.
	 */
	private String interfaceName;
	
	/**
	 * collection of all GeneratorMethod objects which belong to this scope.
	 */
	private ArrayList<GeneratorMethod> methods;
	
	
	/**
	 * constructor that initializes <code>interfaceName</code> and
	 * <code>methods</code> of the object.
	 * @param iName interface name.
	 * @param list ArrayList of GeneratorMethod objects.
	 */
	public GeneratorScope(final String iName,
			final ArrayList<GeneratorMethod> list) {
		interfaceName = iName;
		methods = list;
	}
	
	/**
	 * gets the name of the interface.
	 * @return interface name
	 */
	public String getInterfaceName() {
		return interfaceName;
	}
	
	/**
	 * gets the list of all GeneratorMethod objects. 
	 * @return ArrayList of GeneratorMethod objects
	 */
	public ArrayList<GeneratorMethod> getAllMethods() {
		return methods;
	}
	
	/**
	 * gets a list of all <code>GeneratorMethod</code> objects with no argument.
	 * @return list of all <code>GeneratorMethod</code> objects without
	 * argument.
	 * @see AMethodNode
	 */
	public ArrayList<GeneratorMethod> getMethods() {
		ArrayList<GeneratorMethod> mList = new ArrayList<GeneratorMethod>();
		
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
	public ArrayList<GeneratorMethod> getSimpleMethods() {
		ArrayList<GeneratorMethod> smList = new ArrayList<GeneratorMethod>();
		
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
	public ArrayList<GeneratorMethod> getNestedMethods() {
		ArrayList<GeneratorMethod> nmList = new ArrayList<GeneratorMethod>();
		
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
		return basePath.resolve(Paths.get("ScopeNode" + interfaceName + ".java"));
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		
		sb.append("Scope " + this.interfaceName + ":\n");
		for (GeneratorMethod gm : methods) {
			sb.append("\t" + gm.toString() + "\n");
		}
		
		return sb.toString();
	}
}
