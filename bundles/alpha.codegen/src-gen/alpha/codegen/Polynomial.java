/**
 */
package alpha.codegen;

import fr.irisa.cairn.jnimap.isl.ISLPWQPolynomial;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Polynomial</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link alpha.codegen.Polynomial#getIslPolynomial <em>Isl Polynomial</em>}</li>
 * </ul>
 *
 * @see alpha.codegen.CodegenPackage#getPolynomial()
 * @model
 * @generated
 */
public interface Polynomial extends EObject {
	/**
	 * Returns the value of the '<em><b>Isl Polynomial</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Isl Polynomial</em>' attribute.
	 * @see #setIslPolynomial(ISLPWQPolynomial)
	 * @see alpha.codegen.CodegenPackage#getPolynomial_IslPolynomial()
	 * @model unique="false" dataType="alpha.codegen.ISLPWQPolynomial"
	 * @generated
	 */
	ISLPWQPolynomial getIslPolynomial();

	/**
	 * Sets the value of the '{@link alpha.codegen.Polynomial#getIslPolynomial <em>Isl Polynomial</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Isl Polynomial</em>' attribute.
	 * @see #getIslPolynomial()
	 * @generated
	 */
	void setIslPolynomial(ISLPWQPolynomial value);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model unique="false"
	 * @generated
	 */
	String toCString();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model unique="false" variableNameUnique="false"
	 * @generated
	 */
	String toCString(String variableName);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model unique="false"
	 * @generated
	 */
	EList<String> params();

} // Polynomial
