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
import alpha.model.SystemBody
import alpha.model.Variable
import alpha.model.transformation.StandardizeNames
import alpha.model.util.AShow
import alpha.model.util.AlphaUtil
import fr.irisa.cairn.jnimap.isl.ISLASTBuild
import fr.irisa.cairn.jnimap.isl.ISLSchedule
import fr.irisa.cairn.jnimap.isl.ISLSet
import fr.irisa.cairn.jnimap.isl.ISLUnionSet
import fr.irisa.cairn.jnimap.isl.JNIPtrBoolean
import org.eclipse.xtend.lib.annotations.Accessors

import static extension alpha.abft.ABFT.buildParamStr
import static extension alpha.abft.ABFT.identify_convolution
import static extension alpha.codegen.ProgramPrinter.print
import static extension alpha.codegen.ProgramPrinter.printExpr
import static extension alpha.codegen.ProgramPrinter.printStmt
import static extension alpha.codegen.demandDriven.WriteC.getCardinalityExpr
import static extension alpha.codegen.isl.AffineConverter.convertAff
import static extension alpha.codegen.isl.AffineConverter.convertMultiAff
import static extension alpha.model.util.AlphaUtil.getContainerEquation
import static extension alpha.model.util.CommonExtensions.permutations
import static extension alpha.model.util.CommonExtensions.toArrayList
import static extension alpha.model.util.CommonExtensions.zipWith
import static extension alpha.model.util.ISLUtil.toEmptyUnionSet
import static extension alpha.model.util.ISLUtil.toISLIdentifierList
import static extension alpha.model.util.ISLUtil.toISLMap
import static extension alpha.model.util.ISLUtil.toISLSchedule
import static extension alpha.model.util.ISLUtil.toISLSet
import static extension alpha.model.util.ISLUtil.toISLUnionMap
import static extension alpha.model.util.ISLUtil.toISLUnionSet
import static extension alpha.codegen.isl.PolynomialConverter.convert
import static extension fr.irisa.cairn.jnimap.isl.ISLMap.buildIdentity
import fr.irisa.cairn.jnimap.isl.ISLDimType
import fr.irisa.cairn.jnimap.barvinok.BarvinokBindings
import alpha.codegen.alphaBase.TypeGeneratorBase

enum Version {
		BASELINE,
		ABFT_V1,
		ABFT_V2,
		WRAPPER
	}

class SystemCodeGen {
	
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
		
		this.scheduleDomain = systemBody.standardEquations
		.map[variable.name -> variable.getStmtDomain(expr)]
		.map[value.setTupleName(key).copy.toUnionSet]
		.fold(systemBody.parameterDomain.space.toEmptyUnionSet, [ret, d | ret.union(d)])
		this.scheduleStr = schedule.injectIndices(scheduleDomain, stmtPrefix)
		
		this.stencilVar = system.outputs.get(0)
		this.version = version
		this.tileSizes = tileSizes
		
//		println(scheduleStr)
		
		this.schedule = scheduleStr.toISLSchedule
		
		StandardizeNames.apply(system)
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
			
			void initialize_timer();
			void reset_timer();
			void start_timer();
			void stop_timer();
			double elapsed_time();
			
			struct INJ {
				int t;
				int i;
				int j;
				int k;
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
			};
			
			// Memory mapped targets
			«memoryMap.uniqueTargets.map[memoryTargetMacro].join('\n')»
			
			// Memory access functions
			«system.variables.map[memoryMacro].join('\n')»
			
			«IF version == Version.ABFT_V1 || version == Version.ABFT_V2»
			#ifdef ERROR_INJECTION
			// Error injection harness
			«stencilVar.domain.indexNames.map[i | 
				'''int «i»_INJ'''].join(';\n')
			»;
			int BIT;
			
			void inject_«system.name»(float *val) {
				int *bits;
				bits = (int*)val;
				*bits ^= 1 << BIT;
			}
			#endif
			
			«ENDIF»
			«system.signature»
			{
				«IF version == Version.ABFT_V1 || version == Version.ABFT_V2»
				#ifdef ERROR_INJECTION
				// Error injection configuration
«««			  «stencilVar.domain.indexNames.map[i |
«««  		  	  '''if (getenv("«i»_INJ") == NULL) printf("«i»_INJ is not set\n")'''].join(';\n')
«««  		  	  »;
«««			  if (getenv("BIT")==NULL) printf("BIT is not set\n");
				«stencilVar.domain.indexNames.map[i |
		  	  '''«i»_INJ = getenv("«i»_INJ") != NULL ? atoi(getenv("«i»_INJ")) : (int)(rand() % «if (i == 't') 'T' else 'N'»)'''].join(';\n')
		  	  	»;
				BIT = getenv("BIT") != NULL ? atoi(getenv("BIT")) : (int)(rand() % «if (dataType == BaseDataType.FLOAT) 32 else 64»);
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

				#ifdef ERROR_INJECTION
				struct INJ inj = { «stencilVar.domain.indexNames.map[i |'''«i»_INJ'''].join(', ')» };
				struct Result result = { 0, 0, 0, 0, 0, -1.0, -1.0, -1.0, BIT, inj };
				«countErrors»
				#else
				struct Result result;
				printf("«system.name»: %lf sec\n", execution_time);
				#endif
			
				«localMemoryFree»
				«IF version != Version.BASELINE»
				return result;
				«ENDIF»
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
			 *
			 */
		'''
		code
	}
	
	def localMemoryFree() {
		system.locals.memoryFree
	}
	
	def memoryFree(Variable[] variables) {
		variables.getMemoryChunks.map[key].map[chunkName | 
			'''free(«chunkName»);'''
		].join('\n')
	}
	
	def localMemoryAllocation() {
		system.locals.memoryAllocation
	}
	
	def memoryAllocation(Variable[] variables) {
		/*
		 * construct the domain (union of all variable domains) for each
		 * mapped name
		 */
		val mallocStmts = variables.getMemoryChunks
			.map[value.map[memoryMap.getRange(name).copy].reduce[v1,v2 | v1.union(v2)].coalesce -> key]
			.map[mallocStmt(key, value)]
		
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
		
		val macros = system.systemBodies.get(0).standardEquations.map[
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
			/*
			 * Add injection code for stencil variable stmt
			 */
			if (variable == stencilVar) {
				val injExpr = '''if («indexNames.map[i | '''(«i»==«i»_INJ)'''].join(' && ')») inject_«system.name»(&«lhs»)'''
				'''
					#define «name»_hook(«defIndexNamesStr») «stmtStr»
					#ifdef ERROR_INJECTION
					#define «name»(«defIndexNamesStr») do { «name»_hook(«defIndexNamesStr»); «injExpr»; } while(0)
					#else
					#define «name»(«defIndexNamesStr») «name»_hook(«defIndexNamesStr»)
					#endif
				'''
			} else {
				'''#define «name»(«defIndexNamesStr») «stmtStr»'''
			}
		].sort
		
		macros.join('\n')
	}
	
	def countErrors() {
		val name = 'I'
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
		
		val varAcc = '''«name»(«idxStr»)'''
		
		val vStr = switch (version) { 
			case Version.ABFT_V1 : 'v1'
			case Version.ABFT_V2 : 'v2'
		}
		
		val yVarIndexNames = stencilVar.domain.indexNames
		
		val code = '''
			// Count checksum difference above THRESHOLD
			
			// Returns 1 if signal was raised at (tt,ti,...) else 0
			«checkCoordinateMacro(buildChecksumContainer)»
			
			const char* verbose = getenv("VERBOSE");
			
			#define print_«stmtPrefix»«name»(«idxStr») printf("«vStr»_«name»(«indexNames.map['%d'].join(',')») = %E\n",«idxStr», «varAcc»)
			#define «stmtPrefix»«name»(«idxStr») do { if (verbose != NULL && fabs(«varAcc»)>=threshold) print_«stmtPrefix»«name»(«idxStr»); if (containsInjectionCoordinate(«idxStr») > 0) { if (fabs(«varAcc»)>=threshold) {result.TP++;} else {result.FN++;} } else { if (fabs(«varAcc»)>=threshold) {result.FP++;} else {result.TN++;} } } while(0)
			
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
					result.valid = 1;
				}
			}
«««			if (verbose != NULL) {
«««				printf("«vStr»,%d,«yVarIndexNames.map['%d'].join(',')»,%ld,%ld,%ld,%ld\n", BIT, «yVarIndexNames.map[i | i + '_INJ'].join(', ')», result.TP, result.FP, result.TN, result.FN);
«««			}
			#undef «stmtPrefix»«name»
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
	
	/**
	 * This function is used to identify the list of checksum pairs that surround the injection site
	 * Used to identify whether or not a raised signal is a TP/FP/TN/FN
	 */
	def buildChecksumContainer() {
		if (version != Version.ABFT_V1 && version != Version.ABFT_V2)
			return null;
		val cVar = system.locals.findFirst[name == 'C1']
		
		val nbSpatialDims = cVar.domain.indexNames.size - 1
		
		val face = if (version == Version.ABFT_V1) {
			(systemBody.standardEquations.findFirst[variable == cVar].expr as ReduceExpression).body.contextDomain	
		} else {
			val c2nrs = systemBody.standardEquations.filter[variable.name.startsWith('C2_')]
			(c2nrs.get(c2nrs.size - 1).expr as ReduceExpression).body.contextDomain.copy
				.projectOut(ISLDimType.isl_dim_out, 1+nbSpatialDims, nbSpatialDims + 1)
		}
		
		val convolutionKernel = system.identify_convolution
		val radius = convolutionKernel.key
		val kernel = convolutionKernel.value
		
		val spatialDims = stencilVar.domain.indexNames.size - 1
		
		switch(version) {
			case Version.ABFT_V1 : face.buildV1Container(cVar.buildParamStr, radius, spatialDims)
			case Version.ABFT_V2 : face.buildV2Container(cVar.buildParamStr, radius, spatialDims)
		}
		
	}
	
	def ISLSet buildV1Container(ISLSet face, String paramStr, int radius, int spatialDims) {
		val xs = (0..<spatialDims).map[i | #['i','j','k'].get(i)].toList
		val txs = xs.map[x | 't' + x].toList
		
		val xsStr = xs.join(',')
		val txsStr = '''tt,«txs.join(',')»'''
		val inStr = '''«txsStr»,t,«xsStr»'''
		
		val TT = tileSizes.get(0)
		
		val maps = #[1,-1].permutations(spatialDims).map[ijks | 
			'''c[«inStr»]->c[«txsStr»,t-1,«xs.zipWith(ijks).map[
				val x = key
				val coeff = value
				if (coeff > 0)
					'''«x»+«radius»'''
				else
					'''«x»-«radius»'''
			].join(',')»]'''
		]
		
		val isExact = new JNIPtrBoolean
		val closureStr = '''
			«paramStr»->{
				[«txsStr»,«xsStr»]->c[«txsStr»,«TT»tt,«xsStr»];
				c[«inStr»]->c[«inStr»];
				«maps.join(';\n')»;
			}
		'''
		val closure = closureStr.toISLUnionMap.transitiveClosure(isExact)
		
		if (!isExact.value)
			throw new Exception('Transitive closure should be exact, something went wrong')
		 
		val range = '''«paramStr»->{ c[«inStr»] : «TT»tt-«TT»<=t<=«TT»tt}'''.toISLUnionSet
		val map = closure.copy.intersectRange(range)
		
		val ret = face.copy.toUnionSet.apply(map.copy)
		if (ret.sets.size > 1)
			throw new Exception('Issue applying transitive closure')
		
		val names = (#['tt'] + txs + #['t'] + xs).toList
		val container = AlphaUtil.renameIndices(ret.getSetAt(0).resetTupleID.coalesce, names)
		
		container
	}
	
	def ISLSet buildV2Container(ISLSet face, String paramStr, int radius, int spatialDims) {
		val xs = (0..<spatialDims).map[i | #['i','j','k'].get(i)].toList
		val txs = xs.map[x | 't' + x].toList
		
		val xsStr = xs.join(',')
		val txsStr = '''tt,«txs.join(',')»'''
		val inStr = '''«txsStr»,t,«xsStr»'''
		
		val TT = tileSizes.get(0)
		
		val maps = #[1,-1].permutations(spatialDims).map[ijks | 
			'''c[«inStr»]->c[«txsStr»,t+1,«xs.zipWith(ijks).map[
				val x = key
				val coeff = value
				if (coeff > 0)
					'''«x»+«radius»'''
				else
					'''«x»-«radius»'''
			].join(',')»]'''
		]
		
		val isExact = new JNIPtrBoolean
		val closureStr = '''
			«paramStr»->{
				[«txsStr»,«xsStr»]->c[«txsStr»,«TT»tt-«TT»,«xsStr»];
				c[«inStr»]->c[«inStr»];
				«maps.join(';\n')»;
			}
		'''
		val closure = closureStr.toISLUnionMap.transitiveClosure(isExact)
		
		if (!isExact.value)
			throw new Exception('Transitive closure should be exact, something went wrong')
		 
		val range = '''«paramStr»->{ c[«inStr»] : «TT»tt-«TT»<=t<=«TT»tt}'''.toISLUnionSet
		val map = closure.copy.intersectRange(range)
		
		val ret = face.copy.toUnionSet.apply(map.copy)
		if (ret.sets.size > 1)
			throw new Exception('Issue applying transitive closure')
		
		val names = (#['tt'] + txs + #['t'] + xs).toList
		val container = AlphaUtil.renameIndices(ret.getSetAt(0).resetTupleID.coalesce, names)
		
		container
	}
	
	////////////////////////////////////////////////////
	// HARD CODING experimental set up          (end) //
	////////////////////////////////////////////////////
	
	
	
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
		
		val varAcc = '''«name»(«idxStr»)'''
		
		val code = '''
			// Print «name» values
			
			#define «stmtPrefix»«name»(«idxStr») if (fabs(«varAcc»)>=threshold) printf("«system.name».«name»(«indexNames.map['%d'].join(',')») = %E\n",«idxStr», «varAcc»)
			
			«dataType.print» threshold = 0;
			const char* env_threshold = getenv("THRESHOLD");
			if (env_threshold != NULL) {
				threshold = atof(env_threshold);
			}
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
	
	def signature(AlphaSystem system) {
		signature(system, version)
	}
	def signature(AlphaSystem system, Version _version) {
		val paramArgs = system.parameterDomain.paramNames
			.map[p | 'long ' + p]
			.join(', ')
		val ioArgs = (system.inputs + system.outputs)
			.map[v | 'float ' + (0..<v.indirectionLevel).map['*'].join + v.name]
			.join(', ')
		val returnType = switch(_version) {
			case Version.BASELINE : 'void'
			default : 'struct Result'
		}
		'''«returnType» «system.name»(«paramArgs», «ioArgs»)'''
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