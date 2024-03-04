/**
 */
package alpha.codegen;

import fr.irisa.cairn.jnimap.isl.ISLASTNode;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Function Body</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link alpha.codegen.FunctionBody#getFunction <em>Function</em>}</li>
 *   <li>{@link alpha.codegen.FunctionBody#getStatementMacros <em>Statement Macros</em>}</li>
 *   <li>{@link alpha.codegen.FunctionBody#getISLASTNode <em>ISLAST Node</em>}</li>
 * </ul>
 *
 * @see alpha.codegen.CodegenPackage#getFunctionBody()
 * @model
 * @generated
 */
public interface FunctionBody extends Node, Visitable {
	/**
	 * Returns the value of the '<em><b>Function</b></em>' container reference.
	 * It is bidirectional and its opposite is '{@link alpha.codegen.Function#getBody <em>Body</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Function</em>' container reference.
	 * @see #setFunction(Function)
	 * @see alpha.codegen.CodegenPackage#getFunctionBody_Function()
	 * @see alpha.codegen.Function#getBody
	 * @model opposite="body" transient="false"
	 * @generated
	 */
	Function getFunction();

	/**
	 * Sets the value of the '{@link alpha.codegen.FunctionBody#getFunction <em>Function</em>}' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Function</em>' container reference.
	 * @see #getFunction()
	 * @generated
	 */
	void setFunction(Function value);

	/**
	 * Returns the value of the '<em><b>Statement Macros</b></em>' containment reference list.
	 * The list contents are of type {@link alpha.codegen.StatementMacro}.
	 * It is bidirectional and its opposite is '{@link alpha.codegen.StatementMacro#getFunctionBody <em>Function Body</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Statement Macros</em>' containment reference list.
	 * @see alpha.codegen.CodegenPackage#getFunctionBody_StatementMacros()
	 * @see alpha.codegen.StatementMacro#getFunctionBody
	 * @model opposite="functionBody" containment="true"
	 * @generated
	 */
	EList<StatementMacro> getStatementMacros();

	/**
	 * Returns the value of the '<em><b>ISLAST Node</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>ISLAST Node</em>' attribute.
	 * @see #setISLASTNode(ISLASTNode)
	 * @see alpha.codegen.CodegenPackage#getFunctionBody_ISLASTNode()
	 * @model unique="false" dataType="alpha.codegen.ISLASTNode"
	 * @generated
	 */
	ISLASTNode getISLASTNode();

	/**
	 * Sets the value of the '{@link alpha.codegen.FunctionBody#getISLASTNode <em>ISLAST Node</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>ISLAST Node</em>' attribute.
	 * @see #getISLASTNode()
	 * @generated
	 */
	void setISLASTNode(ISLASTNode value);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model visitorUnique="false"
	 * @generated
	 */
	void accept(Visitor visitor);

} // FunctionBody
