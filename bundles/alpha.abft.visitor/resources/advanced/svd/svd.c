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
static float** M;
static float** U;
static float** S;
static float** V_T;
static float** MM_T;
static float* M_T;
static char* _flag_U;
static char* _flag_S;
static char* _flag_V_T;
static char* _flag_MM_T;
static char* _flag_M_T;

// Memory Macros
#define M(i,j) M[i][j]
#define U(i,j) U[i][j]
#define S(i,j) S[i][j]
#define V_T(i,j) V_T[i][j]
#define MM_T(i,j) MM_T[i][j]
#define M_T(i,j) M_T[((-1 + i >= 0 && -1 + N - i >= 0 && -1 + j >= 0 && -1 + N - j >= 0) ? ((N * i + j)) : (j == 0 && -1 + i >= 0 && -1 + N - i >= 0 && -1 + N >= 0) ? (N * i) : (i == 0 && -1 + N - j >= 0 && -1 + N >= 0 && -1 + j >= 0) ? (j) : 0)]
#define _flag_U(i,j) _flag_U[((-1 + i >= 0 && -1 + N - i >= 0 && -1 + j >= 0 && -1 + N - j >= 0) ? ((N * i + j)) : (j == 0 && -1 + i >= 0 && -1 + N - i >= 0 && -1 + N >= 0) ? (N * i) : (i == 0 && -1 + N - j >= 0 && -1 + N >= 0 && -1 + j >= 0) ? (j) : 0)]
#define _flag_S(i,j) _flag_S[((-1 + i >= 0 && -1 + N - i >= 0 && -1 + j >= 0 && -1 + N - j >= 0) ? ((N * i + j)) : (j == 0 && -1 + i >= 0 && -1 + N - i >= 0 && -1 + N >= 0) ? (N * i) : (i == 0 && -1 + N - j >= 0 && -1 + N >= 0 && -1 + j >= 0) ? (j) : 0)]
#define _flag_V_T(i,j) _flag_V_T[((-1 + i >= 0 && -1 + N - i >= 0 && -1 + j >= 0 && -1 + N - j >= 0) ? ((N * i + j)) : (j == 0 && -1 + i >= 0 && -1 + N - i >= 0 && -1 + N >= 0) ? (N * i) : (i == 0 && -1 + N - j >= 0 && -1 + N >= 0 && -1 + j >= 0) ? (j) : 0)]
#define _flag_MM_T(i,j) _flag_MM_T[((-1 + i >= 0 && -1 + N - i >= 0 && -1 + j >= 0 && -1 + N - j >= 0) ? ((N * i + j)) : (j == 0 && -1 + i >= 0 && -1 + N - i >= 0 && -1 + N >= 0) ? (N * i) : (i == 0 && -1 + N - j >= 0 && -1 + N >= 0 && -1 + j >= 0) ? (j) : 0)]
#define _flag_M_T(i,j) _flag_M_T[((-1 + i >= 0 && -1 + N - i >= 0 && -1 + j >= 0 && -1 + N - j >= 0) ? ((N * i + j)) : (j == 0 && -1 + i >= 0 && -1 + N - i >= 0 && -1 + N >= 0) ? (N * i) : (i == 0 && -1 + N - j >= 0 && -1 + N >= 0 && -1 + j >= 0) ? (j) : 0)]

// Function Declarations
static float eval_U(long i, long j);
static float eval_S(long i, long j);
static float eval_V_T(long i, long j);
static float eval_M_T(long i, long j);
static float reduce0(long N, long ip, long jp);
static float eval_MM_T(long i, long j);
void svd(long _local_N, float** _local_M, float** _local_U, float** _local_S, float** _local_V_T, float** _local_MM_T);

static float eval_U(long i, long j) {
	
	// Check the flags.
	if ((_flag_U(i,j)) == ('N')) {
		_flag_U(i,j) = 'I';
		U(i,j) = M(((i)),((j)));
		_flag_U(i,j) = 'F';
	}
	else if ((_flag_U(i,j)) == ('I')) {
		printf("There is a self dependence on U at (%ld,%ld)\n",i,j);
		exit(-1);
	}
	
	return U(i,j);
}

static float eval_S(long i, long j) {
	
	// Check the flags.
	if ((_flag_S(i,j)) == ('N')) {
		_flag_S(i,j) = 'I';
		S(i,j) = M(((i)),((j)));
		_flag_S(i,j) = 'F';
	}
	else if ((_flag_S(i,j)) == ('I')) {
		printf("There is a self dependence on S at (%ld,%ld)\n",i,j);
		exit(-1);
	}
	
	return S(i,j);
}

static float eval_V_T(long i, long j) {
	
	// Check the flags.
	if ((_flag_V_T(i,j)) == ('N')) {
		_flag_V_T(i,j) = 'I';
		V_T(i,j) = M(((i)),((j)));
		_flag_V_T(i,j) = 'F';
	}
	else if ((_flag_V_T(i,j)) == ('I')) {
		printf("There is a self dependence on V_T at (%ld,%ld)\n",i,j);
		exit(-1);
	}
	
	return V_T(i,j);
}

static float eval_M_T(long i, long j) {
	
	// Check the flags.
	if ((_flag_M_T(i,j)) == ('N')) {
		_flag_M_T(i,j) = 'I';
		M_T(i,j) = M(((j)),((i)));
		_flag_M_T(i,j) = 'F';
	}
	else if ((_flag_M_T(i,j)) == ('I')) {
		printf("There is a self dependence on M_T at (%ld,%ld)\n",i,j);
		exit(-1);
	}
	
	return M_T(i,j);
}

static float reduce0(long N, long ip, long jp) {
	float reduceVar;
	long k;
	
	reduceVar = 0.0f;
	#define RP0(i,j,k) (M(((i)),((k)))) * (eval_M_T(((k)),((j))))
	#define R0(i,j,k) reduceVar = (reduceVar) + (RP0((i),(j),(k)))
	for (k = 0; k < N; k += 1) {
		R0(ip, jp, k);
	}
	#undef RP0
	#undef R0
	return reduceVar;
}

static float eval_MM_T(long i, long j) {
	
	// Check the flags.
	if ((_flag_MM_T(i,j)) == ('N')) {
		_flag_MM_T(i,j) = 'I';
		MM_T(i,j) = reduce0(N,i,j);
		_flag_MM_T(i,j) = 'F';
	}
	else if ((_flag_MM_T(i,j)) == ('I')) {
		printf("There is a self dependence on MM_T at (%ld,%ld)\n",i,j);
		exit(-1);
	}
	
	return MM_T(i,j);
}

void svd(long _local_N, float** _local_M, float** _local_U, float** _local_S, float** _local_V_T, float** _local_MM_T) {
	long i;
	long j;
	
	// Copy arguments to the global variables.
	N = _local_N;
	M = _local_M;
	U = _local_U;
	S = _local_S;
	V_T = _local_V_T;
	MM_T = _local_MM_T;
	
	// Check parameter validity.
	if (!((-1 + N) >= (0))) {
		printf("The value of the parameters are invalid.\n");
		exit(-1);
	}
	
	// Allocate memory for local storage.
	M_T = (float*)(malloc((sizeof(float)) * (((-1 + N >= 0) ? (N*N) : 0))));
	mallocCheck(M_T,"M_T");
	
	// Allocate and initialize flag variables.
	_flag_U = (char*)(malloc((sizeof(char)) * (((-1 + N >= 0) ? (N*N) : 0))));
	mallocCheck(_flag_U,"_flag_U");
	memset(_flag_U,'N',((-1 + N >= 0) ? (N*N) : 0));
	_flag_S = (char*)(malloc((sizeof(char)) * (((-1 + N >= 0) ? (N*N) : 0))));
	mallocCheck(_flag_S,"_flag_S");
	memset(_flag_S,'N',((-1 + N >= 0) ? (N*N) : 0));
	_flag_V_T = (char*)(malloc((sizeof(char)) * (((-1 + N >= 0) ? (N*N) : 0))));
	mallocCheck(_flag_V_T,"_flag_V_T");
	memset(_flag_V_T,'N',((-1 + N >= 0) ? (N*N) : 0));
	_flag_MM_T = (char*)(malloc((sizeof(char)) * (((-1 + N >= 0) ? (N*N) : 0))));
	mallocCheck(_flag_MM_T,"_flag_MM_T");
	memset(_flag_MM_T,'N',((-1 + N >= 0) ? (N*N) : 0));
	_flag_M_T = (char*)(malloc((sizeof(char)) * (((-1 + N >= 0) ? (N*N) : 0))));
	mallocCheck(_flag_M_T,"_flag_M_T");
	memset(_flag_M_T,'N',((-1 + N >= 0) ? (N*N) : 0));
	
	// Evaluate all the outputs.
	#define S0(i,j) eval_U(i,j)
	for (i = 0; i < N; i += 1) {
		for (j = 0; j < N; j += 1) {
			S0(i, j);
		}
	}
	#undef S0
	#define S1(i,j) eval_S(i,j)
	for (i = 0; i < N; i += 1) {
		for (j = 0; j < N; j += 1) {
			S1(i, j);
		}
	}
	#undef S1
	#define S2(i,j) eval_V_T(i,j)
	for (i = 0; i < N; i += 1) {
		for (j = 0; j < N; j += 1) {
			S2(i, j);
		}
	}
	#undef S2
	#define S3(i,j) eval_MM_T(i,j)
	for (i = 0; i < N; i += 1) {
		for (j = 0; j < N; j += 1) {
			S3(i, j);
		}
	}
	#undef S3
	
	// Free all allocated memory.
	free(M_T);
	free(_flag_U);
	free(_flag_S);
	free(_flag_V_T);
	free(_flag_MM_T);
	free(_flag_M_T);
}


// Undefine the Memory and Function Macros
#undef M
#undef U
#undef S
#undef V_T
#undef MM_T
#undef M_T
#undef _flag_U
#undef _flag_S
#undef _flag_V_T
#undef _flag_MM_T
#undef _flag_M_T
#undef ceild
#undef floord
#undef div
#undef max
#undef min
#undef mallocCheck
