package alpha.abft.codegen

import alpha.abft.codegen.util.ISLASTNodeVisitor
import alpha.abft.codegen.util.MemoryMap
import alpha.model.AlphaSystem
import alpha.model.Variable
import fr.irisa.cairn.jnimap.isl.ISLASTBuild

import static extension alpha.abft.ABFT.buildParamStr
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

		// External system declarations
		«system.signature(Version.BASELINE)»;
		«v1System.signature»;
		«v2System.signature»;

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
		
		val sDims = stencilVar.domain.indexNames.size
		val indexNameStr = (1..<sDims).map['%*d'].join(',')
		val injStr = (1..<sDims).map[i | 'sBox, r.inj.' + stencilVar.domain.indexNames.get(i)].join(', ')
		
		val indexNameHeaderStr = (1..<sDims).map['%*s'].join(',')
		val injHeaderStr = (1..<sDims).map[i | '''sBox, "inj.«stencilVar.domain.indexNames.get(i)»"'''].join(', ')
		val injSummaryStr = (1..<sDims).map[i | '''sBox, "-"'''].join(', ')
		
		val code = '''
			static int tBox;
			static int sBox;
			static int rBox;
			static int runBox;
			static char eol[2];
			static int run;
			static int R;
			
«««			static struct ResultsSummary v1_avg = { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0, 0, 0 };
«««			static struct ResultsSummary v2_avg = { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0, 0, 0 };
		
			void printHeader() {
				int S = 300;
				char header_str[S];
				sprintf(header_str, "   %*s : (%*s,«indexNameHeaderStr») : (%*s,%*s,%*s,%*s) : %*s, %*s", 4, "bit", tBox, "inj.t", «injHeaderStr», rBox, "TP", rBox, "FP", rBox, "TN", rBox, "FN", 12, "Detected (%)", 8, "FPR (%)");
				fprintf(stderr, "%s\n", header_str);
				char header_bar[S]; for (int i=0; i<S; i++) header_bar[i] = '-';
				fprintf(stderr, "%.*s\n", (int)strlen(header_str), header_bar);
			}
		
			void print(int version, struct Result r) {
				if (!r.valid) {
					
					return;
				}
				int detected = r.TP > 0 ? 1 : 0;
			    printf("v%d,%*d : (%*d,«indexNameStr») : (%*ld,%*ld,%*ld,%*ld) : %*d, %*.2f (%*d/%d runs)%s", version, 4, r.bit, tBox, r.inj.t, «injStr», rBox, r.TP, rBox, r.FP, rBox, r.TN, rBox, r.FN, 12, detected, 8, r.FPR, runBox, run, R, eol);
			    fflush(stdout);
			}
			
			void accumulate_result(struct ResultsSummary *acc, struct Result r) {
				if (!r.valid)
					return;
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
				
				printf("v%d,%*d : (%*s,«indexNameHeaderStr») : (%*.2f,%*.2f,%*.2f,%*.2f) : %*.2f, %*.2f (%*d/%d runs)\n", version, 4, s->bit, tBox, "-", «injSummaryStr», rBox, s->TP, rBox, s->FP, rBox, s->TN, rBox, s->FN, 12, s->TPR, 8, s->FPR, runBox, run, R);
			}
			
			int main(int argc, char **argv) 
			{
				if (argc < «paramNames.size + 1») {
					printf("usage: %s «paramNames.join(' ')»\n", argv[0]);
					return 1;
				}
				
				// Parse parameter sizes
				«paramInits.join(';\n')»;
				
				tBox = max((int)log10(T) + 1, 6);
				sBox = max((int)log10(N) + 1, 6);
				rBox = (int)log10(2*(T/(float)«tileSizes.get(0)»)*«(1..<sDims).map[i | '''(N/(float)«tileSizes.get(i)»)'''].join('*')») + 4;
				
				if (getenv("SUMMARY") != NULL) {
					strcpy(eol, "\r");
				} else {
					strcpy(eol, "\n");
				}
				
«««				float threshold = atof(argv[«paramNames.size»]);
				«localMemoryAllocation»
				
				srand(0);
				
				«system.inputs.map[inputInitialization].join('\n')»
				«system.outputs.map[inputInitialization].join('\n')»
				
				#define export_injs() do { «stencilVar.domain.indexNames.map[i |'''{ int ival = (int)(rand() % «if (i == 't') 'T' else 'N'»); sprintf(val, "%d", ival); «i»_INJ = ival; setenv("«i»_INJ", val, 1); }'''].join(' ')»; } while(0)
				
				R = (getenv("NUM_RUNS") != NULL) ? atoi(getenv("NUM_RUNS")) : 5;
				runBox = (int)log10(R) + 1;
				
				#ifndef ERROR_INJECTION
				// Baseline
				if (getenv("NO_RUN_BASELINE") == NULL) {
				  «system.call»;
				}				
				«v1System.call»;
				«v2System.call»;
				#else
				
				printHeader();
				
				«stencilVar.domain.indexNames.map[i |'''int «i»_INJ'''].join('; \n')»;
				
				const char* verbose = getenv("VERBOSE");
				
				// ABFTv1
				for (int bit=31; bit>4; bit--) {
«««				//if (getenv("BIT") !=NULL && atoi(getenv("BIT")) != bit) continue;
				  char val[50];
				  sprintf(val, "%d", bit); 
				  setenv("BIT", val, 1);
				  struct ResultsSummary v_avg = { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0, 0, 0 };
				  for (run=0; run<R; run++) {
				  	srand(run);
				  	«system.inputs.map[inputInitialization].join('\n')»
				    export_injs();
				    struct Result v = «v1System.call»;
				    accumulate_result(&v_avg, v);
				    print(1, v);
				  }
				  if (getenv("SUMMARY") != NULL)
				  	print_summary(1, &v_avg);
				}
				// ABFTv2
				for (int bit=31; bit>4; bit--) {
«««				  if (getenv("BIT") !=NULL && atoi(getenv("BIT")) != bit) continue;
				  char val[50];
				  sprintf(val, "%d", bit); 
				  setenv("BIT", val, 1);
				  struct ResultsSummary v_avg = { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0, 0, 0 };
				  for (run=0; run<R; run++) {
				  	srand(run);
				  	«system.inputs.map[inputInitialization].join('\n')»
				    export_injs();
				    struct Result v = «v2System.call»;
				    accumulate_result(&v_avg, v);
				    print(2, v);
				  }
				  if (getenv("SUMMARY") != NULL)
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

