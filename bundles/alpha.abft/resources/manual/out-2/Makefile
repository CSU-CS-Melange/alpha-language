CFLAGS= -O3  -std=c99  -I/usr/include/malloc/
LIBRARIES=-lm
CC?=gcc
OBJS = matmult_abft.o 
all: plain check verify verify-rand

debug: CFLAGS =-DDEBUG -g -Wall -Wextra -std=c99 -I/usr/include/malloc/
debug: all

plain: $(OBJS)
	$(CC) matmult_abft-wrapper.c -o matmult_abft $(OBJS) $(CFLAGS) $(LIBRARIES)

check: $(OBJS)
	$(CC) matmult_abft-wrapper.c -o matmult_abft.check $(OBJS) $(CFLAGS) $(LIBRARIES) -DCHECKING

verify: $(OBJS) matmult_abft_verify.o
	$(CC) matmult_abft-wrapper.c -o matmult_abft.verify $(OBJS) matmult_abft_verify.o $(CFLAGS) $(LIBRARIES) -DVERIFY

verify-rand: $(OBJS) matmult_abft_verify.o
	$(CC) matmult_abft-wrapper.c -o matmult_abft.verify-rand $(OBJS) matmult_abft_verify.o $(CFLAGS) $(LIBRARIES) -DVERIFY -DRANDOM

matmult_abft.o : matmult_abft.c
	$(CC) matmult_abft.c -o matmult_abft.o $(CFLAGS) $(LIBRARIES) -c

matmult_abft_verify.o : matmult_abft_verify.c
	$(CC) matmult_abft_verify.c -o matmult_abft_verify.o $(CFLAGS) $(LIBRARIES) -c

clean:
	rm -f *.o matmult_abft matmult_abft.check matmult_abft.verify matmult_abft.verify-rand
