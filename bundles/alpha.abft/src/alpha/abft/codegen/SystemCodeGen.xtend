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
import alpha.model.transformation.StandardizeNames
import alpha.model.util.AShow
import alpha.codegen.isl.ASTConverter
import alpha.codegen.ProgramPrinter

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
		// fully parenthesized
		val S_Y_2 = 'Y(t,i) = (((0.3332) * (Y((-1 + (t)),(-1 + (i))))) + ((0.3333) * (Y((-1 + (t)),((i)))))) + ((0.3) * (Y((-1 + (t)),(1 + (i)))))'
		val S_C1_0 = 'C1(tt,ti) += Y((3*(tt)),((i)))'
		val S_C2_0 = 'C2(tt,ti,p) = (combosW(((p)))) * (C2_NR(tt,ti,p))'
		val S_C2_1 = 'C2(tt,ti,p) = (combosW(((p)))) * (C2_NR2(tt,ti,p))'
		val S_C2_2 = 'C2(tt,ti,p) = (combosW(((p)))) * (C2_NR3(tt,ti,p))'
		val S_C2_NR2_0 = 'C2_NR2(tt,ti,p) += (allW(((w)))) * (Y((3*(tt) - (w)),(14 + 9*(ti) + (p) - (w))))'
		val S_C2_NR3_0 = 'C2_NR3(tt,ti,p) += (allW(((w)))) * (Y((3*(tt) - (w)),((i))))'
		val S_C2_NR_0 = 'C2_NR(tt,ti,p) += (allW(((w)))) * (Y((3*(tt) - (w)),(9*(ti) + (p) + (w))))'
		
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

			  #undef S_Y_2
			  #undef S_C1_0
			  #undef S_C2_0
			  #undef S_C2_1
			  #undef S_C2_2
			  #undef S_C2_NR2_0
			  #undef S_C2_NR3_0
			  #undef S_C2_NR_0
			  
			  #define S_Y_2(t,i)  do { «S_Y_2»; if (3<=t && t<=6 && 15<=i && i<=35) printf("  Y(%d,%2d) %0.4f\n", t, i, Y((t),(i))); } while(0)
			  
			  #define S_C1_0(tt,ti,i)  do { if (tt==2 && ti==2) printf(" C1(%d) Y(%d,%2d) %0.4f\n", (3*(tt)), (3*(tt)),(i), Y((3*(tt)),(i))); «S_C1_0»; } while(0)
			  
			  float before;
			  
			  
			  #define S_C2_NR2_0(tt,ti,p,w)   do { before=C2_NR2((tt),(ti),(p)); «S_C2_NR2_0»; if (tt==2 && ti==2) printf("C2L(%d,%2d) %0.04f * (Y(%d,%2d) %0.4f) <--- %0.4f   (before: %0.4f  after: %0.4f)\n", 3*(tt) - (w), (p), allW((w)),(3*(tt) - (w)),(14 + 9*(ti) + (p) - (w)),Y((3*(tt) - (w)),(14 + 9*(ti) + (p) - (w))), allW((w))*Y((3*(tt) - (w)),(14 + 9*(ti) + (p) - (w))), before, C2_NR2((tt),(ti),(p))); } while(0)
			  #define S_C2_NR3_0(tt,ti,p,w,i) do { «S_C2_NR3_0»; if (tt==2 && ti==2) printf("C2M(%d,%2d) %0.04f * (Y(%d,%2d) %0.4f) <--- %0.4f   (+='ed %3.4f)\n", 3*(tt) - (w), (p), allW((w)),(3*(tt) - (w)),(i),Y((3*(tt) - (w)),(i)), allW((w))*Y((3*(tt) - (w)),(i)), C2_NR3((tt),(ti),(p))); } while(0)
«««			  #define S_C2_NR_0(tt,ti,p,w)    do { «S_C2_NR_0»; if (tt==2 && ti==2) printf("C2R(%d,%2d) %0.04f * (Y(%d,%2d) %0.4f) <--- %0.4f   (+='ed %3.4f)\n", 3*(tt) - (w), (p), allW((w)),(3*(tt) - (w)),(9*(ti) + (p) + (w)),Y((3*(tt) - (w)),(9*(ti) + (p) + (w))), allW((w))*Y((3*(tt) - (w)),(9*(ti) + (p) + (w))), C2_NR((tt),(ti),(p))); } while(0)
			  
			  // This does not work (bug here, when invoking macro functions)
			  #define S_C2_NR_0(tt,ti,p,w)    do { before=C2_NR(tt,ti,p); «S_C2_NR_0»; if (tt==2 && ti==2) { printf("C2R(%d,%2d) %0.4f + %0.4f -> %0.4f\n", 3*(tt) - (w), (p), before, allW((w))*Y((3*(tt) - (w)),(9*(ti) + (p) + (w))), C2_NR((tt),(ti),(p))); printf("tt(%d) ti(%d) p(%d) w(%d)\n", tt, ti, p, w); }} while(0)
«««			  // This works:
«««			  #define S_C2_NR_0(tt,ti,p,w)    do { here(C2_NR,allW,Y,T,N,tt,ti,p,w); } while(0)
			  
			  #define S_C2_0(tt,ti,p) do { «S_C2_0»; if (tt==2 && ti==2) printf(" C2(%d,%2d) <- %0.4f <- %0.4f * %0.4f\n", tt, (p), C2((tt),(ti),(p)), combosW((p)), C2_NR((tt),(ti),(p))); } while(0)
			  #define S_C2_1(tt,ti,p) do { «S_C2_1»; if (tt==2 && ti==2) printf(" C2(%d,%2d) <- %0.4f <- %0.4f * %0.4f\n", tt, (p), C2((tt),(ti),(p)), combosW((p)), C2_NR2((tt),(ti),(p))); } while(0)
			  #define S_C2_2(tt,ti,p) do { «S_C2_2»; if (tt==2 && ti==2) printf(" C2(%d,%2d) <- %0.4f <- %0.4f * %0.4f\n", tt, (p), C2((tt),(ti),(p)), combosW((p)), C2_NR3((tt),(ti),(p))); } while(0)
«««			  

			  «stmtLoops»
			  
			  «debug»
			  
			  
			  «ILoops»
			
			}
			
			void here(float* C2_NR, float* allW, float* Y, long T, long N, int tt, int ti, int p, int w) {
				
				float* before_ptr = &(C2_NR((tt),(ti),(p)));
				float before = C2_NR((tt),(ti),(p));
				«S_C2_NR_0»; 
				float after = C2_NR((tt),(ti),(p));
				if (tt==2 && ti==2) {
					printf("C2R(%d,%2d) %0.4f + %0.4f -> %0.4f\n", 3*(tt) - (w), (p), before, allW((w))*Y((3*(tt) - (w)),(9*(ti) + (p) + (w))), C2_NR((tt),(ti),(p)));
					printf("tt(%d) ti(%d) p(%d) w(%d)\n", tt, ti, p, w);
				}
			}
			«undefStmtMacros»
			
			«debugMain»
		'''
		code	
	}
	
	private def generate2() {
		
		val S_Y_2 = 'Y(t,i) = (((0.3332) * (Y((-1 + (t)),(-1 + (i))))) + ((0.3333) * (Y((-1 + (t)),((i)))))) + ((0.3) * (Y((-1 + (t)),(1 + (i)))))'
		val S_C1_0 = 'C1(tt,ti) += Y((3*(tt)),((i)))'
		val S_C2_0 = 'C2(tt,ti,p) = (combosW(((p)))) * (C2_NR(tt,ti,p))'
		val S_C2_1 = 'C2(tt,ti,p) = (combosW(((p)))) * (C2_NR2(tt,ti,p))'
		val S_C2_2 = 'C2(tt,ti,p) = (combosW(((p)))) * (C2_NR3(tt,ti,p))'
		val S_C2_NR2_0 = 'C2_NR2(tt,ti,p) += (allW(((w)))) * (Y((3*(tt) - (w)),(14 + 9*(ti) + (p) - (w))))'
		val S_C2_NR3_0 = 'C2_NR3(tt,ti,p) += (allW(((w)))) * (Y((3*(tt) - (w)),((i))))'
		val S_C2_NR_0 = 'C2_NR(tt,ti,p) += (allW(((w)))) * (Y((3*(tt) - (w)),(9*(ti) + (p) + (w))))'
		
		val code = '''
			«aboutComments»
			#include<stdio.h>
			#include<stdlib.h>
			#include<math.h>
			#include<time.h>
			
			// Common macros
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
			  
«««			  #undef S_Y_2
«««			  #undef S_C1_0
«««			  #undef S_C2_0
«««			  #undef S_C2_1
«««			  #undef S_C2_2
«««			  #undef S_C2_NR2_0
«««			  #undef S_C2_NR3_0
«««			  #undef S_C2_NR_0
«««			  
«««			  #define S_Y_2(t,i)  do { «S_Y_2»; if (3<=t && t<=6 && 17<=i && i<=33) printf("  Y(%d,%2d) %0.4f\n", t, i, Y((t),(i))); } while(0)
«««			  
«««			  #define S_C1_0(tt,ti,i)  do { if (tt==2 && ti==2) printf(" C1(%d) Y(%d,%2d) %0.4f\n", (3*(tt)), (3*(tt)),(i), Y((3*(tt)),(i))); «S_C1_0»; } while(0)
«««			  
«««			  #define S_C2_0(tt,ti,p) do { if (tt==2 && ti==2) printf(" C2(%d)\n", tt); «S_C2_0»; } while(0)
«««			  #define S_C2_1(tt,ti,p) do { if (tt==2 && ti==2) printf(" C2(%d)\n", tt); «S_C2_1»; } while(0)
«««			  #define S_C2_2(tt,ti,p) do { if (tt==2 && ti==2) printf(" C2(%d)\n", tt); «S_C2_2»; } while(0)
««««««			  
«««			  #define S_C2_NR2_0(tt,ti,p,w)   do { if (tt==2 && ti==2) printf("C2L(%d) Y(%d,%2d) %0.4f\n", 3*(tt) - (w),(3*(tt) - (w)),(14 + 9*(ti) + (p) - (w)),Y((3*(tt) - (w)),(14 + 9*(ti) + (p) - (w)))); «S_C2_NR2_0»; } while(0)
«««			  #define S_C2_NR3_0(tt,ti,p,w,i) do { if (tt==2 && ti==2) printf("C2M(%d) Y(%d,%2d) %0.4f\n", 3*(tt) - (w),(3*(tt) - (w)),(i),Y((3*(tt) - (w)),(i))); «S_C2_NR3_0»; } while(0)
«««			  #define S_C2_NR_0(tt,ti,p,w)    do { if (tt==2 && ti==2) printf("C2R(%d) Y(%d,%2d) %0.4f\n", 3*(tt) - (w),(3*(tt) - (w)),(9*(ti) + (p) + (w)),Y((3*(tt) - (w)),(9*(ti) + (p) + (w)))); «S_C2_NR_0»; } while(0)
«««			  
«««			  
«««			  
			  «stmtLoops»
			  
			  «debug»
			  
			  «undefStmtMacros»
			  
			  «ILoops»
			
			}
			
			
			«debugMain»
		'''
		code	
	}
	
	def debug() '''
		
«««		// debug
«««		for (int t=6; t>=3; t--) {
«««			printf("here ");
«««			for (int i=15; i<=35; i++) {
«««				printf("%0.4f,", Y(t,i));
«««			}
«««			printf("\n");
«««		}
		

	'''
	
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
		code
	}
	
	def localMemoryAllocation() {
		
		val mallocStmts = system.locals
			.map[domain -> 'float *' + memoryMap.getName(name)]
			.map[mallocStmt(key, value)]
		
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