
import org.yaml.snakeyaml.Yaml
import org.yaml.snakeyaml.DumperOptions
String buildScript(List values){
    def ret=values.collect { '"'+it+'"' }
    return "return ${ret}"
}

String convertScript( String str){
    def ret='"""'+str.replaceAll('"', '\\\\"')+'"""'
    return  "return $ret"
}

def getFileContent(String SolutionDetail ){
    def mf ="ls /Users/hongqizhang/workspace/ansibletest/releases  ".execute().text
    def myls = mf.readLines().collect{ it.split()[0].minus('.xml')}
    def map=[:]
    myls.each { file->
            my_tag="curl -k https://raw.githubusercontent.com/hqzhang/ansibletest/main/releases/${file}.xml".execute().text 
            map[ file]= my_tag
    }
    my_tag=map[SolutionDetail]
    def ret= "<textarea name=\"value\"  value  class=\"setting-input  \" type=\"text\">${my_tag}</textarea>"
    convertScript(ret)
}

def getContent(String refvar ,String jobstr, String repo ,String brch){
   def wksp="/Users/hongqizhang/.jenkins/workspace"
   def url="https://raw.githubusercontent.com/hqzhang"
   def urlext=""
   return """def wksp=\"${wksp}\"
      |def url=\"${url}\"
      |def urlext=\"${urlext}\"
      |def mf ="ls \${wksp}/${jobstr}/releases  ".execute().text
      |def out=mf.readLines().collect{ it.split("\\\\.")[0]}
      |def map=[:]
      |out.each { map[it]="curl -k \${url}/${repo}/${brch}/releases/\${it}.yaml\$urlext".execute().text
      |if ( map[it].contains('404: Not Found')){ map[it]="cat \${wksp}/${jobstr}/releases/\${it}.yaml".execute().text } }
      |return \"\"\" <textarea name=\"value\"  value  class=\"setting-input  \" type=\"text\" rows="8" cols="40">\${map[${refvar}]}</textarea> \"\"\"
      | """.stripMargin()
}
def getContent100(String refvar ,String jobstr, String repo ,String brch){
    println "enter getContent99=================================="
    def wksp="/Users/hongqizhang/.jenkins/workspace"
    def url="https://raw.githubusercontent.com/hqzhang"
    def urlext=""
    return """import org.yaml.snakeyaml.Yaml
    |def wksp=\"${wksp}\"
    |def url=\"${url}\"
    |def yaml = new Yaml()
    |def mf ="ls \${wksp}/${jobstr}/releases  ".execute().text
    |def out=mf.readLines().collect{ it.split("\\\\.")[0]}
    |def map=[:]
    |out.each { map[it]="curl -k \${url}/${repo}/${brch}/releases/\${it}.yaml${urlext}".execute().text
    |if ( map[it].contains('404: Not Found')){ map[it]="cat \${wksp}/${jobstr}/releases/\${it}.yaml".execute().text } 
    |map[it]=(Map)yaml.load(map[it]) }
    |mymap=map[${refvar}]['components']
    |def rendered = "<table><tr>"
    |mymap.each { it.each { k,v->
    |rendered = \"\"\"\${rendered}<tr><td><input name=\"value\" alt=\"\${k}\" json=\"\${k}\" type=\"checkbox\" class=\" \">
    |<label title=\"\${k}\" class=\" \">\${k}</label></td>
    |<td><input type=\"text\" class=\" \" name=\"value\" value=\"\${v}\"> </br></td></tr>\"\"\"  } }
    |return "\${rendered}</tr></table>"
    |""".stripMargin()
}

def getFileList(String dft, String jobstr ){
    def wksp="/Users/hongqizhang/.jenkins/workspace/$jobstr"
    def mf ="ssh hongqizhang@localhost ls ${wksp}/releases  ".execute().text
    def out=mf.readLines().collect{  it.split("\\.")[0] } 
    def index=0
    out.eachWithIndex{ it, id-> 
        if ( it.contains(dft) ){ index=id }
    }
    out.add(0, out.remove(index))
    return out 
}
def getFileList88(String dft){
    def wksp="/Users/hongqizhang/.jenkins/workspace/agroovytest"
    def mf ="ls ${wksp}/releases  ".execute().text
    def out=mf.readLines().collect{ it.split("\\.")[0] }

    return out
}
def getFileDft(String dft ){
   def wksp="/Users/hongqizhang/.jenkins/workspace/agroovytest"
   return """def wksp=\"${wksp}\"
      |def mf ="ls \${wksp}/releases  ".execute().text
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
println "==========="
println getFileDefault('solution')
println "==========="