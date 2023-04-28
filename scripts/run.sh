 
 set -x
 var="jenkins_home"
 cmd="ssh hongqizhang@localhost \'cd $var && pwd && ls -al .\'"
 
 #ssh hongqizhang@localhost '"cd' jenkins_home '&&' pwd '&&' ls -al '."'
 #ssh hongqizhang@localhost 'pwd && ls -al jenkins_home'
 log=`$cmd`
 
 echo "log=$log"
                      