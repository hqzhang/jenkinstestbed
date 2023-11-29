

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
def download="""09:11:25 Files for Anv version-233 already present in /app/anvil/installer/rpository/Anv233"""
def getKeys(String input){
   def map=[:]
   input.readLines().each { line->
      def var=line.split()
      def len=var.size()
      for( i=0; i<len; i++){
         for( j=0; j<len; j++){
            if (var[i].length()>2 && var[j].contains('app') && i!=j && var[j].contains(var[i])) map[var[i]]=var[j]

         }
      }
   }
   return map
}
data=download.split()
searchTerm='Anv'
def result = data.findAll { it.contains(searchTerm) }
println "result=$result"
println getKeys(download)
println getKeys(deploy)

