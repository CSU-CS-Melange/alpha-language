/**
 * generated by Xtext 2.13.0
 */
package alpha.alpha;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>AFunction In Array Notation</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link alpha.alpha.AFunctionInArrayNotation#getNewIds <em>New Ids</em>}</li>
 * </ul>
 *
 * @see alpha.alpha.AlphaPackage#getAFunctionInArrayNotation()
 * @model
 * @generated
 */
public interface AFunctionInArrayNotation extends EObject
{
  /**
   * Returns the value of the '<em><b>New Ids</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>New Ids</em>' containment reference isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>New Ids</em>' containment reference.
   * @see #setNewIds(AIndexAffineExpressionList)
   * @see alpha.alpha.AlphaPackage#getAFunctionInArrayNotation_NewIds()
   * @model containment="true"
   * @generated
   */
  AIndexAffineExpressionList getNewIds();

  /**
   * Sets the value of the '{@link alpha.alpha.AFunctionInArrayNotation#getNewIds <em>New Ids</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>New Ids</em>' containment reference.
   * @see #getNewIds()
   * @generated
   */
  void setNewIds(AIndexAffineExpressionList value);

} // AFunctionInArrayNotation
