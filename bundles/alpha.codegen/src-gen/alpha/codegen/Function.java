/**
 */
package alpha.codegen;

import alpha.model.AlphaSystem;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Function</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link alpha.codegen.Function#getProgram <em>Program</em>}</li>
 *   <li>{@link alpha.codegen.Function#getReturnType <em>Return Type</em>}</li>
 *   <li>{@link alpha.codegen.Function#getName <em>Name</em>}</li>
 *   <li>{@link alpha.codegen.Function#getScalarArgs <em>Scalar Args</em>}</li>
 *   <li>{@link alpha.codegen.Function#getArrayArgs <em>Array Args</em>}</li>
 *   <li>{@link alpha.codegen.Function#getBody <em>Body</em>}</li>
 * </ul>
 *
 * @see alpha.codegen.CodegenPackage#getFunction()
 * @model
 * @generated
 */
public interface Function extends Node, Visitable {
	/**
	 * Returns the value of the '<em><b>Program</b></em>' container reference.
	 * It is bidirectional and its opposite is '{@link alpha.codegen.Program#getFunctions <em>Functions</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Program</em>' container reference.
	 * @see #setProgram(Program)
	 * @see alpha.codegen.CodegenPackage#getFunction_Program()
	 * @see alpha.codegen.Program#getFunctions
	 * @model opposite="functions" transient="false"
	 * @generated
	 */
	Program getProgram();

	/**
	 * Sets the value of the '{@link alpha.codegen.Function#getProgram <em>Program</em>}' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Program</em>' container reference.
	 * @see #getProgram()
	 * @generated
	 */
	void setProgram(Program value);

	/**
	 * Returns the value of the '<em><b>Return Type</b></em>' attribute.
	 * The literals are from the enumeration {@link alpha.codegen.DataType}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Return Type</em>' attribute.
	 * @see alpha.codegen.DataType
	 * @see #setReturnType(DataType)
	 * @see alpha.codegen.CodegenPackage#getFunction_ReturnType()
	 * @model unique="false"
	 * @generated
	 */
	DataType getReturnType();

	/**
	 * Sets the value of the '{@link alpha.codegen.Function#getReturnType <em>Return Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Return Type</em>' attribute.
	 * @see alpha.codegen.DataType
	 * @see #getReturnType()
	 * @generated
	 */
	void setReturnType(DataType value);

	/**
	 * Returns the value of the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Name</em>' attribute.
	 * @see #setName(String)
	 * @see alpha.codegen.CodegenPackage#getFunction_Name()
	 * @model unique="false"
	 * @generated
	 */
	String getName();

	/**
	 * Sets the value of the '{@link alpha.codegen.Function#getName <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
	void setName(String value);

	/**
	 * Returns the value of the '<em><b>Scalar Args</b></em>' reference list.
	 * The list contents are of type {@link alpha.codegen.BaseVariable}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Scalar Args</em>' reference list.
	 * @see alpha.codegen.CodegenPackage#getFunction_ScalarArgs()
	 * @model
	 * @generated
	 */
	EList<BaseVariable> getScalarArgs();

	/**
	 * Returns the value of the '<em><b>Array Args</b></em>' reference list.
	 * The list contents are of type {@link alpha.codegen.ArrayVariable}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Array Args</em>' reference list.
	 * @see alpha.codegen.CodegenPackage#getFunction_ArrayArgs()
	 * @model
	 * @generated
	 */
	EList<ArrayVariable> getArrayArgs();

	/**
	 * Returns the value of the '<em><b>Body</b></em>' containment reference.
	 * It is bidirectional and its opposite is '{@link alpha.codegen.FunctionBody#getFunction <em>Function</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Body</em>' containment reference.
	 * @see #setBody(FunctionBody)
	 * @see alpha.codegen.CodegenPackage#getFunction_Body()
	 * @see alpha.codegen.FunctionBody#getFunction
	 * @model opposite="function" containment="true"
	 * @generated
	 */
	FunctionBody getBody();

	/**
	 * Sets the value of the '{@link alpha.codegen.Function#getBody <em>Body</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Body</em>' containment reference.
	 * @see #getBody()
	 * @generated
	 */
	void setBody(FunctionBody value);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model unique="false"
	 * @generated
	 */
	EList<BaseVariable> args();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model visitorUnique="false"
	 * @generated
	 */
	void accept(Visitor visitor);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model unique="false"
	 * @generated
	 */
	AlphaSystem system();

} // Function
