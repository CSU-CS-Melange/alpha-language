/**
 */
package alpha.codegen.util;

import alpha.codegen.*;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.util.Switch;

/**
 * <!-- begin-user-doc -->
 * The <b>Switch</b> for the model's inheritance hierarchy.
 * It supports the call {@link #doSwitch(EObject) doSwitch(object)}
 * to invoke the <code>caseXXX</code> method for each class of the model,
 * starting with the actual class of the object
 * and proceeding up the inheritance hierarchy
 * until a non-null result is returned,
 * which is the result of the switch.
 * <!-- end-user-doc -->
 * @see alpha.codegen.CodegenPackage
 * @generated
 */
public class CodegenSwitch<T> extends Switch<T> {
	/**
	 * The cached model package
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static CodegenPackage modelPackage;

	/**
	 * Creates an instance of the switch.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public CodegenSwitch() {
		if (modelPackage == null) {
			modelPackage = CodegenPackage.eINSTANCE;
		}
	}

	/**
	 * Checks whether this is a switch for the given package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param ePackage the package in question.
	 * @return whether this is a switch for the given package.
	 * @generated
	 */
	@Override
	protected boolean isSwitchFor(EPackage ePackage) {
		return ePackage == modelPackage;
	}

	/**
	 * Calls <code>caseXXX</code> for each class of the model until one returns a non null result; it yields that result.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the first non-null result returned by a <code>caseXXX</code> call.
	 * @generated
	 */
	@Override
	protected T doSwitch(int classifierID, EObject theEObject) {
		switch (classifierID) {
			case CodegenPackage.PROGRAM: {
				Program program = (Program)theEObject;
				T result = caseProgram(program);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case CodegenPackage.INCLUDE: {
				Include include = (Include)theEObject;
				T result = caseInclude(include);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case CodegenPackage.DATA_TYPE: {
				DataType dataType = (DataType)theEObject;
				T result = caseDataType(dataType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case CodegenPackage.VARIABLE_DECL: {
				VariableDecl variableDecl = (VariableDecl)theEObject;
				T result = caseVariableDecl(variableDecl);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case CodegenPackage.FUNCTION: {
				Function function = (Function)theEObject;
				T result = caseFunction(function);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case CodegenPackage.PARAMETER: {
				Parameter parameter = (Parameter)theEObject;
				T result = caseParameter(parameter);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case CodegenPackage.STATEMENT: {
				Statement statement = (Statement)theEObject;
				T result = caseStatement(statement);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case CodegenPackage.EMPTY_LINE_STMT: {
				EmptyLineStmt emptyLineStmt = (EmptyLineStmt)theEObject;
				T result = caseEmptyLineStmt(emptyLineStmt);
				if (result == null) result = caseStatement(emptyLineStmt);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case CodegenPackage.COMMENT_STMT: {
				CommentStmt commentStmt = (CommentStmt)theEObject;
				T result = caseCommentStmt(commentStmt);
				if (result == null) result = caseStatement(commentStmt);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case CodegenPackage.EXPRESSION_STMT: {
				ExpressionStmt expressionStmt = (ExpressionStmt)theEObject;
				T result = caseExpressionStmt(expressionStmt);
				if (result == null) result = caseStatement(expressionStmt);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case CodegenPackage.MACRO_STMT: {
				MacroStmt macroStmt = (MacroStmt)theEObject;
				T result = caseMacroStmt(macroStmt);
				if (result == null) result = caseStatement(macroStmt);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case CodegenPackage.UNDEF_STMT: {
				UndefStmt undefStmt = (UndefStmt)theEObject;
				T result = caseUndefStmt(undefStmt);
				if (result == null) result = caseStatement(undefStmt);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case CodegenPackage.OMP_STMT: {
				OmpStmt ompStmt = (OmpStmt)theEObject;
				T result = caseOmpStmt(ompStmt);
				if (result == null) result = caseStatement(ompStmt);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case CodegenPackage.IF_STMT: {
				IfStmt ifStmt = (IfStmt)theEObject;
				T result = caseIfStmt(ifStmt);
				if (result == null) result = caseStatement(ifStmt);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case CodegenPackage.BRANCH: {
				Branch branch = (Branch)theEObject;
				T result = caseBranch(branch);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case CodegenPackage.CONDITIONAL_BRANCH: {
				ConditionalBranch conditionalBranch = (ConditionalBranch)theEObject;
				T result = caseConditionalBranch(conditionalBranch);
				if (result == null) result = caseBranch(conditionalBranch);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case CodegenPackage.LOOP_STMT: {
				LoopStmt loopStmt = (LoopStmt)theEObject;
				T result = caseLoopStmt(loopStmt);
				if (result == null) result = caseStatement(loopStmt);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case CodegenPackage.ASSIGNMENT_STMT: {
				AssignmentStmt assignmentStmt = (AssignmentStmt)theEObject;
				T result = caseAssignmentStmt(assignmentStmt);
				if (result == null) result = caseStatement(assignmentStmt);
				if (result == null) result = caseExpression(assignmentStmt);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case CodegenPackage.RETURN_STMT: {
				ReturnStmt returnStmt = (ReturnStmt)theEObject;
				T result = caseReturnStmt(returnStmt);
				if (result == null) result = caseStatement(returnStmt);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case CodegenPackage.EXPRESSION: {
				Expression expression = (Expression)theEObject;
				T result = caseExpression(expression);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case CodegenPackage.CUSTOM_EXPR: {
				CustomExpr customExpr = (CustomExpr)theEObject;
				T result = caseCustomExpr(customExpr);
				if (result == null) result = caseExpression(customExpr);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case CodegenPackage.PARENTHESIZED_EXPR: {
				ParenthesizedExpr parenthesizedExpr = (ParenthesizedExpr)theEObject;
				T result = caseParenthesizedExpr(parenthesizedExpr);
				if (result == null) result = caseExpression(parenthesizedExpr);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case CodegenPackage.CAST_EXPR: {
				CastExpr castExpr = (CastExpr)theEObject;
				T result = caseCastExpr(castExpr);
				if (result == null) result = caseExpression(castExpr);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case CodegenPackage.ARRAY_ACCESS_EXPR: {
				ArrayAccessExpr arrayAccessExpr = (ArrayAccessExpr)theEObject;
				T result = caseArrayAccessExpr(arrayAccessExpr);
				if (result == null) result = caseExpression(arrayAccessExpr);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case CodegenPackage.CALL_EXPR: {
				CallExpr callExpr = (CallExpr)theEObject;
				T result = caseCallExpr(callExpr);
				if (result == null) result = caseExpression(callExpr);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case CodegenPackage.UNARY_EXPR: {
				UnaryExpr unaryExpr = (UnaryExpr)theEObject;
				T result = caseUnaryExpr(unaryExpr);
				if (result == null) result = caseExpression(unaryExpr);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case CodegenPackage.BINARY_EXPR: {
				BinaryExpr binaryExpr = (BinaryExpr)theEObject;
				T result = caseBinaryExpr(binaryExpr);
				if (result == null) result = caseExpression(binaryExpr);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case CodegenPackage.TERNARY_EXPR: {
				TernaryExpr ternaryExpr = (TernaryExpr)theEObject;
				T result = caseTernaryExpr(ternaryExpr);
				if (result == null) result = caseExpression(ternaryExpr);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			default: return defaultCase(theEObject);
		}
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Program</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Program</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseProgram(Program object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Include</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Include</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseInclude(Include object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Data Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Data Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseDataType(DataType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Variable Decl</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Variable Decl</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseVariableDecl(VariableDecl object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Function</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Function</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseFunction(Function object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Parameter</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Parameter</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseParameter(Parameter object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Statement</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Statement</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseStatement(Statement object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Empty Line Stmt</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Empty Line Stmt</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseEmptyLineStmt(EmptyLineStmt object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Comment Stmt</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Comment Stmt</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseCommentStmt(CommentStmt object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Expression Stmt</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Expression Stmt</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseExpressionStmt(ExpressionStmt object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Macro Stmt</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Macro Stmt</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseMacroStmt(MacroStmt object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Undef Stmt</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Undef Stmt</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseUndefStmt(UndefStmt object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Omp Stmt</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Omp Stmt</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseOmpStmt(OmpStmt object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>If Stmt</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>If Stmt</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseIfStmt(IfStmt object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Branch</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Branch</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseBranch(Branch object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Conditional Branch</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Conditional Branch</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseConditionalBranch(ConditionalBranch object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Loop Stmt</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Loop Stmt</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseLoopStmt(LoopStmt object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Assignment Stmt</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Assignment Stmt</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseAssignmentStmt(AssignmentStmt object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Return Stmt</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Return Stmt</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseReturnStmt(ReturnStmt object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Expression</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Expression</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseExpression(Expression object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Custom Expr</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Custom Expr</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseCustomExpr(CustomExpr object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Parenthesized Expr</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Parenthesized Expr</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseParenthesizedExpr(ParenthesizedExpr object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Cast Expr</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Cast Expr</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseCastExpr(CastExpr object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Array Access Expr</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Array Access Expr</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseArrayAccessExpr(ArrayAccessExpr object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Call Expr</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Call Expr</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseCallExpr(CallExpr object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Unary Expr</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Unary Expr</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseUnaryExpr(UnaryExpr object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Binary Expr</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Binary Expr</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseBinaryExpr(BinaryExpr object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Ternary Expr</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Ternary Expr</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseTernaryExpr(TernaryExpr object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>EObject</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch, but this is the last case anyway.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>EObject</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject)
	 * @generated
	 */
	@Override
	public T defaultCase(EObject object) {
		return null;
	}

} //CodegenSwitch
