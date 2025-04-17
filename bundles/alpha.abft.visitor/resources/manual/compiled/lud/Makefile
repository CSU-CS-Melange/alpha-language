CFLAGS= -O3  -std=c99  -I/usr/include/malloc/
LIBRARIES=-lm
CC?=gcc
OBJS = lud_abft_2.o 
all: plain check verify verify-rand

debug: CFLAGS =-DDEBUG -g -Wall -Wextra -std=c99 -I/usr/include/malloc/
debug: all

plain: $(OBJS)
	$(CC) lud_abft_2-wrapper.c -o lud_abft_2 $(OBJS) $(CFLAGS) $(LIBRARIES)

check: $(OBJS)
	$(CC) lud_abft_2-wrapper.c -o lud_abft_2.check $(OBJS) $(CFLAGS) $(LIBRARIES) -DCHECKING

verify: $(OBJS) lud_abft_2_verify.o
	$(CC) lud_abft_2-wrapper.c -o lud_abft_2.verify $(OBJS) lud_abft_2_verify.o $(CFLAGS) $(LIBRARIES) -DVERIFY

verify-rand: $(OBJS) lud_abft_2_verify.o
	$(CC) lud_abft_2-wrapper.c -o lud_abft_2.verify-rand $(OBJS) lud_abft_2_verify.o $(CFLAGS) $(LIBRARIES) -DVERIFY -DRANDOM

lud_abft_2.o : lud_abft_2.c
	$(CC) lud_abft_2.c -o lud_abft_2.o $(CFLAGS) $(LIBRARIES) -c

lud_abft_2_verify.o : lud_abft_2_verify.c
	$(CC) lud_abft_2_verify.c -o lud_abft_2_verify.o $(CFLAGS) $(LIBRARIES) -c

clean:
	rm -f *.o lud_abft_2 lud_abft_2.check lud_abft_2.verify lud_abft_2.verify-rand
