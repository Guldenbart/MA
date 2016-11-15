/**
 * This package includes all classes that are needed for code generation via StringTemplate.
 * All StringTemplate files can be found in the 'templates' package.
 * 
 * All information necessary for code generation is read from the interfaces that define the DSL
 * and wrapped into {@link GeneratorParseTree}, {@link GeneratorScope} and {@link GeneratorMethod}
 * objects. Afterwards, the template files are fed with that information.
 * 
 * @see {@link http://www.stringtemplate.org/}
 * @author Daniel Fritz
 */
package generator;