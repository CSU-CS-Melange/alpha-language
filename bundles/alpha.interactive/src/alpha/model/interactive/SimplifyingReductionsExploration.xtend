package alpha.model.interactive

import alpha.model.AbstractReduceExpression
import alpha.model.AutoRestrictExpression
import alpha.model.CaseExpression
import alpha.model.ReduceExpression
import alpha.model.RestrictExpression
import alpha.model.StandardEquation
import alpha.model.UseEquation
import alpha.model.Variable
import alpha.model.VariableExpression
import alpha.model.analysis.reduction.ShareSpaceAnalysis
import alpha.model.analysis.reduction.ShareSpaceAnalysisResult
import alpha.model.matrix.MatrixOperations
import alpha.model.transformation.Normalize
import alpha.model.transformation.SplitUnionIntoCase
import alpha.model.transformation.SubstituteByDef
import alpha.model.transformation.reduction.Distributivity
import alpha.model.transformation.reduction.HigherOrderOperator
import alpha.model.transformation.reduction.Idempotence
import alpha.model.transformation.reduction.NormalizeReduction
import alpha.model.transformation.reduction.PermutationCaseReduce
import alpha.model.transformation.reduction.ReductionComposition
import alpha.model.transformation.reduction.ReductionDecomposition
import alpha.model.transformation.reduction.SameOperatorSimplification
import alpha.model.transformation.reduction.SimplifyingReductions
import alpha.model.util.AShow
import alpha.model.util.AffineFunctionOperations
import alpha.model.util.AlphaUtil
import fr.irisa.cairn.jnimap.isl.jni.JNIISLMultiAff
import java.util.HashSet
import java.util.LinkedList
import java.util.List
import org.eclipse.xtext.EcoreUtil2

class SimplifyingReductionsExploration extends AbstractInteractiveExploration {

	protected STATE state = STATE.INITIAL
	protected AbstractReduceExpression targetRE
	
	protected List<String> commandHistory;
	
	private new(String filepath) {
		super(filepath);
	}
	
	static final def start(String filepath) {
		val sre = new SimplifyingReductionsExploration(filepath);
		SimplifyingReductionsExploration.SKIP_SINGLE_CHOICE_QUESTIONS = true
		sre.run();
	}

	override mainLoop() {
		SimplifyingReductionsExploration.SKIP_SINGLE_CHOICE_QUESTIONS = false
		while (state != STATE.EXIT) {
			try {
				commandHistory = new LinkedList();

				switch (state) {
					case INITIAL: {
						selectReduceExpression();
					}
					case REDUCTION_SELECTED: {
						expressionDomainCheck()
					}
					case SIDE_EFFECT_FREE_TRANSFORMATIONS: {
						sideEffectFreeTransformations();
					}
					case DP_STEP_TRANSFORMATIONS: {
						StepTransformations();
					}
					case EXIT: {
						//do nothing
					}
				}
				
				if (!commandHistory.isEmpty) {
					setProperty("state", state)
					setProperty("targetRE", targetRE)
					recordState(commandHistory)
				}
			} catch (RuntimeException re) {
				errStream.println("Exception: " + re.class);
				errStream.println(re.message);
				re.printStackTrace
				errStream.println("Restoring previous state.");
				restoreState
			}
		}
		
		outStream.println("Final program:");
		outStream.println(AShow.print(currentSystem));
		outStream.println("");
		outStream.print("Press enter/return to output transformation script")
		inStream.readLine
	
		for (String c : getCommandSequence()) {
			outStream.println(c);
		}
	}
	
	/**
	 * Lists all reductions in the selected SystemBody to work with.
	 * 
	 */
	private def selectReduceExpression() {
		val body = getCurrentBody();
		
		val candidates = EcoreUtil2.getAllContentsOfType(body, AbstractReduceExpression);
		
		val options = new LinkedList<ExplorationStep>();
		
		options.addAll(candidates.map[e|new StepSelectReduction(e)])
		
		//Default option: revert & finish
		options.add(new StepBacktrack());
		options.add(new StepFinish());
		
		outStream.println("");
		outStream.println("Select a ReduceExpression to work on:");
		for (i : 0..< options.size) {
			val opt = options.get(i);
			outStream.println(String.format("%d: %s", i, opt.description));
		}
		val selected = acceptInteger(0, options.size)
		performAction(options.get(selected));	
	}
	
	/**
	 * Once a reduction is selected, the basic assumptions must be checked.
	 *  - The expression domain of the reduction body is a single polyhedron.
	 *  - The ReduceExpression has trivial share space.
	 * 
	 * The latter is currently not checked; it only happens when the domain of 
	 * reduction body is unbounded.
	 */
	private def expressionDomainCheck() {
		val eqName = AlphaUtil.getContainerEquation(targetRE).equationName
		//expression ID must be obtained before transformation
		val exprID = targetRE.expressionID
		
		if (targetRE.body instanceof CaseExpression) {
			outStream.println("");
			outStream.println("Selected reduction has a CaseExpression as its body. Applying PermutationCaseReduce.")
			outStream.print("Press enter/return to continue...")
			inStream.readLine
			
			PermutationCaseReduce.apply(targetRE)
			state = STATE.INITIAL
			targetRE = null
			commandHistory.add(String.format("PermutationCaseReduce(GetExpression(body, %s, %s)", eqName, exprID))
			return;
		}
		
		if (targetRE.body.expressionDomain.nbBasicSets > 1) {
			outStream.println("");
			outStream.println("The expression domain of reduction body is not a single polyhedron. Applying SplitUnionIntoCase.");
			outStream.print("Press enter/return to continue...")
			inStream.readLine
			
			switch (targetRE.body) {
				RestrictExpression : {
					SplitUnionIntoCase.apply(targetRE.body as RestrictExpression)
				}
				AutoRestrictExpression : {
					SplitUnionIntoCase.apply(targetRE.body as AutoRestrictExpression)
				}
				default : {
					throw new UnsupportedOperationException("Expecting the body of reduction to be a restrict. The program may not be normalized.");
				}
			}
			
			state = STATE.INITIAL
			targetRE = null
			commandHistory.add(String.format("SplitUnionIntoCase(GetExpression(body, %s, %s)", eqName, exprID))
			return;
		}
		
		state = STATE.SIDE_EFFECT_FREE_TRANSFORMATIONS
	}
	
	/**
	 * Apply side-effect free SR enhancing transformations.
	 * This step is completely automatic. 
	 * 
	 */
	private def sideEffectFreeTransformations() {
		val eqName = AlphaUtil.getContainerEquation(targetRE).equationName
		//expression ID must be obtained before transformation
		val exprID = targetRE.body.expressionID
		
		//reduction decomposition without side-effect (not implemented)
		//same operator simplification
		val sosCount = SameOperatorSimplification.apply(targetRE)
		if (sosCount > 0) {
			outStream.println("");
			outStream.println(String.format("Applied SameOperatorSimplification: %s", AShow.print(targetRE)));
			outStream.print("Press enter/return to continue...")
			inStream.readLine
			
			state = STATE.INITIAL
			targetRE = null
			commandHistory.add(String.format("SameOperatorSimplification(GetExpression(body, %s, %s)", eqName, exprID))
			return;
		}
		//distributivity
		val distCount = Distributivity.apply(targetRE)
		if (distCount > 0) {
			outStream.println("");
			outStream.println(String.format("Applied Distributivity: %s", AShow.print(targetRE)));
			outStream.print("Press enter/return to continue...")
			inStream.readLine
			
			state = STATE.INITIAL
			targetRE = null
			commandHistory.add(String.format("Distributivity(GetExpression(body, %s, %s)", eqName, exprID))
			return
		}
		
		outStream.println("No side-effect free transformations to apply.");
		
		state = STATE.DP_STEP_TRANSFORMATIONS
	}
	
	/**
	 * Lists all possible DP step that can be taken. 
	 * 
	 */
	private def StepTransformations() {
		Normalize.apply(targetRE.body)
		
		val nbParams = targetRE.expressionDomain.nbParams
		val SSAR = ShareSpaceAnalysis.apply(targetRE)
		
		val options = new LinkedList<ExplorationStep>();
		
		val vectors = SimplifyingReductions.generateCandidateReuseVectors(targetRE, SSAR);
		for (vec : vectors) {
			options.add(new StepSimplifyingReduction(vec, nbParams));
		}
		
		//Idempotent
		if (Idempotence.testLegality(targetRE, SSAR)) {
			options.add(new StepIdempotence());
		}
		
		//Higher-Order Operator
		if (HigherOrderOperator.testLegality(targetRE, SSAR)) {
			options.add(new StepHigherOrderOperator());
		}
		
		//Composition
		if (ReductionComposition.testLegality(targetRE)) {
			options.add(new StepReductionComposition());
		}
		
		//Decomposition with side-effects
		options.addAll(SSAR.findDecompositionCandidates)
		
		//Inline
		options.addAll(targetRE.findInlineCandidates)
		
		//Default option: revert & finish
		options.add(new StepPrintShareSpace(SSAR));
		options.add(new StepBacktrack());
		options.add(new StepFinish());
		
		
		outStream.println("");
		outStream.println("Select an action:");
		for (i : 0..< options.size) {
			val opt = options.get(i);
			outStream.println(String.format("%d: %s", i, opt.description));
		}
		val selected = acceptInteger(0, options.size)
		performAction(options.get(selected));
	}
	
	private def dispatch performAction(StepSelectReduction step) {
		targetRE = step.are 
		state = STATE.REDUCTION_SELECTED
		outStream.println(String.format("TargetReduction: %s", AShow.print(targetRE)));			
	}
	
	private def dispatch performAction(StepSimplifyingReduction step) {
		if (!(targetRE.eContainer instanceof StandardEquation)) {
			val eqName = AlphaUtil.getContainerEquation(targetRE).equationName
			//expression ID must be obtained before transformation
			val exprID = targetRE.expressionID
		
			val eq = NormalizeReduction.apply(targetRE)
			commandHistory.add(String.format("NormalizeReduction(GetExpression(body, %s, %s)", eqName, exprID))
			targetRE = eq.expr as AbstractReduceExpression
		}

		val eqName = AlphaUtil.getContainerEquation(targetRE).equationName
		//expression ID must be obtained before transformation
		val exprID = targetRE.expressionID
		
		SimplifyingReductions.apply(targetRE as ReduceExpression, step.reuseDepNoParams);
		
		outStream.println(String.format("Applied SimplifyingReductions."));
		state = STATE.INITIAL
		targetRE = null
		commandHistory.add(String.format("SimplifyingReductions(GetExpression(body, %s, %s, \"%s\")", eqName, exprID, MatrixOperations.toString(step.reuseDepNoParams)))
		
	}
	
	private def dispatch performAction(StepIdempotence step) {
		val eqName = AlphaUtil.getContainerEquation(targetRE).equationName
		//expression ID must be obtained before transformation
		val exprID = targetRE.expressionID
		
		Idempotence.apply(targetRE);
		
		outStream.println("");
		outStream.println(String.format("Applied Idempotence: %s", AShow.print(targetRE)));
		outStream.print("Press enter/return to continue...")
		inStream.readLine
		
		state = STATE.INITIAL
		targetRE = null
		commandHistory.add(String.format("Idempotence(GetExpression(body, %s, %s)", eqName, exprID))
	}
	
	private def dispatch performAction(StepHigherOrderOperator step) {
		val eqName = AlphaUtil.getContainerEquation(targetRE).equationName
		//expression ID must be obtained before transformation
		val exprID = targetRE.expressionID
		
		HigherOrderOperator.apply(targetRE);
		
		outStream.println("");
		outStream.println(String.format("Applied HigherOrderOperator: %s", AShow.print(targetRE)));
		outStream.print("Press enter/return to continue...")
		inStream.readLine
		
		state = STATE.INITIAL
		targetRE = null
		commandHistory.add(String.format("HigherOrderOperator(GetExpression(body, %s, %s)", eqName, exprID))
	}
	
	private def dispatch performAction(StepReductionComposition step) {
		val eqName = AlphaUtil.getContainerEquation(targetRE).equationName
		//expression ID must be obtained before transformation
		val exprID = targetRE.expressionID
		
		ReductionComposition.apply(targetRE);
		
		outStream.println("");
		outStream.println(String.format("Applied ReductionComposition: %s", AShow.print(targetRE)));
		outStream.print("Press enter/return to continue...")
		inStream.readLine
		
		state = STATE.SIDE_EFFECT_FREE_TRANSFORMATIONS
		commandHistory.add(String.format("ReductionComposition(GetExpression(body, %s, %s))", eqName, exprID))
	}
	
	private def dispatch performAction(StepReductionDecomposition step) {
		val eqName = AlphaUtil.getContainerEquation(targetRE).equationName
		//expression ID must be obtained before transformation
		val exprID = targetRE.expressionID
		
		ReductionDecomposition.apply(targetRE, step.innerProjection,  step.outerProjection);
		
		outStream.println("");
		outStream.println(String.format("Applied ReductionDecomposition: %s", AShow.print(targetRE)));
		outStream.print("Press enter/return to continue...")
		inStream.readLine
		
		state = STATE.INITIAL
		targetRE = null
		commandHistory.add(String.format("ReductionDecomposition(GetExpression(body, %s, %s, \"%s\", \"%s\")", eqName, exprID, step.innerProjection, step.outerProjection))
	}
	
	private def findDecompositionCandidates(ShareSpaceAnalysisResult SSAR) {
		val exprREs = SSAR.expressionsWithReuse
		val kerFp = MatrixOperations.transpose(AffineFunctionOperations.computeKernel(targetRE.projection))

		val REs = new HashSet<long[][]>();
		for (exprRE : exprREs) {
			val intersection = MatrixOperations.plainIntersection(exprRE.value, kerFp)
			if (intersection !== null)
				REs.add(intersection)
		}
		
		val candidates = new LinkedList<StepReductionDecomposition>();
		

		val params = targetRE.body.expressionDomain.parametersNames
		val indices = targetRE.body.expressionDomain.indicesNames		
		for (RE : REs) {
			val Fp = AffineFunctionOperations.constructAffineFunctionWithSpecifiedKernel(params, indices, RE);
			val Fpp = AffineFunctionOperations.projectFunctionDomain(targetRE.projection, Fp.copy)
			candidates.add(new StepReductionDecomposition(Fp, Fpp));
		}
		
		return candidates;
	}
	
	private def dispatch performAction(StepInlineVariable step) {
		//expression ID must be obtained before transformation
		val exprID = targetRE.expressionID
		
		SubstituteByDef.apply(targetRE, step.variable);
		Normalize.apply(targetRE)
		
		outStream.println("");
		outStream.println(String.format("Applied SubstituteByDef: %s", AShow.print(targetRE)));
		outStream.print("Press enter/return to continue...")
		inStream.readLine
		
		state = STATE.SIDE_EFFECT_FREE_TRANSFORMATIONS
		commandHistory.add(String.format("SubstituteByDef(GetExpression(body, %s), %s)", exprID, step.variable.name))
	}
	
	private def dispatch performAction(StepBacktrack step) {
		do {
			rollbackState
		} while (state != STATE.INITIAL && state != STATE.REDUCTION_SELECTED && state != STATE.DP_STEP_TRANSFORMATIONS)
	}
	private def dispatch performAction(StepFinish step) {
		state = STATE.EXIT
	}
	private def dispatch performAction(StepPrintShareSpace step) {
		outStream.println(step.SSAR);
	}
	
	private static enum STATE {
		INITIAL,
		REDUCTION_SELECTED,
		SIDE_EFFECT_FREE_TRANSFORMATIONS,
		DP_STEP_TRANSFORMATIONS,
		EXIT
	}
	
	override protected initProperties() {
		setProperty("state", STATE.INITIAL)
	}
	override protected reflectProperties() {
		state = if (getProperty("state") !== null) getProperty("state") as STATE else STATE.INITIAL;
		targetRE = if (getProperty("targetRE") !== null) getProperty("targetRE") as AbstractReduceExpression else null
		
		//consistency check
		if (targetRE !== null && AlphaUtil.getContainerSystemBody(targetRE) !== currentBody) {
			throw new RuntimeException("Main loop state is inconsistent.");
		}
	}
	
	private static abstract class ExplorationStep {
	
		abstract def String description();
	}
	
	private static class StepSelectReduction extends ExplorationStep {
		AbstractReduceExpression are;
		
		new (AbstractReduceExpression are) {
			this.are = are
		}
		
		override description() {
			String.format("%s (in %s)", AShow.print(are), AlphaUtil.getContainerEquation(are).equationName)
		}
		
	}
	
	private static class StepSimplifyingReduction extends ExplorationStep {
		long[] reuseDepNoParams;
		
		new(long[] reuseDep, int nbParams) {
			reuseDepNoParams = MatrixOperations.removeColumns(reuseDep, (0..<nbParams))
		}
		
		override description() {
			String.format("Apply SimplifyingReduction with: %s", MatrixOperations.toString(reuseDepNoParams));
		}
		
	}
	
	private static class StepIdempotence extends ExplorationStep {
		
		override description() {
			String.format("Apply Idempotence");
		}
	}
	
	private static class StepHigherOrderOperator extends ExplorationStep {
		
		override description() {
			String.format("Apply HigherOrderOperator");
		}
	}
	
	private static class StepReductionComposition extends ExplorationStep {
		
		override description() {
			"Apply ReductionComposition"
		}
		
	}
	
	private static class StepReductionDecomposition extends ExplorationStep {
		
		JNIISLMultiAff innerProjection
		JNIISLMultiAff outerProjection
		
		new(JNIISLMultiAff innerF, JNIISLMultiAff outerF) {
			innerProjection = innerF;
			outerProjection = outerF;
		}
		
		override description() {
			String.format("Apply ReductionDecomposition with %s o %s", outerProjection, innerProjection);
		}
	}
	
	private static class StepInlineVariable extends ExplorationStep {
		Variable variable;
		
		new(Variable v) {
			variable = v
		}
		
		override description() {
			String.format("Inline %s", variable.name);
		}
	}
	
	private static class StepPrintShareSpace extends ExplorationStep {
		ShareSpaceAnalysisResult SSAR;
		new (ShareSpaceAnalysisResult SSAR) {
			this.SSAR = SSAR;
		}
		
		override description() {
			String.format("Print Share Space");
		}
	}
	
	private static class StepBacktrack extends ExplorationStep {
		
		override description() {
			String.format("Revert to previous state.");
		}
	}
	
	private static class StepFinish extends ExplorationStep {
		
		override description() {
			String.format("Finish exploration.");
		}
	}
	
	private static def findInlineCandidates(AbstractReduceExpression are) {
		EcoreUtil2.getAllContentsOfType(are, VariableExpression).filter[ve|ve.variable.local || ve.variable.output].map[
			ve|new StepInlineVariable(ve.variable)
		]
	}
	
	////////////////
	private static dispatch def getEquationName(StandardEquation eq) {
		eq.variable.name
	}
	
	private static dispatch def getEquationName(UseEquation eq) {
		'''UseEquation'''
	}
}