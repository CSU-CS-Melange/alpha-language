/*
 * generated by Xtext 2.22.0
 */
package alpha.model.serializer;

import alpha.model.services.AlphaGrammarAccess;
import com.google.inject.Inject;
import java.util.List;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.IGrammarAccess;
import org.eclipse.xtext.RuleCall;
import org.eclipse.xtext.nodemodel.INode;
import org.eclipse.xtext.serializer.analysis.GrammarAlias.AbstractElementAlias;
import org.eclipse.xtext.serializer.analysis.GrammarAlias.GroupAlias;
import org.eclipse.xtext.serializer.analysis.GrammarAlias.TokenAlias;
import org.eclipse.xtext.serializer.analysis.ISyntacticSequencerPDAProvider.ISynNavigable;
import org.eclipse.xtext.serializer.analysis.ISyntacticSequencerPDAProvider.ISynTransition;
import org.eclipse.xtext.serializer.sequencer.AbstractSyntacticSequencer;

@SuppressWarnings("all")
public class AlphaSyntacticSequencer extends AbstractSyntacticSequencer {

	protected AlphaGrammarAccess grammarAccess;
	protected AbstractElementAlias match_AlphaFunctionTerminalExpression_LeftParenthesisKeyword_2_0_a;
	protected AbstractElementAlias match_AlphaFunctionTerminalExpression_LeftParenthesisKeyword_2_0_p;
	protected AbstractElementAlias match_AlphaTerminalExpression_AlphaUnaryTerminalExpression___LeftParenthesisKeyword_0_0_LeftParenthesisKeyword_0_0_a__q;
	protected AbstractElementAlias match_AlphaTerminalExpression_LeftParenthesisKeyword_0_0_a;
	protected AbstractElementAlias match_AlphaTerminalExpression_LeftParenthesisKeyword_0_0_p;
	protected AbstractElementAlias match_CalculatorExpressionTerminal_LeftParenthesisKeyword_6_0_a;
	protected AbstractElementAlias match_CalculatorExpressionTerminal_LeftParenthesisKeyword_6_0_p;
	protected AbstractElementAlias match_FuzzyVariable_SemicolonKeyword_6_q;
	protected AbstractElementAlias match_SystemBody_ElseKeyword_1_1_q;
	protected AbstractElementAlias match_UseEquation_WithKeyword_0_1_0_q;
	protected AbstractElementAlias match_UseEquation___WithKeyword_0_1_0_q_ColonKeyword_0_2__q;
	protected AbstractElementAlias match_Variable_SemicolonKeyword_3_q;
	
	@Inject
	protected void init(IGrammarAccess access) {
		grammarAccess = (AlphaGrammarAccess) access;
		match_AlphaFunctionTerminalExpression_LeftParenthesisKeyword_2_0_a = new TokenAlias(true, true, grammarAccess.getAlphaFunctionTerminalExpressionAccess().getLeftParenthesisKeyword_2_0());
		match_AlphaFunctionTerminalExpression_LeftParenthesisKeyword_2_0_p = new TokenAlias(true, false, grammarAccess.getAlphaFunctionTerminalExpressionAccess().getLeftParenthesisKeyword_2_0());
		match_AlphaTerminalExpression_AlphaUnaryTerminalExpression___LeftParenthesisKeyword_0_0_LeftParenthesisKeyword_0_0_a__q = new GroupAlias(false, true, new TokenAlias(false, false, grammarAccess.getAlphaUnaryTerminalExpressionAccess().getLeftParenthesisKeyword_0_0()), new TokenAlias(true, true, grammarAccess.getAlphaTerminalExpressionAccess().getLeftParenthesisKeyword_0_0()));
		match_AlphaTerminalExpression_LeftParenthesisKeyword_0_0_a = new TokenAlias(true, true, grammarAccess.getAlphaTerminalExpressionAccess().getLeftParenthesisKeyword_0_0());
		match_AlphaTerminalExpression_LeftParenthesisKeyword_0_0_p = new TokenAlias(true, false, grammarAccess.getAlphaTerminalExpressionAccess().getLeftParenthesisKeyword_0_0());
		match_CalculatorExpressionTerminal_LeftParenthesisKeyword_6_0_a = new TokenAlias(true, true, grammarAccess.getCalculatorExpressionTerminalAccess().getLeftParenthesisKeyword_6_0());
		match_CalculatorExpressionTerminal_LeftParenthesisKeyword_6_0_p = new TokenAlias(true, false, grammarAccess.getCalculatorExpressionTerminalAccess().getLeftParenthesisKeyword_6_0());
		match_FuzzyVariable_SemicolonKeyword_6_q = new TokenAlias(false, true, grammarAccess.getFuzzyVariableAccess().getSemicolonKeyword_6());
		match_SystemBody_ElseKeyword_1_1_q = new TokenAlias(false, true, grammarAccess.getSystemBodyAccess().getElseKeyword_1_1());
		match_UseEquation_WithKeyword_0_1_0_q = new TokenAlias(false, true, grammarAccess.getUseEquationAccess().getWithKeyword_0_1_0());
		match_UseEquation___WithKeyword_0_1_0_q_ColonKeyword_0_2__q = new GroupAlias(false, true, new TokenAlias(false, true, grammarAccess.getUseEquationAccess().getWithKeyword_0_1_0()), new TokenAlias(false, false, grammarAccess.getUseEquationAccess().getColonKeyword_0_2()));
		match_Variable_SemicolonKeyword_3_q = new TokenAlias(false, true, grammarAccess.getVariableAccess().getSemicolonKeyword_3());
	}
	
	@Override
	protected String getUnassignedRuleCallToken(EObject semanticObject, RuleCall ruleCall, INode node) {
		return "";
	}
	
	
	@Override
	protected void emitUnassignedTokens(EObject semanticObject, ISynTransition transition, INode fromNode, INode toNode) {
		if (transition.getAmbiguousSyntaxes().isEmpty()) return;
		List<INode> transitionNodes = collectNodes(fromNode, toNode);
		for (AbstractElementAlias syntax : transition.getAmbiguousSyntaxes()) {
			List<INode> syntaxNodes = getNodesFor(transitionNodes, syntax);
			if (match_AlphaFunctionTerminalExpression_LeftParenthesisKeyword_2_0_a.equals(syntax))
				emit_AlphaFunctionTerminalExpression_LeftParenthesisKeyword_2_0_a(semanticObject, getLastNavigableState(), syntaxNodes);
			else if (match_AlphaFunctionTerminalExpression_LeftParenthesisKeyword_2_0_p.equals(syntax))
				emit_AlphaFunctionTerminalExpression_LeftParenthesisKeyword_2_0_p(semanticObject, getLastNavigableState(), syntaxNodes);
			else if (match_AlphaTerminalExpression_AlphaUnaryTerminalExpression___LeftParenthesisKeyword_0_0_LeftParenthesisKeyword_0_0_a__q.equals(syntax))
				emit_AlphaTerminalExpression_AlphaUnaryTerminalExpression___LeftParenthesisKeyword_0_0_LeftParenthesisKeyword_0_0_a__q(semanticObject, getLastNavigableState(), syntaxNodes);
			else if (match_AlphaTerminalExpression_LeftParenthesisKeyword_0_0_a.equals(syntax))
				emit_AlphaTerminalExpression_LeftParenthesisKeyword_0_0_a(semanticObject, getLastNavigableState(), syntaxNodes);
			else if (match_AlphaTerminalExpression_LeftParenthesisKeyword_0_0_p.equals(syntax))
				emit_AlphaTerminalExpression_LeftParenthesisKeyword_0_0_p(semanticObject, getLastNavigableState(), syntaxNodes);
			else if (match_CalculatorExpressionTerminal_LeftParenthesisKeyword_6_0_a.equals(syntax))
				emit_CalculatorExpressionTerminal_LeftParenthesisKeyword_6_0_a(semanticObject, getLastNavigableState(), syntaxNodes);
			else if (match_CalculatorExpressionTerminal_LeftParenthesisKeyword_6_0_p.equals(syntax))
				emit_CalculatorExpressionTerminal_LeftParenthesisKeyword_6_0_p(semanticObject, getLastNavigableState(), syntaxNodes);
			else if (match_FuzzyVariable_SemicolonKeyword_6_q.equals(syntax))
				emit_FuzzyVariable_SemicolonKeyword_6_q(semanticObject, getLastNavigableState(), syntaxNodes);
			else if (match_SystemBody_ElseKeyword_1_1_q.equals(syntax))
				emit_SystemBody_ElseKeyword_1_1_q(semanticObject, getLastNavigableState(), syntaxNodes);
			else if (match_UseEquation_WithKeyword_0_1_0_q.equals(syntax))
				emit_UseEquation_WithKeyword_0_1_0_q(semanticObject, getLastNavigableState(), syntaxNodes);
			else if (match_UseEquation___WithKeyword_0_1_0_q_ColonKeyword_0_2__q.equals(syntax))
				emit_UseEquation___WithKeyword_0_1_0_q_ColonKeyword_0_2__q(semanticObject, getLastNavigableState(), syntaxNodes);
			else if (match_Variable_SemicolonKeyword_3_q.equals(syntax))
				emit_Variable_SemicolonKeyword_3_q(semanticObject, getLastNavigableState(), syntaxNodes);
			else acceptNodes(getLastNavigableState(), syntaxNodes);
		}
	}

	/**
	 * Ambiguous syntax:
	 *     '('*
	 *
	 * This ambiguous syntax occurs at:
	 *     (rule start) (ambiguity) 'floor' '(' expr=AlphaFunctionExpression
	 *     (rule start) (ambiguity) value=AISLExpressionLiteral
	 *     (rule start) (ambiguity) {AlphaFunctionBinaryExpression.left=}
	 */
	protected void emit_AlphaFunctionTerminalExpression_LeftParenthesisKeyword_2_0_a(EObject semanticObject, ISynNavigable transition, List<INode> nodes) {
		acceptNodes(transition, nodes);
	}
	
	/**
	 * Ambiguous syntax:
	 *     '('+
	 *
	 * This ambiguous syntax occurs at:
	 *     (rule start) (ambiguity) {AlphaFunctionBinaryExpression.left=}
	 */
	protected void emit_AlphaFunctionTerminalExpression_LeftParenthesisKeyword_2_0_p(EObject semanticObject, ISynNavigable transition, List<INode> nodes) {
		acceptNodes(transition, nodes);
	}
	
	/**
	 * Ambiguous syntax:
	 *     ('(' '('*)?
	 *
	 * This ambiguous syntax occurs at:
	 *     (rule start) (ambiguity) 'argreduce' '(' externalFunction=[ExternalFunction|QualifiedName]
	 *     (rule start) (ambiguity) 'argreduce' '(' operator=AREDUCTION_OP
	 *     (rule start) (ambiguity) 'case' '{' exprs+=AlphaExpression
	 *     (rule start) (ambiguity) 'case' name=ID
	 *     (rule start) (ambiguity) 'conv' '(' kernelDomainExpr=CalculatorExpression
	 *     (rule start) (ambiguity) 'reduce' '(' externalFunction=[ExternalFunction|QualifiedName]
	 *     (rule start) (ambiguity) 'reduce' '(' operator=AREDUCTION_OP
	 *     (rule start) (ambiguity) 'select' relationExpr=CalculatorExpression
	 *     (rule start) (ambiguity) 'val' functionExpr=JNIFunction
	 *     (rule start) (ambiguity) 'val' functionExpr=JNIFunctionInArrayNotation
	 *     (rule start) (ambiguity) 'val' fuzzyFunction=FuzzyFunction
	 *     (rule start) (ambiguity) 'val' polynomialExpr=JNIPolynomial
	 *     (rule start) (ambiguity) 'val' polynomialExpr=JNIPolynomialInArrayNotation
	 *     (rule start) (ambiguity) externalFunction=[ExternalFunction|QualifiedName]
	 *     (rule start) (ambiguity) fuzzyFunction=FuzzyFunctionInArrayNotation
	 *     (rule start) (ambiguity) operator=AREDUCTION_OP
	 *     (rule start) (ambiguity) variable=[Variable|ID]
	 */
	protected void emit_AlphaTerminalExpression_AlphaUnaryTerminalExpression___LeftParenthesisKeyword_0_0_LeftParenthesisKeyword_0_0_a__q(EObject semanticObject, ISynNavigable transition, List<INode> nodes) {
		acceptNodes(transition, nodes);
	}
	
	/**
	 * Ambiguous syntax:
	 *     '('*
	 *
	 * This ambiguous syntax occurs at:
	 *     (rule start) '(' (ambiguity) 'auto' ':' expr=AlphaExpression
	 *     (rule start) '(' (ambiguity) 'if' condExpr=AlphaExpression
	 *     (rule start) '(' (ambiguity) '{' domainExpr=CalculatorExpression
	 *     (rule start) '(' (ambiguity) domainExpr=JNIDomain
	 *     (rule start) '(' (ambiguity) domainExpr=JNIDomainInArrayNotation
	 *     (rule start) '(' (ambiguity) expr=ConstantExpression
	 *     (rule start) '(' (ambiguity) expr=VariableExpression
	 *     (rule start) '(' (ambiguity) functionExpr=JNIFunction
	 *     (rule start) '(' (ambiguity) fuzzyFunction=FuzzyFunction
	 *     (rule start) '(' (ambiguity) operator=AUnaryOP
	 *     (rule start) '(' (ambiguity) value=BOOLEAN
	 *     (rule start) '(' (ambiguity) value=FLOAT
	 *     (rule start) '(' (ambiguity) value=SINT
	 *     (rule start) '(' (ambiguity) {BinaryExpression.left=}
	 *     (rule start) (ambiguity) 'argreduce' '(' externalFunction=[ExternalFunction|QualifiedName]
	 *     (rule start) (ambiguity) 'argreduce' '(' operator=AREDUCTION_OP
	 *     (rule start) (ambiguity) 'auto' ':' expr=AlphaExpression
	 *     (rule start) (ambiguity) 'case' '{' exprs+=AlphaExpression
	 *     (rule start) (ambiguity) 'case' name=ID
	 *     (rule start) (ambiguity) 'conv' '(' kernelDomainExpr=CalculatorExpression
	 *     (rule start) (ambiguity) 'if' condExpr=AlphaExpression
	 *     (rule start) (ambiguity) 'reduce' '(' externalFunction=[ExternalFunction|QualifiedName]
	 *     (rule start) (ambiguity) 'reduce' '(' operator=AREDUCTION_OP
	 *     (rule start) (ambiguity) 'select' relationExpr=CalculatorExpression
	 *     (rule start) (ambiguity) 'val' functionExpr=JNIFunction
	 *     (rule start) (ambiguity) 'val' functionExpr=JNIFunctionInArrayNotation
	 *     (rule start) (ambiguity) 'val' fuzzyFunction=FuzzyFunction
	 *     (rule start) (ambiguity) 'val' polynomialExpr=JNIPolynomial
	 *     (rule start) (ambiguity) 'val' polynomialExpr=JNIPolynomialInArrayNotation
	 *     (rule start) (ambiguity) '{' domainExpr=CalculatorExpression
	 *     (rule start) (ambiguity) domainExpr=JNIDomain
	 *     (rule start) (ambiguity) domainExpr=JNIDomainInArrayNotation
	 *     (rule start) (ambiguity) expr=ConstantExpression
	 *     (rule start) (ambiguity) expr=VariableExpression
	 *     (rule start) (ambiguity) externalFunction=[ExternalFunction|QualifiedName]
	 *     (rule start) (ambiguity) functionExpr=JNIFunction
	 *     (rule start) (ambiguity) fuzzyFunction=FuzzyFunction
	 *     (rule start) (ambiguity) fuzzyFunction=FuzzyFunctionInArrayNotation
	 *     (rule start) (ambiguity) operator=AREDUCTION_OP
	 *     (rule start) (ambiguity) operator=AUnaryOP
	 *     (rule start) (ambiguity) value=BOOLEAN
	 *     (rule start) (ambiguity) value=FLOAT
	 *     (rule start) (ambiguity) value=SINT
	 *     (rule start) (ambiguity) variable=[Variable|ID]
	 *     (rule start) (ambiguity) {BinaryExpression.left=}
	 */
	protected void emit_AlphaTerminalExpression_LeftParenthesisKeyword_0_0_a(EObject semanticObject, ISynNavigable transition, List<INode> nodes) {
		acceptNodes(transition, nodes);
	}
	
	/**
	 * Ambiguous syntax:
	 *     '('+
	 *
	 * This ambiguous syntax occurs at:
	 *     (rule start) (ambiguity) 'auto' ':' expr=AlphaExpression
	 *     (rule start) (ambiguity) 'if' condExpr=AlphaExpression
	 *     (rule start) (ambiguity) '{' domainExpr=CalculatorExpression
	 *     (rule start) (ambiguity) domainExpr=JNIDomain
	 *     (rule start) (ambiguity) domainExpr=JNIDomainInArrayNotation
	 *     (rule start) (ambiguity) operator=AUnaryOP
	 *     (rule start) (ambiguity) {BinaryExpression.left=}
	 */
	protected void emit_AlphaTerminalExpression_LeftParenthesisKeyword_0_0_p(EObject semanticObject, ISynNavigable transition, List<INode> nodes) {
		acceptNodes(transition, nodes);
	}
	
	/**
	 * Ambiguous syntax:
	 *     '('*
	 *
	 * This ambiguous syntax occurs at:
	 *     (rule start) (ambiguity) '[' lowerBounds+=AISLExpression
	 *     (rule start) (ambiguity) '[' upperBounds+=AISLExpression
	 *     (rule start) (ambiguity) '{' variable=[Variable|ID]
	 *     (rule start) (ambiguity) alphaFunction=AlphaFunction
	 *     (rule start) (ambiguity) islString=AISLRelation
	 *     (rule start) (ambiguity) islString=AISLSet
	 *     (rule start) (ambiguity) object=[PolyhedralObject|ID]
	 *     (rule start) (ambiguity) operator=AUnaryCalcOp
	 *     (rule start) (ambiguity) {BinaryCalculatorExpression.left=}
	 */
	protected void emit_CalculatorExpressionTerminal_LeftParenthesisKeyword_6_0_a(EObject semanticObject, ISynNavigable transition, List<INode> nodes) {
		acceptNodes(transition, nodes);
	}
	
	/**
	 * Ambiguous syntax:
	 *     '('+
	 *
	 * This ambiguous syntax occurs at:
	 *     (rule start) (ambiguity) operator=AUnaryCalcOp
	 *     (rule start) (ambiguity) {BinaryCalculatorExpression.left=}
	 */
	protected void emit_CalculatorExpressionTerminal_LeftParenthesisKeyword_6_0_p(EObject semanticObject, ISynNavigable transition, List<INode> nodes) {
		acceptNodes(transition, nodes);
	}
	
	/**
	 * Ambiguous syntax:
	 *     ';'?
	 *
	 * This ambiguous syntax occurs at:
	 *     rangeExpr=CalculatorExpression (ambiguity) (rule end)
	 */
	protected void emit_FuzzyVariable_SemicolonKeyword_6_q(EObject semanticObject, ISynNavigable transition, List<INode> nodes) {
		acceptNodes(transition, nodes);
	}
	
	/**
	 * Ambiguous syntax:
	 *     'else'?
	 *
	 * This ambiguous syntax occurs at:
	 *     (rule start) (ambiguity) 'let' (rule start)
	 *     (rule start) (ambiguity) 'let' equations+=Equation
	 */
	protected void emit_SystemBody_ElseKeyword_1_1_q(EObject semanticObject, ISynNavigable transition, List<INode> nodes) {
		acceptNodes(transition, nodes);
	}
	
	/**
	 * Ambiguous syntax:
	 *     'with'?
	 *
	 * This ambiguous syntax occurs at:
	 *     instantiationDomainExpr=CalculatorExpression (ambiguity) ':' '(' ')' '=' system=[AlphaSystem|QualifiedName]
	 *     instantiationDomainExpr=CalculatorExpression (ambiguity) ':' '(' outputExprs+=AlphaExpression
	 */
	protected void emit_UseEquation_WithKeyword_0_1_0_q(EObject semanticObject, ISynNavigable transition, List<INode> nodes) {
		acceptNodes(transition, nodes);
	}
	
	/**
	 * Ambiguous syntax:
	 *     ('with'? ':')?
	 *
	 * This ambiguous syntax occurs at:
	 *     (rule start) (ambiguity) '(' ')' '=' system=[AlphaSystem|QualifiedName]
	 *     (rule start) (ambiguity) '(' outputExprs+=AlphaExpression
	 */
	protected void emit_UseEquation___WithKeyword_0_1_0_q_ColonKeyword_0_2__q(EObject semanticObject, ISynNavigable transition, List<INode> nodes) {
		acceptNodes(transition, nodes);
	}
	
	/**
	 * Ambiguous syntax:
	 *     ';'?
	 *
	 * This ambiguous syntax occurs at:
	 *     domainExpr=CalculatorExpression (ambiguity) (rule end)
	 */
	protected void emit_Variable_SemicolonKeyword_3_q(EObject semanticObject, ISynNavigable transition, List<INode> nodes) {
		acceptNodes(transition, nodes);
	}
	
}
