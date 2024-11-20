CFLAGS= -O3  -std=c99  -I/usr/include/malloc/
LIBRARIES=-lm
CC?=gcc
OBJS = fsub_abft.o 
all: plain check verify verify-rand

debug: CFLAGS =-DDEBUG -g -Wall -Wextra -std=c99 -I/usr/include/malloc/
debug: all

plain: $(OBJS)
	$(CC) fsub_abft-wrapper.c -o fsub_abft $(OBJS) $(CFLAGS) $(LIBRARIES)

check: $(OBJS)
	$(CC) fsub_abft-wrapper.c -o fsub_abft.check $(OBJS) $(CFLAGS) $(LIBRARIES) -DCHECKING

verify: $(OBJS) fsub_abft_verify.o
	$(CC) fsub_abft-wrapper.c -o fsub_abft.verify $(OBJS) fsub_abft_verify.o $(CFLAGS) $(LIBRARIES) -DVERIFY

verify-rand: $(OBJS) fsub_abft_verify.o
	$(CC) fsub_abft-wrapper.c -o fsub_abft.verify-rand $(OBJS) fsub_abft_verify.o $(CFLAGS) $(LIBRARIES) -DVERIFY -DRANDOM

fsub_abft.o : fsub_abft.c
	$(CC) fsub_abft.c -o fsub_abft.o $(CFLAGS) $(LIBRARIES) -c

fsub_abft_verify.o : fsub_abft_verify.c
	$(CC) fsub_abft_verify.c -o fsub_abft_verify.o $(CFLAGS) $(LIBRARIES) -c

clean:
	rm -f *.o fsub_abft fsub_abft.check fsub_abft.verify fsub_abft.verify-rand
