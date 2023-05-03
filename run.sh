#!/bin/bash
packs=`ls /root/workspace/myscripts/*.tar.gz`
cd /tmp
for var in ${packs};do
  myvar=`basename $var`
  echo "${myvar}"
  cp ${var} /tmp
  rm -rf /tmp/tools
  tar -xf ${myvar}
  grep name /tmp/tools/configuration.yml|sed -e s/^.*name\:\ //g 
  rm -rf /tmp/tools
  rm /tmp/${myvar}
done
cd ..
