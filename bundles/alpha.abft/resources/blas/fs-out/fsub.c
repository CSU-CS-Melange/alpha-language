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
static float** L;
static float* b;
static float* x;
static float* x_NR;
static char* _flag_x;
static char* _flag_x_NR;

// Memory Macros
#define L(i,j) L[i][j]
#define b(j) b[j]
#define x(i) x[i]
#define x_NR(i) x_NR[((-1 + N - i >= 0 && -2 + i >= 0) ? ((-1 + i)) : 0)]
#define _flag_x(i) _flag_x[((-1 + N - i >= 0 && -1 + i >= 0) ? (i) : 0)]
#define _flag_x_NR(i) _flag_x_NR[((-1 + N - i >= 0 && -2 + i >= 0) ? ((-1 + i)) : 0)]

// Function Declarations
static float eval_x(long i);
static float reduce0(long N, long ip);
static float eval_x_NR(long i);
void fsub(long _local_N, float** _local_L, float* _local_b, float* _local_x);

static float eval_x(long i) {
	
	// Check the flags.
	if ((_flag_x(i)) == ('N')) {
		_flag_x(i) = 'I';
		x(i) = (((i) == (0)) && ((-1 + N) >= (0))) ? ((b(i)) / (L(((i)),((i))))) : (((b(i)) - (eval_x_NR(i))) / (L(((i)),((i)))));
		_flag_x(i) = 'F';
	}
	else if ((_flag_x(i)) == ('I')) {
		printf("There is a self dependence on x at (%ld)\n",i);
		exit(-1);
	}
	
	return x(i);
}

static float reduce0(long N, long ip) {
	float reduceVar;
	long j;
	
	reduceVar = 0.0f;
	#define RP0(i,j) (L(i,j)) * (eval_x(((j))))
	#define R0(i,j) reduceVar = (reduceVar) + (RP0((i),(j)))
	for (j = 0; j < ip; j += 1) {
		R0(ip, j);
	}
	#undef RP0
	#undef R0
	return reduceVar;
}

static float eval_x_NR(long i) {
	
	// Check the flags.
	if ((_flag_x_NR(i)) == ('N')) {
		_flag_x_NR(i) = 'I';
		x_NR(i) = reduce0(N,i);
		_flag_x_NR(i) = 'F';
	}
	else if ((_flag_x_NR(i)) == ('I')) {
		printf("There is a self dependence on x_NR at (%ld)\n",i);
		exit(-1);
	}
	
	return x_NR(i);
}

void fsub(long _local_N, float** _local_L, float* _local_b, float* _local_x) {
	long i;
	
	// Copy arguments to the global variables.
	N = _local_N;
	L = _local_L;
	b = _local_b;
	x = _local_x;
	
	// Check parameter validity.
	if (!((-1 + N) >= (0))) {
		printf("The value of the parameters are invalid.\n");
		exit(-1);
	}
	
	// Allocate memory for local storage.
	x_NR = (float*)(malloc((sizeof(float)) * (((-2 + N >= 0) ? ((-1 + N)) : 0))));
	mallocCheck(x_NR,"x_NR");
	
	// Allocate and initialize flag variables.
	_flag_x = (char*)(malloc((sizeof(char)) * (((-1 + N >= 0) ? (N) : 0))));
	mallocCheck(_flag_x,"_flag_x");
	memset(_flag_x,'N',((-1 + N >= 0) ? (N) : 0));
	_flag_x_NR = (char*)(malloc((sizeof(char)) * (((-2 + N >= 0) ? ((-1 + N)) : 0))));
	mallocCheck(_flag_x_NR,"_flag_x_NR");
	memset(_flag_x_NR,'N',((-2 + N >= 0) ? ((-1 + N)) : 0));
	
	// Evaluate all the outputs.
	#define S0(i) eval_x(i)
	for (i = 0; i < N; i += 1) {
		S0(i);
	}
	#undef S0
	
	// Free all allocated memory.
	free(x_NR);
	free(_flag_x);
	free(_flag_x_NR);
}


// Undefine the Memory and Function Macros
#undef L
#undef b
#undef x
#undef x_NR
#undef _flag_x
#undef _flag_x_NR
#undef ceild
#undef floord
#undef div
#undef max
#undef min
#undef mallocCheck
