def hello(){
  echo "Hello HQ"

}
def param="xyz"

def getSth(){
  def ret=getClass()
  println "getC=$ret"
  ret=ret.protectionDomain
  println "proDm=$ret"
  ret=ret.codeSource
  println "CodeS=$ret"
  ret=ret.location
  println "loc=$ret"
  ret=ret.path.split('/jobs/')[0]
  println "path=$ret"
  return ret
}
