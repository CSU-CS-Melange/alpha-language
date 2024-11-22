CFLAGS= -O3  -std=c99  -I/usr/include/malloc/
LIBRARIES=-lm
CC?=gcc
OBJS = matmult.o 
all: plain check verify verify-rand

debug: CFLAGS =-DDEBUG -g -Wall -Wextra -std=c99 -I/usr/include/malloc/
debug: all

plain: $(OBJS)
	$(CC) matmult-wrapper.c -o matmult $(OBJS) $(CFLAGS) $(LIBRARIES)

check: $(OBJS)
	$(CC) matmult-wrapper.c -o matmult.check $(OBJS) $(CFLAGS) $(LIBRARIES) -DCHECKING

verify: $(OBJS) matmult_verify.o
	$(CC) matmult-wrapper.c -o matmult.verify $(OBJS) matmult_verify.o $(CFLAGS) $(LIBRARIES) -DVERIFY

verify-rand: $(OBJS) matmult_verify.o
	$(CC) matmult-wrapper.c -o matmult.verify-rand $(OBJS) matmult_verify.o $(CFLAGS) $(LIBRARIES) -DVERIFY -DRANDOM

matmult.o : matmult.c
	$(CC) matmult.c -o matmult.o $(CFLAGS) $(LIBRARIES) -c

matmult_verify.o : matmult_verify.c
	$(CC) matmult_verify.c -o matmult_verify.o $(CFLAGS) $(LIBRARIES) -c

clean:
	rm -f *.o matmult matmult.check matmult.verify matmult.verify-rand
