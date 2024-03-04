/**
 */
package alpha.codegen;

import fr.irisa.cairn.jnimap.isl.ISLQPolynomialPiece;
import fr.irisa.cairn.jnimap.isl.ISLSet;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Polynomial Piece</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link alpha.codegen.PolynomialPiece#getPolynomial <em>Polynomial</em>}</li>
 *   <li>{@link alpha.codegen.PolynomialPiece#getTerms <em>Terms</em>}</li>
 *   <li>{@link alpha.codegen.PolynomialPiece#getIslPiece <em>Isl Piece</em>}</li>
 * </ul>
 *
 * @see alpha.codegen.CodegenPackage#getPolynomialPiece()
 * @model
 * @generated
 */
public interface PolynomialPiece extends PolynomialNode, PolynomialVisitable {
	/**
	 * Returns the value of the '<em><b>Polynomial</b></em>' container reference.
	 * It is bidirectional and its opposite is '{@link alpha.codegen.Polynomial#getPieces <em>Pieces</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Polynomial</em>' container reference.
	 * @see #setPolynomial(Polynomial)
	 * @see alpha.codegen.CodegenPackage#getPolynomialPiece_Polynomial()
	 * @see alpha.codegen.Polynomial#getPieces
	 * @model opposite="pieces" transient="false"
	 * @generated
	 */
	Polynomial getPolynomial();

	/**
	 * Sets the value of the '{@link alpha.codegen.PolynomialPiece#getPolynomial <em>Polynomial</em>}' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Polynomial</em>' container reference.
	 * @see #getPolynomial()
	 * @generated
	 */
	void setPolynomial(Polynomial value);

	/**
	 * Returns the value of the '<em><b>Terms</b></em>' containment reference list.
	 * The list contents are of type {@link alpha.codegen.PolynomialTerm}.
	 * It is bidirectional and its opposite is '{@link alpha.codegen.PolynomialTerm#getPolynomialPiece <em>Polynomial Piece</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Terms</em>' containment reference list.
	 * @see alpha.codegen.CodegenPackage#getPolynomialPiece_Terms()
	 * @see alpha.codegen.PolynomialTerm#getPolynomialPiece
	 * @model opposite="polynomialPiece" containment="true"
	 * @generated
	 */
	EList<PolynomialTerm> getTerms();

	/**
	 * Returns the value of the '<em><b>Isl Piece</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Isl Piece</em>' attribute.
	 * @see #setIslPiece(ISLQPolynomialPiece)
	 * @see alpha.codegen.CodegenPackage#getPolynomialPiece_IslPiece()
	 * @model unique="false" dataType="alpha.codegen.ISLQPolynomialPiece"
	 * @generated
	 */
	ISLQPolynomialPiece getIslPiece();

	/**
	 * Sets the value of the '{@link alpha.codegen.PolynomialPiece#getIslPiece <em>Isl Piece</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Isl Piece</em>' attribute.
	 * @see #getIslPiece()
	 * @generated
	 */
	void setIslPiece(ISLQPolynomialPiece value);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model kind="operation" dataType="alpha.codegen.ISLSet" unique="false"
	 * @generated
	 */
	ISLSet getSet();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model visitorUnique="false"
	 * @generated
	 */
	void accept(PolynomialVisitor visitor);

} // PolynomialPiece
