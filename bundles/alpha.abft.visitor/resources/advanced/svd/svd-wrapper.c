// This file is generated from test alphabets program by code generator in alphaz
// To compile this code, use -lm option for math library.

// Includes
#include <stdio.h>
#include <stdlib.h>
#include <stdbool.h>
#include <math.h>
#include <string.h>
#include <limits.h>
#include <float.h>
#include <time.h>
#include <sys/time.h>
#include <sys/errno.h>


// Common Macros
#define max(x, y)   ((x)>(y) ? (x) : (y))
#define MAX(x, y)	((x)>(y) ? (x) : (y))
#define min(x, y)   ((x)>(y) ? (y) : (x))
#define MIN(x, y)	((x)>(y) ? (y) : (x))
#define CEILD(n,d)  (int)ceil(((double)(n))/((double)(d)))
#define ceild(n,d)  (int)ceil(((double)(n))/((double)(d)))
#define FLOORD(n,d) (int)floor(((double)(n))/((double)(d)))
#define floord(n,d) (int)floor(((double)(n))/((double)(d)))
#define CDIV(x,y)    CEILD((x),(y))
#define div(x,y)    CDIV((x),(y))
#define FDIV(x,y)    FLOORD((x),(y))
#define LB_SHIFT(b,s)  ((int)ceild(b,s) * s)
#define MOD(i,j)   ((i)%(j))
#define mallocCheck(v,s,d) if ((v) == NULL) { printf("Failed to allocate memory for %s : size=%lu\n", "sizeof(d)*(s)", sizeof(d)*(s)); exit(-1); }
#define EPSILON 1.0E-4







//Memory Macros
#define M(i,j) M[i][j]
#define M_T(i,j) M_T[i][j]
#define U(i,j) U[i][j]
#define S(i,j) S[i][j]
#define V_T(i,j) V_T[i][j]
#define MM_T(i,j) MM_T[i][j]

#define U_verify(i,j) U_verify[i][j]
#define S_verify(i,j) S_verify[i][j]
#define V_T_verify(i,j) V_T_verify[i][j]
#define MM_T_verify(i,j) MM_T_verify[i][j]
#define var_U(i,j) U(i,j)
#define var_U_verify(i,j) U_verify(i,j)
#define var_S(i,j) S(i,j)
#define var_S_verify(i,j) S_verify(i,j)
#define var_V_T(i,j) V_T(i,j)
#define var_V_T_verify(i,j) V_T_verify(i,j)
#define var_MM_T(i,j) MM_T(i,j)
#define var_MM_T_verify(i,j) MM_T_verify(i,j)

//function prototypes
void svd(long, float**, float**, float**, float**, float**);
void svd_verify(long, float**, float**, float**, float**, float**);

//main
int main(int argc, char** argv) {
	//Check number of args
	if (argc <= 1) {
		printf("Number of argument is smaller than expected.\n");
		printf("Expecting N\n");
		exit(0);
	}
	
	char *end = 0;
	char *val = 0;
	//Read Parameters
	//Initialisation of N
	errno = 0;
	end = 0;
	val = argv[1];
	long N = strtol(val,&end,10);
	if ((errno == ERANGE && (N == LONG_MAX || N == LONG_MIN)) || (errno != 0 && N == 0)) {
		perror("strtol");
		exit(EXIT_FAILURE);
	}
	if (end == val) {
		fprintf(stderr, "No digits were found for N\n");
		exit(EXIT_FAILURE);
	}
	if (*end != '\0'){
		printf("For parameter N: Converted part: %ld, non-convertible part: %s\n", N, end);
		exit(EXIT_FAILURE);
	}
	
	
	///Parameter checking
	if (!((N >= 1))) {
		printf("The value of parameters are not valid.\n");
		exit(-1);
	}
	
	
	//Memory Allocation
	int mz1, mz2;
	float* _lin_M = (float*)malloc(sizeof(float)*((N) * (N)));
	mallocCheck(_lin_M, ((N) * (N)), float);
	float** M = (float**)malloc(sizeof(float*)*(N));
	mallocCheck(M, (N), float*);
	for (mz1=0;mz1 < N; mz1++) {
		M[mz1] = &_lin_M[(mz1*(N))];
	}
	float* _lin_U = (float*)malloc(sizeof(float)*((N) * (N)));
	mallocCheck(_lin_U, ((N) * (N)), float);
	float** U = (float**)malloc(sizeof(float*)*(N));
	mallocCheck(U, (N), float*);
	for (mz1=0;mz1 < N; mz1++) {
		U[mz1] = &_lin_U[(mz1*(N))];
	}
	float* _lin_S = (float*)malloc(sizeof(float)*((N) * (N)));
	mallocCheck(_lin_S, ((N) * (N)), float);
	float** S = (float**)malloc(sizeof(float*)*(N));
	mallocCheck(S, (N), float*);
	for (mz1=0;mz1 < N; mz1++) {
		S[mz1] = &_lin_S[(mz1*(N))];
	}
	float* _lin_V_T = (float*)malloc(sizeof(float)*((N) * (N)));
	mallocCheck(_lin_V_T, ((N) * (N)), float);
	float** V_T = (float**)malloc(sizeof(float*)*(N));
	mallocCheck(V_T, (N), float*);
	for (mz1=0;mz1 < N; mz1++) {
		V_T[mz1] = &_lin_V_T[(mz1*(N))];
	}
	float* _lin_MM_T = (float*)malloc(sizeof(float)*((N) * (N)));
	mallocCheck(_lin_MM_T, ((N) * (N)), float);
	float** MM_T = (float**)malloc(sizeof(float*)*(N));
	mallocCheck(MM_T, (N), float*);
	for (mz1=0;mz1 < N; mz1++) {
		MM_T[mz1] = &_lin_MM_T[(mz1*(N))];
	}
	#ifdef VERIFY
		float* _lin_U_verify = (float*)malloc(sizeof(float)*((N) * (N)));
		mallocCheck(_lin_U_verify, ((N) * (N)), float);
		float** U_verify = (float**)malloc(sizeof(float*)*(N));
		mallocCheck(U_verify, (N), float*);
		for (mz1=0;mz1 < N; mz1++) {
			U_verify[mz1] = &_lin_U_verify[(mz1*(N))];
		}
		float* _lin_S_verify = (float*)malloc(sizeof(float)*((N) * (N)));
		mallocCheck(_lin_S_verify, ((N) * (N)), float);
		float** S_verify = (float**)malloc(sizeof(float*)*(N));
		mallocCheck(S_verify, (N), float*);
		for (mz1=0;mz1 < N; mz1++) {
			S_verify[mz1] = &_lin_S_verify[(mz1*(N))];
		}
		float* _lin_V_T_verify = (float*)malloc(sizeof(float)*((N) * (N)));
		mallocCheck(_lin_V_T_verify, ((N) * (N)), float);
		float** V_T_verify = (float**)malloc(sizeof(float*)*(N));
		mallocCheck(V_T_verify, (N), float*);
		for (mz1=0;mz1 < N; mz1++) {
			V_T_verify[mz1] = &_lin_V_T_verify[(mz1*(N))];
		}
		float* _lin_MM_T_verify = (float*)malloc(sizeof(float)*((N) * (N)));
		mallocCheck(_lin_MM_T_verify, ((N) * (N)), float);
		float** MM_T_verify = (float**)malloc(sizeof(float*)*(N));
		mallocCheck(MM_T_verify, (N), float*);
		for (mz1=0;mz1 < N; mz1++) {
			MM_T_verify[mz1] = &_lin_MM_T_verify[(mz1*(N))];
		}
	#endif

	//Initialization of rand
	srand((unsigned)time(NULL));
	 
	//Input Initialization
	{
		#if defined (RANDOM)
			#define S0(i,j) (M(i,j) = rand()) 
		#elif defined (CHECKING) || defined (VERIFY)
			#ifdef NO_PROMPT
				#define S0(i,j) scanf("%f", &M(i,j))
			#else
				#define S0(i,j) printf("M(%ld,%ld)=",(long) i,(long) j); scanf("%f", &M(i,j))
			#endif
		#else
			#define S0(i,j) (M(i,j) = 1)   //Default value
		#endif
		
		
		int c1,c2;
		for(c1=0;c1 <= N-1;c1+=1)
		 {
		 	for(c2=0;c2 <= N-1;c2+=1)
		 	 {
		 	 	S0((c1),(c2));
		 	 }
		 }
		#undef S0
	}
	
	//Timing
	struct timeval time;
	double elapsed_time;
	
	//Call the main computation
	gettimeofday(&time, NULL);
	elapsed_time = (((double) time.tv_sec) + ((double) time.tv_usec)/1000000);
	
	svd(N, M, U, S, V_T, MM_T);

	gettimeofday(&time, NULL);
	elapsed_time = (((double) time.tv_sec) + ((double) time.tv_usec)/1000000) - elapsed_time;

	// timing information
	printf("Execution time : %lf sec.\n", elapsed_time);
	
	#ifdef TIMING
		FILE * fp = fopen( "trace.dat","a+");
		if (fp == NULL) {
				printf("I couldn't open trace.dat for writing.\n");
				exit(EXIT_FAILURE);
		}
		fprintf(fp, "%ld\t%lf\n",N,elapsed_time);
		fclose(fp);
	#endif
	
	//Verification Run
	#ifdef VERIFY
		#ifdef TIMING
			gettimeofday(&time, NULL);
			elapsed_time = (((double) time.tv_sec) + ((double) time.tv_usec)/1000000);
		#endif
    	svd_verify(N, M, U_verify, S_verify, V_T_verify, MM_T_verify);
    	#ifdef TIMING
    		gettimeofday(&time, NULL);
			elapsed_time = (((double) time.tv_sec) + ((double) time.tv_usec)/1000000) - elapsed_time;
			
			FILE * fp_verify = fopen( "trace_verify.dat","a+");
			if (fp_verify == NULL) {
					printf("I couldn't open trace_verify.dat for writing.\n");
					exit(EXIT_FAILURE);
			}
			fprintf(fp_verify, "%ld\t%lf\n",N,elapsed_time);
			fclose(fp_verify);
		#endif
	#endif
    	
	#ifdef CHECKING
    	//Print Outputs
		
		{
			#ifdef NO_PROMPT
				#define S0(i,j) printf("%0.2f\n",var_U(i,j))
			#else
				#define S0(i,j) printf("U(%ld,%ld)=",(long) i,(long) j);printf("%0.2f\n",var_U(i,j))
			#endif
			int c1,c2;
			for(c1=0;c1 <= N-1;c1+=1)
			 {
			 	for(c2=0;c2 <= N-1;c2+=1)
			 	 {
			 	 	S0((c1),(c2));
			 	 }
			 }
			#undef S0
		}
		
		{
			#ifdef NO_PROMPT
				#define S0(i,j) printf("%0.2f\n",var_S(i,j))
			#else
				#define S0(i,j) printf("S(%ld,%ld)=",(long) i,(long) j);printf("%0.2f\n",var_S(i,j))
			#endif
			int c1,c2;
			for(c1=0;c1 <= N-1;c1+=1)
			 {
			 	for(c2=0;c2 <= N-1;c2+=1)
			 	 {
			 	 	S0((c1),(c2));
			 	 }
			 }
			#undef S0
		}
		
		{
			#ifdef NO_PROMPT
				#define S0(i,j) printf("%0.2f\n",var_V_T(i,j))
			#else
				#define S0(i,j) printf("V_T(%ld,%ld)=",(long) i,(long) j);printf("%0.2f\n",var_V_T(i,j))
			#endif
			int c1,c2;
			for(c1=0;c1 <= N-1;c1+=1)
			 {
			 	for(c2=0;c2 <= N-1;c2+=1)
			 	 {
			 	 	S0((c1),(c2));
			 	 }
			 }
			#undef S0
		}
		
		{
			#ifdef NO_PROMPT
				#define S0(i,j) printf("%0.2f\n",var_MM_T(i,j))
			#else
				#define S0(i,j) printf("MM_T(%ld,%ld)=",(long) i,(long) j);printf("%0.2f\n",var_MM_T(i,j))
			#endif
			int c1,c2;
			for(c1=0;c1 <= N-1;c1+=1)
			 {
			 	for(c2=0;c2 <= N-1;c2+=1)
			 	 {
			 	 	S0((c1),(c2));
			 	 }
			 }
			#undef S0
		}
	#elif VERIFY
		//Compare outputs for verification
		{
			//Error Counter
			int _errors_ = 0;
			#define S0(i,j) if (fabsf(1.0f - var_U_verify(i,j)/var_U(i,j)) > EPSILON) _errors_++;
			int c1,c2;
			for(c1=0;c1 <= N-1;c1+=1)
			 {
			 	for(c2=0;c2 <= N-1;c2+=1)
			 	 {
			 	 	S0((c1),(c2));
			 	 }
			 }
			#undef S0
			if(_errors_ == 0){
				printf("TEST for U PASSED\n");
			}else{
				printf("TEST for U FAILED. #Errors: %d\n", _errors_);
			}
		}
		{
			//Error Counter
			int _errors_ = 0;
			#define S0(i,j) if (fabsf(1.0f - var_S_verify(i,j)/var_S(i,j)) > EPSILON) _errors_++;
			int c1,c2;
			for(c1=0;c1 <= N-1;c1+=1)
			 {
			 	for(c2=0;c2 <= N-1;c2+=1)
			 	 {
			 	 	S0((c1),(c2));
			 	 }
			 }
			#undef S0
			if(_errors_ == 0){
				printf("TEST for S PASSED\n");
			}else{
				printf("TEST for S FAILED. #Errors: %d\n", _errors_);
			}
		}
		{
			//Error Counter
			int _errors_ = 0;
			#define S0(i,j) if (fabsf(1.0f - var_V_T_verify(i,j)/var_V_T(i,j)) > EPSILON) _errors_++;
			int c1,c2;
			for(c1=0;c1 <= N-1;c1+=1)
			 {
			 	for(c2=0;c2 <= N-1;c2+=1)
			 	 {
			 	 	S0((c1),(c2));
			 	 }
			 }
			#undef S0
			if(_errors_ == 0){
				printf("TEST for V_T PASSED\n");
			}else{
				printf("TEST for V_T FAILED. #Errors: %d\n", _errors_);
			}
		}
		{
			//Error Counter
			int _errors_ = 0;
			#define S0(i,j) if (fabsf(1.0f - var_MM_T_verify(i,j)/var_MM_T(i,j)) > EPSILON) _errors_++;
			int c1,c2;
			for(c1=0;c1 <= N-1;c1+=1)
			 {
			 	for(c2=0;c2 <= N-1;c2+=1)
			 	 {
			 	 	S0((c1),(c2));
			 	 }
			 }
			#undef S0
			if(_errors_ == 0){
				printf("TEST for MM_T PASSED\n");
			}else{
				printf("TEST for MM_T FAILED. #Errors: %d\n", _errors_);
			}
		}
    #endif
    
	//Memory Free
	free(_lin_M);
	free(M);
	free(_lin_U);
	free(U);
	free(_lin_S);
	free(S);
	free(_lin_V_T);
	free(V_T);
	free(_lin_MM_T);
	free(MM_T);
	#ifdef VERIFY
		free(_lin_U_verify);
		free(U_verify);
		free(_lin_S_verify);
		free(S_verify);
		free(_lin_V_T_verify);
		free(V_T_verify);
		free(_lin_MM_T_verify);
		free(MM_T_verify);
	#endif
	
	return EXIT_SUCCESS;
}

//Memory Macros
#undef M
#undef M_T
#undef U
#undef S
#undef V_T
#undef MM_T


//Common Macro undefs
#undef max
#undef MAX
#undef min
#undef MIN
#undef CEILD
#undef ceild
#undef FLOORD
#undef floord
#undef CDIV
#undef FDIV
#undef LB_SHIFT
#undef MOD
#undef EPSILON
