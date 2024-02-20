/**
 */
package alpha.model;

import java.util.Queue;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Equation</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link alpha.model.Equation#getSystemBody <em>System Body</em>}</li>
 *   <li>{@link alpha.model.Equation#getZ__explored <em>Zexplored</em>}</li>
 * </ul>
 *
 * @see alpha.model.ModelPackage#getEquation()
 * @model abstract="true"
 * @generated
 */
public interface Equation extends AlphaNode, AlphaVisitable, AlphaSystemElement, AlphaScheduleTarget {
	/**
	 * Returns the value of the '<em><b>System Body</b></em>' container reference.
	 * It is bidirectional and its opposite is '{@link alpha.model.SystemBody#getEquations <em>Equations</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>System Body</em>' container reference.
	 * @see #setSystemBody(SystemBody)
	 * @see alpha.model.ModelPackage#getEquation_SystemBody()
	 * @see alpha.model.SystemBody#getEquations
	 * @model opposite="equations" transient="false"
	 * @generated
	 */
	SystemBody getSystemBody();

	/**
	 * Sets the value of the '{@link alpha.model.Equation#getSystemBody <em>System Body</em>}' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>System Body</em>' container reference.
	 * @see #getSystemBody()
	 * @generated
	 */
	void setSystemBody(SystemBody value);

	/**
	 * Returns the value of the '<em><b>Zexplored</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Zexplored</em>' attribute.
	 * @see #setZ__explored(Boolean)
	 * @see alpha.model.ModelPackage#getEquation_Z__explored()
	 * @model unique="false" dataType="alpha.model.boolean"
	 * @generated
	 */
	Boolean getZ__explored();

	/**
	 * Sets the value of the '{@link alpha.model.Equation#getZ__explored <em>Zexplored</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Zexplored</em>' attribute.
	 * @see #getZ__explored()
	 * @generated
	 */
	void setZ__explored(Boolean value);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model unique="false" exprIDDataType="alpha.model.IntegerQueue" exprIDUnique="false"
	 * @generated
	 */
	AlphaExpression getExpression(Queue<Integer> exprID);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model unique="false" exprIDDataType="alpha.model.int" exprIDUnique="false" exprIDMany="true"
	 * @generated
	 */
	AlphaExpression getExpression(EList<Integer> exprID);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model unique="false" exprIDstrDataType="alpha.model.String" exprIDstrUnique="false"
	 * @generated
	 */
	AlphaExpression getExpression(String exprIDstr);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model kind="operation" dataType="alpha.model.boolean" unique="false"
	 * @generated
	 */
	Boolean getExplored();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model
	 * @generated
	 */
	void setExplored();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model exploredDataType="alpha.model.boolean" exploredUnique="false"
	 * @generated
	 */
	void setExplored(Boolean explored);

} // Equation
