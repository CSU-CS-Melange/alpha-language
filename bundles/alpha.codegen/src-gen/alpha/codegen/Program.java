/**
 */
package alpha.codegen;

import alpha.model.AlphaSystem;
import alpha.model.Variable;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Program</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link alpha.codegen.Program#getSystem <em>System</em>}</li>
 *   <li>{@link alpha.codegen.Program#getIncludes <em>Includes</em>}</li>
 *   <li>{@link alpha.codegen.Program#getCommonMacros <em>Common Macros</em>}</li>
 *   <li>{@link alpha.codegen.Program#getGlobalVariables <em>Global Variables</em>}</li>
 *   <li>{@link alpha.codegen.Program#getFunctions <em>Functions</em>}</li>
 * </ul>
 *
 * @see alpha.codegen.CodegenPackage#getProgram()
 * @model
 * @generated
 */
public interface Program extends Node, Visitable {
	/**
	 * Returns the value of the '<em><b>System</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>System</em>' reference.
	 * @see #setSystem(AlphaSystem)
	 * @see alpha.codegen.CodegenPackage#getProgram_System()
	 * @model
	 * @generated
	 */
	AlphaSystem getSystem();

	/**
	 * Sets the value of the '{@link alpha.codegen.Program#getSystem <em>System</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>System</em>' reference.
	 * @see #getSystem()
	 * @generated
	 */
	void setSystem(AlphaSystem value);

	/**
	 * Returns the value of the '<em><b>Includes</b></em>' containment reference list.
	 * The list contents are of type {@link alpha.codegen.Include}.
	 * It is bidirectional and its opposite is '{@link alpha.codegen.Include#getProgram <em>Program</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Includes</em>' containment reference list.
	 * @see alpha.codegen.CodegenPackage#getProgram_Includes()
	 * @see alpha.codegen.Include#getProgram
	 * @model opposite="program" containment="true"
	 * @generated
	 */
	EList<Include> getIncludes();

	/**
	 * Returns the value of the '<em><b>Common Macros</b></em>' containment reference list.
	 * The list contents are of type {@link alpha.codegen.GlobalMacro}.
	 * It is bidirectional and its opposite is '{@link alpha.codegen.GlobalMacro#getProgram <em>Program</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Common Macros</em>' containment reference list.
	 * @see alpha.codegen.CodegenPackage#getProgram_CommonMacros()
	 * @see alpha.codegen.GlobalMacro#getProgram
	 * @model opposite="program" containment="true"
	 * @generated
	 */
	EList<GlobalMacro> getCommonMacros();

	/**
	 * Returns the value of the '<em><b>Global Variables</b></em>' containment reference list.
	 * The list contents are of type {@link alpha.codegen.GlobalVariable}.
	 * It is bidirectional and its opposite is '{@link alpha.codegen.GlobalVariable#getProgram <em>Program</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Global Variables</em>' containment reference list.
	 * @see alpha.codegen.CodegenPackage#getProgram_GlobalVariables()
	 * @see alpha.codegen.GlobalVariable#getProgram
	 * @model opposite="program" containment="true"
	 * @generated
	 */
	EList<GlobalVariable> getGlobalVariables();

	/**
	 * Returns the value of the '<em><b>Functions</b></em>' containment reference list.
	 * The list contents are of type {@link alpha.codegen.Function}.
	 * It is bidirectional and its opposite is '{@link alpha.codegen.Function#getProgram <em>Program</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Functions</em>' containment reference list.
	 * @see alpha.codegen.CodegenPackage#getProgram_Functions()
	 * @see alpha.codegen.Function#getProgram
	 * @model opposite="program" containment="true"
	 * @generated
	 */
	EList<Function> getFunctions();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model unique="false" alphaVarUnique="false"
	 * @generated
	 */
	GlobalVariable getGlobalVariable(Variable alphaVar);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model visitorUnique="false"
	 * @generated
	 */
	void accept(Visitor visitor);

} // Program
