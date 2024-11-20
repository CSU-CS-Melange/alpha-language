CFLAGS= -O3  -std=c99  -I/usr/include/malloc/
LIBRARIES=-lm
CC?=gcc
OBJS = scalar.o 
all: plain check verify verify-rand

debug: CFLAGS =-DDEBUG -g -Wall -Wextra -std=c99 -I/usr/include/malloc/
debug: all

plain: $(OBJS)
	$(CC) scalar-wrapper.c -o scalar $(OBJS) $(CFLAGS) $(LIBRARIES)

check: $(OBJS)
	$(CC) scalar-wrapper.c -o scalar.check $(OBJS) $(CFLAGS) $(LIBRARIES) -DCHECKING

verify: $(OBJS) scalar_verify.o
	$(CC) scalar-wrapper.c -o scalar.verify $(OBJS) scalar_verify.o $(CFLAGS) $(LIBRARIES) -DVERIFY

verify-rand: $(OBJS) scalar_verify.o
	$(CC) scalar-wrapper.c -o scalar.verify-rand $(OBJS) scalar_verify.o $(CFLAGS) $(LIBRARIES) -DVERIFY -DRANDOM

scalar.o : scalar.c
	$(CC) scalar.c -o scalar.o $(CFLAGS) $(LIBRARIES) -c

scalar_verify.o : scalar_verify.c
	$(CC) scalar_verify.c -o scalar_verify.o $(CFLAGS) $(LIBRARIES) -c

clean:
	rm -f *.o scalar scalar.check scalar.verify scalar.verify-rand
