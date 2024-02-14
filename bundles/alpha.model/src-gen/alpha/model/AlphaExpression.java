/**
 */
package alpha.model;

import fr.irisa.cairn.jnimap.isl.ISLSet;

import java.util.Queue;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Alpha Expression</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * * Alpha Expressions
 *  * XCore does not provide a way to make private variables in the classes. The current work around
 * is to use long and obvious names for internal variables that are not meant to be accessed. In
 * the AlphaExpression classes, these variables are prefixed by z__internal, where the leading
 * 'z' makes the corresponding methods to show up at the bottom of the content assist.
 * 
 * For JNIISL objects, any getter should return a copy of the object to ensure that the object
 * will not be taken by ISL.
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link alpha.model.AlphaExpression#getZ__internal_cache_exprDom <em>Zinternal cache expr Dom</em>}</li>
 *   <li>{@link alpha.model.AlphaExpression#getZ__internal_cache_contextDom <em>Zinternal cache context Dom</em>}</li>
 *   <li>{@link alpha.model.AlphaExpression#getExpressionID <em>Expression ID</em>}</li>
 * </ul>
 *
 * @see alpha.model.ModelPackage#getAlphaExpression()
 * @model abstract="true"
 * @generated
 */
public interface AlphaExpression extends AlphaNode, AlphaExpressionVisitable {
	/**
	 * Returns the value of the '<em><b>Zinternal cache expr Dom</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Internal object for always copying expression domain at getter
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Zinternal cache expr Dom</em>' attribute.
	 * @see #setZ__internal_cache_exprDom(ISLSet)
	 * @see alpha.model.ModelPackage#getAlphaExpression_Z__internal_cache_exprDom()
	 * @model unique="false" dataType="alpha.model.JNIISLSet"
	 * @generated
	 */
	ISLSet getZ__internal_cache_exprDom();

	/**
	 * Sets the value of the '{@link alpha.model.AlphaExpression#getZ__internal_cache_exprDom <em>Zinternal cache expr Dom</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Zinternal cache expr Dom</em>' attribute.
	 * @see #getZ__internal_cache_exprDom()
	 * @generated
	 */
	void setZ__internal_cache_exprDom(ISLSet value);

	/**
	 * Returns the value of the '<em><b>Zinternal cache context Dom</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Internal object for always copying context domain at getter
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Zinternal cache context Dom</em>' attribute.
	 * @see #setZ__internal_cache_contextDom(ISLSet)
	 * @see alpha.model.ModelPackage#getAlphaExpression_Z__internal_cache_contextDom()
	 * @model unique="false" dataType="alpha.model.JNIISLSet"
	 * @generated
	 */
	ISLSet getZ__internal_cache_contextDom();

	/**
	 * Sets the value of the '{@link alpha.model.AlphaExpression#getZ__internal_cache_contextDom <em>Zinternal cache context Dom</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Zinternal cache context Dom</em>' attribute.
	 * @see #getZ__internal_cache_contextDom()
	 * @generated
	 */
	void setZ__internal_cache_contextDom(ISLSet value);

	/**
	 * Returns the value of the '<em><b>Expression ID</b></em>' attribute list.
	 * The list contents are of type {@link java.lang.Integer}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Expression ID</em>' attribute list.
	 * @see alpha.model.ModelPackage#getAlphaExpression_ExpressionID()
	 * @model unique="false" dataType="alpha.model.int" transient="true" changeable="false" volatile="true" derived="true"
	 * @generated
	 */
	EList<Integer> getExpressionID();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model domDataType="alpha.model.JNIISLSet" domUnique="false"
	 * @generated
	 */
	void setExpressionDomain(ISLSet dom);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model kind="operation" dataType="alpha.model.JNIISLSet" unique="false"
	 * @generated
	 */
	ISLSet getExpressionDomain();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model domDataType="alpha.model.JNIISLSet" domUnique="false"
	 * @generated
	 */
	void setContextDomain(ISLSet dom);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model kind="operation" dataType="alpha.model.JNIISLSet" unique="false"
	 * @generated
	 */
	ISLSet getContextDomain();

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
	 * <!-- begin-model-doc -->
	 * *
	 * Depending on how the ISLSet was calculated (for either the expression domain or the context domain),
	 * the indices of the set might not be named (or a subset of them might not be named).
	 * In this case, issues may arise when trying to use the domain.
	 * To counteract this, if there is not a name for every index,
	 * then replace all the names with the default index names.
	 * <!-- end-model-doc -->
	 * @model dataType="alpha.model.JNIISLSet" unique="false" domDataType="alpha.model.JNIISLSet" domUnique="false"
	 * @generated
	 */
	ISLSet ensureDomainIsNamed(ISLSet dom);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model dataType="alpha.model.String" unique="false"
	 * @generated
	 */
	String toString();

} // AlphaExpression
