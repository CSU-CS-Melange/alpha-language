CFLAGS= -O3  -std=c99  -I/usr/include/malloc/
LIBRARIES=-lm
CC?=gcc
OBJS = matvec_abft.o 
all: plain check verify verify-rand

debug: CFLAGS =-DDEBUG -g -Wall -Wextra -std=c99 -I/usr/include/malloc/
debug: all

plain: $(OBJS)
	$(CC) matvec_abft-wrapper.c -o matvec_abft $(OBJS) $(CFLAGS) $(LIBRARIES)

check: $(OBJS)
	$(CC) matvec_abft-wrapper.c -o matvec_abft.check $(OBJS) $(CFLAGS) $(LIBRARIES) -DCHECKING

verify: $(OBJS) matvec_abft_verify.o
	$(CC) matvec_abft-wrapper.c -o matvec_abft.verify $(OBJS) matvec_abft_verify.o $(CFLAGS) $(LIBRARIES) -DVERIFY

verify-rand: $(OBJS) matvec_abft_verify.o
	$(CC) matvec_abft-wrapper.c -o matvec_abft.verify-rand $(OBJS) matvec_abft_verify.o $(CFLAGS) $(LIBRARIES) -DVERIFY -DRANDOM

matvec_abft.o : matvec_abft.c
	$(CC) matvec_abft.c -o matvec_abft.o $(CFLAGS) $(LIBRARIES) -c

matvec_abft_verify.o : matvec_abft_verify.c
	$(CC) matvec_abft_verify.c -o matvec_abft_verify.o $(CFLAGS) $(LIBRARIES) -c

clean:
	rm -f *.o matvec_abft matvec_abft.check matvec_abft.verify matvec_abft.verify-rand
