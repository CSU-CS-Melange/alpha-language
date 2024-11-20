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
static float* a;
static float* a_c;
static char* _flag_a_c;

// Memory Macros
#define a(i) a[i]
#define a_c() a_c
#define _flag_a_c() _flag_a_c[(0)]

// Function Declarations
static float reduce0(long N);
static float eval_a_c();
void scalar(long _local_N, float* _local_a, float* _local_a_c);

static float reduce0(long N) {
	float reduceVar;
	long i0;
	
	reduceVar = 0.0f;
	#define RP0(i0) a(i0)
	#define R0(i0) reduceVar = (reduceVar) + (RP0((i0)))
	for (i0 = 0; i0 < N; i0 += 1) {
		R0(i0);
	}
	#undef RP0
	#undef R0
	return reduceVar;
}

static float eval_a_c() {
	
	// Check the flags.
	if ((_flag_a_c()) == ('N')) {
		_flag_a_c() = 'I';
		a_c() = reduce0(N);
		_flag_a_c() = 'F';
	}
	else if ((_flag_a_c()) == ('I')) {
		printf("There is a self dependence on a_c at ()\n");
		exit(-1);
	}
	
	return a_c();
}

void scalar(long _local_N, float* _local_a, float* _local_a_c) {
	
	// Copy arguments to the global variables.
	N = _local_N;
	a = _local_a;
	a_c = _local_a_c;
	
	// Check parameter validity.
	if (!((-1 + N) >= (0))) {
		printf("The value of the parameters are invalid.\n");
		exit(-1);
	}
	
	// Allocate memory for local storage.
	
	// Allocate and initialize flag variables.
	_flag_a_c = (char*)(malloc((sizeof(char)) * (((-1 + N >= 0) ? (1) : 0))));
	mallocCheck(_flag_a_c,"_flag_a_c");
	memset(_flag_a_c,'N',((-1 + N >= 0) ? (1) : 0));
	
	// Evaluate all the outputs.
	#define S0() eval_a_c()
	S0();
	#undef S0
	
	// Free all allocated memory.
	free(_flag_a_c);
}


// Undefine the Memory and Function Macros
#undef a
#undef a_c
#undef _flag_a_c
#undef ceild
#undef floord
#undef div
#undef max
#undef min
#undef mallocCheck
