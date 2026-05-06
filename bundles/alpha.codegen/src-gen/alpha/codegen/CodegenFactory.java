/**
 */
package alpha.codegen;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see alpha.codegen.CodegenPackage
 * @generated
 */
public interface CodegenFactory extends EFactory {
	/**
	 * The singleton instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	CodegenFactory eINSTANCE = alpha.codegen.impl.CodegenFactoryImpl.init();

	/**
	 * Returns a new object of class '<em>Program</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Program</em>'.
	 * @generated
	 */
	Program createProgram();

	/**
	 * Returns a new object of class '<em>Include</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Include</em>'.
	 * @generated
	 */
	Include createInclude();

	/**
	 * Returns a new object of class '<em>Data Type</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Data Type</em>'.
	 * @generated
	 */
	DataType createDataType();

	/**
	 * Returns a new object of class '<em>Variable Decl</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Variable Decl</em>'.
	 * @generated
	 */
	VariableDecl createVariableDecl();

	/**
	 * Returns a new object of class '<em>Function</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Function</em>'.
	 * @generated
	 */
	Function createFunction();

	/**
	 * Returns a new object of class '<em>Parameter</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Parameter</em>'.
	 * @generated
	 */
	Parameter createParameter();

	/**
	 * Returns a new object of class '<em>Empty Line Stmt</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Empty Line Stmt</em>'.
	 * @generated
	 */
	EmptyLineStmt createEmptyLineStmt();

	/**
	 * Returns a new object of class '<em>Comment Stmt</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Comment Stmt</em>'.
	 * @generated
	 */
	CommentStmt createCommentStmt();

	/**
	 * Returns a new object of class '<em>Expression Stmt</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Expression Stmt</em>'.
	 * @generated
	 */
	ExpressionStmt createExpressionStmt();

	/**
	 * Returns a new object of class '<em>Macro Stmt</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Macro Stmt</em>'.
	 * @generated
	 */
	MacroStmt createMacroStmt();

	/**
	 * Returns a new object of class '<em>Undef Stmt</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Undef Stmt</em>'.
	 * @generated
	 */
	UndefStmt createUndefStmt();

	/**
	 * Returns a new object of class '<em>Omp Stmt</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Omp Stmt</em>'.
	 * @generated
	 */
	OmpStmt createOmpStmt();

	/**
	 * Returns a new object of class '<em>If Stmt</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>If Stmt</em>'.
	 * @generated
	 */
	IfStmt createIfStmt();

	/**
	 * Returns a new object of class '<em>Branch</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Branch</em>'.
	 * @generated
	 */
	Branch createBranch();

	/**
	 * Returns a new object of class '<em>Conditional Branch</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Conditional Branch</em>'.
	 * @generated
	 */
	ConditionalBranch createConditionalBranch();

	/**
	 * Returns a new object of class '<em>Loop Stmt</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Loop Stmt</em>'.
	 * @generated
	 */
	LoopStmt createLoopStmt();

	/**
	 * Returns a new object of class '<em>Assignment Stmt</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Assignment Stmt</em>'.
	 * @generated
	 */
	AssignmentStmt createAssignmentStmt();

	/**
	 * Returns a new object of class '<em>Return Stmt</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Return Stmt</em>'.
	 * @generated
	 */
	ReturnStmt createReturnStmt();

	/**
	 * Returns a new object of class '<em>Custom Expr</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Custom Expr</em>'.
	 * @generated
	 */
	CustomExpr createCustomExpr();

	/**
	 * Returns a new object of class '<em>Parenthesized Expr</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Parenthesized Expr</em>'.
	 * @generated
	 */
	ParenthesizedExpr createParenthesizedExpr();

	/**
	 * Returns a new object of class '<em>Cast Expr</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Cast Expr</em>'.
	 * @generated
	 */
	CastExpr createCastExpr();

	/**
	 * Returns a new object of class '<em>Array Access Expr</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Array Access Expr</em>'.
	 * @generated
	 */
	ArrayAccessExpr createArrayAccessExpr();

	/**
	 * Returns a new object of class '<em>Call Expr</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Call Expr</em>'.
	 * @generated
	 */
	CallExpr createCallExpr();

	/**
	 * Returns a new object of class '<em>Unary Expr</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Unary Expr</em>'.
	 * @generated
	 */
	UnaryExpr createUnaryExpr();

	/**
	 * Returns a new object of class '<em>Binary Expr</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Binary Expr</em>'.
	 * @generated
	 */
	BinaryExpr createBinaryExpr();

	/**
	 * Returns a new object of class '<em>Ternary Expr</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Ternary Expr</em>'.
	 * @generated
	 */
	TernaryExpr createTernaryExpr();

	/**
	 * Returns the package supported by this factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the package supported by this factory.
	 * @generated
	 */
	CodegenPackage getCodegenPackage();

} //CodegenFactory
