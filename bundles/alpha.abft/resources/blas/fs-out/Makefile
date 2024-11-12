CFLAGS= -O3  -std=c99  -I/usr/include/malloc/
LIBRARIES=-lm
CC?=gcc
OBJS = fsub.o 
all: plain check verify verify-rand

debug: CFLAGS =-DDEBUG -g -Wall -Wextra -std=c99 -I/usr/include/malloc/
debug: all

plain: $(OBJS)
	$(CC) fsub-wrapper.c -o fsub $(OBJS) $(CFLAGS) $(LIBRARIES)

check: $(OBJS)
	$(CC) fsub-wrapper.c -o fsub.check $(OBJS) $(CFLAGS) $(LIBRARIES) -DCHECKING

verify: $(OBJS) fsub_verify.o
	$(CC) fsub-wrapper.c -o fsub.verify $(OBJS) fsub_verify.o $(CFLAGS) $(LIBRARIES) -DVERIFY

verify-rand: $(OBJS) fsub_verify.o
	$(CC) fsub-wrapper.c -o fsub.verify-rand $(OBJS) fsub_verify.o $(CFLAGS) $(LIBRARIES) -DVERIFY -DRANDOM

fsub.o : fsub.c
	$(CC) fsub.c -o fsub.o $(CFLAGS) $(LIBRARIES) -c

fsub_verify.o : fsub_verify.c
	$(CC) fsub_verify.c -o fsub_verify.o $(CFLAGS) $(LIBRARIES) -c

clean:
	rm -f *.o fsub fsub.check fsub.verify fsub.verify-rand
