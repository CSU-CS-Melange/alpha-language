CFLAGS= -O3  -std=c99  -I/usr/include/malloc/
LIBRARIES=-lm
CC?=gcc
OBJS = bsub_aabft.o 
all: plain check verify verify-rand

debug: CFLAGS =-DDEBUG -g -Wall -Wextra -std=c99 -I/usr/include/malloc/
debug: all

plain: $(OBJS)
	$(CC) bsub_aabft-wrapper.c -o bsub_aabft $(OBJS) $(CFLAGS) $(LIBRARIES)

check: $(OBJS)
	$(CC) bsub_aabft-wrapper.c -o bsub_aabft.check $(OBJS) $(CFLAGS) $(LIBRARIES) -DCHECKING

verify: $(OBJS) bsub_aabft_verify.o
	$(CC) bsub_aabft-wrapper.c -o bsub_aabft.verify $(OBJS) bsub_aabft_verify.o $(CFLAGS) $(LIBRARIES) -DVERIFY

verify-rand: $(OBJS) bsub_aabft_verify.o
	$(CC) bsub_aabft-wrapper.c -o bsub_aabft.verify-rand $(OBJS) bsub_aabft_verify.o $(CFLAGS) $(LIBRARIES) -DVERIFY -DRANDOM

bsub_aabft.o : bsub_aabft.c
	$(CC) bsub_aabft.c -o bsub_aabft.o $(CFLAGS) $(LIBRARIES) -c

bsub_aabft_verify.o : bsub_aabft_verify.c
	$(CC) bsub_aabft_verify.c -o bsub_aabft_verify.o $(CFLAGS) $(LIBRARIES) -c

clean:
	rm -f *.o bsub_aabft bsub_aabft.check bsub_aabft.verify bsub_aabft.verify-rand
