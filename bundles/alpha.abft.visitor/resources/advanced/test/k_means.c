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
static float* k;
static float* pts;
static float** S;
static char* _flag_S;

// Memory Macros
#define k() k[0]
#define pts(i) pts[i]
#define S(i,j) S[i][j]
#define _flag_S(i,j) _flag_S[((-2 + i >= 0 && N - i >= 0 && -2 + j >= 0 && N - j >= 0) ? ((((-1 - N) + N * i) + j)) : (-1 + j == 0 && -2 + i >= 0 && N - i >= 0 && -1 + N >= 0) ? ((-N + N * i)) : (-1 + i == 0 && N - j >= 0 && -1 + N >= 0 && -2 + j >= 0) ? ((-1 + j)) : 0)]

// Function Declarations
static float eval_S(long i, long j);
void k_means(long _local_N, float* _local_k, float* _local_pts, float** _local_S);

static float eval_S(long i, long j) {
	
	// Check the flags.
	if ((_flag_S(i,j)) == ('N')) {
		_flag_S(i,j) = 'I';
		S(i,j) = k();
		_flag_S(i,j) = 'F';
	}
	else if ((_flag_S(i,j)) == ('I')) {
		printf("There is a self dependence on S at (%ld,%ld)\n",i,j);
		exit(-1);
	}
	
	return S(i,j);
}

void k_means(long _local_N, float* _local_k, float* _local_pts, float** _local_S) {
	long i;
	long j;
	
	// Copy arguments to the global variables.
	N = _local_N;
	k = _local_k;
	pts = _local_pts;
	S = _local_S;
	
	// Check parameter validity.
	if (!((-1 + N) >= (0))) {
		printf("The value of the parameters are invalid.\n");
		exit(-1);
	}
	
	// Allocate memory for local storage.
	
	// Allocate and initialize flag variables.
	_flag_S = (char*)(malloc((sizeof(char)) * (((-1 + N >= 0) ? (N*N) : 0))));
	mallocCheck(_flag_S,"_flag_S");
	memset(_flag_S,'N',((-1 + N >= 0) ? (N*N) : 0));
	
	// Evaluate all the outputs.
	#define S0(i,j) eval_S(i,j)
	for (i = 1; i <= N; i += 1) {
		for (j = 1; j <= N; j += 1) {
			S0(i, j);
		}
	}
	#undef S0
	
	// Free all allocated memory.
	free(_flag_S);
}


// Undefine the Memory and Function Macros
#undef k
#undef pts
#undef S
#undef _flag_S
#undef ceild
#undef floord
#undef div
#undef max
#undef min
#undef mallocCheck
