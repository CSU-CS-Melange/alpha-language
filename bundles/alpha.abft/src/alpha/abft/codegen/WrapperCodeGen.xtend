package alpha.abft.codegen

import alpha.abft.codegen.util.ISLASTNodeVisitor
import alpha.abft.codegen.util.MemoryMap
import alpha.model.AlphaSystem
import alpha.model.Variable
import fr.irisa.cairn.jnimap.isl.ISLASTBuild

import static extension alpha.abft.ABFT.buildParamStr
import static extension alpha.model.util.CommonExtensions.zipWith
import static extension alpha.model.util.ISLUtil.toISLIdentifierList
import static extension alpha.model.util.ISLUtil.toISLSchedule

class WrapperCodeGen extends SystemCodeGen {
	
	
	
	val AlphaSystem v1System
	val AlphaSystem v2System
	
	def static generateWrapper(AlphaSystem baselineSystem, AlphaSystem v1System, AlphaSystem v2System, MemoryMap memoryMap, Version version, int[] tileSizes) {
		val generator = new WrapperCodeGen(baselineSystem, v1System, v2System, memoryMap, version, tileSizes)
		generator.generate
	}
	
	new(AlphaSystem system, AlphaSystem v1System, AlphaSystem v2System, MemoryMap memoryMap, Version version, int[] tileSizes) {
		super(system, memoryMap, version, tileSizes)
		this.v1System = v1System
		this.v2System = v2System
	}
	
	
	override generate() '''
		// «system.name»-wrapper.c
		
		#include<stdio.h>
		#include<stdlib.h>
		#include<math.h>
		#include<string.h>
		#include<sys/time.h>
		
		#define max(x, y)   ((x)>(y) ? (x) : (y))
		#define min(x, y)   ((x)>(y) ? (y) : (x))
		#define ceild(n,d)  (int)ceil(((double)(n))/((double)(d)))
		#define floord(n,d) (int)floor(((double)(n))/((double)(d)))
		#define mallocCheck(v,s,d) if ((v) == NULL) { printf("Failed to allocate memory for %s : size=%lu\n", "sizeof(d)*(s)", sizeof(d)*(s)); exit(-1); }
		#define new_result() { .valid=0, .TP=0L, .FP=0L, .TN=0L, .FN=0L, .TPR=0.0f, .FPR=0.0f, .FNR=0.0f, .bit=0, .inj={.tt=0, .ti=0, .tj=0, .tk=0}, .result=0.0f, .noise=0.0f}
		#define new_result_summary() { .TP=0L, .FP=0L, .TN=0L, .FN=0L, .TPR=0.0f, .FPR=0.0f, .FNR=0.0f, .bit=0, .nb_detected=0L, .nb_results=0L, .result=0.0f, .noise=0.0f}

		// External system declarations
		«system.signature(Version.BASELINE)»;
		«v1System.signature»;
		«v2System.signature»;
		
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
		};

		// Memory mapped targets
		«system.variables.reject[isLocal].map[memoryMap.getName(name) -> domain].map[memoryTargetMacro].join('\n')»
		
		// Memory access functions
		«system.variables.reject[isLocal].map[memoryMacro].join('\n')»
		
		«system.mainFunction»
	'''
	
	
	
	
	def mainFunction(AlphaSystem system) {
		
		val paramNames = system.parameterDomain.paramNames
		val paramInits = paramNames.map[P |
			val i = paramNames.indexOf(P)
			'''long «P» = atol(argv[«i+1»])'''
		]
		
		val indexNames = stencilVar.domain.indexNames
		val sDims = indexNames.size
		val indexNameStr = (1..<sDims).map['%*d'].join(',')
		val injStr = (1..<sDims).map[i | 'sBox, r.inj.t' + stencilVar.domain.indexNames.get(i)].join(', ')
		
		val indexNameHeaderStr = (1..<sDims).map['%*s'].join(',')
		val injHeaderStr = (1..<sDims).map[i | '''sBox, "inj.«stencilVar.domain.indexNames.get(i)»"'''].join(', ')
		val injSummaryStr = (1..<sDims).map[i | '''sBox, "-"'''].join(', ')
		
		val TT = tileSizes.get(0)
		val TXs = (1..<tileSizes.size).map[i | tileSizes.get(i)]
		
		// random number in [2,T/<TT>]
		val tInjectionSite = #['''(rand() % ((T/«TT»)-2) + 2)''']
		// random number in [1,N/<TX>]
		val sInjectionSite = TXs.map[TX | '''(rand() % ((N/«TX»)-2) + 1)''']
		val injectionSite = tInjectionSite + sInjectionSite
		
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
			sprintf(header_str, "   %*s : (%*s,«indexNameHeaderStr») : (%*s,%*s,%*s,%*s) : %*s, %*s", 4, "bit", tBox, "inj.t", «injHeaderStr», rBox, "TP", rBox, "FP", rBox, "TN", rBox, "FN", 12, "Detected (%)", 8, "FPR (%)");
			char header_bar[S]; for (int i=0; i<S; i++) header_bar[i] = '-';
			fprintf(stdout, "%.*s\n", (int)strlen(header_str), header_bar);
			fprintf(stdout, "%s\n", header_str);
			fprintf(stdout, "%.*s\n", (int)strlen(header_str), header_bar);
		}
		
		void print(int version, struct Result r) {
			if (!log_flag) {
				return;
			}
			int detected = r.TP > 0 ? 1 : 0;
		    printf("v%d,%*d : (%*d,«indexNameStr») : (%*ld,%*ld,%*ld,%*ld) : %*d, %*.2f (%*d/%d runs)%s", version, 4, r.bit, tBox, r.inj.tt, «injStr», rBox, r.TP, rBox, r.FP, rBox, r.TN, rBox, r.FN, 12, detected, 8, r.FPR, runBox, run, R, eol);
		    fflush(stdout);
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
		
		void print_summary(int version, struct ResultsSummary *s) {
			s->TP = s->TP / s->nb_results;
			s->FP = s->FP / s->nb_results;
			s->TN = s->TN / s->nb_results;
			s->FN = s->FN / s->nb_results;
			s->TPR = s->TPR / s->nb_results;
			s->FPR = s->FPR / s->nb_results;
			s->FNR = s->FNR / s->nb_results;
			float detected_rate =100 * s->nb_detected / s->nb_results;
			
			printf("v%d,%*d : (%*s,«indexNameHeaderStr») : (%*.2f,%*.2f,%*.2f,%*.2f) : %*.2f, %*.2f (%*d/%d runs)\n", version, 4, s->bit, tBox, "-", «injSummaryStr», rBox, s->TP, rBox, s->FP, rBox, s->TN, rBox, s->FN, 12, detected_rate, 8, s->FPR, runBox, run, R);
		}
		#endif
		
		int main(int argc, char **argv) 
		{
			if (argc < «paramNames.size + 1») {
				printf("usage: %s «paramNames.join(' ')»\n", argv[0]);
				return 1;
			}
			
			// Parse parameter sizes
			«paramInits.join(';\n')»;
			
			«localMemoryAllocation»
			
			#ifdef «NOISE»
			srand(time(NULL));
			#else
			srand(0);
			#endif
			
			«system.inputs.map[inputInitialization].join('\n')»
			«system.outputs.map[inputInitialization].join('\n')»
			
			#if defined «TIMING»
			
			struct Result r0 = «system.call»;
			struct Result r1 = «v1System.call»;
			struct Result r2 = «v2System.call»;
			
			printf("v0:%0.4f\n", r0.result);
			printf("v1:%0.4f\n", r1.result);
			printf("v2:%0.4f\n", r2.result);
			
			#elif defined «ERROR_INJECTION»
			tBox = max((int)log10(T) + 1, 6);
			sBox = max((int)log10(N) + 1, 6);
			rBox = (int)log10(2*(T/(float)«tileSizes.get(0)»)*«(1..<sDims).map[i | '''(N/(float)«tileSizes.get(i)»)'''].join('*')») + 4;
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
			float threshold_v1, threshold_v2;
			if (threshold == NULL) {
				struct Result result;
				long input_T = T;
				T = «#[20, 4*TT].max»;
				
				result = «v1System.call»;
				printf("floating point noise: %E\n", result.noise);
				float thresholds[10] = { 1e-1, 1e-2, 1e-3, 1e-4, 1e-5, 1e-6, 1e-7, 1e-8, 1e-9, 1e-10 };
				for (int i=9; i>=0; i--) {
					threshold_v1 = thresholds[i];
					if (threshold_v1 > fabs(result.noise))
						break;
				}
				printf(" threshold_v1 set to: %E\n", threshold_v1);
				
				result = «v2System.call»;
				printf("floating point noise: %E\n", result.noise);
				for (int i=9; i>=0; i--) {
					threshold_v2 = thresholds[i];
					if (threshold_v2 > fabs(result.noise))
						break;
				}
				printf(" threshold_v2 set to: %E\n", threshold_v2);
				T = input_T;
			} else {
				threshold_v1 = atoi(getenv("THRESHOLD"));
				threshold_v2 = threshold_v1;
			}

			printHeader();
			
			«stencilVar.domain.indexNames.map[i |'''int «i»_INJ'''].join('; \n')»;
			
			const char* verbose = getenv("VERBOSE");
			const char* single_bit = getenv("BIT");
			
			// ABFTv1
			char val[50];
			sprintf(val, "%E", threshold_v1); 
			setenv("THRESHOLD", val, 1);
			for (int bit=31; bit>=8; bit--) {
				if (single_bit != NULL && atoi(single_bit) != bit)
					continue;
				char val[50];
				sprintf(val, "%d", bit); 
				setenv("bit", val, 1);
				
				struct ResultsSummary v_avg = new_result_summary();
				
				for (run=0; run<R; run++) {
					«system.inputs.map[inputInitialization].join('\n')»
					export_injs();
					struct Result v = «v1System.call»;
					accumulate_result(&v_avg, v);
					print(1, v);
				}
«««				if (getenv("SUMMARY") != NULL)
				print_summary(1, &v_avg);
			}

			// ABFTv2
			sprintf(val, "%E", threshold_v2); 
			setenv("THRESHOLD", val, 1);
			for (int bit=31; bit>=8; bit--) {
				if (single_bit != NULL && atoi(single_bit) != bit)
					continue;
				char val[50];
				sprintf(val, "%d", bit); 
				setenv("bit", val, 1);
				struct ResultsSummary v_avg = new_result_summary();
				for (run=0; run<R; run++) {
					«system.inputs.map[inputInitialization].join('\n')»
					export_injs();
					struct Result v = «v2System.call»;
					accumulate_result(&v_avg, v);
					print(2, v);
				}
«««				if (getenv("SUMMARY") != NULL)
				print_summary(2, &v_avg);
			}
			#endif
		
			return 0;
		}
		'''
		code
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
			#define «stmtName»(«indexNameStr») «variable.name»(«indexNameStr») = rand() % 100 + 1
			«codegenVisitor.toCode»
			#undef «stmtName»
		'''
		code
	}
	
	def call(AlphaSystem system) {
		system.call(#[])
	}
	
	def call(AlphaSystem system, String[] extraArgs) {
		val paramArgs = system.parameterDomain.paramNames
		val ioArgs = (system.inputs + system.outputs).map[name]
		'''«system.name»(«(paramArgs + ioArgs + extraArgs).join(', ')»)'''
	}
	
	override localMemoryAllocation() {
		(system.inputs + system.outputs).memoryAllocation
	}
	
}

