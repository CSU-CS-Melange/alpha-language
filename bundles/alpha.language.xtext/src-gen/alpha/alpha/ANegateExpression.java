/**
 * generated by Xtext 2.13.0
 */
package alpha.alpha;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>ANegate Expression</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link alpha.alpha.ANegateExpression#getOp <em>Op</em>}</li>
 *   <li>{@link alpha.alpha.ANegateExpression#getExpr <em>Expr</em>}</li>
 * </ul>
 *
 * @see alpha.alpha.AlphaPackage#getANegateExpression()
 * @model
 * @generated
 */
public interface ANegateExpression extends AAlphaExpression
{
  /**
   * Returns the value of the '<em><b>Op</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Op</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Op</em>' attribute.
   * @see #setOp(String)
   * @see alpha.alpha.AlphaPackage#getANegateExpression_Op()
   * @model
   * @generated
   */
  String getOp();

  /**
   * Sets the value of the '{@link alpha.alpha.ANegateExpression#getOp <em>Op</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Op</em>' attribute.
   * @see #getOp()
   * @generated
   */
  void setOp(String value);

  /**
   * Returns the value of the '<em><b>Expr</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Expr</em>' containment reference isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Expr</em>' containment reference.
   * @see #setExpr(AAlphaExpression)
   * @see alpha.alpha.AlphaPackage#getANegateExpression_Expr()
   * @model containment="true"
   * @generated
   */
  AAlphaExpression getExpr();

  /**
   * Sets the value of the '{@link alpha.alpha.ANegateExpression#getExpr <em>Expr</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Expr</em>' containment reference.
   * @see #getExpr()
   * @generated
   */
  void setExpr(AAlphaExpression value);

} // ANegateExpression