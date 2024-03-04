/**
 */
package alpha.codegen;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Memory Macro</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link alpha.codegen.MemoryMacro#getFunction <em>Function</em>}</li>
 *   <li>{@link alpha.codegen.MemoryMacro#getAllocation <em>Allocation</em>}</li>
 *   <li>{@link alpha.codegen.MemoryMacro#getVariable <em>Variable</em>}</li>
 * </ul>
 *
 * @see alpha.codegen.CodegenPackage#getMemoryMacro()
 * @model
 * @generated
 */
public interface MemoryMacro extends AbstractMemoryMacro {
	/**
	 * Returns the value of the '<em><b>Function</b></em>' container reference.
	 * It is bidirectional and its opposite is '{@link alpha.codegen.Function#getMemoryMacros <em>Memory Macros</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Function</em>' container reference.
	 * @see #setFunction(Function)
	 * @see alpha.codegen.CodegenPackage#getMemoryMacro_Function()
	 * @see alpha.codegen.Function#getMemoryMacros
	 * @model opposite="memoryMacros" transient="false"
	 * @generated
	 */
	Function getFunction();

	/**
	 * Sets the value of the '{@link alpha.codegen.MemoryMacro#getFunction <em>Function</em>}' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Function</em>' container reference.
	 * @see #getFunction()
	 * @generated
	 */
	void setFunction(Function value);

	/**
	 * Returns the value of the '<em><b>Allocation</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Allocation</em>' reference.
	 * @see #setAllocation(MemoryAllocation)
	 * @see alpha.codegen.CodegenPackage#getMemoryMacro_Allocation()
	 * @model
	 * @generated
	 */
	MemoryAllocation getAllocation();

	/**
	 * Sets the value of the '{@link alpha.codegen.MemoryMacro#getAllocation <em>Allocation</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Allocation</em>' reference.
	 * @see #getAllocation()
	 * @generated
	 */
	void setAllocation(MemoryAllocation value);

	/**
	 * Returns the value of the '<em><b>Variable</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Variable</em>' reference.
	 * @see #setVariable(ArrayVariable)
	 * @see alpha.codegen.CodegenPackage#getMemoryMacro_Variable()
	 * @model
	 * @generated
	 */
	ArrayVariable getVariable();

	/**
	 * Sets the value of the '{@link alpha.codegen.MemoryMacro#getVariable <em>Variable</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Variable</em>' reference.
	 * @see #getVariable()
	 * @generated
	 */
	void setVariable(ArrayVariable value);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model visitorUnique="false"
	 * @generated
	 */
	void accept(Visitor visitor);

} // MemoryMacro
