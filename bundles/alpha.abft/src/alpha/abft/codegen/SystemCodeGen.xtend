package alpha.abft.codegen

import alpha.abft.codegen.util.AlphaSchedule
import alpha.abft.codegen.util.ISLASTNodeVisitor
import alpha.abft.codegen.util.MemoryMap
import alpha.codegen.BaseDataType
import alpha.codegen.Factory
import alpha.codegen.alphaBase.AlphaNameChecker
import alpha.codegen.alphaBase.ExprConverter
import alpha.codegen.demandDriven.WriteCTypeGenerator
import alpha.codegen.isl.MemoryUtils
import alpha.codegen.isl.PolynomialConverter
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
import static extension alpha.model.util.CommonExtensions.toArrayList
import static extension alpha.model.util.ISLUtil.toISLIdentifierList
import static extension alpha.model.util.ISLUtil.toISLSchedule

class SystemCodeGen {
	
	val AlphaSystem system
	val AlphaSchedule alphaSchedule
	val ISLSchedule schedule
	val MemoryMap memoryMap
	val ExprConverter exprConverter
	
	new(AlphaSystem system, AlphaSchedule alphaSchedule, MemoryMap memoryMap) {
		this.system = system
		this.alphaSchedule = alphaSchedule
		this.schedule = alphaSchedule.schedule
		this.memoryMap = memoryMap ?: new MemoryMap(system)
		
		val typeGenerator = new WriteCTypeGenerator(BaseDataType.FLOAT, false)
		val nameChecker = new AlphaNameChecker(false)
		this.exprConverter = new ExprConverter(typeGenerator, nameChecker)
		
		StandardizeNames.apply(system)
	}
	
	def static generateSystemCode(AlphaSystem system, AlphaSchedule schedule, MemoryMap memoryMap) {
		val generator = new SystemCodeGen(system, schedule, memoryMap)
		if (system.name.contains('v2'))
			generator.generateV2
		else
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
			
			«signature»
			{
			
			  «localMemoryAllocation»
			
			  «defStmtMacros»

			  «stmtLoops»
			  
			  «undefStmtMacros»
			  
			  «ILoops»
			
			}
			
			«debugMain»
		'''
		code	
	}
	
	private def generateV2() {
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

			  «ILoops»
			
			}
			
			«debugMain»
		'''
		code	
	}

	def aboutComments() {
		val scheduleLines = schedule.root.toString.split('\n')
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
		alphaSchedule.exprStmtMap.keySet
			.map[name | '''#undef «name»''']
			.join('\n')
	}
	
	def defStmtMacros() {
		val macros = alphaSchedule.exprStmtMap.entrySet.map[
			val name = key
			val expr = value
			
			val indexNamesStr = expr.contextDomain.indexNames.join(',')
			val eq = expr.getContainerEquation as StandardEquation
			if (name == 'S_C2_NR2_0')
				println
			val rhs = exprConverter.convertExpr(expr).printExpr
			var lhs = null as String
			var op = null as String
			if (eq.expr instanceof ReduceExpression) {
				val reduceVarIndexNamesStr = eq.variable.domain.indexNames.join(',')
				lhs = '''«eq.variable.name»(«reduceVarIndexNamesStr»)'''
				op = '+='				
			} else {
				lhs = '''«eq.variable.name»(«indexNamesStr»)'''
				op = '='
			}
			val stmtStr = '''«lhs» «op» «rhs»''' 
			
			'''#define «name»(«indexNamesStr») «stmtStr»'''
		].sort
		
		macros.join('\n')
	}
	
	def ILoops() {
		if (system.locals.findFirst[v | v.name == 'I'] === null)
			return '';
		val IDomain = schedule.domain.sets.findFirst[s | s.tupleName == 'S_I_0'].setTupleName('printI').toUnionSet
		val indexNames = IDomain.sets.get(0).indexNames
		val idxStr = indexNames.join(',')
		val SI = '''printI[«idxStr»]'''
		val paramStr = '[' + IDomain.copy.params.paramNames.join(',') + ']'
		val ISchedule = '''
			domain: "«IDomain.toString»"
			child:
			  schedule: "«paramStr»->[\
			    { «SI»->[tt] }, \
			    { «SI»->[ti] } \
			  ]"
			  
		'''.toISLSchedule
		
		val iterators = indexNames.toISLIdentifierList
		val build = ISLASTBuild.buildFromContext(ISchedule.domain.copy.params)
						.setIterators(iterators.copy)
		
		val node = build.generate(ISchedule.copy)
		
		val codegenVisitor = new ISLASTNodeVisitor().genC(node)
		
		val code = '''
			// Print I values
			#define printI(«idxStr») printf("I(«indexNames.map['%d'].join(',')») = %E\n",«idxStr»,I(«idxStr»))
			
			«codegenVisitor.toCode»
			
			#undef printI
		'''
		code
	}
	
	def stmtLoops() {
//		val iterators = #['tt','t','ti','i'].toISLIdentifierList
		val build = ISLASTBuild.buildFromContext(schedule.domain.copy.params)
//						.setIterators(iterators.copy)
		
		val node = build.generate(schedule.copy)
		
//		val result = ASTConverter.convert(node)
//		result.statements.map[s | ProgramPrinter.printStmt(s)].join('\n')
		
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
	
	def dbg() '''
	'''
	
	
	
	
	
	
	
	
}