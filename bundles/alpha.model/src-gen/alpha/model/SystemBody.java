/**
 */
package alpha.model;

import fr.irisa.cairn.jnimap.isl.jni.JNIISLSet;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>System Body</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link alpha.model.SystemBody#getParameterDomainExpr <em>Parameter Domain Expr</em>}</li>
 *   <li>{@link alpha.model.SystemBody#getEquations <em>Equations</em>}</li>
 * </ul>
 *
 * @see alpha.model.ModelPackage#getSystemBody()
 * @model
 * @generated
 */
public interface SystemBody extends AlphaVisitable {
	/**
	 * Returns the value of the '<em><b>Parameter Domain Expr</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Parameter Domain Expr</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Parameter Domain Expr</em>' containment reference.
	 * @see #setParameterDomainExpr(JNIDomain)
	 * @see alpha.model.ModelPackage#getSystemBody_ParameterDomainExpr()
	 * @model containment="true"
	 * @generated
	 */
	JNIDomain getParameterDomainExpr();

	/**
	 * Sets the value of the '{@link alpha.model.SystemBody#getParameterDomainExpr <em>Parameter Domain Expr</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Parameter Domain Expr</em>' containment reference.
	 * @see #getParameterDomainExpr()
	 * @generated
	 */
	void setParameterDomainExpr(JNIDomain value);

	/**
	 * Returns the value of the '<em><b>Equations</b></em>' containment reference list.
	 * The list contents are of type {@link alpha.model.Equation}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Equations</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Equations</em>' containment reference list.
	 * @see alpha.model.ModelPackage#getSystemBody_Equations()
	 * @model containment="true"
	 * @generated
	 */
	EList<Equation> getEquations();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model kind="operation" dataType="alpha.model.JNIISLSet" unique="false"
	 * @generated
	 */
	JNIISLSet getParameterDomain();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model kind="operation" unique="false"
	 * @generated
	 */
	EList<UseEquation> getUseEquations();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model kind="operation" unique="false"
	 * @generated
	 */
	EList<StandardEquation> getStandardEquations();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model visitorUnique="false"
	 * @generated
	 */
	void accept(AlphaVisitor visitor);

} // SystemBody