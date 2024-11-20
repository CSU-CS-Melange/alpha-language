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
static float* Inv_b_c;
static float* b_c_0;
static float* b_c_1;
static float* x_NR;
static float* b_c_1_NR;
static char* _flag_x;
static char* _flag_Inv_b_c;
static char* _flag_b_c_0;
static char* _flag_b_c_1;
static char* _flag_x_NR;
static char* _flag_b_c_1_NR;

// Memory Macros
#define L(i,j) L[i][j]
#define b(i) b[i]
#define x(i) x[i]
#define Inv_b_c(s) Inv_b_c[s]
#define b_c_0(s) b_c_0[(0)]
#define b_c_1(s) b_c_1[(0)]
#define x_NR(i) x_NR[((-1 + N - i >= 0 && -2 + i >= 0) ? ((-1 + i)) : 0)]
#define b_c_1_NR(s,i) b_c_1_NR[((s == 0 && -1 + N - i >= 0 && -2 + i >= 0) ? ((-1 + i)) : 0)]
#define _flag_x(i) _flag_x[((-1 + N - i >= 0 && -1 + i >= 0) ? (i) : 0)]
#define _flag_Inv_b_c(s) _flag_Inv_b_c[(0)]
#define _flag_b_c_0(s) _flag_b_c_0[(0)]
#define _flag_b_c_1(s) _flag_b_c_1[(0)]
#define _flag_x_NR(i) _flag_x_NR[((-1 + N - i >= 0 && -2 + i >= 0) ? ((-1 + i)) : 0)]
#define _flag_b_c_1_NR(s,i) _flag_b_c_1_NR[((s == 0 && -1 + N - i >= 0 && -2 + i >= 0) ? ((-1 + i)) : 0)]

// Function Declarations
static float eval_x(long i);
static float eval_Inv_b_c(long s);
static float reduce0(long N, long sp);
static float eval_b_c_0(long s);
static float reduce1(long N, long sp);
static float eval_b_c_1(long s);
static float reduce2(long N, long ip);
static float eval_x_NR(long i);
static float reduce3(long N, long sp, long ip);
static float eval_b_c_1_NR(long s, long i);
void fsub_abft(long _local_N, float** _local_L, float* _local_b, float* _local_x, float* _local_Inv_b_c);

static float eval_x(long i) {
	
	// Check the flags.
	if ((_flag_x(i)) == ('N')) {
		_flag_x(i) = 'I';
		x(i) = (((i) == (0)) && ((-1 + N) >= (0))) ? ((b(i)) / (L(((i)),((i))))) : (((b(i)) - (eval_x_NR(i))) / (L(((i)),((i)))));
		_flag_x(i) = 'F';
	}
	else if ((_flag_x(i)) == ('I')) {
		printf("There is a self dependence on x at (%ld)\n",i);
		exit(-1);
	}
	
	return x(i);
}

static float eval_Inv_b_c(long s) {
	
	// Check the flags.
	if ((_flag_Inv_b_c(s)) == ('N')) {
		_flag_Inv_b_c(s) = 'I';
		Inv_b_c(s) = ((eval_b_c_0(s)) - (eval_b_c_1(s))) / (eval_b_c_0(s));
		_flag_Inv_b_c(s) = 'F';
	}
	else if ((_flag_Inv_b_c(s)) == ('I')) {
		printf("There is a self dependence on Inv_b_c at (%ld)\n",s);
		exit(-1);
	}
	
	return Inv_b_c(s);
}

static float reduce0(long N, long sp) {
	float reduceVar;
	long i;
	
	reduceVar = 0.0f;
	#define RP0(s,i) b(((i)))
	#define R0(s,i) reduceVar = (reduceVar) + (RP0((s),(i)))
	for (i = 0; i < N; i += 1) {
		R0(0, i);
	}
	#undef RP0
	#undef R0
	return reduceVar;
}

static float eval_b_c_0(long s) {
	
	// Check the flags.
	if ((_flag_b_c_0(s)) == ('N')) {
		_flag_b_c_0(s) = 'I';
		b_c_0(s) = reduce0(N,s);
		_flag_b_c_0(s) = 'F';
	}
	else if ((_flag_b_c_0(s)) == ('I')) {
		printf("There is a self dependence on b_c_0 at (%ld)\n",s);
		exit(-1);
	}
	
	return b_c_0(s);
}

static float reduce1(long N, long sp) {
	float reduceVar;
	long i;
	
	reduceVar = 0.0f;
	#define RP1(s,i) (((i) == (0)) && ((-1 + N) >= (0))) ? (eval_x(((i)))) : (eval_b_c_1_NR(s,i))
	#define R1(s,i) reduceVar = (reduceVar) + (RP1((s),(i)))
	for (i = 0; i < N; i += 1) {
		R1(0, i);
	}
	#undef RP1
	#undef R1
	return reduceVar;
}

static float eval_b_c_1(long s) {
	
	// Check the flags.
	if ((_flag_b_c_1(s)) == ('N')) {
		_flag_b_c_1(s) = 'I';
		b_c_1(s) = reduce1(N,s);
		_flag_b_c_1(s) = 'F';
	}
	else if ((_flag_b_c_1(s)) == ('I')) {
		printf("There is a self dependence on b_c_1 at (%ld)\n",s);
		exit(-1);
	}
	
	return b_c_1(s);
}

static float reduce2(long N, long ip) {
	float reduceVar;
	long j;
	
	reduceVar = 0.0f;
	#define RP2(i,j) (L(i,j)) * (eval_x(((j))))
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

static float reduce3(long N, long sp, long ip) {
	float reduceVar;
	long k;
	
	reduceVar = 0.0f;
	#define RP3(s,i,k) (L(((i)),((k)))) * (eval_x(((k))))
	#define R3(s,i,k) reduceVar = (reduceVar) + (RP3((s),(i),(k)))
	for (k = 0; k <= ip; k += 1) {
		R3(0, ip, k);
	}
	#undef RP3
	#undef R3
	return reduceVar;
}

static float eval_b_c_1_NR(long s, long i) {
	
	// Check the flags.
	if ((_flag_b_c_1_NR(s,i)) == ('N')) {
		_flag_b_c_1_NR(s,i) = 'I';
		b_c_1_NR(s,i) = reduce3(N,s,i);
		_flag_b_c_1_NR(s,i) = 'F';
	}
	else if ((_flag_b_c_1_NR(s,i)) == ('I')) {
		printf("There is a self dependence on b_c_1_NR at (%ld,%ld)\n",s,i);
		exit(-1);
	}
	
	return b_c_1_NR(s,i);
}

void fsub_abft(long _local_N, float** _local_L, float* _local_b, float* _local_x, float* _local_Inv_b_c) {
	long i;
	
	// Copy arguments to the global variables.
	N = _local_N;
	L = _local_L;
	b = _local_b;
	x = _local_x;
	Inv_b_c = _local_Inv_b_c;
	
	// Check parameter validity.
	if (!((-1 + N) >= (0))) {
		printf("The value of the parameters are invalid.\n");
		exit(-1);
	}
	
	// Allocate memory for local storage.
	b_c_0 = (float*)(malloc((sizeof(float)) * (((-1 + N >= 0) ? (1) : 0))));
	mallocCheck(b_c_0,"b_c_0");
	b_c_1 = (float*)(malloc((sizeof(float)) * (((-1 + N >= 0) ? (1) : 0))));
	mallocCheck(b_c_1,"b_c_1");
	x_NR = (float*)(malloc((sizeof(float)) * (((-2 + N >= 0) ? ((-1 + N)) : 0))));
	mallocCheck(x_NR,"x_NR");
	b_c_1_NR = (float*)(malloc((sizeof(float)) * (((-2 + N >= 0) ? ((-1 + N)) : 0))));
	mallocCheck(b_c_1_NR,"b_c_1_NR");
	
	// Allocate and initialize flag variables.
	_flag_x = (char*)(malloc((sizeof(char)) * (((-1 + N >= 0) ? (N) : 0))));
	mallocCheck(_flag_x,"_flag_x");
	memset(_flag_x,'N',((-1 + N >= 0) ? (N) : 0));
	_flag_Inv_b_c = (char*)(malloc((sizeof(char)) * (((-1 + N >= 0) ? (1) : 0))));
	mallocCheck(_flag_Inv_b_c,"_flag_Inv_b_c");
	memset(_flag_Inv_b_c,'N',((-1 + N >= 0) ? (1) : 0));
	_flag_b_c_0 = (char*)(malloc((sizeof(char)) * (((-1 + N >= 0) ? (1) : 0))));
	mallocCheck(_flag_b_c_0,"_flag_b_c_0");
	memset(_flag_b_c_0,'N',((-1 + N >= 0) ? (1) : 0));
	_flag_b_c_1 = (char*)(malloc((sizeof(char)) * (((-1 + N >= 0) ? (1) : 0))));
	mallocCheck(_flag_b_c_1,"_flag_b_c_1");
	memset(_flag_b_c_1,'N',((-1 + N >= 0) ? (1) : 0));
	_flag_x_NR = (char*)(malloc((sizeof(char)) * (((-2 + N >= 0) ? ((-1 + N)) : 0))));
	mallocCheck(_flag_x_NR,"_flag_x_NR");
	memset(_flag_x_NR,'N',((-2 + N >= 0) ? ((-1 + N)) : 0));
	_flag_b_c_1_NR = (char*)(malloc((sizeof(char)) * (((-2 + N >= 0) ? ((-1 + N)) : 0))));
	mallocCheck(_flag_b_c_1_NR,"_flag_b_c_1_NR");
	memset(_flag_b_c_1_NR,'N',((-2 + N >= 0) ? ((-1 + N)) : 0));
	
	// Evaluate all the outputs.
	#define S0(i) eval_x(i)
	for (i = 0; i < N; i += 1) {
		S0(i);
	}
	#undef S0
	#define S1(s) eval_Inv_b_c(s)
	S1(0);
	#undef S1
	
	// Free all allocated memory.
	free(b_c_0);
	free(b_c_1);
	free(x_NR);
	free(b_c_1_NR);
	free(_flag_x);
	free(_flag_Inv_b_c);
	free(_flag_b_c_0);
	free(_flag_b_c_1);
	free(_flag_x_NR);
	free(_flag_b_c_1_NR);
}


// Undefine the Memory and Function Macros
#undef L
#undef b
#undef x
#undef Inv_b_c
#undef b_c_0
#undef b_c_1
#undef x_NR
#undef b_c_1_NR
#undef _flag_x
#undef _flag_Inv_b_c
#undef _flag_b_c_0
#undef _flag_b_c_1
#undef _flag_x_NR
#undef _flag_b_c_1_NR
#undef ceild
#undef floord
#undef div
#undef max
#undef min
#undef mallocCheck
