package alpha.model.transformation.automation

import alpha.model.AbstractReduceExpression
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
import fr.irisa.cairn.jnimap.isl.ISLMultiAff
import java.util.ArrayList
import java.util.LinkedList
import java.util.Set
import java.util.TreeSet
import org.eclipse.emf.common.util.EList
import org.eclipse.emf.ecore.util.EcoreUtil
import java.util.List
import alpha.model.AlphaInternalStateConstructor

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
	
	public static boolean DEBUG = false;
	
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
	protected List<AlphaRoot> optimizedPrograms;
	
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
		SROSA.run();
		AlphaInternalStateConstructor.recomputeContextDomain(SROSA.optimizedPrograms)
		return SROSA.optimizedPrograms
	}
	
	/**
	 * Entry point to the algorithm
	 * 
	 */
	private def run() {
		val state = preprocessing();
		
		debug("After Preprocessing", state)
		val DPcontext = new DynamicProgrammingContext(state);
		exploreDPcontext(DPcontext)
		
		debug("After DP", DPcontext.state)
		val ls = DPcontext.leafStates.toList
		optimizedPrograms = DPcontext.leafStates.map[s|s.root]
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
	private def void exploreDPcontext(DynamicProgrammingContext DPcontext) {
		while (DPcontext.hasNext) {
			val eq = DPcontext.getNext
			debug(String.format("Optimizing Equation: %s", eq.variable.name))
			
			optimizeEquation(DPcontext, eq)
		}
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
	 */
	private def void optimizeEquation(DynamicProgrammingContext context, StandardEquation eq) {
		if (!(eq.expr instanceof ReduceExpression)) {
			debug(String.format("Not an Equation with ReduceExpression: %s", eq.variable.name))
			context.markFinishedEquation(eq)
			return;
		}
		
		//The child context has all but the target equation put in the excluded list
		//This is to explore only the equations added as a result of transforming the target equation
		val childContext = context.copy
		childContext.parent = context
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
			if (childContext.state.nbSR > context.state.nbSR) {
				context.state = childContext.state;
				context.excludeExploredEquationsInChildContext(childContext)
			}
			context.markFinishedEquation(eq)
			return
		}
		
		val candidates = enumerateCandidates(targetEq.expr as ReduceExpression)
		
		debug(String.format("Number of DP step candidates: %d", candidates.size))
		
		//If there are no candidates, base case of recursion. 
		if (candidates.isEmpty) {
			debug(String.format("Finished Exploring Equation: %s", eq.variable.name))
			childContext.markFinishedEquation(eq)
			exploreDPcontext(childContext)
			if (childContext.state.nbSR > context.state.nbSR) {
				context.state = childContext.state;
				context.excludeExploredEquationsInChildContext(childContext)
			}
			
			context.markFinishedEquation(eq)
			return
		}
		
		//Otherwise apply the DP step and
		for (step : candidates) {
			debug(String.format("Applying Step: %s", step.description))
			val child = childContext.copy
			child.applyDPStep(step)
			exploreDPcontext(child)
			context.children.add(child)
		}
		
		context.markFinishedEquation(eq)
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
		
		//SimplifyingReductions
		val vectors = SimplifyingReductions.generateCandidateReuseVectors(targetRE, SSAR);
		for (vec : vectors) {
			candidates.add(new StepSimplifyingReduction(targetRE, vec, nbParams));
		}
		
		//Idempotent
		if (Idempotence.testLegality(targetRE, SSAR)) {
			candidates.add(new StepIdempotence(targetRE));
		}
		
		//Higher-Order Operator
		if (HigherOrderOperator.testLegality(targetRE, SSAR)) {
			candidates.add(new StepHigherOrderOperator(targetRE));
		}
		
		//Decomposition with side-effects
		for (pair : SimplifyingReductions.generateDecompositionCandidates(SSAR, targetRE)) {
			candidates.add(new StepReductionDecomposition(targetRE, pair.key, pair.value))
		}
		
		return candidates;
	}
	
	private def hasNext(DynamicProgrammingContext context) {
		context.state.body.standardEquations.exists[e|!context.excludedEquations.contains(e.variable.name)]
	}
	private def getNext(DynamicProgrammingContext context) {
		context.state.body.standardEquations.findFirst[e|!context.excludedEquations.contains(e.variable.name)]
	}	
	private def excludeExploredEquationsInChildContext(DynamicProgrammingContext context, DynamicProgrammingContext childContext) {
		childContext.exploredEquations.filter[e|context.state.body.getStandardEquation(e) !== null].forEach[e|context.markFinishedEquation(e)]
	}
	
	protected static class DynamicProgrammingContext {
		protected ProgramState state;
		protected Set<String> excludedEquations = new TreeSet<String>();
		protected Set<String> exploredEquations = new TreeSet<String>();
		protected DynamicProgrammingContext parent;
		protected LinkedList<DynamicProgrammingContext> children;
		
		new (ProgramState state) {
			this.state = state;
			this.children = new LinkedList<DynamicProgrammingContext>
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
		
		def isLeaf() {
			children.empty
		}
	
		def List<ProgramState> leafStates() {
			if (isLeaf) {
				return #[state]
			}
			val states = new ArrayList<ProgramState>
			children.forEach[c|states.addAll(c.leafStates)]
			return states
		}
	
		def addChild(DynamicProgrammingContext child) {
			child.parent = this
			children.add(child)
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
	
	protected static abstract class DynamicProgrammingStep {
		protected val EList<Integer> nodeID
		
		new (AbstractReduceExpression targetRE) {
			nodeID = targetRE.nodeID	
		}
		
		abstract def String description();
	}
	
	protected static class StepSimplifyingReduction extends DynamicProgrammingStep  {
		long[] reuseDepNoParams;
		
		new(AbstractReduceExpression targetRE, long[] reuseDepNoParams, int nbParams) {
			super(targetRE)
			this.reuseDepNoParams = reuseDepNoParams
		}
		
		override description() {
			String.format("Apply SimplifyingReduction with: %s", MatrixOperations.toString(reuseDepNoParams));
		}
	}
		
	protected static class StepIdempotence extends DynamicProgrammingStep {
		
		new(AbstractReduceExpression targetRE) {
			super(targetRE)
		}
		
		override description() {
			String.format("Apply Idempotence");
		}
	}
	
	protected static class StepHigherOrderOperator extends DynamicProgrammingStep {
		
		new(AbstractReduceExpression targetRE) {
			super(targetRE)
		}
		
		override description() {
			String.format("Apply HigherOrderOperator");
		}
	}
	
	protected static class StepReductionDecomposition extends DynamicProgrammingStep {
		
		ISLMultiAff innerProjection
		ISLMultiAff outerProjection
		
		new(AbstractReduceExpression targetRE, ISLMultiAff innerF, ISLMultiAff outerF) {
			super(targetRE)
			innerProjection = innerF;
			outerProjection = outerF;
		}
		
		override description() {
			String.format("Apply ReductionDecomposition with %s o %s", outerProjection, innerProjection);
		}
	}
	
	private def getSystem(ProgramState state) {
		state.root.getSystem(systemName)
	}
	private def getBody(ProgramState state) {
		state.getSystem().systemBodies.get(systemBodyID)
	}
}