/**
 */
package alpha.codegen;

import fr.irisa.cairn.jnimap.isl.ISLSet;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Memory Allocation</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link alpha.codegen.MemoryAllocation#getVariable <em>Variable</em>}</li>
 *   <li>{@link alpha.codegen.MemoryAllocation#getDomain <em>Domain</em>}</li>
 * </ul>
 *
 * @see alpha.codegen.CodegenPackage#getMemoryAllocation()
 * @model
 * @generated
 */
public interface MemoryAllocation extends Node, Visitable {
	/**
	 * Returns the value of the '<em><b>Variable</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Variable</em>' reference.
	 * @see #setVariable(ArrayVariable)
	 * @see alpha.codegen.CodegenPackage#getMemoryAllocation_Variable()
	 * @model
	 * @generated
	 */
	ArrayVariable getVariable();

	/**
	 * Sets the value of the '{@link alpha.codegen.MemoryAllocation#getVariable <em>Variable</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Variable</em>' reference.
	 * @see #getVariable()
	 * @generated
	 */
	void setVariable(ArrayVariable value);

	/**
	 * Returns the value of the '<em><b>Domain</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Domain</em>' attribute.
	 * @see #setDomain(ISLSet)
	 * @see alpha.codegen.CodegenPackage#getMemoryAllocation_Domain()
	 * @model unique="false" dataType="alpha.codegen.ISLSet"
	 * @generated
	 */
	ISLSet getDomain();

	/**
	 * Sets the value of the '{@link alpha.codegen.MemoryAllocation#getDomain <em>Domain</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Domain</em>' attribute.
	 * @see #getDomain()
	 * @generated
	 */
	void setDomain(ISLSet value);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model visitorUnique="false"
	 * @generated
	 */
	void accept(Visitor visitor);

} // MemoryAllocation
