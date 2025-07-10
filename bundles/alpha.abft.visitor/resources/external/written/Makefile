CFLAGS= -O3  -std=c99  -I/usr/include/malloc/
LIBRARIES=-lm
CC?=gcc
OBJS = ext.o 
all: plain check verify verify-rand

debug: CFLAGS =-DDEBUG -g -Wall -Wextra -std=c99 -I/usr/include/malloc/
debug: all

plain: $(OBJS)
	$(CC) ext-wrapper.c -o ext $(OBJS) $(CFLAGS) $(LIBRARIES)

check: $(OBJS)
	$(CC) ext-wrapper.c -o ext.check $(OBJS) $(CFLAGS) $(LIBRARIES) -DCHECKING

verify: $(OBJS) ext_verify.o
	$(CC) ext-wrapper.c -o ext.verify $(OBJS) ext_verify.o $(CFLAGS) $(LIBRARIES) -DVERIFY

verify-rand: $(OBJS) ext_verify.o
	$(CC) ext-wrapper.c -o ext.verify-rand $(OBJS) ext_verify.o $(CFLAGS) $(LIBRARIES) -DVERIFY -DRANDOM

ext.o : ext.c
	$(CC) ext.c -o ext.o $(CFLAGS) $(LIBRARIES) -c

ext_verify.o : ext_verify.c
	$(CC) ext_verify.c -o ext_verify.o $(CFLAGS) $(LIBRARIES) -c

clean:
	rm -f *.o ext ext.check ext.verify ext.verify-rand
