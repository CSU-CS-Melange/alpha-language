CFLAGS= -O3  -std=c99  -I/usr/include/malloc/
LIBRARIES=-lm
CC?=gcc
OBJS = k_means.o 
all: plain check verify verify-rand

debug: CFLAGS =-DDEBUG -g -Wall -Wextra -std=c99 -I/usr/include/malloc/
debug: all

plain: $(OBJS)
	$(CC) k_means-wrapper.c -o k_means $(OBJS) $(CFLAGS) $(LIBRARIES)

check: $(OBJS)
	$(CC) k_means-wrapper.c -o k_means.check $(OBJS) $(CFLAGS) $(LIBRARIES) -DCHECKING

verify: $(OBJS) k_means_verify.o
	$(CC) k_means-wrapper.c -o k_means.verify $(OBJS) k_means_verify.o $(CFLAGS) $(LIBRARIES) -DVERIFY

verify-rand: $(OBJS) k_means_verify.o
	$(CC) k_means-wrapper.c -o k_means.verify-rand $(OBJS) k_means_verify.o $(CFLAGS) $(LIBRARIES) -DVERIFY -DRANDOM

k_means.o : k_means.c
	$(CC) k_means.c -o k_means.o $(CFLAGS) $(LIBRARIES) -c

k_means_verify.o : k_means_verify.c
	$(CC) k_means_verify.c -o k_means_verify.o $(CFLAGS) $(LIBRARIES) -c

clean:
	rm -f *.o k_means k_means.check k_means.verify k_means.verify-rand
