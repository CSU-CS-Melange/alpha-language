CFLAGS= -O3  -std=c99  -I/usr/include/malloc/
LIBRARIES=-lm
CC?=gcc
OBJS = lud.o 
all: plain check verify verify-rand

debug: CFLAGS =-DDEBUG -g -Wall -Wextra -std=c99 -I/usr/include/malloc/
debug: all

plain: $(OBJS)
	$(CC) lud-wrapper.c -o lud $(OBJS) $(CFLAGS) $(LIBRARIES)

check: $(OBJS)
	$(CC) lud-wrapper.c -o lud.check $(OBJS) $(CFLAGS) $(LIBRARIES) -DCHECKING

verify: $(OBJS) lud_verify.o
	$(CC) lud-wrapper.c -o lud.verify $(OBJS) lud_verify.o $(CFLAGS) $(LIBRARIES) -DVERIFY

verify-rand: $(OBJS) lud_verify.o
	$(CC) lud-wrapper.c -o lud.verify-rand $(OBJS) lud_verify.o $(CFLAGS) $(LIBRARIES) -DVERIFY -DRANDOM

lud.o : lud.c
	$(CC) lud.c -o lud.o $(CFLAGS) $(LIBRARIES) -c

lud_verify.o : lud_verify.c
	$(CC) lud_verify.c -o lud_verify.o $(CFLAGS) $(LIBRARIES) -c

clean:
	rm -f *.o lud lud.check lud.verify lud.verify-rand
