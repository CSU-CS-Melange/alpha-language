CFLAGS= -O3  -std=c99  -I/usr/include/malloc/
LIBRARIES=-lm
CC?=gcc
OBJS = matvec.o 
all: plain check verify verify-rand

debug: CFLAGS =-DDEBUG -g -Wall -Wextra -std=c99 -I/usr/include/malloc/
debug: all

plain: $(OBJS)
	$(CC) matvec-wrapper.c -o matvec $(OBJS) $(CFLAGS) $(LIBRARIES)

check: $(OBJS)
	$(CC) matvec-wrapper.c -o matvec.check $(OBJS) $(CFLAGS) $(LIBRARIES) -DCHECKING

verify: $(OBJS) matvec_verify.o
	$(CC) matvec-wrapper.c -o matvec.verify $(OBJS) matvec_verify.o $(CFLAGS) $(LIBRARIES) -DVERIFY

verify-rand: $(OBJS) matvec_verify.o
	$(CC) matvec-wrapper.c -o matvec.verify-rand $(OBJS) matvec_verify.o $(CFLAGS) $(LIBRARIES) -DVERIFY -DRANDOM

matvec.o : matvec.c
	$(CC) matvec.c -o matvec.o $(CFLAGS) $(LIBRARIES) -c

matvec_verify.o : matvec_verify.c
	$(CC) matvec_verify.c -o matvec_verify.o $(CFLAGS) $(LIBRARIES) -c

clean:
	rm -f *.o matvec matvec.check matvec.verify matvec.verify-rand
