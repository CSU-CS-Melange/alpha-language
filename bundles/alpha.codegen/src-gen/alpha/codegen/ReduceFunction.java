/**
 */
package alpha.codegen;

import alpha.model.ReduceExpression;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Reduce Function</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link alpha.codegen.ReduceFunction#getReduceExpr <em>Reduce Expr</em>}</li>
 *   <li>{@link alpha.codegen.ReduceFunction#getReduceVar <em>Reduce Var</em>}</li>
 *   <li>{@link alpha.codegen.ReduceFunction#getMacroName <em>Macro Name</em>}</li>
 * </ul>
 *
 * @see alpha.codegen.CodegenPackage#getReduceFunction()
 * @model
 * @generated
 */
public interface ReduceFunction extends Function {
	/**
	 * Returns the value of the '<em><b>Reduce Expr</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Reduce Expr</em>' reference.
	 * @see #setReduceExpr(ReduceExpression)
	 * @see alpha.codegen.CodegenPackage#getReduceFunction_ReduceExpr()
	 * @model
	 * @generated
	 */
	ReduceExpression getReduceExpr();

	/**
	 * Sets the value of the '{@link alpha.codegen.ReduceFunction#getReduceExpr <em>Reduce Expr</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Reduce Expr</em>' reference.
	 * @see #getReduceExpr()
	 * @generated
	 */
	void setReduceExpr(ReduceExpression value);

	/**
	 * Returns the value of the '<em><b>Reduce Var</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Reduce Var</em>' reference.
	 * @see #setReduceVar(BaseVariable)
	 * @see alpha.codegen.CodegenPackage#getReduceFunction_ReduceVar()
	 * @model
	 * @generated
	 */
	BaseVariable getReduceVar();

	/**
	 * Sets the value of the '{@link alpha.codegen.ReduceFunction#getReduceVar <em>Reduce Var</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Reduce Var</em>' reference.
	 * @see #getReduceVar()
	 * @generated
	 */
	void setReduceVar(BaseVariable value);

	/**
	 * Returns the value of the '<em><b>Macro Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Macro Name</em>' attribute.
	 * @see #setMacroName(String)
	 * @see alpha.codegen.CodegenPackage#getReduceFunction_MacroName()
	 * @model unique="false"
	 * @generated
	 */
	String getMacroName();

	/**
	 * Sets the value of the '{@link alpha.codegen.ReduceFunction#getMacroName <em>Macro Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Macro Name</em>' attribute.
	 * @see #getMacroName()
	 * @generated
	 */
	void setMacroName(String value);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model visitorUnique="false"
	 * @generated
	 */
	void accept(Visitor visitor);

} // ReduceFunction
