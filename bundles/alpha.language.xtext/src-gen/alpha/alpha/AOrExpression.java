/**
 * generated by Xtext 2.13.0
 */
package alpha.alpha;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>AOr Expression</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link alpha.alpha.AOrExpression#getLeft <em>Left</em>}</li>
 *   <li>{@link alpha.alpha.AOrExpression#getOperator <em>Operator</em>}</li>
 *   <li>{@link alpha.alpha.AOrExpression#getRight <em>Right</em>}</li>
 * </ul>
 *
 * @see alpha.alpha.AlphaPackage#getAOrExpression()
 * @model
 * @generated
 */
public interface AOrExpression extends AAlphaExpression
{
  /**
   * Returns the value of the '<em><b>Left</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Left</em>' containment reference isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Left</em>' containment reference.
   * @see #setLeft(AAlphaExpression)
   * @see alpha.alpha.AlphaPackage#getAOrExpression_Left()
   * @model containment="true"
   * @generated
   */
  AAlphaExpression getLeft();

  /**
   * Sets the value of the '{@link alpha.alpha.AOrExpression#getLeft <em>Left</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Left</em>' containment reference.
   * @see #getLeft()
   * @generated
   */
  void setLeft(AAlphaExpression value);

  /**
   * Returns the value of the '<em><b>Operator</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Operator</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Operator</em>' attribute.
   * @see #setOperator(String)
   * @see alpha.alpha.AlphaPackage#getAOrExpression_Operator()
   * @model
   * @generated
   */
  String getOperator();

  /**
   * Sets the value of the '{@link alpha.alpha.AOrExpression#getOperator <em>Operator</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Operator</em>' attribute.
   * @see #getOperator()
   * @generated
   */
  void setOperator(String value);

  /**
   * Returns the value of the '<em><b>Right</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Right</em>' containment reference isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Right</em>' containment reference.
   * @see #setRight(AAlphaExpression)
   * @see alpha.alpha.AlphaPackage#getAOrExpression_Right()
   * @model containment="true"
   * @generated
   */
  AAlphaExpression getRight();

  /**
   * Sets the value of the '{@link alpha.alpha.AOrExpression#getRight <em>Right</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Right</em>' containment reference.
   * @see #getRight()
   * @generated
   */
  void setRight(AAlphaExpression value);

} // AOrExpression
