CFLAGS= -O3  -std=c99  -I/usr/include/malloc/
LIBRARIES=-lm
CC?=gcc
OBJS = matvec_aabft.o 
all: plain check verify verify-rand

debug: CFLAGS =-DDEBUG -g -Wall -Wextra -std=c99 -I/usr/include/malloc/
debug: all

plain: $(OBJS)
	$(CC) matvec_aabft-wrapper.c -o matvec_aabft $(OBJS) $(CFLAGS) $(LIBRARIES)

check: $(OBJS)
	$(CC) matvec_aabft-wrapper.c -o matvec_aabft.check $(OBJS) $(CFLAGS) $(LIBRARIES) -DCHECKING

verify: $(OBJS) matvec_aabft_verify.o
	$(CC) matvec_aabft-wrapper.c -o matvec_aabft.verify $(OBJS) matvec_aabft_verify.o $(CFLAGS) $(LIBRARIES) -DVERIFY

verify-rand: $(OBJS) matvec_aabft_verify.o
	$(CC) matvec_aabft-wrapper.c -o matvec_aabft.verify-rand $(OBJS) matvec_aabft_verify.o $(CFLAGS) $(LIBRARIES) -DVERIFY -DRANDOM

matvec_aabft.o : matvec_aabft.c
	$(CC) matvec_aabft.c -o matvec_aabft.o $(CFLAGS) $(LIBRARIES) -c

matvec_aabft_verify.o : matvec_aabft_verify.c
	$(CC) matvec_aabft_verify.c -o matvec_aabft_verify.o $(CFLAGS) $(LIBRARIES) -c

clean:
	rm -f *.o matvec_aabft matvec_aabft.check matvec_aabft.verify matvec_aabft.verify-rand
