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
static float k;
static float** A;
static float* check_B_i_0;
static float* check_B_i_1;
static float* check_B_j_0;
static float* check_B_j_1;
static float** B;
static float* check_B_i_inv;
static float* check_B_j_inv;
static char** _flag_B;
static char* _flag_check_B_i_inv;
static char* _flag_check_B_j_inv;
static char* _flag_check_B_i_0;
static char* _flag_check_B_i_1;
static char* _flag_check_B_j_0;
static char* _flag_check_B_j_1;


//Local Function Declarations
float eval_verify_B(long, int, int);
float reduce_scalmat_aabft_verify_check_B_i_0_1(long, int);
float eval_verify_check_B_i_0(long, int);
float reduce_scalmat_aabft_verify_check_B_i_1_1(long, int);
float eval_verify_check_B_i_1(long, int);
float eval_verify_check_B_i_inv(long, int);
float reduce_scalmat_aabft_verify_check_B_j_0_1(long, int);
float eval_verify_check_B_j_0(long, int);
float reduce_scalmat_aabft_verify_check_B_j_1_1(long, int);
float eval_verify_check_B_j_1(long, int);
float eval_verify_check_B_j_inv(long, int);

//Memory Macros
#define A(i,j) A[i][j]
#define check_B_i_0(i) check_B_i_0[i]
#define check_B_i_1(i) check_B_i_1[i]
#define check_B_j_0(j) check_B_j_0[j]
#define check_B_j_1(j) check_B_j_1[j]
#define B(i,j) B[i][j]
#define check_B_i_inv(i) check_B_i_inv[i]
#define check_B_j_inv(j) check_B_j_inv[j]
#define _flag_B(i,j) _flag_B[i][j]
#define _flag_check_B_i_inv(i) _flag_check_B_i_inv[i]
#define _flag_check_B_j_inv(j) _flag_check_B_j_inv[j]
#define _flag_check_B_i_0(i) _flag_check_B_i_0[i]
#define _flag_check_B_i_1(i) _flag_check_B_i_1[i]
#define _flag_check_B_j_0(j) _flag_check_B_j_0[j]
#define _flag_check_B_j_1(j) _flag_check_B_j_1[j]

void scalmat_aabft_verify(long N, float* _local_k, float** _local_A, float** _local_B, float* _local_check_B_i_inv, float* _local_check_B_j_inv){
	///Parameter checking
	if (!((N >= 1))) {
		printf("The value of parameters are not valid.\n");
		exit(-1);
	}
	//Copy to global
	
	k = *_local_k;
	A = _local_A;
	B = _local_B;
	check_B_i_inv = _local_check_B_i_inv;
	check_B_j_inv = _local_check_B_j_inv;
	
	//Memory Allocation
	int mz1, mz2;
	
	check_B_i_0 = (float*)malloc(sizeof(float)*(N));
	mallocCheck(check_B_i_0, (N), float);
	
	check_B_i_1 = (float*)malloc(sizeof(float)*(N));
	mallocCheck(check_B_i_1, (N), float);
	
	check_B_j_0 = (float*)malloc(sizeof(float)*(N));
	mallocCheck(check_B_j_0, (N), float);
	
	check_B_j_1 = (float*)malloc(sizeof(float)*(N));
	mallocCheck(check_B_j_1, (N), float);
	
	char* _lin__flag_B = (char*)malloc(sizeof(char)*((N) * (N)));
	mallocCheck(_lin__flag_B, ((N) * (N)), char);
	_flag_B = (char**)malloc(sizeof(char*)*(N));
	mallocCheck(_flag_B, (N), char*);
	for (mz1=0;mz1 < N; mz1++) {
		_flag_B[mz1] = &_lin__flag_B[(mz1*(N))];
	}
	memset(_lin__flag_B, 'N', ((N) * (N)));
	
	_flag_check_B_i_inv = (char*)malloc(sizeof(char)*(N));
	mallocCheck(_flag_check_B_i_inv, (N), char);
	memset(_flag_check_B_i_inv, 'N', (N));
	
	_flag_check_B_j_inv = (char*)malloc(sizeof(char)*(N));
	mallocCheck(_flag_check_B_j_inv, (N), char);
	memset(_flag_check_B_j_inv, 'N', (N));
	
	_flag_check_B_i_0 = (char*)malloc(sizeof(char)*(N));
	mallocCheck(_flag_check_B_i_0, (N), char);
	memset(_flag_check_B_i_0, 'N', (N));
	
	_flag_check_B_i_1 = (char*)malloc(sizeof(char)*(N));
	mallocCheck(_flag_check_B_i_1, (N), char);
	memset(_flag_check_B_i_1, 'N', (N));
	
	_flag_check_B_j_0 = (char*)malloc(sizeof(char)*(N));
	mallocCheck(_flag_check_B_j_0, (N), char);
	memset(_flag_check_B_j_0, 'N', (N));
	
	_flag_check_B_j_1 = (char*)malloc(sizeof(char)*(N));
	mallocCheck(_flag_check_B_j_1, (N), char);
	memset(_flag_check_B_j_1, 'N', (N));
	#define S0(i,j) eval_verify_B(N,i,j)
	{
		//Domain
		//{i,j|i>=0 && N>=i+1 && j>=0 && N>=j+1 && N>=1}
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
	#define S0(i) eval_verify_check_B_i_inv(N,i)
	{
		//Domain
		//{i|i>=0 && N>=i+1 && N>=1}
		int c1;
		for(c1=0;c1 <= N-1;c1+=1)
		 {
		 	S0((c1));
		 }
	}
	#undef S0
	#define S0(j) eval_verify_check_B_j_inv(N,j)
	{
		//Domain
		//{j|j>=0 && N>=j+1 && N>=1}
		int c1;
		for(c1=0;c1 <= N-1;c1+=1)
		 {
		 	S0((c1));
		 }
	}
	#undef S0
	
	//Memory Free
	free(check_B_i_0);
	free(check_B_i_1);
	free(check_B_j_0);
	free(check_B_j_1);
	free(_lin__flag_B);
	free(_flag_B);
	
	free(_flag_check_B_i_inv);
	free(_flag_check_B_j_inv);
	free(_flag_check_B_i_0);
	free(_flag_check_B_i_1);
	free(_flag_check_B_j_0);
	free(_flag_check_B_j_1);
}
float eval_verify_B(long N, int i, int j){
	if ( _flag_B(i,j) == 'N' ) {
		_flag_B(i,j) = 'I';
	//Body for B
		B(i,j) = (k)*(A(i,j));
		_flag_B(i,j) = 'F';
	} else if ( _flag_B(i,j) == 'I' ) {
		printf("There is a self dependence on B at (%d,%d) \n",i,j);
		exit(-1);
	}
	return B(i,j);
}
float reduce_scalmat_aabft_verify_check_B_i_0_1(long N, int ip){
	float reduceVar = 0;
	#define S0(i,j) reduceVar = (reduceVar)+(eval_verify_B(N,i,j))
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
float eval_verify_check_B_i_0(long N, int i){
	if ( _flag_check_B_i_0(i) == 'N' ) {
		_flag_check_B_i_0(i) = 'I';
	//Body for check_B_i_0
		check_B_i_0(i) = reduce_scalmat_aabft_verify_check_B_i_0_1(N,i);
		_flag_check_B_i_0(i) = 'F';
	} else if ( _flag_check_B_i_0(i) == 'I' ) {
		printf("There is a self dependence on check_B_i_0 at (%d) \n",i);
		exit(-1);
	}
	return check_B_i_0(i);
}
float reduce_scalmat_aabft_verify_check_B_i_1_1(long N, int ip){
	float reduceVar = 0;
	#define S0(i,j) reduceVar = (reduceVar)+((k)*(A(i,j)))
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
float eval_verify_check_B_i_1(long N, int i){
	if ( _flag_check_B_i_1(i) == 'N' ) {
		_flag_check_B_i_1(i) = 'I';
	//Body for check_B_i_1
		check_B_i_1(i) = reduce_scalmat_aabft_verify_check_B_i_1_1(N,i);
		_flag_check_B_i_1(i) = 'F';
	} else if ( _flag_check_B_i_1(i) == 'I' ) {
		printf("There is a self dependence on check_B_i_1 at (%d) \n",i);
		exit(-1);
	}
	return check_B_i_1(i);
}
float eval_verify_check_B_i_inv(long N, int i){
	if ( _flag_check_B_i_inv(i) == 'N' ) {
		_flag_check_B_i_inv(i) = 'I';
	//Body for check_B_i_inv
		check_B_i_inv(i) = ((eval_verify_check_B_i_0(N,i))-(eval_verify_check_B_i_1(N,i)))/(eval_verify_check_B_i_0(N,i));
		_flag_check_B_i_inv(i) = 'F';
	} else if ( _flag_check_B_i_inv(i) == 'I' ) {
		printf("There is a self dependence on check_B_i_inv at (%d) \n",i);
		exit(-1);
	}
	return check_B_i_inv(i);
}
float reduce_scalmat_aabft_verify_check_B_j_0_1(long N, int ip){
	float reduceVar = 0;
	#define S0(i,j) reduceVar = (reduceVar)+(eval_verify_B(N,i,j))
	{
		//Domain
		//{i,j|ip>=0 && N>=ip+1 && N>=1 && i>=0 && N>=i+1 && j>=0 && N>=j+1 && ip==j}
		int c1;
		for(c1=0;c1 <= N-1;c1+=1)
		 {
		 	S0((c1),(ip));
		 }
	}
	#undef S0
	return reduceVar;
}
float eval_verify_check_B_j_0(long N, int j){
	if ( _flag_check_B_j_0(j) == 'N' ) {
		_flag_check_B_j_0(j) = 'I';
	//Body for check_B_j_0
		check_B_j_0(j) = reduce_scalmat_aabft_verify_check_B_j_0_1(N,j);
		_flag_check_B_j_0(j) = 'F';
	} else if ( _flag_check_B_j_0(j) == 'I' ) {
		printf("There is a self dependence on check_B_j_0 at (%d) \n",j);
		exit(-1);
	}
	return check_B_j_0(j);
}
float reduce_scalmat_aabft_verify_check_B_j_1_1(long N, int ip){
	float reduceVar = 0;
	#define S0(i,j) reduceVar = (reduceVar)+((k)*(A(i,j)))
	{
		//Domain
		//{i,j|ip>=0 && N>=ip+1 && N>=1 && i>=0 && N>=i+1 && j>=0 && N>=j+1 && ip==j}
		int c1;
		for(c1=0;c1 <= N-1;c1+=1)
		 {
		 	S0((c1),(ip));
		 }
	}
	#undef S0
	return reduceVar;
}
float eval_verify_check_B_j_1(long N, int j){
	if ( _flag_check_B_j_1(j) == 'N' ) {
		_flag_check_B_j_1(j) = 'I';
	//Body for check_B_j_1
		check_B_j_1(j) = reduce_scalmat_aabft_verify_check_B_j_1_1(N,j);
		_flag_check_B_j_1(j) = 'F';
	} else if ( _flag_check_B_j_1(j) == 'I' ) {
		printf("There is a self dependence on check_B_j_1 at (%d) \n",j);
		exit(-1);
	}
	return check_B_j_1(j);
}
float eval_verify_check_B_j_inv(long N, int j){
	if ( _flag_check_B_j_inv(j) == 'N' ) {
		_flag_check_B_j_inv(j) = 'I';
	//Body for check_B_j_inv
		check_B_j_inv(j) = ((eval_verify_check_B_j_0(N,j))-(eval_verify_check_B_j_1(N,j)))/(eval_verify_check_B_j_0(N,j));
		_flag_check_B_j_inv(j) = 'F';
	} else if ( _flag_check_B_j_inv(j) == 'I' ) {
		printf("There is a self dependence on check_B_j_inv at (%d) \n",j);
		exit(-1);
	}
	return check_B_j_inv(j);
}

//Memory Macros
#undef A
#undef check_B_i_0
#undef check_B_i_1
#undef check_B_j_0
#undef check_B_j_1
#undef B
#undef check_B_i_inv
#undef check_B_j_inv
#undef _flag_B
#undef _flag_check_B_i_inv
#undef _flag_check_B_j_inv
#undef _flag_check_B_i_0
#undef _flag_check_B_i_1
#undef _flag_check_B_j_0
#undef _flag_check_B_j_1


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
