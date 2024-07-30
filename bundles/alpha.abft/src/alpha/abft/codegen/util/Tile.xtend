//package alpha.abft.codegen.util
//
//import fr.irisa.cairn.jnimap.isl.ISLASTBuild
//import fr.irisa.cairn.jnimap.isl.ISLASTLoopType
//import fr.irisa.cairn.jnimap.isl.ISLSchedule
//import fr.irisa.cairn.jnimap.isl.ISLScheduleBandNode
//import java.io.File
//import java.io.FileWriter
//import java.util.ArrayList
//import java.util.HashMap
//import java.util.List
//import java.util.Map
//
//import static extension alpha.model.util.ISLUtil.toISLIdentifierList
//import static extension alpha.model.util.ISLUtil.toISLMultiUnionPWAff
//import static extension alpha.model.util.ISLUtil.toISLUnionSet
//
//class Tile {
//
//	static String ttBreak = '4'
//	static String tiBreak = '1'
//
//	
//	def static void main(String[] args) {
//	
//		var domain = '''
//			[T,N]->{
//				S0[t,i]: 0<N and 0<=t<=1 and 0<=i<=N;
//				S1[t,i]: 0<N and 1<t<=T and (i=0 or i=N);
//				S2[t,i]: 0<N and 1<t<=T and 0<i<N;
//			}'''.toISLUnionSet
//		
//		//val s = schedule(domain)
//		
//		var schedule = ISLSchedule.buildFromDomain(domain.copy)
//		val mupwa = '''
//			[T,N]->[
//				{ S0[t,i]->[t]; S1[t,i]->[t]; S2[t,i]->[t] }, 
//				{ S0[t,i]->[i]; S1[t,i]->[i]; S2[t,i]->[i] }
//			]'''.toISLMultiUnionPWAff	
//		schedule = schedule.insertPartialSchedule(mupwa.copy)
//		val x = schedule.root
//	
//		val node = (schedule.root.child(0) as ISLScheduleBandNode)
//					.setASTLoopType(0, ISLASTLoopType.isl_ast_loop_separate)
//					.setASTLoopType(1, ISLASTLoopType.isl_ast_loop_separate)
//		schedule = node.schedule
//		
//		val H = 10
//		val L = 100
//		val kernel = parseKernel(#[
//			"-1,-1,0.3332",
//			"-1,0,0.3333",
//			"-1,1,0.3334"
//		])
//		val tiledSchedule = tiledSchedule(H, L)
//		makeSingleC(kernel, tiledSchedule, H-1, L)
////		makefile(#[0, 1])
////		val v0 = codegen(schedule, 0)
////		val v1 = tiledCodegen(tiledSchedule, H+1, L, 1)
////		wrapper(v0, v1, kernel, H, L)
//		
//	}
//	
//	def static makeSingleC(Map<String, Double> kernel, ISLSchedule schedule, int H, int L) {
//		val iterators = #['tt','ti','t','i'].toISLIdentifierList
//		val build = ISLASTBuild.buildFromContext(schedule.domain.copy.params)
//						.setIterators(iterators.copy)
//		
//		val node = build.generate(schedule.copy)
//		
//		val scheduleLines = schedule.root.toString.split('\n')
//		
//		val codegenVisitor = new ISLASTNodeVisitor(H+1, L, 'K1', #['tt','ti']).genC(node)
//		
//		val v0 = 'base'
//		val v1 = 'abft'
//		
//		try (val fw = new FileWriter(new File('''/Users/lw/tmp/stencil-«H+1»x«L».c'''), false)) {
//			val lines = '''
//				#include<stdio.h>
//				#include<stdlib.h>
//				#include<math.h>
//				#include<time.h>
//				
//				#define max(x, y)   ((x)>(y) ? (x) : (y))
//				#define min(x, y)   ((x)>(y) ? (y) : (x))
//				#define ceild(n,d)  (int)ceil(((double)(n))/((double)(d)))
//				#define floord(n,d) (int)floor(((double)(n))/((double)(d)))
//				#define mallocCheck(v,s,d) if ((v) == NULL) { printf("Failed to allocate memory for %s : size=%lu\n", "sizeof(d)*(s)", sizeof(d)*(s)); exit(-1); }
//
//				#define X(i) X[i]
//				#define Y(t,i) Y[((t))*(N+1)+(i)]
//				#define C1(tt,ti) C1[(N/«L»)*(tt)+(ti)]
//				#define C2(tt,ti) C2[(N/«L»)*(tt)+(ti)]
//				#define W(i) weights[i]
//				
//				#define THRESHOLD 1e-6
//				#define I(tt,ti) fabs((C2(tt,ti)-C1(tt,ti))/C2(tt,ti)) > THRESHOLD // condition when tile computation is correct
//				
//				#define S0(tt,ti,t,i) Y(t,i) = X(i)
//				#define S1(tt,ti,t,i) Y(t,i) = Y(t-1,i)
//				#define S2(tt,ti,t,i) Y(t,i) = 0.3332*Y(t-1,i-1) + 0.3333*Y(t-1,i) + 0.3334*Y(t-1,i+1)
//				#define K1(tt,ti,t,i) C1(tt,ti) += Y(t,i)
//				#define K2(tt,ti,t,i) C2(tt,ti) += W((i)-1-«L»*(ti)+«H+1») * Y(t,i)
//				#define TRAPEZOID(tt,ti) do { compute_trapezoid(tt, ti, X, Y, C1, C2, weights, T, N); } while(I(tt,ti))
//				
//				void compute_trapezoid(int tt, int ti, float* X, float* Y, float* C1, float* C2, float* weights, int T, int N)
//				«codegenVisitor.toUnrolled»
//
//				void stencil_abft(float* X, float* Y, float* C1, float* C2, float* weights, int T, int N)
//				{
//				  /*
//				   «FOR i : 1..<scheduleLines.size»
//				   * «scheduleLines.get(i)»
//				   «ENDFOR»
//				   */
//				  «codegenVisitor.toCode»
//				}
//				
//				void stencil_base(float* X, float* Y, int T, int N)
//				{
//					int t, i;
//					for (i=0; i<=N; i++) {
//						S0(0,0,0,i);
//						S0(0,0,1,i);
//					}
//					for (int t=2; t<=N; t++) {
//						S1(0,0,t,0);
//						for (i=1; i<N; i++) {
//							S2(0,0,t,i);
//						}
//						S1(0,0,t,N);
//						
//					}
//				}
//				
//				#undef S0
//				#undef S1
//				#undef S2
//				#undef K1
//				#undef K2
//				#undef TRAPEZOID
//				
//				// convolution computation
//				#define kernel(i) kernel[i]
//				#define padded_kernel(i) padded_kernel[i]
//				#define patch(i) patch[i]
//				
//				void init(long _P0, long _N0, long _K0, long _PK0, float* kernel, float* padded_kernel, float* patch){
//					///Parameter checking
//					if (!((_P0 >= 1 && _N0 >= 1 && _K0 >= 1 && _PK0 >= 1))) {
//						printf("The value of parameters are not valid.\n");
//						exit(-1);
//					}
//					//Memory Allocation
//					
//					#define S0(i,i1) padded_kernel(i1) = 0.0
//					#define S1(i,i1) padded_kernel(i1) = kernel(-_PK0+i1+_K0)
//					#define S2(i,i1) padded_kernel(i1) = 0.0
//					#define S3(i,i1) patch(i1) = 0.0
//					#define S4(i,i1) patch(i1) = 1.0
//					#define S5(i,i1) patch(i1) = 0.0
//					{
//						//Domain
//						//{i,i1|i==0 && _P0>=1 && _N0>=1 && _K0>=1 && _PK0>=1 && _PK0>=_K0+i1+1 && i1>=0 && 2_PK0>=i1}
//						//{i,i1|i==0 && _K0+i1>=_PK0 && _K0+_PK0>=i1 && _P0>=1 && _N0>=1 && _K0>=1 && _PK0>=1 && i1>=0 && 2_PK0>=i1}
//						//{i,i1|i==0 && _P0>=1 && _N0>=1 && _K0>=1 && _PK0>=1 && i1>=_K0+_PK0+1 && i1>=0 && 2_PK0>=i1}
//						//{i,i1|i==1 && _P0>=1 && _N0>=1 && _K0>=1 && _PK0>=1 && _PK0>=i1+1 && _N0+2_PK0>=i1+1 && i1>=0}
//						//{i,i1|i==1 && i1>=_PK0 && _N0+_PK0>=i1+1 && _P0>=1 && _N0>=1 && _K0>=1 && _PK0>=1 && _N0+2_PK0>=i1+1 && i1>=0}
//						//{i,i1|i==1 && _P0>=1 && _N0>=1 && _K0>=1 && _PK0>=1 && i1>=_N0+_PK0 && _N0+2_PK0>=i1+1 && i1>=0}
//						int c2;
//						for(c2=0;c2 <= -_K0+_PK0-1;c2+=1)
//						 {
//						 	S0((0),(c2));
//						 }
//						for(c2=max(0,-_K0+_PK0);c2 <= min(2*_PK0,_K0+_PK0);c2+=1)
//						 {
//						 	S1((0),(c2));
//						 }
//						for(c2=_K0+_PK0+1;c2 <= 2*_PK0;c2+=1)
//						 {
//						 	S2((0),(c2));
//						 }
//						for(c2=0;c2 <= _PK0-1;c2+=1)
//						 {
//						 	S3((1),(c2));
//						 }
//						for(c2=_PK0;c2 <= _N0+_PK0-1;c2+=1)
//						 {
//						 	S4((1),(c2));
//						 }
//						for(c2=_N0+_PK0;c2 <= _N0+2*_PK0-1;c2+=1)
//						 {
//						 	S5((1),(c2));
//						 }
//					}
//					#undef S0
//					#undef S1
//					#undef S2
//					#undef S3
//					#undef S4
//					#undef S5
//					
//					//Memory Free
//				}
//				
//				//Memory Macros
//				#undef kernel
//				#undef padded_kernel
//				#undef patch
//				
//				//Local Function Declarations
//				float reduce_conv_out_1(long, long, int, float*, float*);
//				
//				//Memory Macros
//				#define kernel(i) kernel[i]
//				#define arr(i) arr[i]
//				#define out(i) out[i]
//				
//				void conv(long _K0, long _L0, float* kernel, float* arr, float* out){
//					///Parameter checking
//					if (!((_K0 >= 1 && _L0 >= _K0))) {
//						printf("The value of parameters are not valid.\n");
//						exit(-1);
//					}
//					//Memory Allocation
//					
//					#define S0(i) out(i) = reduce_conv_out_1(_K0,_L0,i,arr,kernel)
//					{
//						//Domain
//						//{i|_K0+i>=0 && _K0+_L0>=i+1 && _K0>=1 && _L0>=_K0 && i>=0 && _L0>=i+1}
//						int c1;
//						for(c1=0;c1 <= _L0-1;c1+=1)
//						 {
//						 	S0((c1));
//						 }
//					}
//					#undef S0
//					
//					//Memory Free
//				}
//				float reduce_conv_out_1(long _K0, long _L0, int ip, float* arr, float* kernel){
//					float reduceVar = 0;
//					#define S1(i,p) reduceVar = (reduceVar)+((arr(i+p))*(kernel(-p+_K0)))
//					{
//						//Domain
//						//{i,p|_K0+ip>=0 && _K0+_L0>=ip+1 && _K0>=1 && _L0>=_K0 && ip>=0 && _L0>=ip+1 && i+p>=0 && _L0>=i+p+1 && i>=0 && _L0>=i+1 && _K0+p>=0 && _K0>=p && _K0+i>=0 && _K0+_L0>=i+1 && ip==i}
//						int c2;
//						for(c2=max(-_K0,-ip);c2 <= min(_K0,_L0-ip-1);c2+=1)
//						 {
//						 	S1((ip),(c2));
//						 }
//					}
//					#undef S1
//					return reduceVar;
//				}
//				
//				//Memory Macros
//				#undef kernel
//				#undef arr
//				#undef out
//				
//				#define Y_«v0»(t,i) Y_«v0»[((t))*(N+1)+(i)]
//				#define Y_«v1»(t,i) Y_«v1»[((t))*(N+1)+(i)]
//				
//				int main(int argc, char** argv) {
//									
//					if (argc < 3) {
//						printf("usage: %s T N\n", argv[0]);
//						return 1;
//					}
//					int T = atoi(argv[1]);
//					int N = atoi(argv[2]);
//					
//					float* X = (float*)malloc(sizeof(float)*(N+1));
//					float* Y_«v0» = (float*)malloc(sizeof(float)*(T+1)*(N+1));
//					float* Y_«v1» = (float*)malloc(sizeof(float)*(T+1)*(N+1));
//					float* weights = (float*)malloc(sizeof(float)*(«L+2*H»));
//					float* C1 = (float*)malloc(sizeof(float)*((N/«L»)*(T/«H»)));
//					float* C2 = (float*)malloc(sizeof(float)*((N/«L»)*(T/«H»)));
//					
//					//srand(time(NULL));
//					srand(0);
//					
//					for (int i=0; i<=N; i++) {
//						X(i) = rand()%1000;
//					}
//					
//					«weightVarComputation1d(kernel, H, L)»
//					
//					stencil_«v0»(X, Y_«v0», T, N);
//					stencil_«v1»(X, Y_«v1», C1, C2, weights, T, N);
//					
//					// sanity check that abft-augmented code still produces the correct answer
//					int is_correct = 1;
//					for (int t=0; t<=T; t++) {
//						for (int i=0; i<=N; i++) {
//							if (fabs( (Y_«v0»(t,i)-Y_«v1»(t,i)) / Y_«v0»(t,i)) > 1e-4) {
//								printf("error: Y_«v0»(%d,%d)=%f, Y_«v1»(%d,%d)=%f\n",t,i,Y_«v0»(t,i),t,i,Y_«v1»(t,i));
//								is_correct = 0;
//							}
//						}
//					}
//					if (is_correct) {
//						printf("result is correct\n");
//					}
//				}
//			'''
//			println(lines)
//			fw.write(lines)
//    		fw.close();
//		}
//		
//	}
//	
//	def static ISLSchedule tiledSchedule(int H, int L) {
//		
//		val tConstraints = '''«H»tt<=t<«H»tt+«H»'''
//		val iConstraints = '''«L»ti-(«H»-1+«H»tt-t)<=i<«L»ti+«L»+(«H»-1+«H»tt-t)'''
//		val s2Constraints = '''100<N and 40<T and «L»<N and «H»<T and 0<=«H»tt<=T and 0<=«L»ti<=N and «tConstraints» and «iConstraints» and 1<t<=T and 0<i<N'''
//		val isolateConstraints = '''1<=tt,ti and «L»ti+«L-1»+«H»<N and «H»tt+«H-1»<T'''
//		val domain = '''
//			[T,N]->{
//				S0[tt,ti,t,i]: 100<N and 40<T and «L»<N and «H»<T and 0<=«H»tt<=T and 0<=«L»ti<=N and «tConstraints» and «iConstraints» and 0<=t<=1 and 0<=i<=N;
//				S1[tt,ti,t,i]: 100<N and 40<T and «L»<N and «H»<T and 0<=«H»tt<=T and 0<=«L»ti<=N and «tConstraints» and «iConstraints» and 1<t<=T and (i=0 or i=N);
//				S2[tt,ti,t,i]: «s2Constraints»;
//				K1[tt,ti,t,i]: «s2Constraints» and «isolateConstraints» and t=«H»tt+«H-1»;
//				K2[tt,ti,t,i]: «s2Constraints» and «isolateConstraints» and t=«H»tt;
//			}'''.toISLUnionSet
//		
//		 val schedule = ISLSchedule.buildFromString(domain.context, '''
//			domain: "«domain.toString»"
//			child:
//			  sequence:
//			  - filter: "{S0[tt,ti,t,i]}"
//			  - filter: "{S1[tt,ti,t,i]; S2[tt,ti,t,i]; K1[tt,ti,t,i]; K2[tt,ti,t,i] }"
//			    child:
//			      schedule: "[T,N]->[\
//			      	{ S1[tt,ti,t,i]->[tt]; S2[tt,ti,t,i]->[tt]; K1[tt,ti,t,i]->[tt]; K2[tt,ti,t,i]->[tt] }, \
//			      	{ S1[tt,ti,t,i]->[ti]; S2[tt,ti,t,i]->[ti]; K1[tt,ti,t,i]->[ti]; K2[tt,ti,t,i]->[ti] }, \
//			      	{ S1[tt,ti,t,i]->[t]; S2[tt,ti,t,i]->[t]; K1[tt,ti,t,i]->[t]; K2[tt,ti,t,i]->[t] }, \
//			      	{ S1[tt,ti,t,i]->[i]; S2[tt,ti,t,i]->[i]; K1[tt,ti,t,i]->[i]; K2[tt,ti,t,i]->[i] } \
//			      ]"
//			      options: "[T,N]->{ \
//			      	isolate[[] -> [tt,ti,t,i]] : «isolateConstraints»;\
//			        [isolate[] -> unroll[i]] : 2<=i<4 }"
//			      child:
//			        sequence:
//			        - filter: "{S1[tt,ti,t,i]; S2[tt,ti,t,i]}"
//			        - filter: "{K1[tt,ti,t,i]; K2[tt,ti,t,i]}"
//		''')
//
//		schedule
//	}
//	
//	
//	
//	
//	
//	
//	
//	
//	
//	
//	
//	
//	
//	
//	
//	
//	
//	
//	
//	
//	def static tiledCodegen(ISLSchedule schedule, int H, int L, int version) {
//
//		//val build = ISLASTBuild.buildFromContext(schedule.domain.copy.params)
//		val iterators = #['tt','ti','t','i'].toISLIdentifierList
//		val build = ISLASTBuild.buildFromContext(schedule.domain.copy.params)
//						.setIterators(iterators.copy)
//		
//		val node = build.generate(schedule.copy)
//		
//		val scheduleLines = schedule.root.toString.split('\n')
//		
//		val codegenVisitor = new ISLASTNodeVisitor(H, L, 'K1', #['tt','ti']).genC(node)
//		
//		try (val fw = new FileWriter(new File('''/Users/lw/tmp/main.«version».c'''), false)) {
//			val lines = '''
//				#include<stdio.h>
//				#include<stdlib.h>
//				#include<math.h>
//				#include<time.h>
//				
//				#define max(x, y)   ((x)>(y) ? (x) : (y))
//				#define min(x, y)   ((x)>(y) ? (y) : (x))
//				#define ceild(n,d)  (int)ceil(((double)(n))/((double)(d)))
//				#define floord(n,d) (int)floor(((double)(n))/((double)(d)))
//
//				#define X(i) X[i]
//				#define Y(t,i) Y[((t))*(N+1)+(i)]
//				#define C1(tt,ti) C1[(N/«L»)*(tt)+(ti)]
//				#define C2(tt,ti) C2[(N/«L»)*(tt)+(ti)]
//				#define W(i) weights[i]
//				
//				#define THRESHOLD 1e-6
//				#define I(tt,ti) fabs((C2(tt,ti)-C1(tt,ti))/C2(tt,ti)) > THRESHOLD // condition when tile computation is correct
//				
//				#define S0(tt,ti,t,i) Y(t,i) = X(i)
//				#define S1(tt,ti,t,i) Y(t,i) = Y(t-1,i)
//				#define S2(tt,ti,t,i) Y(t,i) = 0.3332*Y(t-1,i-1) + 0.3333*Y(t-1,i) + 0.3334*Y(t-1,i+1)
//				#define K1(tt,ti,t,i) C1(tt,ti) += Y(t,i)
//				#define K2(tt,ti,t,i) C2(tt,ti) += W((i)-1-«L»*(ti)+«H») * Y(t,i)
//				#define TRAPEZOID(tt,ti) do { compute_trapezoid(tt, ti, X, Y, C1, C2, weights, T, N); } while(I(tt,ti))
//				
//				
//				void compute_trapezoid(int tt, int ti, float* X, float* Y, float* C1, float* C2, float* weights, int T, int N)
//				«codegenVisitor.toUnrolled»
//
//				void stencil«version»(float* X, float* Y, float* C1, float* C2, float* weights, int T, int N)
//				{
//				  /*
//				   «FOR i : 1..<scheduleLines.size»
//				   * «scheduleLines.get(i)»
//				   «ENDFOR»
//				   */
//				  «codegenVisitor.toCode»
//				}
//			'''
//			println(lines)
//			fw.write(lines)
//    		fw.close();
//		}
//		
//		version
//	}
//	
//	def static codegen(ISLSchedule schedule, int version) {
//
//		//val build = ISLASTBuild.buildFromContext(schedule.domain.copy.params)
//		val iterators = #['t','i'].toISLIdentifierList
//		val build = ISLASTBuild.buildFromContext(schedule.domain.copy.params)
//						.setIterators(iterators)
//		val node = build.generate(schedule.copy)
//		val scheduleLines = schedule.root.toString.split('\n')
//		
//		try (val fw = new FileWriter(new File('''/Users/lw/tmp/main.«version».c'''), false)) {
//			val lines = '''
//				#include<stdio.h>
//				#include<stdlib.h>
//				#include<math.h>
//				#include<time.h>
//				
//				#define max(x, y)   ((x)>(y) ? (x) : (y))
//				#define min(x, y)   ((x)>(y) ? (y) : (x))
//				#define ceild(n,d)  (int)ceil(((double)(n))/((double)(d)))
//				#define floord(n,d) (int)floor(((double)(n))/((double)(d)))
//				
//				void stencil«version»(float* X, float* Y, int T, int N) {
//				
//				  #define X(i) X[i]
//				  #define Y(t,i) Y[((t))*(N+1)+(i)]
//				
//				  #define S0(t,i) Y(t,i) = X(i)
//				  #define S1(t,i) Y(t,i) = Y(t-1,i)
//				  #define S2(t,i) Y(t,i) = 0.3332*Y(t-1,i-1) + 0.3333*Y(t-1,i) + 0.3334*Y(t-1,i+1)
//				
//				  /*
//				  «FOR i : 1..<scheduleLines.size»
//				   * «scheduleLines.get(i)»
//				  «ENDFOR»
//				   */
//				  «node.toCString»
//				}
//				
//				void main(
//			'''
//			//println(lines)
//			fw.write(lines)
//    		fw.close();
//		}
//		
//		version
//	}
//	
//	def static wrapper(int v0, int v1, Map<String, Double> kernel, int H, int L) {
//		
//		val dType = 'float'
//		
//		try (val fw = new FileWriter(new File('''/Users/lw/tmp/wrapper.c'''), false)) {
//			val lines = '''
//				#include<stdio.h>
//				#include<stdlib.h>
//				#include<time.h>
//				#include<math.h>
//				
//				#define mallocCheck(v,s,d) if ((v) == NULL) { printf("Failed to allocate memory for %s : size=%lu\n", "sizeof(d)*(s)", sizeof(d)*(s)); exit(-1); }
//				
//				#define X(i) X[i]
//				#define Y«v0»(t,i) Y«v0»[((t))*(N+1)+(i)]
//				#define Y«v1»(t,i) Y«v1»[((t))*(N+1)+(i)]
//				#define W(i) weights[(i)]
//				
//				void stencil«v0»(float*, float*, int, int);
//				void stencil«v1»(float*, float*, float*, float*, float*, int, int);
//				void init(long, long, long, long, «dType»*, «dType»*, «dType»*);
//				void conv(long, long, «dType»*, «dType»*, «dType»*);
//				
//				int main(int argc, char** argv) {
//					
//					if (argc < 3) {
//						printf("usage: %s T N\n", argv[0]);
//						return 1;
//					}
//					int T = atoi(argv[1]);
//					int N = atoi(argv[2]);
//					
//					float* X = (float*)malloc(sizeof(float)*(N+1));
//					float* Y«v0» = (float*)malloc(sizeof(float)*(T+1)*(N+1));
//					float* Y«v1» = (float*)malloc(sizeof(float)*(T+1)*(N+1));
//					float* weights = (float*)malloc(sizeof(float)*(«L+2*H»));
//					float* C1 = (float*)malloc(sizeof(float)*((N/«L»)*(T/«H»)));
//					float* C2 = (float*)malloc(sizeof(float)*((N/«L»)*(T/«H»)));
//					
//					//srand(time(NULL));
//					srand(0);
//					
//					for (int i=0; i<=N; i++) {
//						X(i) = rand()%1000;
//					}
//					
//					«weightVarComputation1d(kernel, H, L)»
//					
//					stencil«v0»(X, Y«v0», T, N);
//					stencil«v1»(X, Y«v1», C1, C2, weights, T, N);
//					
//					for (int t=0; t<=T; t++) {
//						for (int i=0; i<=N; i++) {
//							if (fabs( (Y0(t,i)-Y1(t,i)) / Y0(t,i)) > 1e-4)
//								printf("error: Y«v0»(%d,%d)=%f, Y«v1»(%d,%d)=%f\n",t,i,Y«v0»(t,i),t,i,Y«v1»(t,i));
//						}
//					}
//				}
//			'''
//			fw.write(lines)
//    		fw.close();
//		}
//	}
//
//	def static Map<String,Double> parseKernel(String[] kernel) {
//		val numDims = kernel.get(0).split(',').size - 2
//		val ret = new HashMap<String,Double>
//		for (point : kernel) {
//			val key = (1..<1+numDims).map[point.split(',').get(it)].join(',')
//			val value = Double.parseDouble(point.split(',').get(1+numDims))
//			ret.put(key, value)
//		}
//		ret
//	}
//	
//	def static List<Long> computeRadius(Map<String,Double> kernel) {
//		val numDims = kernel.keySet.get(0).split(',').size
//		val radius = (0..<numDims).map[new ArrayList<Long>].toList
//		
//		for (key : kernel.keySet) {
//			val pieces = key.split(',').map[Long.parseLong(it)].toList
//			for (i : 0..<pieces.size) {
//				radius.get(i).add(pieces.get(i))
//			}
//			
//		}
//		val ret = radius.map[it.reduce(v0,v1|Math.max(v0,v1))].toList
//		ret
//	}
//	
//	def static Map<String,Double> shiftKernel(Map<String,Double> kernel, List<Long> radius) {
//		val ret = new HashMap<String,Double>
//		val numDims = kernel.keySet.get(0).split(',').size
//		for (key : kernel.keySet) {
//			val shiftedKey = (0..<numDims).map[Long.parseLong(key.split(',').get(it)) + radius.get(it)].join(',')
//			ret.put(shiftedKey, kernel.get(key))
//		}
//		ret
//	} 
//
//	def static weightVarComputation1d(Map<String, Double> kernel, int H, int L) {
//		val dType = 'float'
//		val radius = computeRadius(kernel)
//		val shiftedKernelMap = shiftKernel(kernel, radius)
//		val ret = '''
//			long _P0 = «H»;
//			long _N0 = «L»;
//			long _K0 = «radius.get(0)»;
//			long _PK0 = _P0*_K0;
//			
//			int padded_kernel_L0 = 2*(_PK0)+1;
//			int patch_L0 = _N0+2*_PK0;
//			
//			«dType»* kernel = («dType»*)malloc(sizeof(«dType»)*(2*_K0+1));
//			mallocCheck(kernel, (2*_K0+1), «dType»);
//			«dType»* padded_kernel = («dType»*)malloc(sizeof(«dType»)*(2*_PK0+1));
//			mallocCheck(padded_kernel, (2*_PK0+1), «dType»);
//			«dType»* padded_kernel_cp = («dType»*)malloc(sizeof(«dType»)*(2*_PK0+1));
//			mallocCheck(padded_kernel_cp, (2*_PK0+1), «dType»);
//			«dType»* patch = («dType»*)malloc(sizeof(«dType»)*(_N0+2*_PK0+0));
//			mallocCheck(patch, (_N0+2*_PK0+0), «dType»);
//			«dType»* patch_cp = («dType»*)malloc(sizeof(«dType»)*(_N0+2*_PK0+0));
//			mallocCheck(patch_cp, (_N0+2*_PK0+0), «dType»);
//			«dType»* _tmp;
//			
//			#define swap(_A,_B) do {_tmp=_A; _A=_B; _B=_tmp;} while(0)
//			#define kernel(i) kernel[i]
//			
//			«FOR key : shiftedKernelMap.keySet»
//			kernel(«key») = «shiftedKernelMap.get(key)»;
//			«ENDFOR»
//			
//			// initialize the buffers with appropriate padding
//			init(_P0, _N0, _K0, _PK0, kernel, padded_kernel, patch);
//			
//			// precompute the self-convolution of the kernel _P0=«H» times
//			for (int p=1; p<_P0; p++) {
//			    conv(_K0, padded_kernel_L0, kernel, padded_kernel, padded_kernel_cp);
//			    swap(padded_kernel, padded_kernel_cp);
//			}
//			conv(_PK0, patch_L0, padded_kernel, patch, patch_cp);
//			swap(patch, patch_cp);
//			
//			for (int i=0; i<«L+2*H»; i++) {
//				W(i) = patch[i];
//			}
//		'''
//		return ret
//	}
//
//	def static makefile(List<Integer> versions) {
//		try (val fw = new FileWriter(new File('/Users/lw/tmp/Makefile'), false)) {
//			val lines = '''
//				FLAGS=
//				all: wrapper
//				
//				«FOR i : versions»
//				«i».o: main.«i».c
//					gcc main.«i».c -c -o «i».o $(FLAGS)
//				
//				«ENDFOR»
//				init.o : init.c
//					gcc init.c -o init.o -c $(FLAGS)
//				
//				conv.o : conv.c
//					gcc conv.c -o conv.o -c $(FLAGS)
//				
//				wrapper: wrapper.c 0.o 1.o init.o conv.o
//					gcc wrapper.c 0.o 1.o init.o conv.o -o wrapper $(FLAGS)
//				
//				clean:
//					rm -f *.o wrapper
//			'''
//			fw.write(lines)
//    		fw.close();
//		}
//	}
//	
//	
//}

