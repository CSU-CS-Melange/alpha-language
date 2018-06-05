/**
 */
package alpha.model;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Fuzzy Function In Array Notation</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link alpha.model.FuzzyFunctionInArrayNotation#getArrayNotation <em>Array Notation</em>}</li>
 * </ul>
 *
 * @see alpha.model.ModelPackage#getFuzzyFunctionInArrayNotation()
 * @model
 * @generated
 */
public interface FuzzyFunctionInArrayNotation extends FuzzyFunction {
	/**
	 * Returns the value of the '<em><b>Array Notation</b></em>' attribute list.
	 * The list contents are of type {@link java.lang.String}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Array Notation</em>' attribute list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Array Notation</em>' attribute list.
	 * @see alpha.model.ModelPackage#getFuzzyFunctionInArrayNotation_ArrayNotation()
	 * @model unique="false"
	 * @generated
	 */
	EList<String> getArrayNotation();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model unique="false"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel body='&lt;%java.lang.String%&gt; _join = &lt;%org.eclipse.xtext.xbase.lib.IterableExtensions%&gt;.join(this.getArrayNotation(), \",\");\n&lt;%java.lang.String%&gt; _plus = (\"[\" + _join);\nreturn (_plus + \"]\");'"
	 * @generated
	 */
	String plainToString();

} // FuzzyFunctionInArrayNotation