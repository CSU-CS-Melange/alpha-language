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
static char* _flag_C;

// Memory Macros
#define A(i,j) A[i][j]
#define B(i,j) B[i][j]
#define C(i,j) C[i][j]
#define _flag_C(i,j) _flag_C[((-1 + i >= 0 && -1 + N - i >= 0 && -1 + j >= 0 && -1 + N - j >= 0) ? ((N * i + j)) : (j == 0 && -1 + i >= 0 && -1 + N - i >= 0 && -1 + N >= 0) ? (N * i) : (i == 0 && -1 + N - j >= 0 && -1 + N >= 0 && -1 + j >= 0) ? (j) : 0)]

// Function Declarations
static float reduce0(long N, long ip, long jp);
static float eval_C(long i, long j);
void matmult(long _local_N, float** _local_A, float** _local_B, float** _local_C);

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

void matmult(long _local_N, float** _local_A, float** _local_B, float** _local_C) {
	long i;
	long j;
	
	// Copy arguments to the global variables.
	N = _local_N;
	A = _local_A;
	B = _local_B;
	C = _local_C;
	
	// Check parameter validity.
	if (!((-1 + N) >= (0))) {
		printf("The value of the parameters are invalid.\n");
		exit(-1);
	}
	
	// Allocate memory for local storage.
	
	// Allocate and initialize flag variables.
	_flag_C = (char*)(malloc((sizeof(char)) * (((-1 + N >= 0) ? (N*N) : 0))));
	mallocCheck(_flag_C,"_flag_C");
	memset(_flag_C,'N',((-1 + N >= 0) ? (N*N) : 0));
	
	// Evaluate all the outputs.
	#define S0(i,j) eval_C(i,j)
	for (i = 0; i < N; i += 1) {
		for (j = 0; j < N; j += 1) {
			S0(i, j);
		}
	}
	#undef S0
	
	// Free all allocated memory.
	free(_flag_C);
}


// Undefine the Memory and Function Macros
#undef A
#undef B
#undef C
#undef _flag_C
#undef ceild
#undef floord
#undef div
#undef max
#undef min
#undef mallocCheck
