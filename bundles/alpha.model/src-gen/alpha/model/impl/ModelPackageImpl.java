/**
 */
package alpha.model.impl;

import alpha.model.AbstractFuzzyReduceExpression;
import alpha.model.AbstractReduceExpression;
import alpha.model.AffineFuzzyVariableUse;
import alpha.model.AlphaCompleteVisitable;
import alpha.model.AlphaConstant;
import alpha.model.AlphaElement;
import alpha.model.AlphaExpression;
import alpha.model.AlphaExpressionVisitable;
import alpha.model.AlphaExpressionVisitor;
import alpha.model.AlphaFunction;
import alpha.model.AlphaFunctionBinaryExpression;
import alpha.model.AlphaFunctionExpression;
import alpha.model.AlphaFunctionFloor;
import alpha.model.AlphaFunctionLiteral;
import alpha.model.AlphaNode;
import alpha.model.AlphaPackage;
import alpha.model.AlphaRoot;
import alpha.model.AlphaScheduleTarget;
import alpha.model.AlphaSystem;
import alpha.model.AlphaSystemElement;
import alpha.model.AlphaVisitable;
import alpha.model.AlphaVisitor;
import alpha.model.ArgReduceExpression;
import alpha.model.AutoRestrictExpression;
import alpha.model.BinaryCalculatorExpression;
import alpha.model.BinaryExpression;
import alpha.model.BooleanExpression;
import alpha.model.CalculatorExpression;
import alpha.model.CalculatorExpressionVisitable;
import alpha.model.CalculatorExpressionVisitor;
import alpha.model.CalculatorNode;
import alpha.model.CaseExpression;
import alpha.model.ConstantExpression;
import alpha.model.ConvolutionExpression;
import alpha.model.DefinedObject;
import alpha.model.DependenceExpression;
import alpha.model.Equation;
import alpha.model.ExternalArgReduceExpression;
import alpha.model.ExternalFunction;
import alpha.model.ExternalFuzzyArgReduceExpression;
import alpha.model.ExternalFuzzyReduceExpression;
import alpha.model.ExternalMultiArgExpression;
import alpha.model.ExternalReduceExpression;
import alpha.model.FuzzyArgReduceExpression;
import alpha.model.FuzzyDependenceExpression;
import alpha.model.FuzzyFunction;
import alpha.model.FuzzyFunctionInArrayNotation;
import alpha.model.FuzzyIndexExpression;
import alpha.model.FuzzyReduceExpression;
import alpha.model.FuzzyVariable;
import alpha.model.FuzzyVariableUse;
import alpha.model.IfExpression;
import alpha.model.Imports;
import alpha.model.IndexExpression;
import alpha.model.IntegerExpression;
import alpha.model.JNIDomain;
import alpha.model.JNIDomainInArrayNotation;
import alpha.model.JNIFunction;
import alpha.model.JNIFunctionInArrayNotation;
import alpha.model.JNIPolynomial;
import alpha.model.JNIPolynomialInArrayNotation;
import alpha.model.JNIRelation;
import alpha.model.ModelFactory;
import alpha.model.ModelPackage;
import alpha.model.MultiArgExpression;
import alpha.model.NestedFuzzyFunction;
import alpha.model.PolyhedralObject;
import alpha.model.PolynomialIndexExpression;
import alpha.model.RealExpression;
import alpha.model.RectangularDomain;
import alpha.model.ReduceExpression;
import alpha.model.RestrictExpression;
import alpha.model.SelectExpression;
import alpha.model.StandardEquation;
import alpha.model.SystemBody;
import alpha.model.UnaryCalculatorExpression;
import alpha.model.UnaryExpression;
import alpha.model.UseEquation;
import alpha.model.Variable;
import alpha.model.VariableDomain;
import alpha.model.VariableExpression;

import fr.irisa.cairn.jnimap.isl.ISLMap;
import fr.irisa.cairn.jnimap.isl.ISLMultiAff;
import fr.irisa.cairn.jnimap.isl.ISLPWQPolynomial;
import fr.irisa.cairn.jnimap.isl.ISLSet;

import fr.irisa.cairn.jnimap.runtime.JNIObject;

import java.util.List;
import java.util.Queue;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EOperation;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

import org.eclipse.emf.ecore.impl.EPackageImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class ModelPackageImpl extends EPackageImpl implements ModelPackage {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass alphaNodeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass alphaCompleteVisitableEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass alphaVisitableEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass alphaExpressionVisitableEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass calculatorExpressionVisitableEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass alphaSystemElementEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass alphaScheduleTargetEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass alphaVisitorEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass alphaExpressionVisitorEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass calculatorExpressionVisitorEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass alphaRootEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass alphaElementEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass importsEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass alphaPackageEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass alphaConstantEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass externalFunctionEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass alphaSystemEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass variableEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass fuzzyVariableEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass systemBodyEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass equationEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass standardEquationEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass useEquationEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass alphaExpressionEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass restrictExpressionEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass autoRestrictExpressionEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass caseExpressionEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass dependenceExpressionEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass fuzzyDependenceExpressionEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass ifExpressionEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass indexExpressionEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass polynomialIndexExpressionEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass fuzzyIndexExpressionEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass abstractReduceExpressionEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass reduceExpressionEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass externalReduceExpressionEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass argReduceExpressionEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass externalArgReduceExpressionEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass convolutionExpressionEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass selectExpressionEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass variableExpressionEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass constantExpressionEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass integerExpressionEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass realExpressionEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass booleanExpressionEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass unaryExpressionEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass binaryExpressionEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass multiArgExpressionEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass externalMultiArgExpressionEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass abstractFuzzyReduceExpressionEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass fuzzyReduceExpressionEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass externalFuzzyReduceExpressionEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass fuzzyArgReduceExpressionEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass externalFuzzyArgReduceExpressionEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass calculatorNodeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass polyhedralObjectEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass calculatorExpressionEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass jniDomainEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass jniDomainInArrayNotationEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass jniRelationEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass jniFunctionEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass jniFunctionInArrayNotationEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass jniPolynomialEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass jniPolynomialInArrayNotationEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass fuzzyFunctionEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass fuzzyVariableUseEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass nestedFuzzyFunctionEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass affineFuzzyVariableUseEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass fuzzyFunctionInArrayNotationEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass unaryCalculatorExpressionEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass binaryCalculatorExpressionEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass variableDomainEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass rectangularDomainEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass definedObjectEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass alphaFunctionEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass alphaFunctionExpressionEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass alphaFunctionBinaryExpressionEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass alphaFunctionLiteralEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass alphaFunctionFloorEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum unarY_OPEEnum = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum binarY_OPEEnum = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum reductioN_OPEEnum = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum polY_OBJECT_TYPEEEnum = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum calculatoR_UNARY_OPEEnum = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum calculatoR_BINARY_OPEEnum = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EDataType jniObjectEDataType = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EDataType jniislSetEDataType = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EDataType jniislMapEDataType = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EDataType jniislMultiAffEDataType = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EDataType jniislpwqPolynomialEDataType = null;

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
	private EDataType listVariableExpressionEDataType = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EDataType integerQueueEDataType = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EDataType stringEDataType = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EDataType intEDataType = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EDataType floatEDataType = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EDataType doubleEDataType = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EDataType booleanEDataType = null;

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
	 * @see alpha.model.ModelPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private ModelPackageImpl() {
		super(eNS_URI, ModelFactory.eINSTANCE);
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
	 * <p>This method is used to initialize {@link ModelPackage#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static ModelPackage init() {
		if (isInited) return (ModelPackage)EPackage.Registry.INSTANCE.getEPackage(ModelPackage.eNS_URI);

		// Obtain or create and register package
		Object registeredModelPackage = EPackage.Registry.INSTANCE.get(eNS_URI);
		ModelPackageImpl theModelPackage = registeredModelPackage instanceof ModelPackageImpl ? (ModelPackageImpl)registeredModelPackage : new ModelPackageImpl();

		isInited = true;

		// Create package meta-data objects
		theModelPackage.createPackageContents();

		// Initialize created meta-data
		theModelPackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theModelPackage.freeze();

		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(ModelPackage.eNS_URI, theModelPackage);
		return theModelPackage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getAlphaNode() {
		return alphaNodeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getAlphaNode_NodeID() {
		return (EAttribute)alphaNodeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getAlphaCompleteVisitable() {
		return alphaCompleteVisitableEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getAlphaVisitable() {
		return alphaVisitableEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getAlphaExpressionVisitable() {
		return alphaExpressionVisitableEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getCalculatorExpressionVisitable() {
		return calculatorExpressionVisitableEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getAlphaSystemElement() {
		return alphaSystemElementEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getAlphaScheduleTarget() {
		return alphaScheduleTargetEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getAlphaVisitor() {
		return alphaVisitorEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getAlphaExpressionVisitor() {
		return alphaExpressionVisitorEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getCalculatorExpressionVisitor() {
		return calculatorExpressionVisitorEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getAlphaRoot() {
		return alphaRootEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getAlphaRoot_Imports() {
		return (EReference)alphaRootEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getAlphaRoot_Elements() {
		return (EReference)alphaRootEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getAlphaElement() {
		return alphaElementEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getImports() {
		return importsEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getImports_ImportedNamespace() {
		return (EAttribute)importsEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getAlphaPackage() {
		return alphaPackageEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getAlphaPackage_Name() {
		return (EAttribute)alphaPackageEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getAlphaPackage_Elements() {
		return (EReference)alphaPackageEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getAlphaConstant() {
		return alphaConstantEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getAlphaConstant_Name() {
		return (EAttribute)alphaConstantEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getAlphaConstant_Value() {
		return (EAttribute)alphaConstantEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getExternalFunction() {
		return externalFunctionEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getExternalFunction_Name() {
		return (EAttribute)externalFunctionEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getExternalFunction_Cardinality() {
		return (EAttribute)externalFunctionEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getAlphaSystem() {
		return alphaSystemEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getAlphaSystem_Name() {
		return (EAttribute)alphaSystemEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getAlphaSystem_ParameterDomainExpr() {
		return (EReference)alphaSystemEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getAlphaSystem_DefinedObjects() {
		return (EReference)alphaSystemEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getAlphaSystem_Inputs() {
		return (EReference)alphaSystemEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getAlphaSystem_Outputs() {
		return (EReference)alphaSystemEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getAlphaSystem_Locals() {
		return (EReference)alphaSystemEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getAlphaSystem_WhileDomainExpr() {
		return (EReference)alphaSystemEClass.getEStructuralFeatures().get(6);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getAlphaSystem_TestExpression() {
		return (EReference)alphaSystemEClass.getEStructuralFeatures().get(7);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getAlphaSystem_SystemBodies() {
		return (EReference)alphaSystemEClass.getEStructuralFeatures().get(8);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getVariable() {
		return variableEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getVariable_Name() {
		return (EAttribute)variableEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getVariable_DomainExpr() {
		return (EReference)variableEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getFuzzyVariable() {
		return fuzzyVariableEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getFuzzyVariable_RangeExpr() {
		return (EReference)fuzzyVariableEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getSystemBody() {
		return systemBodyEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getSystemBody_System() {
		return (EReference)systemBodyEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getSystemBody_ParameterDomainExpr() {
		return (EReference)systemBodyEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getSystemBody_Equations() {
		return (EReference)systemBodyEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getEquation() {
		return equationEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getEquation_SystemBody() {
		return (EReference)equationEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getStandardEquation() {
		return standardEquationEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getStandardEquation_Variable() {
		return (EReference)standardEquationEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getStandardEquation_IndexNames() {
		return (EAttribute)standardEquationEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getStandardEquation_Expr() {
		return (EReference)standardEquationEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getUseEquation() {
		return useEquationEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getUseEquation_Identifier() {
		return (EAttribute)useEquationEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getUseEquation_InstantiationDomainExpr() {
		return (EReference)useEquationEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getUseEquation_SubsystemDims() {
		return (EAttribute)useEquationEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getUseEquation_System() {
		return (EReference)useEquationEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getUseEquation_CallParamsExpr() {
		return (EReference)useEquationEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getUseEquation_InputExprs() {
		return (EReference)useEquationEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getUseEquation_OutputExprs() {
		return (EReference)useEquationEClass.getEStructuralFeatures().get(6);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getAlphaExpression() {
		return alphaExpressionEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getAlphaExpression_Z__internal_cache_exprDom() {
		return (EAttribute)alphaExpressionEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getAlphaExpression_Z__internal_cache_contextDom() {
		return (EAttribute)alphaExpressionEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getAlphaExpression_ExpressionID() {
		return (EAttribute)alphaExpressionEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getRestrictExpression() {
		return restrictExpressionEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getRestrictExpression_DomainExpr() {
		return (EReference)restrictExpressionEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getRestrictExpression_Expr() {
		return (EReference)restrictExpressionEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getAutoRestrictExpression() {
		return autoRestrictExpressionEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getAutoRestrictExpression_Expr() {
		return (EReference)autoRestrictExpressionEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getAutoRestrictExpression_Z__internal_cache_inferredDomain() {
		return (EAttribute)autoRestrictExpressionEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getCaseExpression() {
		return caseExpressionEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getCaseExpression_Name() {
		return (EAttribute)caseExpressionEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getCaseExpression_Exprs() {
		return (EReference)caseExpressionEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getDependenceExpression() {
		return dependenceExpressionEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDependenceExpression_FunctionExpr() {
		return (EReference)dependenceExpressionEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDependenceExpression_Expr() {
		return (EReference)dependenceExpressionEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getFuzzyDependenceExpression() {
		return fuzzyDependenceExpressionEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getFuzzyDependenceExpression_FuzzyFunction() {
		return (EReference)fuzzyDependenceExpressionEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getFuzzyDependenceExpression_Expr() {
		return (EReference)fuzzyDependenceExpressionEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getIfExpression() {
		return ifExpressionEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getIfExpression_CondExpr() {
		return (EReference)ifExpressionEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getIfExpression_ThenExpr() {
		return (EReference)ifExpressionEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getIfExpression_ElseExpr() {
		return (EReference)ifExpressionEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getIndexExpression() {
		return indexExpressionEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getIndexExpression_FunctionExpr() {
		return (EReference)indexExpressionEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getPolynomialIndexExpression() {
		return polynomialIndexExpressionEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getPolynomialIndexExpression_PolynomialExpr() {
		return (EReference)polynomialIndexExpressionEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getFuzzyIndexExpression() {
		return fuzzyIndexExpressionEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getFuzzyIndexExpression_FuzzyFunction() {
		return (EReference)fuzzyIndexExpressionEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getAbstractReduceExpression() {
		return abstractReduceExpressionEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getAbstractReduceExpression_Operator() {
		return (EAttribute)abstractReduceExpressionEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getAbstractReduceExpression_ProjectionExpr() {
		return (EReference)abstractReduceExpressionEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getAbstractReduceExpression_Body() {
		return (EReference)abstractReduceExpressionEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getReduceExpression() {
		return reduceExpressionEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getExternalReduceExpression() {
		return externalReduceExpressionEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getExternalReduceExpression_ExternalFunction() {
		return (EReference)externalReduceExpressionEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getArgReduceExpression() {
		return argReduceExpressionEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getExternalArgReduceExpression() {
		return externalArgReduceExpressionEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getExternalArgReduceExpression_ExternalFunction() {
		return (EReference)externalArgReduceExpressionEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getConvolutionExpression() {
		return convolutionExpressionEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getConvolutionExpression_KernelDomainExpr() {
		return (EReference)convolutionExpressionEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getConvolutionExpression_KernelExpression() {
		return (EReference)convolutionExpressionEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getConvolutionExpression_DataExpression() {
		return (EReference)convolutionExpressionEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getSelectExpression() {
		return selectExpressionEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getSelectExpression_RelationExpr() {
		return (EReference)selectExpressionEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getSelectExpression_Expr() {
		return (EReference)selectExpressionEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getVariableExpression() {
		return variableExpressionEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getVariableExpression_Variable() {
		return (EReference)variableExpressionEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getConstantExpression() {
		return constantExpressionEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getIntegerExpression() {
		return integerExpressionEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getIntegerExpression_Value() {
		return (EAttribute)integerExpressionEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getRealExpression() {
		return realExpressionEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getRealExpression_Value() {
		return (EAttribute)realExpressionEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getBooleanExpression() {
		return booleanExpressionEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getBooleanExpression_Value() {
		return (EAttribute)booleanExpressionEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getUnaryExpression() {
		return unaryExpressionEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getUnaryExpression_Operator() {
		return (EAttribute)unaryExpressionEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getUnaryExpression_Expr() {
		return (EReference)unaryExpressionEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getBinaryExpression() {
		return binaryExpressionEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getBinaryExpression_Operator() {
		return (EAttribute)binaryExpressionEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getBinaryExpression_Left() {
		return (EReference)binaryExpressionEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getBinaryExpression_Right() {
		return (EReference)binaryExpressionEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getMultiArgExpression() {
		return multiArgExpressionEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getMultiArgExpression_Operator() {
		return (EAttribute)multiArgExpressionEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getMultiArgExpression_Exprs() {
		return (EReference)multiArgExpressionEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getExternalMultiArgExpression() {
		return externalMultiArgExpressionEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getExternalMultiArgExpression_ExternalFunction() {
		return (EReference)externalMultiArgExpressionEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getAbstractFuzzyReduceExpression() {
		return abstractFuzzyReduceExpressionEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getAbstractFuzzyReduceExpression_Operator() {
		return (EAttribute)abstractFuzzyReduceExpressionEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getAbstractFuzzyReduceExpression_ProjectionFunction() {
		return (EReference)abstractFuzzyReduceExpressionEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getAbstractFuzzyReduceExpression_Body() {
		return (EReference)abstractFuzzyReduceExpressionEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getFuzzyReduceExpression() {
		return fuzzyReduceExpressionEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getExternalFuzzyReduceExpression() {
		return externalFuzzyReduceExpressionEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getExternalFuzzyReduceExpression_ExternalFunction() {
		return (EReference)externalFuzzyReduceExpressionEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getFuzzyArgReduceExpression() {
		return fuzzyArgReduceExpressionEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getExternalFuzzyArgReduceExpression() {
		return externalFuzzyArgReduceExpressionEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getExternalFuzzyArgReduceExpression_ExternalFunction() {
		return (EReference)externalFuzzyArgReduceExpressionEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getCalculatorNode() {
		return calculatorNodeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getPolyhedralObject() {
		return polyhedralObjectEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getPolyhedralObject_Name() {
		return (EAttribute)polyhedralObjectEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getPolyhedralObject_Expr() {
		return (EReference)polyhedralObjectEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getCalculatorExpression() {
		return calculatorExpressionEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getJNIDomain() {
		return jniDomainEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getJNIDomain_IslString() {
		return (EAttribute)jniDomainEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getJNIDomain_Z__internal_cache_islSet() {
		return (EAttribute)jniDomainEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getJNIDomainInArrayNotation() {
		return jniDomainInArrayNotationEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getJNIRelation() {
		return jniRelationEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getJNIRelation_IslString() {
		return (EAttribute)jniRelationEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getJNIRelation_Z__internal_cache_islMap() {
		return (EAttribute)jniRelationEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getJNIFunction() {
		return jniFunctionEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getJNIFunction_AlphaFunction() {
		return (EReference)jniFunctionEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getJNIFunction_Z__internal_cache_islMAff() {
		return (EAttribute)jniFunctionEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getJNIFunctionInArrayNotation() {
		return jniFunctionInArrayNotationEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getJNIFunctionInArrayNotation_ArrayNotation() {
		return (EAttribute)jniFunctionInArrayNotationEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getJNIPolynomial() {
		return jniPolynomialEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getJNIPolynomial_IslString() {
		return (EAttribute)jniPolynomialEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getJNIPolynomial_Z__internal_cache_islPWQP() {
		return (EAttribute)jniPolynomialEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getJNIPolynomialInArrayNotation() {
		return jniPolynomialInArrayNotationEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getJNIPolynomialInArrayNotation_ArrayNotation() {
		return (EAttribute)jniPolynomialInArrayNotationEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getFuzzyFunction() {
		return fuzzyFunctionEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getFuzzyFunction_AlphaString() {
		return (EAttribute)fuzzyFunctionEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getFuzzyFunction_Indirections() {
		return (EReference)fuzzyFunctionEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getFuzzyFunction_Z__internal_cache_fuzzyMap() {
		return (EAttribute)fuzzyFunctionEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getFuzzyFunction_Z__internal_cache_depRelation() {
		return (EAttribute)fuzzyFunctionEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getFuzzyVariableUse() {
		return fuzzyVariableUseEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getFuzzyVariableUse_FuzzyIndex() {
		return (EAttribute)fuzzyVariableUseEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getFuzzyVariableUse_FuzzyVariable() {
		return (EReference)fuzzyVariableUseEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getNestedFuzzyFunction() {
		return nestedFuzzyFunctionEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getAffineFuzzyVariableUse() {
		return affineFuzzyVariableUseEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getAffineFuzzyVariableUse_UseFunction() {
		return (EReference)affineFuzzyVariableUseEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getFuzzyFunctionInArrayNotation() {
		return fuzzyFunctionInArrayNotationEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getFuzzyFunctionInArrayNotation_ArrayNotation() {
		return (EAttribute)fuzzyFunctionInArrayNotationEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getUnaryCalculatorExpression() {
		return unaryCalculatorExpressionEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getUnaryCalculatorExpression_Operator() {
		return (EAttribute)unaryCalculatorExpressionEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getUnaryCalculatorExpression_Expr() {
		return (EReference)unaryCalculatorExpressionEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getUnaryCalculatorExpression_Z__internal_cache_islObject() {
		return (EAttribute)unaryCalculatorExpressionEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getBinaryCalculatorExpression() {
		return binaryCalculatorExpressionEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getBinaryCalculatorExpression_Operator() {
		return (EAttribute)binaryCalculatorExpressionEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getBinaryCalculatorExpression_Left() {
		return (EReference)binaryCalculatorExpressionEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getBinaryCalculatorExpression_Right() {
		return (EReference)binaryCalculatorExpressionEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getBinaryCalculatorExpression_Z__internal_cache_islObject() {
		return (EAttribute)binaryCalculatorExpressionEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getVariableDomain() {
		return variableDomainEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getVariableDomain_Variable() {
		return (EReference)variableDomainEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getRectangularDomain() {
		return rectangularDomainEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getRectangularDomain_LowerBounds() {
		return (EAttribute)rectangularDomainEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getRectangularDomain_UpperBounds() {
		return (EAttribute)rectangularDomainEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getRectangularDomain_IndexNames() {
		return (EAttribute)rectangularDomainEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getRectangularDomain_Z__internal_cache_islSet() {
		return (EAttribute)rectangularDomainEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getDefinedObject() {
		return definedObjectEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDefinedObject_Object() {
		return (EReference)definedObjectEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getDefinedObject_Z__internalCycleDetector() {
		return (EAttribute)definedObjectEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getAlphaFunction() {
		return alphaFunctionEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getAlphaFunction_IndexList() {
		return (EAttribute)alphaFunctionEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getAlphaFunction_Exprs() {
		return (EReference)alphaFunctionEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getAlphaFunctionExpression() {
		return alphaFunctionExpressionEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getAlphaFunctionBinaryExpression() {
		return alphaFunctionBinaryExpressionEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getAlphaFunctionBinaryExpression_Left() {
		return (EReference)alphaFunctionBinaryExpressionEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getAlphaFunctionBinaryExpression_Right() {
		return (EReference)alphaFunctionBinaryExpressionEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getAlphaFunctionBinaryExpression_Operator() {
		return (EAttribute)alphaFunctionBinaryExpressionEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getAlphaFunctionLiteral() {
		return alphaFunctionLiteralEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getAlphaFunctionLiteral_Value() {
		return (EAttribute)alphaFunctionLiteralEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getAlphaFunctionFloor() {
		return alphaFunctionFloorEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getAlphaFunctionFloor_Expr() {
		return (EReference)alphaFunctionFloorEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EEnum getUNARY_OP() {
		return unarY_OPEEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EEnum getBINARY_OP() {
		return binarY_OPEEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EEnum getREDUCTION_OP() {
		return reductioN_OPEEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EEnum getPOLY_OBJECT_TYPE() {
		return polY_OBJECT_TYPEEEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EEnum getCALCULATOR_UNARY_OP() {
		return calculatoR_UNARY_OPEEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EEnum getCALCULATOR_BINARY_OP() {
		return calculatoR_BINARY_OPEEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EDataType getJNIObject() {
		return jniObjectEDataType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EDataType getJNIISLSet() {
		return jniislSetEDataType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EDataType getJNIISLMap() {
		return jniislMapEDataType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EDataType getJNIISLMultiAff() {
		return jniislMultiAffEDataType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EDataType getJNIISLPWQPolynomial() {
		return jniislpwqPolynomialEDataType;
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
	public EDataType getListVariableExpression() {
		return listVariableExpressionEDataType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EDataType getIntegerQueue() {
		return integerQueueEDataType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EDataType getString() {
		return stringEDataType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EDataType getint() {
		return intEDataType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EDataType getfloat() {
		return floatEDataType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EDataType getdouble() {
		return doubleEDataType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EDataType getboolean() {
		return booleanEDataType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ModelFactory getModelFactory() {
		return (ModelFactory)getEFactoryInstance();
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
		alphaNodeEClass = createEClass(ALPHA_NODE);
		createEAttribute(alphaNodeEClass, ALPHA_NODE__NODE_ID);

		alphaCompleteVisitableEClass = createEClass(ALPHA_COMPLETE_VISITABLE);

		alphaVisitableEClass = createEClass(ALPHA_VISITABLE);

		alphaExpressionVisitableEClass = createEClass(ALPHA_EXPRESSION_VISITABLE);

		calculatorExpressionVisitableEClass = createEClass(CALCULATOR_EXPRESSION_VISITABLE);

		alphaSystemElementEClass = createEClass(ALPHA_SYSTEM_ELEMENT);

		alphaScheduleTargetEClass = createEClass(ALPHA_SCHEDULE_TARGET);

		alphaVisitorEClass = createEClass(ALPHA_VISITOR);

		alphaExpressionVisitorEClass = createEClass(ALPHA_EXPRESSION_VISITOR);

		calculatorExpressionVisitorEClass = createEClass(CALCULATOR_EXPRESSION_VISITOR);

		alphaRootEClass = createEClass(ALPHA_ROOT);
		createEReference(alphaRootEClass, ALPHA_ROOT__IMPORTS);
		createEReference(alphaRootEClass, ALPHA_ROOT__ELEMENTS);

		alphaElementEClass = createEClass(ALPHA_ELEMENT);

		importsEClass = createEClass(IMPORTS);
		createEAttribute(importsEClass, IMPORTS__IMPORTED_NAMESPACE);

		alphaPackageEClass = createEClass(ALPHA_PACKAGE);
		createEAttribute(alphaPackageEClass, ALPHA_PACKAGE__NAME);
		createEReference(alphaPackageEClass, ALPHA_PACKAGE__ELEMENTS);

		alphaConstantEClass = createEClass(ALPHA_CONSTANT);
		createEAttribute(alphaConstantEClass, ALPHA_CONSTANT__NAME);
		createEAttribute(alphaConstantEClass, ALPHA_CONSTANT__VALUE);

		externalFunctionEClass = createEClass(EXTERNAL_FUNCTION);
		createEAttribute(externalFunctionEClass, EXTERNAL_FUNCTION__NAME);
		createEAttribute(externalFunctionEClass, EXTERNAL_FUNCTION__CARDINALITY);

		alphaSystemEClass = createEClass(ALPHA_SYSTEM);
		createEAttribute(alphaSystemEClass, ALPHA_SYSTEM__NAME);
		createEReference(alphaSystemEClass, ALPHA_SYSTEM__PARAMETER_DOMAIN_EXPR);
		createEReference(alphaSystemEClass, ALPHA_SYSTEM__DEFINED_OBJECTS);
		createEReference(alphaSystemEClass, ALPHA_SYSTEM__INPUTS);
		createEReference(alphaSystemEClass, ALPHA_SYSTEM__OUTPUTS);
		createEReference(alphaSystemEClass, ALPHA_SYSTEM__LOCALS);
		createEReference(alphaSystemEClass, ALPHA_SYSTEM__WHILE_DOMAIN_EXPR);
		createEReference(alphaSystemEClass, ALPHA_SYSTEM__TEST_EXPRESSION);
		createEReference(alphaSystemEClass, ALPHA_SYSTEM__SYSTEM_BODIES);

		variableEClass = createEClass(VARIABLE);
		createEAttribute(variableEClass, VARIABLE__NAME);
		createEReference(variableEClass, VARIABLE__DOMAIN_EXPR);

		fuzzyVariableEClass = createEClass(FUZZY_VARIABLE);
		createEReference(fuzzyVariableEClass, FUZZY_VARIABLE__RANGE_EXPR);

		systemBodyEClass = createEClass(SYSTEM_BODY);
		createEReference(systemBodyEClass, SYSTEM_BODY__SYSTEM);
		createEReference(systemBodyEClass, SYSTEM_BODY__PARAMETER_DOMAIN_EXPR);
		createEReference(systemBodyEClass, SYSTEM_BODY__EQUATIONS);

		equationEClass = createEClass(EQUATION);
		createEReference(equationEClass, EQUATION__SYSTEM_BODY);

		standardEquationEClass = createEClass(STANDARD_EQUATION);
		createEReference(standardEquationEClass, STANDARD_EQUATION__VARIABLE);
		createEAttribute(standardEquationEClass, STANDARD_EQUATION__INDEX_NAMES);
		createEReference(standardEquationEClass, STANDARD_EQUATION__EXPR);

		useEquationEClass = createEClass(USE_EQUATION);
		createEAttribute(useEquationEClass, USE_EQUATION__IDENTIFIER);
		createEReference(useEquationEClass, USE_EQUATION__INSTANTIATION_DOMAIN_EXPR);
		createEAttribute(useEquationEClass, USE_EQUATION__SUBSYSTEM_DIMS);
		createEReference(useEquationEClass, USE_EQUATION__SYSTEM);
		createEReference(useEquationEClass, USE_EQUATION__CALL_PARAMS_EXPR);
		createEReference(useEquationEClass, USE_EQUATION__INPUT_EXPRS);
		createEReference(useEquationEClass, USE_EQUATION__OUTPUT_EXPRS);

		alphaExpressionEClass = createEClass(ALPHA_EXPRESSION);
		createEAttribute(alphaExpressionEClass, ALPHA_EXPRESSION__ZINTERNAL_CACHE_EXPR_DOM);
		createEAttribute(alphaExpressionEClass, ALPHA_EXPRESSION__ZINTERNAL_CACHE_CONTEXT_DOM);
		createEAttribute(alphaExpressionEClass, ALPHA_EXPRESSION__EXPRESSION_ID);

		restrictExpressionEClass = createEClass(RESTRICT_EXPRESSION);
		createEReference(restrictExpressionEClass, RESTRICT_EXPRESSION__DOMAIN_EXPR);
		createEReference(restrictExpressionEClass, RESTRICT_EXPRESSION__EXPR);

		autoRestrictExpressionEClass = createEClass(AUTO_RESTRICT_EXPRESSION);
		createEReference(autoRestrictExpressionEClass, AUTO_RESTRICT_EXPRESSION__EXPR);
		createEAttribute(autoRestrictExpressionEClass, AUTO_RESTRICT_EXPRESSION__ZINTERNAL_CACHE_INFERRED_DOMAIN);

		caseExpressionEClass = createEClass(CASE_EXPRESSION);
		createEAttribute(caseExpressionEClass, CASE_EXPRESSION__NAME);
		createEReference(caseExpressionEClass, CASE_EXPRESSION__EXPRS);

		dependenceExpressionEClass = createEClass(DEPENDENCE_EXPRESSION);
		createEReference(dependenceExpressionEClass, DEPENDENCE_EXPRESSION__FUNCTION_EXPR);
		createEReference(dependenceExpressionEClass, DEPENDENCE_EXPRESSION__EXPR);

		fuzzyDependenceExpressionEClass = createEClass(FUZZY_DEPENDENCE_EXPRESSION);
		createEReference(fuzzyDependenceExpressionEClass, FUZZY_DEPENDENCE_EXPRESSION__FUZZY_FUNCTION);
		createEReference(fuzzyDependenceExpressionEClass, FUZZY_DEPENDENCE_EXPRESSION__EXPR);

		ifExpressionEClass = createEClass(IF_EXPRESSION);
		createEReference(ifExpressionEClass, IF_EXPRESSION__COND_EXPR);
		createEReference(ifExpressionEClass, IF_EXPRESSION__THEN_EXPR);
		createEReference(ifExpressionEClass, IF_EXPRESSION__ELSE_EXPR);

		indexExpressionEClass = createEClass(INDEX_EXPRESSION);
		createEReference(indexExpressionEClass, INDEX_EXPRESSION__FUNCTION_EXPR);

		polynomialIndexExpressionEClass = createEClass(POLYNOMIAL_INDEX_EXPRESSION);
		createEReference(polynomialIndexExpressionEClass, POLYNOMIAL_INDEX_EXPRESSION__POLYNOMIAL_EXPR);

		fuzzyIndexExpressionEClass = createEClass(FUZZY_INDEX_EXPRESSION);
		createEReference(fuzzyIndexExpressionEClass, FUZZY_INDEX_EXPRESSION__FUZZY_FUNCTION);

		abstractReduceExpressionEClass = createEClass(ABSTRACT_REDUCE_EXPRESSION);
		createEAttribute(abstractReduceExpressionEClass, ABSTRACT_REDUCE_EXPRESSION__OPERATOR);
		createEReference(abstractReduceExpressionEClass, ABSTRACT_REDUCE_EXPRESSION__PROJECTION_EXPR);
		createEReference(abstractReduceExpressionEClass, ABSTRACT_REDUCE_EXPRESSION__BODY);

		reduceExpressionEClass = createEClass(REDUCE_EXPRESSION);

		externalReduceExpressionEClass = createEClass(EXTERNAL_REDUCE_EXPRESSION);
		createEReference(externalReduceExpressionEClass, EXTERNAL_REDUCE_EXPRESSION__EXTERNAL_FUNCTION);

		argReduceExpressionEClass = createEClass(ARG_REDUCE_EXPRESSION);

		externalArgReduceExpressionEClass = createEClass(EXTERNAL_ARG_REDUCE_EXPRESSION);
		createEReference(externalArgReduceExpressionEClass, EXTERNAL_ARG_REDUCE_EXPRESSION__EXTERNAL_FUNCTION);

		convolutionExpressionEClass = createEClass(CONVOLUTION_EXPRESSION);
		createEReference(convolutionExpressionEClass, CONVOLUTION_EXPRESSION__KERNEL_DOMAIN_EXPR);
		createEReference(convolutionExpressionEClass, CONVOLUTION_EXPRESSION__KERNEL_EXPRESSION);
		createEReference(convolutionExpressionEClass, CONVOLUTION_EXPRESSION__DATA_EXPRESSION);

		selectExpressionEClass = createEClass(SELECT_EXPRESSION);
		createEReference(selectExpressionEClass, SELECT_EXPRESSION__RELATION_EXPR);
		createEReference(selectExpressionEClass, SELECT_EXPRESSION__EXPR);

		variableExpressionEClass = createEClass(VARIABLE_EXPRESSION);
		createEReference(variableExpressionEClass, VARIABLE_EXPRESSION__VARIABLE);

		constantExpressionEClass = createEClass(CONSTANT_EXPRESSION);

		integerExpressionEClass = createEClass(INTEGER_EXPRESSION);
		createEAttribute(integerExpressionEClass, INTEGER_EXPRESSION__VALUE);

		realExpressionEClass = createEClass(REAL_EXPRESSION);
		createEAttribute(realExpressionEClass, REAL_EXPRESSION__VALUE);

		booleanExpressionEClass = createEClass(BOOLEAN_EXPRESSION);
		createEAttribute(booleanExpressionEClass, BOOLEAN_EXPRESSION__VALUE);

		unaryExpressionEClass = createEClass(UNARY_EXPRESSION);
		createEAttribute(unaryExpressionEClass, UNARY_EXPRESSION__OPERATOR);
		createEReference(unaryExpressionEClass, UNARY_EXPRESSION__EXPR);

		binaryExpressionEClass = createEClass(BINARY_EXPRESSION);
		createEAttribute(binaryExpressionEClass, BINARY_EXPRESSION__OPERATOR);
		createEReference(binaryExpressionEClass, BINARY_EXPRESSION__LEFT);
		createEReference(binaryExpressionEClass, BINARY_EXPRESSION__RIGHT);

		multiArgExpressionEClass = createEClass(MULTI_ARG_EXPRESSION);
		createEAttribute(multiArgExpressionEClass, MULTI_ARG_EXPRESSION__OPERATOR);
		createEReference(multiArgExpressionEClass, MULTI_ARG_EXPRESSION__EXPRS);

		externalMultiArgExpressionEClass = createEClass(EXTERNAL_MULTI_ARG_EXPRESSION);
		createEReference(externalMultiArgExpressionEClass, EXTERNAL_MULTI_ARG_EXPRESSION__EXTERNAL_FUNCTION);

		abstractFuzzyReduceExpressionEClass = createEClass(ABSTRACT_FUZZY_REDUCE_EXPRESSION);
		createEAttribute(abstractFuzzyReduceExpressionEClass, ABSTRACT_FUZZY_REDUCE_EXPRESSION__OPERATOR);
		createEReference(abstractFuzzyReduceExpressionEClass, ABSTRACT_FUZZY_REDUCE_EXPRESSION__PROJECTION_FUNCTION);
		createEReference(abstractFuzzyReduceExpressionEClass, ABSTRACT_FUZZY_REDUCE_EXPRESSION__BODY);

		fuzzyReduceExpressionEClass = createEClass(FUZZY_REDUCE_EXPRESSION);

		externalFuzzyReduceExpressionEClass = createEClass(EXTERNAL_FUZZY_REDUCE_EXPRESSION);
		createEReference(externalFuzzyReduceExpressionEClass, EXTERNAL_FUZZY_REDUCE_EXPRESSION__EXTERNAL_FUNCTION);

		fuzzyArgReduceExpressionEClass = createEClass(FUZZY_ARG_REDUCE_EXPRESSION);

		externalFuzzyArgReduceExpressionEClass = createEClass(EXTERNAL_FUZZY_ARG_REDUCE_EXPRESSION);
		createEReference(externalFuzzyArgReduceExpressionEClass, EXTERNAL_FUZZY_ARG_REDUCE_EXPRESSION__EXTERNAL_FUNCTION);

		calculatorNodeEClass = createEClass(CALCULATOR_NODE);

		polyhedralObjectEClass = createEClass(POLYHEDRAL_OBJECT);
		createEAttribute(polyhedralObjectEClass, POLYHEDRAL_OBJECT__NAME);
		createEReference(polyhedralObjectEClass, POLYHEDRAL_OBJECT__EXPR);

		calculatorExpressionEClass = createEClass(CALCULATOR_EXPRESSION);

		jniDomainEClass = createEClass(JNI_DOMAIN);
		createEAttribute(jniDomainEClass, JNI_DOMAIN__ISL_STRING);
		createEAttribute(jniDomainEClass, JNI_DOMAIN__ZINTERNAL_CACHE_ISL_SET);

		jniDomainInArrayNotationEClass = createEClass(JNI_DOMAIN_IN_ARRAY_NOTATION);

		jniRelationEClass = createEClass(JNI_RELATION);
		createEAttribute(jniRelationEClass, JNI_RELATION__ISL_STRING);
		createEAttribute(jniRelationEClass, JNI_RELATION__ZINTERNAL_CACHE_ISL_MAP);

		jniFunctionEClass = createEClass(JNI_FUNCTION);
		createEReference(jniFunctionEClass, JNI_FUNCTION__ALPHA_FUNCTION);
		createEAttribute(jniFunctionEClass, JNI_FUNCTION__ZINTERNAL_CACHE_ISL_MAFF);

		jniFunctionInArrayNotationEClass = createEClass(JNI_FUNCTION_IN_ARRAY_NOTATION);
		createEAttribute(jniFunctionInArrayNotationEClass, JNI_FUNCTION_IN_ARRAY_NOTATION__ARRAY_NOTATION);

		jniPolynomialEClass = createEClass(JNI_POLYNOMIAL);
		createEAttribute(jniPolynomialEClass, JNI_POLYNOMIAL__ISL_STRING);
		createEAttribute(jniPolynomialEClass, JNI_POLYNOMIAL__ZINTERNAL_CACHE_ISL_PWQP);

		jniPolynomialInArrayNotationEClass = createEClass(JNI_POLYNOMIAL_IN_ARRAY_NOTATION);
		createEAttribute(jniPolynomialInArrayNotationEClass, JNI_POLYNOMIAL_IN_ARRAY_NOTATION__ARRAY_NOTATION);

		fuzzyFunctionEClass = createEClass(FUZZY_FUNCTION);
		createEAttribute(fuzzyFunctionEClass, FUZZY_FUNCTION__ALPHA_STRING);
		createEReference(fuzzyFunctionEClass, FUZZY_FUNCTION__INDIRECTIONS);
		createEAttribute(fuzzyFunctionEClass, FUZZY_FUNCTION__ZINTERNAL_CACHE_FUZZY_MAP);
		createEAttribute(fuzzyFunctionEClass, FUZZY_FUNCTION__ZINTERNAL_CACHE_DEP_RELATION);

		fuzzyVariableUseEClass = createEClass(FUZZY_VARIABLE_USE);
		createEAttribute(fuzzyVariableUseEClass, FUZZY_VARIABLE_USE__FUZZY_INDEX);
		createEReference(fuzzyVariableUseEClass, FUZZY_VARIABLE_USE__FUZZY_VARIABLE);

		nestedFuzzyFunctionEClass = createEClass(NESTED_FUZZY_FUNCTION);

		affineFuzzyVariableUseEClass = createEClass(AFFINE_FUZZY_VARIABLE_USE);
		createEReference(affineFuzzyVariableUseEClass, AFFINE_FUZZY_VARIABLE_USE__USE_FUNCTION);

		fuzzyFunctionInArrayNotationEClass = createEClass(FUZZY_FUNCTION_IN_ARRAY_NOTATION);
		createEAttribute(fuzzyFunctionInArrayNotationEClass, FUZZY_FUNCTION_IN_ARRAY_NOTATION__ARRAY_NOTATION);

		unaryCalculatorExpressionEClass = createEClass(UNARY_CALCULATOR_EXPRESSION);
		createEAttribute(unaryCalculatorExpressionEClass, UNARY_CALCULATOR_EXPRESSION__OPERATOR);
		createEReference(unaryCalculatorExpressionEClass, UNARY_CALCULATOR_EXPRESSION__EXPR);
		createEAttribute(unaryCalculatorExpressionEClass, UNARY_CALCULATOR_EXPRESSION__ZINTERNAL_CACHE_ISL_OBJECT);

		binaryCalculatorExpressionEClass = createEClass(BINARY_CALCULATOR_EXPRESSION);
		createEAttribute(binaryCalculatorExpressionEClass, BINARY_CALCULATOR_EXPRESSION__OPERATOR);
		createEReference(binaryCalculatorExpressionEClass, BINARY_CALCULATOR_EXPRESSION__LEFT);
		createEReference(binaryCalculatorExpressionEClass, BINARY_CALCULATOR_EXPRESSION__RIGHT);
		createEAttribute(binaryCalculatorExpressionEClass, BINARY_CALCULATOR_EXPRESSION__ZINTERNAL_CACHE_ISL_OBJECT);

		variableDomainEClass = createEClass(VARIABLE_DOMAIN);
		createEReference(variableDomainEClass, VARIABLE_DOMAIN__VARIABLE);

		rectangularDomainEClass = createEClass(RECTANGULAR_DOMAIN);
		createEAttribute(rectangularDomainEClass, RECTANGULAR_DOMAIN__LOWER_BOUNDS);
		createEAttribute(rectangularDomainEClass, RECTANGULAR_DOMAIN__UPPER_BOUNDS);
		createEAttribute(rectangularDomainEClass, RECTANGULAR_DOMAIN__INDEX_NAMES);
		createEAttribute(rectangularDomainEClass, RECTANGULAR_DOMAIN__ZINTERNAL_CACHE_ISL_SET);

		definedObjectEClass = createEClass(DEFINED_OBJECT);
		createEReference(definedObjectEClass, DEFINED_OBJECT__OBJECT);
		createEAttribute(definedObjectEClass, DEFINED_OBJECT__ZINTERNAL_CYCLE_DETECTOR);

		alphaFunctionEClass = createEClass(ALPHA_FUNCTION);
		createEAttribute(alphaFunctionEClass, ALPHA_FUNCTION__INDEX_LIST);
		createEReference(alphaFunctionEClass, ALPHA_FUNCTION__EXPRS);

		alphaFunctionExpressionEClass = createEClass(ALPHA_FUNCTION_EXPRESSION);

		alphaFunctionBinaryExpressionEClass = createEClass(ALPHA_FUNCTION_BINARY_EXPRESSION);
		createEReference(alphaFunctionBinaryExpressionEClass, ALPHA_FUNCTION_BINARY_EXPRESSION__LEFT);
		createEReference(alphaFunctionBinaryExpressionEClass, ALPHA_FUNCTION_BINARY_EXPRESSION__RIGHT);
		createEAttribute(alphaFunctionBinaryExpressionEClass, ALPHA_FUNCTION_BINARY_EXPRESSION__OPERATOR);

		alphaFunctionLiteralEClass = createEClass(ALPHA_FUNCTION_LITERAL);
		createEAttribute(alphaFunctionLiteralEClass, ALPHA_FUNCTION_LITERAL__VALUE);

		alphaFunctionFloorEClass = createEClass(ALPHA_FUNCTION_FLOOR);
		createEReference(alphaFunctionFloorEClass, ALPHA_FUNCTION_FLOOR__EXPR);

		// Create enums
		unarY_OPEEnum = createEEnum(UNARY_OP);
		binarY_OPEEnum = createEEnum(BINARY_OP);
		reductioN_OPEEnum = createEEnum(REDUCTION_OP);
		polY_OBJECT_TYPEEEnum = createEEnum(POLY_OBJECT_TYPE);
		calculatoR_UNARY_OPEEnum = createEEnum(CALCULATOR_UNARY_OP);
		calculatoR_BINARY_OPEEnum = createEEnum(CALCULATOR_BINARY_OP);

		// Create data types
		jniObjectEDataType = createEDataType(JNI_OBJECT);
		jniislSetEDataType = createEDataType(JNIISL_SET);
		jniislMapEDataType = createEDataType(JNIISL_MAP);
		jniislMultiAffEDataType = createEDataType(JNIISL_MULTI_AFF);
		jniislpwqPolynomialEDataType = createEDataType(JNIISLPWQ_POLYNOMIAL);
		isL_FORMATEDataType = createEDataType(ISL_FORMAT);
		listVariableExpressionEDataType = createEDataType(LIST_VARIABLE_EXPRESSION);
		integerQueueEDataType = createEDataType(INTEGER_QUEUE);
		stringEDataType = createEDataType(STRING);
		intEDataType = createEDataType(INT);
		floatEDataType = createEDataType(FLOAT);
		doubleEDataType = createEDataType(DOUBLE);
		booleanEDataType = createEDataType(BOOLEAN);
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

		// Create type parameters

		// Set bounds for type parameters

		// Add supertypes to classes
		alphaVisitableEClass.getESuperTypes().add(this.getAlphaCompleteVisitable());
		alphaExpressionVisitableEClass.getESuperTypes().add(this.getAlphaCompleteVisitable());
		alphaRootEClass.getESuperTypes().add(this.getAlphaNode());
		alphaRootEClass.getESuperTypes().add(this.getAlphaVisitable());
		alphaElementEClass.getESuperTypes().add(this.getAlphaNode());
		alphaElementEClass.getESuperTypes().add(this.getAlphaVisitable());
		importsEClass.getESuperTypes().add(this.getAlphaNode());
		importsEClass.getESuperTypes().add(this.getAlphaVisitable());
		alphaPackageEClass.getESuperTypes().add(this.getAlphaElement());
		alphaConstantEClass.getESuperTypes().add(this.getAlphaElement());
		externalFunctionEClass.getESuperTypes().add(this.getAlphaElement());
		alphaSystemEClass.getESuperTypes().add(this.getAlphaElement());
		variableEClass.getESuperTypes().add(this.getAlphaNode());
		variableEClass.getESuperTypes().add(this.getAlphaVisitable());
		variableEClass.getESuperTypes().add(this.getAlphaSystemElement());
		fuzzyVariableEClass.getESuperTypes().add(this.getVariable());
		systemBodyEClass.getESuperTypes().add(this.getAlphaNode());
		systemBodyEClass.getESuperTypes().add(this.getAlphaVisitable());
		systemBodyEClass.getESuperTypes().add(this.getAlphaSystemElement());
		equationEClass.getESuperTypes().add(this.getAlphaNode());
		equationEClass.getESuperTypes().add(this.getAlphaVisitable());
		equationEClass.getESuperTypes().add(this.getAlphaSystemElement());
		equationEClass.getESuperTypes().add(this.getAlphaScheduleTarget());
		standardEquationEClass.getESuperTypes().add(this.getEquation());
		useEquationEClass.getESuperTypes().add(this.getEquation());
		alphaExpressionEClass.getESuperTypes().add(this.getAlphaNode());
		alphaExpressionEClass.getESuperTypes().add(this.getAlphaExpressionVisitable());
		restrictExpressionEClass.getESuperTypes().add(this.getAlphaExpression());
		autoRestrictExpressionEClass.getESuperTypes().add(this.getAlphaExpression());
		caseExpressionEClass.getESuperTypes().add(this.getAlphaExpression());
		dependenceExpressionEClass.getESuperTypes().add(this.getAlphaExpression());
		fuzzyDependenceExpressionEClass.getESuperTypes().add(this.getAlphaExpression());
		ifExpressionEClass.getESuperTypes().add(this.getAlphaExpression());
		indexExpressionEClass.getESuperTypes().add(this.getAlphaExpression());
		polynomialIndexExpressionEClass.getESuperTypes().add(this.getAlphaExpression());
		fuzzyIndexExpressionEClass.getESuperTypes().add(this.getAlphaExpression());
		abstractReduceExpressionEClass.getESuperTypes().add(this.getAlphaExpression());
		reduceExpressionEClass.getESuperTypes().add(this.getAbstractReduceExpression());
		externalReduceExpressionEClass.getESuperTypes().add(this.getReduceExpression());
		argReduceExpressionEClass.getESuperTypes().add(this.getAbstractReduceExpression());
		externalArgReduceExpressionEClass.getESuperTypes().add(this.getArgReduceExpression());
		convolutionExpressionEClass.getESuperTypes().add(this.getAlphaExpression());
		selectExpressionEClass.getESuperTypes().add(this.getAlphaExpression());
		variableExpressionEClass.getESuperTypes().add(this.getAlphaExpression());
		constantExpressionEClass.getESuperTypes().add(this.getAlphaExpression());
		integerExpressionEClass.getESuperTypes().add(this.getConstantExpression());
		realExpressionEClass.getESuperTypes().add(this.getConstantExpression());
		booleanExpressionEClass.getESuperTypes().add(this.getConstantExpression());
		unaryExpressionEClass.getESuperTypes().add(this.getAlphaExpression());
		binaryExpressionEClass.getESuperTypes().add(this.getAlphaExpression());
		multiArgExpressionEClass.getESuperTypes().add(this.getAlphaExpression());
		externalMultiArgExpressionEClass.getESuperTypes().add(this.getMultiArgExpression());
		abstractFuzzyReduceExpressionEClass.getESuperTypes().add(this.getAlphaExpression());
		fuzzyReduceExpressionEClass.getESuperTypes().add(this.getAbstractFuzzyReduceExpression());
		externalFuzzyReduceExpressionEClass.getESuperTypes().add(this.getFuzzyReduceExpression());
		fuzzyArgReduceExpressionEClass.getESuperTypes().add(this.getAbstractFuzzyReduceExpression());
		externalFuzzyArgReduceExpressionEClass.getESuperTypes().add(this.getFuzzyArgReduceExpression());
		polyhedralObjectEClass.getESuperTypes().add(this.getAlphaNode());
		polyhedralObjectEClass.getESuperTypes().add(this.getCalculatorNode());
		polyhedralObjectEClass.getESuperTypes().add(this.getAlphaVisitable());
		polyhedralObjectEClass.getESuperTypes().add(this.getAlphaSystemElement());
		calculatorExpressionEClass.getESuperTypes().add(this.getAlphaNode());
		calculatorExpressionEClass.getESuperTypes().add(this.getCalculatorNode());
		calculatorExpressionEClass.getESuperTypes().add(this.getCalculatorExpressionVisitable());
		jniDomainEClass.getESuperTypes().add(this.getCalculatorExpression());
		jniDomainInArrayNotationEClass.getESuperTypes().add(this.getJNIDomain());
		jniRelationEClass.getESuperTypes().add(this.getCalculatorExpression());
		jniFunctionEClass.getESuperTypes().add(this.getCalculatorExpression());
		jniFunctionInArrayNotationEClass.getESuperTypes().add(this.getJNIFunction());
		jniPolynomialEClass.getESuperTypes().add(this.getCalculatorExpression());
		jniPolynomialInArrayNotationEClass.getESuperTypes().add(this.getJNIPolynomial());
		fuzzyFunctionEClass.getESuperTypes().add(this.getAlphaNode());
		fuzzyVariableUseEClass.getESuperTypes().add(this.getAlphaNode());
		nestedFuzzyFunctionEClass.getESuperTypes().add(this.getFuzzyFunction());
		nestedFuzzyFunctionEClass.getESuperTypes().add(this.getFuzzyVariableUse());
		affineFuzzyVariableUseEClass.getESuperTypes().add(this.getFuzzyVariableUse());
		fuzzyFunctionInArrayNotationEClass.getESuperTypes().add(this.getFuzzyFunction());
		unaryCalculatorExpressionEClass.getESuperTypes().add(this.getCalculatorExpression());
		binaryCalculatorExpressionEClass.getESuperTypes().add(this.getCalculatorExpression());
		variableDomainEClass.getESuperTypes().add(this.getCalculatorExpression());
		rectangularDomainEClass.getESuperTypes().add(this.getCalculatorExpression());
		definedObjectEClass.getESuperTypes().add(this.getCalculatorExpression());
		alphaFunctionBinaryExpressionEClass.getESuperTypes().add(this.getAlphaFunctionExpression());
		alphaFunctionLiteralEClass.getESuperTypes().add(this.getAlphaFunctionExpression());
		alphaFunctionFloorEClass.getESuperTypes().add(this.getAlphaFunctionExpression());

		// Initialize classes and features; add operations and parameters
		initEClass(alphaNodeEClass, AlphaNode.class, "AlphaNode", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getAlphaNode_NodeID(), this.getint(), "nodeID", null, 0, -1, AlphaNode.class, IS_TRANSIENT, IS_VOLATILE, !IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, IS_DERIVED, IS_ORDERED);

		EOperation op = addEOperation(alphaNodeEClass, this.getAlphaNode(), "getNode", 0, 1, !IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getIntegerQueue(), "nodeID", 0, 1, !IS_UNIQUE, IS_ORDERED);

		initEClass(alphaCompleteVisitableEClass, AlphaCompleteVisitable.class, "AlphaCompleteVisitable", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(alphaVisitableEClass, AlphaVisitable.class, "AlphaVisitable", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		op = addEOperation(alphaVisitableEClass, null, "accept", 0, 1, !IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getAlphaVisitor(), "visitor", 0, 1, !IS_UNIQUE, IS_ORDERED);

		initEClass(alphaExpressionVisitableEClass, AlphaExpressionVisitable.class, "AlphaExpressionVisitable", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		op = addEOperation(alphaExpressionVisitableEClass, null, "accept", 0, 1, !IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getAlphaExpressionVisitor(), "visitor", 0, 1, !IS_UNIQUE, IS_ORDERED);

		initEClass(calculatorExpressionVisitableEClass, CalculatorExpressionVisitable.class, "CalculatorExpressionVisitable", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		op = addEOperation(calculatorExpressionVisitableEClass, null, "accept", 0, 1, !IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getCalculatorExpressionVisitor(), "visitor", 0, 1, !IS_UNIQUE, IS_ORDERED);

		initEClass(alphaSystemElementEClass, AlphaSystemElement.class, "AlphaSystemElement", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(alphaScheduleTargetEClass, AlphaScheduleTarget.class, "AlphaScheduleTarget", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		addEOperation(alphaScheduleTargetEClass, this.getString(), "getName", 0, 1, !IS_UNIQUE, IS_ORDERED);

		initEClass(alphaVisitorEClass, AlphaVisitor.class, "AlphaVisitor", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		op = addEOperation(alphaVisitorEClass, null, "visitAlphaRoot", 0, 1, !IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getAlphaRoot(), "root", 0, 1, !IS_UNIQUE, IS_ORDERED);

		op = addEOperation(alphaVisitorEClass, null, "visitAlphaPackage", 0, 1, !IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getAlphaPackage(), "ap", 0, 1, !IS_UNIQUE, IS_ORDERED);

		op = addEOperation(alphaVisitorEClass, null, "visitAlphaSystem", 0, 1, !IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getAlphaSystem(), "system", 0, 1, !IS_UNIQUE, IS_ORDERED);

		op = addEOperation(alphaVisitorEClass, null, "visitSystemBody", 0, 1, !IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getSystemBody(), "sysBody", 0, 1, !IS_UNIQUE, IS_ORDERED);

		op = addEOperation(alphaVisitorEClass, null, "visitImports", 0, 1, !IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getImports(), "imports", 0, 1, !IS_UNIQUE, IS_ORDERED);

		op = addEOperation(alphaVisitorEClass, null, "visitAlphaConstant", 0, 1, !IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getAlphaConstant(), "ac", 0, 1, !IS_UNIQUE, IS_ORDERED);

		op = addEOperation(alphaVisitorEClass, null, "visitExternalFunction", 0, 1, !IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getExternalFunction(), "ef", 0, 1, !IS_UNIQUE, IS_ORDERED);

		op = addEOperation(alphaVisitorEClass, null, "visitVariable", 0, 1, !IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getVariable(), "variable", 0, 1, !IS_UNIQUE, IS_ORDERED);

		op = addEOperation(alphaVisitorEClass, null, "visitPolyhedralObject", 0, 1, !IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getPolyhedralObject(), "pobj", 0, 1, !IS_UNIQUE, IS_ORDERED);

		op = addEOperation(alphaVisitorEClass, null, "visitUseEquation", 0, 1, !IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getUseEquation(), "ue", 0, 1, !IS_UNIQUE, IS_ORDERED);

		op = addEOperation(alphaVisitorEClass, null, "visitStandardEquation", 0, 1, !IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getStandardEquation(), "se", 0, 1, !IS_UNIQUE, IS_ORDERED);

		op = addEOperation(alphaVisitorEClass, null, "inAlphaRoot", 0, 1, !IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getAlphaRoot(), "root", 0, 1, !IS_UNIQUE, IS_ORDERED);

		op = addEOperation(alphaVisitorEClass, null, "inAlphaElement", 0, 1, !IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getAlphaElement(), "ap", 0, 1, !IS_UNIQUE, IS_ORDERED);

		op = addEOperation(alphaVisitorEClass, null, "inAlphaPackage", 0, 1, !IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getAlphaPackage(), "ap", 0, 1, !IS_UNIQUE, IS_ORDERED);

		op = addEOperation(alphaVisitorEClass, null, "inAlphaSystem", 0, 1, !IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getAlphaSystem(), "system", 0, 1, !IS_UNIQUE, IS_ORDERED);

		op = addEOperation(alphaVisitorEClass, null, "inSystemBody", 0, 1, !IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getSystemBody(), "sysBody", 0, 1, !IS_UNIQUE, IS_ORDERED);

		op = addEOperation(alphaVisitorEClass, null, "inImports", 0, 1, !IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getImports(), "imports", 0, 1, !IS_UNIQUE, IS_ORDERED);

		op = addEOperation(alphaVisitorEClass, null, "inAlphaConstant", 0, 1, !IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getAlphaConstant(), "ac", 0, 1, !IS_UNIQUE, IS_ORDERED);

		op = addEOperation(alphaVisitorEClass, null, "inExternalFunction", 0, 1, !IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getExternalFunction(), "ef", 0, 1, !IS_UNIQUE, IS_ORDERED);

		op = addEOperation(alphaVisitorEClass, null, "inVariable", 0, 1, !IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getVariable(), "variable", 0, 1, !IS_UNIQUE, IS_ORDERED);

		op = addEOperation(alphaVisitorEClass, null, "inFuzzyVariable", 0, 1, !IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getFuzzyVariable(), "variable", 0, 1, !IS_UNIQUE, IS_ORDERED);

		op = addEOperation(alphaVisitorEClass, null, "inPolyhedralObject", 0, 1, !IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getPolyhedralObject(), "pobj", 0, 1, !IS_UNIQUE, IS_ORDERED);

		op = addEOperation(alphaVisitorEClass, null, "inEquation", 0, 1, !IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getEquation(), "eq", 0, 1, !IS_UNIQUE, IS_ORDERED);

		op = addEOperation(alphaVisitorEClass, null, "inUseEquation", 0, 1, !IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getUseEquation(), "ue", 0, 1, !IS_UNIQUE, IS_ORDERED);

		op = addEOperation(alphaVisitorEClass, null, "inStandardEquation", 0, 1, !IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getStandardEquation(), "se", 0, 1, !IS_UNIQUE, IS_ORDERED);

		op = addEOperation(alphaVisitorEClass, null, "outAlphaRoot", 0, 1, !IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getAlphaRoot(), "root", 0, 1, !IS_UNIQUE, IS_ORDERED);

		op = addEOperation(alphaVisitorEClass, null, "outAlphaElement", 0, 1, !IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getAlphaElement(), "ap", 0, 1, !IS_UNIQUE, IS_ORDERED);

		op = addEOperation(alphaVisitorEClass, null, "outAlphaPackage", 0, 1, !IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getAlphaPackage(), "ap", 0, 1, !IS_UNIQUE, IS_ORDERED);

		op = addEOperation(alphaVisitorEClass, null, "outAlphaSystem", 0, 1, !IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getAlphaSystem(), "system", 0, 1, !IS_UNIQUE, IS_ORDERED);

		op = addEOperation(alphaVisitorEClass, null, "outSystemBody", 0, 1, !IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getSystemBody(), "sysBody", 0, 1, !IS_UNIQUE, IS_ORDERED);

		op = addEOperation(alphaVisitorEClass, null, "outImports", 0, 1, !IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getImports(), "imports", 0, 1, !IS_UNIQUE, IS_ORDERED);

		op = addEOperation(alphaVisitorEClass, null, "outAlphaConstant", 0, 1, !IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getAlphaConstant(), "ac", 0, 1, !IS_UNIQUE, IS_ORDERED);

		op = addEOperation(alphaVisitorEClass, null, "outExternalFunction", 0, 1, !IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getExternalFunction(), "ef", 0, 1, !IS_UNIQUE, IS_ORDERED);

		op = addEOperation(alphaVisitorEClass, null, "outVariable", 0, 1, !IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getVariable(), "variable", 0, 1, !IS_UNIQUE, IS_ORDERED);

		op = addEOperation(alphaVisitorEClass, null, "outFuzzyVariable", 0, 1, !IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getFuzzyVariable(), "variable", 0, 1, !IS_UNIQUE, IS_ORDERED);

		op = addEOperation(alphaVisitorEClass, null, "outPolyhedralObject", 0, 1, !IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getPolyhedralObject(), "pobj", 0, 1, !IS_UNIQUE, IS_ORDERED);

		op = addEOperation(alphaVisitorEClass, null, "outEquation", 0, 1, !IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getEquation(), "eq", 0, 1, !IS_UNIQUE, IS_ORDERED);

		op = addEOperation(alphaVisitorEClass, null, "outUseEquation", 0, 1, !IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getUseEquation(), "ue", 0, 1, !IS_UNIQUE, IS_ORDERED);

		op = addEOperation(alphaVisitorEClass, null, "outStandardEquation", 0, 1, !IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getStandardEquation(), "se", 0, 1, !IS_UNIQUE, IS_ORDERED);

		initEClass(alphaExpressionVisitorEClass, AlphaExpressionVisitor.class, "AlphaExpressionVisitor", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		op = addEOperation(alphaExpressionVisitorEClass, null, "visitAlphaExpression", 0, 1, !IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getAlphaExpression(), "ae", 0, 1, !IS_UNIQUE, IS_ORDERED);

		op = addEOperation(alphaExpressionVisitorEClass, null, "visitRestrictExpression", 0, 1, !IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getRestrictExpression(), "re", 0, 1, !IS_UNIQUE, IS_ORDERED);

		op = addEOperation(alphaExpressionVisitorEClass, null, "visitAutoRestrictExpression", 0, 1, !IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getAutoRestrictExpression(), "are", 0, 1, !IS_UNIQUE, IS_ORDERED);

		op = addEOperation(alphaExpressionVisitorEClass, null, "visitCaseExpression", 0, 1, !IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getCaseExpression(), "ce", 0, 1, !IS_UNIQUE, IS_ORDERED);

		op = addEOperation(alphaExpressionVisitorEClass, null, "visitIfExpression", 0, 1, !IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getIfExpression(), "ie", 0, 1, !IS_UNIQUE, IS_ORDERED);

		op = addEOperation(alphaExpressionVisitorEClass, null, "visitDependenceExpression", 0, 1, !IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getDependenceExpression(), "de", 0, 1, !IS_UNIQUE, IS_ORDERED);

		op = addEOperation(alphaExpressionVisitorEClass, null, "visitFuzzyDependenceExpression", 0, 1, !IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getFuzzyDependenceExpression(), "fde", 0, 1, !IS_UNIQUE, IS_ORDERED);

		op = addEOperation(alphaExpressionVisitorEClass, null, "visitAbstractReduceExpression", 0, 1, !IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getAbstractReduceExpression(), "are", 0, 1, !IS_UNIQUE, IS_ORDERED);

		op = addEOperation(alphaExpressionVisitorEClass, null, "visitAbstractFuzzyReduceExpression", 0, 1, !IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getAbstractFuzzyReduceExpression(), "afre", 0, 1, !IS_UNIQUE, IS_ORDERED);

		op = addEOperation(alphaExpressionVisitorEClass, null, "visitConvolutionExpression", 0, 1, !IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getConvolutionExpression(), "ce", 0, 1, !IS_UNIQUE, IS_ORDERED);

		op = addEOperation(alphaExpressionVisitorEClass, null, "visitUnaryExpression", 0, 1, !IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getUnaryExpression(), "ue", 0, 1, !IS_UNIQUE, IS_ORDERED);

		op = addEOperation(alphaExpressionVisitorEClass, null, "visitBinaryExpression", 0, 1, !IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getBinaryExpression(), "be", 0, 1, !IS_UNIQUE, IS_ORDERED);

		op = addEOperation(alphaExpressionVisitorEClass, null, "visitMultiArgExpression", 0, 1, !IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getMultiArgExpression(), "mae", 0, 1, !IS_UNIQUE, IS_ORDERED);

		op = addEOperation(alphaExpressionVisitorEClass, null, "visitSelectExpression", 0, 1, !IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getSelectExpression(), "se", 0, 1, !IS_UNIQUE, IS_ORDERED);

		op = addEOperation(alphaExpressionVisitorEClass, null, "visitIndexExpression", 0, 1, !IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getIndexExpression(), "ie", 0, 1, !IS_UNIQUE, IS_ORDERED);

		op = addEOperation(alphaExpressionVisitorEClass, null, "visitPolynomialIndexExpression", 0, 1, !IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getPolynomialIndexExpression(), "pie", 0, 1, !IS_UNIQUE, IS_ORDERED);

		op = addEOperation(alphaExpressionVisitorEClass, null, "visitFuzzyIndexExpression", 0, 1, !IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getFuzzyIndexExpression(), "fie", 0, 1, !IS_UNIQUE, IS_ORDERED);

		op = addEOperation(alphaExpressionVisitorEClass, null, "visitVariableExpression", 0, 1, !IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getVariableExpression(), "ve", 0, 1, !IS_UNIQUE, IS_ORDERED);

		op = addEOperation(alphaExpressionVisitorEClass, null, "visitIntegerExpression", 0, 1, !IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getIntegerExpression(), "ie", 0, 1, !IS_UNIQUE, IS_ORDERED);

		op = addEOperation(alphaExpressionVisitorEClass, null, "visitRealExpression", 0, 1, !IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getRealExpression(), "re", 0, 1, !IS_UNIQUE, IS_ORDERED);

		op = addEOperation(alphaExpressionVisitorEClass, null, "visitBooleanExpression", 0, 1, !IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getBooleanExpression(), "be", 0, 1, !IS_UNIQUE, IS_ORDERED);

		op = addEOperation(alphaExpressionVisitorEClass, null, "inAlphaExpression", 0, 1, !IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getAlphaExpression(), "ae", 0, 1, !IS_UNIQUE, IS_ORDERED);

		op = addEOperation(alphaExpressionVisitorEClass, null, "inRestrictExpression", 0, 1, !IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getRestrictExpression(), "re", 0, 1, !IS_UNIQUE, IS_ORDERED);

		op = addEOperation(alphaExpressionVisitorEClass, null, "inAutoRestrictExpression", 0, 1, !IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getAutoRestrictExpression(), "are", 0, 1, !IS_UNIQUE, IS_ORDERED);

		op = addEOperation(alphaExpressionVisitorEClass, null, "inCaseExpression", 0, 1, !IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getCaseExpression(), "ce", 0, 1, !IS_UNIQUE, IS_ORDERED);

		op = addEOperation(alphaExpressionVisitorEClass, null, "inIfExpression", 0, 1, !IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getIfExpression(), "ie", 0, 1, !IS_UNIQUE, IS_ORDERED);

		op = addEOperation(alphaExpressionVisitorEClass, null, "inDependenceExpression", 0, 1, !IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getDependenceExpression(), "de", 0, 1, !IS_UNIQUE, IS_ORDERED);

		op = addEOperation(alphaExpressionVisitorEClass, null, "inFuzzyDependenceExpression", 0, 1, !IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getFuzzyDependenceExpression(), "fde", 0, 1, !IS_UNIQUE, IS_ORDERED);

		op = addEOperation(alphaExpressionVisitorEClass, null, "inAbstractReduceExpression", 0, 1, !IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getAbstractReduceExpression(), "are", 0, 1, !IS_UNIQUE, IS_ORDERED);

		op = addEOperation(alphaExpressionVisitorEClass, null, "inReduceExpression", 0, 1, !IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getReduceExpression(), "re", 0, 1, !IS_UNIQUE, IS_ORDERED);

		op = addEOperation(alphaExpressionVisitorEClass, null, "inArgReduceExpression", 0, 1, !IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getArgReduceExpression(), "are", 0, 1, !IS_UNIQUE, IS_ORDERED);

		op = addEOperation(alphaExpressionVisitorEClass, null, "inAbstractFuzzyReduceExpression", 0, 1, !IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getAbstractFuzzyReduceExpression(), "afre", 0, 1, !IS_UNIQUE, IS_ORDERED);

		op = addEOperation(alphaExpressionVisitorEClass, null, "inFuzzyReduceExpression", 0, 1, !IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getFuzzyReduceExpression(), "fre", 0, 1, !IS_UNIQUE, IS_ORDERED);

		op = addEOperation(alphaExpressionVisitorEClass, null, "inFuzzyArgReduceExpression", 0, 1, !IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getFuzzyArgReduceExpression(), "fare", 0, 1, !IS_UNIQUE, IS_ORDERED);

		op = addEOperation(alphaExpressionVisitorEClass, null, "inConvolutionExpression", 0, 1, !IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getConvolutionExpression(), "ce", 0, 1, !IS_UNIQUE, IS_ORDERED);

		op = addEOperation(alphaExpressionVisitorEClass, null, "inUnaryExpression", 0, 1, !IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getUnaryExpression(), "ue", 0, 1, !IS_UNIQUE, IS_ORDERED);

		op = addEOperation(alphaExpressionVisitorEClass, null, "inBinaryExpression", 0, 1, !IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getBinaryExpression(), "be", 0, 1, !IS_UNIQUE, IS_ORDERED);

		op = addEOperation(alphaExpressionVisitorEClass, null, "inMultiArgExpression", 0, 1, !IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getMultiArgExpression(), "mae", 0, 1, !IS_UNIQUE, IS_ORDERED);

		op = addEOperation(alphaExpressionVisitorEClass, null, "inSelectExpression", 0, 1, !IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getSelectExpression(), "se", 0, 1, !IS_UNIQUE, IS_ORDERED);

		op = addEOperation(alphaExpressionVisitorEClass, null, "inIndexExpression", 0, 1, !IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getIndexExpression(), "ie", 0, 1, !IS_UNIQUE, IS_ORDERED);

		op = addEOperation(alphaExpressionVisitorEClass, null, "inPolynomialIndexExpression", 0, 1, !IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getPolynomialIndexExpression(), "pie", 0, 1, !IS_UNIQUE, IS_ORDERED);

		op = addEOperation(alphaExpressionVisitorEClass, null, "inFuzzyIndexExpression", 0, 1, !IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getFuzzyIndexExpression(), "fie", 0, 1, !IS_UNIQUE, IS_ORDERED);

		op = addEOperation(alphaExpressionVisitorEClass, null, "inVariableExpression", 0, 1, !IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getVariableExpression(), "ve", 0, 1, !IS_UNIQUE, IS_ORDERED);

		op = addEOperation(alphaExpressionVisitorEClass, null, "inConstantExpression", 0, 1, !IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getConstantExpression(), "ce", 0, 1, !IS_UNIQUE, IS_ORDERED);

		op = addEOperation(alphaExpressionVisitorEClass, null, "inIntegerExpression", 0, 1, !IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getIntegerExpression(), "ie", 0, 1, !IS_UNIQUE, IS_ORDERED);

		op = addEOperation(alphaExpressionVisitorEClass, null, "inRealExpression", 0, 1, !IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getRealExpression(), "re", 0, 1, !IS_UNIQUE, IS_ORDERED);

		op = addEOperation(alphaExpressionVisitorEClass, null, "inBooleanExpression", 0, 1, !IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getBooleanExpression(), "be", 0, 1, !IS_UNIQUE, IS_ORDERED);

		op = addEOperation(alphaExpressionVisitorEClass, null, "inExternalReduceExpression", 0, 1, !IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getExternalReduceExpression(), "ere", 0, 1, !IS_UNIQUE, IS_ORDERED);

		op = addEOperation(alphaExpressionVisitorEClass, null, "inExternalArgReduceExpression", 0, 1, !IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getExternalArgReduceExpression(), "eare", 0, 1, !IS_UNIQUE, IS_ORDERED);

		op = addEOperation(alphaExpressionVisitorEClass, null, "inExternalMultiArgExpression", 0, 1, !IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getExternalMultiArgExpression(), "emae", 0, 1, !IS_UNIQUE, IS_ORDERED);

		op = addEOperation(alphaExpressionVisitorEClass, null, "inExternalFuzzyReduceExpression", 0, 1, !IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getExternalFuzzyReduceExpression(), "efre", 0, 1, !IS_UNIQUE, IS_ORDERED);

		op = addEOperation(alphaExpressionVisitorEClass, null, "inExternalFuzzyArgReduceExpression", 0, 1, !IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getExternalFuzzyArgReduceExpression(), "efare", 0, 1, !IS_UNIQUE, IS_ORDERED);

		op = addEOperation(alphaExpressionVisitorEClass, null, "outAlphaExpression", 0, 1, !IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getAlphaExpression(), "ae", 0, 1, !IS_UNIQUE, IS_ORDERED);

		op = addEOperation(alphaExpressionVisitorEClass, null, "outRestrictExpression", 0, 1, !IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getRestrictExpression(), "re", 0, 1, !IS_UNIQUE, IS_ORDERED);

		op = addEOperation(alphaExpressionVisitorEClass, null, "outAutoRestrictExpression", 0, 1, !IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getAutoRestrictExpression(), "are", 0, 1, !IS_UNIQUE, IS_ORDERED);

		op = addEOperation(alphaExpressionVisitorEClass, null, "outCaseExpression", 0, 1, !IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getCaseExpression(), "ce", 0, 1, !IS_UNIQUE, IS_ORDERED);

		op = addEOperation(alphaExpressionVisitorEClass, null, "outIfExpression", 0, 1, !IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getIfExpression(), "ie", 0, 1, !IS_UNIQUE, IS_ORDERED);

		op = addEOperation(alphaExpressionVisitorEClass, null, "outDependenceExpression", 0, 1, !IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getDependenceExpression(), "de", 0, 1, !IS_UNIQUE, IS_ORDERED);

		op = addEOperation(alphaExpressionVisitorEClass, null, "outFuzzyDependenceExpression", 0, 1, !IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getFuzzyDependenceExpression(), "fde", 0, 1, !IS_UNIQUE, IS_ORDERED);

		op = addEOperation(alphaExpressionVisitorEClass, null, "outAbstractReduceExpression", 0, 1, !IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getAbstractReduceExpression(), "are", 0, 1, !IS_UNIQUE, IS_ORDERED);

		op = addEOperation(alphaExpressionVisitorEClass, null, "outReduceExpression", 0, 1, !IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getReduceExpression(), "re", 0, 1, !IS_UNIQUE, IS_ORDERED);

		op = addEOperation(alphaExpressionVisitorEClass, null, "outArgReduceExpression", 0, 1, !IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getArgReduceExpression(), "are", 0, 1, !IS_UNIQUE, IS_ORDERED);

		op = addEOperation(alphaExpressionVisitorEClass, null, "outAbstractFuzzyReduceExpression", 0, 1, !IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getAbstractFuzzyReduceExpression(), "afre", 0, 1, !IS_UNIQUE, IS_ORDERED);

		op = addEOperation(alphaExpressionVisitorEClass, null, "outFuzzyReduceExpression", 0, 1, !IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getFuzzyReduceExpression(), "fre", 0, 1, !IS_UNIQUE, IS_ORDERED);

		op = addEOperation(alphaExpressionVisitorEClass, null, "outFuzzyArgReduceExpression", 0, 1, !IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getFuzzyArgReduceExpression(), "fare", 0, 1, !IS_UNIQUE, IS_ORDERED);

		op = addEOperation(alphaExpressionVisitorEClass, null, "outConvolutionExpression", 0, 1, !IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getConvolutionExpression(), "ce", 0, 1, !IS_UNIQUE, IS_ORDERED);

		op = addEOperation(alphaExpressionVisitorEClass, null, "outUnaryExpression", 0, 1, !IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getUnaryExpression(), "ue", 0, 1, !IS_UNIQUE, IS_ORDERED);

		op = addEOperation(alphaExpressionVisitorEClass, null, "outBinaryExpression", 0, 1, !IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getBinaryExpression(), "be", 0, 1, !IS_UNIQUE, IS_ORDERED);

		op = addEOperation(alphaExpressionVisitorEClass, null, "outMultiArgExpression", 0, 1, !IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getMultiArgExpression(), "mae", 0, 1, !IS_UNIQUE, IS_ORDERED);

		op = addEOperation(alphaExpressionVisitorEClass, null, "outSelectExpression", 0, 1, !IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getSelectExpression(), "se", 0, 1, !IS_UNIQUE, IS_ORDERED);

		op = addEOperation(alphaExpressionVisitorEClass, null, "outIndexExpression", 0, 1, !IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getIndexExpression(), "ie", 0, 1, !IS_UNIQUE, IS_ORDERED);

		op = addEOperation(alphaExpressionVisitorEClass, null, "outPolynomialIndexExpression", 0, 1, !IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getPolynomialIndexExpression(), "pie", 0, 1, !IS_UNIQUE, IS_ORDERED);

		op = addEOperation(alphaExpressionVisitorEClass, null, "outFuzzyIndexExpression", 0, 1, !IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getFuzzyIndexExpression(), "fie", 0, 1, !IS_UNIQUE, IS_ORDERED);

		op = addEOperation(alphaExpressionVisitorEClass, null, "outVariableExpression", 0, 1, !IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getVariableExpression(), "ve", 0, 1, !IS_UNIQUE, IS_ORDERED);

		op = addEOperation(alphaExpressionVisitorEClass, null, "outConstantExpression", 0, 1, !IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getConstantExpression(), "ce", 0, 1, !IS_UNIQUE, IS_ORDERED);

		op = addEOperation(alphaExpressionVisitorEClass, null, "outIntegerExpression", 0, 1, !IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getIntegerExpression(), "ie", 0, 1, !IS_UNIQUE, IS_ORDERED);

		op = addEOperation(alphaExpressionVisitorEClass, null, "outRealExpression", 0, 1, !IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getRealExpression(), "re", 0, 1, !IS_UNIQUE, IS_ORDERED);

		op = addEOperation(alphaExpressionVisitorEClass, null, "outBooleanExpression", 0, 1, !IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getBooleanExpression(), "be", 0, 1, !IS_UNIQUE, IS_ORDERED);

		op = addEOperation(alphaExpressionVisitorEClass, null, "outExternalReduceExpression", 0, 1, !IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getExternalReduceExpression(), "ere", 0, 1, !IS_UNIQUE, IS_ORDERED);

		op = addEOperation(alphaExpressionVisitorEClass, null, "outExternalArgReduceExpression", 0, 1, !IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getExternalArgReduceExpression(), "eare", 0, 1, !IS_UNIQUE, IS_ORDERED);

		op = addEOperation(alphaExpressionVisitorEClass, null, "outExternalMultiArgExpression", 0, 1, !IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getExternalMultiArgExpression(), "emae", 0, 1, !IS_UNIQUE, IS_ORDERED);

		op = addEOperation(alphaExpressionVisitorEClass, null, "outExternalFuzzyReduceExpression", 0, 1, !IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getExternalFuzzyReduceExpression(), "efre", 0, 1, !IS_UNIQUE, IS_ORDERED);

		op = addEOperation(alphaExpressionVisitorEClass, null, "outExternalFuzzyArgReduceExpression", 0, 1, !IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getExternalFuzzyArgReduceExpression(), "efare", 0, 1, !IS_UNIQUE, IS_ORDERED);

		initEClass(calculatorExpressionVisitorEClass, CalculatorExpressionVisitor.class, "CalculatorExpressionVisitor", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		op = addEOperation(calculatorExpressionVisitorEClass, null, "visitCalculatorExpression", 0, 1, !IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getCalculatorExpression(), "expr", 0, 1, !IS_UNIQUE, IS_ORDERED);

		op = addEOperation(calculatorExpressionVisitorEClass, null, "visitUnaryCalculatorExpression", 0, 1, !IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getUnaryCalculatorExpression(), "expr", 0, 1, !IS_UNIQUE, IS_ORDERED);

		op = addEOperation(calculatorExpressionVisitorEClass, null, "visitBinaryCalculatorExpression", 0, 1, !IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getBinaryCalculatorExpression(), "expr", 0, 1, !IS_UNIQUE, IS_ORDERED);

		op = addEOperation(calculatorExpressionVisitorEClass, null, "visitJNIDomain", 0, 1, !IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getJNIDomain(), "jniDomain", 0, 1, !IS_UNIQUE, IS_ORDERED);

		op = addEOperation(calculatorExpressionVisitorEClass, null, "visitJNIRelation", 0, 1, !IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getJNIRelation(), "jniRelation", 0, 1, !IS_UNIQUE, IS_ORDERED);

		op = addEOperation(calculatorExpressionVisitorEClass, null, "visitJNIFunction", 0, 1, !IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getJNIFunction(), "jniFunction", 0, 1, !IS_UNIQUE, IS_ORDERED);

		op = addEOperation(calculatorExpressionVisitorEClass, null, "visitJNIPolynomial", 0, 1, !IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getJNIPolynomial(), "jniPolynomial", 0, 1, !IS_UNIQUE, IS_ORDERED);

		op = addEOperation(calculatorExpressionVisitorEClass, null, "visitVariableDomain", 0, 1, !IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getVariableDomain(), "vdom", 0, 1, !IS_UNIQUE, IS_ORDERED);

		op = addEOperation(calculatorExpressionVisitorEClass, null, "visitRectangularDomain", 0, 1, !IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getRectangularDomain(), "rdom", 0, 1, !IS_UNIQUE, IS_ORDERED);

		op = addEOperation(calculatorExpressionVisitorEClass, null, "visitDefinedObject", 0, 1, !IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getDefinedObject(), "dobj", 0, 1, !IS_UNIQUE, IS_ORDERED);

		initEClass(alphaRootEClass, AlphaRoot.class, "AlphaRoot", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getAlphaRoot_Imports(), this.getImports(), null, "imports", null, 0, -1, AlphaRoot.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getAlphaRoot_Elements(), this.getAlphaElement(), null, "elements", null, 0, -1, AlphaRoot.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		addEOperation(alphaRootEClass, this.getAlphaConstant(), "getConstants", 0, -1, !IS_UNIQUE, IS_ORDERED);

		addEOperation(alphaRootEClass, this.getExternalFunction(), "getExternalFunctions", 0, -1, !IS_UNIQUE, IS_ORDERED);

		addEOperation(alphaRootEClass, this.getAlphaPackage(), "getPackages", 0, -1, !IS_UNIQUE, IS_ORDERED);

		addEOperation(alphaRootEClass, this.getAlphaSystem(), "getSystems", 0, -1, !IS_UNIQUE, IS_ORDERED);

		op = addEOperation(alphaRootEClass, this.getAlphaSystem(), "getSystem", 0, 1, !IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getString(), "name", 0, 1, !IS_UNIQUE, IS_ORDERED);

		op = addEOperation(alphaRootEClass, null, "accept", 0, 1, !IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getAlphaVisitor(), "visitor", 0, 1, !IS_UNIQUE, IS_ORDERED);

		op = addEOperation(alphaRootEClass, this.getAlphaNode(), "getNode", 0, 1, !IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getint(), "nodeID", 0, -1, !IS_UNIQUE, IS_ORDERED);

		op = addEOperation(alphaRootEClass, this.getAlphaNode(), "getNode", 0, 1, !IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getString(), "nodeID", 0, 1, !IS_UNIQUE, IS_ORDERED);

		initEClass(alphaElementEClass, AlphaElement.class, "AlphaElement", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(importsEClass, Imports.class, "Imports", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getImports_ImportedNamespace(), this.getString(), "importedNamespace", null, 0, 1, Imports.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		op = addEOperation(importsEClass, null, "accept", 0, 1, !IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getAlphaVisitor(), "visitor", 0, 1, !IS_UNIQUE, IS_ORDERED);

		initEClass(alphaPackageEClass, AlphaPackage.class, "AlphaPackage", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getAlphaPackage_Name(), this.getString(), "name", null, 0, 1, AlphaPackage.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getAlphaPackage_Elements(), this.getAlphaElement(), null, "elements", null, 0, -1, AlphaPackage.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		addEOperation(alphaPackageEClass, this.getAlphaConstant(), "getConstants", 0, -1, !IS_UNIQUE, IS_ORDERED);

		addEOperation(alphaPackageEClass, this.getExternalFunction(), "getExternalFunctions", 0, -1, !IS_UNIQUE, IS_ORDERED);

		addEOperation(alphaPackageEClass, this.getAlphaPackage(), "getPackages", 0, -1, !IS_UNIQUE, IS_ORDERED);

		addEOperation(alphaPackageEClass, this.getAlphaSystem(), "getSystems", 0, -1, !IS_UNIQUE, IS_ORDERED);

		op = addEOperation(alphaPackageEClass, null, "accept", 0, 1, !IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getAlphaVisitor(), "visitor", 0, 1, !IS_UNIQUE, IS_ORDERED);

		initEClass(alphaConstantEClass, AlphaConstant.class, "AlphaConstant", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getAlphaConstant_Name(), this.getString(), "name", null, 0, 1, AlphaConstant.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getAlphaConstant_Value(), this.getint(), "value", null, 0, 1, AlphaConstant.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		op = addEOperation(alphaConstantEClass, null, "accept", 0, 1, !IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getAlphaVisitor(), "visitor", 0, 1, !IS_UNIQUE, IS_ORDERED);

		initEClass(externalFunctionEClass, ExternalFunction.class, "ExternalFunction", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getExternalFunction_Name(), this.getString(), "name", null, 0, 1, ExternalFunction.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getExternalFunction_Cardinality(), this.getint(), "cardinality", null, 0, 1, ExternalFunction.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		addEOperation(externalFunctionEClass, this.getString(), "getFullyQualifiedName", 0, 1, !IS_UNIQUE, IS_ORDERED);

		op = addEOperation(externalFunctionEClass, null, "accept", 0, 1, !IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getAlphaVisitor(), "visitor", 0, 1, !IS_UNIQUE, IS_ORDERED);

		initEClass(alphaSystemEClass, AlphaSystem.class, "AlphaSystem", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getAlphaSystem_Name(), this.getString(), "name", null, 0, 1, AlphaSystem.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getAlphaSystem_ParameterDomainExpr(), this.getJNIDomain(), null, "parameterDomainExpr", null, 0, 1, AlphaSystem.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getAlphaSystem_DefinedObjects(), this.getPolyhedralObject(), null, "definedObjects", null, 0, -1, AlphaSystem.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getAlphaSystem_Inputs(), this.getVariable(), null, "inputs", null, 0, -1, AlphaSystem.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getAlphaSystem_Outputs(), this.getVariable(), null, "outputs", null, 0, -1, AlphaSystem.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getAlphaSystem_Locals(), this.getVariable(), null, "locals", null, 0, -1, AlphaSystem.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getAlphaSystem_WhileDomainExpr(), this.getCalculatorExpression(), null, "whileDomainExpr", null, 0, 1, AlphaSystem.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getAlphaSystem_TestExpression(), this.getAlphaExpression(), null, "testExpression", null, 0, 1, AlphaSystem.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getAlphaSystem_SystemBodies(), this.getSystemBody(), this.getSystemBody_System(), "systemBodies", null, 0, -1, AlphaSystem.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		addEOperation(alphaSystemEClass, this.getVariable(), "getVariables", 0, -1, !IS_UNIQUE, IS_ORDERED);

		addEOperation(alphaSystemEClass, this.getJNIISLSet(), "getParameterDomain", 0, 1, !IS_UNIQUE, IS_ORDERED);

		addEOperation(alphaSystemEClass, this.getJNIISLSet(), "getWhileDomain", 0, 1, !IS_UNIQUE, IS_ORDERED);

		op = addEOperation(alphaSystemEClass, this.getVariable(), "getVariable", 0, 1, !IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getString(), "varName", 0, 1, !IS_UNIQUE, IS_ORDERED);

		addEOperation(alphaSystemEClass, this.getString(), "getFullyQualifiedName", 0, 1, !IS_UNIQUE, IS_ORDERED);

		op = addEOperation(alphaSystemEClass, null, "accept", 0, 1, !IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getAlphaVisitor(), "visitor", 0, 1, !IS_UNIQUE, IS_ORDERED);

		initEClass(variableEClass, Variable.class, "Variable", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getVariable_Name(), this.getString(), "name", null, 0, 1, Variable.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getVariable_DomainExpr(), this.getCalculatorExpression(), null, "domainExpr", null, 0, 1, Variable.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		addEOperation(variableEClass, this.getJNIISLSet(), "getDomain", 0, 1, !IS_UNIQUE, IS_ORDERED);

		addEOperation(variableEClass, this.getboolean(), "isInput", 0, 1, !IS_UNIQUE, IS_ORDERED);

		addEOperation(variableEClass, this.getboolean(), "isOutput", 0, 1, !IS_UNIQUE, IS_ORDERED);

		addEOperation(variableEClass, this.getboolean(), "isLocal", 0, 1, !IS_UNIQUE, IS_ORDERED);

		op = addEOperation(variableEClass, null, "accept", 0, 1, !IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getAlphaVisitor(), "visitor", 0, 1, !IS_UNIQUE, IS_ORDERED);

		initEClass(fuzzyVariableEClass, FuzzyVariable.class, "FuzzyVariable", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getFuzzyVariable_RangeExpr(), this.getCalculatorExpression(), null, "rangeExpr", null, 0, 1, FuzzyVariable.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		addEOperation(fuzzyVariableEClass, this.getJNIISLSet(), "getRange", 0, 1, !IS_UNIQUE, IS_ORDERED);

		addEOperation(fuzzyVariableEClass, this.getJNIISLMap(), "getRelation", 0, 1, !IS_UNIQUE, IS_ORDERED);

		op = addEOperation(fuzzyVariableEClass, null, "accept", 0, 1, !IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getAlphaVisitor(), "visitor", 0, 1, !IS_UNIQUE, IS_ORDERED);

		initEClass(systemBodyEClass, SystemBody.class, "SystemBody", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getSystemBody_System(), this.getAlphaSystem(), this.getAlphaSystem_SystemBodies(), "system", null, 0, 1, SystemBody.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getSystemBody_ParameterDomainExpr(), this.getJNIDomain(), null, "parameterDomainExpr", null, 0, 1, SystemBody.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getSystemBody_Equations(), this.getEquation(), this.getEquation_SystemBody(), "equations", null, 0, -1, SystemBody.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		addEOperation(systemBodyEClass, this.getJNIISLSet(), "getParameterDomain", 0, 1, !IS_UNIQUE, IS_ORDERED);

		addEOperation(systemBodyEClass, this.getUseEquation(), "getUseEquations", 0, -1, !IS_UNIQUE, IS_ORDERED);

		addEOperation(systemBodyEClass, this.getStandardEquation(), "getStandardEquations", 0, -1, !IS_UNIQUE, IS_ORDERED);

		op = addEOperation(systemBodyEClass, this.getStandardEquation(), "getStandardEquation", 0, 1, !IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getString(), "eqName", 0, 1, !IS_UNIQUE, IS_ORDERED);

		op = addEOperation(systemBodyEClass, this.getStandardEquation(), "getStandardEquation", 0, 1, !IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getVariable(), "v", 0, 1, !IS_UNIQUE, IS_ORDERED);

		op = addEOperation(systemBodyEClass, null, "accept", 0, 1, !IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getAlphaVisitor(), "visitor", 0, 1, !IS_UNIQUE, IS_ORDERED);

		initEClass(equationEClass, Equation.class, "Equation", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getEquation_SystemBody(), this.getSystemBody(), this.getSystemBody_Equations(), "systemBody", null, 0, 1, Equation.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		op = addEOperation(equationEClass, this.getAlphaExpression(), "getExpression", 0, 1, !IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getIntegerQueue(), "exprID", 0, 1, !IS_UNIQUE, IS_ORDERED);

		op = addEOperation(equationEClass, this.getAlphaExpression(), "getExpression", 0, 1, !IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getint(), "exprID", 0, -1, !IS_UNIQUE, IS_ORDERED);

		op = addEOperation(equationEClass, this.getAlphaExpression(), "getExpression", 0, 1, !IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getString(), "exprIDstr", 0, 1, !IS_UNIQUE, IS_ORDERED);

		initEClass(standardEquationEClass, StandardEquation.class, "StandardEquation", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getStandardEquation_Variable(), this.getVariable(), null, "variable", null, 0, 1, StandardEquation.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getStandardEquation_IndexNames(), this.getString(), "indexNames", null, 0, -1, StandardEquation.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getStandardEquation_Expr(), this.getAlphaExpression(), null, "expr", null, 0, 1, StandardEquation.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		addEOperation(standardEquationEClass, this.getString(), "getName", 0, 1, !IS_UNIQUE, IS_ORDERED);

		op = addEOperation(standardEquationEClass, null, "accept", 0, 1, !IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getAlphaVisitor(), "visitor", 0, 1, !IS_UNIQUE, IS_ORDERED);

		initEClass(useEquationEClass, UseEquation.class, "UseEquation", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getUseEquation_Identifier(), this.getString(), "identifier", null, 0, 1, UseEquation.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getUseEquation_InstantiationDomainExpr(), this.getCalculatorExpression(), null, "instantiationDomainExpr", null, 0, 1, UseEquation.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getUseEquation_SubsystemDims(), this.getString(), "subsystemDims", null, 0, -1, UseEquation.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getUseEquation_System(), this.getAlphaSystem(), null, "system", null, 0, 1, UseEquation.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getUseEquation_CallParamsExpr(), this.getJNIFunctionInArrayNotation(), null, "callParamsExpr", null, 0, 1, UseEquation.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getUseEquation_InputExprs(), this.getAlphaExpression(), null, "inputExprs", null, 0, -1, UseEquation.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getUseEquation_OutputExprs(), this.getAlphaExpression(), null, "outputExprs", null, 0, -1, UseEquation.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		addEOperation(useEquationEClass, this.getString(), "getName", 0, 1, !IS_UNIQUE, IS_ORDERED);

		addEOperation(useEquationEClass, this.getJNIISLMultiAff(), "getCallParams", 0, 1, !IS_UNIQUE, IS_ORDERED);

		addEOperation(useEquationEClass, this.getJNIISLSet(), "getInstantiationDomain", 0, 1, !IS_UNIQUE, IS_ORDERED);

		addEOperation(useEquationEClass, this.getListVariableExpression(), "getAllVariableExpressionsInOutputExpressions", 0, 1, !IS_UNIQUE, IS_ORDERED);

		op = addEOperation(useEquationEClass, null, "accept", 0, 1, !IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getAlphaVisitor(), "visitor", 0, 1, !IS_UNIQUE, IS_ORDERED);

		initEClass(alphaExpressionEClass, AlphaExpression.class, "AlphaExpression", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getAlphaExpression_Z__internal_cache_exprDom(), this.getJNIISLSet(), "z__internal_cache_exprDom", null, 0, 1, AlphaExpression.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getAlphaExpression_Z__internal_cache_contextDom(), this.getJNIISLSet(), "z__internal_cache_contextDom", null, 0, 1, AlphaExpression.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getAlphaExpression_ExpressionID(), this.getint(), "expressionID", null, 0, -1, AlphaExpression.class, IS_TRANSIENT, IS_VOLATILE, !IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, IS_DERIVED, IS_ORDERED);

		op = addEOperation(alphaExpressionEClass, null, "setExpressionDomain", 0, 1, !IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getJNIISLSet(), "dom", 0, 1, !IS_UNIQUE, IS_ORDERED);

		addEOperation(alphaExpressionEClass, this.getJNIISLSet(), "getExpressionDomain", 0, 1, !IS_UNIQUE, IS_ORDERED);

		op = addEOperation(alphaExpressionEClass, null, "setContextDomain", 0, 1, !IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getJNIISLSet(), "dom", 0, 1, !IS_UNIQUE, IS_ORDERED);

		addEOperation(alphaExpressionEClass, this.getJNIISLSet(), "getContextDomain", 0, 1, !IS_UNIQUE, IS_ORDERED);

		op = addEOperation(alphaExpressionEClass, this.getAlphaExpression(), "getExpression", 0, 1, !IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getIntegerQueue(), "exprID", 0, 1, !IS_UNIQUE, IS_ORDERED);

		op = addEOperation(alphaExpressionEClass, this.getJNIISLSet(), "ensureDomainIsNamed", 0, 1, !IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getJNIISLSet(), "dom", 0, 1, !IS_UNIQUE, IS_ORDERED);

		initEClass(restrictExpressionEClass, RestrictExpression.class, "RestrictExpression", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getRestrictExpression_DomainExpr(), this.getCalculatorExpression(), null, "domainExpr", null, 0, 1, RestrictExpression.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getRestrictExpression_Expr(), this.getAlphaExpression(), null, "expr", null, 0, 1, RestrictExpression.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		addEOperation(restrictExpressionEClass, this.getJNIISLSet(), "getRestrictDomain", 0, 1, !IS_UNIQUE, IS_ORDERED);

		op = addEOperation(restrictExpressionEClass, null, "accept", 0, 1, !IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getAlphaExpressionVisitor(), "visitor", 0, 1, !IS_UNIQUE, IS_ORDERED);

		initEClass(autoRestrictExpressionEClass, AutoRestrictExpression.class, "AutoRestrictExpression", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getAutoRestrictExpression_Expr(), this.getAlphaExpression(), null, "expr", null, 0, 1, AutoRestrictExpression.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getAutoRestrictExpression_Z__internal_cache_inferredDomain(), this.getJNIISLSet(), "z__internal_cache_inferredDomain", null, 0, 1, AutoRestrictExpression.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		addEOperation(autoRestrictExpressionEClass, this.getJNIISLSet(), "getInferredDomain", 0, 1, !IS_UNIQUE, IS_ORDERED);

		op = addEOperation(autoRestrictExpressionEClass, null, "setInferredDomain", 0, 1, !IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getJNIISLSet(), "islset", 0, 1, !IS_UNIQUE, IS_ORDERED);

		op = addEOperation(autoRestrictExpressionEClass, null, "accept", 0, 1, !IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getAlphaExpressionVisitor(), "visitor", 0, 1, !IS_UNIQUE, IS_ORDERED);

		initEClass(caseExpressionEClass, CaseExpression.class, "CaseExpression", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getCaseExpression_Name(), this.getString(), "name", null, 0, 1, CaseExpression.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getCaseExpression_Exprs(), this.getAlphaExpression(), null, "exprs", null, 0, -1, CaseExpression.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		addEOperation(caseExpressionEClass, this.getboolean(), "isNamed", 0, 1, !IS_UNIQUE, IS_ORDERED);

		op = addEOperation(caseExpressionEClass, null, "accept", 0, 1, !IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getAlphaExpressionVisitor(), "visitor", 0, 1, !IS_UNIQUE, IS_ORDERED);

		initEClass(dependenceExpressionEClass, DependenceExpression.class, "DependenceExpression", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getDependenceExpression_FunctionExpr(), this.getJNIFunction(), null, "functionExpr", null, 0, 1, DependenceExpression.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getDependenceExpression_Expr(), this.getAlphaExpression(), null, "expr", null, 0, 1, DependenceExpression.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		addEOperation(dependenceExpressionEClass, this.getJNIISLMultiAff(), "getFunction", 0, 1, !IS_UNIQUE, IS_ORDERED);

		op = addEOperation(dependenceExpressionEClass, null, "accept", 0, 1, !IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getAlphaExpressionVisitor(), "visitor", 0, 1, !IS_UNIQUE, IS_ORDERED);

		initEClass(fuzzyDependenceExpressionEClass, FuzzyDependenceExpression.class, "FuzzyDependenceExpression", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getFuzzyDependenceExpression_FuzzyFunction(), this.getFuzzyFunction(), null, "fuzzyFunction", null, 0, 1, FuzzyDependenceExpression.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getFuzzyDependenceExpression_Expr(), this.getAlphaExpression(), null, "expr", null, 0, 1, FuzzyDependenceExpression.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		addEOperation(fuzzyDependenceExpressionEClass, this.getJNIISLMap(), "getDependenceRelation", 0, 1, !IS_UNIQUE, IS_ORDERED);

		op = addEOperation(fuzzyDependenceExpressionEClass, null, "accept", 0, 1, !IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getAlphaExpressionVisitor(), "visitor", 0, 1, !IS_UNIQUE, IS_ORDERED);

		initEClass(ifExpressionEClass, IfExpression.class, "IfExpression", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getIfExpression_CondExpr(), this.getAlphaExpression(), null, "condExpr", null, 0, 1, IfExpression.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getIfExpression_ThenExpr(), this.getAlphaExpression(), null, "thenExpr", null, 0, 1, IfExpression.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getIfExpression_ElseExpr(), this.getAlphaExpression(), null, "elseExpr", null, 0, 1, IfExpression.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		op = addEOperation(ifExpressionEClass, null, "accept", 0, 1, !IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getAlphaExpressionVisitor(), "visitor", 0, 1, !IS_UNIQUE, IS_ORDERED);

		initEClass(indexExpressionEClass, IndexExpression.class, "IndexExpression", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getIndexExpression_FunctionExpr(), this.getJNIFunction(), null, "functionExpr", null, 0, 1, IndexExpression.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		addEOperation(indexExpressionEClass, this.getJNIISLMultiAff(), "getFunction", 0, 1, !IS_UNIQUE, IS_ORDERED);

		op = addEOperation(indexExpressionEClass, null, "accept", 0, 1, !IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getAlphaExpressionVisitor(), "visitor", 0, 1, !IS_UNIQUE, IS_ORDERED);

		initEClass(polynomialIndexExpressionEClass, PolynomialIndexExpression.class, "PolynomialIndexExpression", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getPolynomialIndexExpression_PolynomialExpr(), this.getJNIPolynomial(), null, "polynomialExpr", null, 0, 1, PolynomialIndexExpression.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		addEOperation(polynomialIndexExpressionEClass, this.getJNIISLPWQPolynomial(), "getPolynomial", 0, 1, !IS_UNIQUE, IS_ORDERED);

		op = addEOperation(polynomialIndexExpressionEClass, null, "accept", 0, 1, !IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getAlphaExpressionVisitor(), "visitor", 0, 1, !IS_UNIQUE, IS_ORDERED);

		initEClass(fuzzyIndexExpressionEClass, FuzzyIndexExpression.class, "FuzzyIndexExpression", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getFuzzyIndexExpression_FuzzyFunction(), this.getFuzzyFunction(), null, "fuzzyFunction", null, 0, 1, FuzzyIndexExpression.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		addEOperation(fuzzyIndexExpressionEClass, this.getJNIISLMap(), "getDependenceRelation", 0, 1, !IS_UNIQUE, IS_ORDERED);

		op = addEOperation(fuzzyIndexExpressionEClass, null, "accept", 0, 1, !IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getAlphaExpressionVisitor(), "visitor", 0, 1, !IS_UNIQUE, IS_ORDERED);

		initEClass(abstractReduceExpressionEClass, AbstractReduceExpression.class, "AbstractReduceExpression", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getAbstractReduceExpression_Operator(), this.getREDUCTION_OP(), "operator", null, 0, 1, AbstractReduceExpression.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getAbstractReduceExpression_ProjectionExpr(), this.getJNIFunction(), null, "projectionExpr", null, 0, 1, AbstractReduceExpression.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getAbstractReduceExpression_Body(), this.getAlphaExpression(), null, "body", null, 0, 1, AbstractReduceExpression.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		addEOperation(abstractReduceExpressionEClass, this.getJNIISLMultiAff(), "getProjection", 0, 1, !IS_UNIQUE, IS_ORDERED);

		op = addEOperation(abstractReduceExpressionEClass, null, "accept", 0, 1, !IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getAlphaExpressionVisitor(), "visitor", 0, 1, !IS_UNIQUE, IS_ORDERED);

		initEClass(reduceExpressionEClass, ReduceExpression.class, "ReduceExpression", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		op = addEOperation(reduceExpressionEClass, null, "accept", 0, 1, !IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getAlphaExpressionVisitor(), "visitor", 0, 1, !IS_UNIQUE, IS_ORDERED);

		initEClass(externalReduceExpressionEClass, ExternalReduceExpression.class, "ExternalReduceExpression", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getExternalReduceExpression_ExternalFunction(), this.getExternalFunction(), null, "externalFunction", null, 0, 1, ExternalReduceExpression.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		op = addEOperation(externalReduceExpressionEClass, null, "accept", 0, 1, !IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getAlphaExpressionVisitor(), "visitor", 0, 1, !IS_UNIQUE, IS_ORDERED);

		initEClass(argReduceExpressionEClass, ArgReduceExpression.class, "ArgReduceExpression", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		op = addEOperation(argReduceExpressionEClass, null, "accept", 0, 1, !IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getAlphaExpressionVisitor(), "visitor", 0, 1, !IS_UNIQUE, IS_ORDERED);

		initEClass(externalArgReduceExpressionEClass, ExternalArgReduceExpression.class, "ExternalArgReduceExpression", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getExternalArgReduceExpression_ExternalFunction(), this.getExternalFunction(), null, "externalFunction", null, 0, 1, ExternalArgReduceExpression.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		op = addEOperation(externalArgReduceExpressionEClass, null, "accept", 0, 1, !IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getAlphaExpressionVisitor(), "visitor", 0, 1, !IS_UNIQUE, IS_ORDERED);

		initEClass(convolutionExpressionEClass, ConvolutionExpression.class, "ConvolutionExpression", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getConvolutionExpression_KernelDomainExpr(), this.getCalculatorExpression(), null, "kernelDomainExpr", null, 0, 1, ConvolutionExpression.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getConvolutionExpression_KernelExpression(), this.getAlphaExpression(), null, "kernelExpression", null, 0, 1, ConvolutionExpression.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getConvolutionExpression_DataExpression(), this.getAlphaExpression(), null, "dataExpression", null, 0, 1, ConvolutionExpression.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		addEOperation(convolutionExpressionEClass, this.getJNIISLSet(), "getKernelDomain", 0, 1, !IS_UNIQUE, IS_ORDERED);

		op = addEOperation(convolutionExpressionEClass, null, "accept", 0, 1, !IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getAlphaExpressionVisitor(), "visitor", 0, 1, !IS_UNIQUE, IS_ORDERED);

		initEClass(selectExpressionEClass, SelectExpression.class, "SelectExpression", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getSelectExpression_RelationExpr(), this.getCalculatorExpression(), null, "relationExpr", null, 0, 1, SelectExpression.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getSelectExpression_Expr(), this.getAlphaExpression(), null, "expr", null, 0, 1, SelectExpression.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		addEOperation(selectExpressionEClass, this.getJNIISLMap(), "getSelectRelation", 0, 1, !IS_UNIQUE, IS_ORDERED);

		op = addEOperation(selectExpressionEClass, null, "accept", 0, 1, !IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getAlphaExpressionVisitor(), "visitor", 0, 1, !IS_UNIQUE, IS_ORDERED);

		initEClass(variableExpressionEClass, VariableExpression.class, "VariableExpression", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getVariableExpression_Variable(), this.getVariable(), null, "variable", null, 0, 1, VariableExpression.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		op = addEOperation(variableExpressionEClass, null, "accept", 0, 1, !IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getAlphaExpressionVisitor(), "visitor", 0, 1, !IS_UNIQUE, IS_ORDERED);

		initEClass(constantExpressionEClass, ConstantExpression.class, "ConstantExpression", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		addEOperation(constantExpressionEClass, this.getString(), "valueString", 0, 1, !IS_UNIQUE, IS_ORDERED);

		initEClass(integerExpressionEClass, IntegerExpression.class, "IntegerExpression", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getIntegerExpression_Value(), this.getint(), "value", null, 0, 1, IntegerExpression.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		addEOperation(integerExpressionEClass, this.getString(), "valueString", 0, 1, !IS_UNIQUE, IS_ORDERED);

		op = addEOperation(integerExpressionEClass, null, "accept", 0, 1, !IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getAlphaExpressionVisitor(), "visitor", 0, 1, !IS_UNIQUE, IS_ORDERED);

		initEClass(realExpressionEClass, RealExpression.class, "RealExpression", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getRealExpression_Value(), this.getfloat(), "value", null, 0, 1, RealExpression.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		addEOperation(realExpressionEClass, this.getString(), "valueString", 0, 1, !IS_UNIQUE, IS_ORDERED);

		op = addEOperation(realExpressionEClass, null, "accept", 0, 1, !IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getAlphaExpressionVisitor(), "visitor", 0, 1, !IS_UNIQUE, IS_ORDERED);

		initEClass(booleanExpressionEClass, BooleanExpression.class, "BooleanExpression", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getBooleanExpression_Value(), this.getboolean(), "value", null, 0, 1, BooleanExpression.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		addEOperation(booleanExpressionEClass, this.getString(), "valueString", 0, 1, !IS_UNIQUE, IS_ORDERED);

		op = addEOperation(booleanExpressionEClass, null, "accept", 0, 1, !IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getAlphaExpressionVisitor(), "visitor", 0, 1, !IS_UNIQUE, IS_ORDERED);

		initEClass(unaryExpressionEClass, UnaryExpression.class, "UnaryExpression", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getUnaryExpression_Operator(), this.getUNARY_OP(), "operator", null, 0, 1, UnaryExpression.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getUnaryExpression_Expr(), this.getAlphaExpression(), null, "expr", null, 0, 1, UnaryExpression.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		op = addEOperation(unaryExpressionEClass, null, "accept", 0, 1, !IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getAlphaExpressionVisitor(), "visitor", 0, 1, !IS_UNIQUE, IS_ORDERED);

		initEClass(binaryExpressionEClass, BinaryExpression.class, "BinaryExpression", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getBinaryExpression_Operator(), this.getBINARY_OP(), "operator", null, 0, 1, BinaryExpression.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getBinaryExpression_Left(), this.getAlphaExpression(), null, "left", null, 0, 1, BinaryExpression.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getBinaryExpression_Right(), this.getAlphaExpression(), null, "right", null, 0, 1, BinaryExpression.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		op = addEOperation(binaryExpressionEClass, null, "accept", 0, 1, !IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getAlphaExpressionVisitor(), "visitor", 0, 1, !IS_UNIQUE, IS_ORDERED);

		initEClass(multiArgExpressionEClass, MultiArgExpression.class, "MultiArgExpression", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getMultiArgExpression_Operator(), this.getREDUCTION_OP(), "operator", null, 0, 1, MultiArgExpression.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getMultiArgExpression_Exprs(), this.getAlphaExpression(), null, "exprs", null, 0, -1, MultiArgExpression.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		op = addEOperation(multiArgExpressionEClass, null, "accept", 0, 1, !IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getAlphaExpressionVisitor(), "visitor", 0, 1, !IS_UNIQUE, IS_ORDERED);

		initEClass(externalMultiArgExpressionEClass, ExternalMultiArgExpression.class, "ExternalMultiArgExpression", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getExternalMultiArgExpression_ExternalFunction(), this.getExternalFunction(), null, "externalFunction", null, 0, 1, ExternalMultiArgExpression.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		op = addEOperation(externalMultiArgExpressionEClass, null, "accept", 0, 1, !IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getAlphaExpressionVisitor(), "visitor", 0, 1, !IS_UNIQUE, IS_ORDERED);

		initEClass(abstractFuzzyReduceExpressionEClass, AbstractFuzzyReduceExpression.class, "AbstractFuzzyReduceExpression", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getAbstractFuzzyReduceExpression_Operator(), this.getREDUCTION_OP(), "operator", null, 0, 1, AbstractFuzzyReduceExpression.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getAbstractFuzzyReduceExpression_ProjectionFunction(), this.getFuzzyFunction(), null, "projectionFunction", null, 0, 1, AbstractFuzzyReduceExpression.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getAbstractFuzzyReduceExpression_Body(), this.getAlphaExpression(), null, "body", null, 0, 1, AbstractFuzzyReduceExpression.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		addEOperation(abstractFuzzyReduceExpressionEClass, this.getJNIISLMap(), "getProjection", 0, 1, !IS_UNIQUE, IS_ORDERED);

		op = addEOperation(abstractFuzzyReduceExpressionEClass, null, "accept", 0, 1, !IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getAlphaExpressionVisitor(), "visitor", 0, 1, !IS_UNIQUE, IS_ORDERED);

		initEClass(fuzzyReduceExpressionEClass, FuzzyReduceExpression.class, "FuzzyReduceExpression", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		op = addEOperation(fuzzyReduceExpressionEClass, null, "accept", 0, 1, !IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getAlphaExpressionVisitor(), "visitor", 0, 1, !IS_UNIQUE, IS_ORDERED);

		initEClass(externalFuzzyReduceExpressionEClass, ExternalFuzzyReduceExpression.class, "ExternalFuzzyReduceExpression", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getExternalFuzzyReduceExpression_ExternalFunction(), this.getExternalFunction(), null, "externalFunction", null, 0, 1, ExternalFuzzyReduceExpression.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		op = addEOperation(externalFuzzyReduceExpressionEClass, null, "accept", 0, 1, !IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getAlphaExpressionVisitor(), "visitor", 0, 1, !IS_UNIQUE, IS_ORDERED);

		initEClass(fuzzyArgReduceExpressionEClass, FuzzyArgReduceExpression.class, "FuzzyArgReduceExpression", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		op = addEOperation(fuzzyArgReduceExpressionEClass, null, "accept", 0, 1, !IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getAlphaExpressionVisitor(), "visitor", 0, 1, !IS_UNIQUE, IS_ORDERED);

		initEClass(externalFuzzyArgReduceExpressionEClass, ExternalFuzzyArgReduceExpression.class, "ExternalFuzzyArgReduceExpression", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getExternalFuzzyArgReduceExpression_ExternalFunction(), this.getExternalFunction(), null, "externalFunction", null, 0, 1, ExternalFuzzyArgReduceExpression.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		op = addEOperation(externalFuzzyArgReduceExpressionEClass, null, "accept", 0, 1, !IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getAlphaExpressionVisitor(), "visitor", 0, 1, !IS_UNIQUE, IS_ORDERED);

		initEClass(calculatorNodeEClass, CalculatorNode.class, "CalculatorNode", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		addEOperation(calculatorNodeEClass, this.getPOLY_OBJECT_TYPE(), "getType", 0, 1, !IS_UNIQUE, IS_ORDERED);

		addEOperation(calculatorNodeEClass, this.getJNIObject(), "getISLObject", 0, 1, !IS_UNIQUE, IS_ORDERED);

		initEClass(polyhedralObjectEClass, PolyhedralObject.class, "PolyhedralObject", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getPolyhedralObject_Name(), this.getString(), "name", null, 0, 1, PolyhedralObject.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getPolyhedralObject_Expr(), this.getCalculatorExpression(), null, "expr", null, 0, 1, PolyhedralObject.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		addEOperation(polyhedralObjectEClass, this.getPOLY_OBJECT_TYPE(), "getType", 0, 1, !IS_UNIQUE, IS_ORDERED);

		addEOperation(polyhedralObjectEClass, this.getJNIObject(), "getISLObject", 0, 1, !IS_UNIQUE, IS_ORDERED);

		op = addEOperation(polyhedralObjectEClass, null, "accept", 0, 1, !IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getAlphaVisitor(), "visitor", 0, 1, !IS_UNIQUE, IS_ORDERED);

		initEClass(calculatorExpressionEClass, CalculatorExpression.class, "CalculatorExpression", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		op = addEOperation(calculatorExpressionEClass, null, "accept", 0, 1, !IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getCalculatorExpressionVisitor(), "visitor", 0, 1, !IS_UNIQUE, IS_ORDERED);

		addEOperation(calculatorExpressionEClass, this.getString(), "plainToString", 0, 1, !IS_UNIQUE, IS_ORDERED);

		initEClass(jniDomainEClass, JNIDomain.class, "JNIDomain", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getJNIDomain_IslString(), this.getString(), "islString", null, 0, 1, JNIDomain.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getJNIDomain_Z__internal_cache_islSet(), this.getJNIISLSet(), "z__internal_cache_islSet", null, 0, 1, JNIDomain.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		addEOperation(jniDomainEClass, this.getJNIISLSet(), "getISLSet", 0, 1, !IS_UNIQUE, IS_ORDERED);

		op = addEOperation(jniDomainEClass, null, "setISLSet", 0, 1, !IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getJNIISLSet(), "islset", 0, 1, !IS_UNIQUE, IS_ORDERED);

		addEOperation(jniDomainEClass, this.getPOLY_OBJECT_TYPE(), "getType", 0, 1, !IS_UNIQUE, IS_ORDERED);

		addEOperation(jniDomainEClass, this.getJNIObject(), "getISLObject", 0, 1, !IS_UNIQUE, IS_ORDERED);

		addEOperation(jniDomainEClass, this.getString(), "toString", 0, 1, !IS_UNIQUE, IS_ORDERED);

		addEOperation(jniDomainEClass, this.getString(), "plainToString", 0, 1, !IS_UNIQUE, IS_ORDERED);

		op = addEOperation(jniDomainEClass, null, "accept", 0, 1, !IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getCalculatorExpressionVisitor(), "visitor", 0, 1, !IS_UNIQUE, IS_ORDERED);

		initEClass(jniDomainInArrayNotationEClass, JNIDomainInArrayNotation.class, "JNIDomainInArrayNotation", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(jniRelationEClass, JNIRelation.class, "JNIRelation", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getJNIRelation_IslString(), this.getString(), "islString", null, 0, 1, JNIRelation.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getJNIRelation_Z__internal_cache_islMap(), this.getJNIISLMap(), "z__internal_cache_islMap", null, 0, 1, JNIRelation.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		addEOperation(jniRelationEClass, this.getJNIISLMap(), "getISLMap", 0, 1, !IS_UNIQUE, IS_ORDERED);

		op = addEOperation(jniRelationEClass, null, "setISLMap", 0, 1, !IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getJNIISLMap(), "islMap", 0, 1, !IS_UNIQUE, IS_ORDERED);

		addEOperation(jniRelationEClass, this.getPOLY_OBJECT_TYPE(), "getType", 0, 1, !IS_UNIQUE, IS_ORDERED);

		addEOperation(jniRelationEClass, this.getJNIObject(), "getISLObject", 0, 1, !IS_UNIQUE, IS_ORDERED);

		addEOperation(jniRelationEClass, this.getString(), "toString", 0, 1, !IS_UNIQUE, IS_ORDERED);

		addEOperation(jniRelationEClass, this.getString(), "plainToString", 0, 1, !IS_UNIQUE, IS_ORDERED);

		op = addEOperation(jniRelationEClass, null, "accept", 0, 1, !IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getCalculatorExpressionVisitor(), "visitor", 0, 1, !IS_UNIQUE, IS_ORDERED);

		initEClass(jniFunctionEClass, JNIFunction.class, "JNIFunction", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getJNIFunction_AlphaFunction(), this.getAlphaFunction(), null, "alphaFunction", null, 0, 1, JNIFunction.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getJNIFunction_Z__internal_cache_islMAff(), this.getJNIISLMultiAff(), "z__internal_cache_islMAff", null, 0, 1, JNIFunction.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		addEOperation(jniFunctionEClass, this.getJNIISLMultiAff(), "getISLMultiAff", 0, 1, !IS_UNIQUE, IS_ORDERED);

		op = addEOperation(jniFunctionEClass, null, "setISLMultiAff", 0, 1, !IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getJNIISLMultiAff(), "islMAff", 0, 1, !IS_UNIQUE, IS_ORDERED);

		addEOperation(jniFunctionEClass, this.getPOLY_OBJECT_TYPE(), "getType", 0, 1, !IS_UNIQUE, IS_ORDERED);

		addEOperation(jniFunctionEClass, this.getJNIObject(), "getISLObject", 0, 1, !IS_UNIQUE, IS_ORDERED);

		op = addEOperation(jniFunctionEClass, null, "accept", 0, 1, !IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getCalculatorExpressionVisitor(), "visitor", 0, 1, !IS_UNIQUE, IS_ORDERED);

		addEOperation(jniFunctionEClass, this.getString(), "plainToString", 0, 1, !IS_UNIQUE, IS_ORDERED);

		op = addEOperation(jniFunctionEClass, this.getJNIISLMultiAff(), "ensureDomainAndRangeAreNamed", 0, 1, !IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getJNIISLMultiAff(), "maff", 0, 1, !IS_UNIQUE, IS_ORDERED);

		initEClass(jniFunctionInArrayNotationEClass, JNIFunctionInArrayNotation.class, "JNIFunctionInArrayNotation", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getJNIFunctionInArrayNotation_ArrayNotation(), this.getString(), "arrayNotation", null, 0, -1, JNIFunctionInArrayNotation.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		addEOperation(jniFunctionInArrayNotationEClass, this.getString(), "plainToString", 0, 1, !IS_UNIQUE, IS_ORDERED);

		initEClass(jniPolynomialEClass, JNIPolynomial.class, "JNIPolynomial", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getJNIPolynomial_IslString(), this.getString(), "islString", null, 0, 1, JNIPolynomial.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getJNIPolynomial_Z__internal_cache_islPWQP(), this.getJNIISLPWQPolynomial(), "z__internal_cache_islPWQP", null, 0, 1, JNIPolynomial.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		addEOperation(jniPolynomialEClass, this.getJNIISLPWQPolynomial(), "getISLPWQPolynomial", 0, 1, !IS_UNIQUE, IS_ORDERED);

		op = addEOperation(jniPolynomialEClass, null, "setISLPWQPolynomial", 0, 1, !IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getJNIISLPWQPolynomial(), "islQP", 0, 1, !IS_UNIQUE, IS_ORDERED);

		addEOperation(jniPolynomialEClass, this.getPOLY_OBJECT_TYPE(), "getType", 0, 1, !IS_UNIQUE, IS_ORDERED);

		addEOperation(jniPolynomialEClass, this.getJNIObject(), "getISLObject", 0, 1, !IS_UNIQUE, IS_ORDERED);

		op = addEOperation(jniPolynomialEClass, null, "accept", 0, 1, !IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getCalculatorExpressionVisitor(), "visitor", 0, 1, !IS_UNIQUE, IS_ORDERED);

		addEOperation(jniPolynomialEClass, this.getString(), "plainToString", 0, 1, !IS_UNIQUE, IS_ORDERED);

		initEClass(jniPolynomialInArrayNotationEClass, JNIPolynomialInArrayNotation.class, "JNIPolynomialInArrayNotation", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getJNIPolynomialInArrayNotation_ArrayNotation(), this.getString(), "arrayNotation", null, 0, -1, JNIPolynomialInArrayNotation.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		addEOperation(jniPolynomialInArrayNotationEClass, this.getString(), "plainToString", 0, 1, !IS_UNIQUE, IS_ORDERED);

		initEClass(fuzzyFunctionEClass, FuzzyFunction.class, "FuzzyFunction", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getFuzzyFunction_AlphaString(), this.getString(), "alphaString", null, 0, 1, FuzzyFunction.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getFuzzyFunction_Indirections(), this.getFuzzyVariableUse(), null, "indirections", null, 0, -1, FuzzyFunction.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getFuzzyFunction_Z__internal_cache_fuzzyMap(), this.getJNIISLMap(), "z__internal_cache_fuzzyMap", null, 0, 1, FuzzyFunction.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getFuzzyFunction_Z__internal_cache_depRelation(), this.getJNIISLMap(), "z__internal_cache_depRelation", null, 0, 1, FuzzyFunction.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		addEOperation(fuzzyFunctionEClass, this.getJNIISLMap(), "getFuzzyMap", 0, 1, !IS_UNIQUE, IS_ORDERED);

		op = addEOperation(fuzzyFunctionEClass, null, "setFuzzyMap", 0, 1, !IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getJNIISLMap(), "fuzzyMap", 0, 1, !IS_UNIQUE, IS_ORDERED);

		addEOperation(fuzzyFunctionEClass, this.getJNIISLMap(), "getDependenceRelation", 0, 1, !IS_UNIQUE, IS_ORDERED);

		op = addEOperation(fuzzyFunctionEClass, null, "setDependenceRelation", 0, 1, !IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getJNIISLMap(), "depRel", 0, 1, !IS_UNIQUE, IS_ORDERED);

		op = addEOperation(fuzzyFunctionEClass, this.getFuzzyVariableUse(), "getIndirectionByName", 0, 1, !IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getString(), "name", 0, 1, !IS_UNIQUE, IS_ORDERED);

		initEClass(fuzzyVariableUseEClass, FuzzyVariableUse.class, "FuzzyVariableUse", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getFuzzyVariableUse_FuzzyIndex(), this.getString(), "fuzzyIndex", null, 0, 1, FuzzyVariableUse.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getFuzzyVariableUse_FuzzyVariable(), this.getFuzzyVariable(), null, "fuzzyVariable", null, 0, 1, FuzzyVariableUse.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		addEOperation(fuzzyVariableUseEClass, this.getJNIISLMap(), "getDependenceRelation", 0, 1, !IS_UNIQUE, IS_ORDERED);

		initEClass(nestedFuzzyFunctionEClass, NestedFuzzyFunction.class, "NestedFuzzyFunction", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(affineFuzzyVariableUseEClass, AffineFuzzyVariableUse.class, "AffineFuzzyVariableUse", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getAffineFuzzyVariableUse_UseFunction(), this.getJNIFunctionInArrayNotation(), null, "useFunction", null, 0, 1, AffineFuzzyVariableUse.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		addEOperation(affineFuzzyVariableUseEClass, this.getJNIISLMap(), "getDependenceRelation", 0, 1, !IS_UNIQUE, IS_ORDERED);

		initEClass(fuzzyFunctionInArrayNotationEClass, FuzzyFunctionInArrayNotation.class, "FuzzyFunctionInArrayNotation", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getFuzzyFunctionInArrayNotation_ArrayNotation(), this.getString(), "arrayNotation", null, 0, -1, FuzzyFunctionInArrayNotation.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		addEOperation(fuzzyFunctionInArrayNotationEClass, this.getString(), "plainToString", 0, 1, !IS_UNIQUE, IS_ORDERED);

		initEClass(unaryCalculatorExpressionEClass, UnaryCalculatorExpression.class, "UnaryCalculatorExpression", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getUnaryCalculatorExpression_Operator(), this.getCALCULATOR_UNARY_OP(), "operator", null, 0, 1, UnaryCalculatorExpression.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getUnaryCalculatorExpression_Expr(), this.getCalculatorExpression(), null, "expr", null, 0, 1, UnaryCalculatorExpression.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getUnaryCalculatorExpression_Z__internal_cache_islObject(), this.getJNIObject(), "z__internal_cache_islObject", null, 0, 1, UnaryCalculatorExpression.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		addEOperation(unaryCalculatorExpressionEClass, this.getPOLY_OBJECT_TYPE(), "getType", 0, 1, !IS_UNIQUE, IS_ORDERED);

		addEOperation(unaryCalculatorExpressionEClass, this.getJNIObject(), "getISLObject", 0, 1, !IS_UNIQUE, IS_ORDERED);

		op = addEOperation(unaryCalculatorExpressionEClass, null, "accept", 0, 1, !IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getCalculatorExpressionVisitor(), "visitor", 0, 1, !IS_UNIQUE, IS_ORDERED);

		addEOperation(unaryCalculatorExpressionEClass, this.getString(), "plainToString", 0, 1, !IS_UNIQUE, IS_ORDERED);

		initEClass(binaryCalculatorExpressionEClass, BinaryCalculatorExpression.class, "BinaryCalculatorExpression", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getBinaryCalculatorExpression_Operator(), this.getCALCULATOR_BINARY_OP(), "operator", null, 0, 1, BinaryCalculatorExpression.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getBinaryCalculatorExpression_Left(), this.getCalculatorExpression(), null, "left", null, 0, 1, BinaryCalculatorExpression.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getBinaryCalculatorExpression_Right(), this.getCalculatorExpression(), null, "right", null, 0, 1, BinaryCalculatorExpression.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getBinaryCalculatorExpression_Z__internal_cache_islObject(), this.getJNIObject(), "z__internal_cache_islObject", null, 0, 1, BinaryCalculatorExpression.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		addEOperation(binaryCalculatorExpressionEClass, this.getPOLY_OBJECT_TYPE(), "getType", 0, 1, !IS_UNIQUE, IS_ORDERED);

		addEOperation(binaryCalculatorExpressionEClass, this.getJNIObject(), "getISLObject", 0, 1, !IS_UNIQUE, IS_ORDERED);

		op = addEOperation(binaryCalculatorExpressionEClass, null, "accept", 0, 1, !IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getCalculatorExpressionVisitor(), "visitor", 0, 1, !IS_UNIQUE, IS_ORDERED);

		addEOperation(binaryCalculatorExpressionEClass, this.getString(), "plainToString", 0, 1, !IS_UNIQUE, IS_ORDERED);

		initEClass(variableDomainEClass, VariableDomain.class, "VariableDomain", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getVariableDomain_Variable(), this.getVariable(), null, "variable", null, 0, 1, VariableDomain.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		addEOperation(variableDomainEClass, this.getPOLY_OBJECT_TYPE(), "getType", 0, 1, !IS_UNIQUE, IS_ORDERED);

		addEOperation(variableDomainEClass, this.getJNIObject(), "getISLObject", 0, 1, !IS_UNIQUE, IS_ORDERED);

		op = addEOperation(variableDomainEClass, null, "accept", 0, 1, !IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getCalculatorExpressionVisitor(), "visitor", 0, 1, !IS_UNIQUE, IS_ORDERED);

		addEOperation(variableDomainEClass, this.getString(), "plainToString", 0, 1, !IS_UNIQUE, IS_ORDERED);

		initEClass(rectangularDomainEClass, RectangularDomain.class, "RectangularDomain", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getRectangularDomain_LowerBounds(), this.getString(), "lowerBounds", null, 0, -1, RectangularDomain.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getRectangularDomain_UpperBounds(), this.getString(), "upperBounds", null, 0, -1, RectangularDomain.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getRectangularDomain_IndexNames(), this.getString(), "indexNames", null, 0, -1, RectangularDomain.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getRectangularDomain_Z__internal_cache_islSet(), this.getJNIISLSet(), "z__internal_cache_islSet", null, 0, 1, RectangularDomain.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		addEOperation(rectangularDomainEClass, this.getJNIISLSet(), "getISLSet", 0, 1, !IS_UNIQUE, IS_ORDERED);

		op = addEOperation(rectangularDomainEClass, null, "setISLSet", 0, 1, !IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getJNIISLSet(), "islset", 0, 1, !IS_UNIQUE, IS_ORDERED);

		addEOperation(rectangularDomainEClass, this.getPOLY_OBJECT_TYPE(), "getType", 0, 1, !IS_UNIQUE, IS_ORDERED);

		addEOperation(rectangularDomainEClass, this.getJNIObject(), "getISLObject", 0, 1, !IS_UNIQUE, IS_ORDERED);

		op = addEOperation(rectangularDomainEClass, null, "accept", 0, 1, !IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getCalculatorExpressionVisitor(), "visitor", 0, 1, !IS_UNIQUE, IS_ORDERED);

		addEOperation(rectangularDomainEClass, this.getString(), "plainToString", 0, 1, !IS_UNIQUE, IS_ORDERED);

		initEClass(definedObjectEClass, DefinedObject.class, "DefinedObject", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getDefinedObject_Object(), this.getPolyhedralObject(), null, "object", null, 0, 1, DefinedObject.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getDefinedObject_Z__internalCycleDetector(), this.getboolean(), "z__internalCycleDetector", "false", 0, 1, DefinedObject.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		addEOperation(definedObjectEClass, this.getPOLY_OBJECT_TYPE(), "getType", 0, 1, !IS_UNIQUE, IS_ORDERED);

		addEOperation(definedObjectEClass, this.getJNIObject(), "getISLObject", 0, 1, !IS_UNIQUE, IS_ORDERED);

		op = addEOperation(definedObjectEClass, null, "accept", 0, 1, !IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getCalculatorExpressionVisitor(), "visitor", 0, 1, !IS_UNIQUE, IS_ORDERED);

		addEOperation(definedObjectEClass, this.getString(), "plainToString", 0, 1, !IS_UNIQUE, IS_ORDERED);

		initEClass(alphaFunctionEClass, AlphaFunction.class, "AlphaFunction", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getAlphaFunction_IndexList(), this.getString(), "indexList", null, 0, 1, AlphaFunction.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getAlphaFunction_Exprs(), this.getAlphaFunctionExpression(), null, "exprs", null, 0, -1, AlphaFunction.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		addEOperation(alphaFunctionEClass, this.getString(), "plainToString", 0, 1, !IS_UNIQUE, IS_ORDERED);

		initEClass(alphaFunctionExpressionEClass, AlphaFunctionExpression.class, "AlphaFunctionExpression", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		addEOperation(alphaFunctionExpressionEClass, this.getString(), "getISLString", 0, 1, !IS_UNIQUE, IS_ORDERED);

		initEClass(alphaFunctionBinaryExpressionEClass, AlphaFunctionBinaryExpression.class, "AlphaFunctionBinaryExpression", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getAlphaFunctionBinaryExpression_Left(), this.getAlphaFunctionExpression(), null, "left", null, 0, 1, AlphaFunctionBinaryExpression.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getAlphaFunctionBinaryExpression_Right(), this.getAlphaFunctionExpression(), null, "right", null, 0, 1, AlphaFunctionBinaryExpression.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getAlphaFunctionBinaryExpression_Operator(), this.getString(), "operator", null, 0, 1, AlphaFunctionBinaryExpression.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		addEOperation(alphaFunctionBinaryExpressionEClass, this.getString(), "getISLString", 0, 1, !IS_UNIQUE, IS_ORDERED);

		initEClass(alphaFunctionLiteralEClass, AlphaFunctionLiteral.class, "AlphaFunctionLiteral", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getAlphaFunctionLiteral_Value(), this.getString(), "value", null, 0, 1, AlphaFunctionLiteral.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		addEOperation(alphaFunctionLiteralEClass, this.getString(), "getISLString", 0, 1, !IS_UNIQUE, IS_ORDERED);

		initEClass(alphaFunctionFloorEClass, AlphaFunctionFloor.class, "AlphaFunctionFloor", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getAlphaFunctionFloor_Expr(), this.getAlphaFunctionExpression(), null, "expr", null, 0, 1, AlphaFunctionFloor.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		addEOperation(alphaFunctionFloorEClass, this.getString(), "getISLString", 0, 1, !IS_UNIQUE, IS_ORDERED);

		// Initialize enums and add enum literals
		initEEnum(unarY_OPEEnum, alpha.model.UNARY_OP.class, "UNARY_OP");
		addEEnumLiteral(unarY_OPEEnum, alpha.model.UNARY_OP.NOT);
		addEEnumLiteral(unarY_OPEEnum, alpha.model.UNARY_OP.NEGATE);

		initEEnum(binarY_OPEEnum, alpha.model.BINARY_OP.class, "BINARY_OP");
		addEEnumLiteral(binarY_OPEEnum, alpha.model.BINARY_OP.MIN);
		addEEnumLiteral(binarY_OPEEnum, alpha.model.BINARY_OP.MAX);
		addEEnumLiteral(binarY_OPEEnum, alpha.model.BINARY_OP.MUL);
		addEEnumLiteral(binarY_OPEEnum, alpha.model.BINARY_OP.DIV);
		addEEnumLiteral(binarY_OPEEnum, alpha.model.BINARY_OP.MOD);
		addEEnumLiteral(binarY_OPEEnum, alpha.model.BINARY_OP.ADD);
		addEEnumLiteral(binarY_OPEEnum, alpha.model.BINARY_OP.SUB);
		addEEnumLiteral(binarY_OPEEnum, alpha.model.BINARY_OP.AND);
		addEEnumLiteral(binarY_OPEEnum, alpha.model.BINARY_OP.OR);
		addEEnumLiteral(binarY_OPEEnum, alpha.model.BINARY_OP.XOR);
		addEEnumLiteral(binarY_OPEEnum, alpha.model.BINARY_OP.EQ);
		addEEnumLiteral(binarY_OPEEnum, alpha.model.BINARY_OP.NE);
		addEEnumLiteral(binarY_OPEEnum, alpha.model.BINARY_OP.GE);
		addEEnumLiteral(binarY_OPEEnum, alpha.model.BINARY_OP.GT);
		addEEnumLiteral(binarY_OPEEnum, alpha.model.BINARY_OP.LE);
		addEEnumLiteral(binarY_OPEEnum, alpha.model.BINARY_OP.LT);

		initEEnum(reductioN_OPEEnum, alpha.model.REDUCTION_OP.class, "REDUCTION_OP");
		addEEnumLiteral(reductioN_OPEEnum, alpha.model.REDUCTION_OP.MIN);
		addEEnumLiteral(reductioN_OPEEnum, alpha.model.REDUCTION_OP.MAX);
		addEEnumLiteral(reductioN_OPEEnum, alpha.model.REDUCTION_OP.PROD);
		addEEnumLiteral(reductioN_OPEEnum, alpha.model.REDUCTION_OP.SUM);
		addEEnumLiteral(reductioN_OPEEnum, alpha.model.REDUCTION_OP.AND);
		addEEnumLiteral(reductioN_OPEEnum, alpha.model.REDUCTION_OP.OR);
		addEEnumLiteral(reductioN_OPEEnum, alpha.model.REDUCTION_OP.XOR);
		addEEnumLiteral(reductioN_OPEEnum, alpha.model.REDUCTION_OP.EX);

		initEEnum(polY_OBJECT_TYPEEEnum, alpha.model.POLY_OBJECT_TYPE.class, "POLY_OBJECT_TYPE");
		addEEnumLiteral(polY_OBJECT_TYPEEEnum, alpha.model.POLY_OBJECT_TYPE.SET);
		addEEnumLiteral(polY_OBJECT_TYPEEEnum, alpha.model.POLY_OBJECT_TYPE.MAP);
		addEEnumLiteral(polY_OBJECT_TYPEEEnum, alpha.model.POLY_OBJECT_TYPE.FUNCTION);
		addEEnumLiteral(polY_OBJECT_TYPEEEnum, alpha.model.POLY_OBJECT_TYPE.POLYNOMIAL);

		initEEnum(calculatoR_UNARY_OPEEnum, alpha.model.CALCULATOR_UNARY_OP.class, "CALCULATOR_UNARY_OP");
		addEEnumLiteral(calculatoR_UNARY_OPEEnum, alpha.model.CALCULATOR_UNARY_OP.GET_DOMAIN);
		addEEnumLiteral(calculatoR_UNARY_OPEEnum, alpha.model.CALCULATOR_UNARY_OP.GET_RANGE);
		addEEnumLiteral(calculatoR_UNARY_OPEEnum, alpha.model.CALCULATOR_UNARY_OP.COMPLEMENT);
		addEEnumLiteral(calculatoR_UNARY_OPEEnum, alpha.model.CALCULATOR_UNARY_OP.POLYHEDRAL_HULL);
		addEEnumLiteral(calculatoR_UNARY_OPEEnum, alpha.model.CALCULATOR_UNARY_OP.AFFINE_HULL);
		addEEnumLiteral(calculatoR_UNARY_OPEEnum, alpha.model.CALCULATOR_UNARY_OP.REVERSE);

		initEEnum(calculatoR_BINARY_OPEEnum, alpha.model.CALCULATOR_BINARY_OP.class, "CALCULATOR_BINARY_OP");
		addEEnumLiteral(calculatoR_BINARY_OPEEnum, alpha.model.CALCULATOR_BINARY_OP.INTERSECT);
		addEEnumLiteral(calculatoR_BINARY_OPEEnum, alpha.model.CALCULATOR_BINARY_OP.UNION);
		addEEnumLiteral(calculatoR_BINARY_OPEEnum, alpha.model.CALCULATOR_BINARY_OP.CROSS_PRODUCT);
		addEEnumLiteral(calculatoR_BINARY_OPEEnum, alpha.model.CALCULATOR_BINARY_OP.SET_DIFFERENCE);
		addEEnumLiteral(calculatoR_BINARY_OPEEnum, alpha.model.CALCULATOR_BINARY_OP.JOIN);
		addEEnumLiteral(calculatoR_BINARY_OPEEnum, alpha.model.CALCULATOR_BINARY_OP.INTERSECT_RANGE);
		addEEnumLiteral(calculatoR_BINARY_OPEEnum, alpha.model.CALCULATOR_BINARY_OP.SUBTRACT_RANGE);

		// Initialize data types
		initEDataType(jniObjectEDataType, JNIObject.class, "JNIObject", IS_SERIALIZABLE, !IS_GENERATED_INSTANCE_CLASS);
		initEDataType(jniislSetEDataType, ISLSet.class, "JNIISLSet", IS_SERIALIZABLE, !IS_GENERATED_INSTANCE_CLASS);
		initEDataType(jniislMapEDataType, ISLMap.class, "JNIISLMap", IS_SERIALIZABLE, !IS_GENERATED_INSTANCE_CLASS);
		initEDataType(jniislMultiAffEDataType, ISLMultiAff.class, "JNIISLMultiAff", IS_SERIALIZABLE, !IS_GENERATED_INSTANCE_CLASS);
		initEDataType(jniislpwqPolynomialEDataType, ISLPWQPolynomial.class, "JNIISLPWQPolynomial", IS_SERIALIZABLE, !IS_GENERATED_INSTANCE_CLASS);
		initEDataType(isL_FORMATEDataType, fr.irisa.cairn.jnimap.isl.ISL_FORMAT.class, "ISL_FORMAT", IS_SERIALIZABLE, !IS_GENERATED_INSTANCE_CLASS);
		initEDataType(listVariableExpressionEDataType, List.class, "ListVariableExpression", IS_SERIALIZABLE, !IS_GENERATED_INSTANCE_CLASS, "java.util.List<alpha.model.VariableExpression>");
		initEDataType(integerQueueEDataType, Queue.class, "IntegerQueue", IS_SERIALIZABLE, !IS_GENERATED_INSTANCE_CLASS, "java.util.Queue<java.lang.Integer>");
		initEDataType(stringEDataType, String.class, "String", IS_SERIALIZABLE, !IS_GENERATED_INSTANCE_CLASS);
		initEDataType(intEDataType, Integer.class, "int", IS_SERIALIZABLE, !IS_GENERATED_INSTANCE_CLASS);
		initEDataType(floatEDataType, Float.class, "float", IS_SERIALIZABLE, !IS_GENERATED_INSTANCE_CLASS);
		initEDataType(doubleEDataType, Double.class, "double", IS_SERIALIZABLE, !IS_GENERATED_INSTANCE_CLASS);
		initEDataType(booleanEDataType, Boolean.class, "boolean", IS_SERIALIZABLE, !IS_GENERATED_INSTANCE_CLASS);

		// Create resource
		createResource(eNS_URI);
	}

} //ModelPackageImpl
