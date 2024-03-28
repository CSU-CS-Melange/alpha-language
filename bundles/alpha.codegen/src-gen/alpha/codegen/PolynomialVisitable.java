/**
 */
package alpha.codegen;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Polynomial Visitable</b></em>'.
 * <!-- end-user-doc -->
 *
 *
 * @see alpha.codegen.CodegenPackage#getPolynomialVisitable()
 * @model interface="true" abstract="true"
 * @generated
 */
public interface PolynomialVisitable extends EObject {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model visitorUnique="false"
	 * @generated
	 */
	void accept(PolynomialVisitor visitor);

} // PolynomialVisitable
