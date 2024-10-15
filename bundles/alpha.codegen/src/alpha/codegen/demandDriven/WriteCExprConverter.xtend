package alpha.codegen.demandDriven

import alpha.codegen.BaseDataType
import alpha.codegen.Expression
import alpha.codegen.Factory
import alpha.codegen.MacroStmt
import alpha.codegen.ProgramBuilder
import alpha.codegen.alphaBase.AlphaBaseHelpers
import alpha.codegen.alphaBase.AlphaNameChecker
import alpha.codegen.alphaBase.ExprConverter
import alpha.codegen.isl.ASTConverter
import alpha.codegen.isl.LoopGenerator
import alpha.model.ReduceExpression
import alpha.model.StandardEquation
import alpha.model.VariableExpression
import alpha.model.util.AlphaUtil
import alpha.model.util.Face
import alpha.model.util.Face.Boundary
import alpha.model.util.Face.Label
import alpha.model.util.FaceLattice
import fr.irisa.cairn.jnimap.isl.ISLASTBuild
import fr.irisa.cairn.jnimap.isl.ISLAff
import fr.irisa.cairn.jnimap.isl.ISLBasicSet
import fr.irisa.cairn.jnimap.isl.ISLConstraint
import fr.irisa.cairn.jnimap.isl.ISLDimType
import fr.irisa.cairn.jnimap.isl.ISLMultiAff
import fr.irisa.cairn.jnimap.isl.ISLSet
import fr.irisa.cairn.jnimap.isl.ISLSpace
import org.eclipse.xtext.EcoreUtil2

import static extension alpha.codegen.ProgramPrinter.printExpr
import static extension alpha.codegen.isl.AffineConverter.convertMultiAff
import static extension alpha.model.transformation.reduction.SplitReduction.construct1DBasis
import static extension alpha.model.transformation.reduction.SplitReduction.enumerateCandidateSplits
import static extension alpha.model.transformation.reduction.SplitReduction.getReuseMaff
import static extension alpha.model.util.AlphaUtil.getContainerEquation
import static extension alpha.model.util.AlphaUtil.getContainerSystem
import static extension alpha.model.util.CommonExtensions.toArrayList
import static extension alpha.model.util.CommonExtensions.zipWith
import static extension alpha.model.util.ISLUtil.dimensionality
import static extension alpha.model.util.ISLUtil.nullSpace
import static extension alpha.model.util.ISLUtil.toISLSchedule
import static extension fr.irisa.cairn.jnimap.isl.ISLASTBuild.buildFromContext

/**
 * Converts Alpha expressions to simpleC expressions.
 * Adds support for reduce expressions.
 */
class WriteCExprConverter extends ExprConverter {
	/** The name of the reduction variable inside of reduce functions. */
	protected static val reduceVarName = "reduceVar"
	
	/** The program being built. */
	protected val ProgramBuilder program
	
	/** Generates data types compatible with WriteC. */
	protected val WriteCTypeGenerator typeGenerator
	
	/**
	 * A counter for the number of reductions that have been created.
	 * This is used for determining the names of functions and macros which will be emitted.
	 */
	protected var nextReductionId = 0
	
	/** Constructs a new converter for expressions. */
	new(WriteCTypeGenerator typeGenerator, AlphaNameChecker nameChecker, ProgramBuilder program) {
		super(typeGenerator, nameChecker)
		this.program = program
		this.typeGenerator = typeGenerator
	}
	
	/**
	 * Converts an Alpha reduce expression into the appropriate C AST nodes.
	 * A new function is created and added to the program which computes the reduction,
	 * and the appropriate function call expression is returned.
	 */
	def dispatch Expression convertExpr(ReduceExpression expr) {
		if (expr.fractalSimplify) {
			// Create the reduce function and add it to the program.
			val reduceFunction = createFractalReduceFunction(program, expr)
			program.addFunction(reduceFunction)
			
			// Return a call to the reduce function.
			val callArguments = #[expr.contextDomain.paramNames, expr.contextDomain.indexNames].flatten
			return Factory.callExpr(reduceFunction.name, callArguments)
		} else {
			// Create the reduce function and add it to the program.
			val reduceFunction = createReduceFunction(program, expr)
			program.addFunction(reduceFunction)
			
			// Return a call to the reduce function.
			val callArguments = #[expr.contextDomain.paramNames, expr.contextDomain.indexNames].flatten
			return Factory.callExpr(reduceFunction.name, callArguments)
		}
	}
	
	def protected getFractalReduceFunctionName(ReduceExpression re) {
		val variable = (re.getContainerEquation as StandardEquation).variable
		'freduce_' + variable.name
	}
	
	def protected getSystem(ReduceExpression re) {
		re.getContainerSystem
	}
	
	def protected createFractalReduceFunction(ProgramBuilder program, ReduceExpression expr) {
		
		val fp = expr.projection
		val fd = expr.body.getReuseMaff
		val D = expr.facet.toSet.removeRedundancies
		val reduceFunctionName = getFractalReduceFunctionName(expr)
		val lhsVar = (expr.getContainerEquation as StandardEquation).variable.name
		val varExprs = EcoreUtil2.getAllContentsOfType(expr, VariableExpression)
		if (varExprs.size != 1)
			throw new Exception('This implementation only supports a single variable expression in the reduction body')
		val lhsVarCopy = lhsVar + '_copy' 
		val rhsVar = varExprs.get(0).variable.name 
		
		val rho = fd.construct1DBasis
		val acc = fp.nullSpace		
		
		val lattice = FaceLattice.create(D.copy)
		
		// assert 2-dimensional
		if (D.dimensionality != 2) {
			throw new Exception('fractal codegen only works for 2D domains (or embeddings)')
		}
		
		// get splits
		val domains = lattice.root.fractalSplits(fp, fd).toArrayList
		
		val residualComputations = domains.map[d |
			val labeledResidualEdges = d.getResidualEdges(acc, rho)
			val invariantEdge = labeledResidualEdges.findFirst[value == Label.ZERO]
			if (invariantEdge === null) {
				0->d
			} else {
				getScanComputation(d, labeledResidualEdges, rho)
			}
		].toArrayList
		
		val forwardScan = residualComputations.findFirst[key == 1].value
		val backwardScan = residualComputations.findFirst[key == -1].value
		val baseCase = residualComputations.findFirst[key == 0].value
		
		val fDomain = forwardScan.copy.subtract(forwardScan.copy.apply(rho.copy.toMap))
		val bDomain = backwardScan.copy.subtract(backwardScan.copy.preimage(rho.copy))
		
		// create function
		val function = program.startFunction(true, false, Factory.dataType(BaseDataType.VOID), reduceFunctionName)
		
		// add reduce function arguments
		val system = expr.getContainerSystem
		val lhsType = program.instance.globalVariables.findFirst[name == lhsVar].dataType
		val rhsType = program.instance.globalVariables.findFirst[name == rhsVar].dataType
		
		function.addParameter(system.parameterDomain.paramNames.map[toParameter])
		function.addParameter(Factory.dataType(lhsType.baseType, lhsType.indirectionLevel), lhsVar)
		function.addParameter(Factory.dataType(lhsType.baseType, lhsType.indirectionLevel), lhsVar + '_copy')
		function.addParameter(Factory.dataType(rhsType.baseType, rhsType.indirectionLevel), rhsVar)
		
		
		val code = createFractalReduceFunctionCodeGen(program, expr, lhsVar, lhsVarCopy, rhsVar, D, fDomain, bDomain, baseCase, fp, fd, rho)
		val stmt = Factory.customStmt(code.toString)
		function.addStatement(stmt)
		
		function.addEmptyLine
		function.addStatement(Factory.undefStmt('S'))
			
		return function.instance
		
		
	}
	
	def protected createFractalReduceFunctionCodeGen(ProgramBuilder program, ReduceExpression expr, String lhsVar, 
		String lhsVarCopy, String rhsVar, ISLSet inputDomain, ISLSet fDomainRaw, ISLSet bDomainRaw, ISLSet baseCase, 
		ISLMultiAff fp, ISLMultiAff fd, ISLMultiAff rho
	) {
		val ADD_DEBUG_STMTS = false
		
		/*
		 * Codegen
		 * 
		 */
		val funcName = getFractalReduceFunctionName(expr)
		val paramStr = baseCase.paramNames.join(',')
		val indexNames = baseCase.indexNames
		val indexNameStr = indexNames.join(',')
		
		val idMaff = ISLMultiAff.buildIdentity(rho.space.copy)
		val oppositeRho = (idMaff.copy.add(idMaff.copy)).sub(rho.copy)
		
		val lhsAcc = Factory.callExpr(lhsVar, fp.convertMultiAff).printExpr
		val lhsCopyAcc = Factory.callExpr(lhsVarCopy, fp.convertMultiAff).printExpr
		val lhsCopyRightAcc = Factory.callExpr(lhsVarCopy, fp.copy.pullback(rho.copy).convertMultiAff).printExpr
		val lhsLeftAcc = Factory.callExpr(lhsVar, fp.copy.pullback(oppositeRho.copy).convertMultiAff).printExpr
		val rhsAcc = Factory.callExpr(rhsVar, fd.convertMultiAff).printExpr
		
		val reduceExprs = EcoreUtil2.getAllContentsOfType(expr.getContainerSystem, ReduceExpression)
		val thisReduceExprIdx = reduceExprs.indexOf(expr)
				
		val flattenTupleTop = '''S«thisReduceExprIdx»_flatten_top'''
		val flattenTupleBottom = '''S«thisReduceExprIdx»_flatten_bottom'''
		val flattenDomain = fDomainRaw.copy.setTupleName(flattenTupleTop).toUnionSet
			.union(bDomainRaw.copy.setTupleName(flattenTupleBottom).toUnionSet)
		val flattenSchedule = '''
			domain: "«flattenDomain.toString»"
			child:
			  sequence:
			  - filter: "{ «flattenTupleTop»[«indexNameStr»] }"
			  - filter: "{ «flattenTupleBottom»[«indexNameStr»] }"
		'''.toISLSchedule
		val flattenBuild = ISLASTBuild.buildFromContext(flattenSchedule.domain.copy.params)
		val flattenNode = flattenBuild.generate(flattenSchedule.copy)
		
		
		///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		// forward scan
		///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		
		val fAnswerDomain = AlphaUtil.renameIndices(fDomainRaw.copy.apply(fp.copy.toMap), indexNames)
		val fInitTuple = '''S«thisReduceExprIdx»_forward_init'''
		val fAccTuple = '''S«thisReduceExprIdx»_forward_acc'''
		val fIndexNameStr = fAnswerDomain.indexNames.join(',')
		
		val fInit = fAnswerDomain.copy.lexMin.setTupleName(fInitTuple).toUnionSet
		val fAcc = fAnswerDomain.copy.subtract(fAnswerDomain.copy.lexMin).setTupleName(fAccTuple).toUnionSet
		val fDomain = fInit.copy.union(fAcc.copy)
		
		val forwardSchedule = '''
			domain: "«fAcc»"
			child:
«««			  sequence:
«««			  - filter: "{ «fInitTuple»[«fIndexNameStr»] }"
«««			  - filter: "{ «fAccTuple»[«fIndexNameStr»] }"
«««			    child:
			  schedule: "[«paramStr»]->[«fAnswerDomain.indexNames.map[i | '''{ «fAccTuple»[«fIndexNameStr»]->[«i»] }'''].join(',')»]"
		'''.toISLSchedule
		val forwardBuild = ISLASTBuild.buildFromContext(forwardSchedule.domain.copy.params)
		val forwardNode = forwardBuild.generate(forwardSchedule.copy)
		
		val fDbgSched = '''domain: "«fAnswerDomain.copy.setTupleName('D').toUnionSet»"'''.toISLSchedule
		val fDbgNode = fDbgSched.domain.copy.params.buildFromContext.generate(fDbgSched.copy)
		
		///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		// backward scan
		///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		
		val bAnswerDomain = AlphaUtil.renameIndices(bDomainRaw.copy.apply(fp.copy.toMap), indexNames)
		val bAccTuple = '''S«thisReduceExprIdx»_backward_acc'''
		val bIndexNameStr = bAnswerDomain.indexNames.join(',')
		val bAcc = bAnswerDomain.copy.subtract(bAnswerDomain.copy.lexMax).setTupleName(bAccTuple).toUnionSet
		val backwardSchedule = '''
			domain: "«bAcc»"
			child:
«««			  sequence:
«««			  - filter: "{ «bInitTuple»[«bIndexNameStr»] }"
«««			  - filter: "{ «bAccTuple»[«bIndexNameStr»] }"
«««			    child:
			  schedule: "[«paramStr»]->[«bAnswerDomain.indexNames.map[i | '''{ «bAccTuple»[«bIndexNameStr»]->[N-«i»] }'''].join(',')»]"
		'''.toISLSchedule
		val backwardBuild = ISLASTBuild.buildFromContext(backwardSchedule.domain.copy.params)
		val backwardNode = backwardBuild.generate(backwardSchedule.copy)
		
		val bDbgSched = '''domain: "«bAnswerDomain.copy.setTupleName('D').toUnionSet»"'''.toISLSchedule
		val bDbgNode = bDbgSched.domain.copy.params.buildFromContext.generate(bDbgSched.copy)
		
		///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		// combine partial results
		// (i.e., backward scan domain with different statements)
		///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		
		val cTuple = '''S«thisReduceExprIdx»_combine'''
		val cDomain = fAnswerDomain.copy.union(bAnswerDomain.copy).coalesce.setTupleName(cTuple).toUnionSet
		val cIndexNameStr = bIndexNameStr
		val combineSchedule = '''domain: "«cDomain»"'''.toISLSchedule
		val combineBuild = ISLASTBuild.buildFromContext(combineSchedule.domain.copy.params)
		val combineNode = combineBuild.generate(combineSchedule.copy)
		
		val cDbgSched = '''domain: "«fAnswerDomain.copy.union(bAnswerDomain.copy).coalesce.setTupleName('D').toUnionSet»"'''.toISLSchedule
		val cDbgNode = cDbgSched.domain.copy.params.buildFromContext.generate(cDbgSched.copy)
		
		///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		// base case
		///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		
		val baseTuple = '''S«thisReduceExprIdx»'''
		val baseSchedule = '''domain: "«inputDomain.copy.setTupleName(baseTuple).toString»"'''.toISLSchedule
		val baseBuild = ISLASTBuild.buildFromContext(baseSchedule.domain.copy.params)
		val baseNode = baseBuild.generate(baseSchedule.copy)
		
//		val x = {
//			val set = fDomainRaw.copy.apply(fp.copy.toMap).lexMin.coalesce
//			if (set.nbBasicSets != 1)
//				throw new Exception('Current fractal simplification implemented resulted in something non-convex')
//			val bset = set.basicSets.get(0)
//			val vertices = bset.computeVertices;
//			(0..<vertices.getNbCell).forEach[i |
//				val cell = vertices.getCellAt(i)
//				(0..<cell.getNbVertices).forEach[j | 
//					val vertex = cell.getVertexAt(j)
//					val maff = vertex.getExpr
//					println('''«i»,«j»,«maff»''')
//				]
//				
//			]
//			
//			println
//			
//			''
//		}
//		
//		val newN = {
//			val set = baseCase.copy.apply(fp.copy.toMap).lexMax.coalesce
//			if (set.nbBasicSets != 1)
//				throw new Exception('Current fractal simplification implemented resulted in something non-convex')
//			val bset = set.basicSets.get(0)
//			val vertices = bset.computeVertices;
//			(0..<vertices.getNbCell).forEach[i |
//				val cell = vertices.getCellAt(i)
//				(0..<cell.getNbVertices).forEach[j | 
//					val vertex = cell.getVertexAt(j)
//					val maff = vertex.getExpr
//					println('''«i»,«j»,«maff»''')
//				]
//				
//			]
//			
//			println
//			
//			''
//		}
		
		'''
		// base case
		if (N<10) {
			#define «baseTuple»(«indexNameStr») «lhsAcc» = max(«lhsAcc»,«rhsAcc»)
		    «baseNode.toCString»
		    #undef «baseTuple»
		    return;
		  }
		
		// flatten (thick) faces
		#define «flattenTupleTop»(«indexNameStr») «lhsAcc» = max(«lhsAcc»,«rhsAcc»)
		#define «flattenTupleBottom»(«indexNameStr») «lhsCopyAcc» = max(«lhsCopyAcc»,«rhsAcc»)
		«flattenNode.toCString»
		#undef «flattenTupleTop»
		#undef «flattenTupleBottom»
		
		// forward scan
		#define «fAccTuple»(«fIndexNameStr») «lhsAcc» = max(«lhsAcc»,«lhsLeftAcc»)
		«forwardNode.toCString»
		#undef «fAccTuple»
		«IF ADD_DEBUG_STMTS»
		// debug
		#define D(i) printf("f(%2d) = %2ld\n", i, «lhsAcc»)
		«fDbgNode.toCString»
		#undef D
		printf("\n");
		«ENDIF»
		
		// backward scan
		#define «bAccTuple»(«bIndexNameStr») «lhsCopyAcc» = max(«lhsCopyAcc»,«lhsCopyRightAcc»)
		«backwardNode.toCString»
		#undef «bAccTuple»
		«IF ADD_DEBUG_STMTS»
		// debug
		#define D(i) printf("b(%2d) = %2ld\n", i, «lhsCopyAcc»)
		«bDbgNode.toCString»
		#undef D
		printf("\n");
		«ENDIF»
		
		// combine forward and backward scan results
		#define «cTuple»(«cIndexNameStr») «lhsAcc» = max(«lhsAcc»,«lhsCopyAcc»)
		«combineNode.toCString»
		#undef «cTuple»
		«IF ADD_DEBUG_STMTS»
		// debug
		#define D(i) printf("c(%2d) = %2ld\n", i, «lhsCopyAcc»)
		«cDbgNode.toCString»
		#undef D
		printf("===\n");
		«ENDIF»
		
		// recursive call
«««		long Np = N%2==0 ? N/2 : (N+1)/2; // compute this with an ISLPolynomial
		«funcName»((N/2)-1, «lhsVar», «lhsVarCopy», «rhsVar»)'''
	}
	
	/** Creates the function which evaluates the reduction at a specific output point. */
	def protected createReduceFunction(ProgramBuilder program, ReduceExpression expr) {
		// Generate unique names for the reduce function, the reduce point macro,
		// and the accumulation macro. These names are just a prefix plus a common ID number.
		// Keep incrementing that number until the names are unique.
		var String reduceFunctionName
		var String reducePointMacroName
		var String accumulateMacroName
		do {
			reduceFunctionName = "reduce" + nextReductionId
			reducePointMacroName = "RP" + nextReductionId
			accumulateMacroName = "R" + nextReductionId
			nextReductionId += 1
		} while(program.nameChecker.isGlobalOrKeyword(reduceFunctionName, reducePointMacroName, accumulateMacroName))
		
		// Start building the reduce function.
		// The return type is the value type of the variable that the reduce expression writes to.
		val function = program.startFunction(true, false, typeGenerator.alphaValueType, reduceFunctionName)
		
		// Create the "reduction variable", which is what the reduction will accumulate into.
		// This needs to be initialized to the correct value for the reduction operator.
		val initializeStmt = Factory.assignmentStmt(reduceVarName, AlphaBaseHelpers.getReductionInitialValue(typeGenerator.alphaValueBaseType, expr.operator))
		function.addVariable(typeGenerator.alphaValueType, reduceVarName)
			.addStatement(initializeStmt)
			
		// Create the macros that evaluate points within the reduction body
		// and accumulate them into the reduce variable.
		val reducePointMacro = createReducePointMacro(reducePointMacroName, program, expr)
		val accumulateMacro = createAccumulationMacro(accumulateMacroName, expr, reducePointMacro)
		function.addStatement(reducePointMacro, accumulateMacro)
		
		// Use isl to determine what points need to be reduced and how they get reduced.
		val loopDomain = expr.createReduceLoopDomain
		val islAST = LoopGenerator.generateLoops(accumulateMacro.name, loopDomain)
		
		// The size parameters for the loop domain need to be added as function parameters.
		function.addParameter(loopDomain.paramNames.map[toParameter])
		
		// Add declarations for all the loop variables and add the loops themselves to the function.
		val loopResult = ASTConverter.convert(islAST)
		loopResult.declarations.forEach[function.addVariable(typeGenerator.indexType, it)]
		function.addStatement(loopResult.statements)
			
		// Undefine the macros, then have the function return the reduce variable.
		function.addUndefine(reducePointMacro, accumulateMacro)
			.addReturn(reduceVarExpr)
			
		return function.instance
	}
	
	/** Constructs the domain which will represent the loop nest that isl will produce. */
	def protected createReduceLoopDomain(ReduceExpression reduceExpr) {
		// We will use ISL to create the loop nest for the reduction.
		// This needs two things: the domain of points to iterate over,
		// and a map to the time at which they are computed at.
		//
		// When the reduce function is called, we want a specific value within the domain
		// where the reduction outputs its results to (i.e., the context domain of the reduce expression).
		// To allow constraining the reduction body to only the points needed for a particular output,
		// we take the reduction body's context domain and add a parameter for each output dimension.
		// Then, since the output point is determined by the reduction's projection function,
		// we equate each of the output parameters to the appropriate output dimension of the projection function.
		// This will give us a set which is parameterized by the output domain,
		// which in turn allows isl to produce the loops that produce only that one output.
		
		// We will start with the entire reduction body, then add parameters and constraints to it.
		var pointsToReduce = reduceExpr.body.contextDomain.copy
		
		// When we create new names for the domain's parameters,
		// we want to ensure they are unique.
		val existingNames = newHashSet
		existingNames.addAll(pointsToReduce.paramNames)
		existingNames.addAll(pointsToReduce.indexNames)
		
		// For each index in the reduction's output domain,
		// add a new parameter representing that output dimension to the set of points to reduce.
		// Then, set that new parameter equal to its expression in the reduction's projection function.
		// This will ensure we only reduce the set of points which are going to the desired output point.
		val outputDomain = reduceExpr.contextDomain
		for (i: 0 ..< outputDomain.nbIndices) {
			// Get a unique parameter name which will represent that output,
			// record it as an existing name, and add it to the set of points to reduce.
			val outputName = outputDomain.getIndexName(i)
			val parameterName = nameChecker.getUniqueLocalName(existingNames, outputName, "p")
			existingNames.add(parameterName)
			pointsToReduce = pointsToReduce.addParams(#[parameterName])
			
			// Construct an equality constraint for that new parameter
			// according to the expression which computes that index.
			// Add the constraint to the set of points to reduce.
			val outputExpr = reduceExpr.projection.getAff(i)
			val outputConstraint = constrainAddedParameter(pointsToReduce.space, outputExpr)
			pointsToReduce = pointsToReduce.addConstraint(outputConstraint)
		}
		
		return pointsToReduce
	}
	
	/**
	 * Adds an equality constraint to the recently added parameter
	 * (i.e., the parameter with the largest index).
	 * This parameter is set equal to the given affine expression
	 * (which comes from the reduction's projection function).
	 */
	def protected static constrainAddedParameter(ISLSpace reductionSpace, ISLAff outputExpr) {
		// Since we're constraining the parameter that was just added,
		// we know the parameter index we're constraining is the last one.
		val parameterIndex = reductionSpace.nbParams - 1
		
		// The equality constraint we want to add is of the form: outputExpr - newParameter = 0.
		// Thus, let's start with a fresh equality constraint and set the coefficient of
		// the new parameter to be -1.
		var equality = ISLConstraint
			.buildEquality(reductionSpace.copy)
			.setCoefficient(ISLDimType.isl_dim_param, parameterIndex, -1)
		
		// Next, we will copy all the coefficients from the output expression.
		// Since all new parameters are added to the end of the reduction space,
		// the parameters and indices for the output expression are still aligned
		// with the reduction space.
		for (paramIdx: 0 ..< outputExpr.nbParams) {
			val coefficient = outputExpr.getCoefficientVal(ISLDimType.isl_dim_param, paramIdx)
			equality = equality.setCoefficient(ISLDimType.isl_dim_param, paramIdx, coefficient)
		}
		for (inIdx: 0 ..< outputExpr.nbInputs) {
			val coefficient = outputExpr.getCoefficientVal(ISLDimType.isl_dim_in, inIdx)
			equality = equality.setCoefficient(ISLDimType.isl_dim_set, inIdx, coefficient)
		}
		
		return equality
	}
	
	/** Gets an expression representing the reduce variable. */
	def protected static reduceVarExpr() {
		return Factory.customExpr(reduceVarName)
	}
	
	/** Constructs a parameter for the reduce function. */
	def protected toParameter(String name) {
		return Factory.parameter(typeGenerator.indexType, name)
	}
	
	/** Constructs the macro that evaluates a point within the reduction body. */
	def protected createReducePointMacro(String macroName, ProgramBuilder program, ReduceExpression expr) {
		val arguments = expr.body.contextDomain.indexNames
		val replacement = convertExpr(expr.body)
		return Factory.macroStmt(macroName, arguments, replacement)
	}
	
	/** Constructs the macro used to accumulate points of the reduction body into the reduce variable. */
	def protected createAccumulationMacro(String macroName, ReduceExpression expr, MacroStmt reducePointMacro) {
		// Construct a call to the reduce point macro.
		// Since isl's call to this macro doesn't parenthesize the inputs,
		// and since the macro for the reduction body might not be parenthesized,
		// parenthesize all the arguments to the call.
		val reducePointArguments = expr.body.contextDomain.indexNames.map[Factory.parenthesizedExpr(it)]
		val reducePointCall = Factory.callExpr(reducePointMacro.name, reducePointArguments)
		
		// Construct a binary expression to add the reduce variable to the point which was reduced,
		// then wrap that in an assignment statement.
		val operator = AlphaBaseHelpers.getOperator(expr.operator)
		val accumulateExpr = Factory.binaryExpr(operator, reduceVarExpr, reducePointCall)
		val accumulateStmt = Factory.assignmentStmt(reduceVarExpr, accumulateExpr)
		
		return Factory.macroStmt(macroName, expr.body.contextDomain.indexNames, accumulateStmt)
	}
	
	
	/////////////////////////////////////////////////
	// fractal simplification codegen helper functions
	/////////////////////////////////////////////////
	
	// * returns the single candidate split or (optionally) raises an exception
	static def makeSplit(ISLBasicSet set, ISLMultiAff fp, ISLMultiAff fd) {
		val splits = FaceLattice.create(set.copy).root
			.enumerateCandidateSplits(fp, fd)
		
		if (splits.size == 1) {
			val split = splits.get(0)
			val DS1 =  split.aff.toInequalityConstraint.toBasicSet.intersect(set.copy)
			val const1 = (split.aff.getConstant - 1).intValue
			val DS2 = split.aff.negate.setConstant(const1)
						.toInequalityConstraint.toBasicSet.intersect(set.copy)
			return #[DS1, DS2]
		}
		
		return #[set]
	}
	
	static def fractalSplits(Face face, ISLMultiAff fp, ISLMultiAff fd) {
		face.toBasicSet.makeSplit(fp, fd)
			.map[it.makeSplit(fp, fd)]
			.flatten
			.map[removeRedundancies]
			.map[toSet]
	}
	
	static def getResidualEdges(ISLSet domain, ISLSet accumulationSpace, ISLMultiAff rhoMaff) {
		val rho = rhoMaff.affs.map[getConstant]
		val root = FaceLattice.create(domain).root
		val edges = root.generateChildren
		if (edges.size != 3) {
			throw new Exception('Fractal pieces should be triangular')
		}
		
		val labeling = root.getLabeling(rho)
		edges.zipWith(labeling).reject[key.boundaryLabel(accumulationSpace) == Boundary.STRONG]
	}
	
	static def getScanComputation(ISLSet domain, Pair<Face,Label>[] labeledEdges, ISLMultiAff rho) {
		val labeledEdge = labeledEdges.findFirst[value != Label.ZERO]
		val label = labeledEdge.value
		switch (label) {
			case Label.POS : 1->domain
			default : -1->domain 
		}
	}
	
}