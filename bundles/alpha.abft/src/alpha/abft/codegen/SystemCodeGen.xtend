package alpha.abft.codegen

import alpha.abft.codegen.util.ISLASTNodeVisitor
import alpha.abft.codegen.util.MemoryMap
import alpha.codegen.BaseDataType
import alpha.codegen.Factory
import alpha.codegen.alphaBase.AlphaNameChecker
import alpha.codegen.alphaBase.ExprConverter
import alpha.codegen.demandDriven.WriteCTypeGenerator
import alpha.codegen.isl.PolynomialConverter
import alpha.model.AlphaExpression
import alpha.model.AlphaSystem
import alpha.model.ReduceExpression
import alpha.model.StandardEquation
import alpha.model.SystemBody
import alpha.model.Variable
import alpha.model.transformation.StandardizeNames
import alpha.model.util.AShow
import alpha.model.util.AlphaUtil
import fr.irisa.cairn.jnimap.barvinok.BarvinokBindings
import fr.irisa.cairn.jnimap.isl.ISLASTBuild
import fr.irisa.cairn.jnimap.isl.ISLDimType
import fr.irisa.cairn.jnimap.isl.ISLPWQPolynomial
import fr.irisa.cairn.jnimap.isl.ISLSchedule
import fr.irisa.cairn.jnimap.isl.ISLSet
import fr.irisa.cairn.jnimap.isl.ISLUnionSet
import org.eclipse.xtend.lib.annotations.Accessors

import static extension alpha.abft.ABFT.buildParamStr
import static extension alpha.codegen.ProgramPrinter.print
import static extension alpha.codegen.ProgramPrinter.printExpr
import static extension alpha.codegen.ProgramPrinter.printStmt
import static extension alpha.codegen.demandDriven.WriteC.getCardinalityExpr
import static extension alpha.codegen.isl.AffineConverter.convertAff
import static extension alpha.codegen.isl.AffineConverter.convertMultiAff
import static extension alpha.codegen.isl.PolynomialConverter.convert
import static extension alpha.model.util.AlphaUtil.getContainerEquation
import static extension alpha.model.util.CommonExtensions.toArrayList
import static extension alpha.model.util.CommonExtensions.zipWith
import static extension alpha.model.util.ISLUtil.toEmptyUnionSet
import static extension alpha.model.util.ISLUtil.toISLIdentifierList
import static extension alpha.model.util.ISLUtil.toISLMap
import static extension alpha.model.util.ISLUtil.toISLSchedule
import static extension alpha.model.util.ISLUtil.toISLSet
import static extension fr.irisa.cairn.jnimap.isl.ISLMap.buildIdentity
import alpha.model.CaseExpression

enum Version {
		BASELINE,
		ABFT_V1,
		ABFT_V2,
		WRAPPER
	}

class SystemCodeGen {
	
	public static String ERROR_INJECTION = 'ERROR_INJECTION'
	public static String TIMING = 'TIMING'
	public static String REPORT_COMPLEXITY_ONLY = 'REPORT_COMPLEXITY_ONLY'
	
	@Accessors(PUBLIC_GETTER, PROTECTED_SETTER)
	val AlphaSystem system
	
	@Accessors(PUBLIC_GETTER, PROTECTED_SETTER)
	val SystemBody systemBody
	
	@Accessors(PUBLIC_GETTER, PROTECTED_SETTER)
	val ISLSchedule schedule
	
	@Accessors(PUBLIC_GETTER, PROTECTED_SETTER)
	val String scheduleStr
	
	@Accessors(PUBLIC_GETTER, PROTECTED_SETTER)
	val ISLUnionSet scheduleDomain
	
	@Accessors(PUBLIC_GETTER, PROTECTED_SETTER)
	val ExprConverter exprConverter
	
	@Accessors(PUBLIC_GETTER, PROTECTED_SETTER)
	val MemoryMap memoryMap
	
	@Accessors(PUBLIC_GETTER, PROTECTED_SETTER)
	val String stmtPrefix
	
	@Accessors(PUBLIC_GETTER, PROTECTED_SETTER)
	val Variable stencilVar
	
	@Accessors(PUBLIC_GETTER, PROTECTED_SETTER)
	val Version version
	
	@Accessors(PUBLIC_GETTER, PROTECTED_SETTER)
	val int[] tileSizes
	
	@Accessors(PUBLIC_GETTER, PROTECTED_SETTER)
	val BaseDataType dataType
	
	new(AlphaSystem system, MemoryMap memoryMap, Version version, int[] tileSizes) {
		this(system, system.defaultSchedule, memoryMap, version, tileSizes)
	}
	
	new(AlphaSystem system, String schedule, MemoryMap memoryMap, Version version, int[] tileSizes) {
		if (system.outputs.size > 1)
			throw new Exception('Codegen for systems with more than 1 output variable not currently implemented')
			
		this.system = system
		this.systemBody = system.systemBodies.get(0)
		this.memoryMap = memoryMap ?: new MemoryMap(system)
		
		this.dataType = BaseDataType.FLOAT
		val typeGenerator = new WriteCTypeGenerator(dataType, false)
		val nameChecker = new AlphaNameChecker(false)
		this.exprConverter = new ExprConverter(typeGenerator, nameChecker)
		this.stmtPrefix = 'S'
		
		this.stencilVar = system.outputs.get(0)
		this.version = version
		this.tileSizes = tileSizes
		
		this.scheduleDomain = buildScheduleDomain
		this.scheduleStr = schedule.injectIndices(scheduleDomain, stmtPrefix)
		
//		println(scheduleStr)
		
		this.schedule = scheduleStr.toISLSchedule
		
		StandardizeNames.apply(system)
	}
	
	def buildScheduleDomain() {
		// case expression for Y needs to be split into several statements
		// case expression for C2 in v2 also needs to be split
		val yCBEs = (systemBody.standardEquations.findFirst[variable == stencilVar].expr as CaseExpression).exprs
		val yDomain = (0..<yCBEs.size).map[i | i->yCBEs.get(i)].fold(
			ISLUnionSet.buildEmpty(stencilVar.domain.space),
			[ret, p | 
				val i = p.key
				val expr = p.value
				ret.union(expr.contextDomain.copy.setTupleName(stencilVar.name + '_cb' + i).toUnionSet)
			]
		)
		
		val c2Domain = if (version == Version.ABFT_V2) {
			val c2Eq = systemBody.standardEquations.findFirst[variable.name == 'C2']
			val c2CBEs = (c2Eq.expr as CaseExpression).exprs;
			(0..<c2CBEs.size).map[i | i->c2CBEs.get(i)].fold(
				ISLUnionSet.buildEmpty(c2Eq.variable.domain.space),
				[ret, p | 
					val i = p.key
					val expr = p.value
					ret.union(expr.contextDomain.copy.setTupleName('C2_cb' + i).toUnionSet)
				]
			)
		} else if (version == Version.BASELINE || version == Version.WRAPPER) {
			ISLUnionSet.buildEmpty(stencilVar.domain.space)
		} else {
			val c2Eq = systemBody.standardEquations
				.findFirst[variable.name == 'C2']
			c2Eq.variable.getStmtDomain(c2Eq.expr)
				.setTupleName('C2').toUnionSet
		}
		
		val domain = systemBody.standardEquations
			.reject[variable == stencilVar || variable.name == 'C2']
			.map[variable.name -> variable.getStmtDomain(expr)]
			.map[value.setTupleName(key).copy.toUnionSet]
			.fold(systemBody.parameterDomain.space.toEmptyUnionSet, [ret, d | ret.union(d)])
		
		domain.union(yDomain).union(c2Domain)
	}
	
	def static String defaultSchedule(AlphaSystem system) {
		val domain = system.variables.reject[isInput]
			.map[domain.setTupleName(name)]
			.map[toUnionSet]
			.reduce[d1, d2 | d1.union(d2)]
		val schedule = '''
			domain: "«domain»"
		'''
		
		schedule.toString
	}
	
	def static generateSystemCode(AlphaSystem system, CharSequence schedule, MemoryMap memoryMap, Version version, int[] tileSizes) {
		system.generateSystemCode(schedule.toString, memoryMap, version, tileSizes)
	}
	def static generateSystemCode(AlphaSystem system, String schedule, MemoryMap memoryMap, Version version, int[] tileSizes) {
		if (system.systemBodies.size > 1) {
			throw new Exception('Only systems with a single body are currently supported')
		}
		val generator = new SystemCodeGen(system, schedule, memoryMap, version, tileSizes)
		generator.generate
	}
	
	
	protected def generate() {
		
		val TT = tileSizes.get(0)
		val TS = (1..<tileSizes.size).map[i | tileSizes.get(i)].max
		
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
	#define new_result() { .valid=0, .TP=0L, .FP=0L, .TN=0L, .FN=0L, .TPR=0.0f, .FPR=0.0f, .FNR=0.0f, .bit=0, .inj={.tt=0, .ti=0, .tj=0, .tk=0}, .result=0.0f, .noise=0.0f }
	
	void initialize_timer();
	void reset_timer();
	void start_timer();
	void stop_timer();
	double elapsed_time();
	
	struct INJ {
		int tt;
		int ti;
		int tj;
		int tk;
	};

	struct Result {
		int valid;
		long TP;
		long FP;
		long TN;
		long FN;
		float TPR;
		float FPR;
		float FNR;
		int bit;
		struct INJ inj;
		double result;
		double noise;
	};
	
	// Memory mapped targets
	«memoryMap.uniqueTargets.map[memoryTargetMacro].join('\n')»
	
	// Memory access functions
	«system.variables.map[memoryMacro].join('\n')»
	
	«IF version == Version.ABFT_V1 || version == Version.ABFT_V2»
	#ifdef ERROR_INJECTION
	// Error injection harness
	«stencilVar.domain.indexNames.map[i | 
		'''int t«i»_INJ'''].join(';\n')
	»;
	int BIT;
	
	void inject_«system.name»(float *val) {
		int *bits;
		bits = (int*)val;
		*bits ^= 1 << BIT;
	}
	#endif
	
	«ENDIF»
	#if defined «REPORT_COMPLEXITY_ONLY»
	«system.sigantureParamsAsFloats»
	#else
	«system.signature»
	#endif
	{
		#if defined «REPORT_COMPLEXITY_ONLY»
		«computeComplexity»
		#else
		«IF version == Version.ABFT_V1 || version == Version.ABFT_V2»
		#if defined ERROR_INJECTION
		// Error injection configuration
		«stencilVar.domain.indexNames.map[i |
  	  '''t«i»_INJ = getenv("t«i»_INJ") != NULL ? atoi(getenv("t«i»_INJ")) : -1'''].join(';\n')
  	  	»;
		BIT = getenv("bit") != NULL ? atoi(getenv("bit")) : (int)(rand() % «if (dataType == BaseDataType.FLOAT) 32 else 64»);
		#endif
		«ENDIF»
			
		«localMemoryAllocation»

		«defStmtMacros»

		// Timers
		double execution_time;
		initialize_timer();
		start_timer();

		«stmtLoops»

		stop_timer();
		execution_time = elapsed_time();

		«undefStmtMacros»

		«prepareResult»
	
		«localMemoryFree»
		
		return result;
		#endif
	}
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
			 *	 «line»
			«ENDFOR»
			 *
			 * Uses the memory map:
			 «FOR line : memoryMap.toString.split('\n')»
			 *	 «line»
  			 «ENDFOR»
			 *
			 * Implements the schedule:
			 «FOR i : 1..<scheduleLines.size»
			 *   «scheduleLines.get(i)»
 			 «ENDFOR»
«««			 *
«««			 * Has complexity
«««			 «FOR line : computeComplexity.toString.split('\n')»
«««			 *   «line»
««« 			 «ENDFOR»
			 *
			 */
		'''
		code
	}
	
	def computeComplexity() {
		
		val stencilVarPoints = stencilVar.computeComplexity
		if (version == Version.BASELINE) {
			return '' 
		}
		
		val checksumPoints = systemBody.standardEquations
			.map[variable.computeComplexity]
			.reduce[p1,p2 | p1.add(p2)]
		val versionStr = switch (version) {
			case Version.ABFT_V1: 'v1 '
			case Version.ABFT_V2: 'v2 '
			default: ''
		}
		
		val stencilVarCard = PolynomialConverter.convert(stencilVarPoints).printExpr.toString
			.replaceAll('([-]*[0-9][0-9]*)', '((double)$1)')
		val checksumsCard = PolynomialConverter.convert(checksumPoints).printExpr.toString
			.replaceAll('([-]*[0-9][0-9]*)', '((double)$1)')
		
		'''
			#undef ceild
			#undef floord
			#define ceild(n,d)  (double)ceil(((double)(n))/((double)(d)))
			#define floord(n,d) (double)floor(((double)(n))/((double)(d)))
			
			double «stencilVar.name»_card = «stencilVarCard»;
			double total_card = «checksumsCard»;
			double expected_overhead = total_card / «stencilVar.name»_card;
			printf("«versionStr»(«stencilVar.name»,C,overhead): %0.0lf,%0.0lf,%0.2lf\n", «stencilVar.name»_card, total_card, expected_overhead);
			
			#undef ceild
			#undef floord
			#define ceild(n,d)  (int)ceil(((double)(n))/((double)(d)))
			#define floord(n,d) (int)floor(((double)(n))/((double)(d)))
		'''
	}
	
	def ISLPWQPolynomial computeComplexity(Variable variable) {
		val eq = systemBody.standardEquations.findFirst[e | e.variable == variable]
		computeComplexity(variable, eq.expr)
	}
	def dispatch ISLPWQPolynomial computeComplexity(Variable variable, ReduceExpression re) {
		computeComplexity(variable, re.body)
	}
	def dispatch ISLPWQPolynomial computeComplexity(Variable variable, AlphaExpression ae) {
		BarvinokBindings.card(ae.contextDomain)
	}
	
	def localMemoryFree() {
		system.locals.memoryFree
	}
	
	def memoryFree(Variable[] variables) {
		
		val freeStmts = variables.getMemoryChunks
			.map[value.map[memoryMap.getRange(name).copy].reduce[v1,v2 | v1.union(v2)].coalesce -> key]
			.map[memoryFree(key, value)]
		
		val code = if (freeStmts.size > 0) '''
			// Free local memory allocation
			«freeStmts.join('\n')»
		''' else ''''''
		
		code
	}
	
	def memoryFree(ISLSet domainWithParams, String name) {
		
		val dim = domainWithParams.indexNames.size
		val domain = domainWithParams.basicSets
			.map[dropConstraintsNotInvolvingDims(ISLDimType.isl_dim_out, 0, dim).toSet]
			.reduce[s1, s2 | s1.union(s2)]
		
		val indexNames = domain.indexNames;
		
		val dimSizes = (0..<dim).map[i | (dim>..0).reject[j | i==j].fold(
				domain.copy.toIdentityMap.space.buildIdentity,
				[ret, d | ret.projectOut(ISLDimType.isl_dim_out, d, 1)])]
			.map[m | domain.copy.apply(m)]
			.map[coalesce]
			.map[cardinalityExpr]
		
		var code = (0..<domain.indirectionLevel-1).zipWith(indexNames).fold('''''', [ret, p |
				val i = p.key
				val indexName = p.value
				val expr = dimSizes.get(i)
//				val dataType = Factory.dataType(dataType, dim - i - 1)
				val indentation = (0..<i).map['	'].join
//				val writeAcc = '''«name»[«(0..<i+1).map[j | indexNames.get(j)].join('][')»]'''
				'''
					«ret»
					«indentation»for (int «indexName»=0; «indexName»<«expr.printExpr»; «indexName»++) {'''
				])
		{
			val indentation = (0..<domain.indirectionLevel-1).map['	'].join
			val writeAcc =  if (indexNames.size > 1) {
				'''«name»[«(0..<domain.indirectionLevel-1).map[j | indexNames.get(j)].join('][')»]'''
			} else {
				name
			}
			code = '''
				«code»
				«indentation» free(«writeAcc»);
			'''
		}
		code = (domain.indirectionLevel-1>..0).zipWith(indexNames).fold(code, [ret, p |
			val i = p.key
			val writeAcc = if (i == 0) {
				name
			} else {
				'''«name»[«(0..<i).map[j | indexNames.get(j)].join('][')»]'''
			}
			val indentation = (0..<i).map['	'].join
			'''
				«ret»
				«indentation»}
				«indentation»free(«writeAcc»);
			'''
		])
		
		code
		
		
	}
	
	def localMemoryAllocation() {
		system.locals.memoryAllocation
	}
	
	def memoryAllocation(Variable[] variables) {
		variables.memoryAllocation('')
	}
	def memoryAllocation(Variable[] variables, String suffix) {
		/*
		 * construct the domain (union of all variable domains) for each
		 * mapped name
		 */
		val mallocStmts = variables.getMemoryChunks
			.map[value.map[memoryMap.getRange(name).copy].reduce[v1,v2 | v1.union(v2)].coalesce -> key]
			.map[mallocStmt(key, value + suffix)]
		
		val code = if (mallocStmts.size > 0) '''
			// Local memory allocation
			«mallocStmts.join('\n')»
		''' else ''''''
		
		code
	}
	
	/* Returns the map of memory chunk name and its domain */
	def getMemoryChunks(Variable[] variables) {
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
		
		chunkVariables.entrySet.filter[value.filter[v | variables.contains(v)].size > 0]
	}
	
	def mallocStmt(ISLSet domainWithParams, String name) {
		// Call "malloc" to allocate memory and assign it to the variable.
		// Just use the bounding box
		
		val dim = domainWithParams.indexNames.size
		val domain = domainWithParams.basicSets
			.map[dropConstraintsNotInvolvingDims(ISLDimType.isl_dim_out, 0, dim).toSet]
			.reduce[s1, s2 | s1.union(s2)]
		
		val indexNames = domain.indexNames;
		
		val dimSizes = (0..<dim).map[i | (dim>..0).reject[j | i==j].fold(
				domain.copy.toIdentityMap.space.buildIdentity,
				[ret, d | ret.projectOut(ISLDimType.isl_dim_out, d, 1)])]
			.map[m | domain.copy.apply(m)]
			.map[coalesce]
			.map[cardinalityExpr]
		
		val mallocAssignment = Factory.assignmentStmt(
			'''«Factory.dataType(dataType, dim).print» «name»''', 
			Factory.callocCall(Factory.dataType(dataType, dim), dimSizes.get(0))
		).printExpr + ';\n'
		
		var code = (0..<domain.indirectionLevel-1).zipWith(indexNames).fold(mallocAssignment, [ret, p |
				val i = p.key
				val indexName = p.value
				val expr = dimSizes.get(i)
				val dataType = Factory.dataType(dataType, dim - i - 1)
				val mallocCall = Factory.callocCall(dataType, dimSizes.get(i+1))
				val indentation = (0..<i).map['	'].join
				val writeAcc = '''«name»[«(0..<i+1).map[j | indexNames.get(j)].join('][')»]'''
				'''
					«ret»
					«indentation»for (int «indexName»=0; «indexName»<«expr.printExpr»; «indexName»++) {
					«indentation»	«writeAcc» = «mallocCall.printExpr»;'''
				])
		
		code = (domain.indirectionLevel-1>..0).zipWith(indexNames).fold(code, [ret, p |
			val i = p.key
			val indentation = (0..<i).map['	'].join
			'''
				«ret»
				«indentation»}
			'''
		])
		
		code
	}
	
	def indirectionLevel(Variable variable) {
		variable.domain.indirectionLevel
	}
	def indirectionLevel(ISLSet domain) {
		Integer.max(1, domain.nbIndices)
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
		
		// error injection site access
		val TT = tileSizes.get(0)
		val spatialIndexNames = (1..<stencilVar.domain.indexNames.size).map[i | stencilVar.domain.indexNames.get(i)]
		val spatialTileSizes = (1..<tileSizes.size).map[i | tileSizes.get(i)]
		val spatialContext = spatialIndexNames.zipWith(spatialTileSizes)
		
		val nbSpatialDims = stencilVar.domain.indexNames.size - 1
		val injectionName = if (version == Version.ABFT_V1) {
			'C2' 
		} else {
			'C2_NR' + (0..<nbSpatialDims).map[3].reduce[v1,v2|v1*v2]
		}
		val stdEqs = system.systemBodies.get(0).standardEquations
		val stencilVarMacros = {
			val yCBEs = (stdEqs.findFirst[variable == stencilVar].expr as CaseExpression).exprs;
			(0..<yCBEs.size).map[i |
				val expr = yCBEs.get(i)
				val indexNames = expr.contextDomain.indexNames
				val indexNamesStr = indexNames.join(',')
				val name = 'S' + stencilVar.name + '_cb' + i
				val eq = expr.getContainerEquation as StandardEquation
				
				val rhs = expr.printStmtExpr
				val lhs = '''«eq.variable.name»(«indexNamesStr»)'''
				var defIndexNamesStr = indexNamesStr
				var op = '='
				val stmtStr = '''«lhs» «op» «rhs»''' 
				
				if (i==2) {										
					val injectionSVals = if (version == Version.ABFT_V1) {
						spatialContext.map['''«key»==«value»*t«key»_INJ+«value/2»'''].join(' && ')
					} else {
						spatialContext.map['''«key»==«value-2*TT»*t«key»_INJ+«value/2»'''].join(' && ')
					}
					
	//				var injExpr = '''if (t==«TT»*(tt_INJ-1)+1 && «injectionSVals») { printf("        Y(«indexNames.map['%d'].join(',')»)\n", «indexNames.join(',')»); printf("before: %E\n", «lhs»); inject_«system.name»(&«lhs»); printf(" after: %E\n", «lhs»); } '''
					val injExpr = '''if (t==«TT»*(tt_INJ-1)+1 && «injectionSVals») inject_«system.name»(&«lhs»)'''
					'''
						#define «name»_hook(«defIndexNamesStr») «stmtStr»
						#ifdef ERROR_INJECTION
						#define «name»(«defIndexNamesStr») do { «name»_hook(«defIndexNamesStr»); «injExpr»; } while(0)
						#else
						#define «name»(«defIndexNamesStr») «name»_hook(«defIndexNamesStr»)
						#endif'''
				} else {
					'''#define «name»(«defIndexNamesStr») «stmtStr»'''
				}
				
				
			]
		}
		
		val c2cbeMacros = if (version == Version.ABFT_V2) {
			val c2CBEs = (stdEqs.findFirst[variable.name == 'C2'].expr as CaseExpression).exprs;
			(0..<c2CBEs.size).map[i |
				val expr = c2CBEs.get(i)
				val indexNames = expr.contextDomain.indexNames
				val indexNamesStr = indexNames.join(',')
				val name = 'S' + 'C2_cb' + i
				val eq = expr.getContainerEquation as StandardEquation
				
				val rhs = expr.printStmtExpr
				val lhs = '''«eq.variable.name»(«indexNamesStr»)'''
				var defIndexNamesStr = indexNamesStr
				var op = '='
				val stmtStr = '''«lhs» «op» «rhs»''' 
				
				'''#define «name»(«defIndexNamesStr») «stmtStr»'''
			]
		} else {
			#[]
		}
		
		val macros = stdEqs.reject[variable == stencilVar].reject[variable.name == 'C2' && version == Version.ABFT_V2].map[
			val indexNames = expr.contextDomain.indexNames
			val indexNamesStr = indexNames.join(',')
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
		]
		
		(macros + c2cbeMacros + stencilVarMacros).sort.join('\n')
	}
	
	def prepareResult() {
		val name = 'I'
		val variable = system.locals.findFirst[v | v.name == name] 
		if (variable === null)
			return '''
				struct Result result = new_result(); 
				result.result = execution_time;
			''';
			
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
		
		val varAcc = '''«name»(«idxStr»)'''
		
		val vStr = switch (version) { 
			case Version.ABFT_V1 : 'v1'
			case Version.ABFT_V2 : 'v2'
		}
		
		val containsInjectionConditions = indexNames.map[n | '''«n»==«n»_INJ'''].join(' && ')
		
		val code = '''
			#if defined «TIMING»
			struct Result result = new_result();
			result.result = execution_time;
			
			#elif defined «ERROR_INJECTION»
			// Count checksum difference above THRESHOLD
			struct INJ inj = { «stencilVar.domain.indexNames.map[i |'''t«i»_INJ'''].join(', ')» };
			struct Result result = new_result();
			result.bit = BIT;
			result.inj = inj;
			result.noise = -1;
			
			const char* verbose = getenv("VERBOSE");
			
			#define print_«stmtPrefix»«name»(«idxStr») printf("«vStr»_«name»(«indexNames.map['%d'].join(',')») = %E\n",«idxStr», fabs(«varAcc»))
			#define acc_noise(«idxStr») result.noise = max(result.noise, fabs(«varAcc»))
			#define «stmtPrefix»«name»(«idxStr») do { if (verbose != NULL && fabs(«varAcc»)>=threshold) print_«stmtPrefix»«name»(«idxStr»); acc_noise(«idxStr»); if («containsInjectionConditions») { if (fabs(«varAcc»)>=threshold) {result.TP++;} else {result.FN++;} } else { if (fabs(«varAcc»)>=threshold) {result.FP++;} else {result.TN++;} } } while(0)
«««			//#define «stmtPrefix»«name»(«idxStr») do { print_«stmtPrefix»«name»(«idxStr»); acc_noise(«idxStr»); if («containsInjectionConditions») { if (fabs(«varAcc»)>=threshold) {result.TP++;} else {result.FN++;} } else { if (fabs(«varAcc»)>=threshold) {result.FP++;} else {result.TN++;} } } while(0)
			
			«dataType.print» threshold = 0;
			const char* env_threshold = getenv("THRESHOLD");
			if (env_threshold != NULL) {
				threshold = atof(env_threshold);
			}
			
			«codegenVisitor.toCode»

			{
				long N = result.FP + result.TN;
				long P = result.FN + result.TP;
				if (P != 0 && N != 0) {
					result.TPR = 100 * ((float)result.TP) / P;
					result.FPR = 100 * ((float)result.FP) / N;
					result.FNR = 100 * ((float)result.FN) / P;
				}
			}
			#undef «stmtPrefix»«name»
			
			#endif
		'''
		code
	}
	
	
	////////////////////////////////////////////////////
	// HARD CODING experimental set up        (start) //
	////////////////////////////////////////////////////
	
	def checkCoordinateMacro(ISLSet container) {
		val names = stencilVar.domain.indexNames
		val txs = names.map[n | 't' + n]
		val xInjs = names.map[n | n + '_INJ']
		
		val txsStr = txs.join(',')
		
		'''#define containsInjectionCoordinate(«txsStr») 0'''
		
	}
	
	/* This operation becomes very expensive as the dimensionality and size of TT increase */
	def checkCoordinateMacro_old(ISLSet container) {
		val names = stencilVar.domain.indexNames
		val txs = names.map[n | 't' + n]
		val xInjs = names.map[n | n + '_INJ']
		
		val txsStr = txs.join(',')
		val xInjsStr = xInjs.join(',')
		val paramStr = container.paramNames.join(',')
		
		var coords = container.copy.intersect('''[«paramStr»,«txsStr»,«xInjsStr»]->{[«txsStr»,«xInjsStr»]}'''.toString.toISLSet)
			.removeRedundancies
			.coalesce
			.apply('''[«paramStr»,«txsStr»,«xInjsStr»]->{[«txsStr»,«xInjsStr»]->[«txsStr»]}'''.toString.toISLMap)
			.projectOut(ISLDimType.isl_dim_param, 0, container.dim(ISLDimType.isl_dim_param))
		coords = AlphaUtil.renameIndices(coords, txs.toList)
		val expr = BarvinokBindings.card(coords.copy).convert
		
		'''#define containsInjectionCoordinate(«txsStr») «expr.printExpr»'''
		
	}
	
	////////////////////////////////////////////////////
	// HARD CODING experimental set up          (end) //
	////////////////////////////////////////////////////
	
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
	
	def sigantureParamsAsFloats(AlphaSystem system) {
		system?.sigantureParamsAsFloats(version)
	}
	def sigantureParamsAsFloats(AlphaSystem system, Version _version) {
		val paramArgs = system.parameterDomain.paramNames
				.map[p | 'double ' + p]
				.join(', ')
		val ioArgs = (system.inputs + system.outputs)
				.map[v | 'float ' + (0..<v.indirectionLevel).map['*'].join + v.name]
				.join(', ')
			
			'''void «system.name»(«paramArgs», «ioArgs»)'''
	}
	def signature(AlphaSystem system) {
		system?.signature(version)
	}
	def signature(AlphaSystem system, Version _version) {
		val paramArgs = system.parameterDomain.paramNames
			.map[p | 'long ' + p]
			.join(', ')
		val ioArgs = (system.inputs + system.outputs)
			.map[v | 'float ' + (0..<v.indirectionLevel).map['*'].join + v.name]
			.join(', ')
		val returnType = 'struct Result'
		
		'''«returnType» «system.name»(«paramArgs», «ioArgs»)'''
	}
	
	def memoryMacro(Variable variable) {
		variable.memoryMacro('')
	}
	def memoryMacro(Variable variable, String suffix) {
		val stmtName = variable.name + suffix
		val mappedName = 'mem_' + memoryMap.getName(variable.name) + suffix
		
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
		
		val indexNames = range.indexNames;
		
		val paramStr = range.paramNames.join(',')
		val idxStr = indexNames.join(',')
		
		val offsets = indexNames.map[i | 
			val m = '''[«paramStr»]->{[«idxStr»]->[«i»]}'''.toString.toISLMap
			range.copy.apply(m)]
			.map[coalesce]
			.map[lexMinAsPWMultiAff]
			.map[pwmaff | 
				if (pwmaff.nbPieces > 1)
					throw new Exception('Error computing lexMin for memory allocation (pwmaff)')
				pwmaff
			]
			.map[getPiece(0).maff]
			.map[maff | 
				if (maff.dim(ISLDimType.isl_dim_out) > 1)
					throw new Exception('Error computing lexMin for memory allocation (maff)')
				maff
			]
			.map[getAff(0)]
			.map[convertAff(true)]
			.map[printExpr]
			.toArrayList
		
		val indexExprs = indexNames.zipWith(offsets).map[
			val idx = key
			val offset = value
			if (offset != '(0)')
				'''(«idx»)-«offset»'''
			else
				'''«idx»'''
		].toArrayList
		
		val macroReplacement = Factory.arrayAccessExpr(name, indexExprs)
		val macroStmt = Factory.macroStmt(stmtName, range.indexNames, macroReplacement)
		return '''«macroStmt.printStmt»'''
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