
import org.yaml.snakeyaml.Yaml
import org.yaml.snakeyaml.DumperOptions
import jenkins.model.Jenkins
import jenkins.*
import hudson.*
import hudson.model.*
import hudson.model.Run

//import groovy.yaml.YamlSlurper
//// Remove everything which is currently queued/
def execmd(String cmd, String directory){
    ProcessBuilder procBuilder = new ProcessBuilder("bash", "-c", cmd);
    procBuilder.directory(new File(directory))
    procBuilder.redirectErrorStream(true);
    def proc = procBuilder.start()
    proc.waitFor()
    def err=proc.exitValue()
    def reader = new BufferedReader(new InputStreamReader(proc.getInputStream()))
    def line = null
    def out=''
    while ((line = reader.readLine()) != null) { out += line+ "\n" }
    if ( err != 0){
        println("ERROR: $out")
        error("Debug for cmd:$cmd")
    }
    return out
}
def execmd(String cmd){
    def proc=cmd.execute()
    proc.waitFor()
    def out=proc.in.text
    def err=proc.err.text
    def code=proc.exitValue()
    if ( code !=0 ) {
        println("Error11:$err")
        error("Jump out for debug")
    }
    return out
}
def test(){
    def cmd="ssh -q -t hongqizhang@localhost < /var/root/.jenkins/workspace/agroovytest/run.sh "
    def dir="/var/root/.jenkins/workspace/agroovytest"
    def out=exeCmd(cmd,dir)
    println "out=$out"
    println "end test()----------2----------" 
}
def getPackList(String mypath){
    println "enter getPackList()"
    def out="ssh hongqizhang@localhost ls ${mypath}/*.tar.gz".execute().text
    out=out.readLines().collect{ it }
    println "out=$out"
    return out
}

def getCompList(String mypack){
    println "enter getCompList()"
    def mykey=mypack.split("\\/")[-1].split("\\.")[0] 
    println "mykey=$mykey"
    def map=[:], lss=[], key='', sel=':selected';
    def out="ssh hongqizhang@localhost /Users/hongqizhang/workspace/myscripts/run.sh".execute().text
    out.readLines().each{ if (it.contains(".tar.gz") ){
    if ( ! key.isEmpty()){ map[key]=lss; key=it.split("\\.")[0]; lss=[] } else { key=it.split("\\.")[0] } }
    else { lss.add(it+sel) } } ; map[key]=lss
    println "map=$map"
    println "mkey=${map[mykey]}"
    return """def map=[:], lss=[], key='', sel=':selected';
    |def mykey=mypack.split("\\\\/")[-1].split("\\\\.")[0] 
    |def out="ssh hongqizhang@localhost /Users/hongqizhang/workspace/myscripts/run.sh".execute().text
    |out.readLines().each{ if (it.contains(".tar.gz") ){
    |if ( ! key.isEmpty()){ map[key]=lss; key=it.split("\\\\.")[0]; lss=[] } 
    |else { key=it.split("\\\\.")[0] } }
    |else { lss.add(it+sel) } } ; map[key]=lss
    |return map[mykey]
    | """.stripMargin()
}
def getCompVerify(){
    println "-------------getCompVerify()------------------------"
    def mypack='/root/workspace/myscripts/file.tar.gz'
def map=[:], lss=[], key='', sel=':selected';
def mykey=mypack.split("\\/")[-1].split("\\.")[0] 
def out="ssh hongqizhang@localhost /Users/hongqizhang/workspace/myscripts/run.sh".execute().text
out.readLines().each{ if (it.contains(".tar.gz") ){
if ( ! key.isEmpty()){ map[key]=lss; key=it.split("\\.")[0]; lss=[] } 
else { key=it.split("\\.")[0] } }
else { lss.add(it+sel) } } ; map[key]=lss
println "map=$map"
return map[mykey]

}

def getWorkspace(){
   def jobName = 'acgroovytest' // Replace with the name of your Jenkins job
   def buildNumber = 279 // Replace with the build number you want to retrieve the workspace path for

// Get the Jenkins instance
def jenkins = Jenkins.instance
// Get the job
def job = jenkins.getItemByFullName(jobName)
if (job) {
    // Get the build by build number
    def build = job.getBuildByNumber(buildNumber)
    if (build) {
        // Get the workspace path for the build
        def workspacePath = build.getWorkspace().toString()
        println("Workspace Path for ${jobName} build #${buildNumber}: ${workspacePath}")
    } else {
        println("Build #${buildNumber} for ${jobName} does not exist.")
    }
} 
}

def checkBuildRunning(){
    println "enter checkBuildRunning()"
    def job=getJob()
    def scriptDir = getClass().protectionDomain.codeSource.location.path
    def index=scriptDir.indexOf('jobs')
    println "index: $index"
    scriptDir = scriptDir.substring(1,index)
    println "scriptDir: $scriptDir"
   

    //Jenkins.instance.queue.clear()

    //def mybuild = Jenkins.instance.getQueue().getItems()[0].getFuture().get()
    //def workspace = mybuild.getWorkspace().getRemote()
    //println("Workspace: " + workspace)

    def buildingJobs = Jenkins.instance.getAllItems(Job.class).findAll {
        it.isBuilding()
    }
    println(buildingJobs)
    
    buildingJobs.each {
        def jobName = it.toString()
        println "jobName=$jobName"
        def val = jobName.split("\\[|\\]")
        println "val=$val"
        // 'Abort jobs' is the name of the job I have created, and I do not want it to abort itself.
        if((val[1].trim())!='Abort jobs') {
            def my=val[1].trim()
            println "my=$my"
           
            for (build in job.builds) {
                //println "build=$build"
                if (build.isBuilding()) {
                    //println ("currentbuild:"); println currentBuild
                    println("build:");
                    def buildNumber=build.toString().substring(14,).toInteger()
                    println buildNumber
                    //build.doStop();
                    def mybuild = job.getBuildByNumber(buildNumber)
                    def workspacePath = mybuild.getWorkspace().toString()
                    println("Workspace Path for ${jobName} build #${buildNumber}: ${workspacePath}")
                }
            }
        }
    }
}

String buildScript(List values){
    def ret=values.collect { '"'+it+'"' }
    return "return ${ret}"
}

String convertScript( String str){
    def ret='"""'+str.replaceAll('"', '\\\\"')+'"""'
    return  "return $ret"
}
def getBranch(){
    return scm.branches.toString().substring(3,9)
}
def getEnvar(){
    def wksp
    def jobstr
    def build_no
    def repo=scm.getUserRemoteConfigs().toString()
    def brch= scm.branches.toString().substring(3,9)
    println "repofull---------=$repo"
    repo=repo.substring(9, repo.length()-12).split('\\/')[4]
    node {
        wksp = env.WORKSPACE
        jobstr= env.JOB_NAME
        build_no=env.BUILD_NUMBER
    }
    env.WKSP=wksp
    env.JOBSTR=jobstr
    env.BLDNo=build_no
    env.REPO=repo
    env.BRCH=brch
}

def getFileContent(String SolutionDetail){
    def wksp=getWksp()
    def url="https://raw.githubusercontent.com/hqzhang/ansibletest"
    def mf ="ls ${wksp}/release  ".execute().text
    my_tag="curl -k https://raw.githubusercontent.com/hqzhang/ansibletest/main/release/${SolutionDetail}.xml".execute().text 
    def ret= "<textarea name=\"value\"  value  class=\"setting-input  \" type=\"text\">${my_tag}</textarea>"
    convertScript(ret)
}

def parseSolution(String data){
    def flag=false
    def start=true
    type=''
    def lss=[]
    def map=[:]
    rawdata.split("\n").each {
        kv=it.tokenize(":")
        if (it.contains('name')){
            if ( !start ) {
            lss.add(map); map=[:]
            map[kv[0]]=kv[1]
            } else { start=false }
            map[kv[0]]=kv[1]
        } else if (it.contains('daemon')){ 
            flag=true; return true
        } else if ( flag && it.contains('type')){ 
            type=kv[1]; return true
        } else if (type != ''){
            lss.each { cmp->
                if (cmp['type']==type){
                    cmp['version']=kv[1];type = ''
                }
            }
        } else { map[kv[0]]=kv[1]}
    }
    return lss
}

def getSolution(String refvar ){
    println "enter getSolution()=========================="
   def ref='OKNG'
   def script="./run.sh ${ref}"
   def out="ssh hongqizhang@localhost ${script}".execute().text
   println "out=$out"
   return """def script="./run.sh \${${refvar}}"
      |def out="ssh hongqizhang@localhost \${script}".execute().text
      |return \"\"\" <textarea name=\"value\"  value  class=\"setting-input  \" type=\"text\" rows="8" cols="25">\${out}</textarea> \"\"\"
      | """.stripMargin()
}

def getContent(String refvar ){
   def url="https://raw.githubusercontent.com/hqzhang"
   def urlext=""
   def wksp=getWksp()
    def repo=getRepo()
    def brch=getBranch()
   return """def wksp=\"${wksp}\"
      |def url=\"${url}\"
      |def urlext=\"${urlext}\"
      |//def map="curl -k \${url}/${repo}/${brch}/release/\${${refvar}}.yaml\$urlext".execute().text
      |def map="cat \${wksp}/release/\${${refvar}}.yaml".execute().text 
      |map=map.replaceAll('components:\\n','')
      |return \"\"\" <textarea name=\"value\"  value  class=\"setting-input  \" type=\"text\" rows="8" cols="25">\${map}</textarea> \"\"\"
      | """.stripMargin()
}
def getContentSimple(String refvar ){
    def wksp=getWksp()
    def repo=getRepo()
    def brch=getBranch()
    def url="https://raw.githubusercontent.com/hqzhang"
    def urlext=""
    return """
      |def out="cat ${wksp}/release/\${${refvar}}.yaml".execute().text
      |if (out.isEmpty()) { out="curl -k ${url}/${repo}/${brch}/release/\${${refvar}}.yaml${urlext}".execute().text }
      |out=out.replaceAll('components:\\n','')
      |return \"\"\" <textarea name=\"value\"  value  class=\"setting-input  \" type=\"text\" rows="10" cols="25">\${out}</textarea> \"\"\"
      | """.stripMargin()
}
def getContentTest(String refvar ){
    println "enter getContentTest()........."
   return """
   |return getContentSimpleTest(${refvar})
   | """.stripMargin()
}
def getVerify(){
     println "enter getVerify()........."
    def refvar='solution'
    return getContentSimpleTest(refvar)
}
def getContentSimpleVerify(){
    println "enter getContentSimpleverify()================74582375270=================="
    def SolutionConfig='solution'
    def out="cat /var/root/.jenkins/workspace/acgroovytest/release/${SolutionConfig}.yaml".execute().text
    if (out.isEmpty()) { out="curl -k https://bitbucket.org/wave-cloud/groovytest/raw/mymenuchange/release/${SolutionConfig}.yaml".execute().text }
    out=out.replaceAll('components:\n','')
    return """ <textarea name="value"  value  class="setting-input  " type="text" rows="10" cols="25">${out}</textarea> """
}
def getContentTableVerify(){
def wksp="/var/root/.jenkins/workspace/agroovytest"
def url="https://raw.githubusercontent.com/hqzhang"
def yaml = new Yaml()
def ret=''
//ret="curl -k ${url}/groovytest/mymenu/release/${Config}.yaml".execute().text
ret="cat ${wksp}/release/${Config}.yaml".execute().text
ret=(Map)yaml.load(ret)
mymap=ret['components']
def rendered = "<table><tr>"
mymap.each { mark="-"; 
 it.each { kk,vv->
  if ( kk != "name") {  mark="&nbsp;&nbsp;" }
  rendered = """${rendered}<tr>
  <td><input name="value" alt="${kk}" json="${kk}" type="checkbox" style="opacity:0" class=" " checked>
  <span>${mark}&nbsp;</span>
  <label name="value" class=" " value="${kk}">${kk}</label></td>
  <td><input  type="text" class=" " name="value" value="${vv}"> </br> </td></tr> """    } }
return "${rendered}</tr></table>"
}

def getContentTable(String refvar){
    println "enter getContent99=================================="
    def url="https://raw.githubusercontent.com/hqzhang"
    def urlext=""
    def wksp=getWksp()
    def repo=getRepo()
    def brch=getBranch()
    return """import org.yaml.snakeyaml.Yaml
    |def wksp=\"${wksp}\"
    |def url=\"${url}\"
    |def yaml = new Yaml()
    |def ret=''
    |//ret="curl -k \${url}/${repo}/${brch}/release/\${${refvar}}${urlext}".execute().text
    |ret="cat \${wksp}/release/\${${refvar}}".execute().text
    |ret=(Map)yaml.load(ret)
    |mymap=ret['components']
    |def rendered = "<table><tr>"
    |mymap.each { mark="-"; 
    | it.each { kk,vv->
    |  if ( kk != "name") {  mark="&nbsp;&nbsp;" }
    |  rendered = \"\"\"\${rendered}<tr>
    |  <td><input name=\"value\" alt=\"\${kk}\" json=\"\${kk}\" type=\"checkbox\" style=\"opacity:0\" class=\" \" checked>
    |  <span>\${mark}&nbsp;</span>
    |  <label name=\"value\" class=\" \" value=\"\${kk}\">\${kk}</label></td>
    |  <td><input  type=\"text\" class=\" \" name=\"value\" value=\"\${vv}\"> </br> </td></tr> \"\"\"    } }
    |return "\${rendered}</tr></table>"
    |""".stripMargin()
}
def getRepo(){
    def repo=scm.getUserRemoteConfigs().toString()
    repo=repo.substring(17, repo.length()-12).split('/')[2]
    return  repo
}
def getJob(){
    def jobstr = Thread.currentThread().toString().substring(38,).split('/')[0]
    return jobstr
}
def getWksp(){
    def jobstr=getJob()
    def scriptDir = getClass().protectionDomain.codeSource.location.path
    def index=scriptDir.indexOf('jobs')
     scriptDir.substring(1,index)
    def wksp="/var/root/.jenkins/workspace/${jobstr}"
    return wksp
}
def stringParse(String str){
    def data=str.split(',')
    def len= data.size()/2
    def ret=[:]
    def lss=[]
    def map=[:]
    for (int i = 0; i < len; i++) {
        if ( data[2*i] != data[0] ){
            map[ data[2*i]] = data[2*i+1]
        }else{
            if ( i != 0 ){ 
               lss.add(map)
               map=[:]  
            }
            map[ data[2*i]] = data[2*i+1]
        }
    }
    lss.add(map)
    println "lss=$lss"
    return [ components: lss ]
}

def getFileList(String dft){
    println "enter getFileList().."
    def wksp=getWksp()
    def mf ="ls ${wksp}/release  ".execute().text
    println "mf1=$mf"
    def out=mf.readLines().collect{  it.split("\\.")[0] } 
    println "out2=$out"
    def index=0
    if (out.isEmpty()){
        return [ dft ]
    }
    out.eachWithIndex{ it, id-> 
        if ( it.contains(dft) ){ index=id }
    }
    out.add(0, out.remove(index))
    println "out3=$out"
    return out 
}
def getFile(String mypath,String type, String dft){
  println("enter test()")
  def out="ssh root@192.168.0.16 ls ${mypath}/*.${type}".execute().text
  out=out.readLines().collect{ it.split("/")[-1] }
  out.eachWithIndex{ it, id-> if ( it.contains(dft) ){ index=id } }
  out.add(0, out.remove(index))
  return out
}
def getFileList88(String dft){
    def wksp=getWksp()
    def mf ="ls ${wksp}/release  ".execute().text
    def out=mf.readLines().collect{ it.split("\\.")[0] }
    return out
}

def getFileDft(String dft){
    def wksp=getWksp()
   return """def wksp=\"${wksp}\"
      |def mf ="ls \${wksp}/release  ".execute().text
      |def out=mf.readLines().collect{ 
      |    if(it.contains(\"$dft\") ){ '"'+it.split("\\\\.")[0]+':selected"' } 
      |    else { '"'+it.split("\\\\.")[0]+'"' } }
      |return \"\"\"return \$out \"\"\"
      | """.stripMargin()
}

def readYamlString(String str){
    Yaml yaml = new Yaml()
    return  (Map) yaml.load(str)
}
def readYamlFile(String fileName){
    Yaml yaml = new Yaml()
    String fileConts = new File(fileName).text
    return  (Map) yaml.load(fileConts)
}

def writeYamlFile(output,data){
    println("INPUT:"+data)
   
    DumperOptions options = new DumperOptions()
    options.setPrettyFlow(true)
    options.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK)
    yaml = new Yaml(options)
    yaml.dump(data, new FileWriter(output)) 
}
/*
def getComponentsInfo(String fileName){
    println("Enter getComponentList() ")
    String fileConts = new File(fileName).text
    println ("-----------parsing-------")
    def myyaml=new groovy.yaml.YamlSlurper()
    def map=myyaml.parseText(fileConts)['components']
    def names=[]
    def types=[]
    def vers=[]
    map.each { comp ->
       println (comp)
       comp.each{ k, v-> 
          if (v instanceof Map){
            v.each{ kk,vv->
               if(k=='configurations' && kk=='Path'){ 
                  def tmp=vv.split("/")
                  types.add(tmp[1])
                  vers.add(tmp[2])
                }
               
            }
          } else {
             if (k=='name'){ names.add(v)}
          }
       }
      
    }
    map=[:]
    map['names']=names
    map['types']=types
    map['vers']=vers
    println ("-----------parsing-------")
    return map
}*/
