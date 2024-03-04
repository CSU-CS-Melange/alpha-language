/**
 */
package alpha.codegen;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Base Variable</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link alpha.codegen.BaseVariable#getName <em>Name</em>}</li>
 *   <li>{@link alpha.codegen.BaseVariable#getElemType <em>Elem Type</em>}</li>
 * </ul>
 *
 * @see alpha.codegen.CodegenPackage#getBaseVariable()
 * @model
 * @generated
 */
public interface BaseVariable extends Node, Visitable {
	/**
	 * Returns the value of the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Name</em>' attribute.
	 * @see #setName(String)
	 * @see alpha.codegen.CodegenPackage#getBaseVariable_Name()
	 * @model unique="false"
	 * @generated
	 */
	String getName();

	/**
	 * Sets the value of the '{@link alpha.codegen.BaseVariable#getName <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
	void setName(String value);

	/**
	 * Returns the value of the '<em><b>Elem Type</b></em>' attribute.
	 * The literals are from the enumeration {@link alpha.codegen.DataType}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Elem Type</em>' attribute.
	 * @see alpha.codegen.DataType
	 * @see #setElemType(DataType)
	 * @see alpha.codegen.CodegenPackage#getBaseVariable_ElemType()
	 * @model unique="false"
	 * @generated
	 */
	DataType getElemType();

	/**
	 * Sets the value of the '{@link alpha.codegen.BaseVariable#getElemType <em>Elem Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Elem Type</em>' attribute.
	 * @see alpha.codegen.DataType
	 * @see #getElemType()
	 * @generated
	 */
	void setElemType(DataType value);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model unique="false"
	 * @generated
	 */
	String dataType();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model visitorUnique="false"
	 * @generated
	 */
	void accept(Visitor visitor);

} // BaseVariable
