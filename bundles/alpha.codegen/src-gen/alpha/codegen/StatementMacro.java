/**
 */
package alpha.codegen;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Statement Macro</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link alpha.codegen.StatementMacro#getFunctionBody <em>Function Body</em>}</li>
 * </ul>
 *
 * @see alpha.codegen.CodegenPackage#getStatementMacro()
 * @model
 * @generated
 */
public interface StatementMacro extends Macro {
	/**
	 * Returns the value of the '<em><b>Function Body</b></em>' container reference.
	 * It is bidirectional and its opposite is '{@link alpha.codegen.FunctionBody#getStatementMacros <em>Statement Macros</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Function Body</em>' container reference.
	 * @see #setFunctionBody(FunctionBody)
	 * @see alpha.codegen.CodegenPackage#getStatementMacro_FunctionBody()
	 * @see alpha.codegen.FunctionBody#getStatementMacros
	 * @model opposite="statementMacros" transient="false"
	 * @generated
	 */
	FunctionBody getFunctionBody();

	/**
	 * Sets the value of the '{@link alpha.codegen.StatementMacro#getFunctionBody <em>Function Body</em>}' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Function Body</em>' container reference.
	 * @see #getFunctionBody()
	 * @generated
	 */
	void setFunctionBody(FunctionBody value);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model visitorUnique="false"
	 * @generated
	 */
	void accept(Visitor visitor);

} // StatementMacro
