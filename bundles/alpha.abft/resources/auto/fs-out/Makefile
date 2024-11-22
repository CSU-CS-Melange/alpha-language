CFLAGS= -O3  -std=c99  -I/usr/include/malloc/
LIBRARIES=-lm
CC?=gcc
OBJS = fsub_aabft.o 
all: plain check verify verify-rand

debug: CFLAGS =-DDEBUG -g -Wall -Wextra -std=c99 -I/usr/include/malloc/
debug: all

plain: $(OBJS)
	$(CC) fsub_aabft-wrapper.c -o fsub_aabft $(OBJS) $(CFLAGS) $(LIBRARIES)

check: $(OBJS)
	$(CC) fsub_aabft-wrapper.c -o fsub_aabft.check $(OBJS) $(CFLAGS) $(LIBRARIES) -DCHECKING

verify: $(OBJS) fsub_aabft_verify.o
	$(CC) fsub_aabft-wrapper.c -o fsub_aabft.verify $(OBJS) fsub_aabft_verify.o $(CFLAGS) $(LIBRARIES) -DVERIFY

verify-rand: $(OBJS) fsub_aabft_verify.o
	$(CC) fsub_aabft-wrapper.c -o fsub_aabft.verify-rand $(OBJS) fsub_aabft_verify.o $(CFLAGS) $(LIBRARIES) -DVERIFY -DRANDOM

fsub_aabft.o : fsub_aabft.c
	$(CC) fsub_aabft.c -o fsub_aabft.o $(CFLAGS) $(LIBRARIES) -c

fsub_aabft_verify.o : fsub_aabft_verify.c
	$(CC) fsub_aabft_verify.c -o fsub_aabft_verify.o $(CFLAGS) $(LIBRARIES) -c

clean:
	rm -f *.o fsub_aabft fsub_aabft.check fsub_aabft.verify fsub_aabft.verify-rand
