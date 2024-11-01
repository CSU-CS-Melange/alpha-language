CFLAGS= -O3  -std=c99  -I/usr/include/malloc/
LIBRARIES=-lm
CC?=gcc
OBJS = matmult_abft_full.o 
all: plain check verify verify-rand

debug: CFLAGS =-DDEBUG -g -Wall -Wextra -std=c99 -I/usr/include/malloc/
debug: all

plain: $(OBJS)
	$(CC) matmult_abft_full-wrapper.c -o matmult_abft_full $(OBJS) $(CFLAGS) $(LIBRARIES)

check: $(OBJS)
	$(CC) matmult_abft_full-wrapper.c -o matmult_abft_full.check $(OBJS) $(CFLAGS) $(LIBRARIES) -DCHECKING

verify: $(OBJS) matmult_abft_full_verify.o
	$(CC) matmult_abft_full-wrapper.c -o matmult_abft_full.verify $(OBJS) matmult_abft_full_verify.o $(CFLAGS) $(LIBRARIES) -DVERIFY

verify-rand: $(OBJS) matmult_abft_full_verify.o
	$(CC) matmult_abft_full-wrapper.c -o matmult_abft_full.verify-rand $(OBJS) matmult_abft_full_verify.o $(CFLAGS) $(LIBRARIES) -DVERIFY -DRANDOM

matmult_abft_full.o : matmult_abft_full.c
	$(CC) matmult_abft_full.c -o matmult_abft_full.o $(CFLAGS) $(LIBRARIES) -c

matmult_abft_full_verify.o : matmult_abft_full_verify.c
	$(CC) matmult_abft_full_verify.c -o matmult_abft_full_verify.o $(CFLAGS) $(LIBRARIES) -c

clean:
	rm -f *.o matmult_abft_full matmult_abft_full.check matmult_abft_full.verify matmult_abft_full.verify-rand
