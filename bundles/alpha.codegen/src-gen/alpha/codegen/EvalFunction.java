/**
 */
package alpha.codegen;

import alpha.model.StandardEquation;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Eval Function</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link alpha.codegen.EvalFunction#getVariable <em>Variable</em>}</li>
 *   <li>{@link alpha.codegen.EvalFunction#getFlagVariable <em>Flag Variable</em>}</li>
 *   <li>{@link alpha.codegen.EvalFunction#getEquation <em>Equation</em>}</li>
 * </ul>
 *
 * @see alpha.codegen.CodegenPackage#getEvalFunction()
 * @model
 * @generated
 */
public interface EvalFunction extends Function {
	/**
	 * Returns the value of the '<em><b>Variable</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Variable</em>' reference.
	 * @see #setVariable(ArrayVariable)
	 * @see alpha.codegen.CodegenPackage#getEvalFunction_Variable()
	 * @model
	 * @generated
	 */
	ArrayVariable getVariable();

	/**
	 * Sets the value of the '{@link alpha.codegen.EvalFunction#getVariable <em>Variable</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Variable</em>' reference.
	 * @see #getVariable()
	 * @generated
	 */
	void setVariable(ArrayVariable value);

	/**
	 * Returns the value of the '<em><b>Flag Variable</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Flag Variable</em>' reference.
	 * @see #setFlagVariable(ArrayVariable)
	 * @see alpha.codegen.CodegenPackage#getEvalFunction_FlagVariable()
	 * @model
	 * @generated
	 */
	ArrayVariable getFlagVariable();

	/**
	 * Sets the value of the '{@link alpha.codegen.EvalFunction#getFlagVariable <em>Flag Variable</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Flag Variable</em>' reference.
	 * @see #getFlagVariable()
	 * @generated
	 */
	void setFlagVariable(ArrayVariable value);

	/**
	 * Returns the value of the '<em><b>Equation</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Equation</em>' reference.
	 * @see #setEquation(StandardEquation)
	 * @see alpha.codegen.CodegenPackage#getEvalFunction_Equation()
	 * @model
	 * @generated
	 */
	StandardEquation getEquation();

	/**
	 * Sets the value of the '{@link alpha.codegen.EvalFunction#getEquation <em>Equation</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Equation</em>' reference.
	 * @see #getEquation()
	 * @generated
	 */
	void setEquation(StandardEquation value);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model visitorUnique="false"
	 * @generated
	 */
	void accept(Visitor visitor);

} // EvalFunction
