/**
 */
package alpha.codegen;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Visitable</b></em>'.
 * <!-- end-user-doc -->
 *
 *
 * @see alpha.codegen.CodegenPackage#getVisitable()
 * @model interface="true" abstract="true"
 * @generated
 */
public interface Visitable extends EObject {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model visitorUnique="false"
	 * @generated
	 */
	void accept(Visitor visitor);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model unique="false"
	 * @generated
	 */
	String toSString();

} // Visitable
