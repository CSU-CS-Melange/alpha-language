/**
 */
package alpha.codegen.impl;

import alpha.codegen.AbstractMemoryMacro;
import alpha.codegen.AlphaOp;
import alpha.codegen.ArrayVariable;
import alpha.codegen.BaseVariable;
import alpha.codegen.C_BINARY_OP;
import alpha.codegen.C_REDUCTION_OP;
import alpha.codegen.C_UNARY_OP;
import alpha.codegen.CodegenFactory;
import alpha.codegen.CodegenPackage;
import alpha.codegen.DataType;
import alpha.codegen.EvalFunction;
import alpha.codegen.Function;
import alpha.codegen.FunctionBody;
import alpha.codegen.GlobalMacro;
import alpha.codegen.GlobalMemoryMacro;
import alpha.codegen.GlobalVariable;
import alpha.codegen.Include;
import alpha.codegen.Macro;
import alpha.codegen.MemoryAllocation;
import alpha.codegen.MemoryMacro;
import alpha.codegen.Node;
import alpha.codegen.Polynomial;
import alpha.codegen.PolynomialNode;
import alpha.codegen.PolynomialPiece;
import alpha.codegen.PolynomialTerm;
import alpha.codegen.PolynomialVisitable;
import alpha.codegen.PolynomialVisitor;
import alpha.codegen.Program;
import alpha.codegen.ReduceFunction;
import alpha.codegen.StatementMacro;
import alpha.codegen.VariableType;
import alpha.codegen.Visitable;
import alpha.codegen.Visitor;

import alpha.model.ModelPackage;

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

import java.util.HashMap;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EGenericType;
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
	private EClass nodeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass visitableEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass visitorEClass = null;

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
	private EClass macroEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass globalMacroEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass abstractMemoryMacroEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass memoryMacroEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass memoryAllocationEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass globalMemoryMacroEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass statementMacroEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass baseVariableEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass globalVariableEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass arrayVariableEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass functionBodyEClass = null;

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
	private EClass evalFunctionEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass reduceFunctionEClass = null;

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
	private EClass alphaOpEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass polynomialVisitorEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass polynomialNodeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass polynomialVisitableEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass polynomialEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass polynomialPieceEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass polynomialTermEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum variableTypeEEnum = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum dataTypeEEnum = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum c_UNARY_OPEEnum = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum c_BINARY_OPEEnum = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum c_REDUCTION_OPEEnum = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EDataType islSetEDataType = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EDataType islMapEDataType = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EDataType islastNodeEDataType = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EDataType islAffEDataType = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EDataType islAffListEDataType = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EDataType islDimTypeEDataType = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EDataType isL_FORMATEDataType = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EDataType islpwqPolynomialEDataType = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EDataType islqPolynomialPieceEDataType = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EDataType islqPolynomialEDataType = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EDataType islTermEDataType = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EDataType hashMapEDataType = null;

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
		ModelPackage.eINSTANCE.eClass();

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
	public EClass getNode() {
		return nodeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getVisitable() {
		return visitableEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getVisitor() {
		return visitorEClass;
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
	public EReference getInclude_Program() {
		return (EReference)includeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getInclude_Name() {
		return (EAttribute)includeEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getMacro() {
		return macroEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getMacro_Left() {
		return (EAttribute)macroEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getMacro_Right() {
		return (EAttribute)macroEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getGlobalMacro() {
		return globalMacroEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getGlobalMacro_Program() {
		return (EReference)globalMacroEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getAbstractMemoryMacro() {
		return abstractMemoryMacroEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getAbstractMemoryMacro_Map() {
		return (EAttribute)abstractMemoryMacroEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getMemoryMacro() {
		return memoryMacroEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getMemoryMacro_Function() {
		return (EReference)memoryMacroEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getMemoryMacro_Allocation() {
		return (EReference)memoryMacroEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getMemoryMacro_Variable() {
		return (EReference)memoryMacroEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getMemoryAllocation() {
		return memoryAllocationEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getMemoryAllocation_Macro() {
		return (EReference)memoryAllocationEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getMemoryAllocation_Variable() {
		return (EReference)memoryAllocationEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getMemoryAllocation_Map() {
		return (EAttribute)memoryAllocationEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getMemoryAllocation_Domain() {
		return (EAttribute)memoryAllocationEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getMemoryAllocation_ISLASTNode() {
		return (EAttribute)memoryAllocationEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getGlobalMemoryMacro() {
		return globalMemoryMacroEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getGlobalMemoryMacro_Variable() {
		return (EReference)globalMemoryMacroEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getStatementMacro() {
		return statementMacroEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getStatementMacro_FunctionBody() {
		return (EReference)statementMacroEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getBaseVariable() {
		return baseVariableEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getBaseVariable_Name() {
		return (EAttribute)baseVariableEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getBaseVariable_ElemType() {
		return (EAttribute)baseVariableEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getGlobalVariable() {
		return globalVariableEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getGlobalVariable_Program() {
		return (EReference)globalVariableEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getGlobalVariable_MemoryMacro() {
		return (EReference)globalVariableEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getGlobalVariable_AlphaVariable() {
		return (EReference)globalVariableEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getGlobalVariable_Type() {
		return (EAttribute)globalVariableEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getGlobalVariable_NumDims() {
		return (EAttribute)globalVariableEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getGlobalVariable_FlagVariable() {
		return (EAttribute)globalVariableEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getArrayVariable() {
		return arrayVariableEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getFunctionBody() {
		return functionBodyEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getFunctionBody_Function() {
		return (EReference)functionBodyEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getFunctionBody_StatementMacros() {
		return (EReference)functionBodyEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getFunctionBody_ISLASTNode() {
		return (EAttribute)functionBodyEClass.getEStructuralFeatures().get(2);
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
	public EReference getFunction_Program() {
		return (EReference)functionEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getFunction_ReturnType() {
		return (EAttribute)functionEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getFunction_Name() {
		return (EAttribute)functionEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getFunction_ScalarArgs() {
		return (EReference)functionEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getFunction_ArrayArgs() {
		return (EReference)functionEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getFunction_MemoryMacros() {
		return (EReference)functionEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getFunction_Body() {
		return (EReference)functionEClass.getEStructuralFeatures().get(6);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getEvalFunction() {
		return evalFunctionEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getEvalFunction_Variable() {
		return (EReference)evalFunctionEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getEvalFunction_FlagVariable() {
		return (EReference)evalFunctionEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getEvalFunction_Equation() {
		return (EReference)evalFunctionEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getReduceFunction() {
		return reduceFunctionEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getReduceFunction_ReduceExpr() {
		return (EReference)reduceFunctionEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getReduceFunction_ReduceVar() {
		return (EReference)reduceFunctionEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getReduceFunction_MacroName() {
		return (EAttribute)reduceFunctionEClass.getEStructuralFeatures().get(2);
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
	public EReference getProgram_System() {
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
	public EReference getProgram_CommonMacros() {
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
	public EReference getProgram_Functions() {
		return (EReference)programEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getProgram_ReduceFunctions() {
		return (EAttribute)programEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getAlphaOp() {
		return alphaOpEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getPolynomialVisitor() {
		return polynomialVisitorEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getPolynomialNode() {
		return polynomialNodeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getPolynomialVisitable() {
		return polynomialVisitableEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getPolynomial() {
		return polynomialEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getPolynomial_Pieces() {
		return (EReference)polynomialEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getPolynomial_IslPolynomial() {
		return (EAttribute)polynomialEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getPolynomialPiece() {
		return polynomialPieceEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getPolynomialPiece_Polynomial() {
		return (EReference)polynomialPieceEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getPolynomialPiece_Terms() {
		return (EReference)polynomialPieceEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getPolynomialPiece_IslPiece() {
		return (EAttribute)polynomialPieceEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getPolynomialTerm() {
		return polynomialTermEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getPolynomialTerm_PolynomialPiece() {
		return (EReference)polynomialTermEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getPolynomialTerm_IslTerm() {
		return (EAttribute)polynomialTermEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EEnum getVariableType() {
		return variableTypeEEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EEnum getDataType() {
		return dataTypeEEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EEnum getC_UNARY_OP() {
		return c_UNARY_OPEEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EEnum getC_BINARY_OP() {
		return c_BINARY_OPEEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EEnum getC_REDUCTION_OP() {
		return c_REDUCTION_OPEEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EDataType getISLSet() {
		return islSetEDataType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EDataType getISLMap() {
		return islMapEDataType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EDataType getISLASTNode() {
		return islastNodeEDataType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EDataType getISLAff() {
		return islAffEDataType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EDataType getISLAffList() {
		return islAffListEDataType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EDataType getISLDimType() {
		return islDimTypeEDataType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EDataType getISL_FORMAT() {
		return isL_FORMATEDataType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EDataType getISLPWQPolynomial() {
		return islpwqPolynomialEDataType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EDataType getISLQPolynomialPiece() {
		return islqPolynomialPieceEDataType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EDataType getISLQPolynomial() {
		return islqPolynomialEDataType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EDataType getISLTerm() {
		return islTermEDataType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EDataType getHashMap() {
		return hashMapEDataType;
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
		nodeEClass = createEClass(NODE);

		visitableEClass = createEClass(VISITABLE);

		visitorEClass = createEClass(VISITOR);

		includeEClass = createEClass(INCLUDE);
		createEReference(includeEClass, INCLUDE__PROGRAM);
		createEAttribute(includeEClass, INCLUDE__NAME);

		macroEClass = createEClass(MACRO);
		createEAttribute(macroEClass, MACRO__LEFT);
		createEAttribute(macroEClass, MACRO__RIGHT);

		globalMacroEClass = createEClass(GLOBAL_MACRO);
		createEReference(globalMacroEClass, GLOBAL_MACRO__PROGRAM);

		abstractMemoryMacroEClass = createEClass(ABSTRACT_MEMORY_MACRO);
		createEAttribute(abstractMemoryMacroEClass, ABSTRACT_MEMORY_MACRO__MAP);

		memoryMacroEClass = createEClass(MEMORY_MACRO);
		createEReference(memoryMacroEClass, MEMORY_MACRO__FUNCTION);
		createEReference(memoryMacroEClass, MEMORY_MACRO__ALLOCATION);
		createEReference(memoryMacroEClass, MEMORY_MACRO__VARIABLE);

		memoryAllocationEClass = createEClass(MEMORY_ALLOCATION);
		createEReference(memoryAllocationEClass, MEMORY_ALLOCATION__MACRO);
		createEReference(memoryAllocationEClass, MEMORY_ALLOCATION__VARIABLE);
		createEAttribute(memoryAllocationEClass, MEMORY_ALLOCATION__MAP);
		createEAttribute(memoryAllocationEClass, MEMORY_ALLOCATION__DOMAIN);
		createEAttribute(memoryAllocationEClass, MEMORY_ALLOCATION__ISLAST_NODE);

		globalMemoryMacroEClass = createEClass(GLOBAL_MEMORY_MACRO);
		createEReference(globalMemoryMacroEClass, GLOBAL_MEMORY_MACRO__VARIABLE);

		statementMacroEClass = createEClass(STATEMENT_MACRO);
		createEReference(statementMacroEClass, STATEMENT_MACRO__FUNCTION_BODY);

		baseVariableEClass = createEClass(BASE_VARIABLE);
		createEAttribute(baseVariableEClass, BASE_VARIABLE__NAME);
		createEAttribute(baseVariableEClass, BASE_VARIABLE__ELEM_TYPE);

		globalVariableEClass = createEClass(GLOBAL_VARIABLE);
		createEReference(globalVariableEClass, GLOBAL_VARIABLE__PROGRAM);
		createEReference(globalVariableEClass, GLOBAL_VARIABLE__MEMORY_MACRO);
		createEReference(globalVariableEClass, GLOBAL_VARIABLE__ALPHA_VARIABLE);
		createEAttribute(globalVariableEClass, GLOBAL_VARIABLE__TYPE);
		createEAttribute(globalVariableEClass, GLOBAL_VARIABLE__NUM_DIMS);
		createEAttribute(globalVariableEClass, GLOBAL_VARIABLE__FLAG_VARIABLE);

		arrayVariableEClass = createEClass(ARRAY_VARIABLE);

		functionBodyEClass = createEClass(FUNCTION_BODY);
		createEReference(functionBodyEClass, FUNCTION_BODY__FUNCTION);
		createEReference(functionBodyEClass, FUNCTION_BODY__STATEMENT_MACROS);
		createEAttribute(functionBodyEClass, FUNCTION_BODY__ISLAST_NODE);

		functionEClass = createEClass(FUNCTION);
		createEReference(functionEClass, FUNCTION__PROGRAM);
		createEAttribute(functionEClass, FUNCTION__RETURN_TYPE);
		createEAttribute(functionEClass, FUNCTION__NAME);
		createEReference(functionEClass, FUNCTION__SCALAR_ARGS);
		createEReference(functionEClass, FUNCTION__ARRAY_ARGS);
		createEReference(functionEClass, FUNCTION__MEMORY_MACROS);
		createEReference(functionEClass, FUNCTION__BODY);

		evalFunctionEClass = createEClass(EVAL_FUNCTION);
		createEReference(evalFunctionEClass, EVAL_FUNCTION__VARIABLE);
		createEReference(evalFunctionEClass, EVAL_FUNCTION__FLAG_VARIABLE);
		createEReference(evalFunctionEClass, EVAL_FUNCTION__EQUATION);

		reduceFunctionEClass = createEClass(REDUCE_FUNCTION);
		createEReference(reduceFunctionEClass, REDUCE_FUNCTION__REDUCE_EXPR);
		createEReference(reduceFunctionEClass, REDUCE_FUNCTION__REDUCE_VAR);
		createEAttribute(reduceFunctionEClass, REDUCE_FUNCTION__MACRO_NAME);

		programEClass = createEClass(PROGRAM);
		createEReference(programEClass, PROGRAM__SYSTEM);
		createEReference(programEClass, PROGRAM__INCLUDES);
		createEReference(programEClass, PROGRAM__COMMON_MACROS);
		createEReference(programEClass, PROGRAM__GLOBAL_VARIABLES);
		createEReference(programEClass, PROGRAM__FUNCTIONS);
		createEAttribute(programEClass, PROGRAM__REDUCE_FUNCTIONS);

		alphaOpEClass = createEClass(ALPHA_OP);

		polynomialVisitorEClass = createEClass(POLYNOMIAL_VISITOR);

		polynomialNodeEClass = createEClass(POLYNOMIAL_NODE);

		polynomialVisitableEClass = createEClass(POLYNOMIAL_VISITABLE);

		polynomialEClass = createEClass(POLYNOMIAL);
		createEReference(polynomialEClass, POLYNOMIAL__PIECES);
		createEAttribute(polynomialEClass, POLYNOMIAL__ISL_POLYNOMIAL);

		polynomialPieceEClass = createEClass(POLYNOMIAL_PIECE);
		createEReference(polynomialPieceEClass, POLYNOMIAL_PIECE__POLYNOMIAL);
		createEReference(polynomialPieceEClass, POLYNOMIAL_PIECE__TERMS);
		createEAttribute(polynomialPieceEClass, POLYNOMIAL_PIECE__ISL_PIECE);

		polynomialTermEClass = createEClass(POLYNOMIAL_TERM);
		createEReference(polynomialTermEClass, POLYNOMIAL_TERM__POLYNOMIAL_PIECE);
		createEAttribute(polynomialTermEClass, POLYNOMIAL_TERM__ISL_TERM);

		// Create enums
		variableTypeEEnum = createEEnum(VARIABLE_TYPE);
		dataTypeEEnum = createEEnum(DATA_TYPE);
		c_UNARY_OPEEnum = createEEnum(CUNARY_OP);
		c_BINARY_OPEEnum = createEEnum(CBINARY_OP);
		c_REDUCTION_OPEEnum = createEEnum(CREDUCTION_OP);

		// Create data types
		islSetEDataType = createEDataType(ISL_SET);
		islMapEDataType = createEDataType(ISL_MAP);
		islastNodeEDataType = createEDataType(ISLAST_NODE);
		islAffEDataType = createEDataType(ISL_AFF);
		islAffListEDataType = createEDataType(ISL_AFF_LIST);
		islDimTypeEDataType = createEDataType(ISL_DIM_TYPE);
		isL_FORMATEDataType = createEDataType(ISL_FORMAT);
		islpwqPolynomialEDataType = createEDataType(ISLPWQ_POLYNOMIAL);
		islqPolynomialPieceEDataType = createEDataType(ISLQ_POLYNOMIAL_PIECE);
		islqPolynomialEDataType = createEDataType(ISLQ_POLYNOMIAL);
		islTermEDataType = createEDataType(ISL_TERM);
		hashMapEDataType = createEDataType(HASH_MAP);
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
		ModelPackage theModelPackage = (ModelPackage)EPackage.Registry.INSTANCE.getEPackage(ModelPackage.eNS_URI);

		// Create type parameters
		addETypeParameter(hashMapEDataType, "K");
		addETypeParameter(hashMapEDataType, "V");

		// Set bounds for type parameters

		// Add supertypes to classes
		includeEClass.getESuperTypes().add(this.getNode());
		includeEClass.getESuperTypes().add(this.getVisitable());
		macroEClass.getESuperTypes().add(this.getNode());
		macroEClass.getESuperTypes().add(this.getVisitable());
		globalMacroEClass.getESuperTypes().add(this.getMacro());
		abstractMemoryMacroEClass.getESuperTypes().add(this.getMacro());
		memoryMacroEClass.getESuperTypes().add(this.getAbstractMemoryMacro());
		memoryAllocationEClass.getESuperTypes().add(this.getNode());
		memoryAllocationEClass.getESuperTypes().add(this.getVisitable());
		globalMemoryMacroEClass.getESuperTypes().add(this.getAbstractMemoryMacro());
		statementMacroEClass.getESuperTypes().add(this.getMacro());
		baseVariableEClass.getESuperTypes().add(this.getNode());
		baseVariableEClass.getESuperTypes().add(this.getVisitable());
		globalVariableEClass.getESuperTypes().add(this.getBaseVariable());
		arrayVariableEClass.getESuperTypes().add(this.getGlobalVariable());
		functionBodyEClass.getESuperTypes().add(this.getNode());
		functionBodyEClass.getESuperTypes().add(this.getVisitable());
		functionEClass.getESuperTypes().add(this.getNode());
		functionEClass.getESuperTypes().add(this.getVisitable());
		evalFunctionEClass.getESuperTypes().add(this.getFunction());
		reduceFunctionEClass.getESuperTypes().add(this.getFunction());
		programEClass.getESuperTypes().add(this.getNode());
		programEClass.getESuperTypes().add(this.getVisitable());
		polynomialEClass.getESuperTypes().add(this.getPolynomialNode());
		polynomialEClass.getESuperTypes().add(this.getPolynomialVisitable());
		polynomialPieceEClass.getESuperTypes().add(this.getPolynomialNode());
		polynomialPieceEClass.getESuperTypes().add(this.getPolynomialVisitable());
		polynomialTermEClass.getESuperTypes().add(this.getPolynomialNode());
		polynomialTermEClass.getESuperTypes().add(this.getPolynomialVisitable());

		// Initialize classes and features; add operations and parameters
		initEClass(nodeEClass, Node.class, "Node", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(visitableEClass, Visitable.class, "Visitable", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		EOperation op = addEOperation(visitableEClass, null, "accept", 0, 1, !IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getVisitor(), "visitor", 0, 1, !IS_UNIQUE, IS_ORDERED);

		addEOperation(visitableEClass, theEcorePackage.getEString(), "toSString", 0, 1, !IS_UNIQUE, IS_ORDERED);

		initEClass(visitorEClass, Visitor.class, "Visitor", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		op = addEOperation(visitorEClass, null, "visitProgram", 0, 1, !IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getProgram(), "cf", 0, 1, !IS_UNIQUE, IS_ORDERED);

		op = addEOperation(visitorEClass, null, "visitInclude", 0, 1, !IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getInclude(), "i", 0, 1, !IS_UNIQUE, IS_ORDERED);

		op = addEOperation(visitorEClass, null, "visitMacro", 0, 1, !IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getMacro(), "m", 0, 1, !IS_UNIQUE, IS_ORDERED);

		op = addEOperation(visitorEClass, null, "visitGlobalMacro", 0, 1, !IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getGlobalMacro(), "m", 0, 1, !IS_UNIQUE, IS_ORDERED);

		op = addEOperation(visitorEClass, null, "visitGlobalMemoryMacro", 0, 1, !IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getGlobalMemoryMacro(), "m", 0, 1, !IS_UNIQUE, IS_ORDERED);

		op = addEOperation(visitorEClass, null, "visitMemoryMacro", 0, 1, !IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getMemoryMacro(), "m", 0, 1, !IS_UNIQUE, IS_ORDERED);

		op = addEOperation(visitorEClass, null, "visitStatementMacro", 0, 1, !IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getStatementMacro(), "m", 0, 1, !IS_UNIQUE, IS_ORDERED);

		op = addEOperation(visitorEClass, null, "visitBaseVariable", 0, 1, !IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getBaseVariable(), "v", 0, 1, !IS_UNIQUE, IS_ORDERED);

		op = addEOperation(visitorEClass, null, "visitArrayVariable", 0, 1, !IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getArrayVariable(), "v", 0, 1, !IS_UNIQUE, IS_ORDERED);

		op = addEOperation(visitorEClass, null, "visitFunction", 0, 1, !IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getFunction(), "f", 0, 1, !IS_UNIQUE, IS_ORDERED);

		op = addEOperation(visitorEClass, null, "visitMemoryAllocation", 0, 1, !IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getMemoryAllocation(), "ma", 0, 1, !IS_UNIQUE, IS_ORDERED);

		op = addEOperation(visitorEClass, null, "visitFunctionBody", 0, 1, !IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getFunctionBody(), "cs", 0, 1, !IS_UNIQUE, IS_ORDERED);

		op = addEOperation(visitorEClass, null, "visitEvalFunction", 0, 1, !IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getEvalFunction(), "ef", 0, 1, !IS_UNIQUE, IS_ORDERED);

		op = addEOperation(visitorEClass, null, "visitReduceFunction", 0, 1, !IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getReduceFunction(), "rf", 0, 1, !IS_UNIQUE, IS_ORDERED);

		op = addEOperation(visitorEClass, null, "visitGlobalVariable", 0, 1, !IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getGlobalVariable(), "cgv", 0, 1, !IS_UNIQUE, IS_ORDERED);

		op = addEOperation(visitorEClass, null, "inProgram", 0, 1, !IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getProgram(), "cf", 0, 1, !IS_UNIQUE, IS_ORDERED);

		op = addEOperation(visitorEClass, null, "inInclude", 0, 1, !IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getInclude(), "i", 0, 1, !IS_UNIQUE, IS_ORDERED);

		op = addEOperation(visitorEClass, null, "inMacro", 0, 1, !IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getMacro(), "m", 0, 1, !IS_UNIQUE, IS_ORDERED);

		op = addEOperation(visitorEClass, null, "inGlobalMacro", 0, 1, !IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getGlobalMacro(), "m", 0, 1, !IS_UNIQUE, IS_ORDERED);

		op = addEOperation(visitorEClass, null, "inGlobalMemoryMacro", 0, 1, !IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getGlobalMemoryMacro(), "m", 0, 1, !IS_UNIQUE, IS_ORDERED);

		op = addEOperation(visitorEClass, null, "inMemoryMacro", 0, 1, !IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getMemoryMacro(), "m", 0, 1, !IS_UNIQUE, IS_ORDERED);

		op = addEOperation(visitorEClass, null, "inStatementMacro", 0, 1, !IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getStatementMacro(), "m", 0, 1, !IS_UNIQUE, IS_ORDERED);

		op = addEOperation(visitorEClass, null, "inBaseVariable", 0, 1, !IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getBaseVariable(), "v", 0, 1, !IS_UNIQUE, IS_ORDERED);

		op = addEOperation(visitorEClass, null, "inArrayVariable", 0, 1, !IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getArrayVariable(), "av", 0, 1, !IS_UNIQUE, IS_ORDERED);

		op = addEOperation(visitorEClass, null, "inFunction", 0, 1, !IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getFunction(), "f", 0, 1, !IS_UNIQUE, IS_ORDERED);

		op = addEOperation(visitorEClass, null, "inMemoryAllocation", 0, 1, !IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getMemoryAllocation(), "ma", 0, 1, !IS_UNIQUE, IS_ORDERED);

		op = addEOperation(visitorEClass, null, "inFunctionBody", 0, 1, !IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getFunctionBody(), "cs", 0, 1, !IS_UNIQUE, IS_ORDERED);

		op = addEOperation(visitorEClass, null, "inEvalFunction", 0, 1, !IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getEvalFunction(), "ef", 0, 1, !IS_UNIQUE, IS_ORDERED);

		op = addEOperation(visitorEClass, null, "inReduceFunction", 0, 1, !IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getReduceFunction(), "rf", 0, 1, !IS_UNIQUE, IS_ORDERED);

		op = addEOperation(visitorEClass, null, "inGlobalVariable", 0, 1, !IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getGlobalVariable(), "cgv", 0, 1, !IS_UNIQUE, IS_ORDERED);

		op = addEOperation(visitorEClass, null, "outProgram", 0, 1, !IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getProgram(), "cf", 0, 1, !IS_UNIQUE, IS_ORDERED);

		op = addEOperation(visitorEClass, null, "outInclude", 0, 1, !IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getInclude(), "i", 0, 1, !IS_UNIQUE, IS_ORDERED);

		op = addEOperation(visitorEClass, null, "outMacro", 0, 1, !IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getMacro(), "m", 0, 1, !IS_UNIQUE, IS_ORDERED);

		op = addEOperation(visitorEClass, null, "outGlobalMacro", 0, 1, !IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getGlobalMacro(), "m", 0, 1, !IS_UNIQUE, IS_ORDERED);

		op = addEOperation(visitorEClass, null, "outGlobalMemoryMacro", 0, 1, !IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getGlobalMemoryMacro(), "m", 0, 1, !IS_UNIQUE, IS_ORDERED);

		op = addEOperation(visitorEClass, null, "outMemoryMacro", 0, 1, !IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getMemoryMacro(), "m", 0, 1, !IS_UNIQUE, IS_ORDERED);

		op = addEOperation(visitorEClass, null, "outStatementMacro", 0, 1, !IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getStatementMacro(), "m", 0, 1, !IS_UNIQUE, IS_ORDERED);

		op = addEOperation(visitorEClass, null, "outBaseVariable", 0, 1, !IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getBaseVariable(), "v", 0, 1, !IS_UNIQUE, IS_ORDERED);

		op = addEOperation(visitorEClass, null, "outArrayVariable", 0, 1, !IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getArrayVariable(), "av", 0, 1, !IS_UNIQUE, IS_ORDERED);

		op = addEOperation(visitorEClass, null, "outFunction", 0, 1, !IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getFunction(), "f", 0, 1, !IS_UNIQUE, IS_ORDERED);

		op = addEOperation(visitorEClass, null, "outMemoryAllocation", 0, 1, !IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getMemoryAllocation(), "ma", 0, 1, !IS_UNIQUE, IS_ORDERED);

		op = addEOperation(visitorEClass, null, "outFunctionBody", 0, 1, !IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getFunctionBody(), "cs", 0, 1, !IS_UNIQUE, IS_ORDERED);

		op = addEOperation(visitorEClass, null, "outEvalFunction", 0, 1, !IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getEvalFunction(), "ef", 0, 1, !IS_UNIQUE, IS_ORDERED);

		op = addEOperation(visitorEClass, null, "outReduceFunction", 0, 1, !IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getReduceFunction(), "rf", 0, 1, !IS_UNIQUE, IS_ORDERED);

		op = addEOperation(visitorEClass, null, "outGlobalVariable", 0, 1, !IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getGlobalVariable(), "cgv", 0, 1, !IS_UNIQUE, IS_ORDERED);

		initEClass(includeEClass, Include.class, "Include", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getInclude_Program(), this.getProgram(), this.getProgram_Includes(), "program", null, 0, 1, Include.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getInclude_Name(), theEcorePackage.getEString(), "name", null, 0, 1, Include.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		op = addEOperation(includeEClass, null, "accept", 0, 1, !IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getVisitor(), "visitor", 0, 1, !IS_UNIQUE, IS_ORDERED);

		initEClass(macroEClass, Macro.class, "Macro", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getMacro_Left(), theEcorePackage.getEString(), "left", null, 0, 1, Macro.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getMacro_Right(), theEcorePackage.getEString(), "right", null, 0, 1, Macro.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		addEOperation(macroEClass, theEcorePackage.getEString(), "name", 0, 1, !IS_UNIQUE, IS_ORDERED);

		addEOperation(macroEClass, theEcorePackage.getEString(), "def", 0, 1, !IS_UNIQUE, IS_ORDERED);

		addEOperation(macroEClass, theEcorePackage.getEString(), "toCString", 0, 1, !IS_UNIQUE, IS_ORDERED);

		op = addEOperation(macroEClass, null, "accept", 0, 1, !IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getVisitor(), "visitor", 0, 1, !IS_UNIQUE, IS_ORDERED);

		initEClass(globalMacroEClass, GlobalMacro.class, "GlobalMacro", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getGlobalMacro_Program(), this.getProgram(), this.getProgram_CommonMacros(), "program", null, 0, 1, GlobalMacro.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		op = addEOperation(globalMacroEClass, null, "accept", 0, 1, !IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getVisitor(), "visitor", 0, 1, !IS_UNIQUE, IS_ORDERED);

		initEClass(abstractMemoryMacroEClass, AbstractMemoryMacro.class, "AbstractMemoryMacro", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getAbstractMemoryMacro_Map(), this.getISLMap(), "map", null, 0, 1, AbstractMemoryMacro.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		op = addEOperation(abstractMemoryMacroEClass, null, "accept", 0, 1, !IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getVisitor(), "visitor", 0, 1, !IS_UNIQUE, IS_ORDERED);

		initEClass(memoryMacroEClass, MemoryMacro.class, "MemoryMacro", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getMemoryMacro_Function(), this.getFunction(), this.getFunction_MemoryMacros(), "function", null, 0, 1, MemoryMacro.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getMemoryMacro_Allocation(), this.getMemoryAllocation(), null, "allocation", null, 0, 1, MemoryMacro.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getMemoryMacro_Variable(), this.getArrayVariable(), null, "variable", null, 0, 1, MemoryMacro.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		op = addEOperation(memoryMacroEClass, null, "accept", 0, 1, !IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getVisitor(), "visitor", 0, 1, !IS_UNIQUE, IS_ORDERED);

		initEClass(memoryAllocationEClass, MemoryAllocation.class, "MemoryAllocation", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getMemoryAllocation_Macro(), this.getMemoryMacro(), null, "macro", null, 0, 1, MemoryAllocation.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getMemoryAllocation_Variable(), this.getArrayVariable(), null, "variable", null, 0, 1, MemoryAllocation.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getMemoryAllocation_Map(), this.getISLMap(), "map", null, 0, 1, MemoryAllocation.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getMemoryAllocation_Domain(), this.getISLSet(), "domain", null, 0, 1, MemoryAllocation.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getMemoryAllocation_ISLASTNode(), this.getISLASTNode(), "ISLASTNode", null, 0, 1, MemoryAllocation.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		addEOperation(memoryAllocationEClass, null, "card", 0, 1, !IS_UNIQUE, IS_ORDERED);

		addEOperation(memoryAllocationEClass, this.getISLMap(), "map", 0, 1, !IS_UNIQUE, IS_ORDERED);

		op = addEOperation(memoryAllocationEClass, null, "accept", 0, 1, !IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getVisitor(), "visitor", 0, 1, !IS_UNIQUE, IS_ORDERED);

		initEClass(globalMemoryMacroEClass, GlobalMemoryMacro.class, "GlobalMemoryMacro", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getGlobalMemoryMacro_Variable(), this.getGlobalVariable(), this.getGlobalVariable_MemoryMacro(), "variable", null, 0, 1, GlobalMemoryMacro.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		op = addEOperation(globalMemoryMacroEClass, null, "accept", 0, 1, !IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getVisitor(), "visitor", 0, 1, !IS_UNIQUE, IS_ORDERED);

		initEClass(statementMacroEClass, StatementMacro.class, "StatementMacro", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getStatementMacro_FunctionBody(), this.getFunctionBody(), this.getFunctionBody_StatementMacros(), "functionBody", null, 0, 1, StatementMacro.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		op = addEOperation(statementMacroEClass, null, "accept", 0, 1, !IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getVisitor(), "visitor", 0, 1, !IS_UNIQUE, IS_ORDERED);

		initEClass(baseVariableEClass, BaseVariable.class, "BaseVariable", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getBaseVariable_Name(), theEcorePackage.getEString(), "name", null, 0, 1, BaseVariable.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getBaseVariable_ElemType(), this.getDataType(), "elemType", null, 0, 1, BaseVariable.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		addEOperation(baseVariableEClass, theEcorePackage.getEString(), "dataType", 0, 1, !IS_UNIQUE, IS_ORDERED);

		op = addEOperation(baseVariableEClass, null, "accept", 0, 1, !IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getVisitor(), "visitor", 0, 1, !IS_UNIQUE, IS_ORDERED);

		initEClass(globalVariableEClass, GlobalVariable.class, "GlobalVariable", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getGlobalVariable_Program(), this.getProgram(), this.getProgram_GlobalVariables(), "program", null, 0, 1, GlobalVariable.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getGlobalVariable_MemoryMacro(), this.getGlobalMemoryMacro(), this.getGlobalMemoryMacro_Variable(), "memoryMacro", null, 0, 1, GlobalVariable.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getGlobalVariable_AlphaVariable(), theModelPackage.getVariable(), null, "alphaVariable", null, 0, 1, GlobalVariable.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getGlobalVariable_Type(), this.getVariableType(), "type", null, 0, 1, GlobalVariable.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getGlobalVariable_NumDims(), theEcorePackage.getEInt(), "numDims", null, 0, 1, GlobalVariable.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getGlobalVariable_FlagVariable(), theEcorePackage.getEBoolean(), "flagVariable", null, 0, 1, GlobalVariable.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		addEOperation(globalVariableEClass, theEcorePackage.getEBoolean(), "hasAlphaVariable", 0, 1, !IS_UNIQUE, IS_ORDERED);

		addEOperation(globalVariableEClass, theEcorePackage.getEString(), "pointers", 0, 1, !IS_UNIQUE, IS_ORDERED);

		addEOperation(globalVariableEClass, theEcorePackage.getEString(), "dataType", 0, 1, !IS_UNIQUE, IS_ORDERED);

		addEOperation(globalVariableEClass, theEcorePackage.getEString(), "readName", 0, 1, !IS_UNIQUE, IS_ORDERED);

		addEOperation(globalVariableEClass, theEcorePackage.getEString(), "indices", 0, -1, !IS_UNIQUE, IS_ORDERED);

		addEOperation(globalVariableEClass, theEcorePackage.getEString(), "identityAccess", 0, 1, !IS_UNIQUE, IS_ORDERED);

		addEOperation(globalVariableEClass, theEcorePackage.getEString(), "writeName", 0, 1, !IS_UNIQUE, IS_ORDERED);

		op = addEOperation(globalVariableEClass, null, "accept", 0, 1, !IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getVisitor(), "visitor", 0, 1, !IS_UNIQUE, IS_ORDERED);

		initEClass(arrayVariableEClass, ArrayVariable.class, "ArrayVariable", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		op = addEOperation(arrayVariableEClass, null, "accept", 0, 1, !IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getVisitor(), "visitor", 0, 1, !IS_UNIQUE, IS_ORDERED);

		initEClass(functionBodyEClass, FunctionBody.class, "FunctionBody", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getFunctionBody_Function(), this.getFunction(), this.getFunction_Body(), "function", null, 0, 1, FunctionBody.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getFunctionBody_StatementMacros(), this.getStatementMacro(), this.getStatementMacro_FunctionBody(), "statementMacros", null, 0, -1, FunctionBody.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getFunctionBody_ISLASTNode(), this.getISLASTNode(), "ISLASTNode", null, 0, 1, FunctionBody.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		op = addEOperation(functionBodyEClass, null, "accept", 0, 1, !IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getVisitor(), "visitor", 0, 1, !IS_UNIQUE, IS_ORDERED);

		initEClass(functionEClass, Function.class, "Function", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getFunction_Program(), this.getProgram(), this.getProgram_Functions(), "program", null, 0, 1, Function.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getFunction_ReturnType(), this.getDataType(), "returnType", null, 0, 1, Function.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getFunction_Name(), theEcorePackage.getEString(), "name", null, 0, 1, Function.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getFunction_ScalarArgs(), this.getBaseVariable(), null, "scalarArgs", null, 0, -1, Function.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getFunction_ArrayArgs(), this.getArrayVariable(), null, "arrayArgs", null, 0, -1, Function.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getFunction_MemoryMacros(), this.getMemoryMacro(), this.getMemoryMacro_Function(), "memoryMacros", null, 0, -1, Function.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getFunction_Body(), this.getFunctionBody(), this.getFunctionBody_Function(), "body", null, 0, 1, Function.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		addEOperation(functionEClass, this.getBaseVariable(), "args", 0, -1, !IS_UNIQUE, IS_ORDERED);

		op = addEOperation(functionEClass, null, "accept", 0, 1, !IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getVisitor(), "visitor", 0, 1, !IS_UNIQUE, IS_ORDERED);

		addEOperation(functionEClass, theModelPackage.getAlphaSystem(), "system", 0, 1, !IS_UNIQUE, IS_ORDERED);

		initEClass(evalFunctionEClass, EvalFunction.class, "EvalFunction", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getEvalFunction_Variable(), this.getArrayVariable(), null, "variable", null, 0, 1, EvalFunction.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getEvalFunction_FlagVariable(), this.getArrayVariable(), null, "flagVariable", null, 0, 1, EvalFunction.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getEvalFunction_Equation(), theModelPackage.getStandardEquation(), null, "equation", null, 0, 1, EvalFunction.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		op = addEOperation(evalFunctionEClass, null, "accept", 0, 1, !IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getVisitor(), "visitor", 0, 1, !IS_UNIQUE, IS_ORDERED);

		initEClass(reduceFunctionEClass, ReduceFunction.class, "ReduceFunction", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getReduceFunction_ReduceExpr(), theModelPackage.getReduceExpression(), null, "reduceExpr", null, 0, 1, ReduceFunction.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getReduceFunction_ReduceVar(), this.getBaseVariable(), null, "reduceVar", null, 0, 1, ReduceFunction.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getReduceFunction_MacroName(), theEcorePackage.getEString(), "macroName", null, 0, 1, ReduceFunction.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		op = addEOperation(reduceFunctionEClass, null, "accept", 0, 1, !IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getVisitor(), "visitor", 0, 1, !IS_UNIQUE, IS_ORDERED);

		initEClass(programEClass, Program.class, "Program", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getProgram_System(), theModelPackage.getAlphaSystem(), null, "system", null, 0, 1, Program.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getProgram_Includes(), this.getInclude(), this.getInclude_Program(), "includes", null, 0, -1, Program.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getProgram_CommonMacros(), this.getGlobalMacro(), this.getGlobalMacro_Program(), "commonMacros", null, 0, -1, Program.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getProgram_GlobalVariables(), this.getGlobalVariable(), this.getGlobalVariable_Program(), "globalVariables", null, 0, -1, Program.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getProgram_Functions(), this.getFunction(), this.getFunction_Program(), "functions", null, 0, -1, Program.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		EGenericType g1 = createEGenericType(this.getHashMap());
		EGenericType g2 = createEGenericType(theModelPackage.getReduceExpression());
		g1.getETypeArguments().add(g2);
		g2 = createEGenericType(this.getReduceFunction());
		g1.getETypeArguments().add(g2);
		initEAttribute(getProgram_ReduceFunctions(), g1, "reduceFunctions", null, 0, 1, Program.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		op = addEOperation(programEClass, this.getGlobalVariable(), "getGlobalVariable", 0, 1, !IS_UNIQUE, IS_ORDERED);
		addEParameter(op, theModelPackage.getVariable(), "alphaVar", 0, 1, !IS_UNIQUE, IS_ORDERED);

		op = addEOperation(programEClass, null, "accept", 0, 1, !IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getVisitor(), "visitor", 0, 1, !IS_UNIQUE, IS_ORDERED);

		initEClass(alphaOpEClass, AlphaOp.class, "AlphaOp", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		op = addEOperation(alphaOpEClass, this.getC_UNARY_OP(), "toCUnaryOp", 0, 1, !IS_UNIQUE, IS_ORDERED);
		addEParameter(op, theModelPackage.getUNARY_OP(), "o", 0, 1, !IS_UNIQUE, IS_ORDERED);

		op = addEOperation(alphaOpEClass, this.getC_BINARY_OP(), "toCBinaryOp", 0, 1, !IS_UNIQUE, IS_ORDERED);
		addEParameter(op, theModelPackage.getBINARY_OP(), "o", 0, 1, !IS_UNIQUE, IS_ORDERED);

		op = addEOperation(alphaOpEClass, this.getC_REDUCTION_OP(), "toCReductionOp", 0, 1, !IS_UNIQUE, IS_ORDERED);
		addEParameter(op, theModelPackage.getREDUCTION_OP(), "o", 0, 1, !IS_UNIQUE, IS_ORDERED);

		initEClass(polynomialVisitorEClass, PolynomialVisitor.class, "PolynomialVisitor", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		op = addEOperation(polynomialVisitorEClass, null, "visitPolynomial", 0, 1, !IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getPolynomial(), "p", 0, 1, !IS_UNIQUE, IS_ORDERED);

		op = addEOperation(polynomialVisitorEClass, null, "visitPolynomialPiece", 0, 1, !IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getPolynomialPiece(), "pp", 0, 1, !IS_UNIQUE, IS_ORDERED);

		op = addEOperation(polynomialVisitorEClass, null, "visitPolynomialTerm", 0, 1, !IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getPolynomialTerm(), "pt", 0, 1, !IS_UNIQUE, IS_ORDERED);

		op = addEOperation(polynomialVisitorEClass, null, "inPolynomial", 0, 1, !IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getPolynomial(), "p", 0, 1, !IS_UNIQUE, IS_ORDERED);

		op = addEOperation(polynomialVisitorEClass, null, "inPolynomialPiece", 0, 1, !IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getPolynomialPiece(), "pp", 0, 1, !IS_UNIQUE, IS_ORDERED);

		op = addEOperation(polynomialVisitorEClass, null, "inPolynomialTerm", 0, 1, !IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getPolynomialTerm(), "pt", 0, 1, !IS_UNIQUE, IS_ORDERED);

		op = addEOperation(polynomialVisitorEClass, null, "outPolynomial", 0, 1, !IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getPolynomial(), "p", 0, 1, !IS_UNIQUE, IS_ORDERED);

		op = addEOperation(polynomialVisitorEClass, null, "outPolynomialPiece", 0, 1, !IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getPolynomialPiece(), "pp", 0, 1, !IS_UNIQUE, IS_ORDERED);

		op = addEOperation(polynomialVisitorEClass, null, "outPolynomialTerm", 0, 1, !IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getPolynomialTerm(), "pt", 0, 1, !IS_UNIQUE, IS_ORDERED);

		initEClass(polynomialNodeEClass, PolynomialNode.class, "PolynomialNode", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(polynomialVisitableEClass, PolynomialVisitable.class, "PolynomialVisitable", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		op = addEOperation(polynomialVisitableEClass, null, "accept", 0, 1, !IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getPolynomialVisitor(), "visitor", 0, 1, !IS_UNIQUE, IS_ORDERED);

		initEClass(polynomialEClass, Polynomial.class, "Polynomial", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getPolynomial_Pieces(), this.getPolynomialPiece(), this.getPolynomialPiece_Polynomial(), "pieces", null, 0, -1, Polynomial.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getPolynomial_IslPolynomial(), this.getISLPWQPolynomial(), "islPolynomial", null, 0, 1, Polynomial.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		op = addEOperation(polynomialEClass, theEcorePackage.getEString(), "toCString", 0, 1, !IS_UNIQUE, IS_ORDERED);
		addEParameter(op, theEcorePackage.getEString(), "variableName", 0, 1, !IS_UNIQUE, IS_ORDERED);

		addEOperation(polynomialEClass, theEcorePackage.getEString(), "params", 0, -1, !IS_UNIQUE, IS_ORDERED);

		op = addEOperation(polynomialEClass, null, "accept", 0, 1, !IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getPolynomialVisitor(), "visitor", 0, 1, !IS_UNIQUE, IS_ORDERED);

		initEClass(polynomialPieceEClass, PolynomialPiece.class, "PolynomialPiece", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getPolynomialPiece_Polynomial(), this.getPolynomial(), this.getPolynomial_Pieces(), "polynomial", null, 0, 1, PolynomialPiece.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getPolynomialPiece_Terms(), this.getPolynomialTerm(), this.getPolynomialTerm_PolynomialPiece(), "terms", null, 0, -1, PolynomialPiece.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getPolynomialPiece_IslPiece(), this.getISLQPolynomialPiece(), "islPiece", null, 0, 1, PolynomialPiece.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		addEOperation(polynomialPieceEClass, this.getISLSet(), "getSet", 0, 1, !IS_UNIQUE, IS_ORDERED);

		op = addEOperation(polynomialPieceEClass, null, "accept", 0, 1, !IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getPolynomialVisitor(), "visitor", 0, 1, !IS_UNIQUE, IS_ORDERED);

		initEClass(polynomialTermEClass, PolynomialTerm.class, "PolynomialTerm", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getPolynomialTerm_PolynomialPiece(), this.getPolynomialPiece(), this.getPolynomialPiece_Terms(), "polynomialPiece", null, 0, 1, PolynomialTerm.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getPolynomialTerm_IslTerm(), this.getISLTerm(), "islTerm", null, 0, 1, PolynomialTerm.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		addEOperation(polynomialTermEClass, theEcorePackage.getEString(), "value", 0, 1, !IS_UNIQUE, IS_ORDERED);

		addEOperation(polynomialTermEClass, theEcorePackage.getEString(), "values", 0, -1, !IS_UNIQUE, IS_ORDERED);

		addEOperation(polynomialTermEClass, theEcorePackage.getEString(), "coefficient", 0, 1, !IS_UNIQUE, IS_ORDERED);

		op = addEOperation(polynomialTermEClass, theEcorePackage.getEString(), "exponent", 0, 1, !IS_UNIQUE, IS_ORDERED);
		addEParameter(op, theEcorePackage.getEInt(), "i", 0, 1, !IS_UNIQUE, IS_ORDERED);

		addEOperation(polynomialTermEClass, theEcorePackage.getEInt(), "exponents", 0, -1, !IS_UNIQUE, IS_ORDERED);

		addEOperation(polynomialTermEClass, theEcorePackage.getEInt(), "dimParam", 0, 1, !IS_UNIQUE, IS_ORDERED);

		op = addEOperation(polynomialTermEClass, theEcorePackage.getEInt(), "dim", 0, 1, !IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getISLDimType(), "dim_type", 0, 1, !IS_UNIQUE, IS_ORDERED);

		addEOperation(polynomialTermEClass, theEcorePackage.getEString(), "paramNames", 0, -1, !IS_UNIQUE, IS_ORDERED);

		op = addEOperation(polynomialTermEClass, null, "accept", 0, 1, !IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getPolynomialVisitor(), "visitor", 0, 1, !IS_UNIQUE, IS_ORDERED);

		// Initialize enums and add enum literals
		initEEnum(variableTypeEEnum, VariableType.class, "VariableType");
		addEEnumLiteral(variableTypeEEnum, VariableType.INPUT);
		addEEnumLiteral(variableTypeEEnum, VariableType.OUTPUT);
		addEEnumLiteral(variableTypeEEnum, VariableType.LOCAL);

		initEEnum(dataTypeEEnum, DataType.class, "DataType");
		addEEnumLiteral(dataTypeEEnum, DataType.INT);
		addEEnumLiteral(dataTypeEEnum, DataType.LONG);
		addEEnumLiteral(dataTypeEEnum, DataType.FLOAT);
		addEEnumLiteral(dataTypeEEnum, DataType.DOUBLE);
		addEEnumLiteral(dataTypeEEnum, DataType.VOID);
		addEEnumLiteral(dataTypeEEnum, DataType.CHAR);

		initEEnum(c_UNARY_OPEEnum, C_UNARY_OP.class, "C_UNARY_OP");
		addEEnumLiteral(c_UNARY_OPEEnum, C_UNARY_OP.NOT);
		addEEnumLiteral(c_UNARY_OPEEnum, C_UNARY_OP.NEGATE);

		initEEnum(c_BINARY_OPEEnum, C_BINARY_OP.class, "C_BINARY_OP");
		addEEnumLiteral(c_BINARY_OPEEnum, C_BINARY_OP.MIN);
		addEEnumLiteral(c_BINARY_OPEEnum, C_BINARY_OP.MAX);
		addEEnumLiteral(c_BINARY_OPEEnum, C_BINARY_OP.MUL);
		addEEnumLiteral(c_BINARY_OPEEnum, C_BINARY_OP.DIV);
		addEEnumLiteral(c_BINARY_OPEEnum, C_BINARY_OP.MOD);
		addEEnumLiteral(c_BINARY_OPEEnum, C_BINARY_OP.ADD);
		addEEnumLiteral(c_BINARY_OPEEnum, C_BINARY_OP.SUB);
		addEEnumLiteral(c_BINARY_OPEEnum, C_BINARY_OP.AND);
		addEEnumLiteral(c_BINARY_OPEEnum, C_BINARY_OP.OR);
		addEEnumLiteral(c_BINARY_OPEEnum, C_BINARY_OP.XOR);
		addEEnumLiteral(c_BINARY_OPEEnum, C_BINARY_OP.EQ);
		addEEnumLiteral(c_BINARY_OPEEnum, C_BINARY_OP.NE);
		addEEnumLiteral(c_BINARY_OPEEnum, C_BINARY_OP.GE);
		addEEnumLiteral(c_BINARY_OPEEnum, C_BINARY_OP.GT);
		addEEnumLiteral(c_BINARY_OPEEnum, C_BINARY_OP.LE);
		addEEnumLiteral(c_BINARY_OPEEnum, C_BINARY_OP.LT);

		initEEnum(c_REDUCTION_OPEEnum, C_REDUCTION_OP.class, "C_REDUCTION_OP");
		addEEnumLiteral(c_REDUCTION_OPEEnum, C_REDUCTION_OP.MIN);
		addEEnumLiteral(c_REDUCTION_OPEEnum, C_REDUCTION_OP.MAX);
		addEEnumLiteral(c_REDUCTION_OPEEnum, C_REDUCTION_OP.PROD);
		addEEnumLiteral(c_REDUCTION_OPEEnum, C_REDUCTION_OP.SUM);
		addEEnumLiteral(c_REDUCTION_OPEEnum, C_REDUCTION_OP.AND);
		addEEnumLiteral(c_REDUCTION_OPEEnum, C_REDUCTION_OP.OR);
		addEEnumLiteral(c_REDUCTION_OPEEnum, C_REDUCTION_OP.XOR);
		addEEnumLiteral(c_REDUCTION_OPEEnum, C_REDUCTION_OP.EX);

		// Initialize data types
		initEDataType(islSetEDataType, ISLSet.class, "ISLSet", IS_SERIALIZABLE, !IS_GENERATED_INSTANCE_CLASS);
		initEDataType(islMapEDataType, ISLMap.class, "ISLMap", IS_SERIALIZABLE, !IS_GENERATED_INSTANCE_CLASS);
		initEDataType(islastNodeEDataType, ISLASTNode.class, "ISLASTNode", IS_SERIALIZABLE, !IS_GENERATED_INSTANCE_CLASS);
		initEDataType(islAffEDataType, ISLAff.class, "ISLAff", IS_SERIALIZABLE, !IS_GENERATED_INSTANCE_CLASS);
		initEDataType(islAffListEDataType, ISLAffList.class, "ISLAffList", IS_SERIALIZABLE, !IS_GENERATED_INSTANCE_CLASS);
		initEDataType(islDimTypeEDataType, ISLDimType.class, "ISLDimType", IS_SERIALIZABLE, !IS_GENERATED_INSTANCE_CLASS);
		initEDataType(isL_FORMATEDataType, fr.irisa.cairn.jnimap.isl.ISL_FORMAT.class, "ISL_FORMAT", IS_SERIALIZABLE, !IS_GENERATED_INSTANCE_CLASS);
		initEDataType(islpwqPolynomialEDataType, ISLPWQPolynomial.class, "ISLPWQPolynomial", IS_SERIALIZABLE, !IS_GENERATED_INSTANCE_CLASS);
		initEDataType(islqPolynomialPieceEDataType, ISLQPolynomialPiece.class, "ISLQPolynomialPiece", IS_SERIALIZABLE, !IS_GENERATED_INSTANCE_CLASS);
		initEDataType(islqPolynomialEDataType, ISLQPolynomial.class, "ISLQPolynomial", IS_SERIALIZABLE, !IS_GENERATED_INSTANCE_CLASS);
		initEDataType(islTermEDataType, ISLTerm.class, "ISLTerm", IS_SERIALIZABLE, !IS_GENERATED_INSTANCE_CLASS);
		initEDataType(hashMapEDataType, HashMap.class, "HashMap", IS_SERIALIZABLE, !IS_GENERATED_INSTANCE_CLASS);

		// Create resource
		createResource(eNS_URI);
	}

} //CodegenPackageImpl
