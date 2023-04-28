 
 set -x
 var="jenkins_home"
 cmd=" pwd && ls -al  "
 command='ls -a'
 cmd="$command $cmd "
 echo "cmd=$cmd"
 # ssh hongqizhang@localhost '\'\''cd' jenkins_home '&&' pwd '&&' ls -al '.\'\'''
 #ssh hongqizhang@localhost '"cd' jenkins_home '&&' pwd '&&' ls -al '."'
 #ssh hongqizhang@localhost 'pwd && ls -al jenkins_home'

 #log=`$cmd`
 #echo "log=$log"
 log=` cd ~ && pwd && ls -al ~/.ssh && cat ~/.ssh/id_rsa`
 echo "log=$log"
                      