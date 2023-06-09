/**
 */
package alpha.model;

import fr.irisa.cairn.jnimap.isl.ISLMap;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Fuzzy Variable Use</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link alpha.model.FuzzyVariableUse#getFuzzyIndex <em>Fuzzy Index</em>}</li>
 *   <li>{@link alpha.model.FuzzyVariableUse#getFuzzyVariable <em>Fuzzy Variable</em>}</li>
 * </ul>
 *
 * @see alpha.model.ModelPackage#getFuzzyVariableUse()
 * @model abstract="true"
 * @generated
 */
public interface FuzzyVariableUse extends AlphaNode {
	/**
	 * Returns the value of the '<em><b>Fuzzy Index</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Fuzzy Index</em>' attribute.
	 * @see #setFuzzyIndex(String)
	 * @see alpha.model.ModelPackage#getFuzzyVariableUse_FuzzyIndex()
	 * @model unique="false" dataType="alpha.model.String"
	 * @generated
	 */
	String getFuzzyIndex();

	/**
	 * Sets the value of the '{@link alpha.model.FuzzyVariableUse#getFuzzyIndex <em>Fuzzy Index</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Fuzzy Index</em>' attribute.
	 * @see #getFuzzyIndex()
	 * @generated
	 */
	void setFuzzyIndex(String value);

	/**
	 * Returns the value of the '<em><b>Fuzzy Variable</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Fuzzy Variable</em>' reference.
	 * @see #setFuzzyVariable(FuzzyVariable)
	 * @see alpha.model.ModelPackage#getFuzzyVariableUse_FuzzyVariable()
	 * @model
	 * @generated
	 */
	FuzzyVariable getFuzzyVariable();

	/**
	 * Sets the value of the '{@link alpha.model.FuzzyVariableUse#getFuzzyVariable <em>Fuzzy Variable</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Fuzzy Variable</em>' reference.
	 * @see #getFuzzyVariable()
	 * @generated
	 */
	void setFuzzyVariable(FuzzyVariable value);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model kind="operation" dataType="alpha.model.JNIISLMap" unique="false"
	 * @generated
	 */
	ISLMap getDependenceRelation();

} // FuzzyVariableUse
