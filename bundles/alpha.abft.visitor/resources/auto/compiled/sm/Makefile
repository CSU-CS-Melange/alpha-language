CFLAGS= -O3  -std=c99  -I/usr/include/malloc/
LIBRARIES=-lm
CC?=gcc
OBJS = scalmat_aabft.o 
all: plain check verify verify-rand

debug: CFLAGS =-DDEBUG -g -Wall -Wextra -std=c99 -I/usr/include/malloc/
debug: all

plain: $(OBJS)
	$(CC) scalmat_aabft-wrapper.c -o scalmat_aabft $(OBJS) $(CFLAGS) $(LIBRARIES)

check: $(OBJS)
	$(CC) scalmat_aabft-wrapper.c -o scalmat_aabft.check $(OBJS) $(CFLAGS) $(LIBRARIES) -DCHECKING

verify: $(OBJS) scalmat_aabft_verify.o
	$(CC) scalmat_aabft-wrapper.c -o scalmat_aabft.verify $(OBJS) scalmat_aabft_verify.o $(CFLAGS) $(LIBRARIES) -DVERIFY

verify-rand: $(OBJS) scalmat_aabft_verify.o
	$(CC) scalmat_aabft-wrapper.c -o scalmat_aabft.verify-rand $(OBJS) scalmat_aabft_verify.o $(CFLAGS) $(LIBRARIES) -DVERIFY -DRANDOM

scalmat_aabft.o : scalmat_aabft.c
	$(CC) scalmat_aabft.c -o scalmat_aabft.o $(CFLAGS) $(LIBRARIES) -c

scalmat_aabft_verify.o : scalmat_aabft_verify.c
	$(CC) scalmat_aabft_verify.c -o scalmat_aabft_verify.o $(CFLAGS) $(LIBRARIES) -c

clean:
	rm -f *.o scalmat_aabft scalmat_aabft.check scalmat_aabft.verify scalmat_aabft.verify-rand
