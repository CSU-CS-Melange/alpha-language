/**
 */
package alpha.model;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Standard Equation</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link alpha.model.StandardEquation#getVariable <em>Variable</em>}</li>
 *   <li>{@link alpha.model.StandardEquation#getIndexNames <em>Index Names</em>}</li>
 *   <li>{@link alpha.model.StandardEquation#getExpr <em>Expr</em>}</li>
 * </ul>
 *
 * @see alpha.model.ModelPackage#getStandardEquation()
 * @model
 * @generated
 */
public interface StandardEquation extends Equation {
	/**
	 * Returns the value of the '<em><b>Variable</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Variable</em>' reference.
	 * @see #setVariable(Variable)
	 * @see alpha.model.ModelPackage#getStandardEquation_Variable()
	 * @model
	 * @generated
	 */
	Variable getVariable();

	/**
	 * Sets the value of the '{@link alpha.model.StandardEquation#getVariable <em>Variable</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Variable</em>' reference.
	 * @see #getVariable()
	 * @generated
	 */
	void setVariable(Variable value);

	/**
	 * Returns the value of the '<em><b>Index Names</b></em>' attribute list.
	 * The list contents are of type {@link java.lang.String}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Index Names</em>' attribute list.
	 * @see alpha.model.ModelPackage#getStandardEquation_IndexNames()
	 * @model unique="false" dataType="alpha.model.String"
	 * @generated
	 */
	EList<String> getIndexNames();

	/**
	 * Returns the value of the '<em><b>Expr</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Expr</em>' containment reference.
	 * @see #setExpr(AlphaExpression)
	 * @see alpha.model.ModelPackage#getStandardEquation_Expr()
	 * @model containment="true"
	 * @generated
	 */
	AlphaExpression getExpr();

	/**
	 * Sets the value of the '{@link alpha.model.StandardEquation#getExpr <em>Expr</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Expr</em>' containment reference.
	 * @see #getExpr()
	 * @generated
	 */
	void setExpr(AlphaExpression value);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model kind="operation" dataType="alpha.model.String" unique="false"
	 * @generated
	 */
	String getName();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model visitorUnique="false"
	 * @generated
	 */
	void accept(AlphaVisitor visitor);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model dataType="alpha.model.String" unique="false"
	 * @generated
	 */
	String toString();

} // StandardEquation
