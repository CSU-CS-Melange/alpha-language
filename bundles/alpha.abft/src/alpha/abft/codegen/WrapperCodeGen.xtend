package alpha.abft.codegen

import alpha.abft.codegen.util.ISLASTNodeVisitor
import alpha.abft.codegen.util.MemoryMap
import alpha.codegen.Factory
import alpha.codegen.isl.ConditionalConverter
import alpha.model.AlphaSystem
import alpha.model.Variable
import fr.irisa.cairn.jnimap.isl.ISLASTBuild
import fr.irisa.cairn.jnimap.isl.ISLDimType
import fr.irisa.cairn.jnimap.isl.ISLSet

import static extension alpha.abft.ABFT.buildParamStr
import static extension alpha.codegen.ProgramPrinter.print
import static extension alpha.codegen.ProgramPrinter.printExpr
import static extension alpha.model.util.AlphaUtil.copyAE
import static extension alpha.model.util.CommonExtensions.zipWith
import static extension alpha.model.util.ISLUtil.toISLIdentifierList
import static extension alpha.model.util.ISLUtil.toISLSchedule
import static extension alpha.model.util.ISLUtil.toISLSet

class WrapperCodeGen extends SystemCodeGen {
	
	static String goldSuffix = '_GOLD'
	
	val AlphaSystem v1System
	val AlphaSystem v2System
	val AlphaSystem v3System
	val int[] v2TileSizes
	
	def static generateWrapper(AlphaSystem baselineSystem, AlphaSystem v1System, AlphaSystem v2System, AlphaSystem v3System, MemoryMap memoryMap, Version version, int[] v1TileSizes, int[] v2TileSizes) {
		val generator = new WrapperCodeGen(baselineSystem, v1System, v2System, v3System, memoryMap, version, v1TileSizes, v2TileSizes)
		generator.generate
	}
	
	new(AlphaSystem system, AlphaSystem v1System, AlphaSystem v2System, AlphaSystem v3System, MemoryMap memoryMap, Version version, int[] v1TileSizes, int[] v2TileSizes) {
		super(system, memoryMap, version, v1TileSizes)
		this.v1System = v1System
		this.v2System = v2System
		this.v3System = v3System
		this.v2TileSizes = v2TileSizes
	}
	
	def notV3() {
		version != Version.ABFT_V3
	}
	
	override generate() '''
		// «system.name»-wrapper.c
		
		#include<stdio.h>
		#include<stdlib.h>
		#include<math.h>
		#include<string.h>
		#include<time.h>
		
		#define max(x, y)   ((x)>(y) ? (x) : (y))
		#define min(x, y)   ((x)>(y) ? (y) : (x))
		#define ceild(n,d)  (int)ceil(((double)(n))/((double)(d)))
		#define floord(n,d) (int)floor(((double)(n))/((double)(d)))
		#define mallocCheck(v,s,d) if ((v) == NULL) { printf("Failed to allocate memory for %s : size=%lu\n", "sizeof(d)*(s)", sizeof(d)*(s)); exit(-1); }
		#define new_result() { .valid=0, .TP=0L, .FP=0L, .TN=0L, .FN=0L, .TPR=0.0f, .FPR=0.0f, .FNR=0.0f, .bit=0, .inj={.«if (notV3) 't'»t=0, .«if (notV3) 't'»i=0, .«if (notV3) 't'»j=0, .«if (notV3) 't'»k=0}, .result=0.0f, .noise=0.0f}
		#define new_result_summary() { .TP=0L, .FP=0L, .TN=0L, .FN=0L, .TPR=0.0f, .FPR=0.0f, .FNR=0.0f, .bit=0, .nb_detected=0L, .nb_results=0L, .result=0.0f, .noise=0.0f, .max_error=0.0f}

		// External system declarations
		«system.signature(Version.BASELINE)»;
		#if defined «REPORT_COMPLEXITY_ONLY»
		«v1System.sigantureParamsAsFloats»;
		«v2System.sigantureParamsAsFloats»;
		«v3System.sigantureParamsAsFloats»;
		#else
		«v1System.signature»;
		«v2System.signature»;
		«v3System.signature»;
		#endif
		
		struct INJ {
			int «if (notV3) 't'»t;
			int «if (notV3) 't'»i;
			int «if (notV3) 't'»j;
			int «if (notV3) 't'»k;
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
		
		struct ResultsSummary {
			float TP;
			float FP;
			float TN;
			float FN;
			float TPR;
			float FPR;
			float FNR;
			int bit;
			long nb_detected;
			long nb_results;
			double result;
			double noise;
			double max_error;
		};

		// Memory mapped targets
		«system.variables.reject[isLocal].map[memoryMap.getName(name) -> domain].map[memoryTargetMacro].join('\n')»
		#if defined «ERROR_INJECTION»
		«system.outputs.map[memoryMap.getName(name) + goldSuffix -> domain].map[memoryTargetMacro].join('\n')»
		#endif
		
		// Memory access functions
		«system.variables.reject[isLocal].map[memoryMacro].join('\n')»
		#if defined «ERROR_INJECTION»
		«system.outputs.map[memoryMacro(goldSuffix)].join('\n')»
		#endif
		
		«system.mainFunction»
	'''
	
	
	
	
	def mainFunction(AlphaSystem system) {
		
		val paramNames = system.parameterDomain.paramNames
		val paramInits = paramNames.map[P |
			val i = paramNames.indexOf(P)
			'''long «P» = atol(argv[«i+1»])'''
		]
		val paramAsFloatsInits = paramNames.map[P |
			val i = paramNames.indexOf(P)
			'''float «P» = atof(argv[«i+1»])'''
		]
		
		val indexNames = stencilVar.domain.indexNames
		val sDims = indexNames.size
		val indexNameStr = (1..<sDims).map['%*d'].join(',')
		val injStr = (1..<sDims).map[i | 'sBox, r.inj.t' + stencilVar.domain.indexNames.get(i)].join(', ')
		
		val indexNameHeaderStr = (1..<sDims).map['%*s'].join(',')
		val injHeaderStr = (1..<sDims).map[i | '''sBox, "inj.t«stencilVar.domain.indexNames.get(i)»"'''].join(', ')
		val injSummaryStr = (1..<sDims).map[i | '''sBox, "-"'''].join(', ')
		
		val TT = tileSizes.get(0)
		val TXs = (1..<tileSizes.size).map[i | tileSizes.get(i)]
		
		// random number in [2,T/<TT>]
		val tInjectionSite = #['''(rand() % ((T/«TT»)-2) + 2)''']
		// random number in [1,N/<TX>]
		val sInjectionSite = TXs.map[TX | '''(rand() % ((N/«TX»)-2) + 1)''']
		val injectionSite = tInjectionSite + sInjectionSite
		
		val thresholdVarV1 = 'threshold_v1'
		val thresholdVarV2 = 'threshold_v2'
		val thresholdVarV3 = 'threshold_v3'
		
		val code = '''
		#ifdef «ERROR_INJECTION»
		static int tBox;
		static int sBox;
		static int rBox;
		static int runBox;
		static char eol[2];
		static int run;
		static int R;
		static int log_flag;

		void printHeader() {
			int S = 300;
			char header_str[S];
			sprintf(header_str, "   %*s : (%*s,«indexNameHeaderStr») : (%*s,%*s,%*s,%*s) : %*s, %*s, %*s, %*s", 4, "bit", tBox, "inj.tt", «injHeaderStr», rBox, "TP", rBox, "FP", rBox, "TN", rBox, "FN", 12, "Detected (%)", 7, "FPR (%)", 14, "Max rel. error", 2 * runBox + 1, "Runs");
			char header_bar[S]; for (int i=0; i<S; i++) header_bar[i] = '-';
			fprintf(stdout, "%.*s\n", (int)strlen(header_str), header_bar);
			fprintf(stdout, "%s\n", header_str);
			fprintf(stdout, "%.*s\n", (int)strlen(header_str), header_bar);
		}
		
		void print(int version, struct Result r, double max_error) {
			if (!log_flag) {
				return;
			}
			int detected = r.TP > 0 ? 1 : 0;
		    printf("v%d,%*d : (%*d,«indexNameStr») : (%*ld,%*ld,%*ld,%*ld) : %*d, %*.2f, %*E, %*d/%d%s", version, 4, r.bit, tBox, r.inj.tt, «injStr», rBox, r.TP, rBox, r.FP, rBox, r.TN, rBox, r.FN, 12, detected, 7, r.FPR, 14, max_error, runBox, run, R, eol);
		    fflush(stdout);
		}
		
		void print_summary(int version, struct ResultsSummary *s) {
			s->TP = s->TP / s->nb_results;
			s->FP = s->FP / s->nb_results;
			s->TN = s->TN / s->nb_results;
			s->FN = s->FN / s->nb_results;
			s->TPR = s->TPR / s->nb_results;
			s->FPR = s->FPR / s->nb_results;
			s->FNR = s->FNR / s->nb_results;
			float detected_rate =100 * s->nb_detected / s->nb_results;
			
			printf("v%d,%*d : (%*s,«indexNameHeaderStr») : (%*.2f,%*.2f,%*.2f,%*.2f) : %*.2f, %*.2f, %*E, %*d/%d\n", version, 4, s->bit, tBox, "-", «injSummaryStr», rBox, s->TP, rBox, s->FP, rBox, s->TN, rBox, s->FN, 12, detected_rate, 7, s->FPR, 14, s->max_error, runBox, run, R);
		}
		
		void accumulate_result(struct ResultsSummary *acc, struct Result r) {
			acc->TP += r.TP;
			acc->FP += r.FP;
			acc->TN += r.TN;
			acc->FN += r.FN;
			acc->TPR += r.TPR;
			acc->FPR += r.FPR;
			acc->FNR += r.FNR;
			acc->bit = r.bit;
			if (r.TP > 0) {
				acc->nb_detected++;
			}
			acc->nb_results++;
		}
		#endif
		
		int main(int argc, char **argv) 
		{
			if (argc < «paramNames.size + 1») {
				printf("usage: %s «paramNames.join(' ')»\n", argv[0]);
				return 1;
			}
			
			// Parse parameter sizes
			#if defined «REPORT_COMPLEXITY_ONLY»
			«paramAsFloatsInits.join(';\n')»;
			#else
			«paramInits.join(';\n')»;
			#endif
			
			// Check parameter values
			«checkParamValues»
			
			#if defined «REPORT_COMPLEXITY_ONLY»
			«variableDeclarations»
			
			#else
			«localMemoryAllocation»
			
			srand(time(NULL));
			
			«system.inputs.map[inputInitialization].join('\n')»
			#endif
			
			#if defined «TIMING»
			
			struct Result r0 = «system.call»;
			printf("v0 time: %0.4f sec\n", r0.result);
			«IF v1System !==null»
			struct Result r1 = «v1System.call»;
			printf("v1 time: %0.4f sec\n", r1.result);
			«ENDIF»
			«IF v2System !==null»
			struct Result r2 = «v2System.call»;
			printf("v2 time: %0.4f sec\n", r2.result);
			«ENDIF»
			«IF v3System !==null»
			struct Result r3 = «v3System.call»;
			printf("v3 time: %0.4f sec\n", r3.result);
			«ENDIF»
			
			#elif defined «REPORT_COMPLEXITY_ONLY»
			«IF v1System !==null»
			«v1System.call»;
			«ENDIF»
			«IF v2System !==null»
			«v2System.call»;
			«ENDIF»
			«IF v3System !==null»
			«v3System.call»;
			«ENDIF»
			
			#elif defined «ERROR_INJECTION»
			tBox = max((int)log10(T) + 1, 7);
			sBox = max((int)log10(N) + 1, 7);
			«IF v3System !==null»
			rBox = (int)log10(2*(T/(float)«tileSizes.get(0)»)*(N/(float)«tileSizes.get(1)»)*«(2..<sDims).map['N'].join('*')») + 4;
			«ELSE»
			rBox = (int)log10(2*(T/(float)«tileSizes.get(0)»)*«(1..<sDims).map[i | '''(N/(float)«tileSizes.get(1)»)'''].join('*')») + 4;
			«ENDIF»
			if (getenv("VERBOSE") != NULL) {
				strcpy(eol, "\n");
			} else {
				strcpy(eol, "\r");
			}
			
			log_flag = (getenv("LOG") == NULL) ? 1 : 0;
			
«««			#define export_injs2() do { «stencilVar.domain.indexNames.map[i |'''{ int ival = (int)(rand() % («if (i == 't') '''T-(2*«TT»)''' else '''N-(2*«TS»)'''») + «if (i == 't') TT else TS»); sprintf(val, "%d", ival); «i»_INJ = ival; setenv("«i»_INJ", val, 1); }'''].join(' ')»; } while(0)
			#define export_injs() do { «indexNames.zipWith(injectionSite).map['''{ int ival = «value»; sprintf(val, "%d", ival); setenv("t«key»_INJ", val, 1); }'''].join(' ')» } while(0)
			
			R = (getenv("NUM_RUNS") != NULL) ? atoi(getenv("NUM_RUNS")) : 100;
			runBox = (int)log10(R) + 1;

			// if THRESHOLD not explicitly set, do short profiling to estimate the
			// noise due to floating point round off errors
			const char* threshold = getenv("THRESHOLD");
			«IF v1System !==null»
			float «thresholdVarV1»;
			if (threshold == NULL) {
				struct Result result;
				long input_T = T;
				T = «#[20, 4*TT].max»;
				
				result = «v1System.call»;
				printf("floating point noise: %E\n", result.noise);
				float thresholds[10] = { 1e-1, 1e-2, 1e-3, 1e-4, 1e-5, 1e-6, 1e-7, 1e-8, 1e-9, 1e-10, 0.0 };
				for (int i=9; i>=0; i--) {
					«thresholdVarV1» = thresholds[i];
					if («thresholdVarV1» >= fabs(result.noise))
						break;
				}
				printf(" «thresholdVarV1» set to: %E\n", «thresholdVarV1»);
				T = input_T;
			} else {
				«thresholdVarV1» = atoi(getenv("THRESHOLD"));
			}
			«ENDIF»
			«IF v2System !==null»
			float «thresholdVarV2»;
			if (threshold == NULL) {
				struct Result result;
				long input_T = T;
				T = «#[20, 4*TT].max»;
				
				result = «v2System.call»;
				printf("floating point noise: %E\n", result.noise);
				float thresholds[10] = { 1e-2, 1e-3, 1e-4, 1e-5, 1e-6, 1e-7, 1e-8, 1e-9, 1e-10, 0.0 };
				for (int i=9; i>=0; i--) {
					«thresholdVarV2» = thresholds[i];
					if («thresholdVarV2» >= fabs(result.noise))
						break;
				}
				printf(" «thresholdVarV2» set to: %E\n", «thresholdVarV2»);
				T = input_T;
			} else {
				«thresholdVarV2» = atoi(getenv("THRESHOLD"));
			}
			«ENDIF»
			«IF v3System !==null»
			float «thresholdVarV3»;
			if (threshold == NULL) {
				struct Result result;
				long input_T = T;
				T = «#[20, 4*TT].max»;
				
				result = «v3System.call»;
				printf("floating point noise: %E\n", result.noise);
				float thresholds[10] = { 1e-2, 1e-3, 1e-4, 1e-5, 1e-6, 1e-7, 1e-8, 1e-9, 1e-10, 0.0 };
				for (int i=9; i>=0; i--) {
					«thresholdVarV3» = thresholds[i];
					if («thresholdVarV3» >= fabs(result.noise))
						break;
				}
				printf(" «thresholdVarV3» set to: %E\n", «thresholdVarV3»);
				T = input_T;
			} else {
				«thresholdVarV3» = atoi(getenv("THRESHOLD"));
			}
			«ENDIF»
			printHeader();
			
			«stencilVar.domain.indexNames.map[i |'''int «i»_INJ'''].join('; \n')»;
			
			// Get GOLD result, run the input program
			«system.call(goldSuffix)»;
			
			const char* verbose = getenv("VERBOSE");
			const char* single_bit = getenv("BIT");
			char val[50];
			«IF v1System !==null»
			// ABFTv1
			sprintf(val, "%E", «thresholdVarV1»); 
			setenv("THRESHOLD", val, 1);
			for (int bit=31; bit>=8; bit--) {
				if (single_bit != NULL && atoi(single_bit) != bit)
					continue;
				char val[50];
				sprintf(val, "%d", bit); 
				setenv("bit", val, 1);
				
				struct ResultsSummary v_avg = new_result_summary();
				
				for (run=0; run<R; run++) {
«««					«system.inputs.map[inputInitialization].join('\n')»
					export_injs();
					struct Result v = «v1System.call»;
					
					// Compare output with GOLD
					«compareWithGold(thresholdVarV1)»
					v_avg.max_error = max(v_avg.max_error, max_error);
					
					accumulate_result(&v_avg, v);
					print(1, v, max_error);
				}
«««				if (getenv("SUMMARY") != NULL)
				print_summary(1, &v_avg);
			}
			«ENDIF»
			«IF v2System !==null»
			«IF v1System !==null»
			printHeader();
			«ENDIF»
			// ABFTv2
			sprintf(val, "%E", «thresholdVarV2»); 
			setenv("THRESHOLD", val, 1);
			for (int bit=31; bit>=8; bit--) {
				if (single_bit != NULL && atoi(single_bit) != bit)
					continue;
				char val[50];
				sprintf(val, "%d", bit); 
				setenv("bit", val, 1);
				struct ResultsSummary v_avg = new_result_summary();
				for (run=0; run<R; run++) {
«««					«system.inputs.map[inputInitialization].join('\n')»
					export_injs();
					struct Result v = «v2System.call»;
					
					// Compare output with GOLD
					«compareWithGold(thresholdVarV2)»
					v_avg.max_error = max(v_avg.max_error, max_error);
					
					accumulate_result(&v_avg, v);
					print(2, v, max_error);
				}
«««				if (getenv("SUMMARY") != NULL)
				print_summary(2, &v_avg);
			}
			«ENDIF»

			«IF v3System !== null»
			«IF v1System !== null && v2System !== null»
			printHeader();
			«ENDIF»
			// ABFTv3
			sprintf(val, "%E", «thresholdVarV3»); 
			setenv("THRESHOLD", val, 1);
			for (int bit=31; bit>=8; bit--) {
				if (single_bit != NULL && atoi(single_bit) != bit)
					continue;
				char val[50];
				sprintf(val, "%d", bit); 
				setenv("bit", val, 1);
				struct ResultsSummary v_avg = new_result_summary();
				for (run=0; run<R; run++) {
«««					«system.inputs.map[inputInitialization].join('\n')»
					export_injs();
					struct Result v = «v3System.call»;
					
					// Compare output with GOLD
					«compareWithGold(thresholdVarV3)»
					v_avg.max_error = max(v_avg.max_error, max_error);
					
					accumulate_result(&v_avg, v);
					print(3, v, max_error);
				}
«««				if (getenv("SUMMARY") != NULL)
				print_summary(3, &v_avg);
			}
			«ENDIF»

			#endif
		
			return 0;
		}
		'''
		code
	}
	
	def compareWithGold(String thresholdVar) {
		system.outputs.map[v | v.compareWithGold(thresholdVar)].join('\n')	
	}
	
	def compareWithGold(Variable variable, String thresholdVar) {
		val name = variable.name
		val indexNames = variable.domain.indexNames
		val idxStr = indexNames.join(',')
		val paramStr = '[' + variable.domain.copy.params.paramNames.join(',') + ']'
		val domain = variable.domain.copy
			.intersect('''«paramStr»->{[«idxStr»] : t=T}'''.toString.toISLSet)
			.setTupleName(stmtPrefix + name).toUnionSet
		val SVar = '''«stmtPrefix»«name»[«idxStr»]'''
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
		val goldVarAcc = '''«name + goldSuffix»(«idxStr»)'''
		
		val code = '''
«««			#define «stmtPrefix»«name»(«idxStr») do { float delta = fabs((«varAcc»/«goldVarAcc»)-1); if (delta>=«thresholdVar») printf("Y(«indexNames.map['%d'].join(',')») difference with GOLD is %E\n", «indexNames.join(', ')», delta); } while(0)
			double max_error = 0.0f;
			#define «stmtPrefix»«name»(«idxStr») max_error = max(max_error, fabs((«varAcc» / «goldVarAcc») - 1))
			«codegenVisitor.toCode»
			#undef «stmtPrefix»«name»
		'''
		code
	}
	
	def checkParamValues() {
		val nbParams = system.parameterDomain.dim(ISLDimType.isl_dim_param)
		val domain = system.parameterDomain.copy
			.moveDims(ISLDimType.isl_dim_out, 0, ISLDimType.isl_dim_param, 0, nbParams)
		val universe = ISLSet.buildUniverse(domain.space.copy)
		
		val illegalParamValues = universe.subtract(domain.copy)
		
		'''
			if («ConditionalConverter.convert(illegalParamValues).printExpr») {
				printf("Illegal parameter values, must be in «domain»\n");
				return 1;
			}
		'''
	}
	
	def inputInitialization(Variable variable) {
		val stmtName = stmtPrefix + variable.name
		
		val domain = variable.domain.copy.setTupleName(stmtName)
		val indexNameStr = domain.indexNames.join(',')
		
		val paramStr = variable.buildParamStr
		
		val initSchedule = '''
			domain: "«domain.toString»"
			child:
			  schedule: "«paramStr»->[\
			    «domain.indexNames.map[i | '''{ «stmtName»[«indexNameStr»]->[«i»] }'''].join(', \\\n')» \
			  ]"
		'''.toISLSchedule
		
		val iterators = domain.indexNames.toISLIdentifierList
		val build = ISLASTBuild.buildFromContext(initSchedule.domain.copy.params)
			.setIterators(iterators.copy)
		
		val node = build.generate(initSchedule.copy)
		val codegenVisitor = new ISLASTNodeVisitor().genC(node)
		
		val code = '''
			// «variable.name» initialization
			#define «stmtName»(«indexNameStr») «variable.name»(«indexNameStr») = rand()
			«codegenVisitor.toCode»
			#undef «stmtName»
		'''
		code
	}
	
	def call(AlphaSystem system) {
		system?.call('')
	}
	
	def call(AlphaSystem system, String suffix) {
		val paramArgs = system.parameterDomain.paramNames
		val outputs = system.outputs.map[copyAE].map[name = name + suffix; it]
		val ioArgs = (system.inputs + outputs).map[name]
		'''«system.name»(«(paramArgs + ioArgs).join(', ')»)'''
	}
	
	override localMemoryAllocation() {
		'''
			«(system.inputs + system.outputs).memoryAllocation»
			«system.outputs.memoryAllocation(goldSuffix)»
		'''
		
	}
	
	def variableDeclarations() {
		'''«(system.inputs + system.outputs).map[variableDeclaration].join(';\n')»;'''
	}
	
	def variableDeclaration(Variable variable) {
		val dim = variable.domain.dim(ISLDimType.isl_dim_out)
		'''«Factory.dataType(dataType, dim).print» «variable.name»'''
	}
	
}

