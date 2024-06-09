package alpha.model.transformation.reduction

import alpha.model.AbstractReduceExpression
import alpha.model.AlphaInternalStateConstructor
import alpha.model.AlphaSystem
import alpha.model.BINARY_OP
import alpha.model.ReduceExpression
import alpha.model.RestrictExpression
import alpha.model.StandardEquation
import alpha.model.SystemBody
import alpha.model.analysis.reduction.ShareSpaceAnalysisResult
import alpha.model.factory.AlphaUserFactory
import alpha.model.matrix.MatrixOperations
import alpha.model.transformation.Normalize
import alpha.model.transformation.PropagateSimpleEquations
import alpha.model.transformation.SimplifyExpressions
import alpha.model.transformation.SplitUnionIntoCase
import alpha.model.util.AffineFunctionOperations
import alpha.model.util.AlphaOperatorUtil
import alpha.model.util.AlphaUtil
import alpha.model.util.DomainOperations
import fr.irisa.cairn.jnimap.isl.ISLContext
import fr.irisa.cairn.jnimap.isl.ISLDimType
import fr.irisa.cairn.jnimap.isl.ISLMultiAff
import fr.irisa.cairn.jnimap.isl.ISLPoint
import fr.irisa.cairn.jnimap.isl.ISLSet
import fr.irisa.cairn.jnimap.isl.ISLSpace
import fr.irisa.cairn.jnimap.isl.ISLVal
import java.util.ArrayList
import java.util.Comparator
import java.util.LinkedList
import java.util.TreeSet
import java.util.function.Function
import org.eclipse.emf.ecore.util.EcoreUtil

import static extension alpha.model.util.AlphaUtil.getContainerSystemBody

/**
 * Implementation of Theorem 5 in the original Simplifying Reductions paper.
 * 
 * 
 */
class SimplifyingReductions {
	
	public static boolean DEBUG = false;

	/**
	 * Setting this variable to true disables all the
	 * post processing simplifications. Intended to be
	 * used for debugging purposes or to check the
	 * result of SR against the theorem in the paper. 
	 */
	public static boolean DISABLE_POST_PROCESSING = false;
	
	public static Function<SimplifyingReductions, String> defineXaddEquationName = [sr|
		val origName = sr.reductionEquation.variable.name
		var XaddName = origName + "_pos"

		AlphaUtil.duplicateNameResolver.apply(sr.containerSystem, XaddName, "_")
	]
	
	public static Function<SimplifyingReductions, String> defineXsubEquationName = [sr|
		val origName = sr.reductionEquation.variable.name
		var XaddName = origName + "_neg"

		AlphaUtil.duplicateNameResolver.apply(sr.containerSystem, XaddName, "_")
	]
	
	private static def void debug(String msg) {
		if (DEBUG)
			println("[SimplifyingReductions] " + msg)
	}
		
	val ReduceExpression targetReduce;
	val StandardEquation reductionEquation
	val ISLMultiAff reuseDep; 
	val AlphaSystem containerSystem;
	val SystemBody containerSystemBody;
	
	private new(ReduceExpression reduce, ISLMultiAff reuseDep) {
		targetReduce = reduce;
		reductionEquation = AlphaUtil.getContainerEquation(reduce) as StandardEquation
		this.reuseDep = reuseDep;
		containerSystem = AlphaUtil.getContainerSystem(targetReduce)
		containerSystemBody = AlphaUtil.getContainerSystemBody(targetReduce)
	}
	
	static def void apply(ReduceExpression reduce, ISLMultiAff reuseDep) {
		val sr = new SimplifyingReductions(reduce, reuseDep);
		sr.simplify
	}
	
	static def void apply(ReduceExpression reduce, int[] reuseDepNoParams) {
		apply(reduce, reuseDepNoParams.map[v|v as long])
	}
	static def void apply(ReduceExpression reduce, long[] reuseDepNoParams) {
		apply(reduce, reduce.longVecToMultiAff(reuseDepNoParams));
	}
	
	static def long[] asLongVector(ISLMultiAff reuseDep) {
		// reuseDep is a uniform function
		reuseDep.getAffs.map[aff | aff.getConstant]
	}
	
	protected static def ReduceExpression createXadd(AbstractReduceExpression reduceExpr, BasicElements BE) {
		val restrictDom = BE.origDE.copy.subtract(BE.DEp.copy)
		val restrictExpr = AlphaUserFactory.createRestrictExpression(restrictDom, EcoreUtil.copy(reduceExpr.body))
		return AlphaUserFactory.createReduceExpression(reduceExpr.operator, reduceExpr.projection, restrictExpr);
	}
	
	protected def void simplify() {
		val BE = computeBasicElements(targetReduce, reuseDep)
		
		//Xadd = reduce( op, proj, (DE - DE') : E )
		val XaddName = defineXaddEquationName.apply(this)
		var Xadd = null as ReduceExpression
		{
			Xadd = createXadd(targetReduce, BE)
			val restrictDom = (Xadd.body as RestrictExpression).restrictDomain
			
			val XaddDom = restrictDom.copy.apply(targetReduce.projection.toMap)
			val XaddVar = AlphaUserFactory.createVariable(XaddName, XaddDom)
			val XaddEq = AlphaUserFactory.createStandardEquation(XaddVar, Xadd);
			containerSystem.locals.add(XaddVar)
			containerSystemBody.equations.add(XaddEq)
			AlphaInternalStateConstructor.recomputeContextDomain(XaddEq)
		}
		
		//Xsub = reduce( op, proj, proj^-1(Dint) : (DE - DE') : E )
		val XsubName = defineXsubEquationName.apply(this)
		var Xsub = null as ReduceExpression
		if (!BE.Dsub.isEmpty) {
			val restrictDom = BE.DEp.copy.subtract(BE.origDE.copy)
			val DintPreimage = BE.Dint.copy.preimage(targetReduce.projection)
			val depExpr = AlphaUserFactory.createDependenceExpression(reuseDep.copy, EcoreUtil.copy(targetReduce.body))
			val innerRestrict = AlphaUserFactory.createRestrictExpression(restrictDom, depExpr)
			val outerRestrict = AlphaUserFactory.createRestrictExpression(DintPreimage, innerRestrict)
			Xsub = AlphaUserFactory.createReduceExpression(targetReduce.operator, targetReduce.projection, outerRestrict);
			
			val XsubDom = restrictDom.copy.apply(targetReduce.projection.toMap).intersect(BE.Dint.copy)
			val XsubVar = AlphaUserFactory.createVariable(XsubName, XsubDom)
			val XsubEq = AlphaUserFactory.createStandardEquation(XsubVar, Xsub);
			containerSystem.locals.add(XsubVar)
			containerSystemBody.equations.add(XsubEq)
			AlphaInternalStateConstructor.recomputeContextDomain(XsubEq)
		}
		
		//Creating the main CaseExpression of the transformed reduction
		val mainCaseExpr = AlphaUserFactory.createCaseExpression
		
		//common expressions
		val binaryOp = AlphaOperatorUtil.reductionOPtoBinaryOP(targetReduce.operator)
		val XaddRef = AlphaUserFactory.createVariableExpression(containerSystem.getVariable(XaddName))
		val Xref = AlphaUserFactory.createVariableExpression(reductionEquation.variable)
		//Xreuse = projectedReuse @ X
		val reuseExpr = AlphaUserFactory.createDependenceExpression(BE.reuseDepProjected.copy, Xref)
		
		//Case 1
		// (Dadd - Dint) : Xadd
		{
			val restrictDom = BE.Dadd.copy.subtract(BE.Dint.copy)
			val branch1expr = AlphaUserFactory.createRestrictExpression(restrictDom, EcoreUtil.copy(XaddRef));
			
			mainCaseExpr.exprs.add(branch1expr)
		}
		
		//Case 2
		// (Dint - (Dadd V Dsub)) : Xreuse
		{
			val restrictDom = BE.Dint.copy.subtract(BE.Dadd.copy.union(BE.Dsub.copy))
			val branch2expr = AlphaUserFactory.createRestrictExpression(restrictDom, EcoreUtil.copy(reuseExpr));
			
			mainCaseExpr.exprs.add(branch2expr)
		}
		
		//Case 3
		// (Dadd ^ (Dint - Dsub)) : Xadd + Xreuse
		{
			val restrictDom = BE.Dadd.copy.intersect(BE.Dint.copy.subtract(BE.Dsub.copy))
			val binaryExpr = AlphaUserFactory.createBinaryExpression(binaryOp, EcoreUtil.copy(XaddRef), EcoreUtil.copy(reuseExpr))
			val branch3expr = AlphaUserFactory.createRestrictExpression(restrictDom, binaryExpr);
			
			mainCaseExpr.exprs.add(branch3expr)
		}
		
		//Cases 4 and 5 are only when subtraction is necessary 
		if (!BE.Dsub.isEmpty) {
			val XsubRef = AlphaUserFactory.createVariableExpression(containerSystem.getVariable(XsubName))
			//Case 4
			// (Dsub ^ (Dint - Dadd)) : Xreuse - Xsub
			{
				val restrictDom = BE.Dsub.copy.intersect( BE.Dint.copy.subtract( BE.Dadd.copy))
				val binaryExpr = AlphaUserFactory.createBinaryExpression(BE.invOP, EcoreUtil.copy(reuseExpr),  EcoreUtil.copy(XsubRef))
				val branch4expr = AlphaUserFactory.createRestrictExpression(restrictDom, binaryExpr);
				
				mainCaseExpr.exprs.add(branch4expr)
			}
			
			//Case 5
			// (Dadd ^ Dint ^ Dsub) : Xadd + Xreuse - Xsub
			{
				val restrictDom =  BE.Dadd.copy.intersect( BE.Dint.copy.intersect( BE.Dsub.copy))
				val binaryExprAdd = AlphaUserFactory.createBinaryExpression(binaryOp, XaddRef, reuseExpr)
				val binaryExprSub = AlphaUserFactory.createBinaryExpression(BE.invOP, binaryExprAdd,  XsubRef)
				val branch5expr = AlphaUserFactory.createRestrictExpression(restrictDom, binaryExprSub);
				
				mainCaseExpr.exprs.add(branch5expr)
			}
		}
		
		EcoreUtil.replace(targetReduce, mainCaseExpr)
		AlphaInternalStateConstructor.recomputeContextDomain(reductionEquation)
		
		if (!DISABLE_POST_PROCESSING) {
			PropagateSimpleEquations.apply(containerSystemBody)
			Normalize.apply(containerSystemBody)
			
			// The bodies of the residual reduction Xadd and Xsub are unions of the facets
			// and should be split into individual convex pieces
			if (Xadd !== null)
				Xadd.splitReduction
			if (Xsub !== null)
				Xsub.splitReduction
			
			SimplifyExpressions.apply(containerSystemBody)
			Normalize.apply(containerSystemBody)
		}
		AlphaInternalStateConstructor.recomputeContextDomain(containerSystemBody)
	}
	
	/**
	 * Splits a reduction with a non-convex body into cases of reductions
	 * with convex bodies.
	 */
	static def splitReduction(ReduceExpression re) {
		if (re.contextDomain.nbBasicSets == 1)
			return
		val body = re.getContainerSystemBody
		SplitUnionIntoCase.apply(re)
		PermutationCaseReduce.apply(re)
		AlphaInternalStateConstructor.recomputeContextDomain(body)
	}
	
	/**
	 * 'Struct' for storing basic elements (domains and functions)
	 * used in the transformation. The primary purpose of this class
	 * is to separate legality tests with the transformation.
	 * 
	 */
	protected static class BasicElements {
		protected long[][] kerQ
		protected ISLMultiAff reuseDir
		protected ISLMultiAff reuseDepProjected 
		protected ISLSet origDE
		protected ISLSet DEp 
		protected ISLSet Dadd 
		protected ISLSet Dsub
		protected ISLSet Dint
		protected BINARY_OP invOP
	}
	
	/**
	 * Computes BasicElements while performing all the legality tests.
	 * 
	 */
	static def computeBasicElements(AbstractReduceExpression reduce, ISLMultiAff reuseDep) {
		val BE = new BasicElements
		
		if (reduce.contextDomain.nbBasicSets > 1) {
			throw new RuntimeException("The context of the reduction body must be a single polyhedron.");
		}
		
//		if (!(reduce.eContainer instanceof StandardEquation)) {
//			throw new RuntimeException("The target ReduceExpression must be a direct child of a StandardEquation. Apply NormalizeReductions first.");
//		}
		
		if (reduce.body.contextDomain.nbIndices != reuseDep.nbInputs) {
			throw new RuntimeException("Given reuse dependence does not match the dimensionality of the reduction body.");
		}
		
		BE.kerQ = DomainOperations.kernelOfLinearPart(reduce.body.contextDomain)
		if (BE.kerQ !== null) {
			throw new RuntimeException("The body of the target ReduceExpression has non-empty ker(Q); kernel of the linear part of the domain. This case is currently not handled.");
		}
		
		BE.reuseDir = AffineFunctionOperations.negateUniformFunction(reuseDep);
		BE.reuseDepProjected = constructDependenceFunctionInAnswerSpace(reduce.contextDomain.space, reduce.projection, reuseDep)
		if (BE.reuseDepProjected.isIdentity) {
			throw new RuntimeException("The reuse dependence is in the kernel of the projection function.");
		}
		
		BE.reuseDir = AffineFunctionOperations.negateUniformFunction(reuseDep);
		//Find Dadd, Dsub, Dinit
		BE.origDE = reduce.body.contextDomain
		
		//E' is the translation of the original domain by reuse vector
		BE.DEp    = BE.origDE.copy.apply(BE.reuseDir.toMap)
		
		//Dadd = proj (DE - DE')
		BE.Dadd   = BE.origDE.copy.subtract(BE.DEp.copy).apply(reduce.projection.toMap).intersect(reduce.contextDomain)
		
		//Dsub = proj (DE' - DE)
		BE.Dsub   = BE.DEp.copy.subtract(BE.origDE.copy).apply(reduce.projection.toMap).intersect(reduce.contextDomain)
		
		//Dint = proj (DE ^ DE')
		BE.Dint  = BE.origDE.copy.intersect(BE.DEp.copy).apply(reduce.projection.toMap).intersect(reduce.contextDomain)
		
		if (BE.Dint.isEmpty) {
			throw new RuntimeException("Initialization domain is empty; input reuse vector is invalid.");
		}
		
		if (!BE.Dsub.isEmpty)
			BE.invOP = AlphaOperatorUtil.reductionOPtoBinaryInverseOP(reduce.operator)
			
		return BE
	}
	
	static def testLegality(AbstractReduceExpression reduce, int[] reuseDepNoParams) {
		testLegality(reduce, reuseDepNoParams.map[v|v as long])
	}
	
	static def testLegality(AbstractReduceExpression reduce, long[] reuseDepNoParams) {
		testLegality(reduce, reduce.longVecToMultiAff(reuseDepNoParams))
	}
	
	static def testLegality(AbstractReduceExpression reduce, ISLMultiAff reuseDep) {
		try {
			computeBasicElements(reduce, reuseDep)
		} catch (RuntimeException re) {
			debug(re.message)
			return false;	
		}
		
		return true;
	}
	
	/**
	 * The reuse dependence is specified in the space of reduction body,
	 * but the actual dependence will be among the reduction instance.
	 * Thus, the projection of the reuse must be computed.
	 * 
	 * In this method, this process is implemented as evaluating the 
	 * constant vector representing the uniform reuse dependence by
	 * the projection function, and then reconstructing the uniform
	 * function from the result of the evaluation.
	 */
	static def constructDependenceFunctionInAnswerSpace(ISLSpace variableDomainSpace, ISLMultiAff projection, ISLMultiAff reuseDep) {
		val b = AffineFunctionOperations.getConstantVector(reuseDep)
		val nbParams = reuseDep.domainSpace.nbParams

		var point = ISLPoint.buildZero(reuseDep.domainSpace);

		//There is a bug in ISL (already fixed) that makes the ISL_Val version not work.
		// Use the following code when ISL 0.21 is released 
		for (d : 0..<b.size) {
			val dimType = if (d < nbParams) ISLDimType.isl_dim_param else ISLDimType.isl_dim_set
			val pos = if (d < nbParams) d else d-nbParams
			
			val v = ISLVal.buildFromLong(ISLContext.instance, b.get(d))
			point = point.setCoordinate(dimType, pos, v);
		}
		
		val projectedB = new ArrayList<Long>(nbParams+projection.nbOutputs)
		for (d : 0..<nbParams) projectedB.add(0l); //implicit parameter dims
		for (aff : projection.affs) {
			projectedB.add(aff.eval(point.copy).asLong)
		}

		val space = ISLSpace.idMapDimFromSetDim(variableDomainSpace.copy)
		
		return AffineFunctionOperations.createUniformFunction(space, projectedB)
	}
	
	/**
	 * Creates a list of Pair<ISLMultiAff, ISLMultiAff> that are valid projection functions for decomposing
	 * the given reduction. The first element is the projection for the inner reduction.
	 * 
	 * The candidates are created using two methods:
	 *  1. Boundary Constraints. For each constraint c in the domain of the reduction body, 
	 * a function f such that ker(f) = ker(original projection) \intersect ker(c) becomes a candidate.
	 *  2. Distributivity. When the reduction body has share space RE, 
	 * a function f such that ker(f) = ker(original projection) \intersect RE becomes a candidate.
	 *    
	 */
	static def generateDecompositionCandidates(ShareSpaceAnalysisResult SSAR, AbstractReduceExpression targetRE) {
		val exprREs = SSAR.expressionsWithReuse
		val kerF = MatrixOperations.transpose(AffineFunctionOperations.computeKernel(targetRE.projection))

		//Set to avoid redundant options
		val kerFps = new TreeSet<long[][]>(new Comparator<long[][]>() {
			override compare(long[][] o1, long[][] o2) {
				val str1 = MatrixOperations.toString(o1);
				val str2 = MatrixOperations.toString(o2);
				return str1.compareTo(str2);
			}
		});
		
		//Decomposition with Side Effects that expose Distributivity
		for (exprRE : exprREs.filter[exprRE|exprRE.key.contextDomain.nbIndices == targetRE.body.contextDomain.nbIndices]) {
			val intersection = MatrixOperations.kernelIntersection(exprRE.value, kerF)
			if (intersection !== null)
				kerFps.add(intersection)
		}
		//Decomposition with Side Effects that expose Boundary Constraints
		val constraints = targetRE.body.contextDomain.getBasicSetAt(0).constraints
		for (c : constraints) {
			val kerC = MatrixOperations.transpose(DomainOperations.kernelOfLinearPart(c.copy.toBasicSet));
			val ker = MatrixOperations.kernelIntersection(kerF, kerC)
			if (ker !== null) {
				kerFps.add(ker);
			}
		}
		
		val candidates = new LinkedList<Pair<ISLMultiAff, ISLMultiAff>>();
		
		val params = targetRE.body.expressionDomain.paramNames
		var indices = targetRE.body.expressionDomain.indexNames
		if (indices === null) indices = AlphaUtil.defaultDimNames(targetRE.body.expressionDomain.nbIndices)
		for (RE : kerFps) {
			val Fp = AffineFunctionOperations.constructAffineFunctionWithSpecifiedKernel(params, indices, RE);
			if (Fp.nbOutputs > targetRE.projection.nbOutputs) {
				val Fpp = AffineFunctionOperations.projectFunctionDomain(targetRE.projection, Fp.copy)
				candidates.add(new Pair(Fp, Fpp));
			}
		}
		
		return candidates;
	}
	
	private static def longVecToMultiAff(AbstractReduceExpression reduce, long[] reuseDepNoParams) {
		val space = ISLSpace.idMapDimFromSetDim(reduce.body.expressionDomain.space.copy)
		val reuseDep = MatrixOperations.bindVector(newLongArrayOfSize(space.nbParams), reuseDepNoParams);
		
		AffineFunctionOperations.createUniformFunction(space.copy, reuseDep);
	}
}