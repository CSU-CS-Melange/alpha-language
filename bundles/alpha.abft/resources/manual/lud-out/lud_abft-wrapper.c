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
#define A(i,j) A[i][j]
#define L(i,j) L[i][j]
#define U(i,j) U[i][j]
#define L_c_0(j) L_c_0[j]
#define U_r_0(i) U_r_0[i]
#define A_c(j) A_c[j]
#define A_r(i) A_r[i]
#define A_f(i,j) A_f[i][j]

#define L_verify(i,j) L_verify[i][j]
#define U_verify(i,j) U_verify[i][j]
#define L_c_0_verify(j) L_c_0_verify[j]
#define U_r_0_verify(i) U_r_0_verify[i]
#define A_c_verify(j) A_c_verify[j]
#define A_r_verify(i) A_r_verify[i]
#define A_f_verify(i,j) A_f_verify[i][j]
#define var_L(i,j) L(i,j)
#define var_L_verify(i,j) L_verify(i,j)
#define var_U(i,j) U(i,j)
#define var_U_verify(i,j) U_verify(i,j)
#define var_L_c_0(j) L_c_0(j)
#define var_L_c_0_verify(j) L_c_0_verify(j)
#define var_U_r_0(i) U_r_0(i)
#define var_U_r_0_verify(i) U_r_0_verify(i)
#define var_A_c(j) A_c(j)
#define var_A_c_verify(j) A_c_verify(j)
#define var_A_r(i) A_r(i)
#define var_A_r_verify(i) A_r_verify(i)
#define var_A_f(i,j) A_f(i,j)
#define var_A_f_verify(i,j) A_f_verify(i,j)

//function prototypes
void lud_abft(long, float**, float**, float**, float*, float*, float*, float*, float**);
void lud_abft_verify(long, float**, float**, float**, float*, float*, float*, float*, float**);

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
	float* _lin_A = (float*)malloc(sizeof(float)*((N) * (N)));
	mallocCheck(_lin_A, ((N) * (N)), float);
	float** A = (float**)malloc(sizeof(float*)*(N));
	mallocCheck(A, (N), float*);
	for (mz1=0;mz1 < N; mz1++) {
		A[mz1] = &_lin_A[(mz1*(N))];
	}
	float* _lin_L = (float*)malloc(sizeof(float)*((N) * (N)));
	mallocCheck(_lin_L, ((N) * (N)), float);
	float** L = (float**)malloc(sizeof(float*)*(N));
	mallocCheck(L, (N), float*);
	for (mz1=0;mz1 < N; mz1++) {
		L[mz1] = &_lin_L[(mz1*(N))];
	}
	float* _lin_U = (float*)malloc(sizeof(float)*((N) * (N)));
	mallocCheck(_lin_U, ((N) * (N)), float);
	float** U = (float**)malloc(sizeof(float*)*(N));
	mallocCheck(U, (N), float*);
	for (mz1=0;mz1 < N; mz1++) {
		U[mz1] = &_lin_U[(mz1*(N))];
	}
	float* L_c_0 = (float*)malloc(sizeof(float)*(N));
	mallocCheck(L_c_0, (N), float);
	float* U_r_0 = (float*)malloc(sizeof(float)*(N));
	mallocCheck(U_r_0, (N), float);
	float* A_c = (float*)malloc(sizeof(float)*(N));
	mallocCheck(A_c, (N), float);
	float* A_r = (float*)malloc(sizeof(float)*(N));
	mallocCheck(A_r, (N), float);
	float* _lin_A_f = (float*)malloc(sizeof(float)*((N+1) * (N+1)));
	mallocCheck(_lin_A_f, ((N+1) * (N+1)), float);
	float** A_f = (float**)malloc(sizeof(float*)*(N+1));
	mallocCheck(A_f, (N+1), float*);
	for (mz1=0;mz1 < N+1; mz1++) {
		A_f[mz1] = &_lin_A_f[(mz1*(N+1))];
	}
	#ifdef VERIFY
		float* _lin_L_verify = (float*)malloc(sizeof(float)*((N) * (N)));
		mallocCheck(_lin_L_verify, ((N) * (N)), float);
		float** L_verify = (float**)malloc(sizeof(float*)*(N));
		mallocCheck(L_verify, (N), float*);
		for (mz1=0;mz1 < N; mz1++) {
			L_verify[mz1] = &_lin_L_verify[(mz1*(N))];
		}
		float* _lin_U_verify = (float*)malloc(sizeof(float)*((N) * (N)));
		mallocCheck(_lin_U_verify, ((N) * (N)), float);
		float** U_verify = (float**)malloc(sizeof(float*)*(N));
		mallocCheck(U_verify, (N), float*);
		for (mz1=0;mz1 < N; mz1++) {
			U_verify[mz1] = &_lin_U_verify[(mz1*(N))];
		}
		float* L_c_0_verify = (float*)malloc(sizeof(float)*(N));
		mallocCheck(L_c_0_verify, (N), float);
		float* U_r_0_verify = (float*)malloc(sizeof(float)*(N));
		mallocCheck(U_r_0_verify, (N), float);
		float* A_c_verify = (float*)malloc(sizeof(float)*(N));
		mallocCheck(A_c_verify, (N), float);
		float* A_r_verify = (float*)malloc(sizeof(float)*(N));
		mallocCheck(A_r_verify, (N), float);
		float* _lin_A_f_verify = (float*)malloc(sizeof(float)*((N+1) * (N+1)));
		mallocCheck(_lin_A_f_verify, ((N+1) * (N+1)), float);
		float** A_f_verify = (float**)malloc(sizeof(float*)*(N+1));
		mallocCheck(A_f_verify, (N+1), float*);
		for (mz1=0;mz1 < N+1; mz1++) {
			A_f_verify[mz1] = &_lin_A_f_verify[(mz1*(N+1))];
		}
	#endif

	//Initialization of rand
	srand((unsigned)time(NULL));
	 
	//Input Initialization
	{
		#if defined (RANDOM)
			#define S0(i,j) (A(i,j) = rand()) 
		#elif defined (CHECKING) || defined (VERIFY)
			#ifdef NO_PROMPT
				#define S0(i,j) scanf("%f", &A(i,j))
			#else
				#define S0(i,j) printf("A(%ld,%ld)=",(long) i,(long) j); scanf("%f", &A(i,j))
			#endif
		#else
			#define S0(i,j) (A(i,j) = 1)   //Default value
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
	
	lud_abft(N, A, L, U, L_c_0, U_r_0, A_c, A_r, A_f);

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
    	lud_abft_verify(N, A, L_verify, U_verify, L_c_0_verify, U_r_0_verify, A_c_verify, A_r_verify, A_f_verify);
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
				#define S0(i,j) printf("%0.2f\n",var_L(i,j))
			#else
				#define S0(i,j) printf("L(%ld,%ld)=",(long) i,(long) j);printf("%0.2f\n",var_L(i,j))
			#endif
			int c1,c2;
			for(c1=0;c1 <= N-1;c1+=1)
			 {
			 	for(c2=0;c2 <= c1;c2+=1)
			 	 {
			 	 	S0((c1),(c2));
			 	 }
			 }
			#undef S0
		}
		
		{
			#ifdef NO_PROMPT
				#define S0(i,j) printf("%0.2f\n",var_U(i,j))
			#else
				#define S0(i,j) printf("U(%ld,%ld)=",(long) i,(long) j);printf("%0.2f\n",var_U(i,j))
			#endif
			int c1,c2;
			for(c1=0;c1 <= N-1;c1+=1)
			 {
			 	for(c2=c1;c2 <= N-1;c2+=1)
			 	 {
			 	 	S0((c1),(c2));
			 	 }
			 }
			#undef S0
		}
		
		{
			#ifdef NO_PROMPT
				#define S0(j) printf("%0.2f\n",var_L_c_0(j))
			#else
				#define S0(j) printf("L_c_0(%ld)=",(long) j);printf("%0.2f\n",var_L_c_0(j))
			#endif
			int c1;
			for(c1=0;c1 <= N-1;c1+=1)
			 {
			 	S0((c1));
			 }
			#undef S0
		}
		
		{
			#ifdef NO_PROMPT
				#define S0(i) printf("%0.2f\n",var_U_r_0(i))
			#else
				#define S0(i) printf("U_r_0(%ld)=",(long) i);printf("%0.2f\n",var_U_r_0(i))
			#endif
			int c1;
			for(c1=0;c1 <= N-1;c1+=1)
			 {
			 	S0((c1));
			 }
			#undef S0
		}
		
		{
			#ifdef NO_PROMPT
				#define S0(j) printf("%0.2f\n",var_A_c(j))
			#else
				#define S0(j) printf("A_c(%ld)=",(long) j);printf("%0.2f\n",var_A_c(j))
			#endif
			int c1;
			for(c1=0;c1 <= N-1;c1+=1)
			 {
			 	S0((c1));
			 }
			#undef S0
		}
		
		{
			#ifdef NO_PROMPT
				#define S0(i) printf("%0.2f\n",var_A_r(i))
			#else
				#define S0(i) printf("A_r(%ld)=",(long) i);printf("%0.2f\n",var_A_r(i))
			#endif
			int c1;
			for(c1=0;c1 <= N-1;c1+=1)
			 {
			 	S0((c1));
			 }
			#undef S0
		}
		
		{
			#ifdef NO_PROMPT
				#define S0(i,j) printf("%0.2f\n",var_A_f(i,j))
			#else
				#define S0(i,j) printf("A_f(%ld,%ld)=",(long) i,(long) j);printf("%0.2f\n",var_A_f(i,j))
			#endif
			int c1,c2;
			for(c1=0;c1 <= N;c1+=1)
			 {
			 	for(c2=0;c2 <= N;c2+=1)
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
			#define S0(i,j) if (fabsf(1.0f - var_L_verify(i,j)/var_L(i,j)) > EPSILON) _errors_++;
			int c1,c2;
			for(c1=0;c1 <= N-1;c1+=1)
			 {
			 	for(c2=0;c2 <= c1;c2+=1)
			 	 {
			 	 	S0((c1),(c2));
			 	 }
			 }
			#undef S0
			if(_errors_ == 0){
				printf("TEST for L PASSED\n");
			}else{
				printf("TEST for L FAILED. #Errors: %d\n", _errors_);
			}
		}
		{
			//Error Counter
			int _errors_ = 0;
			#define S0(i,j) if (fabsf(1.0f - var_U_verify(i,j)/var_U(i,j)) > EPSILON) _errors_++;
			int c1,c2;
			for(c1=0;c1 <= N-1;c1+=1)
			 {
			 	for(c2=c1;c2 <= N-1;c2+=1)
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
			#define S0(j) if (fabsf(1.0f - var_L_c_0_verify(j)/var_L_c_0(j)) > EPSILON) _errors_++;
			int c1;
			for(c1=0;c1 <= N-1;c1+=1)
			 {
			 	S0((c1));
			 }
			#undef S0
			if(_errors_ == 0){
				printf("TEST for L_c_0 PASSED\n");
			}else{
				printf("TEST for L_c_0 FAILED. #Errors: %d\n", _errors_);
			}
		}
		{
			//Error Counter
			int _errors_ = 0;
			#define S0(i) if (fabsf(1.0f - var_U_r_0_verify(i)/var_U_r_0(i)) > EPSILON) _errors_++;
			int c1;
			for(c1=0;c1 <= N-1;c1+=1)
			 {
			 	S0((c1));
			 }
			#undef S0
			if(_errors_ == 0){
				printf("TEST for U_r_0 PASSED\n");
			}else{
				printf("TEST for U_r_0 FAILED. #Errors: %d\n", _errors_);
			}
		}
		{
			//Error Counter
			int _errors_ = 0;
			#define S0(j) if (fabsf(1.0f - var_A_c_verify(j)/var_A_c(j)) > EPSILON) _errors_++;
			int c1;
			for(c1=0;c1 <= N-1;c1+=1)
			 {
			 	S0((c1));
			 }
			#undef S0
			if(_errors_ == 0){
				printf("TEST for A_c PASSED\n");
			}else{
				printf("TEST for A_c FAILED. #Errors: %d\n", _errors_);
			}
		}
		{
			//Error Counter
			int _errors_ = 0;
			#define S0(i) if (fabsf(1.0f - var_A_r_verify(i)/var_A_r(i)) > EPSILON) _errors_++;
			int c1;
			for(c1=0;c1 <= N-1;c1+=1)
			 {
			 	S0((c1));
			 }
			#undef S0
			if(_errors_ == 0){
				printf("TEST for A_r PASSED\n");
			}else{
				printf("TEST for A_r FAILED. #Errors: %d\n", _errors_);
			}
		}
		{
			//Error Counter
			int _errors_ = 0;
			#define S0(i,j) if (fabsf(1.0f - var_A_f_verify(i,j)/var_A_f(i,j)) > EPSILON) _errors_++;
			int c1,c2;
			for(c1=0;c1 <= N;c1+=1)
			 {
			 	for(c2=0;c2 <= N;c2+=1)
			 	 {
			 	 	S0((c1),(c2));
			 	 }
			 }
			#undef S0
			if(_errors_ == 0){
				printf("TEST for A_f PASSED\n");
			}else{
				printf("TEST for A_f FAILED. #Errors: %d\n", _errors_);
			}
		}
    #endif
    
	//Memory Free
	free(_lin_A);
	free(A);
	free(_lin_L);
	free(L);
	free(_lin_U);
	free(U);
	free(L_c_0);
	free(U_r_0);
	free(A_c);
	free(A_r);
	free(_lin_A_f);
	free(A_f);
	#ifdef VERIFY
		free(_lin_L_verify);
		free(L_verify);
		free(_lin_U_verify);
		free(U_verify);
		free(L_c_0_verify);
		free(U_r_0_verify);
		free(A_c_verify);
		free(A_r_verify);
		free(_lin_A_f_verify);
		free(A_f_verify);
	#endif
	
	return EXIT_SUCCESS;
}

//Memory Macros
#undef A
#undef L
#undef U
#undef L_c_0
#undef U_r_0
#undef A_c
#undef A_r
#undef A_f


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
