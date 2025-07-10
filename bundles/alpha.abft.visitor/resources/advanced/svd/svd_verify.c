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
static float** M;
static float** M_T;
static float** U;
static float** S;
static float** V_T;
static float** MM_T;
static char** _flag_U;
static char** _flag_S;
static char** _flag_V_T;
static char** _flag_MM_T;
static char** _flag_M_T;


//Local Function Declarations
float eval_verify_U(long, int, int);
float eval_verify_S(long, int, int);
float eval_verify_V_T(long, int, int);
float eval_verify_M_T(long, int, int);
float reduce_svd_verify_MM_T_1(long, int, int);
float eval_verify_MM_T(long, int, int);

//Memory Macros
#define M(i,j) M[i][j]
#define M_T(i,j) M_T[i][j]
#define U(i,j) U[i][j]
#define S(i,j) S[i][j]
#define V_T(i,j) V_T[i][j]
#define MM_T(i,j) MM_T[i][j]
#define _flag_U(i,j) _flag_U[i][j]
#define _flag_S(i,j) _flag_S[i][j]
#define _flag_V_T(i,j) _flag_V_T[i][j]
#define _flag_MM_T(i,j) _flag_MM_T[i][j]
#define _flag_M_T(i,j) _flag_M_T[i][j]

void svd_verify(long N, float** _local_M, float** _local_U, float** _local_S, float** _local_V_T, float** _local_MM_T){
	///Parameter checking
	if (!((N >= 1))) {
		printf("The value of parameters are not valid.\n");
		exit(-1);
	}
	//Copy to global
	M = _local_M;
	U = _local_U;
	S = _local_S;
	V_T = _local_V_T;
	MM_T = _local_MM_T;
	
	//Memory Allocation
	int mz1, mz2;
	
	float* _lin_M_T = (float*)malloc(sizeof(float)*((N) * (N)));
	mallocCheck(_lin_M_T, ((N) * (N)), float);
	M_T = (float**)malloc(sizeof(float*)*(N));
	mallocCheck(M_T, (N), float*);
	for (mz1=0;mz1 < N; mz1++) {
		M_T[mz1] = &_lin_M_T[(mz1*(N))];
	}
	
	char* _lin__flag_U = (char*)malloc(sizeof(char)*((N) * (N)));
	mallocCheck(_lin__flag_U, ((N) * (N)), char);
	_flag_U = (char**)malloc(sizeof(char*)*(N));
	mallocCheck(_flag_U, (N), char*);
	for (mz1=0;mz1 < N; mz1++) {
		_flag_U[mz1] = &_lin__flag_U[(mz1*(N))];
	}
	memset(_lin__flag_U, 'N', ((N) * (N)));
	
	char* _lin__flag_S = (char*)malloc(sizeof(char)*((N) * (N)));
	mallocCheck(_lin__flag_S, ((N) * (N)), char);
	_flag_S = (char**)malloc(sizeof(char*)*(N));
	mallocCheck(_flag_S, (N), char*);
	for (mz1=0;mz1 < N; mz1++) {
		_flag_S[mz1] = &_lin__flag_S[(mz1*(N))];
	}
	memset(_lin__flag_S, 'N', ((N) * (N)));
	
	char* _lin__flag_V_T = (char*)malloc(sizeof(char)*((N) * (N)));
	mallocCheck(_lin__flag_V_T, ((N) * (N)), char);
	_flag_V_T = (char**)malloc(sizeof(char*)*(N));
	mallocCheck(_flag_V_T, (N), char*);
	for (mz1=0;mz1 < N; mz1++) {
		_flag_V_T[mz1] = &_lin__flag_V_T[(mz1*(N))];
	}
	memset(_lin__flag_V_T, 'N', ((N) * (N)));
	
	char* _lin__flag_MM_T = (char*)malloc(sizeof(char)*((N) * (N)));
	mallocCheck(_lin__flag_MM_T, ((N) * (N)), char);
	_flag_MM_T = (char**)malloc(sizeof(char*)*(N));
	mallocCheck(_flag_MM_T, (N), char*);
	for (mz1=0;mz1 < N; mz1++) {
		_flag_MM_T[mz1] = &_lin__flag_MM_T[(mz1*(N))];
	}
	memset(_lin__flag_MM_T, 'N', ((N) * (N)));
	
	char* _lin__flag_M_T = (char*)malloc(sizeof(char)*((N) * (N)));
	mallocCheck(_lin__flag_M_T, ((N) * (N)), char);
	_flag_M_T = (char**)malloc(sizeof(char*)*(N));
	mallocCheck(_flag_M_T, (N), char*);
	for (mz1=0;mz1 < N; mz1++) {
		_flag_M_T[mz1] = &_lin__flag_M_T[(mz1*(N))];
	}
	memset(_lin__flag_M_T, 'N', ((N) * (N)));
	#define S0(i,j) eval_verify_U(N,i,j)
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
	#define S0(i,j) eval_verify_S(N,i,j)
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
	#define S0(i,j) eval_verify_V_T(N,i,j)
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
	#define S0(i,j) eval_verify_MM_T(N,i,j)
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
	free(_lin_M_T);
	free(M_T);
	
	free(_lin__flag_U);
	free(_flag_U);
	
	free(_lin__flag_S);
	free(_flag_S);
	
	free(_lin__flag_V_T);
	free(_flag_V_T);
	
	free(_lin__flag_MM_T);
	free(_flag_MM_T);
	
	free(_lin__flag_M_T);
	free(_flag_M_T);
}
float eval_verify_U(long N, int i, int j){
	if ( _flag_U(i,j) == 'N' ) {
		_flag_U(i,j) = 'I';
	//Body for U
		U(i,j) = M(i,j);
		_flag_U(i,j) = 'F';
	} else if ( _flag_U(i,j) == 'I' ) {
		printf("There is a self dependence on U at (%d,%d) \n",i,j);
		exit(-1);
	}
	return U(i,j);
}
float eval_verify_S(long N, int i, int j){
	if ( _flag_S(i,j) == 'N' ) {
		_flag_S(i,j) = 'I';
	//Body for S
		S(i,j) = M(i,j);
		_flag_S(i,j) = 'F';
	} else if ( _flag_S(i,j) == 'I' ) {
		printf("There is a self dependence on S at (%d,%d) \n",i,j);
		exit(-1);
	}
	return S(i,j);
}
float eval_verify_V_T(long N, int i, int j){
	if ( _flag_V_T(i,j) == 'N' ) {
		_flag_V_T(i,j) = 'I';
	//Body for V_T
		V_T(i,j) = M(i,j);
		_flag_V_T(i,j) = 'F';
	} else if ( _flag_V_T(i,j) == 'I' ) {
		printf("There is a self dependence on V_T at (%d,%d) \n",i,j);
		exit(-1);
	}
	return V_T(i,j);
}
float eval_verify_M_T(long N, int i, int j){
	if ( _flag_M_T(i,j) == 'N' ) {
		_flag_M_T(i,j) = 'I';
	//Body for M_T
		M_T(i,j) = M(j,i);
		_flag_M_T(i,j) = 'F';
	} else if ( _flag_M_T(i,j) == 'I' ) {
		printf("There is a self dependence on M_T at (%d,%d) \n",i,j);
		exit(-1);
	}
	return M_T(i,j);
}
float reduce_svd_verify_MM_T_1(long N, int ip, int jp){
	float reduceVar = 0;
	#define S0(i,j,k) reduceVar = (reduceVar)+((M(i,k))*(eval_verify_M_T(N,k,j)))
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
float eval_verify_MM_T(long N, int i, int j){
	if ( _flag_MM_T(i,j) == 'N' ) {
		_flag_MM_T(i,j) = 'I';
	//Body for MM_T
		MM_T(i,j) = reduce_svd_verify_MM_T_1(N,i,j);
		_flag_MM_T(i,j) = 'F';
	} else if ( _flag_MM_T(i,j) == 'I' ) {
		printf("There is a self dependence on MM_T at (%d,%d) \n",i,j);
		exit(-1);
	}
	return MM_T(i,j);
}

//Memory Macros
#undef M
#undef M_T
#undef U
#undef S
#undef V_T
#undef MM_T
#undef _flag_U
#undef _flag_S
#undef _flag_V_T
#undef _flag_MM_T
#undef _flag_M_T


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
