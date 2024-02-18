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
import alpha.model.util.AShow
import alpha.model.util.AlphaUtil
import alpha.model.util.Show
import fr.irisa.cairn.jnimap.isl.ISLMultiAff
import java.util.LinkedList
import java.util.List
import java.util.Set
import java.util.TreeSet
import org.eclipse.emf.common.util.EList
import org.eclipse.emf.ecore.util.EcoreUtil

import static extension alpha.model.util.AlphaUtil.getContainerEquation
import static extension alpha.model.util.ISLUtil.dimensionality
import static extension java.lang.String.format
import static extension org.eclipse.xtext.EcoreUtil2.getAllContentsOfType

/**
 * Implements Algorithm 2 in the Simplifying Reductions paper.
 * 
 * This implementation is incomplete in the following way:
 *  - The input program is assumed to be bounded. There are
 * a number of places in the original paper that discuss
 * a linear space (named L_P) = rays in vertex representation.
 * Cases where this linear space is meaningful are not supported.
 * 
 */
class SimplifyingReductionOptimalSimplificationAlgorithm {
	
	public static boolean DEBUG = false
	
	public static boolean THROTTLE = true
	public static int THROTTLE_LIMIT = 1
	
	private def debug(String content) {
		if (DEBUG)
			System.out.println("[SROptimalSimplification] " + content)
	}
	private def debug(String content, ProgramState state) {
		debug(content)
		if (DEBUG)
			System.out.println(AShow.print(state.body))
	}
	
	protected final  AlphaRoot originalProgram;
	protected final String systemName;
	protected final  int systemBodyID;
		
	protected new (SystemBody body) {
		originalProgram = AlphaUtil.getContainerRoot(body);
		systemName = body.system.fullyQualifiedName
		systemBodyID = body.system.systemBodies.indexOf(body)
	}
	
	static def apply(AlphaSystem system) {
		if (system.systemBodies.size == 1)
			apply(system.systemBodies.get(0))
		else
			throw new IllegalArgumentException("A SystemBody must be specified for an AlphaSystem with multiple bodies.");
	}
	
	static def apply(SystemBody body) {
		val SROSA = new SimplifyingReductionOptimalSimplificationAlgorithm(body);
		SROSA.run()
		return SROSA
	}
	
	/**
	 * Entry point to the algorithm
	 * 
	 */
	private def run() {
		val state = preprocessing();
		
		debug("After Preprocessing", state)
		val DPcontext = new DynamicProgrammingContext(state);
		val optimizedStates = exploreDPcontext(DPcontext)
		val optimizedState = optimizedStates.get(0)
		
		println('Complexity per equation:')
		optimizedState.getBody.equations
			.filter[eq | eq instanceof StandardEquation]
			.map[eq | eq as StandardEquation].forEach[eq |
				if (eq.expr instanceof ReduceExpression) {
					println((eq.expr as ReduceExpression).body.contextDomain.dimensionality + 'D -> ' + eq.variable.name)
				} else {
					println(eq.variable.domain.dimensionality + 'D -> ' + eq.variable.name)
				}
			]
		
		println
		println('Optimized complexity: ' + optimizedState.getBody.complexity + 'D')
		println(Show.print(optimizedState.getSystem))
		
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
	
	/**
	 * Preprocessing involves exposing equations with reductions
	 * being the immediate child expression, and making sure 
	 * that the expression domain of each reduction body is a
	 * single polyhedron. These are the main assumptions made
	 * in the SR algorithm. 
	 * 
	 */
	protected def preprocessing() {
		val copyProg = EcoreUtil.copy(originalProgram);
		val copySystem = copyProg.getSystem(systemName)
		val copyBody = copySystem.systemBodies.get(systemBodyID);
		
		ReductionComposition.apply(copyBody)
		SplitUnionIntoCase.apply(copyBody)
		PermutationCaseReduce.apply(copyBody)
		NormalizeReduction.apply(copyBody)
		Normalize.apply(copyBody)
		
		
		
		new ProgramState(copyProg);
	}
	
	/**
	 * The algorithm optimizes each equation one by one. There are some 
	 * cases where the order and choice of reuse vectors influences schedulability,
	 * but this is not considered in the current implementation.
	 */
	private def exploreDPcontext(DynamicProgrammingContext DPcontext) {
		val context = DPcontext.copy
		while (context.hasNext) {
			val eq = context.getNext
			if (eq.variable.name == 'Y_neg_NR1')
				println
			val optimizations = optimizeEquation(context, eq)
			val remaining = context.getAll.map[variable.name].toString
			val explored = context.exploredEquations.toList.toString
			println('optimized equation: ' + eq.variable.name)
			println('          explored: ' + explored)
			println('         remaining: ' + remaining)
			if (optimizations.isEmpty) {
				println('--> no further optimizations')
				return optimizations
			}
			println('--> # of optimizations: ' + optimizations.size)
			print('')
			optimizations.forEach[os | 
//				println(Show.print(os.getBody))
				os.getBody.equations.filter[e | e instanceof StandardEquation].map[e | e as StandardEquation].forEach[e |
					var flag = if (e.variable.name == eq.variable.name) '  <-- this step' else ''
					println('    (' + e.dimensionality + 'D) ' + e.variable.name + flag)
				]
				println
			]
			println
			println
			context.state = optimizations.get(0)
		}
		return #[context.state]
	}
	
	private def dimensionality(StandardEquation equ) {
		dimensionality(equ, equ.expr)
	}
	private def dispatch dimensionality(StandardEquation equ, ReduceExpression re) {
		re.body.contextDomain.dimensionality
	}
	private def dispatch dimensionality(StandardEquation equ, AlphaExpression ae) {
		equ.variable.domain.nbIndices
	}
	
	/**
	 * Optimization of an equation. This mostly corresponds to Algorithm 2.
	 * 
	 * The most tricky part of the algorithm is dealing with the number of equations
	 * that keeps changing. NormalizeReduction must be systematically applied in
	 * the automated setting, which makes it difficult to optimize a single equation.
	 * In this implementation, there is a data structure named DynamicProgrammingContext
	 * that keeps track of the equations that should be explored. This is used to only optimize
	 * an equation and new equations that are introduced during the process of optimizing this equation.
	 * 
	 * Returns true if equation was completely optimized (all dimensions are reuse have been
	 * exploited), or false otherwise
	 */
	private def List<ProgramState> optimizeEquation(DynamicProgrammingContext context, StandardEquation eq) {
		if (!(eq.expr instanceof ReduceExpression)) {
			debug(String.format("Not an Equation with ReduceExpression: %s", eq.variable.name))
			context.markFinishedEquation(eq)
			return #[context.state]
		}
		
		// The reduce expression is optimally simplified 
		if ((eq.expr as ReduceExpression).isOptimallySimplified) {
			debug(String.format("Finished Exploring Equation: %s", eq.variable.name))
			context.markFinishedEquation(eq)
			return #[context.state]
		}
		
		//The child context has all but the target equation put in the excluded list
		//This is to explore only the equations added as a result of transforming the target equation
		val childContext = context.copy
		childContext.excludedEquations.addAll(context.state.body.standardEquations.filter[e|e != eq].map[e|e.variable.name])
		
		while(!sideEffectFreeTransformations(childContext.state.body, eq.variable.name)) {}
		
		debug("After Side-Effect Free Transformations", childContext.state)
		
		val targetEq = childContext.state.body.getStandardEquation(eq.variable.name)
		
		//If side-effect free transformations caused the child of target equation to be
		// something other than ReduceExpression, then recurse on the new equations 
		if (!(targetEq.expr instanceof ReduceExpression)) {
			debug(String.format("Finished Exploring Equation: %s", eq.variable.name))
			childContext.markFinishedEquation(eq)
			exploreDPcontext(childContext)
			context.markFinishedEquation(eq)
			return #[childContext.state]
		}
		
		val targetRE = targetEq.expr as ReduceExpression
		if (eq.variable.name == 'Y_neg_NR1')
			println
		val candidates = enumerateCandidates(targetRE)
		
		debug(String.format("Number of DP step candidates: %d", candidates.size))
		
		val allOptimizations = newLinkedList
		
		//Apply the DP step to any candidates and recurse
		for (step : candidates) {
			debug(String.format("Applying Step: %s", step.description))
			val child = childContext.copy
			child.step = step
			child.applyDPStep(step)
			val optimizations = exploreDPcontext(child)
			allOptimizations.addAll(optimizations)
		}
		
		context.markFinishedEquation(eq)
		
		if (allOptimizations.isEmpty)
			println()
		
		return allOptimizations
	}
	
	/** 
	 * Returns true if the dimensionality reduce expression's body's context domain is the same
	 * as the dimensionality of the LHS of the container equation.
	 * The parent of the expression is guaranteed to be a Standard Equation since NormalizeReduction
	 * has been systematically called.
	 */
	protected def isOptimallySimplified(ReduceExpression re) {
		if (!(re.eContainer instanceof StandardEquation)) {
			throw new Exception('Reduction has not been normalized: ' + Show.print(re))
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
		
		if (THROTTLE) {
			val nbCandidates = candidates.size
			return candidates.subList(0, THROTTLE_LIMIT < nbCandidates ? THROTTLE_LIMIT : nbCandidates)
		}
		
		return candidates;
	}
	
	private def hasNext(DynamicProgrammingContext context) {
		context.state.body.standardEquations.exists[e|!context.excludedEquations.contains(e.variable.name)]
	}
	private def getNext(DynamicProgrammingContext context) {
		context.state.body.standardEquations.findFirst[e|!context.excludedEquations.contains(e.variable.name)]
	}	
//	private def getEquation(DynamicProgrammingContext context, String name) {
//		context.state.body.standardEquations.findFirst[e|e.variable.name == name]
//	}
	private def getAll(DynamicProgrammingContext context) {
		context.state.body.standardEquations.filter[e|!context.excludedEquations.contains(e.variable.name)].toList
	}
//	private def excludeExploredEquationsInChildContext(DynamicProgrammingContext context, DynamicProgrammingContext childContext) {
//		childContext.exploredEquations.filter[e|context.state.body.getStandardEquation(e) !== null].forEach[e|context.markFinishedEquation(e)]
//	}
	
	protected static class DynamicProgrammingContext {
		protected ProgramState state;
		protected Set<String> excludedEquations = new TreeSet<String>();
		protected Set<String> exploredEquations = new TreeSet<String>();
		protected DynamicProgrammingStep step;
		protected boolean result
		
		new(ProgramState state) {
			this.state = state
			this.result = true
		}
		
		new(ProgramState state, DynamicProgrammingStep step) {
			this(state)
			this.step = step
		}
		
		protected def markFinishedEquation(String eqName) {
			excludedEquations.add(eqName)
			exploredEquations.add(eqName)
		}
		
		protected def markFinishedEquation(StandardEquation eq) {
			excludedEquations.add(eq.variable.name)
			exploredEquations.add(eq.variable.name)
		}
		
		def copy() {
			val copy = new DynamicProgrammingContext(state.copy);
			
			for (ee : excludedEquations) 
				copy.excludedEquations.add(ee)
			
			return copy
		}
	}
	
	protected dispatch def applyDPStep(DynamicProgrammingContext context, StepSimplifyingReduction step) {
		val re = context.state.root.getNode(step.nodeID) as ReduceExpression
		SimplifyingReductions.apply(re, step.reuseDepNoParams)
		context.state.nbSR+=1;
	}
	protected dispatch def applyDPStep(DynamicProgrammingContext context, StepIdempotence step) {
		val re = context.state.root.getNode(step.nodeID) as ReduceExpression
		Idempotence.apply(re)
		context.state.nbSR+=1;
	}
	protected dispatch def applyDPStep(DynamicProgrammingContext context, StepHigherOrderOperator step) {
		val re = context.state.root.getNode(step.nodeID) as ReduceExpression
		HigherOrderOperator.apply(re)
		context.state.nbSR+=1;
	}
	protected dispatch def applyDPStep(DynamicProgrammingContext context, StepReductionDecomposition step) {
		val re = context.state.root.getNode(step.nodeID) as ReduceExpression
		ReductionDecomposition.apply(re, step.innerProjection, step.outerProjection)
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
			String.format("Apply ReductionDecomposition with %s o %s", _outer, _inner);
		}
	}
	
	private def getSystem(ProgramState state) {
		state.root.getSystem(systemName)
	}
	private def getBody(ProgramState state) {
		state.getSystem().systemBodies.get(systemBodyID)
	}
}