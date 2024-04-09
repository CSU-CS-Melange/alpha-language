/**
 */
package alpha.codegen;

import alpha.model.Variable;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Global Variable</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link alpha.codegen.GlobalVariable#getProgram <em>Program</em>}</li>
 *   <li>{@link alpha.codegen.GlobalVariable#getMemoryMacro <em>Memory Macro</em>}</li>
 *   <li>{@link alpha.codegen.GlobalVariable#getAlphaVariable <em>Alpha Variable</em>}</li>
 *   <li>{@link alpha.codegen.GlobalVariable#getType <em>Type</em>}</li>
 *   <li>{@link alpha.codegen.GlobalVariable#getNumDims <em>Num Dims</em>}</li>
 *   <li>{@link alpha.codegen.GlobalVariable#isFlagVariable <em>Flag Variable</em>}</li>
 * </ul>
 *
 * @see alpha.codegen.CodegenPackage#getGlobalVariable()
 * @model
 * @generated
 */
public interface GlobalVariable extends BaseVariable {
	/**
	 * Returns the value of the '<em><b>Program</b></em>' container reference.
	 * It is bidirectional and its opposite is '{@link alpha.codegen.Program#getGlobalVariables <em>Global Variables</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Program</em>' container reference.
	 * @see #setProgram(Program)
	 * @see alpha.codegen.CodegenPackage#getGlobalVariable_Program()
	 * @see alpha.codegen.Program#getGlobalVariables
	 * @model opposite="globalVariables" transient="false"
	 * @generated
	 */
	Program getProgram();

	/**
	 * Sets the value of the '{@link alpha.codegen.GlobalVariable#getProgram <em>Program</em>}' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Program</em>' container reference.
	 * @see #getProgram()
	 * @generated
	 */
	void setProgram(Program value);

	/**
	 * Returns the value of the '<em><b>Memory Macro</b></em>' containment reference.
	 * It is bidirectional and its opposite is '{@link alpha.codegen.GlobalMemoryMacro#getVariable <em>Variable</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Memory Macro</em>' containment reference.
	 * @see #setMemoryMacro(GlobalMemoryMacro)
	 * @see alpha.codegen.CodegenPackage#getGlobalVariable_MemoryMacro()
	 * @see alpha.codegen.GlobalMemoryMacro#getVariable
	 * @model opposite="variable" containment="true"
	 * @generated
	 */
	GlobalMemoryMacro getMemoryMacro();

	/**
	 * Sets the value of the '{@link alpha.codegen.GlobalVariable#getMemoryMacro <em>Memory Macro</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Memory Macro</em>' containment reference.
	 * @see #getMemoryMacro()
	 * @generated
	 */
	void setMemoryMacro(GlobalMemoryMacro value);

	/**
	 * Returns the value of the '<em><b>Alpha Variable</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Alpha Variable</em>' reference.
	 * @see #setAlphaVariable(Variable)
	 * @see alpha.codegen.CodegenPackage#getGlobalVariable_AlphaVariable()
	 * @model
	 * @generated
	 */
	Variable getAlphaVariable();

	/**
	 * Sets the value of the '{@link alpha.codegen.GlobalVariable#getAlphaVariable <em>Alpha Variable</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Alpha Variable</em>' reference.
	 * @see #getAlphaVariable()
	 * @generated
	 */
	void setAlphaVariable(Variable value);

	/**
	 * Returns the value of the '<em><b>Type</b></em>' attribute.
	 * The literals are from the enumeration {@link alpha.codegen.VariableType}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Type</em>' attribute.
	 * @see alpha.codegen.VariableType
	 * @see #setType(VariableType)
	 * @see alpha.codegen.CodegenPackage#getGlobalVariable_Type()
	 * @model unique="false"
	 * @generated
	 */
	VariableType getType();

	/**
	 * Sets the value of the '{@link alpha.codegen.GlobalVariable#getType <em>Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Type</em>' attribute.
	 * @see alpha.codegen.VariableType
	 * @see #getType()
	 * @generated
	 */
	void setType(VariableType value);

	/**
	 * Returns the value of the '<em><b>Num Dims</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Num Dims</em>' attribute.
	 * @see #setNumDims(int)
	 * @see alpha.codegen.CodegenPackage#getGlobalVariable_NumDims()
	 * @model unique="false"
	 * @generated
	 */
	int getNumDims();

	/**
	 * Sets the value of the '{@link alpha.codegen.GlobalVariable#getNumDims <em>Num Dims</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Num Dims</em>' attribute.
	 * @see #getNumDims()
	 * @generated
	 */
	void setNumDims(int value);

	/**
	 * Returns the value of the '<em><b>Flag Variable</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Flag Variable</em>' attribute.
	 * @see #setFlagVariable(boolean)
	 * @see alpha.codegen.CodegenPackage#getGlobalVariable_FlagVariable()
	 * @model unique="false"
	 * @generated
	 */
	boolean isFlagVariable();

	/**
	 * Sets the value of the '{@link alpha.codegen.GlobalVariable#isFlagVariable <em>Flag Variable</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Flag Variable</em>' attribute.
	 * @see #isFlagVariable()
	 * @generated
	 */
	void setFlagVariable(boolean value);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model unique="false"
	 * @generated
	 */
	boolean hasAlphaVariable();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model unique="false"
	 * @generated
	 */
	boolean hasMemoryMacro();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model unique="false"
	 * @generated
	 */
	String pointers();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model unique="false"
	 * @generated
	 */
	String dataType();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model unique="false"
	 * @generated
	 */
	String readName();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model unique="false"
	 * @generated
	 */
	EList<String> params();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model unique="false"
	 * @generated
	 */
	EList<String> indices();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model unique="false"
	 * @generated
	 */
	String identityAccess();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model unique="false"
	 * @generated
	 */
	String writeName();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model visitorUnique="false"
	 * @generated
	 */
	void accept(Visitor visitor);

} // GlobalVariable
