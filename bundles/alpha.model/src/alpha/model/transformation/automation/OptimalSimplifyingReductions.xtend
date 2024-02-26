package alpha.model.transformation.automation

import alpha.model.AbstractReduceExpression
import alpha.model.AlphaExpression
import alpha.model.AlphaRoot
import alpha.model.AlphaSystem
import alpha.model.ReduceExpression
import alpha.model.StandardEquation
import alpha.model.SystemBody
import alpha.model.analysis.reduction.ShareSpaceAnalysis
import alpha.model.matrix.MatrixOperations
import alpha.model.transformation.Normalize
import alpha.model.transformation.SplitUnionIntoCase
import alpha.model.transformation.reduction.Distributivity
import alpha.model.transformation.reduction.HigherOrderOperator
import alpha.model.transformation.reduction.Idempotence
import alpha.model.transformation.reduction.NormalizeReduction
import alpha.model.transformation.reduction.PermutationCaseReduce
import alpha.model.transformation.reduction.ReductionComposition
import alpha.model.transformation.reduction.ReductionDecomposition
import alpha.model.transformation.reduction.SameOperatorSimplification
import alpha.model.transformation.reduction.SimplifyingReductions
import alpha.model.util.AlphaUtil
import alpha.model.util.Show
import fr.irisa.cairn.jnimap.isl.ISLMultiAff
import java.io.BufferedWriter
import java.io.FileWriter
import java.util.LinkedList
import java.util.List
import java.util.Map
import org.eclipse.emf.common.util.EList
import org.eclipse.emf.ecore.util.EcoreUtil

import static extension alpha.model.util.AlphaUtil.*
import static extension alpha.model.util.ISLUtil.dimensionality
import static extension java.lang.String.format
import alpha.model.transformation.RaiseDependence
import alpha.model.AlphaInternalStateConstructor
import alpha.model.transformation.RaiseDependenceAndIsolate

/**
 * Implements Algorithm 2 in the Simplifying Reductions paper.
 * 
 */
class OptimalSimplifyingReductions {
	
	public static boolean DEBUG = false
	
	static boolean THROTTLE = true
	static int THROTTLE_LIMIT = 10
	static long optimizationNum
	
	static String saveDirectory = 'resources/opt'
	
	protected AlphaRoot root
	protected AlphaSystem system
	protected SystemBody systemBody
	protected int systemBodyID
	protected String originalSystemName
	
	
	protected final Map<Integer, List<State>> optimizations
	
	protected new (SystemBody originalSystemBody) {
		root = EcoreUtil.copy(AlphaUtil.getContainerRoot(originalSystemBody))
		system = root.getSystem(originalSystemBody.system.fullyQualifiedName)
		systemBodyID = originalSystemBody.system.systemBodies.indexOf(originalSystemBody)
		systemBody = system.systemBodies.get(systemBodyID)
		optimizations = newHashMap
		originalSystemName = system.name
		optimizationNum = 0
	}
	
	static def apply(AlphaSystem system) {
		if (system.systemBodies.size == 1)
			apply(system.systemBodies.get(0))
		else
			throw new IllegalArgumentException("A SystemBody must be specified for an AlphaSystem with multiple bodies.")
	}
	
	static def apply(SystemBody body) {
		val osr = new OptimalSimplifyingReductions(body)
		osr.run
		return osr
	}
	
	protected def preprocessing() {
		ReductionComposition.apply(systemBody)
		SplitUnionIntoCase.apply(systemBody)
		PermutationCaseReduce.apply(systemBody)
		NormalizeReduction.apply(systemBody)
		Normalize.apply(systemBody)
		
		RaiseDependenceAndIsolate.apply(systemBody)
		AlphaInternalStateConstructor.recomputeContextDomain(systemBody)
		
		println('After preprocessing:')
		println(Show.print(systemBody))
		
		val state = new State(systemBody, newLinkedList)
		
		(0..<state.complexity).forEach[i | optimizations.put(i, newLinkedList)]
		
		return state
	}
	
	/**
	 * Entry point to the algorithm
	 * 
	 */
	private def run() {
		val state = preprocessing
		
		try {
			state.optimizeUnexploredEquations
		} catch (ThrottleException e) {
			println('Throttled search to stop after ' + THROTTLE_LIMIT + ' results')
		}
		println
		
		val foundOptimizations = optimizations.keySet
			.reject[k | optimizations.get(k).size == 0]
			.map[k | k -> optimizations.get(k)]
			.toList
		foundOptimizations.forEach[keyOpts | 
			val opts = keyOpts.value
			opts.forEach[println(show)]
		]
		
		println
		foundOptimizations.forEach[keyOpts | 
			val key = keyOpts.key
			val opts = keyOpts.value
			println('Number of ' + key + 'D optimizations: ' + opts.size)
		]
	}
	
	private def addToOptimzations(State state) {
		val opts = optimizations.get(state.complexity)
		opts += state
	}
	
	private def void optimizeUnexploredEquations(State state) {
		state.body.standardEquations.reject[explored].forEach[
			optimizeEquation(state)
		]
		
		val stateComplexity = state.complexity
		if (stateComplexity <= 2) {
			optimizationNum++
			print('\rnumber of 2D optimizations found: ' + optimizationNum)
			state.addToOptimzations
			
			if (saveDirectory !== null) {
				val fileName = '%s/%s.v%03d.alpha'.format(saveDirectory, state.body.system.name, optimizationNum)
				//println(state.show)
				val stateStr = state.show.toString
				stateStr.writeToFile(fileName)
			}
			if (THROTTLE && optimizationNum >= THROTTLE_LIMIT)
				throw new ThrottleException
			
		}
	}
	
	private def void optimizeEquation(StandardEquation eq, State state) {
		optimizeEquation(eq, eq.expr, state)
		return
	}
	private def dispatch void optimizeEquation(StandardEquation eq, ReduceExpression re, State state) {
		val containerSystemBody = eq.getContainerSystemBody
		if (re.isOptimallySimplified) {
			eq.setExplored
			return
		}
		
		val body = eq.getContainerSystemBody
		
		while(!sideEffectFreeTransformations(body, eq.variable.name)) {}
		
		val targetEq = body.getStandardEquation(eq.variable.name)
		
		if (targetEq.expr.isNotReduceExpr) {
			eq.setExplored
			return
		}
		
		val candidates = enumerateCandidates(targetEq.expr as ReduceExpression)
		
		for (step : candidates) {
			val optimizedRoot = EcoreUtil.copy(containerSystemBody.getContainerRoot)
			val optimizedBody = optimizedRoot.getSystem(originalSystemName).systemBodies.get(systemBodyID)
			val optimizedEq = optimizedRoot.getEquation(targetEq.name)
			optimizedEq.expr.applyDPStep(step)
			// mark equation as finished/explored if it is no longer contains a reduction expr 
			optimizedEq.explored = optimizedEq.expr.isNotReduceExpr
			val steps = newLinkedList
			steps.addAll(state.steps)
			steps += step
			val newState = new State(optimizedBody, steps)
			optimizeUnexploredEquations(newState)
		}
	}
	private def dispatch void optimizeEquation(StandardEquation eq, AlphaExpression ae, State state) {
		eq.explored = true
	}
	
	private def StandardEquation getEquation(AlphaRoot root, String name) {
		val eqs = root.getSystem(originalSystemName)
			.systemBodies.get(systemBodyID)
			.equations
			.filter[eq | eq instanceof StandardEquation]
			.map[eq | eq as StandardEquation]
			.filter[eq | eq.name == name]
		if (eqs.size != 1) {
			throw new Exception("failed to get expression in copied root, this should not happen")
		}
		eqs.get(0)
	}
	
	def static int complexity(SystemBody body) {
		body.equations
			.filter[eq | eq instanceof StandardEquation]
			.map[eq | eq as StandardEquation].map[eq |
				if (eq.expr instanceof ReduceExpression) {
					(eq.expr as ReduceExpression).body.contextDomain.dimensionality
				} else {
					eq.variable.domain.dimensionality
				}
			].max
	}
	
	
	private def static isNotReduceExpr(AlphaExpression expr) {
		!(expr instanceof ReduceExpression)
	}
	
	/** 
	 * Returns true if the dimensionality reduce expression's body's context domain is the same
	 * as the dimensionality of the LHS of the container equation.
	 * The parent of the expression is guaranteed to be a Standard Equation since NormalizeReduction
	 * has been systematically called.
	 */
	protected def isOptimallySimplified(ReduceExpression re) {
		if (!(re.eContainer instanceof StandardEquation)) {
			throw new Exception('Reduction has not been normalized: ' + Show.print(re.getContainerEquation))
		}
		val eq = re.getContainerEquation as StandardEquation
		val lhsDim = eq.variable.domain.nbIndices
		val rhsDim = re.body.contextDomain.dimensionality
		lhsDim >= rhsDim
	}
	
	/**
	 * Side effect free transformations are repeatedly applied until convergence.
	 * 
	 */
	protected def sideEffectFreeTransformations(SystemBody body, String eqName) {
		val eq = body.getStandardEquation(eqName)

		SplitUnionIntoCase.apply(body)
		PermutationCaseReduce.apply(body)
		val nrCount = NormalizeReduction.apply(body)
		Normalize.apply(body)
		
		if (nrCount > 0) return false;
		
		//If earlier transformation has exposed non-reduce expressions,
		// then DP must recurse on new equations after NormalizeReduction
		if (!(eq.expr instanceof AbstractReduceExpression)) return true;
		
		if (SameOperatorSimplification.apply(eq.expr as ReduceExpression) > 0) return false;
		if (Distributivity.apply(eq.expr as ReduceExpression) > 0) return false;
		
		return true;
	}
	
	/**
	 * Creates a list of possible transformations that are valid steps in the DP.
	 * 
	 */
	protected def enumerateCandidates(AbstractReduceExpression targetRE) {
		val nbParams = targetRE.expressionDomain.nbParams
		val SSAR = ShareSpaceAnalysis.apply(targetRE)
		
		val candidates = new LinkedList<DynamicProgrammingStep>();
		
		// SimplifyingReductions if body is bigger than answer 
		val answerDim = (targetRE.getContainerEquation as StandardEquation).variable.domain.dimensionality
		val bodyDim = targetRE.body.contextDomain.dimensionality
		if (bodyDim > answerDim) {
			val vectors = SimplifyingReductions.generateCandidateReuseVectors(targetRE, SSAR);
			for (vec : vectors) {
				candidates.add(new StepSimplifyingReduction(targetRE, vec, nbParams));
			}
		}
		
		// Idempotent
		if (Idempotence.testLegality(targetRE, SSAR)) {
			candidates.add(new StepIdempotence(targetRE));
		}
		
		// Higher-Order Operator
		if (HigherOrderOperator.testLegality(targetRE, SSAR)) {
			candidates.add(new StepHigherOrderOperator(targetRE));
		}
		
		// Decomposition with side-effects
		for (pair : SimplifyingReductions.generateDecompositionCandidates(SSAR, targetRE)) {
			candidates.add(new StepReductionDecomposition(targetRE, pair.key, pair.value))
		}
		
		return candidates;
	}
	

	protected dispatch def applyDPStep(ReduceExpression re, StepSimplifyingReduction step) {
		SimplifyingReductions.apply(re, step.reuseDepNoParams)
	}
	protected dispatch def applyDPStep(ReduceExpression re, StepIdempotence step) {
		Idempotence.apply(re)
	}
	protected dispatch def applyDPStep(ReduceExpression re, StepHigherOrderOperator step) {
		HigherOrderOperator.apply(re)
	}
	protected dispatch def applyDPStep(ReduceExpression re, StepReductionDecomposition step) {
		ReductionDecomposition.apply(re, step.innerProjection, step.outerProjection)
		NormalizeReduction.apply(re)
		Normalize.apply(systemBody)
	}
	protected dispatch def applyDPStep(AlphaExpression ae, DynamicProgrammingStep step) {
		// do nothing
	}
	
	static class State {
		SystemBody body
		List<DynamicProgrammingStep> steps
		
		new (SystemBody body, List<DynamicProgrammingStep> steps) {
			this.body = body
			this.steps = steps
		}
		
		def root() {
			body.getContainerRoot
		}
		
		def complexity() {
			body.complexity
		}
		
		def show() '''
			// Complexity: «complexity»D
			«(0..<steps.size).map[i | 
				val indent = '// ' + (0..<i).map['+--'].join + '+-- '
				indent + steps.get(i).description
			].join('\n')»
			«Show.print(body.system)»
		'''
	}
	
	static abstract class DynamicProgrammingStep {
		protected val EList<Integer> nodeID
		protected AbstractReduceExpression re;
		
		new (AbstractReduceExpression targetRE) {
			nodeID = targetRE.nodeID
			re = targetRE	
		}
		
		abstract def String description();
	}
	
	static class StepBeginEquation extends DynamicProgrammingStep  {
		
		new(AbstractReduceExpression targetRE) {
			super(targetRE)
		}
		
		override description() {
			val eq = re.getContainerEquation
			val eqVarName = (eq instanceof StandardEquation) ? (eq as StandardEquation).variable.name : null 
			val toEqStr = (eqVarName !== null) ? 'to %s'.format(eqVarName) : ''
			String.format("Optimize equation %s", toEqStr);
		}
	}
	
	static class StepSimplifyingReduction extends DynamicProgrammingStep  {
		long[] reuseDepNoParams;
		
		new(AbstractReduceExpression targetRE, long[] reuseDepNoParams, int nbParams) {
			super(targetRE)
			this.reuseDepNoParams = reuseDepNoParams
		}
		
		override description() {
			val eq = re.getContainerEquation
			val eqVarName = (eq instanceof StandardEquation) ? (eq as StandardEquation).variable.name : null 
			val toEqStr = (eqVarName !== null) ? ' to %s'.format(eqVarName) : ''
			String.format("Apply SimplifyingReduction%s with: %s", toEqStr, MatrixOperations.toString(reuseDepNoParams));
		}
		
		def getReuseDepNoParams() {
			reuseDepNoParams
		}
	}
		
	static class StepIdempotence extends DynamicProgrammingStep {
		
		new(AbstractReduceExpression targetRE) {
			super(targetRE)
		}
		
		override description() {
			String.format("Apply Idempotence");
		}
	}
	
	static class StepHigherOrderOperator extends DynamicProgrammingStep {
		
		new(AbstractReduceExpression targetRE) {
			super(targetRE)
		}
		
		override description() {
			String.format("Apply HigherOrderOperator");
		}
	}
	
	static class StepReductionDecomposition extends DynamicProgrammingStep {
		
		ISLMultiAff innerProjection
		ISLMultiAff outerProjection
		
		ISLMultiAff _inner
		ISLMultiAff _outer
		
		new(AbstractReduceExpression targetRE, ISLMultiAff innerF, ISLMultiAff outerF) {
			super(targetRE)
			innerProjection = innerF;
			outerProjection = outerF;
			_inner = innerF.copy
			_outer = outerF.copy
		}
		
		override description() {
			val eq = re.getContainerEquation
			val eqVarName = (eq instanceof StandardEquation) ? (eq as StandardEquation).variable.name : null 
			val toEqStr = (eqVarName !== null) ? ' to %s'.format(eqVarName) : ''
			String.format("Apply ReductionDecomposition%s with %s o %s", toEqStr, _outer, _inner);
		}
	}
	
	static class ThrottleException extends Exception {
		
	}
	
	static def writeToFile(String blob, String fileName) {
	    val writer = new BufferedWriter(new FileWriter(fileName));
	    writer.write(blob);
	    
	    writer.close();
	}
	
}