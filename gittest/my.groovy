

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

def deploy="""
09:11:25 ... Deploying component Anvil_TradeEntry to /app/anvil/share/bin/Anvil_TradeEntry on 20.215.145.76
"""
def download="""
fsd  afa afa ...
09:11:25 Files for Anv version-233 already present in /app/anvil/installer/repository/Anv233
afafjaf aa
"""
def getKeys(String input,String key){
   def map=[:]
   def log=input.readLines().findAll{  it.contains(key)}
   log.each { line->
      if (line.contains(key)){
         def path=line.split().find{ it.contains(key) }
         line.split().each {
            if(path.contains(it) && it.length()>2 && path!=it) map[it]=path
         }
      }
   }
   return map
}

def out=getKeys(deploy,'/bin/')
def getBase(Map out){
   def list=[]
  out.each { type, mypath->
      def index = mypath.lastIndexOf('/')
      list.add( mypath[0..index-1])
  }
  return list
}
println getBase(out)


