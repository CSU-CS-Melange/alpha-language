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
static float* a;
static float* b;
static float* c;
static char* _flag_b;
static char* _flag_c;

// Memory Macros
#define a() a[0]
#define b() b[0]
#define c() c[0]
#define _flag_b() _flag_b[(0)]
#define _flag_c() _flag_c[(0)]

// Function Declarations
static float eval_b();
static float eval_c();
void ext(long _local_N, float* _local_a, float* _local_b, float* _local_c);

static float eval_b() {
	
	// Check the flags.
	if ((_flag_b()) == ('N')) {
		_flag_b() = 'I';
		b() = (2) * (a());
		_flag_b() = 'F';
	}
	else if ((_flag_b()) == ('I')) {
		printf("There is a self dependence on b at ()\n");
		exit(-1);
	}
	
	return b();
}

static float eval_c() {
	
	// Check the flags.
	if ((_flag_c()) == ('N')) {
		_flag_c() = 'I';
		c() = (a()) - (1);
		_flag_c() = 'F';
	}
	else if ((_flag_c()) == ('I')) {
		printf("There is a self dependence on c at ()\n");
		exit(-1);
	}
	
	return c();
}

void ext(long _local_N, float* _local_a, float* _local_b, float* _local_c) {
	
	// Copy arguments to the global variables.
	N = _local_N;
	a = _local_a;
	b = _local_b;
	c = _local_c;
	
	// Check parameter validity.
	if (!((-1 + N) >= (0))) {
		printf("The value of the parameters are invalid.\n");
		exit(-1);
	}
	
	// Allocate memory for local storage.
	
	// Allocate and initialize flag variables.
	_flag_b = (char*)(malloc((sizeof(char)) * (((-1 + N >= 0) ? (1) : 0))));
	mallocCheck(_flag_b,"_flag_b");
	memset(_flag_b,'N',((-1 + N >= 0) ? (1) : 0));
	_flag_c = (char*)(malloc((sizeof(char)) * (((-1 + N >= 0) ? (1) : 0))));
	mallocCheck(_flag_c,"_flag_c");
	memset(_flag_c,'N',((-1 + N >= 0) ? (1) : 0));
	
	// Evaluate all the outputs.
	#define S0() eval_b()
	S0();
	#undef S0
	#define S1() eval_c()
	S1();
	#undef S1
	
	// Free all allocated memory.
	free(_flag_b);
	free(_flag_c);
}


// Undefine the Memory and Function Macros
#undef a
#undef b
#undef c
#undef _flag_b
#undef _flag_c
#undef ceild
#undef floord
#undef div
#undef max
#undef min
#undef mallocCheck
