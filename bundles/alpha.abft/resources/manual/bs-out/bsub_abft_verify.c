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
static float** U;
static float* b;
static float* b_1;
static float* U_c;
static float* x;
static float* Inv_b_c;
static char* _flag_x;
static char* _flag_Inv_b_c;
static char* _flag_b_1;
static char* _flag_U_c;


//Local Function Declarations
float reduce_bsub_abft_verify_x_1(long, int);
float eval_verify_x(long, int);
float reduce_bsub_abft_verify_U_c_1(long, int);
float eval_verify_U_c(long, int);
float reduce_bsub_abft_verify_b_1_1(long, int);
float eval_verify_b_1(long, int);
float eval_verify_Inv_b_c(long, int);

//Memory Macros
#define U(i,j) U[i][j]
#define b(i) b[i]
#define b_1(i) b_1[i]
#define U_c(j) U_c[j]
#define x(i) x[i]
#define Inv_b_c(i) Inv_b_c[i]
#define _flag_x(i) _flag_x[i]
#define _flag_Inv_b_c(i) _flag_Inv_b_c[i]
#define _flag_b_1(i) _flag_b_1[i]
#define _flag_U_c(j) _flag_U_c[j]

void bsub_abft_verify(long N, float** _local_U, float* _local_b, float* _local_x, float* _local_Inv_b_c){
	///Parameter checking
	if (!((N >= 1))) {
		printf("The value of parameters are not valid.\n");
		exit(-1);
	}
	//Copy to global
	U = _local_U;
	b = _local_b;
	x = _local_x;
	Inv_b_c = _local_Inv_b_c;
	
	//Memory Allocation
	int mz1;
	
	b_1 = (float*)malloc(sizeof(float)*(N));
	mallocCheck(b_1, (N), float);
	
	U_c = (float*)malloc(sizeof(float)*(N));
	mallocCheck(U_c, (N), float);
	
	_flag_x = (char*)malloc(sizeof(char)*(N));
	mallocCheck(_flag_x, (N), char);
	memset(_flag_x, 'N', (N));
	
	_flag_Inv_b_c = (char*)malloc(sizeof(char)*(N));
	mallocCheck(_flag_Inv_b_c, (N), char);
	memset(_flag_Inv_b_c, 'N', (N));
	
	_flag_b_1 = (char*)malloc(sizeof(char)*(N));
	mallocCheck(_flag_b_1, (N), char);
	memset(_flag_b_1, 'N', (N));
	
	_flag_U_c = (char*)malloc(sizeof(char)*(N));
	mallocCheck(_flag_U_c, (N), char);
	memset(_flag_U_c, 'N', (N));
	#define S0(i) eval_verify_x(N,i)
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
	#define S0(i) eval_verify_Inv_b_c(N,i)
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
	
	//Memory Free
	free(b_1);
	free(U_c);
	free(_flag_x);
	free(_flag_Inv_b_c);
	free(_flag_b_1);
	free(_flag_U_c);
}
float reduce_bsub_abft_verify_x_1(long N, int ip){
	float reduceVar = 0;
	#define S0(i,j) reduceVar = (reduceVar)+((U(i,j))*(eval_verify_x(N,j)))
	{
		//Domain
		//{i,j|N>=ip+2 && ip>=0 && N>=1 && N>=i+2 && j>=i+1 && N>=j+1 && i>=0 && j>=0 && ip==i}
		int c2;
		for(c2=ip+1;c2 <= N-1;c2+=1)
		 {
		 	S0((ip),(c2));
		 }
	}
	#undef S0
	return reduceVar;
}
float eval_verify_x(long N, int i){
	if ( _flag_x(i) == 'N' ) {
		_flag_x(i) = 'I';
	//Body for x
		x(i) = (((i == N-1))?(b(i))/(U(i,i)):(((b(i))-(reduce_bsub_abft_verify_x_1(N,i)))/(U(i,i))));
		_flag_x(i) = 'F';
	} else if ( _flag_x(i) == 'I' ) {
		printf("There is a self dependence on x at (%d) \n",i);
		exit(-1);
	}
	return x(i);
}
float reduce_bsub_abft_verify_U_c_1(long N, int jp){
	float reduceVar = 0;
	#define S0(j,i) reduceVar = (reduceVar)+(U(i,j))
	{
		//Domain
		//{j,i|N>=jp+1 && jp>=0 && N>=1 && i>=0 && j>=i && N>=j+1 && j>=0 && jp==j}
		int c2;
		for(c2=0;c2 <= jp;c2+=1)
		 {
		 	S0((jp),(c2));
		 }
	}
	#undef S0
	return reduceVar;
}
float eval_verify_U_c(long N, int j){
	if ( _flag_U_c(j) == 'N' ) {
		_flag_U_c(j) = 'I';
	//Body for U_c
		U_c(j) = reduce_bsub_abft_verify_U_c_1(N,j);
		_flag_U_c(j) = 'F';
	} else if ( _flag_U_c(j) == 'I' ) {
		printf("There is a self dependence on U_c at (%d) \n",j);
		exit(-1);
	}
	return U_c(j);
}
float reduce_bsub_abft_verify_b_1_1(long N, int ip){
	float reduceVar = 0;
	#define S0(i,k) reduceVar = (reduceVar)+((U(i,k))*(eval_verify_x(N,k)))
	{
		//Domain
		//{i,k|ip>=0 && N>=ip+2 && N>=1 && i>=0 && k>=i && N>=k+1 && N>=i+2 && k>=0 && ip==i}
		int c2;
		for(c2=ip;c2 <= N-1;c2+=1)
		 {
		 	S0((ip),(c2));
		 }
	}
	#undef S0
	return reduceVar;
}
float eval_verify_b_1(long N, int i){
	if ( _flag_b_1(i) == 'N' ) {
		_flag_b_1(i) = 'I';
	//Body for b_1
		b_1(i) = (((i == N-1))?b(i):(reduce_bsub_abft_verify_b_1_1(N,i)));
		_flag_b_1(i) = 'F';
	} else if ( _flag_b_1(i) == 'I' ) {
		printf("There is a self dependence on b_1 at (%d) \n",i);
		exit(-1);
	}
	return b_1(i);
}
float eval_verify_Inv_b_c(long N, int i){
	if ( _flag_Inv_b_c(i) == 'N' ) {
		_flag_Inv_b_c(i) = 'I';
	//Body for Inv_b_c
		Inv_b_c(i) = ((b(i))-(eval_verify_b_1(N,i)))/(b(i));
		_flag_Inv_b_c(i) = 'F';
	} else if ( _flag_Inv_b_c(i) == 'I' ) {
		printf("There is a self dependence on Inv_b_c at (%d) \n",i);
		exit(-1);
	}
	return Inv_b_c(i);
}

//Memory Macros
#undef U
#undef b
#undef b_1
#undef U_c
#undef x
#undef Inv_b_c
#undef _flag_x
#undef _flag_Inv_b_c
#undef _flag_b_1
#undef _flag_U_c


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
