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
static float** B;
static float** C;
static float* Inv_C_r;
static float* Inv_C_c;
static float* C_r_0;
static float* C_r_1;
static float* C_c_0;
static float* C_c_1;
static char* _flag_C;
static char* _flag_Inv_C_r;
static char* _flag_Inv_C_c;
static char* _flag_C_r_0;
static char* _flag_C_r_1;
static char* _flag_C_c_0;
static char* _flag_C_c_1;

// Memory Macros
#define A(i,j) A[i][j]
#define B(i,j) B[i][j]
#define C(i,j) C[i][j]
#define Inv_C_r(i) Inv_C_r[i]
#define Inv_C_c(j) Inv_C_c[j]
#define C_r_0(i) C_r_0[((-1 + N - i >= 0 && -1 + i >= 0) ? (i) : 0)]
#define C_r_1(i) C_r_1[((-1 + N - i >= 0 && -1 + i >= 0) ? (i) : 0)]
#define C_c_0(j) C_c_0[((-1 + N - j >= 0 && -1 + j >= 0) ? (j) : 0)]
#define C_c_1(j) C_c_1[((-1 + N - j >= 0 && -1 + j >= 0) ? (j) : 0)]
#define _flag_C(i,j) _flag_C[((-1 + i >= 0 && -1 + N - i >= 0 && -1 + j >= 0 && -1 + N - j >= 0) ? ((N * i + j)) : (j == 0 && -1 + i >= 0 && -1 + N - i >= 0 && -1 + N >= 0) ? (N * i) : (i == 0 && -1 + N - j >= 0 && -1 + N >= 0 && -1 + j >= 0) ? (j) : 0)]
#define _flag_Inv_C_r(i) _flag_Inv_C_r[((-1 + N - i >= 0 && -1 + i >= 0) ? (i) : 0)]
#define _flag_Inv_C_c(j) _flag_Inv_C_c[((-1 + N - j >= 0 && -1 + j >= 0) ? (j) : 0)]
#define _flag_C_r_0(i) _flag_C_r_0[((-1 + N - i >= 0 && -1 + i >= 0) ? (i) : 0)]
#define _flag_C_r_1(i) _flag_C_r_1[((-1 + N - i >= 0 && -1 + i >= 0) ? (i) : 0)]
#define _flag_C_c_0(j) _flag_C_c_0[((-1 + N - j >= 0 && -1 + j >= 0) ? (j) : 0)]
#define _flag_C_c_1(j) _flag_C_c_1[((-1 + N - j >= 0 && -1 + j >= 0) ? (j) : 0)]

// Function Declarations
static float reduce0(long N, long ip, long jp);
static float eval_C(long i, long j);
static float reduce1(long N, long ip);
static float eval_C_r_0(long i);
static float reduce2(long N, long ip);
static float eval_C_r_1(long i);
static float reduce3(long N, long jp);
static float eval_C_c_0(long j);
static float reduce4(long N, long jp);
static float eval_C_c_1(long j);
static float eval_Inv_C_r(long i);
static float eval_Inv_C_c(long j);
void matmult_aabft(long _local_N, float** _local_A, float** _local_B, float** _local_C, float* _local_Inv_C_r, float* _local_Inv_C_c);

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
	#define RP1(i,j) eval_C(i,j)
	#define R1(i,j) reduceVar = (reduceVar) + (RP1((i),(j)))
	for (j = 0; j < N; j += 1) {
		R1(ip, j);
	}
	#undef RP1
	#undef R1
	return reduceVar;
}

static float eval_C_r_0(long i) {
	
	// Check the flags.
	if ((_flag_C_r_0(i)) == ('N')) {
		_flag_C_r_0(i) = 'I';
		C_r_0(i) = reduce1(N,i);
		_flag_C_r_0(i) = 'F';
	}
	else if ((_flag_C_r_0(i)) == ('I')) {
		printf("There is a self dependence on C_r_0 at (%ld)\n",i);
		exit(-1);
	}
	
	return C_r_0(i);
}

static float reduce2(long N, long ip) {
	float reduceVar;
	long j;
	long k;
	
	reduceVar = 0.0f;
	#define RP2(i,j,k) (A(((i)),((k)))) * (B(((k)),((j))))
	#define R2(i,j,k) reduceVar = (reduceVar) + (RP2((i),(j),(k)))
	for (j = 0; j < N; j += 1) {
		for (k = 0; k < N; k += 1) {
			R2(ip, j, k);
		}
	}
	#undef RP2
	#undef R2
	return reduceVar;
}

static float eval_C_r_1(long i) {
	
	// Check the flags.
	if ((_flag_C_r_1(i)) == ('N')) {
		_flag_C_r_1(i) = 'I';
		C_r_1(i) = reduce2(N,i);
		_flag_C_r_1(i) = 'F';
	}
	else if ((_flag_C_r_1(i)) == ('I')) {
		printf("There is a self dependence on C_r_1 at (%ld)\n",i);
		exit(-1);
	}
	
	return C_r_1(i);
}

static float reduce3(long N, long jp) {
	float reduceVar;
	long i;
	
	reduceVar = 0.0f;
	#define RP3(i,j) eval_C(i,j)
	#define R3(i,j) reduceVar = (reduceVar) + (RP3((i),(j)))
	for (i = 0; i < N; i += 1) {
		R3(i, jp);
	}
	#undef RP3
	#undef R3
	return reduceVar;
}

static float eval_C_c_0(long j) {
	
	// Check the flags.
	if ((_flag_C_c_0(j)) == ('N')) {
		_flag_C_c_0(j) = 'I';
		C_c_0(j) = reduce3(N,j);
		_flag_C_c_0(j) = 'F';
	}
	else if ((_flag_C_c_0(j)) == ('I')) {
		printf("There is a self dependence on C_c_0 at (%ld)\n",j);
		exit(-1);
	}
	
	return C_c_0(j);
}

static float reduce4(long N, long jp) {
	float reduceVar;
	long i;
	long k;
	
	reduceVar = 0.0f;
	#define RP4(i,j,k) (A(((i)),((k)))) * (B(((k)),((j))))
	#define R4(i,j,k) reduceVar = (reduceVar) + (RP4((i),(j),(k)))
	for (i = 0; i < N; i += 1) {
		for (k = 0; k < N; k += 1) {
			R4(i, jp, k);
		}
	}
	#undef RP4
	#undef R4
	return reduceVar;
}

static float eval_C_c_1(long j) {
	
	// Check the flags.
	if ((_flag_C_c_1(j)) == ('N')) {
		_flag_C_c_1(j) = 'I';
		C_c_1(j) = reduce4(N,j);
		_flag_C_c_1(j) = 'F';
	}
	else if ((_flag_C_c_1(j)) == ('I')) {
		printf("There is a self dependence on C_c_1 at (%ld)\n",j);
		exit(-1);
	}
	
	return C_c_1(j);
}

static float eval_Inv_C_r(long i) {
	
	// Check the flags.
	if ((_flag_Inv_C_r(i)) == ('N')) {
		_flag_Inv_C_r(i) = 'I';
		Inv_C_r(i) = ((eval_C_r_0(i)) - (eval_C_r_1(i))) / (eval_C_r_0(i));
		_flag_Inv_C_r(i) = 'F';
	}
	else if ((_flag_Inv_C_r(i)) == ('I')) {
		printf("There is a self dependence on Inv_C_r at (%ld)\n",i);
		exit(-1);
	}
	
	return Inv_C_r(i);
}

static float eval_Inv_C_c(long j) {
	
	// Check the flags.
	if ((_flag_Inv_C_c(j)) == ('N')) {
		_flag_Inv_C_c(j) = 'I';
		Inv_C_c(j) = ((eval_C_c_0(j)) - (eval_C_c_1(j))) / (eval_C_c_0(j));
		_flag_Inv_C_c(j) = 'F';
	}
	else if ((_flag_Inv_C_c(j)) == ('I')) {
		printf("There is a self dependence on Inv_C_c at (%ld)\n",j);
		exit(-1);
	}
	
	return Inv_C_c(j);
}

void matmult_aabft(long _local_N, float** _local_A, float** _local_B, float** _local_C, float* _local_Inv_C_r, float* _local_Inv_C_c) {
	long i;
	long j;
	
	// Copy arguments to the global variables.
	N = _local_N;
	A = _local_A;
	B = _local_B;
	C = _local_C;
	Inv_C_r = _local_Inv_C_r;
	Inv_C_c = _local_Inv_C_c;
	
	// Check parameter validity.
	if (!((-1 + N) >= (0))) {
		printf("The value of the parameters are invalid.\n");
		exit(-1);
	}
	
	// Allocate memory for local storage.
	C_r_0 = (float*)(malloc((sizeof(float)) * (((-1 + N >= 0) ? (N) : 0))));
	mallocCheck(C_r_0,"C_r_0");
	C_r_1 = (float*)(malloc((sizeof(float)) * (((-1 + N >= 0) ? (N) : 0))));
	mallocCheck(C_r_1,"C_r_1");
	C_c_0 = (float*)(malloc((sizeof(float)) * (((-1 + N >= 0) ? (N) : 0))));
	mallocCheck(C_c_0,"C_c_0");
	C_c_1 = (float*)(malloc((sizeof(float)) * (((-1 + N >= 0) ? (N) : 0))));
	mallocCheck(C_c_1,"C_c_1");
	
	// Allocate and initialize flag variables.
	_flag_C = (char*)(malloc((sizeof(char)) * (((-1 + N >= 0) ? (N*N) : 0))));
	mallocCheck(_flag_C,"_flag_C");
	memset(_flag_C,'N',((-1 + N >= 0) ? (N*N) : 0));
	_flag_Inv_C_r = (char*)(malloc((sizeof(char)) * (((-1 + N >= 0) ? (N) : 0))));
	mallocCheck(_flag_Inv_C_r,"_flag_Inv_C_r");
	memset(_flag_Inv_C_r,'N',((-1 + N >= 0) ? (N) : 0));
	_flag_Inv_C_c = (char*)(malloc((sizeof(char)) * (((-1 + N >= 0) ? (N) : 0))));
	mallocCheck(_flag_Inv_C_c,"_flag_Inv_C_c");
	memset(_flag_Inv_C_c,'N',((-1 + N >= 0) ? (N) : 0));
	_flag_C_r_0 = (char*)(malloc((sizeof(char)) * (((-1 + N >= 0) ? (N) : 0))));
	mallocCheck(_flag_C_r_0,"_flag_C_r_0");
	memset(_flag_C_r_0,'N',((-1 + N >= 0) ? (N) : 0));
	_flag_C_r_1 = (char*)(malloc((sizeof(char)) * (((-1 + N >= 0) ? (N) : 0))));
	mallocCheck(_flag_C_r_1,"_flag_C_r_1");
	memset(_flag_C_r_1,'N',((-1 + N >= 0) ? (N) : 0));
	_flag_C_c_0 = (char*)(malloc((sizeof(char)) * (((-1 + N >= 0) ? (N) : 0))));
	mallocCheck(_flag_C_c_0,"_flag_C_c_0");
	memset(_flag_C_c_0,'N',((-1 + N >= 0) ? (N) : 0));
	_flag_C_c_1 = (char*)(malloc((sizeof(char)) * (((-1 + N >= 0) ? (N) : 0))));
	mallocCheck(_flag_C_c_1,"_flag_C_c_1");
	memset(_flag_C_c_1,'N',((-1 + N >= 0) ? (N) : 0));
	
	// Evaluate all the outputs.
	#define S0(i,j) eval_C(i,j)
	for (i = 0; i < N; i += 1) {
		for (j = 0; j < N; j += 1) {
			S0(i, j);
		}
	}
	#undef S0
	#define S1(i) eval_Inv_C_r(i)
	for (i = 0; i < N; i += 1) {
		S1(i);
	}
	#undef S1
	#define S2(j) eval_Inv_C_c(j)
	for (j = 0; j < N; j += 1) {
		S2(j);
	}
	#undef S2
	
	// Free all allocated memory.
	free(C_r_0);
	free(C_r_1);
	free(C_c_0);
	free(C_c_1);
	free(_flag_C);
	free(_flag_Inv_C_r);
	free(_flag_Inv_C_c);
	free(_flag_C_r_0);
	free(_flag_C_r_1);
	free(_flag_C_c_0);
	free(_flag_C_c_1);
}


// Undefine the Memory and Function Macros
#undef A
#undef B
#undef C
#undef Inv_C_r
#undef Inv_C_c
#undef C_r_0
#undef C_r_1
#undef C_c_0
#undef C_c_1
#undef _flag_C
#undef _flag_Inv_C_r
#undef _flag_Inv_C_c
#undef _flag_C_r_0
#undef _flag_C_r_1
#undef _flag_C_c_0
#undef _flag_C_c_1
#undef ceild
#undef floord
#undef div
#undef max
#undef min
#undef mallocCheck
