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
static float** L;
static float** U;
static float* L_c_0;
static float* U_r_0;
static float* A_c;
static float* A_r;
static float** A_f;
static float* U_NR;
static float* L_NR;
static float* A_f_NR;
static float* A_f_NR2;
static char* _flag_L;
static char* _flag_U;
static char* _flag_L_c_0;
static char* _flag_U_r_0;
static char* _flag_A_c;
static char* _flag_A_r;
static char* _flag_A_f;
static char* _flag_U_NR;
static char* _flag_L_NR;
static char* _flag_A_f_NR;
static char* _flag_A_f_NR2;

// Memory Macros
#define A(i,j) A[i][j]
#define L(i,j) L[i][j]
#define U(i,j) U[i][j]
#define L_c_0(j) L_c_0[j]
#define U_r_0(i) U_r_0[i]
#define A_c(j) A_c[j]
#define A_r(i) A_r[i]
#define A_f(i,j) A_f[i][j]
#define U_NR(i,j) U_NR[((-2 + i >= 0 && -1 - i + j >= 0 && -1 + N - j >= 0) ? ((((-2 * N + (-1 + 2 * N) * i - i*i) + 2 * j))/2) : (-i + j == 0 && -2 + i >= 0 && -1 + N - i >= 0) ? (((-2 * N + (1 + 2 * N) * i - i*i))/2) : (-1 + i == 0 && -1 + N - j >= 0 && -2 + j >= 0) ? ((-i + j)) : 0)]
#define L_NR(i,j) L_NR[((-2 + i >= 0 && -1 + N - i >= 0 && -2 + j >= 0 && i - j >= 0) ? ((((-2 - i + i*i) + 2 * j))/2) : (-1 + j == 0 && -2 + i >= 0 && -1 + N - i >= 0) ? (((-i + i*i))/2) : 0)]
#define A_f_NR(i,j) A_f_NR[((-N + i == 0 && -1 + N - j >= 0 && -1 + j >= 0) ? (j) : 0)]
#define A_f_NR2(i,j) A_f_NR2[((-N + j == 0 && -1 + i >= 0 && -1 + N - i >= 0) ? (i) : 0)]
#define _flag_L(i,j) _flag_L[((-1 + i >= 0 && -1 + N - i >= 0 && -1 + j >= 0 && i - j >= 0) ? ((((i + i*i) + 2 * j))/2) : (j == 0 && -1 + i >= 0 && -1 + N - i >= 0) ? (((i + i*i))/2) : 0)]
#define _flag_U(i,j) _flag_U[((-1 + i >= 0 && -1 - i + j >= 0 && -1 + N - j >= 0) ? (((((-1 + 2 * N) * i - i*i) + 2 * j))/2) : (-i + j == 0 && -1 + i >= 0 && -1 + N - i >= 0) ? ((((1 + 2 * N) * i - i*i))/2) : (i == 0 && -1 + N - j >= 0 && -1 + j >= 0) ? ((-i + j)) : 0)]
#define _flag_L_c_0(j) _flag_L_c_0[((-1 + N - j >= 0 && -1 + j >= 0) ? (j) : 0)]
#define _flag_U_r_0(i) _flag_U_r_0[((-1 + N - i >= 0 && -1 + i >= 0) ? (i) : 0)]
#define _flag_A_c(j) _flag_A_c[((-1 + N - j >= 0 && -1 + j >= 0) ? (j) : 0)]
#define _flag_A_r(i) _flag_A_r[((-1 + N - i >= 0 && -1 + i >= 0) ? (i) : 0)]
#define _flag_A_f(i,j) _flag_A_f[((-1 + i >= 0 && N - i >= 0 && -1 + j >= 0 && N - j >= 0) ? (((1 + N) * i + j)) : (j == 0 && -1 + i >= 0 && N - i >= 0 && N >= 0) ? ((1 + N) * i) : (i == 0 && N - j >= 0 && N >= 0 && -1 + j >= 0) ? (j) : 0)]
#define _flag_U_NR(i,j) _flag_U_NR[((-2 + i >= 0 && -1 - i + j >= 0 && -1 + N - j >= 0) ? ((((-2 * N + (-1 + 2 * N) * i - i*i) + 2 * j))/2) : (-i + j == 0 && -2 + i >= 0 && -1 + N - i >= 0) ? (((-2 * N + (1 + 2 * N) * i - i*i))/2) : (-1 + i == 0 && -1 + N - j >= 0 && -2 + j >= 0) ? ((-i + j)) : 0)]
#define _flag_L_NR(i,j) _flag_L_NR[((-2 + i >= 0 && -1 + N - i >= 0 && -2 + j >= 0 && i - j >= 0) ? ((((-2 - i + i*i) + 2 * j))/2) : (-1 + j == 0 && -2 + i >= 0 && -1 + N - i >= 0) ? (((-i + i*i))/2) : 0)]
#define _flag_A_f_NR(i,j) _flag_A_f_NR[((-N + i == 0 && -1 + N - j >= 0 && -1 + j >= 0) ? (j) : 0)]
#define _flag_A_f_NR2(i,j) _flag_A_f_NR2[((-N + j == 0 && -1 + i >= 0 && -1 + N - i >= 0) ? (i) : 0)]

// Function Declarations
static float eval_U(long i, long j);
static float eval_L(long i, long j);
static float reduce0(long N, long jp);
static float eval_L_c_0(long j);
static float reduce1(long N, long i);
static float eval_U_r_0(long i);
static float reduce2(long N, long jp);
static float eval_A_c(long j);
static float reduce3(long N, long i);
static float eval_A_r(long i);
static float eval_A_f(long i, long j);
static float reduce4(long N, long ip, long jp);
static float eval_U_NR(long i, long j);
static float reduce5(long N, long ip, long jp);
static float eval_L_NR(long i, long j);
static float reduce6(long N, long ip, long jp);
static float eval_A_f_NR(long i, long j);
static float reduce7(long N, long ip, long jp);
static float eval_A_f_NR2(long i, long j);
void lud_abft(long _local_N, float** _local_A, float** _local_L, float** _local_U, float* _local_L_c_0, float* _local_U_r_0, float* _local_A_c, float* _local_A_r, float** _local_A_f);

static float eval_U(long i, long j) {
	
	// Check the flags.
	if ((_flag_U(i,j)) == ('N')) {
		_flag_U(i,j) = 'I';
		U(i,j) = (((i) == (0)) && ((-1 + N) >= (0))) ? (A(i,j)) : ((A(i,j)) - (eval_U_NR(i,j)));
		_flag_U(i,j) = 'F';
	}
	else if ((_flag_U(i,j)) == ('I')) {
		printf("There is a self dependence on U at (%ld,%ld)\n",i,j);
		exit(-1);
	}
	
	return U(i,j);
}

static float eval_L(long i, long j) {
	
	// Check the flags.
	if ((_flag_L(i,j)) == ('N')) {
		_flag_L(i,j) = 'I';
		L(i,j) = (((j) == (0)) && ((-1 + N) >= (0))) ? ((A(i,j)) / (eval_U(((j)),((j))))) : (((A(i,j)) - (eval_L_NR(i,j))) / (eval_U(((j)),((j)))));
		_flag_L(i,j) = 'F';
	}
	else if ((_flag_L(i,j)) == ('I')) {
		printf("There is a self dependence on L at (%ld,%ld)\n",i,j);
		exit(-1);
	}
	
	return L(i,j);
}

static float reduce0(long N, long jp) {
	float reduceVar;
	long i;
	
	reduceVar = 0.0f;
	#define RP0(j,i) eval_L(((i)),((j)))
	#define R0(j,i) reduceVar = (reduceVar) + (RP0((j),(i)))
	for (i = jp; i < N; i += 1) {
		R0(jp, i);
	}
	#undef RP0
	#undef R0
	return reduceVar;
}

static float eval_L_c_0(long j) {
	
	// Check the flags.
	if ((_flag_L_c_0(j)) == ('N')) {
		_flag_L_c_0(j) = 'I';
		L_c_0(j) = reduce0(N,j);
		_flag_L_c_0(j) = 'F';
	}
	else if ((_flag_L_c_0(j)) == ('I')) {
		printf("There is a self dependence on L_c_0 at (%ld)\n",j);
		exit(-1);
	}
	
	return L_c_0(j);
}

static float reduce1(long N, long i) {
	float reduceVar;
	long i1;
	
	reduceVar = 0.0f;
	#define RP1(i0,i1) eval_U(i0,i1)
	#define R1(i0,i1) reduceVar = (reduceVar) + (RP1((i0),(i1)))
	for (i1 = i; i1 < N; i1 += 1) {
		R1(i, i1);
	}
	#undef RP1
	#undef R1
	return reduceVar;
}

static float eval_U_r_0(long i) {
	
	// Check the flags.
	if ((_flag_U_r_0(i)) == ('N')) {
		_flag_U_r_0(i) = 'I';
		U_r_0(i) = reduce1(N,i);
		_flag_U_r_0(i) = 'F';
	}
	else if ((_flag_U_r_0(i)) == ('I')) {
		printf("There is a self dependence on U_r_0 at (%ld)\n",i);
		exit(-1);
	}
	
	return U_r_0(i);
}

static float reduce2(long N, long jp) {
	float reduceVar;
	long i;
	
	reduceVar = 0.0f;
	#define RP2(j,i) A(((i)),((j)))
	#define R2(j,i) reduceVar = (reduceVar) + (RP2((j),(i)))
	for (i = 0; i < N; i += 1) {
		R2(jp, i);
	}
	#undef RP2
	#undef R2
	return reduceVar;
}

static float eval_A_c(long j) {
	
	// Check the flags.
	if ((_flag_A_c(j)) == ('N')) {
		_flag_A_c(j) = 'I';
		A_c(j) = reduce2(N,j);
		_flag_A_c(j) = 'F';
	}
	else if ((_flag_A_c(j)) == ('I')) {
		printf("There is a self dependence on A_c at (%ld)\n",j);
		exit(-1);
	}
	
	return A_c(j);
}

static float reduce3(long N, long i) {
	float reduceVar;
	long i1;
	
	reduceVar = 0.0f;
	#define RP3(i0,i1) A(i0,i1)
	#define R3(i0,i1) reduceVar = (reduceVar) + (RP3((i0),(i1)))
	for (i1 = 0; i1 < N; i1 += 1) {
		R3(i, i1);
	}
	#undef RP3
	#undef R3
	return reduceVar;
}

static float eval_A_r(long i) {
	
	// Check the flags.
	if ((_flag_A_r(i)) == ('N')) {
		_flag_A_r(i) = 'I';
		A_r(i) = reduce3(N,i);
		_flag_A_r(i) = 'F';
	}
	else if ((_flag_A_r(i)) == ('I')) {
		printf("There is a self dependence on A_r at (%ld)\n",i);
		exit(-1);
	}
	
	return A_r(i);
}

static float eval_A_f(long i, long j) {
	
	// Check the flags.
	if ((_flag_A_f(i,j)) == ('N')) {
		_flag_A_f(i,j) = 'I';
		A_f(i,j) = (((-N + i) == (0)) && ((-1 + N) >= (0))) ? (eval_A_f_NR(i,j)) : ((((-N + j) == (0)) && ((-1 + N) >= (0))) ? (eval_A_f_NR2(i,j)) : (((((-1 + N) >= (0)) && ((-1 + N - i) >= (0))) && ((-1 + N - j) >= (0))) ? (A(i,j)) : (A((N - (i)),(N - (j))))));
		_flag_A_f(i,j) = 'F';
	}
	else if ((_flag_A_f(i,j)) == ('I')) {
		printf("There is a self dependence on A_f at (%ld,%ld)\n",i,j);
		exit(-1);
	}
	
	return A_f(i,j);
}

static float reduce4(long N, long ip, long jp) {
	float reduceVar;
	long k;
	
	reduceVar = 0.0f;
	#define RP4(i,j,k) (eval_L(((i)),((k)))) * (eval_U(((k)),((j))))
	#define R4(i,j,k) reduceVar = (reduceVar) + (RP4((i),(j),(k)))
	for (k = 0; k < ip; k += 1) {
		R4(ip, jp, k);
	}
	#undef RP4
	#undef R4
	return reduceVar;
}

static float eval_U_NR(long i, long j) {
	
	// Check the flags.
	if ((_flag_U_NR(i,j)) == ('N')) {
		_flag_U_NR(i,j) = 'I';
		U_NR(i,j) = reduce4(N,i,j);
		_flag_U_NR(i,j) = 'F';
	}
	else if ((_flag_U_NR(i,j)) == ('I')) {
		printf("There is a self dependence on U_NR at (%ld,%ld)\n",i,j);
		exit(-1);
	}
	
	return U_NR(i,j);
}

static float reduce5(long N, long ip, long jp) {
	float reduceVar;
	long k;
	
	reduceVar = 0.0f;
	#define RP5(i,j,k) (eval_L(((i)),((k)))) * (eval_U(((k)),((j))))
	#define R5(i,j,k) reduceVar = (reduceVar) + (RP5((i),(j),(k)))
	for (k = 0; k < jp; k += 1) {
		R5(ip, jp, k);
	}
	#undef RP5
	#undef R5
	return reduceVar;
}

static float eval_L_NR(long i, long j) {
	
	// Check the flags.
	if ((_flag_L_NR(i,j)) == ('N')) {
		_flag_L_NR(i,j) = 'I';
		L_NR(i,j) = reduce5(N,i,j);
		_flag_L_NR(i,j) = 'F';
	}
	else if ((_flag_L_NR(i,j)) == ('I')) {
		printf("There is a self dependence on L_NR at (%ld,%ld)\n",i,j);
		exit(-1);
	}
	
	return L_NR(i,j);
}

static float reduce6(long N, long ip, long jp) {
	float reduceVar;
	long k;
	
	reduceVar = 0.0f;
	#define RP6(i,j,k) A(((k)),((j)))
	#define R6(i,j,k) reduceVar = (reduceVar) + (RP6((i),(j),(k)))
	for (k = 0; k < N; k += 1) {
		R6(N, jp, k);
	}
	#undef RP6
	#undef R6
	return reduceVar;
}

static float eval_A_f_NR(long i, long j) {
	
	// Check the flags.
	if ((_flag_A_f_NR(i,j)) == ('N')) {
		_flag_A_f_NR(i,j) = 'I';
		A_f_NR(i,j) = reduce6(N,i,j);
		_flag_A_f_NR(i,j) = 'F';
	}
	else if ((_flag_A_f_NR(i,j)) == ('I')) {
		printf("There is a self dependence on A_f_NR at (%ld,%ld)\n",i,j);
		exit(-1);
	}
	
	return A_f_NR(i,j);
}

static float reduce7(long N, long ip, long jp) {
	float reduceVar;
	long k;
	
	reduceVar = 0.0f;
	#define RP7(i,j,k) A(((i)),((k)))
	#define R7(i,j,k) reduceVar = (reduceVar) + (RP7((i),(j),(k)))
	for (k = 0; k < N; k += 1) {
		R7(ip, N, k);
	}
	#undef RP7
	#undef R7
	return reduceVar;
}

static float eval_A_f_NR2(long i, long j) {
	
	// Check the flags.
	if ((_flag_A_f_NR2(i,j)) == ('N')) {
		_flag_A_f_NR2(i,j) = 'I';
		A_f_NR2(i,j) = reduce7(N,i,j);
		_flag_A_f_NR2(i,j) = 'F';
	}
	else if ((_flag_A_f_NR2(i,j)) == ('I')) {
		printf("There is a self dependence on A_f_NR2 at (%ld,%ld)\n",i,j);
		exit(-1);
	}
	
	return A_f_NR2(i,j);
}

void lud_abft(long _local_N, float** _local_A, float** _local_L, float** _local_U, float* _local_L_c_0, float* _local_U_r_0, float* _local_A_c, float* _local_A_r, float** _local_A_f) {
	long i;
	long j;
	
	// Copy arguments to the global variables.
	N = _local_N;
	A = _local_A;
	L = _local_L;
	U = _local_U;
	L_c_0 = _local_L_c_0;
	U_r_0 = _local_U_r_0;
	A_c = _local_A_c;
	A_r = _local_A_r;
	A_f = _local_A_f;
	
	// Check parameter validity.
	if (!((-1 + N) >= (0))) {
		printf("The value of the parameters are invalid.\n");
		exit(-1);
	}
	
	// Allocate memory for local storage.
	U_NR = (float*)(malloc((sizeof(float)) * (((-2 + N >= 0) ? (((-N + N*N))/2) : 0))));
	mallocCheck(U_NR,"U_NR");
	L_NR = (float*)(malloc((sizeof(float)) * (((-2 + N >= 0) ? (((-N + N*N))/2) : 0))));
	mallocCheck(L_NR,"L_NR");
	A_f_NR = (float*)(malloc((sizeof(float)) * (((-1 + N >= 0) ? (N) : 0))));
	mallocCheck(A_f_NR,"A_f_NR");
	A_f_NR2 = (float*)(malloc((sizeof(float)) * (((-1 + N >= 0) ? (N) : 0))));
	mallocCheck(A_f_NR2,"A_f_NR2");
	
	// Allocate and initialize flag variables.
	_flag_L = (char*)(malloc((sizeof(char)) * (((-1 + N >= 0) ? (((N + N*N))/2) : 0))));
	mallocCheck(_flag_L,"_flag_L");
	memset(_flag_L,'N',((-1 + N >= 0) ? (((N + N*N))/2) : 0));
	_flag_U = (char*)(malloc((sizeof(char)) * (((-1 + N >= 0) ? (((N + N*N))/2) : 0))));
	mallocCheck(_flag_U,"_flag_U");
	memset(_flag_U,'N',((-1 + N >= 0) ? (((N + N*N))/2) : 0));
	_flag_L_c_0 = (char*)(malloc((sizeof(char)) * (((-1 + N >= 0) ? (N) : 0))));
	mallocCheck(_flag_L_c_0,"_flag_L_c_0");
	memset(_flag_L_c_0,'N',((-1 + N >= 0) ? (N) : 0));
	_flag_U_r_0 = (char*)(malloc((sizeof(char)) * (((-1 + N >= 0) ? (N) : 0))));
	mallocCheck(_flag_U_r_0,"_flag_U_r_0");
	memset(_flag_U_r_0,'N',((-1 + N >= 0) ? (N) : 0));
	_flag_A_c = (char*)(malloc((sizeof(char)) * (((-1 + N >= 0) ? (N) : 0))));
	mallocCheck(_flag_A_c,"_flag_A_c");
	memset(_flag_A_c,'N',((-1 + N >= 0) ? (N) : 0));
	_flag_A_r = (char*)(malloc((sizeof(char)) * (((-1 + N >= 0) ? (N) : 0))));
	mallocCheck(_flag_A_r,"_flag_A_r");
	memset(_flag_A_r,'N',((-1 + N >= 0) ? (N) : 0));
	_flag_A_f = (char*)(malloc((sizeof(char)) * (((-1 + N >= 0) ? ((1 + 2 * N + N*N)) : 0))));
	mallocCheck(_flag_A_f,"_flag_A_f");
	memset(_flag_A_f,'N',((-1 + N >= 0) ? ((1 + 2 * N + N*N)) : 0));
	_flag_U_NR = (char*)(malloc((sizeof(char)) * (((-2 + N >= 0) ? (((-N + N*N))/2) : 0))));
	mallocCheck(_flag_U_NR,"_flag_U_NR");
	memset(_flag_U_NR,'N',((-2 + N >= 0) ? (((-N + N*N))/2) : 0));
	_flag_L_NR = (char*)(malloc((sizeof(char)) * (((-2 + N >= 0) ? (((-N + N*N))/2) : 0))));
	mallocCheck(_flag_L_NR,"_flag_L_NR");
	memset(_flag_L_NR,'N',((-2 + N >= 0) ? (((-N + N*N))/2) : 0));
	_flag_A_f_NR = (char*)(malloc((sizeof(char)) * (((-1 + N >= 0) ? (N) : 0))));
	mallocCheck(_flag_A_f_NR,"_flag_A_f_NR");
	memset(_flag_A_f_NR,'N',((-1 + N >= 0) ? (N) : 0));
	_flag_A_f_NR2 = (char*)(malloc((sizeof(char)) * (((-1 + N >= 0) ? (N) : 0))));
	mallocCheck(_flag_A_f_NR2,"_flag_A_f_NR2");
	memset(_flag_A_f_NR2,'N',((-1 + N >= 0) ? (N) : 0));
	
	// Evaluate all the outputs.
	#define S0(i,j) eval_L(i,j)
	for (i = 0; i < N; i += 1) {
		for (j = 0; j <= i; j += 1) {
			S0(i, j);
		}
	}
	#undef S0
	#define S1(i,j) eval_U(i,j)
	for (i = 0; i < N; i += 1) {
		for (j = i; j < N; j += 1) {
			S1(i, j);
		}
	}
	#undef S1
	#define S2(j) eval_L_c_0(j)
	for (j = 0; j < N; j += 1) {
		S2(j);
	}
	#undef S2
	#define S3(i) eval_U_r_0(i)
	for (i = 0; i < N; i += 1) {
		S3(i);
	}
	#undef S3
	#define S4(j) eval_A_c(j)
	for (j = 0; j < N; j += 1) {
		S4(j);
	}
	#undef S4
	#define S5(i) eval_A_r(i)
	for (i = 0; i < N; i += 1) {
		S5(i);
	}
	#undef S5
	#define S6(i,j) eval_A_f(i,j)
	for (i = 0; i <= N; i += 1) {
		for (j = 0; j <= N; j += 1) {
			S6(i, j);
		}
	}
	#undef S6
	
	// Free all allocated memory.
	free(U_NR);
	free(L_NR);
	free(A_f_NR);
	free(A_f_NR2);
	free(_flag_L);
	free(_flag_U);
	free(_flag_L_c_0);
	free(_flag_U_r_0);
	free(_flag_A_c);
	free(_flag_A_r);
	free(_flag_A_f);
	free(_flag_U_NR);
	free(_flag_L_NR);
	free(_flag_A_f_NR);
	free(_flag_A_f_NR2);
}


// Undefine the Memory and Function Macros
#undef A
#undef L
#undef U
#undef L_c_0
#undef U_r_0
#undef A_c
#undef A_r
#undef A_f
#undef U_NR
#undef L_NR
#undef A_f_NR
#undef A_f_NR2
#undef _flag_L
#undef _flag_U
#undef _flag_L_c_0
#undef _flag_U_r_0
#undef _flag_A_c
#undef _flag_A_r
#undef _flag_A_f
#undef _flag_U_NR
#undef _flag_L_NR
#undef _flag_A_f_NR
#undef _flag_A_f_NR2
#undef ceild
#undef floord
#undef div
#undef max
#undef min
#undef mallocCheck
