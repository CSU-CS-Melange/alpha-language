/**
 * generated by Xtext 2.13.0
 */
package alpha.alpha;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>AVariable Domain</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link alpha.alpha.AVariableDomain#getVar <em>Var</em>}</li>
 * </ul>
 *
 * @see alpha.alpha.AlphaPackage#getAVariableDomain()
 * @model
 * @generated
 */
public interface AVariableDomain extends APolyhedralObjectExpression, APolyObjectInRestrict
{
  /**
   * Returns the value of the '<em><b>Var</b></em>' reference.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Var</em>' reference isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Var</em>' reference.
   * @see #setVar(AVariable)
   * @see alpha.alpha.AlphaPackage#getAVariableDomain_Var()
   * @model
   * @generated
   */
  AVariable getVar();

  /**
   * Sets the value of the '{@link alpha.alpha.AVariableDomain#getVar <em>Var</em>}' reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Var</em>' reference.
   * @see #getVar()
   * @generated
   */
  void setVar(AVariable value);

} // AVariableDomain
