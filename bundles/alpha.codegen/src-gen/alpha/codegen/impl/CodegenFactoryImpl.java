/**
 */
package alpha.codegen.impl;

import alpha.codegen.*;

import fr.irisa.cairn.jnimap.isl.ISLASTNode;
import fr.irisa.cairn.jnimap.isl.ISLAff;
import fr.irisa.cairn.jnimap.isl.ISLAffList;
import fr.irisa.cairn.jnimap.isl.ISLDimType;
import fr.irisa.cairn.jnimap.isl.ISLMap;
import fr.irisa.cairn.jnimap.isl.ISLPWQPolynomial;
import fr.irisa.cairn.jnimap.isl.ISLQPolynomial;
import fr.irisa.cairn.jnimap.isl.ISLQPolynomialPiece;
import fr.irisa.cairn.jnimap.isl.ISLSet;
import fr.irisa.cairn.jnimap.isl.ISLTerm;
import fr.irisa.cairn.jnimap.isl.ISL_FORMAT;

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
			case CodegenPackage.NODE: return createNode();
			case CodegenPackage.INCLUDE: return createInclude();
			case CodegenPackage.MACRO: return createMacro();
			case CodegenPackage.GLOBAL_MACRO: return createGlobalMacro();
			case CodegenPackage.MEMORY_MACRO: return createMemoryMacro();
			case CodegenPackage.MEMORY_ALLOCATION: return createMemoryAllocation();
			case CodegenPackage.GLOBAL_MEMORY_MACRO: return createGlobalMemoryMacro();
			case CodegenPackage.STATEMENT_MACRO: return createStatementMacro();
			case CodegenPackage.BASE_VARIABLE: return createBaseVariable();
			case CodegenPackage.GLOBAL_VARIABLE: return createGlobalVariable();
			case CodegenPackage.ARRAY_VARIABLE: return createArrayVariable();
			case CodegenPackage.FUNCTION_BODY: return createFunctionBody();
			case CodegenPackage.FUNCTION: return createFunction();
			case CodegenPackage.EVAL_FUNCTION: return createEvalFunction();
			case CodegenPackage.PROGRAM: return createProgram();
			case CodegenPackage.ALPHA_OP: return createAlphaOp();
			case CodegenPackage.POLYNOMIAL_VISITOR: return createPolynomialVisitor();
			case CodegenPackage.POLYNOMIAL_NODE: return createPolynomialNode();
			case CodegenPackage.POLYNOMIAL: return createPolynomial();
			case CodegenPackage.POLYNOMIAL_PIECE: return createPolynomialPiece();
			case CodegenPackage.POLYNOMIAL_TERM: return createPolynomialTerm();
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
			case CodegenPackage.VARIABLE_TYPE:
				return createVariableTypeFromString(eDataType, initialValue);
			case CodegenPackage.DATA_TYPE:
				return createDataTypeFromString(eDataType, initialValue);
			case CodegenPackage.CUNARY_OP:
				return createC_UNARY_OPFromString(eDataType, initialValue);
			case CodegenPackage.CBINARY_OP:
				return createC_BINARY_OPFromString(eDataType, initialValue);
			case CodegenPackage.CREDUCTION_OP:
				return createC_REDUCTION_OPFromString(eDataType, initialValue);
			case CodegenPackage.ISL_SET:
				return createISLSetFromString(eDataType, initialValue);
			case CodegenPackage.ISL_MAP:
				return createISLMapFromString(eDataType, initialValue);
			case CodegenPackage.ISLAST_NODE:
				return createISLASTNodeFromString(eDataType, initialValue);
			case CodegenPackage.ISL_AFF:
				return createISLAffFromString(eDataType, initialValue);
			case CodegenPackage.ISL_AFF_LIST:
				return createISLAffListFromString(eDataType, initialValue);
			case CodegenPackage.ISL_DIM_TYPE:
				return createISLDimTypeFromString(eDataType, initialValue);
			case CodegenPackage.ISL_FORMAT:
				return createISL_FORMATFromString(eDataType, initialValue);
			case CodegenPackage.ISLPWQ_POLYNOMIAL:
				return createISLPWQPolynomialFromString(eDataType, initialValue);
			case CodegenPackage.ISLQ_POLYNOMIAL_PIECE:
				return createISLQPolynomialPieceFromString(eDataType, initialValue);
			case CodegenPackage.ISLQ_POLYNOMIAL:
				return createISLQPolynomialFromString(eDataType, initialValue);
			case CodegenPackage.ISL_TERM:
				return createISLTermFromString(eDataType, initialValue);
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
			case CodegenPackage.VARIABLE_TYPE:
				return convertVariableTypeToString(eDataType, instanceValue);
			case CodegenPackage.DATA_TYPE:
				return convertDataTypeToString(eDataType, instanceValue);
			case CodegenPackage.CUNARY_OP:
				return convertC_UNARY_OPToString(eDataType, instanceValue);
			case CodegenPackage.CBINARY_OP:
				return convertC_BINARY_OPToString(eDataType, instanceValue);
			case CodegenPackage.CREDUCTION_OP:
				return convertC_REDUCTION_OPToString(eDataType, instanceValue);
			case CodegenPackage.ISL_SET:
				return convertISLSetToString(eDataType, instanceValue);
			case CodegenPackage.ISL_MAP:
				return convertISLMapToString(eDataType, instanceValue);
			case CodegenPackage.ISLAST_NODE:
				return convertISLASTNodeToString(eDataType, instanceValue);
			case CodegenPackage.ISL_AFF:
				return convertISLAffToString(eDataType, instanceValue);
			case CodegenPackage.ISL_AFF_LIST:
				return convertISLAffListToString(eDataType, instanceValue);
			case CodegenPackage.ISL_DIM_TYPE:
				return convertISLDimTypeToString(eDataType, instanceValue);
			case CodegenPackage.ISL_FORMAT:
				return convertISL_FORMATToString(eDataType, instanceValue);
			case CodegenPackage.ISLPWQ_POLYNOMIAL:
				return convertISLPWQPolynomialToString(eDataType, instanceValue);
			case CodegenPackage.ISLQ_POLYNOMIAL_PIECE:
				return convertISLQPolynomialPieceToString(eDataType, instanceValue);
			case CodegenPackage.ISLQ_POLYNOMIAL:
				return convertISLQPolynomialToString(eDataType, instanceValue);
			case CodegenPackage.ISL_TERM:
				return convertISLTermToString(eDataType, instanceValue);
			default:
				throw new IllegalArgumentException("The datatype '" + eDataType.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Node createNode() {
		NodeImpl node = new NodeImpl();
		return node;
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
	public Macro createMacro() {
		MacroImpl macro = new MacroImpl();
		return macro;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public GlobalMacro createGlobalMacro() {
		GlobalMacroImpl globalMacro = new GlobalMacroImpl();
		return globalMacro;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public MemoryMacro createMemoryMacro() {
		MemoryMacroImpl memoryMacro = new MemoryMacroImpl();
		return memoryMacro;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public MemoryAllocation createMemoryAllocation() {
		MemoryAllocationImpl memoryAllocation = new MemoryAllocationImpl();
		return memoryAllocation;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public GlobalMemoryMacro createGlobalMemoryMacro() {
		GlobalMemoryMacroImpl globalMemoryMacro = new GlobalMemoryMacroImpl();
		return globalMemoryMacro;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public StatementMacro createStatementMacro() {
		StatementMacroImpl statementMacro = new StatementMacroImpl();
		return statementMacro;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public BaseVariable createBaseVariable() {
		BaseVariableImpl baseVariable = new BaseVariableImpl();
		return baseVariable;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public GlobalVariable createGlobalVariable() {
		GlobalVariableImpl globalVariable = new GlobalVariableImpl();
		return globalVariable;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ArrayVariable createArrayVariable() {
		ArrayVariableImpl arrayVariable = new ArrayVariableImpl();
		return arrayVariable;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public FunctionBody createFunctionBody() {
		FunctionBodyImpl functionBody = new FunctionBodyImpl();
		return functionBody;
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
	public EvalFunction createEvalFunction() {
		EvalFunctionImpl evalFunction = new EvalFunctionImpl();
		return evalFunction;
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
	public AlphaOp createAlphaOp() {
		AlphaOpImpl alphaOp = new AlphaOpImpl();
		return alphaOp;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PolynomialVisitor createPolynomialVisitor() {
		PolynomialVisitorImpl polynomialVisitor = new PolynomialVisitorImpl();
		return polynomialVisitor;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PolynomialNode createPolynomialNode() {
		PolynomialNodeImpl polynomialNode = new PolynomialNodeImpl();
		return polynomialNode;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Polynomial createPolynomial() {
		PolynomialImpl polynomial = new PolynomialImpl();
		return polynomial;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PolynomialPiece createPolynomialPiece() {
		PolynomialPieceImpl polynomialPiece = new PolynomialPieceImpl();
		return polynomialPiece;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PolynomialTerm createPolynomialTerm() {
		PolynomialTermImpl polynomialTerm = new PolynomialTermImpl();
		return polynomialTerm;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public VariableType createVariableTypeFromString(EDataType eDataType, String initialValue) {
		VariableType result = VariableType.get(initialValue);
		if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertVariableTypeToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DataType createDataTypeFromString(EDataType eDataType, String initialValue) {
		DataType result = DataType.get(initialValue);
		if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertDataTypeToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public C_UNARY_OP createC_UNARY_OPFromString(EDataType eDataType, String initialValue) {
		C_UNARY_OP result = C_UNARY_OP.get(initialValue);
		if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertC_UNARY_OPToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public C_BINARY_OP createC_BINARY_OPFromString(EDataType eDataType, String initialValue) {
		C_BINARY_OP result = C_BINARY_OP.get(initialValue);
		if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertC_BINARY_OPToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public C_REDUCTION_OP createC_REDUCTION_OPFromString(EDataType eDataType, String initialValue) {
		C_REDUCTION_OP result = C_REDUCTION_OP.get(initialValue);
		if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertC_REDUCTION_OPToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ISLSet createISLSetFromString(EDataType eDataType, String initialValue) {
		return (ISLSet)super.createFromString(eDataType, initialValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertISLSetToString(EDataType eDataType, Object instanceValue) {
		return super.convertToString(eDataType, instanceValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ISLMap createISLMapFromString(EDataType eDataType, String initialValue) {
		return (ISLMap)super.createFromString(eDataType, initialValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertISLMapToString(EDataType eDataType, Object instanceValue) {
		return super.convertToString(eDataType, instanceValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ISLASTNode createISLASTNodeFromString(EDataType eDataType, String initialValue) {
		return (ISLASTNode)super.createFromString(eDataType, initialValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertISLASTNodeToString(EDataType eDataType, Object instanceValue) {
		return super.convertToString(eDataType, instanceValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ISLAff createISLAffFromString(EDataType eDataType, String initialValue) {
		return (ISLAff)super.createFromString(eDataType, initialValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertISLAffToString(EDataType eDataType, Object instanceValue) {
		return super.convertToString(eDataType, instanceValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ISLAffList createISLAffListFromString(EDataType eDataType, String initialValue) {
		return (ISLAffList)super.createFromString(eDataType, initialValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertISLAffListToString(EDataType eDataType, Object instanceValue) {
		return super.convertToString(eDataType, instanceValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ISLDimType createISLDimTypeFromString(EDataType eDataType, String initialValue) {
		return (ISLDimType)super.createFromString(eDataType, initialValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertISLDimTypeToString(EDataType eDataType, Object instanceValue) {
		return super.convertToString(eDataType, instanceValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ISL_FORMAT createISL_FORMATFromString(EDataType eDataType, String initialValue) {
		return (ISL_FORMAT)super.createFromString(eDataType, initialValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertISL_FORMATToString(EDataType eDataType, Object instanceValue) {
		return super.convertToString(eDataType, instanceValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ISLPWQPolynomial createISLPWQPolynomialFromString(EDataType eDataType, String initialValue) {
		return (ISLPWQPolynomial)super.createFromString(eDataType, initialValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertISLPWQPolynomialToString(EDataType eDataType, Object instanceValue) {
		return super.convertToString(eDataType, instanceValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ISLQPolynomialPiece createISLQPolynomialPieceFromString(EDataType eDataType, String initialValue) {
		return (ISLQPolynomialPiece)super.createFromString(eDataType, initialValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertISLQPolynomialPieceToString(EDataType eDataType, Object instanceValue) {
		return super.convertToString(eDataType, instanceValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ISLQPolynomial createISLQPolynomialFromString(EDataType eDataType, String initialValue) {
		return (ISLQPolynomial)super.createFromString(eDataType, initialValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertISLQPolynomialToString(EDataType eDataType, Object instanceValue) {
		return super.convertToString(eDataType, instanceValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ISLTerm createISLTermFromString(EDataType eDataType, String initialValue) {
		return (ISLTerm)super.createFromString(eDataType, initialValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertISLTermToString(EDataType eDataType, Object instanceValue) {
		return super.convertToString(eDataType, instanceValue);
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
