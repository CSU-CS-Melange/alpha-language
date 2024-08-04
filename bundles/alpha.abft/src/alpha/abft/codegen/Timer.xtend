package alpha.abft.codegen

class Timer {
	
	
	def static generateTimer() {
 		generate
 	}
 	
 	def static generate() '''
		#include<stdio.h>
		#include<sys/time.h>
		
		static double start, stop;        /* store the times locally */
		static int start_flag, stop_flag; /* flag timer use */
		
		void initialize_timer()
		{
		    start = 0.0;
		    stop  = 0.0;
		}
		
		void reset_timer()
		{
		    initialize_timer();
		}
		
		void start_timer()
		{
		    struct timeval time;
		    if (gettimeofday(&time, NULL) < 0)
				perror("start_timer,gettimeofday");
		    start = (((double) time.tv_sec) + ((double) time.tv_usec)/1000000);
		}
		
		void stop_timer()
		{
		    struct timeval time;
		    if (gettimeofday(&time, NULL) < 0)
				perror("stop_timer,gettimeofday");
		    stop = (((double) time.tv_sec) + ((double) time.tv_usec)/1000000);
		}
		
		double elapsed_time()
		{
		    return (stop-start);
		}
	'''
 
 }