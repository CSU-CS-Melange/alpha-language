/**
 */
package alpha.model;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>External Multi Arg Expression</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link alpha.model.ExternalMultiArgExpression#getExternalFunction <em>External Function</em>}</li>
 * </ul>
 *
 * @see alpha.model.ModelPackage#getExternalMultiArgExpression()
 * @model
 * @generated
 */
public interface ExternalMultiArgExpression extends MultiArgExpression {
	/**
	 * Returns the value of the '<em><b>External Function</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>External Function</em>' reference.
	 * @see #setExternalFunction(ExternalFunction)
	 * @see alpha.model.ModelPackage#getExternalMultiArgExpression_ExternalFunction()
	 * @model
	 * @generated
	 */
	ExternalFunction getExternalFunction();

	/**
	 * Sets the value of the '{@link alpha.model.ExternalMultiArgExpression#getExternalFunction <em>External Function</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>External Function</em>' reference.
	 * @see #getExternalFunction()
	 * @generated
	 */
	void setExternalFunction(ExternalFunction value);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model visitorUnique="false"
	 * @generated
	 */
	void accept(AlphaExpressionVisitor visitor);

} // ExternalMultiArgExpression
