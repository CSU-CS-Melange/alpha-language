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
static float* check_x_i_inv;
static float* check_x_i_0;
static float* check_x_i_1;
static float* x_NR;
static float* check_x_i_1_NR;
static char* _flag_x;
static char* _flag_check_x_i_inv;
static char* _flag_check_x_i_0;
static char* _flag_check_x_i_1;
static char* _flag_x_NR;
static char* _flag_check_x_i_1_NR;

// Memory Macros
#define L(i,j) L[i][j]
#define b(i) b[i]
#define x(i) x[i]
#define check_x_i_inv() check_x_i_inv[0]
#define check_x_i_0() check_x_i_0[(0)]
#define check_x_i_1() check_x_i_1[(0)]
#define x_NR(i) x_NR[((-1 + N - i >= 0 && -2 + i >= 0) ? ((-1 + i)) : 0)]
#define check_x_i_1_NR(i0) check_x_i_1_NR[((-1 + N - i0 >= 0 && -2 + i0 >= 0) ? ((-1 + i0)) : 0)]
#define _flag_x(i) _flag_x[((-1 + N - i >= 0 && -1 + i >= 0) ? (i) : 0)]
#define _flag_check_x_i_inv() _flag_check_x_i_inv[(0)]
#define _flag_check_x_i_0() _flag_check_x_i_0[(0)]
#define _flag_check_x_i_1() _flag_check_x_i_1[(0)]
#define _flag_x_NR(i) _flag_x_NR[((-1 + N - i >= 0 && -2 + i >= 0) ? ((-1 + i)) : 0)]
#define _flag_check_x_i_1_NR(i0) _flag_check_x_i_1_NR[((-1 + N - i0 >= 0 && -2 + i0 >= 0) ? ((-1 + i0)) : 0)]

// Function Declarations
static float eval_x(long i);
static float reduce0(long N);
static float eval_check_x_i_0();
static float reduce1(long N);
static float eval_check_x_i_1();
static float eval_check_x_i_inv();
static float reduce2(long N, long ip);
static float eval_x_NR(long i);
static float reduce3(long N, long i0);
static float eval_check_x_i_1_NR(long i0);
void fsub_aabft(long _local_N, float** _local_L, float* _local_b, float* _local_x, float* _local_check_x_i_inv);

static float eval_x(long i) {
	
	// Check the flags.
	if ((_flag_x(i)) == ('N')) {
		_flag_x(i) = 'I';
		x(i) = (((i) == (0)) && ((-1 + N) >= (0))) ? ((b(((i)))) / (L(((i)),((i))))) : (((b(((i)))) - (eval_x_NR(i))) / (L(((i)),((i)))));
		_flag_x(i) = 'F';
	}
	else if ((_flag_x(i)) == ('I')) {
		printf("There is a self dependence on x at (%ld)\n",i);
		exit(-1);
	}
	
	return x(i);
}

static float reduce0(long N) {
	float reduceVar;
	long i;
	
	reduceVar = 0.0f;
	#define RP0(i) eval_x(((i)))
	#define R0(i) reduceVar = (reduceVar) + (RP0((i)))
	for (i = 0; i < N; i += 1) {
		R0(i);
	}
	#undef RP0
	#undef R0
	return reduceVar;
}

static float eval_check_x_i_0() {
	
	// Check the flags.
	if ((_flag_check_x_i_0()) == ('N')) {
		_flag_check_x_i_0() = 'I';
		check_x_i_0() = reduce0(N);
		_flag_check_x_i_0() = 'F';
	}
	else if ((_flag_check_x_i_0()) == ('I')) {
		printf("There is a self dependence on check_x_i_0 at ()\n");
		exit(-1);
	}
	
	return check_x_i_0();
}

static float reduce1(long N) {
	float reduceVar;
	long i;
	
	reduceVar = 0.0f;
	#define RP1(i) (((i) == (0)) && ((-1 + N) >= (0))) ? ((b(((i)))) / (L(((i)),((i))))) : (((b(((i)))) - (eval_check_x_i_1_NR(i))) / (L(((i)),((i)))))
	#define R1(i) reduceVar = (reduceVar) + (RP1((i)))
	for (i = 0; i < N; i += 1) {
		R1(i);
	}
	#undef RP1
	#undef R1
	return reduceVar;
}

static float eval_check_x_i_1() {
	
	// Check the flags.
	if ((_flag_check_x_i_1()) == ('N')) {
		_flag_check_x_i_1() = 'I';
		check_x_i_1() = reduce1(N);
		_flag_check_x_i_1() = 'F';
	}
	else if ((_flag_check_x_i_1()) == ('I')) {
		printf("There is a self dependence on check_x_i_1 at ()\n");
		exit(-1);
	}
	
	return check_x_i_1();
}

static float eval_check_x_i_inv() {
	
	// Check the flags.
	if ((_flag_check_x_i_inv()) == ('N')) {
		_flag_check_x_i_inv() = 'I';
		check_x_i_inv() = ((eval_check_x_i_0()) - (eval_check_x_i_1())) / (eval_check_x_i_0());
		_flag_check_x_i_inv() = 'F';
	}
	else if ((_flag_check_x_i_inv()) == ('I')) {
		printf("There is a self dependence on check_x_i_inv at ()\n");
		exit(-1);
	}
	
	return check_x_i_inv();
}

static float reduce2(long N, long ip) {
	float reduceVar;
	long j;
	
	reduceVar = 0.0f;
	#define RP2(i,j) (L(((i)),((j)))) * (eval_x(((j))))
	#define R2(i,j) reduceVar = (reduceVar) + (RP2((i),(j)))
	for (j = 0; j < ip; j += 1) {
		R2(ip, j);
	}
	#undef RP2
	#undef R2
	return reduceVar;
}

static float eval_x_NR(long i) {
	
	// Check the flags.
	if ((_flag_x_NR(i)) == ('N')) {
		_flag_x_NR(i) = 'I';
		x_NR(i) = reduce2(N,i);
		_flag_x_NR(i) = 'F';
	}
	else if ((_flag_x_NR(i)) == ('I')) {
		printf("There is a self dependence on x_NR at (%ld)\n",i);
		exit(-1);
	}
	
	return x_NR(i);
}

static float reduce3(long N, long i0) {
	float reduceVar;
	long j;
	
	reduceVar = 0.0f;
	#define RP3(i,j) (L(((i)),((j)))) * (eval_x(((j))))
	#define R3(i,j) reduceVar = (reduceVar) + (RP3((i),(j)))
	for (j = 0; j < i0; j += 1) {
		R3(i0, j);
	}
	#undef RP3
	#undef R3
	return reduceVar;
}

static float eval_check_x_i_1_NR(long i0) {
	
	// Check the flags.
	if ((_flag_check_x_i_1_NR(i0)) == ('N')) {
		_flag_check_x_i_1_NR(i0) = 'I';
		check_x_i_1_NR(i0) = reduce3(N,i0);
		_flag_check_x_i_1_NR(i0) = 'F';
	}
	else if ((_flag_check_x_i_1_NR(i0)) == ('I')) {
		printf("There is a self dependence on check_x_i_1_NR at (%ld)\n",i0);
		exit(-1);
	}
	
	return check_x_i_1_NR(i0);
}

void fsub_aabft(long _local_N, float** _local_L, float* _local_b, float* _local_x, float* _local_check_x_i_inv) {
	long i;
	
	// Copy arguments to the global variables.
	N = _local_N;
	L = _local_L;
	b = _local_b;
	x = _local_x;
	check_x_i_inv = _local_check_x_i_inv;
	
	// Check parameter validity.
	if (!((-1 + N) >= (0))) {
		printf("The value of the parameters are invalid.\n");
		exit(-1);
	}
	
	// Allocate memory for local storage.
	check_x_i_0 = (float*)(malloc((sizeof(float)) * (((-1 + N >= 0) ? (1) : 0))));
	mallocCheck(check_x_i_0,"check_x_i_0");
	check_x_i_1 = (float*)(malloc((sizeof(float)) * (((-1 + N >= 0) ? (1) : 0))));
	mallocCheck(check_x_i_1,"check_x_i_1");
	x_NR = (float*)(malloc((sizeof(float)) * (((-2 + N >= 0) ? ((-1 + N)) : 0))));
	mallocCheck(x_NR,"x_NR");
	check_x_i_1_NR = (float*)(malloc((sizeof(float)) * (((-2 + N >= 0) ? ((-1 + N)) : 0))));
	mallocCheck(check_x_i_1_NR,"check_x_i_1_NR");
	
	// Allocate and initialize flag variables.
	_flag_x = (char*)(malloc((sizeof(char)) * (((-1 + N >= 0) ? (N) : 0))));
	mallocCheck(_flag_x,"_flag_x");
	memset(_flag_x,'N',((-1 + N >= 0) ? (N) : 0));
	_flag_check_x_i_inv = (char*)(malloc((sizeof(char)) * (((-1 + N >= 0) ? (1) : 0))));
	mallocCheck(_flag_check_x_i_inv,"_flag_check_x_i_inv");
	memset(_flag_check_x_i_inv,'N',((-1 + N >= 0) ? (1) : 0));
	_flag_check_x_i_0 = (char*)(malloc((sizeof(char)) * (((-1 + N >= 0) ? (1) : 0))));
	mallocCheck(_flag_check_x_i_0,"_flag_check_x_i_0");
	memset(_flag_check_x_i_0,'N',((-1 + N >= 0) ? (1) : 0));
	_flag_check_x_i_1 = (char*)(malloc((sizeof(char)) * (((-1 + N >= 0) ? (1) : 0))));
	mallocCheck(_flag_check_x_i_1,"_flag_check_x_i_1");
	memset(_flag_check_x_i_1,'N',((-1 + N >= 0) ? (1) : 0));
	_flag_x_NR = (char*)(malloc((sizeof(char)) * (((-2 + N >= 0) ? ((-1 + N)) : 0))));
	mallocCheck(_flag_x_NR,"_flag_x_NR");
	memset(_flag_x_NR,'N',((-2 + N >= 0) ? ((-1 + N)) : 0));
	_flag_check_x_i_1_NR = (char*)(malloc((sizeof(char)) * (((-2 + N >= 0) ? ((-1 + N)) : 0))));
	mallocCheck(_flag_check_x_i_1_NR,"_flag_check_x_i_1_NR");
	memset(_flag_check_x_i_1_NR,'N',((-2 + N >= 0) ? ((-1 + N)) : 0));
	
	// Evaluate all the outputs.
	#define S0(i) eval_x(i)
	for (i = 0; i < N; i += 1) {
		S0(i);
	}
	#undef S0
	#define S1() eval_check_x_i_inv()
	S1();
	#undef S1
	
	// Free all allocated memory.
	free(check_x_i_0);
	free(check_x_i_1);
	free(x_NR);
	free(check_x_i_1_NR);
	free(_flag_x);
	free(_flag_check_x_i_inv);
	free(_flag_check_x_i_0);
	free(_flag_check_x_i_1);
	free(_flag_x_NR);
	free(_flag_check_x_i_1_NR);
}


// Undefine the Memory and Function Macros
#undef L
#undef b
#undef x
#undef check_x_i_inv
#undef check_x_i_0
#undef check_x_i_1
#undef x_NR
#undef check_x_i_1_NR
#undef _flag_x
#undef _flag_check_x_i_inv
#undef _flag_check_x_i_0
#undef _flag_check_x_i_1
#undef _flag_x_NR
#undef _flag_check_x_i_1_NR
#undef ceild
#undef floord
#undef div
#undef max
#undef min
#undef mallocCheck
