CFLAGS= -O3  -std=c99  -I/usr/include/malloc/
LIBRARIES=-lm
CC?=gcc
OBJS = bsub_abft.o 
all: plain check verify verify-rand

debug: CFLAGS =-DDEBUG -g -Wall -Wextra -std=c99 -I/usr/include/malloc/
debug: all

plain: $(OBJS)
	$(CC) bsub_abft-wrapper.c -o bsub_abft $(OBJS) $(CFLAGS) $(LIBRARIES)

check: $(OBJS)
	$(CC) bsub_abft-wrapper.c -o bsub_abft.check $(OBJS) $(CFLAGS) $(LIBRARIES) -DCHECKING

verify: $(OBJS) bsub_abft_verify.o
	$(CC) bsub_abft-wrapper.c -o bsub_abft.verify $(OBJS) bsub_abft_verify.o $(CFLAGS) $(LIBRARIES) -DVERIFY

verify-rand: $(OBJS) bsub_abft_verify.o
	$(CC) bsub_abft-wrapper.c -o bsub_abft.verify-rand $(OBJS) bsub_abft_verify.o $(CFLAGS) $(LIBRARIES) -DVERIFY -DRANDOM

bsub_abft.o : bsub_abft.c
	$(CC) bsub_abft.c -o bsub_abft.o $(CFLAGS) $(LIBRARIES) -c

bsub_abft_verify.o : bsub_abft_verify.c
	$(CC) bsub_abft_verify.c -o bsub_abft_verify.o $(CFLAGS) $(LIBRARIES) -c

clean:
	rm -f *.o bsub_abft bsub_abft.check bsub_abft.verify bsub_abft.verify-rand
