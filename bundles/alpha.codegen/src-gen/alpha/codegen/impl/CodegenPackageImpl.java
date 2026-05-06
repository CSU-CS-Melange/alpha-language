/**
 */
package alpha.codegen.impl;

import alpha.codegen.ArrayAccessExpr;
import alpha.codegen.AssignmentOperator;
import alpha.codegen.AssignmentStmt;
import alpha.codegen.BaseDataType;
import alpha.codegen.BinaryExpr;
import alpha.codegen.BinaryOperator;
import alpha.codegen.Branch;
import alpha.codegen.CallExpr;
import alpha.codegen.CastExpr;
import alpha.codegen.CodegenFactory;
import alpha.codegen.CodegenPackage;
import alpha.codegen.CommentStmt;
import alpha.codegen.ConditionalBranch;
import alpha.codegen.CustomExpr;
import alpha.codegen.DataType;
import alpha.codegen.EmptyLineStmt;
import alpha.codegen.Expression;
import alpha.codegen.ExpressionStmt;
import alpha.codegen.Function;
import alpha.codegen.IfStmt;
import alpha.codegen.Include;
import alpha.codegen.LoopStmt;
import alpha.codegen.MacroStmt;
import alpha.codegen.OmpStmt;
import alpha.codegen.Parameter;
import alpha.codegen.ParenthesizedExpr;
import alpha.codegen.Program;
import alpha.codegen.ReturnStmt;
import alpha.codegen.Statement;
import alpha.codegen.TernaryExpr;
import alpha.codegen.UnaryExpr;
import alpha.codegen.UnaryOperator;
import alpha.codegen.UndefStmt;
import alpha.codegen.VariableDecl;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EOperation;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EcorePackage;

import org.eclipse.emf.ecore.impl.EPackageImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class CodegenPackageImpl extends EPackageImpl implements CodegenPackage {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass programEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass includeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass dataTypeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass variableDeclEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass functionEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass parameterEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass statementEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass emptyLineStmtEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass commentStmtEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass expressionStmtEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass macroStmtEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass undefStmtEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass ompStmtEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass ifStmtEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass branchEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass conditionalBranchEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass loopStmtEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass assignmentStmtEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass returnStmtEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass expressionEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass customExprEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass parenthesizedExprEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass castExprEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass arrayAccessExprEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass callExprEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass unaryExprEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass binaryExprEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass ternaryExprEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum baseDataTypeEEnum = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum assignmentOperatorEEnum = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum unaryOperatorEEnum = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum binaryOperatorEEnum = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EDataType stringIterableEDataType = null;

	/**
	 * Creates an instance of the model <b>Package</b>, registered with
	 * {@link org.eclipse.emf.ecore.EPackage.Registry EPackage.Registry} by the package
	 * package URI value.
	 * <p>Note: the correct way to create the package is via the static
	 * factory method {@link #init init()}, which also performs
	 * initialization of the package, or returns the registered package,
	 * if one already exists.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.ecore.EPackage.Registry
	 * @see alpha.codegen.CodegenPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private CodegenPackageImpl() {
		super(eNS_URI, CodegenFactory.eINSTANCE);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static boolean isInited = false;

	/**
	 * Creates, registers, and initializes the <b>Package</b> for this model, and for any others upon which it depends.
	 *
	 * <p>This method is used to initialize {@link CodegenPackage#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static CodegenPackage init() {
		if (isInited) return (CodegenPackage)EPackage.Registry.INSTANCE.getEPackage(CodegenPackage.eNS_URI);

		// Obtain or create and register package
		Object registeredCodegenPackage = EPackage.Registry.INSTANCE.get(eNS_URI);
		CodegenPackageImpl theCodegenPackage = registeredCodegenPackage instanceof CodegenPackageImpl ? (CodegenPackageImpl)registeredCodegenPackage : new CodegenPackageImpl();

		isInited = true;

		// Initialize simple dependencies
		EcorePackage.eINSTANCE.eClass();

		// Create package meta-data objects
		theCodegenPackage.createPackageContents();

		// Initialize created meta-data
		theCodegenPackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theCodegenPackage.freeze();

		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(CodegenPackage.eNS_URI, theCodegenPackage);
		return theCodegenPackage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getProgram() {
		return programEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getProgram_HeaderComment() {
		return (EReference)programEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getProgram_Includes() {
		return (EReference)programEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getProgram_FunctionMacros() {
		return (EReference)programEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getProgram_GlobalVariables() {
		return (EReference)programEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getProgram_MemoryMacros() {
		return (EReference)programEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getProgram_Functions() {
		return (EReference)programEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getInclude() {
		return includeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getInclude_File() {
		return (EAttribute)includeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getInclude_UseQuotes() {
		return (EAttribute)includeEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getDataType() {
		return dataTypeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getDataType_BaseType() {
		return (EAttribute)dataTypeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getDataType_IndirectionLevel() {
		return (EAttribute)dataTypeEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getVariableDecl() {
		return variableDeclEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getVariableDecl_IsStatic() {
		return (EAttribute)variableDeclEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getVariableDecl_DataType() {
		return (EReference)variableDeclEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getVariableDecl_Name() {
		return (EAttribute)variableDeclEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getFunction() {
		return functionEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getFunction_IsStatic() {
		return (EAttribute)functionEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getFunction_IsInline() {
		return (EAttribute)functionEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getFunction_ReturnType() {
		return (EReference)functionEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getFunction_Name() {
		return (EAttribute)functionEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getFunction_Parameters() {
		return (EReference)functionEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getFunction_Declarations() {
		return (EReference)functionEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getFunction_Statements() {
		return (EReference)functionEClass.getEStructuralFeatures().get(6);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getParameter() {
		return parameterEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getParameter_DataType() {
		return (EReference)parameterEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getParameter_Name() {
		return (EAttribute)parameterEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getStatement() {
		return statementEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getEmptyLineStmt() {
		return emptyLineStmtEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getCommentStmt() {
		return commentStmtEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getCommentStmt_CommentLines() {
		return (EAttribute)commentStmtEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getCommentStmt__AddAll__Iterable() {
		return commentStmtEClass.getEOperations().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getExpressionStmt() {
		return expressionStmtEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getExpressionStmt_Expression() {
		return (EReference)expressionStmtEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getMacroStmt() {
		return macroStmtEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getMacroStmt_Name() {
		return (EAttribute)macroStmtEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getMacroStmt_Arguments() {
		return (EAttribute)macroStmtEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getMacroStmt_Replacement() {
		return (EReference)macroStmtEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getUndefStmt() {
		return undefStmtEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getUndefStmt_Name() {
		return (EAttribute)undefStmtEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getOmpStmt() {
		return ompStmtEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getOmpStmt_PrivateVars() {
		return (EAttribute)ompStmtEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getIfStmt() {
		return ifStmtEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getIfStmt_IfBranch() {
		return (EReference)ifStmtEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getIfStmt_ElseIfBranches() {
		return (EReference)ifStmtEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getIfStmt_ElseBranch() {
		return (EReference)ifStmtEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getBranch() {
		return branchEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getBranch_Body() {
		return (EReference)branchEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getConditionalBranch() {
		return conditionalBranchEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getConditionalBranch_Conditional() {
		return (EReference)conditionalBranchEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getLoopStmt() {
		return loopStmtEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getLoopStmt_LoopVariable() {
		return (EAttribute)loopStmtEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getLoopStmt_Initializer() {
		return (EReference)loopStmtEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getLoopStmt_Conditional() {
		return (EReference)loopStmtEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getLoopStmt_IncrementBy() {
		return (EReference)loopStmtEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getLoopStmt_Body() {
		return (EReference)loopStmtEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getAssignmentStmt() {
		return assignmentStmtEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getAssignmentStmt_Left() {
		return (EReference)assignmentStmtEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getAssignmentStmt_AssignType() {
		return (EAttribute)assignmentStmtEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getAssignmentStmt_Right() {
		return (EReference)assignmentStmtEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getReturnStmt() {
		return returnStmtEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getReturnStmt_Expression() {
		return (EReference)returnStmtEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getExpression() {
		return expressionEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getCustomExpr() {
		return customExprEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getCustomExpr_Expression() {
		return (EAttribute)customExprEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getParenthesizedExpr() {
		return parenthesizedExprEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getParenthesizedExpr_Expression() {
		return (EReference)parenthesizedExprEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getCastExpr() {
		return castExprEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getCastExpr_DataType() {
		return (EReference)castExprEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getCastExpr_Expression() {
		return (EReference)castExprEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getArrayAccessExpr() {
		return arrayAccessExprEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getArrayAccessExpr_VariableName() {
		return (EAttribute)arrayAccessExprEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getArrayAccessExpr_IndexExpressions() {
		return (EReference)arrayAccessExprEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getCallExpr() {
		return callExprEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getCallExpr_FunctionName() {
		return (EAttribute)callExprEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getCallExpr_Arguments() {
		return (EReference)callExprEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getUnaryExpr() {
		return unaryExprEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getUnaryExpr_Operator() {
		return (EAttribute)unaryExprEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getUnaryExpr_Expression() {
		return (EReference)unaryExprEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getBinaryExpr() {
		return binaryExprEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getBinaryExpr_Operator() {
		return (EAttribute)binaryExprEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getBinaryExpr_Left() {
		return (EReference)binaryExprEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getBinaryExpr_Right() {
		return (EReference)binaryExprEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getTernaryExpr() {
		return ternaryExprEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getTernaryExpr_Conditional() {
		return (EReference)ternaryExprEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getTernaryExpr_ThenExpr() {
		return (EReference)ternaryExprEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getTernaryExpr_ElseExpr() {
		return (EReference)ternaryExprEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EEnum getBaseDataType() {
		return baseDataTypeEEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EEnum getAssignmentOperator() {
		return assignmentOperatorEEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EEnum getUnaryOperator() {
		return unaryOperatorEEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EEnum getBinaryOperator() {
		return binaryOperatorEEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EDataType getStringIterable() {
		return stringIterableEDataType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public CodegenFactory getCodegenFactory() {
		return (CodegenFactory)getEFactoryInstance();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private boolean isCreated = false;

	/**
	 * Creates the meta-model objects for the package.  This method is
	 * guarded to have no affect on any invocation but its first.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void createPackageContents() {
		if (isCreated) return;
		isCreated = true;

		// Create classes and their features
		programEClass = createEClass(PROGRAM);
		createEReference(programEClass, PROGRAM__HEADER_COMMENT);
		createEReference(programEClass, PROGRAM__INCLUDES);
		createEReference(programEClass, PROGRAM__FUNCTION_MACROS);
		createEReference(programEClass, PROGRAM__GLOBAL_VARIABLES);
		createEReference(programEClass, PROGRAM__MEMORY_MACROS);
		createEReference(programEClass, PROGRAM__FUNCTIONS);

		includeEClass = createEClass(INCLUDE);
		createEAttribute(includeEClass, INCLUDE__FILE);
		createEAttribute(includeEClass, INCLUDE__USE_QUOTES);

		dataTypeEClass = createEClass(DATA_TYPE);
		createEAttribute(dataTypeEClass, DATA_TYPE__BASE_TYPE);
		createEAttribute(dataTypeEClass, DATA_TYPE__INDIRECTION_LEVEL);

		variableDeclEClass = createEClass(VARIABLE_DECL);
		createEAttribute(variableDeclEClass, VARIABLE_DECL__IS_STATIC);
		createEReference(variableDeclEClass, VARIABLE_DECL__DATA_TYPE);
		createEAttribute(variableDeclEClass, VARIABLE_DECL__NAME);

		functionEClass = createEClass(FUNCTION);
		createEAttribute(functionEClass, FUNCTION__IS_STATIC);
		createEAttribute(functionEClass, FUNCTION__IS_INLINE);
		createEReference(functionEClass, FUNCTION__RETURN_TYPE);
		createEAttribute(functionEClass, FUNCTION__NAME);
		createEReference(functionEClass, FUNCTION__PARAMETERS);
		createEReference(functionEClass, FUNCTION__DECLARATIONS);
		createEReference(functionEClass, FUNCTION__STATEMENTS);

		parameterEClass = createEClass(PARAMETER);
		createEReference(parameterEClass, PARAMETER__DATA_TYPE);
		createEAttribute(parameterEClass, PARAMETER__NAME);

		statementEClass = createEClass(STATEMENT);

		emptyLineStmtEClass = createEClass(EMPTY_LINE_STMT);

		commentStmtEClass = createEClass(COMMENT_STMT);
		createEAttribute(commentStmtEClass, COMMENT_STMT__COMMENT_LINES);
		createEOperation(commentStmtEClass, COMMENT_STMT___ADD_ALL__ITERABLE);

		expressionStmtEClass = createEClass(EXPRESSION_STMT);
		createEReference(expressionStmtEClass, EXPRESSION_STMT__EXPRESSION);

		macroStmtEClass = createEClass(MACRO_STMT);
		createEAttribute(macroStmtEClass, MACRO_STMT__NAME);
		createEAttribute(macroStmtEClass, MACRO_STMT__ARGUMENTS);
		createEReference(macroStmtEClass, MACRO_STMT__REPLACEMENT);

		undefStmtEClass = createEClass(UNDEF_STMT);
		createEAttribute(undefStmtEClass, UNDEF_STMT__NAME);

		ompStmtEClass = createEClass(OMP_STMT);
		createEAttribute(ompStmtEClass, OMP_STMT__PRIVATE_VARS);

		ifStmtEClass = createEClass(IF_STMT);
		createEReference(ifStmtEClass, IF_STMT__IF_BRANCH);
		createEReference(ifStmtEClass, IF_STMT__ELSE_IF_BRANCHES);
		createEReference(ifStmtEClass, IF_STMT__ELSE_BRANCH);

		branchEClass = createEClass(BRANCH);
		createEReference(branchEClass, BRANCH__BODY);

		conditionalBranchEClass = createEClass(CONDITIONAL_BRANCH);
		createEReference(conditionalBranchEClass, CONDITIONAL_BRANCH__CONDITIONAL);

		loopStmtEClass = createEClass(LOOP_STMT);
		createEAttribute(loopStmtEClass, LOOP_STMT__LOOP_VARIABLE);
		createEReference(loopStmtEClass, LOOP_STMT__INITIALIZER);
		createEReference(loopStmtEClass, LOOP_STMT__CONDITIONAL);
		createEReference(loopStmtEClass, LOOP_STMT__INCREMENT_BY);
		createEReference(loopStmtEClass, LOOP_STMT__BODY);

		assignmentStmtEClass = createEClass(ASSIGNMENT_STMT);
		createEReference(assignmentStmtEClass, ASSIGNMENT_STMT__LEFT);
		createEAttribute(assignmentStmtEClass, ASSIGNMENT_STMT__ASSIGN_TYPE);
		createEReference(assignmentStmtEClass, ASSIGNMENT_STMT__RIGHT);

		returnStmtEClass = createEClass(RETURN_STMT);
		createEReference(returnStmtEClass, RETURN_STMT__EXPRESSION);

		expressionEClass = createEClass(EXPRESSION);

		customExprEClass = createEClass(CUSTOM_EXPR);
		createEAttribute(customExprEClass, CUSTOM_EXPR__EXPRESSION);

		parenthesizedExprEClass = createEClass(PARENTHESIZED_EXPR);
		createEReference(parenthesizedExprEClass, PARENTHESIZED_EXPR__EXPRESSION);

		castExprEClass = createEClass(CAST_EXPR);
		createEReference(castExprEClass, CAST_EXPR__DATA_TYPE);
		createEReference(castExprEClass, CAST_EXPR__EXPRESSION);

		arrayAccessExprEClass = createEClass(ARRAY_ACCESS_EXPR);
		createEAttribute(arrayAccessExprEClass, ARRAY_ACCESS_EXPR__VARIABLE_NAME);
		createEReference(arrayAccessExprEClass, ARRAY_ACCESS_EXPR__INDEX_EXPRESSIONS);

		callExprEClass = createEClass(CALL_EXPR);
		createEAttribute(callExprEClass, CALL_EXPR__FUNCTION_NAME);
		createEReference(callExprEClass, CALL_EXPR__ARGUMENTS);

		unaryExprEClass = createEClass(UNARY_EXPR);
		createEAttribute(unaryExprEClass, UNARY_EXPR__OPERATOR);
		createEReference(unaryExprEClass, UNARY_EXPR__EXPRESSION);

		binaryExprEClass = createEClass(BINARY_EXPR);
		createEAttribute(binaryExprEClass, BINARY_EXPR__OPERATOR);
		createEReference(binaryExprEClass, BINARY_EXPR__LEFT);
		createEReference(binaryExprEClass, BINARY_EXPR__RIGHT);

		ternaryExprEClass = createEClass(TERNARY_EXPR);
		createEReference(ternaryExprEClass, TERNARY_EXPR__CONDITIONAL);
		createEReference(ternaryExprEClass, TERNARY_EXPR__THEN_EXPR);
		createEReference(ternaryExprEClass, TERNARY_EXPR__ELSE_EXPR);

		// Create enums
		baseDataTypeEEnum = createEEnum(BASE_DATA_TYPE);
		assignmentOperatorEEnum = createEEnum(ASSIGNMENT_OPERATOR);
		unaryOperatorEEnum = createEEnum(UNARY_OPERATOR);
		binaryOperatorEEnum = createEEnum(BINARY_OPERATOR);

		// Create data types
		stringIterableEDataType = createEDataType(STRING_ITERABLE);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private boolean isInitialized = false;

	/**
	 * Complete the initialization of the package and its meta-model.  This
	 * method is guarded to have no affect on any invocation but its first.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void initializePackageContents() {
		if (isInitialized) return;
		isInitialized = true;

		// Initialize package
		setName(eNAME);
		setNsPrefix(eNS_PREFIX);
		setNsURI(eNS_URI);

		// Obtain other dependent packages
		EcorePackage theEcorePackage = (EcorePackage)EPackage.Registry.INSTANCE.getEPackage(EcorePackage.eNS_URI);

		// Create type parameters

		// Set bounds for type parameters

		// Add supertypes to classes
		emptyLineStmtEClass.getESuperTypes().add(this.getStatement());
		commentStmtEClass.getESuperTypes().add(this.getStatement());
		expressionStmtEClass.getESuperTypes().add(this.getStatement());
		macroStmtEClass.getESuperTypes().add(this.getStatement());
		undefStmtEClass.getESuperTypes().add(this.getStatement());
		ompStmtEClass.getESuperTypes().add(this.getStatement());
		ifStmtEClass.getESuperTypes().add(this.getStatement());
		conditionalBranchEClass.getESuperTypes().add(this.getBranch());
		loopStmtEClass.getESuperTypes().add(this.getStatement());
		assignmentStmtEClass.getESuperTypes().add(this.getStatement());
		assignmentStmtEClass.getESuperTypes().add(this.getExpression());
		returnStmtEClass.getESuperTypes().add(this.getStatement());
		customExprEClass.getESuperTypes().add(this.getExpression());
		parenthesizedExprEClass.getESuperTypes().add(this.getExpression());
		castExprEClass.getESuperTypes().add(this.getExpression());
		arrayAccessExprEClass.getESuperTypes().add(this.getExpression());
		callExprEClass.getESuperTypes().add(this.getExpression());
		unaryExprEClass.getESuperTypes().add(this.getExpression());
		binaryExprEClass.getESuperTypes().add(this.getExpression());
		ternaryExprEClass.getESuperTypes().add(this.getExpression());

		// Initialize classes, features, and operations; add parameters
		initEClass(programEClass, Program.class, "Program", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getProgram_HeaderComment(), this.getCommentStmt(), null, "headerComment", null, 0, 1, Program.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getProgram_Includes(), this.getInclude(), null, "includes", null, 0, -1, Program.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getProgram_FunctionMacros(), this.getMacroStmt(), null, "functionMacros", null, 0, -1, Program.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getProgram_GlobalVariables(), this.getVariableDecl(), null, "globalVariables", null, 0, -1, Program.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getProgram_MemoryMacros(), this.getMacroStmt(), null, "memoryMacros", null, 0, -1, Program.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getProgram_Functions(), this.getFunction(), null, "functions", null, 0, -1, Program.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(includeEClass, Include.class, "Include", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getInclude_File(), theEcorePackage.getEString(), "file", null, 0, 1, Include.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getInclude_UseQuotes(), theEcorePackage.getEBoolean(), "useQuotes", null, 0, 1, Include.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(dataTypeEClass, DataType.class, "DataType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getDataType_BaseType(), this.getBaseDataType(), "baseType", null, 0, 1, DataType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getDataType_IndirectionLevel(), theEcorePackage.getEInt(), "indirectionLevel", null, 0, 1, DataType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(variableDeclEClass, VariableDecl.class, "VariableDecl", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getVariableDecl_IsStatic(), theEcorePackage.getEBoolean(), "isStatic", null, 0, 1, VariableDecl.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getVariableDecl_DataType(), this.getDataType(), null, "dataType", null, 0, 1, VariableDecl.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getVariableDecl_Name(), theEcorePackage.getEString(), "name", null, 0, 1, VariableDecl.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(functionEClass, Function.class, "Function", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getFunction_IsStatic(), theEcorePackage.getEBooleanObject(), "isStatic", null, 0, 1, Function.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getFunction_IsInline(), theEcorePackage.getEBooleanObject(), "isInline", null, 0, 1, Function.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getFunction_ReturnType(), this.getDataType(), null, "returnType", null, 0, 1, Function.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getFunction_Name(), theEcorePackage.getEString(), "name", null, 0, 1, Function.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getFunction_Parameters(), this.getParameter(), null, "parameters", null, 0, -1, Function.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getFunction_Declarations(), this.getVariableDecl(), null, "declarations", null, 0, -1, Function.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getFunction_Statements(), this.getStatement(), null, "statements", null, 0, -1, Function.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(parameterEClass, Parameter.class, "Parameter", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getParameter_DataType(), this.getDataType(), null, "dataType", null, 0, 1, Parameter.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getParameter_Name(), theEcorePackage.getEString(), "name", null, 0, 1, Parameter.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(statementEClass, Statement.class, "Statement", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(emptyLineStmtEClass, EmptyLineStmt.class, "EmptyLineStmt", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(commentStmtEClass, CommentStmt.class, "CommentStmt", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getCommentStmt_CommentLines(), theEcorePackage.getEString(), "commentLines", null, 0, -1, CommentStmt.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		EOperation op = initEOperation(getCommentStmt__AddAll__Iterable(), null, "addAll", 0, 1, !IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getStringIterable(), "lines", 0, 1, !IS_UNIQUE, IS_ORDERED);

		initEClass(expressionStmtEClass, ExpressionStmt.class, "ExpressionStmt", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getExpressionStmt_Expression(), this.getExpression(), null, "expression", null, 0, 1, ExpressionStmt.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(macroStmtEClass, MacroStmt.class, "MacroStmt", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getMacroStmt_Name(), theEcorePackage.getEString(), "name", null, 0, 1, MacroStmt.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getMacroStmt_Arguments(), theEcorePackage.getEString(), "arguments", null, 0, -1, MacroStmt.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getMacroStmt_Replacement(), this.getExpression(), null, "replacement", null, 0, 1, MacroStmt.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(undefStmtEClass, UndefStmt.class, "UndefStmt", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getUndefStmt_Name(), theEcorePackage.getEString(), "name", null, 0, 1, UndefStmt.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(ompStmtEClass, OmpStmt.class, "OmpStmt", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getOmpStmt_PrivateVars(), theEcorePackage.getEString(), "privateVars", null, 0, -1, OmpStmt.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(ifStmtEClass, IfStmt.class, "IfStmt", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getIfStmt_IfBranch(), this.getConditionalBranch(), null, "ifBranch", null, 0, 1, IfStmt.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getIfStmt_ElseIfBranches(), this.getConditionalBranch(), null, "elseIfBranches", null, 0, -1, IfStmt.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getIfStmt_ElseBranch(), this.getBranch(), null, "elseBranch", null, 0, 1, IfStmt.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(branchEClass, Branch.class, "Branch", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getBranch_Body(), this.getStatement(), null, "body", null, 0, -1, Branch.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(conditionalBranchEClass, ConditionalBranch.class, "ConditionalBranch", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getConditionalBranch_Conditional(), this.getExpression(), null, "conditional", null, 0, 1, ConditionalBranch.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(loopStmtEClass, LoopStmt.class, "LoopStmt", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getLoopStmt_LoopVariable(), theEcorePackage.getEString(), "loopVariable", null, 0, 1, LoopStmt.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getLoopStmt_Initializer(), this.getExpression(), null, "initializer", null, 0, 1, LoopStmt.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getLoopStmt_Conditional(), this.getExpression(), null, "conditional", null, 0, 1, LoopStmt.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getLoopStmt_IncrementBy(), this.getExpression(), null, "incrementBy", null, 0, 1, LoopStmt.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getLoopStmt_Body(), this.getStatement(), null, "body", null, 0, -1, LoopStmt.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(assignmentStmtEClass, AssignmentStmt.class, "AssignmentStmt", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getAssignmentStmt_Left(), this.getExpression(), null, "left", null, 0, 1, AssignmentStmt.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getAssignmentStmt_AssignType(), this.getAssignmentOperator(), "assignType", null, 0, 1, AssignmentStmt.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getAssignmentStmt_Right(), this.getExpression(), null, "right", null, 0, 1, AssignmentStmt.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(returnStmtEClass, ReturnStmt.class, "ReturnStmt", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getReturnStmt_Expression(), this.getExpression(), null, "expression", null, 0, 1, ReturnStmt.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(expressionEClass, Expression.class, "Expression", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(customExprEClass, CustomExpr.class, "CustomExpr", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getCustomExpr_Expression(), theEcorePackage.getEString(), "expression", null, 0, 1, CustomExpr.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(parenthesizedExprEClass, ParenthesizedExpr.class, "ParenthesizedExpr", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getParenthesizedExpr_Expression(), this.getExpression(), null, "expression", null, 0, 1, ParenthesizedExpr.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(castExprEClass, CastExpr.class, "CastExpr", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getCastExpr_DataType(), this.getDataType(), null, "dataType", null, 0, 1, CastExpr.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getCastExpr_Expression(), this.getExpression(), null, "expression", null, 0, 1, CastExpr.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(arrayAccessExprEClass, ArrayAccessExpr.class, "ArrayAccessExpr", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getArrayAccessExpr_VariableName(), theEcorePackage.getEString(), "variableName", null, 0, 1, ArrayAccessExpr.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getArrayAccessExpr_IndexExpressions(), this.getExpression(), null, "indexExpressions", null, 0, -1, ArrayAccessExpr.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(callExprEClass, CallExpr.class, "CallExpr", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getCallExpr_FunctionName(), theEcorePackage.getEString(), "functionName", null, 0, 1, CallExpr.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getCallExpr_Arguments(), this.getExpression(), null, "arguments", null, 0, -1, CallExpr.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(unaryExprEClass, UnaryExpr.class, "UnaryExpr", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getUnaryExpr_Operator(), this.getUnaryOperator(), "operator", null, 0, 1, UnaryExpr.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getUnaryExpr_Expression(), this.getExpression(), null, "expression", null, 0, 1, UnaryExpr.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(binaryExprEClass, BinaryExpr.class, "BinaryExpr", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getBinaryExpr_Operator(), this.getBinaryOperator(), "operator", null, 0, 1, BinaryExpr.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getBinaryExpr_Left(), this.getExpression(), null, "left", null, 0, 1, BinaryExpr.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getBinaryExpr_Right(), this.getExpression(), null, "right", null, 0, 1, BinaryExpr.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(ternaryExprEClass, TernaryExpr.class, "TernaryExpr", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getTernaryExpr_Conditional(), this.getExpression(), null, "conditional", null, 0, 1, TernaryExpr.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getTernaryExpr_ThenExpr(), this.getExpression(), null, "thenExpr", null, 0, 1, TernaryExpr.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getTernaryExpr_ElseExpr(), this.getExpression(), null, "elseExpr", null, 0, 1, TernaryExpr.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		// Initialize enums and add enum literals
		initEEnum(baseDataTypeEEnum, BaseDataType.class, "BaseDataType");
		addEEnumLiteral(baseDataTypeEEnum, BaseDataType.VOID);
		addEEnumLiteral(baseDataTypeEEnum, BaseDataType.CHAR);
		addEEnumLiteral(baseDataTypeEEnum, BaseDataType.INT);
		addEEnumLiteral(baseDataTypeEEnum, BaseDataType.LONG);
		addEEnumLiteral(baseDataTypeEEnum, BaseDataType.FLOAT);
		addEEnumLiteral(baseDataTypeEEnum, BaseDataType.DOUBLE);

		initEEnum(assignmentOperatorEEnum, AssignmentOperator.class, "AssignmentOperator");
		addEEnumLiteral(assignmentOperatorEEnum, AssignmentOperator.STANDARD);
		addEEnumLiteral(assignmentOperatorEEnum, AssignmentOperator.PLUS);
		addEEnumLiteral(assignmentOperatorEEnum, AssignmentOperator.TIMES);

		initEEnum(unaryOperatorEEnum, UnaryOperator.class, "UnaryOperator");
		addEEnumLiteral(unaryOperatorEEnum, UnaryOperator.NEGATE);
		addEEnumLiteral(unaryOperatorEEnum, UnaryOperator.NOT);

		initEEnum(binaryOperatorEEnum, BinaryOperator.class, "BinaryOperator");
		addEEnumLiteral(binaryOperatorEEnum, BinaryOperator.PLUS);
		addEEnumLiteral(binaryOperatorEEnum, BinaryOperator.MINUS);
		addEEnumLiteral(binaryOperatorEEnum, BinaryOperator.TIMES);
		addEEnumLiteral(binaryOperatorEEnum, BinaryOperator.DIVIDE);
		addEEnumLiteral(binaryOperatorEEnum, BinaryOperator.MODULUS);
		addEEnumLiteral(binaryOperatorEEnum, BinaryOperator.MIN);
		addEEnumLiteral(binaryOperatorEEnum, BinaryOperator.MAX);
		addEEnumLiteral(binaryOperatorEEnum, BinaryOperator.AND);
		addEEnumLiteral(binaryOperatorEEnum, BinaryOperator.OR);
		addEEnumLiteral(binaryOperatorEEnum, BinaryOperator.LT);
		addEEnumLiteral(binaryOperatorEEnum, BinaryOperator.LEQ);
		addEEnumLiteral(binaryOperatorEEnum, BinaryOperator.GT);
		addEEnumLiteral(binaryOperatorEEnum, BinaryOperator.GEQ);
		addEEnumLiteral(binaryOperatorEEnum, BinaryOperator.EQ);
		addEEnumLiteral(binaryOperatorEEnum, BinaryOperator.NEQ);

		// Initialize data types
		initEDataType(stringIterableEDataType, Iterable.class, "StringIterable", IS_SERIALIZABLE, !IS_GENERATED_INSTANCE_CLASS, "java.lang.Iterable<java.lang.String>");

		// Create resource
		createResource(eNS_URI);
	}

} //CodegenPackageImpl
