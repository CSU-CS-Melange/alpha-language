/**
 */
package alpha.model;

import fr.irisa.cairn.jnimap.isl.jni.JNIISLMultiAff;

import fr.irisa.cairn.jnimap.runtime.JNIObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>JNI Function</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link alpha.model.JNIFunction#getAlphaString <em>Alpha String</em>}</li>
 *   <li>{@link alpha.model.JNIFunction#getZ__internal_cache_islMAff <em>Zinternal cache isl MAff</em>}</li>
 * </ul>
 *
 * @see alpha.model.ModelPackage#getJNIFunction()
 * @model
 * @generated
 */
public interface JNIFunction extends CalculatorExpression {
	/**
	 * Returns the value of the '<em><b>Alpha String</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Alpha String</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Alpha String</em>' attribute.
	 * @see #setAlphaString(String)
	 * @see alpha.model.ModelPackage#getJNIFunction_AlphaString()
	 * @model unique="false"
	 * @generated
	 */
	String getAlphaString();

	/**
	 * Sets the value of the '{@link alpha.model.JNIFunction#getAlphaString <em>Alpha String</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Alpha String</em>' attribute.
	 * @see #getAlphaString()
	 * @generated
	 */
	void setAlphaString(String value);

	/**
	 * Returns the value of the '<em><b>Zinternal cache isl MAff</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Zinternal cache isl MAff</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Zinternal cache isl MAff</em>' attribute.
	 * @see #setZ__internal_cache_islMAff(JNIISLMultiAff)
	 * @see alpha.model.ModelPackage#getJNIFunction_Z__internal_cache_islMAff()
	 * @model unique="false" dataType="alpha.model.JNIISLMultiAff"
	 * @generated
	 */
	JNIISLMultiAff getZ__internal_cache_islMAff();

	/**
	 * Sets the value of the '{@link alpha.model.JNIFunction#getZ__internal_cache_islMAff <em>Zinternal cache isl MAff</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Zinternal cache isl MAff</em>' attribute.
	 * @see #getZ__internal_cache_islMAff()
	 * @generated
	 */
	void setZ__internal_cache_islMAff(JNIISLMultiAff value);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model kind="operation" dataType="alpha.model.JNIISLMultiAff" unique="false"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel body='&lt;%fr.irisa.cairn.jnimap.isl.jni.JNIISLMultiAff%&gt; _xifexpression = null;\n&lt;%fr.irisa.cairn.jnimap.isl.jni.JNIISLMultiAff%&gt; _z__internal_cache_islMAff = this.getZ__internal_cache_islMAff();\nboolean _tripleNotEquals = (_z__internal_cache_islMAff != null);\nif (_tripleNotEquals)\n{\n\t_xifexpression = this.getZ__internal_cache_islMAff().copy();\n}\nelse\n{\n\t_xifexpression = null;\n}\nreturn _xifexpression;'"
	 * @generated
	 */
	JNIISLMultiAff getISLMultiAff();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model islMAffDataType="alpha.model.JNIISLMultiAff" islMAffUnique="false"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel body='this.setZ__internal_cache_islMAff(islMAff);'"
	 * @generated
	 */
	void setISLMultiAff(JNIISLMultiAff islMAff);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model kind="operation" unique="false"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel body='return &lt;%alpha.model.POLY_OBJECT_TYPE%&gt;.FUNCTION;'"
	 * @generated
	 */
	POLY_OBJECT_TYPE getType();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model kind="operation" dataType="alpha.model.JNIObject" unique="false"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel body='return this.getISLMultiAff();'"
	 * @generated
	 */
	JNIObject getISLObject();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model visitorUnique="false"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel body='visitor.visitJNIFunction(this);'"
	 * @generated
	 */
	void accept(CalculatorExpressionVisitor visitor);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model unique="false"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel body='return this.getAlphaString();'"
	 * @generated
	 */
	String plainToString();

} // JNIFunction