 
 set -x
 var="jenkins_home"
 cmd="cd $var && pwd && ls -al "
 command='ssh hongqizhang@localhost'
 cmd="$command \"$cmd\" "
 echo "cmd=$cmd"
 # ssh hongqizhang@localhost '\'\''cd' jenkins_home '&&' pwd '&&' ls -al '.\'\'''
 #ssh hongqizhang@localhost '"cd' jenkins_home '&&' pwd '&&' ls -al '."'
 #ssh hongqizhang@localhost 'pwd && ls -al jenkins_home'
 log=`$cmd`

 echo "log=$log"
                      