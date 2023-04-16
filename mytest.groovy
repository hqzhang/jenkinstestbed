#!/usr/bin/env groovy
    import groovy.json.JsonSlurper
    import groovy.json.JsonOutput
    import groovy.yaml.YamlSlurper
    import mylib
    import sayHello
  
    String mybuildScript(List values){
       def ret=values.collect { '"'+it+'"' }
    return "return ${ret}"
    }
/*
    String convertScript( String str){
      def ret='"""'+str.replaceAll('"', '\\\\"')+'"""'
      return  """
      map=menu.getFileMap()
      con=map[SolutionDetail]
      return $ret
      """
    }*/

def getFileMap(){
    def mf ="ls /Users/hongqizhang/workspace/ansibletest/releases  ".execute().text
    def myls = mf.readLines().collect{ it.split()[0].minus('.xml')}
    def map=[:]
    myls.each { map[it]="curl -k https://raw.githubusercontent.com/hqzhang/ansibletest/main/releases/${file}.xml".execute().text 
    }
    return map
}


def getFileList(){
    def test=''
    def mf ="ls /Users/hongqizhang/workspace/ansibletest/releases  ".execute().text
    def out=mf.readLines().collect{ it.split()[0].minus('.xml')}
    mybuildScript(out)
}
SolutionDetail='config'

String buildReturn( String str ){
    def ret= "<textarea name=\"value\"  value  class=\"setting-input  \" type=\"text\">${str}</textarea>"
    ret='"""'+ret.replaceAll('"', '\\\\"')+'"""'
    return  "return $ret"
}
def getFileContent(String refvar ){
        def wksp="/Users/hongqizhang/.jenkins/workspace/agroovytest"
        def url="https://raw.githubusercontent.com/hqzhang/ansibletest/main"
        def mf ="ls ${wksp}/releases  ".execute().text
        def myls = mf.readLines().collect{ it.split()[0].minus('.xml')}
        def ret=''
        myls.each{
            str="curl -k ${url}/releases/${it}.xml".execute().text
            myStr ="if(${refvar}.equals('$it')){ ${buildReturn(str)} }"+"\n"
            if ( !ret?.trim() ) {  ret += myStr }
            else { ret += "else "+myStr }
        }
        ret += """else{ ${buildReturn("N/A")} }"""
        return ret
}
def getContent1(String SolutionDetail ){
    
        def wksp="/Users/hongqizhang/workspace/ansibletest"
        def url="https://raw.githubusercontent.com/hqzhang/ansibletest/main"
        mf ="ls ${wksp}/releases  ".execute().text
        myls = mf.readLines().collect{ it.split()[0].minus('.xml')}
        map=[:]
        myls.each { map[it]="curl -k ${url}/releases/${it}.xml".execute().text }       
        ret= "<textarea name=\"value\"  value  class=\"setting-input  \" type=\"text\">${map[SolutionDetail]}</textarea>"
        return  '"""'+ret.replaceAll('"', '\\\\"')+'"""'
      
}
def getContent(String SolutionDetail ){
   return """def wksp="/Users/hongqizhang/workspace/ansibletest"
      |def url="https://raw.githubusercontent.com/hqzhang/ansibletest/main"
      |def mf ="ls \${wksp}/releases  ".execute().text
      |def myls = mf.readLines().collect{ it.split()[0].minus('.xml')}
      |def map=[:]
      |myls.each { map[it]="curl -k \${url}/releases/\${it}.xml".execute().text }  
      |return \"\"\" <textarea name=\"value\"  value  class=\"setting-input  \" type=\"text\">\${map[SolutionDetail]}</textarea> \"\"\"
      | """.stripMargin()
}
println "============"
println getContent('SolutionDetail')
println "============="
def refvar='config'
def wksp="/Users/hongqizhang/.jenkins/workspace/agroovytest"
def url="https://raw.githubusercontent.com/hqzhang/ansibletest/main"
def mf ="ls ${wksp}/releases  ".execute().text
def myls = mf.readLines().collect{ it.split()[0].minus('.xml')}
def map=[:]
myls.each { map[it]="curl -k https://raw.githubusercontent.com/hqzhang/ansibletest/main/releases/${it}.xml".execute().text }
println """ "<textarea name="value"  value  class="setting-input  " type="text">${map[refvar]}</textarea>" """

println "end--------------"


  /*String buildScriptDefault(List values,String key){
        List tmp = []
        values.each{ 
            if ( it.contains(key) ) {
                tmp.add( it+":selected" )
            } else { tmp.add( it)}
        }

        buildScript(tmp)
    }s

    Types.eachWithIndex{ it,index->
            String myList = buildScriptDefault(srcMap[it], defaultList[index])
            String myStr ="if(${myenv}.equals('$it')){ $myList }"+"\n"
            if ( !ret?.trim() ) {  ret += myStr }
            else { ret += "else "+myStr }
    }
    ret += """else{  return ["N/A"] }"""
    println "$ret"*/

    System.exit(1)
    List myvar = ['abc','efd','xyz']
    List uvar = ['pqr','opq']

    String buildScript1(List values){
      List mytmp = []
      values.each { mytmp.add('"'+it+'"') }
      return "return ${mytmp}"
    }
    String buildScript(List values){
      def ret=values.collect { '"'+it+'"' }
      return "return ${ret}"
    }
        String buildScriptDefault(List values,String key){
        List tmp = []
        values.each{ 
            if ( it.contains(key) ) {
                tmp.add( it+":selected" )
            } else { tmp.add( it)}
        }

        buildScript(tmp)
    }

   String getEnvironment(String val='',List values=[]){
   if ( values.isEmpty() ) { values = myvar }
   if ( val?.trim() ) { 
      buildScriptDefault(values, val)
   } else {
      buildScript(values)
   }
}
    println getEnvironment('xyz',myvar)
    println buildScriptDefault('xyz')
    println myvar
    System.exit(1)
    def gettags = ("git ls-remote --refs --tags  git@github.com:hqzhang/octest.git  ").execute().text
    println gettag   println "------"
    def mid = gettags.readLines().collect { it.split()[1].replaceAll('refs/tags/', '') }
    println "=========="
    println buildScript(mid)
    gettags = ("git ls-remote --heads  git@github.com:hqzhang/octest.git  ").execute().text
    println gettags
    println "------"
    println gettags.readLines().collect { it.split()[1].replaceAll('refs/heads/', '') }
    gettags = ("git ls-remote --refs --tags  git@github.com:hqzhang/octest.git").execute().text
    mid=gettags.readLines().collect { it.split()[1].replaceAll('refs/tags/', '') }[2]
    println mid
