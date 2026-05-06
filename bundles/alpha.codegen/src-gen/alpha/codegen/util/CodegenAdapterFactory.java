/**
 */
package alpha.codegen.util;

import alpha.codegen.*;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notifier;

import org.eclipse.emf.common.notify.impl.AdapterFactoryImpl;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * The <b>Adapter Factory</b> for the model.
 * It provides an adapter <code>createXXX</code> method for each class of the model.
 * <!-- end-user-doc -->
 * @see alpha.codegen.CodegenPackage
 * @generated
 */
public class CodegenAdapterFactory extends AdapterFactoryImpl {
	/**
	 * The cached model package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static CodegenPackage modelPackage;

	/**
	 * Creates an instance of the adapter factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public CodegenAdapterFactory() {
		if (modelPackage == null) {
			modelPackage = CodegenPackage.eINSTANCE;
		}
	}

	/**
	 * Returns whether this factory is applicable for the type of the object.
	 * <!-- begin-user-doc -->
	 * This implementation returns <code>true</code> if the object is either the model's package or is an instance object of the model.
	 * <!-- end-user-doc -->
	 * @return whether this factory is applicable for the type of the object.
	 * @generated
	 */
	@Override
	public boolean isFactoryForType(Object object) {
		if (object == modelPackage) {
			return true;
		}
		if (object instanceof EObject) {
			return ((EObject)object).eClass().getEPackage() == modelPackage;
		}
		return false;
	}

	/**
	 * The switch that delegates to the <code>createXXX</code> methods.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected CodegenSwitch<Adapter> modelSwitch =
		new CodegenSwitch<Adapter>() {
			@Override
			public Adapter caseProgram(Program object) {
				return createProgramAdapter();
			}
			@Override
			public Adapter caseInclude(Include object) {
				return createIncludeAdapter();
			}
			@Override
			public Adapter caseDataType(DataType object) {
				return createDataTypeAdapter();
			}
			@Override
			public Adapter caseVariableDecl(VariableDecl object) {
				return createVariableDeclAdapter();
			}
			@Override
			public Adapter caseFunction(Function object) {
				return createFunctionAdapter();
			}
			@Override
			public Adapter caseParameter(Parameter object) {
				return createParameterAdapter();
			}
			@Override
			public Adapter caseStatement(Statement object) {
				return createStatementAdapter();
			}
			@Override
			public Adapter caseEmptyLineStmt(EmptyLineStmt object) {
				return createEmptyLineStmtAdapter();
			}
			@Override
			public Adapter caseCommentStmt(CommentStmt object) {
				return createCommentStmtAdapter();
			}
			@Override
			public Adapter caseExpressionStmt(ExpressionStmt object) {
				return createExpressionStmtAdapter();
			}
			@Override
			public Adapter caseMacroStmt(MacroStmt object) {
				return createMacroStmtAdapter();
			}
			@Override
			public Adapter caseUndefStmt(UndefStmt object) {
				return createUndefStmtAdapter();
			}
			@Override
			public Adapter caseOmpStmt(OmpStmt object) {
				return createOmpStmtAdapter();
			}
			@Override
			public Adapter caseIfStmt(IfStmt object) {
				return createIfStmtAdapter();
			}
			@Override
			public Adapter caseBranch(Branch object) {
				return createBranchAdapter();
			}
			@Override
			public Adapter caseConditionalBranch(ConditionalBranch object) {
				return createConditionalBranchAdapter();
			}
			@Override
			public Adapter caseLoopStmt(LoopStmt object) {
				return createLoopStmtAdapter();
			}
			@Override
			public Adapter caseAssignmentStmt(AssignmentStmt object) {
				return createAssignmentStmtAdapter();
			}
			@Override
			public Adapter caseReturnStmt(ReturnStmt object) {
				return createReturnStmtAdapter();
			}
			@Override
			public Adapter caseExpression(Expression object) {
				return createExpressionAdapter();
			}
			@Override
			public Adapter caseCustomExpr(CustomExpr object) {
				return createCustomExprAdapter();
			}
			@Override
			public Adapter caseParenthesizedExpr(ParenthesizedExpr object) {
				return createParenthesizedExprAdapter();
			}
			@Override
			public Adapter caseCastExpr(CastExpr object) {
				return createCastExprAdapter();
			}
			@Override
			public Adapter caseArrayAccessExpr(ArrayAccessExpr object) {
				return createArrayAccessExprAdapter();
			}
			@Override
			public Adapter caseCallExpr(CallExpr object) {
				return createCallExprAdapter();
			}
			@Override
			public Adapter caseUnaryExpr(UnaryExpr object) {
				return createUnaryExprAdapter();
			}
			@Override
			public Adapter caseBinaryExpr(BinaryExpr object) {
				return createBinaryExprAdapter();
			}
			@Override
			public Adapter caseTernaryExpr(TernaryExpr object) {
				return createTernaryExprAdapter();
			}
			@Override
			public Adapter defaultCase(EObject object) {
				return createEObjectAdapter();
			}
		};

	/**
	 * Creates an adapter for the <code>target</code>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param target the object to adapt.
	 * @return the adapter for the <code>target</code>.
	 * @generated
	 */
	@Override
	public Adapter createAdapter(Notifier target) {
		return modelSwitch.doSwitch((EObject)target);
	}


	/**
	 * Creates a new adapter for an object of class '{@link alpha.codegen.Program <em>Program</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see alpha.codegen.Program
	 * @generated
	 */
	public Adapter createProgramAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link alpha.codegen.Include <em>Include</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see alpha.codegen.Include
	 * @generated
	 */
	public Adapter createIncludeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link alpha.codegen.DataType <em>Data Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see alpha.codegen.DataType
	 * @generated
	 */
	public Adapter createDataTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link alpha.codegen.VariableDecl <em>Variable Decl</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see alpha.codegen.VariableDecl
	 * @generated
	 */
	public Adapter createVariableDeclAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link alpha.codegen.Function <em>Function</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see alpha.codegen.Function
	 * @generated
	 */
	public Adapter createFunctionAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link alpha.codegen.Parameter <em>Parameter</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see alpha.codegen.Parameter
	 * @generated
	 */
	public Adapter createParameterAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link alpha.codegen.Statement <em>Statement</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see alpha.codegen.Statement
	 * @generated
	 */
	public Adapter createStatementAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link alpha.codegen.EmptyLineStmt <em>Empty Line Stmt</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see alpha.codegen.EmptyLineStmt
	 * @generated
	 */
	public Adapter createEmptyLineStmtAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link alpha.codegen.CommentStmt <em>Comment Stmt</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see alpha.codegen.CommentStmt
	 * @generated
	 */
	public Adapter createCommentStmtAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link alpha.codegen.ExpressionStmt <em>Expression Stmt</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see alpha.codegen.ExpressionStmt
	 * @generated
	 */
	public Adapter createExpressionStmtAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link alpha.codegen.MacroStmt <em>Macro Stmt</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see alpha.codegen.MacroStmt
	 * @generated
	 */
	public Adapter createMacroStmtAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link alpha.codegen.UndefStmt <em>Undef Stmt</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see alpha.codegen.UndefStmt
	 * @generated
	 */
	public Adapter createUndefStmtAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link alpha.codegen.OmpStmt <em>Omp Stmt</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see alpha.codegen.OmpStmt
	 * @generated
	 */
	public Adapter createOmpStmtAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link alpha.codegen.IfStmt <em>If Stmt</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see alpha.codegen.IfStmt
	 * @generated
	 */
	public Adapter createIfStmtAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link alpha.codegen.Branch <em>Branch</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see alpha.codegen.Branch
	 * @generated
	 */
	public Adapter createBranchAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link alpha.codegen.ConditionalBranch <em>Conditional Branch</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see alpha.codegen.ConditionalBranch
	 * @generated
	 */
	public Adapter createConditionalBranchAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link alpha.codegen.LoopStmt <em>Loop Stmt</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see alpha.codegen.LoopStmt
	 * @generated
	 */
	public Adapter createLoopStmtAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link alpha.codegen.AssignmentStmt <em>Assignment Stmt</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see alpha.codegen.AssignmentStmt
	 * @generated
	 */
	public Adapter createAssignmentStmtAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link alpha.codegen.ReturnStmt <em>Return Stmt</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see alpha.codegen.ReturnStmt
	 * @generated
	 */
	public Adapter createReturnStmtAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link alpha.codegen.Expression <em>Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see alpha.codegen.Expression
	 * @generated
	 */
	public Adapter createExpressionAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link alpha.codegen.CustomExpr <em>Custom Expr</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see alpha.codegen.CustomExpr
	 * @generated
	 */
	public Adapter createCustomExprAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link alpha.codegen.ParenthesizedExpr <em>Parenthesized Expr</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see alpha.codegen.ParenthesizedExpr
	 * @generated
	 */
	public Adapter createParenthesizedExprAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link alpha.codegen.CastExpr <em>Cast Expr</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see alpha.codegen.CastExpr
	 * @generated
	 */
	public Adapter createCastExprAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link alpha.codegen.ArrayAccessExpr <em>Array Access Expr</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see alpha.codegen.ArrayAccessExpr
	 * @generated
	 */
	public Adapter createArrayAccessExprAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link alpha.codegen.CallExpr <em>Call Expr</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see alpha.codegen.CallExpr
	 * @generated
	 */
	public Adapter createCallExprAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link alpha.codegen.UnaryExpr <em>Unary Expr</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see alpha.codegen.UnaryExpr
	 * @generated
	 */
	public Adapter createUnaryExprAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link alpha.codegen.BinaryExpr <em>Binary Expr</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see alpha.codegen.BinaryExpr
	 * @generated
	 */
	public Adapter createBinaryExprAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link alpha.codegen.TernaryExpr <em>Ternary Expr</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see alpha.codegen.TernaryExpr
	 * @generated
	 */
	public Adapter createTernaryExprAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for the default case.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @generated
	 */
	public Adapter createEObjectAdapter() {
		return null;
	}

} //CodegenAdapterFactory
