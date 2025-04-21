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
static float** A;
static float** B;
static float* check_B_i_0;
static float* check_B_i_1;
static float* check_B_i_inv;
static float* check_B_j_0;
static float* check_B_j_1;
static float* check_B_j_inv;
static char* _flag_B;
static char* _flag_check_B_i_0;
static char* _flag_check_B_i_1;
static char* _flag_check_B_i_inv;
static char* _flag_check_B_j_0;
static char* _flag_check_B_j_1;
static char* _flag_check_B_j_inv;

// Memory Macros
#define k() k[0]
#define A(i,j) A[i][j]
#define B(i,j) B[i][j]
#define check_B_i_0(i) check_B_i_0[i]
#define check_B_i_1(i) check_B_i_1[i]
#define check_B_i_inv(i) check_B_i_inv[i]
#define check_B_j_0(j) check_B_j_0[j]
#define check_B_j_1(j) check_B_j_1[j]
#define check_B_j_inv(j) check_B_j_inv[j]
#define _flag_B(i,j) _flag_B[((-1 + i >= 0 && -1 + N - i >= 0 && -1 + j >= 0 && -1 + N - j >= 0) ? ((N * i + j)) : (j == 0 && -1 + i >= 0 && -1 + N - i >= 0 && -1 + N >= 0) ? (N * i) : (i == 0 && -1 + N - j >= 0 && -1 + N >= 0 && -1 + j >= 0) ? (j) : 0)]
#define _flag_check_B_i_0(i) _flag_check_B_i_0[((-1 + N - i >= 0 && -1 + i >= 0) ? (i) : 0)]
#define _flag_check_B_i_1(i) _flag_check_B_i_1[((-1 + N - i >= 0 && -1 + i >= 0) ? (i) : 0)]
#define _flag_check_B_i_inv(i) _flag_check_B_i_inv[((-1 + N - i >= 0 && -1 + i >= 0) ? (i) : 0)]
#define _flag_check_B_j_0(j) _flag_check_B_j_0[((-1 + N - j >= 0 && -1 + j >= 0) ? (j) : 0)]
#define _flag_check_B_j_1(j) _flag_check_B_j_1[((-1 + N - j >= 0 && -1 + j >= 0) ? (j) : 0)]
#define _flag_check_B_j_inv(j) _flag_check_B_j_inv[((-1 + N - j >= 0 && -1 + j >= 0) ? (j) : 0)]

// Function Declarations
static float eval_B(long i, long j);
static float reduce0(long N, long ip);
static float eval_check_B_i_0(long i);
static float reduce1(long N, long ip);
static float eval_check_B_i_1(long i);
static float eval_check_B_i_inv(long i);
static float reduce2(long N, long jp);
static float eval_check_B_j_0(long j);
static float reduce3(long N, long jp);
static float eval_check_B_j_1(long j);
static float eval_check_B_j_inv(long j);
void scalmat_aabft(long _local_N, float* _local_k, float** _local_A, float** _local_B, float* _local_check_B_i_0, float* _local_check_B_i_1, float* _local_check_B_i_inv, float* _local_check_B_j_0, float* _local_check_B_j_1, float* _local_check_B_j_inv);

static float eval_B(long i, long j) {
	
	// Check the flags.
	if ((_flag_B(i,j)) == ('N')) {
		_flag_B(i,j) = 'I';
		B(i,j) = (k()) * (A(((i)),((j))));
		_flag_B(i,j) = 'F';
	}
	else if ((_flag_B(i,j)) == ('I')) {
		printf("There is a self dependence on B at (%ld,%ld)\n",i,j);
		exit(-1);
	}
	
	return B(i,j);
}

static float reduce0(long N, long ip) {
	float reduceVar;
	long j;
	
	reduceVar = 0.0f;
	#define RP0(i,j) eval_B(((i)),((j)))
	#define R0(i,j) reduceVar = (reduceVar) + (RP0((i),(j)))
	for (j = 0; j < N; j += 1) {
		R0(ip, j);
	}
	#undef RP0
	#undef R0
	return reduceVar;
}

static float eval_check_B_i_0(long i) {
	
	// Check the flags.
	if ((_flag_check_B_i_0(i)) == ('N')) {
		_flag_check_B_i_0(i) = 'I';
		check_B_i_0(i) = reduce0(N,i);
		_flag_check_B_i_0(i) = 'F';
	}
	else if ((_flag_check_B_i_0(i)) == ('I')) {
		printf("There is a self dependence on check_B_i_0 at (%ld)\n",i);
		exit(-1);
	}
	
	return check_B_i_0(i);
}

static float reduce1(long N, long ip) {
	float reduceVar;
	long j;
	
	reduceVar = 0.0f;
	#define RP1(i,j) (k()) * (A(((i)),((j))))
	#define R1(i,j) reduceVar = (reduceVar) + (RP1((i),(j)))
	for (j = 0; j < N; j += 1) {
		R1(ip, j);
	}
	#undef RP1
	#undef R1
	return reduceVar;
}

static float eval_check_B_i_1(long i) {
	
	// Check the flags.
	if ((_flag_check_B_i_1(i)) == ('N')) {
		_flag_check_B_i_1(i) = 'I';
		check_B_i_1(i) = reduce1(N,i);
		_flag_check_B_i_1(i) = 'F';
	}
	else if ((_flag_check_B_i_1(i)) == ('I')) {
		printf("There is a self dependence on check_B_i_1 at (%ld)\n",i);
		exit(-1);
	}
	
	return check_B_i_1(i);
}

static float eval_check_B_i_inv(long i) {
	
	// Check the flags.
	if ((_flag_check_B_i_inv(i)) == ('N')) {
		_flag_check_B_i_inv(i) = 'I';
		check_B_i_inv(i) = ((eval_check_B_i_0(((i)))) - (eval_check_B_i_1(((i))))) / (eval_check_B_i_0(((i))));
		_flag_check_B_i_inv(i) = 'F';
	}
	else if ((_flag_check_B_i_inv(i)) == ('I')) {
		printf("There is a self dependence on check_B_i_inv at (%ld)\n",i);
		exit(-1);
	}
	
	return check_B_i_inv(i);
}

static float reduce2(long N, long jp) {
	float reduceVar;
	long i;
	
	reduceVar = 0.0f;
	#define RP2(i,j) eval_B(((i)),((j)))
	#define R2(i,j) reduceVar = (reduceVar) + (RP2((i),(j)))
	for (i = 0; i < N; i += 1) {
		R2(i, jp);
	}
	#undef RP2
	#undef R2
	return reduceVar;
}

static float eval_check_B_j_0(long j) {
	
	// Check the flags.
	if ((_flag_check_B_j_0(j)) == ('N')) {
		_flag_check_B_j_0(j) = 'I';
		check_B_j_0(j) = reduce2(N,j);
		_flag_check_B_j_0(j) = 'F';
	}
	else if ((_flag_check_B_j_0(j)) == ('I')) {
		printf("There is a self dependence on check_B_j_0 at (%ld)\n",j);
		exit(-1);
	}
	
	return check_B_j_0(j);
}

static float reduce3(long N, long jp) {
	float reduceVar;
	long i;
	
	reduceVar = 0.0f;
	#define RP3(i,j) (k()) * (A(((i)),((j))))
	#define R3(i,j) reduceVar = (reduceVar) + (RP3((i),(j)))
	for (i = 0; i < N; i += 1) {
		R3(i, jp);
	}
	#undef RP3
	#undef R3
	return reduceVar;
}

static float eval_check_B_j_1(long j) {
	
	// Check the flags.
	if ((_flag_check_B_j_1(j)) == ('N')) {
		_flag_check_B_j_1(j) = 'I';
		check_B_j_1(j) = reduce3(N,j);
		_flag_check_B_j_1(j) = 'F';
	}
	else if ((_flag_check_B_j_1(j)) == ('I')) {
		printf("There is a self dependence on check_B_j_1 at (%ld)\n",j);
		exit(-1);
	}
	
	return check_B_j_1(j);
}

static float eval_check_B_j_inv(long j) {
	
	// Check the flags.
	if ((_flag_check_B_j_inv(j)) == ('N')) {
		_flag_check_B_j_inv(j) = 'I';
		check_B_j_inv(j) = ((eval_check_B_j_0(((j)))) - (eval_check_B_j_1(((j))))) / (eval_check_B_j_0(((j))));
		_flag_check_B_j_inv(j) = 'F';
	}
	else if ((_flag_check_B_j_inv(j)) == ('I')) {
		printf("There is a self dependence on check_B_j_inv at (%ld)\n",j);
		exit(-1);
	}
	
	return check_B_j_inv(j);
}

void scalmat_aabft(long _local_N, float* _local_k, float** _local_A, float** _local_B, float* _local_check_B_i_0, float* _local_check_B_i_1, float* _local_check_B_i_inv, float* _local_check_B_j_0, float* _local_check_B_j_1, float* _local_check_B_j_inv) {
	long i;
	long j;
	
	// Copy arguments to the global variables.
	N = _local_N;
	k = _local_k;
	A = _local_A;
	B = _local_B;
	check_B_i_0 = _local_check_B_i_0;
	check_B_i_1 = _local_check_B_i_1;
	check_B_i_inv = _local_check_B_i_inv;
	check_B_j_0 = _local_check_B_j_0;
	check_B_j_1 = _local_check_B_j_1;
	check_B_j_inv = _local_check_B_j_inv;
	
	// Check parameter validity.
	if (!((-1 + N) >= (0))) {
		printf("The value of the parameters are invalid.\n");
		exit(-1);
	}
	
	// Allocate memory for local storage.
	
	// Allocate and initialize flag variables.
	_flag_B = (char*)(malloc((sizeof(char)) * (((-1 + N >= 0) ? (N*N) : 0))));
	mallocCheck(_flag_B,"_flag_B");
	memset(_flag_B,'N',((-1 + N >= 0) ? (N*N) : 0));
	_flag_check_B_i_0 = (char*)(malloc((sizeof(char)) * (((-1 + N >= 0) ? (N) : 0))));
	mallocCheck(_flag_check_B_i_0,"_flag_check_B_i_0");
	memset(_flag_check_B_i_0,'N',((-1 + N >= 0) ? (N) : 0));
	_flag_check_B_i_1 = (char*)(malloc((sizeof(char)) * (((-1 + N >= 0) ? (N) : 0))));
	mallocCheck(_flag_check_B_i_1,"_flag_check_B_i_1");
	memset(_flag_check_B_i_1,'N',((-1 + N >= 0) ? (N) : 0));
	_flag_check_B_i_inv = (char*)(malloc((sizeof(char)) * (((-1 + N >= 0) ? (N) : 0))));
	mallocCheck(_flag_check_B_i_inv,"_flag_check_B_i_inv");
	memset(_flag_check_B_i_inv,'N',((-1 + N >= 0) ? (N) : 0));
	_flag_check_B_j_0 = (char*)(malloc((sizeof(char)) * (((-1 + N >= 0) ? (N) : 0))));
	mallocCheck(_flag_check_B_j_0,"_flag_check_B_j_0");
	memset(_flag_check_B_j_0,'N',((-1 + N >= 0) ? (N) : 0));
	_flag_check_B_j_1 = (char*)(malloc((sizeof(char)) * (((-1 + N >= 0) ? (N) : 0))));
	mallocCheck(_flag_check_B_j_1,"_flag_check_B_j_1");
	memset(_flag_check_B_j_1,'N',((-1 + N >= 0) ? (N) : 0));
	_flag_check_B_j_inv = (char*)(malloc((sizeof(char)) * (((-1 + N >= 0) ? (N) : 0))));
	mallocCheck(_flag_check_B_j_inv,"_flag_check_B_j_inv");
	memset(_flag_check_B_j_inv,'N',((-1 + N >= 0) ? (N) : 0));
	
	// Evaluate all the outputs.
	#define S0(i,j) eval_B(i,j)
	for (i = 0; i < N; i += 1) {
		for (j = 0; j < N; j += 1) {
			S0(i, j);
		}
	}
	#undef S0
	#define S1(i) eval_check_B_i_0(i)
	for (i = 0; i < N; i += 1) {
		S1(i);
	}
	#undef S1
	#define S2(i) eval_check_B_i_1(i)
	for (i = 0; i < N; i += 1) {
		S2(i);
	}
	#undef S2
	#define S3(i) eval_check_B_i_inv(i)
	for (i = 0; i < N; i += 1) {
		S3(i);
	}
	#undef S3
	#define S4(j) eval_check_B_j_0(j)
	for (j = 0; j < N; j += 1) {
		S4(j);
	}
	#undef S4
	#define S5(j) eval_check_B_j_1(j)
	for (j = 0; j < N; j += 1) {
		S5(j);
	}
	#undef S5
	#define S6(j) eval_check_B_j_inv(j)
	for (j = 0; j < N; j += 1) {
		S6(j);
	}
	#undef S6
	
	// Free all allocated memory.
	free(_flag_B);
	free(_flag_check_B_i_0);
	free(_flag_check_B_i_1);
	free(_flag_check_B_i_inv);
	free(_flag_check_B_j_0);
	free(_flag_check_B_j_1);
	free(_flag_check_B_j_inv);
}


// Undefine the Memory and Function Macros
#undef k
#undef A
#undef B
#undef check_B_i_0
#undef check_B_i_1
#undef check_B_i_inv
#undef check_B_j_0
#undef check_B_j_1
#undef check_B_j_inv
#undef _flag_B
#undef _flag_check_B_i_0
#undef _flag_check_B_i_1
#undef _flag_check_B_i_inv
#undef _flag_check_B_j_0
#undef _flag_check_B_j_1
#undef _flag_check_B_j_inv
#undef ceild
#undef floord
#undef div
#undef max
#undef min
#undef mallocCheck
