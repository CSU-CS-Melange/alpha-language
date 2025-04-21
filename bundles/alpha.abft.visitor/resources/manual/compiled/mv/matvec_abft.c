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
static float* check_b_i_0;
static char* _flag_b;
static char* _flag_check_b_i_0;

// Memory Macros
#define A(i,j) A[i][j]
#define x(i) x[i]
#define b(i) b[i]
#define check_b_i_0() check_b_i_0[0]
#define _flag_b(i) _flag_b[((-1 + N - i >= 0 && -1 + i >= 0) ? (i) : 0)]
#define _flag_check_b_i_0() _flag_check_b_i_0[(0)]

// Function Declarations
static float reduce0(long N, long ip);
static float eval_b(long i);
static float reduce1(long N);
static float eval_check_b_i_0();
void matvec_abft(long _local_N, float** _local_A, float* _local_x, float* _local_b, float* _local_check_b_i_0);

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

static float reduce1(long N) {
	float reduceVar;
	long i;
	
	reduceVar = 0.0f;
	#define RP1(i) eval_b(((i)))
	#define R1(i) reduceVar = (reduceVar) + (RP1((i)))
	for (i = 0; i < N; i += 1) {
		R1(i);
	}
	#undef RP1
	#undef R1
	return reduceVar;
}

static float eval_check_b_i_0() {
	
	// Check the flags.
	if ((_flag_check_b_i_0()) == ('N')) {
		_flag_check_b_i_0() = 'I';
		check_b_i_0() = reduce1(N);
		_flag_check_b_i_0() = 'F';
	}
	else if ((_flag_check_b_i_0()) == ('I')) {
		printf("There is a self dependence on check_b_i_0 at ()\n");
		exit(-1);
	}
	
	return check_b_i_0();
}

void matvec_abft(long _local_N, float** _local_A, float* _local_x, float* _local_b, float* _local_check_b_i_0) {
	long i;
	
	// Copy arguments to the global variables.
	N = _local_N;
	A = _local_A;
	x = _local_x;
	b = _local_b;
	check_b_i_0 = _local_check_b_i_0;
	
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
	_flag_check_b_i_0 = (char*)(malloc((sizeof(char)) * (((-1 + N >= 0) ? (1) : 0))));
	mallocCheck(_flag_check_b_i_0,"_flag_check_b_i_0");
	memset(_flag_check_b_i_0,'N',((-1 + N >= 0) ? (1) : 0));
	
	// Evaluate all the outputs.
	#define S0(i) eval_b(i)
	for (i = 0; i < N; i += 1) {
		S0(i);
	}
	#undef S0
	#define S1() eval_check_b_i_0()
	S1();
	#undef S1
	
	// Free all allocated memory.
	free(_flag_b);
	free(_flag_check_b_i_0);
}


// Undefine the Memory and Function Macros
#undef A
#undef x
#undef b
#undef check_b_i_0
#undef _flag_b
#undef _flag_check_b_i_0
#undef ceild
#undef floord
#undef div
#undef max
#undef min
#undef mallocCheck
