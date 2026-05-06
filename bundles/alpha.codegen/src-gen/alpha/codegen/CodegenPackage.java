/**
 */
package alpha.codegen;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EOperation;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

/**
 * <!-- begin-user-doc -->
 * The <b>Package</b> for the model.
 * It contains accessors for the meta objects to represent
 * <ul>
 *   <li>each class,</li>
 *   <li>each feature of each class,</li>
 *   <li>each operation of each class,</li>
 *   <li>each enum,</li>
 *   <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc -->
 * @see alpha.codegen.CodegenFactory
 * @model kind="package"
 * @generated
 */
public interface CodegenPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "codegen";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "alpha.codegen";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "codegen";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	CodegenPackage eINSTANCE = alpha.codegen.impl.CodegenPackageImpl.init();

	/**
	 * The meta object id for the '{@link alpha.codegen.impl.ProgramImpl <em>Program</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see alpha.codegen.impl.ProgramImpl
	 * @see alpha.codegen.impl.CodegenPackageImpl#getProgram()
	 * @generated
	 */
	int PROGRAM = 0;

	/**
	 * The feature id for the '<em><b>Header Comment</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROGRAM__HEADER_COMMENT = 0;

	/**
	 * The feature id for the '<em><b>Includes</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROGRAM__INCLUDES = 1;

	/**
	 * The feature id for the '<em><b>Function Macros</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROGRAM__FUNCTION_MACROS = 2;

	/**
	 * The feature id for the '<em><b>Global Variables</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROGRAM__GLOBAL_VARIABLES = 3;

	/**
	 * The feature id for the '<em><b>Memory Macros</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROGRAM__MEMORY_MACROS = 4;

	/**
	 * The feature id for the '<em><b>Functions</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROGRAM__FUNCTIONS = 5;

	/**
	 * The number of structural features of the '<em>Program</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROGRAM_FEATURE_COUNT = 6;

	/**
	 * The number of operations of the '<em>Program</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROGRAM_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link alpha.codegen.impl.IncludeImpl <em>Include</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see alpha.codegen.impl.IncludeImpl
	 * @see alpha.codegen.impl.CodegenPackageImpl#getInclude()
	 * @generated
	 */
	int INCLUDE = 1;

	/**
	 * The feature id for the '<em><b>File</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INCLUDE__FILE = 0;

	/**
	 * The feature id for the '<em><b>Use Quotes</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INCLUDE__USE_QUOTES = 1;

	/**
	 * The number of structural features of the '<em>Include</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INCLUDE_FEATURE_COUNT = 2;

	/**
	 * The number of operations of the '<em>Include</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INCLUDE_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link alpha.codegen.impl.DataTypeImpl <em>Data Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see alpha.codegen.impl.DataTypeImpl
	 * @see alpha.codegen.impl.CodegenPackageImpl#getDataType()
	 * @generated
	 */
	int DATA_TYPE = 2;

	/**
	 * The feature id for the '<em><b>Base Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DATA_TYPE__BASE_TYPE = 0;

	/**
	 * The feature id for the '<em><b>Indirection Level</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DATA_TYPE__INDIRECTION_LEVEL = 1;

	/**
	 * The number of structural features of the '<em>Data Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DATA_TYPE_FEATURE_COUNT = 2;

	/**
	 * The number of operations of the '<em>Data Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DATA_TYPE_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link alpha.codegen.impl.VariableDeclImpl <em>Variable Decl</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see alpha.codegen.impl.VariableDeclImpl
	 * @see alpha.codegen.impl.CodegenPackageImpl#getVariableDecl()
	 * @generated
	 */
	int VARIABLE_DECL = 3;

	/**
	 * The feature id for the '<em><b>Is Static</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VARIABLE_DECL__IS_STATIC = 0;

	/**
	 * The feature id for the '<em><b>Data Type</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VARIABLE_DECL__DATA_TYPE = 1;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VARIABLE_DECL__NAME = 2;

	/**
	 * The number of structural features of the '<em>Variable Decl</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VARIABLE_DECL_FEATURE_COUNT = 3;

	/**
	 * The number of operations of the '<em>Variable Decl</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VARIABLE_DECL_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link alpha.codegen.impl.FunctionImpl <em>Function</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see alpha.codegen.impl.FunctionImpl
	 * @see alpha.codegen.impl.CodegenPackageImpl#getFunction()
	 * @generated
	 */
	int FUNCTION = 4;

	/**
	 * The feature id for the '<em><b>Is Static</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUNCTION__IS_STATIC = 0;

	/**
	 * The feature id for the '<em><b>Is Inline</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUNCTION__IS_INLINE = 1;

	/**
	 * The feature id for the '<em><b>Return Type</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUNCTION__RETURN_TYPE = 2;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUNCTION__NAME = 3;

	/**
	 * The feature id for the '<em><b>Parameters</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUNCTION__PARAMETERS = 4;

	/**
	 * The feature id for the '<em><b>Declarations</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUNCTION__DECLARATIONS = 5;

	/**
	 * The feature id for the '<em><b>Statements</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUNCTION__STATEMENTS = 6;

	/**
	 * The number of structural features of the '<em>Function</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUNCTION_FEATURE_COUNT = 7;

	/**
	 * The number of operations of the '<em>Function</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUNCTION_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link alpha.codegen.impl.ParameterImpl <em>Parameter</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see alpha.codegen.impl.ParameterImpl
	 * @see alpha.codegen.impl.CodegenPackageImpl#getParameter()
	 * @generated
	 */
	int PARAMETER = 5;

	/**
	 * The feature id for the '<em><b>Data Type</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARAMETER__DATA_TYPE = 0;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARAMETER__NAME = 1;

	/**
	 * The number of structural features of the '<em>Parameter</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARAMETER_FEATURE_COUNT = 2;

	/**
	 * The number of operations of the '<em>Parameter</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARAMETER_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link alpha.codegen.Statement <em>Statement</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see alpha.codegen.Statement
	 * @see alpha.codegen.impl.CodegenPackageImpl#getStatement()
	 * @generated
	 */
	int STATEMENT = 6;

	/**
	 * The number of structural features of the '<em>Statement</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATEMENT_FEATURE_COUNT = 0;

	/**
	 * The number of operations of the '<em>Statement</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATEMENT_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link alpha.codegen.impl.EmptyLineStmtImpl <em>Empty Line Stmt</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see alpha.codegen.impl.EmptyLineStmtImpl
	 * @see alpha.codegen.impl.CodegenPackageImpl#getEmptyLineStmt()
	 * @generated
	 */
	int EMPTY_LINE_STMT = 7;

	/**
	 * The number of structural features of the '<em>Empty Line Stmt</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EMPTY_LINE_STMT_FEATURE_COUNT = STATEMENT_FEATURE_COUNT + 0;

	/**
	 * The number of operations of the '<em>Empty Line Stmt</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EMPTY_LINE_STMT_OPERATION_COUNT = STATEMENT_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link alpha.codegen.impl.CommentStmtImpl <em>Comment Stmt</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see alpha.codegen.impl.CommentStmtImpl
	 * @see alpha.codegen.impl.CodegenPackageImpl#getCommentStmt()
	 * @generated
	 */
	int COMMENT_STMT = 8;

	/**
	 * The feature id for the '<em><b>Comment Lines</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMMENT_STMT__COMMENT_LINES = STATEMENT_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Comment Stmt</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMMENT_STMT_FEATURE_COUNT = STATEMENT_FEATURE_COUNT + 1;

	/**
	 * The operation id for the '<em>Add All</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMMENT_STMT___ADD_ALL__ITERABLE = STATEMENT_OPERATION_COUNT + 0;

	/**
	 * The number of operations of the '<em>Comment Stmt</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMMENT_STMT_OPERATION_COUNT = STATEMENT_OPERATION_COUNT + 1;

	/**
	 * The meta object id for the '{@link alpha.codegen.impl.ExpressionStmtImpl <em>Expression Stmt</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see alpha.codegen.impl.ExpressionStmtImpl
	 * @see alpha.codegen.impl.CodegenPackageImpl#getExpressionStmt()
	 * @generated
	 */
	int EXPRESSION_STMT = 9;

	/**
	 * The feature id for the '<em><b>Expression</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXPRESSION_STMT__EXPRESSION = STATEMENT_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Expression Stmt</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXPRESSION_STMT_FEATURE_COUNT = STATEMENT_FEATURE_COUNT + 1;

	/**
	 * The number of operations of the '<em>Expression Stmt</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXPRESSION_STMT_OPERATION_COUNT = STATEMENT_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link alpha.codegen.impl.MacroStmtImpl <em>Macro Stmt</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see alpha.codegen.impl.MacroStmtImpl
	 * @see alpha.codegen.impl.CodegenPackageImpl#getMacroStmt()
	 * @generated
	 */
	int MACRO_STMT = 10;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MACRO_STMT__NAME = STATEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Arguments</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MACRO_STMT__ARGUMENTS = STATEMENT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Replacement</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MACRO_STMT__REPLACEMENT = STATEMENT_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>Macro Stmt</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MACRO_STMT_FEATURE_COUNT = STATEMENT_FEATURE_COUNT + 3;

	/**
	 * The number of operations of the '<em>Macro Stmt</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MACRO_STMT_OPERATION_COUNT = STATEMENT_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link alpha.codegen.impl.UndefStmtImpl <em>Undef Stmt</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see alpha.codegen.impl.UndefStmtImpl
	 * @see alpha.codegen.impl.CodegenPackageImpl#getUndefStmt()
	 * @generated
	 */
	int UNDEF_STMT = 11;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int UNDEF_STMT__NAME = STATEMENT_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Undef Stmt</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int UNDEF_STMT_FEATURE_COUNT = STATEMENT_FEATURE_COUNT + 1;

	/**
	 * The number of operations of the '<em>Undef Stmt</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int UNDEF_STMT_OPERATION_COUNT = STATEMENT_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link alpha.codegen.impl.OmpStmtImpl <em>Omp Stmt</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see alpha.codegen.impl.OmpStmtImpl
	 * @see alpha.codegen.impl.CodegenPackageImpl#getOmpStmt()
	 * @generated
	 */
	int OMP_STMT = 12;

	/**
	 * The feature id for the '<em><b>Private Vars</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OMP_STMT__PRIVATE_VARS = STATEMENT_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Omp Stmt</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OMP_STMT_FEATURE_COUNT = STATEMENT_FEATURE_COUNT + 1;

	/**
	 * The number of operations of the '<em>Omp Stmt</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OMP_STMT_OPERATION_COUNT = STATEMENT_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link alpha.codegen.impl.IfStmtImpl <em>If Stmt</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see alpha.codegen.impl.IfStmtImpl
	 * @see alpha.codegen.impl.CodegenPackageImpl#getIfStmt()
	 * @generated
	 */
	int IF_STMT = 13;

	/**
	 * The feature id for the '<em><b>If Branch</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IF_STMT__IF_BRANCH = STATEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Else If Branches</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IF_STMT__ELSE_IF_BRANCHES = STATEMENT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Else Branch</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IF_STMT__ELSE_BRANCH = STATEMENT_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>If Stmt</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IF_STMT_FEATURE_COUNT = STATEMENT_FEATURE_COUNT + 3;

	/**
	 * The number of operations of the '<em>If Stmt</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IF_STMT_OPERATION_COUNT = STATEMENT_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link alpha.codegen.impl.BranchImpl <em>Branch</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see alpha.codegen.impl.BranchImpl
	 * @see alpha.codegen.impl.CodegenPackageImpl#getBranch()
	 * @generated
	 */
	int BRANCH = 14;

	/**
	 * The feature id for the '<em><b>Body</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BRANCH__BODY = 0;

	/**
	 * The number of structural features of the '<em>Branch</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BRANCH_FEATURE_COUNT = 1;

	/**
	 * The number of operations of the '<em>Branch</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BRANCH_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link alpha.codegen.impl.ConditionalBranchImpl <em>Conditional Branch</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see alpha.codegen.impl.ConditionalBranchImpl
	 * @see alpha.codegen.impl.CodegenPackageImpl#getConditionalBranch()
	 * @generated
	 */
	int CONDITIONAL_BRANCH = 15;

	/**
	 * The feature id for the '<em><b>Body</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONDITIONAL_BRANCH__BODY = BRANCH__BODY;

	/**
	 * The feature id for the '<em><b>Conditional</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONDITIONAL_BRANCH__CONDITIONAL = BRANCH_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Conditional Branch</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONDITIONAL_BRANCH_FEATURE_COUNT = BRANCH_FEATURE_COUNT + 1;

	/**
	 * The number of operations of the '<em>Conditional Branch</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONDITIONAL_BRANCH_OPERATION_COUNT = BRANCH_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link alpha.codegen.impl.LoopStmtImpl <em>Loop Stmt</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see alpha.codegen.impl.LoopStmtImpl
	 * @see alpha.codegen.impl.CodegenPackageImpl#getLoopStmt()
	 * @generated
	 */
	int LOOP_STMT = 16;

	/**
	 * The feature id for the '<em><b>Loop Variable</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LOOP_STMT__LOOP_VARIABLE = STATEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Initializer</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LOOP_STMT__INITIALIZER = STATEMENT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Conditional</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LOOP_STMT__CONDITIONAL = STATEMENT_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Increment By</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LOOP_STMT__INCREMENT_BY = STATEMENT_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Body</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LOOP_STMT__BODY = STATEMENT_FEATURE_COUNT + 4;

	/**
	 * The number of structural features of the '<em>Loop Stmt</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LOOP_STMT_FEATURE_COUNT = STATEMENT_FEATURE_COUNT + 5;

	/**
	 * The number of operations of the '<em>Loop Stmt</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LOOP_STMT_OPERATION_COUNT = STATEMENT_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link alpha.codegen.impl.AssignmentStmtImpl <em>Assignment Stmt</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see alpha.codegen.impl.AssignmentStmtImpl
	 * @see alpha.codegen.impl.CodegenPackageImpl#getAssignmentStmt()
	 * @generated
	 */
	int ASSIGNMENT_STMT = 17;

	/**
	 * The feature id for the '<em><b>Left</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ASSIGNMENT_STMT__LEFT = STATEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Assign Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ASSIGNMENT_STMT__ASSIGN_TYPE = STATEMENT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Right</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ASSIGNMENT_STMT__RIGHT = STATEMENT_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>Assignment Stmt</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ASSIGNMENT_STMT_FEATURE_COUNT = STATEMENT_FEATURE_COUNT + 3;

	/**
	 * The number of operations of the '<em>Assignment Stmt</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ASSIGNMENT_STMT_OPERATION_COUNT = STATEMENT_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link alpha.codegen.impl.ReturnStmtImpl <em>Return Stmt</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see alpha.codegen.impl.ReturnStmtImpl
	 * @see alpha.codegen.impl.CodegenPackageImpl#getReturnStmt()
	 * @generated
	 */
	int RETURN_STMT = 18;

	/**
	 * The feature id for the '<em><b>Expression</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RETURN_STMT__EXPRESSION = STATEMENT_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Return Stmt</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RETURN_STMT_FEATURE_COUNT = STATEMENT_FEATURE_COUNT + 1;

	/**
	 * The number of operations of the '<em>Return Stmt</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RETURN_STMT_OPERATION_COUNT = STATEMENT_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link alpha.codegen.Expression <em>Expression</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see alpha.codegen.Expression
	 * @see alpha.codegen.impl.CodegenPackageImpl#getExpression()
	 * @generated
	 */
	int EXPRESSION = 19;

	/**
	 * The number of structural features of the '<em>Expression</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXPRESSION_FEATURE_COUNT = 0;

	/**
	 * The number of operations of the '<em>Expression</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXPRESSION_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link alpha.codegen.impl.CustomExprImpl <em>Custom Expr</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see alpha.codegen.impl.CustomExprImpl
	 * @see alpha.codegen.impl.CodegenPackageImpl#getCustomExpr()
	 * @generated
	 */
	int CUSTOM_EXPR = 20;

	/**
	 * The feature id for the '<em><b>Expression</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CUSTOM_EXPR__EXPRESSION = EXPRESSION_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Custom Expr</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CUSTOM_EXPR_FEATURE_COUNT = EXPRESSION_FEATURE_COUNT + 1;

	/**
	 * The number of operations of the '<em>Custom Expr</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CUSTOM_EXPR_OPERATION_COUNT = EXPRESSION_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link alpha.codegen.impl.ParenthesizedExprImpl <em>Parenthesized Expr</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see alpha.codegen.impl.ParenthesizedExprImpl
	 * @see alpha.codegen.impl.CodegenPackageImpl#getParenthesizedExpr()
	 * @generated
	 */
	int PARENTHESIZED_EXPR = 21;

	/**
	 * The feature id for the '<em><b>Expression</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARENTHESIZED_EXPR__EXPRESSION = EXPRESSION_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Parenthesized Expr</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARENTHESIZED_EXPR_FEATURE_COUNT = EXPRESSION_FEATURE_COUNT + 1;

	/**
	 * The number of operations of the '<em>Parenthesized Expr</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARENTHESIZED_EXPR_OPERATION_COUNT = EXPRESSION_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link alpha.codegen.impl.CastExprImpl <em>Cast Expr</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see alpha.codegen.impl.CastExprImpl
	 * @see alpha.codegen.impl.CodegenPackageImpl#getCastExpr()
	 * @generated
	 */
	int CAST_EXPR = 22;

	/**
	 * The feature id for the '<em><b>Data Type</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CAST_EXPR__DATA_TYPE = EXPRESSION_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Expression</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CAST_EXPR__EXPRESSION = EXPRESSION_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Cast Expr</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CAST_EXPR_FEATURE_COUNT = EXPRESSION_FEATURE_COUNT + 2;

	/**
	 * The number of operations of the '<em>Cast Expr</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CAST_EXPR_OPERATION_COUNT = EXPRESSION_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link alpha.codegen.impl.ArrayAccessExprImpl <em>Array Access Expr</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see alpha.codegen.impl.ArrayAccessExprImpl
	 * @see alpha.codegen.impl.CodegenPackageImpl#getArrayAccessExpr()
	 * @generated
	 */
	int ARRAY_ACCESS_EXPR = 23;

	/**
	 * The feature id for the '<em><b>Variable Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ARRAY_ACCESS_EXPR__VARIABLE_NAME = EXPRESSION_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Index Expressions</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ARRAY_ACCESS_EXPR__INDEX_EXPRESSIONS = EXPRESSION_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Array Access Expr</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ARRAY_ACCESS_EXPR_FEATURE_COUNT = EXPRESSION_FEATURE_COUNT + 2;

	/**
	 * The number of operations of the '<em>Array Access Expr</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ARRAY_ACCESS_EXPR_OPERATION_COUNT = EXPRESSION_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link alpha.codegen.impl.CallExprImpl <em>Call Expr</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see alpha.codegen.impl.CallExprImpl
	 * @see alpha.codegen.impl.CodegenPackageImpl#getCallExpr()
	 * @generated
	 */
	int CALL_EXPR = 24;

	/**
	 * The feature id for the '<em><b>Function Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CALL_EXPR__FUNCTION_NAME = EXPRESSION_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Arguments</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CALL_EXPR__ARGUMENTS = EXPRESSION_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Call Expr</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CALL_EXPR_FEATURE_COUNT = EXPRESSION_FEATURE_COUNT + 2;

	/**
	 * The number of operations of the '<em>Call Expr</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CALL_EXPR_OPERATION_COUNT = EXPRESSION_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link alpha.codegen.impl.UnaryExprImpl <em>Unary Expr</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see alpha.codegen.impl.UnaryExprImpl
	 * @see alpha.codegen.impl.CodegenPackageImpl#getUnaryExpr()
	 * @generated
	 */
	int UNARY_EXPR = 25;

	/**
	 * The feature id for the '<em><b>Operator</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int UNARY_EXPR__OPERATOR = EXPRESSION_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Expression</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int UNARY_EXPR__EXPRESSION = EXPRESSION_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Unary Expr</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int UNARY_EXPR_FEATURE_COUNT = EXPRESSION_FEATURE_COUNT + 2;

	/**
	 * The number of operations of the '<em>Unary Expr</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int UNARY_EXPR_OPERATION_COUNT = EXPRESSION_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link alpha.codegen.impl.BinaryExprImpl <em>Binary Expr</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see alpha.codegen.impl.BinaryExprImpl
	 * @see alpha.codegen.impl.CodegenPackageImpl#getBinaryExpr()
	 * @generated
	 */
	int BINARY_EXPR = 26;

	/**
	 * The feature id for the '<em><b>Operator</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BINARY_EXPR__OPERATOR = EXPRESSION_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Left</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BINARY_EXPR__LEFT = EXPRESSION_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Right</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BINARY_EXPR__RIGHT = EXPRESSION_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>Binary Expr</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BINARY_EXPR_FEATURE_COUNT = EXPRESSION_FEATURE_COUNT + 3;

	/**
	 * The number of operations of the '<em>Binary Expr</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BINARY_EXPR_OPERATION_COUNT = EXPRESSION_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link alpha.codegen.impl.TernaryExprImpl <em>Ternary Expr</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see alpha.codegen.impl.TernaryExprImpl
	 * @see alpha.codegen.impl.CodegenPackageImpl#getTernaryExpr()
	 * @generated
	 */
	int TERNARY_EXPR = 27;

	/**
	 * The feature id for the '<em><b>Conditional</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TERNARY_EXPR__CONDITIONAL = EXPRESSION_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Then Expr</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TERNARY_EXPR__THEN_EXPR = EXPRESSION_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Else Expr</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TERNARY_EXPR__ELSE_EXPR = EXPRESSION_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>Ternary Expr</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TERNARY_EXPR_FEATURE_COUNT = EXPRESSION_FEATURE_COUNT + 3;

	/**
	 * The number of operations of the '<em>Ternary Expr</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TERNARY_EXPR_OPERATION_COUNT = EXPRESSION_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link alpha.codegen.BaseDataType <em>Base Data Type</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see alpha.codegen.BaseDataType
	 * @see alpha.codegen.impl.CodegenPackageImpl#getBaseDataType()
	 * @generated
	 */
	int BASE_DATA_TYPE = 28;

	/**
	 * The meta object id for the '{@link alpha.codegen.AssignmentOperator <em>Assignment Operator</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see alpha.codegen.AssignmentOperator
	 * @see alpha.codegen.impl.CodegenPackageImpl#getAssignmentOperator()
	 * @generated
	 */
	int ASSIGNMENT_OPERATOR = 29;

	/**
	 * The meta object id for the '{@link alpha.codegen.UnaryOperator <em>Unary Operator</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see alpha.codegen.UnaryOperator
	 * @see alpha.codegen.impl.CodegenPackageImpl#getUnaryOperator()
	 * @generated
	 */
	int UNARY_OPERATOR = 30;

	/**
	 * The meta object id for the '{@link alpha.codegen.BinaryOperator <em>Binary Operator</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see alpha.codegen.BinaryOperator
	 * @see alpha.codegen.impl.CodegenPackageImpl#getBinaryOperator()
	 * @generated
	 */
	int BINARY_OPERATOR = 31;

	/**
	 * The meta object id for the '<em>String Iterable</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see java.lang.Iterable
	 * @see alpha.codegen.impl.CodegenPackageImpl#getStringIterable()
	 * @generated
	 */
	int STRING_ITERABLE = 32;


	/**
	 * Returns the meta object for class '{@link alpha.codegen.Program <em>Program</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Program</em>'.
	 * @see alpha.codegen.Program
	 * @generated
	 */
	EClass getProgram();

	/**
	 * Returns the meta object for the containment reference '{@link alpha.codegen.Program#getHeaderComment <em>Header Comment</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Header Comment</em>'.
	 * @see alpha.codegen.Program#getHeaderComment()
	 * @see #getProgram()
	 * @generated
	 */
	EReference getProgram_HeaderComment();

	/**
	 * Returns the meta object for the containment reference list '{@link alpha.codegen.Program#getIncludes <em>Includes</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Includes</em>'.
	 * @see alpha.codegen.Program#getIncludes()
	 * @see #getProgram()
	 * @generated
	 */
	EReference getProgram_Includes();

	/**
	 * Returns the meta object for the containment reference list '{@link alpha.codegen.Program#getFunctionMacros <em>Function Macros</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Function Macros</em>'.
	 * @see alpha.codegen.Program#getFunctionMacros()
	 * @see #getProgram()
	 * @generated
	 */
	EReference getProgram_FunctionMacros();

	/**
	 * Returns the meta object for the containment reference list '{@link alpha.codegen.Program#getGlobalVariables <em>Global Variables</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Global Variables</em>'.
	 * @see alpha.codegen.Program#getGlobalVariables()
	 * @see #getProgram()
	 * @generated
	 */
	EReference getProgram_GlobalVariables();

	/**
	 * Returns the meta object for the containment reference list '{@link alpha.codegen.Program#getMemoryMacros <em>Memory Macros</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Memory Macros</em>'.
	 * @see alpha.codegen.Program#getMemoryMacros()
	 * @see #getProgram()
	 * @generated
	 */
	EReference getProgram_MemoryMacros();

	/**
	 * Returns the meta object for the containment reference list '{@link alpha.codegen.Program#getFunctions <em>Functions</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Functions</em>'.
	 * @see alpha.codegen.Program#getFunctions()
	 * @see #getProgram()
	 * @generated
	 */
	EReference getProgram_Functions();

	/**
	 * Returns the meta object for class '{@link alpha.codegen.Include <em>Include</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Include</em>'.
	 * @see alpha.codegen.Include
	 * @generated
	 */
	EClass getInclude();

	/**
	 * Returns the meta object for the attribute '{@link alpha.codegen.Include#getFile <em>File</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>File</em>'.
	 * @see alpha.codegen.Include#getFile()
	 * @see #getInclude()
	 * @generated
	 */
	EAttribute getInclude_File();

	/**
	 * Returns the meta object for the attribute '{@link alpha.codegen.Include#isUseQuotes <em>Use Quotes</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Use Quotes</em>'.
	 * @see alpha.codegen.Include#isUseQuotes()
	 * @see #getInclude()
	 * @generated
	 */
	EAttribute getInclude_UseQuotes();

	/**
	 * Returns the meta object for class '{@link alpha.codegen.DataType <em>Data Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Data Type</em>'.
	 * @see alpha.codegen.DataType
	 * @generated
	 */
	EClass getDataType();

	/**
	 * Returns the meta object for the attribute '{@link alpha.codegen.DataType#getBaseType <em>Base Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Base Type</em>'.
	 * @see alpha.codegen.DataType#getBaseType()
	 * @see #getDataType()
	 * @generated
	 */
	EAttribute getDataType_BaseType();

	/**
	 * Returns the meta object for the attribute '{@link alpha.codegen.DataType#getIndirectionLevel <em>Indirection Level</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Indirection Level</em>'.
	 * @see alpha.codegen.DataType#getIndirectionLevel()
	 * @see #getDataType()
	 * @generated
	 */
	EAttribute getDataType_IndirectionLevel();

	/**
	 * Returns the meta object for class '{@link alpha.codegen.VariableDecl <em>Variable Decl</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Variable Decl</em>'.
	 * @see alpha.codegen.VariableDecl
	 * @generated
	 */
	EClass getVariableDecl();

	/**
	 * Returns the meta object for the attribute '{@link alpha.codegen.VariableDecl#isIsStatic <em>Is Static</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Is Static</em>'.
	 * @see alpha.codegen.VariableDecl#isIsStatic()
	 * @see #getVariableDecl()
	 * @generated
	 */
	EAttribute getVariableDecl_IsStatic();

	/**
	 * Returns the meta object for the containment reference '{@link alpha.codegen.VariableDecl#getDataType <em>Data Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Data Type</em>'.
	 * @see alpha.codegen.VariableDecl#getDataType()
	 * @see #getVariableDecl()
	 * @generated
	 */
	EReference getVariableDecl_DataType();

	/**
	 * Returns the meta object for the attribute '{@link alpha.codegen.VariableDecl#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see alpha.codegen.VariableDecl#getName()
	 * @see #getVariableDecl()
	 * @generated
	 */
	EAttribute getVariableDecl_Name();

	/**
	 * Returns the meta object for class '{@link alpha.codegen.Function <em>Function</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Function</em>'.
	 * @see alpha.codegen.Function
	 * @generated
	 */
	EClass getFunction();

	/**
	 * Returns the meta object for the attribute '{@link alpha.codegen.Function#getIsStatic <em>Is Static</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Is Static</em>'.
	 * @see alpha.codegen.Function#getIsStatic()
	 * @see #getFunction()
	 * @generated
	 */
	EAttribute getFunction_IsStatic();

	/**
	 * Returns the meta object for the attribute '{@link alpha.codegen.Function#getIsInline <em>Is Inline</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Is Inline</em>'.
	 * @see alpha.codegen.Function#getIsInline()
	 * @see #getFunction()
	 * @generated
	 */
	EAttribute getFunction_IsInline();

	/**
	 * Returns the meta object for the containment reference '{@link alpha.codegen.Function#getReturnType <em>Return Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Return Type</em>'.
	 * @see alpha.codegen.Function#getReturnType()
	 * @see #getFunction()
	 * @generated
	 */
	EReference getFunction_ReturnType();

	/**
	 * Returns the meta object for the attribute '{@link alpha.codegen.Function#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see alpha.codegen.Function#getName()
	 * @see #getFunction()
	 * @generated
	 */
	EAttribute getFunction_Name();

	/**
	 * Returns the meta object for the containment reference list '{@link alpha.codegen.Function#getParameters <em>Parameters</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Parameters</em>'.
	 * @see alpha.codegen.Function#getParameters()
	 * @see #getFunction()
	 * @generated
	 */
	EReference getFunction_Parameters();

	/**
	 * Returns the meta object for the containment reference list '{@link alpha.codegen.Function#getDeclarations <em>Declarations</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Declarations</em>'.
	 * @see alpha.codegen.Function#getDeclarations()
	 * @see #getFunction()
	 * @generated
	 */
	EReference getFunction_Declarations();

	/**
	 * Returns the meta object for the containment reference list '{@link alpha.codegen.Function#getStatements <em>Statements</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Statements</em>'.
	 * @see alpha.codegen.Function#getStatements()
	 * @see #getFunction()
	 * @generated
	 */
	EReference getFunction_Statements();

	/**
	 * Returns the meta object for class '{@link alpha.codegen.Parameter <em>Parameter</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Parameter</em>'.
	 * @see alpha.codegen.Parameter
	 * @generated
	 */
	EClass getParameter();

	/**
	 * Returns the meta object for the containment reference '{@link alpha.codegen.Parameter#getDataType <em>Data Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Data Type</em>'.
	 * @see alpha.codegen.Parameter#getDataType()
	 * @see #getParameter()
	 * @generated
	 */
	EReference getParameter_DataType();

	/**
	 * Returns the meta object for the attribute '{@link alpha.codegen.Parameter#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see alpha.codegen.Parameter#getName()
	 * @see #getParameter()
	 * @generated
	 */
	EAttribute getParameter_Name();

	/**
	 * Returns the meta object for class '{@link alpha.codegen.Statement <em>Statement</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Statement</em>'.
	 * @see alpha.codegen.Statement
	 * @generated
	 */
	EClass getStatement();

	/**
	 * Returns the meta object for class '{@link alpha.codegen.EmptyLineStmt <em>Empty Line Stmt</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Empty Line Stmt</em>'.
	 * @see alpha.codegen.EmptyLineStmt
	 * @generated
	 */
	EClass getEmptyLineStmt();

	/**
	 * Returns the meta object for class '{@link alpha.codegen.CommentStmt <em>Comment Stmt</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Comment Stmt</em>'.
	 * @see alpha.codegen.CommentStmt
	 * @generated
	 */
	EClass getCommentStmt();

	/**
	 * Returns the meta object for the attribute list '{@link alpha.codegen.CommentStmt#getCommentLines <em>Comment Lines</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Comment Lines</em>'.
	 * @see alpha.codegen.CommentStmt#getCommentLines()
	 * @see #getCommentStmt()
	 * @generated
	 */
	EAttribute getCommentStmt_CommentLines();

	/**
	 * Returns the meta object for the '{@link alpha.codegen.CommentStmt#addAll(java.lang.Iterable) <em>Add All</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Add All</em>' operation.
	 * @see alpha.codegen.CommentStmt#addAll(java.lang.Iterable)
	 * @generated
	 */
	EOperation getCommentStmt__AddAll__Iterable();

	/**
	 * Returns the meta object for class '{@link alpha.codegen.ExpressionStmt <em>Expression Stmt</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Expression Stmt</em>'.
	 * @see alpha.codegen.ExpressionStmt
	 * @generated
	 */
	EClass getExpressionStmt();

	/**
	 * Returns the meta object for the containment reference '{@link alpha.codegen.ExpressionStmt#getExpression <em>Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Expression</em>'.
	 * @see alpha.codegen.ExpressionStmt#getExpression()
	 * @see #getExpressionStmt()
	 * @generated
	 */
	EReference getExpressionStmt_Expression();

	/**
	 * Returns the meta object for class '{@link alpha.codegen.MacroStmt <em>Macro Stmt</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Macro Stmt</em>'.
	 * @see alpha.codegen.MacroStmt
	 * @generated
	 */
	EClass getMacroStmt();

	/**
	 * Returns the meta object for the attribute '{@link alpha.codegen.MacroStmt#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see alpha.codegen.MacroStmt#getName()
	 * @see #getMacroStmt()
	 * @generated
	 */
	EAttribute getMacroStmt_Name();

	/**
	 * Returns the meta object for the attribute list '{@link alpha.codegen.MacroStmt#getArguments <em>Arguments</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Arguments</em>'.
	 * @see alpha.codegen.MacroStmt#getArguments()
	 * @see #getMacroStmt()
	 * @generated
	 */
	EAttribute getMacroStmt_Arguments();

	/**
	 * Returns the meta object for the containment reference '{@link alpha.codegen.MacroStmt#getReplacement <em>Replacement</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Replacement</em>'.
	 * @see alpha.codegen.MacroStmt#getReplacement()
	 * @see #getMacroStmt()
	 * @generated
	 */
	EReference getMacroStmt_Replacement();

	/**
	 * Returns the meta object for class '{@link alpha.codegen.UndefStmt <em>Undef Stmt</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Undef Stmt</em>'.
	 * @see alpha.codegen.UndefStmt
	 * @generated
	 */
	EClass getUndefStmt();

	/**
	 * Returns the meta object for the attribute '{@link alpha.codegen.UndefStmt#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see alpha.codegen.UndefStmt#getName()
	 * @see #getUndefStmt()
	 * @generated
	 */
	EAttribute getUndefStmt_Name();

	/**
	 * Returns the meta object for class '{@link alpha.codegen.OmpStmt <em>Omp Stmt</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Omp Stmt</em>'.
	 * @see alpha.codegen.OmpStmt
	 * @generated
	 */
	EClass getOmpStmt();

	/**
	 * Returns the meta object for the attribute list '{@link alpha.codegen.OmpStmt#getPrivateVars <em>Private Vars</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Private Vars</em>'.
	 * @see alpha.codegen.OmpStmt#getPrivateVars()
	 * @see #getOmpStmt()
	 * @generated
	 */
	EAttribute getOmpStmt_PrivateVars();

	/**
	 * Returns the meta object for class '{@link alpha.codegen.IfStmt <em>If Stmt</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>If Stmt</em>'.
	 * @see alpha.codegen.IfStmt
	 * @generated
	 */
	EClass getIfStmt();

	/**
	 * Returns the meta object for the containment reference '{@link alpha.codegen.IfStmt#getIfBranch <em>If Branch</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>If Branch</em>'.
	 * @see alpha.codegen.IfStmt#getIfBranch()
	 * @see #getIfStmt()
	 * @generated
	 */
	EReference getIfStmt_IfBranch();

	/**
	 * Returns the meta object for the containment reference list '{@link alpha.codegen.IfStmt#getElseIfBranches <em>Else If Branches</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Else If Branches</em>'.
	 * @see alpha.codegen.IfStmt#getElseIfBranches()
	 * @see #getIfStmt()
	 * @generated
	 */
	EReference getIfStmt_ElseIfBranches();

	/**
	 * Returns the meta object for the containment reference '{@link alpha.codegen.IfStmt#getElseBranch <em>Else Branch</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Else Branch</em>'.
	 * @see alpha.codegen.IfStmt#getElseBranch()
	 * @see #getIfStmt()
	 * @generated
	 */
	EReference getIfStmt_ElseBranch();

	/**
	 * Returns the meta object for class '{@link alpha.codegen.Branch <em>Branch</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Branch</em>'.
	 * @see alpha.codegen.Branch
	 * @generated
	 */
	EClass getBranch();

	/**
	 * Returns the meta object for the containment reference list '{@link alpha.codegen.Branch#getBody <em>Body</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Body</em>'.
	 * @see alpha.codegen.Branch#getBody()
	 * @see #getBranch()
	 * @generated
	 */
	EReference getBranch_Body();

	/**
	 * Returns the meta object for class '{@link alpha.codegen.ConditionalBranch <em>Conditional Branch</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Conditional Branch</em>'.
	 * @see alpha.codegen.ConditionalBranch
	 * @generated
	 */
	EClass getConditionalBranch();

	/**
	 * Returns the meta object for the containment reference '{@link alpha.codegen.ConditionalBranch#getConditional <em>Conditional</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Conditional</em>'.
	 * @see alpha.codegen.ConditionalBranch#getConditional()
	 * @see #getConditionalBranch()
	 * @generated
	 */
	EReference getConditionalBranch_Conditional();

	/**
	 * Returns the meta object for class '{@link alpha.codegen.LoopStmt <em>Loop Stmt</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Loop Stmt</em>'.
	 * @see alpha.codegen.LoopStmt
	 * @generated
	 */
	EClass getLoopStmt();

	/**
	 * Returns the meta object for the attribute '{@link alpha.codegen.LoopStmt#getLoopVariable <em>Loop Variable</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Loop Variable</em>'.
	 * @see alpha.codegen.LoopStmt#getLoopVariable()
	 * @see #getLoopStmt()
	 * @generated
	 */
	EAttribute getLoopStmt_LoopVariable();

	/**
	 * Returns the meta object for the containment reference '{@link alpha.codegen.LoopStmt#getInitializer <em>Initializer</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Initializer</em>'.
	 * @see alpha.codegen.LoopStmt#getInitializer()
	 * @see #getLoopStmt()
	 * @generated
	 */
	EReference getLoopStmt_Initializer();

	/**
	 * Returns the meta object for the containment reference '{@link alpha.codegen.LoopStmt#getConditional <em>Conditional</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Conditional</em>'.
	 * @see alpha.codegen.LoopStmt#getConditional()
	 * @see #getLoopStmt()
	 * @generated
	 */
	EReference getLoopStmt_Conditional();

	/**
	 * Returns the meta object for the containment reference '{@link alpha.codegen.LoopStmt#getIncrementBy <em>Increment By</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Increment By</em>'.
	 * @see alpha.codegen.LoopStmt#getIncrementBy()
	 * @see #getLoopStmt()
	 * @generated
	 */
	EReference getLoopStmt_IncrementBy();

	/**
	 * Returns the meta object for the containment reference list '{@link alpha.codegen.LoopStmt#getBody <em>Body</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Body</em>'.
	 * @see alpha.codegen.LoopStmt#getBody()
	 * @see #getLoopStmt()
	 * @generated
	 */
	EReference getLoopStmt_Body();

	/**
	 * Returns the meta object for class '{@link alpha.codegen.AssignmentStmt <em>Assignment Stmt</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Assignment Stmt</em>'.
	 * @see alpha.codegen.AssignmentStmt
	 * @generated
	 */
	EClass getAssignmentStmt();

	/**
	 * Returns the meta object for the containment reference '{@link alpha.codegen.AssignmentStmt#getLeft <em>Left</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Left</em>'.
	 * @see alpha.codegen.AssignmentStmt#getLeft()
	 * @see #getAssignmentStmt()
	 * @generated
	 */
	EReference getAssignmentStmt_Left();

	/**
	 * Returns the meta object for the attribute '{@link alpha.codegen.AssignmentStmt#getAssignType <em>Assign Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Assign Type</em>'.
	 * @see alpha.codegen.AssignmentStmt#getAssignType()
	 * @see #getAssignmentStmt()
	 * @generated
	 */
	EAttribute getAssignmentStmt_AssignType();

	/**
	 * Returns the meta object for the containment reference '{@link alpha.codegen.AssignmentStmt#getRight <em>Right</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Right</em>'.
	 * @see alpha.codegen.AssignmentStmt#getRight()
	 * @see #getAssignmentStmt()
	 * @generated
	 */
	EReference getAssignmentStmt_Right();

	/**
	 * Returns the meta object for class '{@link alpha.codegen.ReturnStmt <em>Return Stmt</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Return Stmt</em>'.
	 * @see alpha.codegen.ReturnStmt
	 * @generated
	 */
	EClass getReturnStmt();

	/**
	 * Returns the meta object for the containment reference '{@link alpha.codegen.ReturnStmt#getExpression <em>Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Expression</em>'.
	 * @see alpha.codegen.ReturnStmt#getExpression()
	 * @see #getReturnStmt()
	 * @generated
	 */
	EReference getReturnStmt_Expression();

	/**
	 * Returns the meta object for class '{@link alpha.codegen.Expression <em>Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Expression</em>'.
	 * @see alpha.codegen.Expression
	 * @generated
	 */
	EClass getExpression();

	/**
	 * Returns the meta object for class '{@link alpha.codegen.CustomExpr <em>Custom Expr</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Custom Expr</em>'.
	 * @see alpha.codegen.CustomExpr
	 * @generated
	 */
	EClass getCustomExpr();

	/**
	 * Returns the meta object for the attribute '{@link alpha.codegen.CustomExpr#getExpression <em>Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Expression</em>'.
	 * @see alpha.codegen.CustomExpr#getExpression()
	 * @see #getCustomExpr()
	 * @generated
	 */
	EAttribute getCustomExpr_Expression();

	/**
	 * Returns the meta object for class '{@link alpha.codegen.ParenthesizedExpr <em>Parenthesized Expr</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Parenthesized Expr</em>'.
	 * @see alpha.codegen.ParenthesizedExpr
	 * @generated
	 */
	EClass getParenthesizedExpr();

	/**
	 * Returns the meta object for the containment reference '{@link alpha.codegen.ParenthesizedExpr#getExpression <em>Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Expression</em>'.
	 * @see alpha.codegen.ParenthesizedExpr#getExpression()
	 * @see #getParenthesizedExpr()
	 * @generated
	 */
	EReference getParenthesizedExpr_Expression();

	/**
	 * Returns the meta object for class '{@link alpha.codegen.CastExpr <em>Cast Expr</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Cast Expr</em>'.
	 * @see alpha.codegen.CastExpr
	 * @generated
	 */
	EClass getCastExpr();

	/**
	 * Returns the meta object for the containment reference '{@link alpha.codegen.CastExpr#getDataType <em>Data Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Data Type</em>'.
	 * @see alpha.codegen.CastExpr#getDataType()
	 * @see #getCastExpr()
	 * @generated
	 */
	EReference getCastExpr_DataType();

	/**
	 * Returns the meta object for the containment reference '{@link alpha.codegen.CastExpr#getExpression <em>Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Expression</em>'.
	 * @see alpha.codegen.CastExpr#getExpression()
	 * @see #getCastExpr()
	 * @generated
	 */
	EReference getCastExpr_Expression();

	/**
	 * Returns the meta object for class '{@link alpha.codegen.ArrayAccessExpr <em>Array Access Expr</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Array Access Expr</em>'.
	 * @see alpha.codegen.ArrayAccessExpr
	 * @generated
	 */
	EClass getArrayAccessExpr();

	/**
	 * Returns the meta object for the attribute '{@link alpha.codegen.ArrayAccessExpr#getVariableName <em>Variable Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Variable Name</em>'.
	 * @see alpha.codegen.ArrayAccessExpr#getVariableName()
	 * @see #getArrayAccessExpr()
	 * @generated
	 */
	EAttribute getArrayAccessExpr_VariableName();

	/**
	 * Returns the meta object for the containment reference list '{@link alpha.codegen.ArrayAccessExpr#getIndexExpressions <em>Index Expressions</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Index Expressions</em>'.
	 * @see alpha.codegen.ArrayAccessExpr#getIndexExpressions()
	 * @see #getArrayAccessExpr()
	 * @generated
	 */
	EReference getArrayAccessExpr_IndexExpressions();

	/**
	 * Returns the meta object for class '{@link alpha.codegen.CallExpr <em>Call Expr</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Call Expr</em>'.
	 * @see alpha.codegen.CallExpr
	 * @generated
	 */
	EClass getCallExpr();

	/**
	 * Returns the meta object for the attribute '{@link alpha.codegen.CallExpr#getFunctionName <em>Function Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Function Name</em>'.
	 * @see alpha.codegen.CallExpr#getFunctionName()
	 * @see #getCallExpr()
	 * @generated
	 */
	EAttribute getCallExpr_FunctionName();

	/**
	 * Returns the meta object for the containment reference list '{@link alpha.codegen.CallExpr#getArguments <em>Arguments</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Arguments</em>'.
	 * @see alpha.codegen.CallExpr#getArguments()
	 * @see #getCallExpr()
	 * @generated
	 */
	EReference getCallExpr_Arguments();

	/**
	 * Returns the meta object for class '{@link alpha.codegen.UnaryExpr <em>Unary Expr</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Unary Expr</em>'.
	 * @see alpha.codegen.UnaryExpr
	 * @generated
	 */
	EClass getUnaryExpr();

	/**
	 * Returns the meta object for the attribute '{@link alpha.codegen.UnaryExpr#getOperator <em>Operator</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Operator</em>'.
	 * @see alpha.codegen.UnaryExpr#getOperator()
	 * @see #getUnaryExpr()
	 * @generated
	 */
	EAttribute getUnaryExpr_Operator();

	/**
	 * Returns the meta object for the containment reference '{@link alpha.codegen.UnaryExpr#getExpression <em>Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Expression</em>'.
	 * @see alpha.codegen.UnaryExpr#getExpression()
	 * @see #getUnaryExpr()
	 * @generated
	 */
	EReference getUnaryExpr_Expression();

	/**
	 * Returns the meta object for class '{@link alpha.codegen.BinaryExpr <em>Binary Expr</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Binary Expr</em>'.
	 * @see alpha.codegen.BinaryExpr
	 * @generated
	 */
	EClass getBinaryExpr();

	/**
	 * Returns the meta object for the attribute '{@link alpha.codegen.BinaryExpr#getOperator <em>Operator</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Operator</em>'.
	 * @see alpha.codegen.BinaryExpr#getOperator()
	 * @see #getBinaryExpr()
	 * @generated
	 */
	EAttribute getBinaryExpr_Operator();

	/**
	 * Returns the meta object for the containment reference '{@link alpha.codegen.BinaryExpr#getLeft <em>Left</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Left</em>'.
	 * @see alpha.codegen.BinaryExpr#getLeft()
	 * @see #getBinaryExpr()
	 * @generated
	 */
	EReference getBinaryExpr_Left();

	/**
	 * Returns the meta object for the containment reference '{@link alpha.codegen.BinaryExpr#getRight <em>Right</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Right</em>'.
	 * @see alpha.codegen.BinaryExpr#getRight()
	 * @see #getBinaryExpr()
	 * @generated
	 */
	EReference getBinaryExpr_Right();

	/**
	 * Returns the meta object for class '{@link alpha.codegen.TernaryExpr <em>Ternary Expr</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Ternary Expr</em>'.
	 * @see alpha.codegen.TernaryExpr
	 * @generated
	 */
	EClass getTernaryExpr();

	/**
	 * Returns the meta object for the containment reference '{@link alpha.codegen.TernaryExpr#getConditional <em>Conditional</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Conditional</em>'.
	 * @see alpha.codegen.TernaryExpr#getConditional()
	 * @see #getTernaryExpr()
	 * @generated
	 */
	EReference getTernaryExpr_Conditional();

	/**
	 * Returns the meta object for the containment reference '{@link alpha.codegen.TernaryExpr#getThenExpr <em>Then Expr</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Then Expr</em>'.
	 * @see alpha.codegen.TernaryExpr#getThenExpr()
	 * @see #getTernaryExpr()
	 * @generated
	 */
	EReference getTernaryExpr_ThenExpr();

	/**
	 * Returns the meta object for the containment reference '{@link alpha.codegen.TernaryExpr#getElseExpr <em>Else Expr</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Else Expr</em>'.
	 * @see alpha.codegen.TernaryExpr#getElseExpr()
	 * @see #getTernaryExpr()
	 * @generated
	 */
	EReference getTernaryExpr_ElseExpr();

	/**
	 * Returns the meta object for enum '{@link alpha.codegen.BaseDataType <em>Base Data Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Base Data Type</em>'.
	 * @see alpha.codegen.BaseDataType
	 * @generated
	 */
	EEnum getBaseDataType();

	/**
	 * Returns the meta object for enum '{@link alpha.codegen.AssignmentOperator <em>Assignment Operator</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Assignment Operator</em>'.
	 * @see alpha.codegen.AssignmentOperator
	 * @generated
	 */
	EEnum getAssignmentOperator();

	/**
	 * Returns the meta object for enum '{@link alpha.codegen.UnaryOperator <em>Unary Operator</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Unary Operator</em>'.
	 * @see alpha.codegen.UnaryOperator
	 * @generated
	 */
	EEnum getUnaryOperator();

	/**
	 * Returns the meta object for enum '{@link alpha.codegen.BinaryOperator <em>Binary Operator</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Binary Operator</em>'.
	 * @see alpha.codegen.BinaryOperator
	 * @generated
	 */
	EEnum getBinaryOperator();

	/**
	 * Returns the meta object for data type '{@link java.lang.Iterable <em>String Iterable</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for data type '<em>String Iterable</em>'.
	 * @see java.lang.Iterable
	 * @model instanceClass="java.lang.Iterable&lt;java.lang.String&gt;"
	 * @generated
	 */
	EDataType getStringIterable();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	CodegenFactory getCodegenFactory();

	/**
	 * <!-- begin-user-doc -->
	 * Defines literals for the meta objects that represent
	 * <ul>
	 *   <li>each class,</li>
	 *   <li>each feature of each class,</li>
	 *   <li>each operation of each class,</li>
	 *   <li>each enum,</li>
	 *   <li>and each data type</li>
	 * </ul>
	 * <!-- end-user-doc -->
	 * @generated
	 */
	interface Literals {
		/**
		 * The meta object literal for the '{@link alpha.codegen.impl.ProgramImpl <em>Program</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see alpha.codegen.impl.ProgramImpl
		 * @see alpha.codegen.impl.CodegenPackageImpl#getProgram()
		 * @generated
		 */
		EClass PROGRAM = eINSTANCE.getProgram();

		/**
		 * The meta object literal for the '<em><b>Header Comment</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PROGRAM__HEADER_COMMENT = eINSTANCE.getProgram_HeaderComment();

		/**
		 * The meta object literal for the '<em><b>Includes</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PROGRAM__INCLUDES = eINSTANCE.getProgram_Includes();

		/**
		 * The meta object literal for the '<em><b>Function Macros</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PROGRAM__FUNCTION_MACROS = eINSTANCE.getProgram_FunctionMacros();

		/**
		 * The meta object literal for the '<em><b>Global Variables</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PROGRAM__GLOBAL_VARIABLES = eINSTANCE.getProgram_GlobalVariables();

		/**
		 * The meta object literal for the '<em><b>Memory Macros</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PROGRAM__MEMORY_MACROS = eINSTANCE.getProgram_MemoryMacros();

		/**
		 * The meta object literal for the '<em><b>Functions</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PROGRAM__FUNCTIONS = eINSTANCE.getProgram_Functions();

		/**
		 * The meta object literal for the '{@link alpha.codegen.impl.IncludeImpl <em>Include</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see alpha.codegen.impl.IncludeImpl
		 * @see alpha.codegen.impl.CodegenPackageImpl#getInclude()
		 * @generated
		 */
		EClass INCLUDE = eINSTANCE.getInclude();

		/**
		 * The meta object literal for the '<em><b>File</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute INCLUDE__FILE = eINSTANCE.getInclude_File();

		/**
		 * The meta object literal for the '<em><b>Use Quotes</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute INCLUDE__USE_QUOTES = eINSTANCE.getInclude_UseQuotes();

		/**
		 * The meta object literal for the '{@link alpha.codegen.impl.DataTypeImpl <em>Data Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see alpha.codegen.impl.DataTypeImpl
		 * @see alpha.codegen.impl.CodegenPackageImpl#getDataType()
		 * @generated
		 */
		EClass DATA_TYPE = eINSTANCE.getDataType();

		/**
		 * The meta object literal for the '<em><b>Base Type</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute DATA_TYPE__BASE_TYPE = eINSTANCE.getDataType_BaseType();

		/**
		 * The meta object literal for the '<em><b>Indirection Level</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute DATA_TYPE__INDIRECTION_LEVEL = eINSTANCE.getDataType_IndirectionLevel();

		/**
		 * The meta object literal for the '{@link alpha.codegen.impl.VariableDeclImpl <em>Variable Decl</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see alpha.codegen.impl.VariableDeclImpl
		 * @see alpha.codegen.impl.CodegenPackageImpl#getVariableDecl()
		 * @generated
		 */
		EClass VARIABLE_DECL = eINSTANCE.getVariableDecl();

		/**
		 * The meta object literal for the '<em><b>Is Static</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute VARIABLE_DECL__IS_STATIC = eINSTANCE.getVariableDecl_IsStatic();

		/**
		 * The meta object literal for the '<em><b>Data Type</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference VARIABLE_DECL__DATA_TYPE = eINSTANCE.getVariableDecl_DataType();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute VARIABLE_DECL__NAME = eINSTANCE.getVariableDecl_Name();

		/**
		 * The meta object literal for the '{@link alpha.codegen.impl.FunctionImpl <em>Function</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see alpha.codegen.impl.FunctionImpl
		 * @see alpha.codegen.impl.CodegenPackageImpl#getFunction()
		 * @generated
		 */
		EClass FUNCTION = eINSTANCE.getFunction();

		/**
		 * The meta object literal for the '<em><b>Is Static</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute FUNCTION__IS_STATIC = eINSTANCE.getFunction_IsStatic();

		/**
		 * The meta object literal for the '<em><b>Is Inline</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute FUNCTION__IS_INLINE = eINSTANCE.getFunction_IsInline();

		/**
		 * The meta object literal for the '<em><b>Return Type</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference FUNCTION__RETURN_TYPE = eINSTANCE.getFunction_ReturnType();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute FUNCTION__NAME = eINSTANCE.getFunction_Name();

		/**
		 * The meta object literal for the '<em><b>Parameters</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference FUNCTION__PARAMETERS = eINSTANCE.getFunction_Parameters();

		/**
		 * The meta object literal for the '<em><b>Declarations</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference FUNCTION__DECLARATIONS = eINSTANCE.getFunction_Declarations();

		/**
		 * The meta object literal for the '<em><b>Statements</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference FUNCTION__STATEMENTS = eINSTANCE.getFunction_Statements();

		/**
		 * The meta object literal for the '{@link alpha.codegen.impl.ParameterImpl <em>Parameter</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see alpha.codegen.impl.ParameterImpl
		 * @see alpha.codegen.impl.CodegenPackageImpl#getParameter()
		 * @generated
		 */
		EClass PARAMETER = eINSTANCE.getParameter();

		/**
		 * The meta object literal for the '<em><b>Data Type</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PARAMETER__DATA_TYPE = eINSTANCE.getParameter_DataType();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PARAMETER__NAME = eINSTANCE.getParameter_Name();

		/**
		 * The meta object literal for the '{@link alpha.codegen.Statement <em>Statement</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see alpha.codegen.Statement
		 * @see alpha.codegen.impl.CodegenPackageImpl#getStatement()
		 * @generated
		 */
		EClass STATEMENT = eINSTANCE.getStatement();

		/**
		 * The meta object literal for the '{@link alpha.codegen.impl.EmptyLineStmtImpl <em>Empty Line Stmt</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see alpha.codegen.impl.EmptyLineStmtImpl
		 * @see alpha.codegen.impl.CodegenPackageImpl#getEmptyLineStmt()
		 * @generated
		 */
		EClass EMPTY_LINE_STMT = eINSTANCE.getEmptyLineStmt();

		/**
		 * The meta object literal for the '{@link alpha.codegen.impl.CommentStmtImpl <em>Comment Stmt</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see alpha.codegen.impl.CommentStmtImpl
		 * @see alpha.codegen.impl.CodegenPackageImpl#getCommentStmt()
		 * @generated
		 */
		EClass COMMENT_STMT = eINSTANCE.getCommentStmt();

		/**
		 * The meta object literal for the '<em><b>Comment Lines</b></em>' attribute list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute COMMENT_STMT__COMMENT_LINES = eINSTANCE.getCommentStmt_CommentLines();

		/**
		 * The meta object literal for the '<em><b>Add All</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation COMMENT_STMT___ADD_ALL__ITERABLE = eINSTANCE.getCommentStmt__AddAll__Iterable();

		/**
		 * The meta object literal for the '{@link alpha.codegen.impl.ExpressionStmtImpl <em>Expression Stmt</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see alpha.codegen.impl.ExpressionStmtImpl
		 * @see alpha.codegen.impl.CodegenPackageImpl#getExpressionStmt()
		 * @generated
		 */
		EClass EXPRESSION_STMT = eINSTANCE.getExpressionStmt();

		/**
		 * The meta object literal for the '<em><b>Expression</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference EXPRESSION_STMT__EXPRESSION = eINSTANCE.getExpressionStmt_Expression();

		/**
		 * The meta object literal for the '{@link alpha.codegen.impl.MacroStmtImpl <em>Macro Stmt</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see alpha.codegen.impl.MacroStmtImpl
		 * @see alpha.codegen.impl.CodegenPackageImpl#getMacroStmt()
		 * @generated
		 */
		EClass MACRO_STMT = eINSTANCE.getMacroStmt();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute MACRO_STMT__NAME = eINSTANCE.getMacroStmt_Name();

		/**
		 * The meta object literal for the '<em><b>Arguments</b></em>' attribute list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute MACRO_STMT__ARGUMENTS = eINSTANCE.getMacroStmt_Arguments();

		/**
		 * The meta object literal for the '<em><b>Replacement</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference MACRO_STMT__REPLACEMENT = eINSTANCE.getMacroStmt_Replacement();

		/**
		 * The meta object literal for the '{@link alpha.codegen.impl.UndefStmtImpl <em>Undef Stmt</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see alpha.codegen.impl.UndefStmtImpl
		 * @see alpha.codegen.impl.CodegenPackageImpl#getUndefStmt()
		 * @generated
		 */
		EClass UNDEF_STMT = eINSTANCE.getUndefStmt();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute UNDEF_STMT__NAME = eINSTANCE.getUndefStmt_Name();

		/**
		 * The meta object literal for the '{@link alpha.codegen.impl.OmpStmtImpl <em>Omp Stmt</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see alpha.codegen.impl.OmpStmtImpl
		 * @see alpha.codegen.impl.CodegenPackageImpl#getOmpStmt()
		 * @generated
		 */
		EClass OMP_STMT = eINSTANCE.getOmpStmt();

		/**
		 * The meta object literal for the '<em><b>Private Vars</b></em>' attribute list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute OMP_STMT__PRIVATE_VARS = eINSTANCE.getOmpStmt_PrivateVars();

		/**
		 * The meta object literal for the '{@link alpha.codegen.impl.IfStmtImpl <em>If Stmt</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see alpha.codegen.impl.IfStmtImpl
		 * @see alpha.codegen.impl.CodegenPackageImpl#getIfStmt()
		 * @generated
		 */
		EClass IF_STMT = eINSTANCE.getIfStmt();

		/**
		 * The meta object literal for the '<em><b>If Branch</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference IF_STMT__IF_BRANCH = eINSTANCE.getIfStmt_IfBranch();

		/**
		 * The meta object literal for the '<em><b>Else If Branches</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference IF_STMT__ELSE_IF_BRANCHES = eINSTANCE.getIfStmt_ElseIfBranches();

		/**
		 * The meta object literal for the '<em><b>Else Branch</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference IF_STMT__ELSE_BRANCH = eINSTANCE.getIfStmt_ElseBranch();

		/**
		 * The meta object literal for the '{@link alpha.codegen.impl.BranchImpl <em>Branch</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see alpha.codegen.impl.BranchImpl
		 * @see alpha.codegen.impl.CodegenPackageImpl#getBranch()
		 * @generated
		 */
		EClass BRANCH = eINSTANCE.getBranch();

		/**
		 * The meta object literal for the '<em><b>Body</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference BRANCH__BODY = eINSTANCE.getBranch_Body();

		/**
		 * The meta object literal for the '{@link alpha.codegen.impl.ConditionalBranchImpl <em>Conditional Branch</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see alpha.codegen.impl.ConditionalBranchImpl
		 * @see alpha.codegen.impl.CodegenPackageImpl#getConditionalBranch()
		 * @generated
		 */
		EClass CONDITIONAL_BRANCH = eINSTANCE.getConditionalBranch();

		/**
		 * The meta object literal for the '<em><b>Conditional</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CONDITIONAL_BRANCH__CONDITIONAL = eINSTANCE.getConditionalBranch_Conditional();

		/**
		 * The meta object literal for the '{@link alpha.codegen.impl.LoopStmtImpl <em>Loop Stmt</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see alpha.codegen.impl.LoopStmtImpl
		 * @see alpha.codegen.impl.CodegenPackageImpl#getLoopStmt()
		 * @generated
		 */
		EClass LOOP_STMT = eINSTANCE.getLoopStmt();

		/**
		 * The meta object literal for the '<em><b>Loop Variable</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute LOOP_STMT__LOOP_VARIABLE = eINSTANCE.getLoopStmt_LoopVariable();

		/**
		 * The meta object literal for the '<em><b>Initializer</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference LOOP_STMT__INITIALIZER = eINSTANCE.getLoopStmt_Initializer();

		/**
		 * The meta object literal for the '<em><b>Conditional</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference LOOP_STMT__CONDITIONAL = eINSTANCE.getLoopStmt_Conditional();

		/**
		 * The meta object literal for the '<em><b>Increment By</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference LOOP_STMT__INCREMENT_BY = eINSTANCE.getLoopStmt_IncrementBy();

		/**
		 * The meta object literal for the '<em><b>Body</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference LOOP_STMT__BODY = eINSTANCE.getLoopStmt_Body();

		/**
		 * The meta object literal for the '{@link alpha.codegen.impl.AssignmentStmtImpl <em>Assignment Stmt</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see alpha.codegen.impl.AssignmentStmtImpl
		 * @see alpha.codegen.impl.CodegenPackageImpl#getAssignmentStmt()
		 * @generated
		 */
		EClass ASSIGNMENT_STMT = eINSTANCE.getAssignmentStmt();

		/**
		 * The meta object literal for the '<em><b>Left</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ASSIGNMENT_STMT__LEFT = eINSTANCE.getAssignmentStmt_Left();

		/**
		 * The meta object literal for the '<em><b>Assign Type</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ASSIGNMENT_STMT__ASSIGN_TYPE = eINSTANCE.getAssignmentStmt_AssignType();

		/**
		 * The meta object literal for the '<em><b>Right</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ASSIGNMENT_STMT__RIGHT = eINSTANCE.getAssignmentStmt_Right();

		/**
		 * The meta object literal for the '{@link alpha.codegen.impl.ReturnStmtImpl <em>Return Stmt</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see alpha.codegen.impl.ReturnStmtImpl
		 * @see alpha.codegen.impl.CodegenPackageImpl#getReturnStmt()
		 * @generated
		 */
		EClass RETURN_STMT = eINSTANCE.getReturnStmt();

		/**
		 * The meta object literal for the '<em><b>Expression</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference RETURN_STMT__EXPRESSION = eINSTANCE.getReturnStmt_Expression();

		/**
		 * The meta object literal for the '{@link alpha.codegen.Expression <em>Expression</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see alpha.codegen.Expression
		 * @see alpha.codegen.impl.CodegenPackageImpl#getExpression()
		 * @generated
		 */
		EClass EXPRESSION = eINSTANCE.getExpression();

		/**
		 * The meta object literal for the '{@link alpha.codegen.impl.CustomExprImpl <em>Custom Expr</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see alpha.codegen.impl.CustomExprImpl
		 * @see alpha.codegen.impl.CodegenPackageImpl#getCustomExpr()
		 * @generated
		 */
		EClass CUSTOM_EXPR = eINSTANCE.getCustomExpr();

		/**
		 * The meta object literal for the '<em><b>Expression</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CUSTOM_EXPR__EXPRESSION = eINSTANCE.getCustomExpr_Expression();

		/**
		 * The meta object literal for the '{@link alpha.codegen.impl.ParenthesizedExprImpl <em>Parenthesized Expr</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see alpha.codegen.impl.ParenthesizedExprImpl
		 * @see alpha.codegen.impl.CodegenPackageImpl#getParenthesizedExpr()
		 * @generated
		 */
		EClass PARENTHESIZED_EXPR = eINSTANCE.getParenthesizedExpr();

		/**
		 * The meta object literal for the '<em><b>Expression</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PARENTHESIZED_EXPR__EXPRESSION = eINSTANCE.getParenthesizedExpr_Expression();

		/**
		 * The meta object literal for the '{@link alpha.codegen.impl.CastExprImpl <em>Cast Expr</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see alpha.codegen.impl.CastExprImpl
		 * @see alpha.codegen.impl.CodegenPackageImpl#getCastExpr()
		 * @generated
		 */
		EClass CAST_EXPR = eINSTANCE.getCastExpr();

		/**
		 * The meta object literal for the '<em><b>Data Type</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CAST_EXPR__DATA_TYPE = eINSTANCE.getCastExpr_DataType();

		/**
		 * The meta object literal for the '<em><b>Expression</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CAST_EXPR__EXPRESSION = eINSTANCE.getCastExpr_Expression();

		/**
		 * The meta object literal for the '{@link alpha.codegen.impl.ArrayAccessExprImpl <em>Array Access Expr</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see alpha.codegen.impl.ArrayAccessExprImpl
		 * @see alpha.codegen.impl.CodegenPackageImpl#getArrayAccessExpr()
		 * @generated
		 */
		EClass ARRAY_ACCESS_EXPR = eINSTANCE.getArrayAccessExpr();

		/**
		 * The meta object literal for the '<em><b>Variable Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ARRAY_ACCESS_EXPR__VARIABLE_NAME = eINSTANCE.getArrayAccessExpr_VariableName();

		/**
		 * The meta object literal for the '<em><b>Index Expressions</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ARRAY_ACCESS_EXPR__INDEX_EXPRESSIONS = eINSTANCE.getArrayAccessExpr_IndexExpressions();

		/**
		 * The meta object literal for the '{@link alpha.codegen.impl.CallExprImpl <em>Call Expr</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see alpha.codegen.impl.CallExprImpl
		 * @see alpha.codegen.impl.CodegenPackageImpl#getCallExpr()
		 * @generated
		 */
		EClass CALL_EXPR = eINSTANCE.getCallExpr();

		/**
		 * The meta object literal for the '<em><b>Function Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CALL_EXPR__FUNCTION_NAME = eINSTANCE.getCallExpr_FunctionName();

		/**
		 * The meta object literal for the '<em><b>Arguments</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CALL_EXPR__ARGUMENTS = eINSTANCE.getCallExpr_Arguments();

		/**
		 * The meta object literal for the '{@link alpha.codegen.impl.UnaryExprImpl <em>Unary Expr</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see alpha.codegen.impl.UnaryExprImpl
		 * @see alpha.codegen.impl.CodegenPackageImpl#getUnaryExpr()
		 * @generated
		 */
		EClass UNARY_EXPR = eINSTANCE.getUnaryExpr();

		/**
		 * The meta object literal for the '<em><b>Operator</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute UNARY_EXPR__OPERATOR = eINSTANCE.getUnaryExpr_Operator();

		/**
		 * The meta object literal for the '<em><b>Expression</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference UNARY_EXPR__EXPRESSION = eINSTANCE.getUnaryExpr_Expression();

		/**
		 * The meta object literal for the '{@link alpha.codegen.impl.BinaryExprImpl <em>Binary Expr</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see alpha.codegen.impl.BinaryExprImpl
		 * @see alpha.codegen.impl.CodegenPackageImpl#getBinaryExpr()
		 * @generated
		 */
		EClass BINARY_EXPR = eINSTANCE.getBinaryExpr();

		/**
		 * The meta object literal for the '<em><b>Operator</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute BINARY_EXPR__OPERATOR = eINSTANCE.getBinaryExpr_Operator();

		/**
		 * The meta object literal for the '<em><b>Left</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference BINARY_EXPR__LEFT = eINSTANCE.getBinaryExpr_Left();

		/**
		 * The meta object literal for the '<em><b>Right</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference BINARY_EXPR__RIGHT = eINSTANCE.getBinaryExpr_Right();

		/**
		 * The meta object literal for the '{@link alpha.codegen.impl.TernaryExprImpl <em>Ternary Expr</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see alpha.codegen.impl.TernaryExprImpl
		 * @see alpha.codegen.impl.CodegenPackageImpl#getTernaryExpr()
		 * @generated
		 */
		EClass TERNARY_EXPR = eINSTANCE.getTernaryExpr();

		/**
		 * The meta object literal for the '<em><b>Conditional</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TERNARY_EXPR__CONDITIONAL = eINSTANCE.getTernaryExpr_Conditional();

		/**
		 * The meta object literal for the '<em><b>Then Expr</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TERNARY_EXPR__THEN_EXPR = eINSTANCE.getTernaryExpr_ThenExpr();

		/**
		 * The meta object literal for the '<em><b>Else Expr</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TERNARY_EXPR__ELSE_EXPR = eINSTANCE.getTernaryExpr_ElseExpr();

		/**
		 * The meta object literal for the '{@link alpha.codegen.BaseDataType <em>Base Data Type</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see alpha.codegen.BaseDataType
		 * @see alpha.codegen.impl.CodegenPackageImpl#getBaseDataType()
		 * @generated
		 */
		EEnum BASE_DATA_TYPE = eINSTANCE.getBaseDataType();

		/**
		 * The meta object literal for the '{@link alpha.codegen.AssignmentOperator <em>Assignment Operator</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see alpha.codegen.AssignmentOperator
		 * @see alpha.codegen.impl.CodegenPackageImpl#getAssignmentOperator()
		 * @generated
		 */
		EEnum ASSIGNMENT_OPERATOR = eINSTANCE.getAssignmentOperator();

		/**
		 * The meta object literal for the '{@link alpha.codegen.UnaryOperator <em>Unary Operator</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see alpha.codegen.UnaryOperator
		 * @see alpha.codegen.impl.CodegenPackageImpl#getUnaryOperator()
		 * @generated
		 */
		EEnum UNARY_OPERATOR = eINSTANCE.getUnaryOperator();

		/**
		 * The meta object literal for the '{@link alpha.codegen.BinaryOperator <em>Binary Operator</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see alpha.codegen.BinaryOperator
		 * @see alpha.codegen.impl.CodegenPackageImpl#getBinaryOperator()
		 * @generated
		 */
		EEnum BINARY_OPERATOR = eINSTANCE.getBinaryOperator();

		/**
		 * The meta object literal for the '<em>String Iterable</em>' data type.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see java.lang.Iterable
		 * @see alpha.codegen.impl.CodegenPackageImpl#getStringIterable()
		 * @generated
		 */
		EDataType STRING_ITERABLE = eINSTANCE.getStringIterable();

	}

} //CodegenPackage
