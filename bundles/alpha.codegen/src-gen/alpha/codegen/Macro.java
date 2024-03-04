/**
 */
package alpha.codegen;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Macro</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link alpha.codegen.Macro#getLeft <em>Left</em>}</li>
 *   <li>{@link alpha.codegen.Macro#getRight <em>Right</em>}</li>
 * </ul>
 *
 * @see alpha.codegen.CodegenPackage#getMacro()
 * @model
 * @generated
 */
public interface Macro extends Node, Visitable {
	/**
	 * Returns the value of the '<em><b>Left</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Left</em>' attribute.
	 * @see #setLeft(String)
	 * @see alpha.codegen.CodegenPackage#getMacro_Left()
	 * @model unique="false"
	 * @generated
	 */
	String getLeft();

	/**
	 * Sets the value of the '{@link alpha.codegen.Macro#getLeft <em>Left</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Left</em>' attribute.
	 * @see #getLeft()
	 * @generated
	 */
	void setLeft(String value);

	/**
	 * Returns the value of the '<em><b>Right</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Right</em>' attribute.
	 * @see #setRight(String)
	 * @see alpha.codegen.CodegenPackage#getMacro_Right()
	 * @model unique="false"
	 * @generated
	 */
	String getRight();

	/**
	 * Sets the value of the '{@link alpha.codegen.Macro#getRight <em>Right</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Right</em>' attribute.
	 * @see #getRight()
	 * @generated
	 */
	void setRight(String value);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model unique="false"
	 * @generated
	 */
	String name();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model unique="false"
	 * @generated
	 */
	String def();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model unique="false"
	 * @generated
	 */
	String toCString();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model visitorUnique="false"
	 * @generated
	 */
	void accept(Visitor visitor);

} // Macro
