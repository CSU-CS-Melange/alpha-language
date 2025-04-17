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
static float* check_U_i_0;
static float* check_U_i_1;
static float* check_U_j_0;
static float* check_U_j_1;
static float* check_L_i_0;
static float* check_L_i_1;
static float* check_L_j_0;
static float* check_L_j_1;
static float* U_NR;
static float* L_NR;
static float* check_U_i_1_NR;
static float* check_U_j_1_NR;
static float* check_L_i_1_NR;
static float* check_L_j_1_NR;
static char* _flag_L;
static char* _flag_U;
static char* _flag_check_U_i_0;
static char* _flag_check_U_i_1;
static char* _flag_check_U_j_0;
static char* _flag_check_U_j_1;
static char* _flag_check_L_i_0;
static char* _flag_check_L_i_1;
static char* _flag_check_L_j_0;
static char* _flag_check_L_j_1;
static char* _flag_U_NR;
static char* _flag_L_NR;
static char* _flag_check_U_i_1_NR;
static char* _flag_check_U_j_1_NR;
static char* _flag_check_L_i_1_NR;
static char* _flag_check_L_j_1_NR;

// Memory Macros
#define A(i,j) A[i][j]
#define L(i,j) L[i][j]
#define U(i,j) U[i][j]
#define check_U_i_0(i) check_U_i_0[i]
#define check_U_i_1(i) check_U_i_1[i]
#define check_U_j_0(j) check_U_j_0[j]
#define check_U_j_1(j) check_U_j_1[j]
#define check_L_i_0(i) check_L_i_0[i]
#define check_L_i_1(i) check_L_i_1[i]
#define check_L_j_0(j) check_L_j_0[j]
#define check_L_j_1(j) check_L_j_1[j]
#define U_NR(i,j) U_NR[((-2 + i >= 0 && -1 - i + j >= 0 && -1 + N - j >= 0) ? ((((-2 * N + (-1 + 2 * N) * i - i*i) + 2 * j))/2) : (-i + j == 0 && -2 + i >= 0 && -1 + N - i >= 0) ? (((-2 * N + (1 + 2 * N) * i - i*i))/2) : (-1 + i == 0 && -1 + N - j >= 0 && -2 + j >= 0) ? ((-i + j)) : 0)]
#define L_NR(i,j) L_NR[((-2 + i >= 0 && -1 + N - i >= 0 && -2 + j >= 0 && i - j >= 0) ? ((((-2 - i + i*i) + 2 * j))/2) : (-1 + j == 0 && -2 + i >= 0 && -1 + N - i >= 0) ? (((-i + i*i))/2) : 0)]
#define check_U_i_1_NR(i,j) check_U_i_1_NR[((-2 + i >= 0 && -1 - i + j >= 0 && -1 + N - j >= 0) ? ((((-2 * N + (-1 + 2 * N) * i - i*i) + 2 * j))/2) : (-i + j == 0 && -2 + i >= 0 && -1 + N - i >= 0) ? (((-2 * N + (1 + 2 * N) * i - i*i))/2) : (-1 + i == 0 && -1 + N - j >= 0 && -2 + j >= 0) ? ((-i + j)) : 0)]
#define check_U_j_1_NR(i,j) check_U_j_1_NR[((-2 + i >= 0 && -1 - i + j >= 0 && -1 + N - j >= 0) ? ((((-2 * N + (-1 + 2 * N) * i - i*i) + 2 * j))/2) : (-i + j == 0 && -2 + i >= 0 && -1 + N - i >= 0) ? (((-2 * N + (1 + 2 * N) * i - i*i))/2) : (-1 + i == 0 && -1 + N - j >= 0 && -2 + j >= 0) ? ((-i + j)) : 0)]
#define check_L_i_1_NR(i,j) check_L_i_1_NR[((-2 + i >= 0 && -1 + N - i >= 0 && -2 + j >= 0 && i - j >= 0) ? ((((-2 - i + i*i) + 2 * j))/2) : (-1 + j == 0 && -2 + i >= 0 && -1 + N - i >= 0) ? (((-i + i*i))/2) : 0)]
#define check_L_j_1_NR(i,j) check_L_j_1_NR[((-2 + i >= 0 && -1 + N - i >= 0 && -2 + j >= 0 && i - j >= 0) ? ((((-2 - i + i*i) + 2 * j))/2) : (-1 + j == 0 && -2 + i >= 0 && -1 + N - i >= 0) ? (((-i + i*i))/2) : 0)]
#define _flag_L(i,j) _flag_L[((-1 + i >= 0 && -1 + N - i >= 0 && -1 + j >= 0 && i - j >= 0) ? ((((i + i*i) + 2 * j))/2) : (j == 0 && -1 + i >= 0 && -1 + N - i >= 0) ? (((i + i*i))/2) : 0)]
#define _flag_U(i,j) _flag_U[((-1 + i >= 0 && -1 - i + j >= 0 && -1 + N - j >= 0) ? (((((-1 + 2 * N) * i - i*i) + 2 * j))/2) : (-i + j == 0 && -1 + i >= 0 && -1 + N - i >= 0) ? ((((1 + 2 * N) * i - i*i))/2) : (i == 0 && -1 + N - j >= 0 && -1 + j >= 0) ? ((-i + j)) : 0)]
#define _flag_check_U_i_0(i) _flag_check_U_i_0[((-1 + N - i >= 0 && -1 + i >= 0) ? (i) : 0)]
#define _flag_check_U_i_1(i) _flag_check_U_i_1[((-1 + N - i >= 0 && -1 + i >= 0) ? (i) : 0)]
#define _flag_check_U_j_0(j) _flag_check_U_j_0[((-1 + N - j >= 0 && -1 + j >= 0) ? (j) : 0)]
#define _flag_check_U_j_1(j) _flag_check_U_j_1[((-1 + N - j >= 0 && -1 + j >= 0) ? (j) : 0)]
#define _flag_check_L_i_0(i) _flag_check_L_i_0[((-1 + N - i >= 0 && -1 + i >= 0) ? (i) : 0)]
#define _flag_check_L_i_1(i) _flag_check_L_i_1[((-1 + N - i >= 0 && -1 + i >= 0) ? (i) : 0)]
#define _flag_check_L_j_0(j) _flag_check_L_j_0[((-1 + N - j >= 0 && -1 + j >= 0) ? (j) : 0)]
#define _flag_check_L_j_1(j) _flag_check_L_j_1[((-1 + N - j >= 0 && -1 + j >= 0) ? (j) : 0)]
#define _flag_U_NR(i,j) _flag_U_NR[((-2 + i >= 0 && -1 - i + j >= 0 && -1 + N - j >= 0) ? ((((-2 * N + (-1 + 2 * N) * i - i*i) + 2 * j))/2) : (-i + j == 0 && -2 + i >= 0 && -1 + N - i >= 0) ? (((-2 * N + (1 + 2 * N) * i - i*i))/2) : (-1 + i == 0 && -1 + N - j >= 0 && -2 + j >= 0) ? ((-i + j)) : 0)]
#define _flag_L_NR(i,j) _flag_L_NR[((-2 + i >= 0 && -1 + N - i >= 0 && -2 + j >= 0 && i - j >= 0) ? ((((-2 - i + i*i) + 2 * j))/2) : (-1 + j == 0 && -2 + i >= 0 && -1 + N - i >= 0) ? (((-i + i*i))/2) : 0)]
#define _flag_check_U_i_1_NR(i,j) _flag_check_U_i_1_NR[((-2 + i >= 0 && -1 - i + j >= 0 && -1 + N - j >= 0) ? ((((-2 * N + (-1 + 2 * N) * i - i*i) + 2 * j))/2) : (-i + j == 0 && -2 + i >= 0 && -1 + N - i >= 0) ? (((-2 * N + (1 + 2 * N) * i - i*i))/2) : (-1 + i == 0 && -1 + N - j >= 0 && -2 + j >= 0) ? ((-i + j)) : 0)]
#define _flag_check_U_j_1_NR(i,j) _flag_check_U_j_1_NR[((-2 + i >= 0 && -1 - i + j >= 0 && -1 + N - j >= 0) ? ((((-2 * N + (-1 + 2 * N) * i - i*i) + 2 * j))/2) : (-i + j == 0 && -2 + i >= 0 && -1 + N - i >= 0) ? (((-2 * N + (1 + 2 * N) * i - i*i))/2) : (-1 + i == 0 && -1 + N - j >= 0 && -2 + j >= 0) ? ((-i + j)) : 0)]
#define _flag_check_L_i_1_NR(i,j) _flag_check_L_i_1_NR[((-2 + i >= 0 && -1 + N - i >= 0 && -2 + j >= 0 && i - j >= 0) ? ((((-2 - i + i*i) + 2 * j))/2) : (-1 + j == 0 && -2 + i >= 0 && -1 + N - i >= 0) ? (((-i + i*i))/2) : 0)]
#define _flag_check_L_j_1_NR(i,j) _flag_check_L_j_1_NR[((-2 + i >= 0 && -1 + N - i >= 0 && -2 + j >= 0 && i - j >= 0) ? ((((-2 - i + i*i) + 2 * j))/2) : (-1 + j == 0 && -2 + i >= 0 && -1 + N - i >= 0) ? (((-i + i*i))/2) : 0)]

// Function Declarations
static float eval_U(long i, long j);
static float eval_L(long i, long j);
static float reduce0(long N, long ip);
static float eval_check_U_i_0(long i);
static float reduce1(long N, long ip);
static float eval_check_U_i_1(long i);
static float reduce2(long N, long jp);
static float eval_check_U_j_0(long j);
static float reduce3(long N, long jp);
static float eval_check_U_j_1(long j);
static float reduce4(long N, long ip);
static float eval_check_L_i_0(long i);
static float reduce5(long N, long ip);
static float eval_check_L_i_1(long i);
static float reduce6(long N, long jp);
static float eval_check_L_j_0(long j);
static float reduce7(long N, long jp);
static float eval_check_L_j_1(long j);
static float reduce8(long N, long ip, long jp);
static float eval_U_NR(long i, long j);
static float reduce9(long N, long ip, long jp);
static float eval_L_NR(long i, long j);
static float reduce10(long N, long ip, long jp);
static float eval_check_U_i_1_NR(long i, long j);
static float reduce11(long N, long ip, long jp);
static float eval_check_U_j_1_NR(long i, long j);
static float reduce12(long N, long ip, long jp);
static float eval_check_L_i_1_NR(long i, long j);
static float reduce13(long N, long ip, long jp);
static float eval_check_L_j_1_NR(long i, long j);
void lud_aabft(long _local_N, float** _local_A, float** _local_L, float** _local_U, float* _local_check_U_i_0, float* _local_check_U_i_1, float* _local_check_U_j_0, float* _local_check_U_j_1, float* _local_check_L_i_0, float* _local_check_L_i_1, float* _local_check_L_j_0, float* _local_check_L_j_1);

static float eval_U(long i, long j) {
	
	// Check the flags.
	if ((_flag_U(i,j)) == ('N')) {
		_flag_U(i,j) = 'I';
		U(i,j) = (((i) == (0)) && ((-1 + N) >= (0))) ? (A(((i)),((j)))) : ((A(((i)),((j)))) - (eval_U_NR(i,j)));
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
		L(i,j) = (((j) == (0)) && ((-1 + N) >= (0))) ? ((A(((i)),((j)))) / (eval_U(((j)),((j))))) : (((A(((i)),((j)))) - (eval_L_NR(i,j))) / (eval_U(((j)),((j)))));
		_flag_L(i,j) = 'F';
	}
	else if ((_flag_L(i,j)) == ('I')) {
		printf("There is a self dependence on L at (%ld,%ld)\n",i,j);
		exit(-1);
	}
	
	return L(i,j);
}

static float reduce0(long N, long ip) {
	float reduceVar;
	long j;
	
	reduceVar = 0.0f;
	#define RP0(i,j) eval_U(((i)),((j)))
	#define R0(i,j) reduceVar = (reduceVar) + (RP0((i),(j)))
	for (j = ip; j < N; j += 1) {
		R0(ip, j);
	}
	#undef RP0
	#undef R0
	return reduceVar;
}

static float eval_check_U_i_0(long i) {
	
	// Check the flags.
	if ((_flag_check_U_i_0(i)) == ('N')) {
		_flag_check_U_i_0(i) = 'I';
		check_U_i_0(i) = reduce0(N,i);
		_flag_check_U_i_0(i) = 'F';
	}
	else if ((_flag_check_U_i_0(i)) == ('I')) {
		printf("There is a self dependence on check_U_i_0 at (%ld)\n",i);
		exit(-1);
	}
	
	return check_U_i_0(i);
}

static float reduce1(long N, long ip) {
	float reduceVar;
	long j;
	
	reduceVar = 0.0f;
	#define RP1(i,j) ((((i) == (0)) && ((j) >= (0))) && ((-1 + N - j) >= (0))) ? (A(((i)),((j)))) : ((A(((i)),((j)))) - (eval_check_U_i_1_NR(i,j)))
	#define R1(i,j) reduceVar = (reduceVar) + (RP1((i),(j)))
	for (j = ip; j < N; j += 1) {
		R1(ip, j);
	}
	#undef RP1
	#undef R1
	return reduceVar;
}

static float eval_check_U_i_1(long i) {
	
	// Check the flags.
	if ((_flag_check_U_i_1(i)) == ('N')) {
		_flag_check_U_i_1(i) = 'I';
		check_U_i_1(i) = reduce1(N,i);
		_flag_check_U_i_1(i) = 'F';
	}
	else if ((_flag_check_U_i_1(i)) == ('I')) {
		printf("There is a self dependence on check_U_i_1 at (%ld)\n",i);
		exit(-1);
	}
	
	return check_U_i_1(i);
}

static float reduce2(long N, long jp) {
	float reduceVar;
	long i;
	
	reduceVar = 0.0f;
	#define RP2(i,j) eval_U(((i)),((j)))
	#define R2(i,j) reduceVar = (reduceVar) + (RP2((i),(j)))
	for (i = 0; i <= jp; i += 1) {
		R2(i, jp);
	}
	#undef RP2
	#undef R2
	return reduceVar;
}

static float eval_check_U_j_0(long j) {
	
	// Check the flags.
	if ((_flag_check_U_j_0(j)) == ('N')) {
		_flag_check_U_j_0(j) = 'I';
		check_U_j_0(j) = reduce2(N,j);
		_flag_check_U_j_0(j) = 'F';
	}
	else if ((_flag_check_U_j_0(j)) == ('I')) {
		printf("There is a self dependence on check_U_j_0 at (%ld)\n",j);
		exit(-1);
	}
	
	return check_U_j_0(j);
}

static float reduce3(long N, long jp) {
	float reduceVar;
	long i;
	
	reduceVar = 0.0f;
	#define RP3(i,j) ((((i) == (0)) && ((j) >= (0))) && ((-1 + N - j) >= (0))) ? (A(((i)),((j)))) : ((A(((i)),((j)))) - (eval_check_U_j_1_NR(i,j)))
	#define R3(i,j) reduceVar = (reduceVar) + (RP3((i),(j)))
	for (i = 0; i <= jp; i += 1) {
		R3(i, jp);
	}
	#undef RP3
	#undef R3
	return reduceVar;
}

static float eval_check_U_j_1(long j) {
	
	// Check the flags.
	if ((_flag_check_U_j_1(j)) == ('N')) {
		_flag_check_U_j_1(j) = 'I';
		check_U_j_1(j) = reduce3(N,j);
		_flag_check_U_j_1(j) = 'F';
	}
	else if ((_flag_check_U_j_1(j)) == ('I')) {
		printf("There is a self dependence on check_U_j_1 at (%ld)\n",j);
		exit(-1);
	}
	
	return check_U_j_1(j);
}

static float reduce4(long N, long ip) {
	float reduceVar;
	long j;
	
	reduceVar = 0.0f;
	#define RP4(i,j) eval_L(((i)),((j)))
	#define R4(i,j) reduceVar = (reduceVar) + (RP4((i),(j)))
	for (j = 0; j <= ip; j += 1) {
		R4(ip, j);
	}
	#undef RP4
	#undef R4
	return reduceVar;
}

static float eval_check_L_i_0(long i) {
	
	// Check the flags.
	if ((_flag_check_L_i_0(i)) == ('N')) {
		_flag_check_L_i_0(i) = 'I';
		check_L_i_0(i) = reduce4(N,i);
		_flag_check_L_i_0(i) = 'F';
	}
	else if ((_flag_check_L_i_0(i)) == ('I')) {
		printf("There is a self dependence on check_L_i_0 at (%ld)\n",i);
		exit(-1);
	}
	
	return check_L_i_0(i);
}

static float reduce5(long N, long ip) {
	float reduceVar;
	long j;
	
	reduceVar = 0.0f;
	#define RP5(i,j) ((((j) == (0)) && ((i) >= (0))) && ((-1 + N - i) >= (0))) ? ((A(((i)),((j)))) / (eval_U(((j)),((j))))) : (((A(((i)),((j)))) - (eval_check_L_i_1_NR(i,j))) / (eval_U(((j)),((j)))))
	#define R5(i,j) reduceVar = (reduceVar) + (RP5((i),(j)))
	for (j = 0; j <= ip; j += 1) {
		R5(ip, j);
	}
	#undef RP5
	#undef R5
	return reduceVar;
}

static float eval_check_L_i_1(long i) {
	
	// Check the flags.
	if ((_flag_check_L_i_1(i)) == ('N')) {
		_flag_check_L_i_1(i) = 'I';
		check_L_i_1(i) = reduce5(N,i);
		_flag_check_L_i_1(i) = 'F';
	}
	else if ((_flag_check_L_i_1(i)) == ('I')) {
		printf("There is a self dependence on check_L_i_1 at (%ld)\n",i);
		exit(-1);
	}
	
	return check_L_i_1(i);
}

static float reduce6(long N, long jp) {
	float reduceVar;
	long i;
	
	reduceVar = 0.0f;
	#define RP6(i,j) eval_L(((i)),((j)))
	#define R6(i,j) reduceVar = (reduceVar) + (RP6((i),(j)))
	for (i = jp; i < N; i += 1) {
		R6(i, jp);
	}
	#undef RP6
	#undef R6
	return reduceVar;
}

static float eval_check_L_j_0(long j) {
	
	// Check the flags.
	if ((_flag_check_L_j_0(j)) == ('N')) {
		_flag_check_L_j_0(j) = 'I';
		check_L_j_0(j) = reduce6(N,j);
		_flag_check_L_j_0(j) = 'F';
	}
	else if ((_flag_check_L_j_0(j)) == ('I')) {
		printf("There is a self dependence on check_L_j_0 at (%ld)\n",j);
		exit(-1);
	}
	
	return check_L_j_0(j);
}

static float reduce7(long N, long jp) {
	float reduceVar;
	long i;
	
	reduceVar = 0.0f;
	#define RP7(i,j) ((((j) == (0)) && ((i) >= (0))) && ((-1 + N - i) >= (0))) ? ((A(((i)),((j)))) / (eval_U(((j)),((j))))) : (((A(((i)),((j)))) - (eval_check_L_j_1_NR(i,j))) / (eval_U(((j)),((j)))))
	#define R7(i,j) reduceVar = (reduceVar) + (RP7((i),(j)))
	for (i = jp; i < N; i += 1) {
		R7(i, jp);
	}
	#undef RP7
	#undef R7
	return reduceVar;
}

static float eval_check_L_j_1(long j) {
	
	// Check the flags.
	if ((_flag_check_L_j_1(j)) == ('N')) {
		_flag_check_L_j_1(j) = 'I';
		check_L_j_1(j) = reduce7(N,j);
		_flag_check_L_j_1(j) = 'F';
	}
	else if ((_flag_check_L_j_1(j)) == ('I')) {
		printf("There is a self dependence on check_L_j_1 at (%ld)\n",j);
		exit(-1);
	}
	
	return check_L_j_1(j);
}

static float reduce8(long N, long ip, long jp) {
	float reduceVar;
	long k;
	
	reduceVar = 0.0f;
	#define RP8(i,j,k) (eval_L(((i)),((k)))) * (eval_U(((k)),((j))))
	#define R8(i,j,k) reduceVar = (reduceVar) + (RP8((i),(j),(k)))
	for (k = 0; k < ip; k += 1) {
		R8(ip, jp, k);
	}
	#undef RP8
	#undef R8
	return reduceVar;
}

static float eval_U_NR(long i, long j) {
	
	// Check the flags.
	if ((_flag_U_NR(i,j)) == ('N')) {
		_flag_U_NR(i,j) = 'I';
		U_NR(i,j) = reduce8(N,i,j);
		_flag_U_NR(i,j) = 'F';
	}
	else if ((_flag_U_NR(i,j)) == ('I')) {
		printf("There is a self dependence on U_NR at (%ld,%ld)\n",i,j);
		exit(-1);
	}
	
	return U_NR(i,j);
}

static float reduce9(long N, long ip, long jp) {
	float reduceVar;
	long k;
	
	reduceVar = 0.0f;
	#define RP9(i,j,k) (eval_L(((i)),((k)))) * (eval_U(((k)),((j))))
	#define R9(i,j,k) reduceVar = (reduceVar) + (RP9((i),(j),(k)))
	for (k = 0; k < jp; k += 1) {
		R9(ip, jp, k);
	}
	#undef RP9
	#undef R9
	return reduceVar;
}

static float eval_L_NR(long i, long j) {
	
	// Check the flags.
	if ((_flag_L_NR(i,j)) == ('N')) {
		_flag_L_NR(i,j) = 'I';
		L_NR(i,j) = reduce9(N,i,j);
		_flag_L_NR(i,j) = 'F';
	}
	else if ((_flag_L_NR(i,j)) == ('I')) {
		printf("There is a self dependence on L_NR at (%ld,%ld)\n",i,j);
		exit(-1);
	}
	
	return L_NR(i,j);
}

static float reduce10(long N, long ip, long jp) {
	float reduceVar;
	long k;
	
	reduceVar = 0.0f;
	#define RP10(i,j,k) (eval_L(((i)),((k)))) * (eval_U(((k)),((j))))
	#define R10(i,j,k) reduceVar = (reduceVar) + (RP10((i),(j),(k)))
	for (k = 0; k < ip; k += 1) {
		R10(ip, jp, k);
	}
	#undef RP10
	#undef R10
	return reduceVar;
}

static float eval_check_U_i_1_NR(long i, long j) {
	
	// Check the flags.
	if ((_flag_check_U_i_1_NR(i,j)) == ('N')) {
		_flag_check_U_i_1_NR(i,j) = 'I';
		check_U_i_1_NR(i,j) = reduce10(N,i,j);
		_flag_check_U_i_1_NR(i,j) = 'F';
	}
	else if ((_flag_check_U_i_1_NR(i,j)) == ('I')) {
		printf("There is a self dependence on check_U_i_1_NR at (%ld,%ld)\n",i,j);
		exit(-1);
	}
	
	return check_U_i_1_NR(i,j);
}

static float reduce11(long N, long ip, long jp) {
	float reduceVar;
	long k;
	
	reduceVar = 0.0f;
	#define RP11(i,j,k) (eval_L(((i)),((k)))) * (eval_U(((k)),((j))))
	#define R11(i,j,k) reduceVar = (reduceVar) + (RP11((i),(j),(k)))
	for (k = 0; k < ip; k += 1) {
		R11(ip, jp, k);
	}
	#undef RP11
	#undef R11
	return reduceVar;
}

static float eval_check_U_j_1_NR(long i, long j) {
	
	// Check the flags.
	if ((_flag_check_U_j_1_NR(i,j)) == ('N')) {
		_flag_check_U_j_1_NR(i,j) = 'I';
		check_U_j_1_NR(i,j) = reduce11(N,i,j);
		_flag_check_U_j_1_NR(i,j) = 'F';
	}
	else if ((_flag_check_U_j_1_NR(i,j)) == ('I')) {
		printf("There is a self dependence on check_U_j_1_NR at (%ld,%ld)\n",i,j);
		exit(-1);
	}
	
	return check_U_j_1_NR(i,j);
}

static float reduce12(long N, long ip, long jp) {
	float reduceVar;
	long k;
	
	reduceVar = 0.0f;
	#define RP12(i,j,k) (eval_L(((i)),((k)))) * (eval_U(((k)),((j))))
	#define R12(i,j,k) reduceVar = (reduceVar) + (RP12((i),(j),(k)))
	for (k = 0; k < jp; k += 1) {
		R12(ip, jp, k);
	}
	#undef RP12
	#undef R12
	return reduceVar;
}

static float eval_check_L_i_1_NR(long i, long j) {
	
	// Check the flags.
	if ((_flag_check_L_i_1_NR(i,j)) == ('N')) {
		_flag_check_L_i_1_NR(i,j) = 'I';
		check_L_i_1_NR(i,j) = reduce12(N,i,j);
		_flag_check_L_i_1_NR(i,j) = 'F';
	}
	else if ((_flag_check_L_i_1_NR(i,j)) == ('I')) {
		printf("There is a self dependence on check_L_i_1_NR at (%ld,%ld)\n",i,j);
		exit(-1);
	}
	
	return check_L_i_1_NR(i,j);
}

static float reduce13(long N, long ip, long jp) {
	float reduceVar;
	long k;
	
	reduceVar = 0.0f;
	#define RP13(i,j,k) (eval_L(((i)),((k)))) * (eval_U(((k)),((j))))
	#define R13(i,j,k) reduceVar = (reduceVar) + (RP13((i),(j),(k)))
	for (k = 0; k < jp; k += 1) {
		R13(ip, jp, k);
	}
	#undef RP13
	#undef R13
	return reduceVar;
}

static float eval_check_L_j_1_NR(long i, long j) {
	
	// Check the flags.
	if ((_flag_check_L_j_1_NR(i,j)) == ('N')) {
		_flag_check_L_j_1_NR(i,j) = 'I';
		check_L_j_1_NR(i,j) = reduce13(N,i,j);
		_flag_check_L_j_1_NR(i,j) = 'F';
	}
	else if ((_flag_check_L_j_1_NR(i,j)) == ('I')) {
		printf("There is a self dependence on check_L_j_1_NR at (%ld,%ld)\n",i,j);
		exit(-1);
	}
	
	return check_L_j_1_NR(i,j);
}

void lud_aabft(long _local_N, float** _local_A, float** _local_L, float** _local_U, float* _local_check_U_i_0, float* _local_check_U_i_1, float* _local_check_U_j_0, float* _local_check_U_j_1, float* _local_check_L_i_0, float* _local_check_L_i_1, float* _local_check_L_j_0, float* _local_check_L_j_1) {
	long i;
	long j;
	
	// Copy arguments to the global variables.
	N = _local_N;
	A = _local_A;
	L = _local_L;
	U = _local_U;
	check_U_i_0 = _local_check_U_i_0;
	check_U_i_1 = _local_check_U_i_1;
	check_U_j_0 = _local_check_U_j_0;
	check_U_j_1 = _local_check_U_j_1;
	check_L_i_0 = _local_check_L_i_0;
	check_L_i_1 = _local_check_L_i_1;
	check_L_j_0 = _local_check_L_j_0;
	check_L_j_1 = _local_check_L_j_1;
	
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
	check_U_i_1_NR = (float*)(malloc((sizeof(float)) * (((-2 + N >= 0) ? (((-N + N*N))/2) : 0))));
	mallocCheck(check_U_i_1_NR,"check_U_i_1_NR");
	check_U_j_1_NR = (float*)(malloc((sizeof(float)) * (((-2 + N >= 0) ? (((-N + N*N))/2) : 0))));
	mallocCheck(check_U_j_1_NR,"check_U_j_1_NR");
	check_L_i_1_NR = (float*)(malloc((sizeof(float)) * (((-2 + N >= 0) ? (((-N + N*N))/2) : 0))));
	mallocCheck(check_L_i_1_NR,"check_L_i_1_NR");
	check_L_j_1_NR = (float*)(malloc((sizeof(float)) * (((-2 + N >= 0) ? (((-N + N*N))/2) : 0))));
	mallocCheck(check_L_j_1_NR,"check_L_j_1_NR");
	
	// Allocate and initialize flag variables.
	_flag_L = (char*)(malloc((sizeof(char)) * (((-1 + N >= 0) ? (((N + N*N))/2) : 0))));
	mallocCheck(_flag_L,"_flag_L");
	memset(_flag_L,'N',((-1 + N >= 0) ? (((N + N*N))/2) : 0));
	_flag_U = (char*)(malloc((sizeof(char)) * (((-1 + N >= 0) ? (((N + N*N))/2) : 0))));
	mallocCheck(_flag_U,"_flag_U");
	memset(_flag_U,'N',((-1 + N >= 0) ? (((N + N*N))/2) : 0));
	_flag_check_U_i_0 = (char*)(malloc((sizeof(char)) * (((-1 + N >= 0) ? (N) : 0))));
	mallocCheck(_flag_check_U_i_0,"_flag_check_U_i_0");
	memset(_flag_check_U_i_0,'N',((-1 + N >= 0) ? (N) : 0));
	_flag_check_U_i_1 = (char*)(malloc((sizeof(char)) * (((-1 + N >= 0) ? (N) : 0))));
	mallocCheck(_flag_check_U_i_1,"_flag_check_U_i_1");
	memset(_flag_check_U_i_1,'N',((-1 + N >= 0) ? (N) : 0));
	_flag_check_U_j_0 = (char*)(malloc((sizeof(char)) * (((-1 + N >= 0) ? (N) : 0))));
	mallocCheck(_flag_check_U_j_0,"_flag_check_U_j_0");
	memset(_flag_check_U_j_0,'N',((-1 + N >= 0) ? (N) : 0));
	_flag_check_U_j_1 = (char*)(malloc((sizeof(char)) * (((-1 + N >= 0) ? (N) : 0))));
	mallocCheck(_flag_check_U_j_1,"_flag_check_U_j_1");
	memset(_flag_check_U_j_1,'N',((-1 + N >= 0) ? (N) : 0));
	_flag_check_L_i_0 = (char*)(malloc((sizeof(char)) * (((-1 + N >= 0) ? (N) : 0))));
	mallocCheck(_flag_check_L_i_0,"_flag_check_L_i_0");
	memset(_flag_check_L_i_0,'N',((-1 + N >= 0) ? (N) : 0));
	_flag_check_L_i_1 = (char*)(malloc((sizeof(char)) * (((-1 + N >= 0) ? (N) : 0))));
	mallocCheck(_flag_check_L_i_1,"_flag_check_L_i_1");
	memset(_flag_check_L_i_1,'N',((-1 + N >= 0) ? (N) : 0));
	_flag_check_L_j_0 = (char*)(malloc((sizeof(char)) * (((-1 + N >= 0) ? (N) : 0))));
	mallocCheck(_flag_check_L_j_0,"_flag_check_L_j_0");
	memset(_flag_check_L_j_0,'N',((-1 + N >= 0) ? (N) : 0));
	_flag_check_L_j_1 = (char*)(malloc((sizeof(char)) * (((-1 + N >= 0) ? (N) : 0))));
	mallocCheck(_flag_check_L_j_1,"_flag_check_L_j_1");
	memset(_flag_check_L_j_1,'N',((-1 + N >= 0) ? (N) : 0));
	_flag_U_NR = (char*)(malloc((sizeof(char)) * (((-2 + N >= 0) ? (((-N + N*N))/2) : 0))));
	mallocCheck(_flag_U_NR,"_flag_U_NR");
	memset(_flag_U_NR,'N',((-2 + N >= 0) ? (((-N + N*N))/2) : 0));
	_flag_L_NR = (char*)(malloc((sizeof(char)) * (((-2 + N >= 0) ? (((-N + N*N))/2) : 0))));
	mallocCheck(_flag_L_NR,"_flag_L_NR");
	memset(_flag_L_NR,'N',((-2 + N >= 0) ? (((-N + N*N))/2) : 0));
	_flag_check_U_i_1_NR = (char*)(malloc((sizeof(char)) * (((-2 + N >= 0) ? (((-N + N*N))/2) : 0))));
	mallocCheck(_flag_check_U_i_1_NR,"_flag_check_U_i_1_NR");
	memset(_flag_check_U_i_1_NR,'N',((-2 + N >= 0) ? (((-N + N*N))/2) : 0));
	_flag_check_U_j_1_NR = (char*)(malloc((sizeof(char)) * (((-2 + N >= 0) ? (((-N + N*N))/2) : 0))));
	mallocCheck(_flag_check_U_j_1_NR,"_flag_check_U_j_1_NR");
	memset(_flag_check_U_j_1_NR,'N',((-2 + N >= 0) ? (((-N + N*N))/2) : 0));
	_flag_check_L_i_1_NR = (char*)(malloc((sizeof(char)) * (((-2 + N >= 0) ? (((-N + N*N))/2) : 0))));
	mallocCheck(_flag_check_L_i_1_NR,"_flag_check_L_i_1_NR");
	memset(_flag_check_L_i_1_NR,'N',((-2 + N >= 0) ? (((-N + N*N))/2) : 0));
	_flag_check_L_j_1_NR = (char*)(malloc((sizeof(char)) * (((-2 + N >= 0) ? (((-N + N*N))/2) : 0))));
	mallocCheck(_flag_check_L_j_1_NR,"_flag_check_L_j_1_NR");
	memset(_flag_check_L_j_1_NR,'N',((-2 + N >= 0) ? (((-N + N*N))/2) : 0));
	
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
	#define S2(i) eval_check_U_i_0(i)
	for (i = 0; i < N; i += 1) {
		S2(i);
	}
	#undef S2
	#define S3(i) eval_check_U_i_1(i)
	for (i = 0; i < N; i += 1) {
		S3(i);
	}
	#undef S3
	#define S4(j) eval_check_U_j_0(j)
	for (j = 0; j < N; j += 1) {
		S4(j);
	}
	#undef S4
	#define S5(j) eval_check_U_j_1(j)
	for (j = 0; j < N; j += 1) {
		S5(j);
	}
	#undef S5
	#define S6(i) eval_check_L_i_0(i)
	for (i = 0; i < N; i += 1) {
		S6(i);
	}
	#undef S6
	#define S7(i) eval_check_L_i_1(i)
	for (i = 0; i < N; i += 1) {
		S7(i);
	}
	#undef S7
	#define S8(j) eval_check_L_j_0(j)
	for (j = 0; j < N; j += 1) {
		S8(j);
	}
	#undef S8
	#define S9(j) eval_check_L_j_1(j)
	for (j = 0; j < N; j += 1) {
		S9(j);
	}
	#undef S9
	
	// Free all allocated memory.
	free(U_NR);
	free(L_NR);
	free(check_U_i_1_NR);
	free(check_U_j_1_NR);
	free(check_L_i_1_NR);
	free(check_L_j_1_NR);
	free(_flag_L);
	free(_flag_U);
	free(_flag_check_U_i_0);
	free(_flag_check_U_i_1);
	free(_flag_check_U_j_0);
	free(_flag_check_U_j_1);
	free(_flag_check_L_i_0);
	free(_flag_check_L_i_1);
	free(_flag_check_L_j_0);
	free(_flag_check_L_j_1);
	free(_flag_U_NR);
	free(_flag_L_NR);
	free(_flag_check_U_i_1_NR);
	free(_flag_check_U_j_1_NR);
	free(_flag_check_L_i_1_NR);
	free(_flag_check_L_j_1_NR);
}


// Undefine the Memory and Function Macros
#undef A
#undef L
#undef U
#undef check_U_i_0
#undef check_U_i_1
#undef check_U_j_0
#undef check_U_j_1
#undef check_L_i_0
#undef check_L_i_1
#undef check_L_j_0
#undef check_L_j_1
#undef U_NR
#undef L_NR
#undef check_U_i_1_NR
#undef check_U_j_1_NR
#undef check_L_i_1_NR
#undef check_L_j_1_NR
#undef _flag_L
#undef _flag_U
#undef _flag_check_U_i_0
#undef _flag_check_U_i_1
#undef _flag_check_U_j_0
#undef _flag_check_U_j_1
#undef _flag_check_L_i_0
#undef _flag_check_L_i_1
#undef _flag_check_L_j_0
#undef _flag_check_L_j_1
#undef _flag_U_NR
#undef _flag_L_NR
#undef _flag_check_U_i_1_NR
#undef _flag_check_U_j_1_NR
#undef _flag_check_L_i_1_NR
#undef _flag_check_L_j_1_NR
#undef ceild
#undef floord
#undef div
#undef max
#undef min
#undef mallocCheck
