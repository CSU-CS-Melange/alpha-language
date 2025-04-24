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
static float* pts;
static float** S;
static char** _flag_S;


//Local Function Declarations
float eval_verify_S(long, int, int);

//Memory Macros
#define pts(i) pts[i]
#define S(i,j) S[i][j]
#define _flag_S(i,j) _flag_S[i][j]

void k_means_verify(long N, float* _local_k, float* _local_pts, float** _local_S){
	///Parameter checking
	if (!((N >= 1))) {
		printf("The value of parameters are not valid.\n");
		exit(-1);
	}
	//Copy to global
	
	k = *_local_k;
	pts = _local_pts;
	S = _local_S;
	
	//Memory Allocation
	int mz1, mz2;
	
	char* _lin__flag_S = (char*)malloc(sizeof(char)*((N+1) * (N+1)));
	mallocCheck(_lin__flag_S, ((N+1) * (N+1)), char);
	_flag_S = (char**)malloc(sizeof(char*)*(N+1));
	mallocCheck(_flag_S, (N+1), char*);
	for (mz1=0;mz1 < N+1; mz1++) {
		_flag_S[mz1] = &_lin__flag_S[(mz1*(N+1))];
	}
	memset(_lin__flag_S, 'N', ((N+1) * (N+1)));
	#define S0(i,j) eval_verify_S(N,i,j)
	{
		//Domain
		//{i,j|i>=1 && j>=1 && N>=i && N>=j && N>=1}
		int c1,c2;
		for(c1=1;c1 <= N;c1+=1)
		 {
		 	for(c2=1;c2 <= N;c2+=1)
		 	 {
		 	 	S0((c1),(c2));
		 	 }
		 }
	}
	#undef S0
	
	//Memory Free
	free(_lin__flag_S);
	free(_flag_S);
}
float eval_verify_S(long N, int i, int j){
	if ( _flag_S(i,j) == 'N' ) {
		_flag_S(i,j) = 'I';
	//Body for S
		S(i,j) = k;
		_flag_S(i,j) = 'F';
	} else if ( _flag_S(i,j) == 'I' ) {
		printf("There is a self dependence on S at (%d,%d) \n",i,j);
		exit(-1);
	}
	return S(i,j);
}

//Memory Macros
#undef pts
#undef S
#undef _flag_S


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
