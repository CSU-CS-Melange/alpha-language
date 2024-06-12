package alpha.model.transformation.automation

import alpha.model.AbstractReduceExpression
import alpha.model.AlphaExpression
import alpha.model.AlphaRoot
import alpha.model.AlphaSystem
import alpha.model.ReduceExpression
import alpha.model.StandardEquation
import alpha.model.SystemBody
import alpha.model.analysis.reduction.CandidateReuse
import alpha.model.analysis.reduction.ShareSpaceAnalysis
import alpha.model.matrix.MatrixOperations
import alpha.model.transformation.Normalize
import alpha.model.transformation.SplitUnionIntoCase
import alpha.model.transformation.reduction.Distributivity
import alpha.model.transformation.reduction.FractalSimplify
import alpha.model.transformation.reduction.HigherOrderOperator
import alpha.model.transformation.reduction.Idempotence
import alpha.model.transformation.reduction.NormalizeReduction
import alpha.model.transformation.reduction.PermutationCaseReduce
import alpha.model.transformation.reduction.ReductionComposition
import alpha.model.transformation.reduction.ReductionDecomposition
import alpha.model.transformation.reduction.RemoveIdenticalAnswers
import alpha.model.transformation.reduction.SameOperatorSimplification
import alpha.model.transformation.reduction.SimplifyingReductions
import alpha.model.transformation.reduction.SplitReduction
import alpha.model.util.AlphaUtil
import alpha.model.util.Face
import alpha.model.util.Show
import fr.irisa.cairn.jnimap.isl.ISLConstraint
import fr.irisa.cairn.jnimap.isl.ISLMultiAff
import fr.irisa.cairn.jnimap.isl.ISLSet
import java.util.LinkedList
import java.util.List
import java.util.Map
import org.eclipse.emf.common.util.EList
import org.eclipse.emf.ecore.util.EcoreUtil

import static extension alpha.model.ComplexityCalculator.complexity
import static extension alpha.model.analysis.reduction.ReductionUtil.isSimilar
import static extension alpha.model.transformation.reduction.SplitReduction.requiresFractalSplits
import static extension alpha.model.util.AlphaOperatorUtil.hasInverse
import static extension alpha.model.util.AlphaOperatorUtil.hasNoInverse
import static extension alpha.model.util.AlphaUtil.getContainerEquation
import static extension alpha.model.util.AlphaUtil.getContainerRoot
import static extension alpha.model.util.AlphaUtil.getContainerSystemBody
import static extension alpha.model.util.ISLUtil.dimensionality
import static extension java.lang.String.format

/**
 * Implements Algorithm 2 in the Simplifying Reductions paper. The current
 * implementation does not prune the search space. It simply explores all possible
 * combinations of reduction decomposition, dependence raising, and simplification
 * until termination or it finds the specified (via throttleLimit) number of 
 * optimized versions, whichever comes first.
 * 
 */
class OptimalSimplifyingReductions {
	
	
	private def void debug(String msg) {
		if (verbose)
			println("[OptimalSimplifyingReductions] " + msg)
	}
	
	protected AlphaRoot root
	protected AlphaSystem system
	protected SystemBody systemBody
	protected int systemBodyID
	protected String originalSystemName
	protected int initialComplexity
	protected boolean throttle
	protected int throttleLimit
	protected long optimizationNum
	protected int targetComplexity
	protected boolean trySplitting
	protected boolean verbose
	
	/**
	 * This maps contains the simplified versions of the program obtained
	 * during exploration. Simplified versions are grouped by complexity.
	 * The program versions of complexity O(N^k) reside in the list mapped to
	 * by the key k.
	 */
	public Map<Integer, List<State>> optimizations
	
	/**
	 * Data structure to keep track of 2-faces used to detect fractal simplification
	 */
	Map<AbstractReduceExpression, Face> explored2Faces
	
	/**
	 * Creates an OSR instance and initializes exploration space parameters 
	 */
	protected new (AlphaSystem system, int limit, int complexity, boolean trySplitting, boolean verbose) {
		if (system.systemBodies.size > 1) {
			throw new IllegalArgumentException("AlphaSystems with multiple system bodies is not yet supported.")
		}
		this.root = EcoreUtil.copy(AlphaUtil.getContainerRoot(system))
		this.system = this.root.getSystem(system.fullyQualifiedName)
		this.systemBodyID = 0
		this.systemBody = this.system.systemBodies.get(this.systemBodyID)
		this.optimizations = newHashMap
		this.originalSystemName = this.system.name
		this.optimizationNum = 0
		this.initialComplexity = this.systemBody.complexity
		this.throttle = limit > 0
		this.throttleLimit = limit
		this.targetComplexity = complexity
		this.trySplitting = trySplitting
		this.verbose = verbose
		this.explored2Faces = newHashMap
	}
	
	/** 
	 * Entry points to the optimal simplification algorithm.
	 * If no limit is specified, then it will explore all possible simplifications.
	 */
	static def apply(AlphaSystem system, int limit, int targetComplexity, boolean trySplitting, boolean verbose) {
		val osr = new OptimalSimplifyingReductions(system, limit, targetComplexity, trySplitting, verbose)
		osr.run
		return osr
	}
	
	/** 
	 * Preprocess the input program. After preprocessing all reductions are
	 * normalized and the body of every reduction is a single convex polyhedron
	 * (i.e., an ISLSet with a single ISLBasicSet). This is achieved via 
	 * SplitUnionIntoCase, PermutationCaseReduce, and NormalizeReduction.
	 * 
	 * The State object is a wrapper around the AlphaRoot object and used to 
	 * maintain a correspondence between the sequence of dynamic programming steps
	 * performed and transformed program.
	 */
	protected def preprocessing() {
		ReductionComposition.apply(systemBody)
		SplitUnionIntoCase.apply(systemBody)
		PermutationCaseReduce.apply(systemBody)
		NormalizeReduction.apply(systemBody)
		Normalize.apply(systemBody)
		
		debug('After preprocessing:')
		debug(Show.print(systemBody))
		
		val state = new State(systemBody, newLinkedList)
		
		(0..<state.complexity).forEach[i | optimizations.put(i, newLinkedList)]
		
		return state
	}
	
	/**
	 * Entry point to the algorithm. The current implementation maintains a list
	 * of unexplored equations for each program state. Given an initial preprocessed
	 * program state, we begin by optimizing equations one at a time.
	 * 
	 */
	private def run() {
		val state = preprocessing
		
		try {
			state.optimizeUnexploredEquations
		} catch (ThrottleException e) {
			debug('Throttled search to stop after ' + throttleLimit + ' results')
		}
	}
	
	/**
	 * optimizeUnexploredEquations and optimizeEquation together carry out the recursive simplification
	 * algorithm. The given program state is optimized while it has unexplored equations. Each equation
	 * has a boolean flag property to indicate whether it has been explored. Any new equations that
	 * are introduced during the optimization are effectively queued because they have not yet been 
	 * marked as explored.
	 * 
	 * After the program state has been optimized (i.e., all equations have been explored), if the 
	 * state complexity is smaller than the initialComplexity then the state is added to the list of
	 * optimized versions.
	 */
	private def void optimizeUnexploredEquations(State state) {
		while (state.body.hasUnexploredEquations) {
			val eq = state.body.nextUnexploredEquation
			debug('optimizing equation ' + eq.variable.name) 
			debug(state.show.toString)
			eq.optimizeEquation(eq.expr, state)
		}
		
		val stateComplexity = state.complexity
		if (stateComplexity == targetComplexity) {
			println('''[alpha]: found simplification/v«optimizationNum»/«system.name».alpha''')
			optimizationNum++
			state.addToOptimzations
			if (throttle && optimizationNum >= throttleLimit)
				throw new ThrottleException
		}
	}
	
	/** 
	 * optimizeEquation is implemented as a dispatch method, because only equations involving
	 * reduce expressions need to be recursively explored.
	 */
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
		
		// Enumerate the list of candidate dynamic programming steps
		val candidates = enumerateCandidates(targetEq.expr as ReduceExpression)
		candidates.forEach[c |
			debug("candidate: " + c.description)
		]
		
		// Explore each step recursively. For each step, create a new state by applying the step.
		// Then recursively call optimizeUnexploredEquations for the new state
		for (step : candidates) {
			val optimizedRoot = EcoreUtil.copy(containerSystemBody.getContainerRoot)
			val optimizedBody = optimizedRoot.getSystem(originalSystemName).systemBodies.get(systemBodyID)
			val optimizedEq = optimizedRoot.getEquation(targetEq.name)
			optimizedEq.expr.applyDPStep(step)
			optimizedEq.explored = optimizedEq.expr.isNotReduceExpr
			val steps = newLinkedList
			steps.addAll(state.steps)
			steps += step
			val newState = new State(optimizedBody, steps)
			optimizeUnexploredEquations(newState)
		}
		
		// At this point, the recursion has returned for the current state of the equation eq and
		// eq is marked as explored
		eq.setExplored
	}
	private def dispatch void optimizeEquation(StandardEquation eq, AlphaExpression ae, State state) {
		eq.explored = true
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
		
		// If earlier transformation has exposed non-reduce expressions,
		// then DP must recurse on new equations after NormalizeReduction
		if (!(eq.expr instanceof AbstractReduceExpression)) return true;
		
		if (SameOperatorSimplification.apply(eq.expr as ReduceExpression) > 0) return false;
		if (Distributivity.apply(eq.expr as ReduceExpression) > 0) return false;
		
		return true;
	}
	
	/** 
	 * Return true if the dimensionality of the reduction body is bigger than the dimensionality
	 * of the variable on the LHS of the containing equation, and false otherwise
	 */
	private def shouldSimplify(AbstractReduceExpression are) {
		val answerDim = (are.getContainerEquation as StandardEquation).variable.domain.dimensionality
		val bodyDim = are.body.contextDomain.dimensionality
		return bodyDim > answerDim
	}
	
	/**
	 * Return true if shouldSimplify returns true and the reduction operator does not admit an inverse
	 */
	private def shouldSplit(AbstractReduceExpression are, boolean shouldSimplify) {
		trySplitting && shouldSimplify && are.operator.hasNoInverse
	}
	
	/**
	 * Tests if this reduce expression needs to be fractal simplified. If all of the 
	 * following hold for the reduction body D:
	 *   1) the reduction operator does not admit an inverse
	 *   2) D is 2-dimensional
	 *   3) D is geometrically similar to a previously encountered reduction (re') body
	 * 
	 * Then the similar reduction re' is returned, else null is returned.
	 */
	private def AbstractReduceExpression fractalSimplification(AbstractReduceExpression are) {
		
		val varName = (are.getContainerEquation as StandardEquation).variable.name 
		debug('Testing if equation ' + varName + ' needs fractal simplification')

		val facet = are.facet
		
		// Fractal simplification is only required on 2-faces when the reduction
		// operator does not admit an inverse
		if (are.operator.hasInverse) 
			return null
		
		if (varName == 'Y_NR4')
			println
		// check if we have encountered a "similar" 2D reduction body before
		val similarExplored2Face = explored2Faces.entrySet.findFirst[es | 
			are.isSimilar(es.key)
		]
		if (similarExplored2Face !== null) {
			val largerReduceExpr = similarExplored2Face.key
			val similarVarName = (similarExplored2Face.key.getContainerEquation as StandardEquation).variable.name
			debug(varName + ' is similar to previously encountered reduction ' + similarVarName)
			return largerReduceExpr
		}
		
		// if not add the pair to previouslySeen and return false
		explored2Faces.put(are, facet)
		debug(varName + ' is NOT similar to any previously encountered reductions')
		return null
	}
	
	/** 
	 * Creates a list of possible transformations that are valid steps in the DP
	 * 
	 */
	protected def enumerateCandidates(AbstractReduceExpression targetRE) {
		val nbParams = targetRE.expressionDomain.nbParams
		val SSAR = ShareSpaceAnalysis.apply(targetRE)
		
		val candidates = new LinkedList<DynamicProgrammingStep>();
		
		// SimplifyingReductions 
		val shouldSimplify = targetRE.shouldSimplify
		if (shouldSimplify) {
			val candidateReuse = new CandidateReuse(targetRE, SSAR)
			if (candidateReuse.hasIdenticalAnswers) {
				/*
				 * If identical answers are found, then do not continue processing other DP steps. 
				 * This is a special case of simplification.Add single step to remove the identical 
				 * answers and return.
				 */
				val maff = candidateReuse.identicalAnswerBasis
				val domain = candidateReuse.identicalAnswerDomain
				return #[new StepRemoveIndenticalAnswers(targetRE, maff, domain)]
			} else {
				candidates.addAll(candidateReuse.vectors.map[vec | new StepSimplifyingReduction(targetRE, vec, nbParams)])
			}
		}
		
		// Fractal Simplification
		val largerRE = targetRE.fractalSimplification
		if (largerRE !== null) {
			/*
			 * Fractal simplification can only take place on 2-faces as a final step. No additional
			 * steps should be processed.
			 */
			return #[new StepFractalSimplify(targetRE, largerRE)]
		}
		
		// Splitting
		if (targetRE.shouldSplit(shouldSimplify) && candidates.empty) {
			if (targetRE.requiresFractalSplits) {
				candidates.add(new StepSplitReduction(targetRE))
			} else {				
				val splits = SplitReduction.enumerateCandidateSplits(targetRE)
				candidates.addAll(splits.map[split | new StepSplitReduction(targetRE, split)])
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
		val decompositionCandidates = SimplifyingReductions.generateDecompositionCandidates(SSAR, targetRE)
		for (pair : decompositionCandidates) {
			candidates.add(new StepReductionDecomposition(targetRE, pair.key, pair.value))
		}
		
		return candidates;
	}
	
	////////////////////////////////////////////////////////////
	// Dynamic programming step application methods
	////////////////////////////////////////////////////////////

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
		ReductionDecomposition.apply(re, step.innerProjection.copy, step.outerProjection.copy)
		NormalizeReduction.apply(re.getContainerEquation)
		Normalize.apply(systemBody)
	}
	protected dispatch def applyDPStep(ReduceExpression re, StepSplitReduction step) {
		val equation = re.getContainerEquation
		SplitReduction.apply(re, step.split)
		// re is no longer contained in the AST
		NormalizeReduction.apply(equation)
	}
	protected dispatch def applyDPStep(ReduceExpression re, StepRemoveIndenticalAnswers step) {
		RemoveIdenticalAnswers.transform(re, step.identicalAnswerBasis, step.identicalAnswerDomain)
	}
	protected dispatch def applyDPStep(ReduceExpression re, StepFractalSimplify step) {
		FractalSimplify.transform(re, step.largerReduceExpr)
	}
	protected dispatch def applyDPStep(AlphaExpression ae, DynamicProgrammingStep step) {
		// do nothing
	}
	
	////////////////////////////////////////////////////////////
	// Dynamic programming step definitions
	////////////////////////////////////////////////////////////
	
	static abstract class DynamicProgrammingStep {
		protected val EList<Integer> nodeID
		protected AbstractReduceExpression re;
		
		new (AbstractReduceExpression targetRE) {
			nodeID = targetRE.nodeID
			re = targetRE	
		}
		
		def String toEqStr() {
			val eq = re.getContainerEquation
			val eqVarName = (eq instanceof StandardEquation) ? (eq as StandardEquation).variable.name : null 
			(eqVarName !== null) ? ' to %s'.format(eqVarName) : ''
		}
		
		abstract def String description();
	}
	
	static class StepBeginEquation extends DynamicProgrammingStep  {
		new(AbstractReduceExpression targetRE) {
			super(targetRE)
		}
		
		override description() {
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
		
		new(AbstractReduceExpression targetRE, ISLMultiAff innerF, ISLMultiAff outerF) {
			super(targetRE)
			innerProjection = innerF;
			outerProjection = outerF;
		}
		
		override description() {
			String.format("Apply ReductionDecomposition%s with %s o %s", toEqStr, outerProjection, innerProjection);
		}
	}
	
	static class StepSplitReduction extends DynamicProgrammingStep {
		ISLConstraint split
		new(AbstractReduceExpression targetRE) {
			super(targetRE)
		}
		new(AbstractReduceExpression targetRE, ISLConstraint split) {
			super(targetRE)
			this.split = split
		}
		
		override description() {
			if (split !== null) {
				String.format("Apply SplitReduction%s with %s", toEqStr, split)
			} else {
				String.format("Apply SplitReduction%s", toEqStr)
			}
		}
	}
	
	static class StepRemoveIndenticalAnswers extends DynamicProgrammingStep {
		ISLMultiAff identicalAnswerBasis
		ISLSet identicalAnswerDomain
		new(AbstractReduceExpression targetRE, ISLMultiAff identicalAnswerBasis, ISLSet identicalAnswerDomain) {
			super(targetRE)
			this.identicalAnswerBasis = identicalAnswerBasis
			this.identicalAnswerDomain = identicalAnswerDomain
		}
		
		override description() {
			String.format("Apply RemoveIdenticalAnswers %s with %s", toEqStr, identicalAnswerBasis);
		}
	}
	
	static class StepFractalSimplify extends DynamicProgrammingStep {
		AbstractReduceExpression largerReduceExpr
		new(AbstractReduceExpression targetRE, AbstractReduceExpression largerReduceExpr) {
			super(targetRE)
			this.largerReduceExpr = largerReduceExpr
		}
		
		override description() {
			val largerEqName = (largerReduceExpr.getContainerEquation as StandardEquation).variable.name
			String.format("Apply FractalSimplify%s with %s", toEqStr, largerEqName);
		}
	}
	
	////////////////////////////////////////////////////////////
	// Miscellaneous helper inner classes
	////////////////////////////////////////////////////////////
	
	/** 
	 * Exception used to indicate that the exploration should stop. The current implementation
	 * does not implement any pruning of the search space. If/when pruning is introduced, this 
	 * should probably be removed.
	 */
	static class ThrottleException extends Exception {}
	
	/**
	 * This class is used to maintain the correspondence between the current state of the program
	 * and the history of dynamic programming steps taken to get there. This is the main object 
	 * that is passed around during the recursive simplification.
	 */
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
		
		def showSteps() {
			(0..<steps.size).map[i | 
				val indent = '// ' + (0..<i).map['+--'].join + '+-- '
				indent + steps.get(i).description
			].join('\n')
		}
		
		def show() '''
			// Complexity: «complexity»D
			«showSteps»
			«Show.print(body.system)»
		'''
	}
	
	////////////////////////////////////////////////////////////
	// Miscellaneous helper functions
	////////////////////////////////////////////////////////////
	
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
	
	/** 
	 * Returns true if the dimensionality reduce expression's body's context domain is the same
	 * as the dimensionality of the variable on the LHS of the container equation.
	 * The parent of the expression is guaranteed to be a Standard Equation since NormalizeReduction
	 * has been systematically called.
	 */
	protected def isOptimallySimplified(ReduceExpression re) {
		if (!(re.eContainer instanceof StandardEquation)) {
			throw new Exception('Reduction has not been normalized: ' + Show.print(re.getContainerEquation))
		}
		val eq = re.getContainerEquation as StandardEquation
		val lhsDim = eq.variable.domain.dimensionality
		val rhsDim = re.body.contextDomain.dimensionality
		lhsDim >= rhsDim
	}
	
	/** Returns true if expr is not a ReduceExpression */
	private def static isNotReduceExpr(AlphaExpression expr) {
		!(expr instanceof ReduceExpression)
	}
	
	/** Add the state to the appropriate list of optimizations */
	private def addToOptimzations(State state) {
		val opts = optimizations.get(state.complexity)
		opts += state
	}
	
	/** Returns true if the system body has at least one unexplored equation */
	private def hasUnexploredEquations(SystemBody body) {
		body.standardEquations.reject[explored].size > 0
	}
	
	/** Returns the first equation in the system body marked as unexplored */
	private def nextUnexploredEquation(SystemBody body) {
		body.standardEquations.findFirst[eq | !eq.explored]
	}
}