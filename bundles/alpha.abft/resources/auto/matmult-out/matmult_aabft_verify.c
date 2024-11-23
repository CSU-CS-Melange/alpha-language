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
static float* C_r_0;
static float* C_r_1;
static float* C_c_0;
static float* C_c_1;
static float** C;
static float* Inv_C_r;
static float* Inv_C_c;
static char** _flag_C;
static char* _flag_Inv_C_r;
static char* _flag_Inv_C_c;
static char* _flag_C_r_0;
static char* _flag_C_r_1;
static char* _flag_C_c_0;
static char* _flag_C_c_1;


//Local Function Declarations
float reduce_matmult_aabft_verify_C_1(long, int, int);
float eval_verify_C(long, int, int);
float reduce_matmult_aabft_verify_C_r_0_1(long, int);
float eval_verify_C_r_0(long, int);
float reduce_matmult_aabft_verify_C_r_1_1(long, int);
float eval_verify_C_r_1(long, int);
float reduce_matmult_aabft_verify_C_c_0_1(long, int);
float eval_verify_C_c_0(long, int);
float reduce_matmult_aabft_verify_C_c_1_1(long, int);
float eval_verify_C_c_1(long, int);
float eval_verify_Inv_C_r(long, int);
float eval_verify_Inv_C_c(long, int);

//Memory Macros
#define A(i,j) A[i][j]
#define B(i,j) B[i][j]
#define C_r_0(i) C_r_0[i]
#define C_r_1(i) C_r_1[i]
#define C_c_0(j) C_c_0[j]
#define C_c_1(j) C_c_1[j]
#define C(i,j) C[i][j]
#define Inv_C_r(i) Inv_C_r[i]
#define Inv_C_c(j) Inv_C_c[j]
#define _flag_C(i,j) _flag_C[i][j]
#define _flag_Inv_C_r(i) _flag_Inv_C_r[i]
#define _flag_Inv_C_c(j) _flag_Inv_C_c[j]
#define _flag_C_r_0(i) _flag_C_r_0[i]
#define _flag_C_r_1(i) _flag_C_r_1[i]
#define _flag_C_c_0(j) _flag_C_c_0[j]
#define _flag_C_c_1(j) _flag_C_c_1[j]

void matmult_aabft_verify(long N, float** _local_A, float** _local_B, float** _local_C, float* _local_Inv_C_r, float* _local_Inv_C_c){
	///Parameter checking
	if (!((N >= 1))) {
		printf("The value of parameters are not valid.\n");
		exit(-1);
	}
	//Copy to global
	A = _local_A;
	B = _local_B;
	C = _local_C;
	Inv_C_r = _local_Inv_C_r;
	Inv_C_c = _local_Inv_C_c;
	
	//Memory Allocation
	int mz1, mz2;
	
	C_r_0 = (float*)malloc(sizeof(float)*(N));
	mallocCheck(C_r_0, (N), float);
	
	C_r_1 = (float*)malloc(sizeof(float)*(N));
	mallocCheck(C_r_1, (N), float);
	
	C_c_0 = (float*)malloc(sizeof(float)*(N));
	mallocCheck(C_c_0, (N), float);
	
	C_c_1 = (float*)malloc(sizeof(float)*(N));
	mallocCheck(C_c_1, (N), float);
	
	char* _lin__flag_C = (char*)malloc(sizeof(char)*((N) * (N)));
	mallocCheck(_lin__flag_C, ((N) * (N)), char);
	_flag_C = (char**)malloc(sizeof(char*)*(N));
	mallocCheck(_flag_C, (N), char*);
	for (mz1=0;mz1 < N; mz1++) {
		_flag_C[mz1] = &_lin__flag_C[(mz1*(N))];
	}
	memset(_lin__flag_C, 'N', ((N) * (N)));
	
	_flag_Inv_C_r = (char*)malloc(sizeof(char)*(N));
	mallocCheck(_flag_Inv_C_r, (N), char);
	memset(_flag_Inv_C_r, 'N', (N));
	
	_flag_Inv_C_c = (char*)malloc(sizeof(char)*(N));
	mallocCheck(_flag_Inv_C_c, (N), char);
	memset(_flag_Inv_C_c, 'N', (N));
	
	_flag_C_r_0 = (char*)malloc(sizeof(char)*(N));
	mallocCheck(_flag_C_r_0, (N), char);
	memset(_flag_C_r_0, 'N', (N));
	
	_flag_C_r_1 = (char*)malloc(sizeof(char)*(N));
	mallocCheck(_flag_C_r_1, (N), char);
	memset(_flag_C_r_1, 'N', (N));
	
	_flag_C_c_0 = (char*)malloc(sizeof(char)*(N));
	mallocCheck(_flag_C_c_0, (N), char);
	memset(_flag_C_c_0, 'N', (N));
	
	_flag_C_c_1 = (char*)malloc(sizeof(char)*(N));
	mallocCheck(_flag_C_c_1, (N), char);
	memset(_flag_C_c_1, 'N', (N));
	#define S0(i,j) eval_verify_C(N,i,j)
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
	#define S0(i) eval_verify_Inv_C_r(N,i)
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
	#define S0(j) eval_verify_Inv_C_c(N,j)
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
	free(C_r_0);
	free(C_r_1);
	free(C_c_0);
	free(C_c_1);
	free(_lin__flag_C);
	free(_flag_C);
	
	free(_flag_Inv_C_r);
	free(_flag_Inv_C_c);
	free(_flag_C_r_0);
	free(_flag_C_r_1);
	free(_flag_C_c_0);
	free(_flag_C_c_1);
}
float reduce_matmult_aabft_verify_C_1(long N, int ip, int jp){
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
		C(i,j) = reduce_matmult_aabft_verify_C_1(N,i,j);
		_flag_C(i,j) = 'F';
	} else if ( _flag_C(i,j) == 'I' ) {
		printf("There is a self dependence on C at (%d,%d) \n",i,j);
		exit(-1);
	}
	return C(i,j);
}
float reduce_matmult_aabft_verify_C_r_0_1(long N, int ip){
	float reduceVar = 0;
	#define S0(i,j) reduceVar = (reduceVar)+(eval_verify_C(N,i,j))
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
float eval_verify_C_r_0(long N, int i){
	if ( _flag_C_r_0(i) == 'N' ) {
		_flag_C_r_0(i) = 'I';
	//Body for C_r_0
		C_r_0(i) = reduce_matmult_aabft_verify_C_r_0_1(N,i);
		_flag_C_r_0(i) = 'F';
	} else if ( _flag_C_r_0(i) == 'I' ) {
		printf("There is a self dependence on C_r_0 at (%d) \n",i);
		exit(-1);
	}
	return C_r_0(i);
}
float reduce_matmult_aabft_verify_C_r_1_1(long N, int ip){
	float reduceVar = 0;
	#define S0(i,j,k) reduceVar = (reduceVar)+((A(i,k))*(B(k,j)))
	{
		//Domain
		//{i,j,k|ip>=0 && N>=ip+1 && N>=1 && i>=0 && N>=i+1 && k>=0 && N>=k+1 && j>=0 && N>=j+1 && ip==i}
		int c2,c3;
		for(c2=0;c2 <= N-1;c2+=1)
		 {
		 	for(c3=0;c3 <= N-1;c3+=1)
		 	 {
		 	 	S0((ip),(c2),(c3));
		 	 }
		 }
	}
	#undef S0
	return reduceVar;
}
float eval_verify_C_r_1(long N, int i){
	if ( _flag_C_r_1(i) == 'N' ) {
		_flag_C_r_1(i) = 'I';
	//Body for C_r_1
		C_r_1(i) = reduce_matmult_aabft_verify_C_r_1_1(N,i);
		_flag_C_r_1(i) = 'F';
	} else if ( _flag_C_r_1(i) == 'I' ) {
		printf("There is a self dependence on C_r_1 at (%d) \n",i);
		exit(-1);
	}
	return C_r_1(i);
}
float reduce_matmult_aabft_verify_C_c_0_1(long N, int ip){
	float reduceVar = 0;
	#define S0(i,j) reduceVar = (reduceVar)+(eval_verify_C(N,i,j))
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
float eval_verify_C_c_0(long N, int j){
	if ( _flag_C_c_0(j) == 'N' ) {
		_flag_C_c_0(j) = 'I';
	//Body for C_c_0
		C_c_0(j) = reduce_matmult_aabft_verify_C_c_0_1(N,j);
		_flag_C_c_0(j) = 'F';
	} else if ( _flag_C_c_0(j) == 'I' ) {
		printf("There is a self dependence on C_c_0 at (%d) \n",j);
		exit(-1);
	}
	return C_c_0(j);
}
float reduce_matmult_aabft_verify_C_c_1_1(long N, int ip){
	float reduceVar = 0;
	#define S0(i,j,k) reduceVar = (reduceVar)+((A(i,k))*(B(k,j)))
	{
		//Domain
		//{i,j,k|N>=ip+1 && ip>=0 && N>=1 && i>=0 && N>=i+1 && k>=0 && N>=k+1 && j>=0 && N>=j+1 && ip==j}
		int c1,c3;
		for(c1=0;c1 <= N-1;c1+=1)
		 {
		 	for(c3=0;c3 <= N-1;c3+=1)
		 	 {
		 	 	S0((c1),(ip),(c3));
		 	 }
		 }
	}
	#undef S0
	return reduceVar;
}
float eval_verify_C_c_1(long N, int j){
	if ( _flag_C_c_1(j) == 'N' ) {
		_flag_C_c_1(j) = 'I';
	//Body for C_c_1
		C_c_1(j) = reduce_matmult_aabft_verify_C_c_1_1(N,j);
		_flag_C_c_1(j) = 'F';
	} else if ( _flag_C_c_1(j) == 'I' ) {
		printf("There is a self dependence on C_c_1 at (%d) \n",j);
		exit(-1);
	}
	return C_c_1(j);
}
float eval_verify_Inv_C_r(long N, int i){
	if ( _flag_Inv_C_r(i) == 'N' ) {
		_flag_Inv_C_r(i) = 'I';
	//Body for Inv_C_r
		Inv_C_r(i) = ((eval_verify_C_r_0(N,i))-(eval_verify_C_r_1(N,i)))/(eval_verify_C_r_0(N,i));
		_flag_Inv_C_r(i) = 'F';
	} else if ( _flag_Inv_C_r(i) == 'I' ) {
		printf("There is a self dependence on Inv_C_r at (%d) \n",i);
		exit(-1);
	}
	return Inv_C_r(i);
}
float eval_verify_Inv_C_c(long N, int j){
	if ( _flag_Inv_C_c(j) == 'N' ) {
		_flag_Inv_C_c(j) = 'I';
	//Body for Inv_C_c
		Inv_C_c(j) = ((eval_verify_C_c_0(N,j))-(eval_verify_C_c_1(N,j)))/(eval_verify_C_c_0(N,j));
		_flag_Inv_C_c(j) = 'F';
	} else if ( _flag_Inv_C_c(j) == 'I' ) {
		printf("There is a self dependence on Inv_C_c at (%d) \n",j);
		exit(-1);
	}
	return Inv_C_c(j);
}

//Memory Macros
#undef A
#undef B
#undef C_r_0
#undef C_r_1
#undef C_c_0
#undef C_c_1
#undef C
#undef Inv_C_r
#undef Inv_C_c
#undef _flag_C
#undef _flag_Inv_C_r
#undef _flag_Inv_C_c
#undef _flag_C_r_0
#undef _flag_C_r_1
#undef _flag_C_c_0
#undef _flag_C_c_1


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
