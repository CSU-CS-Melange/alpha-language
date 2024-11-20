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
#define U(i,j) U[i][j]
#define b(i) b[i]
#define b_1(i) b_1[i]
#define U_c(j) U_c[j]
#define x(i) x[i]
#define Inv_b_c(i) Inv_b_c[i]

#define x_verify(i) x_verify[i]
#define Inv_b_c_verify(i) Inv_b_c_verify[i]
#define var_x(i) x(i)
#define var_x_verify(i) x_verify(i)
#define var_Inv_b_c(i) Inv_b_c(i)
#define var_Inv_b_c_verify(i) Inv_b_c_verify(i)

//function prototypes
void bsub_abft(long, float**, float*, float*, float*);
void bsub_abft_verify(long, float**, float*, float*, float*);

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
	float* _lin_U = (float*)malloc(sizeof(float)*((N) * (N)));
	mallocCheck(_lin_U, ((N) * (N)), float);
	float** U = (float**)malloc(sizeof(float*)*(N));
	mallocCheck(U, (N), float*);
	for (mz1=0;mz1 < N; mz1++) {
		U[mz1] = &_lin_U[(mz1*(N))];
	}
	float* b = (float*)malloc(sizeof(float)*(N));
	mallocCheck(b, (N), float);
	float* x = (float*)malloc(sizeof(float)*(N));
	mallocCheck(x, (N), float);
	float* Inv_b_c = (float*)malloc(sizeof(float)*(N));
	mallocCheck(Inv_b_c, (N), float);
	#ifdef VERIFY
		float* x_verify = (float*)malloc(sizeof(float)*(N));
		mallocCheck(x_verify, (N), float);
		float* Inv_b_c_verify = (float*)malloc(sizeof(float)*(N));
		mallocCheck(Inv_b_c_verify, (N), float);
	#endif

	//Initialization of rand
	srand((unsigned)time(NULL));
	 
	//Input Initialization
	{
		#if defined (RANDOM)
			#define S0(i,j) (U(i,j) = rand()) 
		#elif defined (CHECKING) || defined (VERIFY)
			#ifdef NO_PROMPT
				#define S0(i,j) scanf("%f", &U(i,j))
			#else
				#define S0(i,j) printf("U(%ld,%ld)=",(long) i,(long) j); scanf("%f", &U(i,j))
			#endif
		#else
			#define S0(i,j) (U(i,j) = 1)   //Default value
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
		#if defined (RANDOM)
			#define S0(i) (b(i) = rand()) 
		#elif defined (CHECKING) || defined (VERIFY)
			#ifdef NO_PROMPT
				#define S0(i) scanf("%f", &b(i))
			#else
				#define S0(i) printf("b(%ld)=",(long) i); scanf("%f", &b(i))
			#endif
		#else
			#define S0(i) (b(i) = 1)   //Default value
		#endif
		
		
		int c1;
		for(c1=0;c1 <= N-1;c1+=1)
		 {
		 	S0((c1));
		 }
		#undef S0
	}
	
	//Timing
	struct timeval time;
	double elapsed_time;
	
	//Call the main computation
	gettimeofday(&time, NULL);
	elapsed_time = (((double) time.tv_sec) + ((double) time.tv_usec)/1000000);
	
	bsub_abft(N, U, b, x, Inv_b_c);

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
    	bsub_abft_verify(N, U, b, x_verify, Inv_b_c_verify);
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
				#define S0(i) printf("%0.2f\n",var_x(i))
			#else
				#define S0(i) printf("x(%ld)=",(long) i);printf("%0.2f\n",var_x(i))
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
				#define S0(i) printf("%0.2f\n",var_Inv_b_c(i))
			#else
				#define S0(i) printf("Inv_b_c(%ld)=",(long) i);printf("%0.2f\n",var_Inv_b_c(i))
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
			#define S0(i) if (fabsf(1.0f - var_x_verify(i)/var_x(i)) > EPSILON) _errors_++;
			int c1;
			for(c1=0;c1 <= N-1;c1+=1)
			 {
			 	S0((c1));
			 }
			#undef S0
			if(_errors_ == 0){
				printf("TEST for x PASSED\n");
			}else{
				printf("TEST for x FAILED. #Errors: %d\n", _errors_);
			}
		}
		{
			//Error Counter
			int _errors_ = 0;
			#define S0(i) if (fabsf(1.0f - var_Inv_b_c_verify(i)/var_Inv_b_c(i)) > EPSILON) _errors_++;
			int c1;
			for(c1=0;c1 <= N-1;c1+=1)
			 {
			 	S0((c1));
			 }
			#undef S0
			if(_errors_ == 0){
				printf("TEST for Inv_b_c PASSED\n");
			}else{
				printf("TEST for Inv_b_c FAILED. #Errors: %d\n", _errors_);
			}
		}
    #endif
    
	//Memory Free
	free(_lin_U);
	free(U);
	free(b);
	free(x);
	free(Inv_b_c);
	#ifdef VERIFY
		free(x_verify);
		free(Inv_b_c_verify);
	#endif
	
	return EXIT_SUCCESS;
}

//Memory Macros
#undef U
#undef b
#undef b_1
#undef U_c
#undef x
#undef Inv_b_c


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
