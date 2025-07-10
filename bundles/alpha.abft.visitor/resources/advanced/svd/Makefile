CFLAGS= -O3  -std=c99  -I/usr/include/malloc/
LIBRARIES=-lm
CC?=gcc
OBJS = svd.o 
all: plain check verify verify-rand

debug: CFLAGS =-DDEBUG -g -Wall -Wextra -std=c99 -I/usr/include/malloc/
debug: all

plain: $(OBJS)
	$(CC) svd-wrapper.c -o svd $(OBJS) $(CFLAGS) $(LIBRARIES)

check: $(OBJS)
	$(CC) svd-wrapper.c -o svd.check $(OBJS) $(CFLAGS) $(LIBRARIES) -DCHECKING

verify: $(OBJS) svd_verify.o
	$(CC) svd-wrapper.c -o svd.verify $(OBJS) svd_verify.o $(CFLAGS) $(LIBRARIES) -DVERIFY

verify-rand: $(OBJS) svd_verify.o
	$(CC) svd-wrapper.c -o svd.verify-rand $(OBJS) svd_verify.o $(CFLAGS) $(LIBRARIES) -DVERIFY -DRANDOM

svd.o : svd.c
	$(CC) svd.c -o svd.o $(CFLAGS) $(LIBRARIES) -c

svd_verify.o : svd_verify.c
	$(CC) svd_verify.c -o svd_verify.o $(CFLAGS) $(LIBRARIES) -c

clean:
	rm -f *.o svd svd.check svd.verify svd.verify-rand
