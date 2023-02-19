

def exeCmd(String cmd){
    println cmd
    proc=cmd.execute()
    out=proc.text
    code=proc.exitValue()
    return out
}

def gitStatus(){
   cmd ="git status "
   return exeCmd(cmd)
}

def pwdCmd(){
   cmd= "pwd"
   return exeCmd(cmd)
}

def command = "pwd"
def proc = command.execute()
proc.waitFor()
println proc.text