package generator;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

/**
 * class that wraps everything we need to know for code generation
 * about a method scope.
 * Each GeneratorScope object holds all {@link GeneratorMethod} objects that
 * represent a method that belongs to the scope this object represents.
 * 
 * @author Daniel Fritz
 * @see GeneratorMethod
 * @see Lope
 */
 // TODO Frage: ^ zu kompliziert oder wäre es kürzer zu ungenau/falsch?
public final class GeneratorScope {

	/**
	 * name of the interface that gives this scope its name.
	 */
	private String interfaceName;
	
	/**
	 * ArrayList of all GeneratorMethod objects which belong to this scope.
	 */
	private ArrayList<GeneratorMethod> methods;
	
	
	/**
	 * constructor that initializes interfaceName and methods of the object.
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
	 * gets a list of all GeneratorMethods object of type 'method'.
	 * @return ArrayList of all 'method' type GeneratorMethod objects.
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
	 * gets a list of all GeneratorMethods object of type 'simpleMethod'.
	 * @return ArrayList of all 'simpleMethod' type GeneratorMethod objects.
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
	 * gets a list of all GeneratorMethods object of type 'nestedMethod'.
	 * @return ArrayList of all 'nestedMethod' type GeneratorMethod objects.
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
	 * Composes the file path for a Lope file (see {@link Lope}) that is
	 * derived from the corresponding GeneratorScope object.
	 * @param basePath path that the file path is based on.
	 * @return composed path object
	 */
	public Path getLopeClassPath(final Path basePath) {
		return basePath.resolve(Paths.get("Lope" + interfaceName + ".java"));
	}
}
