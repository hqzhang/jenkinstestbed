#!/bin/bash
list=`find . -type f -name '*.groovy'|xargs grep -o '^def [A-Za-z]*(' | grep -o ' [A-Za-z]*'`
#data="def mymethod(){"
#echo "$data" |grep -o '^def [A-Za-z]*('
echo $list
for i in $list;
do
    #echo $i
    out=`find . -type f | xargs grep -sinw --color $i`
    cnt=`find . -type f | xargs grep -sinw --color $i|wc -l`
    #echo $res
    if (( cnt == 1 )); then
       echo "Unused method at: $out"
    fi
done
