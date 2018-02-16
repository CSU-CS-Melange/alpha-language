/*
 * generated by Xtext 2.13.0
 */
package alpha.model.ide.contentassist.antlr;

import alpha.model.ide.contentassist.antlr.internal.InternalAlphaParser;
import alpha.model.services.AlphaGrammarAccess;
import com.google.inject.Inject;
import java.util.HashMap;
import java.util.Map;
import org.eclipse.xtext.AbstractElement;
import org.eclipse.xtext.ide.editor.contentassist.antlr.AbstractContentAssistParser;

public class AlphaParser extends AbstractContentAssistParser {

	@Inject
	private AlphaGrammarAccess grammarAccess;

	private Map<AbstractElement, String> nameMappings;

	@Override
	protected InternalAlphaParser createParser() {
		InternalAlphaParser result = new InternalAlphaParser(null);
		result.setGrammarAccess(grammarAccess);
		return result;
	}

	@Override
	protected String getRuleName(AbstractElement element) {
		if (nameMappings == null) {
			nameMappings = new HashMap<AbstractElement, String>() {
				private static final long serialVersionUID = 1L;
				{
					put(grammarAccess.getAlphaRootAccess().getElementsAlternatives_0(), "rule__AlphaRoot__ElementsAlternatives_0");
					put(grammarAccess.getAlphaPackageAccess().getElementsAlternatives_3_0(), "rule__AlphaPackage__ElementsAlternatives_3_0");
					put(grammarAccess.getAISLStringAccess().getAlternatives(), "rule__AISLString__Alternatives");
					put(grammarAccess.getAISLExpressionAccess().getAlternatives(), "rule__AISLExpression__Alternatives");
					put(grammarAccess.getAlphaExpressionAccess().getAlternatives(), "rule__AlphaExpression__Alternatives");
					put(grammarAccess.getAlphaTerminalExpressionAccess().getAlternatives(), "rule__AlphaTerminalExpression__Alternatives");
					put(grammarAccess.getRestrictExpressionAccess().getAlternatives(), "rule__RestrictExpression__Alternatives");
					put(grammarAccess.getRestrictExpressionAccess().getDomainExprAlternatives_0_0_0(), "rule__RestrictExpression__DomainExprAlternatives_0_0_0");
					put(grammarAccess.getDependenceExpressionAccess().getAlternatives(), "rule__DependenceExpression__Alternatives");
					put(grammarAccess.getIndexExpressionAccess().getAlternatives(), "rule__IndexExpression__Alternatives");
					put(grammarAccess.getReduceExpressionAccess().getProjectionAlternatives_4_0(), "rule__ReduceExpression__ProjectionAlternatives_4_0");
					put(grammarAccess.getExternalReduceExpressionAccess().getProjectionAlternatives_4_0(), "rule__ExternalReduceExpression__ProjectionAlternatives_4_0");
					put(grammarAccess.getArgReduceExpressionAccess().getProjectionAlternatives_4_0(), "rule__ArgReduceExpression__ProjectionAlternatives_4_0");
					put(grammarAccess.getExternalArgReduceExpressionAccess().getProjectionAlternatives_4_0(), "rule__ExternalArgReduceExpression__ProjectionAlternatives_4_0");
					put(grammarAccess.getUnaryOrTerminalExpressionAccess().getAlternatives(), "rule__UnaryOrTerminalExpression__Alternatives");
					put(grammarAccess.getConstantExpressionAccess().getAlternatives(), "rule__ConstantExpression__Alternatives");
					put(grammarAccess.getAREDUCTION_OPAccess().getAlternatives(), "rule__AREDUCTION_OP__Alternatives");
					put(grammarAccess.getAOrOPAccess().getAlternatives(), "rule__AOrOP__Alternatives");
					put(grammarAccess.getARelationalOPAccess().getAlternatives(), "rule__ARelationalOP__Alternatives");
					put(grammarAccess.getAAdditiveOPAccess().getAlternatives(), "rule__AAdditiveOP__Alternatives");
					put(grammarAccess.getAMultiplicativeOPAccess().getAlternatives(), "rule__AMultiplicativeOP__Alternatives");
					put(grammarAccess.getAMINMAX_OPAccess().getAlternatives(), "rule__AMINMAX_OP__Alternatives");
					put(grammarAccess.getAUnaryOPAccess().getAlternatives(), "rule__AUnaryOP__Alternatives");
					put(grammarAccess.getUnaryOrTerminalCalculatorExpressionAccess().getAlternatives(), "rule__UnaryOrTerminalCalculatorExpression__Alternatives");
					put(grammarAccess.getCalculatorExpressionTerminalAccess().getAlternatives(), "rule__CalculatorExpressionTerminal__Alternatives");
					put(grammarAccess.getAUnaryCalcOpAccess().getAlternatives(), "rule__AUnaryCalcOp__Alternatives");
					put(grammarAccess.getABinaryCalcOpAccess().getAlternatives(), "rule__ABinaryCalcOp__Alternatives");
					put(grammarAccess.getAlphaConstantAccess().getGroup(), "rule__AlphaConstant__Group__0");
					put(grammarAccess.getExternalFunctionAccess().getGroup(), "rule__ExternalFunction__Group__0");
					put(grammarAccess.getImportsAccess().getGroup(), "rule__Imports__Group__0");
					put(grammarAccess.getAlphaPackageAccess().getGroup(), "rule__AlphaPackage__Group__0");
					put(grammarAccess.getAlphaSystemAccess().getGroup(), "rule__AlphaSystem__Group__0");
					put(grammarAccess.getAlphaSystemAccess().getGroup_3(), "rule__AlphaSystem__Group_3__0");
					put(grammarAccess.getAlphaSystemAccess().getGroup_4(), "rule__AlphaSystem__Group_4__0");
					put(grammarAccess.getAlphaSystemAccess().getGroup_5(), "rule__AlphaSystem__Group_5__0");
					put(grammarAccess.getAlphaSystemAccess().getGroup_6(), "rule__AlphaSystem__Group_6__0");
					put(grammarAccess.getAlphaSystemAccess().getGroup_7(), "rule__AlphaSystem__Group_7__0");
					put(grammarAccess.getAlphaSystemAccess().getGroup_8(), "rule__AlphaSystem__Group_8__0");
					put(grammarAccess.getAlphaSystemAccess().getGroup_9(), "rule__AlphaSystem__Group_9__0");
					put(grammarAccess.getInputVariableAccess().getGroup(), "rule__InputVariable__Group__0");
					put(grammarAccess.getOutputVariableAccess().getGroup(), "rule__OutputVariable__Group__0");
					put(grammarAccess.getLocalVariableAccess().getGroup(), "rule__LocalVariable__Group__0");
					put(grammarAccess.getFuzzyVariableAccess().getGroup(), "rule__FuzzyVariable__Group__0");
					put(grammarAccess.getJNIDomainInArrayNotationAccess().getGroup(), "rule__JNIDomainInArrayNotation__Group__0");
					put(grammarAccess.getJNIFunctionInArrayNotationAccess().getGroup(), "rule__JNIFunctionInArrayNotation__Group__0");
					put(grammarAccess.getJNIFunctionInArrayNotationAccess().getGroup_2(), "rule__JNIFunctionInArrayNotation__Group_2__0");
					put(grammarAccess.getJNIFunctionInArrayNotationAccess().getGroup_2_1(), "rule__JNIFunctionInArrayNotation__Group_2_1__0");
					put(grammarAccess.getQualifiedNameAccess().getGroup(), "rule__QualifiedName__Group__0");
					put(grammarAccess.getQualifiedNameAccess().getGroup_1(), "rule__QualifiedName__Group_1__0");
					put(grammarAccess.getQualifiedNameWithWildcardAccess().getGroup(), "rule__QualifiedNameWithWildcard__Group__0");
					put(grammarAccess.getIndexNameAccess().getGroup(), "rule__IndexName__Group__0");
					put(grammarAccess.getAIndexListAccess().getGroup(), "rule__AIndexList__Group__0");
					put(grammarAccess.getAIndexListAccess().getGroup_1(), "rule__AIndexList__Group_1__0");
					put(grammarAccess.getAParamDomainAccess().getGroup(), "rule__AParamDomain__Group__0");
					put(grammarAccess.getAISLSetAccess().getGroup(), "rule__AISLSet__Group__0");
					put(grammarAccess.getAISLSetAccess().getGroup_2(), "rule__AISLSet__Group_2__0");
					put(grammarAccess.getAISLBasicSetAccess().getGroup(), "rule__AISLBasicSet__Group__0");
					put(grammarAccess.getAISLRelationAccess().getGroup(), "rule__AISLRelation__Group__0");
					put(grammarAccess.getAISLRelationAccess().getGroup_2(), "rule__AISLRelation__Group_2__0");
					put(grammarAccess.getAISLBasicRelationAccess().getGroup(), "rule__AISLBasicRelation__Group__0");
					put(grammarAccess.getAISLExpressionListAccess().getGroup(), "rule__AISLExpressionList__Group__0");
					put(grammarAccess.getAISLExpressionListAccess().getGroup_1(), "rule__AISLExpressionList__Group_1__0");
					put(grammarAccess.getAAlphaFunctionAccess().getGroup(), "rule__AAlphaFunction__Group__0");
					put(grammarAccess.getStandardEquationAccess().getGroup(), "rule__StandardEquation__Group__0");
					put(grammarAccess.getStandardEquationAccess().getGroup_1(), "rule__StandardEquation__Group_1__0");
					put(grammarAccess.getStandardEquationAccess().getGroup_1_2(), "rule__StandardEquation__Group_1_2__0");
					put(grammarAccess.getUseEquationAccess().getGroup(), "rule__UseEquation__Group__0");
					put(grammarAccess.getUseEquationAccess().getGroup_0(), "rule__UseEquation__Group_0__0");
					put(grammarAccess.getUseEquationAccess().getGroup_0_2(), "rule__UseEquation__Group_0_2__0");
					put(grammarAccess.getUseEquationAccess().getGroup_0_2_1(), "rule__UseEquation__Group_0_2_1__0");
					put(grammarAccess.getUseEquationAccess().getGroup_0_2_1_2(), "rule__UseEquation__Group_0_2_1_2__0");
					put(grammarAccess.getUseEquationAccess().getGroup_2(), "rule__UseEquation__Group_2__0");
					put(grammarAccess.getUseEquationAccess().getGroup_2_1(), "rule__UseEquation__Group_2_1__0");
					put(grammarAccess.getUseEquationAccess().getGroup_8(), "rule__UseEquation__Group_8__0");
					put(grammarAccess.getUseEquationAccess().getGroup_8_1(), "rule__UseEquation__Group_8_1__0");
					put(grammarAccess.getAlphaTerminalExpressionAccess().getGroup_0(), "rule__AlphaTerminalExpression__Group_0__0");
					put(grammarAccess.getIfExpressionAccess().getGroup(), "rule__IfExpression__Group__0");
					put(grammarAccess.getRestrictExpressionAccess().getGroup_0(), "rule__RestrictExpression__Group_0__0");
					put(grammarAccess.getRestrictExpressionAccess().getGroup_1(), "rule__RestrictExpression__Group_1__0");
					put(grammarAccess.getAutoRestrictExpressionAccess().getGroup(), "rule__AutoRestrictExpression__Group__0");
					put(grammarAccess.getCaseExpressionAccess().getGroup(), "rule__CaseExpression__Group__0");
					put(grammarAccess.getCaseExpressionAccess().getGroup_3(), "rule__CaseExpression__Group_3__0");
					put(grammarAccess.getDependenceExpressionAccess().getGroup_0(), "rule__DependenceExpression__Group_0__0");
					put(grammarAccess.getDependenceExpressionAccess().getGroup_1(), "rule__DependenceExpression__Group_1__0");
					put(grammarAccess.getIndexExpressionAccess().getGroup_0(), "rule__IndexExpression__Group_0__0");
					put(grammarAccess.getReduceExpressionAccess().getGroup(), "rule__ReduceExpression__Group__0");
					put(grammarAccess.getExternalReduceExpressionAccess().getGroup(), "rule__ExternalReduceExpression__Group__0");
					put(grammarAccess.getArgReduceExpressionAccess().getGroup(), "rule__ArgReduceExpression__Group__0");
					put(grammarAccess.getExternalArgReduceExpressionAccess().getGroup(), "rule__ExternalArgReduceExpression__Group__0");
					put(grammarAccess.getConvolutionExpressionAccess().getGroup(), "rule__ConvolutionExpression__Group__0");
					put(grammarAccess.getSelectExpressionAccess().getGroup(), "rule__SelectExpression__Group__0");
					put(grammarAccess.getOrExpressionAccess().getGroup(), "rule__OrExpression__Group__0");
					put(grammarAccess.getOrExpressionAccess().getGroup_1(), "rule__OrExpression__Group_1__0");
					put(grammarAccess.getAndExpressionAccess().getGroup(), "rule__AndExpression__Group__0");
					put(grammarAccess.getAndExpressionAccess().getGroup_1(), "rule__AndExpression__Group_1__0");
					put(grammarAccess.getRelationalExpressionAccess().getGroup(), "rule__RelationalExpression__Group__0");
					put(grammarAccess.getRelationalExpressionAccess().getGroup_1(), "rule__RelationalExpression__Group_1__0");
					put(grammarAccess.getAdditiveExpressionAccess().getGroup(), "rule__AdditiveExpression__Group__0");
					put(grammarAccess.getAdditiveExpressionAccess().getGroup_1(), "rule__AdditiveExpression__Group_1__0");
					put(grammarAccess.getMultiplicativeExpressionAccess().getGroup(), "rule__MultiplicativeExpression__Group__0");
					put(grammarAccess.getMultiplicativeExpressionAccess().getGroup_1(), "rule__MultiplicativeExpression__Group_1__0");
					put(grammarAccess.getMinMaxExpressionAccess().getGroup(), "rule__MinMaxExpression__Group__0");
					put(grammarAccess.getMinMaxExpressionAccess().getGroup_1(), "rule__MinMaxExpression__Group_1__0");
					put(grammarAccess.getMultiArgExpressionAccess().getGroup(), "rule__MultiArgExpression__Group__0");
					put(grammarAccess.getMultiArgExpressionAccess().getGroup_3(), "rule__MultiArgExpression__Group_3__0");
					put(grammarAccess.getExternalMultiArgExpressionAccess().getGroup(), "rule__ExternalMultiArgExpression__Group__0");
					put(grammarAccess.getExternalMultiArgExpressionAccess().getGroup_3(), "rule__ExternalMultiArgExpression__Group_3__0");
					put(grammarAccess.getUnaryExpressionAccess().getGroup(), "rule__UnaryExpression__Group__0");
					put(grammarAccess.getPolyhedralObjectAccess().getGroup(), "rule__PolyhedralObject__Group__0");
					put(grammarAccess.getCalculatorExpressionAccess().getGroup(), "rule__CalculatorExpression__Group__0");
					put(grammarAccess.getCalculatorExpressionAccess().getGroup_1(), "rule__CalculatorExpression__Group_1__0");
					put(grammarAccess.getCalculatorExpressionTerminalAccess().getGroup_6(), "rule__CalculatorExpressionTerminal__Group_6__0");
					put(grammarAccess.getUnaryCalculatorExpressionAccess().getGroup(), "rule__UnaryCalculatorExpression__Group__0");
					put(grammarAccess.getVariableDomainAccess().getGroup(), "rule__VariableDomain__Group__0");
					put(grammarAccess.getRectangularDomainAccess().getGroup(), "rule__RectangularDomain__Group__0");
					put(grammarAccess.getRectangularDomainAccess().getGroup_2(), "rule__RectangularDomain__Group_2__0");
					put(grammarAccess.getRectangularDomainAccess().getGroup_4(), "rule__RectangularDomain__Group_4__0");
					put(grammarAccess.getRectangularDomainAccess().getGroup_4_3(), "rule__RectangularDomain__Group_4_3__0");
					put(grammarAccess.getAlphaRootAccess().getElementsAssignment(), "rule__AlphaRoot__ElementsAssignment");
					put(grammarAccess.getAlphaConstantAccess().getNameAssignment_1(), "rule__AlphaConstant__NameAssignment_1");
					put(grammarAccess.getAlphaConstantAccess().getValueAssignment_3(), "rule__AlphaConstant__ValueAssignment_3");
					put(grammarAccess.getExternalFunctionAccess().getNameAssignment_1(), "rule__ExternalFunction__NameAssignment_1");
					put(grammarAccess.getExternalFunctionAccess().getCardinalityAssignment_3(), "rule__ExternalFunction__CardinalityAssignment_3");
					put(grammarAccess.getImportsAccess().getImportedNamespaceAssignment_1(), "rule__Imports__ImportedNamespaceAssignment_1");
					put(grammarAccess.getAlphaPackageAccess().getNameAssignment_1(), "rule__AlphaPackage__NameAssignment_1");
					put(grammarAccess.getAlphaPackageAccess().getElementsAssignment_3(), "rule__AlphaPackage__ElementsAssignment_3");
					put(grammarAccess.getAlphaSystemAccess().getNameAssignment_1(), "rule__AlphaSystem__NameAssignment_1");
					put(grammarAccess.getAlphaSystemAccess().getParameterDomainAssignment_2(), "rule__AlphaSystem__ParameterDomainAssignment_2");
					put(grammarAccess.getAlphaSystemAccess().getDefinedObjectsAssignment_3_1(), "rule__AlphaSystem__DefinedObjectsAssignment_3_1");
					put(grammarAccess.getAlphaSystemAccess().getInputsAssignment_4_1(), "rule__AlphaSystem__InputsAssignment_4_1");
					put(grammarAccess.getAlphaSystemAccess().getOutputsAssignment_5_1(), "rule__AlphaSystem__OutputsAssignment_5_1");
					put(grammarAccess.getAlphaSystemAccess().getLocalsAssignment_6_1(), "rule__AlphaSystem__LocalsAssignment_6_1");
					put(grammarAccess.getAlphaSystemAccess().getFuzzyVariablesAssignment_7_1(), "rule__AlphaSystem__FuzzyVariablesAssignment_7_1");
					put(grammarAccess.getAlphaSystemAccess().getWhileDomainAssignment_8_1(), "rule__AlphaSystem__WhileDomainAssignment_8_1");
					put(grammarAccess.getAlphaSystemAccess().getTestExpressionAssignment_8_4(), "rule__AlphaSystem__TestExpressionAssignment_8_4");
					put(grammarAccess.getAlphaSystemAccess().getUseEquationsAssignment_9_1(), "rule__AlphaSystem__UseEquationsAssignment_9_1");
					put(grammarAccess.getAlphaSystemAccess().getEquationsAssignment_9_2(), "rule__AlphaSystem__EquationsAssignment_9_2");
					put(grammarAccess.getInputVariableAccess().getNameAssignment_0(), "rule__InputVariable__NameAssignment_0");
					put(grammarAccess.getInputVariableAccess().getDomainExprAssignment_2(), "rule__InputVariable__DomainExprAssignment_2");
					put(grammarAccess.getOutputVariableAccess().getNameAssignment_0(), "rule__OutputVariable__NameAssignment_0");
					put(grammarAccess.getOutputVariableAccess().getDomainExprAssignment_2(), "rule__OutputVariable__DomainExprAssignment_2");
					put(grammarAccess.getLocalVariableAccess().getNameAssignment_0(), "rule__LocalVariable__NameAssignment_0");
					put(grammarAccess.getLocalVariableAccess().getDomainExprAssignment_2(), "rule__LocalVariable__DomainExprAssignment_2");
					put(grammarAccess.getFuzzyVariableAccess().getNameAssignment_0(), "rule__FuzzyVariable__NameAssignment_0");
					put(grammarAccess.getFuzzyVariableAccess().getDomainExprAssignment_2(), "rule__FuzzyVariable__DomainExprAssignment_2");
					put(grammarAccess.getJNIDomainAccess().getIslStringAssignment(), "rule__JNIDomain__IslStringAssignment");
					put(grammarAccess.getJNIDomainInArrayNotationAccess().getIslStringAssignment_2(), "rule__JNIDomainInArrayNotation__IslStringAssignment_2");
					put(grammarAccess.getJNIParamDomainAccess().getIslStringAssignment(), "rule__JNIParamDomain__IslStringAssignment");
					put(grammarAccess.getJNIRelationAccess().getIslStringAssignment(), "rule__JNIRelation__IslStringAssignment");
					put(grammarAccess.getJNIFunctionAccess().getAlphaStringAssignment(), "rule__JNIFunction__AlphaStringAssignment");
					put(grammarAccess.getJNIFunctionInArrayNotationAccess().getArrayNotationAssignment_2_0(), "rule__JNIFunctionInArrayNotation__ArrayNotationAssignment_2_0");
					put(grammarAccess.getJNIFunctionInArrayNotationAccess().getArrayNotationAssignment_2_1_1(), "rule__JNIFunctionInArrayNotation__ArrayNotationAssignment_2_1_1");
					put(grammarAccess.getStandardEquationAccess().getVariableAssignment_0(), "rule__StandardEquation__VariableAssignment_0");
					put(grammarAccess.getStandardEquationAccess().getIndexNamesAssignment_1_1(), "rule__StandardEquation__IndexNamesAssignment_1_1");
					put(grammarAccess.getStandardEquationAccess().getIndexNamesAssignment_1_2_1(), "rule__StandardEquation__IndexNamesAssignment_1_2_1");
					put(grammarAccess.getStandardEquationAccess().getExprAssignment_3(), "rule__StandardEquation__ExprAssignment_3");
					put(grammarAccess.getUseEquationAccess().getInstantiationDomainAssignment_0_1(), "rule__UseEquation__InstantiationDomainAssignment_0_1");
					put(grammarAccess.getUseEquationAccess().getSubsystemDimsAssignment_0_2_1_1(), "rule__UseEquation__SubsystemDimsAssignment_0_2_1_1");
					put(grammarAccess.getUseEquationAccess().getSubsystemDimsAssignment_0_2_1_2_1(), "rule__UseEquation__SubsystemDimsAssignment_0_2_1_2_1");
					put(grammarAccess.getUseEquationAccess().getOutputExprsAssignment_2_0(), "rule__UseEquation__OutputExprsAssignment_2_0");
					put(grammarAccess.getUseEquationAccess().getOutputExprsAssignment_2_1_1(), "rule__UseEquation__OutputExprsAssignment_2_1_1");
					put(grammarAccess.getUseEquationAccess().getSystemAssignment_5(), "rule__UseEquation__SystemAssignment_5");
					put(grammarAccess.getUseEquationAccess().getCallParamsAssignment_6(), "rule__UseEquation__CallParamsAssignment_6");
					put(grammarAccess.getUseEquationAccess().getInputExprsAssignment_8_0(), "rule__UseEquation__InputExprsAssignment_8_0");
					put(grammarAccess.getUseEquationAccess().getInputExprsAssignment_8_1_1(), "rule__UseEquation__InputExprsAssignment_8_1_1");
					put(grammarAccess.getIfExpressionAccess().getCondExprAssignment_1(), "rule__IfExpression__CondExprAssignment_1");
					put(grammarAccess.getIfExpressionAccess().getThenExprAssignment_3(), "rule__IfExpression__ThenExprAssignment_3");
					put(grammarAccess.getIfExpressionAccess().getElseExprAssignment_5(), "rule__IfExpression__ElseExprAssignment_5");
					put(grammarAccess.getRestrictExpressionAccess().getDomainExprAssignment_0_0(), "rule__RestrictExpression__DomainExprAssignment_0_0");
					put(grammarAccess.getRestrictExpressionAccess().getExprAssignment_0_2(), "rule__RestrictExpression__ExprAssignment_0_2");
					put(grammarAccess.getRestrictExpressionAccess().getDomainExprAssignment_1_1(), "rule__RestrictExpression__DomainExprAssignment_1_1");
					put(grammarAccess.getRestrictExpressionAccess().getExprAssignment_1_4(), "rule__RestrictExpression__ExprAssignment_1_4");
					put(grammarAccess.getAutoRestrictExpressionAccess().getExprAssignment_2(), "rule__AutoRestrictExpression__ExprAssignment_2");
					put(grammarAccess.getCaseExpressionAccess().getNameAssignment_1(), "rule__CaseExpression__NameAssignment_1");
					put(grammarAccess.getCaseExpressionAccess().getExprsAssignment_3_0(), "rule__CaseExpression__ExprsAssignment_3_0");
					put(grammarAccess.getDependenceExpressionAccess().getFunctionAssignment_0_0(), "rule__DependenceExpression__FunctionAssignment_0_0");
					put(grammarAccess.getDependenceExpressionAccess().getExprAssignment_0_2(), "rule__DependenceExpression__ExprAssignment_0_2");
					put(grammarAccess.getDependenceExpressionAccess().getExprAssignment_1_0(), "rule__DependenceExpression__ExprAssignment_1_0");
					put(grammarAccess.getDependenceExpressionAccess().getFunctionAssignment_1_1(), "rule__DependenceExpression__FunctionAssignment_1_1");
					put(grammarAccess.getIndexExpressionAccess().getFunctionAssignment_0_1(), "rule__IndexExpression__FunctionAssignment_0_1");
					put(grammarAccess.getIndexExpressionAccess().getFunctionAssignment_1(), "rule__IndexExpression__FunctionAssignment_1");
					put(grammarAccess.getReduceExpressionAccess().getOperatorAssignment_2(), "rule__ReduceExpression__OperatorAssignment_2");
					put(grammarAccess.getReduceExpressionAccess().getProjectionAssignment_4(), "rule__ReduceExpression__ProjectionAssignment_4");
					put(grammarAccess.getReduceExpressionAccess().getBodyAssignment_6(), "rule__ReduceExpression__BodyAssignment_6");
					put(grammarAccess.getExternalReduceExpressionAccess().getExternalFunctionAssignment_2(), "rule__ExternalReduceExpression__ExternalFunctionAssignment_2");
					put(grammarAccess.getExternalReduceExpressionAccess().getProjectionAssignment_4(), "rule__ExternalReduceExpression__ProjectionAssignment_4");
					put(grammarAccess.getExternalReduceExpressionAccess().getBodyAssignment_6(), "rule__ExternalReduceExpression__BodyAssignment_6");
					put(grammarAccess.getArgReduceExpressionAccess().getOperatorAssignment_2(), "rule__ArgReduceExpression__OperatorAssignment_2");
					put(grammarAccess.getArgReduceExpressionAccess().getProjectionAssignment_4(), "rule__ArgReduceExpression__ProjectionAssignment_4");
					put(grammarAccess.getArgReduceExpressionAccess().getBodyAssignment_6(), "rule__ArgReduceExpression__BodyAssignment_6");
					put(grammarAccess.getExternalArgReduceExpressionAccess().getExternalFunctionAssignment_2(), "rule__ExternalArgReduceExpression__ExternalFunctionAssignment_2");
					put(grammarAccess.getExternalArgReduceExpressionAccess().getProjectionAssignment_4(), "rule__ExternalArgReduceExpression__ProjectionAssignment_4");
					put(grammarAccess.getExternalArgReduceExpressionAccess().getBodyAssignment_6(), "rule__ExternalArgReduceExpression__BodyAssignment_6");
					put(grammarAccess.getConvolutionExpressionAccess().getKernelDomainAssignment_2(), "rule__ConvolutionExpression__KernelDomainAssignment_2");
					put(grammarAccess.getConvolutionExpressionAccess().getKernelExpressionAssignment_4(), "rule__ConvolutionExpression__KernelExpressionAssignment_4");
					put(grammarAccess.getConvolutionExpressionAccess().getDataExpressionAssignment_6(), "rule__ConvolutionExpression__DataExpressionAssignment_6");
					put(grammarAccess.getSelectExpressionAccess().getSelectRelationAssignment_1(), "rule__SelectExpression__SelectRelationAssignment_1");
					put(grammarAccess.getSelectExpressionAccess().getExprAssignment_3(), "rule__SelectExpression__ExprAssignment_3");
					put(grammarAccess.getOrExpressionAccess().getOperatorAssignment_1_1(), "rule__OrExpression__OperatorAssignment_1_1");
					put(grammarAccess.getOrExpressionAccess().getRightAssignment_1_2(), "rule__OrExpression__RightAssignment_1_2");
					put(grammarAccess.getAndExpressionAccess().getOperatorAssignment_1_1(), "rule__AndExpression__OperatorAssignment_1_1");
					put(grammarAccess.getAndExpressionAccess().getRightAssignment_1_2(), "rule__AndExpression__RightAssignment_1_2");
					put(grammarAccess.getRelationalExpressionAccess().getOperatorAssignment_1_1(), "rule__RelationalExpression__OperatorAssignment_1_1");
					put(grammarAccess.getRelationalExpressionAccess().getRightAssignment_1_2(), "rule__RelationalExpression__RightAssignment_1_2");
					put(grammarAccess.getAdditiveExpressionAccess().getOperatorAssignment_1_1(), "rule__AdditiveExpression__OperatorAssignment_1_1");
					put(grammarAccess.getAdditiveExpressionAccess().getRightAssignment_1_2(), "rule__AdditiveExpression__RightAssignment_1_2");
					put(grammarAccess.getMultiplicativeExpressionAccess().getOperatorAssignment_1_1(), "rule__MultiplicativeExpression__OperatorAssignment_1_1");
					put(grammarAccess.getMultiplicativeExpressionAccess().getRightAssignment_1_2(), "rule__MultiplicativeExpression__RightAssignment_1_2");
					put(grammarAccess.getMinMaxExpressionAccess().getOperatorAssignment_1_1(), "rule__MinMaxExpression__OperatorAssignment_1_1");
					put(grammarAccess.getMinMaxExpressionAccess().getRightAssignment_1_2(), "rule__MinMaxExpression__RightAssignment_1_2");
					put(grammarAccess.getMultiArgExpressionAccess().getOperatorAssignment_0(), "rule__MultiArgExpression__OperatorAssignment_0");
					put(grammarAccess.getMultiArgExpressionAccess().getExprsAssignment_2(), "rule__MultiArgExpression__ExprsAssignment_2");
					put(grammarAccess.getMultiArgExpressionAccess().getExprsAssignment_3_1(), "rule__MultiArgExpression__ExprsAssignment_3_1");
					put(grammarAccess.getExternalMultiArgExpressionAccess().getExternalFunctionAssignment_0(), "rule__ExternalMultiArgExpression__ExternalFunctionAssignment_0");
					put(grammarAccess.getExternalMultiArgExpressionAccess().getExprsAssignment_2(), "rule__ExternalMultiArgExpression__ExprsAssignment_2");
					put(grammarAccess.getExternalMultiArgExpressionAccess().getExprsAssignment_3_1(), "rule__ExternalMultiArgExpression__ExprsAssignment_3_1");
					put(grammarAccess.getUnaryExpressionAccess().getOperatorAssignment_0(), "rule__UnaryExpression__OperatorAssignment_0");
					put(grammarAccess.getUnaryExpressionAccess().getExprAssignment_1(), "rule__UnaryExpression__ExprAssignment_1");
					put(grammarAccess.getVariableExpressionAccess().getVariableAssignment(), "rule__VariableExpression__VariableAssignment");
					put(grammarAccess.getBooleanExpressionAccess().getValueAssignment(), "rule__BooleanExpression__ValueAssignment");
					put(grammarAccess.getIntegerExpressionAccess().getValueAssignment(), "rule__IntegerExpression__ValueAssignment");
					put(grammarAccess.getRealExpressionAccess().getValueAssignment(), "rule__RealExpression__ValueAssignment");
					put(grammarAccess.getPolyhedralObjectAccess().getNameAssignment_0(), "rule__PolyhedralObject__NameAssignment_0");
					put(grammarAccess.getPolyhedralObjectAccess().getExprAssignment_2(), "rule__PolyhedralObject__ExprAssignment_2");
					put(grammarAccess.getCalculatorExpressionAccess().getOperatorAssignment_1_1(), "rule__CalculatorExpression__OperatorAssignment_1_1");
					put(grammarAccess.getCalculatorExpressionAccess().getRightAssignment_1_2(), "rule__CalculatorExpression__RightAssignment_1_2");
					put(grammarAccess.getUnaryCalculatorExpressionAccess().getOperatorAssignment_0(), "rule__UnaryCalculatorExpression__OperatorAssignment_0");
					put(grammarAccess.getUnaryCalculatorExpressionAccess().getExprAssignment_1(), "rule__UnaryCalculatorExpression__ExprAssignment_1");
					put(grammarAccess.getVariableDomainAccess().getVariableAssignment_1(), "rule__VariableDomain__VariableAssignment_1");
					put(grammarAccess.getRectangularDomainAccess().getUpperBoundsAssignment_1(), "rule__RectangularDomain__UpperBoundsAssignment_1");
					put(grammarAccess.getRectangularDomainAccess().getUpperBoundsAssignment_2_1(), "rule__RectangularDomain__UpperBoundsAssignment_2_1");
					put(grammarAccess.getRectangularDomainAccess().getIndexNamesAssignment_4_2(), "rule__RectangularDomain__IndexNamesAssignment_4_2");
					put(grammarAccess.getRectangularDomainAccess().getIndexNamesAssignment_4_3_1(), "rule__RectangularDomain__IndexNamesAssignment_4_3_1");
					put(grammarAccess.getDefinedObjectAccess().getObjectAssignment(), "rule__DefinedObject__ObjectAssignment");
				}
			};
		}
		return nameMappings.get(element);
	}
			
	@Override
	protected String[] getInitialHiddenTokens() {
		return new String[] { "RULE_WS", "RULE_ML_COMMENT", "RULE_SL_COMMENT" };
	}

	public AlphaGrammarAccess getGrammarAccess() {
		return this.grammarAccess;
	}

	public void setGrammarAccess(AlphaGrammarAccess grammarAccess) {
		this.grammarAccess = grammarAccess;
	}
}