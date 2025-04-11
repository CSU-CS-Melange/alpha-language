CFLAGS= -O3  -std=c99  -I/usr/include/malloc/
LIBRARIES=-lm
CC?=gcc
OBJS = lud_abft.o 
all: plain check verify verify-rand

debug: CFLAGS =-DDEBUG -g -Wall -Wextra -std=c99 -I/usr/include/malloc/
debug: all

plain: $(OBJS)
	$(CC) lud_abft-wrapper.c -o lud_abft $(OBJS) $(CFLAGS) $(LIBRARIES)

check: $(OBJS)
	$(CC) lud_abft-wrapper.c -o lud_abft.check $(OBJS) $(CFLAGS) $(LIBRARIES) -DCHECKING

verify: $(OBJS) lud_abft_verify.o
	$(CC) lud_abft-wrapper.c -o lud_abft.verify $(OBJS) lud_abft_verify.o $(CFLAGS) $(LIBRARIES) -DVERIFY

verify-rand: $(OBJS) lud_abft_verify.o
	$(CC) lud_abft-wrapper.c -o lud_abft.verify-rand $(OBJS) lud_abft_verify.o $(CFLAGS) $(LIBRARIES) -DVERIFY -DRANDOM

lud_abft.o : lud_abft.c
	$(CC) lud_abft.c -o lud_abft.o $(CFLAGS) $(LIBRARIES) -c

lud_abft_verify.o : lud_abft_verify.c
	$(CC) lud_abft_verify.c -o lud_abft_verify.o $(CFLAGS) $(LIBRARIES) -c

clean:
	rm -f *.o lud_abft lud_abft.check lud_abft.verify lud_abft.verify-rand
