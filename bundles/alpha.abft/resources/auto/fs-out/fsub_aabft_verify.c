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
static float** L;
static float* b;
static float* x_c_0;
static float* x_c_1;
static float* x;
static float* Inv_x_c;
static char* _flag_x;
static char* _flag_Inv_x_c;
static char* _flag_x_c_0;
static char* _flag_x_c_1;


//Local Function Declarations
float reduce_fsub_aabft_verify_x_1(long, int);
float eval_verify_x(long, int);
float reduce_fsub_aabft_verify_x_c_0_1(long, int);
float eval_verify_x_c_0(long, int);
float reduce_fsub_aabft_verify_x_c_1_2(long, int, int);
float reduce_fsub_aabft_verify_x_c_1_1(long, int);
float eval_verify_x_c_1(long, int);
float eval_verify_Inv_x_c(long, int);

//Memory Macros
#define L(i,j) L[i][j]
#define b(i) b[i]
#define x_c_0(s) x_c_0[s]
#define x_c_1(s) x_c_1[s]
#define x(i) x[i]
#define Inv_x_c(s) Inv_x_c[s]
#define _flag_x(i) _flag_x[i]
#define _flag_Inv_x_c(s) _flag_Inv_x_c[s]
#define _flag_x_c_0(s) _flag_x_c_0[s]
#define _flag_x_c_1(s) _flag_x_c_1[s]

void fsub_aabft_verify(long N, float** _local_L, float* _local_b, float* _local_x, float* _local_Inv_x_c){
	///Parameter checking
	if (!((N >= 1))) {
		printf("The value of parameters are not valid.\n");
		exit(-1);
	}
	//Copy to global
	L = _local_L;
	b = _local_b;
	x = _local_x;
	Inv_x_c = _local_Inv_x_c;
	
	//Memory Allocation
	int mz1;
	
	x_c_0 = (float*)malloc(sizeof(float)*(1));
	mallocCheck(x_c_0, (1), float);
	
	x_c_1 = (float*)malloc(sizeof(float)*(1));
	mallocCheck(x_c_1, (1), float);
	
	_flag_x = (char*)malloc(sizeof(char)*(N));
	mallocCheck(_flag_x, (N), char);
	memset(_flag_x, 'N', (N));
	
	_flag_Inv_x_c = (char*)malloc(sizeof(char)*(1));
	mallocCheck(_flag_Inv_x_c, (1), char);
	memset(_flag_Inv_x_c, 'N', (1));
	
	_flag_x_c_0 = (char*)malloc(sizeof(char)*(1));
	mallocCheck(_flag_x_c_0, (1), char);
	memset(_flag_x_c_0, 'N', (1));
	
	_flag_x_c_1 = (char*)malloc(sizeof(char)*(1));
	mallocCheck(_flag_x_c_1, (1), char);
	memset(_flag_x_c_1, 'N', (1));
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
	#define S0(s) eval_verify_Inv_x_c(N,s)
	{
		//Domain
		//{s|s==0 && N>=1}
		S0((0));
	}
	#undef S0
	
	//Memory Free
	free(x_c_0);
	free(x_c_1);
	free(_flag_x);
	free(_flag_Inv_x_c);
	free(_flag_x_c_0);
	free(_flag_x_c_1);
}
float reduce_fsub_aabft_verify_x_1(long N, int ip){
	float reduceVar = 0;
	#define S0(i,j) reduceVar = (reduceVar)+((L(i,j))*(eval_verify_x(N,j)))
	{
		//Domain
		//{i,j|ip>=1 && N>=ip+1 && N>=1 && i>=1 && j>=0 && i>=j+1 && N>=i+1 && N>=j+1 && ip==i}
		int c2;
		for(c2=0;c2 <= ip-1;c2+=1)
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
		x(i) = (((i == 0))?(b(i))/(L(i,i)):(((b(i))-(reduce_fsub_aabft_verify_x_1(N,i)))/(L(i,i))));
		_flag_x(i) = 'F';
	} else if ( _flag_x(i) == 'I' ) {
		printf("There is a self dependence on x at (%d) \n",i);
		exit(-1);
	}
	return x(i);
}
float reduce_fsub_aabft_verify_x_c_0_1(long N, int ip){
	float reduceVar = 0;
	#define S0(i) reduceVar = (reduceVar)+(eval_verify_x(N,i))
	{
		//Domain
		//{i|i==0 && ip==0 && N>=1 && ip==i}
		S0((0));
	}
	#undef S0
	return reduceVar;
}
float eval_verify_x_c_0(long N, int s){
	if ( _flag_x_c_0(s) == 'N' ) {
		_flag_x_c_0(s) = 'I';
	//Body for x_c_0
		x_c_0(s) = reduce_fsub_aabft_verify_x_c_0_1(N,s);
		_flag_x_c_0(s) = 'F';
	} else if ( _flag_x_c_0(s) == 'I' ) {
		printf("There is a self dependence on x_c_0 at (%d) \n",s);
		exit(-1);
	}
	return x_c_0(s);
}
float reduce_fsub_aabft_verify_x_c_1_2(long N, int sp, int ip){
	float reduceVar = 0;
	#define S0(s,i,j) reduceVar = (reduceVar)+((L(i,j))*(eval_verify_x(N,j)))
	{
		//Domain
		//{s,i,j|s==0 && sp==0 && ip>=1 && N>=ip+1 && N>=1 && i>=1 && j>=0 && i>=j+1 && N>=i+1 && N>=j+1 && sp==s && ip==i}
		int c3;
		for(c3=0;c3 <= ip-1;c3+=1)
		 {
		 	S0((0),(ip),(c3));
		 }
	}
	#undef S0
	return reduceVar;
}
float reduce_fsub_aabft_verify_x_c_1_1(long N, int sp){
	float reduceVar = 0;
	#define S0(s,i) reduceVar = (reduceVar)+((((i == 0 && s == 0))?(b(i))/(L(i,i)):(((b(i))-(reduce_fsub_aabft_verify_x_c_1_2(N,s,i)))/(L(i,i)))))
	{
		//Domain
		//{s,i|s==0 && sp==0 && N>=1 && i>=0 && N>=i+1 && sp==s}
		int c2;
		for(c2=0;c2 <= N-1;c2+=1)
		 {
		 	S0((0),(c2));
		 }
	}
	#undef S0
	return reduceVar;
}
float eval_verify_x_c_1(long N, int s){
	if ( _flag_x_c_1(s) == 'N' ) {
		_flag_x_c_1(s) = 'I';
	//Body for x_c_1
		x_c_1(s) = reduce_fsub_aabft_verify_x_c_1_1(N,s);
		_flag_x_c_1(s) = 'F';
	} else if ( _flag_x_c_1(s) == 'I' ) {
		printf("There is a self dependence on x_c_1 at (%d) \n",s);
		exit(-1);
	}
	return x_c_1(s);
}
float eval_verify_Inv_x_c(long N, int s){
	if ( _flag_Inv_x_c(s) == 'N' ) {
		_flag_Inv_x_c(s) = 'I';
	//Body for Inv_x_c
		Inv_x_c(s) = ((eval_verify_x_c_0(N,s))-(eval_verify_x_c_1(N,s)))/(eval_verify_x_c_0(N,s));
		_flag_Inv_x_c(s) = 'F';
	} else if ( _flag_Inv_x_c(s) == 'I' ) {
		printf("There is a self dependence on Inv_x_c at (%d) \n",s);
		exit(-1);
	}
	return Inv_x_c(s);
}

//Memory Macros
#undef L
#undef b
#undef x_c_0
#undef x_c_1
#undef x
#undef Inv_x_c
#undef _flag_x
#undef _flag_Inv_x_c
#undef _flag_x_c_0
#undef _flag_x_c_1


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
