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
static float* A;
static float* B;
static float* C;
static float* check_C_i_inv;
static float* check_C_j_inv;
static float* check_C_i_0;
static float* check_C_i_1;
static float* check_C_j_0;
static float* check_C_j_1;
static float* check_C_i_1_NR;
static float* check_C_j_1_NR;

// Memory Macros
#define mem_A(i,j) A[((-1 + i >= 0 && -1 + N - i >= 0 && -1 + j >= 0 && -1 + N - j >= 0) ? ((N * i + j)) : (j == 0 && -1 + i >= 0 && -1 + N - i >= 0 && -1 + N >= 0) ? (N * i) : (i == 0 && -1 + N - j >= 0 && -1 + N >= 0 && -1 + j >= 0) ? (j) : 0)]
#define A(i,j) mem_A(((i)),((j)))
#define mem_B(i,j) B[((-1 + i >= 0 && -1 + N - i >= 0 && -1 + j >= 0 && -1 + N - j >= 0) ? ((N * i + j)) : (j == 0 && -1 + i >= 0 && -1 + N - i >= 0 && -1 + N >= 0) ? (N * i) : (i == 0 && -1 + N - j >= 0 && -1 + N >= 0 && -1 + j >= 0) ? (j) : 0)]
#define B(i,j) mem_B(((i)),((j)))
#define mem_C(i,j) C[((-1 + i >= 0 && -1 + N - i >= 0 && -1 + j >= 0 && -1 + N - j >= 0) ? ((N * i + j)) : (j == 0 && -1 + i >= 0 && -1 + N - i >= 0 && -1 + N >= 0) ? (N * i) : (i == 0 && -1 + N - j >= 0 && -1 + N >= 0 && -1 + j >= 0) ? (j) : 0)]
#define C(i,j) mem_C(((i)),((j)))
#define mem_check_C_i_inv(i) check_C_i_inv[((-1 + N - i >= 0 && -1 + i >= 0) ? (i) : 0)]
#define check_C_i_inv(i) mem_check_C_i_inv(((i)))
#define mem_check_C_j_inv(j) check_C_j_inv[((-1 + N - j >= 0 && -1 + j >= 0) ? (j) : 0)]
#define check_C_j_inv(j) mem_check_C_j_inv(((j)))
#define mem_check_C_i_0(i) check_C_i_0[((-1 + N - i >= 0 && -1 + i >= 0) ? (i) : 0)]
#define check_C_i_0(i) mem_check_C_i_0(((i)))
#define mem_check_C_i_1(i) check_C_i_1[((-1 + N - i >= 0 && -1 + i >= 0) ? (i) : 0)]
#define check_C_i_1(i) mem_check_C_i_1(((i)))
#define mem_check_C_j_0(j) check_C_j_0[((-1 + N - j >= 0 && -1 + j >= 0) ? (j) : 0)]
#define check_C_j_0(j) mem_check_C_j_0(((j)))
#define mem_check_C_j_1(j) check_C_j_1[((-1 + N - j >= 0 && -1 + j >= 0) ? (j) : 0)]
#define check_C_j_1(j) mem_check_C_j_1(((j)))
#define mem_check_C_i_1_NR(i0,i1) check_C_i_1_NR[((-1 + i0 >= 0 && -1 + N - i0 >= 0 && -1 + i1 >= 0 && -1 + N - i1 >= 0) ? ((N * i0 + i1)) : (i1 == 0 && -1 + i0 >= 0 && -1 + N - i0 >= 0 && -1 + N >= 0) ? (N * i0) : (i0 == 0 && -1 + N - i1 >= 0 && -1 + N >= 0 && -1 + i1 >= 0) ? (i1) : 0)]
#define check_C_i_1_NR(i0,i1) mem_check_C_i_1_NR(((i0)),((i1)))
#define mem_check_C_j_1_NR(i0,i1) check_C_j_1_NR[((-1 + i0 >= 0 && -1 + N - i0 >= 0 && -1 + i1 >= 0 && -1 + N - i1 >= 0) ? ((N * i0 + i1)) : (i1 == 0 && -1 + i0 >= 0 && -1 + N - i0 >= 0 && -1 + N >= 0) ? (N * i0) : (i0 == 0 && -1 + N - i1 >= 0 && -1 + N >= 0 && -1 + i1 >= 0) ? (i1) : 0)]
#define check_C_j_1_NR(i0,i1) mem_check_C_j_1_NR(((i0)),((i1)))

// Function Declarations
static float reduce0(long N, long ip, long jp);
static void eval_C(long i, long j);
static float reduce1(long N, long ip);
static void eval_check_C_i_0(long i);
static float reduce2(long N, long ip);
static void eval_check_C_i_1(long i);
static void eval_check_C_i_inv(long i);
static float reduce3(long N, long jp);
static void eval_check_C_j_0(long j);
static float reduce4(long N, long jp);
static void eval_check_C_j_1(long j);
static void eval_check_C_j_inv(long j);
static float reduce5(long N, long i0p, long i1p);
static void eval_check_C_i_1_NR(long i0, long i1);
static float reduce6(long N, long i0p, long i1p);
static void eval_check_C_j_1_NR(long i0, long i1);
void matmult_aabft(long _local_N, float* _local_A, float* _local_B, float* _local_C, float* _local_check_C_i_inv, float* _local_check_C_j_inv);

static float reduce0(long N, long ip, long jp) {
	float reduceVar;
	long c3;
	
	reduceVar = 0.0f;
	#define RP0(i,j,k) (A(((i)),((k)))) * (B(((k)),((j))))
	#define R0(i,j,k) reduceVar = (reduceVar) + (RP0((i),(j),(k)))
	for (c3 = 0; c3 < N; c3 += 1) {
		R0(ip, jp, c3);
	}
	#undef RP0
	#undef R0
	return reduceVar;
}

static void eval_C(long i, long j) {
	
	C(i,j) = reduce0(N,i,j);
}

static float reduce1(long N, long ip) {
	float reduceVar;
	long c2;
	
	reduceVar = 0.0f;
	#define RP1(i,j) C(((i)),((j)))
	#define R1(i,j) reduceVar = (reduceVar) + (RP1((i),(j)))
	for (c2 = 0; c2 < N; c2 += 1) {
		R1(ip, c2);
	}
	#undef RP1
	#undef R1
	return reduceVar;
}

static void eval_check_C_i_0(long i) {
	
	check_C_i_0(i) = reduce1(N,i);
}

static float reduce2(long N, long ip) {
	float reduceVar;
	long c2;
	
	reduceVar = 0.0f;
	#define RP2(i,j) check_C_i_1_NR(((i)),((j)))
	#define R2(i,j) reduceVar = (reduceVar) + (RP2((i),(j)))
	for (c2 = 0; c2 < N; c2 += 1) {
		R2(ip, c2);
	}
	#undef RP2
	#undef R2
	return reduceVar;
}

static void eval_check_C_i_1(long i) {
	
	check_C_i_1(i) = reduce2(N,i);
}

static void eval_check_C_i_inv(long i) {
	
	check_C_i_inv(i) = ((check_C_i_0(((i)))) - (check_C_i_1(((i))))) / (check_C_i_0(((i))));
}

static float reduce3(long N, long jp) {
	float reduceVar;
	long j;
	
	reduceVar = 0.0f;
	#define RP3(i,j) C((((i))),(((j))))
	#define R3(i,j) reduceVar = (reduceVar) + (RP3((i),(j)))
	for (j = 0; j < N; j += 1) {
		R3(j, jp);
	}
	#undef RP3
	#undef R3
	return reduceVar;
}

static void eval_check_C_j_0(long j) {
	
	check_C_j_0(j) = reduce3(N,j);
}

static float reduce4(long N, long jp) {
	float reduceVar;
	long j;
	
	reduceVar = 0.0f;
	#define RP4(i,j) check_C_j_1_NR((((i))),(((j))))
	#define R4(i,j) reduceVar = (reduceVar) + (RP4((i),(j)))
	for (j = 0; j < N; j += 1) {
		R4(j, jp);
	}
	#undef RP4
	#undef R4
	return reduceVar;
}

static void eval_check_C_j_1(long j) {
	
	check_C_j_1(j) = reduce4(N,j);
}

static void eval_check_C_j_inv(long j) {
	
	check_C_j_inv(j) = ((check_C_j_0(((j)))) - (check_C_j_1(((j))))) / (check_C_j_0(((j))));
}

static float reduce5(long N, long i0p, long i1p) {
	float reduceVar;
	long c3;
	
	reduceVar = 0.0f;
	#define RP5(i0,i1,k) (A(((i0)),((k)))) * (B(((k)),((i1))))
	#define R5(i0,i1,k) reduceVar = (reduceVar) + (RP5((i0),(i1),(k)))
	for (c3 = 0; c3 < N; c3 += 1) {
		R5(i0p, i1p, c3);
	}
	#undef RP5
	#undef R5
	return reduceVar;
}

static void eval_check_C_i_1_NR(long i0, long i1) {
	
	check_C_i_1_NR(i0,i1) = reduce5(N,i0,i1);
}

static float reduce6(long N, long i0p, long i1p) {
	float reduceVar;
	long c3;
	
	reduceVar = 0.0f;
	#define RP6(i0,i1,k) (A(((i0)),((k)))) * (B(((k)),((i1))))
	#define R6(i0,i1,k) reduceVar = (reduceVar) + (RP6((i0),(i1),(k)))
	for (c3 = 0; c3 < N; c3 += 1) {
		R6(i0p, i1p, c3);
	}
	#undef RP6
	#undef R6
	return reduceVar;
}

static void eval_check_C_j_1_NR(long i0, long i1) {
	
	check_C_j_1_NR(i0,i1) = reduce6(N,i0,i1);
}

void matmult_aabft(long _local_N, float* _local_A, float* _local_B, float* _local_C, float* _local_check_C_i_inv, float* _local_check_C_j_inv) {
	long c1;
	long c2;
	
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
	
	// Evaluate all the outputs.
	for (c1 = 0; c1 < N; c1 += 1) {
		for (c2 = 0; c2 < N; c2 += 1) {
			eval_C(c1, c2);
		}
	}
	for (c1 = 0; c1 < N; c1 += 1) {
		eval_check_C_i_0(c1);
	}
	for (c1 = 0; c1 < N; c1 += 1) {
		for (c2 = 0; c2 < N; c2 += 1) {
			eval_check_C_i_1_NR(c1, c2);
		}
	}
	for (c1 = 0; c1 < N; c1 += 1) {
		for (c2 = 0; c2 < N; c2 += 1) {
			eval_check_C_j_1_NR(c1, c2);
		}
	}
	for (c1 = 0; c1 < N; c1 += 1) {
		eval_check_C_j_1(c1);
	}
	for (c1 = 0; c1 < N; c1 += 1) {
		eval_check_C_j_0(c1);
	}
	for (c1 = 0; c1 < N; c1 += 1) {
		eval_check_C_j_inv(c1);
	}
	for (c1 = 0; c1 < N; c1 += 1) {
		eval_check_C_i_1(c1);
	}
	for (c1 = 0; c1 < N; c1 += 1) {
		eval_check_C_i_inv(c1);
	}
	
	// Free all allocated memory.
	free(check_C_i_0);
	free(check_C_i_1);
	free(check_C_j_0);
	free(check_C_j_1);
	free(check_C_i_1_NR);
	free(check_C_j_1_NR);
}


// Undefine the Memory and Function Macros
#undef mem_A
#undef A
#undef mem_B
#undef B
#undef mem_C
#undef C
#undef mem_check_C_i_inv
#undef check_C_i_inv
#undef mem_check_C_j_inv
#undef check_C_j_inv
#undef mem_check_C_i_0
#undef check_C_i_0
#undef mem_check_C_i_1
#undef check_C_i_1
#undef mem_check_C_j_0
#undef check_C_j_0
#undef mem_check_C_j_1
#undef check_C_j_1
#undef mem_check_C_i_1_NR
#undef check_C_i_1_NR
#undef mem_check_C_j_1_NR
#undef check_C_j_1_NR
#undef ceild
#undef floord
#undef div
#undef max
#undef min
#undef mallocCheck