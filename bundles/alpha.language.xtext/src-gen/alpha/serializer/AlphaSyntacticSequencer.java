/*
 * generated by Xtext 2.12.0
 */
package alpha.serializer;

import alpha.services.AlphaGrammarAccess;
import com.google.inject.Inject;
import java.util.List;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.IGrammarAccess;
import org.eclipse.xtext.RuleCall;
import org.eclipse.xtext.nodemodel.INode;
import org.eclipse.xtext.serializer.analysis.GrammarAlias.AbstractElementAlias;
import org.eclipse.xtext.serializer.analysis.GrammarAlias.TokenAlias;
import org.eclipse.xtext.serializer.analysis.ISyntacticSequencerPDAProvider.ISynNavigable;
import org.eclipse.xtext.serializer.analysis.ISyntacticSequencerPDAProvider.ISynTransition;
import org.eclipse.xtext.serializer.sequencer.AbstractSyntacticSequencer;

@SuppressWarnings("all")
public class AlphaSyntacticSequencer extends AbstractSyntacticSequencer {

	protected AlphaGrammarAccess grammarAccess;
	protected AbstractElementAlias match_AAffineSystem_FuzzyKeyword_6_0_q;
	protected AbstractElementAlias match_AAffineSystem_InputsKeyword_3_0_q;
	protected AbstractElementAlias match_AAffineSystem_LetKeyword_7_0_q;
	protected AbstractElementAlias match_AAffineSystem_LocalsKeyword_5_0_q;
	protected AbstractElementAlias match_AAffineSystem_OutputsKeyword_4_0_q;
	protected AbstractElementAlias match_AParanthesizedAlphabetsExpression_LeftParenthesisKeyword_0_a;
	protected AbstractElementAlias match_AParanthesizedAlphabetsExpression_LeftParenthesisKeyword_0_p;
	
	@Inject
	protected void init(IGrammarAccess access) {
		grammarAccess = (AlphaGrammarAccess) access;
		match_AAffineSystem_FuzzyKeyword_6_0_q = new TokenAlias(false, true, grammarAccess.getAAffineSystemAccess().getFuzzyKeyword_6_0());
		match_AAffineSystem_InputsKeyword_3_0_q = new TokenAlias(false, true, grammarAccess.getAAffineSystemAccess().getInputsKeyword_3_0());
		match_AAffineSystem_LetKeyword_7_0_q = new TokenAlias(false, true, grammarAccess.getAAffineSystemAccess().getLetKeyword_7_0());
		match_AAffineSystem_LocalsKeyword_5_0_q = new TokenAlias(false, true, grammarAccess.getAAffineSystemAccess().getLocalsKeyword_5_0());
		match_AAffineSystem_OutputsKeyword_4_0_q = new TokenAlias(false, true, grammarAccess.getAAffineSystemAccess().getOutputsKeyword_4_0());
		match_AParanthesizedAlphabetsExpression_LeftParenthesisKeyword_0_a = new TokenAlias(true, true, grammarAccess.getAParanthesizedAlphabetsExpressionAccess().getLeftParenthesisKeyword_0());
		match_AParanthesizedAlphabetsExpression_LeftParenthesisKeyword_0_p = new TokenAlias(true, false, grammarAccess.getAParanthesizedAlphabetsExpressionAccess().getLeftParenthesisKeyword_0());
	}
	
	@Override
	protected String getUnassignedRuleCallToken(EObject semanticObject, RuleCall ruleCall, INode node) {
		if (ruleCall.getRule() == grammarAccess.getOP_EQRule())
			return getOP_EQToken(semanticObject, ruleCall, node);
		return "";
	}
	
	/**
	 * terminal OP_EQ : '=';
	 */
	protected String getOP_EQToken(EObject semanticObject, RuleCall ruleCall, INode node) {
		if (node != null)
			return getTokenText(node);
		return "=";
	}
	
	@Override
	protected void emitUnassignedTokens(EObject semanticObject, ISynTransition transition, INode fromNode, INode toNode) {
		if (transition.getAmbiguousSyntaxes().isEmpty()) return;
		List<INode> transitionNodes = collectNodes(fromNode, toNode);
		for (AbstractElementAlias syntax : transition.getAmbiguousSyntaxes()) {
			List<INode> syntaxNodes = getNodesFor(transitionNodes, syntax);
			if (match_AAffineSystem_FuzzyKeyword_6_0_q.equals(syntax))
				emit_AAffineSystem_FuzzyKeyword_6_0_q(semanticObject, getLastNavigableState(), syntaxNodes);
			else if (match_AAffineSystem_InputsKeyword_3_0_q.equals(syntax))
				emit_AAffineSystem_InputsKeyword_3_0_q(semanticObject, getLastNavigableState(), syntaxNodes);
			else if (match_AAffineSystem_LetKeyword_7_0_q.equals(syntax))
				emit_AAffineSystem_LetKeyword_7_0_q(semanticObject, getLastNavigableState(), syntaxNodes);
			else if (match_AAffineSystem_LocalsKeyword_5_0_q.equals(syntax))
				emit_AAffineSystem_LocalsKeyword_5_0_q(semanticObject, getLastNavigableState(), syntaxNodes);
			else if (match_AAffineSystem_OutputsKeyword_4_0_q.equals(syntax))
				emit_AAffineSystem_OutputsKeyword_4_0_q(semanticObject, getLastNavigableState(), syntaxNodes);
			else if (match_AParanthesizedAlphabetsExpression_LeftParenthesisKeyword_0_a.equals(syntax))
				emit_AParanthesizedAlphabetsExpression_LeftParenthesisKeyword_0_a(semanticObject, getLastNavigableState(), syntaxNodes);
			else if (match_AParanthesizedAlphabetsExpression_LeftParenthesisKeyword_0_p.equals(syntax))
				emit_AParanthesizedAlphabetsExpression_LeftParenthesisKeyword_0_p(semanticObject, getLastNavigableState(), syntaxNodes);
			else acceptNodes(getLastNavigableState(), syntaxNodes);
		}
	}

	/**
	 * Ambiguous syntax:
	 *     'fuzzy'?
	 *
	 * This ambiguous syntax occurs at:
	 *     inputDeclarations+=AVariableDeclaration 'outputs'? 'locals'? (ambiguity) 'let' equations+=AEquation
	 *     inputDeclarations+=AVariableDeclaration 'outputs'? 'locals'? (ambiguity) 'let' useEquations+=AUseEquation
	 *     inputDeclarations+=AVariableDeclaration 'outputs'? 'locals'? (ambiguity) 'let'? '.' (rule end)
	 *     localvarDeclarations+=AVariableDeclaration (ambiguity) 'let' equations+=AEquation
	 *     localvarDeclarations+=AVariableDeclaration (ambiguity) 'let' useEquations+=AUseEquation
	 *     localvarDeclarations+=AVariableDeclaration (ambiguity) 'let'? '.' (rule end)
	 *     outputDeclarations+=AVariableDeclaration 'locals'? (ambiguity) 'let' equations+=AEquation
	 *     outputDeclarations+=AVariableDeclaration 'locals'? (ambiguity) 'let' useEquations+=AUseEquation
	 *     outputDeclarations+=AVariableDeclaration 'locals'? (ambiguity) 'let'? '.' (rule end)
	 *     parameters=AParamDomain 'inputs'? 'outputs'? 'locals'? (ambiguity) 'let' equations+=AEquation
	 *     parameters=AParamDomain 'inputs'? 'outputs'? 'locals'? (ambiguity) 'let' useEquations+=AUseEquation
	 *     parameters=AParamDomain 'inputs'? 'outputs'? 'locals'? (ambiguity) 'let'? '.' (rule end)
	 */
	protected void emit_AAffineSystem_FuzzyKeyword_6_0_q(EObject semanticObject, ISynNavigable transition, List<INode> nodes) {
		acceptNodes(transition, nodes);
	}
	
	/**
	 * Ambiguous syntax:
	 *     'inputs'?
	 *
	 * This ambiguous syntax occurs at:
	 *     parameters=AParamDomain (ambiguity) 'outputs' outputDeclarations+=AVariableDeclaration
	 *     parameters=AParamDomain (ambiguity) 'outputs'? 'locals' localvarDeclarations+=AVariableDeclaration
	 *     parameters=AParamDomain (ambiguity) 'outputs'? 'locals'? 'fuzzy' fuzzyVariables+=AFuzzyVariableDeclaration
	 *     parameters=AParamDomain (ambiguity) 'outputs'? 'locals'? 'fuzzy'? 'let' equations+=AEquation
	 *     parameters=AParamDomain (ambiguity) 'outputs'? 'locals'? 'fuzzy'? 'let' useEquations+=AUseEquation
	 *     parameters=AParamDomain (ambiguity) 'outputs'? 'locals'? 'fuzzy'? 'let'? '.' (rule end)
	 */
	protected void emit_AAffineSystem_InputsKeyword_3_0_q(EObject semanticObject, ISynNavigable transition, List<INode> nodes) {
		acceptNodes(transition, nodes);
	}
	
	/**
	 * Ambiguous syntax:
	 *     'let'?
	 *
	 * This ambiguous syntax occurs at:
	 *     fuzzyVariables+=AFuzzyVariableDeclaration (ambiguity) '.' (rule end)
	 *     inputDeclarations+=AVariableDeclaration 'outputs'? 'locals'? 'fuzzy'? (ambiguity) '.' (rule end)
	 *     localvarDeclarations+=AVariableDeclaration 'fuzzy'? (ambiguity) '.' (rule end)
	 *     outputDeclarations+=AVariableDeclaration 'locals'? 'fuzzy'? (ambiguity) '.' (rule end)
	 *     parameters=AParamDomain 'inputs'? 'outputs'? 'locals'? 'fuzzy'? (ambiguity) '.' (rule end)
	 */
	protected void emit_AAffineSystem_LetKeyword_7_0_q(EObject semanticObject, ISynNavigable transition, List<INode> nodes) {
		acceptNodes(transition, nodes);
	}
	
	/**
	 * Ambiguous syntax:
	 *     'locals'?
	 *
	 * This ambiguous syntax occurs at:
	 *     inputDeclarations+=AVariableDeclaration 'outputs'? (ambiguity) 'fuzzy' fuzzyVariables+=AFuzzyVariableDeclaration
	 *     inputDeclarations+=AVariableDeclaration 'outputs'? (ambiguity) 'fuzzy'? 'let' equations+=AEquation
	 *     inputDeclarations+=AVariableDeclaration 'outputs'? (ambiguity) 'fuzzy'? 'let' useEquations+=AUseEquation
	 *     inputDeclarations+=AVariableDeclaration 'outputs'? (ambiguity) 'fuzzy'? 'let'? '.' (rule end)
	 *     outputDeclarations+=AVariableDeclaration (ambiguity) 'fuzzy' fuzzyVariables+=AFuzzyVariableDeclaration
	 *     outputDeclarations+=AVariableDeclaration (ambiguity) 'fuzzy'? 'let' equations+=AEquation
	 *     outputDeclarations+=AVariableDeclaration (ambiguity) 'fuzzy'? 'let' useEquations+=AUseEquation
	 *     outputDeclarations+=AVariableDeclaration (ambiguity) 'fuzzy'? 'let'? '.' (rule end)
	 *     parameters=AParamDomain 'inputs'? 'outputs'? (ambiguity) 'fuzzy' fuzzyVariables+=AFuzzyVariableDeclaration
	 *     parameters=AParamDomain 'inputs'? 'outputs'? (ambiguity) 'fuzzy'? 'let' equations+=AEquation
	 *     parameters=AParamDomain 'inputs'? 'outputs'? (ambiguity) 'fuzzy'? 'let' useEquations+=AUseEquation
	 *     parameters=AParamDomain 'inputs'? 'outputs'? (ambiguity) 'fuzzy'? 'let'? '.' (rule end)
	 */
	protected void emit_AAffineSystem_LocalsKeyword_5_0_q(EObject semanticObject, ISynNavigable transition, List<INode> nodes) {
		acceptNodes(transition, nodes);
	}
	
	/**
	 * Ambiguous syntax:
	 *     'outputs'?
	 *
	 * This ambiguous syntax occurs at:
	 *     inputDeclarations+=AVariableDeclaration (ambiguity) 'locals' localvarDeclarations+=AVariableDeclaration
	 *     inputDeclarations+=AVariableDeclaration (ambiguity) 'locals'? 'fuzzy' fuzzyVariables+=AFuzzyVariableDeclaration
	 *     inputDeclarations+=AVariableDeclaration (ambiguity) 'locals'? 'fuzzy'? 'let' equations+=AEquation
	 *     inputDeclarations+=AVariableDeclaration (ambiguity) 'locals'? 'fuzzy'? 'let' useEquations+=AUseEquation
	 *     inputDeclarations+=AVariableDeclaration (ambiguity) 'locals'? 'fuzzy'? 'let'? '.' (rule end)
	 *     parameters=AParamDomain 'inputs'? (ambiguity) 'locals' localvarDeclarations+=AVariableDeclaration
	 *     parameters=AParamDomain 'inputs'? (ambiguity) 'locals'? 'fuzzy' fuzzyVariables+=AFuzzyVariableDeclaration
	 *     parameters=AParamDomain 'inputs'? (ambiguity) 'locals'? 'fuzzy'? 'let' equations+=AEquation
	 *     parameters=AParamDomain 'inputs'? (ambiguity) 'locals'? 'fuzzy'? 'let' useEquations+=AUseEquation
	 *     parameters=AParamDomain 'inputs'? (ambiguity) 'locals'? 'fuzzy'? 'let'? '.' (rule end)
	 */
	protected void emit_AAffineSystem_OutputsKeyword_4_0_q(EObject semanticObject, ISynNavigable transition, List<INode> nodes) {
		acceptNodes(transition, nodes);
	}
	
	/**
	 * Ambiguous syntax:
	 *     '('*
	 *
	 * This ambiguous syntax occurs at:
	 *     (rule start) (ambiguity) '[' func=IndexAffineExpressionTerminal
	 *     (rule start) (ambiguity) 'argreduce' '(' op=AReductionOperator
	 *     (rule start) (ambiguity) 'case' '{' exprs+=AAlphaExpression
	 *     (rule start) (ambiguity) 'case' name=ID
	 *     (rule start) (ambiguity) 'if' cond=AAlphaExpression
	 *     (rule start) (ambiguity) 'reduce' '(' op=AReductionOperator
	 *     (rule start) (ambiguity) 'val(' indexes=AIndexList
	 *     (rule start) (ambiguity) domain=ADomain
	 *     (rule start) (ambiguity) func=AFunction
	 *     (rule start) (ambiguity) func=[AExternalFunction|ID]
	 *     (rule start) (ambiguity) op='not'
	 *     (rule start) (ambiguity) op=ARITHMETIC_REDUCTION_OP
	 *     (rule start) (ambiguity) op=LOGICAL_REDUCTION_OP
	 *     (rule start) (ambiguity) op=OP_MINUS
	 *     (rule start) (ambiguity) value=BOOLEAN
	 *     (rule start) (ambiguity) value=INT
	 *     (rule start) (ambiguity) value=REAL
	 *     (rule start) (ambiguity) var=[AVariable|ID]
	 *     (rule start) (ambiguity) {AAdditiveExpression.left=}
	 *     (rule start) (ambiguity) {AAndExpression.left=}
	 *     (rule start) (ambiguity) {AMinMaxExpression.left=}
	 *     (rule start) (ambiguity) {AMultiplicativeExpression.left=}
	 *     (rule start) (ambiguity) {AOrExpression.left=}
	 *     (rule start) (ambiguity) {ARelationalExpression.left=}
	 */
	protected void emit_AParanthesizedAlphabetsExpression_LeftParenthesisKeyword_0_a(EObject semanticObject, ISynNavigable transition, List<INode> nodes) {
		acceptNodes(transition, nodes);
	}
	
	/**
	 * Ambiguous syntax:
	 *     '('+
	 *
	 * This ambiguous syntax occurs at:
	 *     (rule start) (ambiguity) '[' func=IndexAffineExpressionTerminal
	 *     (rule start) (ambiguity) 'argreduce' '(' op=AReductionOperator
	 *     (rule start) (ambiguity) 'case' '{' exprs+=AAlphaExpression
	 *     (rule start) (ambiguity) 'case' name=ID
	 *     (rule start) (ambiguity) 'if' cond=AAlphaExpression
	 *     (rule start) (ambiguity) 'reduce' '(' op=AReductionOperator
	 *     (rule start) (ambiguity) 'val(' indexes=AIndexList
	 *     (rule start) (ambiguity) domain=ADomain
	 *     (rule start) (ambiguity) func=AFunction
	 *     (rule start) (ambiguity) func=[AExternalFunction|ID]
	 *     (rule start) (ambiguity) op='not'
	 *     (rule start) (ambiguity) op=ARITHMETIC_REDUCTION_OP
	 *     (rule start) (ambiguity) op=LOGICAL_REDUCTION_OP
	 *     (rule start) (ambiguity) op=OP_MINUS
	 *     (rule start) (ambiguity) value=BOOLEAN
	 *     (rule start) (ambiguity) value=INT
	 *     (rule start) (ambiguity) value=REAL
	 *     (rule start) (ambiguity) var=[AVariable|ID]
	 *     (rule start) (ambiguity) {AAdditiveExpression.left=}
	 *     (rule start) (ambiguity) {AAndExpression.left=}
	 *     (rule start) (ambiguity) {AMinMaxExpression.left=}
	 *     (rule start) (ambiguity) {AMultiplicativeExpression.left=}
	 *     (rule start) (ambiguity) {AOrExpression.left=}
	 *     (rule start) (ambiguity) {ARelationalExpression.left=}
	 */
	protected void emit_AParanthesizedAlphabetsExpression_LeftParenthesisKeyword_0_p(EObject semanticObject, ISynNavigable transition, List<INode> nodes) {
		acceptNodes(transition, nodes);
	}
	
}
