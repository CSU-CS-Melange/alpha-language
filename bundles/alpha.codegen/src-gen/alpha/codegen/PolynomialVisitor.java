/**
 */
package alpha.codegen;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Polynomial Visitor</b></em>'.
 * <!-- end-user-doc -->
 *
 *
 * @see alpha.codegen.CodegenPackage#getPolynomialVisitor()
 * @model
 * @generated
 */
public interface PolynomialVisitor extends EObject {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model pUnique="false"
	 * @generated
	 */
	void visitPolynomial(Polynomial p);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model ppUnique="false"
	 * @generated
	 */
	void visitPolynomialPiece(PolynomialPiece pp);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model ptUnique="false"
	 * @generated
	 */
	void visitPolynomialTerm(PolynomialTerm pt);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model pUnique="false"
	 * @generated
	 */
	void inPolynomial(Polynomial p);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model ppUnique="false"
	 * @generated
	 */
	void inPolynomialPiece(PolynomialPiece pp);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model ptUnique="false"
	 * @generated
	 */
	void inPolynomialTerm(PolynomialTerm pt);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model pUnique="false"
	 * @generated
	 */
	void outPolynomial(Polynomial p);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model ppUnique="false"
	 * @generated
	 */
	void outPolynomialPiece(PolynomialPiece pp);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model ptUnique="false"
	 * @generated
	 */
	void outPolynomialTerm(PolynomialTerm pt);

} // PolynomialVisitor
