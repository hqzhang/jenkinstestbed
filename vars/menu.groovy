
import org.yaml.snakeyaml.Yaml
import org.yaml.snakeyaml.DumperOptions
import jenkins.model.Jenkins
import hudson.*
import hudson.model.*
import jenkins.*
//import groovy.yaml.YamlSlurper
//// Remove everything which is currently queued/
def getPackList(String fileName){
    println "enter getPackList()================="
    def out="ssh root@192.168.0.16 ls /root/workspace/myscripts/*.tar.gz".execute().text
    return out.readLines().collect{ it.split("/")[-1] }
}

def getCompList2(String pack){
    def mykey=pack
    println "enter getCompList()================="
    def out="ssh root@192.168.0.16 /root/workspace/myscripts/run.sh".execute().text
    println "out=$out"
    out=out.readLines()
    println "out=$out"
    def map=[:]
    def lss=[]
    def key=''
    out.each{ var->
       if (var.contains(".tar.gz") ){
          if ( ! key.isEmpty()){
            map[key]=lss
            key=var.split("\\.")[0]
            key=var
            lss=[]
          } else { key=var.split("\\.")[0]
          key=var
           }
       } else { lss.add(var) }
    }
    map[key]=lss
    println "map=$map"
    println "$mykey=${map[mykey]}"
    return map[mykey]
}

def getCompList(String mypack){
    println "enter getCompList()"
    def mykey=mypack.split("\\.")[0] 
    def map=[:]
    def lss=[]
    def key=''
    def out="ssh root@192.168.0.16 /root/workspace/myscripts/run.sh".execute().text.readLines()
    out.each{ if (it.contains(".tar.gz") ){
    if ( ! key.isEmpty()){ map[key]=lss; key=it.split("\\.")[0]; lss=[] } else { key=it.split("\\.")[0] } }
    else { lss.add('"'+it+'"') } } ; map[key]=lss
    return """def key='' 
    |def map=[:]
    |def lss=[]
    |def mykey=${mypack}.split("\\.")[0] 
    |def out="ssh root@192.168.0.16 /root/workspace/myscripts/run.sh".execute().text.readLines()
    |out.each{ if (it.contains(".tar.gz") ){
    |if ( ! key.isEmpty()){ map[key]=lss; key=it.split("\\.")[0]; lss=[] } else { key=it.split("\\.")[0] } }
    |else { lss.add('"'+it+'"') } } ; map[key]=lss
    |println map
    |return map[mykey]
    | """.stripMargin()
}
def gitCompVerify(){
def mypack='file.tar.gz'
def key='' 
def map=[:]
def lss=[]
def mykey=mypack.split("\.")[0] 
def out="ssh root@192.168.0.16 /root/workspace/myscripts/run.sh".execute().text.readLines()
out.each{ if (it.contains(".tar.gz") ){
if ( ! key.isEmpty()){ map[key]=lss; key=it.split("\.")[0]; lss=[] } else { key=it.split("\.")[0] } }
else { lss.add('"'+it+'"') } } ; map[key]=lss
println map
return map[mykey]
}
def checkBuildRunning(){
    //Jenkins.instance.queue.clear()
    def buildingJobs = Jenkins.instance.getAllItems(Job.class).findAll {
        it.isBuilding()
    }
    buildingJobs.each {
        def jobName = it.toString()
        def val = jobName.split("\\[|\\]")

        // 'Abort jobs' is the name of the job I have created, and I do not want it to abort itself.

        if((val[1].trim())!='Abort jobs') {
            def job = Jenkins.instance.getItemByFullName(val[1].trim())
            for (build in job.builds) {
                if (build.isBuilding()) {
                    println(build)
                    //build.doStop();
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

def getFileContent(String SolutionDetail,String wksp ){
    def url="https://raw.githubusercontent.com/hqzhang/ansibletest"
    def mf ="ls ${wksp}/release  ".execute().text
    def myls = mf.readLines().collect{ it.split()[0].minus('.xml')}
    def map=[:]
    myls.each { file->
            my_tag="curl -k https://raw.githubusercontent.com/hqzhang/ansibletest/main/release/${file}.xml".execute().text 
            map[ file]= my_tag
    }
    my_tag=map[SolutionDetail]
    def ret= "<textarea name=\"value\"  value  class=\"setting-input  \" type=\"text\">${my_tag}</textarea>"
    convertScript(ret)
}

def getContent(String refvar ,String wksp, String repo ,String brch){
   def url="https://raw.githubusercontent.com/hqzhang"
   def urlext=""
   return """def wksp=\"${wksp}\"
      |def url=\"${url}\"
      |def urlext=\"${urlext}\"
      |def mf ="ls \${wksp}/release  ".execute().text
      |def out=mf.readLines().collect{ it.split("\\\\.")[0]}
      |def map=[:]
      |out.each { map[it]="curl -k \${url}/${repo}/${brch}/release/\${it}.yaml\$urlext".execute().text
      |if ( map[it].contains('404: Not Found')){ map[it]="cat \${wksp}/release/\${it}.yaml".execute().text } }
      |return \"\"\" <textarea name=\"value\"  value  class=\"setting-input  \" type=\"text\" rows="8" cols="40">\${map[${refvar}]}</textarea> \"\"\"
      | """.stripMargin()
}
//////////////////////////

def getContent100(String refvar, String wksp, String repo ,String brch){
    println "enter getContent99=================================="
    def url="https://raw.githubusercontent.com/hqzhang"
    def urlext=""
    return """import org.yaml.snakeyaml.Yaml
    |def wksp=\"${wksp}\"
    |def url=\"${url}\"
    |def yaml = new Yaml()
    |def mf ="ls \${wksp}/release  ".execute().text
    |def out=mf.readLines().collect{ it.split("\\\\.")[0]}
    |def map=[:]
    |out.each { map[it]="curl -k \${url}/${repo}/${brch}/release/\${it}.yaml${urlext}".execute().text
    |if ( map[it].contains('404: Not Found')){ map[it]="cat \${wksp}/release/\${it}.yaml".execute().text } 
    |map[it]=(Map)yaml.load(map[it]) }
    |mymap=map[${refvar}]['components']
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
    println "map=$map"
    lss.add(map)
    println "lss=$lss"
    
    return [ components: lss ]
}

def getFileList(String dft, String wksp ){
    println "enter getFileList:ls ${wksp}/release "
    def mf ="ls ${wksp}/release  ".execute().text
    println "mf=$mf"
    def out=mf.readLines().collect{  it.split("\\.")[0] } 
    println "out=$out"
    def index=0
    if (out.isEmpty()){
        return [ dft ]
    }
    out.eachWithIndex{ it, id-> 
        if ( it.contains(dft) ){ index=id }
    }
    out.add(0, out.remove(index))
    return out 
}
def getFileList88(String dft,String wksp){
    def mf ="ls ${wksp}/release  ".execute().text
    def out=mf.readLines().collect{ it.split("\\.")[0] }

    return out
}
def getFileDft(String dft, String wksp){
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