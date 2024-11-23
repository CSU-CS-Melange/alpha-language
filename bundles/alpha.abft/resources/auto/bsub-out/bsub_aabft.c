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
static float** U;
static float* b;
static float* x;
static float* Inv_x_c;
static float* x_c_0;
static float* x_c_1;
static float* x_NR;
static float* x_c_1_NR;
static char* _flag_x;
static char* _flag_Inv_x_c;
static char* _flag_x_c_0;
static char* _flag_x_c_1;
static char* _flag_x_NR;
static char* _flag_x_c_1_NR;

// Memory Macros
#define U(i,j) U[i][j]
#define b(i) b[i]
#define x(i) x[i]
#define Inv_x_c() Inv_x_c[0]
#define x_c_0() x_c_0[(0)]
#define x_c_1() x_c_1[(0)]
#define x_NR(i) x_NR[((-2 + N - i >= 0 && -1 + i >= 0) ? (i) : 0)]
#define x_c_1_NR(i) x_c_1_NR[((-2 + N - i >= 0 && -1 + i >= 0) ? (i) : 0)]
#define _flag_x(i) _flag_x[((-1 + N - i >= 0 && -1 + i >= 0) ? (i) : 0)]
#define _flag_Inv_x_c() _flag_Inv_x_c[(0)]
#define _flag_x_c_0() _flag_x_c_0[(0)]
#define _flag_x_c_1() _flag_x_c_1[(0)]
#define _flag_x_NR(i) _flag_x_NR[((-2 + N - i >= 0 && -1 + i >= 0) ? (i) : 0)]
#define _flag_x_c_1_NR(i) _flag_x_c_1_NR[((-2 + N - i >= 0 && -1 + i >= 0) ? (i) : 0)]

// Function Declarations
static float eval_x(long i);
static float reduce0(long N);
static float eval_x_c_0();
static float reduce1(long N);
static float eval_x_c_1();
static float eval_Inv_x_c();
static float reduce2(long N, long ip);
static float eval_x_NR(long i);
static float reduce3(long N, long ip);
static float eval_x_c_1_NR(long i);
void bsub_aabft(long _local_N, float** _local_U, float* _local_b, float* _local_x, float* _local_Inv_x_c);

static float eval_x(long i) {
	
	// Check the flags.
	if ((_flag_x(i)) == ('N')) {
		_flag_x(i) = 'I';
		x(i) = (((1 - N + i) == (0)) && ((-1 + N) >= (0))) ? ((b(i)) / (U(((i)),((i))))) : (((b(i)) - (eval_x_NR(i))) / (U(((i)),((i)))));
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
	#define RP0(i) eval_x(i)
	#define R0(i) reduceVar = (reduceVar) + (RP0((i)))
	for (i = 0; i < N; i += 1) {
		R0(i);
	}
	#undef RP0
	#undef R0
	return reduceVar;
}

static float eval_x_c_0() {
	
	// Check the flags.
	if ((_flag_x_c_0()) == ('N')) {
		_flag_x_c_0() = 'I';
		x_c_0() = reduce0(N);
		_flag_x_c_0() = 'F';
	}
	else if ((_flag_x_c_0()) == ('I')) {
		printf("There is a self dependence on x_c_0 at ()\n");
		exit(-1);
	}
	
	return x_c_0();
}

static float reduce1(long N) {
	float reduceVar;
	long i;
	
	reduceVar = 0.0f;
	#define RP1(i) (((1 - N + i) == (0)) && ((-1 + N) >= (0))) ? ((b(i)) / (U(((i)),((i))))) : (((b(i)) - (eval_x_c_1_NR(i))) / (U(((i)),((i)))))
	#define R1(i) reduceVar = (reduceVar) + (RP1((i)))
	for (i = 0; i < N; i += 1) {
		R1(i);
	}
	#undef RP1
	#undef R1
	return reduceVar;
}

static float eval_x_c_1() {
	
	// Check the flags.
	if ((_flag_x_c_1()) == ('N')) {
		_flag_x_c_1() = 'I';
		x_c_1() = reduce1(N);
		_flag_x_c_1() = 'F';
	}
	else if ((_flag_x_c_1()) == ('I')) {
		printf("There is a self dependence on x_c_1 at ()\n");
		exit(-1);
	}
	
	return x_c_1();
}

static float eval_Inv_x_c() {
	
	// Check the flags.
	if ((_flag_Inv_x_c()) == ('N')) {
		_flag_Inv_x_c() = 'I';
		Inv_x_c() = ((eval_x_c_0()) - (eval_x_c_1())) / (eval_x_c_0());
		_flag_Inv_x_c() = 'F';
	}
	else if ((_flag_Inv_x_c()) == ('I')) {
		printf("There is a self dependence on Inv_x_c at ()\n");
		exit(-1);
	}
	
	return Inv_x_c();
}

static float reduce2(long N, long ip) {
	float reduceVar;
	long j;
	
	reduceVar = 0.0f;
	#define RP2(i,j) (U(i,j)) * (eval_x(((j))))
	#define R2(i,j) reduceVar = (reduceVar) + (RP2((i),(j)))
	for (j = ip + 1; j < N; j += 1) {
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

static float reduce3(long N, long ip) {
	float reduceVar;
	long j;
	
	reduceVar = 0.0f;
	#define RP3(i,j) (U(i,j)) * (eval_x(((j))))
	#define R3(i,j) reduceVar = (reduceVar) + (RP3((i),(j)))
	for (j = ip + 1; j < N; j += 1) {
		R3(ip, j);
	}
	#undef RP3
	#undef R3
	return reduceVar;
}

static float eval_x_c_1_NR(long i) {
	
	// Check the flags.
	if ((_flag_x_c_1_NR(i)) == ('N')) {
		_flag_x_c_1_NR(i) = 'I';
		x_c_1_NR(i) = reduce3(N,i);
		_flag_x_c_1_NR(i) = 'F';
	}
	else if ((_flag_x_c_1_NR(i)) == ('I')) {
		printf("There is a self dependence on x_c_1_NR at (%ld)\n",i);
		exit(-1);
	}
	
	return x_c_1_NR(i);
}

void bsub_aabft(long _local_N, float** _local_U, float* _local_b, float* _local_x, float* _local_Inv_x_c) {
	long i;
	
	// Copy arguments to the global variables.
	N = _local_N;
	U = _local_U;
	b = _local_b;
	x = _local_x;
	Inv_x_c = _local_Inv_x_c;
	
	// Check parameter validity.
	if (!((-1 + N) >= (0))) {
		printf("The value of the parameters are invalid.\n");
		exit(-1);
	}
	
	// Allocate memory for local storage.
	x_c_0 = (float*)(malloc((sizeof(float)) * (((-1 + N >= 0) ? (1) : 0))));
	mallocCheck(x_c_0,"x_c_0");
	x_c_1 = (float*)(malloc((sizeof(float)) * (((-1 + N >= 0) ? (1) : 0))));
	mallocCheck(x_c_1,"x_c_1");
	x_NR = (float*)(malloc((sizeof(float)) * (((-2 + N >= 0) ? ((-1 + N)) : 0))));
	mallocCheck(x_NR,"x_NR");
	x_c_1_NR = (float*)(malloc((sizeof(float)) * (((-2 + N >= 0) ? ((-1 + N)) : 0))));
	mallocCheck(x_c_1_NR,"x_c_1_NR");
	
	// Allocate and initialize flag variables.
	_flag_x = (char*)(malloc((sizeof(char)) * (((-1 + N >= 0) ? (N) : 0))));
	mallocCheck(_flag_x,"_flag_x");
	memset(_flag_x,'N',((-1 + N >= 0) ? (N) : 0));
	_flag_Inv_x_c = (char*)(malloc((sizeof(char)) * (((-1 + N >= 0) ? (1) : 0))));
	mallocCheck(_flag_Inv_x_c,"_flag_Inv_x_c");
	memset(_flag_Inv_x_c,'N',((-1 + N >= 0) ? (1) : 0));
	_flag_x_c_0 = (char*)(malloc((sizeof(char)) * (((-1 + N >= 0) ? (1) : 0))));
	mallocCheck(_flag_x_c_0,"_flag_x_c_0");
	memset(_flag_x_c_0,'N',((-1 + N >= 0) ? (1) : 0));
	_flag_x_c_1 = (char*)(malloc((sizeof(char)) * (((-1 + N >= 0) ? (1) : 0))));
	mallocCheck(_flag_x_c_1,"_flag_x_c_1");
	memset(_flag_x_c_1,'N',((-1 + N >= 0) ? (1) : 0));
	_flag_x_NR = (char*)(malloc((sizeof(char)) * (((-2 + N >= 0) ? ((-1 + N)) : 0))));
	mallocCheck(_flag_x_NR,"_flag_x_NR");
	memset(_flag_x_NR,'N',((-2 + N >= 0) ? ((-1 + N)) : 0));
	_flag_x_c_1_NR = (char*)(malloc((sizeof(char)) * (((-2 + N >= 0) ? ((-1 + N)) : 0))));
	mallocCheck(_flag_x_c_1_NR,"_flag_x_c_1_NR");
	memset(_flag_x_c_1_NR,'N',((-2 + N >= 0) ? ((-1 + N)) : 0));
	
	// Evaluate all the outputs.
	#define S0(i) eval_x(i)
	for (i = 0; i < N; i += 1) {
		S0(i);
	}
	#undef S0
	#define S1() eval_Inv_x_c()
	S1();
	#undef S1
	
	// Free all allocated memory.
	free(x_c_0);
	free(x_c_1);
	free(x_NR);
	free(x_c_1_NR);
	free(_flag_x);
	free(_flag_Inv_x_c);
	free(_flag_x_c_0);
	free(_flag_x_c_1);
	free(_flag_x_NR);
	free(_flag_x_c_1_NR);
}


// Undefine the Memory and Function Macros
#undef U
#undef b
#undef x
#undef Inv_x_c
#undef x_c_0
#undef x_c_1
#undef x_NR
#undef x_c_1_NR
#undef _flag_x
#undef _flag_Inv_x_c
#undef _flag_x_c_0
#undef _flag_x_c_1
#undef _flag_x_NR
#undef _flag_x_c_1_NR
#undef ceild
#undef floord
#undef div
#undef max
#undef min
#undef mallocCheck
