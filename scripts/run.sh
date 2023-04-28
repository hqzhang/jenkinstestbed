 
 set -x
 var="jenkins_home"
 cmd=" pwd && ls -al "
 command='ssh root@192.168.0.16'
 cmd="$command $cmd "
 echo "cmd=$cmd"
 # ssh hongqizhang@localhost '\'\''cd' jenkins_home '&&' pwd '&&' ls -al '.\'\'''
 #ssh hongqizhang@localhost '"cd' jenkins_home '&&' pwd '&&' ls -al '."'
 #ssh hongqizhang@localhost 'pwd && ls -al jenkins_home'

 log=`$cmd`
 echo "log=$log"
 log=` pwd && ls -al`
 echo "log=$log"
                      