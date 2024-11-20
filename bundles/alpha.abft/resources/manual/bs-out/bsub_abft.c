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
static float* Inv_b_c;
static float* b_1;
static float* U_c;
static float* x_NR;
static float* b_1_NR;
static char* _flag_x;
static char* _flag_Inv_b_c;
static char* _flag_b_1;
static char* _flag_U_c;
static char* _flag_x_NR;
static char* _flag_b_1_NR;

// Memory Macros
#define U(i,j) U[i][j]
#define b(i) b[i]
#define x(i) x[i]
#define Inv_b_c(i) Inv_b_c[i]
#define b_1(i) b_1[((-1 + N - i >= 0 && -1 + i >= 0) ? (i) : 0)]
#define U_c(j) U_c[((-1 + N - j >= 0 && -1 + j >= 0) ? (j) : 0)]
#define x_NR(i) x_NR[((-2 + N - i >= 0 && -1 + i >= 0) ? (i) : 0)]
#define b_1_NR(i) b_1_NR[((-2 + N - i >= 0 && -1 + i >= 0) ? (i) : 0)]
#define _flag_x(i) _flag_x[((-1 + N - i >= 0 && -1 + i >= 0) ? (i) : 0)]
#define _flag_Inv_b_c(i) _flag_Inv_b_c[((-1 + N - i >= 0 && -1 + i >= 0) ? (i) : 0)]
#define _flag_b_1(i) _flag_b_1[((-1 + N - i >= 0 && -1 + i >= 0) ? (i) : 0)]
#define _flag_U_c(j) _flag_U_c[((-1 + N - j >= 0 && -1 + j >= 0) ? (j) : 0)]
#define _flag_x_NR(i) _flag_x_NR[((-2 + N - i >= 0 && -1 + i >= 0) ? (i) : 0)]
#define _flag_b_1_NR(i) _flag_b_1_NR[((-2 + N - i >= 0 && -1 + i >= 0) ? (i) : 0)]

// Function Declarations
static float eval_x(long i);
static float reduce0(long N, long jp);
static float eval_U_c(long j);
static float eval_b_1(long i);
static float eval_Inv_b_c(long i);
static float reduce1(long N, long ip);
static float eval_x_NR(long i);
static float reduce2(long N, long ip);
static float eval_b_1_NR(long i);
void bsub_abft(long _local_N, float** _local_U, float* _local_b, float* _local_x, float* _local_Inv_b_c);

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

static float reduce0(long N, long jp) {
	float reduceVar;
	long i;
	
	reduceVar = 0.0f;
	#define RP0(j,i) U(((i)),((j)))
	#define R0(j,i) reduceVar = (reduceVar) + (RP0((j),(i)))
	for (i = 0; i <= jp; i += 1) {
		R0(jp, i);
	}
	#undef RP0
	#undef R0
	return reduceVar;
}

static float eval_U_c(long j) {
	
	// Check the flags.
	if ((_flag_U_c(j)) == ('N')) {
		_flag_U_c(j) = 'I';
		U_c(j) = reduce0(N,j);
		_flag_U_c(j) = 'F';
	}
	else if ((_flag_U_c(j)) == ('I')) {
		printf("There is a self dependence on U_c at (%ld)\n",j);
		exit(-1);
	}
	
	return U_c(j);
}

static float eval_b_1(long i) {
	
	// Check the flags.
	if ((_flag_b_1(i)) == ('N')) {
		_flag_b_1(i) = 'I';
		b_1(i) = (((1 - N + i) == (0)) && ((-1 + N) >= (0))) ? (b(i)) : (eval_b_1_NR(i));
		_flag_b_1(i) = 'F';
	}
	else if ((_flag_b_1(i)) == ('I')) {
		printf("There is a self dependence on b_1 at (%ld)\n",i);
		exit(-1);
	}
	
	return b_1(i);
}

static float eval_Inv_b_c(long i) {
	
	// Check the flags.
	if ((_flag_Inv_b_c(i)) == ('N')) {
		_flag_Inv_b_c(i) = 'I';
		Inv_b_c(i) = ((b(i)) - (eval_b_1(i))) / (b(i));
		_flag_Inv_b_c(i) = 'F';
	}
	else if ((_flag_Inv_b_c(i)) == ('I')) {
		printf("There is a self dependence on Inv_b_c at (%ld)\n",i);
		exit(-1);
	}
	
	return Inv_b_c(i);
}

static float reduce1(long N, long ip) {
	float reduceVar;
	long j;
	
	reduceVar = 0.0f;
	#define RP1(i,j) (U(i,j)) * (eval_x(((j))))
	#define R1(i,j) reduceVar = (reduceVar) + (RP1((i),(j)))
	for (j = ip + 1; j < N; j += 1) {
		R1(ip, j);
	}
	#undef RP1
	#undef R1
	return reduceVar;
}

static float eval_x_NR(long i) {
	
	// Check the flags.
	if ((_flag_x_NR(i)) == ('N')) {
		_flag_x_NR(i) = 'I';
		x_NR(i) = reduce1(N,i);
		_flag_x_NR(i) = 'F';
	}
	else if ((_flag_x_NR(i)) == ('I')) {
		printf("There is a self dependence on x_NR at (%ld)\n",i);
		exit(-1);
	}
	
	return x_NR(i);
}

static float reduce2(long N, long ip) {
	float reduceVar;
	long k;
	
	reduceVar = 0.0f;
	#define RP2(i,k) (U(i,k)) * (eval_x(((k))))
	#define R2(i,k) reduceVar = (reduceVar) + (RP2((i),(k)))
	for (k = ip; k < N; k += 1) {
		R2(ip, k);
	}
	#undef RP2
	#undef R2
	return reduceVar;
}

static float eval_b_1_NR(long i) {
	
	// Check the flags.
	if ((_flag_b_1_NR(i)) == ('N')) {
		_flag_b_1_NR(i) = 'I';
		b_1_NR(i) = reduce2(N,i);
		_flag_b_1_NR(i) = 'F';
	}
	else if ((_flag_b_1_NR(i)) == ('I')) {
		printf("There is a self dependence on b_1_NR at (%ld)\n",i);
		exit(-1);
	}
	
	return b_1_NR(i);
}

void bsub_abft(long _local_N, float** _local_U, float* _local_b, float* _local_x, float* _local_Inv_b_c) {
	long i;
	
	// Copy arguments to the global variables.
	N = _local_N;
	U = _local_U;
	b = _local_b;
	x = _local_x;
	Inv_b_c = _local_Inv_b_c;
	
	// Check parameter validity.
	if (!((-1 + N) >= (0))) {
		printf("The value of the parameters are invalid.\n");
		exit(-1);
	}
	
	// Allocate memory for local storage.
	b_1 = (float*)(malloc((sizeof(float)) * (((-1 + N >= 0) ? (N) : 0))));
	mallocCheck(b_1,"b_1");
	U_c = (float*)(malloc((sizeof(float)) * (((-1 + N >= 0) ? (N) : 0))));
	mallocCheck(U_c,"U_c");
	x_NR = (float*)(malloc((sizeof(float)) * (((-2 + N >= 0) ? ((-1 + N)) : 0))));
	mallocCheck(x_NR,"x_NR");
	b_1_NR = (float*)(malloc((sizeof(float)) * (((-2 + N >= 0) ? ((-1 + N)) : 0))));
	mallocCheck(b_1_NR,"b_1_NR");
	
	// Allocate and initialize flag variables.
	_flag_x = (char*)(malloc((sizeof(char)) * (((-1 + N >= 0) ? (N) : 0))));
	mallocCheck(_flag_x,"_flag_x");
	memset(_flag_x,'N',((-1 + N >= 0) ? (N) : 0));
	_flag_Inv_b_c = (char*)(malloc((sizeof(char)) * (((-1 + N >= 0) ? (N) : 0))));
	mallocCheck(_flag_Inv_b_c,"_flag_Inv_b_c");
	memset(_flag_Inv_b_c,'N',((-1 + N >= 0) ? (N) : 0));
	_flag_b_1 = (char*)(malloc((sizeof(char)) * (((-1 + N >= 0) ? (N) : 0))));
	mallocCheck(_flag_b_1,"_flag_b_1");
	memset(_flag_b_1,'N',((-1 + N >= 0) ? (N) : 0));
	_flag_U_c = (char*)(malloc((sizeof(char)) * (((-1 + N >= 0) ? (N) : 0))));
	mallocCheck(_flag_U_c,"_flag_U_c");
	memset(_flag_U_c,'N',((-1 + N >= 0) ? (N) : 0));
	_flag_x_NR = (char*)(malloc((sizeof(char)) * (((-2 + N >= 0) ? ((-1 + N)) : 0))));
	mallocCheck(_flag_x_NR,"_flag_x_NR");
	memset(_flag_x_NR,'N',((-2 + N >= 0) ? ((-1 + N)) : 0));
	_flag_b_1_NR = (char*)(malloc((sizeof(char)) * (((-2 + N >= 0) ? ((-1 + N)) : 0))));
	mallocCheck(_flag_b_1_NR,"_flag_b_1_NR");
	memset(_flag_b_1_NR,'N',((-2 + N >= 0) ? ((-1 + N)) : 0));
	
	// Evaluate all the outputs.
	#define S0(i) eval_x(i)
	for (i = 0; i < N; i += 1) {
		S0(i);
	}
	#undef S0
	#define S1(i) eval_Inv_b_c(i)
	for (i = 0; i < N; i += 1) {
		S1(i);
	}
	#undef S1
	
	// Free all allocated memory.
	free(b_1);
	free(U_c);
	free(x_NR);
	free(b_1_NR);
	free(_flag_x);
	free(_flag_Inv_b_c);
	free(_flag_b_1);
	free(_flag_U_c);
	free(_flag_x_NR);
	free(_flag_b_1_NR);
}


// Undefine the Memory and Function Macros
#undef U
#undef b
#undef x
#undef Inv_b_c
#undef b_1
#undef U_c
#undef x_NR
#undef b_1_NR
#undef _flag_x
#undef _flag_Inv_b_c
#undef _flag_b_1
#undef _flag_U_c
#undef _flag_x_NR
#undef _flag_b_1_NR
#undef ceild
#undef floord
#undef div
#undef max
#undef min
#undef mallocCheck
