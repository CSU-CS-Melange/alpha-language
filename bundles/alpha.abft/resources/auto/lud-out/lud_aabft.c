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
static float* Inv_L_c;
static float* Inv_U_r;
static float* L_c_0;
static float* L_c_1;
static float* U_r_0;
static float* U_r_1;
static float* U_NR;
static float* L_NR;
static float* L_c_1_NR;
static float* U_r_1_NR;
static char* _flag_L;
static char* _flag_U;
static char* _flag_Inv_L_c;
static char* _flag_Inv_U_r;
static char* _flag_L_c_0;
static char* _flag_L_c_1;
static char* _flag_U_r_0;
static char* _flag_U_r_1;
static char* _flag_U_NR;
static char* _flag_L_NR;
static char* _flag_L_c_1_NR;
static char* _flag_U_r_1_NR;

// Memory Macros
#define A(i,j) A[i][j]
#define L(i,j) L[i][j]
#define U(i,j) U[i][j]
#define Inv_L_c(j) Inv_L_c[j]
#define Inv_U_r(i) Inv_U_r[i]
#define L_c_0(j) L_c_0[((-1 + N - j >= 0 && -1 + j >= 0) ? (j) : 0)]
#define L_c_1(j) L_c_1[((-1 + N - j >= 0 && -1 + j >= 0) ? (j) : 0)]
#define U_r_0(i) U_r_0[((-1 + N - i >= 0 && -1 + i >= 0) ? (i) : 0)]
#define U_r_1(i) U_r_1[((-1 + N - i >= 0 && -1 + i >= 0) ? (i) : 0)]
#define U_NR(i,j) U_NR[((-2 + i >= 0 && -1 - i + j >= 0 && -1 + N - j >= 0) ? ((((-2 * N + (-1 + 2 * N) * i - i*i) + 2 * j))/2) : (-i + j == 0 && -2 + i >= 0 && -1 + N - i >= 0) ? (((-2 * N + (1 + 2 * N) * i - i*i))/2) : (-1 + i == 0 && -1 + N - j >= 0 && -2 + j >= 0) ? ((-i + j)) : 0)]
#define L_NR(i,j) L_NR[((-2 + i >= 0 && -1 + N - i >= 0 && -2 + j >= 0 && i - j >= 0) ? ((((-2 - i + i*i) + 2 * j))/2) : (-1 + j == 0 && -2 + i >= 0 && -1 + N - i >= 0) ? (((-i + i*i))/2) : 0)]
#define L_c_1_NR(i,j) L_c_1_NR[((-2 + i >= 0 && -1 + N - i >= 0 && -2 + j >= 0 && i - j >= 0) ? ((((-2 - i + i*i) + 2 * j))/2) : (-1 + j == 0 && -2 + i >= 0 && -1 + N - i >= 0) ? (((-i + i*i))/2) : 0)]
#define U_r_1_NR(i,j) U_r_1_NR[((-2 + i >= 0 && -1 - i + j >= 0 && -1 + N - j >= 0) ? ((((-2 * N + (-1 + 2 * N) * i - i*i) + 2 * j))/2) : (-i + j == 0 && -2 + i >= 0 && -1 + N - i >= 0) ? (((-2 * N + (1 + 2 * N) * i - i*i))/2) : (-1 + i == 0 && -1 + N - j >= 0 && -2 + j >= 0) ? ((-i + j)) : 0)]
#define _flag_L(i,j) _flag_L[((-1 + i >= 0 && -1 + N - i >= 0 && -1 + j >= 0 && i - j >= 0) ? ((((i + i*i) + 2 * j))/2) : (j == 0 && -1 + i >= 0 && -1 + N - i >= 0) ? (((i + i*i))/2) : 0)]
#define _flag_U(i,j) _flag_U[((-1 + i >= 0 && -1 - i + j >= 0 && -1 + N - j >= 0) ? (((((-1 + 2 * N) * i - i*i) + 2 * j))/2) : (-i + j == 0 && -1 + i >= 0 && -1 + N - i >= 0) ? ((((1 + 2 * N) * i - i*i))/2) : (i == 0 && -1 + N - j >= 0 && -1 + j >= 0) ? ((-i + j)) : 0)]
#define _flag_Inv_L_c(j) _flag_Inv_L_c[((-1 + N - j >= 0 && -1 + j >= 0) ? (j) : 0)]
#define _flag_Inv_U_r(i) _flag_Inv_U_r[((-1 + N - i >= 0 && -1 + i >= 0) ? (i) : 0)]
#define _flag_L_c_0(j) _flag_L_c_0[((-1 + N - j >= 0 && -1 + j >= 0) ? (j) : 0)]
#define _flag_L_c_1(j) _flag_L_c_1[((-1 + N - j >= 0 && -1 + j >= 0) ? (j) : 0)]
#define _flag_U_r_0(i) _flag_U_r_0[((-1 + N - i >= 0 && -1 + i >= 0) ? (i) : 0)]
#define _flag_U_r_1(i) _flag_U_r_1[((-1 + N - i >= 0 && -1 + i >= 0) ? (i) : 0)]
#define _flag_U_NR(i,j) _flag_U_NR[((-2 + i >= 0 && -1 - i + j >= 0 && -1 + N - j >= 0) ? ((((-2 * N + (-1 + 2 * N) * i - i*i) + 2 * j))/2) : (-i + j == 0 && -2 + i >= 0 && -1 + N - i >= 0) ? (((-2 * N + (1 + 2 * N) * i - i*i))/2) : (-1 + i == 0 && -1 + N - j >= 0 && -2 + j >= 0) ? ((-i + j)) : 0)]
#define _flag_L_NR(i,j) _flag_L_NR[((-2 + i >= 0 && -1 + N - i >= 0 && -2 + j >= 0 && i - j >= 0) ? ((((-2 - i + i*i) + 2 * j))/2) : (-1 + j == 0 && -2 + i >= 0 && -1 + N - i >= 0) ? (((-i + i*i))/2) : 0)]
#define _flag_L_c_1_NR(i,j) _flag_L_c_1_NR[((-2 + i >= 0 && -1 + N - i >= 0 && -2 + j >= 0 && i - j >= 0) ? ((((-2 - i + i*i) + 2 * j))/2) : (-1 + j == 0 && -2 + i >= 0 && -1 + N - i >= 0) ? (((-i + i*i))/2) : 0)]
#define _flag_U_r_1_NR(i,j) _flag_U_r_1_NR[((-2 + i >= 0 && -1 - i + j >= 0 && -1 + N - j >= 0) ? ((((-2 * N + (-1 + 2 * N) * i - i*i) + 2 * j))/2) : (-i + j == 0 && -2 + i >= 0 && -1 + N - i >= 0) ? (((-2 * N + (1 + 2 * N) * i - i*i))/2) : (-1 + i == 0 && -1 + N - j >= 0 && -2 + j >= 0) ? ((-i + j)) : 0)]

// Function Declarations
static float eval_U(long i, long j);
static float eval_L(long i, long j);
static float reduce0(long N, long jp);
static float eval_L_c_0(long j);
static float reduce1(long N, long jp);
static float eval_L_c_1(long j);
static float reduce2(long N, long ip);
static float eval_U_r_0(long i);
static float reduce3(long N, long ip);
static float eval_U_r_1(long i);
static float eval_Inv_L_c(long j);
static float eval_Inv_U_r(long i);
static float reduce4(long N, long ip, long jp);
static float eval_U_NR(long i, long j);
static float reduce5(long N, long ip, long jp);
static float eval_L_NR(long i, long j);
static float reduce6(long N, long ip, long jp);
static float eval_L_c_1_NR(long i, long j);
static float reduce7(long N, long ip, long jp);
static float eval_U_r_1_NR(long i, long j);
void lud_aabft(long _local_N, float** _local_A, float** _local_L, float** _local_U, float* _local_Inv_L_c, float* _local_Inv_U_r);

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
	#define RP0(i,j) eval_L(i,j)
	#define R0(i,j) reduceVar = (reduceVar) + (RP0((i),(j)))
	for (i = jp; i < N; i += 1) {
		R0(i, jp);
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

static float reduce1(long N, long jp) {
	float reduceVar;
	long i;
	
	reduceVar = 0.0f;
	#define RP1(i,j) ((((j) == (0)) && ((i) >= (0))) && ((-1 + N - i) >= (0))) ? ((A(i,j)) / (eval_U(((j)),((j))))) : (((A(i,j)) - (eval_L_c_1_NR(i,j))) / (eval_U(((j)),((j)))))
	#define R1(i,j) reduceVar = (reduceVar) + (RP1((i),(j)))
	for (i = jp; i < N; i += 1) {
		R1(i, jp);
	}
	#undef RP1
	#undef R1
	return reduceVar;
}

static float eval_L_c_1(long j) {
	
	// Check the flags.
	if ((_flag_L_c_1(j)) == ('N')) {
		_flag_L_c_1(j) = 'I';
		L_c_1(j) = reduce1(N,j);
		_flag_L_c_1(j) = 'F';
	}
	else if ((_flag_L_c_1(j)) == ('I')) {
		printf("There is a self dependence on L_c_1 at (%ld)\n",j);
		exit(-1);
	}
	
	return L_c_1(j);
}

static float reduce2(long N, long ip) {
	float reduceVar;
	long j;
	
	reduceVar = 0.0f;
	#define RP2(i,j) eval_U(i,j)
	#define R2(i,j) reduceVar = (reduceVar) + (RP2((i),(j)))
	for (j = ip; j < N; j += 1) {
		R2(ip, j);
	}
	#undef RP2
	#undef R2
	return reduceVar;
}

static float eval_U_r_0(long i) {
	
	// Check the flags.
	if ((_flag_U_r_0(i)) == ('N')) {
		_flag_U_r_0(i) = 'I';
		U_r_0(i) = reduce2(N,i);
		_flag_U_r_0(i) = 'F';
	}
	else if ((_flag_U_r_0(i)) == ('I')) {
		printf("There is a self dependence on U_r_0 at (%ld)\n",i);
		exit(-1);
	}
	
	return U_r_0(i);
}

static float reduce3(long N, long ip) {
	float reduceVar;
	long j;
	
	reduceVar = 0.0f;
	#define RP3(i,j) ((((i) == (0)) && ((j) >= (0))) && ((-1 + N - j) >= (0))) ? (A(i,j)) : ((A(i,j)) - (eval_U_r_1_NR(i,j)))
	#define R3(i,j) reduceVar = (reduceVar) + (RP3((i),(j)))
	for (j = ip; j < N; j += 1) {
		R3(ip, j);
	}
	#undef RP3
	#undef R3
	return reduceVar;
}

static float eval_U_r_1(long i) {
	
	// Check the flags.
	if ((_flag_U_r_1(i)) == ('N')) {
		_flag_U_r_1(i) = 'I';
		U_r_1(i) = reduce3(N,i);
		_flag_U_r_1(i) = 'F';
	}
	else if ((_flag_U_r_1(i)) == ('I')) {
		printf("There is a self dependence on U_r_1 at (%ld)\n",i);
		exit(-1);
	}
	
	return U_r_1(i);
}

static float eval_Inv_L_c(long j) {
	
	// Check the flags.
	if ((_flag_Inv_L_c(j)) == ('N')) {
		_flag_Inv_L_c(j) = 'I';
		Inv_L_c(j) = ((eval_L_c_0(j)) - (eval_L_c_1(j))) / (eval_L_c_0(j));
		_flag_Inv_L_c(j) = 'F';
	}
	else if ((_flag_Inv_L_c(j)) == ('I')) {
		printf("There is a self dependence on Inv_L_c at (%ld)\n",j);
		exit(-1);
	}
	
	return Inv_L_c(j);
}

static float eval_Inv_U_r(long i) {
	
	// Check the flags.
	if ((_flag_Inv_U_r(i)) == ('N')) {
		_flag_Inv_U_r(i) = 'I';
		Inv_U_r(i) = ((eval_U_r_0(i)) - (eval_U_r_1(i))) / (eval_U_r_0(i));
		_flag_Inv_U_r(i) = 'F';
	}
	else if ((_flag_Inv_U_r(i)) == ('I')) {
		printf("There is a self dependence on Inv_U_r at (%ld)\n",i);
		exit(-1);
	}
	
	return Inv_U_r(i);
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
	#define RP6(i,j,k) (eval_L(((i)),((k)))) * (eval_U(((k)),((j))))
	#define R6(i,j,k) reduceVar = (reduceVar) + (RP6((i),(j),(k)))
	for (k = 0; k < jp; k += 1) {
		R6(ip, jp, k);
	}
	#undef RP6
	#undef R6
	return reduceVar;
}

static float eval_L_c_1_NR(long i, long j) {
	
	// Check the flags.
	if ((_flag_L_c_1_NR(i,j)) == ('N')) {
		_flag_L_c_1_NR(i,j) = 'I';
		L_c_1_NR(i,j) = reduce6(N,i,j);
		_flag_L_c_1_NR(i,j) = 'F';
	}
	else if ((_flag_L_c_1_NR(i,j)) == ('I')) {
		printf("There is a self dependence on L_c_1_NR at (%ld,%ld)\n",i,j);
		exit(-1);
	}
	
	return L_c_1_NR(i,j);
}

static float reduce7(long N, long ip, long jp) {
	float reduceVar;
	long k;
	
	reduceVar = 0.0f;
	#define RP7(i,j,k) (eval_L(((i)),((k)))) * (eval_U(((k)),((j))))
	#define R7(i,j,k) reduceVar = (reduceVar) + (RP7((i),(j),(k)))
	for (k = 0; k < ip; k += 1) {
		R7(ip, jp, k);
	}
	#undef RP7
	#undef R7
	return reduceVar;
}

static float eval_U_r_1_NR(long i, long j) {
	
	// Check the flags.
	if ((_flag_U_r_1_NR(i,j)) == ('N')) {
		_flag_U_r_1_NR(i,j) = 'I';
		U_r_1_NR(i,j) = reduce7(N,i,j);
		_flag_U_r_1_NR(i,j) = 'F';
	}
	else if ((_flag_U_r_1_NR(i,j)) == ('I')) {
		printf("There is a self dependence on U_r_1_NR at (%ld,%ld)\n",i,j);
		exit(-1);
	}
	
	return U_r_1_NR(i,j);
}

void lud_aabft(long _local_N, float** _local_A, float** _local_L, float** _local_U, float* _local_Inv_L_c, float* _local_Inv_U_r) {
	long i;
	long j;
	
	// Copy arguments to the global variables.
	N = _local_N;
	A = _local_A;
	L = _local_L;
	U = _local_U;
	Inv_L_c = _local_Inv_L_c;
	Inv_U_r = _local_Inv_U_r;
	
	// Check parameter validity.
	if (!((-1 + N) >= (0))) {
		printf("The value of the parameters are invalid.\n");
		exit(-1);
	}
	
	// Allocate memory for local storage.
	L_c_0 = (float*)(malloc((sizeof(float)) * (((-1 + N >= 0) ? (N) : 0))));
	mallocCheck(L_c_0,"L_c_0");
	L_c_1 = (float*)(malloc((sizeof(float)) * (((-1 + N >= 0) ? (N) : 0))));
	mallocCheck(L_c_1,"L_c_1");
	U_r_0 = (float*)(malloc((sizeof(float)) * (((-1 + N >= 0) ? (N) : 0))));
	mallocCheck(U_r_0,"U_r_0");
	U_r_1 = (float*)(malloc((sizeof(float)) * (((-1 + N >= 0) ? (N) : 0))));
	mallocCheck(U_r_1,"U_r_1");
	U_NR = (float*)(malloc((sizeof(float)) * (((-2 + N >= 0) ? (((-N + N*N))/2) : 0))));
	mallocCheck(U_NR,"U_NR");
	L_NR = (float*)(malloc((sizeof(float)) * (((-2 + N >= 0) ? (((-N + N*N))/2) : 0))));
	mallocCheck(L_NR,"L_NR");
	L_c_1_NR = (float*)(malloc((sizeof(float)) * (((-2 + N >= 0) ? (((-N + N*N))/2) : 0))));
	mallocCheck(L_c_1_NR,"L_c_1_NR");
	U_r_1_NR = (float*)(malloc((sizeof(float)) * (((-2 + N >= 0) ? (((-N + N*N))/2) : 0))));
	mallocCheck(U_r_1_NR,"U_r_1_NR");
	
	// Allocate and initialize flag variables.
	_flag_L = (char*)(malloc((sizeof(char)) * (((-1 + N >= 0) ? (((N + N*N))/2) : 0))));
	mallocCheck(_flag_L,"_flag_L");
	memset(_flag_L,'N',((-1 + N >= 0) ? (((N + N*N))/2) : 0));
	_flag_U = (char*)(malloc((sizeof(char)) * (((-1 + N >= 0) ? (((N + N*N))/2) : 0))));
	mallocCheck(_flag_U,"_flag_U");
	memset(_flag_U,'N',((-1 + N >= 0) ? (((N + N*N))/2) : 0));
	_flag_Inv_L_c = (char*)(malloc((sizeof(char)) * (((-1 + N >= 0) ? (N) : 0))));
	mallocCheck(_flag_Inv_L_c,"_flag_Inv_L_c");
	memset(_flag_Inv_L_c,'N',((-1 + N >= 0) ? (N) : 0));
	_flag_Inv_U_r = (char*)(malloc((sizeof(char)) * (((-1 + N >= 0) ? (N) : 0))));
	mallocCheck(_flag_Inv_U_r,"_flag_Inv_U_r");
	memset(_flag_Inv_U_r,'N',((-1 + N >= 0) ? (N) : 0));
	_flag_L_c_0 = (char*)(malloc((sizeof(char)) * (((-1 + N >= 0) ? (N) : 0))));
	mallocCheck(_flag_L_c_0,"_flag_L_c_0");
	memset(_flag_L_c_0,'N',((-1 + N >= 0) ? (N) : 0));
	_flag_L_c_1 = (char*)(malloc((sizeof(char)) * (((-1 + N >= 0) ? (N) : 0))));
	mallocCheck(_flag_L_c_1,"_flag_L_c_1");
	memset(_flag_L_c_1,'N',((-1 + N >= 0) ? (N) : 0));
	_flag_U_r_0 = (char*)(malloc((sizeof(char)) * (((-1 + N >= 0) ? (N) : 0))));
	mallocCheck(_flag_U_r_0,"_flag_U_r_0");
	memset(_flag_U_r_0,'N',((-1 + N >= 0) ? (N) : 0));
	_flag_U_r_1 = (char*)(malloc((sizeof(char)) * (((-1 + N >= 0) ? (N) : 0))));
	mallocCheck(_flag_U_r_1,"_flag_U_r_1");
	memset(_flag_U_r_1,'N',((-1 + N >= 0) ? (N) : 0));
	_flag_U_NR = (char*)(malloc((sizeof(char)) * (((-2 + N >= 0) ? (((-N + N*N))/2) : 0))));
	mallocCheck(_flag_U_NR,"_flag_U_NR");
	memset(_flag_U_NR,'N',((-2 + N >= 0) ? (((-N + N*N))/2) : 0));
	_flag_L_NR = (char*)(malloc((sizeof(char)) * (((-2 + N >= 0) ? (((-N + N*N))/2) : 0))));
	mallocCheck(_flag_L_NR,"_flag_L_NR");
	memset(_flag_L_NR,'N',((-2 + N >= 0) ? (((-N + N*N))/2) : 0));
	_flag_L_c_1_NR = (char*)(malloc((sizeof(char)) * (((-2 + N >= 0) ? (((-N + N*N))/2) : 0))));
	mallocCheck(_flag_L_c_1_NR,"_flag_L_c_1_NR");
	memset(_flag_L_c_1_NR,'N',((-2 + N >= 0) ? (((-N + N*N))/2) : 0));
	_flag_U_r_1_NR = (char*)(malloc((sizeof(char)) * (((-2 + N >= 0) ? (((-N + N*N))/2) : 0))));
	mallocCheck(_flag_U_r_1_NR,"_flag_U_r_1_NR");
	memset(_flag_U_r_1_NR,'N',((-2 + N >= 0) ? (((-N + N*N))/2) : 0));
	
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
	#define S2(j) eval_Inv_L_c(j)
	for (j = 0; j < N; j += 1) {
		S2(j);
	}
	#undef S2
	#define S3(i) eval_Inv_U_r(i)
	for (i = 0; i < N; i += 1) {
		S3(i);
	}
	#undef S3
	
	// Free all allocated memory.
	free(L_c_0);
	free(L_c_1);
	free(U_r_0);
	free(U_r_1);
	free(U_NR);
	free(L_NR);
	free(L_c_1_NR);
	free(U_r_1_NR);
	free(_flag_L);
	free(_flag_U);
	free(_flag_Inv_L_c);
	free(_flag_Inv_U_r);
	free(_flag_L_c_0);
	free(_flag_L_c_1);
	free(_flag_U_r_0);
	free(_flag_U_r_1);
	free(_flag_U_NR);
	free(_flag_L_NR);
	free(_flag_L_c_1_NR);
	free(_flag_U_r_1_NR);
}


// Undefine the Memory and Function Macros
#undef A
#undef L
#undef U
#undef Inv_L_c
#undef Inv_U_r
#undef L_c_0
#undef L_c_1
#undef U_r_0
#undef U_r_1
#undef U_NR
#undef L_NR
#undef L_c_1_NR
#undef U_r_1_NR
#undef _flag_L
#undef _flag_U
#undef _flag_Inv_L_c
#undef _flag_Inv_U_r
#undef _flag_L_c_0
#undef _flag_L_c_1
#undef _flag_U_r_0
#undef _flag_U_r_1
#undef _flag_U_NR
#undef _flag_L_NR
#undef _flag_L_c_1_NR
#undef _flag_U_r_1_NR
#undef ceild
#undef floord
#undef div
#undef max
#undef min
#undef mallocCheck
