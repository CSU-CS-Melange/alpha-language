CFLAGS= -O3  -std=c99  -I/usr/include/malloc/
LIBRARIES=-lm
CC?=gcc
OBJS = lud_aabft.o 
all: plain check verify verify-rand

debug: CFLAGS =-DDEBUG -g -Wall -Wextra -std=c99 -I/usr/include/malloc/
debug: all

plain: $(OBJS)
	$(CC) lud_aabft-wrapper.c -o lud_aabft $(OBJS) $(CFLAGS) $(LIBRARIES)

check: $(OBJS)
	$(CC) lud_aabft-wrapper.c -o lud_aabft.check $(OBJS) $(CFLAGS) $(LIBRARIES) -DCHECKING

verify: $(OBJS) lud_aabft_verify.o
	$(CC) lud_aabft-wrapper.c -o lud_aabft.verify $(OBJS) lud_aabft_verify.o $(CFLAGS) $(LIBRARIES) -DVERIFY

verify-rand: $(OBJS) lud_aabft_verify.o
	$(CC) lud_aabft-wrapper.c -o lud_aabft.verify-rand $(OBJS) lud_aabft_verify.o $(CFLAGS) $(LIBRARIES) -DVERIFY -DRANDOM

lud_aabft.o : lud_aabft.c
	$(CC) lud_aabft.c -o lud_aabft.o $(CFLAGS) $(LIBRARIES) -c

lud_aabft_verify.o : lud_aabft_verify.c
	$(CC) lud_aabft_verify.c -o lud_aabft_verify.o $(CFLAGS) $(LIBRARIES) -c

clean:
	rm -f *.o lud_aabft lud_aabft.check lud_aabft.verify lud_aabft.verify-rand
