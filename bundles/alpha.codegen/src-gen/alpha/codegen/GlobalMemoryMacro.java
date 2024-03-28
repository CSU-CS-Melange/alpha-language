/**
 */
package alpha.codegen;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Global Memory Macro</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link alpha.codegen.GlobalMemoryMacro#getVariable <em>Variable</em>}</li>
 * </ul>
 *
 * @see alpha.codegen.CodegenPackage#getGlobalMemoryMacro()
 * @model
 * @generated
 */
public interface GlobalMemoryMacro extends AbstractMemoryMacro {
	/**
	 * Returns the value of the '<em><b>Variable</b></em>' container reference.
	 * It is bidirectional and its opposite is '{@link alpha.codegen.GlobalVariable#getMemoryMacro <em>Memory Macro</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Variable</em>' container reference.
	 * @see #setVariable(GlobalVariable)
	 * @see alpha.codegen.CodegenPackage#getGlobalMemoryMacro_Variable()
	 * @see alpha.codegen.GlobalVariable#getMemoryMacro
	 * @model opposite="memoryMacro" transient="false"
	 * @generated
	 */
	GlobalVariable getVariable();

	/**
	 * Sets the value of the '{@link alpha.codegen.GlobalMemoryMacro#getVariable <em>Variable</em>}' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Variable</em>' container reference.
	 * @see #getVariable()
	 * @generated
	 */
	void setVariable(GlobalVariable value);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model visitorUnique="false"
	 * @generated
	 */
	void accept(Visitor visitor);

} // GlobalMemoryMacro
