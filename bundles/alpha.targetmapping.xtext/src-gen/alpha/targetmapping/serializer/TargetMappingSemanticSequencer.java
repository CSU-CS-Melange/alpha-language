/*
 * generated by Xtext 2.22.0
 */
package alpha.targetmapping.serializer;

import alpha.model.AffineFuzzyVariableUse;
import alpha.model.AlphaConstant;
import alpha.model.AlphaFunction;
import alpha.model.AlphaFunctionBinaryExpression;
import alpha.model.AlphaFunctionFloor;
import alpha.model.AlphaFunctionLiteral;
import alpha.model.AlphaPackage;
import alpha.model.AlphaRoot;
import alpha.model.AlphaSystem;
import alpha.model.ArgReduceExpression;
import alpha.model.AutoRestrictExpression;
import alpha.model.BinaryCalculatorExpression;
import alpha.model.BinaryExpression;
import alpha.model.BooleanExpression;
import alpha.model.CaseExpression;
import alpha.model.ConvolutionExpression;
import alpha.model.DefinedObject;
import alpha.model.DependenceExpression;
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
import alpha.model.serializer.AlphaSemanticSequencer;
import alpha.targetmapping.BandExpression;
import alpha.targetmapping.BandPiece;
import alpha.targetmapping.BandPieceForReductionBody;
import alpha.targetmapping.ContextExpression;
import alpha.targetmapping.ExtensionExpression;
import alpha.targetmapping.ExtensionTarget;
import alpha.targetmapping.FilterExpression;
import alpha.targetmapping.GuardExpression;
import alpha.targetmapping.IsolateSpecification;
import alpha.targetmapping.LoopTypeSpecification;
import alpha.targetmapping.MarkExpression;
import alpha.targetmapping.ScheduleTargetRestrictDomain;
import alpha.targetmapping.SequenceExpression;
import alpha.targetmapping.SetExpression;
import alpha.targetmapping.TargetMapping;
import alpha.targetmapping.TargetMappingForSystemBody;
import alpha.targetmapping.TargetmappingPackage;
import alpha.targetmapping.services.TargetMappingGrammarAccess;
import com.google.inject.Inject;
import java.util.Set;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.xtext.Action;
import org.eclipse.xtext.Parameter;
import org.eclipse.xtext.ParserRule;
import org.eclipse.xtext.serializer.ISerializationContext;
import org.eclipse.xtext.serializer.acceptor.SequenceFeeder;
import org.eclipse.xtext.serializer.sequencer.ITransientValueService.ValueTransient;

@SuppressWarnings("all")
public class TargetMappingSemanticSequencer extends AlphaSemanticSequencer {

	@Inject
	private TargetMappingGrammarAccess grammarAccess;
	
	@Override
	public void sequence(ISerializationContext context, EObject semanticObject) {
		EPackage epackage = semanticObject.eClass().getEPackage();
		ParserRule rule = context.getParserRule();
		Action action = context.getAssignedAction();
		Set<Parameter> parameters = context.getEnabledBooleanParameters();
		if (epackage == ModelPackage.eINSTANCE)
			switch (semanticObject.eClass().getClassifierID()) {
			case ModelPackage.AFFINE_FUZZY_VARIABLE_USE:
				sequence_AffineFuzzyVariableUse(context, (AffineFuzzyVariableUse) semanticObject); 
				return; 
			case ModelPackage.ALPHA_CONSTANT:
				sequence_AlphaConstant(context, (AlphaConstant) semanticObject); 
				return; 
			case ModelPackage.ALPHA_FUNCTION:
				sequence_AlphaFunction(context, (AlphaFunction) semanticObject); 
				return; 
			case ModelPackage.ALPHA_FUNCTION_BINARY_EXPRESSION:
				sequence_AlphaFunctionAdditiveBinaryExpression_AlphaFunctionMultiplicativeBinaryExpression_AlphaFunctionRelationalBinaryExpression(context, (AlphaFunctionBinaryExpression) semanticObject); 
				return; 
			case ModelPackage.ALPHA_FUNCTION_FLOOR:
				sequence_AlphaFunctionFloor(context, (AlphaFunctionFloor) semanticObject); 
				return; 
			case ModelPackage.ALPHA_FUNCTION_LITERAL:
				sequence_AlphaFunctionLiteral(context, (AlphaFunctionLiteral) semanticObject); 
				return; 
			case ModelPackage.ALPHA_PACKAGE:
				sequence_AlphaPackage(context, (AlphaPackage) semanticObject); 
				return; 
			case ModelPackage.ALPHA_ROOT:
				sequence_AlphaRoot(context, (AlphaRoot) semanticObject); 
				return; 
			case ModelPackage.ALPHA_SYSTEM:
				sequence_AlphaSystem(context, (AlphaSystem) semanticObject); 
				return; 
			case ModelPackage.ARG_REDUCE_EXPRESSION:
				sequence_ArgReduceExpression(context, (ArgReduceExpression) semanticObject); 
				return; 
			case ModelPackage.AUTO_RESTRICT_EXPRESSION:
				sequence_AutoRestrictExpression(context, (AutoRestrictExpression) semanticObject); 
				return; 
			case ModelPackage.BINARY_CALCULATOR_EXPRESSION:
				sequence_CalculatorExpression(context, (BinaryCalculatorExpression) semanticObject); 
				return; 
			case ModelPackage.BINARY_EXPRESSION:
				sequence_AdditiveExpression_AndExpression_MinMaxExpression_MultiplicativeExpression_OrExpression_RelationalExpression(context, (BinaryExpression) semanticObject); 
				return; 
			case ModelPackage.BOOLEAN_EXPRESSION:
				sequence_BooleanExpression(context, (BooleanExpression) semanticObject); 
				return; 
			case ModelPackage.CASE_EXPRESSION:
				sequence_CaseExpression(context, (CaseExpression) semanticObject); 
				return; 
			case ModelPackage.CONVOLUTION_EXPRESSION:
				sequence_ConvolutionExpression(context, (ConvolutionExpression) semanticObject); 
				return; 
			case ModelPackage.DEFINED_OBJECT:
				sequence_DefinedObject(context, (DefinedObject) semanticObject); 
				return; 
			case ModelPackage.DEPENDENCE_EXPRESSION:
				sequence_DependenceExpression(context, (DependenceExpression) semanticObject); 
				return; 
			case ModelPackage.EXTERNAL_ARG_REDUCE_EXPRESSION:
				sequence_ExternalArgReduceExpression(context, (ExternalArgReduceExpression) semanticObject); 
				return; 
			case ModelPackage.EXTERNAL_FUNCTION:
				sequence_ExternalFunction(context, (ExternalFunction) semanticObject); 
				return; 
			case ModelPackage.EXTERNAL_FUZZY_ARG_REDUCE_EXPRESSION:
				sequence_ExternalFuzzyArgReduceExpression(context, (ExternalFuzzyArgReduceExpression) semanticObject); 
				return; 
			case ModelPackage.EXTERNAL_FUZZY_REDUCE_EXPRESSION:
				sequence_ExternalFuzzyReduceExpression(context, (ExternalFuzzyReduceExpression) semanticObject); 
				return; 
			case ModelPackage.EXTERNAL_MULTI_ARG_EXPRESSION:
				sequence_ExternalMultiArgExpression(context, (ExternalMultiArgExpression) semanticObject); 
				return; 
			case ModelPackage.EXTERNAL_REDUCE_EXPRESSION:
				sequence_ExternalReduceExpression(context, (ExternalReduceExpression) semanticObject); 
				return; 
			case ModelPackage.FUZZY_ARG_REDUCE_EXPRESSION:
				sequence_FuzzyArgReduceExpression(context, (FuzzyArgReduceExpression) semanticObject); 
				return; 
			case ModelPackage.FUZZY_DEPENDENCE_EXPRESSION:
				sequence_FuzzyDependenceExpression(context, (FuzzyDependenceExpression) semanticObject); 
				return; 
			case ModelPackage.FUZZY_FUNCTION:
				sequence_FuzzyFunction(context, (FuzzyFunction) semanticObject); 
				return; 
			case ModelPackage.FUZZY_FUNCTION_IN_ARRAY_NOTATION:
				sequence_FuzzyFunctionInArrayNotation(context, (FuzzyFunctionInArrayNotation) semanticObject); 
				return; 
			case ModelPackage.FUZZY_INDEX_EXPRESSION:
				sequence_FuzzyIndexExpression(context, (FuzzyIndexExpression) semanticObject); 
				return; 
			case ModelPackage.FUZZY_REDUCE_EXPRESSION:
				sequence_FuzzyReduceExpression(context, (FuzzyReduceExpression) semanticObject); 
				return; 
			case ModelPackage.FUZZY_VARIABLE:
				if (rule == grammarAccess.getFuzzyVariableNameOnlyRule()) {
					sequence_FuzzyVariableNameOnly(context, (FuzzyVariable) semanticObject); 
					return; 
				}
				else if (rule == grammarAccess.getFuzzyVariableRule()) {
					sequence_FuzzyVariable(context, (FuzzyVariable) semanticObject); 
					return; 
				}
				else break;
			case ModelPackage.IF_EXPRESSION:
				sequence_IfExpression(context, (IfExpression) semanticObject); 
				return; 
			case ModelPackage.IMPORTS:
				sequence_Imports(context, (Imports) semanticObject); 
				return; 
			case ModelPackage.INDEX_EXPRESSION:
				sequence_IndexExpression(context, (IndexExpression) semanticObject); 
				return; 
			case ModelPackage.INTEGER_EXPRESSION:
				sequence_IntegerExpression(context, (IntegerExpression) semanticObject); 
				return; 
			case ModelPackage.JNI_DOMAIN:
				if (rule == grammarAccess.getJNIDomainRule()
						|| rule == grammarAccess.getCalculatorExpressionRule()
						|| action == grammarAccess.getCalculatorExpressionAccess().getBinaryCalculatorExpressionLeftAction_1_0()
						|| rule == grammarAccess.getUnaryOrTerminalCalculatorExpressionRule()
						|| rule == grammarAccess.getCalculatorExpressionTerminalRule()) {
					sequence_JNIDomain(context, (JNIDomain) semanticObject); 
					return; 
				}
				else if (rule == grammarAccess.getJNIParamDomainInArrayNotationRule()) {
					sequence_JNIParamDomainInArrayNotation(context, (JNIDomain) semanticObject); 
					return; 
				}
				else if (rule == grammarAccess.getJNIParamDomainRule()) {
					sequence_JNIParamDomain(context, (JNIDomain) semanticObject); 
					return; 
				}
				else break;
			case ModelPackage.JNI_DOMAIN_IN_ARRAY_NOTATION:
				sequence_JNIDomainInArrayNotation(context, (JNIDomainInArrayNotation) semanticObject); 
				return; 
			case ModelPackage.JNI_FUNCTION:
				sequence_JNIFunction(context, (JNIFunction) semanticObject); 
				return; 
			case ModelPackage.JNI_FUNCTION_IN_ARRAY_NOTATION:
				sequence_JNIFunctionInArrayNotation(context, (JNIFunctionInArrayNotation) semanticObject); 
				return; 
			case ModelPackage.JNI_POLYNOMIAL:
				sequence_JNIPolynomial(context, (JNIPolynomial) semanticObject); 
				return; 
			case ModelPackage.JNI_POLYNOMIAL_IN_ARRAY_NOTATION:
				sequence_JNIPolynomialInArrayNotation(context, (JNIPolynomialInArrayNotation) semanticObject); 
				return; 
			case ModelPackage.JNI_RELATION:
				sequence_JNIRelation(context, (JNIRelation) semanticObject); 
				return; 
			case ModelPackage.MULTI_ARG_EXPRESSION:
				sequence_MultiArgExpression(context, (MultiArgExpression) semanticObject); 
				return; 
			case ModelPackage.NESTED_FUZZY_FUNCTION:
				sequence_NestedFuzzyFunction(context, (NestedFuzzyFunction) semanticObject); 
				return; 
			case ModelPackage.POLYHEDRAL_OBJECT:
				sequence_PolyhedralObject(context, (PolyhedralObject) semanticObject); 
				return; 
			case ModelPackage.POLYNOMIAL_INDEX_EXPRESSION:
				sequence_PolynomialIndexExpression(context, (PolynomialIndexExpression) semanticObject); 
				return; 
			case ModelPackage.REAL_EXPRESSION:
				sequence_RealExpression(context, (RealExpression) semanticObject); 
				return; 
			case ModelPackage.RECTANGULAR_DOMAIN:
				sequence_RectangularDomain(context, (RectangularDomain) semanticObject); 
				return; 
			case ModelPackage.REDUCE_EXPRESSION:
				sequence_ReduceExpression(context, (ReduceExpression) semanticObject); 
				return; 
			case ModelPackage.RESTRICT_EXPRESSION:
				sequence_RestrictExpression(context, (RestrictExpression) semanticObject); 
				return; 
			case ModelPackage.SELECT_EXPRESSION:
				sequence_SelectExpression(context, (SelectExpression) semanticObject); 
				return; 
			case ModelPackage.STANDARD_EQUATION:
				sequence_StandardEquation(context, (StandardEquation) semanticObject); 
				return; 
			case ModelPackage.SYSTEM_BODY:
				sequence_SystemBody(context, (SystemBody) semanticObject); 
				return; 
			case ModelPackage.UNARY_CALCULATOR_EXPRESSION:
				sequence_UnaryCalculatorExpression(context, (UnaryCalculatorExpression) semanticObject); 
				return; 
			case ModelPackage.UNARY_EXPRESSION:
				sequence_UnaryExpression(context, (UnaryExpression) semanticObject); 
				return; 
			case ModelPackage.USE_EQUATION:
				sequence_UseEquation(context, (UseEquation) semanticObject); 
				return; 
			case ModelPackage.VARIABLE:
				if (rule == grammarAccess.getVariableNameOnlyRule()) {
					sequence_VariableNameOnly(context, (Variable) semanticObject); 
					return; 
				}
				else if (rule == grammarAccess.getVariableRule()) {
					sequence_Variable(context, (Variable) semanticObject); 
					return; 
				}
				else break;
			case ModelPackage.VARIABLE_DOMAIN:
				sequence_VariableDomain(context, (VariableDomain) semanticObject); 
				return; 
			case ModelPackage.VARIABLE_EXPRESSION:
				sequence_VariableExpression(context, (VariableExpression) semanticObject); 
				return; 
			}
		else if (epackage == TargetmappingPackage.eINSTANCE)
			switch (semanticObject.eClass().getClassifierID()) {
			case TargetmappingPackage.BAND_EXPRESSION:
				sequence_BandExpression(context, (BandExpression) semanticObject); 
				return; 
			case TargetmappingPackage.BAND_PIECE:
				sequence_BandPiece(context, (BandPiece) semanticObject); 
				return; 
			case TargetmappingPackage.BAND_PIECE_FOR_REDUCTION_BODY:
				sequence_BandPieceForReductionBody(context, (BandPieceForReductionBody) semanticObject); 
				return; 
			case TargetmappingPackage.CONTEXT_EXPRESSION:
				sequence_ContextExpression(context, (ContextExpression) semanticObject); 
				return; 
			case TargetmappingPackage.EXTENSION_EXPRESSION:
				sequence_ExtensionExpression(context, (ExtensionExpression) semanticObject); 
				return; 
			case TargetmappingPackage.EXTENSION_TARGET:
				sequence_ExtensionTarget(context, (ExtensionTarget) semanticObject); 
				return; 
			case TargetmappingPackage.FILTER_EXPRESSION:
				sequence_FilterExpression(context, (FilterExpression) semanticObject); 
				return; 
			case TargetmappingPackage.GUARD_EXPRESSION:
				sequence_GuardExpression(context, (GuardExpression) semanticObject); 
				return; 
			case TargetmappingPackage.ISOLATE_SPECIFICATION:
				sequence_IsolateSpecification(context, (IsolateSpecification) semanticObject); 
				return; 
			case TargetmappingPackage.LOOP_TYPE_SPECIFICATION:
				sequence_LoopTypeSpecification(context, (LoopTypeSpecification) semanticObject); 
				return; 
			case TargetmappingPackage.MARK_EXPRESSION:
				sequence_MarkExpression(context, (MarkExpression) semanticObject); 
				return; 
			case TargetmappingPackage.SCHEDULE_TARGET_RESTRICT_DOMAIN:
				if (rule == grammarAccess.getScheduleTargetRestrictDomainRule()) {
					sequence_ScheduleTargetRestrictDomain(context, (ScheduleTargetRestrictDomain) semanticObject); 
					return; 
				}
				else if (rule == grammarAccess.getScheduleTargetRestrictNoSetRule()) {
					sequence_ScheduleTargetRestrictNoSet(context, (ScheduleTargetRestrictDomain) semanticObject); 
					return; 
				}
				else break;
			case TargetmappingPackage.SEQUENCE_EXPRESSION:
				sequence_SequenceExpression(context, (SequenceExpression) semanticObject); 
				return; 
			case TargetmappingPackage.SET_EXPRESSION:
				sequence_SetExpression(context, (SetExpression) semanticObject); 
				return; 
			case TargetmappingPackage.TARGET_MAPPING:
				sequence_TargetMapping(context, (TargetMapping) semanticObject); 
				return; 
			case TargetmappingPackage.TARGET_MAPPING_FOR_SYSTEM_BODY:
				sequence_TargetMappingForSystemBody(context, (TargetMappingForSystemBody) semanticObject); 
				return; 
			}
		if (errorAcceptor != null)
			errorAcceptor.accept(diagnosticProvider.createInvalidContextOrTypeDiagnostic(semanticObject, context));
	}
	
	/**
	 * Contexts:
	 *     ScheduleTreeExpression returns BandExpression
	 *     BandExpression returns BandExpression
	 *
	 * Constraint:
	 *     (
	 *         (tile?='tile' | parallel?='parallel' | loopTypeSpecifications+=LoopTypeSpecification | isolateSpecification=IsolateSpecification)* 
	 *         bandPieces+=BandPiece+ 
	 *         child=ScheduleTreeExpression?
	 *     )
	 */
	protected void sequence_BandExpression(ISerializationContext context, BandExpression semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Contexts:
	 *     BandPiece returns BandPieceForReductionBody
	 *     BandPieceForReductionBody returns BandPieceForReductionBody
	 *
	 * Constraint:
	 *     (pieceDomain=ScheduleTargetRestrictDomain reductionInitialization=STRING? partialScheduleExpr=JNIFunctionInArrayNotation)
	 */
	protected void sequence_BandPieceForReductionBody(ISerializationContext context, BandPieceForReductionBody semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Contexts:
	 *     BandPiece returns BandPiece
	 *
	 * Constraint:
	 *     (pieceDomain=ScheduleTargetRestrictDomain partialScheduleExpr=JNIFunctionInArrayNotation)
	 */
	protected void sequence_BandPiece(ISerializationContext context, BandPiece semanticObject) {
		if (errorAcceptor != null) {
			if (transientValues.isValueTransient(semanticObject, TargetmappingPackage.Literals.BAND_PIECE__PIECE_DOMAIN) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, TargetmappingPackage.Literals.BAND_PIECE__PIECE_DOMAIN));
			if (transientValues.isValueTransient(semanticObject, TargetmappingPackage.Literals.BAND_PIECE__PARTIAL_SCHEDULE_EXPR) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, TargetmappingPackage.Literals.BAND_PIECE__PARTIAL_SCHEDULE_EXPR));
		}
		SequenceFeeder feeder = createSequencerFeeder(context, semanticObject);
		feeder.accept(grammarAccess.getBandPieceAccess().getPieceDomainScheduleTargetRestrictDomainParserRuleCall_0_0_0(), semanticObject.getPieceDomain());
		feeder.accept(grammarAccess.getBandPieceAccess().getPartialScheduleExprJNIFunctionInArrayNotationParserRuleCall_0_2_0(), semanticObject.getPartialScheduleExpr());
		feeder.finish();
	}
	
	
	/**
	 * Contexts:
	 *     ContextExpression returns ContextExpression
	 *
	 * Constraint:
	 *     (contextDomainExpr=JNIDomain child=ScheduleTreeExpression)
	 */
	protected void sequence_ContextExpression(ISerializationContext context, ContextExpression semanticObject) {
		if (errorAcceptor != null) {
			if (transientValues.isValueTransient(semanticObject, TargetmappingPackage.Literals.CONTEXT_EXPRESSION__CONTEXT_DOMAIN_EXPR) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, TargetmappingPackage.Literals.CONTEXT_EXPRESSION__CONTEXT_DOMAIN_EXPR));
			if (transientValues.isValueTransient(semanticObject, TargetmappingPackage.Literals.CONTEXT_EXPRESSION__CHILD) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, TargetmappingPackage.Literals.CONTEXT_EXPRESSION__CHILD));
		}
		SequenceFeeder feeder = createSequencerFeeder(context, semanticObject);
		feeder.accept(grammarAccess.getContextExpressionAccess().getContextDomainExprJNIDomainParserRuleCall_1_0(), semanticObject.getContextDomainExpr());
		feeder.accept(grammarAccess.getContextExpressionAccess().getChildScheduleTreeExpressionParserRuleCall_2_0(), semanticObject.getChild());
		feeder.finish();
	}
	
	
	/**
	 * Contexts:
	 *     FilterOrExtension returns ExtensionExpression
	 *     ExtensionExpression returns ExtensionExpression
	 *
	 * Constraint:
	 *     (extensionTargets+=ExtensionTarget extensionTargets+=ExtensionTarget* child=ScheduleTreeExpression)
	 */
	protected void sequence_ExtensionExpression(ISerializationContext context, ExtensionExpression semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Contexts:
	 *     ExtensionTarget returns ExtensionTarget
	 *
	 * Constraint:
	 *     (source=[AlphaScheduleTarget|ID]? extensionMapExpr=JNIRelation name=ID (indexNames+=ID indexNames+=ID*)?)
	 */
	protected void sequence_ExtensionTarget(ISerializationContext context, ExtensionTarget semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Contexts:
	 *     ScheduleTreeExpression returns FilterExpression
	 *     FilterOrExtension returns FilterExpression
	 *     FilterExpression returns FilterExpression
	 *
	 * Constraint:
	 *     (filterDomains+=ScheduleTargetRestrictDomain filterDomains+=ScheduleTargetRestrictDomain* child=ScheduleTreeExpression)
	 */
	protected void sequence_FilterExpression(ISerializationContext context, FilterExpression semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Contexts:
	 *     ScheduleTreeExpression returns GuardExpression
	 *     GuardExpression returns GuardExpression
	 *
	 * Constraint:
	 *     (guardDomainExpr=JNIDomain child=ScheduleTreeExpression)
	 */
	protected void sequence_GuardExpression(ISerializationContext context, GuardExpression semanticObject) {
		if (errorAcceptor != null) {
			if (transientValues.isValueTransient(semanticObject, TargetmappingPackage.Literals.GUARD_EXPRESSION__GUARD_DOMAIN_EXPR) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, TargetmappingPackage.Literals.GUARD_EXPRESSION__GUARD_DOMAIN_EXPR));
			if (transientValues.isValueTransient(semanticObject, TargetmappingPackage.Literals.GUARD_EXPRESSION__CHILD) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, TargetmappingPackage.Literals.GUARD_EXPRESSION__CHILD));
		}
		SequenceFeeder feeder = createSequencerFeeder(context, semanticObject);
		feeder.accept(grammarAccess.getGuardExpressionAccess().getGuardDomainExprJNIDomainParserRuleCall_1_0(), semanticObject.getGuardDomainExpr());
		feeder.accept(grammarAccess.getGuardExpressionAccess().getChildScheduleTreeExpressionParserRuleCall_2_0(), semanticObject.getChild());
		feeder.finish();
	}
	
	
	/**
	 * Contexts:
	 *     IsolateSpecification returns IsolateSpecification
	 *
	 * Constraint:
	 *     (isolateDomainExpr=JNIDomain (loopTypeSpecifications+=LoopTypeSpecification loopTypeSpecifications+=LoopTypeSpecification*)?)
	 */
	protected void sequence_IsolateSpecification(ISerializationContext context, IsolateSpecification semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Contexts:
	 *     LoopTypeSpecification returns LoopTypeSpecification
	 *
	 * Constraint:
	 *     (loopType=ISLASTLoopType dimension=INT)
	 */
	protected void sequence_LoopTypeSpecification(ISerializationContext context, LoopTypeSpecification semanticObject) {
		if (errorAcceptor != null) {
			if (transientValues.isValueTransient(semanticObject, TargetmappingPackage.Literals.LOOP_TYPE_SPECIFICATION__LOOP_TYPE) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, TargetmappingPackage.Literals.LOOP_TYPE_SPECIFICATION__LOOP_TYPE));
			if (transientValues.isValueTransient(semanticObject, TargetmappingPackage.Literals.LOOP_TYPE_SPECIFICATION__DIMENSION) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, TargetmappingPackage.Literals.LOOP_TYPE_SPECIFICATION__DIMENSION));
		}
		SequenceFeeder feeder = createSequencerFeeder(context, semanticObject);
		feeder.accept(grammarAccess.getLoopTypeSpecificationAccess().getLoopTypeISLASTLoopTypeParserRuleCall_0_0(), semanticObject.getLoopType());
		feeder.accept(grammarAccess.getLoopTypeSpecificationAccess().getDimensionINTTerminalRuleCall_2_0(), semanticObject.getDimension());
		feeder.finish();
	}
	
	
	/**
	 * Contexts:
	 *     ScheduleTreeExpression returns MarkExpression
	 *     MarkExpression returns MarkExpression
	 *
	 * Constraint:
	 *     (identifier=ID child=ScheduleTreeExpression)
	 */
	protected void sequence_MarkExpression(ISerializationContext context, MarkExpression semanticObject) {
		if (errorAcceptor != null) {
			if (transientValues.isValueTransient(semanticObject, TargetmappingPackage.Literals.MARK_EXPRESSION__IDENTIFIER) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, TargetmappingPackage.Literals.MARK_EXPRESSION__IDENTIFIER));
			if (transientValues.isValueTransient(semanticObject, TargetmappingPackage.Literals.MARK_EXPRESSION__CHILD) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, TargetmappingPackage.Literals.MARK_EXPRESSION__CHILD));
		}
		SequenceFeeder feeder = createSequencerFeeder(context, semanticObject);
		feeder.accept(grammarAccess.getMarkExpressionAccess().getIdentifierIDTerminalRuleCall_2_0(), semanticObject.getIdentifier());
		feeder.accept(grammarAccess.getMarkExpressionAccess().getChildScheduleTreeExpressionParserRuleCall_4_0(), semanticObject.getChild());
		feeder.finish();
	}
	
	
	/**
	 * Contexts:
	 *     ScheduleTargetRestrictDomain returns ScheduleTargetRestrictDomain
	 *
	 * Constraint:
	 *     (scheduleTarget=[AlphaScheduleTarget|ID] (indexNames+=ID indexNames+=ID*)? restrictDomainExpr=JNIDomainInArrayNotation?)
	 */
	protected void sequence_ScheduleTargetRestrictDomain(ISerializationContext context, ScheduleTargetRestrictDomain semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Contexts:
	 *     ScheduleTargetRestrictNoSet returns ScheduleTargetRestrictDomain
	 *
	 * Constraint:
	 *     (scheduleTarget=[AlphaScheduleTarget|ID] indexNames+=ID indexNames+=ID*)
	 */
	protected void sequence_ScheduleTargetRestrictNoSet(ISerializationContext context, ScheduleTargetRestrictDomain semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Contexts:
	 *     ScheduleTreeExpression returns SequenceExpression
	 *     SequenceExpression returns SequenceExpression
	 *
	 * Constraint:
	 *     children+=FilterOrExtension+
	 */
	protected void sequence_SequenceExpression(ISerializationContext context, SequenceExpression semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Contexts:
	 *     ScheduleTreeExpression returns SetExpression
	 *     SetExpression returns SetExpression
	 *
	 * Constraint:
	 *     children+=FilterExpression+
	 */
	protected void sequence_SetExpression(ISerializationContext context, SetExpression semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Contexts:
	 *     TargetMappingForSystemBody returns TargetMappingForSystemBody
	 *
	 * Constraint:
	 *     (targetBody=[SystemBody|IntRef]? (scheduleTreeRoot=ContextExpression | scheduleTreeRoot=ScheduleTreeExpression))
	 */
	protected void sequence_TargetMappingForSystemBody(ISerializationContext context, TargetMappingForSystemBody semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Contexts:
	 *     TargetMapping returns TargetMapping
	 *
	 * Constraint:
	 *     (targetSystem=[AlphaSystem|QualifiedName] systemBodyTMs+=TargetMappingForSystemBody*)
	 */
	protected void sequence_TargetMapping(ISerializationContext context, TargetMapping semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
}
