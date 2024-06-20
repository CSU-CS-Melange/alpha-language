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
static float** U;
static float* U_NR;
static float* L_NR;
static char* _flag_L;
static char* _flag_U;
static char* _flag_U_NR;
static char* _flag_L_NR;

// Memory Macros
#define A(i,j) A[i][j]
#define L(i,j) L[i][j]
#define U(i,j) U[i][j]
#define U_NR(i,j) U_NR[((-2 + i >= 0 && -1 - i + j >= 0 && -1 + N - j >= 0) ? ((((-2 * N + (-1 + 2 * N) * i - i*i) + 2 * j))/2) : (-i + j == 0 && -2 + i >= 0 && -1 + N - i >= 0) ? (((-2 * N + (1 + 2 * N) * i - i*i))/2) : (-1 + i == 0 && -1 + N - j >= 0 && -2 + j >= 0) ? ((-i + j)) : 0)]
#define L_NR(i,j) L_NR[((-3 + i >= 0 && -1 + N - i >= 0 && -2 + j >= 0 && -1 + i - j >= 0) ? ((((-3 * i + i*i) + 2 * j))/2) : (-1 + j == 0 && -3 + i >= 0 && -1 + N - i >= 0) ? (((2 - 3 * i + i*i))/2) : 0)]
#define _flag_L(i,j) _flag_L[((-2 + i >= 0 && -1 + N - i >= 0 && -1 + j >= 0 && -1 + i - j >= 0) ? ((((-i + i*i) + 2 * j))/2) : (j == 0 && -2 + i >= 0 && -1 + N - i >= 0) ? (((-i + i*i))/2) : 0)]
#define _flag_U(i,j) _flag_U[((-1 + i >= 0 && -1 - i + j >= 0 && -1 + N - j >= 0) ? (((((-1 + 2 * N) * i - i*i) + 2 * j))/2) : (-i + j == 0 && -1 + i >= 0 && -1 + N - i >= 0) ? ((((1 + 2 * N) * i - i*i))/2) : (i == 0 && -1 + N - j >= 0 && -1 + j >= 0) ? ((-i + j)) : 0)]
#define _flag_U_NR(i,j) _flag_U_NR[((-2 + i >= 0 && -1 - i + j >= 0 && -1 + N - j >= 0) ? ((((-2 * N + (-1 + 2 * N) * i - i*i) + 2 * j))/2) : (-i + j == 0 && -2 + i >= 0 && -1 + N - i >= 0) ? (((-2 * N + (1 + 2 * N) * i - i*i))/2) : (-1 + i == 0 && -1 + N - j >= 0 && -2 + j >= 0) ? ((-i + j)) : 0)]
#define _flag_L_NR(i,j) _flag_L_NR[((-3 + i >= 0 && -1 + N - i >= 0 && -2 + j >= 0 && -1 + i - j >= 0) ? ((((-3 * i + i*i) + 2 * j))/2) : (-1 + j == 0 && -3 + i >= 0 && -1 + N - i >= 0) ? (((2 - 3 * i + i*i))/2) : 0)]

// Function Declarations
static float eval_U(long i, long j);
static float eval_L(long i, long j);
static float reduce0(long N, long ip, long jp);
static float eval_U_NR(long i, long j);
static float reduce1(long N, long ip, long jp);
static float eval_L_NR(long i, long j);
void LUDecomposition(long _local_N, float** _local_A, float** _local_L, float** _local_U);

static float eval_U(long i, long j) {
	
	// Check the flags.
	if ((_flag_U(i,j)) == ('N')) {
		_flag_U(i,j) = 'I';
		U(i,j) = (((i) == (0)) && ((-1 + N) >= (0))) ? (A(i,j)) : ((A(i,j)) - (eval_U_NR(i,j)));
		_flag_U(i,j) = 'F';
	}
	else if ((_flag_U(i,j)) == ('I')) {
		printf("There is a self dependence on U at (%ld,%ld)\n",i,j);
		exit(-1);
	}
	
	return U(i,j);
}

static float eval_L(long i, long j) {
	
	// Check the flags.
	if ((_flag_L(i,j)) == ('N')) {
		_flag_L(i,j) = 'I';
		L(i,j) = (((j) == (0)) && ((-1 + N) >= (0))) ? ((A(i,j)) / (eval_U((j),(j)))) : (((A(i,j)) - (eval_L_NR(i,j))) / (eval_U((j),(j))));
		_flag_L(i,j) = 'F';
	}
	else if ((_flag_L(i,j)) == ('I')) {
		printf("There is a self dependence on L at (%ld,%ld)\n",i,j);
		exit(-1);
	}
	
	return L(i,j);
}

static float reduce0(long N, long ip, long jp) {
	float reduceVar;
	long k;
	
	reduceVar = 0.0f;
	#define RP0(i,j,k) (eval_L((i),(k))) * (eval_U((k),(j)))
	#define R0(i,j,k) reduceVar = (reduceVar) + (RP0((i),(j),(k)))
	for (k = 0; k < ip; k += 1) {
		R0(ip, jp, k);
	}
	#undef RP0
	#undef R0
	return reduceVar;
}

static float eval_U_NR(long i, long j) {
	
	// Check the flags.
	if ((_flag_U_NR(i,j)) == ('N')) {
		_flag_U_NR(i,j) = 'I';
		U_NR(i,j) = reduce0(N,i,j);
		_flag_U_NR(i,j) = 'F';
	}
	else if ((_flag_U_NR(i,j)) == ('I')) {
		printf("There is a self dependence on U_NR at (%ld,%ld)\n",i,j);
		exit(-1);
	}
	
	return U_NR(i,j);
}

static float reduce1(long N, long ip, long jp) {
	float reduceVar;
	long k;
	
	reduceVar = 0.0f;
	#define RP1(i,j,k) (eval_L((i),(-1 + k))) * (eval_U((k),(j)))
	#define R1(i,j,k) reduceVar = (reduceVar) + (RP1((i),(j),(k)))
	for (k = 1; k <= jp; k += 1) {
		R1(ip, jp, k);
	}
	#undef RP1
	#undef R1
	return reduceVar;
}

static float eval_L_NR(long i, long j) {
	
	// Check the flags.
	if ((_flag_L_NR(i,j)) == ('N')) {
		_flag_L_NR(i,j) = 'I';
		L_NR(i,j) = reduce1(N,i,j);
		_flag_L_NR(i,j) = 'F';
	}
	else if ((_flag_L_NR(i,j)) == ('I')) {
		printf("There is a self dependence on L_NR at (%ld,%ld)\n",i,j);
		exit(-1);
	}
	
	return L_NR(i,j);
}

void LUDecomposition(long _local_N, float** _local_A, float** _local_L, float** _local_U) {
	long i;
	long j;
	
	// Copy arguments to the global variables.
	N = _local_N;
	A = _local_A;
	L = _local_L;
	U = _local_U;
	
	// Check parameter validity.
	if (!((-1 + N) >= (0))) {
		printf("The value of the parameters are invalid.\n");
		exit(-1);
	}
	
	// Allocate memory for local storage.
	U_NR = (float*)(malloc((sizeof(float)) * (((-2 + N >= 0) ? (((-N + N*N))/2) : 0))));
	mallocCheck(U_NR,"U_NR");
	L_NR = (float*)(malloc((sizeof(float)) * (((-3 + N >= 0) ? (((2 - 3 * N + N*N))/2) : 0))));
	mallocCheck(L_NR,"L_NR");
	
	// Allocate and initialize flag variables.
	_flag_L = (char*)(malloc((sizeof(char)) * (((-2 + N >= 0) ? (((-N + N*N))/2) : 0))));
	mallocCheck(_flag_L,"_flag_L");
	memset(_flag_L,'N',((-2 + N >= 0) ? (((-N + N*N))/2) : 0));
	_flag_U = (char*)(malloc((sizeof(char)) * (((-1 + N >= 0) ? (((N + N*N))/2) : 0))));
	mallocCheck(_flag_U,"_flag_U");
	memset(_flag_U,'N',((-1 + N >= 0) ? (((N + N*N))/2) : 0));
	_flag_U_NR = (char*)(malloc((sizeof(char)) * (((-2 + N >= 0) ? (((-N + N*N))/2) : 0))));
	mallocCheck(_flag_U_NR,"_flag_U_NR");
	memset(_flag_U_NR,'N',((-2 + N >= 0) ? (((-N + N*N))/2) : 0));
	_flag_L_NR = (char*)(malloc((sizeof(char)) * (((-3 + N >= 0) ? (((2 - 3 * N + N*N))/2) : 0))));
	mallocCheck(_flag_L_NR,"_flag_L_NR");
	memset(_flag_L_NR,'N',((-3 + N >= 0) ? (((2 - 3 * N + N*N))/2) : 0));
	
	// Evaluate all the outputs.
	#define S0(i,j) eval_L(i,j)
	for (i = 1; i < N; i += 1) {
		for (j = 0; j < i; j += 1) {
			S0(i, j);
		}
	}
	#undef S0
	#define S1(i,j) eval_U(i,j)
	for (i = 0; i < N; i += 1) {
		for (j = i; j < N; j += 1) {
			S1(i, j);
		}
	}
	#undef S1
	
	// Free all allocated memory.
	free(U_NR);
	free(L_NR);
	free(_flag_L);
	free(_flag_U);
	free(_flag_U_NR);
	free(_flag_L_NR);
}


// Undefine the Memory and Function Macros
#undef A
#undef L
#undef U
#undef U_NR
#undef L_NR
#undef _flag_L
#undef _flag_U
#undef _flag_U_NR
#undef _flag_L_NR
#undef ceild
#undef floord
#undef div
#undef max
#undef min
#undef mallocCheck
