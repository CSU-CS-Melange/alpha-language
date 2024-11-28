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
static float** L;
static float** L_T;
static float x;
static char** _flag_L;
static char** _flag_L_T;
static char _flag_x;


//Local Function Declarations
float eval_verify_L(long, int, int);
float eval_verify_L_T(long, int, int);
float eval_verify_x(long);

//Memory Macros
#define A(i,j) A[i][j]
#define L(i,j) L[i][j]
#define L_T(i,j) L_T[i][j]
#define _flag_L(i,j) _flag_L[i][j]
#define _flag_L_T(i,j) _flag_L_T[i][j]

void cholesky_banachiewicz_verify(long N, float** _local_A, float** _local_L, float** _local_L_T, float* _local_x){
	///Parameter checking
	if (!((N >= 1))) {
		printf("The value of parameters are not valid.\n");
		exit(-1);
	}
	//Copy to global
	A = _local_A;
	L = _local_L;
	L_T = _local_L_T;
	
	
	//Memory Allocation
	int mz1, mz2;
	
	char* _lin__flag_L = (char*)malloc(sizeof(char)*((N) * (N)));
	mallocCheck(_lin__flag_L, ((N) * (N)), char);
	_flag_L = (char**)malloc(sizeof(char*)*(N));
	mallocCheck(_flag_L, (N), char*);
	for (mz1=0;mz1 < N; mz1++) {
		_flag_L[mz1] = &_lin__flag_L[(mz1*(N))];
	}
	memset(_lin__flag_L, 'N', ((N) * (N)));
	
	char* _lin__flag_L_T = (char*)malloc(sizeof(char)*((N) * (N)));
	mallocCheck(_lin__flag_L_T, ((N) * (N)), char);
	_flag_L_T = (char**)malloc(sizeof(char*)*(N));
	mallocCheck(_flag_L_T, (N), char*);
	for (mz1=0;mz1 < N; mz1++) {
		_flag_L_T[mz1] = &_lin__flag_L_T[(mz1*(N))];
	}
	memset(_lin__flag_L_T, 'N', ((N) * (N)));
	
	_flag_x = 'N';
	#define S0(i,j) eval_verify_L(N,i,j)
	{
		//Domain
		//{i,j|N>=i+1 && j>=0 && i>=j && N>=1}
		int c1,c2;
		for(c1=0;c1 <= N-1;c1+=1)
		 {
		 	for(c2=0;c2 <= c1;c2+=1)
		 	 {
		 	 	S0((c1),(c2));
		 	 }
		 }
	}
	#undef S0
	#define S0(i,j) eval_verify_L_T(N,i,j)
	{
		//Domain
		//{i,j|i>=0 && j>=i && N>=j+1 && N>=1}
		int c1,c2;
		for(c1=0;c1 <= N-1;c1+=1)
		 {
		 	for(c2=c1;c2 <= N-1;c2+=1)
		 	 {
		 	 	S0((c1),(c2));
		 	 }
		 }
	}
	#undef S0
	#define S0() eval_verify_x(N)
	{
		//Domain
		//{|N>=1}
		S0();
	}
	#undef S0
	//Copy scalars to output
	*_local_x = x;
	
	//Memory Free
	free(_lin__flag_L);
	free(_flag_L);
	
	free(_lin__flag_L_T);
	free(_flag_L_T);
	
}
float eval_verify_L(long N, int i, int j){
	if ( _flag_L(i,j) == 'N' ) {
		_flag_L(i,j) = 'I';
	//Body for L
		L(i,j) = A(i,j);
		_flag_L(i,j) = 'F';
	} else if ( _flag_L(i,j) == 'I' ) {
		printf("There is a self dependence on L at (%d,%d) \n",i,j);
		exit(-1);
	}
	return L(i,j);
}
float eval_verify_L_T(long N, int i, int j){
	if ( _flag_L_T(i,j) == 'N' ) {
		_flag_L_T(i,j) = 'I';
	//Body for L_T
		L_T(i,j) = eval_verify_L(N,j,i);
		_flag_L_T(i,j) = 'F';
	} else if ( _flag_L_T(i,j) == 'I' ) {
		printf("There is a self dependence on L_T at (%d,%d) \n",i,j);
		exit(-1);
	}
	return L_T(i,j);
}
float eval_verify_x(long N){
	if ( _flag_x == 'N' ) {
		_flag_x = 'I';
	//Body for x
		x = 4;
		_flag_x = 'F';
	} else if ( _flag_x == 'I' ) {
		printf("There is a self dependence on x at () \n");
		exit(-1);
	}
	return x;
}

//Memory Macros
#undef A
#undef L
#undef L_T
#undef _flag_L
#undef _flag_L_T


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
