/**
 */
package alpha.model;

import fr.irisa.cairn.jnimap.isl.jni.JNIISLSet;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Convolution Expression</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link alpha.model.ConvolutionExpression#getKernelDomainExpr <em>Kernel Domain Expr</em>}</li>
 *   <li>{@link alpha.model.ConvolutionExpression#getKernelExpression <em>Kernel Expression</em>}</li>
 *   <li>{@link alpha.model.ConvolutionExpression#getDataExpression <em>Data Expression</em>}</li>
 * </ul>
 *
 * @see alpha.model.ModelPackage#getConvolutionExpression()
 * @model
 * @generated
 */
public interface ConvolutionExpression extends AlphaExpression {
	/**
	 * Returns the value of the '<em><b>Kernel Domain Expr</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Kernel Domain Expr</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Kernel Domain Expr</em>' containment reference.
	 * @see #setKernelDomainExpr(CalculatorExpression)
	 * @see alpha.model.ModelPackage#getConvolutionExpression_KernelDomainExpr()
	 * @model containment="true"
	 * @generated
	 */
	CalculatorExpression getKernelDomainExpr();

	/**
	 * Sets the value of the '{@link alpha.model.ConvolutionExpression#getKernelDomainExpr <em>Kernel Domain Expr</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Kernel Domain Expr</em>' containment reference.
	 * @see #getKernelDomainExpr()
	 * @generated
	 */
	void setKernelDomainExpr(CalculatorExpression value);

	/**
	 * Returns the value of the '<em><b>Kernel Expression</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Kernel Expression</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Kernel Expression</em>' containment reference.
	 * @see #setKernelExpression(AlphaExpression)
	 * @see alpha.model.ModelPackage#getConvolutionExpression_KernelExpression()
	 * @model containment="true"
	 * @generated
	 */
	AlphaExpression getKernelExpression();

	/**
	 * Sets the value of the '{@link alpha.model.ConvolutionExpression#getKernelExpression <em>Kernel Expression</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Kernel Expression</em>' containment reference.
	 * @see #getKernelExpression()
	 * @generated
	 */
	void setKernelExpression(AlphaExpression value);

	/**
	 * Returns the value of the '<em><b>Data Expression</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Data Expression</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Data Expression</em>' containment reference.
	 * @see #setDataExpression(AlphaExpression)
	 * @see alpha.model.ModelPackage#getConvolutionExpression_DataExpression()
	 * @model containment="true"
	 * @generated
	 */
	AlphaExpression getDataExpression();

	/**
	 * Sets the value of the '{@link alpha.model.ConvolutionExpression#getDataExpression <em>Data Expression</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Data Expression</em>' containment reference.
	 * @see #getDataExpression()
	 * @generated
	 */
	void setDataExpression(AlphaExpression value);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model kind="operation" dataType="alpha.model.JNIISLSet" unique="false"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel body='&lt;%fr.irisa.cairn.jnimap.isl.jni.JNIISLSet%&gt; _xifexpression = null;\n&lt;%alpha.model.POLY_OBJECT_TYPE%&gt; _type = this.getKernelDomainExpr().getType();\nboolean _notEquals = (!&lt;%com.google.common.base.Objects%&gt;.equal(_type, &lt;%alpha.model.POLY_OBJECT_TYPE%&gt;.SET));\nif (_notEquals)\n{\n\t_xifexpression = null;\n}\nelse\n{\n\t&lt;%fr.irisa.cairn.jnimap.runtime.JNIObject%&gt; _iSLObject = this.getKernelDomainExpr().getISLObject();\n\t_xifexpression = ((&lt;%fr.irisa.cairn.jnimap.isl.jni.JNIISLSet%&gt;) _iSLObject);\n}\nreturn _xifexpression;'"
	 * @generated
	 */
	JNIISLSet getKernelDomain();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model visitorUnique="false"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel body='visitor.visitConvolutionExpression(this);'"
	 * @generated
	 */
	void accept(AlphaExpressionVisitor visitor);

} // ConvolutionExpression
