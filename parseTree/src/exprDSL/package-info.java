/**
 * This package contains all interfaces for a primitive DSL that describes
 * simple arithmetic expressions.
 * <p>
 * With this language, precedence rules - other than parantheses - are not
 * covered.
 * Only the four basic arithmetic operations are implemented.
 * <p>
 * Every expression has to start with <code>expr</code> and end with
 * <code>end</code>. 
 * After you called the initial <code>expr</code>, you can chain any amount of
 * {@link Operation}s to it. If you want a nested expression (inside of
 * <code>expr</code> or an operation), just start again with
 * <code>TreeBuilder.begin().expr()</code>.
 * 
 * @author Daniel Fritz
 */

package exprDSL;