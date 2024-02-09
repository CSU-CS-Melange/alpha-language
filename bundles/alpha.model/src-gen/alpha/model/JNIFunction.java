/**
 */
package alpha.model;

import fr.irisa.cairn.jnimap.isl.ISLMultiAff;

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
 *   <li>{@link alpha.model.JNIFunction#getAlphaFunction <em>Alpha Function</em>}</li>
 *   <li>{@link alpha.model.JNIFunction#getZ__internal_cache_islMAff <em>Zinternal cache isl MAff</em>}</li>
 * </ul>
 *
 * @see alpha.model.ModelPackage#getJNIFunction()
 * @model
 * @generated
 */
public interface JNIFunction extends CalculatorExpression {
	/**
	 * Returns the value of the '<em><b>Alpha Function</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Alpha Function</em>' containment reference.
	 * @see #setAlphaFunction(AlphaFunction)
	 * @see alpha.model.ModelPackage#getJNIFunction_AlphaFunction()
	 * @model containment="true"
	 * @generated
	 */
	AlphaFunction getAlphaFunction();

	/**
	 * Sets the value of the '{@link alpha.model.JNIFunction#getAlphaFunction <em>Alpha Function</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Alpha Function</em>' containment reference.
	 * @see #getAlphaFunction()
	 * @generated
	 */
	void setAlphaFunction(AlphaFunction value);

	/**
	 * Returns the value of the '<em><b>Zinternal cache isl MAff</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Zinternal cache isl MAff</em>' attribute.
	 * @see #setZ__internal_cache_islMAff(ISLMultiAff)
	 * @see alpha.model.ModelPackage#getJNIFunction_Z__internal_cache_islMAff()
	 * @model unique="false" dataType="alpha.model.JNIISLMultiAff"
	 * @generated
	 */
	ISLMultiAff getZ__internal_cache_islMAff();

	/**
	 * Sets the value of the '{@link alpha.model.JNIFunction#getZ__internal_cache_islMAff <em>Zinternal cache isl MAff</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Zinternal cache isl MAff</em>' attribute.
	 * @see #getZ__internal_cache_islMAff()
	 * @generated
	 */
	void setZ__internal_cache_islMAff(ISLMultiAff value);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model kind="operation" dataType="alpha.model.JNIISLMultiAff" unique="false"
	 * @generated
	 */
	ISLMultiAff getISLMultiAff();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model islMAffDataType="alpha.model.JNIISLMultiAff" islMAffUnique="false"
	 * @generated
	 */
	void setISLMultiAff(ISLMultiAff islMAff);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model kind="operation" unique="false"
	 * @generated
	 */
	POLY_OBJECT_TYPE getType();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model kind="operation" dataType="alpha.model.JNIObject" unique="false"
	 * @generated
	 */
	JNIObject getISLObject();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model visitorUnique="false"
	 * @generated
	 */
	void accept(CalculatorExpressionVisitor visitor);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model dataType="alpha.model.String" unique="false"
	 * @generated
	 */
	String plainToString();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * *
	 * Sometimes the ISLMutliAff does not have any input or output
	 * names (i.e., is null). In this case, issues may arise when
	 * trying to use the domain and range. To counteract this, if
	 * there is not a name for every index, then replace all the
	 * names with the default index names.
	 * <!-- end-model-doc -->
	 * @model dataType="alpha.model.JNIISLMultiAff" unique="false" maffDataType="alpha.model.JNIISLMultiAff" maffUnique="false"
	 * @generated
	 */
	ISLMultiAff ensureDomainAndRangeAreNamed(ISLMultiAff maff);

} // JNIFunction
