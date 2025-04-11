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
static float* x;
static float* b;
static char* _flag_b;

// Memory Macros
#define A(i,j) A[i][j]
#define x(i) x[i]
#define b(i) b[i]
#define _flag_b(i) _flag_b[((-1 + N - i >= 0 && -1 + i >= 0) ? (i) : 0)]

// Function Declarations
static float reduce0(long N, long ip);
static float eval_b(long i);
void matvec(long _local_N, float** _local_A, float* _local_x, float* _local_b);

static float reduce0(long N, long ip) {
	float reduceVar;
	long k;
	
	reduceVar = 0.0f;
	#define RP0(i,k) (A(((i)),((k)))) * (x(((k))))
	#define R0(i,k) reduceVar = (reduceVar) + (RP0((i),(k)))
	for (k = 0; k < N; k += 1) {
		R0(ip, k);
	}
	#undef RP0
	#undef R0
	return reduceVar;
}

static float eval_b(long i) {
	
	// Check the flags.
	if ((_flag_b(i)) == ('N')) {
		_flag_b(i) = 'I';
		b(i) = reduce0(N,i);
		_flag_b(i) = 'F';
	}
	else if ((_flag_b(i)) == ('I')) {
		printf("There is a self dependence on b at (%ld)\n",i);
		exit(-1);
	}
	
	return b(i);
}

void matvec(long _local_N, float** _local_A, float* _local_x, float* _local_b) {
	long i;
	
	// Copy arguments to the global variables.
	N = _local_N;
	A = _local_A;
	x = _local_x;
	b = _local_b;
	
	// Check parameter validity.
	if (!((-1 + N) >= (0))) {
		printf("The value of the parameters are invalid.\n");
		exit(-1);
	}
	
	// Allocate memory for local storage.
	
	// Allocate and initialize flag variables.
	_flag_b = (char*)(malloc((sizeof(char)) * (((-1 + N >= 0) ? (N) : 0))));
	mallocCheck(_flag_b,"_flag_b");
	memset(_flag_b,'N',((-1 + N >= 0) ? (N) : 0));
	
	// Evaluate all the outputs.
	#define S0(i) eval_b(i)
	for (i = 0; i < N; i += 1) {
		S0(i);
	}
	#undef S0
	
	// Free all allocated memory.
	free(_flag_b);
}


// Undefine the Memory and Function Macros
#undef A
#undef x
#undef b
#undef _flag_b
#undef ceild
#undef floord
#undef div
#undef max
#undef min
#undef mallocCheck
