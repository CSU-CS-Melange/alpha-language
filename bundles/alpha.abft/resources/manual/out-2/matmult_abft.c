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
static float** B;
static float** C;
static float* Inv_C_i;
static float* C_C_i_0;
static float* C_C_i_1;
static char* _flag_C;
static char* _flag_Inv_C_i;
static char* _flag_C_C_i_0;
static char* _flag_C_C_i_1;

// Memory Macros
#define A(i,j) A[i][j]
#define B(i,j) B[i][j]
#define C(i,j) C[i][j]
#define Inv_C_i(i) Inv_C_i[i]
#define C_C_i_0(i) C_C_i_0[((-1 + N - i >= 0 && -1 + i >= 0) ? (i) : 0)]
#define C_C_i_1(i) C_C_i_1[((-1 + N - i >= 0 && -1 + i >= 0) ? (i) : 0)]
#define _flag_C(i,j) _flag_C[((-1 + i >= 0 && -1 + N - i >= 0 && -1 + j >= 0 && -1 + N - j >= 0) ? ((N * i + j)) : (j == 0 && -1 + i >= 0 && -1 + N - i >= 0 && -1 + N >= 0) ? (N * i) : (i == 0 && -1 + N - j >= 0 && -1 + N >= 0 && -1 + j >= 0) ? (j) : 0)]
#define _flag_Inv_C_i(i) _flag_Inv_C_i[((-1 + N - i >= 0 && -1 + i >= 0) ? (i) : 0)]
#define _flag_C_C_i_0(i) _flag_C_C_i_0[((-1 + N - i >= 0 && -1 + i >= 0) ? (i) : 0)]
#define _flag_C_C_i_1(i) _flag_C_C_i_1[((-1 + N - i >= 0 && -1 + i >= 0) ? (i) : 0)]

// Function Declarations
static float reduce0(long N, long ip, long jp);
static float eval_C(long i, long j);
static float reduce1(long N, long i);
static float eval_C_C_i_0(long i);
static float reduce2(long N, long ip);
static float eval_C_C_i_1(long i);
static float eval_Inv_C_i(long i);
void matmult_abft(long _local_N, float** _local_A, float** _local_B, float** _local_C, float* _local_Inv_C_i);

static float reduce0(long N, long ip, long jp) {
	float reduceVar;
	long k;
	
	reduceVar = 0.0f;
	#define RP0(i,j,k) (A(((i)),((k)))) * (B(((k)),((j))))
	#define R0(i,j,k) reduceVar = (reduceVar) + (RP0((i),(j),(k)))
	for (k = 0; k < N; k += 1) {
		R0(ip, jp, k);
	}
	#undef RP0
	#undef R0
	return reduceVar;
}

static float eval_C(long i, long j) {
	
	// Check the flags.
	if ((_flag_C(i,j)) == ('N')) {
		_flag_C(i,j) = 'I';
		C(i,j) = reduce0(N,i,j);
		_flag_C(i,j) = 'F';
	}
	else if ((_flag_C(i,j)) == ('I')) {
		printf("There is a self dependence on C at (%ld,%ld)\n",i,j);
		exit(-1);
	}
	
	return C(i,j);
}

static float reduce1(long N, long i) {
	float reduceVar;
	long i1;
	
	reduceVar = 0.0f;
	#define RP1(i0,i1) eval_C(i0,i1)
	#define R1(i0,i1) reduceVar = (reduceVar) + (RP1((i0),(i1)))
	for (i1 = 0; i1 < N; i1 += 1) {
		R1(i, i1);
	}
	#undef RP1
	#undef R1
	return reduceVar;
}

static float eval_C_C_i_0(long i) {
	
	// Check the flags.
	if ((_flag_C_C_i_0(i)) == ('N')) {
		_flag_C_C_i_0(i) = 'I';
		C_C_i_0(i) = reduce1(N,i);
		_flag_C_C_i_0(i) = 'F';
	}
	else if ((_flag_C_C_i_0(i)) == ('I')) {
		printf("There is a self dependence on C_C_i_0 at (%ld)\n",i);
		exit(-1);
	}
	
	return C_C_i_0(i);
}

static float reduce2(long N, long ip) {
	float reduceVar;
	long j;
	long k;
	
	reduceVar = 0.0f;
	#define RP2(i,j,k) (A(((i)),((k)))) * (B(((k)),((j))))
	#define R2(i,j,k) reduceVar = (reduceVar) + (RP2((i),(j),(k)))
	for (j = 0; j < N; j += 1) {
		for (k = 0; k < N; k += 1) {
			R2(ip, j, k);
		}
	}
	#undef RP2
	#undef R2
	return reduceVar;
}

static float eval_C_C_i_1(long i) {
	
	// Check the flags.
	if ((_flag_C_C_i_1(i)) == ('N')) {
		_flag_C_C_i_1(i) = 'I';
		C_C_i_1(i) = reduce2(N,i);
		_flag_C_C_i_1(i) = 'F';
	}
	else if ((_flag_C_C_i_1(i)) == ('I')) {
		printf("There is a self dependence on C_C_i_1 at (%ld)\n",i);
		exit(-1);
	}
	
	return C_C_i_1(i);
}

static float eval_Inv_C_i(long i) {
	
	// Check the flags.
	if ((_flag_Inv_C_i(i)) == ('N')) {
		_flag_Inv_C_i(i) = 'I';
		Inv_C_i(i) = ((eval_C_C_i_0(i)) - (eval_C_C_i_1(i))) / (eval_C_C_i_0(i));
		_flag_Inv_C_i(i) = 'F';
	}
	else if ((_flag_Inv_C_i(i)) == ('I')) {
		printf("There is a self dependence on Inv_C_i at (%ld)\n",i);
		exit(-1);
	}
	
	return Inv_C_i(i);
}

void matmult_abft(long _local_N, float** _local_A, float** _local_B, float** _local_C, float* _local_Inv_C_i) {
	long i;
	long j;
	
	// Copy arguments to the global variables.
	N = _local_N;
	A = _local_A;
	B = _local_B;
	C = _local_C;
	Inv_C_i = _local_Inv_C_i;
	
	// Check parameter validity.
	if (!((-1 + N) >= (0))) {
		printf("The value of the parameters are invalid.\n");
		exit(-1);
	}
	
	// Allocate memory for local storage.
	C_C_i_0 = (float*)(malloc((sizeof(float)) * (((-1 + N >= 0) ? (N) : 0))));
	mallocCheck(C_C_i_0,"C_C_i_0");
	C_C_i_1 = (float*)(malloc((sizeof(float)) * (((-1 + N >= 0) ? (N) : 0))));
	mallocCheck(C_C_i_1,"C_C_i_1");
	
	// Allocate and initialize flag variables.
	_flag_C = (char*)(malloc((sizeof(char)) * (((-1 + N >= 0) ? (N*N) : 0))));
	mallocCheck(_flag_C,"_flag_C");
	memset(_flag_C,'N',((-1 + N >= 0) ? (N*N) : 0));
	_flag_Inv_C_i = (char*)(malloc((sizeof(char)) * (((-1 + N >= 0) ? (N) : 0))));
	mallocCheck(_flag_Inv_C_i,"_flag_Inv_C_i");
	memset(_flag_Inv_C_i,'N',((-1 + N >= 0) ? (N) : 0));
	_flag_C_C_i_0 = (char*)(malloc((sizeof(char)) * (((-1 + N >= 0) ? (N) : 0))));
	mallocCheck(_flag_C_C_i_0,"_flag_C_C_i_0");
	memset(_flag_C_C_i_0,'N',((-1 + N >= 0) ? (N) : 0));
	_flag_C_C_i_1 = (char*)(malloc((sizeof(char)) * (((-1 + N >= 0) ? (N) : 0))));
	mallocCheck(_flag_C_C_i_1,"_flag_C_C_i_1");
	memset(_flag_C_C_i_1,'N',((-1 + N >= 0) ? (N) : 0));
	
	// Evaluate all the outputs.
	#define S0(i,j) eval_C(i,j)
	for (i = 0; i < N; i += 1) {
		for (j = 0; j < N; j += 1) {
			S0(i, j);
		}
	}
	#undef S0
	#define S1(i) eval_Inv_C_i(i)
	for (i = 0; i < N; i += 1) {
		S1(i);
	}
	#undef S1
	
	// Free all allocated memory.
	free(C_C_i_0);
	free(C_C_i_1);
	free(_flag_C);
	free(_flag_Inv_C_i);
	free(_flag_C_C_i_0);
	free(_flag_C_C_i_1);
}


// Undefine the Memory and Function Macros
#undef A
#undef B
#undef C
#undef Inv_C_i
#undef C_C_i_0
#undef C_C_i_1
#undef _flag_C
#undef _flag_Inv_C_i
#undef _flag_C_C_i_0
#undef _flag_C_C_i_1
#undef ceild
#undef floord
#undef div
#undef max
#undef min
#undef mallocCheck
