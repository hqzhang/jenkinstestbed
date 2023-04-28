 
 set -x
 var="jenkins_home"
 cmd="ssh hongqizhang@localhost \"cd $var && pwd && ls -al .\""
 log=`$cmd`
 echo "log=$log"
                      