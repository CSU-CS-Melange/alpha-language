/**
 */
package alpha.model;

import alpha.model.util.Facet;

import fr.irisa.cairn.jnimap.isl.ISLMultiAff;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Abstract Reduce Expression</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link alpha.model.AbstractReduceExpression#getOperator <em>Operator</em>}</li>
 *   <li>{@link alpha.model.AbstractReduceExpression#getProjectionExpr <em>Projection Expr</em>}</li>
 *   <li>{@link alpha.model.AbstractReduceExpression#getBody <em>Body</em>}</li>
 *   <li>{@link alpha.model.AbstractReduceExpression#getZ__internal_facet <em>Zinternal facet</em>}</li>
 *   <li>{@link alpha.model.AbstractReduceExpression#getNbFreeDimensionsInBody <em>Nb Free Dimensions In Body</em>}</li>
 * </ul>
 *
 * @see alpha.model.ModelPackage#getAbstractReduceExpression()
 * @model abstract="true"
 * @generated
 */
public interface AbstractReduceExpression extends AlphaExpression {
	/**
	 * Returns the value of the '<em><b>Operator</b></em>' attribute.
	 * The literals are from the enumeration {@link alpha.model.REDUCTION_OP}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Operator</em>' attribute.
	 * @see alpha.model.REDUCTION_OP
	 * @see #setOperator(REDUCTION_OP)
	 * @see alpha.model.ModelPackage#getAbstractReduceExpression_Operator()
	 * @model unique="false"
	 * @generated
	 */
	REDUCTION_OP getOperator();

	/**
	 * Sets the value of the '{@link alpha.model.AbstractReduceExpression#getOperator <em>Operator</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Operator</em>' attribute.
	 * @see alpha.model.REDUCTION_OP
	 * @see #getOperator()
	 * @generated
	 */
	void setOperator(REDUCTION_OP value);

	/**
	 * Returns the value of the '<em><b>Projection Expr</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Projection Expr</em>' containment reference.
	 * @see #setProjectionExpr(JNIFunction)
	 * @see alpha.model.ModelPackage#getAbstractReduceExpression_ProjectionExpr()
	 * @model containment="true"
	 * @generated
	 */
	JNIFunction getProjectionExpr();

	/**
	 * Sets the value of the '{@link alpha.model.AbstractReduceExpression#getProjectionExpr <em>Projection Expr</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Projection Expr</em>' containment reference.
	 * @see #getProjectionExpr()
	 * @generated
	 */
	void setProjectionExpr(JNIFunction value);

	/**
	 * Returns the value of the '<em><b>Body</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Body</em>' containment reference.
	 * @see #setBody(AlphaExpression)
	 * @see alpha.model.ModelPackage#getAbstractReduceExpression_Body()
	 * @model containment="true"
	 * @generated
	 */
	AlphaExpression getBody();

	/**
	 * Sets the value of the '{@link alpha.model.AbstractReduceExpression#getBody <em>Body</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Body</em>' containment reference.
	 * @see #getBody()
	 * @generated
	 */
	void setBody(AlphaExpression value);

	/**
	 * Returns the value of the '<em><b>Zinternal facet</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Zinternal facet</em>' attribute.
	 * @see #setZ__internal_facet(Facet)
	 * @see alpha.model.ModelPackage#getAbstractReduceExpression_Z__internal_facet()
	 * @model unique="false" dataType="alpha.model.Facet"
	 * @generated
	 */
	Facet getZ__internal_facet();

	/**
	 * Sets the value of the '{@link alpha.model.AbstractReduceExpression#getZ__internal_facet <em>Zinternal facet</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Zinternal facet</em>' attribute.
	 * @see #getZ__internal_facet()
	 * @generated
	 */
	void setZ__internal_facet(Facet value);

	/**
	 * Returns the value of the '<em><b>Nb Free Dimensions In Body</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Nb Free Dimensions In Body</em>' attribute.
	 * @see #setNbFreeDimensionsInBody(Integer)
	 * @see alpha.model.ModelPackage#getAbstractReduceExpression_NbFreeDimensionsInBody()
	 * @model unique="false" dataType="alpha.model.int"
	 * @generated
	 */
	Integer getNbFreeDimensionsInBody();

	/**
	 * Sets the value of the '{@link alpha.model.AbstractReduceExpression#getNbFreeDimensionsInBody <em>Nb Free Dimensions In Body</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Nb Free Dimensions In Body</em>' attribute.
	 * @see #getNbFreeDimensionsInBody()
	 * @generated
	 */
	void setNbFreeDimensionsInBody(Integer value);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model kind="operation" dataType="alpha.model.JNIISLMultiAff" unique="false"
	 * @generated
	 */
	ISLMultiAff getProjection();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model visitorUnique="false"
	 * @generated
	 */
	void accept(AlphaExpressionVisitor visitor);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model kind="operation" dataType="alpha.model.Facet" unique="false"
	 * @generated
	 */
	Facet getFacet();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model facetDataType="alpha.model.Facet" facetUnique="false"
	 * @generated
	 */
	void setFacet(Facet facet);

} // AbstractReduceExpression
