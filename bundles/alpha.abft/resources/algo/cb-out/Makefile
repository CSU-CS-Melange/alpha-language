CFLAGS= -O3  -std=c99  -I/usr/include/malloc/
LIBRARIES=-lm
CC?=gcc
OBJS = cholesky_banachiewicz.o 
all: plain check verify verify-rand

debug: CFLAGS =-DDEBUG -g -Wall -Wextra -std=c99 -I/usr/include/malloc/
debug: all

plain: $(OBJS)
	$(CC) cholesky_banachiewicz-wrapper.c -o cholesky_banachiewicz $(OBJS) $(CFLAGS) $(LIBRARIES)

check: $(OBJS)
	$(CC) cholesky_banachiewicz-wrapper.c -o cholesky_banachiewicz.check $(OBJS) $(CFLAGS) $(LIBRARIES) -DCHECKING

verify: $(OBJS) cholesky_banachiewicz_verify.o
	$(CC) cholesky_banachiewicz-wrapper.c -o cholesky_banachiewicz.verify $(OBJS) cholesky_banachiewicz_verify.o $(CFLAGS) $(LIBRARIES) -DVERIFY

verify-rand: $(OBJS) cholesky_banachiewicz_verify.o
	$(CC) cholesky_banachiewicz-wrapper.c -o cholesky_banachiewicz.verify-rand $(OBJS) cholesky_banachiewicz_verify.o $(CFLAGS) $(LIBRARIES) -DVERIFY -DRANDOM

cholesky_banachiewicz.o : cholesky_banachiewicz.c
	$(CC) cholesky_banachiewicz.c -o cholesky_banachiewicz.o $(CFLAGS) $(LIBRARIES) -c

cholesky_banachiewicz_verify.o : cholesky_banachiewicz_verify.c
	$(CC) cholesky_banachiewicz_verify.c -o cholesky_banachiewicz_verify.o $(CFLAGS) $(LIBRARIES) -c

clean:
	rm -f *.o cholesky_banachiewicz cholesky_banachiewicz.check cholesky_banachiewicz.verify cholesky_banachiewicz.verify-rand
