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
#define check_U_i_0(i) check_U_i_0[i]
#define check_U_i_1(i) check_U_i_1[i]
#define check_U_j_0(j) check_U_j_0[j]
#define check_U_j_1(j) check_U_j_1[j]
#define check_L_i_0(i) check_L_i_0[i]
#define check_L_i_1(i) check_L_i_1[i]
#define check_L_j_0(j) check_L_j_0[j]
#define check_L_j_1(j) check_L_j_1[j]

#define L_verify(i,j) L_verify[i][j]
#define U_verify(i,j) U_verify[i][j]
#define check_U_i_0_verify(i) check_U_i_0_verify[i]
#define check_U_i_1_verify(i) check_U_i_1_verify[i]
#define check_U_j_0_verify(j) check_U_j_0_verify[j]
#define check_U_j_1_verify(j) check_U_j_1_verify[j]
#define check_L_i_0_verify(i) check_L_i_0_verify[i]
#define check_L_i_1_verify(i) check_L_i_1_verify[i]
#define check_L_j_0_verify(j) check_L_j_0_verify[j]
#define check_L_j_1_verify(j) check_L_j_1_verify[j]
#define var_L(i,j) L(i,j)
#define var_L_verify(i,j) L_verify(i,j)
#define var_U(i,j) U(i,j)
#define var_U_verify(i,j) U_verify(i,j)
#define var_check_U_i_0(i) check_U_i_0(i)
#define var_check_U_i_0_verify(i) check_U_i_0_verify(i)
#define var_check_U_i_1(i) check_U_i_1(i)
#define var_check_U_i_1_verify(i) check_U_i_1_verify(i)
#define var_check_U_j_0(j) check_U_j_0(j)
#define var_check_U_j_0_verify(j) check_U_j_0_verify(j)
#define var_check_U_j_1(j) check_U_j_1(j)
#define var_check_U_j_1_verify(j) check_U_j_1_verify(j)
#define var_check_L_i_0(i) check_L_i_0(i)
#define var_check_L_i_0_verify(i) check_L_i_0_verify(i)
#define var_check_L_i_1(i) check_L_i_1(i)
#define var_check_L_i_1_verify(i) check_L_i_1_verify(i)
#define var_check_L_j_0(j) check_L_j_0(j)
#define var_check_L_j_0_verify(j) check_L_j_0_verify(j)
#define var_check_L_j_1(j) check_L_j_1(j)
#define var_check_L_j_1_verify(j) check_L_j_1_verify(j)

//function prototypes
void lud_aabft(long, float**, float**, float**, float*, float*, float*, float*, float*, float*, float*, float*);
void lud_aabft_verify(long, float**, float**, float**, float*, float*, float*, float*, float*, float*, float*, float*);

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
	float* check_U_i_0 = (float*)malloc(sizeof(float)*(N));
	mallocCheck(check_U_i_0, (N), float);
	float* check_U_i_1 = (float*)malloc(sizeof(float)*(N));
	mallocCheck(check_U_i_1, (N), float);
	float* check_U_j_0 = (float*)malloc(sizeof(float)*(N));
	mallocCheck(check_U_j_0, (N), float);
	float* check_U_j_1 = (float*)malloc(sizeof(float)*(N));
	mallocCheck(check_U_j_1, (N), float);
	float* check_L_i_0 = (float*)malloc(sizeof(float)*(N));
	mallocCheck(check_L_i_0, (N), float);
	float* check_L_i_1 = (float*)malloc(sizeof(float)*(N));
	mallocCheck(check_L_i_1, (N), float);
	float* check_L_j_0 = (float*)malloc(sizeof(float)*(N));
	mallocCheck(check_L_j_0, (N), float);
	float* check_L_j_1 = (float*)malloc(sizeof(float)*(N));
	mallocCheck(check_L_j_1, (N), float);
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
		float* check_U_i_0_verify = (float*)malloc(sizeof(float)*(N));
		mallocCheck(check_U_i_0_verify, (N), float);
		float* check_U_i_1_verify = (float*)malloc(sizeof(float)*(N));
		mallocCheck(check_U_i_1_verify, (N), float);
		float* check_U_j_0_verify = (float*)malloc(sizeof(float)*(N));
		mallocCheck(check_U_j_0_verify, (N), float);
		float* check_U_j_1_verify = (float*)malloc(sizeof(float)*(N));
		mallocCheck(check_U_j_1_verify, (N), float);
		float* check_L_i_0_verify = (float*)malloc(sizeof(float)*(N));
		mallocCheck(check_L_i_0_verify, (N), float);
		float* check_L_i_1_verify = (float*)malloc(sizeof(float)*(N));
		mallocCheck(check_L_i_1_verify, (N), float);
		float* check_L_j_0_verify = (float*)malloc(sizeof(float)*(N));
		mallocCheck(check_L_j_0_verify, (N), float);
		float* check_L_j_1_verify = (float*)malloc(sizeof(float)*(N));
		mallocCheck(check_L_j_1_verify, (N), float);
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
	
	lud_aabft(N, A, L, U, check_U_i_0, check_U_i_1, check_U_j_0, check_U_j_1, check_L_i_0, check_L_i_1, check_L_j_0, check_L_j_1);

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
    	lud_aabft_verify(N, A, L_verify, U_verify, check_U_i_0_verify, check_U_i_1_verify, check_U_j_0_verify, check_U_j_1_verify, check_L_i_0_verify, check_L_i_1_verify, check_L_j_0_verify, check_L_j_1_verify);
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
				#define S0(i) printf("%0.2f\n",var_check_U_i_0(i))
			#else
				#define S0(i) printf("check_U_i_0(%ld)=",(long) i);printf("%0.2f\n",var_check_U_i_0(i))
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
				#define S0(i) printf("%0.2f\n",var_check_U_i_1(i))
			#else
				#define S0(i) printf("check_U_i_1(%ld)=",(long) i);printf("%0.2f\n",var_check_U_i_1(i))
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
				#define S0(j) printf("%0.2f\n",var_check_U_j_0(j))
			#else
				#define S0(j) printf("check_U_j_0(%ld)=",(long) j);printf("%0.2f\n",var_check_U_j_0(j))
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
				#define S0(j) printf("%0.2f\n",var_check_U_j_1(j))
			#else
				#define S0(j) printf("check_U_j_1(%ld)=",(long) j);printf("%0.2f\n",var_check_U_j_1(j))
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
				#define S0(i) printf("%0.2f\n",var_check_L_i_0(i))
			#else
				#define S0(i) printf("check_L_i_0(%ld)=",(long) i);printf("%0.2f\n",var_check_L_i_0(i))
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
				#define S0(i) printf("%0.2f\n",var_check_L_i_1(i))
			#else
				#define S0(i) printf("check_L_i_1(%ld)=",(long) i);printf("%0.2f\n",var_check_L_i_1(i))
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
				#define S0(j) printf("%0.2f\n",var_check_L_j_0(j))
			#else
				#define S0(j) printf("check_L_j_0(%ld)=",(long) j);printf("%0.2f\n",var_check_L_j_0(j))
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
				#define S0(j) printf("%0.2f\n",var_check_L_j_1(j))
			#else
				#define S0(j) printf("check_L_j_1(%ld)=",(long) j);printf("%0.2f\n",var_check_L_j_1(j))
			#endif
			int c1;
			for(c1=0;c1 <= N-1;c1+=1)
			 {
			 	S0((c1));
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
			#define S0(i) if (fabsf(1.0f - var_check_U_i_0_verify(i)/var_check_U_i_0(i)) > EPSILON) _errors_++;
			int c1;
			for(c1=0;c1 <= N-1;c1+=1)
			 {
			 	S0((c1));
			 }
			#undef S0
			if(_errors_ == 0){
				printf("TEST for check_U_i_0 PASSED\n");
			}else{
				printf("TEST for check_U_i_0 FAILED. #Errors: %d\n", _errors_);
			}
		}
		{
			//Error Counter
			int _errors_ = 0;
			#define S0(i) if (fabsf(1.0f - var_check_U_i_1_verify(i)/var_check_U_i_1(i)) > EPSILON) _errors_++;
			int c1;
			for(c1=0;c1 <= N-1;c1+=1)
			 {
			 	S0((c1));
			 }
			#undef S0
			if(_errors_ == 0){
				printf("TEST for check_U_i_1 PASSED\n");
			}else{
				printf("TEST for check_U_i_1 FAILED. #Errors: %d\n", _errors_);
			}
		}
		{
			//Error Counter
			int _errors_ = 0;
			#define S0(j) if (fabsf(1.0f - var_check_U_j_0_verify(j)/var_check_U_j_0(j)) > EPSILON) _errors_++;
			int c1;
			for(c1=0;c1 <= N-1;c1+=1)
			 {
			 	S0((c1));
			 }
			#undef S0
			if(_errors_ == 0){
				printf("TEST for check_U_j_0 PASSED\n");
			}else{
				printf("TEST for check_U_j_0 FAILED. #Errors: %d\n", _errors_);
			}
		}
		{
			//Error Counter
			int _errors_ = 0;
			#define S0(j) if (fabsf(1.0f - var_check_U_j_1_verify(j)/var_check_U_j_1(j)) > EPSILON) _errors_++;
			int c1;
			for(c1=0;c1 <= N-1;c1+=1)
			 {
			 	S0((c1));
			 }
			#undef S0
			if(_errors_ == 0){
				printf("TEST for check_U_j_1 PASSED\n");
			}else{
				printf("TEST for check_U_j_1 FAILED. #Errors: %d\n", _errors_);
			}
		}
		{
			//Error Counter
			int _errors_ = 0;
			#define S0(i) if (fabsf(1.0f - var_check_L_i_0_verify(i)/var_check_L_i_0(i)) > EPSILON) _errors_++;
			int c1;
			for(c1=0;c1 <= N-1;c1+=1)
			 {
			 	S0((c1));
			 }
			#undef S0
			if(_errors_ == 0){
				printf("TEST for check_L_i_0 PASSED\n");
			}else{
				printf("TEST for check_L_i_0 FAILED. #Errors: %d\n", _errors_);
			}
		}
		{
			//Error Counter
			int _errors_ = 0;
			#define S0(i) if (fabsf(1.0f - var_check_L_i_1_verify(i)/var_check_L_i_1(i)) > EPSILON) _errors_++;
			int c1;
			for(c1=0;c1 <= N-1;c1+=1)
			 {
			 	S0((c1));
			 }
			#undef S0
			if(_errors_ == 0){
				printf("TEST for check_L_i_1 PASSED\n");
			}else{
				printf("TEST for check_L_i_1 FAILED. #Errors: %d\n", _errors_);
			}
		}
		{
			//Error Counter
			int _errors_ = 0;
			#define S0(j) if (fabsf(1.0f - var_check_L_j_0_verify(j)/var_check_L_j_0(j)) > EPSILON) _errors_++;
			int c1;
			for(c1=0;c1 <= N-1;c1+=1)
			 {
			 	S0((c1));
			 }
			#undef S0
			if(_errors_ == 0){
				printf("TEST for check_L_j_0 PASSED\n");
			}else{
				printf("TEST for check_L_j_0 FAILED. #Errors: %d\n", _errors_);
			}
		}
		{
			//Error Counter
			int _errors_ = 0;
			#define S0(j) if (fabsf(1.0f - var_check_L_j_1_verify(j)/var_check_L_j_1(j)) > EPSILON) _errors_++;
			int c1;
			for(c1=0;c1 <= N-1;c1+=1)
			 {
			 	S0((c1));
			 }
			#undef S0
			if(_errors_ == 0){
				printf("TEST for check_L_j_1 PASSED\n");
			}else{
				printf("TEST for check_L_j_1 FAILED. #Errors: %d\n", _errors_);
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
	free(check_U_i_0);
	free(check_U_i_1);
	free(check_U_j_0);
	free(check_U_j_1);
	free(check_L_i_0);
	free(check_L_i_1);
	free(check_L_j_0);
	free(check_L_j_1);
	#ifdef VERIFY
		free(_lin_L_verify);
		free(L_verify);
		free(_lin_U_verify);
		free(U_verify);
		free(check_U_i_0_verify);
		free(check_U_i_1_verify);
		free(check_U_j_0_verify);
		free(check_U_j_1_verify);
		free(check_L_i_0_verify);
		free(check_L_i_1_verify);
		free(check_L_j_0_verify);
		free(check_L_j_1_verify);
	#endif
	
	return EXIT_SUCCESS;
}

//Memory Macros
#undef A
#undef L
#undef U
#undef check_U_i_0
#undef check_U_i_1
#undef check_U_j_0
#undef check_U_j_1
#undef check_L_i_0
#undef check_L_i_1
#undef check_L_j_0
#undef check_L_j_1


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
