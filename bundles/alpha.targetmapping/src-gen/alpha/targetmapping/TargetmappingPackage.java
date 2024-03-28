/**
 */
package alpha.targetmapping;

import alpha.model.ModelPackage;

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
 * @see alpha.targetmapping.TargetmappingFactory
 * @model kind="package"
 * @generated
 */
public interface TargetmappingPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "targetmapping";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "alpha.targetmapping";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "targetmapping";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	TargetmappingPackage eINSTANCE = alpha.targetmapping.impl.TargetmappingPackageImpl.init();

	/**
	 * The meta object id for the '{@link alpha.targetmapping.TargetMappingNode <em>Target Mapping Node</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see alpha.targetmapping.TargetMappingNode
	 * @see alpha.targetmapping.impl.TargetmappingPackageImpl#getTargetMappingNode()
	 * @generated
	 */
	int TARGET_MAPPING_NODE = 0;

	/**
	 * The number of structural features of the '<em>Target Mapping Node</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TARGET_MAPPING_NODE_FEATURE_COUNT = 0;

	/**
	 * The meta object id for the '{@link alpha.targetmapping.TargetMappingVisitable <em>Target Mapping Visitable</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see alpha.targetmapping.TargetMappingVisitable
	 * @see alpha.targetmapping.impl.TargetmappingPackageImpl#getTargetMappingVisitable()
	 * @generated
	 */
	int TARGET_MAPPING_VISITABLE = 1;

	/**
	 * The number of structural features of the '<em>Target Mapping Visitable</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TARGET_MAPPING_VISITABLE_FEATURE_COUNT = 0;

	/**
	 * The meta object id for the '{@link alpha.targetmapping.impl.TargetMappingVisitorImpl <em>Target Mapping Visitor</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see alpha.targetmapping.impl.TargetMappingVisitorImpl
	 * @see alpha.targetmapping.impl.TargetmappingPackageImpl#getTargetMappingVisitor()
	 * @generated
	 */
	int TARGET_MAPPING_VISITOR = 2;

	/**
	 * The number of structural features of the '<em>Target Mapping Visitor</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TARGET_MAPPING_VISITOR_FEATURE_COUNT = 0;

	/**
	 * The meta object id for the '{@link alpha.targetmapping.ScopingEntity <em>Scoping Entity</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see alpha.targetmapping.ScopingEntity
	 * @see alpha.targetmapping.impl.TargetmappingPackageImpl#getScopingEntity()
	 * @generated
	 */
	int SCOPING_ENTITY = 3;

	/**
	 * The number of structural features of the '<em>Scoping Entity</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SCOPING_ENTITY_FEATURE_COUNT = 0;

	/**
	 * The meta object id for the '{@link alpha.targetmapping.impl.TargetMappingImpl <em>Target Mapping</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see alpha.targetmapping.impl.TargetMappingImpl
	 * @see alpha.targetmapping.impl.TargetmappingPackageImpl#getTargetMapping()
	 * @generated
	 */
	int TARGET_MAPPING = 4;

	/**
	 * The feature id for the '<em><b>Target System</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TARGET_MAPPING__TARGET_SYSTEM = TARGET_MAPPING_NODE_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>System Body TMs</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TARGET_MAPPING__SYSTEM_BODY_TMS = TARGET_MAPPING_NODE_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Target Mapping</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TARGET_MAPPING_FEATURE_COUNT = TARGET_MAPPING_NODE_FEATURE_COUNT + 2;

	/**
	 * The meta object id for the '{@link alpha.targetmapping.impl.TargetMappingForSystemBodyImpl <em>Target Mapping For System Body</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see alpha.targetmapping.impl.TargetMappingForSystemBodyImpl
	 * @see alpha.targetmapping.impl.TargetmappingPackageImpl#getTargetMappingForSystemBody()
	 * @generated
	 */
	int TARGET_MAPPING_FOR_SYSTEM_BODY = 5;

	/**
	 * The feature id for the '<em><b>Target Body</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TARGET_MAPPING_FOR_SYSTEM_BODY__TARGET_BODY = TARGET_MAPPING_NODE_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Target Mapping</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TARGET_MAPPING_FOR_SYSTEM_BODY__TARGET_MAPPING = TARGET_MAPPING_NODE_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Schedule Tree Root</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TARGET_MAPPING_FOR_SYSTEM_BODY__SCHEDULE_TREE_ROOT = TARGET_MAPPING_NODE_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>Target Mapping For System Body</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TARGET_MAPPING_FOR_SYSTEM_BODY_FEATURE_COUNT = TARGET_MAPPING_NODE_FEATURE_COUNT + 3;

	/**
	 * The meta object id for the '{@link alpha.targetmapping.impl.ScheduleTreeExpressionImpl <em>Schedule Tree Expression</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see alpha.targetmapping.impl.ScheduleTreeExpressionImpl
	 * @see alpha.targetmapping.impl.TargetmappingPackageImpl#getScheduleTreeExpression()
	 * @generated
	 */
	int SCHEDULE_TREE_EXPRESSION = 6;

	/**
	 * The number of structural features of the '<em>Schedule Tree Expression</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SCHEDULE_TREE_EXPRESSION_FEATURE_COUNT = TARGET_MAPPING_NODE_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link alpha.targetmapping.impl.ContextExpressionImpl <em>Context Expression</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see alpha.targetmapping.impl.ContextExpressionImpl
	 * @see alpha.targetmapping.impl.TargetmappingPackageImpl#getContextExpression()
	 * @generated
	 */
	int CONTEXT_EXPRESSION = 7;

	/**
	 * The feature id for the '<em><b>Context Domain Expr</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONTEXT_EXPRESSION__CONTEXT_DOMAIN_EXPR = SCHEDULE_TREE_EXPRESSION_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Child</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONTEXT_EXPRESSION__CHILD = SCHEDULE_TREE_EXPRESSION_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Context Expression</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONTEXT_EXPRESSION_FEATURE_COUNT = SCHEDULE_TREE_EXPRESSION_FEATURE_COUNT + 2;

	/**
	 * The meta object id for the '{@link alpha.targetmapping.impl.SetExpressionImpl <em>Set Expression</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see alpha.targetmapping.impl.SetExpressionImpl
	 * @see alpha.targetmapping.impl.TargetmappingPackageImpl#getSetExpression()
	 * @generated
	 */
	int SET_EXPRESSION = 8;

	/**
	 * The feature id for the '<em><b>Children</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SET_EXPRESSION__CHILDREN = SCHEDULE_TREE_EXPRESSION_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Set Expression</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SET_EXPRESSION_FEATURE_COUNT = SCHEDULE_TREE_EXPRESSION_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link alpha.targetmapping.impl.SequenceExpressionImpl <em>Sequence Expression</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see alpha.targetmapping.impl.SequenceExpressionImpl
	 * @see alpha.targetmapping.impl.TargetmappingPackageImpl#getSequenceExpression()
	 * @generated
	 */
	int SEQUENCE_EXPRESSION = 9;

	/**
	 * The feature id for the '<em><b>Children</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SEQUENCE_EXPRESSION__CHILDREN = SCHEDULE_TREE_EXPRESSION_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Sequence Expression</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SEQUENCE_EXPRESSION_FEATURE_COUNT = SCHEDULE_TREE_EXPRESSION_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link alpha.targetmapping.impl.FilterExpressionImpl <em>Filter Expression</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see alpha.targetmapping.impl.FilterExpressionImpl
	 * @see alpha.targetmapping.impl.TargetmappingPackageImpl#getFilterExpression()
	 * @generated
	 */
	int FILTER_EXPRESSION = 10;

	/**
	 * The feature id for the '<em><b>Filter Domains</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FILTER_EXPRESSION__FILTER_DOMAINS = SCHEDULE_TREE_EXPRESSION_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Child</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FILTER_EXPRESSION__CHILD = SCHEDULE_TREE_EXPRESSION_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Filter Expression</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FILTER_EXPRESSION_FEATURE_COUNT = SCHEDULE_TREE_EXPRESSION_FEATURE_COUNT + 2;

	/**
	 * The meta object id for the '{@link alpha.targetmapping.impl.ScheduleTargetRestrictDomainImpl <em>Schedule Target Restrict Domain</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see alpha.targetmapping.impl.ScheduleTargetRestrictDomainImpl
	 * @see alpha.targetmapping.impl.TargetmappingPackageImpl#getScheduleTargetRestrictDomain()
	 * @generated
	 */
	int SCHEDULE_TARGET_RESTRICT_DOMAIN = 11;

	/**
	 * The feature id for the '<em><b>Schedule Target</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SCHEDULE_TARGET_RESTRICT_DOMAIN__SCHEDULE_TARGET = TARGET_MAPPING_NODE_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Index Names</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SCHEDULE_TARGET_RESTRICT_DOMAIN__INDEX_NAMES = TARGET_MAPPING_NODE_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Restrict Domain Expr</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SCHEDULE_TARGET_RESTRICT_DOMAIN__RESTRICT_DOMAIN_EXPR = TARGET_MAPPING_NODE_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>Schedule Target Restrict Domain</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SCHEDULE_TARGET_RESTRICT_DOMAIN_FEATURE_COUNT = TARGET_MAPPING_NODE_FEATURE_COUNT + 3;

	/**
	 * The meta object id for the '{@link alpha.targetmapping.impl.GuardExpressionImpl <em>Guard Expression</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see alpha.targetmapping.impl.GuardExpressionImpl
	 * @see alpha.targetmapping.impl.TargetmappingPackageImpl#getGuardExpression()
	 * @generated
	 */
	int GUARD_EXPRESSION = 12;

	/**
	 * The feature id for the '<em><b>Guard Domain Expr</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GUARD_EXPRESSION__GUARD_DOMAIN_EXPR = SCHEDULE_TREE_EXPRESSION_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Child</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GUARD_EXPRESSION__CHILD = SCHEDULE_TREE_EXPRESSION_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Guard Expression</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GUARD_EXPRESSION_FEATURE_COUNT = SCHEDULE_TREE_EXPRESSION_FEATURE_COUNT + 2;

	/**
	 * The meta object id for the '{@link alpha.targetmapping.impl.MarkExpressionImpl <em>Mark Expression</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see alpha.targetmapping.impl.MarkExpressionImpl
	 * @see alpha.targetmapping.impl.TargetmappingPackageImpl#getMarkExpression()
	 * @generated
	 */
	int MARK_EXPRESSION = 13;

	/**
	 * The feature id for the '<em><b>Identifier</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MARK_EXPRESSION__IDENTIFIER = SCHEDULE_TREE_EXPRESSION_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Child</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MARK_EXPRESSION__CHILD = SCHEDULE_TREE_EXPRESSION_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Mark Expression</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MARK_EXPRESSION_FEATURE_COUNT = SCHEDULE_TREE_EXPRESSION_FEATURE_COUNT + 2;

	/**
	 * The meta object id for the '{@link alpha.targetmapping.impl.AbstractBandExpressionImpl <em>Abstract Band Expression</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see alpha.targetmapping.impl.AbstractBandExpressionImpl
	 * @see alpha.targetmapping.impl.TargetmappingPackageImpl#getAbstractBandExpression()
	 * @generated
	 */
	int ABSTRACT_BAND_EXPRESSION = 14;

	/**
	 * The feature id for the '<em><b>Band Pieces</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABSTRACT_BAND_EXPRESSION__BAND_PIECES = SCHEDULE_TREE_EXPRESSION_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Schedule Dimension Names</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABSTRACT_BAND_EXPRESSION__SCHEDULE_DIMENSION_NAMES = SCHEDULE_TREE_EXPRESSION_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Abstract Band Expression</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABSTRACT_BAND_EXPRESSION_FEATURE_COUNT = SCHEDULE_TREE_EXPRESSION_FEATURE_COUNT + 2;

	/**
	 * The meta object id for the '{@link alpha.targetmapping.impl.BandExpressionImpl <em>Band Expression</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see alpha.targetmapping.impl.BandExpressionImpl
	 * @see alpha.targetmapping.impl.TargetmappingPackageImpl#getBandExpression()
	 * @generated
	 */
	int BAND_EXPRESSION = 15;

	/**
	 * The feature id for the '<em><b>Band Pieces</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BAND_EXPRESSION__BAND_PIECES = ABSTRACT_BAND_EXPRESSION__BAND_PIECES;

	/**
	 * The feature id for the '<em><b>Schedule Dimension Names</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BAND_EXPRESSION__SCHEDULE_DIMENSION_NAMES = ABSTRACT_BAND_EXPRESSION__SCHEDULE_DIMENSION_NAMES;

	/**
	 * The feature id for the '<em><b>Loop Type Specifications</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BAND_EXPRESSION__LOOP_TYPE_SPECIFICATIONS = ABSTRACT_BAND_EXPRESSION_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Isolate Specification</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BAND_EXPRESSION__ISOLATE_SPECIFICATION = ABSTRACT_BAND_EXPRESSION_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Child</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BAND_EXPRESSION__CHILD = ABSTRACT_BAND_EXPRESSION_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>Band Expression</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BAND_EXPRESSION_FEATURE_COUNT = ABSTRACT_BAND_EXPRESSION_FEATURE_COUNT + 3;

	/**
	 * The meta object id for the '{@link alpha.targetmapping.impl.BandPieceImpl <em>Band Piece</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see alpha.targetmapping.impl.BandPieceImpl
	 * @see alpha.targetmapping.impl.TargetmappingPackageImpl#getBandPiece()
	 * @generated
	 */
	int BAND_PIECE = 16;

	/**
	 * The feature id for the '<em><b>Piece Domain</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BAND_PIECE__PIECE_DOMAIN = TARGET_MAPPING_NODE_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Partial Schedule Expr</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BAND_PIECE__PARTIAL_SCHEDULE_EXPR = TARGET_MAPPING_NODE_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Band Piece</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BAND_PIECE_FEATURE_COUNT = TARGET_MAPPING_NODE_FEATURE_COUNT + 2;

	/**
	 * The meta object id for the '{@link alpha.targetmapping.impl.LoopTypeSpecificationImpl <em>Loop Type Specification</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see alpha.targetmapping.impl.LoopTypeSpecificationImpl
	 * @see alpha.targetmapping.impl.TargetmappingPackageImpl#getLoopTypeSpecification()
	 * @generated
	 */
	int LOOP_TYPE_SPECIFICATION = 17;

	/**
	 * The feature id for the '<em><b>Dimension</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LOOP_TYPE_SPECIFICATION__DIMENSION = TARGET_MAPPING_NODE_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Loop Type Specification</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LOOP_TYPE_SPECIFICATION_FEATURE_COUNT = TARGET_MAPPING_NODE_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link alpha.targetmapping.impl.ISLLoopTypeSpecificationImpl <em>ISL Loop Type Specification</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see alpha.targetmapping.impl.ISLLoopTypeSpecificationImpl
	 * @see alpha.targetmapping.impl.TargetmappingPackageImpl#getISLLoopTypeSpecification()
	 * @generated
	 */
	int ISL_LOOP_TYPE_SPECIFICATION = 18;

	/**
	 * The feature id for the '<em><b>Dimension</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ISL_LOOP_TYPE_SPECIFICATION__DIMENSION = LOOP_TYPE_SPECIFICATION__DIMENSION;

	/**
	 * The feature id for the '<em><b>Loop Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ISL_LOOP_TYPE_SPECIFICATION__LOOP_TYPE = LOOP_TYPE_SPECIFICATION_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>ISL Loop Type Specification</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ISL_LOOP_TYPE_SPECIFICATION_FEATURE_COUNT = LOOP_TYPE_SPECIFICATION_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link alpha.targetmapping.impl.AlphaLoopTypeSpecificationImpl <em>Alpha Loop Type Specification</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see alpha.targetmapping.impl.AlphaLoopTypeSpecificationImpl
	 * @see alpha.targetmapping.impl.TargetmappingPackageImpl#getAlphaLoopTypeSpecification()
	 * @generated
	 */
	int ALPHA_LOOP_TYPE_SPECIFICATION = 19;

	/**
	 * The feature id for the '<em><b>Dimension</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ALPHA_LOOP_TYPE_SPECIFICATION__DIMENSION = LOOP_TYPE_SPECIFICATION__DIMENSION;

	/**
	 * The feature id for the '<em><b>Loop Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ALPHA_LOOP_TYPE_SPECIFICATION__LOOP_TYPE = LOOP_TYPE_SPECIFICATION_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Alpha Loop Type Specification</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ALPHA_LOOP_TYPE_SPECIFICATION_FEATURE_COUNT = LOOP_TYPE_SPECIFICATION_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link alpha.targetmapping.impl.IsolateSpecificationImpl <em>Isolate Specification</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see alpha.targetmapping.impl.IsolateSpecificationImpl
	 * @see alpha.targetmapping.impl.TargetmappingPackageImpl#getIsolateSpecification()
	 * @generated
	 */
	int ISOLATE_SPECIFICATION = 20;

	/**
	 * The feature id for the '<em><b>Isolate Domain Expr</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ISOLATE_SPECIFICATION__ISOLATE_DOMAIN_EXPR = TARGET_MAPPING_NODE_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Loop Type Specifications</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ISOLATE_SPECIFICATION__LOOP_TYPE_SPECIFICATIONS = TARGET_MAPPING_NODE_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Isolate Specification</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ISOLATE_SPECIFICATION_FEATURE_COUNT = TARGET_MAPPING_NODE_FEATURE_COUNT + 2;

	/**
	 * The meta object id for the '{@link alpha.targetmapping.impl.TileBandExpressionImpl <em>Tile Band Expression</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see alpha.targetmapping.impl.TileBandExpressionImpl
	 * @see alpha.targetmapping.impl.TargetmappingPackageImpl#getTileBandExpression()
	 * @generated
	 */
	int TILE_BAND_EXPRESSION = 21;

	/**
	 * The feature id for the '<em><b>Band Pieces</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TILE_BAND_EXPRESSION__BAND_PIECES = ABSTRACT_BAND_EXPRESSION__BAND_PIECES;

	/**
	 * The feature id for the '<em><b>Schedule Dimension Names</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TILE_BAND_EXPRESSION__SCHEDULE_DIMENSION_NAMES = ABSTRACT_BAND_EXPRESSION__SCHEDULE_DIMENSION_NAMES;

	/**
	 * The feature id for the '<em><b>Tiling Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TILE_BAND_EXPRESSION__TILING_TYPE = ABSTRACT_BAND_EXPRESSION_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Tiling Specification</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TILE_BAND_EXPRESSION__TILING_SPECIFICATION = ABSTRACT_BAND_EXPRESSION_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Tile Band Expression</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TILE_BAND_EXPRESSION_FEATURE_COUNT = ABSTRACT_BAND_EXPRESSION_FEATURE_COUNT + 2;

	/**
	 * The meta object id for the '{@link alpha.targetmapping.impl.TilingSpecificationImpl <em>Tiling Specification</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see alpha.targetmapping.impl.TilingSpecificationImpl
	 * @see alpha.targetmapping.impl.TargetmappingPackageImpl#getTilingSpecification()
	 * @generated
	 */
	int TILING_SPECIFICATION = 22;

	/**
	 * The feature id for the '<em><b>Loop Schedule Expr</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TILING_SPECIFICATION__LOOP_SCHEDULE_EXPR = TARGET_MAPPING_NODE_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Tiling Specification</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TILING_SPECIFICATION_FEATURE_COUNT = TARGET_MAPPING_NODE_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link alpha.targetmapping.impl.TileLoopSpecificationImpl <em>Tile Loop Specification</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see alpha.targetmapping.impl.TileLoopSpecificationImpl
	 * @see alpha.targetmapping.impl.TargetmappingPackageImpl#getTileLoopSpecification()
	 * @generated
	 */
	int TILE_LOOP_SPECIFICATION = 23;

	/**
	 * The feature id for the '<em><b>Loop Schedule Expr</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TILE_LOOP_SPECIFICATION__LOOP_SCHEDULE_EXPR = TILING_SPECIFICATION__LOOP_SCHEDULE_EXPR;

	/**
	 * The feature id for the '<em><b>Parallel</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TILE_LOOP_SPECIFICATION__PARALLEL = TILING_SPECIFICATION_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Tile Size Specifications</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TILE_LOOP_SPECIFICATION__TILE_SIZE_SPECIFICATIONS = TILING_SPECIFICATION_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Tiling Specification</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TILE_LOOP_SPECIFICATION__TILING_SPECIFICATION = TILING_SPECIFICATION_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>Tile Loop Specification</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TILE_LOOP_SPECIFICATION_FEATURE_COUNT = TILING_SPECIFICATION_FEATURE_COUNT + 3;

	/**
	 * The meta object id for the '{@link alpha.targetmapping.impl.PointLoopSpecificationImpl <em>Point Loop Specification</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see alpha.targetmapping.impl.PointLoopSpecificationImpl
	 * @see alpha.targetmapping.impl.TargetmappingPackageImpl#getPointLoopSpecification()
	 * @generated
	 */
	int POINT_LOOP_SPECIFICATION = 24;

	/**
	 * The feature id for the '<em><b>Loop Schedule Expr</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int POINT_LOOP_SPECIFICATION__LOOP_SCHEDULE_EXPR = TILING_SPECIFICATION__LOOP_SCHEDULE_EXPR;

	/**
	 * The feature id for the '<em><b>Loop Type Specifications</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int POINT_LOOP_SPECIFICATION__LOOP_TYPE_SPECIFICATIONS = TILING_SPECIFICATION_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Full Tile Specification</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int POINT_LOOP_SPECIFICATION__FULL_TILE_SPECIFICATION = TILING_SPECIFICATION_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Point Loop Specification</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int POINT_LOOP_SPECIFICATION_FEATURE_COUNT = TILING_SPECIFICATION_FEATURE_COUNT + 2;

	/**
	 * The meta object id for the '{@link alpha.targetmapping.impl.FullTileSpecificationImpl <em>Full Tile Specification</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see alpha.targetmapping.impl.FullTileSpecificationImpl
	 * @see alpha.targetmapping.impl.TargetmappingPackageImpl#getFullTileSpecification()
	 * @generated
	 */
	int FULL_TILE_SPECIFICATION = 25;

	/**
	 * The feature id for the '<em><b>Loop Type Specifications</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FULL_TILE_SPECIFICATION__LOOP_TYPE_SPECIFICATIONS = TARGET_MAPPING_NODE_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Full Tile Specification</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FULL_TILE_SPECIFICATION_FEATURE_COUNT = TARGET_MAPPING_NODE_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link alpha.targetmapping.impl.TileSizeSpecificationImpl <em>Tile Size Specification</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see alpha.targetmapping.impl.TileSizeSpecificationImpl
	 * @see alpha.targetmapping.impl.TargetmappingPackageImpl#getTileSizeSpecification()
	 * @generated
	 */
	int TILE_SIZE_SPECIFICATION = 26;

	/**
	 * The number of structural features of the '<em>Tile Size Specification</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TILE_SIZE_SPECIFICATION_FEATURE_COUNT = TARGET_MAPPING_NODE_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link alpha.targetmapping.impl.FixedTileSizeImpl <em>Fixed Tile Size</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see alpha.targetmapping.impl.FixedTileSizeImpl
	 * @see alpha.targetmapping.impl.TargetmappingPackageImpl#getFixedTileSize()
	 * @generated
	 */
	int FIXED_TILE_SIZE = 27;

	/**
	 * The feature id for the '<em><b>Tile Size</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FIXED_TILE_SIZE__TILE_SIZE = TILE_SIZE_SPECIFICATION_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Fixed Tile Size</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FIXED_TILE_SIZE_FEATURE_COUNT = TILE_SIZE_SPECIFICATION_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link alpha.targetmapping.impl.ParametricTileSizeImpl <em>Parametric Tile Size</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see alpha.targetmapping.impl.ParametricTileSizeImpl
	 * @see alpha.targetmapping.impl.TargetmappingPackageImpl#getParametricTileSize()
	 * @generated
	 */
	int PARAMETRIC_TILE_SIZE = 28;

	/**
	 * The feature id for the '<em><b>Tile Size Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARAMETRIC_TILE_SIZE__TILE_SIZE_NAME = TILE_SIZE_SPECIFICATION_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Parametric Tile Size</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARAMETRIC_TILE_SIZE_FEATURE_COUNT = TILE_SIZE_SPECIFICATION_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link alpha.targetmapping.impl.CompileTimeConstantTileSizeImpl <em>Compile Time Constant Tile Size</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see alpha.targetmapping.impl.CompileTimeConstantTileSizeImpl
	 * @see alpha.targetmapping.impl.TargetmappingPackageImpl#getCompileTimeConstantTileSize()
	 * @generated
	 */
	int COMPILE_TIME_CONSTANT_TILE_SIZE = 29;

	/**
	 * The feature id for the '<em><b>Tile Size Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPILE_TIME_CONSTANT_TILE_SIZE__TILE_SIZE_NAME = TILE_SIZE_SPECIFICATION_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Default Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPILE_TIME_CONSTANT_TILE_SIZE__DEFAULT_VALUE = TILE_SIZE_SPECIFICATION_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Compile Time Constant Tile Size</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPILE_TIME_CONSTANT_TILE_SIZE_FEATURE_COUNT = TILE_SIZE_SPECIFICATION_FEATURE_COUNT + 2;

	/**
	 * The meta object id for the '{@link alpha.targetmapping.impl.ExtensionExpressionImpl <em>Extension Expression</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see alpha.targetmapping.impl.ExtensionExpressionImpl
	 * @see alpha.targetmapping.impl.TargetmappingPackageImpl#getExtensionExpression()
	 * @generated
	 */
	int EXTENSION_EXPRESSION = 30;

	/**
	 * The feature id for the '<em><b>Extension Targets</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXTENSION_EXPRESSION__EXTENSION_TARGETS = SCHEDULE_TREE_EXPRESSION_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Child</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXTENSION_EXPRESSION__CHILD = SCHEDULE_TREE_EXPRESSION_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Extension Expression</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXTENSION_EXPRESSION_FEATURE_COUNT = SCHEDULE_TREE_EXPRESSION_FEATURE_COUNT + 2;

	/**
	 * The meta object id for the '{@link alpha.targetmapping.impl.ExtensionTargetImpl <em>Extension Target</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see alpha.targetmapping.impl.ExtensionTargetImpl
	 * @see alpha.targetmapping.impl.TargetmappingPackageImpl#getExtensionTarget()
	 * @generated
	 */
	int EXTENSION_TARGET = 31;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXTENSION_TARGET__NAME = ModelPackage.ALPHA_SCHEDULE_TARGET_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Index Names</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXTENSION_TARGET__INDEX_NAMES = ModelPackage.ALPHA_SCHEDULE_TARGET_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Extension Map Expr</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXTENSION_TARGET__EXTENSION_MAP_EXPR = ModelPackage.ALPHA_SCHEDULE_TARGET_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>Extension Target</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXTENSION_TARGET_FEATURE_COUNT = ModelPackage.ALPHA_SCHEDULE_TARGET_FEATURE_COUNT + 3;

	/**
	 * The meta object id for the '{@link alpha.targetmapping.impl.JNIIdentityFunctionImpl <em>JNI Identity Function</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see alpha.targetmapping.impl.JNIIdentityFunctionImpl
	 * @see alpha.targetmapping.impl.TargetmappingPackageImpl#getJNIIdentityFunction()
	 * @generated
	 */
	int JNI_IDENTITY_FUNCTION = 32;

	/**
	 * The feature id for the '<em><b>Node ID</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int JNI_IDENTITY_FUNCTION__NODE_ID = ModelPackage.JNI_FUNCTION__NODE_ID;

	/**
	 * The feature id for the '<em><b>Alpha Function</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int JNI_IDENTITY_FUNCTION__ALPHA_FUNCTION = ModelPackage.JNI_FUNCTION__ALPHA_FUNCTION;

	/**
	 * The feature id for the '<em><b>Zinternal cache isl MAff</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int JNI_IDENTITY_FUNCTION__ZINTERNAL_CACHE_ISL_MAFF = ModelPackage.JNI_FUNCTION__ZINTERNAL_CACHE_ISL_MAFF;

	/**
	 * The number of structural features of the '<em>JNI Identity Function</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int JNI_IDENTITY_FUNCTION_FEATURE_COUNT = ModelPackage.JNI_FUNCTION_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link alpha.targetmapping.ALPHA_LOOP_TYPE <em>ALPHA LOOP TYPE</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see alpha.targetmapping.ALPHA_LOOP_TYPE
	 * @see alpha.targetmapping.impl.TargetmappingPackageImpl#getALPHA_LOOP_TYPE()
	 * @generated
	 */
	int ALPHA_LOOP_TYPE = 33;

	/**
	 * The meta object id for the '{@link alpha.targetmapping.TILING_TYPE <em>TILING TYPE</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see alpha.targetmapping.TILING_TYPE
	 * @see alpha.targetmapping.impl.TargetmappingPackageImpl#getTILING_TYPE()
	 * @generated
	 */
	int TILING_TYPE = 34;

	/**
	 * The meta object id for the '{@link alpha.targetmapping.DataType <em>Data Type</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see alpha.targetmapping.DataType
	 * @see alpha.targetmapping.impl.TargetmappingPackageImpl#getDataType()
	 * @generated
	 */
	int DATA_TYPE = 35;

	/**
	 * The meta object id for the '<em>JNI Object</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see fr.irisa.cairn.jnimap.runtime.JNIObject
	 * @see alpha.targetmapping.impl.TargetmappingPackageImpl#getJNIObject()
	 * @generated
	 */
	int JNI_OBJECT = 36;

	/**
	 * The meta object id for the '<em>JNIISL Set</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see fr.irisa.cairn.jnimap.isl.ISLSet
	 * @see alpha.targetmapping.impl.TargetmappingPackageImpl#getJNIISLSet()
	 * @generated
	 */
	int JNIISL_SET = 37;

	/**
	 * The meta object id for the '<em>JNIISL Map</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see fr.irisa.cairn.jnimap.isl.ISLMap
	 * @see alpha.targetmapping.impl.TargetmappingPackageImpl#getJNIISLMap()
	 * @generated
	 */
	int JNIISL_MAP = 38;

	/**
	 * The meta object id for the '<em>JNIISL Multi Aff</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see fr.irisa.cairn.jnimap.isl.ISLMultiAff
	 * @see alpha.targetmapping.impl.TargetmappingPackageImpl#getJNIISLMultiAff()
	 * @generated
	 */
	int JNIISL_MULTI_AFF = 39;

	/**
	 * The meta object id for the '<em>JNIISLPWQ Polynomial</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see fr.irisa.cairn.jnimap.isl.ISLPWQPolynomial
	 * @see alpha.targetmapping.impl.TargetmappingPackageImpl#getJNIISLPWQPolynomial()
	 * @generated
	 */
	int JNIISLPWQ_POLYNOMIAL = 40;

	/**
	 * The meta object id for the '<em>ISL FORMAT</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see fr.irisa.cairn.jnimap.isl.ISL_FORMAT
	 * @see alpha.targetmapping.impl.TargetmappingPackageImpl#getISL_FORMAT()
	 * @generated
	 */
	int ISL_FORMAT = 41;

	/**
	 * The meta object id for the '<em>ISLAST Loop Type</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see fr.irisa.cairn.jnimap.isl.ISLASTLoopType
	 * @see alpha.targetmapping.impl.TargetmappingPackageImpl#getISLASTLoopType()
	 * @generated
	 */
	int ISLAST_LOOP_TYPE = 42;


	/**
	 * Returns the meta object for class '{@link alpha.targetmapping.TargetMappingNode <em>Target Mapping Node</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Target Mapping Node</em>'.
	 * @see alpha.targetmapping.TargetMappingNode
	 * @generated
	 */
	EClass getTargetMappingNode();

	/**
	 * Returns the meta object for class '{@link alpha.targetmapping.TargetMappingVisitable <em>Target Mapping Visitable</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Target Mapping Visitable</em>'.
	 * @see alpha.targetmapping.TargetMappingVisitable
	 * @generated
	 */
	EClass getTargetMappingVisitable();

	/**
	 * Returns the meta object for class '{@link alpha.targetmapping.TargetMappingVisitor <em>Target Mapping Visitor</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Target Mapping Visitor</em>'.
	 * @see alpha.targetmapping.TargetMappingVisitor
	 * @generated
	 */
	EClass getTargetMappingVisitor();

	/**
	 * Returns the meta object for class '{@link alpha.targetmapping.ScopingEntity <em>Scoping Entity</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Scoping Entity</em>'.
	 * @see alpha.targetmapping.ScopingEntity
	 * @generated
	 */
	EClass getScopingEntity();

	/**
	 * Returns the meta object for class '{@link alpha.targetmapping.TargetMapping <em>Target Mapping</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Target Mapping</em>'.
	 * @see alpha.targetmapping.TargetMapping
	 * @generated
	 */
	EClass getTargetMapping();

	/**
	 * Returns the meta object for the reference '{@link alpha.targetmapping.TargetMapping#getTargetSystem <em>Target System</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Target System</em>'.
	 * @see alpha.targetmapping.TargetMapping#getTargetSystem()
	 * @see #getTargetMapping()
	 * @generated
	 */
	EReference getTargetMapping_TargetSystem();

	/**
	 * Returns the meta object for the containment reference list '{@link alpha.targetmapping.TargetMapping#getSystemBodyTMs <em>System Body TMs</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>System Body TMs</em>'.
	 * @see alpha.targetmapping.TargetMapping#getSystemBodyTMs()
	 * @see #getTargetMapping()
	 * @generated
	 */
	EReference getTargetMapping_SystemBodyTMs();

	/**
	 * Returns the meta object for class '{@link alpha.targetmapping.TargetMappingForSystemBody <em>Target Mapping For System Body</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Target Mapping For System Body</em>'.
	 * @see alpha.targetmapping.TargetMappingForSystemBody
	 * @generated
	 */
	EClass getTargetMappingForSystemBody();

	/**
	 * Returns the meta object for the reference '{@link alpha.targetmapping.TargetMappingForSystemBody#getTargetBody <em>Target Body</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Target Body</em>'.
	 * @see alpha.targetmapping.TargetMappingForSystemBody#getTargetBody()
	 * @see #getTargetMappingForSystemBody()
	 * @generated
	 */
	EReference getTargetMappingForSystemBody_TargetBody();

	/**
	 * Returns the meta object for the container reference '{@link alpha.targetmapping.TargetMappingForSystemBody#getTargetMapping <em>Target Mapping</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the container reference '<em>Target Mapping</em>'.
	 * @see alpha.targetmapping.TargetMappingForSystemBody#getTargetMapping()
	 * @see #getTargetMappingForSystemBody()
	 * @generated
	 */
	EReference getTargetMappingForSystemBody_TargetMapping();

	/**
	 * Returns the meta object for the containment reference '{@link alpha.targetmapping.TargetMappingForSystemBody#getScheduleTreeRoot <em>Schedule Tree Root</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Schedule Tree Root</em>'.
	 * @see alpha.targetmapping.TargetMappingForSystemBody#getScheduleTreeRoot()
	 * @see #getTargetMappingForSystemBody()
	 * @generated
	 */
	EReference getTargetMappingForSystemBody_ScheduleTreeRoot();

	/**
	 * Returns the meta object for class '{@link alpha.targetmapping.ScheduleTreeExpression <em>Schedule Tree Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Schedule Tree Expression</em>'.
	 * @see alpha.targetmapping.ScheduleTreeExpression
	 * @generated
	 */
	EClass getScheduleTreeExpression();

	/**
	 * Returns the meta object for class '{@link alpha.targetmapping.ContextExpression <em>Context Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Context Expression</em>'.
	 * @see alpha.targetmapping.ContextExpression
	 * @generated
	 */
	EClass getContextExpression();

	/**
	 * Returns the meta object for the containment reference '{@link alpha.targetmapping.ContextExpression#getContextDomainExpr <em>Context Domain Expr</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Context Domain Expr</em>'.
	 * @see alpha.targetmapping.ContextExpression#getContextDomainExpr()
	 * @see #getContextExpression()
	 * @generated
	 */
	EReference getContextExpression_ContextDomainExpr();

	/**
	 * Returns the meta object for the containment reference '{@link alpha.targetmapping.ContextExpression#getChild <em>Child</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Child</em>'.
	 * @see alpha.targetmapping.ContextExpression#getChild()
	 * @see #getContextExpression()
	 * @generated
	 */
	EReference getContextExpression_Child();

	/**
	 * Returns the meta object for class '{@link alpha.targetmapping.SetExpression <em>Set Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Set Expression</em>'.
	 * @see alpha.targetmapping.SetExpression
	 * @generated
	 */
	EClass getSetExpression();

	/**
	 * Returns the meta object for the containment reference list '{@link alpha.targetmapping.SetExpression#getChildren <em>Children</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Children</em>'.
	 * @see alpha.targetmapping.SetExpression#getChildren()
	 * @see #getSetExpression()
	 * @generated
	 */
	EReference getSetExpression_Children();

	/**
	 * Returns the meta object for class '{@link alpha.targetmapping.SequenceExpression <em>Sequence Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Sequence Expression</em>'.
	 * @see alpha.targetmapping.SequenceExpression
	 * @generated
	 */
	EClass getSequenceExpression();

	/**
	 * Returns the meta object for the containment reference list '{@link alpha.targetmapping.SequenceExpression#getChildren <em>Children</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Children</em>'.
	 * @see alpha.targetmapping.SequenceExpression#getChildren()
	 * @see #getSequenceExpression()
	 * @generated
	 */
	EReference getSequenceExpression_Children();

	/**
	 * Returns the meta object for class '{@link alpha.targetmapping.FilterExpression <em>Filter Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Filter Expression</em>'.
	 * @see alpha.targetmapping.FilterExpression
	 * @generated
	 */
	EClass getFilterExpression();

	/**
	 * Returns the meta object for the containment reference list '{@link alpha.targetmapping.FilterExpression#getFilterDomains <em>Filter Domains</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Filter Domains</em>'.
	 * @see alpha.targetmapping.FilterExpression#getFilterDomains()
	 * @see #getFilterExpression()
	 * @generated
	 */
	EReference getFilterExpression_FilterDomains();

	/**
	 * Returns the meta object for the containment reference '{@link alpha.targetmapping.FilterExpression#getChild <em>Child</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Child</em>'.
	 * @see alpha.targetmapping.FilterExpression#getChild()
	 * @see #getFilterExpression()
	 * @generated
	 */
	EReference getFilterExpression_Child();

	/**
	 * Returns the meta object for class '{@link alpha.targetmapping.ScheduleTargetRestrictDomain <em>Schedule Target Restrict Domain</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Schedule Target Restrict Domain</em>'.
	 * @see alpha.targetmapping.ScheduleTargetRestrictDomain
	 * @generated
	 */
	EClass getScheduleTargetRestrictDomain();

	/**
	 * Returns the meta object for the reference '{@link alpha.targetmapping.ScheduleTargetRestrictDomain#getScheduleTarget <em>Schedule Target</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Schedule Target</em>'.
	 * @see alpha.targetmapping.ScheduleTargetRestrictDomain#getScheduleTarget()
	 * @see #getScheduleTargetRestrictDomain()
	 * @generated
	 */
	EReference getScheduleTargetRestrictDomain_ScheduleTarget();

	/**
	 * Returns the meta object for the attribute list '{@link alpha.targetmapping.ScheduleTargetRestrictDomain#getIndexNames <em>Index Names</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Index Names</em>'.
	 * @see alpha.targetmapping.ScheduleTargetRestrictDomain#getIndexNames()
	 * @see #getScheduleTargetRestrictDomain()
	 * @generated
	 */
	EAttribute getScheduleTargetRestrictDomain_IndexNames();

	/**
	 * Returns the meta object for the containment reference '{@link alpha.targetmapping.ScheduleTargetRestrictDomain#getRestrictDomainExpr <em>Restrict Domain Expr</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Restrict Domain Expr</em>'.
	 * @see alpha.targetmapping.ScheduleTargetRestrictDomain#getRestrictDomainExpr()
	 * @see #getScheduleTargetRestrictDomain()
	 * @generated
	 */
	EReference getScheduleTargetRestrictDomain_RestrictDomainExpr();

	/**
	 * Returns the meta object for class '{@link alpha.targetmapping.GuardExpression <em>Guard Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Guard Expression</em>'.
	 * @see alpha.targetmapping.GuardExpression
	 * @generated
	 */
	EClass getGuardExpression();

	/**
	 * Returns the meta object for the containment reference '{@link alpha.targetmapping.GuardExpression#getGuardDomainExpr <em>Guard Domain Expr</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Guard Domain Expr</em>'.
	 * @see alpha.targetmapping.GuardExpression#getGuardDomainExpr()
	 * @see #getGuardExpression()
	 * @generated
	 */
	EReference getGuardExpression_GuardDomainExpr();

	/**
	 * Returns the meta object for the containment reference '{@link alpha.targetmapping.GuardExpression#getChild <em>Child</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Child</em>'.
	 * @see alpha.targetmapping.GuardExpression#getChild()
	 * @see #getGuardExpression()
	 * @generated
	 */
	EReference getGuardExpression_Child();

	/**
	 * Returns the meta object for class '{@link alpha.targetmapping.MarkExpression <em>Mark Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Mark Expression</em>'.
	 * @see alpha.targetmapping.MarkExpression
	 * @generated
	 */
	EClass getMarkExpression();

	/**
	 * Returns the meta object for the attribute '{@link alpha.targetmapping.MarkExpression#getIdentifier <em>Identifier</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Identifier</em>'.
	 * @see alpha.targetmapping.MarkExpression#getIdentifier()
	 * @see #getMarkExpression()
	 * @generated
	 */
	EAttribute getMarkExpression_Identifier();

	/**
	 * Returns the meta object for the containment reference '{@link alpha.targetmapping.MarkExpression#getChild <em>Child</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Child</em>'.
	 * @see alpha.targetmapping.MarkExpression#getChild()
	 * @see #getMarkExpression()
	 * @generated
	 */
	EReference getMarkExpression_Child();

	/**
	 * Returns the meta object for class '{@link alpha.targetmapping.AbstractBandExpression <em>Abstract Band Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Abstract Band Expression</em>'.
	 * @see alpha.targetmapping.AbstractBandExpression
	 * @generated
	 */
	EClass getAbstractBandExpression();

	/**
	 * Returns the meta object for the containment reference list '{@link alpha.targetmapping.AbstractBandExpression#getBandPieces <em>Band Pieces</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Band Pieces</em>'.
	 * @see alpha.targetmapping.AbstractBandExpression#getBandPieces()
	 * @see #getAbstractBandExpression()
	 * @generated
	 */
	EReference getAbstractBandExpression_BandPieces();

	/**
	 * Returns the meta object for the attribute list '{@link alpha.targetmapping.AbstractBandExpression#getScheduleDimensionNames <em>Schedule Dimension Names</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Schedule Dimension Names</em>'.
	 * @see alpha.targetmapping.AbstractBandExpression#getScheduleDimensionNames()
	 * @see #getAbstractBandExpression()
	 * @generated
	 */
	EAttribute getAbstractBandExpression_ScheduleDimensionNames();

	/**
	 * Returns the meta object for class '{@link alpha.targetmapping.BandExpression <em>Band Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Band Expression</em>'.
	 * @see alpha.targetmapping.BandExpression
	 * @generated
	 */
	EClass getBandExpression();

	/**
	 * Returns the meta object for the containment reference list '{@link alpha.targetmapping.BandExpression#getLoopTypeSpecifications <em>Loop Type Specifications</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Loop Type Specifications</em>'.
	 * @see alpha.targetmapping.BandExpression#getLoopTypeSpecifications()
	 * @see #getBandExpression()
	 * @generated
	 */
	EReference getBandExpression_LoopTypeSpecifications();

	/**
	 * Returns the meta object for the containment reference '{@link alpha.targetmapping.BandExpression#getIsolateSpecification <em>Isolate Specification</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Isolate Specification</em>'.
	 * @see alpha.targetmapping.BandExpression#getIsolateSpecification()
	 * @see #getBandExpression()
	 * @generated
	 */
	EReference getBandExpression_IsolateSpecification();

	/**
	 * Returns the meta object for the containment reference '{@link alpha.targetmapping.BandExpression#getChild <em>Child</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Child</em>'.
	 * @see alpha.targetmapping.BandExpression#getChild()
	 * @see #getBandExpression()
	 * @generated
	 */
	EReference getBandExpression_Child();

	/**
	 * Returns the meta object for class '{@link alpha.targetmapping.BandPiece <em>Band Piece</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Band Piece</em>'.
	 * @see alpha.targetmapping.BandPiece
	 * @generated
	 */
	EClass getBandPiece();

	/**
	 * Returns the meta object for the containment reference '{@link alpha.targetmapping.BandPiece#getPieceDomain <em>Piece Domain</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Piece Domain</em>'.
	 * @see alpha.targetmapping.BandPiece#getPieceDomain()
	 * @see #getBandPiece()
	 * @generated
	 */
	EReference getBandPiece_PieceDomain();

	/**
	 * Returns the meta object for the containment reference '{@link alpha.targetmapping.BandPiece#getPartialScheduleExpr <em>Partial Schedule Expr</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Partial Schedule Expr</em>'.
	 * @see alpha.targetmapping.BandPiece#getPartialScheduleExpr()
	 * @see #getBandPiece()
	 * @generated
	 */
	EReference getBandPiece_PartialScheduleExpr();

	/**
	 * Returns the meta object for class '{@link alpha.targetmapping.LoopTypeSpecification <em>Loop Type Specification</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Loop Type Specification</em>'.
	 * @see alpha.targetmapping.LoopTypeSpecification
	 * @generated
	 */
	EClass getLoopTypeSpecification();

	/**
	 * Returns the meta object for the attribute '{@link alpha.targetmapping.LoopTypeSpecification#getDimension <em>Dimension</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Dimension</em>'.
	 * @see alpha.targetmapping.LoopTypeSpecification#getDimension()
	 * @see #getLoopTypeSpecification()
	 * @generated
	 */
	EAttribute getLoopTypeSpecification_Dimension();

	/**
	 * Returns the meta object for class '{@link alpha.targetmapping.ISLLoopTypeSpecification <em>ISL Loop Type Specification</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>ISL Loop Type Specification</em>'.
	 * @see alpha.targetmapping.ISLLoopTypeSpecification
	 * @generated
	 */
	EClass getISLLoopTypeSpecification();

	/**
	 * Returns the meta object for the attribute '{@link alpha.targetmapping.ISLLoopTypeSpecification#getLoopType <em>Loop Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Loop Type</em>'.
	 * @see alpha.targetmapping.ISLLoopTypeSpecification#getLoopType()
	 * @see #getISLLoopTypeSpecification()
	 * @generated
	 */
	EAttribute getISLLoopTypeSpecification_LoopType();

	/**
	 * Returns the meta object for class '{@link alpha.targetmapping.AlphaLoopTypeSpecification <em>Alpha Loop Type Specification</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Alpha Loop Type Specification</em>'.
	 * @see alpha.targetmapping.AlphaLoopTypeSpecification
	 * @generated
	 */
	EClass getAlphaLoopTypeSpecification();

	/**
	 * Returns the meta object for the attribute '{@link alpha.targetmapping.AlphaLoopTypeSpecification#getLoopType <em>Loop Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Loop Type</em>'.
	 * @see alpha.targetmapping.AlphaLoopTypeSpecification#getLoopType()
	 * @see #getAlphaLoopTypeSpecification()
	 * @generated
	 */
	EAttribute getAlphaLoopTypeSpecification_LoopType();

	/**
	 * Returns the meta object for class '{@link alpha.targetmapping.IsolateSpecification <em>Isolate Specification</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Isolate Specification</em>'.
	 * @see alpha.targetmapping.IsolateSpecification
	 * @generated
	 */
	EClass getIsolateSpecification();

	/**
	 * Returns the meta object for the containment reference '{@link alpha.targetmapping.IsolateSpecification#getIsolateDomainExpr <em>Isolate Domain Expr</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Isolate Domain Expr</em>'.
	 * @see alpha.targetmapping.IsolateSpecification#getIsolateDomainExpr()
	 * @see #getIsolateSpecification()
	 * @generated
	 */
	EReference getIsolateSpecification_IsolateDomainExpr();

	/**
	 * Returns the meta object for the containment reference list '{@link alpha.targetmapping.IsolateSpecification#getLoopTypeSpecifications <em>Loop Type Specifications</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Loop Type Specifications</em>'.
	 * @see alpha.targetmapping.IsolateSpecification#getLoopTypeSpecifications()
	 * @see #getIsolateSpecification()
	 * @generated
	 */
	EReference getIsolateSpecification_LoopTypeSpecifications();

	/**
	 * Returns the meta object for class '{@link alpha.targetmapping.TileBandExpression <em>Tile Band Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Tile Band Expression</em>'.
	 * @see alpha.targetmapping.TileBandExpression
	 * @generated
	 */
	EClass getTileBandExpression();

	/**
	 * Returns the meta object for the attribute '{@link alpha.targetmapping.TileBandExpression#getTilingType <em>Tiling Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Tiling Type</em>'.
	 * @see alpha.targetmapping.TileBandExpression#getTilingType()
	 * @see #getTileBandExpression()
	 * @generated
	 */
	EAttribute getTileBandExpression_TilingType();

	/**
	 * Returns the meta object for the containment reference '{@link alpha.targetmapping.TileBandExpression#getTilingSpecification <em>Tiling Specification</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Tiling Specification</em>'.
	 * @see alpha.targetmapping.TileBandExpression#getTilingSpecification()
	 * @see #getTileBandExpression()
	 * @generated
	 */
	EReference getTileBandExpression_TilingSpecification();

	/**
	 * Returns the meta object for class '{@link alpha.targetmapping.TilingSpecification <em>Tiling Specification</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Tiling Specification</em>'.
	 * @see alpha.targetmapping.TilingSpecification
	 * @generated
	 */
	EClass getTilingSpecification();

	/**
	 * Returns the meta object for the containment reference '{@link alpha.targetmapping.TilingSpecification#getLoopScheduleExpr <em>Loop Schedule Expr</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Loop Schedule Expr</em>'.
	 * @see alpha.targetmapping.TilingSpecification#getLoopScheduleExpr()
	 * @see #getTilingSpecification()
	 * @generated
	 */
	EReference getTilingSpecification_LoopScheduleExpr();

	/**
	 * Returns the meta object for class '{@link alpha.targetmapping.TileLoopSpecification <em>Tile Loop Specification</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Tile Loop Specification</em>'.
	 * @see alpha.targetmapping.TileLoopSpecification
	 * @generated
	 */
	EClass getTileLoopSpecification();

	/**
	 * Returns the meta object for the attribute '{@link alpha.targetmapping.TileLoopSpecification#isParallel <em>Parallel</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Parallel</em>'.
	 * @see alpha.targetmapping.TileLoopSpecification#isParallel()
	 * @see #getTileLoopSpecification()
	 * @generated
	 */
	EAttribute getTileLoopSpecification_Parallel();

	/**
	 * Returns the meta object for the containment reference list '{@link alpha.targetmapping.TileLoopSpecification#getTileSizeSpecifications <em>Tile Size Specifications</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Tile Size Specifications</em>'.
	 * @see alpha.targetmapping.TileLoopSpecification#getTileSizeSpecifications()
	 * @see #getTileLoopSpecification()
	 * @generated
	 */
	EReference getTileLoopSpecification_TileSizeSpecifications();

	/**
	 * Returns the meta object for the containment reference '{@link alpha.targetmapping.TileLoopSpecification#getTilingSpecification <em>Tiling Specification</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Tiling Specification</em>'.
	 * @see alpha.targetmapping.TileLoopSpecification#getTilingSpecification()
	 * @see #getTileLoopSpecification()
	 * @generated
	 */
	EReference getTileLoopSpecification_TilingSpecification();

	/**
	 * Returns the meta object for class '{@link alpha.targetmapping.PointLoopSpecification <em>Point Loop Specification</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Point Loop Specification</em>'.
	 * @see alpha.targetmapping.PointLoopSpecification
	 * @generated
	 */
	EClass getPointLoopSpecification();

	/**
	 * Returns the meta object for the containment reference list '{@link alpha.targetmapping.PointLoopSpecification#getLoopTypeSpecifications <em>Loop Type Specifications</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Loop Type Specifications</em>'.
	 * @see alpha.targetmapping.PointLoopSpecification#getLoopTypeSpecifications()
	 * @see #getPointLoopSpecification()
	 * @generated
	 */
	EReference getPointLoopSpecification_LoopTypeSpecifications();

	/**
	 * Returns the meta object for the containment reference '{@link alpha.targetmapping.PointLoopSpecification#getFullTileSpecification <em>Full Tile Specification</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Full Tile Specification</em>'.
	 * @see alpha.targetmapping.PointLoopSpecification#getFullTileSpecification()
	 * @see #getPointLoopSpecification()
	 * @generated
	 */
	EReference getPointLoopSpecification_FullTileSpecification();

	/**
	 * Returns the meta object for class '{@link alpha.targetmapping.FullTileSpecification <em>Full Tile Specification</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Full Tile Specification</em>'.
	 * @see alpha.targetmapping.FullTileSpecification
	 * @generated
	 */
	EClass getFullTileSpecification();

	/**
	 * Returns the meta object for the containment reference list '{@link alpha.targetmapping.FullTileSpecification#getLoopTypeSpecifications <em>Loop Type Specifications</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Loop Type Specifications</em>'.
	 * @see alpha.targetmapping.FullTileSpecification#getLoopTypeSpecifications()
	 * @see #getFullTileSpecification()
	 * @generated
	 */
	EReference getFullTileSpecification_LoopTypeSpecifications();

	/**
	 * Returns the meta object for class '{@link alpha.targetmapping.TileSizeSpecification <em>Tile Size Specification</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Tile Size Specification</em>'.
	 * @see alpha.targetmapping.TileSizeSpecification
	 * @generated
	 */
	EClass getTileSizeSpecification();

	/**
	 * Returns the meta object for class '{@link alpha.targetmapping.FixedTileSize <em>Fixed Tile Size</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Fixed Tile Size</em>'.
	 * @see alpha.targetmapping.FixedTileSize
	 * @generated
	 */
	EClass getFixedTileSize();

	/**
	 * Returns the meta object for the attribute '{@link alpha.targetmapping.FixedTileSize#getTileSize <em>Tile Size</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Tile Size</em>'.
	 * @see alpha.targetmapping.FixedTileSize#getTileSize()
	 * @see #getFixedTileSize()
	 * @generated
	 */
	EAttribute getFixedTileSize_TileSize();

	/**
	 * Returns the meta object for class '{@link alpha.targetmapping.ParametricTileSize <em>Parametric Tile Size</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Parametric Tile Size</em>'.
	 * @see alpha.targetmapping.ParametricTileSize
	 * @generated
	 */
	EClass getParametricTileSize();

	/**
	 * Returns the meta object for the attribute '{@link alpha.targetmapping.ParametricTileSize#getTileSizeName <em>Tile Size Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Tile Size Name</em>'.
	 * @see alpha.targetmapping.ParametricTileSize#getTileSizeName()
	 * @see #getParametricTileSize()
	 * @generated
	 */
	EAttribute getParametricTileSize_TileSizeName();

	/**
	 * Returns the meta object for class '{@link alpha.targetmapping.CompileTimeConstantTileSize <em>Compile Time Constant Tile Size</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Compile Time Constant Tile Size</em>'.
	 * @see alpha.targetmapping.CompileTimeConstantTileSize
	 * @generated
	 */
	EClass getCompileTimeConstantTileSize();

	/**
	 * Returns the meta object for the attribute '{@link alpha.targetmapping.CompileTimeConstantTileSize#getTileSizeName <em>Tile Size Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Tile Size Name</em>'.
	 * @see alpha.targetmapping.CompileTimeConstantTileSize#getTileSizeName()
	 * @see #getCompileTimeConstantTileSize()
	 * @generated
	 */
	EAttribute getCompileTimeConstantTileSize_TileSizeName();

	/**
	 * Returns the meta object for the attribute '{@link alpha.targetmapping.CompileTimeConstantTileSize#getDefaultValue <em>Default Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Default Value</em>'.
	 * @see alpha.targetmapping.CompileTimeConstantTileSize#getDefaultValue()
	 * @see #getCompileTimeConstantTileSize()
	 * @generated
	 */
	EAttribute getCompileTimeConstantTileSize_DefaultValue();

	/**
	 * Returns the meta object for class '{@link alpha.targetmapping.ExtensionExpression <em>Extension Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Extension Expression</em>'.
	 * @see alpha.targetmapping.ExtensionExpression
	 * @generated
	 */
	EClass getExtensionExpression();

	/**
	 * Returns the meta object for the containment reference list '{@link alpha.targetmapping.ExtensionExpression#getExtensionTargets <em>Extension Targets</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Extension Targets</em>'.
	 * @see alpha.targetmapping.ExtensionExpression#getExtensionTargets()
	 * @see #getExtensionExpression()
	 * @generated
	 */
	EReference getExtensionExpression_ExtensionTargets();

	/**
	 * Returns the meta object for the containment reference '{@link alpha.targetmapping.ExtensionExpression#getChild <em>Child</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Child</em>'.
	 * @see alpha.targetmapping.ExtensionExpression#getChild()
	 * @see #getExtensionExpression()
	 * @generated
	 */
	EReference getExtensionExpression_Child();

	/**
	 * Returns the meta object for class '{@link alpha.targetmapping.ExtensionTarget <em>Extension Target</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Extension Target</em>'.
	 * @see alpha.targetmapping.ExtensionTarget
	 * @generated
	 */
	EClass getExtensionTarget();

	/**
	 * Returns the meta object for the attribute '{@link alpha.targetmapping.ExtensionTarget#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see alpha.targetmapping.ExtensionTarget#getName()
	 * @see #getExtensionTarget()
	 * @generated
	 */
	EAttribute getExtensionTarget_Name();

	/**
	 * Returns the meta object for the attribute list '{@link alpha.targetmapping.ExtensionTarget#getIndexNames <em>Index Names</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Index Names</em>'.
	 * @see alpha.targetmapping.ExtensionTarget#getIndexNames()
	 * @see #getExtensionTarget()
	 * @generated
	 */
	EAttribute getExtensionTarget_IndexNames();

	/**
	 * Returns the meta object for the containment reference '{@link alpha.targetmapping.ExtensionTarget#getExtensionMapExpr <em>Extension Map Expr</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Extension Map Expr</em>'.
	 * @see alpha.targetmapping.ExtensionTarget#getExtensionMapExpr()
	 * @see #getExtensionTarget()
	 * @generated
	 */
	EReference getExtensionTarget_ExtensionMapExpr();

	/**
	 * Returns the meta object for class '{@link alpha.targetmapping.JNIIdentityFunction <em>JNI Identity Function</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>JNI Identity Function</em>'.
	 * @see alpha.targetmapping.JNIIdentityFunction
	 * @generated
	 */
	EClass getJNIIdentityFunction();

	/**
	 * Returns the meta object for enum '{@link alpha.targetmapping.ALPHA_LOOP_TYPE <em>ALPHA LOOP TYPE</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>ALPHA LOOP TYPE</em>'.
	 * @see alpha.targetmapping.ALPHA_LOOP_TYPE
	 * @generated
	 */
	EEnum getALPHA_LOOP_TYPE();

	/**
	 * Returns the meta object for enum '{@link alpha.targetmapping.TILING_TYPE <em>TILING TYPE</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>TILING TYPE</em>'.
	 * @see alpha.targetmapping.TILING_TYPE
	 * @generated
	 */
	EEnum getTILING_TYPE();

	/**
	 * Returns the meta object for enum '{@link alpha.targetmapping.DataType <em>Data Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Data Type</em>'.
	 * @see alpha.targetmapping.DataType
	 * @generated
	 */
	EEnum getDataType();

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
	 * Returns the meta object for data type '{@link fr.irisa.cairn.jnimap.isl.ISLASTLoopType <em>ISLAST Loop Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for data type '<em>ISLAST Loop Type</em>'.
	 * @see fr.irisa.cairn.jnimap.isl.ISLASTLoopType
	 * @model instanceClass="fr.irisa.cairn.jnimap.isl.ISLASTLoopType"
	 * @generated
	 */
	EDataType getISLASTLoopType();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	TargetmappingFactory getTargetmappingFactory();

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
		 * The meta object literal for the '{@link alpha.targetmapping.TargetMappingNode <em>Target Mapping Node</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see alpha.targetmapping.TargetMappingNode
		 * @see alpha.targetmapping.impl.TargetmappingPackageImpl#getTargetMappingNode()
		 * @generated
		 */
		EClass TARGET_MAPPING_NODE = eINSTANCE.getTargetMappingNode();

		/**
		 * The meta object literal for the '{@link alpha.targetmapping.TargetMappingVisitable <em>Target Mapping Visitable</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see alpha.targetmapping.TargetMappingVisitable
		 * @see alpha.targetmapping.impl.TargetmappingPackageImpl#getTargetMappingVisitable()
		 * @generated
		 */
		EClass TARGET_MAPPING_VISITABLE = eINSTANCE.getTargetMappingVisitable();

		/**
		 * The meta object literal for the '{@link alpha.targetmapping.impl.TargetMappingVisitorImpl <em>Target Mapping Visitor</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see alpha.targetmapping.impl.TargetMappingVisitorImpl
		 * @see alpha.targetmapping.impl.TargetmappingPackageImpl#getTargetMappingVisitor()
		 * @generated
		 */
		EClass TARGET_MAPPING_VISITOR = eINSTANCE.getTargetMappingVisitor();

		/**
		 * The meta object literal for the '{@link alpha.targetmapping.ScopingEntity <em>Scoping Entity</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see alpha.targetmapping.ScopingEntity
		 * @see alpha.targetmapping.impl.TargetmappingPackageImpl#getScopingEntity()
		 * @generated
		 */
		EClass SCOPING_ENTITY = eINSTANCE.getScopingEntity();

		/**
		 * The meta object literal for the '{@link alpha.targetmapping.impl.TargetMappingImpl <em>Target Mapping</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see alpha.targetmapping.impl.TargetMappingImpl
		 * @see alpha.targetmapping.impl.TargetmappingPackageImpl#getTargetMapping()
		 * @generated
		 */
		EClass TARGET_MAPPING = eINSTANCE.getTargetMapping();

		/**
		 * The meta object literal for the '<em><b>Target System</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TARGET_MAPPING__TARGET_SYSTEM = eINSTANCE.getTargetMapping_TargetSystem();

		/**
		 * The meta object literal for the '<em><b>System Body TMs</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TARGET_MAPPING__SYSTEM_BODY_TMS = eINSTANCE.getTargetMapping_SystemBodyTMs();

		/**
		 * The meta object literal for the '{@link alpha.targetmapping.impl.TargetMappingForSystemBodyImpl <em>Target Mapping For System Body</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see alpha.targetmapping.impl.TargetMappingForSystemBodyImpl
		 * @see alpha.targetmapping.impl.TargetmappingPackageImpl#getTargetMappingForSystemBody()
		 * @generated
		 */
		EClass TARGET_MAPPING_FOR_SYSTEM_BODY = eINSTANCE.getTargetMappingForSystemBody();

		/**
		 * The meta object literal for the '<em><b>Target Body</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TARGET_MAPPING_FOR_SYSTEM_BODY__TARGET_BODY = eINSTANCE.getTargetMappingForSystemBody_TargetBody();

		/**
		 * The meta object literal for the '<em><b>Target Mapping</b></em>' container reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TARGET_MAPPING_FOR_SYSTEM_BODY__TARGET_MAPPING = eINSTANCE.getTargetMappingForSystemBody_TargetMapping();

		/**
		 * The meta object literal for the '<em><b>Schedule Tree Root</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TARGET_MAPPING_FOR_SYSTEM_BODY__SCHEDULE_TREE_ROOT = eINSTANCE.getTargetMappingForSystemBody_ScheduleTreeRoot();

		/**
		 * The meta object literal for the '{@link alpha.targetmapping.impl.ScheduleTreeExpressionImpl <em>Schedule Tree Expression</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see alpha.targetmapping.impl.ScheduleTreeExpressionImpl
		 * @see alpha.targetmapping.impl.TargetmappingPackageImpl#getScheduleTreeExpression()
		 * @generated
		 */
		EClass SCHEDULE_TREE_EXPRESSION = eINSTANCE.getScheduleTreeExpression();

		/**
		 * The meta object literal for the '{@link alpha.targetmapping.impl.ContextExpressionImpl <em>Context Expression</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see alpha.targetmapping.impl.ContextExpressionImpl
		 * @see alpha.targetmapping.impl.TargetmappingPackageImpl#getContextExpression()
		 * @generated
		 */
		EClass CONTEXT_EXPRESSION = eINSTANCE.getContextExpression();

		/**
		 * The meta object literal for the '<em><b>Context Domain Expr</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CONTEXT_EXPRESSION__CONTEXT_DOMAIN_EXPR = eINSTANCE.getContextExpression_ContextDomainExpr();

		/**
		 * The meta object literal for the '<em><b>Child</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CONTEXT_EXPRESSION__CHILD = eINSTANCE.getContextExpression_Child();

		/**
		 * The meta object literal for the '{@link alpha.targetmapping.impl.SetExpressionImpl <em>Set Expression</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see alpha.targetmapping.impl.SetExpressionImpl
		 * @see alpha.targetmapping.impl.TargetmappingPackageImpl#getSetExpression()
		 * @generated
		 */
		EClass SET_EXPRESSION = eINSTANCE.getSetExpression();

		/**
		 * The meta object literal for the '<em><b>Children</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference SET_EXPRESSION__CHILDREN = eINSTANCE.getSetExpression_Children();

		/**
		 * The meta object literal for the '{@link alpha.targetmapping.impl.SequenceExpressionImpl <em>Sequence Expression</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see alpha.targetmapping.impl.SequenceExpressionImpl
		 * @see alpha.targetmapping.impl.TargetmappingPackageImpl#getSequenceExpression()
		 * @generated
		 */
		EClass SEQUENCE_EXPRESSION = eINSTANCE.getSequenceExpression();

		/**
		 * The meta object literal for the '<em><b>Children</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference SEQUENCE_EXPRESSION__CHILDREN = eINSTANCE.getSequenceExpression_Children();

		/**
		 * The meta object literal for the '{@link alpha.targetmapping.impl.FilterExpressionImpl <em>Filter Expression</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see alpha.targetmapping.impl.FilterExpressionImpl
		 * @see alpha.targetmapping.impl.TargetmappingPackageImpl#getFilterExpression()
		 * @generated
		 */
		EClass FILTER_EXPRESSION = eINSTANCE.getFilterExpression();

		/**
		 * The meta object literal for the '<em><b>Filter Domains</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference FILTER_EXPRESSION__FILTER_DOMAINS = eINSTANCE.getFilterExpression_FilterDomains();

		/**
		 * The meta object literal for the '<em><b>Child</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference FILTER_EXPRESSION__CHILD = eINSTANCE.getFilterExpression_Child();

		/**
		 * The meta object literal for the '{@link alpha.targetmapping.impl.ScheduleTargetRestrictDomainImpl <em>Schedule Target Restrict Domain</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see alpha.targetmapping.impl.ScheduleTargetRestrictDomainImpl
		 * @see alpha.targetmapping.impl.TargetmappingPackageImpl#getScheduleTargetRestrictDomain()
		 * @generated
		 */
		EClass SCHEDULE_TARGET_RESTRICT_DOMAIN = eINSTANCE.getScheduleTargetRestrictDomain();

		/**
		 * The meta object literal for the '<em><b>Schedule Target</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference SCHEDULE_TARGET_RESTRICT_DOMAIN__SCHEDULE_TARGET = eINSTANCE.getScheduleTargetRestrictDomain_ScheduleTarget();

		/**
		 * The meta object literal for the '<em><b>Index Names</b></em>' attribute list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SCHEDULE_TARGET_RESTRICT_DOMAIN__INDEX_NAMES = eINSTANCE.getScheduleTargetRestrictDomain_IndexNames();

		/**
		 * The meta object literal for the '<em><b>Restrict Domain Expr</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference SCHEDULE_TARGET_RESTRICT_DOMAIN__RESTRICT_DOMAIN_EXPR = eINSTANCE.getScheduleTargetRestrictDomain_RestrictDomainExpr();

		/**
		 * The meta object literal for the '{@link alpha.targetmapping.impl.GuardExpressionImpl <em>Guard Expression</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see alpha.targetmapping.impl.GuardExpressionImpl
		 * @see alpha.targetmapping.impl.TargetmappingPackageImpl#getGuardExpression()
		 * @generated
		 */
		EClass GUARD_EXPRESSION = eINSTANCE.getGuardExpression();

		/**
		 * The meta object literal for the '<em><b>Guard Domain Expr</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference GUARD_EXPRESSION__GUARD_DOMAIN_EXPR = eINSTANCE.getGuardExpression_GuardDomainExpr();

		/**
		 * The meta object literal for the '<em><b>Child</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference GUARD_EXPRESSION__CHILD = eINSTANCE.getGuardExpression_Child();

		/**
		 * The meta object literal for the '{@link alpha.targetmapping.impl.MarkExpressionImpl <em>Mark Expression</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see alpha.targetmapping.impl.MarkExpressionImpl
		 * @see alpha.targetmapping.impl.TargetmappingPackageImpl#getMarkExpression()
		 * @generated
		 */
		EClass MARK_EXPRESSION = eINSTANCE.getMarkExpression();

		/**
		 * The meta object literal for the '<em><b>Identifier</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute MARK_EXPRESSION__IDENTIFIER = eINSTANCE.getMarkExpression_Identifier();

		/**
		 * The meta object literal for the '<em><b>Child</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference MARK_EXPRESSION__CHILD = eINSTANCE.getMarkExpression_Child();

		/**
		 * The meta object literal for the '{@link alpha.targetmapping.impl.AbstractBandExpressionImpl <em>Abstract Band Expression</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see alpha.targetmapping.impl.AbstractBandExpressionImpl
		 * @see alpha.targetmapping.impl.TargetmappingPackageImpl#getAbstractBandExpression()
		 * @generated
		 */
		EClass ABSTRACT_BAND_EXPRESSION = eINSTANCE.getAbstractBandExpression();

		/**
		 * The meta object literal for the '<em><b>Band Pieces</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ABSTRACT_BAND_EXPRESSION__BAND_PIECES = eINSTANCE.getAbstractBandExpression_BandPieces();

		/**
		 * The meta object literal for the '<em><b>Schedule Dimension Names</b></em>' attribute list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ABSTRACT_BAND_EXPRESSION__SCHEDULE_DIMENSION_NAMES = eINSTANCE.getAbstractBandExpression_ScheduleDimensionNames();

		/**
		 * The meta object literal for the '{@link alpha.targetmapping.impl.BandExpressionImpl <em>Band Expression</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see alpha.targetmapping.impl.BandExpressionImpl
		 * @see alpha.targetmapping.impl.TargetmappingPackageImpl#getBandExpression()
		 * @generated
		 */
		EClass BAND_EXPRESSION = eINSTANCE.getBandExpression();

		/**
		 * The meta object literal for the '<em><b>Loop Type Specifications</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference BAND_EXPRESSION__LOOP_TYPE_SPECIFICATIONS = eINSTANCE.getBandExpression_LoopTypeSpecifications();

		/**
		 * The meta object literal for the '<em><b>Isolate Specification</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference BAND_EXPRESSION__ISOLATE_SPECIFICATION = eINSTANCE.getBandExpression_IsolateSpecification();

		/**
		 * The meta object literal for the '<em><b>Child</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference BAND_EXPRESSION__CHILD = eINSTANCE.getBandExpression_Child();

		/**
		 * The meta object literal for the '{@link alpha.targetmapping.impl.BandPieceImpl <em>Band Piece</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see alpha.targetmapping.impl.BandPieceImpl
		 * @see alpha.targetmapping.impl.TargetmappingPackageImpl#getBandPiece()
		 * @generated
		 */
		EClass BAND_PIECE = eINSTANCE.getBandPiece();

		/**
		 * The meta object literal for the '<em><b>Piece Domain</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference BAND_PIECE__PIECE_DOMAIN = eINSTANCE.getBandPiece_PieceDomain();

		/**
		 * The meta object literal for the '<em><b>Partial Schedule Expr</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference BAND_PIECE__PARTIAL_SCHEDULE_EXPR = eINSTANCE.getBandPiece_PartialScheduleExpr();

		/**
		 * The meta object literal for the '{@link alpha.targetmapping.impl.LoopTypeSpecificationImpl <em>Loop Type Specification</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see alpha.targetmapping.impl.LoopTypeSpecificationImpl
		 * @see alpha.targetmapping.impl.TargetmappingPackageImpl#getLoopTypeSpecification()
		 * @generated
		 */
		EClass LOOP_TYPE_SPECIFICATION = eINSTANCE.getLoopTypeSpecification();

		/**
		 * The meta object literal for the '<em><b>Dimension</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute LOOP_TYPE_SPECIFICATION__DIMENSION = eINSTANCE.getLoopTypeSpecification_Dimension();

		/**
		 * The meta object literal for the '{@link alpha.targetmapping.impl.ISLLoopTypeSpecificationImpl <em>ISL Loop Type Specification</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see alpha.targetmapping.impl.ISLLoopTypeSpecificationImpl
		 * @see alpha.targetmapping.impl.TargetmappingPackageImpl#getISLLoopTypeSpecification()
		 * @generated
		 */
		EClass ISL_LOOP_TYPE_SPECIFICATION = eINSTANCE.getISLLoopTypeSpecification();

		/**
		 * The meta object literal for the '<em><b>Loop Type</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ISL_LOOP_TYPE_SPECIFICATION__LOOP_TYPE = eINSTANCE.getISLLoopTypeSpecification_LoopType();

		/**
		 * The meta object literal for the '{@link alpha.targetmapping.impl.AlphaLoopTypeSpecificationImpl <em>Alpha Loop Type Specification</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see alpha.targetmapping.impl.AlphaLoopTypeSpecificationImpl
		 * @see alpha.targetmapping.impl.TargetmappingPackageImpl#getAlphaLoopTypeSpecification()
		 * @generated
		 */
		EClass ALPHA_LOOP_TYPE_SPECIFICATION = eINSTANCE.getAlphaLoopTypeSpecification();

		/**
		 * The meta object literal for the '<em><b>Loop Type</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ALPHA_LOOP_TYPE_SPECIFICATION__LOOP_TYPE = eINSTANCE.getAlphaLoopTypeSpecification_LoopType();

		/**
		 * The meta object literal for the '{@link alpha.targetmapping.impl.IsolateSpecificationImpl <em>Isolate Specification</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see alpha.targetmapping.impl.IsolateSpecificationImpl
		 * @see alpha.targetmapping.impl.TargetmappingPackageImpl#getIsolateSpecification()
		 * @generated
		 */
		EClass ISOLATE_SPECIFICATION = eINSTANCE.getIsolateSpecification();

		/**
		 * The meta object literal for the '<em><b>Isolate Domain Expr</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ISOLATE_SPECIFICATION__ISOLATE_DOMAIN_EXPR = eINSTANCE.getIsolateSpecification_IsolateDomainExpr();

		/**
		 * The meta object literal for the '<em><b>Loop Type Specifications</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ISOLATE_SPECIFICATION__LOOP_TYPE_SPECIFICATIONS = eINSTANCE.getIsolateSpecification_LoopTypeSpecifications();

		/**
		 * The meta object literal for the '{@link alpha.targetmapping.impl.TileBandExpressionImpl <em>Tile Band Expression</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see alpha.targetmapping.impl.TileBandExpressionImpl
		 * @see alpha.targetmapping.impl.TargetmappingPackageImpl#getTileBandExpression()
		 * @generated
		 */
		EClass TILE_BAND_EXPRESSION = eINSTANCE.getTileBandExpression();

		/**
		 * The meta object literal for the '<em><b>Tiling Type</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute TILE_BAND_EXPRESSION__TILING_TYPE = eINSTANCE.getTileBandExpression_TilingType();

		/**
		 * The meta object literal for the '<em><b>Tiling Specification</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TILE_BAND_EXPRESSION__TILING_SPECIFICATION = eINSTANCE.getTileBandExpression_TilingSpecification();

		/**
		 * The meta object literal for the '{@link alpha.targetmapping.impl.TilingSpecificationImpl <em>Tiling Specification</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see alpha.targetmapping.impl.TilingSpecificationImpl
		 * @see alpha.targetmapping.impl.TargetmappingPackageImpl#getTilingSpecification()
		 * @generated
		 */
		EClass TILING_SPECIFICATION = eINSTANCE.getTilingSpecification();

		/**
		 * The meta object literal for the '<em><b>Loop Schedule Expr</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TILING_SPECIFICATION__LOOP_SCHEDULE_EXPR = eINSTANCE.getTilingSpecification_LoopScheduleExpr();

		/**
		 * The meta object literal for the '{@link alpha.targetmapping.impl.TileLoopSpecificationImpl <em>Tile Loop Specification</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see alpha.targetmapping.impl.TileLoopSpecificationImpl
		 * @see alpha.targetmapping.impl.TargetmappingPackageImpl#getTileLoopSpecification()
		 * @generated
		 */
		EClass TILE_LOOP_SPECIFICATION = eINSTANCE.getTileLoopSpecification();

		/**
		 * The meta object literal for the '<em><b>Parallel</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute TILE_LOOP_SPECIFICATION__PARALLEL = eINSTANCE.getTileLoopSpecification_Parallel();

		/**
		 * The meta object literal for the '<em><b>Tile Size Specifications</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TILE_LOOP_SPECIFICATION__TILE_SIZE_SPECIFICATIONS = eINSTANCE.getTileLoopSpecification_TileSizeSpecifications();

		/**
		 * The meta object literal for the '<em><b>Tiling Specification</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TILE_LOOP_SPECIFICATION__TILING_SPECIFICATION = eINSTANCE.getTileLoopSpecification_TilingSpecification();

		/**
		 * The meta object literal for the '{@link alpha.targetmapping.impl.PointLoopSpecificationImpl <em>Point Loop Specification</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see alpha.targetmapping.impl.PointLoopSpecificationImpl
		 * @see alpha.targetmapping.impl.TargetmappingPackageImpl#getPointLoopSpecification()
		 * @generated
		 */
		EClass POINT_LOOP_SPECIFICATION = eINSTANCE.getPointLoopSpecification();

		/**
		 * The meta object literal for the '<em><b>Loop Type Specifications</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference POINT_LOOP_SPECIFICATION__LOOP_TYPE_SPECIFICATIONS = eINSTANCE.getPointLoopSpecification_LoopTypeSpecifications();

		/**
		 * The meta object literal for the '<em><b>Full Tile Specification</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference POINT_LOOP_SPECIFICATION__FULL_TILE_SPECIFICATION = eINSTANCE.getPointLoopSpecification_FullTileSpecification();

		/**
		 * The meta object literal for the '{@link alpha.targetmapping.impl.FullTileSpecificationImpl <em>Full Tile Specification</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see alpha.targetmapping.impl.FullTileSpecificationImpl
		 * @see alpha.targetmapping.impl.TargetmappingPackageImpl#getFullTileSpecification()
		 * @generated
		 */
		EClass FULL_TILE_SPECIFICATION = eINSTANCE.getFullTileSpecification();

		/**
		 * The meta object literal for the '<em><b>Loop Type Specifications</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference FULL_TILE_SPECIFICATION__LOOP_TYPE_SPECIFICATIONS = eINSTANCE.getFullTileSpecification_LoopTypeSpecifications();

		/**
		 * The meta object literal for the '{@link alpha.targetmapping.impl.TileSizeSpecificationImpl <em>Tile Size Specification</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see alpha.targetmapping.impl.TileSizeSpecificationImpl
		 * @see alpha.targetmapping.impl.TargetmappingPackageImpl#getTileSizeSpecification()
		 * @generated
		 */
		EClass TILE_SIZE_SPECIFICATION = eINSTANCE.getTileSizeSpecification();

		/**
		 * The meta object literal for the '{@link alpha.targetmapping.impl.FixedTileSizeImpl <em>Fixed Tile Size</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see alpha.targetmapping.impl.FixedTileSizeImpl
		 * @see alpha.targetmapping.impl.TargetmappingPackageImpl#getFixedTileSize()
		 * @generated
		 */
		EClass FIXED_TILE_SIZE = eINSTANCE.getFixedTileSize();

		/**
		 * The meta object literal for the '<em><b>Tile Size</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute FIXED_TILE_SIZE__TILE_SIZE = eINSTANCE.getFixedTileSize_TileSize();

		/**
		 * The meta object literal for the '{@link alpha.targetmapping.impl.ParametricTileSizeImpl <em>Parametric Tile Size</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see alpha.targetmapping.impl.ParametricTileSizeImpl
		 * @see alpha.targetmapping.impl.TargetmappingPackageImpl#getParametricTileSize()
		 * @generated
		 */
		EClass PARAMETRIC_TILE_SIZE = eINSTANCE.getParametricTileSize();

		/**
		 * The meta object literal for the '<em><b>Tile Size Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PARAMETRIC_TILE_SIZE__TILE_SIZE_NAME = eINSTANCE.getParametricTileSize_TileSizeName();

		/**
		 * The meta object literal for the '{@link alpha.targetmapping.impl.CompileTimeConstantTileSizeImpl <em>Compile Time Constant Tile Size</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see alpha.targetmapping.impl.CompileTimeConstantTileSizeImpl
		 * @see alpha.targetmapping.impl.TargetmappingPackageImpl#getCompileTimeConstantTileSize()
		 * @generated
		 */
		EClass COMPILE_TIME_CONSTANT_TILE_SIZE = eINSTANCE.getCompileTimeConstantTileSize();

		/**
		 * The meta object literal for the '<em><b>Tile Size Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute COMPILE_TIME_CONSTANT_TILE_SIZE__TILE_SIZE_NAME = eINSTANCE.getCompileTimeConstantTileSize_TileSizeName();

		/**
		 * The meta object literal for the '<em><b>Default Value</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute COMPILE_TIME_CONSTANT_TILE_SIZE__DEFAULT_VALUE = eINSTANCE.getCompileTimeConstantTileSize_DefaultValue();

		/**
		 * The meta object literal for the '{@link alpha.targetmapping.impl.ExtensionExpressionImpl <em>Extension Expression</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see alpha.targetmapping.impl.ExtensionExpressionImpl
		 * @see alpha.targetmapping.impl.TargetmappingPackageImpl#getExtensionExpression()
		 * @generated
		 */
		EClass EXTENSION_EXPRESSION = eINSTANCE.getExtensionExpression();

		/**
		 * The meta object literal for the '<em><b>Extension Targets</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference EXTENSION_EXPRESSION__EXTENSION_TARGETS = eINSTANCE.getExtensionExpression_ExtensionTargets();

		/**
		 * The meta object literal for the '<em><b>Child</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference EXTENSION_EXPRESSION__CHILD = eINSTANCE.getExtensionExpression_Child();

		/**
		 * The meta object literal for the '{@link alpha.targetmapping.impl.ExtensionTargetImpl <em>Extension Target</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see alpha.targetmapping.impl.ExtensionTargetImpl
		 * @see alpha.targetmapping.impl.TargetmappingPackageImpl#getExtensionTarget()
		 * @generated
		 */
		EClass EXTENSION_TARGET = eINSTANCE.getExtensionTarget();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute EXTENSION_TARGET__NAME = eINSTANCE.getExtensionTarget_Name();

		/**
		 * The meta object literal for the '<em><b>Index Names</b></em>' attribute list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute EXTENSION_TARGET__INDEX_NAMES = eINSTANCE.getExtensionTarget_IndexNames();

		/**
		 * The meta object literal for the '<em><b>Extension Map Expr</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference EXTENSION_TARGET__EXTENSION_MAP_EXPR = eINSTANCE.getExtensionTarget_ExtensionMapExpr();

		/**
		 * The meta object literal for the '{@link alpha.targetmapping.impl.JNIIdentityFunctionImpl <em>JNI Identity Function</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see alpha.targetmapping.impl.JNIIdentityFunctionImpl
		 * @see alpha.targetmapping.impl.TargetmappingPackageImpl#getJNIIdentityFunction()
		 * @generated
		 */
		EClass JNI_IDENTITY_FUNCTION = eINSTANCE.getJNIIdentityFunction();

		/**
		 * The meta object literal for the '{@link alpha.targetmapping.ALPHA_LOOP_TYPE <em>ALPHA LOOP TYPE</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see alpha.targetmapping.ALPHA_LOOP_TYPE
		 * @see alpha.targetmapping.impl.TargetmappingPackageImpl#getALPHA_LOOP_TYPE()
		 * @generated
		 */
		EEnum ALPHA_LOOP_TYPE = eINSTANCE.getALPHA_LOOP_TYPE();

		/**
		 * The meta object literal for the '{@link alpha.targetmapping.TILING_TYPE <em>TILING TYPE</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see alpha.targetmapping.TILING_TYPE
		 * @see alpha.targetmapping.impl.TargetmappingPackageImpl#getTILING_TYPE()
		 * @generated
		 */
		EEnum TILING_TYPE = eINSTANCE.getTILING_TYPE();

		/**
		 * The meta object literal for the '{@link alpha.targetmapping.DataType <em>Data Type</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see alpha.targetmapping.DataType
		 * @see alpha.targetmapping.impl.TargetmappingPackageImpl#getDataType()
		 * @generated
		 */
		EEnum DATA_TYPE = eINSTANCE.getDataType();

		/**
		 * The meta object literal for the '<em>JNI Object</em>' data type.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see fr.irisa.cairn.jnimap.runtime.JNIObject
		 * @see alpha.targetmapping.impl.TargetmappingPackageImpl#getJNIObject()
		 * @generated
		 */
		EDataType JNI_OBJECT = eINSTANCE.getJNIObject();

		/**
		 * The meta object literal for the '<em>JNIISL Set</em>' data type.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see fr.irisa.cairn.jnimap.isl.ISLSet
		 * @see alpha.targetmapping.impl.TargetmappingPackageImpl#getJNIISLSet()
		 * @generated
		 */
		EDataType JNIISL_SET = eINSTANCE.getJNIISLSet();

		/**
		 * The meta object literal for the '<em>JNIISL Map</em>' data type.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see fr.irisa.cairn.jnimap.isl.ISLMap
		 * @see alpha.targetmapping.impl.TargetmappingPackageImpl#getJNIISLMap()
		 * @generated
		 */
		EDataType JNIISL_MAP = eINSTANCE.getJNIISLMap();

		/**
		 * The meta object literal for the '<em>JNIISL Multi Aff</em>' data type.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see fr.irisa.cairn.jnimap.isl.ISLMultiAff
		 * @see alpha.targetmapping.impl.TargetmappingPackageImpl#getJNIISLMultiAff()
		 * @generated
		 */
		EDataType JNIISL_MULTI_AFF = eINSTANCE.getJNIISLMultiAff();

		/**
		 * The meta object literal for the '<em>JNIISLPWQ Polynomial</em>' data type.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see fr.irisa.cairn.jnimap.isl.ISLPWQPolynomial
		 * @see alpha.targetmapping.impl.TargetmappingPackageImpl#getJNIISLPWQPolynomial()
		 * @generated
		 */
		EDataType JNIISLPWQ_POLYNOMIAL = eINSTANCE.getJNIISLPWQPolynomial();

		/**
		 * The meta object literal for the '<em>ISL FORMAT</em>' data type.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see fr.irisa.cairn.jnimap.isl.ISL_FORMAT
		 * @see alpha.targetmapping.impl.TargetmappingPackageImpl#getISL_FORMAT()
		 * @generated
		 */
		EDataType ISL_FORMAT = eINSTANCE.getISL_FORMAT();

		/**
		 * The meta object literal for the '<em>ISLAST Loop Type</em>' data type.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see fr.irisa.cairn.jnimap.isl.ISLASTLoopType
		 * @see alpha.targetmapping.impl.TargetmappingPackageImpl#getISLASTLoopType()
		 * @generated
		 */
		EDataType ISLAST_LOOP_TYPE = eINSTANCE.getISLASTLoopType();

	}

} //TargetmappingPackage
