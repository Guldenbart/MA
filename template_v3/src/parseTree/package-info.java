/**
 * package that contains non-generated classes that are needed to build a {@link ParseTree}.
 * 
 * The ParseTree structure is used to store all information about an expression.
 * One ParseTree object holds a list of {@link AScopeNode} objects and every AScopeNode object
 * holds a list of {@link AMethodNode} objects. That way, we store the information which method 
 * - belonging to which scope - was called in which order. We obviously need that information to
 * evaluate the expression.
 * 
 * TODO more
 * 
 * @author Daniel Fritz
 *
 */
package parseTree;