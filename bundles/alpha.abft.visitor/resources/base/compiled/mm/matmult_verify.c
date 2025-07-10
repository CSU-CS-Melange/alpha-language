// This file is generated from test alphabets program by code generator in alphaz
// To compile this code, use -lm option for math library.

// Includes
#include <stdio.h>
#include <stdlib.h>
#include <stdbool.h>
#include <math.h>
#include <string.h>
#include <limits.h>
#include <float.h>


// Common Macros
#define max(x, y)   ((x)>(y) ? (x) : (y))
#define MAX(x, y)	((x)>(y) ? (x) : (y))
#define min(x, y)   ((x)>(y) ? (y) : (x))
#define MIN(x, y)	((x)>(y) ? (y) : (x))
#define CEILD(n,d)  (int)ceil(((double)(n))/((double)(d)))
#define ceild(n,d)  (int)ceil(((double)(n))/((double)(d)))
#define FLOORD(n,d) (int)floor(((double)(n))/((double)(d)))
#define floord(n,d) (int)floor(((double)(n))/((double)(d)))
#define CDIV(x,y)    CEILD((x),(y))
#define div(x,y)    CDIV((x),(y))
#define FDIV(x,y)    FLOORD((x),(y))
#define LB_SHIFT(b,s)  ((int)ceild(b,s) * s)
#define MOD(i,j)   ((i)%(j))
#define mallocCheck(v,s,d) if ((v) == NULL) { printf("Failed to allocate memory for %s : size=%lu\n", "sizeof(d)*(s)", sizeof(d)*(s)); exit(-1); }

// Common functions for min and max
//functions for integer max
inline int __max_int(int x, int y){
	return ((x)>(y) ? (x) : (y));
}

inline short __max_short(short x, short y){
	return ((x)>(y) ? (x) : (y));
}

inline long __max_long(long x, long y){
	return ((x)>(y) ? (x) : (y));
}

inline unsigned int __max_unsigned_int(unsigned int x, unsigned int y){
	return ((x)>(y) ? (x) : (y));
}

inline unsigned short __max_unsigned_short(unsigned short x, unsigned short y){
	return ((x)>(y) ? (x) : (y));
}

//function for float max
inline float __max_float(float x, float y){
	return ((x)>(y) ? (x) : (y));
}

//function for double max
inline double __max_double(double x, double y){
	return ((x)>(y) ? (x) : (y));
}

//function for integer min
inline int __min_int(int x, int y){
	return ((x)>(y) ? (y) : (x));
}

inline short __min_short(short x, short y){
	return ((x)>(y) ? (y) : (x));
}

inline long __min_long(long x, long y){
	return ((x)>(y) ? (y) : (x));
}

inline unsigned int __min_unsigned_int(unsigned int x, unsigned int y){
	return ((x)>(y) ? (y) : (x));
}

inline unsigned short __min_unsigned_short(unsigned short x, unsigned short y){
	return ((x)>(y) ? (y) : (x));
}

inline unsigned long __min_unsigned_long(unsigned long x, unsigned long y){
	return ((x)>(y) ? (y) : (x));
}

inline float __min_float(float x, float y){
	return ((x)>(y) ? (y) : (x));
}

inline double __min_double(double x, double y){
	return ((x)>(y) ? (y) : (x));
}



///Global Variables
static float** A;
static float** B;
static float** C;
static char** _flag_C;


//Local Function Declarations
float reduce_matmult_verify_C_1(long, int, int);
float eval_verify_C(long, int, int);

//Memory Macros
#define A(i,j) A[i][j]
#define B(i,j) B[i][j]
#define C(i,j) C[i][j]
#define _flag_C(i,j) _flag_C[i][j]

void matmult_verify(long N, float** _local_A, float** _local_B, float** _local_C){
	///Parameter checking
	if (!((N >= 1))) {
		printf("The value of parameters are not valid.\n");
		exit(-1);
	}
	//Copy to global
	A = _local_A;
	B = _local_B;
	C = _local_C;
	
	//Memory Allocation
	int mz1, mz2;
	
	char* _lin__flag_C = (char*)malloc(sizeof(char)*((N) * (N)));
	mallocCheck(_lin__flag_C, ((N) * (N)), char);
	_flag_C = (char**)malloc(sizeof(char*)*(N));
	mallocCheck(_flag_C, (N), char*);
	for (mz1=0;mz1 < N; mz1++) {
		_flag_C[mz1] = &_lin__flag_C[(mz1*(N))];
	}
	memset(_lin__flag_C, 'N', ((N) * (N)));
	#define S0(i,j) eval_verify_C(N,i,j)
	{
		//Domain
		//{i,j|i>=0 && j>=0 && N>=i+1 && N>=j+1 && N>=1}
		int c1,c2;
		for(c1=0;c1 <= N-1;c1+=1)
		 {
		 	for(c2=0;c2 <= N-1;c2+=1)
		 	 {
		 	 	S0((c1),(c2));
		 	 }
		 }
	}
	#undef S0
	
	//Memory Free
	free(_lin__flag_C);
	free(_flag_C);
}
float reduce_matmult_verify_C_1(long N, int ip, int jp){
	float reduceVar = 0;
	#define S0(i,j,k) reduceVar = (reduceVar)+((A(i,k))*(B(k,j)))
	{
		//Domain
		//{i,j,k|ip>=0 && N>=ip+1 && jp>=0 && N>=jp+1 && N>=1 && i>=0 && N>=i+1 && k>=0 && N>=k+1 && j>=0 && N>=j+1 && ip==i && jp==j}
		int c3;
		for(c3=0;c3 <= N-1;c3+=1)
		 {
		 	S0((ip),(jp),(c3));
		 }
	}
	#undef S0
	return reduceVar;
}
float eval_verify_C(long N, int i, int j){
	if ( _flag_C(i,j) == 'N' ) {
		_flag_C(i,j) = 'I';
	//Body for C
		C(i,j) = reduce_matmult_verify_C_1(N,i,j);
		_flag_C(i,j) = 'F';
	} else if ( _flag_C(i,j) == 'I' ) {
		printf("There is a self dependence on C at (%d,%d) \n",i,j);
		exit(-1);
	}
	return C(i,j);
}

//Memory Macros
#undef A
#undef B
#undef C
#undef _flag_C


//Common Macro undefs
#undef max
#undef MAX
#undef min
#undef MIN
#undef CEILD
#undef ceild
#undef FLOORD
#undef floord
#undef CDIV
#undef FDIV
#undef LB_SHIFT
#undef MOD
