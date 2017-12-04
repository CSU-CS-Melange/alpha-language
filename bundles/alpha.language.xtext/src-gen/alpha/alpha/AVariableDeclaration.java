/**
 * generated by Xtext 2.13.0
 */
package alpha.alpha;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>AVariable Declaration</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link alpha.alpha.AVariableDeclaration#getVarList <em>Var List</em>}</li>
 *   <li>{@link alpha.alpha.AVariableDeclaration#getDomain <em>Domain</em>}</li>
 * </ul>
 *
 * @see alpha.alpha.AlphaPackage#getAVariableDeclaration()
 * @model
 * @generated
 */
public interface AVariableDeclaration extends EObject
{
  /**
   * Returns the value of the '<em><b>Var List</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Var List</em>' containment reference isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Var List</em>' containment reference.
   * @see #setVarList(AVariableList)
   * @see alpha.alpha.AlphaPackage#getAVariableDeclaration_VarList()
   * @model containment="true"
   * @generated
   */
  AVariableList getVarList();

  /**
   * Sets the value of the '{@link alpha.alpha.AVariableDeclaration#getVarList <em>Var List</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Var List</em>' containment reference.
   * @see #getVarList()
   * @generated
   */
  void setVarList(AVariableList value);

  /**
   * Returns the value of the '<em><b>Domain</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Domain</em>' containment reference isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Domain</em>' containment reference.
   * @see #setDomain(APolyhedralObjectExpression)
   * @see alpha.alpha.AlphaPackage#getAVariableDeclaration_Domain()
   * @model containment="true"
   * @generated
   */
  APolyhedralObjectExpression getDomain();

  /**
   * Sets the value of the '{@link alpha.alpha.AVariableDeclaration#getDomain <em>Domain</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Domain</em>' containment reference.
   * @see #getDomain()
   * @generated
   */
  void setDomain(APolyhedralObjectExpression value);

} // AVariableDeclaration