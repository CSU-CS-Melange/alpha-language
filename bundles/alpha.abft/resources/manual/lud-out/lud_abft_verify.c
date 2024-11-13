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
static float* L_c;
static float* U_r;
static float* A_c;
static float* A_r;
static float** A_f;
static float** L;
static float** U;
static char** _flag_L;
static char** _flag_U;
static char* _flag_L_c;
static char* _flag_U_r;
static char* _flag_A_c;
static char* _flag_A_r;
static char** _flag_A_f;


//Local Function Declarations
float reduce_lud_abft_verify_U_1(long, int, int);
float eval_verify_U(long, int, int);
float reduce_lud_abft_verify_L_1(long, int, int);
float eval_verify_L(long, int, int);
float reduce_lud_abft_verify_L_c_1(long, int);
float eval_verify_L_c(long, int);
float reduce_lud_abft_verify_U_r_1(long, int);
float eval_verify_U_r(long, int);
float reduce_lud_abft_verify_A_c_1(long, int);
float eval_verify_A_c(long, int);
float reduce_lud_abft_verify_A_r_1(long, int);
float eval_verify_A_r(long, int);
float reduce_lud_abft_verify_A_f_1(long, int, int);
float reduce_lud_abft_verify_A_f_2(long, int, int);
float eval_verify_A_f(long, int, int);

//Memory Macros
#define A(i,j) A[i][j]
#define L_c(j) L_c[j]
#define U_r(i) U_r[i]
#define A_c(j) A_c[j]
#define A_r(i) A_r[i]
#define A_f(i,j) A_f[i][j]
#define L(i,j) L[i][j]
#define U(i,j) U[i][j]
#define _flag_L(i,j) _flag_L[i][j]
#define _flag_U(i,j) _flag_U[i][j]
#define _flag_L_c(j) _flag_L_c[j]
#define _flag_U_r(i) _flag_U_r[i]
#define _flag_A_c(j) _flag_A_c[j]
#define _flag_A_r(i) _flag_A_r[i]
#define _flag_A_f(i,j) _flag_A_f[i][j]

void lud_abft_verify(long N, float** _local_A, float** _local_L, float** _local_U){
	///Parameter checking
	if (!((N >= 1))) {
		printf("The value of parameters are not valid.\n");
		exit(-1);
	}
	//Copy to global
	A = _local_A;
	L = _local_L;
	U = _local_U;
	
	//Memory Allocation
	int mz1, mz2;
	
	L_c = (float*)malloc(sizeof(float)*(N));
	mallocCheck(L_c, (N), float);
	
	U_r = (float*)malloc(sizeof(float)*(N));
	mallocCheck(U_r, (N), float);
	
	A_c = (float*)malloc(sizeof(float)*(N));
	mallocCheck(A_c, (N), float);
	
	A_r = (float*)malloc(sizeof(float)*(N));
	mallocCheck(A_r, (N), float);
	
	float* _lin_A_f = (float*)malloc(sizeof(float)*((N+1) * (N+1)));
	mallocCheck(_lin_A_f, ((N+1) * (N+1)), float);
	A_f = (float**)malloc(sizeof(float*)*(N+1));
	mallocCheck(A_f, (N+1), float*);
	for (mz1=0;mz1 < N+1; mz1++) {
		A_f[mz1] = &_lin_A_f[(mz1*(N+1))];
	}
	
	char* _lin__flag_L = (char*)malloc(sizeof(char)*((N) * (N)));
	mallocCheck(_lin__flag_L, ((N) * (N)), char);
	_flag_L = (char**)malloc(sizeof(char*)*(N));
	mallocCheck(_flag_L, (N), char*);
	for (mz1=0;mz1 < N; mz1++) {
		_flag_L[mz1] = &_lin__flag_L[(mz1*(N))];
	}
	memset(_lin__flag_L, 'N', ((N) * (N)));
	
	char* _lin__flag_U = (char*)malloc(sizeof(char)*((N) * (N)));
	mallocCheck(_lin__flag_U, ((N) * (N)), char);
	_flag_U = (char**)malloc(sizeof(char*)*(N));
	mallocCheck(_flag_U, (N), char*);
	for (mz1=0;mz1 < N; mz1++) {
		_flag_U[mz1] = &_lin__flag_U[(mz1*(N))];
	}
	memset(_lin__flag_U, 'N', ((N) * (N)));
	
	_flag_L_c = (char*)malloc(sizeof(char)*(N));
	mallocCheck(_flag_L_c, (N), char);
	memset(_flag_L_c, 'N', (N));
	
	_flag_U_r = (char*)malloc(sizeof(char)*(N));
	mallocCheck(_flag_U_r, (N), char);
	memset(_flag_U_r, 'N', (N));
	
	_flag_A_c = (char*)malloc(sizeof(char)*(N));
	mallocCheck(_flag_A_c, (N), char);
	memset(_flag_A_c, 'N', (N));
	
	_flag_A_r = (char*)malloc(sizeof(char)*(N));
	mallocCheck(_flag_A_r, (N), char);
	memset(_flag_A_r, 'N', (N));
	
	char* _lin__flag_A_f = (char*)malloc(sizeof(char)*((N+1) * (N+1)));
	mallocCheck(_lin__flag_A_f, ((N+1) * (N+1)), char);
	_flag_A_f = (char**)malloc(sizeof(char*)*(N+1));
	mallocCheck(_flag_A_f, (N+1), char*);
	for (mz1=0;mz1 < N+1; mz1++) {
		_flag_A_f[mz1] = &_lin__flag_A_f[(mz1*(N+1))];
	}
	memset(_lin__flag_A_f, 'N', ((N+1) * (N+1)));
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
	#define S0(i,j) eval_verify_U(N,i,j)
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
	
	//Memory Free
	free(L_c);
	free(U_r);
	free(A_c);
	free(A_r);
	free(_lin_A_f);
	free(A_f);
	
	free(_lin__flag_L);
	free(_flag_L);
	
	free(_lin__flag_U);
	free(_flag_U);
	
	free(_flag_L_c);
	free(_flag_U_r);
	free(_flag_A_c);
	free(_flag_A_r);
	free(_lin__flag_A_f);
	free(_flag_A_f);
}
float reduce_lud_abft_verify_U_1(long N, int ip, int jp){
	float reduceVar = 0;
	#define S0(i,j,k) reduceVar = (reduceVar)+((eval_verify_L(N,i,k))*(eval_verify_U(N,k,j)))
	{
		//Domain
		//{i,j,k|jp>=0 && N>=jp+1 && ip>=1 && N>=ip+1 && N>=1 && jp>=ip && i>=1 && k>=0 && i>=k+1 && N>=i+1 && j>=k && N>=j+1 && j>=0 && j>=i && ip==i && jp==j}
		int c3;
		for(c3=0;c3 <= ip-1;c3+=1)
		 {
		 	S0((ip),(jp),(c3));
		 }
	}
	#undef S0
	return reduceVar;
}
float eval_verify_U(long N, int i, int j){
	if ( _flag_U(i,j) == 'N' ) {
		_flag_U(i,j) = 'I';
	//Body for U
		U(i,j) = (((i == 0))?eval_verify_A_f(N,i,j):((eval_verify_A_f(N,i,j))-(reduce_lud_abft_verify_U_1(N,i,j))));
		_flag_U(i,j) = 'F';
	} else if ( _flag_U(i,j) == 'I' ) {
		printf("There is a self dependence on U at (%d,%d) \n",i,j);
		exit(-1);
	}
	return U(i,j);
}
float reduce_lud_abft_verify_L_1(long N, int ip, int jp){
	float reduceVar = 0;
	#define S0(i,j,k) reduceVar = (reduceVar)+((eval_verify_L(N,i,k))*(eval_verify_U(N,k,j)))
	{
		//Domain
		//{i,j,k|N>=jp+1 && ip>=0 && jp>=1 && N>=ip+1 && N>=1 && ip>=jp && j>=1 && k>=0 && j>=k+1 && N>=i+1 && N>=j+1 && i>=k && i>=j && i>=0 && ip==i && jp==j}
		int c3;
		for(c3=0;c3 <= jp-1;c3+=1)
		 {
		 	S0((ip),(jp),(c3));
		 }
	}
	#undef S0
	return reduceVar;
}
float eval_verify_L(long N, int i, int j){
	if ( _flag_L(i,j) == 'N' ) {
		_flag_L(i,j) = 'I';
	//Body for L
		L(i,j) = (((j == 0))?(eval_verify_A_f(N,i,j))/(eval_verify_U(N,j,j)):(((eval_verify_A_f(N,i,j))-(reduce_lud_abft_verify_L_1(N,i,j)))/(eval_verify_U(N,j,j))));
		_flag_L(i,j) = 'F';
	} else if ( _flag_L(i,j) == 'I' ) {
		printf("There is a self dependence on L at (%d,%d) \n",i,j);
		exit(-1);
	}
	return L(i,j);
}
float reduce_lud_abft_verify_L_c_1(long N, int jp){
	float reduceVar = 0;
	#define S0(j,i) reduceVar = (reduceVar)+(eval_verify_L(N,i,j))
	{
		//Domain
		//{j,i|N>=jp+1 && jp>=0 && N>=1 && N>=i+1 && j>=0 && i>=j && N>=j+1 && jp==j}
		int c2;
		for(c2=jp;c2 <= N-1;c2+=1)
		 {
		 	S0((jp),(c2));
		 }
	}
	#undef S0
	return reduceVar;
}
float eval_verify_L_c(long N, int j){
	if ( _flag_L_c(j) == 'N' ) {
		_flag_L_c(j) = 'I';
	//Body for L_c
		L_c(j) = reduce_lud_abft_verify_L_c_1(N,j);
		_flag_L_c(j) = 'F';
	} else if ( _flag_L_c(j) == 'I' ) {
		printf("There is a self dependence on L_c at (%d) \n",j);
		exit(-1);
	}
	return L_c(j);
}
float reduce_lud_abft_verify_U_r_1(long N, int ip){
	float reduceVar = 0;
	#define S0(i,j) reduceVar = (reduceVar)+(eval_verify_U(N,i,j))
	{
		//Domain
		//{i,j|ip>=0 && N>=ip+1 && N>=1 && i>=0 && j>=i && N>=j+1 && N>=i+1 && ip==i}
		int c2;
		for(c2=ip;c2 <= N-1;c2+=1)
		 {
		 	S0((ip),(c2));
		 }
	}
	#undef S0
	return reduceVar;
}
float eval_verify_U_r(long N, int i){
	if ( _flag_U_r(i) == 'N' ) {
		_flag_U_r(i) = 'I';
	//Body for U_r
		U_r(i) = reduce_lud_abft_verify_U_r_1(N,i);
		_flag_U_r(i) = 'F';
	} else if ( _flag_U_r(i) == 'I' ) {
		printf("There is a self dependence on U_r at (%d) \n",i);
		exit(-1);
	}
	return U_r(i);
}
float reduce_lud_abft_verify_A_c_1(long N, int jp){
	float reduceVar = 0;
	#define S0(j,i) reduceVar = (reduceVar)+(A(i,j))
	{
		//Domain
		//{j,i|jp>=0 && N>=jp+1 && N>=1 && i>=0 && N>=i+1 && j>=0 && N>=j+1 && jp==j}
		int c2;
		for(c2=0;c2 <= N-1;c2+=1)
		 {
		 	S0((jp),(c2));
		 }
	}
	#undef S0
	return reduceVar;
}
float eval_verify_A_c(long N, int j){
	if ( _flag_A_c(j) == 'N' ) {
		_flag_A_c(j) = 'I';
	//Body for A_c
		A_c(j) = reduce_lud_abft_verify_A_c_1(N,j);
		_flag_A_c(j) = 'F';
	} else if ( _flag_A_c(j) == 'I' ) {
		printf("There is a self dependence on A_c at (%d) \n",j);
		exit(-1);
	}
	return A_c(j);
}
float reduce_lud_abft_verify_A_r_1(long N, int ip){
	float reduceVar = 0;
	#define S0(i,j) reduceVar = (reduceVar)+(A(i,j))
	{
		//Domain
		//{i,j|ip>=0 && N>=ip+1 && N>=1 && i>=0 && N>=i+1 && j>=0 && N>=j+1 && ip==i}
		int c2;
		for(c2=0;c2 <= N-1;c2+=1)
		 {
		 	S0((ip),(c2));
		 }
	}
	#undef S0
	return reduceVar;
}
float eval_verify_A_r(long N, int i){
	if ( _flag_A_r(i) == 'N' ) {
		_flag_A_r(i) = 'I';
	//Body for A_r
		A_r(i) = reduce_lud_abft_verify_A_r_1(N,i);
		_flag_A_r(i) = 'F';
	} else if ( _flag_A_r(i) == 'I' ) {
		printf("There is a self dependence on A_r at (%d) \n",i);
		exit(-1);
	}
	return A_r(i);
}
float reduce_lud_abft_verify_A_f_1(long N, int ip, int jp){
	float reduceVar = 0;
	#define S0(i,j,k) reduceVar = (reduceVar)+(A(k,j))
	{
		//Domain
		//{i,j,k|i==N && ip==N && jp>=0 && N>=jp+1 && N>=1 && k>=0 && N>=k+1 && j>=0 && N>=j+1 && ip==i && jp==j}
		int c3;
		for(c3=0;c3 <= N-1;c3+=1)
		 {
		 	S0((N),(jp),(c3));
		 }
	}
	#undef S0
	return reduceVar;
}
float reduce_lud_abft_verify_A_f_2(long N, int ip, int jp){
	float reduceVar = 0;
	#define S0(i,j,k) reduceVar = (reduceVar)+(A(i,k))
	{
		//Domain
		//{i,j,k|j==N && jp==N && ip>=0 && N>=ip+1 && N>=1 && i>=0 && N>=i+1 && k>=0 && N>=k+1 && ip==i && jp==j}
		int c3;
		for(c3=0;c3 <= N-1;c3+=1)
		 {
		 	S0((ip),(N),(c3));
		 }
	}
	#undef S0
	return reduceVar;
}
float eval_verify_A_f(long N, int i, int j){
	if ( _flag_A_f(i,j) == 'N' ) {
		_flag_A_f(i,j) = 'I';
	//Body for A_f
		A_f(i,j) = (((i == N && N >= j+1))?reduce_lud_abft_verify_A_f_1(N,i,j):(((j == N && N >= i+1))?reduce_lud_abft_verify_A_f_2(N,i,j):(((N >= i+1 && N >= j+1))?A(i,j):(A(-i+N,-j+N)))));
		_flag_A_f(i,j) = 'F';
	} else if ( _flag_A_f(i,j) == 'I' ) {
		printf("There is a self dependence on A_f at (%d,%d) \n",i,j);
		exit(-1);
	}
	return A_f(i,j);
}

//Memory Macros
#undef A
#undef L_c
#undef U_r
#undef A_c
#undef A_r
#undef A_f
#undef L
#undef U
#undef _flag_L
#undef _flag_U
#undef _flag_L_c
#undef _flag_U_r
#undef _flag_A_c
#undef _flag_A_r
#undef _flag_A_f


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
