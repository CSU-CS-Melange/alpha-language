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
static float* check_C_i_inv;
static float* check_C_j_inv;
static float* check_C_i_0;
static float* check_C_i_1;
static float* check_C_j_0;
static float* check_C_j_1;
static float* check_C_i_1_NR;
static float* check_C_j_1_NR;
static char* _flag_C;
static char* _flag_check_C_i_inv;
static char* _flag_check_C_j_inv;
static char* _flag_check_C_i_0;
static char* _flag_check_C_i_1;
static char* _flag_check_C_j_0;
static char* _flag_check_C_j_1;
static char* _flag_check_C_i_1_NR;
static char* _flag_check_C_j_1_NR;

// Memory Macros
#define A(i,j) A[i][j]
#define B(i,j) B[i][j]
#define C(i,j) C[i][j]
#define check_C_i_inv(i) check_C_i_inv[i]
#define check_C_j_inv(j) check_C_j_inv[j]
#define check_C_i_0(i) check_C_i_0[((-1 + N - i >= 0 && -1 + i >= 0) ? (i) : 0)]
#define check_C_i_1(i) check_C_i_1[((-1 + N - i >= 0 && -1 + i >= 0) ? (i) : 0)]
#define check_C_j_0(j) check_C_j_0[((-1 + N - j >= 0 && -1 + j >= 0) ? (j) : 0)]
#define check_C_j_1(j) check_C_j_1[((-1 + N - j >= 0 && -1 + j >= 0) ? (j) : 0)]
#define check_C_i_1_NR(i0,i1) check_C_i_1_NR[((-1 + i0 >= 0 && -1 + N - i0 >= 0 && -1 + i1 >= 0 && -1 + N - i1 >= 0) ? ((N * i0 + i1)) : (i1 == 0 && -1 + i0 >= 0 && -1 + N - i0 >= 0 && -1 + N >= 0) ? (N * i0) : (i0 == 0 && -1 + N - i1 >= 0 && -1 + N >= 0 && -1 + i1 >= 0) ? (i1) : 0)]
#define check_C_j_1_NR(i0,i1) check_C_j_1_NR[((-1 + i0 >= 0 && -1 + N - i0 >= 0 && -1 + i1 >= 0 && -1 + N - i1 >= 0) ? ((N * i0 + i1)) : (i1 == 0 && -1 + i0 >= 0 && -1 + N - i0 >= 0 && -1 + N >= 0) ? (N * i0) : (i0 == 0 && -1 + N - i1 >= 0 && -1 + N >= 0 && -1 + i1 >= 0) ? (i1) : 0)]
#define _flag_C(i,j) _flag_C[((-1 + i >= 0 && -1 + N - i >= 0 && -1 + j >= 0 && -1 + N - j >= 0) ? ((N * i + j)) : (j == 0 && -1 + i >= 0 && -1 + N - i >= 0 && -1 + N >= 0) ? (N * i) : (i == 0 && -1 + N - j >= 0 && -1 + N >= 0 && -1 + j >= 0) ? (j) : 0)]
#define _flag_check_C_i_inv(i) _flag_check_C_i_inv[((-1 + N - i >= 0 && -1 + i >= 0) ? (i) : 0)]
#define _flag_check_C_j_inv(j) _flag_check_C_j_inv[((-1 + N - j >= 0 && -1 + j >= 0) ? (j) : 0)]
#define _flag_check_C_i_0(i) _flag_check_C_i_0[((-1 + N - i >= 0 && -1 + i >= 0) ? (i) : 0)]
#define _flag_check_C_i_1(i) _flag_check_C_i_1[((-1 + N - i >= 0 && -1 + i >= 0) ? (i) : 0)]
#define _flag_check_C_j_0(j) _flag_check_C_j_0[((-1 + N - j >= 0 && -1 + j >= 0) ? (j) : 0)]
#define _flag_check_C_j_1(j) _flag_check_C_j_1[((-1 + N - j >= 0 && -1 + j >= 0) ? (j) : 0)]
#define _flag_check_C_i_1_NR(i0,i1) _flag_check_C_i_1_NR[((-1 + i0 >= 0 && -1 + N - i0 >= 0 && -1 + i1 >= 0 && -1 + N - i1 >= 0) ? ((N * i0 + i1)) : (i1 == 0 && -1 + i0 >= 0 && -1 + N - i0 >= 0 && -1 + N >= 0) ? (N * i0) : (i0 == 0 && -1 + N - i1 >= 0 && -1 + N >= 0 && -1 + i1 >= 0) ? (i1) : 0)]
#define _flag_check_C_j_1_NR(i0,i1) _flag_check_C_j_1_NR[((-1 + i0 >= 0 && -1 + N - i0 >= 0 && -1 + i1 >= 0 && -1 + N - i1 >= 0) ? ((N * i0 + i1)) : (i1 == 0 && -1 + i0 >= 0 && -1 + N - i0 >= 0 && -1 + N >= 0) ? (N * i0) : (i0 == 0 && -1 + N - i1 >= 0 && -1 + N >= 0 && -1 + i1 >= 0) ? (i1) : 0)]

// Function Declarations
static float reduce0(long N, long ip, long jp);
static float eval_C(long i, long j);
static float reduce1(long N, long ip);
static float eval_check_C_i_0(long i);
static float reduce2(long N, long ip);
static float eval_check_C_i_1(long i);
static float eval_check_C_i_inv(long i);
static float reduce3(long N, long jp);
static float eval_check_C_j_0(long j);
static float reduce4(long N, long jp);
static float eval_check_C_j_1(long j);
static float eval_check_C_j_inv(long j);
static float reduce5(long N, long i0, long i1);
static float eval_check_C_i_1_NR(long i0, long i1);
static float reduce6(long N, long i0, long i1);
static float eval_check_C_j_1_NR(long i0, long i1);
void matmult_aabft(long _local_N, float** _local_A, float** _local_B, float** _local_C, float* _local_check_C_i_inv, float* _local_check_C_j_inv);

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

static float reduce1(long N, long ip) {
	float reduceVar;
	long j;
	
	reduceVar = 0.0f;
	#define RP1(i,j) eval_C(((i)),((j)))
	#define R1(i,j) reduceVar = (reduceVar) + (RP1((i),(j)))
	for (j = 0; j < N; j += 1) {
		R1(ip, j);
	}
	#undef RP1
	#undef R1
	return reduceVar;
}

static float eval_check_C_i_0(long i) {
	
	// Check the flags.
	if ((_flag_check_C_i_0(i)) == ('N')) {
		_flag_check_C_i_0(i) = 'I';
		check_C_i_0(i) = reduce1(N,i);
		_flag_check_C_i_0(i) = 'F';
	}
	else if ((_flag_check_C_i_0(i)) == ('I')) {
		printf("There is a self dependence on check_C_i_0 at (%ld)\n",i);
		exit(-1);
	}
	
	return check_C_i_0(i);
}

static float reduce2(long N, long ip) {
	float reduceVar;
	long j;
	
	reduceVar = 0.0f;
	#define RP2(i,j) eval_check_C_i_1_NR(((i)),((j)))
	#define R2(i,j) reduceVar = (reduceVar) + (RP2((i),(j)))
	for (j = 0; j < N; j += 1) {
		R2(ip, j);
	}
	#undef RP2
	#undef R2
	return reduceVar;
}

static float eval_check_C_i_1(long i) {
	
	// Check the flags.
	if ((_flag_check_C_i_1(i)) == ('N')) {
		_flag_check_C_i_1(i) = 'I';
		check_C_i_1(i) = reduce2(N,i);
		_flag_check_C_i_1(i) = 'F';
	}
	else if ((_flag_check_C_i_1(i)) == ('I')) {
		printf("There is a self dependence on check_C_i_1 at (%ld)\n",i);
		exit(-1);
	}
	
	return check_C_i_1(i);
}

static float eval_check_C_i_inv(long i) {
	
	// Check the flags.
	if ((_flag_check_C_i_inv(i)) == ('N')) {
		_flag_check_C_i_inv(i) = 'I';
		check_C_i_inv(i) = ((eval_check_C_i_0(((i)))) - (eval_check_C_i_1(((i))))) / (eval_check_C_i_0(((i))));
		_flag_check_C_i_inv(i) = 'F';
	}
	else if ((_flag_check_C_i_inv(i)) == ('I')) {
		printf("There is a self dependence on check_C_i_inv at (%ld)\n",i);
		exit(-1);
	}
	
	return check_C_i_inv(i);
}

static float reduce3(long N, long jp) {
	float reduceVar;
	long i;
	
	reduceVar = 0.0f;
	#define RP3(i,j) eval_C(((i)),((j)))
	#define R3(i,j) reduceVar = (reduceVar) + (RP3((i),(j)))
	for (i = 0; i < N; i += 1) {
		R3(i, jp);
	}
	#undef RP3
	#undef R3
	return reduceVar;
}

static float eval_check_C_j_0(long j) {
	
	// Check the flags.
	if ((_flag_check_C_j_0(j)) == ('N')) {
		_flag_check_C_j_0(j) = 'I';
		check_C_j_0(j) = reduce3(N,j);
		_flag_check_C_j_0(j) = 'F';
	}
	else if ((_flag_check_C_j_0(j)) == ('I')) {
		printf("There is a self dependence on check_C_j_0 at (%ld)\n",j);
		exit(-1);
	}
	
	return check_C_j_0(j);
}

static float reduce4(long N, long jp) {
	float reduceVar;
	long i;
	
	reduceVar = 0.0f;
	#define RP4(i,j) eval_check_C_j_1_NR(((i)),((j)))
	#define R4(i,j) reduceVar = (reduceVar) + (RP4((i),(j)))
	for (i = 0; i < N; i += 1) {
		R4(i, jp);
	}
	#undef RP4
	#undef R4
	return reduceVar;
}

static float eval_check_C_j_1(long j) {
	
	// Check the flags.
	if ((_flag_check_C_j_1(j)) == ('N')) {
		_flag_check_C_j_1(j) = 'I';
		check_C_j_1(j) = reduce4(N,j);
		_flag_check_C_j_1(j) = 'F';
	}
	else if ((_flag_check_C_j_1(j)) == ('I')) {
		printf("There is a self dependence on check_C_j_1 at (%ld)\n",j);
		exit(-1);
	}
	
	return check_C_j_1(j);
}

static float eval_check_C_j_inv(long j) {
	
	// Check the flags.
	if ((_flag_check_C_j_inv(j)) == ('N')) {
		_flag_check_C_j_inv(j) = 'I';
		check_C_j_inv(j) = ((eval_check_C_j_0(((j)))) - (eval_check_C_j_1(((j))))) / (eval_check_C_j_0(((j))));
		_flag_check_C_j_inv(j) = 'F';
	}
	else if ((_flag_check_C_j_inv(j)) == ('I')) {
		printf("There is a self dependence on check_C_j_inv at (%ld)\n",j);
		exit(-1);
	}
	
	return check_C_j_inv(j);
}

static float reduce5(long N, long i0, long i1) {
	float reduceVar;
	long k;
	
	reduceVar = 0.0f;
	#define RP5(i,j,k) (A(((i)),((k)))) * (B(((k)),((j))))
	#define R5(i,j,k) reduceVar = (reduceVar) + (RP5((i),(j),(k)))
	for (k = 0; k < N; k += 1) {
		R5(i0, i1, k);
	}
	#undef RP5
	#undef R5
	return reduceVar;
}

static float eval_check_C_i_1_NR(long i0, long i1) {
	
	// Check the flags.
	if ((_flag_check_C_i_1_NR(i0,i1)) == ('N')) {
		_flag_check_C_i_1_NR(i0,i1) = 'I';
		check_C_i_1_NR(i0,i1) = reduce5(N,i0,i1);
		_flag_check_C_i_1_NR(i0,i1) = 'F';
	}
	else if ((_flag_check_C_i_1_NR(i0,i1)) == ('I')) {
		printf("There is a self dependence on check_C_i_1_NR at (%ld,%ld)\n",i0,i1);
		exit(-1);
	}
	
	return check_C_i_1_NR(i0,i1);
}

static float reduce6(long N, long i0, long i1) {
	float reduceVar;
	long k;
	
	reduceVar = 0.0f;
	#define RP6(i,j,k) (A(((i)),((k)))) * (B(((k)),((j))))
	#define R6(i,j,k) reduceVar = (reduceVar) + (RP6((i),(j),(k)))
	for (k = 0; k < N; k += 1) {
		R6(i0, i1, k);
	}
	#undef RP6
	#undef R6
	return reduceVar;
}

static float eval_check_C_j_1_NR(long i0, long i1) {
	
	// Check the flags.
	if ((_flag_check_C_j_1_NR(i0,i1)) == ('N')) {
		_flag_check_C_j_1_NR(i0,i1) = 'I';
		check_C_j_1_NR(i0,i1) = reduce6(N,i0,i1);
		_flag_check_C_j_1_NR(i0,i1) = 'F';
	}
	else if ((_flag_check_C_j_1_NR(i0,i1)) == ('I')) {
		printf("There is a self dependence on check_C_j_1_NR at (%ld,%ld)\n",i0,i1);
		exit(-1);
	}
	
	return check_C_j_1_NR(i0,i1);
}

void matmult_aabft(long _local_N, float** _local_A, float** _local_B, float** _local_C, float* _local_check_C_i_inv, float* _local_check_C_j_inv) {
	long i;
	long j;
	
	// Copy arguments to the global variables.
	N = _local_N;
	A = _local_A;
	B = _local_B;
	C = _local_C;
	check_C_i_inv = _local_check_C_i_inv;
	check_C_j_inv = _local_check_C_j_inv;
	
	// Check parameter validity.
	if (!((-1 + N) >= (0))) {
		printf("The value of the parameters are invalid.\n");
		exit(-1);
	}
	
	// Allocate memory for local storage.
	check_C_i_0 = (float*)(malloc((sizeof(float)) * (((-1 + N >= 0) ? (N) : 0))));
	mallocCheck(check_C_i_0,"check_C_i_0");
	check_C_i_1 = (float*)(malloc((sizeof(float)) * (((-1 + N >= 0) ? (N) : 0))));
	mallocCheck(check_C_i_1,"check_C_i_1");
	check_C_j_0 = (float*)(malloc((sizeof(float)) * (((-1 + N >= 0) ? (N) : 0))));
	mallocCheck(check_C_j_0,"check_C_j_0");
	check_C_j_1 = (float*)(malloc((sizeof(float)) * (((-1 + N >= 0) ? (N) : 0))));
	mallocCheck(check_C_j_1,"check_C_j_1");
	check_C_i_1_NR = (float*)(malloc((sizeof(float)) * (((-1 + N >= 0) ? (N*N) : 0))));
	mallocCheck(check_C_i_1_NR,"check_C_i_1_NR");
	check_C_j_1_NR = (float*)(malloc((sizeof(float)) * (((-1 + N >= 0) ? (N*N) : 0))));
	mallocCheck(check_C_j_1_NR,"check_C_j_1_NR");
	
	// Allocate and initialize flag variables.
	_flag_C = (char*)(malloc((sizeof(char)) * (((-1 + N >= 0) ? (N*N) : 0))));
	mallocCheck(_flag_C,"_flag_C");
	memset(_flag_C,'N',((-1 + N >= 0) ? (N*N) : 0));
	_flag_check_C_i_inv = (char*)(malloc((sizeof(char)) * (((-1 + N >= 0) ? (N) : 0))));
	mallocCheck(_flag_check_C_i_inv,"_flag_check_C_i_inv");
	memset(_flag_check_C_i_inv,'N',((-1 + N >= 0) ? (N) : 0));
	_flag_check_C_j_inv = (char*)(malloc((sizeof(char)) * (((-1 + N >= 0) ? (N) : 0))));
	mallocCheck(_flag_check_C_j_inv,"_flag_check_C_j_inv");
	memset(_flag_check_C_j_inv,'N',((-1 + N >= 0) ? (N) : 0));
	_flag_check_C_i_0 = (char*)(malloc((sizeof(char)) * (((-1 + N >= 0) ? (N) : 0))));
	mallocCheck(_flag_check_C_i_0,"_flag_check_C_i_0");
	memset(_flag_check_C_i_0,'N',((-1 + N >= 0) ? (N) : 0));
	_flag_check_C_i_1 = (char*)(malloc((sizeof(char)) * (((-1 + N >= 0) ? (N) : 0))));
	mallocCheck(_flag_check_C_i_1,"_flag_check_C_i_1");
	memset(_flag_check_C_i_1,'N',((-1 + N >= 0) ? (N) : 0));
	_flag_check_C_j_0 = (char*)(malloc((sizeof(char)) * (((-1 + N >= 0) ? (N) : 0))));
	mallocCheck(_flag_check_C_j_0,"_flag_check_C_j_0");
	memset(_flag_check_C_j_0,'N',((-1 + N >= 0) ? (N) : 0));
	_flag_check_C_j_1 = (char*)(malloc((sizeof(char)) * (((-1 + N >= 0) ? (N) : 0))));
	mallocCheck(_flag_check_C_j_1,"_flag_check_C_j_1");
	memset(_flag_check_C_j_1,'N',((-1 + N >= 0) ? (N) : 0));
	_flag_check_C_i_1_NR = (char*)(malloc((sizeof(char)) * (((-1 + N >= 0) ? (N*N) : 0))));
	mallocCheck(_flag_check_C_i_1_NR,"_flag_check_C_i_1_NR");
	memset(_flag_check_C_i_1_NR,'N',((-1 + N >= 0) ? (N*N) : 0));
	_flag_check_C_j_1_NR = (char*)(malloc((sizeof(char)) * (((-1 + N >= 0) ? (N*N) : 0))));
	mallocCheck(_flag_check_C_j_1_NR,"_flag_check_C_j_1_NR");
	memset(_flag_check_C_j_1_NR,'N',((-1 + N >= 0) ? (N*N) : 0));
	
	// Evaluate all the outputs.
	#define S0(i,j) eval_C(i,j)
	for (i = 0; i < N; i += 1) {
		for (j = 0; j < N; j += 1) {
			S0(i, j);
		}
	}
	#undef S0
	#define S1(i) eval_check_C_i_inv(i)
	for (i = 0; i < N; i += 1) {
		S1(i);
	}
	#undef S1
	#define S2(j) eval_check_C_j_inv(j)
	for (j = 0; j < N; j += 1) {
		S2(j);
	}
	#undef S2
	
	// Free all allocated memory.
	free(check_C_i_0);
	free(check_C_i_1);
	free(check_C_j_0);
	free(check_C_j_1);
	free(check_C_i_1_NR);
	free(check_C_j_1_NR);
	free(_flag_C);
	free(_flag_check_C_i_inv);
	free(_flag_check_C_j_inv);
	free(_flag_check_C_i_0);
	free(_flag_check_C_i_1);
	free(_flag_check_C_j_0);
	free(_flag_check_C_j_1);
	free(_flag_check_C_i_1_NR);
	free(_flag_check_C_j_1_NR);
}


// Undefine the Memory and Function Macros
#undef A
#undef B
#undef C
#undef check_C_i_inv
#undef check_C_j_inv
#undef check_C_i_0
#undef check_C_i_1
#undef check_C_j_0
#undef check_C_j_1
#undef check_C_i_1_NR
#undef check_C_j_1_NR
#undef _flag_C
#undef _flag_check_C_i_inv
#undef _flag_check_C_j_inv
#undef _flag_check_C_i_0
#undef _flag_check_C_i_1
#undef _flag_check_C_j_0
#undef _flag_check_C_j_1
#undef _flag_check_C_i_1_NR
#undef _flag_check_C_j_1_NR
#undef ceild
#undef floord
#undef div
#undef max
#undef min
#undef mallocCheck