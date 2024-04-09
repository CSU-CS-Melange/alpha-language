package alpha.codegen.constructor

import alpha.codegen.ArrayVariable
import alpha.codegen.DataType
import alpha.codegen.Program
import alpha.model.AlphaSystem
import alpha.model.ReduceExpression
import alpha.model.StandardEquation
import alpha.model.Variable
import alpha.targetmapping.TargetMapping
import fr.irisa.cairn.jnimap.isl.ISLASTBuild
import fr.irisa.cairn.jnimap.isl.ISLConstraint
import fr.irisa.cairn.jnimap.isl.ISLDimType
import fr.irisa.cairn.jnimap.isl.ISLSchedule
import fr.irisa.cairn.jnimap.isl.ISLSet
import fr.irisa.cairn.jnimap.isl.ISLUnionSet
import java.util.ArrayList
import java.util.Map

import static extension alpha.codegen.factory.Factory.*
import static extension alpha.codegen.util.CodegenUtil.*
import static extension alpha.model.util.AlphaUtil.*
import static extension fr.irisa.cairn.jnimap.isl.ISLMultiAff.buildIdentity

class WriteCProgram extends BaseProgram {
	
	protected Map<Variable, ArrayVariable> flagCVs
	var int reductionNumber = 0
	
	
	def static Program build(AlphaSystem system, TargetMapping tm) {
		if (system.systemBodies.size > 1)
			throw new Exception("No support yet for programs with multiple system bodies")
		
		val visitor = new WriteCProgram(tm)
		system.accept(visitor)
		visitor.program
	}
	
	new(TargetMapping tm) {
		super()
		flagCVs = newHashMap
		program = createProgram
		this.tm = tm
	}
	
	def ISLUnionSet outputStatementsDomain(AlphaSystem s) {
		s.outputs.map[domain.setTupleName('''S«s.outputs.indexOf(it)»''').toUnionSet]
		         .reduce(d0,d1 | d0.copy.union(d1.copy))
	}
	
	override inAlphaSystem(AlphaSystem s) {
		super.inAlphaSystem(s)
		
		(s.locals + s.outputs).forEach[v | flagCVs.put(v, v.createArrayVariableForFlag)]
		
		// memory allocations
		val allocations = #[localCVs.values, flagCVs.values].flatten.map[it.createMemoryAllocation]
		
		// statement macros
		val statementMacros = s.outputs.map[statementMacro(
			'''S«s.outputs.indexOf(it)»(«it.indices.join(',')»)''',
			'''eval_«name»(«(domain.indexNames).join(', ')»)'''
		)]
		
		// schedule tree node
		val schedule = ISLSchedule.buildFromDomain(s.outputStatementsDomain)
		val build = ISLASTBuild.buildFromContext(schedule.domain.copy.params)
		val node = build.generate(schedule.copy)
		
		val body = createFunctionBody(statementMacros, node)

		val paramArgs = s.paramScalarVariables
		val arrayArgs = (inputCVs + outputCVs).values
				
		val function = createFunction(DataType.VOID, s.name, paramArgs, arrayArgs, localCVs.values, allocations, body)

		program.globalVariables.addAll(paramArgs + arrayArgs + localCVs.values + flagCVs.values)
		program.functions.add(function)
	}
	
	
	override inStandardEquation(StandardEquation se) {
		if (!se.variable.isOutput)
			return                 
		
		val evalVar = outputCVs.get(se.variable)
		val flagVar = flagCVs.get(se.variable)
		val scalarArgs = se.variable.indexScalarVariables
		val function = createEvalFunction(evalVar, flagVar, scalarArgs, se)
		
		program.functions.add(function)
	}
	
	override inReduceExpression(ReduceExpression re) {
		// Generate some names for the reduction,
		// then increment the counter for the number of reductions created.
		val macroName = "R" + reductionNumber.toString
		val reduceFunctionName = '''reduce«reductionNumber»'''
		reductionNumber += 1

		// Make a local variable to accumulate into.
		val reduceVar = baseVariable("reduceVariable", DataType.FLOAT)
		
		// Construct the domain of points to loop through and the AST node for the loop nest
		// and put it into a function body.
		val loopDomain = createReduceLoopDomain(re)
		val astNode = createReduceLoopASTNode(loopDomain, macroName)
		val functionBody = createFunctionBody(#[], astNode)
		
		// Create the reduce function and add it to the program.
		// We don't want to add function parameters for the ISL set parameters
		// that are present in the reduce expression's context domain,
		// as those ISL set parameters are already global variables.
		val contextParamCount = re.contextDomain.nbParams
		val params = loopDomain.paramNames.drop(contextParamCount).map[param | baseVariable(param, DataType.LONG)]
		val function =  createReduceFunction(reduceFunctionName, re, reduceVar, macroName, functionBody, params)
		program.functions.add(function)
		
		// Add to a mapping from reduce expression to that function
		if (program.reduceFunctions === null) {
			program.reduceFunctions = newHashMap()
		}
		program.reduceFunctions.put(re, function)
	}
	
	def createReduceLoopASTNode(ISLSet loopDomain, String macroName) {
		// Build the "context" by copying the loop domain and dropping all indices.
		val context = loopDomain.copy.params
		
		// Construct a schedule by taking the loop domain's space,
		// adding an input dimension for each output dimension
		// (to make it a map space instead of a set space),
		// create an identity map from that, and tack in the macro name.
		val schedule = loopDomain.space
			.addDims(ISLDimType.isl_dim_in, loopDomain.nbIndices)
			.buildIdentity
			.toMap
			.intersectDomain(loopDomain.copy)
			.setTupleName(ISLDimType.isl_dim_in, macroName)
			.toUnionMap
		
		// Have ISL create an AST node from the context and schedule.
		return ISLASTBuild.buildFromContext(context).generate(schedule)
	}
	
	def createReduceLoopDomain(ReduceExpression reduceExpr) {
		// We will use ISL to create the loop nest for the reduction.
		// This needs two things: the domain of points to iterate over,
		// and a map to the time at which they are computed at.
		//
		// The domain to iterate over is computed as follows:
		// 1. Start with a copy of the reduction body's context domain. 
		// 2. Add one parameter for each index of the reduce expression's context domain.
		// 3. Add equality constraints setting the new parameters equal to the
		//    expressions on the right-hand side of the projection function.
		//
		// The idea is that we want to compute some point in the reduce expression's context.
		// This point is already set when starting the loops,
		// so we convert those indices into parameters here.
		// We then construct an equality constraint for each parameter,
		// where the parameter equals the appropriate expression from the projection function.
		// This tells ISL how to fix the indices inside the reduction body's context
		// according to the outer indices, that way we only iterate over the points we need.
		
		// 1. Start with a copy of the reduction body's context domain.
		// Also record how many parameters it originally had.
		var loopDomain = reduceExpr.body.contextDomain.copy
		val originalParamCount = loopDomain.nbParams
		
		// 2. Add one parameter for each index of the reduce expression's context domain.
		val existingNames = #[loopDomain.paramNames, loopDomain.indexNames].flatten.toArrayList
		val addedNames = new ArrayList<String>()
		for (toAdd: reduceExpr.contextDomain.indexNames) {
			// Append a "p" to the name of the index to indicate it's now a parameter.
			// If that name already exists, keep adding a "p" to it until it's unique.
			var updatedName = toAdd
			do {
				updatedName += "p"
			} while (existingNames.contains(updatedName))
			
			// Record keeping.
			addedNames.add(updatedName)
			existingNames.add(updatedName)
			
			// Add this name as a new parameter in the loop's domain.
			loopDomain = loopDomain.addParams(#[updatedName])
		}
		
		// 3. Add equality constraints setting the new parameters equal to the
		//    expressions on the right-hand side of the projection function.
		for (idxAndAff: reduceExpr.projection.affs.indexed) {
			val idx = idxAndAff.key
			val aff = idxAndAff.value
			
			// First, build an equality constraint from the loop domain's space,
			// then set the coefficient of the added parameter to -1.
			// This way, we can equate the projection function's expression
			// to the parameter (via fp_expr - new_param = 0).
			var equality = ISLConstraint
				.buildEquality(loopDomain.space)
				.setCoefficient(ISLDimType.isl_dim_param, idx + originalParamCount, -1)
			
			// Next, copy all the parameter and index coefficients from the projection function.
			for (paramIdx: 0 ..< aff.nbParams) {
				val coefficient = aff.getCoefficientVal(ISLDimType.isl_dim_param, paramIdx)
				equality = equality.setCoefficient(ISLDimType.isl_dim_param, paramIdx, coefficient)
			}
			for (inIdx: 0 ..< aff.nbInputs) {
				val coefficient = aff.getCoefficientVal(ISLDimType.isl_dim_in, inIdx)
				equality = equality.setCoefficient(ISLDimType.isl_dim_set, inIdx, coefficient)
			}
			
			// Finally, copy over the constant and add the constraint to our loop domain.
			equality = equality.setConstant(aff.constantVal)
			loopDomain = loopDomain.addConstraint(equality)
		}
		
		return loopDomain
	}
	
	static def <T> toArrayList(T... src) {
		return new ArrayList<T>(src);
	}
}