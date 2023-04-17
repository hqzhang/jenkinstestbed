
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

def getContent(String SolutionDetail ){
   def wksp="/Users/hongqizhang/.jenkins/workspace/agroovytest"
   def url="https://raw.githubusercontent.com/hqzhang/groovytest/master"
   def urlext=""
   return """def wksp=\"${wksp}\"
      |def url=\"${url}\"
      |def urlext=\"${urlext}\"
      |def mf ="ls ${wksp}/releases  ".execute().text
      |def out=mf.readLines().collect{ it.split("\\\\.")[0]}
      |def map=[:]
      |out.each { map[it]="curl -k \${url}/releases/\${it}.yaml\${urlext}".execute().text
      |if ( map[it].contains('404: Not Found')){ map[it]="cat \${wksp}/releases/\${it}.yaml".execute().text } }
      |return \"\"\" <textarea name=\"value\"  value  class=\"setting-input  \" type=\"text\">\${map[SolutionDetail]}</textarea> \"\"\"
      | """.stripMargin()
}

def getContent1(String SolutionDetail ){
   return '''
      def wksp="/Users/hongqizhang/.jenkins/workspace/agroovytest"
      def url="https://raw.githubusercontent.com/hqzhang/groovytest/master"
      def mf ="ls ${wksp}/releases  ".execute().text
      def myls = mf.readLines().collect{ it.split()[0].minus('.xml')}
      def map=[:]
      myls.each { map[it]="curl -k ${url}/releases/\${it}.xml".execute().text }  
      return """ <textarea name="value"  value  class="setting-input  " type="text">\${map[SolutionDetail]}</textarea> """
      '''
}
def getFileDefault(String dft ){
    def test=''
    def mf ="ls /Users/hongqizhang/workspace/ansibletest/releases  ".execute().text
    def out=mf.readLines().collect{ it.split()[0].minus('.xml') }
    def ret=[]
    println out
    out.each {  
        if ( it.contains(dft) ) { 
            out.remove(it)
            out.add(it+':selected') 
        }
    }
    buildScript(out)
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