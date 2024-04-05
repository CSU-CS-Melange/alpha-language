/**
 */
package alpha.codegen;

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
	 * The meta object id for the '{@link alpha.codegen.impl.NodeImpl <em>Node</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see alpha.codegen.impl.NodeImpl
	 * @see alpha.codegen.impl.CodegenPackageImpl#getNode()
	 * @generated
	 */
	int NODE = 0;

	/**
	 * The number of structural features of the '<em>Node</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NODE_FEATURE_COUNT = 0;

	/**
	 * The meta object id for the '{@link alpha.codegen.Visitable <em>Visitable</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see alpha.codegen.Visitable
	 * @see alpha.codegen.impl.CodegenPackageImpl#getVisitable()
	 * @generated
	 */
	int VISITABLE = 1;

	/**
	 * The number of structural features of the '<em>Visitable</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VISITABLE_FEATURE_COUNT = 0;

	/**
	 * The meta object id for the '{@link alpha.codegen.Visitor <em>Visitor</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see alpha.codegen.Visitor
	 * @see alpha.codegen.impl.CodegenPackageImpl#getVisitor()
	 * @generated
	 */
	int VISITOR = 2;

	/**
	 * The number of structural features of the '<em>Visitor</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VISITOR_FEATURE_COUNT = 0;

	/**
	 * The meta object id for the '{@link alpha.codegen.impl.IncludeImpl <em>Include</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see alpha.codegen.impl.IncludeImpl
	 * @see alpha.codegen.impl.CodegenPackageImpl#getInclude()
	 * @generated
	 */
	int INCLUDE = 3;

	/**
	 * The feature id for the '<em><b>Program</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INCLUDE__PROGRAM = NODE_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INCLUDE__NAME = NODE_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Include</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INCLUDE_FEATURE_COUNT = NODE_FEATURE_COUNT + 2;

	/**
	 * The meta object id for the '{@link alpha.codegen.impl.MacroImpl <em>Macro</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see alpha.codegen.impl.MacroImpl
	 * @see alpha.codegen.impl.CodegenPackageImpl#getMacro()
	 * @generated
	 */
	int MACRO = 4;

	/**
	 * The feature id for the '<em><b>Left</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MACRO__LEFT = NODE_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Right</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MACRO__RIGHT = NODE_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Macro</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MACRO_FEATURE_COUNT = NODE_FEATURE_COUNT + 2;

	/**
	 * The meta object id for the '{@link alpha.codegen.impl.GlobalMacroImpl <em>Global Macro</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see alpha.codegen.impl.GlobalMacroImpl
	 * @see alpha.codegen.impl.CodegenPackageImpl#getGlobalMacro()
	 * @generated
	 */
	int GLOBAL_MACRO = 5;

	/**
	 * The feature id for the '<em><b>Left</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GLOBAL_MACRO__LEFT = MACRO__LEFT;

	/**
	 * The feature id for the '<em><b>Right</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GLOBAL_MACRO__RIGHT = MACRO__RIGHT;

	/**
	 * The feature id for the '<em><b>Program</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GLOBAL_MACRO__PROGRAM = MACRO_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Global Macro</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GLOBAL_MACRO_FEATURE_COUNT = MACRO_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link alpha.codegen.impl.AbstractMemoryMacroImpl <em>Abstract Memory Macro</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see alpha.codegen.impl.AbstractMemoryMacroImpl
	 * @see alpha.codegen.impl.CodegenPackageImpl#getAbstractMemoryMacro()
	 * @generated
	 */
	int ABSTRACT_MEMORY_MACRO = 6;

	/**
	 * The feature id for the '<em><b>Left</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABSTRACT_MEMORY_MACRO__LEFT = MACRO__LEFT;

	/**
	 * The feature id for the '<em><b>Right</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABSTRACT_MEMORY_MACRO__RIGHT = MACRO__RIGHT;

	/**
	 * The feature id for the '<em><b>Map</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABSTRACT_MEMORY_MACRO__MAP = MACRO_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Abstract Memory Macro</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABSTRACT_MEMORY_MACRO_FEATURE_COUNT = MACRO_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link alpha.codegen.impl.MemoryMacroImpl <em>Memory Macro</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see alpha.codegen.impl.MemoryMacroImpl
	 * @see alpha.codegen.impl.CodegenPackageImpl#getMemoryMacro()
	 * @generated
	 */
	int MEMORY_MACRO = 7;

	/**
	 * The feature id for the '<em><b>Left</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MEMORY_MACRO__LEFT = ABSTRACT_MEMORY_MACRO__LEFT;

	/**
	 * The feature id for the '<em><b>Right</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MEMORY_MACRO__RIGHT = ABSTRACT_MEMORY_MACRO__RIGHT;

	/**
	 * The feature id for the '<em><b>Map</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MEMORY_MACRO__MAP = ABSTRACT_MEMORY_MACRO__MAP;

	/**
	 * The feature id for the '<em><b>Function</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MEMORY_MACRO__FUNCTION = ABSTRACT_MEMORY_MACRO_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Allocation</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MEMORY_MACRO__ALLOCATION = ABSTRACT_MEMORY_MACRO_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Variable</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MEMORY_MACRO__VARIABLE = ABSTRACT_MEMORY_MACRO_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>Memory Macro</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MEMORY_MACRO_FEATURE_COUNT = ABSTRACT_MEMORY_MACRO_FEATURE_COUNT + 3;

	/**
	 * The meta object id for the '{@link alpha.codegen.impl.MemoryAllocationImpl <em>Memory Allocation</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see alpha.codegen.impl.MemoryAllocationImpl
	 * @see alpha.codegen.impl.CodegenPackageImpl#getMemoryAllocation()
	 * @generated
	 */
	int MEMORY_ALLOCATION = 8;

	/**
	 * The feature id for the '<em><b>Macro</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MEMORY_ALLOCATION__MACRO = NODE_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Variable</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MEMORY_ALLOCATION__VARIABLE = NODE_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Map</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MEMORY_ALLOCATION__MAP = NODE_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Domain</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MEMORY_ALLOCATION__DOMAIN = NODE_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>ISLAST Node</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MEMORY_ALLOCATION__ISLAST_NODE = NODE_FEATURE_COUNT + 4;

	/**
	 * The number of structural features of the '<em>Memory Allocation</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MEMORY_ALLOCATION_FEATURE_COUNT = NODE_FEATURE_COUNT + 5;

	/**
	 * The meta object id for the '{@link alpha.codegen.impl.GlobalMemoryMacroImpl <em>Global Memory Macro</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see alpha.codegen.impl.GlobalMemoryMacroImpl
	 * @see alpha.codegen.impl.CodegenPackageImpl#getGlobalMemoryMacro()
	 * @generated
	 */
	int GLOBAL_MEMORY_MACRO = 9;

	/**
	 * The feature id for the '<em><b>Left</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GLOBAL_MEMORY_MACRO__LEFT = ABSTRACT_MEMORY_MACRO__LEFT;

	/**
	 * The feature id for the '<em><b>Right</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GLOBAL_MEMORY_MACRO__RIGHT = ABSTRACT_MEMORY_MACRO__RIGHT;

	/**
	 * The feature id for the '<em><b>Map</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GLOBAL_MEMORY_MACRO__MAP = ABSTRACT_MEMORY_MACRO__MAP;

	/**
	 * The feature id for the '<em><b>Variable</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GLOBAL_MEMORY_MACRO__VARIABLE = ABSTRACT_MEMORY_MACRO_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Global Memory Macro</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GLOBAL_MEMORY_MACRO_FEATURE_COUNT = ABSTRACT_MEMORY_MACRO_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link alpha.codegen.impl.StatementMacroImpl <em>Statement Macro</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see alpha.codegen.impl.StatementMacroImpl
	 * @see alpha.codegen.impl.CodegenPackageImpl#getStatementMacro()
	 * @generated
	 */
	int STATEMENT_MACRO = 10;

	/**
	 * The feature id for the '<em><b>Left</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATEMENT_MACRO__LEFT = MACRO__LEFT;

	/**
	 * The feature id for the '<em><b>Right</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATEMENT_MACRO__RIGHT = MACRO__RIGHT;

	/**
	 * The feature id for the '<em><b>Function Body</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATEMENT_MACRO__FUNCTION_BODY = MACRO_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Statement Macro</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATEMENT_MACRO_FEATURE_COUNT = MACRO_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link alpha.codegen.impl.BaseVariableImpl <em>Base Variable</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see alpha.codegen.impl.BaseVariableImpl
	 * @see alpha.codegen.impl.CodegenPackageImpl#getBaseVariable()
	 * @generated
	 */
	int BASE_VARIABLE = 11;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BASE_VARIABLE__NAME = NODE_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Elem Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BASE_VARIABLE__ELEM_TYPE = NODE_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Base Variable</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BASE_VARIABLE_FEATURE_COUNT = NODE_FEATURE_COUNT + 2;

	/**
	 * The meta object id for the '{@link alpha.codegen.impl.GlobalVariableImpl <em>Global Variable</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see alpha.codegen.impl.GlobalVariableImpl
	 * @see alpha.codegen.impl.CodegenPackageImpl#getGlobalVariable()
	 * @generated
	 */
	int GLOBAL_VARIABLE = 12;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GLOBAL_VARIABLE__NAME = BASE_VARIABLE__NAME;

	/**
	 * The feature id for the '<em><b>Elem Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GLOBAL_VARIABLE__ELEM_TYPE = BASE_VARIABLE__ELEM_TYPE;

	/**
	 * The feature id for the '<em><b>Program</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GLOBAL_VARIABLE__PROGRAM = BASE_VARIABLE_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Memory Macro</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GLOBAL_VARIABLE__MEMORY_MACRO = BASE_VARIABLE_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Alpha Variable</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GLOBAL_VARIABLE__ALPHA_VARIABLE = BASE_VARIABLE_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GLOBAL_VARIABLE__TYPE = BASE_VARIABLE_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Num Dims</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GLOBAL_VARIABLE__NUM_DIMS = BASE_VARIABLE_FEATURE_COUNT + 4;

	/**
	 * The feature id for the '<em><b>Flag Variable</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GLOBAL_VARIABLE__FLAG_VARIABLE = BASE_VARIABLE_FEATURE_COUNT + 5;

	/**
	 * The number of structural features of the '<em>Global Variable</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GLOBAL_VARIABLE_FEATURE_COUNT = BASE_VARIABLE_FEATURE_COUNT + 6;

	/**
	 * The meta object id for the '{@link alpha.codegen.impl.ArrayVariableImpl <em>Array Variable</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see alpha.codegen.impl.ArrayVariableImpl
	 * @see alpha.codegen.impl.CodegenPackageImpl#getArrayVariable()
	 * @generated
	 */
	int ARRAY_VARIABLE = 13;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ARRAY_VARIABLE__NAME = GLOBAL_VARIABLE__NAME;

	/**
	 * The feature id for the '<em><b>Elem Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ARRAY_VARIABLE__ELEM_TYPE = GLOBAL_VARIABLE__ELEM_TYPE;

	/**
	 * The feature id for the '<em><b>Program</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ARRAY_VARIABLE__PROGRAM = GLOBAL_VARIABLE__PROGRAM;

	/**
	 * The feature id for the '<em><b>Memory Macro</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ARRAY_VARIABLE__MEMORY_MACRO = GLOBAL_VARIABLE__MEMORY_MACRO;

	/**
	 * The feature id for the '<em><b>Alpha Variable</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ARRAY_VARIABLE__ALPHA_VARIABLE = GLOBAL_VARIABLE__ALPHA_VARIABLE;

	/**
	 * The feature id for the '<em><b>Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ARRAY_VARIABLE__TYPE = GLOBAL_VARIABLE__TYPE;

	/**
	 * The feature id for the '<em><b>Num Dims</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ARRAY_VARIABLE__NUM_DIMS = GLOBAL_VARIABLE__NUM_DIMS;

	/**
	 * The feature id for the '<em><b>Flag Variable</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ARRAY_VARIABLE__FLAG_VARIABLE = GLOBAL_VARIABLE__FLAG_VARIABLE;

	/**
	 * The number of structural features of the '<em>Array Variable</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ARRAY_VARIABLE_FEATURE_COUNT = GLOBAL_VARIABLE_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link alpha.codegen.impl.FunctionBodyImpl <em>Function Body</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see alpha.codegen.impl.FunctionBodyImpl
	 * @see alpha.codegen.impl.CodegenPackageImpl#getFunctionBody()
	 * @generated
	 */
	int FUNCTION_BODY = 14;

	/**
	 * The feature id for the '<em><b>Function</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUNCTION_BODY__FUNCTION = NODE_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Statement Macros</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUNCTION_BODY__STATEMENT_MACROS = NODE_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>ISLAST Node</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUNCTION_BODY__ISLAST_NODE = NODE_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>Function Body</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUNCTION_BODY_FEATURE_COUNT = NODE_FEATURE_COUNT + 3;

	/**
	 * The meta object id for the '{@link alpha.codegen.impl.FunctionImpl <em>Function</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see alpha.codegen.impl.FunctionImpl
	 * @see alpha.codegen.impl.CodegenPackageImpl#getFunction()
	 * @generated
	 */
	int FUNCTION = 15;

	/**
	 * The feature id for the '<em><b>Program</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUNCTION__PROGRAM = NODE_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Return Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUNCTION__RETURN_TYPE = NODE_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUNCTION__NAME = NODE_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Scalar Args</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUNCTION__SCALAR_ARGS = NODE_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Array Args</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUNCTION__ARRAY_ARGS = NODE_FEATURE_COUNT + 4;

	/**
	 * The feature id for the '<em><b>Memory Macros</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUNCTION__MEMORY_MACROS = NODE_FEATURE_COUNT + 5;

	/**
	 * The feature id for the '<em><b>Body</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUNCTION__BODY = NODE_FEATURE_COUNT + 6;

	/**
	 * The number of structural features of the '<em>Function</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUNCTION_FEATURE_COUNT = NODE_FEATURE_COUNT + 7;

	/**
	 * The meta object id for the '{@link alpha.codegen.impl.EvalFunctionImpl <em>Eval Function</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see alpha.codegen.impl.EvalFunctionImpl
	 * @see alpha.codegen.impl.CodegenPackageImpl#getEvalFunction()
	 * @generated
	 */
	int EVAL_FUNCTION = 16;

	/**
	 * The feature id for the '<em><b>Program</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EVAL_FUNCTION__PROGRAM = FUNCTION__PROGRAM;

	/**
	 * The feature id for the '<em><b>Return Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EVAL_FUNCTION__RETURN_TYPE = FUNCTION__RETURN_TYPE;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EVAL_FUNCTION__NAME = FUNCTION__NAME;

	/**
	 * The feature id for the '<em><b>Scalar Args</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EVAL_FUNCTION__SCALAR_ARGS = FUNCTION__SCALAR_ARGS;

	/**
	 * The feature id for the '<em><b>Array Args</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EVAL_FUNCTION__ARRAY_ARGS = FUNCTION__ARRAY_ARGS;

	/**
	 * The feature id for the '<em><b>Memory Macros</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EVAL_FUNCTION__MEMORY_MACROS = FUNCTION__MEMORY_MACROS;

	/**
	 * The feature id for the '<em><b>Body</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EVAL_FUNCTION__BODY = FUNCTION__BODY;

	/**
	 * The feature id for the '<em><b>Variable</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EVAL_FUNCTION__VARIABLE = FUNCTION_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Flag Variable</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EVAL_FUNCTION__FLAG_VARIABLE = FUNCTION_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Equation</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EVAL_FUNCTION__EQUATION = FUNCTION_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>Eval Function</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EVAL_FUNCTION_FEATURE_COUNT = FUNCTION_FEATURE_COUNT + 3;

	/**
	 * The meta object id for the '{@link alpha.codegen.impl.ReduceFunctionImpl <em>Reduce Function</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see alpha.codegen.impl.ReduceFunctionImpl
	 * @see alpha.codegen.impl.CodegenPackageImpl#getReduceFunction()
	 * @generated
	 */
	int REDUCE_FUNCTION = 17;

	/**
	 * The feature id for the '<em><b>Program</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REDUCE_FUNCTION__PROGRAM = FUNCTION__PROGRAM;

	/**
	 * The feature id for the '<em><b>Return Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REDUCE_FUNCTION__RETURN_TYPE = FUNCTION__RETURN_TYPE;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REDUCE_FUNCTION__NAME = FUNCTION__NAME;

	/**
	 * The feature id for the '<em><b>Scalar Args</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REDUCE_FUNCTION__SCALAR_ARGS = FUNCTION__SCALAR_ARGS;

	/**
	 * The feature id for the '<em><b>Array Args</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REDUCE_FUNCTION__ARRAY_ARGS = FUNCTION__ARRAY_ARGS;

	/**
	 * The feature id for the '<em><b>Memory Macros</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REDUCE_FUNCTION__MEMORY_MACROS = FUNCTION__MEMORY_MACROS;

	/**
	 * The feature id for the '<em><b>Body</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REDUCE_FUNCTION__BODY = FUNCTION__BODY;

	/**
	 * The feature id for the '<em><b>Reduce Expr</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REDUCE_FUNCTION__REDUCE_EXPR = FUNCTION_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Reduce Var</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REDUCE_FUNCTION__REDUCE_VAR = FUNCTION_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Macro Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REDUCE_FUNCTION__MACRO_NAME = FUNCTION_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>Reduce Function</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REDUCE_FUNCTION_FEATURE_COUNT = FUNCTION_FEATURE_COUNT + 3;

	/**
	 * The meta object id for the '{@link alpha.codegen.impl.ProgramImpl <em>Program</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see alpha.codegen.impl.ProgramImpl
	 * @see alpha.codegen.impl.CodegenPackageImpl#getProgram()
	 * @generated
	 */
	int PROGRAM = 18;

	/**
	 * The feature id for the '<em><b>System</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROGRAM__SYSTEM = NODE_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Includes</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROGRAM__INCLUDES = NODE_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Common Macros</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROGRAM__COMMON_MACROS = NODE_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Global Variables</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROGRAM__GLOBAL_VARIABLES = NODE_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Functions</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROGRAM__FUNCTIONS = NODE_FEATURE_COUNT + 4;

	/**
	 * The feature id for the '<em><b>Reduce Functions</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROGRAM__REDUCE_FUNCTIONS = NODE_FEATURE_COUNT + 5;

	/**
	 * The number of structural features of the '<em>Program</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROGRAM_FEATURE_COUNT = NODE_FEATURE_COUNT + 6;

	/**
	 * The meta object id for the '{@link alpha.codegen.impl.AlphaOpImpl <em>Alpha Op</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see alpha.codegen.impl.AlphaOpImpl
	 * @see alpha.codegen.impl.CodegenPackageImpl#getAlphaOp()
	 * @generated
	 */
	int ALPHA_OP = 19;

	/**
	 * The number of structural features of the '<em>Alpha Op</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ALPHA_OP_FEATURE_COUNT = 0;

	/**
	 * The meta object id for the '{@link alpha.codegen.impl.PolynomialImpl <em>Polynomial</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see alpha.codegen.impl.PolynomialImpl
	 * @see alpha.codegen.impl.CodegenPackageImpl#getPolynomial()
	 * @generated
	 */
	int POLYNOMIAL = 20;

	/**
	 * The feature id for the '<em><b>Isl Polynomial</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int POLYNOMIAL__ISL_POLYNOMIAL = 0;

	/**
	 * The number of structural features of the '<em>Polynomial</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int POLYNOMIAL_FEATURE_COUNT = 1;

	/**
	 * The meta object id for the '{@link alpha.codegen.VariableType <em>Variable Type</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see alpha.codegen.VariableType
	 * @see alpha.codegen.impl.CodegenPackageImpl#getVariableType()
	 * @generated
	 */
	int VARIABLE_TYPE = 21;

	/**
	 * The meta object id for the '{@link alpha.codegen.DataType <em>Data Type</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see alpha.codegen.DataType
	 * @see alpha.codegen.impl.CodegenPackageImpl#getDataType()
	 * @generated
	 */
	int DATA_TYPE = 22;

	/**
	 * The meta object id for the '{@link alpha.codegen.C_UNARY_OP <em>CUNARY OP</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see alpha.codegen.C_UNARY_OP
	 * @see alpha.codegen.impl.CodegenPackageImpl#getC_UNARY_OP()
	 * @generated
	 */
	int CUNARY_OP = 23;

	/**
	 * The meta object id for the '{@link alpha.codegen.C_BINARY_OP <em>CBINARY OP</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see alpha.codegen.C_BINARY_OP
	 * @see alpha.codegen.impl.CodegenPackageImpl#getC_BINARY_OP()
	 * @generated
	 */
	int CBINARY_OP = 24;

	/**
	 * The meta object id for the '{@link alpha.codegen.C_REDUCTION_OP <em>CREDUCTION OP</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see alpha.codegen.C_REDUCTION_OP
	 * @see alpha.codegen.impl.CodegenPackageImpl#getC_REDUCTION_OP()
	 * @generated
	 */
	int CREDUCTION_OP = 25;

	/**
	 * The meta object id for the '<em>ISL Set</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see fr.irisa.cairn.jnimap.isl.ISLSet
	 * @see alpha.codegen.impl.CodegenPackageImpl#getISLSet()
	 * @generated
	 */
	int ISL_SET = 26;

	/**
	 * The meta object id for the '<em>ISL Map</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see fr.irisa.cairn.jnimap.isl.ISLMap
	 * @see alpha.codegen.impl.CodegenPackageImpl#getISLMap()
	 * @generated
	 */
	int ISL_MAP = 27;

	/**
	 * The meta object id for the '<em>ISLAST Node</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see fr.irisa.cairn.jnimap.isl.ISLASTNode
	 * @see alpha.codegen.impl.CodegenPackageImpl#getISLASTNode()
	 * @generated
	 */
	int ISLAST_NODE = 28;

	/**
	 * The meta object id for the '<em>ISL Aff</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see fr.irisa.cairn.jnimap.isl.ISLAff
	 * @see alpha.codegen.impl.CodegenPackageImpl#getISLAff()
	 * @generated
	 */
	int ISL_AFF = 29;

	/**
	 * The meta object id for the '<em>ISL Aff List</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see fr.irisa.cairn.jnimap.isl.ISLAffList
	 * @see alpha.codegen.impl.CodegenPackageImpl#getISLAffList()
	 * @generated
	 */
	int ISL_AFF_LIST = 30;

	/**
	 * The meta object id for the '<em>ISL Dim Type</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see fr.irisa.cairn.jnimap.isl.ISLDimType
	 * @see alpha.codegen.impl.CodegenPackageImpl#getISLDimType()
	 * @generated
	 */
	int ISL_DIM_TYPE = 31;

	/**
	 * The meta object id for the '<em>ISL FORMAT</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see fr.irisa.cairn.jnimap.isl.ISL_FORMAT
	 * @see alpha.codegen.impl.CodegenPackageImpl#getISL_FORMAT()
	 * @generated
	 */
	int ISL_FORMAT = 32;

	/**
	 * The meta object id for the '<em>ISLPWQ Polynomial</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see fr.irisa.cairn.jnimap.isl.ISLPWQPolynomial
	 * @see alpha.codegen.impl.CodegenPackageImpl#getISLPWQPolynomial()
	 * @generated
	 */
	int ISLPWQ_POLYNOMIAL = 33;

	/**
	 * The meta object id for the '<em>ISLQ Polynomial Piece</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see fr.irisa.cairn.jnimap.isl.ISLQPolynomialPiece
	 * @see alpha.codegen.impl.CodegenPackageImpl#getISLQPolynomialPiece()
	 * @generated
	 */
	int ISLQ_POLYNOMIAL_PIECE = 34;

	/**
	 * The meta object id for the '<em>ISLQ Polynomial</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see fr.irisa.cairn.jnimap.isl.ISLQPolynomial
	 * @see alpha.codegen.impl.CodegenPackageImpl#getISLQPolynomial()
	 * @generated
	 */
	int ISLQ_POLYNOMIAL = 35;

	/**
	 * The meta object id for the '<em>ISL Term</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see fr.irisa.cairn.jnimap.isl.ISLTerm
	 * @see alpha.codegen.impl.CodegenPackageImpl#getISLTerm()
	 * @generated
	 */
	int ISL_TERM = 36;

	/**
	 * The meta object id for the '<em>Hash Map</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see java.util.HashMap
	 * @see alpha.codegen.impl.CodegenPackageImpl#getHashMap()
	 * @generated
	 */
	int HASH_MAP = 37;


	/**
	 * Returns the meta object for class '{@link alpha.codegen.Node <em>Node</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Node</em>'.
	 * @see alpha.codegen.Node
	 * @generated
	 */
	EClass getNode();

	/**
	 * Returns the meta object for class '{@link alpha.codegen.Visitable <em>Visitable</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Visitable</em>'.
	 * @see alpha.codegen.Visitable
	 * @generated
	 */
	EClass getVisitable();

	/**
	 * Returns the meta object for class '{@link alpha.codegen.Visitor <em>Visitor</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Visitor</em>'.
	 * @see alpha.codegen.Visitor
	 * @generated
	 */
	EClass getVisitor();

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
	 * Returns the meta object for the container reference '{@link alpha.codegen.Include#getProgram <em>Program</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the container reference '<em>Program</em>'.
	 * @see alpha.codegen.Include#getProgram()
	 * @see #getInclude()
	 * @generated
	 */
	EReference getInclude_Program();

	/**
	 * Returns the meta object for the attribute '{@link alpha.codegen.Include#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see alpha.codegen.Include#getName()
	 * @see #getInclude()
	 * @generated
	 */
	EAttribute getInclude_Name();

	/**
	 * Returns the meta object for class '{@link alpha.codegen.Macro <em>Macro</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Macro</em>'.
	 * @see alpha.codegen.Macro
	 * @generated
	 */
	EClass getMacro();

	/**
	 * Returns the meta object for the attribute '{@link alpha.codegen.Macro#getLeft <em>Left</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Left</em>'.
	 * @see alpha.codegen.Macro#getLeft()
	 * @see #getMacro()
	 * @generated
	 */
	EAttribute getMacro_Left();

	/**
	 * Returns the meta object for the attribute '{@link alpha.codegen.Macro#getRight <em>Right</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Right</em>'.
	 * @see alpha.codegen.Macro#getRight()
	 * @see #getMacro()
	 * @generated
	 */
	EAttribute getMacro_Right();

	/**
	 * Returns the meta object for class '{@link alpha.codegen.GlobalMacro <em>Global Macro</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Global Macro</em>'.
	 * @see alpha.codegen.GlobalMacro
	 * @generated
	 */
	EClass getGlobalMacro();

	/**
	 * Returns the meta object for the container reference '{@link alpha.codegen.GlobalMacro#getProgram <em>Program</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the container reference '<em>Program</em>'.
	 * @see alpha.codegen.GlobalMacro#getProgram()
	 * @see #getGlobalMacro()
	 * @generated
	 */
	EReference getGlobalMacro_Program();

	/**
	 * Returns the meta object for class '{@link alpha.codegen.AbstractMemoryMacro <em>Abstract Memory Macro</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Abstract Memory Macro</em>'.
	 * @see alpha.codegen.AbstractMemoryMacro
	 * @generated
	 */
	EClass getAbstractMemoryMacro();

	/**
	 * Returns the meta object for the attribute '{@link alpha.codegen.AbstractMemoryMacro#getMap <em>Map</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Map</em>'.
	 * @see alpha.codegen.AbstractMemoryMacro#getMap()
	 * @see #getAbstractMemoryMacro()
	 * @generated
	 */
	EAttribute getAbstractMemoryMacro_Map();

	/**
	 * Returns the meta object for class '{@link alpha.codegen.MemoryMacro <em>Memory Macro</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Memory Macro</em>'.
	 * @see alpha.codegen.MemoryMacro
	 * @generated
	 */
	EClass getMemoryMacro();

	/**
	 * Returns the meta object for the container reference '{@link alpha.codegen.MemoryMacro#getFunction <em>Function</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the container reference '<em>Function</em>'.
	 * @see alpha.codegen.MemoryMacro#getFunction()
	 * @see #getMemoryMacro()
	 * @generated
	 */
	EReference getMemoryMacro_Function();

	/**
	 * Returns the meta object for the reference '{@link alpha.codegen.MemoryMacro#getAllocation <em>Allocation</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Allocation</em>'.
	 * @see alpha.codegen.MemoryMacro#getAllocation()
	 * @see #getMemoryMacro()
	 * @generated
	 */
	EReference getMemoryMacro_Allocation();

	/**
	 * Returns the meta object for the reference '{@link alpha.codegen.MemoryMacro#getVariable <em>Variable</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Variable</em>'.
	 * @see alpha.codegen.MemoryMacro#getVariable()
	 * @see #getMemoryMacro()
	 * @generated
	 */
	EReference getMemoryMacro_Variable();

	/**
	 * Returns the meta object for class '{@link alpha.codegen.MemoryAllocation <em>Memory Allocation</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Memory Allocation</em>'.
	 * @see alpha.codegen.MemoryAllocation
	 * @generated
	 */
	EClass getMemoryAllocation();

	/**
	 * Returns the meta object for the reference '{@link alpha.codegen.MemoryAllocation#getMacro <em>Macro</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Macro</em>'.
	 * @see alpha.codegen.MemoryAllocation#getMacro()
	 * @see #getMemoryAllocation()
	 * @generated
	 */
	EReference getMemoryAllocation_Macro();

	/**
	 * Returns the meta object for the reference '{@link alpha.codegen.MemoryAllocation#getVariable <em>Variable</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Variable</em>'.
	 * @see alpha.codegen.MemoryAllocation#getVariable()
	 * @see #getMemoryAllocation()
	 * @generated
	 */
	EReference getMemoryAllocation_Variable();

	/**
	 * Returns the meta object for the attribute '{@link alpha.codegen.MemoryAllocation#getMap <em>Map</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Map</em>'.
	 * @see alpha.codegen.MemoryAllocation#getMap()
	 * @see #getMemoryAllocation()
	 * @generated
	 */
	EAttribute getMemoryAllocation_Map();

	/**
	 * Returns the meta object for the attribute '{@link alpha.codegen.MemoryAllocation#getDomain <em>Domain</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Domain</em>'.
	 * @see alpha.codegen.MemoryAllocation#getDomain()
	 * @see #getMemoryAllocation()
	 * @generated
	 */
	EAttribute getMemoryAllocation_Domain();

	/**
	 * Returns the meta object for the attribute '{@link alpha.codegen.MemoryAllocation#getISLASTNode <em>ISLAST Node</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>ISLAST Node</em>'.
	 * @see alpha.codegen.MemoryAllocation#getISLASTNode()
	 * @see #getMemoryAllocation()
	 * @generated
	 */
	EAttribute getMemoryAllocation_ISLASTNode();

	/**
	 * Returns the meta object for class '{@link alpha.codegen.GlobalMemoryMacro <em>Global Memory Macro</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Global Memory Macro</em>'.
	 * @see alpha.codegen.GlobalMemoryMacro
	 * @generated
	 */
	EClass getGlobalMemoryMacro();

	/**
	 * Returns the meta object for the container reference '{@link alpha.codegen.GlobalMemoryMacro#getVariable <em>Variable</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the container reference '<em>Variable</em>'.
	 * @see alpha.codegen.GlobalMemoryMacro#getVariable()
	 * @see #getGlobalMemoryMacro()
	 * @generated
	 */
	EReference getGlobalMemoryMacro_Variable();

	/**
	 * Returns the meta object for class '{@link alpha.codegen.StatementMacro <em>Statement Macro</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Statement Macro</em>'.
	 * @see alpha.codegen.StatementMacro
	 * @generated
	 */
	EClass getStatementMacro();

	/**
	 * Returns the meta object for the container reference '{@link alpha.codegen.StatementMacro#getFunctionBody <em>Function Body</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the container reference '<em>Function Body</em>'.
	 * @see alpha.codegen.StatementMacro#getFunctionBody()
	 * @see #getStatementMacro()
	 * @generated
	 */
	EReference getStatementMacro_FunctionBody();

	/**
	 * Returns the meta object for class '{@link alpha.codegen.BaseVariable <em>Base Variable</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Base Variable</em>'.
	 * @see alpha.codegen.BaseVariable
	 * @generated
	 */
	EClass getBaseVariable();

	/**
	 * Returns the meta object for the attribute '{@link alpha.codegen.BaseVariable#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see alpha.codegen.BaseVariable#getName()
	 * @see #getBaseVariable()
	 * @generated
	 */
	EAttribute getBaseVariable_Name();

	/**
	 * Returns the meta object for the attribute '{@link alpha.codegen.BaseVariable#getElemType <em>Elem Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Elem Type</em>'.
	 * @see alpha.codegen.BaseVariable#getElemType()
	 * @see #getBaseVariable()
	 * @generated
	 */
	EAttribute getBaseVariable_ElemType();

	/**
	 * Returns the meta object for class '{@link alpha.codegen.GlobalVariable <em>Global Variable</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Global Variable</em>'.
	 * @see alpha.codegen.GlobalVariable
	 * @generated
	 */
	EClass getGlobalVariable();

	/**
	 * Returns the meta object for the container reference '{@link alpha.codegen.GlobalVariable#getProgram <em>Program</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the container reference '<em>Program</em>'.
	 * @see alpha.codegen.GlobalVariable#getProgram()
	 * @see #getGlobalVariable()
	 * @generated
	 */
	EReference getGlobalVariable_Program();

	/**
	 * Returns the meta object for the containment reference '{@link alpha.codegen.GlobalVariable#getMemoryMacro <em>Memory Macro</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Memory Macro</em>'.
	 * @see alpha.codegen.GlobalVariable#getMemoryMacro()
	 * @see #getGlobalVariable()
	 * @generated
	 */
	EReference getGlobalVariable_MemoryMacro();

	/**
	 * Returns the meta object for the reference '{@link alpha.codegen.GlobalVariable#getAlphaVariable <em>Alpha Variable</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Alpha Variable</em>'.
	 * @see alpha.codegen.GlobalVariable#getAlphaVariable()
	 * @see #getGlobalVariable()
	 * @generated
	 */
	EReference getGlobalVariable_AlphaVariable();

	/**
	 * Returns the meta object for the attribute '{@link alpha.codegen.GlobalVariable#getType <em>Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Type</em>'.
	 * @see alpha.codegen.GlobalVariable#getType()
	 * @see #getGlobalVariable()
	 * @generated
	 */
	EAttribute getGlobalVariable_Type();

	/**
	 * Returns the meta object for the attribute '{@link alpha.codegen.GlobalVariable#getNumDims <em>Num Dims</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Num Dims</em>'.
	 * @see alpha.codegen.GlobalVariable#getNumDims()
	 * @see #getGlobalVariable()
	 * @generated
	 */
	EAttribute getGlobalVariable_NumDims();

	/**
	 * Returns the meta object for the attribute '{@link alpha.codegen.GlobalVariable#isFlagVariable <em>Flag Variable</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Flag Variable</em>'.
	 * @see alpha.codegen.GlobalVariable#isFlagVariable()
	 * @see #getGlobalVariable()
	 * @generated
	 */
	EAttribute getGlobalVariable_FlagVariable();

	/**
	 * Returns the meta object for class '{@link alpha.codegen.ArrayVariable <em>Array Variable</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Array Variable</em>'.
	 * @see alpha.codegen.ArrayVariable
	 * @generated
	 */
	EClass getArrayVariable();

	/**
	 * Returns the meta object for class '{@link alpha.codegen.FunctionBody <em>Function Body</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Function Body</em>'.
	 * @see alpha.codegen.FunctionBody
	 * @generated
	 */
	EClass getFunctionBody();

	/**
	 * Returns the meta object for the container reference '{@link alpha.codegen.FunctionBody#getFunction <em>Function</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the container reference '<em>Function</em>'.
	 * @see alpha.codegen.FunctionBody#getFunction()
	 * @see #getFunctionBody()
	 * @generated
	 */
	EReference getFunctionBody_Function();

	/**
	 * Returns the meta object for the containment reference list '{@link alpha.codegen.FunctionBody#getStatementMacros <em>Statement Macros</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Statement Macros</em>'.
	 * @see alpha.codegen.FunctionBody#getStatementMacros()
	 * @see #getFunctionBody()
	 * @generated
	 */
	EReference getFunctionBody_StatementMacros();

	/**
	 * Returns the meta object for the attribute '{@link alpha.codegen.FunctionBody#getISLASTNode <em>ISLAST Node</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>ISLAST Node</em>'.
	 * @see alpha.codegen.FunctionBody#getISLASTNode()
	 * @see #getFunctionBody()
	 * @generated
	 */
	EAttribute getFunctionBody_ISLASTNode();

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
	 * Returns the meta object for the container reference '{@link alpha.codegen.Function#getProgram <em>Program</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the container reference '<em>Program</em>'.
	 * @see alpha.codegen.Function#getProgram()
	 * @see #getFunction()
	 * @generated
	 */
	EReference getFunction_Program();

	/**
	 * Returns the meta object for the attribute '{@link alpha.codegen.Function#getReturnType <em>Return Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Return Type</em>'.
	 * @see alpha.codegen.Function#getReturnType()
	 * @see #getFunction()
	 * @generated
	 */
	EAttribute getFunction_ReturnType();

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
	 * Returns the meta object for the reference list '{@link alpha.codegen.Function#getScalarArgs <em>Scalar Args</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Scalar Args</em>'.
	 * @see alpha.codegen.Function#getScalarArgs()
	 * @see #getFunction()
	 * @generated
	 */
	EReference getFunction_ScalarArgs();

	/**
	 * Returns the meta object for the reference list '{@link alpha.codegen.Function#getArrayArgs <em>Array Args</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Array Args</em>'.
	 * @see alpha.codegen.Function#getArrayArgs()
	 * @see #getFunction()
	 * @generated
	 */
	EReference getFunction_ArrayArgs();

	/**
	 * Returns the meta object for the containment reference list '{@link alpha.codegen.Function#getMemoryMacros <em>Memory Macros</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Memory Macros</em>'.
	 * @see alpha.codegen.Function#getMemoryMacros()
	 * @see #getFunction()
	 * @generated
	 */
	EReference getFunction_MemoryMacros();

	/**
	 * Returns the meta object for the containment reference '{@link alpha.codegen.Function#getBody <em>Body</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Body</em>'.
	 * @see alpha.codegen.Function#getBody()
	 * @see #getFunction()
	 * @generated
	 */
	EReference getFunction_Body();

	/**
	 * Returns the meta object for class '{@link alpha.codegen.EvalFunction <em>Eval Function</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Eval Function</em>'.
	 * @see alpha.codegen.EvalFunction
	 * @generated
	 */
	EClass getEvalFunction();

	/**
	 * Returns the meta object for the reference '{@link alpha.codegen.EvalFunction#getVariable <em>Variable</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Variable</em>'.
	 * @see alpha.codegen.EvalFunction#getVariable()
	 * @see #getEvalFunction()
	 * @generated
	 */
	EReference getEvalFunction_Variable();

	/**
	 * Returns the meta object for the reference '{@link alpha.codegen.EvalFunction#getFlagVariable <em>Flag Variable</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Flag Variable</em>'.
	 * @see alpha.codegen.EvalFunction#getFlagVariable()
	 * @see #getEvalFunction()
	 * @generated
	 */
	EReference getEvalFunction_FlagVariable();

	/**
	 * Returns the meta object for the reference '{@link alpha.codegen.EvalFunction#getEquation <em>Equation</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Equation</em>'.
	 * @see alpha.codegen.EvalFunction#getEquation()
	 * @see #getEvalFunction()
	 * @generated
	 */
	EReference getEvalFunction_Equation();

	/**
	 * Returns the meta object for class '{@link alpha.codegen.ReduceFunction <em>Reduce Function</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Reduce Function</em>'.
	 * @see alpha.codegen.ReduceFunction
	 * @generated
	 */
	EClass getReduceFunction();

	/**
	 * Returns the meta object for the reference '{@link alpha.codegen.ReduceFunction#getReduceExpr <em>Reduce Expr</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Reduce Expr</em>'.
	 * @see alpha.codegen.ReduceFunction#getReduceExpr()
	 * @see #getReduceFunction()
	 * @generated
	 */
	EReference getReduceFunction_ReduceExpr();

	/**
	 * Returns the meta object for the reference '{@link alpha.codegen.ReduceFunction#getReduceVar <em>Reduce Var</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Reduce Var</em>'.
	 * @see alpha.codegen.ReduceFunction#getReduceVar()
	 * @see #getReduceFunction()
	 * @generated
	 */
	EReference getReduceFunction_ReduceVar();

	/**
	 * Returns the meta object for the attribute '{@link alpha.codegen.ReduceFunction#getMacroName <em>Macro Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Macro Name</em>'.
	 * @see alpha.codegen.ReduceFunction#getMacroName()
	 * @see #getReduceFunction()
	 * @generated
	 */
	EAttribute getReduceFunction_MacroName();

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
	 * Returns the meta object for the reference '{@link alpha.codegen.Program#getSystem <em>System</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>System</em>'.
	 * @see alpha.codegen.Program#getSystem()
	 * @see #getProgram()
	 * @generated
	 */
	EReference getProgram_System();

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
	 * Returns the meta object for the containment reference list '{@link alpha.codegen.Program#getCommonMacros <em>Common Macros</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Common Macros</em>'.
	 * @see alpha.codegen.Program#getCommonMacros()
	 * @see #getProgram()
	 * @generated
	 */
	EReference getProgram_CommonMacros();

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
	 * Returns the meta object for the attribute '{@link alpha.codegen.Program#getReduceFunctions <em>Reduce Functions</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Reduce Functions</em>'.
	 * @see alpha.codegen.Program#getReduceFunctions()
	 * @see #getProgram()
	 * @generated
	 */
	EAttribute getProgram_ReduceFunctions();

	/**
	 * Returns the meta object for class '{@link alpha.codegen.AlphaOp <em>Alpha Op</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Alpha Op</em>'.
	 * @see alpha.codegen.AlphaOp
	 * @generated
	 */
	EClass getAlphaOp();

	/**
	 * Returns the meta object for class '{@link alpha.codegen.Polynomial <em>Polynomial</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Polynomial</em>'.
	 * @see alpha.codegen.Polynomial
	 * @generated
	 */
	EClass getPolynomial();

	/**
	 * Returns the meta object for the attribute '{@link alpha.codegen.Polynomial#getIslPolynomial <em>Isl Polynomial</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Isl Polynomial</em>'.
	 * @see alpha.codegen.Polynomial#getIslPolynomial()
	 * @see #getPolynomial()
	 * @generated
	 */
	EAttribute getPolynomial_IslPolynomial();

	/**
	 * Returns the meta object for enum '{@link alpha.codegen.VariableType <em>Variable Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Variable Type</em>'.
	 * @see alpha.codegen.VariableType
	 * @generated
	 */
	EEnum getVariableType();

	/**
	 * Returns the meta object for enum '{@link alpha.codegen.DataType <em>Data Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Data Type</em>'.
	 * @see alpha.codegen.DataType
	 * @generated
	 */
	EEnum getDataType();

	/**
	 * Returns the meta object for enum '{@link alpha.codegen.C_UNARY_OP <em>CUNARY OP</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>CUNARY OP</em>'.
	 * @see alpha.codegen.C_UNARY_OP
	 * @generated
	 */
	EEnum getC_UNARY_OP();

	/**
	 * Returns the meta object for enum '{@link alpha.codegen.C_BINARY_OP <em>CBINARY OP</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>CBINARY OP</em>'.
	 * @see alpha.codegen.C_BINARY_OP
	 * @generated
	 */
	EEnum getC_BINARY_OP();

	/**
	 * Returns the meta object for enum '{@link alpha.codegen.C_REDUCTION_OP <em>CREDUCTION OP</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>CREDUCTION OP</em>'.
	 * @see alpha.codegen.C_REDUCTION_OP
	 * @generated
	 */
	EEnum getC_REDUCTION_OP();

	/**
	 * Returns the meta object for data type '{@link fr.irisa.cairn.jnimap.isl.ISLSet <em>ISL Set</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for data type '<em>ISL Set</em>'.
	 * @see fr.irisa.cairn.jnimap.isl.ISLSet
	 * @model instanceClass="fr.irisa.cairn.jnimap.isl.ISLSet"
	 * @generated
	 */
	EDataType getISLSet();

	/**
	 * Returns the meta object for data type '{@link fr.irisa.cairn.jnimap.isl.ISLMap <em>ISL Map</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for data type '<em>ISL Map</em>'.
	 * @see fr.irisa.cairn.jnimap.isl.ISLMap
	 * @model instanceClass="fr.irisa.cairn.jnimap.isl.ISLMap"
	 * @generated
	 */
	EDataType getISLMap();

	/**
	 * Returns the meta object for data type '{@link fr.irisa.cairn.jnimap.isl.ISLASTNode <em>ISLAST Node</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for data type '<em>ISLAST Node</em>'.
	 * @see fr.irisa.cairn.jnimap.isl.ISLASTNode
	 * @model instanceClass="fr.irisa.cairn.jnimap.isl.ISLASTNode"
	 * @generated
	 */
	EDataType getISLASTNode();

	/**
	 * Returns the meta object for data type '{@link fr.irisa.cairn.jnimap.isl.ISLAff <em>ISL Aff</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for data type '<em>ISL Aff</em>'.
	 * @see fr.irisa.cairn.jnimap.isl.ISLAff
	 * @model instanceClass="fr.irisa.cairn.jnimap.isl.ISLAff"
	 * @generated
	 */
	EDataType getISLAff();

	/**
	 * Returns the meta object for data type '{@link fr.irisa.cairn.jnimap.isl.ISLAffList <em>ISL Aff List</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for data type '<em>ISL Aff List</em>'.
	 * @see fr.irisa.cairn.jnimap.isl.ISLAffList
	 * @model instanceClass="fr.irisa.cairn.jnimap.isl.ISLAffList"
	 * @generated
	 */
	EDataType getISLAffList();

	/**
	 * Returns the meta object for data type '{@link fr.irisa.cairn.jnimap.isl.ISLDimType <em>ISL Dim Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for data type '<em>ISL Dim Type</em>'.
	 * @see fr.irisa.cairn.jnimap.isl.ISLDimType
	 * @model instanceClass="fr.irisa.cairn.jnimap.isl.ISLDimType"
	 * @generated
	 */
	EDataType getISLDimType();

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
	 * Returns the meta object for data type '{@link fr.irisa.cairn.jnimap.isl.ISLPWQPolynomial <em>ISLPWQ Polynomial</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for data type '<em>ISLPWQ Polynomial</em>'.
	 * @see fr.irisa.cairn.jnimap.isl.ISLPWQPolynomial
	 * @model instanceClass="fr.irisa.cairn.jnimap.isl.ISLPWQPolynomial"
	 * @generated
	 */
	EDataType getISLPWQPolynomial();

	/**
	 * Returns the meta object for data type '{@link fr.irisa.cairn.jnimap.isl.ISLQPolynomialPiece <em>ISLQ Polynomial Piece</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for data type '<em>ISLQ Polynomial Piece</em>'.
	 * @see fr.irisa.cairn.jnimap.isl.ISLQPolynomialPiece
	 * @model instanceClass="fr.irisa.cairn.jnimap.isl.ISLQPolynomialPiece"
	 * @generated
	 */
	EDataType getISLQPolynomialPiece();

	/**
	 * Returns the meta object for data type '{@link fr.irisa.cairn.jnimap.isl.ISLQPolynomial <em>ISLQ Polynomial</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for data type '<em>ISLQ Polynomial</em>'.
	 * @see fr.irisa.cairn.jnimap.isl.ISLQPolynomial
	 * @model instanceClass="fr.irisa.cairn.jnimap.isl.ISLQPolynomial"
	 * @generated
	 */
	EDataType getISLQPolynomial();

	/**
	 * Returns the meta object for data type '{@link fr.irisa.cairn.jnimap.isl.ISLTerm <em>ISL Term</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for data type '<em>ISL Term</em>'.
	 * @see fr.irisa.cairn.jnimap.isl.ISLTerm
	 * @model instanceClass="fr.irisa.cairn.jnimap.isl.ISLTerm"
	 * @generated
	 */
	EDataType getISLTerm();

	/**
	 * Returns the meta object for data type '{@link java.util.HashMap <em>Hash Map</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for data type '<em>Hash Map</em>'.
	 * @see java.util.HashMap
	 * @model instanceClass="java.util.HashMap" typeParameters="K V"
	 * @generated
	 */
	EDataType getHashMap();

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
	 *   <li>each enum,</li>
	 *   <li>and each data type</li>
	 * </ul>
	 * <!-- end-user-doc -->
	 * @generated
	 */
	interface Literals {
		/**
		 * The meta object literal for the '{@link alpha.codegen.impl.NodeImpl <em>Node</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see alpha.codegen.impl.NodeImpl
		 * @see alpha.codegen.impl.CodegenPackageImpl#getNode()
		 * @generated
		 */
		EClass NODE = eINSTANCE.getNode();

		/**
		 * The meta object literal for the '{@link alpha.codegen.Visitable <em>Visitable</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see alpha.codegen.Visitable
		 * @see alpha.codegen.impl.CodegenPackageImpl#getVisitable()
		 * @generated
		 */
		EClass VISITABLE = eINSTANCE.getVisitable();

		/**
		 * The meta object literal for the '{@link alpha.codegen.Visitor <em>Visitor</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see alpha.codegen.Visitor
		 * @see alpha.codegen.impl.CodegenPackageImpl#getVisitor()
		 * @generated
		 */
		EClass VISITOR = eINSTANCE.getVisitor();

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
		 * The meta object literal for the '<em><b>Program</b></em>' container reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference INCLUDE__PROGRAM = eINSTANCE.getInclude_Program();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute INCLUDE__NAME = eINSTANCE.getInclude_Name();

		/**
		 * The meta object literal for the '{@link alpha.codegen.impl.MacroImpl <em>Macro</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see alpha.codegen.impl.MacroImpl
		 * @see alpha.codegen.impl.CodegenPackageImpl#getMacro()
		 * @generated
		 */
		EClass MACRO = eINSTANCE.getMacro();

		/**
		 * The meta object literal for the '<em><b>Left</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute MACRO__LEFT = eINSTANCE.getMacro_Left();

		/**
		 * The meta object literal for the '<em><b>Right</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute MACRO__RIGHT = eINSTANCE.getMacro_Right();

		/**
		 * The meta object literal for the '{@link alpha.codegen.impl.GlobalMacroImpl <em>Global Macro</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see alpha.codegen.impl.GlobalMacroImpl
		 * @see alpha.codegen.impl.CodegenPackageImpl#getGlobalMacro()
		 * @generated
		 */
		EClass GLOBAL_MACRO = eINSTANCE.getGlobalMacro();

		/**
		 * The meta object literal for the '<em><b>Program</b></em>' container reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference GLOBAL_MACRO__PROGRAM = eINSTANCE.getGlobalMacro_Program();

		/**
		 * The meta object literal for the '{@link alpha.codegen.impl.AbstractMemoryMacroImpl <em>Abstract Memory Macro</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see alpha.codegen.impl.AbstractMemoryMacroImpl
		 * @see alpha.codegen.impl.CodegenPackageImpl#getAbstractMemoryMacro()
		 * @generated
		 */
		EClass ABSTRACT_MEMORY_MACRO = eINSTANCE.getAbstractMemoryMacro();

		/**
		 * The meta object literal for the '<em><b>Map</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ABSTRACT_MEMORY_MACRO__MAP = eINSTANCE.getAbstractMemoryMacro_Map();

		/**
		 * The meta object literal for the '{@link alpha.codegen.impl.MemoryMacroImpl <em>Memory Macro</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see alpha.codegen.impl.MemoryMacroImpl
		 * @see alpha.codegen.impl.CodegenPackageImpl#getMemoryMacro()
		 * @generated
		 */
		EClass MEMORY_MACRO = eINSTANCE.getMemoryMacro();

		/**
		 * The meta object literal for the '<em><b>Function</b></em>' container reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference MEMORY_MACRO__FUNCTION = eINSTANCE.getMemoryMacro_Function();

		/**
		 * The meta object literal for the '<em><b>Allocation</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference MEMORY_MACRO__ALLOCATION = eINSTANCE.getMemoryMacro_Allocation();

		/**
		 * The meta object literal for the '<em><b>Variable</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference MEMORY_MACRO__VARIABLE = eINSTANCE.getMemoryMacro_Variable();

		/**
		 * The meta object literal for the '{@link alpha.codegen.impl.MemoryAllocationImpl <em>Memory Allocation</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see alpha.codegen.impl.MemoryAllocationImpl
		 * @see alpha.codegen.impl.CodegenPackageImpl#getMemoryAllocation()
		 * @generated
		 */
		EClass MEMORY_ALLOCATION = eINSTANCE.getMemoryAllocation();

		/**
		 * The meta object literal for the '<em><b>Macro</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference MEMORY_ALLOCATION__MACRO = eINSTANCE.getMemoryAllocation_Macro();

		/**
		 * The meta object literal for the '<em><b>Variable</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference MEMORY_ALLOCATION__VARIABLE = eINSTANCE.getMemoryAllocation_Variable();

		/**
		 * The meta object literal for the '<em><b>Map</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute MEMORY_ALLOCATION__MAP = eINSTANCE.getMemoryAllocation_Map();

		/**
		 * The meta object literal for the '<em><b>Domain</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute MEMORY_ALLOCATION__DOMAIN = eINSTANCE.getMemoryAllocation_Domain();

		/**
		 * The meta object literal for the '<em><b>ISLAST Node</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute MEMORY_ALLOCATION__ISLAST_NODE = eINSTANCE.getMemoryAllocation_ISLASTNode();

		/**
		 * The meta object literal for the '{@link alpha.codegen.impl.GlobalMemoryMacroImpl <em>Global Memory Macro</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see alpha.codegen.impl.GlobalMemoryMacroImpl
		 * @see alpha.codegen.impl.CodegenPackageImpl#getGlobalMemoryMacro()
		 * @generated
		 */
		EClass GLOBAL_MEMORY_MACRO = eINSTANCE.getGlobalMemoryMacro();

		/**
		 * The meta object literal for the '<em><b>Variable</b></em>' container reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference GLOBAL_MEMORY_MACRO__VARIABLE = eINSTANCE.getGlobalMemoryMacro_Variable();

		/**
		 * The meta object literal for the '{@link alpha.codegen.impl.StatementMacroImpl <em>Statement Macro</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see alpha.codegen.impl.StatementMacroImpl
		 * @see alpha.codegen.impl.CodegenPackageImpl#getStatementMacro()
		 * @generated
		 */
		EClass STATEMENT_MACRO = eINSTANCE.getStatementMacro();

		/**
		 * The meta object literal for the '<em><b>Function Body</b></em>' container reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference STATEMENT_MACRO__FUNCTION_BODY = eINSTANCE.getStatementMacro_FunctionBody();

		/**
		 * The meta object literal for the '{@link alpha.codegen.impl.BaseVariableImpl <em>Base Variable</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see alpha.codegen.impl.BaseVariableImpl
		 * @see alpha.codegen.impl.CodegenPackageImpl#getBaseVariable()
		 * @generated
		 */
		EClass BASE_VARIABLE = eINSTANCE.getBaseVariable();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute BASE_VARIABLE__NAME = eINSTANCE.getBaseVariable_Name();

		/**
		 * The meta object literal for the '<em><b>Elem Type</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute BASE_VARIABLE__ELEM_TYPE = eINSTANCE.getBaseVariable_ElemType();

		/**
		 * The meta object literal for the '{@link alpha.codegen.impl.GlobalVariableImpl <em>Global Variable</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see alpha.codegen.impl.GlobalVariableImpl
		 * @see alpha.codegen.impl.CodegenPackageImpl#getGlobalVariable()
		 * @generated
		 */
		EClass GLOBAL_VARIABLE = eINSTANCE.getGlobalVariable();

		/**
		 * The meta object literal for the '<em><b>Program</b></em>' container reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference GLOBAL_VARIABLE__PROGRAM = eINSTANCE.getGlobalVariable_Program();

		/**
		 * The meta object literal for the '<em><b>Memory Macro</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference GLOBAL_VARIABLE__MEMORY_MACRO = eINSTANCE.getGlobalVariable_MemoryMacro();

		/**
		 * The meta object literal for the '<em><b>Alpha Variable</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference GLOBAL_VARIABLE__ALPHA_VARIABLE = eINSTANCE.getGlobalVariable_AlphaVariable();

		/**
		 * The meta object literal for the '<em><b>Type</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute GLOBAL_VARIABLE__TYPE = eINSTANCE.getGlobalVariable_Type();

		/**
		 * The meta object literal for the '<em><b>Num Dims</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute GLOBAL_VARIABLE__NUM_DIMS = eINSTANCE.getGlobalVariable_NumDims();

		/**
		 * The meta object literal for the '<em><b>Flag Variable</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute GLOBAL_VARIABLE__FLAG_VARIABLE = eINSTANCE.getGlobalVariable_FlagVariable();

		/**
		 * The meta object literal for the '{@link alpha.codegen.impl.ArrayVariableImpl <em>Array Variable</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see alpha.codegen.impl.ArrayVariableImpl
		 * @see alpha.codegen.impl.CodegenPackageImpl#getArrayVariable()
		 * @generated
		 */
		EClass ARRAY_VARIABLE = eINSTANCE.getArrayVariable();

		/**
		 * The meta object literal for the '{@link alpha.codegen.impl.FunctionBodyImpl <em>Function Body</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see alpha.codegen.impl.FunctionBodyImpl
		 * @see alpha.codegen.impl.CodegenPackageImpl#getFunctionBody()
		 * @generated
		 */
		EClass FUNCTION_BODY = eINSTANCE.getFunctionBody();

		/**
		 * The meta object literal for the '<em><b>Function</b></em>' container reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference FUNCTION_BODY__FUNCTION = eINSTANCE.getFunctionBody_Function();

		/**
		 * The meta object literal for the '<em><b>Statement Macros</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference FUNCTION_BODY__STATEMENT_MACROS = eINSTANCE.getFunctionBody_StatementMacros();

		/**
		 * The meta object literal for the '<em><b>ISLAST Node</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute FUNCTION_BODY__ISLAST_NODE = eINSTANCE.getFunctionBody_ISLASTNode();

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
		 * The meta object literal for the '<em><b>Program</b></em>' container reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference FUNCTION__PROGRAM = eINSTANCE.getFunction_Program();

		/**
		 * The meta object literal for the '<em><b>Return Type</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute FUNCTION__RETURN_TYPE = eINSTANCE.getFunction_ReturnType();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute FUNCTION__NAME = eINSTANCE.getFunction_Name();

		/**
		 * The meta object literal for the '<em><b>Scalar Args</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference FUNCTION__SCALAR_ARGS = eINSTANCE.getFunction_ScalarArgs();

		/**
		 * The meta object literal for the '<em><b>Array Args</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference FUNCTION__ARRAY_ARGS = eINSTANCE.getFunction_ArrayArgs();

		/**
		 * The meta object literal for the '<em><b>Memory Macros</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference FUNCTION__MEMORY_MACROS = eINSTANCE.getFunction_MemoryMacros();

		/**
		 * The meta object literal for the '<em><b>Body</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference FUNCTION__BODY = eINSTANCE.getFunction_Body();

		/**
		 * The meta object literal for the '{@link alpha.codegen.impl.EvalFunctionImpl <em>Eval Function</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see alpha.codegen.impl.EvalFunctionImpl
		 * @see alpha.codegen.impl.CodegenPackageImpl#getEvalFunction()
		 * @generated
		 */
		EClass EVAL_FUNCTION = eINSTANCE.getEvalFunction();

		/**
		 * The meta object literal for the '<em><b>Variable</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference EVAL_FUNCTION__VARIABLE = eINSTANCE.getEvalFunction_Variable();

		/**
		 * The meta object literal for the '<em><b>Flag Variable</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference EVAL_FUNCTION__FLAG_VARIABLE = eINSTANCE.getEvalFunction_FlagVariable();

		/**
		 * The meta object literal for the '<em><b>Equation</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference EVAL_FUNCTION__EQUATION = eINSTANCE.getEvalFunction_Equation();

		/**
		 * The meta object literal for the '{@link alpha.codegen.impl.ReduceFunctionImpl <em>Reduce Function</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see alpha.codegen.impl.ReduceFunctionImpl
		 * @see alpha.codegen.impl.CodegenPackageImpl#getReduceFunction()
		 * @generated
		 */
		EClass REDUCE_FUNCTION = eINSTANCE.getReduceFunction();

		/**
		 * The meta object literal for the '<em><b>Reduce Expr</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference REDUCE_FUNCTION__REDUCE_EXPR = eINSTANCE.getReduceFunction_ReduceExpr();

		/**
		 * The meta object literal for the '<em><b>Reduce Var</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference REDUCE_FUNCTION__REDUCE_VAR = eINSTANCE.getReduceFunction_ReduceVar();

		/**
		 * The meta object literal for the '<em><b>Macro Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute REDUCE_FUNCTION__MACRO_NAME = eINSTANCE.getReduceFunction_MacroName();

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
		 * The meta object literal for the '<em><b>System</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PROGRAM__SYSTEM = eINSTANCE.getProgram_System();

		/**
		 * The meta object literal for the '<em><b>Includes</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PROGRAM__INCLUDES = eINSTANCE.getProgram_Includes();

		/**
		 * The meta object literal for the '<em><b>Common Macros</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PROGRAM__COMMON_MACROS = eINSTANCE.getProgram_CommonMacros();

		/**
		 * The meta object literal for the '<em><b>Global Variables</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PROGRAM__GLOBAL_VARIABLES = eINSTANCE.getProgram_GlobalVariables();

		/**
		 * The meta object literal for the '<em><b>Functions</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PROGRAM__FUNCTIONS = eINSTANCE.getProgram_Functions();

		/**
		 * The meta object literal for the '<em><b>Reduce Functions</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PROGRAM__REDUCE_FUNCTIONS = eINSTANCE.getProgram_ReduceFunctions();

		/**
		 * The meta object literal for the '{@link alpha.codegen.impl.AlphaOpImpl <em>Alpha Op</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see alpha.codegen.impl.AlphaOpImpl
		 * @see alpha.codegen.impl.CodegenPackageImpl#getAlphaOp()
		 * @generated
		 */
		EClass ALPHA_OP = eINSTANCE.getAlphaOp();

		/**
		 * The meta object literal for the '{@link alpha.codegen.impl.PolynomialImpl <em>Polynomial</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see alpha.codegen.impl.PolynomialImpl
		 * @see alpha.codegen.impl.CodegenPackageImpl#getPolynomial()
		 * @generated
		 */
		EClass POLYNOMIAL = eINSTANCE.getPolynomial();

		/**
		 * The meta object literal for the '<em><b>Isl Polynomial</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute POLYNOMIAL__ISL_POLYNOMIAL = eINSTANCE.getPolynomial_IslPolynomial();

		/**
		 * The meta object literal for the '{@link alpha.codegen.VariableType <em>Variable Type</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see alpha.codegen.VariableType
		 * @see alpha.codegen.impl.CodegenPackageImpl#getVariableType()
		 * @generated
		 */
		EEnum VARIABLE_TYPE = eINSTANCE.getVariableType();

		/**
		 * The meta object literal for the '{@link alpha.codegen.DataType <em>Data Type</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see alpha.codegen.DataType
		 * @see alpha.codegen.impl.CodegenPackageImpl#getDataType()
		 * @generated
		 */
		EEnum DATA_TYPE = eINSTANCE.getDataType();

		/**
		 * The meta object literal for the '{@link alpha.codegen.C_UNARY_OP <em>CUNARY OP</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see alpha.codegen.C_UNARY_OP
		 * @see alpha.codegen.impl.CodegenPackageImpl#getC_UNARY_OP()
		 * @generated
		 */
		EEnum CUNARY_OP = eINSTANCE.getC_UNARY_OP();

		/**
		 * The meta object literal for the '{@link alpha.codegen.C_BINARY_OP <em>CBINARY OP</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see alpha.codegen.C_BINARY_OP
		 * @see alpha.codegen.impl.CodegenPackageImpl#getC_BINARY_OP()
		 * @generated
		 */
		EEnum CBINARY_OP = eINSTANCE.getC_BINARY_OP();

		/**
		 * The meta object literal for the '{@link alpha.codegen.C_REDUCTION_OP <em>CREDUCTION OP</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see alpha.codegen.C_REDUCTION_OP
		 * @see alpha.codegen.impl.CodegenPackageImpl#getC_REDUCTION_OP()
		 * @generated
		 */
		EEnum CREDUCTION_OP = eINSTANCE.getC_REDUCTION_OP();

		/**
		 * The meta object literal for the '<em>ISL Set</em>' data type.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see fr.irisa.cairn.jnimap.isl.ISLSet
		 * @see alpha.codegen.impl.CodegenPackageImpl#getISLSet()
		 * @generated
		 */
		EDataType ISL_SET = eINSTANCE.getISLSet();

		/**
		 * The meta object literal for the '<em>ISL Map</em>' data type.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see fr.irisa.cairn.jnimap.isl.ISLMap
		 * @see alpha.codegen.impl.CodegenPackageImpl#getISLMap()
		 * @generated
		 */
		EDataType ISL_MAP = eINSTANCE.getISLMap();

		/**
		 * The meta object literal for the '<em>ISLAST Node</em>' data type.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see fr.irisa.cairn.jnimap.isl.ISLASTNode
		 * @see alpha.codegen.impl.CodegenPackageImpl#getISLASTNode()
		 * @generated
		 */
		EDataType ISLAST_NODE = eINSTANCE.getISLASTNode();

		/**
		 * The meta object literal for the '<em>ISL Aff</em>' data type.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see fr.irisa.cairn.jnimap.isl.ISLAff
		 * @see alpha.codegen.impl.CodegenPackageImpl#getISLAff()
		 * @generated
		 */
		EDataType ISL_AFF = eINSTANCE.getISLAff();

		/**
		 * The meta object literal for the '<em>ISL Aff List</em>' data type.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see fr.irisa.cairn.jnimap.isl.ISLAffList
		 * @see alpha.codegen.impl.CodegenPackageImpl#getISLAffList()
		 * @generated
		 */
		EDataType ISL_AFF_LIST = eINSTANCE.getISLAffList();

		/**
		 * The meta object literal for the '<em>ISL Dim Type</em>' data type.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see fr.irisa.cairn.jnimap.isl.ISLDimType
		 * @see alpha.codegen.impl.CodegenPackageImpl#getISLDimType()
		 * @generated
		 */
		EDataType ISL_DIM_TYPE = eINSTANCE.getISLDimType();

		/**
		 * The meta object literal for the '<em>ISL FORMAT</em>' data type.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see fr.irisa.cairn.jnimap.isl.ISL_FORMAT
		 * @see alpha.codegen.impl.CodegenPackageImpl#getISL_FORMAT()
		 * @generated
		 */
		EDataType ISL_FORMAT = eINSTANCE.getISL_FORMAT();

		/**
		 * The meta object literal for the '<em>ISLPWQ Polynomial</em>' data type.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see fr.irisa.cairn.jnimap.isl.ISLPWQPolynomial
		 * @see alpha.codegen.impl.CodegenPackageImpl#getISLPWQPolynomial()
		 * @generated
		 */
		EDataType ISLPWQ_POLYNOMIAL = eINSTANCE.getISLPWQPolynomial();

		/**
		 * The meta object literal for the '<em>ISLQ Polynomial Piece</em>' data type.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see fr.irisa.cairn.jnimap.isl.ISLQPolynomialPiece
		 * @see alpha.codegen.impl.CodegenPackageImpl#getISLQPolynomialPiece()
		 * @generated
		 */
		EDataType ISLQ_POLYNOMIAL_PIECE = eINSTANCE.getISLQPolynomialPiece();

		/**
		 * The meta object literal for the '<em>ISLQ Polynomial</em>' data type.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see fr.irisa.cairn.jnimap.isl.ISLQPolynomial
		 * @see alpha.codegen.impl.CodegenPackageImpl#getISLQPolynomial()
		 * @generated
		 */
		EDataType ISLQ_POLYNOMIAL = eINSTANCE.getISLQPolynomial();

		/**
		 * The meta object literal for the '<em>ISL Term</em>' data type.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see fr.irisa.cairn.jnimap.isl.ISLTerm
		 * @see alpha.codegen.impl.CodegenPackageImpl#getISLTerm()
		 * @generated
		 */
		EDataType ISL_TERM = eINSTANCE.getISLTerm();

		/**
		 * The meta object literal for the '<em>Hash Map</em>' data type.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see java.util.HashMap
		 * @see alpha.codegen.impl.CodegenPackageImpl#getHashMap()
		 * @generated
		 */
		EDataType HASH_MAP = eINSTANCE.getHashMap();

	}

} //CodegenPackage
