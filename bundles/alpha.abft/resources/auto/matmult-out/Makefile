CFLAGS= -O3  -std=c99  -I/usr/include/malloc/
LIBRARIES=-lm
CC?=gcc
OBJS = matmult_aabft.o 
all: plain check verify verify-rand

debug: CFLAGS =-DDEBUG -g -Wall -Wextra -std=c99 -I/usr/include/malloc/
debug: all

plain: $(OBJS)
	$(CC) matmult_aabft-wrapper.c -o matmult_aabft $(OBJS) $(CFLAGS) $(LIBRARIES)

check: $(OBJS)
	$(CC) matmult_aabft-wrapper.c -o matmult_aabft.check $(OBJS) $(CFLAGS) $(LIBRARIES) -DCHECKING

verify: $(OBJS) matmult_aabft_verify.o
	$(CC) matmult_aabft-wrapper.c -o matmult_aabft.verify $(OBJS) matmult_aabft_verify.o $(CFLAGS) $(LIBRARIES) -DVERIFY

verify-rand: $(OBJS) matmult_aabft_verify.o
	$(CC) matmult_aabft-wrapper.c -o matmult_aabft.verify-rand $(OBJS) matmult_aabft_verify.o $(CFLAGS) $(LIBRARIES) -DVERIFY -DRANDOM

matmult_aabft.o : matmult_aabft.c
	$(CC) matmult_aabft.c -o matmult_aabft.o $(CFLAGS) $(LIBRARIES) -c

matmult_aabft_verify.o : matmult_aabft_verify.c
	$(CC) matmult_aabft_verify.c -o matmult_aabft_verify.o $(CFLAGS) $(LIBRARIES) -c

clean:
	rm -f *.o matmult_aabft matmult_aabft.check matmult_aabft.verify matmult_aabft.verify-rand
