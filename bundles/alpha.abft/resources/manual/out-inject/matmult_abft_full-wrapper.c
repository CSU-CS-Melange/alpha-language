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
#define B(i,j) B[i][j]
#define C(i,j) C[i][j]
#define Inv_C_i(i) Inv_C_i[i]
#define Inv_C_j(j) Inv_C_j[j]
#define C_C_i_0(i) C_C_i_0[i]
#define C_C_i_1(i) C_C_i_1[i]
#define C_C_j_0(j) C_C_j_0[j]
#define C_C_j_1(j) C_C_j_1[j]

#define C_verify(i,j) C_verify[i][j]
#define Inv_C_i_verify(i) Inv_C_i_verify[i]
#define Inv_C_j_verify(j) Inv_C_j_verify[j]
#define C_C_i_0_verify(i) C_C_i_0_verify[i]
#define C_C_i_1_verify(i) C_C_i_1_verify[i]
#define C_C_j_0_verify(j) C_C_j_0_verify[j]
#define C_C_j_1_verify(j) C_C_j_1_verify[j]
#define var_C(i,j) C(i,j)
#define var_C_verify(i,j) C_verify(i,j)
#define var_Inv_C_i(i) Inv_C_i(i)
#define var_Inv_C_i_verify(i) Inv_C_i_verify(i)
#define var_Inv_C_j(j) Inv_C_j(j)
#define var_Inv_C_j_verify(j) Inv_C_j_verify(j)
#define var_C_C_i_0(i) C_C_i_0(i)
#define var_C_C_i_0_verify(i) C_C_i_0_verify(i)
#define var_C_C_i_1(i) C_C_i_1(i)
#define var_C_C_i_1_verify(i) C_C_i_1_verify(i)
#define var_C_C_j_0(j) C_C_j_0(j)
#define var_C_C_j_0_verify(j) C_C_j_0_verify(j)
#define var_C_C_j_1(j) C_C_j_1(j)
#define var_C_C_j_1_verify(j) C_C_j_1_verify(j)

//function prototypes
void matmult_abft_full(long, float**, float**, float**, float*, float*, float*, float*, float*, float*);
void matmult_abft_full_verify(long, float**, float**, float**, float*, float*, float*, float*, float*, float*);

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
	float* _lin_B = (float*)malloc(sizeof(float)*((N) * (N)));
	mallocCheck(_lin_B, ((N) * (N)), float);
	float** B = (float**)malloc(sizeof(float*)*(N));
	mallocCheck(B, (N), float*);
	for (mz1=0;mz1 < N; mz1++) {
		B[mz1] = &_lin_B[(mz1*(N))];
	}
	float* _lin_C = (float*)malloc(sizeof(float)*((N) * (N)));
	mallocCheck(_lin_C, ((N) * (N)), float);
	float** C = (float**)malloc(sizeof(float*)*(N));
	mallocCheck(C, (N), float*);
	for (mz1=0;mz1 < N; mz1++) {
		C[mz1] = &_lin_C[(mz1*(N))];
	}
	float* Inv_C_i = (float*)malloc(sizeof(float)*(N));
	mallocCheck(Inv_C_i, (N), float);
	float* Inv_C_j = (float*)malloc(sizeof(float)*(N));
	mallocCheck(Inv_C_j, (N), float);
	float* C_C_i_0 = (float*)malloc(sizeof(float)*(N));
	mallocCheck(C_C_i_0, (N), float);
	float* C_C_i_1 = (float*)malloc(sizeof(float)*(N));
	mallocCheck(C_C_i_1, (N), float);
	float* C_C_j_0 = (float*)malloc(sizeof(float)*(N));
	mallocCheck(C_C_j_0, (N), float);
	float* C_C_j_1 = (float*)malloc(sizeof(float)*(N));
	mallocCheck(C_C_j_1, (N), float);
	#ifdef VERIFY
		float* _lin_C_verify = (float*)malloc(sizeof(float)*((N) * (N)));
		mallocCheck(_lin_C_verify, ((N) * (N)), float);
		float** C_verify = (float**)malloc(sizeof(float*)*(N));
		mallocCheck(C_verify, (N), float*);
		for (mz1=0;mz1 < N; mz1++) {
			C_verify[mz1] = &_lin_C_verify[(mz1*(N))];
		}
		float* Inv_C_i_verify = (float*)malloc(sizeof(float)*(N));
		mallocCheck(Inv_C_i_verify, (N), float);
		float* Inv_C_j_verify = (float*)malloc(sizeof(float)*(N));
		mallocCheck(Inv_C_j_verify, (N), float);
		float* C_C_i_0_verify = (float*)malloc(sizeof(float)*(N));
		mallocCheck(C_C_i_0_verify, (N), float);
		float* C_C_i_1_verify = (float*)malloc(sizeof(float)*(N));
		mallocCheck(C_C_i_1_verify, (N), float);
		float* C_C_j_0_verify = (float*)malloc(sizeof(float)*(N));
		mallocCheck(C_C_j_0_verify, (N), float);
		float* C_C_j_1_verify = (float*)malloc(sizeof(float)*(N));
		mallocCheck(C_C_j_1_verify, (N), float);
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
	{
		#if defined (RANDOM)
			#define S0(i,j) (B(i,j) = rand()) 
		#elif defined (CHECKING) || defined (VERIFY)
			#ifdef NO_PROMPT
				#define S0(i,j) scanf("%f", &B(i,j))
			#else
				#define S0(i,j) printf("B(%ld,%ld)=",(long) i,(long) j); scanf("%f", &B(i,j))
			#endif
		#else
			#define S0(i,j) (B(i,j) = 1)   //Default value
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
	
	matmult_abft_full(N, A, B, C, Inv_C_i, Inv_C_j, C_C_i_0, C_C_i_1, C_C_j_0, C_C_j_1);

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
    	matmult_abft_full_verify(N, A, B, C_verify, Inv_C_i_verify, Inv_C_j_verify, C_C_i_0_verify, C_C_i_1_verify, C_C_j_0_verify, C_C_j_1_verify);
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
				#define S0(i,j) printf("%0.2f\n",var_C(i,j))
			#else
				#define S0(i,j) printf("C(%ld,%ld)=",(long) i,(long) j);printf("%0.2f\n",var_C(i,j))
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
				#define S0(i) printf("%0.2f\n",var_Inv_C_i(i))
			#else
				#define S0(i) printf("Inv_C_i(%ld)=",(long) i);printf("%0.2f\n",var_Inv_C_i(i))
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
				#define S0(j) printf("%0.2f\n",var_Inv_C_j(j))
			#else
				#define S0(j) printf("Inv_C_j(%ld)=",(long) j);printf("%0.2f\n",var_Inv_C_j(j))
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
				#define S0(i) printf("%0.2f\n",var_C_C_i_0(i))
			#else
				#define S0(i) printf("C_C_i_0(%ld)=",(long) i);printf("%0.2f\n",var_C_C_i_0(i))
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
				#define S0(i) printf("%0.2f\n",var_C_C_i_1(i))
			#else
				#define S0(i) printf("C_C_i_1(%ld)=",(long) i);printf("%0.2f\n",var_C_C_i_1(i))
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
				#define S0(j) printf("%0.2f\n",var_C_C_j_0(j))
			#else
				#define S0(j) printf("C_C_j_0(%ld)=",(long) j);printf("%0.2f\n",var_C_C_j_0(j))
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
				#define S0(j) printf("%0.2f\n",var_C_C_j_1(j))
			#else
				#define S0(j) printf("C_C_j_1(%ld)=",(long) j);printf("%0.2f\n",var_C_C_j_1(j))
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
			#define S0(i,j) if (fabsf(1.0f - var_C_verify(i,j)/var_C(i,j)) > EPSILON) _errors_++;
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
				printf("TEST for C PASSED\n");
			}else{
				printf("TEST for C FAILED. #Errors: %d\n", _errors_);
			}
		}
		{
			//Error Counter
			int _errors_ = 0;
			#define S0(i) if (fabsf(1.0f - var_Inv_C_i_verify(i)/var_Inv_C_i(i)) > EPSILON) _errors_++;
			int c1;
			for(c1=0;c1 <= N-1;c1+=1)
			 {
			 	S0((c1));
			 }
			#undef S0
			if(_errors_ == 0){
				printf("TEST for Inv_C_i PASSED\n");
			}else{
				printf("TEST for Inv_C_i FAILED. #Errors: %d\n", _errors_);
			}
		}
		{
			//Error Counter
			int _errors_ = 0;
			#define S0(j) if (fabsf(1.0f - var_Inv_C_j_verify(j)/var_Inv_C_j(j)) > EPSILON) _errors_++;
			int c1;
			for(c1=0;c1 <= N-1;c1+=1)
			 {
			 	S0((c1));
			 }
			#undef S0
			if(_errors_ == 0){
				printf("TEST for Inv_C_j PASSED\n");
			}else{
				printf("TEST for Inv_C_j FAILED. #Errors: %d\n", _errors_);
			}
		}
		{
			//Error Counter
			int _errors_ = 0;
			#define S0(i) if (fabsf(1.0f - var_C_C_i_0_verify(i)/var_C_C_i_0(i)) > EPSILON) _errors_++;
			int c1;
			for(c1=0;c1 <= N-1;c1+=1)
			 {
			 	S0((c1));
			 }
			#undef S0
			if(_errors_ == 0){
				printf("TEST for C_C_i_0 PASSED\n");
			}else{
				printf("TEST for C_C_i_0 FAILED. #Errors: %d\n", _errors_);
			}
		}
		{
			//Error Counter
			int _errors_ = 0;
			#define S0(i) if (fabsf(1.0f - var_C_C_i_1_verify(i)/var_C_C_i_1(i)) > EPSILON) _errors_++;
			int c1;
			for(c1=0;c1 <= N-1;c1+=1)
			 {
			 	S0((c1));
			 }
			#undef S0
			if(_errors_ == 0){
				printf("TEST for C_C_i_1 PASSED\n");
			}else{
				printf("TEST for C_C_i_1 FAILED. #Errors: %d\n", _errors_);
			}
		}
		{
			//Error Counter
			int _errors_ = 0;
			#define S0(j) if (fabsf(1.0f - var_C_C_j_0_verify(j)/var_C_C_j_0(j)) > EPSILON) _errors_++;
			int c1;
			for(c1=0;c1 <= N-1;c1+=1)
			 {
			 	S0((c1));
			 }
			#undef S0
			if(_errors_ == 0){
				printf("TEST for C_C_j_0 PASSED\n");
			}else{
				printf("TEST for C_C_j_0 FAILED. #Errors: %d\n", _errors_);
			}
		}
		{
			//Error Counter
			int _errors_ = 0;
			#define S0(j) if (fabsf(1.0f - var_C_C_j_1_verify(j)/var_C_C_j_1(j)) > EPSILON) _errors_++;
			int c1;
			for(c1=0;c1 <= N-1;c1+=1)
			 {
			 	S0((c1));
			 }
			#undef S0
			if(_errors_ == 0){
				printf("TEST for C_C_j_1 PASSED\n");
			}else{
				printf("TEST for C_C_j_1 FAILED. #Errors: %d\n", _errors_);
			}
		}
    #endif
    
	//Memory Free
	free(_lin_A);
	free(A);
	free(_lin_B);
	free(B);
	free(_lin_C);
	free(C);
	free(Inv_C_i);
	free(Inv_C_j);
	free(C_C_i_0);
	free(C_C_i_1);
	free(C_C_j_0);
	free(C_C_j_1);
	#ifdef VERIFY
		free(_lin_C_verify);
		free(C_verify);
		free(Inv_C_i_verify);
		free(Inv_C_j_verify);
		free(C_C_i_0_verify);
		free(C_C_i_1_verify);
		free(C_C_j_0_verify);
		free(C_C_j_1_verify);
	#endif
	
	return EXIT_SUCCESS;
}

//Memory Macros
#undef A
#undef B
#undef C
#undef Inv_C_i
#undef Inv_C_j
#undef C_C_i_0
#undef C_C_i_1
#undef C_C_j_0
#undef C_C_j_1


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
