import groovy.json.JsonSlurper

@groovy.transform.Field
def user='fredzhang123:ATBBFSVPkWdLh9LjtZpc2wWXeNqADB6891B3'
@groovy.transform.Field
def envList=['DEV','BAT']
@groovy.transform.Field
def serversList=[DEV: ['s11','ss22','ss33'],
                 BAT: ['s44','s55','s66'] ] 
@groovy.transform.Field
def defaultList=['ss33', 's55']
String buildDefault(List out,String key){
   def tmp=[]
   out.each{ if(it.contains(key)){ tmp.add(0, it) } else { tmp.add(it) } }
   return tmp
}
String setDefault(List out,String key){
   def tmp=[]
   out.each{ if(it.contains(key)){ tmp.add(0,'"'+it+'"') }  else { tmp.add('"'+it+'"') } } 
   return tmp
}         
String getServerScript(String refvar){
    def map=[:]
    envList.eachWithIndex{ it,index->
       map[it]=setDefault(serversList[it],defaultList[index])
    }
    return """def map=${map}
    |return map[${refvar}]
    |""".stripMargin()
}
def getPack(String mypath, String dft){
    println("enter getPack()")
   tmp=[]
  def out="ssh root@192.168.0.16 ls ${mypath}/*.tar.gz".execute().text
  println "out=$out"
  out.readLines().each{ if( it.contains(dft) ){ tmp.add(0,it) } else { tmp.add(it) } }
  println "out=$out"
  return tmp
}         
def getPackScript(String mypath, String dft){
   println("enter getPackScript()")
  return """tmp=[]
  |def out="ssh root@192.168.0.16 ls ${mypath}/*.tar.gz".execute().text
  |out.readLines().each{ if( it.contains(\"${dft}\") ){ tmp.add(0,it) } else { tmp.add(it) } }
  |return tmp
  |""".stripMargin()
}
def getFile(String wksp, String dft){
    println("enter getFile()")
    def tmp=[]
    def out="ls ${wksp}/release  ".execute().text
    println "out=$out"
    out.readLines().each{ if( it.contains(dft) ){ tmp.add(0,it) } else { tmp.add(it) } }
    return tmp
}         
def getFileScript(String wksp, String dft){
    println("enter getFileScript()....")
  return """def tmp=[]
  |def out="ls ${wksp}/release  ".execute().text
  |out.readLines().each{ if( it.contains(\"${dft}\") ){ tmp.add(0,it) } else { tmp.add(it) } }
  |return tmp
  |""".stripMargin()
}
def getContentScript(String wksp, String refvar ){
    println("enter getContentScript()....")
    return """
    |def out="cat ${wksp}/release/\${${refvar}}".execute().text
    |out=out.replaceAll('components:\\n','')
    |return \"\"\" <textarea name=\"value\"  value  class=\"setting-input  \" type=\"text\" rows="10" cols="25">\${out}</textarea> \"\"\"
    | """.stripMargin()
}

String getBranches( String myurl, String dft ){
   if ( !myurl?.trim() ) { myurl = url  }
   def gettags = ("git ls-remote --heads  $myurl  ").execute().text
   def ret = gettags.readLines().collect { it.split()[1].replaceAll('refs/heads/', '') }
   return buildDefault(ret, dft)
}
String getFileHub(String repo, String folder ){
   def tmp=[]
   def cmd = """curl https://api.github.com/repos/${repo}/git/trees/master?recursive=2  """
   println "cmd=$cmd"
   def out = cmd.execute().text
   def jsonSlurper = new JsonSlurper()
   def obj = jsonSlurper.parseText(out)
   
   obj['tree'].each{
      if(it['path'].contains("${folder}/") ) { tmp.add(it['path'])}
    }
   return tmp
}
USERNAME='fredzhang123'
PASSWORD='ATBBFSVPkWdLh9LjtZpc2wWXeNqADB6891B3'
def getFileBit(String repo, String folder){
    def repoPR="https://api.bitbucket.org/2.0/repositories/$repo/src/master/releases"
    def ret=[]
    //def cmd="curl -u $USERNAME:$PASSWORD --request GET ${repoPR}  "
    def cmd="curl --request GET ${repoPR}  "
    def out=cmd.execute().text
  
    def json=new JsonSlurper()
    def obj=json.parseText(out)
  
    obj['values'].each { ret.add(it['path'])}
    return ret
    }

println getFileBit("wave-cloud/groovytest","releases")

