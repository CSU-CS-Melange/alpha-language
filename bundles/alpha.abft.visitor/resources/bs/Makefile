CFLAGS= -O3  -std=c99  -I/usr/include/malloc/
LIBRARIES=-lm
CC?=gcc
OBJS = bsub.o 
all: plain check verify verify-rand

debug: CFLAGS =-DDEBUG -g -Wall -Wextra -std=c99 -I/usr/include/malloc/
debug: all

plain: $(OBJS)
	$(CC) bsub-wrapper.c -o bsub $(OBJS) $(CFLAGS) $(LIBRARIES)

check: $(OBJS)
	$(CC) bsub-wrapper.c -o bsub.check $(OBJS) $(CFLAGS) $(LIBRARIES) -DCHECKING

verify: $(OBJS) bsub_verify.o
	$(CC) bsub-wrapper.c -o bsub.verify $(OBJS) bsub_verify.o $(CFLAGS) $(LIBRARIES) -DVERIFY

verify-rand: $(OBJS) bsub_verify.o
	$(CC) bsub-wrapper.c -o bsub.verify-rand $(OBJS) bsub_verify.o $(CFLAGS) $(LIBRARIES) -DVERIFY -DRANDOM

bsub.o : bsub.c
	$(CC) bsub.c -o bsub.o $(CFLAGS) $(LIBRARIES) -c

bsub_verify.o : bsub_verify.c
	$(CC) bsub_verify.c -o bsub_verify.o $(CFLAGS) $(LIBRARIES) -c

clean:
	rm -f *.o bsub bsub.check bsub.verify bsub.verify-rand
