// This code was auto-generated with AlphaZ.

#include <float.h>
#include <limits.h>
#include <math.h>
#include <stdbool.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>

// Function Macros
#define ceild(n,d) ((int)ceil(((double)(n))/((double)(d))))
#define floord(n,d) ((int)floor(((double)(n))/((double)(d))))
#define div(a,b) (ceild((a),(b)))
#define max(a,b) (((a)>(b))?(a):(b))
#define min(a,b) (((a)<(b))?(a):(b))
#define mallocCheck(v,s) if ((v) == NULL) { printf("Failed to allocate memory for variable: %s\n", (s)); exit(-1); }

// Global Variables
static long N;
static float** A;
static float** L;
static float** L_T;
static float* x;
static char* _flag_L;
static char* _flag_L_T;
static char* _flag_x;

// Memory Macros
#define A(i,j) A[i][j]
#define L(i,j) L[i][j]
#define L_T(i,j) L_T[i][j]
#define x() x[0]
#define _flag_L(i,j) _flag_L[((-1 + i >= 0 && -1 + N - i >= 0 && -1 + j >= 0 && i - j >= 0) ? ((((i + i*i) + 2 * j))/2) : (j == 0 && -1 + i >= 0 && -1 + N - i >= 0) ? (((i + i*i))/2) : 0)]
#define _flag_L_T(i,j) _flag_L_T[((-1 + i >= 0 && -1 - i + j >= 0 && -1 + N - j >= 0) ? (((((-1 + 2 * N) * i - i*i) + 2 * j))/2) : (-i + j == 0 && -1 + i >= 0 && -1 + N - i >= 0) ? ((((1 + 2 * N) * i - i*i))/2) : (i == 0 && -1 + N - j >= 0 && -1 + j >= 0) ? ((-i + j)) : 0)]
#define _flag_x() _flag_x[(0)]

// Function Declarations
static float eval_L(long i0, long i1);
static float eval_L_T(long i, long j);
static float eval_x();
void cholesky_banachiewicz(long _local_N, float** _local_A, float** _local_L, float** _local_L_T, float* _local_x);

static float eval_L(long i0, long i1) {
	
	// Check the flags.
	if ((_flag_L(i0,i1)) == ('N')) {
		_flag_L(i0,i1) = 'I';
		L(i0,i1) = A(i0,i1);
		_flag_L(i0,i1) = 'F';
	}
	else if ((_flag_L(i0,i1)) == ('I')) {
		printf("There is a self dependence on L at (%ld,%ld)\n",i0,i1);
		exit(-1);
	}
	
	return L(i0,i1);
}

static float eval_L_T(long i, long j) {
	
	// Check the flags.
	if ((_flag_L_T(i,j)) == ('N')) {
		_flag_L_T(i,j) = 'I';
		L_T(i,j) = eval_L(((j)),((i)));
		_flag_L_T(i,j) = 'F';
	}
	else if ((_flag_L_T(i,j)) == ('I')) {
		printf("There is a self dependence on L_T at (%ld,%ld)\n",i,j);
		exit(-1);
	}
	
	return L_T(i,j);
}

static float eval_x() {
	
	// Check the flags.
	if ((_flag_x()) == ('N')) {
		_flag_x() = 'I';
		x() = sqrt(9);
		_flag_x() = 'F';
	}
	else if ((_flag_x()) == ('I')) {
		printf("There is a self dependence on x at ()\n");
		exit(-1);
	}
	
	return x();
}

void cholesky_banachiewicz(long _local_N, float** _local_A, float** _local_L, float** _local_L_T, float* _local_x) {
	long i;
	long j;
	
	// Copy arguments to the global variables.
	N = _local_N;
	A = _local_A;
	L = _local_L;
	L_T = _local_L_T;
	x = _local_x;
	
	// Check parameter validity.
	if (!((-1 + N) >= (0))) {
		printf("The value of the parameters are invalid.\n");
		exit(-1);
	}
	
	// Allocate memory for local storage.
	
	// Allocate and initialize flag variables.
	_flag_L = (char*)(malloc((sizeof(char)) * (((-1 + N >= 0) ? (((N + N*N))/2) : 0))));
	mallocCheck(_flag_L,"_flag_L");
	memset(_flag_L,'N',((-1 + N >= 0) ? (((N + N*N))/2) : 0));
	_flag_L_T = (char*)(malloc((sizeof(char)) * (((-1 + N >= 0) ? (((N + N*N))/2) : 0))));
	mallocCheck(_flag_L_T,"_flag_L_T");
	memset(_flag_L_T,'N',((-1 + N >= 0) ? (((N + N*N))/2) : 0));
	_flag_x = (char*)(malloc((sizeof(char)) * (((-1 + N >= 0) ? (1) : 0))));
	mallocCheck(_flag_x,"_flag_x");
	memset(_flag_x,'N',((-1 + N >= 0) ? (1) : 0));
	
	// Evaluate all the outputs.
	#define S0(i,j) eval_L(i,j)
	for (i = 0; i < N; i += 1) {
		for (j = 0; j <= i; j += 1) {
			S0(i, j);
		}
	}
	#undef S0
	#define S1(i,j) eval_L_T(i,j)
	for (i = 0; i < N; i += 1) {
		for (j = i; j < N; j += 1) {
			S1(i, j);
		}
	}
	#undef S1
	#define S2() eval_x()
	S2();
	#undef S2
	
	// Free all allocated memory.
	free(_flag_L);
	free(_flag_L_T);
	free(_flag_x);
}


// Undefine the Memory and Function Macros
#undef A
#undef L
#undef L_T
#undef x
#undef _flag_L
#undef _flag_L_T
#undef _flag_x
#undef ceild
#undef floord
#undef div
#undef max
#undef min
#undef mallocCheck
