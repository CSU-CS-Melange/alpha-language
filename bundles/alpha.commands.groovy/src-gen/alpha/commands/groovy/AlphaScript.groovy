package alpha.commands.groovy

import alpha.model.AlphaRoot
import java.util.List
import alpha.model.AlphaRoot
import alpha.model.AlphaCompleteVisitable
import alpha.model.AlphaRoot
import alpha.model.AlphaSystem
import alpha.model.SystemBody
import alpha.model.StandardEquation
import alpha.model.AlphaExpression
import alpha.model.AlphaNode
import alpha.model.Variable
import fr.irisa.cairn.jnimap.isl.ISLMultiAff
import alpha.model.AlphaRoot
import java.util.List
import alpha.model.Equation
import alpha.model.AlphaSystem
import alpha.model.Variable
import alpha.model.AlphaVisitable
import alpha.model.AlphaExpression
import alpha.model.SystemBody
import alpha.model.AlphaCompleteVisitable
import alpha.model.AutoRestrictExpression
import alpha.model.AlphaRoot
import alpha.model.RestrictExpression
import alpha.model.StandardEquation
import alpha.model.SystemBody
import alpha.model.StandardEquation
import alpha.model.AbstractReduceExpression
import alpha.model.DependenceExpression
import alpha.model.AlphaExpression
import alpha.model.AlphaVisitable
import alpha.model.AlphaSystem
import alpha.model.ReduceExpression

abstract class AlphaScript extends AbstractAlphaScript {

	AlphaRoot ReadAlpha(String file) {
		alpha.commands.Core.ReadAlpha(file)
	}
	List<AlphaRoot> ReadAlphaBundle(String file) {
		alpha.commands.Core.ReadAlphaBundle(file)
	}
	void Show(AlphaCompleteVisitable node) {
		alpha.commands.Core.Show(node)
	}
	void AShow(AlphaCompleteVisitable node) {
		alpha.commands.Core.AShow(node)
	}
	void Save(AlphaRoot root, String filename) {
		alpha.commands.Core.Save(root, filename)
	}
	void ASave(AlphaRoot root, String filename) {
		alpha.commands.Core.ASave(root, filename)
	}
	void CheckProgram(AlphaRoot root) {
		alpha.commands.Core.CheckProgram(root)
	}
	void PrintAST(AlphaCompleteVisitable node) {
		alpha.commands.Core.PrintAST(node)
	}
	void WriteToFile(String filepath, String content) {
		alpha.commands.Utility.WriteToFile(filepath, content)
	}
	AlphaRoot GetRoot(List<AlphaRoot> bundle, String systemName) {
		alpha.commands.Utility.GetRoot(bundle, systemName)
	}
	AlphaSystem GetSystem(AlphaRoot root, String systemName) {
		alpha.commands.Utility.GetSystem(root, systemName)
	}
	SystemBody GetSystemBody(AlphaSystem system, int bodyID) {
		alpha.commands.Utility.GetSystemBody(system, bodyID)
	}
	SystemBody GetSystemBody(AlphaSystem system) {
		alpha.commands.Utility.GetSystemBody(system)
	}
	StandardEquation GetEquation(SystemBody body, String varName) {
		alpha.commands.Utility.GetEquation(body, varName)
	}
	AlphaExpression GetExpression(Equation eq, String exprID) {
		alpha.commands.Utility.GetExpression(eq, exprID)
	}
	AlphaExpression GetExpression(Equation eq) {
		alpha.commands.Utility.GetExpression(eq)
	}
	AlphaExpression GetExpression(SystemBody body, String varName, String exprID) {
		alpha.commands.Utility.GetExpression(body, varName, exprID)
	}
	AlphaExpression GetExpression(SystemBody body, String varName) {
		alpha.commands.Utility.GetExpression(body, varName)
	}
	AlphaExpression GetExpression(AlphaSystem system, int bodyID, String varName, String exprID) {
		alpha.commands.Utility.GetExpression(system, bodyID, varName, exprID)
	}
	AlphaExpression GetExpression(AlphaSystem system, String varName, String exprID) {
		alpha.commands.Utility.GetExpression(system, varName, exprID)
	}
	AlphaExpression GetExpression(AlphaSystem system, int bodyID, String varName) {
		alpha.commands.Utility.GetExpression(system, bodyID, varName)
	}
	AlphaExpression GetExpression(AlphaSystem system, String varName) {
		alpha.commands.Utility.GetExpression(system, varName)
	}
	AlphaNode GetNode(AlphaRoot root, String nodeID) {
		alpha.commands.Utility.GetNode(root, nodeID)
	}
	Variable GetVariable(AlphaSystem system, String varname) {
		alpha.commands.Utility.GetVariable(system, varname)
	}
	Variable GetVariable(SystemBody body, String varname) {
		alpha.commands.Utility.GetVariable(body, varname)
	}
	void RenameVariable(AlphaSystem system, String oldName, String newName) {
		alpha.commands.Utility.RenameVariable(system, oldName, newName)
	}
	void ChangeOfBasis(AlphaSystem system, Variable var, ISLMultiAff f) {
		alpha.commands.Transformations.ChangeOfBasis(system, var, f)
	}
	void ChangeOfBasis(AlphaSystem system, String varName, String fStr) {
		alpha.commands.Transformations.ChangeOfBasis(system, varName, fStr)
	}
	void Normalize(AlphaVisitable node) {
		alpha.commands.Transformations.Normalize(node)
	}
	void Normalize(AlphaExpression expr) {
		alpha.commands.Transformations.Normalize(expr)
	}
	void DeepNormalize(AlphaVisitable node) {
		alpha.commands.Transformations.DeepNormalize(node)
	}
	void DeepNormalize(AlphaExpression expr) {
		alpha.commands.Transformations.DeepNormalize(expr)
	}
	void PropagateSimpleEquations(AlphaSystem system) {
		alpha.commands.Transformations.PropagateSimpleEquations(system)
	}
	void PropagateSimpleEquations(AlphaSystem system, int bodyID) {
		alpha.commands.Transformations.PropagateSimpleEquations(system, bodyID)
	}
	void PropagateSimpleEquations(SystemBody body) {
		alpha.commands.Transformations.PropagateSimpleEquations(body)
	}
	void LiftAutoRestrict(AlphaCompleteVisitable node) {
		alpha.commands.Transformations.LiftAutoRestrict(node)
	}
	void LiftAutoRestrict(AutoRestrictExpression are) {
		alpha.commands.Transformations.LiftAutoRestrict(are)
	}
	void RemoveUnusedEquations(AlphaRoot root) {
		alpha.commands.Transformations.RemoveUnusedEquations(root)
	}
	void RemoveUnusedEquations(AlphaSystem system) {
		alpha.commands.Transformations.RemoveUnusedEquations(system)
	}
	void SimplifyExpressions(AlphaVisitable node) {
		alpha.commands.Transformations.SimplifyExpressions(node)
	}
	void SplitUnionIntoCase(AlphaSystem system) {
		alpha.commands.Transformations.SplitUnionIntoCase(system)
	}
	void SplitUnionIntoCase(AlphaSystem system, int bodyID) {
		alpha.commands.Transformations.SplitUnionIntoCase(system, bodyID)
	}
	void SplitUnionIntoCase(SystemBody body) {
		alpha.commands.Transformations.SplitUnionIntoCase(body)
	}
	void SplitUnionIntoCase(RestrictExpression re) {
		alpha.commands.Transformations.SplitUnionIntoCase(re)
	}
	void SplitUnionIntoCase(AutoRestrictExpression re) {
		alpha.commands.Transformations.SplitUnionIntoCase(re)
	}
	void SubstituteByDef(AlphaSystem system, Variable inlineVar) {
		alpha.commands.Transformations.SubstituteByDef(system, inlineVar)
	}
	void SubstituteByDef(AlphaSystem system, String inlineVarStr) {
		alpha.commands.Transformations.SubstituteByDef(system, inlineVarStr)
	}
	void SubstituteByDef(SystemBody body, Variable inlineVar) {
		alpha.commands.Transformations.SubstituteByDef(body, inlineVar)
	}
	void SubstituteByDef(SystemBody body, String inlineVarStr) {
		alpha.commands.Transformations.SubstituteByDef(body, inlineVarStr)
	}
	void SubstituteByDef(StandardEquation targetEq, Variable inlineVar) {
		alpha.commands.Transformations.SubstituteByDef(targetEq, inlineVar)
	}
	void SubstituteByDef(StandardEquation targetEq, String inlineVarStr) {
		alpha.commands.Transformations.SubstituteByDef(targetEq, inlineVarStr)
	}
	void SubstituteByDef(AlphaExpression targetExpr, Variable inlineVar) {
		alpha.commands.Transformations.SubstituteByDef(targetExpr, inlineVar)
	}
	void SubstituteByDef(AlphaExpression targetExpr, String inlineVarStr) {
		alpha.commands.Transformations.SubstituteByDef(targetExpr, inlineVarStr)
	}
	void Distributivity(SystemBody body, String targetEq, String exprID) {
		alpha.commands.Reductions.Distributivity(body, targetEq, exprID)
	}
	void Distributivity(SystemBody body, String targetEq) {
		alpha.commands.Reductions.Distributivity(body, targetEq)
	}
	void Distributivity(StandardEquation eq, String exprID) {
		alpha.commands.Reductions.Distributivity(eq, exprID)
	}
	void Distributivity(StandardEquation eq) {
		alpha.commands.Reductions.Distributivity(eq)
	}
	void Distributivity(AbstractReduceExpression are) {
		alpha.commands.Reductions.Distributivity(are)
	}
	void FactorOutFromReduction(SystemBody body, String targetEq, String exprID) {
		alpha.commands.Reductions.FactorOutFromReduction(body, targetEq, exprID)
	}
	void FactorOutFromReduction(SystemBody body, String targetEq) {
		alpha.commands.Reductions.FactorOutFromReduction(body, targetEq)
	}
	void FactorOutFromReduction(StandardEquation eq, String exprID) {
		alpha.commands.Reductions.FactorOutFromReduction(eq, exprID)
	}
	void FactorOutFromReduction(StandardEquation eq) {
		alpha.commands.Reductions.FactorOutFromReduction(eq)
	}
	void FactorOutFromReduction(DependenceExpression expr) {
		alpha.commands.Reductions.FactorOutFromReduction(expr)
	}
	void HigherOrderOperator(SystemBody body, String targetEq, String exprID) {
		alpha.commands.Reductions.HigherOrderOperator(body, targetEq, exprID)
	}
	void HigherOrderOperator(SystemBody body, String targetEq) {
		alpha.commands.Reductions.HigherOrderOperator(body, targetEq)
	}
	void HigherOrderOperator(StandardEquation eq, String exprID) {
		alpha.commands.Reductions.HigherOrderOperator(eq, exprID)
	}
	void HigherOrderOperator(StandardEquation eq) {
		alpha.commands.Reductions.HigherOrderOperator(eq)
	}
	void HigherOrderOperator(AbstractReduceExpression are) {
		alpha.commands.Reductions.HigherOrderOperator(are)
	}
	void HoistOutOfReduction(SystemBody body, String targetEq, String exprID) {
		alpha.commands.Reductions.HoistOutOfReduction(body, targetEq, exprID)
	}
	void HoistOutOfReduction(SystemBody body, String targetEq) {
		alpha.commands.Reductions.HoistOutOfReduction(body, targetEq)
	}
	void HoistOutOfReduction(StandardEquation eq, String exprID) {
		alpha.commands.Reductions.HoistOutOfReduction(eq, exprID)
	}
	void HoistOutOfReduction(StandardEquation eq) {
		alpha.commands.Reductions.HoistOutOfReduction(eq)
	}
	void HoistOutOfReduction(AlphaExpression expr) {
		alpha.commands.Reductions.HoistOutOfReduction(expr)
	}
	void Idempotence(SystemBody body, String targetEq, String exprID) {
		alpha.commands.Reductions.Idempotence(body, targetEq, exprID)
	}
	void Idempotence(SystemBody body, String targetEq) {
		alpha.commands.Reductions.Idempotence(body, targetEq)
	}
	void Idempotence(StandardEquation eq, String exprID) {
		alpha.commands.Reductions.Idempotence(eq, exprID)
	}
	void Idempotence(StandardEquation eq) {
		alpha.commands.Reductions.Idempotence(eq)
	}
	void Idempotence(AbstractReduceExpression are) {
		alpha.commands.Reductions.Idempotence(are)
	}
	void NormalizeReduction(AlphaVisitable node) {
		alpha.commands.Reductions.NormalizeReduction(node)
	}
	void NormalizeReduction(SystemBody body, String targetEq, String exprID) {
		alpha.commands.Reductions.NormalizeReduction(body, targetEq, exprID)
	}
	void NormalizeReduction(SystemBody body, String targetEq) {
		alpha.commands.Reductions.NormalizeReduction(body, targetEq)
	}
	void NormalizeReduction(StandardEquation eq, String exprID) {
		alpha.commands.Reductions.NormalizeReduction(eq, exprID)
	}
	void NormalizeReduction(StandardEquation eq) {
		alpha.commands.Reductions.NormalizeReduction(eq)
	}
	void NormalizeReduction(AbstractReduceExpression reduction) {
		alpha.commands.Reductions.NormalizeReduction(reduction)
	}
	void PermutationCaseReduce(AlphaSystem system) {
		alpha.commands.Reductions.PermutationCaseReduce(system)
	}
	void PermutationCaseReduce(SystemBody body) {
		alpha.commands.Reductions.PermutationCaseReduce(body)
	}
	void PermutationCaseReduce(SystemBody body, String targetEq, String exprID) {
		alpha.commands.Reductions.PermutationCaseReduce(body, targetEq, exprID)
	}
	void PermutationCaseReduce(SystemBody body, String targetEq) {
		alpha.commands.Reductions.PermutationCaseReduce(body, targetEq)
	}
	void PermutationCaseReduce(StandardEquation eq, String exprID) {
		alpha.commands.Reductions.PermutationCaseReduce(eq, exprID)
	}
	void PermutationCaseReduce(StandardEquation eq) {
		alpha.commands.Reductions.PermutationCaseReduce(eq)
	}
	void PermutationCaseReduce(AbstractReduceExpression are) {
		alpha.commands.Reductions.PermutationCaseReduce(are)
	}
	void ReductionComposition(AlphaSystem system) {
		alpha.commands.Reductions.ReductionComposition(system)
	}
	void ReductionComposition(SystemBody body) {
		alpha.commands.Reductions.ReductionComposition(body)
	}
	void ReductionComposition(SystemBody body, String targetEq, String exprID) {
		alpha.commands.Reductions.ReductionComposition(body, targetEq, exprID)
	}
	void ReductionComposition(SystemBody body, String targetEq) {
		alpha.commands.Reductions.ReductionComposition(body, targetEq)
	}
	void ReductionComposition(StandardEquation eq, String exprID) {
		alpha.commands.Reductions.ReductionComposition(eq, exprID)
	}
	void ReductionComposition(StandardEquation eq) {
		alpha.commands.Reductions.ReductionComposition(eq)
	}
	void ReductionComposition(AbstractReduceExpression reduction) {
		alpha.commands.Reductions.ReductionComposition(reduction)
	}
	void ReducionDecomposition(SystemBody body, String targetEq, String exprID, ISLMultiAff f1, ISLMultiAff f2) {
		alpha.commands.Reductions.ReducionDecomposition(body, targetEq, exprID, f1, f2)
	}
	void ReducionDecomposition(SystemBody body, String targetEq, String exprID, String f1Str, String f2Str) {
		alpha.commands.Reductions.ReducionDecomposition(body, targetEq, exprID, f1Str, f2Str)
	}
	void ReducionDecomposition(StandardEquation eq, String exprID, ISLMultiAff f1, ISLMultiAff f2) {
		alpha.commands.Reductions.ReducionDecomposition(eq, exprID, f1, f2)
	}
	void ReducionDecomposition(StandardEquation eq, ISLMultiAff f1, ISLMultiAff f2) {
		alpha.commands.Reductions.ReducionDecomposition(eq, f1, f2)
	}
	void ReducionDecomposition(StandardEquation eq, String exprID, String f1Str, String f2Str) {
		alpha.commands.Reductions.ReducionDecomposition(eq, exprID, f1Str, f2Str)
	}
	void ReducionDecomposition(StandardEquation eq, String f1Str, String f2Str) {
		alpha.commands.Reductions.ReducionDecomposition(eq, f1Str, f2Str)
	}
	void ReductionDecomposition(AbstractReduceExpression reduction, ISLMultiAff f1, ISLMultiAff f2) {
		alpha.commands.Reductions.ReductionDecomposition(reduction, f1, f2)
	}
	void ReductionDecomposition(AbstractReduceExpression reduction, String f1Str, String f2Str) {
		alpha.commands.Reductions.ReductionDecomposition(reduction, f1Str, f2Str)
	}
	void SameOperatorSimplification(SystemBody body, String targetEq, String exprID) {
		alpha.commands.Reductions.SameOperatorSimplification(body, targetEq, exprID)
	}
	void SameOperatorSimplification(SystemBody body, String targetEq) {
		alpha.commands.Reductions.SameOperatorSimplification(body, targetEq)
	}
	void SameOperatorSimplification(StandardEquation eq, String exprID) {
		alpha.commands.Reductions.SameOperatorSimplification(eq, exprID)
	}
	void SameOperatorSimplification(StandardEquation eq) {
		alpha.commands.Reductions.SameOperatorSimplification(eq)
	}
	void SameOperatorSimplification(AbstractReduceExpression reduction) {
		alpha.commands.Reductions.SameOperatorSimplification(reduction)
	}
	void SimplifyingReductions(SystemBody body, String targetEq, String exprID, int[] reuseVec) {
		alpha.commands.Reductions.SimplifyingReductions(body, targetEq, exprID, reuseVec)
	}
	void SimplifyingReductions(SystemBody body, String targetEq, String exprID, String reuseVecStr) {
		alpha.commands.Reductions.SimplifyingReductions(body, targetEq, exprID, reuseVecStr)
	}
	void SimplifyingReductions(SystemBody body, String targetEq, int[] reuseVec) {
		alpha.commands.Reductions.SimplifyingReductions(body, targetEq, reuseVec)
	}
	void SimplifyingReductions(SystemBody body, String targetEq, String reuseVecStr) {
		alpha.commands.Reductions.SimplifyingReductions(body, targetEq, reuseVecStr)
	}
	void SimplifyingReductions(StandardEquation eq, String exprID, int[] reuseVec) {
		alpha.commands.Reductions.SimplifyingReductions(eq, exprID, reuseVec)
	}
	void SimplifyingReductions(StandardEquation eq, String exprID, String reuseVecStr) {
		alpha.commands.Reductions.SimplifyingReductions(eq, exprID, reuseVecStr)
	}
	void SimplifyingReductions(StandardEquation eq, int[] reuseVec) {
		alpha.commands.Reductions.SimplifyingReductions(eq, reuseVec)
	}
	void SimplifyingReductions(StandardEquation eq, String reuseVecStr) {
		alpha.commands.Reductions.SimplifyingReductions(eq, reuseVecStr)
	}
	void SimplifyingReductions(ReduceExpression reduction, int[] reuseVec) {
		alpha.commands.Reductions.SimplifyingReductions(reduction, reuseVec)
	}
	void SimplifyingReductions(ReduceExpression reduction, String reuseVecStr) {
		alpha.commands.Reductions.SimplifyingReductions(reduction, reuseVecStr)
	}

}
