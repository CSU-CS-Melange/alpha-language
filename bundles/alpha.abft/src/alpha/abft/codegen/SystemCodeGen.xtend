package alpha.abft.codegen

import alpha.abft.codegen.util.ISLASTNodeVisitor
import alpha.abft.codegen.util.MemoryMap
import alpha.codegen.BaseDataType
import alpha.codegen.Factory
import alpha.codegen.alphaBase.AlphaNameChecker
import alpha.codegen.alphaBase.ExprConverter
import alpha.codegen.demandDriven.WriteCTypeGenerator
import alpha.codegen.isl.MemoryUtils
import alpha.codegen.isl.PolynomialConverter
import alpha.model.AlphaExpression
import alpha.model.AlphaSystem
import alpha.model.ReduceExpression
import alpha.model.StandardEquation
import alpha.model.Variable
import alpha.model.transformation.StandardizeNames
import alpha.model.util.AShow
import fr.irisa.cairn.jnimap.isl.ISLASTBuild
import fr.irisa.cairn.jnimap.isl.ISLSchedule
import fr.irisa.cairn.jnimap.isl.ISLSet

import static extension alpha.codegen.ProgramPrinter.printExpr
import static extension alpha.codegen.ProgramPrinter.printStmt
import static extension alpha.codegen.demandDriven.WriteC.getCardinalityExpr
import static extension alpha.codegen.isl.AffineConverter.convertMultiAff
import static extension alpha.model.util.AlphaUtil.getContainerEquation
import static extension alpha.model.util.ISLUtil.toISLIdentifierList
import static extension alpha.model.util.ISLUtil.toISLSchedule
import static extension alpha.model.util.ISLUtil.toEmptyUnionSet
import static extension alpha.abft.ABFT.buildParamStr
import fr.irisa.cairn.jnimap.isl.ISLUnionSet
import alpha.model.SystemBody

class SystemCodeGen {
	
	val AlphaSystem system
	val SystemBody systemBody
	val ISLSchedule schedule
	val String scheduleStr
	val ISLUnionSet scheduleDomain
	val MemoryMap memoryMap
	val ExprConverter exprConverter
	val String stmtPrefix
	
	new(AlphaSystem system, String schedule, MemoryMap memoryMap) {
		this.system = system
		this.systemBody = system.systemBodies.get(0)
		this.memoryMap = memoryMap ?: new MemoryMap(system)
		
		val typeGenerator = new WriteCTypeGenerator(BaseDataType.FLOAT, false)
		val nameChecker = new AlphaNameChecker(false)
		this.exprConverter = new ExprConverter(typeGenerator, nameChecker)
		this.stmtPrefix = 'S'
		
		this.scheduleDomain = systemBody.standardEquations
		.map[variable.name -> variable.getStmtDomain(expr)]
		.map[value.setTupleName(key).copy.toUnionSet]
		.fold(systemBody.parameterDomain.space.toEmptyUnionSet, [ret, d | ret.union(d)])
		this.scheduleStr = schedule.injectIndices(scheduleDomain, stmtPrefix)
		this.schedule = scheduleStr.toISLSchedule
		
		StandardizeNames.apply(system)
	}
	
	def static generateSystemCode(AlphaSystem system, CharSequence schedule, MemoryMap memoryMap) {
		system.generateSystemCode(schedule.toString, memoryMap)
	}
	def static generateSystemCode(AlphaSystem system, String schedule, MemoryMap memoryMap) {
		if (system.systemBodies.size > 1) {
			throw new Exception('Only systems with a single body are currently supported')
		}
		val generator = new SystemCodeGen(system, schedule, memoryMap)
		generator.generate
	}
	
	private def generate() {
		val code = '''
			«aboutComments»
			#include<stdio.h>
			#include<stdlib.h>
			#include<math.h>
			#include<time.h>
			
			#define max(x, y)   ((x)>(y) ? (x) : (y))
			#define min(x, y)   ((x)>(y) ? (y) : (x))
			#define ceild(n,d)  (int)ceil(((double)(n))/((double)(d)))
			#define floord(n,d) (int)floor(((double)(n))/((double)(d)))
			#define mallocCheck(v,s,d) if ((v) == NULL) { printf("Failed to allocate memory for %s : size=%lu\n", "sizeof(d)*(s)", sizeof(d)*(s)); exit(-1); }

			// Memory mapped targets
			«memoryMap.uniqueTargets.map[memoryTargetMacro].join('\n')»
			
			// Memory access functions
			«system.variables.map[memoryMacro].join('\n')»
			
			void here(float* C2_NR, float* allW, float* Y, long T, long N, int tt, int ti, int p, int w);
			
			«signature»
			{
			
			  «localMemoryAllocation»

			  «defStmtMacros»

			  «stmtLoops»

			  «undefStmtMacros»

			  «printResultLoops('I')»
			
			}
			
			«debugMain»
		'''
		code	
	}

	def aboutComments() {
		val scheduleLines = scheduleStr.toString.split('\n')
		val code = '''
			/* «system.name».c
			 * 
			 * Code generation of the following Alpha system:
			 «FOR line : AShow.print(system).split('\n')»
			 *   «line»
			«ENDFOR»
			 *
			 * Uses the memory map:
			 «FOR line : memoryMap.toString.split('\n')»
			 *   «line»
  			 «ENDFOR»
			 *
			 * Implements the schedule:
			 «FOR i : 1..<scheduleLines.size»
			 *   «scheduleLines.get(i)»
 			 «ENDFOR»
			 *
			 */
		'''
		println
		code
	}
	
	def localMemoryAllocation() {
		
		/*
		 * get list of local variables associated with each mapped name
		 */
		val chunkVariables = newHashMap
		system.variables.forEach[v | 
			val mappedName = memoryMap.getName(v.name)
			val list = chunkVariables.get(mappedName) ?: newLinkedList
			list.add(v)
			chunkVariables.put(mappedName, list)
		]
		
		/*
		 * For now, assert that mapping across locals and inputs/outputs
		 * do not exist
		 */
		chunkVariables.entrySet.filter[value.size > 0]
			.filter[value.filter[isLocal].size > 0 && (value.filter[isInput || isOutput].size > 0)]
			.forEach[
				throw new Exception('Mappings across locals and inputs/outputs not currently supported')
			]
		
		/*
		 * construct the domain (union of all variable domains) for each
		 * mapped name
		 */
		val mallocStmts = chunkVariables.entrySet
			.filter[value.filter[isLocal].size > 0]
			.map[value.map[domain.copy].reduce[v1,v2 | v1.union(v2)].coalesce -> key]
			.map[mallocStmt(key, 'float *' + value)]
		
		val code = '''
			// Local memory allocation
			«mallocStmts.map[printStmt].join('\n')»
		'''
		code
	}
	
	def mallocStmt(ISLSet domain, String name) {
		// Call "malloc" to allocate memory and assign it to the variable.
		val cardinalityExpr = domain.cardinalityExpr
		val dataType = Factory.dataType(BaseDataType.FLOAT, 1)
		val mallocCall = Factory.callocCall(dataType, cardinalityExpr)
		val mallocAssignment = Factory.assignmentStmt(name, mallocCall)
		mallocAssignment
	}
	
	def undefStmtMacros() {
		system.systemBodies.get(0).standardEquations
			.map['''#undef S«variable.name»''']
			.join('\n')
	}
	
	def dispatch printStmtExpr(ReduceExpression re) {
		exprConverter.convertExpr(re.body).printExpr
	}
	def dispatch printStmtExpr(AlphaExpression ae) {
		exprConverter.convertExpr(ae).printExpr
	}
	
	def defStmtMacros() {
		
		val macros = system.systemBodies.get(0).standardEquations.map[
			val indexNamesStr = expr.contextDomain.indexNames.join(',')
			val name = 'S' + variable.name
			val eq = expr.getContainerEquation as StandardEquation
			
			val rhs = expr.printStmtExpr
			val lhs = '''«eq.variable.name»(«indexNamesStr»)'''
			var defIndexNamesStr = indexNamesStr
			var op = '='
			
			if (eq.expr instanceof ReduceExpression) {
				val re = eq.expr as ReduceExpression
				val reduceVarIndexNamesStr = re.body.contextDomain.indexNames.join(',')
				defIndexNamesStr = reduceVarIndexNamesStr
				op = '+='				
			}
			val stmtStr = '''«lhs» «op» «rhs»''' 
			
			'''#define «name»(«defIndexNamesStr») «stmtStr»'''
		].sort
		
		macros.join('\n')
	}
	
	def printResultLoops(String name) {
		val variable = system.locals.findFirst[v | v.name == name] 
		if (variable === null)
			return '';
			
		val domain = variable.domain.setTupleName(stmtPrefix + name).toUnionSet
		val indexNames = domain.sets.get(0).indexNames
		val idxStr = indexNames.join(',')
		val SVar = '''«stmtPrefix»«name»[«idxStr»]'''
		val paramStr = '[' + domain.copy.params.paramNames.join(',') + ']'
		val ISchedule = '''
			domain: "«domain.toString»"
			child:
			  schedule: "«paramStr»->[«indexNames.map[i | '''{ «SVar»->[«i»] }'''].join(',')»]"
			  
		'''.toISLSchedule
		
		val iterators = indexNames.toISLIdentifierList
		val build = ISLASTBuild.buildFromContext(ISchedule.domain.copy.params)
						.setIterators(iterators.copy)
		
		val node = build.generate(ISchedule.copy)
		
		val codegenVisitor = new ISLASTNodeVisitor().genC(node)
		
		val code = '''
			// Print «name» values
			#define «stmtPrefix»«name»(«idxStr») printf("«name»(«indexNames.map['%d'].join(',')») = %E\n",«idxStr»,«name»(«idxStr»))
			
			«codegenVisitor.toCode»
			
			#undef «stmtPrefix»«name»
		'''
		code
	}
	
	def stmtLoops() {
		val build = ISLASTBuild.buildFromContext(scheduleDomain.copy.params)
		
		val node = build.generate(schedule.copy)
		
		val scheduleLines = schedule.root.toString.split('\n')
		
		val codegenVisitor = new ISLASTNodeVisitor().genC(node)
		val code = '''		
			/*
			 «FOR i : 1..<scheduleLines.size»
			 * «scheduleLines.get(i)»
			 «ENDFOR»
			 */
			«codegenVisitor.toCode»
		'''
		code
	}
	
	def signature() {
		val paramArgs = system.parameterDomain.paramNames
			.map[p | 'long ' + p]
			.join(',')
		val ioArgs = (system.inputs + system.outputs)
			.map[v | 'float *' + v.name]
			.join(',')
		'''void «system.name»(«paramArgs», «ioArgs»)'''
	}
	
	def memoryMacro(Variable variable) {
		val stmtName = variable.name
		val mappedName = 'mem_' + memoryMap.getName(variable.name)
		
		val pwmaff = memoryMap.getMap(variable).toPWMultiAff
		if (pwmaff.nbPieces != 1)
			throw new Exception('Error constructing memory macro, multiple pieces in map.')
		val maff = pwmaff.getPiece(0).getMaff
		
		val varAcc = variable.domain.indexNames.join(',')
		val memAcc = maff.convertMultiAff.map[printExpr].join(',')
		
		'''#define «stmtName»(«varAcc») «mappedName»(«memAcc»)'''
	}
	
	def memoryTargetMacro(Pair<String,ISLSet> entry) {
		val name = entry.key
		val range = entry.value
		val stmtName = 'mem_' + name
		
		val rank = MemoryUtils.rank(range)
		val accessExpression = PolynomialConverter.convert(rank)
		val macroReplacement = Factory.arrayAccessExpr(name, accessExpression)
		val macroStmt = Factory.macroStmt(stmtName, range.indexNames, macroReplacement)
		return '''«macroStmt.printStmt»'''
	}
	
	
	
	
	def debugMain() {
		val XVar = system.inputs.get(0)
		val stmtName = 'S' + XVar.name
		val XDomain = XVar.domain.copy.setTupleName(stmtName)
		val mallocSize = XDomain.indexNames.map['(N+1)'].join('*')
		val paramStr = XDomain.copy.params.paramNames.join(',')
		val idxs = XDomain.indexNames
		val X = stmtName + '[' + idxs.join(',') + ']'
		
		
		val xInitSchedStr = '''
			domain: "«XDomain.toString»"
			child:
			  schedule: "[«paramStr»]->[\
			    «idxs.map[idx | '''{ «X»->[«idx»] }'''].join(', \\\n')» \
			  ]"
			  
		'''
		val xInitSched = xInitSchedStr.toISLSchedule
		
		val iterators = idxs.toISLIdentifierList
		val build = ISLASTBuild.buildFromContext(xInitSched.domain.copy.params)
						.setIterators(iterators.copy)
		
		val node = build.generate(xInitSched.copy)
		val codegenVisitor = new ISLASTNodeVisitor().genC(node)
		
		val XLoops = '''
			#define «stmtName»(«idxs.join(',')») «XVar.name»(«idxs.join(',')») = rand() % 100 + 1
			«codegenVisitor.toCode»
			#undef «stmtName»
			
		'''
		val nd = idxs.size
		val T = switch (nd) {
			case 1 : 100
			case 2 : 50
			case 3 : 15
		}
		val N = switch (nd) {
			case 1 : 1000
			case 2 : 300
			case 3 : 70
		}
		
		
		val code = '''
			int main() {
				long T = «T»;
				long N = «N»;
				
				float *X = malloc(sizeof(float)*«mallocSize»);
				float *Y = malloc(sizeof(float)*2*«mallocSize»);
				
				srand(0);
				
				«XLoops»
				
				«system.name»(T, N, X, Y);
				
				return 0;
			}
		'''
		code
	}
	
	
	
	
	
	/* Gets the tuple with the name 'name' and its indices */
	def static String stmt(ISLUnionSet uset, String name) {
		val set = uset.sets.findFirst[s | s.tupleName == name]
		set.tupleName + '[' + set.indexNames.join(',') + ']' 
	}
	
	/* Replaces the variable strings in the schedule with their statement names */
	def static injectIndices(CharSequence schedule, ISLUnionSet domain) {
		schedule.injectIndices(domain, 'S')
	}
	def static injectIndices(CharSequence schedule, ISLUnionSet domain, String stmtPrefix) {
		val domStr = domain.sets.map[setTupleName(stmtPrefix + tupleName).toUnionSet]
			.reduce[d1,d2|d1.union(d2)]
			.toString
		domain.sets.map[tupleName].fold(schedule.toString, [ret, n | ret.replace(n+"'", stmtPrefix+domain.stmt(n))])
			.replace("domain'", domStr)
			.replace("params'", domain.space.buildParamStr)
	}
	
	/* Returns the context domain of the expression or the body of the reduce expression */
	def static dispatch getStmtDomain(Variable variable, ReduceExpression re) {
		re.body.contextDomain.copy
	}
	def static dispatch getStmtDomain(Variable variable, AlphaExpression ae) {
		ae.contextDomain.copy
	}
	
	
	
	
	
}