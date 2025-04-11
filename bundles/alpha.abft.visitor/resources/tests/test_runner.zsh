# !/bin/bash

sizes=(2 5 10 20 50 100 200 300 400 500 600 700 800 900 1000 1250 1500 1750 2000 2500 3000 3500 4000 4500 5000)
path="../"
ext=".verify-rand"

progs=("lud/lud" "lud-abft/lud_abft")
now=$(date)
echo Starting $now

now=$(sed -e 's/ /-/g' -e 's/://g' <<< $now)
now=$(tr '[:upper:]' '[:lower:]' <<< $now)
log=test_log_$now.csv

echo Saving results to $log
echo "prog,N,time" > $log

t="0"

for p in ${progs[@]}; do
    for n in ${sizes[@]}; do
        prog=$(sed -e 's|[^/]*/|/|' -e 's|/||' <<< $p)

        echo "Executing $prog with parameter N=$n"

        t=$($path$p$ext $n | sed 's/[^0-9.]*//g' | sed -r 's/.{1}$//')
        
        echo Processed $n-by-$n in $t seconds
        echo "$prog,$n,$t" >> $log
    done
done

echo "Done"
