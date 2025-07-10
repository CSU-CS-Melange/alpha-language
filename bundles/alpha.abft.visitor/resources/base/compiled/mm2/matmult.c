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
static float* A;
static float* B;
static float* C;

// Memory Macros
#define mem_A(i,j) A[((-1 + i >= 0 && -1 + N - i >= 0 && -1 + j >= 0 && -1 + N - j >= 0) ? ((N * i + j)) : (j == 0 && -1 + i >= 0 && -1 + N - i >= 0 && -1 + N >= 0) ? (N * i) : (i == 0 && -1 + N - j >= 0 && -1 + N >= 0 && -1 + j >= 0) ? (j) : 0)]
#define A(i,j) mem_A(((i)),((j)))
#define mem_B(i,j) B[((-1 + i >= 0 && -1 + N - i >= 0 && -1 + j >= 0 && -1 + N - j >= 0) ? ((N * i + j)) : (j == 0 && -1 + i >= 0 && -1 + N - i >= 0 && -1 + N >= 0) ? (N * i) : (i == 0 && -1 + N - j >= 0 && -1 + N >= 0 && -1 + j >= 0) ? (j) : 0)]
#define B(i,j) mem_B(((i)),((j)))
#define mem_C(i,j) C[((-1 + i >= 0 && -1 + N - i >= 0 && -1 + j >= 0 && -1 + N - j >= 0) ? ((N * i + j)) : (j == 0 && -1 + i >= 0 && -1 + N - i >= 0 && -1 + N >= 0) ? (N * i) : (i == 0 && -1 + N - j >= 0 && -1 + N >= 0 && -1 + j >= 0) ? (j) : 0)]
#define C(i,j) mem_C(((i)),((j)))

// Function Declarations
static float reduce0(long N, long ip, long jp);
static void eval_C(long i, long j);
void matmult(long _local_N, float* _local_A, float* _local_B, float* _local_C);

static float reduce0(long N, long ip, long jp) {
	float reduceVar;
	long c3;
	
	reduceVar = 0.0f;
	#define RP0(i,j,k) (A(((i)),((k)))) * (B(((k)),((j))))
	#define R0(i,j,k) reduceVar = (reduceVar) + (RP0((i),(j),(k)))
	for (c3 = 0; c3 < N; c3 += 1) {
		R0(ip, jp, c3);
	}
	#undef RP0
	#undef R0
	return reduceVar;
}

static void eval_C(long i, long j) {
	
	C(i,j) = reduce0(N,i,j);
}

void matmult(long _local_N, float* _local_A, float* _local_B, float* _local_C) {
	long c1;
	long c2;
	
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
	
	// Evaluate all the outputs.
	for (c1 = 0; c1 < N; c1 += 1) {
		for (c2 = 0; c2 < N; c2 += 1) {
			eval_C(c1, c2);
		}
	}
	
	// Free all allocated memory.
}


// Undefine the Memory and Function Macros
#undef mem_A
#undef A
#undef mem_B
#undef B
#undef mem_C
#undef C
#undef ceild
#undef floord
#undef div
#undef max
#undef min
#undef mallocCheck