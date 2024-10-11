/**
 */
package alpha.model;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

/**
 * <!-- begin-user-doc -->
 * The <b>Package</b> for the model.
 * It contains accessors for the meta objects to represent
 * <ul>
 *   <li>each class,</li>
 *   <li>each feature of each class,</li>
 *   <li>each enum,</li>
 *   <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc -->
 * @see alpha.model.ModelFactory
 * @model kind="package"
 * @generated
 */
public interface ModelPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "model";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "alpha.model";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "model";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	ModelPackage eINSTANCE = alpha.model.impl.ModelPackageImpl.init();

	/**
	 * The meta object id for the '{@link alpha.model.impl.AlphaNodeImpl <em>Alpha Node</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see alpha.model.impl.AlphaNodeImpl
	 * @see alpha.model.impl.ModelPackageImpl#getAlphaNode()
	 * @generated
	 */
	int ALPHA_NODE = 0;

	/**
	 * The feature id for the '<em><b>Node ID</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ALPHA_NODE__NODE_ID = 0;

	/**
	 * The number of structural features of the '<em>Alpha Node</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ALPHA_NODE_FEATURE_COUNT = 1;

	/**
	 * The meta object id for the '{@link alpha.model.AlphaCompleteVisitable <em>Alpha Complete Visitable</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see alpha.model.AlphaCompleteVisitable
	 * @see alpha.model.impl.ModelPackageImpl#getAlphaCompleteVisitable()
	 * @generated
	 */
	int ALPHA_COMPLETE_VISITABLE = 1;

	/**
	 * The number of structural features of the '<em>Alpha Complete Visitable</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ALPHA_COMPLETE_VISITABLE_FEATURE_COUNT = 0;

	/**
	 * The meta object id for the '{@link alpha.model.AlphaVisitable <em>Alpha Visitable</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see alpha.model.AlphaVisitable
	 * @see alpha.model.impl.ModelPackageImpl#getAlphaVisitable()
	 * @generated
	 */
	int ALPHA_VISITABLE = 2;

	/**
	 * The number of structural features of the '<em>Alpha Visitable</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ALPHA_VISITABLE_FEATURE_COUNT = ALPHA_COMPLETE_VISITABLE_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link alpha.model.AlphaExpressionVisitable <em>Alpha Expression Visitable</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see alpha.model.AlphaExpressionVisitable
	 * @see alpha.model.impl.ModelPackageImpl#getAlphaExpressionVisitable()
	 * @generated
	 */
	int ALPHA_EXPRESSION_VISITABLE = 3;

	/**
	 * The number of structural features of the '<em>Alpha Expression Visitable</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ALPHA_EXPRESSION_VISITABLE_FEATURE_COUNT = ALPHA_COMPLETE_VISITABLE_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link alpha.model.CalculatorExpressionVisitable <em>Calculator Expression Visitable</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see alpha.model.CalculatorExpressionVisitable
	 * @see alpha.model.impl.ModelPackageImpl#getCalculatorExpressionVisitable()
	 * @generated
	 */
	int CALCULATOR_EXPRESSION_VISITABLE = 4;

	/**
	 * The number of structural features of the '<em>Calculator Expression Visitable</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CALCULATOR_EXPRESSION_VISITABLE_FEATURE_COUNT = 0;

	/**
	 * The meta object id for the '{@link alpha.model.AlphaSystemElement <em>Alpha System Element</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see alpha.model.AlphaSystemElement
	 * @see alpha.model.impl.ModelPackageImpl#getAlphaSystemElement()
	 * @generated
	 */
	int ALPHA_SYSTEM_ELEMENT = 5;

	/**
	 * The number of structural features of the '<em>Alpha System Element</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ALPHA_SYSTEM_ELEMENT_FEATURE_COUNT = 0;

	/**
	 * The meta object id for the '{@link alpha.model.AlphaScheduleTarget <em>Alpha Schedule Target</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see alpha.model.AlphaScheduleTarget
	 * @see alpha.model.impl.ModelPackageImpl#getAlphaScheduleTarget()
	 * @generated
	 */
	int ALPHA_SCHEDULE_TARGET = 6;

	/**
	 * The number of structural features of the '<em>Alpha Schedule Target</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ALPHA_SCHEDULE_TARGET_FEATURE_COUNT = 0;

	/**
	 * The meta object id for the '{@link alpha.model.AlphaVisitor <em>Alpha Visitor</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see alpha.model.AlphaVisitor
	 * @see alpha.model.impl.ModelPackageImpl#getAlphaVisitor()
	 * @generated
	 */
	int ALPHA_VISITOR = 7;

	/**
	 * The number of structural features of the '<em>Alpha Visitor</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ALPHA_VISITOR_FEATURE_COUNT = 0;

	/**
	 * The meta object id for the '{@link alpha.model.AlphaExpressionVisitor <em>Alpha Expression Visitor</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see alpha.model.AlphaExpressionVisitor
	 * @see alpha.model.impl.ModelPackageImpl#getAlphaExpressionVisitor()
	 * @generated
	 */
	int ALPHA_EXPRESSION_VISITOR = 8;

	/**
	 * The number of structural features of the '<em>Alpha Expression Visitor</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ALPHA_EXPRESSION_VISITOR_FEATURE_COUNT = 0;

	/**
	 * The meta object id for the '{@link alpha.model.CalculatorExpressionVisitor <em>Calculator Expression Visitor</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see alpha.model.CalculatorExpressionVisitor
	 * @see alpha.model.impl.ModelPackageImpl#getCalculatorExpressionVisitor()
	 * @generated
	 */
	int CALCULATOR_EXPRESSION_VISITOR = 9;

	/**
	 * The number of structural features of the '<em>Calculator Expression Visitor</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CALCULATOR_EXPRESSION_VISITOR_FEATURE_COUNT = 0;

	/**
	 * The meta object id for the '{@link alpha.model.impl.AlphaRootImpl <em>Alpha Root</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see alpha.model.impl.AlphaRootImpl
	 * @see alpha.model.impl.ModelPackageImpl#getAlphaRoot()
	 * @generated
	 */
	int ALPHA_ROOT = 10;

	/**
	 * The feature id for the '<em><b>Node ID</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ALPHA_ROOT__NODE_ID = ALPHA_NODE__NODE_ID;

	/**
	 * The feature id for the '<em><b>Imports</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ALPHA_ROOT__IMPORTS = ALPHA_NODE_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Elements</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ALPHA_ROOT__ELEMENTS = ALPHA_NODE_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Alpha Root</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ALPHA_ROOT_FEATURE_COUNT = ALPHA_NODE_FEATURE_COUNT + 2;

	/**
	 * The meta object id for the '{@link alpha.model.AlphaElement <em>Alpha Element</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see alpha.model.AlphaElement
	 * @see alpha.model.impl.ModelPackageImpl#getAlphaElement()
	 * @generated
	 */
	int ALPHA_ELEMENT = 11;

	/**
	 * The feature id for the '<em><b>Node ID</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ALPHA_ELEMENT__NODE_ID = ALPHA_NODE__NODE_ID;

	/**
	 * The number of structural features of the '<em>Alpha Element</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ALPHA_ELEMENT_FEATURE_COUNT = ALPHA_NODE_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link alpha.model.impl.ImportsImpl <em>Imports</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see alpha.model.impl.ImportsImpl
	 * @see alpha.model.impl.ModelPackageImpl#getImports()
	 * @generated
	 */
	int IMPORTS = 12;

	/**
	 * The feature id for the '<em><b>Node ID</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IMPORTS__NODE_ID = ALPHA_NODE__NODE_ID;

	/**
	 * The feature id for the '<em><b>Imported Namespace</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IMPORTS__IMPORTED_NAMESPACE = ALPHA_NODE_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Imports</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IMPORTS_FEATURE_COUNT = ALPHA_NODE_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link alpha.model.impl.AlphaPackageImpl <em>Alpha Package</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see alpha.model.impl.AlphaPackageImpl
	 * @see alpha.model.impl.ModelPackageImpl#getAlphaPackage()
	 * @generated
	 */
	int ALPHA_PACKAGE = 13;

	/**
	 * The feature id for the '<em><b>Node ID</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ALPHA_PACKAGE__NODE_ID = ALPHA_ELEMENT__NODE_ID;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ALPHA_PACKAGE__NAME = ALPHA_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Elements</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ALPHA_PACKAGE__ELEMENTS = ALPHA_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Alpha Package</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ALPHA_PACKAGE_FEATURE_COUNT = ALPHA_ELEMENT_FEATURE_COUNT + 2;

	/**
	 * The meta object id for the '{@link alpha.model.impl.AlphaConstantImpl <em>Alpha Constant</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see alpha.model.impl.AlphaConstantImpl
	 * @see alpha.model.impl.ModelPackageImpl#getAlphaConstant()
	 * @generated
	 */
	int ALPHA_CONSTANT = 14;

	/**
	 * The feature id for the '<em><b>Node ID</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ALPHA_CONSTANT__NODE_ID = ALPHA_ELEMENT__NODE_ID;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ALPHA_CONSTANT__NAME = ALPHA_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ALPHA_CONSTANT__VALUE = ALPHA_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Alpha Constant</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ALPHA_CONSTANT_FEATURE_COUNT = ALPHA_ELEMENT_FEATURE_COUNT + 2;

	/**
	 * The meta object id for the '{@link alpha.model.impl.ExternalFunctionImpl <em>External Function</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see alpha.model.impl.ExternalFunctionImpl
	 * @see alpha.model.impl.ModelPackageImpl#getExternalFunction()
	 * @generated
	 */
	int EXTERNAL_FUNCTION = 15;

	/**
	 * The feature id for the '<em><b>Node ID</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXTERNAL_FUNCTION__NODE_ID = ALPHA_ELEMENT__NODE_ID;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXTERNAL_FUNCTION__NAME = ALPHA_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Cardinality</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXTERNAL_FUNCTION__CARDINALITY = ALPHA_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>External Function</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXTERNAL_FUNCTION_FEATURE_COUNT = ALPHA_ELEMENT_FEATURE_COUNT + 2;

	/**
	 * The meta object id for the '{@link alpha.model.impl.AlphaSystemImpl <em>Alpha System</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see alpha.model.impl.AlphaSystemImpl
	 * @see alpha.model.impl.ModelPackageImpl#getAlphaSystem()
	 * @generated
	 */
	int ALPHA_SYSTEM = 16;

	/**
	 * The feature id for the '<em><b>Node ID</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ALPHA_SYSTEM__NODE_ID = ALPHA_ELEMENT__NODE_ID;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ALPHA_SYSTEM__NAME = ALPHA_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Parameter Domain Expr</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ALPHA_SYSTEM__PARAMETER_DOMAIN_EXPR = ALPHA_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Defined Objects</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ALPHA_SYSTEM__DEFINED_OBJECTS = ALPHA_ELEMENT_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Inputs</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ALPHA_SYSTEM__INPUTS = ALPHA_ELEMENT_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Outputs</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ALPHA_SYSTEM__OUTPUTS = ALPHA_ELEMENT_FEATURE_COUNT + 4;

	/**
	 * The feature id for the '<em><b>Locals</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ALPHA_SYSTEM__LOCALS = ALPHA_ELEMENT_FEATURE_COUNT + 5;

	/**
	 * The feature id for the '<em><b>While Domain Expr</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ALPHA_SYSTEM__WHILE_DOMAIN_EXPR = ALPHA_ELEMENT_FEATURE_COUNT + 6;

	/**
	 * The feature id for the '<em><b>Test Expression</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ALPHA_SYSTEM__TEST_EXPRESSION = ALPHA_ELEMENT_FEATURE_COUNT + 7;

	/**
	 * The feature id for the '<em><b>System Bodies</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ALPHA_SYSTEM__SYSTEM_BODIES = ALPHA_ELEMENT_FEATURE_COUNT + 8;

	/**
	 * The number of structural features of the '<em>Alpha System</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ALPHA_SYSTEM_FEATURE_COUNT = ALPHA_ELEMENT_FEATURE_COUNT + 9;

	/**
	 * The meta object id for the '{@link alpha.model.impl.VariableImpl <em>Variable</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see alpha.model.impl.VariableImpl
	 * @see alpha.model.impl.ModelPackageImpl#getVariable()
	 * @generated
	 */
	int VARIABLE = 17;

	/**
	 * The feature id for the '<em><b>Node ID</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VARIABLE__NODE_ID = ALPHA_NODE__NODE_ID;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VARIABLE__NAME = ALPHA_NODE_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Domain Expr</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VARIABLE__DOMAIN_EXPR = ALPHA_NODE_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Variable</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VARIABLE_FEATURE_COUNT = ALPHA_NODE_FEATURE_COUNT + 2;

	/**
	 * The meta object id for the '{@link alpha.model.impl.FuzzyVariableImpl <em>Fuzzy Variable</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see alpha.model.impl.FuzzyVariableImpl
	 * @see alpha.model.impl.ModelPackageImpl#getFuzzyVariable()
	 * @generated
	 */
	int FUZZY_VARIABLE = 18;

	/**
	 * The feature id for the '<em><b>Node ID</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUZZY_VARIABLE__NODE_ID = VARIABLE__NODE_ID;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUZZY_VARIABLE__NAME = VARIABLE__NAME;

	/**
	 * The feature id for the '<em><b>Domain Expr</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUZZY_VARIABLE__DOMAIN_EXPR = VARIABLE__DOMAIN_EXPR;

	/**
	 * The feature id for the '<em><b>Range Expr</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUZZY_VARIABLE__RANGE_EXPR = VARIABLE_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Fuzzy Variable</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUZZY_VARIABLE_FEATURE_COUNT = VARIABLE_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link alpha.model.impl.SystemBodyImpl <em>System Body</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see alpha.model.impl.SystemBodyImpl
	 * @see alpha.model.impl.ModelPackageImpl#getSystemBody()
	 * @generated
	 */
	int SYSTEM_BODY = 19;

	/**
	 * The feature id for the '<em><b>Node ID</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SYSTEM_BODY__NODE_ID = ALPHA_NODE__NODE_ID;

	/**
	 * The feature id for the '<em><b>System</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SYSTEM_BODY__SYSTEM = ALPHA_NODE_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Parameter Domain Expr</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SYSTEM_BODY__PARAMETER_DOMAIN_EXPR = ALPHA_NODE_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Equations</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SYSTEM_BODY__EQUATIONS = ALPHA_NODE_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>System Body</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SYSTEM_BODY_FEATURE_COUNT = ALPHA_NODE_FEATURE_COUNT + 3;

	/**
	 * The meta object id for the '{@link alpha.model.impl.EquationImpl <em>Equation</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see alpha.model.impl.EquationImpl
	 * @see alpha.model.impl.ModelPackageImpl#getEquation()
	 * @generated
	 */
	int EQUATION = 20;

	/**
	 * The feature id for the '<em><b>Node ID</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EQUATION__NODE_ID = ALPHA_NODE__NODE_ID;

	/**
	 * The feature id for the '<em><b>System Body</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EQUATION__SYSTEM_BODY = ALPHA_NODE_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Zexplored</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EQUATION__ZEXPLORED = ALPHA_NODE_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Equation</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EQUATION_FEATURE_COUNT = ALPHA_NODE_FEATURE_COUNT + 2;

	/**
	 * The meta object id for the '{@link alpha.model.impl.StandardEquationImpl <em>Standard Equation</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see alpha.model.impl.StandardEquationImpl
	 * @see alpha.model.impl.ModelPackageImpl#getStandardEquation()
	 * @generated
	 */
	int STANDARD_EQUATION = 21;

	/**
	 * The feature id for the '<em><b>Node ID</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STANDARD_EQUATION__NODE_ID = EQUATION__NODE_ID;

	/**
	 * The feature id for the '<em><b>System Body</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STANDARD_EQUATION__SYSTEM_BODY = EQUATION__SYSTEM_BODY;

	/**
	 * The feature id for the '<em><b>Zexplored</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STANDARD_EQUATION__ZEXPLORED = EQUATION__ZEXPLORED;

	/**
	 * The feature id for the '<em><b>Variable</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STANDARD_EQUATION__VARIABLE = EQUATION_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Index Names</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STANDARD_EQUATION__INDEX_NAMES = EQUATION_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Expr</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STANDARD_EQUATION__EXPR = EQUATION_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>Standard Equation</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STANDARD_EQUATION_FEATURE_COUNT = EQUATION_FEATURE_COUNT + 3;

	/**
	 * The meta object id for the '{@link alpha.model.impl.UseEquationImpl <em>Use Equation</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see alpha.model.impl.UseEquationImpl
	 * @see alpha.model.impl.ModelPackageImpl#getUseEquation()
	 * @generated
	 */
	int USE_EQUATION = 22;

	/**
	 * The feature id for the '<em><b>Node ID</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int USE_EQUATION__NODE_ID = EQUATION__NODE_ID;

	/**
	 * The feature id for the '<em><b>System Body</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int USE_EQUATION__SYSTEM_BODY = EQUATION__SYSTEM_BODY;

	/**
	 * The feature id for the '<em><b>Zexplored</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int USE_EQUATION__ZEXPLORED = EQUATION__ZEXPLORED;

	/**
	 * The feature id for the '<em><b>Identifier</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int USE_EQUATION__IDENTIFIER = EQUATION_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Instantiation Domain Expr</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int USE_EQUATION__INSTANTIATION_DOMAIN_EXPR = EQUATION_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Subsystem Dims</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int USE_EQUATION__SUBSYSTEM_DIMS = EQUATION_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>System</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int USE_EQUATION__SYSTEM = EQUATION_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Call Params Expr</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int USE_EQUATION__CALL_PARAMS_EXPR = EQUATION_FEATURE_COUNT + 4;

	/**
	 * The feature id for the '<em><b>Input Exprs</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int USE_EQUATION__INPUT_EXPRS = EQUATION_FEATURE_COUNT + 5;

	/**
	 * The feature id for the '<em><b>Output Exprs</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int USE_EQUATION__OUTPUT_EXPRS = EQUATION_FEATURE_COUNT + 6;

	/**
	 * The number of structural features of the '<em>Use Equation</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int USE_EQUATION_FEATURE_COUNT = EQUATION_FEATURE_COUNT + 7;

	/**
	 * The meta object id for the '{@link alpha.model.impl.AlphaExpressionImpl <em>Alpha Expression</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see alpha.model.impl.AlphaExpressionImpl
	 * @see alpha.model.impl.ModelPackageImpl#getAlphaExpression()
	 * @generated
	 */
	int ALPHA_EXPRESSION = 23;

	/**
	 * The feature id for the '<em><b>Node ID</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ALPHA_EXPRESSION__NODE_ID = ALPHA_NODE__NODE_ID;

	/**
	 * The feature id for the '<em><b>Zinternal cache expr Dom</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ALPHA_EXPRESSION__ZINTERNAL_CACHE_EXPR_DOM = ALPHA_NODE_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Zinternal cache context Dom</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ALPHA_EXPRESSION__ZINTERNAL_CACHE_CONTEXT_DOM = ALPHA_NODE_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Expression ID</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ALPHA_EXPRESSION__EXPRESSION_ID = ALPHA_NODE_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>Alpha Expression</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ALPHA_EXPRESSION_FEATURE_COUNT = ALPHA_NODE_FEATURE_COUNT + 3;

	/**
	 * The meta object id for the '{@link alpha.model.impl.RestrictExpressionImpl <em>Restrict Expression</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see alpha.model.impl.RestrictExpressionImpl
	 * @see alpha.model.impl.ModelPackageImpl#getRestrictExpression()
	 * @generated
	 */
	int RESTRICT_EXPRESSION = 24;

	/**
	 * The feature id for the '<em><b>Node ID</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RESTRICT_EXPRESSION__NODE_ID = ALPHA_EXPRESSION__NODE_ID;

	/**
	 * The feature id for the '<em><b>Zinternal cache expr Dom</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RESTRICT_EXPRESSION__ZINTERNAL_CACHE_EXPR_DOM = ALPHA_EXPRESSION__ZINTERNAL_CACHE_EXPR_DOM;

	/**
	 * The feature id for the '<em><b>Zinternal cache context Dom</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RESTRICT_EXPRESSION__ZINTERNAL_CACHE_CONTEXT_DOM = ALPHA_EXPRESSION__ZINTERNAL_CACHE_CONTEXT_DOM;

	/**
	 * The feature id for the '<em><b>Expression ID</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RESTRICT_EXPRESSION__EXPRESSION_ID = ALPHA_EXPRESSION__EXPRESSION_ID;

	/**
	 * The feature id for the '<em><b>Domain Expr</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RESTRICT_EXPRESSION__DOMAIN_EXPR = ALPHA_EXPRESSION_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Expr</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RESTRICT_EXPRESSION__EXPR = ALPHA_EXPRESSION_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Restrict Expression</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RESTRICT_EXPRESSION_FEATURE_COUNT = ALPHA_EXPRESSION_FEATURE_COUNT + 2;

	/**
	 * The meta object id for the '{@link alpha.model.impl.AutoRestrictExpressionImpl <em>Auto Restrict Expression</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see alpha.model.impl.AutoRestrictExpressionImpl
	 * @see alpha.model.impl.ModelPackageImpl#getAutoRestrictExpression()
	 * @generated
	 */
	int AUTO_RESTRICT_EXPRESSION = 25;

	/**
	 * The feature id for the '<em><b>Node ID</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int AUTO_RESTRICT_EXPRESSION__NODE_ID = ALPHA_EXPRESSION__NODE_ID;

	/**
	 * The feature id for the '<em><b>Zinternal cache expr Dom</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int AUTO_RESTRICT_EXPRESSION__ZINTERNAL_CACHE_EXPR_DOM = ALPHA_EXPRESSION__ZINTERNAL_CACHE_EXPR_DOM;

	/**
	 * The feature id for the '<em><b>Zinternal cache context Dom</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int AUTO_RESTRICT_EXPRESSION__ZINTERNAL_CACHE_CONTEXT_DOM = ALPHA_EXPRESSION__ZINTERNAL_CACHE_CONTEXT_DOM;

	/**
	 * The feature id for the '<em><b>Expression ID</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int AUTO_RESTRICT_EXPRESSION__EXPRESSION_ID = ALPHA_EXPRESSION__EXPRESSION_ID;

	/**
	 * The feature id for the '<em><b>Expr</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int AUTO_RESTRICT_EXPRESSION__EXPR = ALPHA_EXPRESSION_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Zinternal cache inferred Domain</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int AUTO_RESTRICT_EXPRESSION__ZINTERNAL_CACHE_INFERRED_DOMAIN = ALPHA_EXPRESSION_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Auto Restrict Expression</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int AUTO_RESTRICT_EXPRESSION_FEATURE_COUNT = ALPHA_EXPRESSION_FEATURE_COUNT + 2;

	/**
	 * The meta object id for the '{@link alpha.model.impl.CaseExpressionImpl <em>Case Expression</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see alpha.model.impl.CaseExpressionImpl
	 * @see alpha.model.impl.ModelPackageImpl#getCaseExpression()
	 * @generated
	 */
	int CASE_EXPRESSION = 26;

	/**
	 * The feature id for the '<em><b>Node ID</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CASE_EXPRESSION__NODE_ID = ALPHA_EXPRESSION__NODE_ID;

	/**
	 * The feature id for the '<em><b>Zinternal cache expr Dom</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CASE_EXPRESSION__ZINTERNAL_CACHE_EXPR_DOM = ALPHA_EXPRESSION__ZINTERNAL_CACHE_EXPR_DOM;

	/**
	 * The feature id for the '<em><b>Zinternal cache context Dom</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CASE_EXPRESSION__ZINTERNAL_CACHE_CONTEXT_DOM = ALPHA_EXPRESSION__ZINTERNAL_CACHE_CONTEXT_DOM;

	/**
	 * The feature id for the '<em><b>Expression ID</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CASE_EXPRESSION__EXPRESSION_ID = ALPHA_EXPRESSION__EXPRESSION_ID;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CASE_EXPRESSION__NAME = ALPHA_EXPRESSION_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Exprs</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CASE_EXPRESSION__EXPRS = ALPHA_EXPRESSION_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Case Expression</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CASE_EXPRESSION_FEATURE_COUNT = ALPHA_EXPRESSION_FEATURE_COUNT + 2;

	/**
	 * The meta object id for the '{@link alpha.model.impl.DependenceExpressionImpl <em>Dependence Expression</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see alpha.model.impl.DependenceExpressionImpl
	 * @see alpha.model.impl.ModelPackageImpl#getDependenceExpression()
	 * @generated
	 */
	int DEPENDENCE_EXPRESSION = 27;

	/**
	 * The feature id for the '<em><b>Node ID</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DEPENDENCE_EXPRESSION__NODE_ID = ALPHA_EXPRESSION__NODE_ID;

	/**
	 * The feature id for the '<em><b>Zinternal cache expr Dom</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DEPENDENCE_EXPRESSION__ZINTERNAL_CACHE_EXPR_DOM = ALPHA_EXPRESSION__ZINTERNAL_CACHE_EXPR_DOM;

	/**
	 * The feature id for the '<em><b>Zinternal cache context Dom</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DEPENDENCE_EXPRESSION__ZINTERNAL_CACHE_CONTEXT_DOM = ALPHA_EXPRESSION__ZINTERNAL_CACHE_CONTEXT_DOM;

	/**
	 * The feature id for the '<em><b>Expression ID</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DEPENDENCE_EXPRESSION__EXPRESSION_ID = ALPHA_EXPRESSION__EXPRESSION_ID;

	/**
	 * The feature id for the '<em><b>Function Expr</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DEPENDENCE_EXPRESSION__FUNCTION_EXPR = ALPHA_EXPRESSION_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Expr</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DEPENDENCE_EXPRESSION__EXPR = ALPHA_EXPRESSION_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Dependence Expression</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DEPENDENCE_EXPRESSION_FEATURE_COUNT = ALPHA_EXPRESSION_FEATURE_COUNT + 2;

	/**
	 * The meta object id for the '{@link alpha.model.impl.FuzzyDependenceExpressionImpl <em>Fuzzy Dependence Expression</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see alpha.model.impl.FuzzyDependenceExpressionImpl
	 * @see alpha.model.impl.ModelPackageImpl#getFuzzyDependenceExpression()
	 * @generated
	 */
	int FUZZY_DEPENDENCE_EXPRESSION = 28;

	/**
	 * The feature id for the '<em><b>Node ID</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUZZY_DEPENDENCE_EXPRESSION__NODE_ID = ALPHA_EXPRESSION__NODE_ID;

	/**
	 * The feature id for the '<em><b>Zinternal cache expr Dom</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUZZY_DEPENDENCE_EXPRESSION__ZINTERNAL_CACHE_EXPR_DOM = ALPHA_EXPRESSION__ZINTERNAL_CACHE_EXPR_DOM;

	/**
	 * The feature id for the '<em><b>Zinternal cache context Dom</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUZZY_DEPENDENCE_EXPRESSION__ZINTERNAL_CACHE_CONTEXT_DOM = ALPHA_EXPRESSION__ZINTERNAL_CACHE_CONTEXT_DOM;

	/**
	 * The feature id for the '<em><b>Expression ID</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUZZY_DEPENDENCE_EXPRESSION__EXPRESSION_ID = ALPHA_EXPRESSION__EXPRESSION_ID;

	/**
	 * The feature id for the '<em><b>Fuzzy Function</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUZZY_DEPENDENCE_EXPRESSION__FUZZY_FUNCTION = ALPHA_EXPRESSION_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Expr</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUZZY_DEPENDENCE_EXPRESSION__EXPR = ALPHA_EXPRESSION_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Fuzzy Dependence Expression</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUZZY_DEPENDENCE_EXPRESSION_FEATURE_COUNT = ALPHA_EXPRESSION_FEATURE_COUNT + 2;

	/**
	 * The meta object id for the '{@link alpha.model.impl.IfExpressionImpl <em>If Expression</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see alpha.model.impl.IfExpressionImpl
	 * @see alpha.model.impl.ModelPackageImpl#getIfExpression()
	 * @generated
	 */
	int IF_EXPRESSION = 29;

	/**
	 * The feature id for the '<em><b>Node ID</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IF_EXPRESSION__NODE_ID = ALPHA_EXPRESSION__NODE_ID;

	/**
	 * The feature id for the '<em><b>Zinternal cache expr Dom</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IF_EXPRESSION__ZINTERNAL_CACHE_EXPR_DOM = ALPHA_EXPRESSION__ZINTERNAL_CACHE_EXPR_DOM;

	/**
	 * The feature id for the '<em><b>Zinternal cache context Dom</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IF_EXPRESSION__ZINTERNAL_CACHE_CONTEXT_DOM = ALPHA_EXPRESSION__ZINTERNAL_CACHE_CONTEXT_DOM;

	/**
	 * The feature id for the '<em><b>Expression ID</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IF_EXPRESSION__EXPRESSION_ID = ALPHA_EXPRESSION__EXPRESSION_ID;

	/**
	 * The feature id for the '<em><b>Cond Expr</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IF_EXPRESSION__COND_EXPR = ALPHA_EXPRESSION_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Then Expr</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IF_EXPRESSION__THEN_EXPR = ALPHA_EXPRESSION_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Else Expr</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IF_EXPRESSION__ELSE_EXPR = ALPHA_EXPRESSION_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>If Expression</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IF_EXPRESSION_FEATURE_COUNT = ALPHA_EXPRESSION_FEATURE_COUNT + 3;

	/**
	 * The meta object id for the '{@link alpha.model.impl.IndexExpressionImpl <em>Index Expression</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see alpha.model.impl.IndexExpressionImpl
	 * @see alpha.model.impl.ModelPackageImpl#getIndexExpression()
	 * @generated
	 */
	int INDEX_EXPRESSION = 30;

	/**
	 * The feature id for the '<em><b>Node ID</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INDEX_EXPRESSION__NODE_ID = ALPHA_EXPRESSION__NODE_ID;

	/**
	 * The feature id for the '<em><b>Zinternal cache expr Dom</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INDEX_EXPRESSION__ZINTERNAL_CACHE_EXPR_DOM = ALPHA_EXPRESSION__ZINTERNAL_CACHE_EXPR_DOM;

	/**
	 * The feature id for the '<em><b>Zinternal cache context Dom</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INDEX_EXPRESSION__ZINTERNAL_CACHE_CONTEXT_DOM = ALPHA_EXPRESSION__ZINTERNAL_CACHE_CONTEXT_DOM;

	/**
	 * The feature id for the '<em><b>Expression ID</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INDEX_EXPRESSION__EXPRESSION_ID = ALPHA_EXPRESSION__EXPRESSION_ID;

	/**
	 * The feature id for the '<em><b>Function Expr</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INDEX_EXPRESSION__FUNCTION_EXPR = ALPHA_EXPRESSION_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Index Expression</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INDEX_EXPRESSION_FEATURE_COUNT = ALPHA_EXPRESSION_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link alpha.model.impl.PolynomialIndexExpressionImpl <em>Polynomial Index Expression</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see alpha.model.impl.PolynomialIndexExpressionImpl
	 * @see alpha.model.impl.ModelPackageImpl#getPolynomialIndexExpression()
	 * @generated
	 */
	int POLYNOMIAL_INDEX_EXPRESSION = 31;

	/**
	 * The feature id for the '<em><b>Node ID</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int POLYNOMIAL_INDEX_EXPRESSION__NODE_ID = ALPHA_EXPRESSION__NODE_ID;

	/**
	 * The feature id for the '<em><b>Zinternal cache expr Dom</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int POLYNOMIAL_INDEX_EXPRESSION__ZINTERNAL_CACHE_EXPR_DOM = ALPHA_EXPRESSION__ZINTERNAL_CACHE_EXPR_DOM;

	/**
	 * The feature id for the '<em><b>Zinternal cache context Dom</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int POLYNOMIAL_INDEX_EXPRESSION__ZINTERNAL_CACHE_CONTEXT_DOM = ALPHA_EXPRESSION__ZINTERNAL_CACHE_CONTEXT_DOM;

	/**
	 * The feature id for the '<em><b>Expression ID</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int POLYNOMIAL_INDEX_EXPRESSION__EXPRESSION_ID = ALPHA_EXPRESSION__EXPRESSION_ID;

	/**
	 * The feature id for the '<em><b>Polynomial Expr</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int POLYNOMIAL_INDEX_EXPRESSION__POLYNOMIAL_EXPR = ALPHA_EXPRESSION_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Polynomial Index Expression</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int POLYNOMIAL_INDEX_EXPRESSION_FEATURE_COUNT = ALPHA_EXPRESSION_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link alpha.model.impl.FuzzyIndexExpressionImpl <em>Fuzzy Index Expression</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see alpha.model.impl.FuzzyIndexExpressionImpl
	 * @see alpha.model.impl.ModelPackageImpl#getFuzzyIndexExpression()
	 * @generated
	 */
	int FUZZY_INDEX_EXPRESSION = 32;

	/**
	 * The feature id for the '<em><b>Node ID</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUZZY_INDEX_EXPRESSION__NODE_ID = ALPHA_EXPRESSION__NODE_ID;

	/**
	 * The feature id for the '<em><b>Zinternal cache expr Dom</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUZZY_INDEX_EXPRESSION__ZINTERNAL_CACHE_EXPR_DOM = ALPHA_EXPRESSION__ZINTERNAL_CACHE_EXPR_DOM;

	/**
	 * The feature id for the '<em><b>Zinternal cache context Dom</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUZZY_INDEX_EXPRESSION__ZINTERNAL_CACHE_CONTEXT_DOM = ALPHA_EXPRESSION__ZINTERNAL_CACHE_CONTEXT_DOM;

	/**
	 * The feature id for the '<em><b>Expression ID</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUZZY_INDEX_EXPRESSION__EXPRESSION_ID = ALPHA_EXPRESSION__EXPRESSION_ID;

	/**
	 * The feature id for the '<em><b>Fuzzy Function</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUZZY_INDEX_EXPRESSION__FUZZY_FUNCTION = ALPHA_EXPRESSION_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Fuzzy Index Expression</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUZZY_INDEX_EXPRESSION_FEATURE_COUNT = ALPHA_EXPRESSION_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link alpha.model.impl.AbstractReduceExpressionImpl <em>Abstract Reduce Expression</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see alpha.model.impl.AbstractReduceExpressionImpl
	 * @see alpha.model.impl.ModelPackageImpl#getAbstractReduceExpression()
	 * @generated
	 */
	int ABSTRACT_REDUCE_EXPRESSION = 33;

	/**
	 * The feature id for the '<em><b>Node ID</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABSTRACT_REDUCE_EXPRESSION__NODE_ID = ALPHA_EXPRESSION__NODE_ID;

	/**
	 * The feature id for the '<em><b>Zinternal cache expr Dom</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABSTRACT_REDUCE_EXPRESSION__ZINTERNAL_CACHE_EXPR_DOM = ALPHA_EXPRESSION__ZINTERNAL_CACHE_EXPR_DOM;

	/**
	 * The feature id for the '<em><b>Zinternal cache context Dom</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABSTRACT_REDUCE_EXPRESSION__ZINTERNAL_CACHE_CONTEXT_DOM = ALPHA_EXPRESSION__ZINTERNAL_CACHE_CONTEXT_DOM;

	/**
	 * The feature id for the '<em><b>Expression ID</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABSTRACT_REDUCE_EXPRESSION__EXPRESSION_ID = ALPHA_EXPRESSION__EXPRESSION_ID;

	/**
	 * The feature id for the '<em><b>Operator</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABSTRACT_REDUCE_EXPRESSION__OPERATOR = ALPHA_EXPRESSION_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Projection Expr</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABSTRACT_REDUCE_EXPRESSION__PROJECTION_EXPR = ALPHA_EXPRESSION_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Body</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABSTRACT_REDUCE_EXPRESSION__BODY = ALPHA_EXPRESSION_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Zinternal facet</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABSTRACT_REDUCE_EXPRESSION__ZINTERNAL_FACET = ALPHA_EXPRESSION_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Nb Free Dimensions In Body</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABSTRACT_REDUCE_EXPRESSION__NB_FREE_DIMENSIONS_IN_BODY = ALPHA_EXPRESSION_FEATURE_COUNT + 4;

	/**
	 * The feature id for the '<em><b>Zfractal Simplify</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABSTRACT_REDUCE_EXPRESSION__ZFRACTAL_SIMPLIFY = ALPHA_EXPRESSION_FEATURE_COUNT + 5;

	/**
	 * The number of structural features of the '<em>Abstract Reduce Expression</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABSTRACT_REDUCE_EXPRESSION_FEATURE_COUNT = ALPHA_EXPRESSION_FEATURE_COUNT + 6;

	/**
	 * The meta object id for the '{@link alpha.model.impl.ReduceExpressionImpl <em>Reduce Expression</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see alpha.model.impl.ReduceExpressionImpl
	 * @see alpha.model.impl.ModelPackageImpl#getReduceExpression()
	 * @generated
	 */
	int REDUCE_EXPRESSION = 34;

	/**
	 * The feature id for the '<em><b>Node ID</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REDUCE_EXPRESSION__NODE_ID = ABSTRACT_REDUCE_EXPRESSION__NODE_ID;

	/**
	 * The feature id for the '<em><b>Zinternal cache expr Dom</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REDUCE_EXPRESSION__ZINTERNAL_CACHE_EXPR_DOM = ABSTRACT_REDUCE_EXPRESSION__ZINTERNAL_CACHE_EXPR_DOM;

	/**
	 * The feature id for the '<em><b>Zinternal cache context Dom</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REDUCE_EXPRESSION__ZINTERNAL_CACHE_CONTEXT_DOM = ABSTRACT_REDUCE_EXPRESSION__ZINTERNAL_CACHE_CONTEXT_DOM;

	/**
	 * The feature id for the '<em><b>Expression ID</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REDUCE_EXPRESSION__EXPRESSION_ID = ABSTRACT_REDUCE_EXPRESSION__EXPRESSION_ID;

	/**
	 * The feature id for the '<em><b>Operator</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REDUCE_EXPRESSION__OPERATOR = ABSTRACT_REDUCE_EXPRESSION__OPERATOR;

	/**
	 * The feature id for the '<em><b>Projection Expr</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REDUCE_EXPRESSION__PROJECTION_EXPR = ABSTRACT_REDUCE_EXPRESSION__PROJECTION_EXPR;

	/**
	 * The feature id for the '<em><b>Body</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REDUCE_EXPRESSION__BODY = ABSTRACT_REDUCE_EXPRESSION__BODY;

	/**
	 * The feature id for the '<em><b>Zinternal facet</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REDUCE_EXPRESSION__ZINTERNAL_FACET = ABSTRACT_REDUCE_EXPRESSION__ZINTERNAL_FACET;

	/**
	 * The feature id for the '<em><b>Nb Free Dimensions In Body</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REDUCE_EXPRESSION__NB_FREE_DIMENSIONS_IN_BODY = ABSTRACT_REDUCE_EXPRESSION__NB_FREE_DIMENSIONS_IN_BODY;

	/**
	 * The feature id for the '<em><b>Zfractal Simplify</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REDUCE_EXPRESSION__ZFRACTAL_SIMPLIFY = ABSTRACT_REDUCE_EXPRESSION__ZFRACTAL_SIMPLIFY;

	/**
	 * The number of structural features of the '<em>Reduce Expression</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REDUCE_EXPRESSION_FEATURE_COUNT = ABSTRACT_REDUCE_EXPRESSION_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link alpha.model.impl.ExternalReduceExpressionImpl <em>External Reduce Expression</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see alpha.model.impl.ExternalReduceExpressionImpl
	 * @see alpha.model.impl.ModelPackageImpl#getExternalReduceExpression()
	 * @generated
	 */
	int EXTERNAL_REDUCE_EXPRESSION = 35;

	/**
	 * The feature id for the '<em><b>Node ID</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXTERNAL_REDUCE_EXPRESSION__NODE_ID = REDUCE_EXPRESSION__NODE_ID;

	/**
	 * The feature id for the '<em><b>Zinternal cache expr Dom</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXTERNAL_REDUCE_EXPRESSION__ZINTERNAL_CACHE_EXPR_DOM = REDUCE_EXPRESSION__ZINTERNAL_CACHE_EXPR_DOM;

	/**
	 * The feature id for the '<em><b>Zinternal cache context Dom</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXTERNAL_REDUCE_EXPRESSION__ZINTERNAL_CACHE_CONTEXT_DOM = REDUCE_EXPRESSION__ZINTERNAL_CACHE_CONTEXT_DOM;

	/**
	 * The feature id for the '<em><b>Expression ID</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXTERNAL_REDUCE_EXPRESSION__EXPRESSION_ID = REDUCE_EXPRESSION__EXPRESSION_ID;

	/**
	 * The feature id for the '<em><b>Operator</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXTERNAL_REDUCE_EXPRESSION__OPERATOR = REDUCE_EXPRESSION__OPERATOR;

	/**
	 * The feature id for the '<em><b>Projection Expr</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXTERNAL_REDUCE_EXPRESSION__PROJECTION_EXPR = REDUCE_EXPRESSION__PROJECTION_EXPR;

	/**
	 * The feature id for the '<em><b>Body</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXTERNAL_REDUCE_EXPRESSION__BODY = REDUCE_EXPRESSION__BODY;

	/**
	 * The feature id for the '<em><b>Zinternal facet</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXTERNAL_REDUCE_EXPRESSION__ZINTERNAL_FACET = REDUCE_EXPRESSION__ZINTERNAL_FACET;

	/**
	 * The feature id for the '<em><b>Nb Free Dimensions In Body</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXTERNAL_REDUCE_EXPRESSION__NB_FREE_DIMENSIONS_IN_BODY = REDUCE_EXPRESSION__NB_FREE_DIMENSIONS_IN_BODY;

	/**
	 * The feature id for the '<em><b>Zfractal Simplify</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXTERNAL_REDUCE_EXPRESSION__ZFRACTAL_SIMPLIFY = REDUCE_EXPRESSION__ZFRACTAL_SIMPLIFY;

	/**
	 * The feature id for the '<em><b>External Function</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXTERNAL_REDUCE_EXPRESSION__EXTERNAL_FUNCTION = REDUCE_EXPRESSION_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>External Reduce Expression</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXTERNAL_REDUCE_EXPRESSION_FEATURE_COUNT = REDUCE_EXPRESSION_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link alpha.model.impl.ArgReduceExpressionImpl <em>Arg Reduce Expression</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see alpha.model.impl.ArgReduceExpressionImpl
	 * @see alpha.model.impl.ModelPackageImpl#getArgReduceExpression()
	 * @generated
	 */
	int ARG_REDUCE_EXPRESSION = 36;

	/**
	 * The feature id for the '<em><b>Node ID</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ARG_REDUCE_EXPRESSION__NODE_ID = ABSTRACT_REDUCE_EXPRESSION__NODE_ID;

	/**
	 * The feature id for the '<em><b>Zinternal cache expr Dom</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ARG_REDUCE_EXPRESSION__ZINTERNAL_CACHE_EXPR_DOM = ABSTRACT_REDUCE_EXPRESSION__ZINTERNAL_CACHE_EXPR_DOM;

	/**
	 * The feature id for the '<em><b>Zinternal cache context Dom</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ARG_REDUCE_EXPRESSION__ZINTERNAL_CACHE_CONTEXT_DOM = ABSTRACT_REDUCE_EXPRESSION__ZINTERNAL_CACHE_CONTEXT_DOM;

	/**
	 * The feature id for the '<em><b>Expression ID</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ARG_REDUCE_EXPRESSION__EXPRESSION_ID = ABSTRACT_REDUCE_EXPRESSION__EXPRESSION_ID;

	/**
	 * The feature id for the '<em><b>Operator</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ARG_REDUCE_EXPRESSION__OPERATOR = ABSTRACT_REDUCE_EXPRESSION__OPERATOR;

	/**
	 * The feature id for the '<em><b>Projection Expr</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ARG_REDUCE_EXPRESSION__PROJECTION_EXPR = ABSTRACT_REDUCE_EXPRESSION__PROJECTION_EXPR;

	/**
	 * The feature id for the '<em><b>Body</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ARG_REDUCE_EXPRESSION__BODY = ABSTRACT_REDUCE_EXPRESSION__BODY;

	/**
	 * The feature id for the '<em><b>Zinternal facet</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ARG_REDUCE_EXPRESSION__ZINTERNAL_FACET = ABSTRACT_REDUCE_EXPRESSION__ZINTERNAL_FACET;

	/**
	 * The feature id for the '<em><b>Nb Free Dimensions In Body</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ARG_REDUCE_EXPRESSION__NB_FREE_DIMENSIONS_IN_BODY = ABSTRACT_REDUCE_EXPRESSION__NB_FREE_DIMENSIONS_IN_BODY;

	/**
	 * The feature id for the '<em><b>Zfractal Simplify</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ARG_REDUCE_EXPRESSION__ZFRACTAL_SIMPLIFY = ABSTRACT_REDUCE_EXPRESSION__ZFRACTAL_SIMPLIFY;

	/**
	 * The number of structural features of the '<em>Arg Reduce Expression</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ARG_REDUCE_EXPRESSION_FEATURE_COUNT = ABSTRACT_REDUCE_EXPRESSION_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link alpha.model.impl.ExternalArgReduceExpressionImpl <em>External Arg Reduce Expression</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see alpha.model.impl.ExternalArgReduceExpressionImpl
	 * @see alpha.model.impl.ModelPackageImpl#getExternalArgReduceExpression()
	 * @generated
	 */
	int EXTERNAL_ARG_REDUCE_EXPRESSION = 37;

	/**
	 * The feature id for the '<em><b>Node ID</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXTERNAL_ARG_REDUCE_EXPRESSION__NODE_ID = ARG_REDUCE_EXPRESSION__NODE_ID;

	/**
	 * The feature id for the '<em><b>Zinternal cache expr Dom</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXTERNAL_ARG_REDUCE_EXPRESSION__ZINTERNAL_CACHE_EXPR_DOM = ARG_REDUCE_EXPRESSION__ZINTERNAL_CACHE_EXPR_DOM;

	/**
	 * The feature id for the '<em><b>Zinternal cache context Dom</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXTERNAL_ARG_REDUCE_EXPRESSION__ZINTERNAL_CACHE_CONTEXT_DOM = ARG_REDUCE_EXPRESSION__ZINTERNAL_CACHE_CONTEXT_DOM;

	/**
	 * The feature id for the '<em><b>Expression ID</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXTERNAL_ARG_REDUCE_EXPRESSION__EXPRESSION_ID = ARG_REDUCE_EXPRESSION__EXPRESSION_ID;

	/**
	 * The feature id for the '<em><b>Operator</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXTERNAL_ARG_REDUCE_EXPRESSION__OPERATOR = ARG_REDUCE_EXPRESSION__OPERATOR;

	/**
	 * The feature id for the '<em><b>Projection Expr</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXTERNAL_ARG_REDUCE_EXPRESSION__PROJECTION_EXPR = ARG_REDUCE_EXPRESSION__PROJECTION_EXPR;

	/**
	 * The feature id for the '<em><b>Body</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXTERNAL_ARG_REDUCE_EXPRESSION__BODY = ARG_REDUCE_EXPRESSION__BODY;

	/**
	 * The feature id for the '<em><b>Zinternal facet</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXTERNAL_ARG_REDUCE_EXPRESSION__ZINTERNAL_FACET = ARG_REDUCE_EXPRESSION__ZINTERNAL_FACET;

	/**
	 * The feature id for the '<em><b>Nb Free Dimensions In Body</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXTERNAL_ARG_REDUCE_EXPRESSION__NB_FREE_DIMENSIONS_IN_BODY = ARG_REDUCE_EXPRESSION__NB_FREE_DIMENSIONS_IN_BODY;

	/**
	 * The feature id for the '<em><b>Zfractal Simplify</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXTERNAL_ARG_REDUCE_EXPRESSION__ZFRACTAL_SIMPLIFY = ARG_REDUCE_EXPRESSION__ZFRACTAL_SIMPLIFY;

	/**
	 * The feature id for the '<em><b>External Function</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXTERNAL_ARG_REDUCE_EXPRESSION__EXTERNAL_FUNCTION = ARG_REDUCE_EXPRESSION_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>External Arg Reduce Expression</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXTERNAL_ARG_REDUCE_EXPRESSION_FEATURE_COUNT = ARG_REDUCE_EXPRESSION_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link alpha.model.impl.ConvolutionExpressionImpl <em>Convolution Expression</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see alpha.model.impl.ConvolutionExpressionImpl
	 * @see alpha.model.impl.ModelPackageImpl#getConvolutionExpression()
	 * @generated
	 */
	int CONVOLUTION_EXPRESSION = 38;

	/**
	 * The feature id for the '<em><b>Node ID</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONVOLUTION_EXPRESSION__NODE_ID = ALPHA_EXPRESSION__NODE_ID;

	/**
	 * The feature id for the '<em><b>Zinternal cache expr Dom</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONVOLUTION_EXPRESSION__ZINTERNAL_CACHE_EXPR_DOM = ALPHA_EXPRESSION__ZINTERNAL_CACHE_EXPR_DOM;

	/**
	 * The feature id for the '<em><b>Zinternal cache context Dom</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONVOLUTION_EXPRESSION__ZINTERNAL_CACHE_CONTEXT_DOM = ALPHA_EXPRESSION__ZINTERNAL_CACHE_CONTEXT_DOM;

	/**
	 * The feature id for the '<em><b>Expression ID</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONVOLUTION_EXPRESSION__EXPRESSION_ID = ALPHA_EXPRESSION__EXPRESSION_ID;

	/**
	 * The feature id for the '<em><b>Kernel Domain Expr</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONVOLUTION_EXPRESSION__KERNEL_DOMAIN_EXPR = ALPHA_EXPRESSION_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Kernel Expression</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONVOLUTION_EXPRESSION__KERNEL_EXPRESSION = ALPHA_EXPRESSION_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Data Expression</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONVOLUTION_EXPRESSION__DATA_EXPRESSION = ALPHA_EXPRESSION_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>Convolution Expression</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONVOLUTION_EXPRESSION_FEATURE_COUNT = ALPHA_EXPRESSION_FEATURE_COUNT + 3;

	/**
	 * The meta object id for the '{@link alpha.model.impl.SelectExpressionImpl <em>Select Expression</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see alpha.model.impl.SelectExpressionImpl
	 * @see alpha.model.impl.ModelPackageImpl#getSelectExpression()
	 * @generated
	 */
	int SELECT_EXPRESSION = 39;

	/**
	 * The feature id for the '<em><b>Node ID</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SELECT_EXPRESSION__NODE_ID = ALPHA_EXPRESSION__NODE_ID;

	/**
	 * The feature id for the '<em><b>Zinternal cache expr Dom</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SELECT_EXPRESSION__ZINTERNAL_CACHE_EXPR_DOM = ALPHA_EXPRESSION__ZINTERNAL_CACHE_EXPR_DOM;

	/**
	 * The feature id for the '<em><b>Zinternal cache context Dom</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SELECT_EXPRESSION__ZINTERNAL_CACHE_CONTEXT_DOM = ALPHA_EXPRESSION__ZINTERNAL_CACHE_CONTEXT_DOM;

	/**
	 * The feature id for the '<em><b>Expression ID</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SELECT_EXPRESSION__EXPRESSION_ID = ALPHA_EXPRESSION__EXPRESSION_ID;

	/**
	 * The feature id for the '<em><b>Relation Expr</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SELECT_EXPRESSION__RELATION_EXPR = ALPHA_EXPRESSION_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Expr</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SELECT_EXPRESSION__EXPR = ALPHA_EXPRESSION_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Select Expression</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SELECT_EXPRESSION_FEATURE_COUNT = ALPHA_EXPRESSION_FEATURE_COUNT + 2;

	/**
	 * The meta object id for the '{@link alpha.model.impl.VariableExpressionImpl <em>Variable Expression</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see alpha.model.impl.VariableExpressionImpl
	 * @see alpha.model.impl.ModelPackageImpl#getVariableExpression()
	 * @generated
	 */
	int VARIABLE_EXPRESSION = 40;

	/**
	 * The feature id for the '<em><b>Node ID</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VARIABLE_EXPRESSION__NODE_ID = ALPHA_EXPRESSION__NODE_ID;

	/**
	 * The feature id for the '<em><b>Zinternal cache expr Dom</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VARIABLE_EXPRESSION__ZINTERNAL_CACHE_EXPR_DOM = ALPHA_EXPRESSION__ZINTERNAL_CACHE_EXPR_DOM;

	/**
	 * The feature id for the '<em><b>Zinternal cache context Dom</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VARIABLE_EXPRESSION__ZINTERNAL_CACHE_CONTEXT_DOM = ALPHA_EXPRESSION__ZINTERNAL_CACHE_CONTEXT_DOM;

	/**
	 * The feature id for the '<em><b>Expression ID</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VARIABLE_EXPRESSION__EXPRESSION_ID = ALPHA_EXPRESSION__EXPRESSION_ID;

	/**
	 * The feature id for the '<em><b>Variable</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VARIABLE_EXPRESSION__VARIABLE = ALPHA_EXPRESSION_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Variable Expression</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VARIABLE_EXPRESSION_FEATURE_COUNT = ALPHA_EXPRESSION_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link alpha.model.ConstantExpression <em>Constant Expression</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see alpha.model.ConstantExpression
	 * @see alpha.model.impl.ModelPackageImpl#getConstantExpression()
	 * @generated
	 */
	int CONSTANT_EXPRESSION = 41;

	/**
	 * The feature id for the '<em><b>Node ID</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONSTANT_EXPRESSION__NODE_ID = ALPHA_EXPRESSION__NODE_ID;

	/**
	 * The feature id for the '<em><b>Zinternal cache expr Dom</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONSTANT_EXPRESSION__ZINTERNAL_CACHE_EXPR_DOM = ALPHA_EXPRESSION__ZINTERNAL_CACHE_EXPR_DOM;

	/**
	 * The feature id for the '<em><b>Zinternal cache context Dom</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONSTANT_EXPRESSION__ZINTERNAL_CACHE_CONTEXT_DOM = ALPHA_EXPRESSION__ZINTERNAL_CACHE_CONTEXT_DOM;

	/**
	 * The feature id for the '<em><b>Expression ID</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONSTANT_EXPRESSION__EXPRESSION_ID = ALPHA_EXPRESSION__EXPRESSION_ID;

	/**
	 * The number of structural features of the '<em>Constant Expression</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONSTANT_EXPRESSION_FEATURE_COUNT = ALPHA_EXPRESSION_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link alpha.model.impl.IntegerExpressionImpl <em>Integer Expression</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see alpha.model.impl.IntegerExpressionImpl
	 * @see alpha.model.impl.ModelPackageImpl#getIntegerExpression()
	 * @generated
	 */
	int INTEGER_EXPRESSION = 42;

	/**
	 * The feature id for the '<em><b>Node ID</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INTEGER_EXPRESSION__NODE_ID = CONSTANT_EXPRESSION__NODE_ID;

	/**
	 * The feature id for the '<em><b>Zinternal cache expr Dom</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INTEGER_EXPRESSION__ZINTERNAL_CACHE_EXPR_DOM = CONSTANT_EXPRESSION__ZINTERNAL_CACHE_EXPR_DOM;

	/**
	 * The feature id for the '<em><b>Zinternal cache context Dom</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INTEGER_EXPRESSION__ZINTERNAL_CACHE_CONTEXT_DOM = CONSTANT_EXPRESSION__ZINTERNAL_CACHE_CONTEXT_DOM;

	/**
	 * The feature id for the '<em><b>Expression ID</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INTEGER_EXPRESSION__EXPRESSION_ID = CONSTANT_EXPRESSION__EXPRESSION_ID;

	/**
	 * The feature id for the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INTEGER_EXPRESSION__VALUE = CONSTANT_EXPRESSION_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Integer Expression</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INTEGER_EXPRESSION_FEATURE_COUNT = CONSTANT_EXPRESSION_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link alpha.model.impl.RealExpressionImpl <em>Real Expression</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see alpha.model.impl.RealExpressionImpl
	 * @see alpha.model.impl.ModelPackageImpl#getRealExpression()
	 * @generated
	 */
	int REAL_EXPRESSION = 43;

	/**
	 * The feature id for the '<em><b>Node ID</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REAL_EXPRESSION__NODE_ID = CONSTANT_EXPRESSION__NODE_ID;

	/**
	 * The feature id for the '<em><b>Zinternal cache expr Dom</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REAL_EXPRESSION__ZINTERNAL_CACHE_EXPR_DOM = CONSTANT_EXPRESSION__ZINTERNAL_CACHE_EXPR_DOM;

	/**
	 * The feature id for the '<em><b>Zinternal cache context Dom</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REAL_EXPRESSION__ZINTERNAL_CACHE_CONTEXT_DOM = CONSTANT_EXPRESSION__ZINTERNAL_CACHE_CONTEXT_DOM;

	/**
	 * The feature id for the '<em><b>Expression ID</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REAL_EXPRESSION__EXPRESSION_ID = CONSTANT_EXPRESSION__EXPRESSION_ID;

	/**
	 * The feature id for the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REAL_EXPRESSION__VALUE = CONSTANT_EXPRESSION_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Real Expression</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REAL_EXPRESSION_FEATURE_COUNT = CONSTANT_EXPRESSION_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link alpha.model.impl.BooleanExpressionImpl <em>Boolean Expression</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see alpha.model.impl.BooleanExpressionImpl
	 * @see alpha.model.impl.ModelPackageImpl#getBooleanExpression()
	 * @generated
	 */
	int BOOLEAN_EXPRESSION = 44;

	/**
	 * The feature id for the '<em><b>Node ID</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BOOLEAN_EXPRESSION__NODE_ID = CONSTANT_EXPRESSION__NODE_ID;

	/**
	 * The feature id for the '<em><b>Zinternal cache expr Dom</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BOOLEAN_EXPRESSION__ZINTERNAL_CACHE_EXPR_DOM = CONSTANT_EXPRESSION__ZINTERNAL_CACHE_EXPR_DOM;

	/**
	 * The feature id for the '<em><b>Zinternal cache context Dom</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BOOLEAN_EXPRESSION__ZINTERNAL_CACHE_CONTEXT_DOM = CONSTANT_EXPRESSION__ZINTERNAL_CACHE_CONTEXT_DOM;

	/**
	 * The feature id for the '<em><b>Expression ID</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BOOLEAN_EXPRESSION__EXPRESSION_ID = CONSTANT_EXPRESSION__EXPRESSION_ID;

	/**
	 * The feature id for the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BOOLEAN_EXPRESSION__VALUE = CONSTANT_EXPRESSION_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Boolean Expression</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BOOLEAN_EXPRESSION_FEATURE_COUNT = CONSTANT_EXPRESSION_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link alpha.model.impl.UnaryExpressionImpl <em>Unary Expression</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see alpha.model.impl.UnaryExpressionImpl
	 * @see alpha.model.impl.ModelPackageImpl#getUnaryExpression()
	 * @generated
	 */
	int UNARY_EXPRESSION = 45;

	/**
	 * The feature id for the '<em><b>Node ID</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int UNARY_EXPRESSION__NODE_ID = ALPHA_EXPRESSION__NODE_ID;

	/**
	 * The feature id for the '<em><b>Zinternal cache expr Dom</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int UNARY_EXPRESSION__ZINTERNAL_CACHE_EXPR_DOM = ALPHA_EXPRESSION__ZINTERNAL_CACHE_EXPR_DOM;

	/**
	 * The feature id for the '<em><b>Zinternal cache context Dom</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int UNARY_EXPRESSION__ZINTERNAL_CACHE_CONTEXT_DOM = ALPHA_EXPRESSION__ZINTERNAL_CACHE_CONTEXT_DOM;

	/**
	 * The feature id for the '<em><b>Expression ID</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int UNARY_EXPRESSION__EXPRESSION_ID = ALPHA_EXPRESSION__EXPRESSION_ID;

	/**
	 * The feature id for the '<em><b>Operator</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int UNARY_EXPRESSION__OPERATOR = ALPHA_EXPRESSION_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Expr</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int UNARY_EXPRESSION__EXPR = ALPHA_EXPRESSION_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Unary Expression</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int UNARY_EXPRESSION_FEATURE_COUNT = ALPHA_EXPRESSION_FEATURE_COUNT + 2;

	/**
	 * The meta object id for the '{@link alpha.model.impl.BinaryExpressionImpl <em>Binary Expression</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see alpha.model.impl.BinaryExpressionImpl
	 * @see alpha.model.impl.ModelPackageImpl#getBinaryExpression()
	 * @generated
	 */
	int BINARY_EXPRESSION = 46;

	/**
	 * The feature id for the '<em><b>Node ID</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BINARY_EXPRESSION__NODE_ID = ALPHA_EXPRESSION__NODE_ID;

	/**
	 * The feature id for the '<em><b>Zinternal cache expr Dom</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BINARY_EXPRESSION__ZINTERNAL_CACHE_EXPR_DOM = ALPHA_EXPRESSION__ZINTERNAL_CACHE_EXPR_DOM;

	/**
	 * The feature id for the '<em><b>Zinternal cache context Dom</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BINARY_EXPRESSION__ZINTERNAL_CACHE_CONTEXT_DOM = ALPHA_EXPRESSION__ZINTERNAL_CACHE_CONTEXT_DOM;

	/**
	 * The feature id for the '<em><b>Expression ID</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BINARY_EXPRESSION__EXPRESSION_ID = ALPHA_EXPRESSION__EXPRESSION_ID;

	/**
	 * The feature id for the '<em><b>Operator</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BINARY_EXPRESSION__OPERATOR = ALPHA_EXPRESSION_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Left</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BINARY_EXPRESSION__LEFT = ALPHA_EXPRESSION_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Right</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BINARY_EXPRESSION__RIGHT = ALPHA_EXPRESSION_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>Binary Expression</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BINARY_EXPRESSION_FEATURE_COUNT = ALPHA_EXPRESSION_FEATURE_COUNT + 3;

	/**
	 * The meta object id for the '{@link alpha.model.impl.MultiArgExpressionImpl <em>Multi Arg Expression</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see alpha.model.impl.MultiArgExpressionImpl
	 * @see alpha.model.impl.ModelPackageImpl#getMultiArgExpression()
	 * @generated
	 */
	int MULTI_ARG_EXPRESSION = 47;

	/**
	 * The feature id for the '<em><b>Node ID</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MULTI_ARG_EXPRESSION__NODE_ID = ALPHA_EXPRESSION__NODE_ID;

	/**
	 * The feature id for the '<em><b>Zinternal cache expr Dom</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MULTI_ARG_EXPRESSION__ZINTERNAL_CACHE_EXPR_DOM = ALPHA_EXPRESSION__ZINTERNAL_CACHE_EXPR_DOM;

	/**
	 * The feature id for the '<em><b>Zinternal cache context Dom</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MULTI_ARG_EXPRESSION__ZINTERNAL_CACHE_CONTEXT_DOM = ALPHA_EXPRESSION__ZINTERNAL_CACHE_CONTEXT_DOM;

	/**
	 * The feature id for the '<em><b>Expression ID</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MULTI_ARG_EXPRESSION__EXPRESSION_ID = ALPHA_EXPRESSION__EXPRESSION_ID;

	/**
	 * The feature id for the '<em><b>Operator</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MULTI_ARG_EXPRESSION__OPERATOR = ALPHA_EXPRESSION_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Exprs</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MULTI_ARG_EXPRESSION__EXPRS = ALPHA_EXPRESSION_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Multi Arg Expression</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MULTI_ARG_EXPRESSION_FEATURE_COUNT = ALPHA_EXPRESSION_FEATURE_COUNT + 2;

	/**
	 * The meta object id for the '{@link alpha.model.impl.ExternalMultiArgExpressionImpl <em>External Multi Arg Expression</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see alpha.model.impl.ExternalMultiArgExpressionImpl
	 * @see alpha.model.impl.ModelPackageImpl#getExternalMultiArgExpression()
	 * @generated
	 */
	int EXTERNAL_MULTI_ARG_EXPRESSION = 48;

	/**
	 * The feature id for the '<em><b>Node ID</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXTERNAL_MULTI_ARG_EXPRESSION__NODE_ID = MULTI_ARG_EXPRESSION__NODE_ID;

	/**
	 * The feature id for the '<em><b>Zinternal cache expr Dom</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXTERNAL_MULTI_ARG_EXPRESSION__ZINTERNAL_CACHE_EXPR_DOM = MULTI_ARG_EXPRESSION__ZINTERNAL_CACHE_EXPR_DOM;

	/**
	 * The feature id for the '<em><b>Zinternal cache context Dom</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXTERNAL_MULTI_ARG_EXPRESSION__ZINTERNAL_CACHE_CONTEXT_DOM = MULTI_ARG_EXPRESSION__ZINTERNAL_CACHE_CONTEXT_DOM;

	/**
	 * The feature id for the '<em><b>Expression ID</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXTERNAL_MULTI_ARG_EXPRESSION__EXPRESSION_ID = MULTI_ARG_EXPRESSION__EXPRESSION_ID;

	/**
	 * The feature id for the '<em><b>Operator</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXTERNAL_MULTI_ARG_EXPRESSION__OPERATOR = MULTI_ARG_EXPRESSION__OPERATOR;

	/**
	 * The feature id for the '<em><b>Exprs</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXTERNAL_MULTI_ARG_EXPRESSION__EXPRS = MULTI_ARG_EXPRESSION__EXPRS;

	/**
	 * The feature id for the '<em><b>External Function</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXTERNAL_MULTI_ARG_EXPRESSION__EXTERNAL_FUNCTION = MULTI_ARG_EXPRESSION_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>External Multi Arg Expression</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXTERNAL_MULTI_ARG_EXPRESSION_FEATURE_COUNT = MULTI_ARG_EXPRESSION_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link alpha.model.impl.AbstractFuzzyReduceExpressionImpl <em>Abstract Fuzzy Reduce Expression</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see alpha.model.impl.AbstractFuzzyReduceExpressionImpl
	 * @see alpha.model.impl.ModelPackageImpl#getAbstractFuzzyReduceExpression()
	 * @generated
	 */
	int ABSTRACT_FUZZY_REDUCE_EXPRESSION = 49;

	/**
	 * The feature id for the '<em><b>Node ID</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABSTRACT_FUZZY_REDUCE_EXPRESSION__NODE_ID = ALPHA_EXPRESSION__NODE_ID;

	/**
	 * The feature id for the '<em><b>Zinternal cache expr Dom</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABSTRACT_FUZZY_REDUCE_EXPRESSION__ZINTERNAL_CACHE_EXPR_DOM = ALPHA_EXPRESSION__ZINTERNAL_CACHE_EXPR_DOM;

	/**
	 * The feature id for the '<em><b>Zinternal cache context Dom</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABSTRACT_FUZZY_REDUCE_EXPRESSION__ZINTERNAL_CACHE_CONTEXT_DOM = ALPHA_EXPRESSION__ZINTERNAL_CACHE_CONTEXT_DOM;

	/**
	 * The feature id for the '<em><b>Expression ID</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABSTRACT_FUZZY_REDUCE_EXPRESSION__EXPRESSION_ID = ALPHA_EXPRESSION__EXPRESSION_ID;

	/**
	 * The feature id for the '<em><b>Operator</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABSTRACT_FUZZY_REDUCE_EXPRESSION__OPERATOR = ALPHA_EXPRESSION_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Projection Function</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABSTRACT_FUZZY_REDUCE_EXPRESSION__PROJECTION_FUNCTION = ALPHA_EXPRESSION_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Body</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABSTRACT_FUZZY_REDUCE_EXPRESSION__BODY = ALPHA_EXPRESSION_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>Abstract Fuzzy Reduce Expression</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABSTRACT_FUZZY_REDUCE_EXPRESSION_FEATURE_COUNT = ALPHA_EXPRESSION_FEATURE_COUNT + 3;

	/**
	 * The meta object id for the '{@link alpha.model.impl.FuzzyReduceExpressionImpl <em>Fuzzy Reduce Expression</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see alpha.model.impl.FuzzyReduceExpressionImpl
	 * @see alpha.model.impl.ModelPackageImpl#getFuzzyReduceExpression()
	 * @generated
	 */
	int FUZZY_REDUCE_EXPRESSION = 50;

	/**
	 * The feature id for the '<em><b>Node ID</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUZZY_REDUCE_EXPRESSION__NODE_ID = ABSTRACT_FUZZY_REDUCE_EXPRESSION__NODE_ID;

	/**
	 * The feature id for the '<em><b>Zinternal cache expr Dom</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUZZY_REDUCE_EXPRESSION__ZINTERNAL_CACHE_EXPR_DOM = ABSTRACT_FUZZY_REDUCE_EXPRESSION__ZINTERNAL_CACHE_EXPR_DOM;

	/**
	 * The feature id for the '<em><b>Zinternal cache context Dom</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUZZY_REDUCE_EXPRESSION__ZINTERNAL_CACHE_CONTEXT_DOM = ABSTRACT_FUZZY_REDUCE_EXPRESSION__ZINTERNAL_CACHE_CONTEXT_DOM;

	/**
	 * The feature id for the '<em><b>Expression ID</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUZZY_REDUCE_EXPRESSION__EXPRESSION_ID = ABSTRACT_FUZZY_REDUCE_EXPRESSION__EXPRESSION_ID;

	/**
	 * The feature id for the '<em><b>Operator</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUZZY_REDUCE_EXPRESSION__OPERATOR = ABSTRACT_FUZZY_REDUCE_EXPRESSION__OPERATOR;

	/**
	 * The feature id for the '<em><b>Projection Function</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUZZY_REDUCE_EXPRESSION__PROJECTION_FUNCTION = ABSTRACT_FUZZY_REDUCE_EXPRESSION__PROJECTION_FUNCTION;

	/**
	 * The feature id for the '<em><b>Body</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUZZY_REDUCE_EXPRESSION__BODY = ABSTRACT_FUZZY_REDUCE_EXPRESSION__BODY;

	/**
	 * The number of structural features of the '<em>Fuzzy Reduce Expression</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUZZY_REDUCE_EXPRESSION_FEATURE_COUNT = ABSTRACT_FUZZY_REDUCE_EXPRESSION_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link alpha.model.impl.ExternalFuzzyReduceExpressionImpl <em>External Fuzzy Reduce Expression</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see alpha.model.impl.ExternalFuzzyReduceExpressionImpl
	 * @see alpha.model.impl.ModelPackageImpl#getExternalFuzzyReduceExpression()
	 * @generated
	 */
	int EXTERNAL_FUZZY_REDUCE_EXPRESSION = 51;

	/**
	 * The feature id for the '<em><b>Node ID</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXTERNAL_FUZZY_REDUCE_EXPRESSION__NODE_ID = FUZZY_REDUCE_EXPRESSION__NODE_ID;

	/**
	 * The feature id for the '<em><b>Zinternal cache expr Dom</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXTERNAL_FUZZY_REDUCE_EXPRESSION__ZINTERNAL_CACHE_EXPR_DOM = FUZZY_REDUCE_EXPRESSION__ZINTERNAL_CACHE_EXPR_DOM;

	/**
	 * The feature id for the '<em><b>Zinternal cache context Dom</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXTERNAL_FUZZY_REDUCE_EXPRESSION__ZINTERNAL_CACHE_CONTEXT_DOM = FUZZY_REDUCE_EXPRESSION__ZINTERNAL_CACHE_CONTEXT_DOM;

	/**
	 * The feature id for the '<em><b>Expression ID</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXTERNAL_FUZZY_REDUCE_EXPRESSION__EXPRESSION_ID = FUZZY_REDUCE_EXPRESSION__EXPRESSION_ID;

	/**
	 * The feature id for the '<em><b>Operator</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXTERNAL_FUZZY_REDUCE_EXPRESSION__OPERATOR = FUZZY_REDUCE_EXPRESSION__OPERATOR;

	/**
	 * The feature id for the '<em><b>Projection Function</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXTERNAL_FUZZY_REDUCE_EXPRESSION__PROJECTION_FUNCTION = FUZZY_REDUCE_EXPRESSION__PROJECTION_FUNCTION;

	/**
	 * The feature id for the '<em><b>Body</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXTERNAL_FUZZY_REDUCE_EXPRESSION__BODY = FUZZY_REDUCE_EXPRESSION__BODY;

	/**
	 * The feature id for the '<em><b>External Function</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXTERNAL_FUZZY_REDUCE_EXPRESSION__EXTERNAL_FUNCTION = FUZZY_REDUCE_EXPRESSION_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>External Fuzzy Reduce Expression</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXTERNAL_FUZZY_REDUCE_EXPRESSION_FEATURE_COUNT = FUZZY_REDUCE_EXPRESSION_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link alpha.model.impl.FuzzyArgReduceExpressionImpl <em>Fuzzy Arg Reduce Expression</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see alpha.model.impl.FuzzyArgReduceExpressionImpl
	 * @see alpha.model.impl.ModelPackageImpl#getFuzzyArgReduceExpression()
	 * @generated
	 */
	int FUZZY_ARG_REDUCE_EXPRESSION = 52;

	/**
	 * The feature id for the '<em><b>Node ID</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUZZY_ARG_REDUCE_EXPRESSION__NODE_ID = ABSTRACT_FUZZY_REDUCE_EXPRESSION__NODE_ID;

	/**
	 * The feature id for the '<em><b>Zinternal cache expr Dom</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUZZY_ARG_REDUCE_EXPRESSION__ZINTERNAL_CACHE_EXPR_DOM = ABSTRACT_FUZZY_REDUCE_EXPRESSION__ZINTERNAL_CACHE_EXPR_DOM;

	/**
	 * The feature id for the '<em><b>Zinternal cache context Dom</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUZZY_ARG_REDUCE_EXPRESSION__ZINTERNAL_CACHE_CONTEXT_DOM = ABSTRACT_FUZZY_REDUCE_EXPRESSION__ZINTERNAL_CACHE_CONTEXT_DOM;

	/**
	 * The feature id for the '<em><b>Expression ID</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUZZY_ARG_REDUCE_EXPRESSION__EXPRESSION_ID = ABSTRACT_FUZZY_REDUCE_EXPRESSION__EXPRESSION_ID;

	/**
	 * The feature id for the '<em><b>Operator</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUZZY_ARG_REDUCE_EXPRESSION__OPERATOR = ABSTRACT_FUZZY_REDUCE_EXPRESSION__OPERATOR;

	/**
	 * The feature id for the '<em><b>Projection Function</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUZZY_ARG_REDUCE_EXPRESSION__PROJECTION_FUNCTION = ABSTRACT_FUZZY_REDUCE_EXPRESSION__PROJECTION_FUNCTION;

	/**
	 * The feature id for the '<em><b>Body</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUZZY_ARG_REDUCE_EXPRESSION__BODY = ABSTRACT_FUZZY_REDUCE_EXPRESSION__BODY;

	/**
	 * The number of structural features of the '<em>Fuzzy Arg Reduce Expression</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUZZY_ARG_REDUCE_EXPRESSION_FEATURE_COUNT = ABSTRACT_FUZZY_REDUCE_EXPRESSION_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link alpha.model.impl.ExternalFuzzyArgReduceExpressionImpl <em>External Fuzzy Arg Reduce Expression</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see alpha.model.impl.ExternalFuzzyArgReduceExpressionImpl
	 * @see alpha.model.impl.ModelPackageImpl#getExternalFuzzyArgReduceExpression()
	 * @generated
	 */
	int EXTERNAL_FUZZY_ARG_REDUCE_EXPRESSION = 53;

	/**
	 * The feature id for the '<em><b>Node ID</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXTERNAL_FUZZY_ARG_REDUCE_EXPRESSION__NODE_ID = FUZZY_ARG_REDUCE_EXPRESSION__NODE_ID;

	/**
	 * The feature id for the '<em><b>Zinternal cache expr Dom</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXTERNAL_FUZZY_ARG_REDUCE_EXPRESSION__ZINTERNAL_CACHE_EXPR_DOM = FUZZY_ARG_REDUCE_EXPRESSION__ZINTERNAL_CACHE_EXPR_DOM;

	/**
	 * The feature id for the '<em><b>Zinternal cache context Dom</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXTERNAL_FUZZY_ARG_REDUCE_EXPRESSION__ZINTERNAL_CACHE_CONTEXT_DOM = FUZZY_ARG_REDUCE_EXPRESSION__ZINTERNAL_CACHE_CONTEXT_DOM;

	/**
	 * The feature id for the '<em><b>Expression ID</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXTERNAL_FUZZY_ARG_REDUCE_EXPRESSION__EXPRESSION_ID = FUZZY_ARG_REDUCE_EXPRESSION__EXPRESSION_ID;

	/**
	 * The feature id for the '<em><b>Operator</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXTERNAL_FUZZY_ARG_REDUCE_EXPRESSION__OPERATOR = FUZZY_ARG_REDUCE_EXPRESSION__OPERATOR;

	/**
	 * The feature id for the '<em><b>Projection Function</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXTERNAL_FUZZY_ARG_REDUCE_EXPRESSION__PROJECTION_FUNCTION = FUZZY_ARG_REDUCE_EXPRESSION__PROJECTION_FUNCTION;

	/**
	 * The feature id for the '<em><b>Body</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXTERNAL_FUZZY_ARG_REDUCE_EXPRESSION__BODY = FUZZY_ARG_REDUCE_EXPRESSION__BODY;

	/**
	 * The feature id for the '<em><b>External Function</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXTERNAL_FUZZY_ARG_REDUCE_EXPRESSION__EXTERNAL_FUNCTION = FUZZY_ARG_REDUCE_EXPRESSION_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>External Fuzzy Arg Reduce Expression</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXTERNAL_FUZZY_ARG_REDUCE_EXPRESSION_FEATURE_COUNT = FUZZY_ARG_REDUCE_EXPRESSION_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link alpha.model.CalculatorNode <em>Calculator Node</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see alpha.model.CalculatorNode
	 * @see alpha.model.impl.ModelPackageImpl#getCalculatorNode()
	 * @generated
	 */
	int CALCULATOR_NODE = 54;

	/**
	 * The number of structural features of the '<em>Calculator Node</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CALCULATOR_NODE_FEATURE_COUNT = 0;

	/**
	 * The meta object id for the '{@link alpha.model.impl.PolyhedralObjectImpl <em>Polyhedral Object</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see alpha.model.impl.PolyhedralObjectImpl
	 * @see alpha.model.impl.ModelPackageImpl#getPolyhedralObject()
	 * @generated
	 */
	int POLYHEDRAL_OBJECT = 55;

	/**
	 * The feature id for the '<em><b>Node ID</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int POLYHEDRAL_OBJECT__NODE_ID = ALPHA_NODE__NODE_ID;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int POLYHEDRAL_OBJECT__NAME = ALPHA_NODE_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Expr</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int POLYHEDRAL_OBJECT__EXPR = ALPHA_NODE_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Polyhedral Object</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int POLYHEDRAL_OBJECT_FEATURE_COUNT = ALPHA_NODE_FEATURE_COUNT + 2;

	/**
	 * The meta object id for the '{@link alpha.model.impl.CalculatorExpressionImpl <em>Calculator Expression</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see alpha.model.impl.CalculatorExpressionImpl
	 * @see alpha.model.impl.ModelPackageImpl#getCalculatorExpression()
	 * @generated
	 */
	int CALCULATOR_EXPRESSION = 56;

	/**
	 * The feature id for the '<em><b>Node ID</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CALCULATOR_EXPRESSION__NODE_ID = ALPHA_NODE__NODE_ID;

	/**
	 * The number of structural features of the '<em>Calculator Expression</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CALCULATOR_EXPRESSION_FEATURE_COUNT = ALPHA_NODE_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link alpha.model.impl.JNIDomainImpl <em>JNI Domain</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see alpha.model.impl.JNIDomainImpl
	 * @see alpha.model.impl.ModelPackageImpl#getJNIDomain()
	 * @generated
	 */
	int JNI_DOMAIN = 57;

	/**
	 * The feature id for the '<em><b>Node ID</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int JNI_DOMAIN__NODE_ID = CALCULATOR_EXPRESSION__NODE_ID;

	/**
	 * The feature id for the '<em><b>Isl String</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int JNI_DOMAIN__ISL_STRING = CALCULATOR_EXPRESSION_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Zinternal cache isl Set</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int JNI_DOMAIN__ZINTERNAL_CACHE_ISL_SET = CALCULATOR_EXPRESSION_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>JNI Domain</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int JNI_DOMAIN_FEATURE_COUNT = CALCULATOR_EXPRESSION_FEATURE_COUNT + 2;

	/**
	 * The meta object id for the '{@link alpha.model.impl.JNIDomainInArrayNotationImpl <em>JNI Domain In Array Notation</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see alpha.model.impl.JNIDomainInArrayNotationImpl
	 * @see alpha.model.impl.ModelPackageImpl#getJNIDomainInArrayNotation()
	 * @generated
	 */
	int JNI_DOMAIN_IN_ARRAY_NOTATION = 58;

	/**
	 * The feature id for the '<em><b>Node ID</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int JNI_DOMAIN_IN_ARRAY_NOTATION__NODE_ID = JNI_DOMAIN__NODE_ID;

	/**
	 * The feature id for the '<em><b>Isl String</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int JNI_DOMAIN_IN_ARRAY_NOTATION__ISL_STRING = JNI_DOMAIN__ISL_STRING;

	/**
	 * The feature id for the '<em><b>Zinternal cache isl Set</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int JNI_DOMAIN_IN_ARRAY_NOTATION__ZINTERNAL_CACHE_ISL_SET = JNI_DOMAIN__ZINTERNAL_CACHE_ISL_SET;

	/**
	 * The number of structural features of the '<em>JNI Domain In Array Notation</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int JNI_DOMAIN_IN_ARRAY_NOTATION_FEATURE_COUNT = JNI_DOMAIN_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link alpha.model.impl.JNIRelationImpl <em>JNI Relation</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see alpha.model.impl.JNIRelationImpl
	 * @see alpha.model.impl.ModelPackageImpl#getJNIRelation()
	 * @generated
	 */
	int JNI_RELATION = 59;

	/**
	 * The feature id for the '<em><b>Node ID</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int JNI_RELATION__NODE_ID = CALCULATOR_EXPRESSION__NODE_ID;

	/**
	 * The feature id for the '<em><b>Isl String</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int JNI_RELATION__ISL_STRING = CALCULATOR_EXPRESSION_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Zinternal cache isl Map</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int JNI_RELATION__ZINTERNAL_CACHE_ISL_MAP = CALCULATOR_EXPRESSION_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>JNI Relation</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int JNI_RELATION_FEATURE_COUNT = CALCULATOR_EXPRESSION_FEATURE_COUNT + 2;

	/**
	 * The meta object id for the '{@link alpha.model.impl.JNIFunctionImpl <em>JNI Function</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see alpha.model.impl.JNIFunctionImpl
	 * @see alpha.model.impl.ModelPackageImpl#getJNIFunction()
	 * @generated
	 */
	int JNI_FUNCTION = 60;

	/**
	 * The feature id for the '<em><b>Node ID</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int JNI_FUNCTION__NODE_ID = CALCULATOR_EXPRESSION__NODE_ID;

	/**
	 * The feature id for the '<em><b>Alpha Function</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int JNI_FUNCTION__ALPHA_FUNCTION = CALCULATOR_EXPRESSION_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Zinternal cache isl MAff</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int JNI_FUNCTION__ZINTERNAL_CACHE_ISL_MAFF = CALCULATOR_EXPRESSION_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>JNI Function</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int JNI_FUNCTION_FEATURE_COUNT = CALCULATOR_EXPRESSION_FEATURE_COUNT + 2;

	/**
	 * The meta object id for the '{@link alpha.model.impl.JNIFunctionInArrayNotationImpl <em>JNI Function In Array Notation</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see alpha.model.impl.JNIFunctionInArrayNotationImpl
	 * @see alpha.model.impl.ModelPackageImpl#getJNIFunctionInArrayNotation()
	 * @generated
	 */
	int JNI_FUNCTION_IN_ARRAY_NOTATION = 61;

	/**
	 * The feature id for the '<em><b>Node ID</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int JNI_FUNCTION_IN_ARRAY_NOTATION__NODE_ID = JNI_FUNCTION__NODE_ID;

	/**
	 * The feature id for the '<em><b>Alpha Function</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int JNI_FUNCTION_IN_ARRAY_NOTATION__ALPHA_FUNCTION = JNI_FUNCTION__ALPHA_FUNCTION;

	/**
	 * The feature id for the '<em><b>Zinternal cache isl MAff</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int JNI_FUNCTION_IN_ARRAY_NOTATION__ZINTERNAL_CACHE_ISL_MAFF = JNI_FUNCTION__ZINTERNAL_CACHE_ISL_MAFF;

	/**
	 * The feature id for the '<em><b>Array Notation</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int JNI_FUNCTION_IN_ARRAY_NOTATION__ARRAY_NOTATION = JNI_FUNCTION_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>JNI Function In Array Notation</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int JNI_FUNCTION_IN_ARRAY_NOTATION_FEATURE_COUNT = JNI_FUNCTION_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link alpha.model.impl.JNIPolynomialImpl <em>JNI Polynomial</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see alpha.model.impl.JNIPolynomialImpl
	 * @see alpha.model.impl.ModelPackageImpl#getJNIPolynomial()
	 * @generated
	 */
	int JNI_POLYNOMIAL = 62;

	/**
	 * The feature id for the '<em><b>Node ID</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int JNI_POLYNOMIAL__NODE_ID = CALCULATOR_EXPRESSION__NODE_ID;

	/**
	 * The feature id for the '<em><b>Isl String</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int JNI_POLYNOMIAL__ISL_STRING = CALCULATOR_EXPRESSION_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Zinternal cache isl PWQP</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int JNI_POLYNOMIAL__ZINTERNAL_CACHE_ISL_PWQP = CALCULATOR_EXPRESSION_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>JNI Polynomial</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int JNI_POLYNOMIAL_FEATURE_COUNT = CALCULATOR_EXPRESSION_FEATURE_COUNT + 2;

	/**
	 * The meta object id for the '{@link alpha.model.impl.JNIPolynomialInArrayNotationImpl <em>JNI Polynomial In Array Notation</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see alpha.model.impl.JNIPolynomialInArrayNotationImpl
	 * @see alpha.model.impl.ModelPackageImpl#getJNIPolynomialInArrayNotation()
	 * @generated
	 */
	int JNI_POLYNOMIAL_IN_ARRAY_NOTATION = 63;

	/**
	 * The feature id for the '<em><b>Node ID</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int JNI_POLYNOMIAL_IN_ARRAY_NOTATION__NODE_ID = JNI_POLYNOMIAL__NODE_ID;

	/**
	 * The feature id for the '<em><b>Isl String</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int JNI_POLYNOMIAL_IN_ARRAY_NOTATION__ISL_STRING = JNI_POLYNOMIAL__ISL_STRING;

	/**
	 * The feature id for the '<em><b>Zinternal cache isl PWQP</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int JNI_POLYNOMIAL_IN_ARRAY_NOTATION__ZINTERNAL_CACHE_ISL_PWQP = JNI_POLYNOMIAL__ZINTERNAL_CACHE_ISL_PWQP;

	/**
	 * The feature id for the '<em><b>Array Notation</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int JNI_POLYNOMIAL_IN_ARRAY_NOTATION__ARRAY_NOTATION = JNI_POLYNOMIAL_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>JNI Polynomial In Array Notation</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int JNI_POLYNOMIAL_IN_ARRAY_NOTATION_FEATURE_COUNT = JNI_POLYNOMIAL_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link alpha.model.impl.FuzzyFunctionImpl <em>Fuzzy Function</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see alpha.model.impl.FuzzyFunctionImpl
	 * @see alpha.model.impl.ModelPackageImpl#getFuzzyFunction()
	 * @generated
	 */
	int FUZZY_FUNCTION = 64;

	/**
	 * The feature id for the '<em><b>Node ID</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUZZY_FUNCTION__NODE_ID = ALPHA_NODE__NODE_ID;

	/**
	 * The feature id for the '<em><b>Alpha String</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUZZY_FUNCTION__ALPHA_STRING = ALPHA_NODE_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Indirections</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUZZY_FUNCTION__INDIRECTIONS = ALPHA_NODE_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Zinternal cache fuzzy Map</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUZZY_FUNCTION__ZINTERNAL_CACHE_FUZZY_MAP = ALPHA_NODE_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Zinternal cache dep Relation</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUZZY_FUNCTION__ZINTERNAL_CACHE_DEP_RELATION = ALPHA_NODE_FEATURE_COUNT + 3;

	/**
	 * The number of structural features of the '<em>Fuzzy Function</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUZZY_FUNCTION_FEATURE_COUNT = ALPHA_NODE_FEATURE_COUNT + 4;

	/**
	 * The meta object id for the '{@link alpha.model.impl.FuzzyVariableUseImpl <em>Fuzzy Variable Use</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see alpha.model.impl.FuzzyVariableUseImpl
	 * @see alpha.model.impl.ModelPackageImpl#getFuzzyVariableUse()
	 * @generated
	 */
	int FUZZY_VARIABLE_USE = 65;

	/**
	 * The feature id for the '<em><b>Node ID</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUZZY_VARIABLE_USE__NODE_ID = ALPHA_NODE__NODE_ID;

	/**
	 * The feature id for the '<em><b>Fuzzy Index</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUZZY_VARIABLE_USE__FUZZY_INDEX = ALPHA_NODE_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Fuzzy Variable</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUZZY_VARIABLE_USE__FUZZY_VARIABLE = ALPHA_NODE_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Fuzzy Variable Use</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUZZY_VARIABLE_USE_FEATURE_COUNT = ALPHA_NODE_FEATURE_COUNT + 2;

	/**
	 * The meta object id for the '{@link alpha.model.impl.NestedFuzzyFunctionImpl <em>Nested Fuzzy Function</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see alpha.model.impl.NestedFuzzyFunctionImpl
	 * @see alpha.model.impl.ModelPackageImpl#getNestedFuzzyFunction()
	 * @generated
	 */
	int NESTED_FUZZY_FUNCTION = 66;

	/**
	 * The feature id for the '<em><b>Node ID</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NESTED_FUZZY_FUNCTION__NODE_ID = FUZZY_FUNCTION__NODE_ID;

	/**
	 * The feature id for the '<em><b>Alpha String</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NESTED_FUZZY_FUNCTION__ALPHA_STRING = FUZZY_FUNCTION__ALPHA_STRING;

	/**
	 * The feature id for the '<em><b>Indirections</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NESTED_FUZZY_FUNCTION__INDIRECTIONS = FUZZY_FUNCTION__INDIRECTIONS;

	/**
	 * The feature id for the '<em><b>Zinternal cache fuzzy Map</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NESTED_FUZZY_FUNCTION__ZINTERNAL_CACHE_FUZZY_MAP = FUZZY_FUNCTION__ZINTERNAL_CACHE_FUZZY_MAP;

	/**
	 * The feature id for the '<em><b>Zinternal cache dep Relation</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NESTED_FUZZY_FUNCTION__ZINTERNAL_CACHE_DEP_RELATION = FUZZY_FUNCTION__ZINTERNAL_CACHE_DEP_RELATION;

	/**
	 * The feature id for the '<em><b>Fuzzy Index</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NESTED_FUZZY_FUNCTION__FUZZY_INDEX = FUZZY_FUNCTION_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Fuzzy Variable</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NESTED_FUZZY_FUNCTION__FUZZY_VARIABLE = FUZZY_FUNCTION_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Nested Fuzzy Function</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NESTED_FUZZY_FUNCTION_FEATURE_COUNT = FUZZY_FUNCTION_FEATURE_COUNT + 2;

	/**
	 * The meta object id for the '{@link alpha.model.impl.AffineFuzzyVariableUseImpl <em>Affine Fuzzy Variable Use</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see alpha.model.impl.AffineFuzzyVariableUseImpl
	 * @see alpha.model.impl.ModelPackageImpl#getAffineFuzzyVariableUse()
	 * @generated
	 */
	int AFFINE_FUZZY_VARIABLE_USE = 67;

	/**
	 * The feature id for the '<em><b>Node ID</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int AFFINE_FUZZY_VARIABLE_USE__NODE_ID = FUZZY_VARIABLE_USE__NODE_ID;

	/**
	 * The feature id for the '<em><b>Fuzzy Index</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int AFFINE_FUZZY_VARIABLE_USE__FUZZY_INDEX = FUZZY_VARIABLE_USE__FUZZY_INDEX;

	/**
	 * The feature id for the '<em><b>Fuzzy Variable</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int AFFINE_FUZZY_VARIABLE_USE__FUZZY_VARIABLE = FUZZY_VARIABLE_USE__FUZZY_VARIABLE;

	/**
	 * The feature id for the '<em><b>Use Function</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int AFFINE_FUZZY_VARIABLE_USE__USE_FUNCTION = FUZZY_VARIABLE_USE_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Affine Fuzzy Variable Use</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int AFFINE_FUZZY_VARIABLE_USE_FEATURE_COUNT = FUZZY_VARIABLE_USE_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link alpha.model.impl.FuzzyFunctionInArrayNotationImpl <em>Fuzzy Function In Array Notation</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see alpha.model.impl.FuzzyFunctionInArrayNotationImpl
	 * @see alpha.model.impl.ModelPackageImpl#getFuzzyFunctionInArrayNotation()
	 * @generated
	 */
	int FUZZY_FUNCTION_IN_ARRAY_NOTATION = 68;

	/**
	 * The feature id for the '<em><b>Node ID</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUZZY_FUNCTION_IN_ARRAY_NOTATION__NODE_ID = FUZZY_FUNCTION__NODE_ID;

	/**
	 * The feature id for the '<em><b>Alpha String</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUZZY_FUNCTION_IN_ARRAY_NOTATION__ALPHA_STRING = FUZZY_FUNCTION__ALPHA_STRING;

	/**
	 * The feature id for the '<em><b>Indirections</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUZZY_FUNCTION_IN_ARRAY_NOTATION__INDIRECTIONS = FUZZY_FUNCTION__INDIRECTIONS;

	/**
	 * The feature id for the '<em><b>Zinternal cache fuzzy Map</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUZZY_FUNCTION_IN_ARRAY_NOTATION__ZINTERNAL_CACHE_FUZZY_MAP = FUZZY_FUNCTION__ZINTERNAL_CACHE_FUZZY_MAP;

	/**
	 * The feature id for the '<em><b>Zinternal cache dep Relation</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUZZY_FUNCTION_IN_ARRAY_NOTATION__ZINTERNAL_CACHE_DEP_RELATION = FUZZY_FUNCTION__ZINTERNAL_CACHE_DEP_RELATION;

	/**
	 * The feature id for the '<em><b>Array Notation</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUZZY_FUNCTION_IN_ARRAY_NOTATION__ARRAY_NOTATION = FUZZY_FUNCTION_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Fuzzy Function In Array Notation</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUZZY_FUNCTION_IN_ARRAY_NOTATION_FEATURE_COUNT = FUZZY_FUNCTION_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link alpha.model.impl.UnaryCalculatorExpressionImpl <em>Unary Calculator Expression</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see alpha.model.impl.UnaryCalculatorExpressionImpl
	 * @see alpha.model.impl.ModelPackageImpl#getUnaryCalculatorExpression()
	 * @generated
	 */
	int UNARY_CALCULATOR_EXPRESSION = 69;

	/**
	 * The feature id for the '<em><b>Node ID</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int UNARY_CALCULATOR_EXPRESSION__NODE_ID = CALCULATOR_EXPRESSION__NODE_ID;

	/**
	 * The feature id for the '<em><b>Operator</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int UNARY_CALCULATOR_EXPRESSION__OPERATOR = CALCULATOR_EXPRESSION_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Expr</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int UNARY_CALCULATOR_EXPRESSION__EXPR = CALCULATOR_EXPRESSION_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Zinternal cache isl Object</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int UNARY_CALCULATOR_EXPRESSION__ZINTERNAL_CACHE_ISL_OBJECT = CALCULATOR_EXPRESSION_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>Unary Calculator Expression</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int UNARY_CALCULATOR_EXPRESSION_FEATURE_COUNT = CALCULATOR_EXPRESSION_FEATURE_COUNT + 3;

	/**
	 * The meta object id for the '{@link alpha.model.impl.BinaryCalculatorExpressionImpl <em>Binary Calculator Expression</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see alpha.model.impl.BinaryCalculatorExpressionImpl
	 * @see alpha.model.impl.ModelPackageImpl#getBinaryCalculatorExpression()
	 * @generated
	 */
	int BINARY_CALCULATOR_EXPRESSION = 70;

	/**
	 * The feature id for the '<em><b>Node ID</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BINARY_CALCULATOR_EXPRESSION__NODE_ID = CALCULATOR_EXPRESSION__NODE_ID;

	/**
	 * The feature id for the '<em><b>Operator</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BINARY_CALCULATOR_EXPRESSION__OPERATOR = CALCULATOR_EXPRESSION_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Left</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BINARY_CALCULATOR_EXPRESSION__LEFT = CALCULATOR_EXPRESSION_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Right</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BINARY_CALCULATOR_EXPRESSION__RIGHT = CALCULATOR_EXPRESSION_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Zinternal cache isl Object</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BINARY_CALCULATOR_EXPRESSION__ZINTERNAL_CACHE_ISL_OBJECT = CALCULATOR_EXPRESSION_FEATURE_COUNT + 3;

	/**
	 * The number of structural features of the '<em>Binary Calculator Expression</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BINARY_CALCULATOR_EXPRESSION_FEATURE_COUNT = CALCULATOR_EXPRESSION_FEATURE_COUNT + 4;

	/**
	 * The meta object id for the '{@link alpha.model.impl.VariableDomainImpl <em>Variable Domain</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see alpha.model.impl.VariableDomainImpl
	 * @see alpha.model.impl.ModelPackageImpl#getVariableDomain()
	 * @generated
	 */
	int VARIABLE_DOMAIN = 71;

	/**
	 * The feature id for the '<em><b>Node ID</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VARIABLE_DOMAIN__NODE_ID = CALCULATOR_EXPRESSION__NODE_ID;

	/**
	 * The feature id for the '<em><b>Variable</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VARIABLE_DOMAIN__VARIABLE = CALCULATOR_EXPRESSION_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Variable Domain</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VARIABLE_DOMAIN_FEATURE_COUNT = CALCULATOR_EXPRESSION_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link alpha.model.impl.RectangularDomainImpl <em>Rectangular Domain</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see alpha.model.impl.RectangularDomainImpl
	 * @see alpha.model.impl.ModelPackageImpl#getRectangularDomain()
	 * @generated
	 */
	int RECTANGULAR_DOMAIN = 72;

	/**
	 * The feature id for the '<em><b>Node ID</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RECTANGULAR_DOMAIN__NODE_ID = CALCULATOR_EXPRESSION__NODE_ID;

	/**
	 * The feature id for the '<em><b>Lower Bounds</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RECTANGULAR_DOMAIN__LOWER_BOUNDS = CALCULATOR_EXPRESSION_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Upper Bounds</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RECTANGULAR_DOMAIN__UPPER_BOUNDS = CALCULATOR_EXPRESSION_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Index Names</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RECTANGULAR_DOMAIN__INDEX_NAMES = CALCULATOR_EXPRESSION_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Zinternal cache isl Set</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RECTANGULAR_DOMAIN__ZINTERNAL_CACHE_ISL_SET = CALCULATOR_EXPRESSION_FEATURE_COUNT + 3;

	/**
	 * The number of structural features of the '<em>Rectangular Domain</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RECTANGULAR_DOMAIN_FEATURE_COUNT = CALCULATOR_EXPRESSION_FEATURE_COUNT + 4;

	/**
	 * The meta object id for the '{@link alpha.model.impl.DefinedObjectImpl <em>Defined Object</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see alpha.model.impl.DefinedObjectImpl
	 * @see alpha.model.impl.ModelPackageImpl#getDefinedObject()
	 * @generated
	 */
	int DEFINED_OBJECT = 73;

	/**
	 * The feature id for the '<em><b>Node ID</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DEFINED_OBJECT__NODE_ID = CALCULATOR_EXPRESSION__NODE_ID;

	/**
	 * The feature id for the '<em><b>Object</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DEFINED_OBJECT__OBJECT = CALCULATOR_EXPRESSION_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Zinternal Cycle Detector</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DEFINED_OBJECT__ZINTERNAL_CYCLE_DETECTOR = CALCULATOR_EXPRESSION_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Defined Object</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DEFINED_OBJECT_FEATURE_COUNT = CALCULATOR_EXPRESSION_FEATURE_COUNT + 2;

	/**
	 * The meta object id for the '{@link alpha.model.impl.AlphaFunctionImpl <em>Alpha Function</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see alpha.model.impl.AlphaFunctionImpl
	 * @see alpha.model.impl.ModelPackageImpl#getAlphaFunction()
	 * @generated
	 */
	int ALPHA_FUNCTION = 74;

	/**
	 * The feature id for the '<em><b>Index List</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ALPHA_FUNCTION__INDEX_LIST = 0;

	/**
	 * The feature id for the '<em><b>Exprs</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ALPHA_FUNCTION__EXPRS = 1;

	/**
	 * The number of structural features of the '<em>Alpha Function</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ALPHA_FUNCTION_FEATURE_COUNT = 2;

	/**
	 * The meta object id for the '{@link alpha.model.impl.AlphaFunctionExpressionImpl <em>Alpha Function Expression</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see alpha.model.impl.AlphaFunctionExpressionImpl
	 * @see alpha.model.impl.ModelPackageImpl#getAlphaFunctionExpression()
	 * @generated
	 */
	int ALPHA_FUNCTION_EXPRESSION = 75;

	/**
	 * The number of structural features of the '<em>Alpha Function Expression</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ALPHA_FUNCTION_EXPRESSION_FEATURE_COUNT = 0;

	/**
	 * The meta object id for the '{@link alpha.model.impl.AlphaFunctionBinaryExpressionImpl <em>Alpha Function Binary Expression</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see alpha.model.impl.AlphaFunctionBinaryExpressionImpl
	 * @see alpha.model.impl.ModelPackageImpl#getAlphaFunctionBinaryExpression()
	 * @generated
	 */
	int ALPHA_FUNCTION_BINARY_EXPRESSION = 76;

	/**
	 * The feature id for the '<em><b>Left</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ALPHA_FUNCTION_BINARY_EXPRESSION__LEFT = ALPHA_FUNCTION_EXPRESSION_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Right</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ALPHA_FUNCTION_BINARY_EXPRESSION__RIGHT = ALPHA_FUNCTION_EXPRESSION_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Operator</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ALPHA_FUNCTION_BINARY_EXPRESSION__OPERATOR = ALPHA_FUNCTION_EXPRESSION_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>Alpha Function Binary Expression</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ALPHA_FUNCTION_BINARY_EXPRESSION_FEATURE_COUNT = ALPHA_FUNCTION_EXPRESSION_FEATURE_COUNT + 3;

	/**
	 * The meta object id for the '{@link alpha.model.impl.AlphaFunctionLiteralImpl <em>Alpha Function Literal</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see alpha.model.impl.AlphaFunctionLiteralImpl
	 * @see alpha.model.impl.ModelPackageImpl#getAlphaFunctionLiteral()
	 * @generated
	 */
	int ALPHA_FUNCTION_LITERAL = 77;

	/**
	 * The feature id for the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ALPHA_FUNCTION_LITERAL__VALUE = ALPHA_FUNCTION_EXPRESSION_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Alpha Function Literal</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ALPHA_FUNCTION_LITERAL_FEATURE_COUNT = ALPHA_FUNCTION_EXPRESSION_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link alpha.model.impl.AlphaFunctionFloorImpl <em>Alpha Function Floor</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see alpha.model.impl.AlphaFunctionFloorImpl
	 * @see alpha.model.impl.ModelPackageImpl#getAlphaFunctionFloor()
	 * @generated
	 */
	int ALPHA_FUNCTION_FLOOR = 78;

	/**
	 * The feature id for the '<em><b>Expr</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ALPHA_FUNCTION_FLOOR__EXPR = ALPHA_FUNCTION_EXPRESSION_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Alpha Function Floor</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ALPHA_FUNCTION_FLOOR_FEATURE_COUNT = ALPHA_FUNCTION_EXPRESSION_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link alpha.model.UNARY_OP <em>UNARY OP</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see alpha.model.UNARY_OP
	 * @see alpha.model.impl.ModelPackageImpl#getUNARY_OP()
	 * @generated
	 */
	int UNARY_OP = 79;

	/**
	 * The meta object id for the '{@link alpha.model.BINARY_OP <em>BINARY OP</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see alpha.model.BINARY_OP
	 * @see alpha.model.impl.ModelPackageImpl#getBINARY_OP()
	 * @generated
	 */
	int BINARY_OP = 80;

	/**
	 * The meta object id for the '{@link alpha.model.REDUCTION_OP <em>REDUCTION OP</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see alpha.model.REDUCTION_OP
	 * @see alpha.model.impl.ModelPackageImpl#getREDUCTION_OP()
	 * @generated
	 */
	int REDUCTION_OP = 81;

	/**
	 * The meta object id for the '{@link alpha.model.POLY_OBJECT_TYPE <em>POLY OBJECT TYPE</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see alpha.model.POLY_OBJECT_TYPE
	 * @see alpha.model.impl.ModelPackageImpl#getPOLY_OBJECT_TYPE()
	 * @generated
	 */
	int POLY_OBJECT_TYPE = 82;

	/**
	 * The meta object id for the '{@link alpha.model.CALCULATOR_UNARY_OP <em>CALCULATOR UNARY OP</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see alpha.model.CALCULATOR_UNARY_OP
	 * @see alpha.model.impl.ModelPackageImpl#getCALCULATOR_UNARY_OP()
	 * @generated
	 */
	int CALCULATOR_UNARY_OP = 83;

	/**
	 * The meta object id for the '{@link alpha.model.CALCULATOR_BINARY_OP <em>CALCULATOR BINARY OP</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see alpha.model.CALCULATOR_BINARY_OP
	 * @see alpha.model.impl.ModelPackageImpl#getCALCULATOR_BINARY_OP()
	 * @generated
	 */
	int CALCULATOR_BINARY_OP = 84;

	/**
	 * The meta object id for the '<em>JNI Object</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see fr.irisa.cairn.jnimap.runtime.JNIObject
	 * @see alpha.model.impl.ModelPackageImpl#getJNIObject()
	 * @generated
	 */
	int JNI_OBJECT = 85;

	/**
	 * The meta object id for the '<em>JNIISL Set</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see fr.irisa.cairn.jnimap.isl.ISLSet
	 * @see alpha.model.impl.ModelPackageImpl#getJNIISLSet()
	 * @generated
	 */
	int JNIISL_SET = 86;

	/**
	 * The meta object id for the '<em>JNIISL Map</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see fr.irisa.cairn.jnimap.isl.ISLMap
	 * @see alpha.model.impl.ModelPackageImpl#getJNIISLMap()
	 * @generated
	 */
	int JNIISL_MAP = 87;

	/**
	 * The meta object id for the '<em>JNIISL Multi Aff</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see fr.irisa.cairn.jnimap.isl.ISLMultiAff
	 * @see alpha.model.impl.ModelPackageImpl#getJNIISLMultiAff()
	 * @generated
	 */
	int JNIISL_MULTI_AFF = 88;

	/**
	 * The meta object id for the '<em>JNIISLPWQ Polynomial</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see fr.irisa.cairn.jnimap.isl.ISLPWQPolynomial
	 * @see alpha.model.impl.ModelPackageImpl#getJNIISLPWQPolynomial()
	 * @generated
	 */
	int JNIISLPWQ_POLYNOMIAL = 89;

	/**
	 * The meta object id for the '<em>ISL FORMAT</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see fr.irisa.cairn.jnimap.isl.ISL_FORMAT
	 * @see alpha.model.impl.ModelPackageImpl#getISL_FORMAT()
	 * @generated
	 */
	int ISL_FORMAT = 90;

	/**
	 * The meta object id for the '<em>List Variable Expression</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see java.util.List
	 * @see alpha.model.impl.ModelPackageImpl#getListVariableExpression()
	 * @generated
	 */
	int LIST_VARIABLE_EXPRESSION = 91;

	/**
	 * The meta object id for the '<em>Integer Queue</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see java.util.Queue
	 * @see alpha.model.impl.ModelPackageImpl#getIntegerQueue()
	 * @generated
	 */
	int INTEGER_QUEUE = 92;

	/**
	 * The meta object id for the '<em>String</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see java.lang.String
	 * @see alpha.model.impl.ModelPackageImpl#getString()
	 * @generated
	 */
	int STRING = 93;

	/**
	 * The meta object id for the '<em>Face</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see alpha.model.util.Face
	 * @see alpha.model.impl.ModelPackageImpl#getFace()
	 * @generated
	 */
	int FACE = 94;

	/**
	 * The meta object id for the '<em>Face Lattice</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see alpha.model.util.FaceLattice
	 * @see alpha.model.impl.ModelPackageImpl#getFaceLattice()
	 * @generated
	 */
	int FACE_LATTICE = 95;

	/**
	 * The meta object id for the '<em>int</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see java.lang.Integer
	 * @see alpha.model.impl.ModelPackageImpl#getint()
	 * @generated
	 */
	int INT = 96;

	/**
	 * The meta object id for the '<em>float</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see java.lang.Float
	 * @see alpha.model.impl.ModelPackageImpl#getfloat()
	 * @generated
	 */
	int FLOAT = 97;

	/**
	 * The meta object id for the '<em>double</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see java.lang.Double
	 * @see alpha.model.impl.ModelPackageImpl#getdouble()
	 * @generated
	 */
	int DOUBLE = 98;

	/**
	 * The meta object id for the '<em>boolean</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see java.lang.Boolean
	 * @see alpha.model.impl.ModelPackageImpl#getboolean()
	 * @generated
	 */
	int BOOLEAN = 99;


	/**
	 * Returns the meta object for class '{@link alpha.model.AlphaNode <em>Alpha Node</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Alpha Node</em>'.
	 * @see alpha.model.AlphaNode
	 * @generated
	 */
	EClass getAlphaNode();

	/**
	 * Returns the meta object for the attribute list '{@link alpha.model.AlphaNode#getNodeID <em>Node ID</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Node ID</em>'.
	 * @see alpha.model.AlphaNode#getNodeID()
	 * @see #getAlphaNode()
	 * @generated
	 */
	EAttribute getAlphaNode_NodeID();

	/**
	 * Returns the meta object for class '{@link alpha.model.AlphaCompleteVisitable <em>Alpha Complete Visitable</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Alpha Complete Visitable</em>'.
	 * @see alpha.model.AlphaCompleteVisitable
	 * @generated
	 */
	EClass getAlphaCompleteVisitable();

	/**
	 * Returns the meta object for class '{@link alpha.model.AlphaVisitable <em>Alpha Visitable</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Alpha Visitable</em>'.
	 * @see alpha.model.AlphaVisitable
	 * @generated
	 */
	EClass getAlphaVisitable();

	/**
	 * Returns the meta object for class '{@link alpha.model.AlphaExpressionVisitable <em>Alpha Expression Visitable</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Alpha Expression Visitable</em>'.
	 * @see alpha.model.AlphaExpressionVisitable
	 * @generated
	 */
	EClass getAlphaExpressionVisitable();

	/**
	 * Returns the meta object for class '{@link alpha.model.CalculatorExpressionVisitable <em>Calculator Expression Visitable</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Calculator Expression Visitable</em>'.
	 * @see alpha.model.CalculatorExpressionVisitable
	 * @generated
	 */
	EClass getCalculatorExpressionVisitable();

	/**
	 * Returns the meta object for class '{@link alpha.model.AlphaSystemElement <em>Alpha System Element</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Alpha System Element</em>'.
	 * @see alpha.model.AlphaSystemElement
	 * @generated
	 */
	EClass getAlphaSystemElement();

	/**
	 * Returns the meta object for class '{@link alpha.model.AlphaScheduleTarget <em>Alpha Schedule Target</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Alpha Schedule Target</em>'.
	 * @see alpha.model.AlphaScheduleTarget
	 * @generated
	 */
	EClass getAlphaScheduleTarget();

	/**
	 * Returns the meta object for class '{@link alpha.model.AlphaVisitor <em>Alpha Visitor</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Alpha Visitor</em>'.
	 * @see alpha.model.AlphaVisitor
	 * @generated
	 */
	EClass getAlphaVisitor();

	/**
	 * Returns the meta object for class '{@link alpha.model.AlphaExpressionVisitor <em>Alpha Expression Visitor</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Alpha Expression Visitor</em>'.
	 * @see alpha.model.AlphaExpressionVisitor
	 * @generated
	 */
	EClass getAlphaExpressionVisitor();

	/**
	 * Returns the meta object for class '{@link alpha.model.CalculatorExpressionVisitor <em>Calculator Expression Visitor</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Calculator Expression Visitor</em>'.
	 * @see alpha.model.CalculatorExpressionVisitor
	 * @generated
	 */
	EClass getCalculatorExpressionVisitor();

	/**
	 * Returns the meta object for class '{@link alpha.model.AlphaRoot <em>Alpha Root</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Alpha Root</em>'.
	 * @see alpha.model.AlphaRoot
	 * @generated
	 */
	EClass getAlphaRoot();

	/**
	 * Returns the meta object for the containment reference list '{@link alpha.model.AlphaRoot#getImports <em>Imports</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Imports</em>'.
	 * @see alpha.model.AlphaRoot#getImports()
	 * @see #getAlphaRoot()
	 * @generated
	 */
	EReference getAlphaRoot_Imports();

	/**
	 * Returns the meta object for the containment reference list '{@link alpha.model.AlphaRoot#getElements <em>Elements</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Elements</em>'.
	 * @see alpha.model.AlphaRoot#getElements()
	 * @see #getAlphaRoot()
	 * @generated
	 */
	EReference getAlphaRoot_Elements();

	/**
	 * Returns the meta object for class '{@link alpha.model.AlphaElement <em>Alpha Element</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Alpha Element</em>'.
	 * @see alpha.model.AlphaElement
	 * @generated
	 */
	EClass getAlphaElement();

	/**
	 * Returns the meta object for class '{@link alpha.model.Imports <em>Imports</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Imports</em>'.
	 * @see alpha.model.Imports
	 * @generated
	 */
	EClass getImports();

	/**
	 * Returns the meta object for the attribute '{@link alpha.model.Imports#getImportedNamespace <em>Imported Namespace</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Imported Namespace</em>'.
	 * @see alpha.model.Imports#getImportedNamespace()
	 * @see #getImports()
	 * @generated
	 */
	EAttribute getImports_ImportedNamespace();

	/**
	 * Returns the meta object for class '{@link alpha.model.AlphaPackage <em>Alpha Package</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Alpha Package</em>'.
	 * @see alpha.model.AlphaPackage
	 * @generated
	 */
	EClass getAlphaPackage();

	/**
	 * Returns the meta object for the attribute '{@link alpha.model.AlphaPackage#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see alpha.model.AlphaPackage#getName()
	 * @see #getAlphaPackage()
	 * @generated
	 */
	EAttribute getAlphaPackage_Name();

	/**
	 * Returns the meta object for the containment reference list '{@link alpha.model.AlphaPackage#getElements <em>Elements</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Elements</em>'.
	 * @see alpha.model.AlphaPackage#getElements()
	 * @see #getAlphaPackage()
	 * @generated
	 */
	EReference getAlphaPackage_Elements();

	/**
	 * Returns the meta object for class '{@link alpha.model.AlphaConstant <em>Alpha Constant</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Alpha Constant</em>'.
	 * @see alpha.model.AlphaConstant
	 * @generated
	 */
	EClass getAlphaConstant();

	/**
	 * Returns the meta object for the attribute '{@link alpha.model.AlphaConstant#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see alpha.model.AlphaConstant#getName()
	 * @see #getAlphaConstant()
	 * @generated
	 */
	EAttribute getAlphaConstant_Name();

	/**
	 * Returns the meta object for the attribute '{@link alpha.model.AlphaConstant#getValue <em>Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Value</em>'.
	 * @see alpha.model.AlphaConstant#getValue()
	 * @see #getAlphaConstant()
	 * @generated
	 */
	EAttribute getAlphaConstant_Value();

	/**
	 * Returns the meta object for class '{@link alpha.model.ExternalFunction <em>External Function</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>External Function</em>'.
	 * @see alpha.model.ExternalFunction
	 * @generated
	 */
	EClass getExternalFunction();

	/**
	 * Returns the meta object for the attribute '{@link alpha.model.ExternalFunction#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see alpha.model.ExternalFunction#getName()
	 * @see #getExternalFunction()
	 * @generated
	 */
	EAttribute getExternalFunction_Name();

	/**
	 * Returns the meta object for the attribute '{@link alpha.model.ExternalFunction#getCardinality <em>Cardinality</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Cardinality</em>'.
	 * @see alpha.model.ExternalFunction#getCardinality()
	 * @see #getExternalFunction()
	 * @generated
	 */
	EAttribute getExternalFunction_Cardinality();

	/**
	 * Returns the meta object for class '{@link alpha.model.AlphaSystem <em>Alpha System</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Alpha System</em>'.
	 * @see alpha.model.AlphaSystem
	 * @generated
	 */
	EClass getAlphaSystem();

	/**
	 * Returns the meta object for the attribute '{@link alpha.model.AlphaSystem#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see alpha.model.AlphaSystem#getName()
	 * @see #getAlphaSystem()
	 * @generated
	 */
	EAttribute getAlphaSystem_Name();

	/**
	 * Returns the meta object for the containment reference '{@link alpha.model.AlphaSystem#getParameterDomainExpr <em>Parameter Domain Expr</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Parameter Domain Expr</em>'.
	 * @see alpha.model.AlphaSystem#getParameterDomainExpr()
	 * @see #getAlphaSystem()
	 * @generated
	 */
	EReference getAlphaSystem_ParameterDomainExpr();

	/**
	 * Returns the meta object for the containment reference list '{@link alpha.model.AlphaSystem#getDefinedObjects <em>Defined Objects</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Defined Objects</em>'.
	 * @see alpha.model.AlphaSystem#getDefinedObjects()
	 * @see #getAlphaSystem()
	 * @generated
	 */
	EReference getAlphaSystem_DefinedObjects();

	/**
	 * Returns the meta object for the containment reference list '{@link alpha.model.AlphaSystem#getInputs <em>Inputs</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Inputs</em>'.
	 * @see alpha.model.AlphaSystem#getInputs()
	 * @see #getAlphaSystem()
	 * @generated
	 */
	EReference getAlphaSystem_Inputs();

	/**
	 * Returns the meta object for the containment reference list '{@link alpha.model.AlphaSystem#getOutputs <em>Outputs</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Outputs</em>'.
	 * @see alpha.model.AlphaSystem#getOutputs()
	 * @see #getAlphaSystem()
	 * @generated
	 */
	EReference getAlphaSystem_Outputs();

	/**
	 * Returns the meta object for the containment reference list '{@link alpha.model.AlphaSystem#getLocals <em>Locals</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Locals</em>'.
	 * @see alpha.model.AlphaSystem#getLocals()
	 * @see #getAlphaSystem()
	 * @generated
	 */
	EReference getAlphaSystem_Locals();

	/**
	 * Returns the meta object for the containment reference '{@link alpha.model.AlphaSystem#getWhileDomainExpr <em>While Domain Expr</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>While Domain Expr</em>'.
	 * @see alpha.model.AlphaSystem#getWhileDomainExpr()
	 * @see #getAlphaSystem()
	 * @generated
	 */
	EReference getAlphaSystem_WhileDomainExpr();

	/**
	 * Returns the meta object for the containment reference '{@link alpha.model.AlphaSystem#getTestExpression <em>Test Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Test Expression</em>'.
	 * @see alpha.model.AlphaSystem#getTestExpression()
	 * @see #getAlphaSystem()
	 * @generated
	 */
	EReference getAlphaSystem_TestExpression();

	/**
	 * Returns the meta object for the containment reference list '{@link alpha.model.AlphaSystem#getSystemBodies <em>System Bodies</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>System Bodies</em>'.
	 * @see alpha.model.AlphaSystem#getSystemBodies()
	 * @see #getAlphaSystem()
	 * @generated
	 */
	EReference getAlphaSystem_SystemBodies();

	/**
	 * Returns the meta object for class '{@link alpha.model.Variable <em>Variable</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Variable</em>'.
	 * @see alpha.model.Variable
	 * @generated
	 */
	EClass getVariable();

	/**
	 * Returns the meta object for the attribute '{@link alpha.model.Variable#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see alpha.model.Variable#getName()
	 * @see #getVariable()
	 * @generated
	 */
	EAttribute getVariable_Name();

	/**
	 * Returns the meta object for the containment reference '{@link alpha.model.Variable#getDomainExpr <em>Domain Expr</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Domain Expr</em>'.
	 * @see alpha.model.Variable#getDomainExpr()
	 * @see #getVariable()
	 * @generated
	 */
	EReference getVariable_DomainExpr();

	/**
	 * Returns the meta object for class '{@link alpha.model.FuzzyVariable <em>Fuzzy Variable</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Fuzzy Variable</em>'.
	 * @see alpha.model.FuzzyVariable
	 * @generated
	 */
	EClass getFuzzyVariable();

	/**
	 * Returns the meta object for the containment reference '{@link alpha.model.FuzzyVariable#getRangeExpr <em>Range Expr</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Range Expr</em>'.
	 * @see alpha.model.FuzzyVariable#getRangeExpr()
	 * @see #getFuzzyVariable()
	 * @generated
	 */
	EReference getFuzzyVariable_RangeExpr();

	/**
	 * Returns the meta object for class '{@link alpha.model.SystemBody <em>System Body</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>System Body</em>'.
	 * @see alpha.model.SystemBody
	 * @generated
	 */
	EClass getSystemBody();

	/**
	 * Returns the meta object for the container reference '{@link alpha.model.SystemBody#getSystem <em>System</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the container reference '<em>System</em>'.
	 * @see alpha.model.SystemBody#getSystem()
	 * @see #getSystemBody()
	 * @generated
	 */
	EReference getSystemBody_System();

	/**
	 * Returns the meta object for the containment reference '{@link alpha.model.SystemBody#getParameterDomainExpr <em>Parameter Domain Expr</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Parameter Domain Expr</em>'.
	 * @see alpha.model.SystemBody#getParameterDomainExpr()
	 * @see #getSystemBody()
	 * @generated
	 */
	EReference getSystemBody_ParameterDomainExpr();

	/**
	 * Returns the meta object for the containment reference list '{@link alpha.model.SystemBody#getEquations <em>Equations</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Equations</em>'.
	 * @see alpha.model.SystemBody#getEquations()
	 * @see #getSystemBody()
	 * @generated
	 */
	EReference getSystemBody_Equations();

	/**
	 * Returns the meta object for class '{@link alpha.model.Equation <em>Equation</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Equation</em>'.
	 * @see alpha.model.Equation
	 * @generated
	 */
	EClass getEquation();

	/**
	 * Returns the meta object for the container reference '{@link alpha.model.Equation#getSystemBody <em>System Body</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the container reference '<em>System Body</em>'.
	 * @see alpha.model.Equation#getSystemBody()
	 * @see #getEquation()
	 * @generated
	 */
	EReference getEquation_SystemBody();

	/**
	 * Returns the meta object for the attribute '{@link alpha.model.Equation#getZ__explored <em>Zexplored</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Zexplored</em>'.
	 * @see alpha.model.Equation#getZ__explored()
	 * @see #getEquation()
	 * @generated
	 */
	EAttribute getEquation_Z__explored();

	/**
	 * Returns the meta object for class '{@link alpha.model.StandardEquation <em>Standard Equation</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Standard Equation</em>'.
	 * @see alpha.model.StandardEquation
	 * @generated
	 */
	EClass getStandardEquation();

	/**
	 * Returns the meta object for the reference '{@link alpha.model.StandardEquation#getVariable <em>Variable</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Variable</em>'.
	 * @see alpha.model.StandardEquation#getVariable()
	 * @see #getStandardEquation()
	 * @generated
	 */
	EReference getStandardEquation_Variable();

	/**
	 * Returns the meta object for the attribute list '{@link alpha.model.StandardEquation#getIndexNames <em>Index Names</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Index Names</em>'.
	 * @see alpha.model.StandardEquation#getIndexNames()
	 * @see #getStandardEquation()
	 * @generated
	 */
	EAttribute getStandardEquation_IndexNames();

	/**
	 * Returns the meta object for the containment reference '{@link alpha.model.StandardEquation#getExpr <em>Expr</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Expr</em>'.
	 * @see alpha.model.StandardEquation#getExpr()
	 * @see #getStandardEquation()
	 * @generated
	 */
	EReference getStandardEquation_Expr();

	/**
	 * Returns the meta object for class '{@link alpha.model.UseEquation <em>Use Equation</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Use Equation</em>'.
	 * @see alpha.model.UseEquation
	 * @generated
	 */
	EClass getUseEquation();

	/**
	 * Returns the meta object for the attribute '{@link alpha.model.UseEquation#getIdentifier <em>Identifier</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Identifier</em>'.
	 * @see alpha.model.UseEquation#getIdentifier()
	 * @see #getUseEquation()
	 * @generated
	 */
	EAttribute getUseEquation_Identifier();

	/**
	 * Returns the meta object for the containment reference '{@link alpha.model.UseEquation#getInstantiationDomainExpr <em>Instantiation Domain Expr</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Instantiation Domain Expr</em>'.
	 * @see alpha.model.UseEquation#getInstantiationDomainExpr()
	 * @see #getUseEquation()
	 * @generated
	 */
	EReference getUseEquation_InstantiationDomainExpr();

	/**
	 * Returns the meta object for the attribute list '{@link alpha.model.UseEquation#getSubsystemDims <em>Subsystem Dims</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Subsystem Dims</em>'.
	 * @see alpha.model.UseEquation#getSubsystemDims()
	 * @see #getUseEquation()
	 * @generated
	 */
	EAttribute getUseEquation_SubsystemDims();

	/**
	 * Returns the meta object for the reference '{@link alpha.model.UseEquation#getSystem <em>System</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>System</em>'.
	 * @see alpha.model.UseEquation#getSystem()
	 * @see #getUseEquation()
	 * @generated
	 */
	EReference getUseEquation_System();

	/**
	 * Returns the meta object for the containment reference '{@link alpha.model.UseEquation#getCallParamsExpr <em>Call Params Expr</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Call Params Expr</em>'.
	 * @see alpha.model.UseEquation#getCallParamsExpr()
	 * @see #getUseEquation()
	 * @generated
	 */
	EReference getUseEquation_CallParamsExpr();

	/**
	 * Returns the meta object for the containment reference list '{@link alpha.model.UseEquation#getInputExprs <em>Input Exprs</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Input Exprs</em>'.
	 * @see alpha.model.UseEquation#getInputExprs()
	 * @see #getUseEquation()
	 * @generated
	 */
	EReference getUseEquation_InputExprs();

	/**
	 * Returns the meta object for the containment reference list '{@link alpha.model.UseEquation#getOutputExprs <em>Output Exprs</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Output Exprs</em>'.
	 * @see alpha.model.UseEquation#getOutputExprs()
	 * @see #getUseEquation()
	 * @generated
	 */
	EReference getUseEquation_OutputExprs();

	/**
	 * Returns the meta object for class '{@link alpha.model.AlphaExpression <em>Alpha Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Alpha Expression</em>'.
	 * @see alpha.model.AlphaExpression
	 * @generated
	 */
	EClass getAlphaExpression();

	/**
	 * Returns the meta object for the attribute '{@link alpha.model.AlphaExpression#getZ__internal_cache_exprDom <em>Zinternal cache expr Dom</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Zinternal cache expr Dom</em>'.
	 * @see alpha.model.AlphaExpression#getZ__internal_cache_exprDom()
	 * @see #getAlphaExpression()
	 * @generated
	 */
	EAttribute getAlphaExpression_Z__internal_cache_exprDom();

	/**
	 * Returns the meta object for the attribute '{@link alpha.model.AlphaExpression#getZ__internal_cache_contextDom <em>Zinternal cache context Dom</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Zinternal cache context Dom</em>'.
	 * @see alpha.model.AlphaExpression#getZ__internal_cache_contextDom()
	 * @see #getAlphaExpression()
	 * @generated
	 */
	EAttribute getAlphaExpression_Z__internal_cache_contextDom();

	/**
	 * Returns the meta object for the attribute list '{@link alpha.model.AlphaExpression#getExpressionID <em>Expression ID</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Expression ID</em>'.
	 * @see alpha.model.AlphaExpression#getExpressionID()
	 * @see #getAlphaExpression()
	 * @generated
	 */
	EAttribute getAlphaExpression_ExpressionID();

	/**
	 * Returns the meta object for class '{@link alpha.model.RestrictExpression <em>Restrict Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Restrict Expression</em>'.
	 * @see alpha.model.RestrictExpression
	 * @generated
	 */
	EClass getRestrictExpression();

	/**
	 * Returns the meta object for the containment reference '{@link alpha.model.RestrictExpression#getDomainExpr <em>Domain Expr</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Domain Expr</em>'.
	 * @see alpha.model.RestrictExpression#getDomainExpr()
	 * @see #getRestrictExpression()
	 * @generated
	 */
	EReference getRestrictExpression_DomainExpr();

	/**
	 * Returns the meta object for the containment reference '{@link alpha.model.RestrictExpression#getExpr <em>Expr</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Expr</em>'.
	 * @see alpha.model.RestrictExpression#getExpr()
	 * @see #getRestrictExpression()
	 * @generated
	 */
	EReference getRestrictExpression_Expr();

	/**
	 * Returns the meta object for class '{@link alpha.model.AutoRestrictExpression <em>Auto Restrict Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Auto Restrict Expression</em>'.
	 * @see alpha.model.AutoRestrictExpression
	 * @generated
	 */
	EClass getAutoRestrictExpression();

	/**
	 * Returns the meta object for the containment reference '{@link alpha.model.AutoRestrictExpression#getExpr <em>Expr</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Expr</em>'.
	 * @see alpha.model.AutoRestrictExpression#getExpr()
	 * @see #getAutoRestrictExpression()
	 * @generated
	 */
	EReference getAutoRestrictExpression_Expr();

	/**
	 * Returns the meta object for the attribute '{@link alpha.model.AutoRestrictExpression#getZ__internal_cache_inferredDomain <em>Zinternal cache inferred Domain</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Zinternal cache inferred Domain</em>'.
	 * @see alpha.model.AutoRestrictExpression#getZ__internal_cache_inferredDomain()
	 * @see #getAutoRestrictExpression()
	 * @generated
	 */
	EAttribute getAutoRestrictExpression_Z__internal_cache_inferredDomain();

	/**
	 * Returns the meta object for class '{@link alpha.model.CaseExpression <em>Case Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Case Expression</em>'.
	 * @see alpha.model.CaseExpression
	 * @generated
	 */
	EClass getCaseExpression();

	/**
	 * Returns the meta object for the attribute '{@link alpha.model.CaseExpression#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see alpha.model.CaseExpression#getName()
	 * @see #getCaseExpression()
	 * @generated
	 */
	EAttribute getCaseExpression_Name();

	/**
	 * Returns the meta object for the containment reference list '{@link alpha.model.CaseExpression#getExprs <em>Exprs</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Exprs</em>'.
	 * @see alpha.model.CaseExpression#getExprs()
	 * @see #getCaseExpression()
	 * @generated
	 */
	EReference getCaseExpression_Exprs();

	/**
	 * Returns the meta object for class '{@link alpha.model.DependenceExpression <em>Dependence Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Dependence Expression</em>'.
	 * @see alpha.model.DependenceExpression
	 * @generated
	 */
	EClass getDependenceExpression();

	/**
	 * Returns the meta object for the containment reference '{@link alpha.model.DependenceExpression#getFunctionExpr <em>Function Expr</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Function Expr</em>'.
	 * @see alpha.model.DependenceExpression#getFunctionExpr()
	 * @see #getDependenceExpression()
	 * @generated
	 */
	EReference getDependenceExpression_FunctionExpr();

	/**
	 * Returns the meta object for the containment reference '{@link alpha.model.DependenceExpression#getExpr <em>Expr</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Expr</em>'.
	 * @see alpha.model.DependenceExpression#getExpr()
	 * @see #getDependenceExpression()
	 * @generated
	 */
	EReference getDependenceExpression_Expr();

	/**
	 * Returns the meta object for class '{@link alpha.model.FuzzyDependenceExpression <em>Fuzzy Dependence Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Fuzzy Dependence Expression</em>'.
	 * @see alpha.model.FuzzyDependenceExpression
	 * @generated
	 */
	EClass getFuzzyDependenceExpression();

	/**
	 * Returns the meta object for the containment reference '{@link alpha.model.FuzzyDependenceExpression#getFuzzyFunction <em>Fuzzy Function</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Fuzzy Function</em>'.
	 * @see alpha.model.FuzzyDependenceExpression#getFuzzyFunction()
	 * @see #getFuzzyDependenceExpression()
	 * @generated
	 */
	EReference getFuzzyDependenceExpression_FuzzyFunction();

	/**
	 * Returns the meta object for the containment reference '{@link alpha.model.FuzzyDependenceExpression#getExpr <em>Expr</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Expr</em>'.
	 * @see alpha.model.FuzzyDependenceExpression#getExpr()
	 * @see #getFuzzyDependenceExpression()
	 * @generated
	 */
	EReference getFuzzyDependenceExpression_Expr();

	/**
	 * Returns the meta object for class '{@link alpha.model.IfExpression <em>If Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>If Expression</em>'.
	 * @see alpha.model.IfExpression
	 * @generated
	 */
	EClass getIfExpression();

	/**
	 * Returns the meta object for the containment reference '{@link alpha.model.IfExpression#getCondExpr <em>Cond Expr</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Cond Expr</em>'.
	 * @see alpha.model.IfExpression#getCondExpr()
	 * @see #getIfExpression()
	 * @generated
	 */
	EReference getIfExpression_CondExpr();

	/**
	 * Returns the meta object for the containment reference '{@link alpha.model.IfExpression#getThenExpr <em>Then Expr</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Then Expr</em>'.
	 * @see alpha.model.IfExpression#getThenExpr()
	 * @see #getIfExpression()
	 * @generated
	 */
	EReference getIfExpression_ThenExpr();

	/**
	 * Returns the meta object for the containment reference '{@link alpha.model.IfExpression#getElseExpr <em>Else Expr</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Else Expr</em>'.
	 * @see alpha.model.IfExpression#getElseExpr()
	 * @see #getIfExpression()
	 * @generated
	 */
	EReference getIfExpression_ElseExpr();

	/**
	 * Returns the meta object for class '{@link alpha.model.IndexExpression <em>Index Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Index Expression</em>'.
	 * @see alpha.model.IndexExpression
	 * @generated
	 */
	EClass getIndexExpression();

	/**
	 * Returns the meta object for the containment reference '{@link alpha.model.IndexExpression#getFunctionExpr <em>Function Expr</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Function Expr</em>'.
	 * @see alpha.model.IndexExpression#getFunctionExpr()
	 * @see #getIndexExpression()
	 * @generated
	 */
	EReference getIndexExpression_FunctionExpr();

	/**
	 * Returns the meta object for class '{@link alpha.model.PolynomialIndexExpression <em>Polynomial Index Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Polynomial Index Expression</em>'.
	 * @see alpha.model.PolynomialIndexExpression
	 * @generated
	 */
	EClass getPolynomialIndexExpression();

	/**
	 * Returns the meta object for the containment reference '{@link alpha.model.PolynomialIndexExpression#getPolynomialExpr <em>Polynomial Expr</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Polynomial Expr</em>'.
	 * @see alpha.model.PolynomialIndexExpression#getPolynomialExpr()
	 * @see #getPolynomialIndexExpression()
	 * @generated
	 */
	EReference getPolynomialIndexExpression_PolynomialExpr();

	/**
	 * Returns the meta object for class '{@link alpha.model.FuzzyIndexExpression <em>Fuzzy Index Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Fuzzy Index Expression</em>'.
	 * @see alpha.model.FuzzyIndexExpression
	 * @generated
	 */
	EClass getFuzzyIndexExpression();

	/**
	 * Returns the meta object for the containment reference '{@link alpha.model.FuzzyIndexExpression#getFuzzyFunction <em>Fuzzy Function</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Fuzzy Function</em>'.
	 * @see alpha.model.FuzzyIndexExpression#getFuzzyFunction()
	 * @see #getFuzzyIndexExpression()
	 * @generated
	 */
	EReference getFuzzyIndexExpression_FuzzyFunction();

	/**
	 * Returns the meta object for class '{@link alpha.model.AbstractReduceExpression <em>Abstract Reduce Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Abstract Reduce Expression</em>'.
	 * @see alpha.model.AbstractReduceExpression
	 * @generated
	 */
	EClass getAbstractReduceExpression();

	/**
	 * Returns the meta object for the attribute '{@link alpha.model.AbstractReduceExpression#getOperator <em>Operator</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Operator</em>'.
	 * @see alpha.model.AbstractReduceExpression#getOperator()
	 * @see #getAbstractReduceExpression()
	 * @generated
	 */
	EAttribute getAbstractReduceExpression_Operator();

	/**
	 * Returns the meta object for the containment reference '{@link alpha.model.AbstractReduceExpression#getProjectionExpr <em>Projection Expr</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Projection Expr</em>'.
	 * @see alpha.model.AbstractReduceExpression#getProjectionExpr()
	 * @see #getAbstractReduceExpression()
	 * @generated
	 */
	EReference getAbstractReduceExpression_ProjectionExpr();

	/**
	 * Returns the meta object for the containment reference '{@link alpha.model.AbstractReduceExpression#getBody <em>Body</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Body</em>'.
	 * @see alpha.model.AbstractReduceExpression#getBody()
	 * @see #getAbstractReduceExpression()
	 * @generated
	 */
	EReference getAbstractReduceExpression_Body();

	/**
	 * Returns the meta object for the attribute '{@link alpha.model.AbstractReduceExpression#getZ__internal_facet <em>Zinternal facet</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Zinternal facet</em>'.
	 * @see alpha.model.AbstractReduceExpression#getZ__internal_facet()
	 * @see #getAbstractReduceExpression()
	 * @generated
	 */
	EAttribute getAbstractReduceExpression_Z__internal_facet();

	/**
	 * Returns the meta object for the attribute '{@link alpha.model.AbstractReduceExpression#getNbFreeDimensionsInBody <em>Nb Free Dimensions In Body</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Nb Free Dimensions In Body</em>'.
	 * @see alpha.model.AbstractReduceExpression#getNbFreeDimensionsInBody()
	 * @see #getAbstractReduceExpression()
	 * @generated
	 */
	EAttribute getAbstractReduceExpression_NbFreeDimensionsInBody();

	/**
	 * Returns the meta object for the attribute '{@link alpha.model.AbstractReduceExpression#getZ__fractalSimplify <em>Zfractal Simplify</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Zfractal Simplify</em>'.
	 * @see alpha.model.AbstractReduceExpression#getZ__fractalSimplify()
	 * @see #getAbstractReduceExpression()
	 * @generated
	 */
	EAttribute getAbstractReduceExpression_Z__fractalSimplify();

	/**
	 * Returns the meta object for class '{@link alpha.model.ReduceExpression <em>Reduce Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Reduce Expression</em>'.
	 * @see alpha.model.ReduceExpression
	 * @generated
	 */
	EClass getReduceExpression();

	/**
	 * Returns the meta object for class '{@link alpha.model.ExternalReduceExpression <em>External Reduce Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>External Reduce Expression</em>'.
	 * @see alpha.model.ExternalReduceExpression
	 * @generated
	 */
	EClass getExternalReduceExpression();

	/**
	 * Returns the meta object for the reference '{@link alpha.model.ExternalReduceExpression#getExternalFunction <em>External Function</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>External Function</em>'.
	 * @see alpha.model.ExternalReduceExpression#getExternalFunction()
	 * @see #getExternalReduceExpression()
	 * @generated
	 */
	EReference getExternalReduceExpression_ExternalFunction();

	/**
	 * Returns the meta object for class '{@link alpha.model.ArgReduceExpression <em>Arg Reduce Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Arg Reduce Expression</em>'.
	 * @see alpha.model.ArgReduceExpression
	 * @generated
	 */
	EClass getArgReduceExpression();

	/**
	 * Returns the meta object for class '{@link alpha.model.ExternalArgReduceExpression <em>External Arg Reduce Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>External Arg Reduce Expression</em>'.
	 * @see alpha.model.ExternalArgReduceExpression
	 * @generated
	 */
	EClass getExternalArgReduceExpression();

	/**
	 * Returns the meta object for the reference '{@link alpha.model.ExternalArgReduceExpression#getExternalFunction <em>External Function</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>External Function</em>'.
	 * @see alpha.model.ExternalArgReduceExpression#getExternalFunction()
	 * @see #getExternalArgReduceExpression()
	 * @generated
	 */
	EReference getExternalArgReduceExpression_ExternalFunction();

	/**
	 * Returns the meta object for class '{@link alpha.model.ConvolutionExpression <em>Convolution Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Convolution Expression</em>'.
	 * @see alpha.model.ConvolutionExpression
	 * @generated
	 */
	EClass getConvolutionExpression();

	/**
	 * Returns the meta object for the containment reference '{@link alpha.model.ConvolutionExpression#getKernelDomainExpr <em>Kernel Domain Expr</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Kernel Domain Expr</em>'.
	 * @see alpha.model.ConvolutionExpression#getKernelDomainExpr()
	 * @see #getConvolutionExpression()
	 * @generated
	 */
	EReference getConvolutionExpression_KernelDomainExpr();

	/**
	 * Returns the meta object for the containment reference '{@link alpha.model.ConvolutionExpression#getKernelExpression <em>Kernel Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Kernel Expression</em>'.
	 * @see alpha.model.ConvolutionExpression#getKernelExpression()
	 * @see #getConvolutionExpression()
	 * @generated
	 */
	EReference getConvolutionExpression_KernelExpression();

	/**
	 * Returns the meta object for the containment reference '{@link alpha.model.ConvolutionExpression#getDataExpression <em>Data Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Data Expression</em>'.
	 * @see alpha.model.ConvolutionExpression#getDataExpression()
	 * @see #getConvolutionExpression()
	 * @generated
	 */
	EReference getConvolutionExpression_DataExpression();

	/**
	 * Returns the meta object for class '{@link alpha.model.SelectExpression <em>Select Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Select Expression</em>'.
	 * @see alpha.model.SelectExpression
	 * @generated
	 */
	EClass getSelectExpression();

	/**
	 * Returns the meta object for the containment reference '{@link alpha.model.SelectExpression#getRelationExpr <em>Relation Expr</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Relation Expr</em>'.
	 * @see alpha.model.SelectExpression#getRelationExpr()
	 * @see #getSelectExpression()
	 * @generated
	 */
	EReference getSelectExpression_RelationExpr();

	/**
	 * Returns the meta object for the containment reference '{@link alpha.model.SelectExpression#getExpr <em>Expr</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Expr</em>'.
	 * @see alpha.model.SelectExpression#getExpr()
	 * @see #getSelectExpression()
	 * @generated
	 */
	EReference getSelectExpression_Expr();

	/**
	 * Returns the meta object for class '{@link alpha.model.VariableExpression <em>Variable Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Variable Expression</em>'.
	 * @see alpha.model.VariableExpression
	 * @generated
	 */
	EClass getVariableExpression();

	/**
	 * Returns the meta object for the reference '{@link alpha.model.VariableExpression#getVariable <em>Variable</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Variable</em>'.
	 * @see alpha.model.VariableExpression#getVariable()
	 * @see #getVariableExpression()
	 * @generated
	 */
	EReference getVariableExpression_Variable();

	/**
	 * Returns the meta object for class '{@link alpha.model.ConstantExpression <em>Constant Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Constant Expression</em>'.
	 * @see alpha.model.ConstantExpression
	 * @generated
	 */
	EClass getConstantExpression();

	/**
	 * Returns the meta object for class '{@link alpha.model.IntegerExpression <em>Integer Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Integer Expression</em>'.
	 * @see alpha.model.IntegerExpression
	 * @generated
	 */
	EClass getIntegerExpression();

	/**
	 * Returns the meta object for the attribute '{@link alpha.model.IntegerExpression#getValue <em>Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Value</em>'.
	 * @see alpha.model.IntegerExpression#getValue()
	 * @see #getIntegerExpression()
	 * @generated
	 */
	EAttribute getIntegerExpression_Value();

	/**
	 * Returns the meta object for class '{@link alpha.model.RealExpression <em>Real Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Real Expression</em>'.
	 * @see alpha.model.RealExpression
	 * @generated
	 */
	EClass getRealExpression();

	/**
	 * Returns the meta object for the attribute '{@link alpha.model.RealExpression#getValue <em>Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Value</em>'.
	 * @see alpha.model.RealExpression#getValue()
	 * @see #getRealExpression()
	 * @generated
	 */
	EAttribute getRealExpression_Value();

	/**
	 * Returns the meta object for class '{@link alpha.model.BooleanExpression <em>Boolean Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Boolean Expression</em>'.
	 * @see alpha.model.BooleanExpression
	 * @generated
	 */
	EClass getBooleanExpression();

	/**
	 * Returns the meta object for the attribute '{@link alpha.model.BooleanExpression#getValue <em>Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Value</em>'.
	 * @see alpha.model.BooleanExpression#getValue()
	 * @see #getBooleanExpression()
	 * @generated
	 */
	EAttribute getBooleanExpression_Value();

	/**
	 * Returns the meta object for class '{@link alpha.model.UnaryExpression <em>Unary Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Unary Expression</em>'.
	 * @see alpha.model.UnaryExpression
	 * @generated
	 */
	EClass getUnaryExpression();

	/**
	 * Returns the meta object for the attribute '{@link alpha.model.UnaryExpression#getOperator <em>Operator</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Operator</em>'.
	 * @see alpha.model.UnaryExpression#getOperator()
	 * @see #getUnaryExpression()
	 * @generated
	 */
	EAttribute getUnaryExpression_Operator();

	/**
	 * Returns the meta object for the containment reference '{@link alpha.model.UnaryExpression#getExpr <em>Expr</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Expr</em>'.
	 * @see alpha.model.UnaryExpression#getExpr()
	 * @see #getUnaryExpression()
	 * @generated
	 */
	EReference getUnaryExpression_Expr();

	/**
	 * Returns the meta object for class '{@link alpha.model.BinaryExpression <em>Binary Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Binary Expression</em>'.
	 * @see alpha.model.BinaryExpression
	 * @generated
	 */
	EClass getBinaryExpression();

	/**
	 * Returns the meta object for the attribute '{@link alpha.model.BinaryExpression#getOperator <em>Operator</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Operator</em>'.
	 * @see alpha.model.BinaryExpression#getOperator()
	 * @see #getBinaryExpression()
	 * @generated
	 */
	EAttribute getBinaryExpression_Operator();

	/**
	 * Returns the meta object for the containment reference '{@link alpha.model.BinaryExpression#getLeft <em>Left</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Left</em>'.
	 * @see alpha.model.BinaryExpression#getLeft()
	 * @see #getBinaryExpression()
	 * @generated
	 */
	EReference getBinaryExpression_Left();

	/**
	 * Returns the meta object for the containment reference '{@link alpha.model.BinaryExpression#getRight <em>Right</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Right</em>'.
	 * @see alpha.model.BinaryExpression#getRight()
	 * @see #getBinaryExpression()
	 * @generated
	 */
	EReference getBinaryExpression_Right();

	/**
	 * Returns the meta object for class '{@link alpha.model.MultiArgExpression <em>Multi Arg Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Multi Arg Expression</em>'.
	 * @see alpha.model.MultiArgExpression
	 * @generated
	 */
	EClass getMultiArgExpression();

	/**
	 * Returns the meta object for the attribute '{@link alpha.model.MultiArgExpression#getOperator <em>Operator</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Operator</em>'.
	 * @see alpha.model.MultiArgExpression#getOperator()
	 * @see #getMultiArgExpression()
	 * @generated
	 */
	EAttribute getMultiArgExpression_Operator();

	/**
	 * Returns the meta object for the containment reference list '{@link alpha.model.MultiArgExpression#getExprs <em>Exprs</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Exprs</em>'.
	 * @see alpha.model.MultiArgExpression#getExprs()
	 * @see #getMultiArgExpression()
	 * @generated
	 */
	EReference getMultiArgExpression_Exprs();

	/**
	 * Returns the meta object for class '{@link alpha.model.ExternalMultiArgExpression <em>External Multi Arg Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>External Multi Arg Expression</em>'.
	 * @see alpha.model.ExternalMultiArgExpression
	 * @generated
	 */
	EClass getExternalMultiArgExpression();

	/**
	 * Returns the meta object for the reference '{@link alpha.model.ExternalMultiArgExpression#getExternalFunction <em>External Function</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>External Function</em>'.
	 * @see alpha.model.ExternalMultiArgExpression#getExternalFunction()
	 * @see #getExternalMultiArgExpression()
	 * @generated
	 */
	EReference getExternalMultiArgExpression_ExternalFunction();

	/**
	 * Returns the meta object for class '{@link alpha.model.AbstractFuzzyReduceExpression <em>Abstract Fuzzy Reduce Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Abstract Fuzzy Reduce Expression</em>'.
	 * @see alpha.model.AbstractFuzzyReduceExpression
	 * @generated
	 */
	EClass getAbstractFuzzyReduceExpression();

	/**
	 * Returns the meta object for the attribute '{@link alpha.model.AbstractFuzzyReduceExpression#getOperator <em>Operator</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Operator</em>'.
	 * @see alpha.model.AbstractFuzzyReduceExpression#getOperator()
	 * @see #getAbstractFuzzyReduceExpression()
	 * @generated
	 */
	EAttribute getAbstractFuzzyReduceExpression_Operator();

	/**
	 * Returns the meta object for the containment reference '{@link alpha.model.AbstractFuzzyReduceExpression#getProjectionFunction <em>Projection Function</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Projection Function</em>'.
	 * @see alpha.model.AbstractFuzzyReduceExpression#getProjectionFunction()
	 * @see #getAbstractFuzzyReduceExpression()
	 * @generated
	 */
	EReference getAbstractFuzzyReduceExpression_ProjectionFunction();

	/**
	 * Returns the meta object for the containment reference '{@link alpha.model.AbstractFuzzyReduceExpression#getBody <em>Body</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Body</em>'.
	 * @see alpha.model.AbstractFuzzyReduceExpression#getBody()
	 * @see #getAbstractFuzzyReduceExpression()
	 * @generated
	 */
	EReference getAbstractFuzzyReduceExpression_Body();

	/**
	 * Returns the meta object for class '{@link alpha.model.FuzzyReduceExpression <em>Fuzzy Reduce Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Fuzzy Reduce Expression</em>'.
	 * @see alpha.model.FuzzyReduceExpression
	 * @generated
	 */
	EClass getFuzzyReduceExpression();

	/**
	 * Returns the meta object for class '{@link alpha.model.ExternalFuzzyReduceExpression <em>External Fuzzy Reduce Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>External Fuzzy Reduce Expression</em>'.
	 * @see alpha.model.ExternalFuzzyReduceExpression
	 * @generated
	 */
	EClass getExternalFuzzyReduceExpression();

	/**
	 * Returns the meta object for the reference '{@link alpha.model.ExternalFuzzyReduceExpression#getExternalFunction <em>External Function</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>External Function</em>'.
	 * @see alpha.model.ExternalFuzzyReduceExpression#getExternalFunction()
	 * @see #getExternalFuzzyReduceExpression()
	 * @generated
	 */
	EReference getExternalFuzzyReduceExpression_ExternalFunction();

	/**
	 * Returns the meta object for class '{@link alpha.model.FuzzyArgReduceExpression <em>Fuzzy Arg Reduce Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Fuzzy Arg Reduce Expression</em>'.
	 * @see alpha.model.FuzzyArgReduceExpression
	 * @generated
	 */
	EClass getFuzzyArgReduceExpression();

	/**
	 * Returns the meta object for class '{@link alpha.model.ExternalFuzzyArgReduceExpression <em>External Fuzzy Arg Reduce Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>External Fuzzy Arg Reduce Expression</em>'.
	 * @see alpha.model.ExternalFuzzyArgReduceExpression
	 * @generated
	 */
	EClass getExternalFuzzyArgReduceExpression();

	/**
	 * Returns the meta object for the reference '{@link alpha.model.ExternalFuzzyArgReduceExpression#getExternalFunction <em>External Function</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>External Function</em>'.
	 * @see alpha.model.ExternalFuzzyArgReduceExpression#getExternalFunction()
	 * @see #getExternalFuzzyArgReduceExpression()
	 * @generated
	 */
	EReference getExternalFuzzyArgReduceExpression_ExternalFunction();

	/**
	 * Returns the meta object for class '{@link alpha.model.CalculatorNode <em>Calculator Node</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Calculator Node</em>'.
	 * @see alpha.model.CalculatorNode
	 * @generated
	 */
	EClass getCalculatorNode();

	/**
	 * Returns the meta object for class '{@link alpha.model.PolyhedralObject <em>Polyhedral Object</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Polyhedral Object</em>'.
	 * @see alpha.model.PolyhedralObject
	 * @generated
	 */
	EClass getPolyhedralObject();

	/**
	 * Returns the meta object for the attribute '{@link alpha.model.PolyhedralObject#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see alpha.model.PolyhedralObject#getName()
	 * @see #getPolyhedralObject()
	 * @generated
	 */
	EAttribute getPolyhedralObject_Name();

	/**
	 * Returns the meta object for the containment reference '{@link alpha.model.PolyhedralObject#getExpr <em>Expr</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Expr</em>'.
	 * @see alpha.model.PolyhedralObject#getExpr()
	 * @see #getPolyhedralObject()
	 * @generated
	 */
	EReference getPolyhedralObject_Expr();

	/**
	 * Returns the meta object for class '{@link alpha.model.CalculatorExpression <em>Calculator Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Calculator Expression</em>'.
	 * @see alpha.model.CalculatorExpression
	 * @generated
	 */
	EClass getCalculatorExpression();

	/**
	 * Returns the meta object for class '{@link alpha.model.JNIDomain <em>JNI Domain</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>JNI Domain</em>'.
	 * @see alpha.model.JNIDomain
	 * @generated
	 */
	EClass getJNIDomain();

	/**
	 * Returns the meta object for the attribute '{@link alpha.model.JNIDomain#getIslString <em>Isl String</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Isl String</em>'.
	 * @see alpha.model.JNIDomain#getIslString()
	 * @see #getJNIDomain()
	 * @generated
	 */
	EAttribute getJNIDomain_IslString();

	/**
	 * Returns the meta object for the attribute '{@link alpha.model.JNIDomain#getZ__internal_cache_islSet <em>Zinternal cache isl Set</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Zinternal cache isl Set</em>'.
	 * @see alpha.model.JNIDomain#getZ__internal_cache_islSet()
	 * @see #getJNIDomain()
	 * @generated
	 */
	EAttribute getJNIDomain_Z__internal_cache_islSet();

	/**
	 * Returns the meta object for class '{@link alpha.model.JNIDomainInArrayNotation <em>JNI Domain In Array Notation</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>JNI Domain In Array Notation</em>'.
	 * @see alpha.model.JNIDomainInArrayNotation
	 * @generated
	 */
	EClass getJNIDomainInArrayNotation();

	/**
	 * Returns the meta object for class '{@link alpha.model.JNIRelation <em>JNI Relation</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>JNI Relation</em>'.
	 * @see alpha.model.JNIRelation
	 * @generated
	 */
	EClass getJNIRelation();

	/**
	 * Returns the meta object for the attribute '{@link alpha.model.JNIRelation#getIslString <em>Isl String</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Isl String</em>'.
	 * @see alpha.model.JNIRelation#getIslString()
	 * @see #getJNIRelation()
	 * @generated
	 */
	EAttribute getJNIRelation_IslString();

	/**
	 * Returns the meta object for the attribute '{@link alpha.model.JNIRelation#getZ__internal_cache_islMap <em>Zinternal cache isl Map</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Zinternal cache isl Map</em>'.
	 * @see alpha.model.JNIRelation#getZ__internal_cache_islMap()
	 * @see #getJNIRelation()
	 * @generated
	 */
	EAttribute getJNIRelation_Z__internal_cache_islMap();

	/**
	 * Returns the meta object for class '{@link alpha.model.JNIFunction <em>JNI Function</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>JNI Function</em>'.
	 * @see alpha.model.JNIFunction
	 * @generated
	 */
	EClass getJNIFunction();

	/**
	 * Returns the meta object for the containment reference '{@link alpha.model.JNIFunction#getAlphaFunction <em>Alpha Function</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Alpha Function</em>'.
	 * @see alpha.model.JNIFunction#getAlphaFunction()
	 * @see #getJNIFunction()
	 * @generated
	 */
	EReference getJNIFunction_AlphaFunction();

	/**
	 * Returns the meta object for the attribute '{@link alpha.model.JNIFunction#getZ__internal_cache_islMAff <em>Zinternal cache isl MAff</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Zinternal cache isl MAff</em>'.
	 * @see alpha.model.JNIFunction#getZ__internal_cache_islMAff()
	 * @see #getJNIFunction()
	 * @generated
	 */
	EAttribute getJNIFunction_Z__internal_cache_islMAff();

	/**
	 * Returns the meta object for class '{@link alpha.model.JNIFunctionInArrayNotation <em>JNI Function In Array Notation</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>JNI Function In Array Notation</em>'.
	 * @see alpha.model.JNIFunctionInArrayNotation
	 * @generated
	 */
	EClass getJNIFunctionInArrayNotation();

	/**
	 * Returns the meta object for the attribute list '{@link alpha.model.JNIFunctionInArrayNotation#getArrayNotation <em>Array Notation</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Array Notation</em>'.
	 * @see alpha.model.JNIFunctionInArrayNotation#getArrayNotation()
	 * @see #getJNIFunctionInArrayNotation()
	 * @generated
	 */
	EAttribute getJNIFunctionInArrayNotation_ArrayNotation();

	/**
	 * Returns the meta object for class '{@link alpha.model.JNIPolynomial <em>JNI Polynomial</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>JNI Polynomial</em>'.
	 * @see alpha.model.JNIPolynomial
	 * @generated
	 */
	EClass getJNIPolynomial();

	/**
	 * Returns the meta object for the attribute '{@link alpha.model.JNIPolynomial#getIslString <em>Isl String</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Isl String</em>'.
	 * @see alpha.model.JNIPolynomial#getIslString()
	 * @see #getJNIPolynomial()
	 * @generated
	 */
	EAttribute getJNIPolynomial_IslString();

	/**
	 * Returns the meta object for the attribute '{@link alpha.model.JNIPolynomial#getZ__internal_cache_islPWQP <em>Zinternal cache isl PWQP</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Zinternal cache isl PWQP</em>'.
	 * @see alpha.model.JNIPolynomial#getZ__internal_cache_islPWQP()
	 * @see #getJNIPolynomial()
	 * @generated
	 */
	EAttribute getJNIPolynomial_Z__internal_cache_islPWQP();

	/**
	 * Returns the meta object for class '{@link alpha.model.JNIPolynomialInArrayNotation <em>JNI Polynomial In Array Notation</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>JNI Polynomial In Array Notation</em>'.
	 * @see alpha.model.JNIPolynomialInArrayNotation
	 * @generated
	 */
	EClass getJNIPolynomialInArrayNotation();

	/**
	 * Returns the meta object for the attribute list '{@link alpha.model.JNIPolynomialInArrayNotation#getArrayNotation <em>Array Notation</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Array Notation</em>'.
	 * @see alpha.model.JNIPolynomialInArrayNotation#getArrayNotation()
	 * @see #getJNIPolynomialInArrayNotation()
	 * @generated
	 */
	EAttribute getJNIPolynomialInArrayNotation_ArrayNotation();

	/**
	 * Returns the meta object for class '{@link alpha.model.FuzzyFunction <em>Fuzzy Function</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Fuzzy Function</em>'.
	 * @see alpha.model.FuzzyFunction
	 * @generated
	 */
	EClass getFuzzyFunction();

	/**
	 * Returns the meta object for the attribute '{@link alpha.model.FuzzyFunction#getAlphaString <em>Alpha String</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Alpha String</em>'.
	 * @see alpha.model.FuzzyFunction#getAlphaString()
	 * @see #getFuzzyFunction()
	 * @generated
	 */
	EAttribute getFuzzyFunction_AlphaString();

	/**
	 * Returns the meta object for the containment reference list '{@link alpha.model.FuzzyFunction#getIndirections <em>Indirections</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Indirections</em>'.
	 * @see alpha.model.FuzzyFunction#getIndirections()
	 * @see #getFuzzyFunction()
	 * @generated
	 */
	EReference getFuzzyFunction_Indirections();

	/**
	 * Returns the meta object for the attribute '{@link alpha.model.FuzzyFunction#getZ__internal_cache_fuzzyMap <em>Zinternal cache fuzzy Map</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Zinternal cache fuzzy Map</em>'.
	 * @see alpha.model.FuzzyFunction#getZ__internal_cache_fuzzyMap()
	 * @see #getFuzzyFunction()
	 * @generated
	 */
	EAttribute getFuzzyFunction_Z__internal_cache_fuzzyMap();

	/**
	 * Returns the meta object for the attribute '{@link alpha.model.FuzzyFunction#getZ__internal_cache_depRelation <em>Zinternal cache dep Relation</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Zinternal cache dep Relation</em>'.
	 * @see alpha.model.FuzzyFunction#getZ__internal_cache_depRelation()
	 * @see #getFuzzyFunction()
	 * @generated
	 */
	EAttribute getFuzzyFunction_Z__internal_cache_depRelation();

	/**
	 * Returns the meta object for class '{@link alpha.model.FuzzyVariableUse <em>Fuzzy Variable Use</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Fuzzy Variable Use</em>'.
	 * @see alpha.model.FuzzyVariableUse
	 * @generated
	 */
	EClass getFuzzyVariableUse();

	/**
	 * Returns the meta object for the attribute '{@link alpha.model.FuzzyVariableUse#getFuzzyIndex <em>Fuzzy Index</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Fuzzy Index</em>'.
	 * @see alpha.model.FuzzyVariableUse#getFuzzyIndex()
	 * @see #getFuzzyVariableUse()
	 * @generated
	 */
	EAttribute getFuzzyVariableUse_FuzzyIndex();

	/**
	 * Returns the meta object for the reference '{@link alpha.model.FuzzyVariableUse#getFuzzyVariable <em>Fuzzy Variable</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Fuzzy Variable</em>'.
	 * @see alpha.model.FuzzyVariableUse#getFuzzyVariable()
	 * @see #getFuzzyVariableUse()
	 * @generated
	 */
	EReference getFuzzyVariableUse_FuzzyVariable();

	/**
	 * Returns the meta object for class '{@link alpha.model.NestedFuzzyFunction <em>Nested Fuzzy Function</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Nested Fuzzy Function</em>'.
	 * @see alpha.model.NestedFuzzyFunction
	 * @generated
	 */
	EClass getNestedFuzzyFunction();

	/**
	 * Returns the meta object for class '{@link alpha.model.AffineFuzzyVariableUse <em>Affine Fuzzy Variable Use</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Affine Fuzzy Variable Use</em>'.
	 * @see alpha.model.AffineFuzzyVariableUse
	 * @generated
	 */
	EClass getAffineFuzzyVariableUse();

	/**
	 * Returns the meta object for the containment reference '{@link alpha.model.AffineFuzzyVariableUse#getUseFunction <em>Use Function</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Use Function</em>'.
	 * @see alpha.model.AffineFuzzyVariableUse#getUseFunction()
	 * @see #getAffineFuzzyVariableUse()
	 * @generated
	 */
	EReference getAffineFuzzyVariableUse_UseFunction();

	/**
	 * Returns the meta object for class '{@link alpha.model.FuzzyFunctionInArrayNotation <em>Fuzzy Function In Array Notation</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Fuzzy Function In Array Notation</em>'.
	 * @see alpha.model.FuzzyFunctionInArrayNotation
	 * @generated
	 */
	EClass getFuzzyFunctionInArrayNotation();

	/**
	 * Returns the meta object for the attribute list '{@link alpha.model.FuzzyFunctionInArrayNotation#getArrayNotation <em>Array Notation</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Array Notation</em>'.
	 * @see alpha.model.FuzzyFunctionInArrayNotation#getArrayNotation()
	 * @see #getFuzzyFunctionInArrayNotation()
	 * @generated
	 */
	EAttribute getFuzzyFunctionInArrayNotation_ArrayNotation();

	/**
	 * Returns the meta object for class '{@link alpha.model.UnaryCalculatorExpression <em>Unary Calculator Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Unary Calculator Expression</em>'.
	 * @see alpha.model.UnaryCalculatorExpression
	 * @generated
	 */
	EClass getUnaryCalculatorExpression();

	/**
	 * Returns the meta object for the attribute '{@link alpha.model.UnaryCalculatorExpression#getOperator <em>Operator</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Operator</em>'.
	 * @see alpha.model.UnaryCalculatorExpression#getOperator()
	 * @see #getUnaryCalculatorExpression()
	 * @generated
	 */
	EAttribute getUnaryCalculatorExpression_Operator();

	/**
	 * Returns the meta object for the containment reference '{@link alpha.model.UnaryCalculatorExpression#getExpr <em>Expr</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Expr</em>'.
	 * @see alpha.model.UnaryCalculatorExpression#getExpr()
	 * @see #getUnaryCalculatorExpression()
	 * @generated
	 */
	EReference getUnaryCalculatorExpression_Expr();

	/**
	 * Returns the meta object for the attribute '{@link alpha.model.UnaryCalculatorExpression#getZ__internal_cache_islObject <em>Zinternal cache isl Object</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Zinternal cache isl Object</em>'.
	 * @see alpha.model.UnaryCalculatorExpression#getZ__internal_cache_islObject()
	 * @see #getUnaryCalculatorExpression()
	 * @generated
	 */
	EAttribute getUnaryCalculatorExpression_Z__internal_cache_islObject();

	/**
	 * Returns the meta object for class '{@link alpha.model.BinaryCalculatorExpression <em>Binary Calculator Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Binary Calculator Expression</em>'.
	 * @see alpha.model.BinaryCalculatorExpression
	 * @generated
	 */
	EClass getBinaryCalculatorExpression();

	/**
	 * Returns the meta object for the attribute '{@link alpha.model.BinaryCalculatorExpression#getOperator <em>Operator</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Operator</em>'.
	 * @see alpha.model.BinaryCalculatorExpression#getOperator()
	 * @see #getBinaryCalculatorExpression()
	 * @generated
	 */
	EAttribute getBinaryCalculatorExpression_Operator();

	/**
	 * Returns the meta object for the containment reference '{@link alpha.model.BinaryCalculatorExpression#getLeft <em>Left</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Left</em>'.
	 * @see alpha.model.BinaryCalculatorExpression#getLeft()
	 * @see #getBinaryCalculatorExpression()
	 * @generated
	 */
	EReference getBinaryCalculatorExpression_Left();

	/**
	 * Returns the meta object for the containment reference '{@link alpha.model.BinaryCalculatorExpression#getRight <em>Right</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Right</em>'.
	 * @see alpha.model.BinaryCalculatorExpression#getRight()
	 * @see #getBinaryCalculatorExpression()
	 * @generated
	 */
	EReference getBinaryCalculatorExpression_Right();

	/**
	 * Returns the meta object for the attribute '{@link alpha.model.BinaryCalculatorExpression#getZ__internal_cache_islObject <em>Zinternal cache isl Object</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Zinternal cache isl Object</em>'.
	 * @see alpha.model.BinaryCalculatorExpression#getZ__internal_cache_islObject()
	 * @see #getBinaryCalculatorExpression()
	 * @generated
	 */
	EAttribute getBinaryCalculatorExpression_Z__internal_cache_islObject();

	/**
	 * Returns the meta object for class '{@link alpha.model.VariableDomain <em>Variable Domain</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Variable Domain</em>'.
	 * @see alpha.model.VariableDomain
	 * @generated
	 */
	EClass getVariableDomain();

	/**
	 * Returns the meta object for the reference '{@link alpha.model.VariableDomain#getVariable <em>Variable</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Variable</em>'.
	 * @see alpha.model.VariableDomain#getVariable()
	 * @see #getVariableDomain()
	 * @generated
	 */
	EReference getVariableDomain_Variable();

	/**
	 * Returns the meta object for class '{@link alpha.model.RectangularDomain <em>Rectangular Domain</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Rectangular Domain</em>'.
	 * @see alpha.model.RectangularDomain
	 * @generated
	 */
	EClass getRectangularDomain();

	/**
	 * Returns the meta object for the attribute list '{@link alpha.model.RectangularDomain#getLowerBounds <em>Lower Bounds</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Lower Bounds</em>'.
	 * @see alpha.model.RectangularDomain#getLowerBounds()
	 * @see #getRectangularDomain()
	 * @generated
	 */
	EAttribute getRectangularDomain_LowerBounds();

	/**
	 * Returns the meta object for the attribute list '{@link alpha.model.RectangularDomain#getUpperBounds <em>Upper Bounds</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Upper Bounds</em>'.
	 * @see alpha.model.RectangularDomain#getUpperBounds()
	 * @see #getRectangularDomain()
	 * @generated
	 */
	EAttribute getRectangularDomain_UpperBounds();

	/**
	 * Returns the meta object for the attribute list '{@link alpha.model.RectangularDomain#getIndexNames <em>Index Names</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Index Names</em>'.
	 * @see alpha.model.RectangularDomain#getIndexNames()
	 * @see #getRectangularDomain()
	 * @generated
	 */
	EAttribute getRectangularDomain_IndexNames();

	/**
	 * Returns the meta object for the attribute '{@link alpha.model.RectangularDomain#getZ__internal_cache_islSet <em>Zinternal cache isl Set</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Zinternal cache isl Set</em>'.
	 * @see alpha.model.RectangularDomain#getZ__internal_cache_islSet()
	 * @see #getRectangularDomain()
	 * @generated
	 */
	EAttribute getRectangularDomain_Z__internal_cache_islSet();

	/**
	 * Returns the meta object for class '{@link alpha.model.DefinedObject <em>Defined Object</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Defined Object</em>'.
	 * @see alpha.model.DefinedObject
	 * @generated
	 */
	EClass getDefinedObject();

	/**
	 * Returns the meta object for the reference '{@link alpha.model.DefinedObject#getObject <em>Object</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Object</em>'.
	 * @see alpha.model.DefinedObject#getObject()
	 * @see #getDefinedObject()
	 * @generated
	 */
	EReference getDefinedObject_Object();

	/**
	 * Returns the meta object for the attribute '{@link alpha.model.DefinedObject#getZ__internalCycleDetector <em>Zinternal Cycle Detector</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Zinternal Cycle Detector</em>'.
	 * @see alpha.model.DefinedObject#getZ__internalCycleDetector()
	 * @see #getDefinedObject()
	 * @generated
	 */
	EAttribute getDefinedObject_Z__internalCycleDetector();

	/**
	 * Returns the meta object for class '{@link alpha.model.AlphaFunction <em>Alpha Function</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Alpha Function</em>'.
	 * @see alpha.model.AlphaFunction
	 * @generated
	 */
	EClass getAlphaFunction();

	/**
	 * Returns the meta object for the attribute '{@link alpha.model.AlphaFunction#getIndexList <em>Index List</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Index List</em>'.
	 * @see alpha.model.AlphaFunction#getIndexList()
	 * @see #getAlphaFunction()
	 * @generated
	 */
	EAttribute getAlphaFunction_IndexList();

	/**
	 * Returns the meta object for the containment reference list '{@link alpha.model.AlphaFunction#getExprs <em>Exprs</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Exprs</em>'.
	 * @see alpha.model.AlphaFunction#getExprs()
	 * @see #getAlphaFunction()
	 * @generated
	 */
	EReference getAlphaFunction_Exprs();

	/**
	 * Returns the meta object for class '{@link alpha.model.AlphaFunctionExpression <em>Alpha Function Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Alpha Function Expression</em>'.
	 * @see alpha.model.AlphaFunctionExpression
	 * @generated
	 */
	EClass getAlphaFunctionExpression();

	/**
	 * Returns the meta object for class '{@link alpha.model.AlphaFunctionBinaryExpression <em>Alpha Function Binary Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Alpha Function Binary Expression</em>'.
	 * @see alpha.model.AlphaFunctionBinaryExpression
	 * @generated
	 */
	EClass getAlphaFunctionBinaryExpression();

	/**
	 * Returns the meta object for the containment reference '{@link alpha.model.AlphaFunctionBinaryExpression#getLeft <em>Left</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Left</em>'.
	 * @see alpha.model.AlphaFunctionBinaryExpression#getLeft()
	 * @see #getAlphaFunctionBinaryExpression()
	 * @generated
	 */
	EReference getAlphaFunctionBinaryExpression_Left();

	/**
	 * Returns the meta object for the containment reference '{@link alpha.model.AlphaFunctionBinaryExpression#getRight <em>Right</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Right</em>'.
	 * @see alpha.model.AlphaFunctionBinaryExpression#getRight()
	 * @see #getAlphaFunctionBinaryExpression()
	 * @generated
	 */
	EReference getAlphaFunctionBinaryExpression_Right();

	/**
	 * Returns the meta object for the attribute '{@link alpha.model.AlphaFunctionBinaryExpression#getOperator <em>Operator</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Operator</em>'.
	 * @see alpha.model.AlphaFunctionBinaryExpression#getOperator()
	 * @see #getAlphaFunctionBinaryExpression()
	 * @generated
	 */
	EAttribute getAlphaFunctionBinaryExpression_Operator();

	/**
	 * Returns the meta object for class '{@link alpha.model.AlphaFunctionLiteral <em>Alpha Function Literal</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Alpha Function Literal</em>'.
	 * @see alpha.model.AlphaFunctionLiteral
	 * @generated
	 */
	EClass getAlphaFunctionLiteral();

	/**
	 * Returns the meta object for the attribute '{@link alpha.model.AlphaFunctionLiteral#getValue <em>Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Value</em>'.
	 * @see alpha.model.AlphaFunctionLiteral#getValue()
	 * @see #getAlphaFunctionLiteral()
	 * @generated
	 */
	EAttribute getAlphaFunctionLiteral_Value();

	/**
	 * Returns the meta object for class '{@link alpha.model.AlphaFunctionFloor <em>Alpha Function Floor</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Alpha Function Floor</em>'.
	 * @see alpha.model.AlphaFunctionFloor
	 * @generated
	 */
	EClass getAlphaFunctionFloor();

	/**
	 * Returns the meta object for the containment reference '{@link alpha.model.AlphaFunctionFloor#getExpr <em>Expr</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Expr</em>'.
	 * @see alpha.model.AlphaFunctionFloor#getExpr()
	 * @see #getAlphaFunctionFloor()
	 * @generated
	 */
	EReference getAlphaFunctionFloor_Expr();

	/**
	 * Returns the meta object for enum '{@link alpha.model.UNARY_OP <em>UNARY OP</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>UNARY OP</em>'.
	 * @see alpha.model.UNARY_OP
	 * @generated
	 */
	EEnum getUNARY_OP();

	/**
	 * Returns the meta object for enum '{@link alpha.model.BINARY_OP <em>BINARY OP</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>BINARY OP</em>'.
	 * @see alpha.model.BINARY_OP
	 * @generated
	 */
	EEnum getBINARY_OP();

	/**
	 * Returns the meta object for enum '{@link alpha.model.REDUCTION_OP <em>REDUCTION OP</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>REDUCTION OP</em>'.
	 * @see alpha.model.REDUCTION_OP
	 * @generated
	 */
	EEnum getREDUCTION_OP();

	/**
	 * Returns the meta object for enum '{@link alpha.model.POLY_OBJECT_TYPE <em>POLY OBJECT TYPE</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>POLY OBJECT TYPE</em>'.
	 * @see alpha.model.POLY_OBJECT_TYPE
	 * @generated
	 */
	EEnum getPOLY_OBJECT_TYPE();

	/**
	 * Returns the meta object for enum '{@link alpha.model.CALCULATOR_UNARY_OP <em>CALCULATOR UNARY OP</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>CALCULATOR UNARY OP</em>'.
	 * @see alpha.model.CALCULATOR_UNARY_OP
	 * @generated
	 */
	EEnum getCALCULATOR_UNARY_OP();

	/**
	 * Returns the meta object for enum '{@link alpha.model.CALCULATOR_BINARY_OP <em>CALCULATOR BINARY OP</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>CALCULATOR BINARY OP</em>'.
	 * @see alpha.model.CALCULATOR_BINARY_OP
	 * @generated
	 */
	EEnum getCALCULATOR_BINARY_OP();

	/**
	 * Returns the meta object for data type '{@link fr.irisa.cairn.jnimap.runtime.JNIObject <em>JNI Object</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for data type '<em>JNI Object</em>'.
	 * @see fr.irisa.cairn.jnimap.runtime.JNIObject
	 * @model instanceClass="fr.irisa.cairn.jnimap.runtime.JNIObject"
	 * @generated
	 */
	EDataType getJNIObject();

	/**
	 * Returns the meta object for data type '{@link fr.irisa.cairn.jnimap.isl.ISLSet <em>JNIISL Set</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for data type '<em>JNIISL Set</em>'.
	 * @see fr.irisa.cairn.jnimap.isl.ISLSet
	 * @model instanceClass="fr.irisa.cairn.jnimap.isl.ISLSet"
	 * @generated
	 */
	EDataType getJNIISLSet();

	/**
	 * Returns the meta object for data type '{@link fr.irisa.cairn.jnimap.isl.ISLMap <em>JNIISL Map</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for data type '<em>JNIISL Map</em>'.
	 * @see fr.irisa.cairn.jnimap.isl.ISLMap
	 * @model instanceClass="fr.irisa.cairn.jnimap.isl.ISLMap"
	 * @generated
	 */
	EDataType getJNIISLMap();

	/**
	 * Returns the meta object for data type '{@link fr.irisa.cairn.jnimap.isl.ISLMultiAff <em>JNIISL Multi Aff</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for data type '<em>JNIISL Multi Aff</em>'.
	 * @see fr.irisa.cairn.jnimap.isl.ISLMultiAff
	 * @model instanceClass="fr.irisa.cairn.jnimap.isl.ISLMultiAff"
	 * @generated
	 */
	EDataType getJNIISLMultiAff();

	/**
	 * Returns the meta object for data type '{@link fr.irisa.cairn.jnimap.isl.ISLPWQPolynomial <em>JNIISLPWQ Polynomial</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for data type '<em>JNIISLPWQ Polynomial</em>'.
	 * @see fr.irisa.cairn.jnimap.isl.ISLPWQPolynomial
	 * @model instanceClass="fr.irisa.cairn.jnimap.isl.ISLPWQPolynomial"
	 * @generated
	 */
	EDataType getJNIISLPWQPolynomial();

	/**
	 * Returns the meta object for data type '{@link fr.irisa.cairn.jnimap.isl.ISL_FORMAT <em>ISL FORMAT</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for data type '<em>ISL FORMAT</em>'.
	 * @see fr.irisa.cairn.jnimap.isl.ISL_FORMAT
	 * @model instanceClass="fr.irisa.cairn.jnimap.isl.ISL_FORMAT"
	 * @generated
	 */
	EDataType getISL_FORMAT();

	/**
	 * Returns the meta object for data type '{@link java.util.List <em>List Variable Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for data type '<em>List Variable Expression</em>'.
	 * @see java.util.List
	 * @model instanceClass="java.util.List&lt;alpha.model.VariableExpression&gt;"
	 * @generated
	 */
	EDataType getListVariableExpression();

	/**
	 * Returns the meta object for data type '{@link java.util.Queue <em>Integer Queue</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for data type '<em>Integer Queue</em>'.
	 * @see java.util.Queue
	 * @model instanceClass="java.util.Queue&lt;java.lang.Integer&gt;"
	 * @generated
	 */
	EDataType getIntegerQueue();

	/**
	 * Returns the meta object for data type '{@link java.lang.String <em>String</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for data type '<em>String</em>'.
	 * @see java.lang.String
	 * @model instanceClass="java.lang.String"
	 * @generated
	 */
	EDataType getString();

	/**
	 * Returns the meta object for data type '{@link alpha.model.util.Face <em>Face</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for data type '<em>Face</em>'.
	 * @see alpha.model.util.Face
	 * @model instanceClass="alpha.model.util.Face"
	 * @generated
	 */
	EDataType getFace();

	/**
	 * Returns the meta object for data type '{@link alpha.model.util.FaceLattice <em>Face Lattice</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for data type '<em>Face Lattice</em>'.
	 * @see alpha.model.util.FaceLattice
	 * @model instanceClass="alpha.model.util.FaceLattice"
	 * @generated
	 */
	EDataType getFaceLattice();

	/**
	 * Returns the meta object for data type '{@link java.lang.Integer <em>int</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for data type '<em>int</em>'.
	 * @see java.lang.Integer
	 * @model instanceClass="java.lang.Integer"
	 * @generated
	 */
	EDataType getint();

	/**
	 * Returns the meta object for data type '{@link java.lang.Float <em>float</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for data type '<em>float</em>'.
	 * @see java.lang.Float
	 * @model instanceClass="java.lang.Float"
	 * @generated
	 */
	EDataType getfloat();

	/**
	 * Returns the meta object for data type '{@link java.lang.Double <em>double</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for data type '<em>double</em>'.
	 * @see java.lang.Double
	 * @model instanceClass="java.lang.Double"
	 * @generated
	 */
	EDataType getdouble();

	/**
	 * Returns the meta object for data type '{@link java.lang.Boolean <em>boolean</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for data type '<em>boolean</em>'.
	 * @see java.lang.Boolean
	 * @model instanceClass="java.lang.Boolean"
	 * @generated
	 */
	EDataType getboolean();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	ModelFactory getModelFactory();

	/**
	 * <!-- begin-user-doc -->
	 * Defines literals for the meta objects that represent
	 * <ul>
	 *   <li>each class,</li>
	 *   <li>each feature of each class,</li>
	 *   <li>each enum,</li>
	 *   <li>and each data type</li>
	 * </ul>
	 * <!-- end-user-doc -->
	 * @generated
	 */
	interface Literals {
		/**
		 * The meta object literal for the '{@link alpha.model.impl.AlphaNodeImpl <em>Alpha Node</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see alpha.model.impl.AlphaNodeImpl
		 * @see alpha.model.impl.ModelPackageImpl#getAlphaNode()
		 * @generated
		 */
		EClass ALPHA_NODE = eINSTANCE.getAlphaNode();

		/**
		 * The meta object literal for the '<em><b>Node ID</b></em>' attribute list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ALPHA_NODE__NODE_ID = eINSTANCE.getAlphaNode_NodeID();

		/**
		 * The meta object literal for the '{@link alpha.model.AlphaCompleteVisitable <em>Alpha Complete Visitable</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see alpha.model.AlphaCompleteVisitable
		 * @see alpha.model.impl.ModelPackageImpl#getAlphaCompleteVisitable()
		 * @generated
		 */
		EClass ALPHA_COMPLETE_VISITABLE = eINSTANCE.getAlphaCompleteVisitable();

		/**
		 * The meta object literal for the '{@link alpha.model.AlphaVisitable <em>Alpha Visitable</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see alpha.model.AlphaVisitable
		 * @see alpha.model.impl.ModelPackageImpl#getAlphaVisitable()
		 * @generated
		 */
		EClass ALPHA_VISITABLE = eINSTANCE.getAlphaVisitable();

		/**
		 * The meta object literal for the '{@link alpha.model.AlphaExpressionVisitable <em>Alpha Expression Visitable</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see alpha.model.AlphaExpressionVisitable
		 * @see alpha.model.impl.ModelPackageImpl#getAlphaExpressionVisitable()
		 * @generated
		 */
		EClass ALPHA_EXPRESSION_VISITABLE = eINSTANCE.getAlphaExpressionVisitable();

		/**
		 * The meta object literal for the '{@link alpha.model.CalculatorExpressionVisitable <em>Calculator Expression Visitable</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see alpha.model.CalculatorExpressionVisitable
		 * @see alpha.model.impl.ModelPackageImpl#getCalculatorExpressionVisitable()
		 * @generated
		 */
		EClass CALCULATOR_EXPRESSION_VISITABLE = eINSTANCE.getCalculatorExpressionVisitable();

		/**
		 * The meta object literal for the '{@link alpha.model.AlphaSystemElement <em>Alpha System Element</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see alpha.model.AlphaSystemElement
		 * @see alpha.model.impl.ModelPackageImpl#getAlphaSystemElement()
		 * @generated
		 */
		EClass ALPHA_SYSTEM_ELEMENT = eINSTANCE.getAlphaSystemElement();

		/**
		 * The meta object literal for the '{@link alpha.model.AlphaScheduleTarget <em>Alpha Schedule Target</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see alpha.model.AlphaScheduleTarget
		 * @see alpha.model.impl.ModelPackageImpl#getAlphaScheduleTarget()
		 * @generated
		 */
		EClass ALPHA_SCHEDULE_TARGET = eINSTANCE.getAlphaScheduleTarget();

		/**
		 * The meta object literal for the '{@link alpha.model.AlphaVisitor <em>Alpha Visitor</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see alpha.model.AlphaVisitor
		 * @see alpha.model.impl.ModelPackageImpl#getAlphaVisitor()
		 * @generated
		 */
		EClass ALPHA_VISITOR = eINSTANCE.getAlphaVisitor();

		/**
		 * The meta object literal for the '{@link alpha.model.AlphaExpressionVisitor <em>Alpha Expression Visitor</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see alpha.model.AlphaExpressionVisitor
		 * @see alpha.model.impl.ModelPackageImpl#getAlphaExpressionVisitor()
		 * @generated
		 */
		EClass ALPHA_EXPRESSION_VISITOR = eINSTANCE.getAlphaExpressionVisitor();

		/**
		 * The meta object literal for the '{@link alpha.model.CalculatorExpressionVisitor <em>Calculator Expression Visitor</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see alpha.model.CalculatorExpressionVisitor
		 * @see alpha.model.impl.ModelPackageImpl#getCalculatorExpressionVisitor()
		 * @generated
		 */
		EClass CALCULATOR_EXPRESSION_VISITOR = eINSTANCE.getCalculatorExpressionVisitor();

		/**
		 * The meta object literal for the '{@link alpha.model.impl.AlphaRootImpl <em>Alpha Root</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see alpha.model.impl.AlphaRootImpl
		 * @see alpha.model.impl.ModelPackageImpl#getAlphaRoot()
		 * @generated
		 */
		EClass ALPHA_ROOT = eINSTANCE.getAlphaRoot();

		/**
		 * The meta object literal for the '<em><b>Imports</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ALPHA_ROOT__IMPORTS = eINSTANCE.getAlphaRoot_Imports();

		/**
		 * The meta object literal for the '<em><b>Elements</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ALPHA_ROOT__ELEMENTS = eINSTANCE.getAlphaRoot_Elements();

		/**
		 * The meta object literal for the '{@link alpha.model.AlphaElement <em>Alpha Element</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see alpha.model.AlphaElement
		 * @see alpha.model.impl.ModelPackageImpl#getAlphaElement()
		 * @generated
		 */
		EClass ALPHA_ELEMENT = eINSTANCE.getAlphaElement();

		/**
		 * The meta object literal for the '{@link alpha.model.impl.ImportsImpl <em>Imports</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see alpha.model.impl.ImportsImpl
		 * @see alpha.model.impl.ModelPackageImpl#getImports()
		 * @generated
		 */
		EClass IMPORTS = eINSTANCE.getImports();

		/**
		 * The meta object literal for the '<em><b>Imported Namespace</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute IMPORTS__IMPORTED_NAMESPACE = eINSTANCE.getImports_ImportedNamespace();

		/**
		 * The meta object literal for the '{@link alpha.model.impl.AlphaPackageImpl <em>Alpha Package</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see alpha.model.impl.AlphaPackageImpl
		 * @see alpha.model.impl.ModelPackageImpl#getAlphaPackage()
		 * @generated
		 */
		EClass ALPHA_PACKAGE = eINSTANCE.getAlphaPackage();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ALPHA_PACKAGE__NAME = eINSTANCE.getAlphaPackage_Name();

		/**
		 * The meta object literal for the '<em><b>Elements</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ALPHA_PACKAGE__ELEMENTS = eINSTANCE.getAlphaPackage_Elements();

		/**
		 * The meta object literal for the '{@link alpha.model.impl.AlphaConstantImpl <em>Alpha Constant</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see alpha.model.impl.AlphaConstantImpl
		 * @see alpha.model.impl.ModelPackageImpl#getAlphaConstant()
		 * @generated
		 */
		EClass ALPHA_CONSTANT = eINSTANCE.getAlphaConstant();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ALPHA_CONSTANT__NAME = eINSTANCE.getAlphaConstant_Name();

		/**
		 * The meta object literal for the '<em><b>Value</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ALPHA_CONSTANT__VALUE = eINSTANCE.getAlphaConstant_Value();

		/**
		 * The meta object literal for the '{@link alpha.model.impl.ExternalFunctionImpl <em>External Function</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see alpha.model.impl.ExternalFunctionImpl
		 * @see alpha.model.impl.ModelPackageImpl#getExternalFunction()
		 * @generated
		 */
		EClass EXTERNAL_FUNCTION = eINSTANCE.getExternalFunction();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute EXTERNAL_FUNCTION__NAME = eINSTANCE.getExternalFunction_Name();

		/**
		 * The meta object literal for the '<em><b>Cardinality</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute EXTERNAL_FUNCTION__CARDINALITY = eINSTANCE.getExternalFunction_Cardinality();

		/**
		 * The meta object literal for the '{@link alpha.model.impl.AlphaSystemImpl <em>Alpha System</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see alpha.model.impl.AlphaSystemImpl
		 * @see alpha.model.impl.ModelPackageImpl#getAlphaSystem()
		 * @generated
		 */
		EClass ALPHA_SYSTEM = eINSTANCE.getAlphaSystem();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ALPHA_SYSTEM__NAME = eINSTANCE.getAlphaSystem_Name();

		/**
		 * The meta object literal for the '<em><b>Parameter Domain Expr</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ALPHA_SYSTEM__PARAMETER_DOMAIN_EXPR = eINSTANCE.getAlphaSystem_ParameterDomainExpr();

		/**
		 * The meta object literal for the '<em><b>Defined Objects</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ALPHA_SYSTEM__DEFINED_OBJECTS = eINSTANCE.getAlphaSystem_DefinedObjects();

		/**
		 * The meta object literal for the '<em><b>Inputs</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ALPHA_SYSTEM__INPUTS = eINSTANCE.getAlphaSystem_Inputs();

		/**
		 * The meta object literal for the '<em><b>Outputs</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ALPHA_SYSTEM__OUTPUTS = eINSTANCE.getAlphaSystem_Outputs();

		/**
		 * The meta object literal for the '<em><b>Locals</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ALPHA_SYSTEM__LOCALS = eINSTANCE.getAlphaSystem_Locals();

		/**
		 * The meta object literal for the '<em><b>While Domain Expr</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ALPHA_SYSTEM__WHILE_DOMAIN_EXPR = eINSTANCE.getAlphaSystem_WhileDomainExpr();

		/**
		 * The meta object literal for the '<em><b>Test Expression</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ALPHA_SYSTEM__TEST_EXPRESSION = eINSTANCE.getAlphaSystem_TestExpression();

		/**
		 * The meta object literal for the '<em><b>System Bodies</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ALPHA_SYSTEM__SYSTEM_BODIES = eINSTANCE.getAlphaSystem_SystemBodies();

		/**
		 * The meta object literal for the '{@link alpha.model.impl.VariableImpl <em>Variable</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see alpha.model.impl.VariableImpl
		 * @see alpha.model.impl.ModelPackageImpl#getVariable()
		 * @generated
		 */
		EClass VARIABLE = eINSTANCE.getVariable();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute VARIABLE__NAME = eINSTANCE.getVariable_Name();

		/**
		 * The meta object literal for the '<em><b>Domain Expr</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference VARIABLE__DOMAIN_EXPR = eINSTANCE.getVariable_DomainExpr();

		/**
		 * The meta object literal for the '{@link alpha.model.impl.FuzzyVariableImpl <em>Fuzzy Variable</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see alpha.model.impl.FuzzyVariableImpl
		 * @see alpha.model.impl.ModelPackageImpl#getFuzzyVariable()
		 * @generated
		 */
		EClass FUZZY_VARIABLE = eINSTANCE.getFuzzyVariable();

		/**
		 * The meta object literal for the '<em><b>Range Expr</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference FUZZY_VARIABLE__RANGE_EXPR = eINSTANCE.getFuzzyVariable_RangeExpr();

		/**
		 * The meta object literal for the '{@link alpha.model.impl.SystemBodyImpl <em>System Body</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see alpha.model.impl.SystemBodyImpl
		 * @see alpha.model.impl.ModelPackageImpl#getSystemBody()
		 * @generated
		 */
		EClass SYSTEM_BODY = eINSTANCE.getSystemBody();

		/**
		 * The meta object literal for the '<em><b>System</b></em>' container reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference SYSTEM_BODY__SYSTEM = eINSTANCE.getSystemBody_System();

		/**
		 * The meta object literal for the '<em><b>Parameter Domain Expr</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference SYSTEM_BODY__PARAMETER_DOMAIN_EXPR = eINSTANCE.getSystemBody_ParameterDomainExpr();

		/**
		 * The meta object literal for the '<em><b>Equations</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference SYSTEM_BODY__EQUATIONS = eINSTANCE.getSystemBody_Equations();

		/**
		 * The meta object literal for the '{@link alpha.model.impl.EquationImpl <em>Equation</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see alpha.model.impl.EquationImpl
		 * @see alpha.model.impl.ModelPackageImpl#getEquation()
		 * @generated
		 */
		EClass EQUATION = eINSTANCE.getEquation();

		/**
		 * The meta object literal for the '<em><b>System Body</b></em>' container reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference EQUATION__SYSTEM_BODY = eINSTANCE.getEquation_SystemBody();

		/**
		 * The meta object literal for the '<em><b>Zexplored</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute EQUATION__ZEXPLORED = eINSTANCE.getEquation_Z__explored();

		/**
		 * The meta object literal for the '{@link alpha.model.impl.StandardEquationImpl <em>Standard Equation</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see alpha.model.impl.StandardEquationImpl
		 * @see alpha.model.impl.ModelPackageImpl#getStandardEquation()
		 * @generated
		 */
		EClass STANDARD_EQUATION = eINSTANCE.getStandardEquation();

		/**
		 * The meta object literal for the '<em><b>Variable</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference STANDARD_EQUATION__VARIABLE = eINSTANCE.getStandardEquation_Variable();

		/**
		 * The meta object literal for the '<em><b>Index Names</b></em>' attribute list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute STANDARD_EQUATION__INDEX_NAMES = eINSTANCE.getStandardEquation_IndexNames();

		/**
		 * The meta object literal for the '<em><b>Expr</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference STANDARD_EQUATION__EXPR = eINSTANCE.getStandardEquation_Expr();

		/**
		 * The meta object literal for the '{@link alpha.model.impl.UseEquationImpl <em>Use Equation</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see alpha.model.impl.UseEquationImpl
		 * @see alpha.model.impl.ModelPackageImpl#getUseEquation()
		 * @generated
		 */
		EClass USE_EQUATION = eINSTANCE.getUseEquation();

		/**
		 * The meta object literal for the '<em><b>Identifier</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute USE_EQUATION__IDENTIFIER = eINSTANCE.getUseEquation_Identifier();

		/**
		 * The meta object literal for the '<em><b>Instantiation Domain Expr</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference USE_EQUATION__INSTANTIATION_DOMAIN_EXPR = eINSTANCE.getUseEquation_InstantiationDomainExpr();

		/**
		 * The meta object literal for the '<em><b>Subsystem Dims</b></em>' attribute list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute USE_EQUATION__SUBSYSTEM_DIMS = eINSTANCE.getUseEquation_SubsystemDims();

		/**
		 * The meta object literal for the '<em><b>System</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference USE_EQUATION__SYSTEM = eINSTANCE.getUseEquation_System();

		/**
		 * The meta object literal for the '<em><b>Call Params Expr</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference USE_EQUATION__CALL_PARAMS_EXPR = eINSTANCE.getUseEquation_CallParamsExpr();

		/**
		 * The meta object literal for the '<em><b>Input Exprs</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference USE_EQUATION__INPUT_EXPRS = eINSTANCE.getUseEquation_InputExprs();

		/**
		 * The meta object literal for the '<em><b>Output Exprs</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference USE_EQUATION__OUTPUT_EXPRS = eINSTANCE.getUseEquation_OutputExprs();

		/**
		 * The meta object literal for the '{@link alpha.model.impl.AlphaExpressionImpl <em>Alpha Expression</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see alpha.model.impl.AlphaExpressionImpl
		 * @see alpha.model.impl.ModelPackageImpl#getAlphaExpression()
		 * @generated
		 */
		EClass ALPHA_EXPRESSION = eINSTANCE.getAlphaExpression();

		/**
		 * The meta object literal for the '<em><b>Zinternal cache expr Dom</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ALPHA_EXPRESSION__ZINTERNAL_CACHE_EXPR_DOM = eINSTANCE.getAlphaExpression_Z__internal_cache_exprDom();

		/**
		 * The meta object literal for the '<em><b>Zinternal cache context Dom</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ALPHA_EXPRESSION__ZINTERNAL_CACHE_CONTEXT_DOM = eINSTANCE.getAlphaExpression_Z__internal_cache_contextDom();

		/**
		 * The meta object literal for the '<em><b>Expression ID</b></em>' attribute list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ALPHA_EXPRESSION__EXPRESSION_ID = eINSTANCE.getAlphaExpression_ExpressionID();

		/**
		 * The meta object literal for the '{@link alpha.model.impl.RestrictExpressionImpl <em>Restrict Expression</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see alpha.model.impl.RestrictExpressionImpl
		 * @see alpha.model.impl.ModelPackageImpl#getRestrictExpression()
		 * @generated
		 */
		EClass RESTRICT_EXPRESSION = eINSTANCE.getRestrictExpression();

		/**
		 * The meta object literal for the '<em><b>Domain Expr</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference RESTRICT_EXPRESSION__DOMAIN_EXPR = eINSTANCE.getRestrictExpression_DomainExpr();

		/**
		 * The meta object literal for the '<em><b>Expr</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference RESTRICT_EXPRESSION__EXPR = eINSTANCE.getRestrictExpression_Expr();

		/**
		 * The meta object literal for the '{@link alpha.model.impl.AutoRestrictExpressionImpl <em>Auto Restrict Expression</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see alpha.model.impl.AutoRestrictExpressionImpl
		 * @see alpha.model.impl.ModelPackageImpl#getAutoRestrictExpression()
		 * @generated
		 */
		EClass AUTO_RESTRICT_EXPRESSION = eINSTANCE.getAutoRestrictExpression();

		/**
		 * The meta object literal for the '<em><b>Expr</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference AUTO_RESTRICT_EXPRESSION__EXPR = eINSTANCE.getAutoRestrictExpression_Expr();

		/**
		 * The meta object literal for the '<em><b>Zinternal cache inferred Domain</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute AUTO_RESTRICT_EXPRESSION__ZINTERNAL_CACHE_INFERRED_DOMAIN = eINSTANCE.getAutoRestrictExpression_Z__internal_cache_inferredDomain();

		/**
		 * The meta object literal for the '{@link alpha.model.impl.CaseExpressionImpl <em>Case Expression</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see alpha.model.impl.CaseExpressionImpl
		 * @see alpha.model.impl.ModelPackageImpl#getCaseExpression()
		 * @generated
		 */
		EClass CASE_EXPRESSION = eINSTANCE.getCaseExpression();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CASE_EXPRESSION__NAME = eINSTANCE.getCaseExpression_Name();

		/**
		 * The meta object literal for the '<em><b>Exprs</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CASE_EXPRESSION__EXPRS = eINSTANCE.getCaseExpression_Exprs();

		/**
		 * The meta object literal for the '{@link alpha.model.impl.DependenceExpressionImpl <em>Dependence Expression</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see alpha.model.impl.DependenceExpressionImpl
		 * @see alpha.model.impl.ModelPackageImpl#getDependenceExpression()
		 * @generated
		 */
		EClass DEPENDENCE_EXPRESSION = eINSTANCE.getDependenceExpression();

		/**
		 * The meta object literal for the '<em><b>Function Expr</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DEPENDENCE_EXPRESSION__FUNCTION_EXPR = eINSTANCE.getDependenceExpression_FunctionExpr();

		/**
		 * The meta object literal for the '<em><b>Expr</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DEPENDENCE_EXPRESSION__EXPR = eINSTANCE.getDependenceExpression_Expr();

		/**
		 * The meta object literal for the '{@link alpha.model.impl.FuzzyDependenceExpressionImpl <em>Fuzzy Dependence Expression</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see alpha.model.impl.FuzzyDependenceExpressionImpl
		 * @see alpha.model.impl.ModelPackageImpl#getFuzzyDependenceExpression()
		 * @generated
		 */
		EClass FUZZY_DEPENDENCE_EXPRESSION = eINSTANCE.getFuzzyDependenceExpression();

		/**
		 * The meta object literal for the '<em><b>Fuzzy Function</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference FUZZY_DEPENDENCE_EXPRESSION__FUZZY_FUNCTION = eINSTANCE.getFuzzyDependenceExpression_FuzzyFunction();

		/**
		 * The meta object literal for the '<em><b>Expr</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference FUZZY_DEPENDENCE_EXPRESSION__EXPR = eINSTANCE.getFuzzyDependenceExpression_Expr();

		/**
		 * The meta object literal for the '{@link alpha.model.impl.IfExpressionImpl <em>If Expression</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see alpha.model.impl.IfExpressionImpl
		 * @see alpha.model.impl.ModelPackageImpl#getIfExpression()
		 * @generated
		 */
		EClass IF_EXPRESSION = eINSTANCE.getIfExpression();

		/**
		 * The meta object literal for the '<em><b>Cond Expr</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference IF_EXPRESSION__COND_EXPR = eINSTANCE.getIfExpression_CondExpr();

		/**
		 * The meta object literal for the '<em><b>Then Expr</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference IF_EXPRESSION__THEN_EXPR = eINSTANCE.getIfExpression_ThenExpr();

		/**
		 * The meta object literal for the '<em><b>Else Expr</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference IF_EXPRESSION__ELSE_EXPR = eINSTANCE.getIfExpression_ElseExpr();

		/**
		 * The meta object literal for the '{@link alpha.model.impl.IndexExpressionImpl <em>Index Expression</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see alpha.model.impl.IndexExpressionImpl
		 * @see alpha.model.impl.ModelPackageImpl#getIndexExpression()
		 * @generated
		 */
		EClass INDEX_EXPRESSION = eINSTANCE.getIndexExpression();

		/**
		 * The meta object literal for the '<em><b>Function Expr</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference INDEX_EXPRESSION__FUNCTION_EXPR = eINSTANCE.getIndexExpression_FunctionExpr();

		/**
		 * The meta object literal for the '{@link alpha.model.impl.PolynomialIndexExpressionImpl <em>Polynomial Index Expression</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see alpha.model.impl.PolynomialIndexExpressionImpl
		 * @see alpha.model.impl.ModelPackageImpl#getPolynomialIndexExpression()
		 * @generated
		 */
		EClass POLYNOMIAL_INDEX_EXPRESSION = eINSTANCE.getPolynomialIndexExpression();

		/**
		 * The meta object literal for the '<em><b>Polynomial Expr</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference POLYNOMIAL_INDEX_EXPRESSION__POLYNOMIAL_EXPR = eINSTANCE.getPolynomialIndexExpression_PolynomialExpr();

		/**
		 * The meta object literal for the '{@link alpha.model.impl.FuzzyIndexExpressionImpl <em>Fuzzy Index Expression</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see alpha.model.impl.FuzzyIndexExpressionImpl
		 * @see alpha.model.impl.ModelPackageImpl#getFuzzyIndexExpression()
		 * @generated
		 */
		EClass FUZZY_INDEX_EXPRESSION = eINSTANCE.getFuzzyIndexExpression();

		/**
		 * The meta object literal for the '<em><b>Fuzzy Function</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference FUZZY_INDEX_EXPRESSION__FUZZY_FUNCTION = eINSTANCE.getFuzzyIndexExpression_FuzzyFunction();

		/**
		 * The meta object literal for the '{@link alpha.model.impl.AbstractReduceExpressionImpl <em>Abstract Reduce Expression</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see alpha.model.impl.AbstractReduceExpressionImpl
		 * @see alpha.model.impl.ModelPackageImpl#getAbstractReduceExpression()
		 * @generated
		 */
		EClass ABSTRACT_REDUCE_EXPRESSION = eINSTANCE.getAbstractReduceExpression();

		/**
		 * The meta object literal for the '<em><b>Operator</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ABSTRACT_REDUCE_EXPRESSION__OPERATOR = eINSTANCE.getAbstractReduceExpression_Operator();

		/**
		 * The meta object literal for the '<em><b>Projection Expr</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ABSTRACT_REDUCE_EXPRESSION__PROJECTION_EXPR = eINSTANCE.getAbstractReduceExpression_ProjectionExpr();

		/**
		 * The meta object literal for the '<em><b>Body</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ABSTRACT_REDUCE_EXPRESSION__BODY = eINSTANCE.getAbstractReduceExpression_Body();

		/**
		 * The meta object literal for the '<em><b>Zinternal facet</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ABSTRACT_REDUCE_EXPRESSION__ZINTERNAL_FACET = eINSTANCE.getAbstractReduceExpression_Z__internal_facet();

		/**
		 * The meta object literal for the '<em><b>Nb Free Dimensions In Body</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ABSTRACT_REDUCE_EXPRESSION__NB_FREE_DIMENSIONS_IN_BODY = eINSTANCE.getAbstractReduceExpression_NbFreeDimensionsInBody();

		/**
		 * The meta object literal for the '<em><b>Zfractal Simplify</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ABSTRACT_REDUCE_EXPRESSION__ZFRACTAL_SIMPLIFY = eINSTANCE.getAbstractReduceExpression_Z__fractalSimplify();

		/**
		 * The meta object literal for the '{@link alpha.model.impl.ReduceExpressionImpl <em>Reduce Expression</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see alpha.model.impl.ReduceExpressionImpl
		 * @see alpha.model.impl.ModelPackageImpl#getReduceExpression()
		 * @generated
		 */
		EClass REDUCE_EXPRESSION = eINSTANCE.getReduceExpression();

		/**
		 * The meta object literal for the '{@link alpha.model.impl.ExternalReduceExpressionImpl <em>External Reduce Expression</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see alpha.model.impl.ExternalReduceExpressionImpl
		 * @see alpha.model.impl.ModelPackageImpl#getExternalReduceExpression()
		 * @generated
		 */
		EClass EXTERNAL_REDUCE_EXPRESSION = eINSTANCE.getExternalReduceExpression();

		/**
		 * The meta object literal for the '<em><b>External Function</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference EXTERNAL_REDUCE_EXPRESSION__EXTERNAL_FUNCTION = eINSTANCE.getExternalReduceExpression_ExternalFunction();

		/**
		 * The meta object literal for the '{@link alpha.model.impl.ArgReduceExpressionImpl <em>Arg Reduce Expression</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see alpha.model.impl.ArgReduceExpressionImpl
		 * @see alpha.model.impl.ModelPackageImpl#getArgReduceExpression()
		 * @generated
		 */
		EClass ARG_REDUCE_EXPRESSION = eINSTANCE.getArgReduceExpression();

		/**
		 * The meta object literal for the '{@link alpha.model.impl.ExternalArgReduceExpressionImpl <em>External Arg Reduce Expression</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see alpha.model.impl.ExternalArgReduceExpressionImpl
		 * @see alpha.model.impl.ModelPackageImpl#getExternalArgReduceExpression()
		 * @generated
		 */
		EClass EXTERNAL_ARG_REDUCE_EXPRESSION = eINSTANCE.getExternalArgReduceExpression();

		/**
		 * The meta object literal for the '<em><b>External Function</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference EXTERNAL_ARG_REDUCE_EXPRESSION__EXTERNAL_FUNCTION = eINSTANCE.getExternalArgReduceExpression_ExternalFunction();

		/**
		 * The meta object literal for the '{@link alpha.model.impl.ConvolutionExpressionImpl <em>Convolution Expression</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see alpha.model.impl.ConvolutionExpressionImpl
		 * @see alpha.model.impl.ModelPackageImpl#getConvolutionExpression()
		 * @generated
		 */
		EClass CONVOLUTION_EXPRESSION = eINSTANCE.getConvolutionExpression();

		/**
		 * The meta object literal for the '<em><b>Kernel Domain Expr</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CONVOLUTION_EXPRESSION__KERNEL_DOMAIN_EXPR = eINSTANCE.getConvolutionExpression_KernelDomainExpr();

		/**
		 * The meta object literal for the '<em><b>Kernel Expression</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CONVOLUTION_EXPRESSION__KERNEL_EXPRESSION = eINSTANCE.getConvolutionExpression_KernelExpression();

		/**
		 * The meta object literal for the '<em><b>Data Expression</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CONVOLUTION_EXPRESSION__DATA_EXPRESSION = eINSTANCE.getConvolutionExpression_DataExpression();

		/**
		 * The meta object literal for the '{@link alpha.model.impl.SelectExpressionImpl <em>Select Expression</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see alpha.model.impl.SelectExpressionImpl
		 * @see alpha.model.impl.ModelPackageImpl#getSelectExpression()
		 * @generated
		 */
		EClass SELECT_EXPRESSION = eINSTANCE.getSelectExpression();

		/**
		 * The meta object literal for the '<em><b>Relation Expr</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference SELECT_EXPRESSION__RELATION_EXPR = eINSTANCE.getSelectExpression_RelationExpr();

		/**
		 * The meta object literal for the '<em><b>Expr</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference SELECT_EXPRESSION__EXPR = eINSTANCE.getSelectExpression_Expr();

		/**
		 * The meta object literal for the '{@link alpha.model.impl.VariableExpressionImpl <em>Variable Expression</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see alpha.model.impl.VariableExpressionImpl
		 * @see alpha.model.impl.ModelPackageImpl#getVariableExpression()
		 * @generated
		 */
		EClass VARIABLE_EXPRESSION = eINSTANCE.getVariableExpression();

		/**
		 * The meta object literal for the '<em><b>Variable</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference VARIABLE_EXPRESSION__VARIABLE = eINSTANCE.getVariableExpression_Variable();

		/**
		 * The meta object literal for the '{@link alpha.model.ConstantExpression <em>Constant Expression</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see alpha.model.ConstantExpression
		 * @see alpha.model.impl.ModelPackageImpl#getConstantExpression()
		 * @generated
		 */
		EClass CONSTANT_EXPRESSION = eINSTANCE.getConstantExpression();

		/**
		 * The meta object literal for the '{@link alpha.model.impl.IntegerExpressionImpl <em>Integer Expression</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see alpha.model.impl.IntegerExpressionImpl
		 * @see alpha.model.impl.ModelPackageImpl#getIntegerExpression()
		 * @generated
		 */
		EClass INTEGER_EXPRESSION = eINSTANCE.getIntegerExpression();

		/**
		 * The meta object literal for the '<em><b>Value</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute INTEGER_EXPRESSION__VALUE = eINSTANCE.getIntegerExpression_Value();

		/**
		 * The meta object literal for the '{@link alpha.model.impl.RealExpressionImpl <em>Real Expression</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see alpha.model.impl.RealExpressionImpl
		 * @see alpha.model.impl.ModelPackageImpl#getRealExpression()
		 * @generated
		 */
		EClass REAL_EXPRESSION = eINSTANCE.getRealExpression();

		/**
		 * The meta object literal for the '<em><b>Value</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute REAL_EXPRESSION__VALUE = eINSTANCE.getRealExpression_Value();

		/**
		 * The meta object literal for the '{@link alpha.model.impl.BooleanExpressionImpl <em>Boolean Expression</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see alpha.model.impl.BooleanExpressionImpl
		 * @see alpha.model.impl.ModelPackageImpl#getBooleanExpression()
		 * @generated
		 */
		EClass BOOLEAN_EXPRESSION = eINSTANCE.getBooleanExpression();

		/**
		 * The meta object literal for the '<em><b>Value</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute BOOLEAN_EXPRESSION__VALUE = eINSTANCE.getBooleanExpression_Value();

		/**
		 * The meta object literal for the '{@link alpha.model.impl.UnaryExpressionImpl <em>Unary Expression</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see alpha.model.impl.UnaryExpressionImpl
		 * @see alpha.model.impl.ModelPackageImpl#getUnaryExpression()
		 * @generated
		 */
		EClass UNARY_EXPRESSION = eINSTANCE.getUnaryExpression();

		/**
		 * The meta object literal for the '<em><b>Operator</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute UNARY_EXPRESSION__OPERATOR = eINSTANCE.getUnaryExpression_Operator();

		/**
		 * The meta object literal for the '<em><b>Expr</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference UNARY_EXPRESSION__EXPR = eINSTANCE.getUnaryExpression_Expr();

		/**
		 * The meta object literal for the '{@link alpha.model.impl.BinaryExpressionImpl <em>Binary Expression</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see alpha.model.impl.BinaryExpressionImpl
		 * @see alpha.model.impl.ModelPackageImpl#getBinaryExpression()
		 * @generated
		 */
		EClass BINARY_EXPRESSION = eINSTANCE.getBinaryExpression();

		/**
		 * The meta object literal for the '<em><b>Operator</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute BINARY_EXPRESSION__OPERATOR = eINSTANCE.getBinaryExpression_Operator();

		/**
		 * The meta object literal for the '<em><b>Left</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference BINARY_EXPRESSION__LEFT = eINSTANCE.getBinaryExpression_Left();

		/**
		 * The meta object literal for the '<em><b>Right</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference BINARY_EXPRESSION__RIGHT = eINSTANCE.getBinaryExpression_Right();

		/**
		 * The meta object literal for the '{@link alpha.model.impl.MultiArgExpressionImpl <em>Multi Arg Expression</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see alpha.model.impl.MultiArgExpressionImpl
		 * @see alpha.model.impl.ModelPackageImpl#getMultiArgExpression()
		 * @generated
		 */
		EClass MULTI_ARG_EXPRESSION = eINSTANCE.getMultiArgExpression();

		/**
		 * The meta object literal for the '<em><b>Operator</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute MULTI_ARG_EXPRESSION__OPERATOR = eINSTANCE.getMultiArgExpression_Operator();

		/**
		 * The meta object literal for the '<em><b>Exprs</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference MULTI_ARG_EXPRESSION__EXPRS = eINSTANCE.getMultiArgExpression_Exprs();

		/**
		 * The meta object literal for the '{@link alpha.model.impl.ExternalMultiArgExpressionImpl <em>External Multi Arg Expression</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see alpha.model.impl.ExternalMultiArgExpressionImpl
		 * @see alpha.model.impl.ModelPackageImpl#getExternalMultiArgExpression()
		 * @generated
		 */
		EClass EXTERNAL_MULTI_ARG_EXPRESSION = eINSTANCE.getExternalMultiArgExpression();

		/**
		 * The meta object literal for the '<em><b>External Function</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference EXTERNAL_MULTI_ARG_EXPRESSION__EXTERNAL_FUNCTION = eINSTANCE.getExternalMultiArgExpression_ExternalFunction();

		/**
		 * The meta object literal for the '{@link alpha.model.impl.AbstractFuzzyReduceExpressionImpl <em>Abstract Fuzzy Reduce Expression</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see alpha.model.impl.AbstractFuzzyReduceExpressionImpl
		 * @see alpha.model.impl.ModelPackageImpl#getAbstractFuzzyReduceExpression()
		 * @generated
		 */
		EClass ABSTRACT_FUZZY_REDUCE_EXPRESSION = eINSTANCE.getAbstractFuzzyReduceExpression();

		/**
		 * The meta object literal for the '<em><b>Operator</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ABSTRACT_FUZZY_REDUCE_EXPRESSION__OPERATOR = eINSTANCE.getAbstractFuzzyReduceExpression_Operator();

		/**
		 * The meta object literal for the '<em><b>Projection Function</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ABSTRACT_FUZZY_REDUCE_EXPRESSION__PROJECTION_FUNCTION = eINSTANCE.getAbstractFuzzyReduceExpression_ProjectionFunction();

		/**
		 * The meta object literal for the '<em><b>Body</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ABSTRACT_FUZZY_REDUCE_EXPRESSION__BODY = eINSTANCE.getAbstractFuzzyReduceExpression_Body();

		/**
		 * The meta object literal for the '{@link alpha.model.impl.FuzzyReduceExpressionImpl <em>Fuzzy Reduce Expression</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see alpha.model.impl.FuzzyReduceExpressionImpl
		 * @see alpha.model.impl.ModelPackageImpl#getFuzzyReduceExpression()
		 * @generated
		 */
		EClass FUZZY_REDUCE_EXPRESSION = eINSTANCE.getFuzzyReduceExpression();

		/**
		 * The meta object literal for the '{@link alpha.model.impl.ExternalFuzzyReduceExpressionImpl <em>External Fuzzy Reduce Expression</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see alpha.model.impl.ExternalFuzzyReduceExpressionImpl
		 * @see alpha.model.impl.ModelPackageImpl#getExternalFuzzyReduceExpression()
		 * @generated
		 */
		EClass EXTERNAL_FUZZY_REDUCE_EXPRESSION = eINSTANCE.getExternalFuzzyReduceExpression();

		/**
		 * The meta object literal for the '<em><b>External Function</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference EXTERNAL_FUZZY_REDUCE_EXPRESSION__EXTERNAL_FUNCTION = eINSTANCE.getExternalFuzzyReduceExpression_ExternalFunction();

		/**
		 * The meta object literal for the '{@link alpha.model.impl.FuzzyArgReduceExpressionImpl <em>Fuzzy Arg Reduce Expression</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see alpha.model.impl.FuzzyArgReduceExpressionImpl
		 * @see alpha.model.impl.ModelPackageImpl#getFuzzyArgReduceExpression()
		 * @generated
		 */
		EClass FUZZY_ARG_REDUCE_EXPRESSION = eINSTANCE.getFuzzyArgReduceExpression();

		/**
		 * The meta object literal for the '{@link alpha.model.impl.ExternalFuzzyArgReduceExpressionImpl <em>External Fuzzy Arg Reduce Expression</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see alpha.model.impl.ExternalFuzzyArgReduceExpressionImpl
		 * @see alpha.model.impl.ModelPackageImpl#getExternalFuzzyArgReduceExpression()
		 * @generated
		 */
		EClass EXTERNAL_FUZZY_ARG_REDUCE_EXPRESSION = eINSTANCE.getExternalFuzzyArgReduceExpression();

		/**
		 * The meta object literal for the '<em><b>External Function</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference EXTERNAL_FUZZY_ARG_REDUCE_EXPRESSION__EXTERNAL_FUNCTION = eINSTANCE.getExternalFuzzyArgReduceExpression_ExternalFunction();

		/**
		 * The meta object literal for the '{@link alpha.model.CalculatorNode <em>Calculator Node</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see alpha.model.CalculatorNode
		 * @see alpha.model.impl.ModelPackageImpl#getCalculatorNode()
		 * @generated
		 */
		EClass CALCULATOR_NODE = eINSTANCE.getCalculatorNode();

		/**
		 * The meta object literal for the '{@link alpha.model.impl.PolyhedralObjectImpl <em>Polyhedral Object</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see alpha.model.impl.PolyhedralObjectImpl
		 * @see alpha.model.impl.ModelPackageImpl#getPolyhedralObject()
		 * @generated
		 */
		EClass POLYHEDRAL_OBJECT = eINSTANCE.getPolyhedralObject();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute POLYHEDRAL_OBJECT__NAME = eINSTANCE.getPolyhedralObject_Name();

		/**
		 * The meta object literal for the '<em><b>Expr</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference POLYHEDRAL_OBJECT__EXPR = eINSTANCE.getPolyhedralObject_Expr();

		/**
		 * The meta object literal for the '{@link alpha.model.impl.CalculatorExpressionImpl <em>Calculator Expression</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see alpha.model.impl.CalculatorExpressionImpl
		 * @see alpha.model.impl.ModelPackageImpl#getCalculatorExpression()
		 * @generated
		 */
		EClass CALCULATOR_EXPRESSION = eINSTANCE.getCalculatorExpression();

		/**
		 * The meta object literal for the '{@link alpha.model.impl.JNIDomainImpl <em>JNI Domain</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see alpha.model.impl.JNIDomainImpl
		 * @see alpha.model.impl.ModelPackageImpl#getJNIDomain()
		 * @generated
		 */
		EClass JNI_DOMAIN = eINSTANCE.getJNIDomain();

		/**
		 * The meta object literal for the '<em><b>Isl String</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute JNI_DOMAIN__ISL_STRING = eINSTANCE.getJNIDomain_IslString();

		/**
		 * The meta object literal for the '<em><b>Zinternal cache isl Set</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute JNI_DOMAIN__ZINTERNAL_CACHE_ISL_SET = eINSTANCE.getJNIDomain_Z__internal_cache_islSet();

		/**
		 * The meta object literal for the '{@link alpha.model.impl.JNIDomainInArrayNotationImpl <em>JNI Domain In Array Notation</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see alpha.model.impl.JNIDomainInArrayNotationImpl
		 * @see alpha.model.impl.ModelPackageImpl#getJNIDomainInArrayNotation()
		 * @generated
		 */
		EClass JNI_DOMAIN_IN_ARRAY_NOTATION = eINSTANCE.getJNIDomainInArrayNotation();

		/**
		 * The meta object literal for the '{@link alpha.model.impl.JNIRelationImpl <em>JNI Relation</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see alpha.model.impl.JNIRelationImpl
		 * @see alpha.model.impl.ModelPackageImpl#getJNIRelation()
		 * @generated
		 */
		EClass JNI_RELATION = eINSTANCE.getJNIRelation();

		/**
		 * The meta object literal for the '<em><b>Isl String</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute JNI_RELATION__ISL_STRING = eINSTANCE.getJNIRelation_IslString();

		/**
		 * The meta object literal for the '<em><b>Zinternal cache isl Map</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute JNI_RELATION__ZINTERNAL_CACHE_ISL_MAP = eINSTANCE.getJNIRelation_Z__internal_cache_islMap();

		/**
		 * The meta object literal for the '{@link alpha.model.impl.JNIFunctionImpl <em>JNI Function</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see alpha.model.impl.JNIFunctionImpl
		 * @see alpha.model.impl.ModelPackageImpl#getJNIFunction()
		 * @generated
		 */
		EClass JNI_FUNCTION = eINSTANCE.getJNIFunction();

		/**
		 * The meta object literal for the '<em><b>Alpha Function</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference JNI_FUNCTION__ALPHA_FUNCTION = eINSTANCE.getJNIFunction_AlphaFunction();

		/**
		 * The meta object literal for the '<em><b>Zinternal cache isl MAff</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute JNI_FUNCTION__ZINTERNAL_CACHE_ISL_MAFF = eINSTANCE.getJNIFunction_Z__internal_cache_islMAff();

		/**
		 * The meta object literal for the '{@link alpha.model.impl.JNIFunctionInArrayNotationImpl <em>JNI Function In Array Notation</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see alpha.model.impl.JNIFunctionInArrayNotationImpl
		 * @see alpha.model.impl.ModelPackageImpl#getJNIFunctionInArrayNotation()
		 * @generated
		 */
		EClass JNI_FUNCTION_IN_ARRAY_NOTATION = eINSTANCE.getJNIFunctionInArrayNotation();

		/**
		 * The meta object literal for the '<em><b>Array Notation</b></em>' attribute list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute JNI_FUNCTION_IN_ARRAY_NOTATION__ARRAY_NOTATION = eINSTANCE.getJNIFunctionInArrayNotation_ArrayNotation();

		/**
		 * The meta object literal for the '{@link alpha.model.impl.JNIPolynomialImpl <em>JNI Polynomial</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see alpha.model.impl.JNIPolynomialImpl
		 * @see alpha.model.impl.ModelPackageImpl#getJNIPolynomial()
		 * @generated
		 */
		EClass JNI_POLYNOMIAL = eINSTANCE.getJNIPolynomial();

		/**
		 * The meta object literal for the '<em><b>Isl String</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute JNI_POLYNOMIAL__ISL_STRING = eINSTANCE.getJNIPolynomial_IslString();

		/**
		 * The meta object literal for the '<em><b>Zinternal cache isl PWQP</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute JNI_POLYNOMIAL__ZINTERNAL_CACHE_ISL_PWQP = eINSTANCE.getJNIPolynomial_Z__internal_cache_islPWQP();

		/**
		 * The meta object literal for the '{@link alpha.model.impl.JNIPolynomialInArrayNotationImpl <em>JNI Polynomial In Array Notation</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see alpha.model.impl.JNIPolynomialInArrayNotationImpl
		 * @see alpha.model.impl.ModelPackageImpl#getJNIPolynomialInArrayNotation()
		 * @generated
		 */
		EClass JNI_POLYNOMIAL_IN_ARRAY_NOTATION = eINSTANCE.getJNIPolynomialInArrayNotation();

		/**
		 * The meta object literal for the '<em><b>Array Notation</b></em>' attribute list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute JNI_POLYNOMIAL_IN_ARRAY_NOTATION__ARRAY_NOTATION = eINSTANCE.getJNIPolynomialInArrayNotation_ArrayNotation();

		/**
		 * The meta object literal for the '{@link alpha.model.impl.FuzzyFunctionImpl <em>Fuzzy Function</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see alpha.model.impl.FuzzyFunctionImpl
		 * @see alpha.model.impl.ModelPackageImpl#getFuzzyFunction()
		 * @generated
		 */
		EClass FUZZY_FUNCTION = eINSTANCE.getFuzzyFunction();

		/**
		 * The meta object literal for the '<em><b>Alpha String</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute FUZZY_FUNCTION__ALPHA_STRING = eINSTANCE.getFuzzyFunction_AlphaString();

		/**
		 * The meta object literal for the '<em><b>Indirections</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference FUZZY_FUNCTION__INDIRECTIONS = eINSTANCE.getFuzzyFunction_Indirections();

		/**
		 * The meta object literal for the '<em><b>Zinternal cache fuzzy Map</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute FUZZY_FUNCTION__ZINTERNAL_CACHE_FUZZY_MAP = eINSTANCE.getFuzzyFunction_Z__internal_cache_fuzzyMap();

		/**
		 * The meta object literal for the '<em><b>Zinternal cache dep Relation</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute FUZZY_FUNCTION__ZINTERNAL_CACHE_DEP_RELATION = eINSTANCE.getFuzzyFunction_Z__internal_cache_depRelation();

		/**
		 * The meta object literal for the '{@link alpha.model.impl.FuzzyVariableUseImpl <em>Fuzzy Variable Use</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see alpha.model.impl.FuzzyVariableUseImpl
		 * @see alpha.model.impl.ModelPackageImpl#getFuzzyVariableUse()
		 * @generated
		 */
		EClass FUZZY_VARIABLE_USE = eINSTANCE.getFuzzyVariableUse();

		/**
		 * The meta object literal for the '<em><b>Fuzzy Index</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute FUZZY_VARIABLE_USE__FUZZY_INDEX = eINSTANCE.getFuzzyVariableUse_FuzzyIndex();

		/**
		 * The meta object literal for the '<em><b>Fuzzy Variable</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference FUZZY_VARIABLE_USE__FUZZY_VARIABLE = eINSTANCE.getFuzzyVariableUse_FuzzyVariable();

		/**
		 * The meta object literal for the '{@link alpha.model.impl.NestedFuzzyFunctionImpl <em>Nested Fuzzy Function</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see alpha.model.impl.NestedFuzzyFunctionImpl
		 * @see alpha.model.impl.ModelPackageImpl#getNestedFuzzyFunction()
		 * @generated
		 */
		EClass NESTED_FUZZY_FUNCTION = eINSTANCE.getNestedFuzzyFunction();

		/**
		 * The meta object literal for the '{@link alpha.model.impl.AffineFuzzyVariableUseImpl <em>Affine Fuzzy Variable Use</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see alpha.model.impl.AffineFuzzyVariableUseImpl
		 * @see alpha.model.impl.ModelPackageImpl#getAffineFuzzyVariableUse()
		 * @generated
		 */
		EClass AFFINE_FUZZY_VARIABLE_USE = eINSTANCE.getAffineFuzzyVariableUse();

		/**
		 * The meta object literal for the '<em><b>Use Function</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference AFFINE_FUZZY_VARIABLE_USE__USE_FUNCTION = eINSTANCE.getAffineFuzzyVariableUse_UseFunction();

		/**
		 * The meta object literal for the '{@link alpha.model.impl.FuzzyFunctionInArrayNotationImpl <em>Fuzzy Function In Array Notation</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see alpha.model.impl.FuzzyFunctionInArrayNotationImpl
		 * @see alpha.model.impl.ModelPackageImpl#getFuzzyFunctionInArrayNotation()
		 * @generated
		 */
		EClass FUZZY_FUNCTION_IN_ARRAY_NOTATION = eINSTANCE.getFuzzyFunctionInArrayNotation();

		/**
		 * The meta object literal for the '<em><b>Array Notation</b></em>' attribute list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute FUZZY_FUNCTION_IN_ARRAY_NOTATION__ARRAY_NOTATION = eINSTANCE.getFuzzyFunctionInArrayNotation_ArrayNotation();

		/**
		 * The meta object literal for the '{@link alpha.model.impl.UnaryCalculatorExpressionImpl <em>Unary Calculator Expression</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see alpha.model.impl.UnaryCalculatorExpressionImpl
		 * @see alpha.model.impl.ModelPackageImpl#getUnaryCalculatorExpression()
		 * @generated
		 */
		EClass UNARY_CALCULATOR_EXPRESSION = eINSTANCE.getUnaryCalculatorExpression();

		/**
		 * The meta object literal for the '<em><b>Operator</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute UNARY_CALCULATOR_EXPRESSION__OPERATOR = eINSTANCE.getUnaryCalculatorExpression_Operator();

		/**
		 * The meta object literal for the '<em><b>Expr</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference UNARY_CALCULATOR_EXPRESSION__EXPR = eINSTANCE.getUnaryCalculatorExpression_Expr();

		/**
		 * The meta object literal for the '<em><b>Zinternal cache isl Object</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute UNARY_CALCULATOR_EXPRESSION__ZINTERNAL_CACHE_ISL_OBJECT = eINSTANCE.getUnaryCalculatorExpression_Z__internal_cache_islObject();

		/**
		 * The meta object literal for the '{@link alpha.model.impl.BinaryCalculatorExpressionImpl <em>Binary Calculator Expression</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see alpha.model.impl.BinaryCalculatorExpressionImpl
		 * @see alpha.model.impl.ModelPackageImpl#getBinaryCalculatorExpression()
		 * @generated
		 */
		EClass BINARY_CALCULATOR_EXPRESSION = eINSTANCE.getBinaryCalculatorExpression();

		/**
		 * The meta object literal for the '<em><b>Operator</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute BINARY_CALCULATOR_EXPRESSION__OPERATOR = eINSTANCE.getBinaryCalculatorExpression_Operator();

		/**
		 * The meta object literal for the '<em><b>Left</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference BINARY_CALCULATOR_EXPRESSION__LEFT = eINSTANCE.getBinaryCalculatorExpression_Left();

		/**
		 * The meta object literal for the '<em><b>Right</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference BINARY_CALCULATOR_EXPRESSION__RIGHT = eINSTANCE.getBinaryCalculatorExpression_Right();

		/**
		 * The meta object literal for the '<em><b>Zinternal cache isl Object</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute BINARY_CALCULATOR_EXPRESSION__ZINTERNAL_CACHE_ISL_OBJECT = eINSTANCE.getBinaryCalculatorExpression_Z__internal_cache_islObject();

		/**
		 * The meta object literal for the '{@link alpha.model.impl.VariableDomainImpl <em>Variable Domain</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see alpha.model.impl.VariableDomainImpl
		 * @see alpha.model.impl.ModelPackageImpl#getVariableDomain()
		 * @generated
		 */
		EClass VARIABLE_DOMAIN = eINSTANCE.getVariableDomain();

		/**
		 * The meta object literal for the '<em><b>Variable</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference VARIABLE_DOMAIN__VARIABLE = eINSTANCE.getVariableDomain_Variable();

		/**
		 * The meta object literal for the '{@link alpha.model.impl.RectangularDomainImpl <em>Rectangular Domain</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see alpha.model.impl.RectangularDomainImpl
		 * @see alpha.model.impl.ModelPackageImpl#getRectangularDomain()
		 * @generated
		 */
		EClass RECTANGULAR_DOMAIN = eINSTANCE.getRectangularDomain();

		/**
		 * The meta object literal for the '<em><b>Lower Bounds</b></em>' attribute list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute RECTANGULAR_DOMAIN__LOWER_BOUNDS = eINSTANCE.getRectangularDomain_LowerBounds();

		/**
		 * The meta object literal for the '<em><b>Upper Bounds</b></em>' attribute list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute RECTANGULAR_DOMAIN__UPPER_BOUNDS = eINSTANCE.getRectangularDomain_UpperBounds();

		/**
		 * The meta object literal for the '<em><b>Index Names</b></em>' attribute list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute RECTANGULAR_DOMAIN__INDEX_NAMES = eINSTANCE.getRectangularDomain_IndexNames();

		/**
		 * The meta object literal for the '<em><b>Zinternal cache isl Set</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute RECTANGULAR_DOMAIN__ZINTERNAL_CACHE_ISL_SET = eINSTANCE.getRectangularDomain_Z__internal_cache_islSet();

		/**
		 * The meta object literal for the '{@link alpha.model.impl.DefinedObjectImpl <em>Defined Object</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see alpha.model.impl.DefinedObjectImpl
		 * @see alpha.model.impl.ModelPackageImpl#getDefinedObject()
		 * @generated
		 */
		EClass DEFINED_OBJECT = eINSTANCE.getDefinedObject();

		/**
		 * The meta object literal for the '<em><b>Object</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DEFINED_OBJECT__OBJECT = eINSTANCE.getDefinedObject_Object();

		/**
		 * The meta object literal for the '<em><b>Zinternal Cycle Detector</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute DEFINED_OBJECT__ZINTERNAL_CYCLE_DETECTOR = eINSTANCE.getDefinedObject_Z__internalCycleDetector();

		/**
		 * The meta object literal for the '{@link alpha.model.impl.AlphaFunctionImpl <em>Alpha Function</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see alpha.model.impl.AlphaFunctionImpl
		 * @see alpha.model.impl.ModelPackageImpl#getAlphaFunction()
		 * @generated
		 */
		EClass ALPHA_FUNCTION = eINSTANCE.getAlphaFunction();

		/**
		 * The meta object literal for the '<em><b>Index List</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ALPHA_FUNCTION__INDEX_LIST = eINSTANCE.getAlphaFunction_IndexList();

		/**
		 * The meta object literal for the '<em><b>Exprs</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ALPHA_FUNCTION__EXPRS = eINSTANCE.getAlphaFunction_Exprs();

		/**
		 * The meta object literal for the '{@link alpha.model.impl.AlphaFunctionExpressionImpl <em>Alpha Function Expression</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see alpha.model.impl.AlphaFunctionExpressionImpl
		 * @see alpha.model.impl.ModelPackageImpl#getAlphaFunctionExpression()
		 * @generated
		 */
		EClass ALPHA_FUNCTION_EXPRESSION = eINSTANCE.getAlphaFunctionExpression();

		/**
		 * The meta object literal for the '{@link alpha.model.impl.AlphaFunctionBinaryExpressionImpl <em>Alpha Function Binary Expression</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see alpha.model.impl.AlphaFunctionBinaryExpressionImpl
		 * @see alpha.model.impl.ModelPackageImpl#getAlphaFunctionBinaryExpression()
		 * @generated
		 */
		EClass ALPHA_FUNCTION_BINARY_EXPRESSION = eINSTANCE.getAlphaFunctionBinaryExpression();

		/**
		 * The meta object literal for the '<em><b>Left</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ALPHA_FUNCTION_BINARY_EXPRESSION__LEFT = eINSTANCE.getAlphaFunctionBinaryExpression_Left();

		/**
		 * The meta object literal for the '<em><b>Right</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ALPHA_FUNCTION_BINARY_EXPRESSION__RIGHT = eINSTANCE.getAlphaFunctionBinaryExpression_Right();

		/**
		 * The meta object literal for the '<em><b>Operator</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ALPHA_FUNCTION_BINARY_EXPRESSION__OPERATOR = eINSTANCE.getAlphaFunctionBinaryExpression_Operator();

		/**
		 * The meta object literal for the '{@link alpha.model.impl.AlphaFunctionLiteralImpl <em>Alpha Function Literal</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see alpha.model.impl.AlphaFunctionLiteralImpl
		 * @see alpha.model.impl.ModelPackageImpl#getAlphaFunctionLiteral()
		 * @generated
		 */
		EClass ALPHA_FUNCTION_LITERAL = eINSTANCE.getAlphaFunctionLiteral();

		/**
		 * The meta object literal for the '<em><b>Value</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ALPHA_FUNCTION_LITERAL__VALUE = eINSTANCE.getAlphaFunctionLiteral_Value();

		/**
		 * The meta object literal for the '{@link alpha.model.impl.AlphaFunctionFloorImpl <em>Alpha Function Floor</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see alpha.model.impl.AlphaFunctionFloorImpl
		 * @see alpha.model.impl.ModelPackageImpl#getAlphaFunctionFloor()
		 * @generated
		 */
		EClass ALPHA_FUNCTION_FLOOR = eINSTANCE.getAlphaFunctionFloor();

		/**
		 * The meta object literal for the '<em><b>Expr</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ALPHA_FUNCTION_FLOOR__EXPR = eINSTANCE.getAlphaFunctionFloor_Expr();

		/**
		 * The meta object literal for the '{@link alpha.model.UNARY_OP <em>UNARY OP</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see alpha.model.UNARY_OP
		 * @see alpha.model.impl.ModelPackageImpl#getUNARY_OP()
		 * @generated
		 */
		EEnum UNARY_OP = eINSTANCE.getUNARY_OP();

		/**
		 * The meta object literal for the '{@link alpha.model.BINARY_OP <em>BINARY OP</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see alpha.model.BINARY_OP
		 * @see alpha.model.impl.ModelPackageImpl#getBINARY_OP()
		 * @generated
		 */
		EEnum BINARY_OP = eINSTANCE.getBINARY_OP();

		/**
		 * The meta object literal for the '{@link alpha.model.REDUCTION_OP <em>REDUCTION OP</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see alpha.model.REDUCTION_OP
		 * @see alpha.model.impl.ModelPackageImpl#getREDUCTION_OP()
		 * @generated
		 */
		EEnum REDUCTION_OP = eINSTANCE.getREDUCTION_OP();

		/**
		 * The meta object literal for the '{@link alpha.model.POLY_OBJECT_TYPE <em>POLY OBJECT TYPE</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see alpha.model.POLY_OBJECT_TYPE
		 * @see alpha.model.impl.ModelPackageImpl#getPOLY_OBJECT_TYPE()
		 * @generated
		 */
		EEnum POLY_OBJECT_TYPE = eINSTANCE.getPOLY_OBJECT_TYPE();

		/**
		 * The meta object literal for the '{@link alpha.model.CALCULATOR_UNARY_OP <em>CALCULATOR UNARY OP</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see alpha.model.CALCULATOR_UNARY_OP
		 * @see alpha.model.impl.ModelPackageImpl#getCALCULATOR_UNARY_OP()
		 * @generated
		 */
		EEnum CALCULATOR_UNARY_OP = eINSTANCE.getCALCULATOR_UNARY_OP();

		/**
		 * The meta object literal for the '{@link alpha.model.CALCULATOR_BINARY_OP <em>CALCULATOR BINARY OP</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see alpha.model.CALCULATOR_BINARY_OP
		 * @see alpha.model.impl.ModelPackageImpl#getCALCULATOR_BINARY_OP()
		 * @generated
		 */
		EEnum CALCULATOR_BINARY_OP = eINSTANCE.getCALCULATOR_BINARY_OP();

		/**
		 * The meta object literal for the '<em>JNI Object</em>' data type.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see fr.irisa.cairn.jnimap.runtime.JNIObject
		 * @see alpha.model.impl.ModelPackageImpl#getJNIObject()
		 * @generated
		 */
		EDataType JNI_OBJECT = eINSTANCE.getJNIObject();

		/**
		 * The meta object literal for the '<em>JNIISL Set</em>' data type.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see fr.irisa.cairn.jnimap.isl.ISLSet
		 * @see alpha.model.impl.ModelPackageImpl#getJNIISLSet()
		 * @generated
		 */
		EDataType JNIISL_SET = eINSTANCE.getJNIISLSet();

		/**
		 * The meta object literal for the '<em>JNIISL Map</em>' data type.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see fr.irisa.cairn.jnimap.isl.ISLMap
		 * @see alpha.model.impl.ModelPackageImpl#getJNIISLMap()
		 * @generated
		 */
		EDataType JNIISL_MAP = eINSTANCE.getJNIISLMap();

		/**
		 * The meta object literal for the '<em>JNIISL Multi Aff</em>' data type.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see fr.irisa.cairn.jnimap.isl.ISLMultiAff
		 * @see alpha.model.impl.ModelPackageImpl#getJNIISLMultiAff()
		 * @generated
		 */
		EDataType JNIISL_MULTI_AFF = eINSTANCE.getJNIISLMultiAff();

		/**
		 * The meta object literal for the '<em>JNIISLPWQ Polynomial</em>' data type.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see fr.irisa.cairn.jnimap.isl.ISLPWQPolynomial
		 * @see alpha.model.impl.ModelPackageImpl#getJNIISLPWQPolynomial()
		 * @generated
		 */
		EDataType JNIISLPWQ_POLYNOMIAL = eINSTANCE.getJNIISLPWQPolynomial();

		/**
		 * The meta object literal for the '<em>ISL FORMAT</em>' data type.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see fr.irisa.cairn.jnimap.isl.ISL_FORMAT
		 * @see alpha.model.impl.ModelPackageImpl#getISL_FORMAT()
		 * @generated
		 */
		EDataType ISL_FORMAT = eINSTANCE.getISL_FORMAT();

		/**
		 * The meta object literal for the '<em>List Variable Expression</em>' data type.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see java.util.List
		 * @see alpha.model.impl.ModelPackageImpl#getListVariableExpression()
		 * @generated
		 */
		EDataType LIST_VARIABLE_EXPRESSION = eINSTANCE.getListVariableExpression();

		/**
		 * The meta object literal for the '<em>Integer Queue</em>' data type.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see java.util.Queue
		 * @see alpha.model.impl.ModelPackageImpl#getIntegerQueue()
		 * @generated
		 */
		EDataType INTEGER_QUEUE = eINSTANCE.getIntegerQueue();

		/**
		 * The meta object literal for the '<em>String</em>' data type.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see java.lang.String
		 * @see alpha.model.impl.ModelPackageImpl#getString()
		 * @generated
		 */
		EDataType STRING = eINSTANCE.getString();

		/**
		 * The meta object literal for the '<em>Face</em>' data type.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see alpha.model.util.Face
		 * @see alpha.model.impl.ModelPackageImpl#getFace()
		 * @generated
		 */
		EDataType FACE = eINSTANCE.getFace();

		/**
		 * The meta object literal for the '<em>Face Lattice</em>' data type.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see alpha.model.util.FaceLattice
		 * @see alpha.model.impl.ModelPackageImpl#getFaceLattice()
		 * @generated
		 */
		EDataType FACE_LATTICE = eINSTANCE.getFaceLattice();

		/**
		 * The meta object literal for the '<em>int</em>' data type.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see java.lang.Integer
		 * @see alpha.model.impl.ModelPackageImpl#getint()
		 * @generated
		 */
		EDataType INT = eINSTANCE.getint();

		/**
		 * The meta object literal for the '<em>float</em>' data type.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see java.lang.Float
		 * @see alpha.model.impl.ModelPackageImpl#getfloat()
		 * @generated
		 */
		EDataType FLOAT = eINSTANCE.getfloat();

		/**
		 * The meta object literal for the '<em>double</em>' data type.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see java.lang.Double
		 * @see alpha.model.impl.ModelPackageImpl#getdouble()
		 * @generated
		 */
		EDataType DOUBLE = eINSTANCE.getdouble();

		/**
		 * The meta object literal for the '<em>boolean</em>' data type.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see java.lang.Boolean
		 * @see alpha.model.impl.ModelPackageImpl#getboolean()
		 * @generated
		 */
		EDataType BOOLEAN = eINSTANCE.getboolean();

	}

} //ModelPackage
