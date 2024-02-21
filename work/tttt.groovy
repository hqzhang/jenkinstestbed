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
    def ret=[]
    def out="ls ${wksp}/release  ".execute().text
    println "out=$out"
    out.readLines().each{ if( it.contains(dft) ){ ret.add(0,it) } else { ret.add(it) } }
    return ret
}         
def getFileScript(String wksp, String dft){
    println("enter getFileScript()....")
  return """def ret=[]
  |def out="ls ${wksp}/release  ".execute().text
  |out.readLines().each{ if( it.contains(\"${dft}\") ){ ret.add(0,it) } else { ret.add(it) } }
  |return ret
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
String getBranchesScript(String myurl, String dft ){
   return """def ret=[]
   |def out = "git ls-remote --heads  ${myurl}  ".execute().text
   |out = out.readLines().collect { it.split()[1].replaceAll('refs/heads/', '') }
   |out.each{ if( it.contains(\"${dft}\") ){ ret.add(0,it) } else { ret.add(it) } }
   |return ret
   | """.stripMargin()
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
@groovy.transform.Field
def USERNAME='fredzhang123'
@groovy.transform.Field
def PASSWORD='ATBBFSVPkWdLh9LjtZpc2wWXeNqADB6891B3'
def getFileBit(){
    println "ener getFileBit()"
    def repo="wave-cloud/groovytest"
    def folder="releases"
    def repoPR="https://api.bitbucket.org/2.0/repositories/$repo/src/master/releases"
    def ret=[]
    //def cmd="curl -u fredzhang123:ATBBFSVPkWdLh9LjtZpc2wWXeNqADB6891B3 --request GET ${repoPR}  "
    def cmd="curl --request GET ${repoPR}  "
    println "cmd=$cmd"
    def out=cmd.execute().text

    def json=new JsonSlurper()
    def obj=json.parseText(out)
  
    obj['values'].each { ret.add(it['path'])}
    return ret
    }

//println getFileBit()
/*
import org.yaml.snakeyaml.Yaml
def ret=[]
def fileName="/var/root/.jenkins/workspace/agroovytest/release/solution.yml"
String fileConts = "cat $fileName".execute().text.replaceAll('!component','')
Yaml yaml = new Yaml()
def Map  map = (Map) yaml.load(fileConts)
map['components'].each { ret.add(it.type)}
println ret*/

import org.yaml.snakeyaml.Yaml
def rollbacklist="release-PRIOR"
def ret=[],type=''
def fileName="/var/root/.jenkins/workspace/agroovytest/solution/${rollbacklist}/solution.yml"
String fileConts = "cat $fileName".execute().text.replaceAll('- !component\n','').replaceAll('components:\n','')
fileConts.readLines().eachWithIndex {it, idx ->
    var=it.split(': ') 
    if (idx%2==0) { type=var[-1]}
    else { ret.add( type+'/'+var[-1])}
}
 
println ret
  

