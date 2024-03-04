/**
 */
package alpha.codegen;

import fr.irisa.cairn.jnimap.isl.ISLDimType;
import fr.irisa.cairn.jnimap.isl.ISLTerm;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Polynomial Term</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link alpha.codegen.PolynomialTerm#getPolynomialPiece <em>Polynomial Piece</em>}</li>
 *   <li>{@link alpha.codegen.PolynomialTerm#getIslTerm <em>Isl Term</em>}</li>
 * </ul>
 *
 * @see alpha.codegen.CodegenPackage#getPolynomialTerm()
 * @model
 * @generated
 */
public interface PolynomialTerm extends PolynomialNode, PolynomialVisitable {
	/**
	 * Returns the value of the '<em><b>Polynomial Piece</b></em>' container reference.
	 * It is bidirectional and its opposite is '{@link alpha.codegen.PolynomialPiece#getTerms <em>Terms</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Polynomial Piece</em>' container reference.
	 * @see #setPolynomialPiece(PolynomialPiece)
	 * @see alpha.codegen.CodegenPackage#getPolynomialTerm_PolynomialPiece()
	 * @see alpha.codegen.PolynomialPiece#getTerms
	 * @model opposite="terms" transient="false"
	 * @generated
	 */
	PolynomialPiece getPolynomialPiece();

	/**
	 * Sets the value of the '{@link alpha.codegen.PolynomialTerm#getPolynomialPiece <em>Polynomial Piece</em>}' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Polynomial Piece</em>' container reference.
	 * @see #getPolynomialPiece()
	 * @generated
	 */
	void setPolynomialPiece(PolynomialPiece value);

	/**
	 * Returns the value of the '<em><b>Isl Term</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Isl Term</em>' attribute.
	 * @see #setIslTerm(ISLTerm)
	 * @see alpha.codegen.CodegenPackage#getPolynomialTerm_IslTerm()
	 * @model unique="false" dataType="alpha.codegen.ISLTerm"
	 * @generated
	 */
	ISLTerm getIslTerm();

	/**
	 * Sets the value of the '{@link alpha.codegen.PolynomialTerm#getIslTerm <em>Isl Term</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Isl Term</em>' attribute.
	 * @see #getIslTerm()
	 * @generated
	 */
	void setIslTerm(ISLTerm value);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model unique="false"
	 * @generated
	 */
	String value();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model unique="false"
	 * @generated
	 */
	EList<String> values();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model unique="false"
	 * @generated
	 */
	String coefficient();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model unique="false" iUnique="false"
	 * @generated
	 */
	String exponent(int i);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model unique="false"
	 * @generated
	 */
	EList<Integer> exponents();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model unique="false"
	 * @generated
	 */
	int dimParam();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model unique="false" dim_typeDataType="alpha.codegen.ISLDimType" dim_typeUnique="false"
	 * @generated
	 */
	int dim(ISLDimType dim_type);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model unique="false"
	 * @generated
	 */
	EList<String> paramNames();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model visitorUnique="false"
	 * @generated
	 */
	void accept(PolynomialVisitor visitor);

} // PolynomialTerm
