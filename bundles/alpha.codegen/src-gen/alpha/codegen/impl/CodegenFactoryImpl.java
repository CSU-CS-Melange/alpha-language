/**
 */
package alpha.codegen.impl;

import alpha.codegen.*;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.impl.EFactoryImpl;

import org.eclipse.emf.ecore.plugin.EcorePlugin;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class CodegenFactoryImpl extends EFactoryImpl implements CodegenFactory {
	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static CodegenFactory init() {
		try {
			CodegenFactory theCodegenFactory = (CodegenFactory)EPackage.Registry.INSTANCE.getEFactory(CodegenPackage.eNS_URI);
			if (theCodegenFactory != null) {
				return theCodegenFactory;
			}
		}
		catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new CodegenFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public CodegenFactoryImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EObject create(EClass eClass) {
		switch (eClass.getClassifierID()) {
			case CodegenPackage.PROGRAM: return createProgram();
			case CodegenPackage.INCLUDE: return createInclude();
			case CodegenPackage.DATA_TYPE: return createDataType();
			case CodegenPackage.VARIABLE_DECL: return createVariableDecl();
			case CodegenPackage.FUNCTION: return createFunction();
			case CodegenPackage.PARAMETER: return createParameter();
			case CodegenPackage.EMPTY_LINE_STMT: return createEmptyLineStmt();
			case CodegenPackage.COMMENT_STMT: return createCommentStmt();
			case CodegenPackage.EXPRESSION_STMT: return createExpressionStmt();
			case CodegenPackage.MACRO_STMT: return createMacroStmt();
			case CodegenPackage.UNDEF_STMT: return createUndefStmt();
			case CodegenPackage.OMP_STMT: return createOmpStmt();
			case CodegenPackage.IF_STMT: return createIfStmt();
			case CodegenPackage.BRANCH: return createBranch();
			case CodegenPackage.CONDITIONAL_BRANCH: return createConditionalBranch();
			case CodegenPackage.LOOP_STMT: return createLoopStmt();
			case CodegenPackage.ASSIGNMENT_STMT: return createAssignmentStmt();
			case CodegenPackage.RETURN_STMT: return createReturnStmt();
			case CodegenPackage.CUSTOM_EXPR: return createCustomExpr();
			case CodegenPackage.PARENTHESIZED_EXPR: return createParenthesizedExpr();
			case CodegenPackage.CAST_EXPR: return createCastExpr();
			case CodegenPackage.ARRAY_ACCESS_EXPR: return createArrayAccessExpr();
			case CodegenPackage.CALL_EXPR: return createCallExpr();
			case CodegenPackage.UNARY_EXPR: return createUnaryExpr();
			case CodegenPackage.BINARY_EXPR: return createBinaryExpr();
			case CodegenPackage.TERNARY_EXPR: return createTernaryExpr();
			default:
				throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object createFromString(EDataType eDataType, String initialValue) {
		switch (eDataType.getClassifierID()) {
			case CodegenPackage.BASE_DATA_TYPE:
				return createBaseDataTypeFromString(eDataType, initialValue);
			case CodegenPackage.ASSIGNMENT_OPERATOR:
				return createAssignmentOperatorFromString(eDataType, initialValue);
			case CodegenPackage.UNARY_OPERATOR:
				return createUnaryOperatorFromString(eDataType, initialValue);
			case CodegenPackage.BINARY_OPERATOR:
				return createBinaryOperatorFromString(eDataType, initialValue);
			case CodegenPackage.STRING_ITERABLE:
				return createStringIterableFromString(eDataType, initialValue);
			default:
				throw new IllegalArgumentException("The datatype '" + eDataType.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String convertToString(EDataType eDataType, Object instanceValue) {
		switch (eDataType.getClassifierID()) {
			case CodegenPackage.BASE_DATA_TYPE:
				return convertBaseDataTypeToString(eDataType, instanceValue);
			case CodegenPackage.ASSIGNMENT_OPERATOR:
				return convertAssignmentOperatorToString(eDataType, instanceValue);
			case CodegenPackage.UNARY_OPERATOR:
				return convertUnaryOperatorToString(eDataType, instanceValue);
			case CodegenPackage.BINARY_OPERATOR:
				return convertBinaryOperatorToString(eDataType, instanceValue);
			case CodegenPackage.STRING_ITERABLE:
				return convertStringIterableToString(eDataType, instanceValue);
			default:
				throw new IllegalArgumentException("The datatype '" + eDataType.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Program createProgram() {
		ProgramImpl program = new ProgramImpl();
		return program;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Include createInclude() {
		IncludeImpl include = new IncludeImpl();
		return include;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DataType createDataType() {
		DataTypeImpl dataType = new DataTypeImpl();
		return dataType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public VariableDecl createVariableDecl() {
		VariableDeclImpl variableDecl = new VariableDeclImpl();
		return variableDecl;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Function createFunction() {
		FunctionImpl function = new FunctionImpl();
		return function;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Parameter createParameter() {
		ParameterImpl parameter = new ParameterImpl();
		return parameter;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EmptyLineStmt createEmptyLineStmt() {
		EmptyLineStmtImpl emptyLineStmt = new EmptyLineStmtImpl();
		return emptyLineStmt;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public CommentStmt createCommentStmt() {
		CommentStmtImpl commentStmt = new CommentStmtImpl();
		return commentStmt;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ExpressionStmt createExpressionStmt() {
		ExpressionStmtImpl expressionStmt = new ExpressionStmtImpl();
		return expressionStmt;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public MacroStmt createMacroStmt() {
		MacroStmtImpl macroStmt = new MacroStmtImpl();
		return macroStmt;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public UndefStmt createUndefStmt() {
		UndefStmtImpl undefStmt = new UndefStmtImpl();
		return undefStmt;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public OmpStmt createOmpStmt() {
		OmpStmtImpl ompStmt = new OmpStmtImpl();
		return ompStmt;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public IfStmt createIfStmt() {
		IfStmtImpl ifStmt = new IfStmtImpl();
		return ifStmt;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Branch createBranch() {
		BranchImpl branch = new BranchImpl();
		return branch;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ConditionalBranch createConditionalBranch() {
		ConditionalBranchImpl conditionalBranch = new ConditionalBranchImpl();
		return conditionalBranch;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public LoopStmt createLoopStmt() {
		LoopStmtImpl loopStmt = new LoopStmtImpl();
		return loopStmt;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public AssignmentStmt createAssignmentStmt() {
		AssignmentStmtImpl assignmentStmt = new AssignmentStmtImpl();
		return assignmentStmt;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ReturnStmt createReturnStmt() {
		ReturnStmtImpl returnStmt = new ReturnStmtImpl();
		return returnStmt;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public CustomExpr createCustomExpr() {
		CustomExprImpl customExpr = new CustomExprImpl();
		return customExpr;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ParenthesizedExpr createParenthesizedExpr() {
		ParenthesizedExprImpl parenthesizedExpr = new ParenthesizedExprImpl();
		return parenthesizedExpr;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public CastExpr createCastExpr() {
		CastExprImpl castExpr = new CastExprImpl();
		return castExpr;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ArrayAccessExpr createArrayAccessExpr() {
		ArrayAccessExprImpl arrayAccessExpr = new ArrayAccessExprImpl();
		return arrayAccessExpr;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public CallExpr createCallExpr() {
		CallExprImpl callExpr = new CallExprImpl();
		return callExpr;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public UnaryExpr createUnaryExpr() {
		UnaryExprImpl unaryExpr = new UnaryExprImpl();
		return unaryExpr;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public BinaryExpr createBinaryExpr() {
		BinaryExprImpl binaryExpr = new BinaryExprImpl();
		return binaryExpr;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TernaryExpr createTernaryExpr() {
		TernaryExprImpl ternaryExpr = new TernaryExprImpl();
		return ternaryExpr;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public BaseDataType createBaseDataTypeFromString(EDataType eDataType, String initialValue) {
		BaseDataType result = BaseDataType.get(initialValue);
		if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertBaseDataTypeToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public AssignmentOperator createAssignmentOperatorFromString(EDataType eDataType, String initialValue) {
		AssignmentOperator result = AssignmentOperator.get(initialValue);
		if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertAssignmentOperatorToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public UnaryOperator createUnaryOperatorFromString(EDataType eDataType, String initialValue) {
		UnaryOperator result = UnaryOperator.get(initialValue);
		if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertUnaryOperatorToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public BinaryOperator createBinaryOperatorFromString(EDataType eDataType, String initialValue) {
		BinaryOperator result = BinaryOperator.get(initialValue);
		if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertBinaryOperatorToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	public Iterable<String> createStringIterableFromString(EDataType eDataType, String initialValue) {
		return (Iterable<String>)super.createFromString(initialValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertStringIterableToString(EDataType eDataType, Object instanceValue) {
		return super.convertToString(instanceValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public CodegenPackage getCodegenPackage() {
		return (CodegenPackage)getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static CodegenPackage getPackage() {
		return CodegenPackage.eINSTANCE;
	}

} //CodegenFactoryImpl
