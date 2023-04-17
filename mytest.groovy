#!/usr/bin/env groovy
    import groovy.json.JsonSlurper
    import groovy.json.JsonOutput
    import groovy.yaml.YamlSlurper
    import mylib
    import sayHello
def exeCmd(String cmd){
    def proc=cmd.execute()
    proc.waitFor()
    def out=proc.in.text
    def err=proc.err.text
    def code=proc.exitValue()
    println("err:$err")
    println ("code=$code")
    return out
}
 def getFileContent1(String SolutionDetail){
    def wksp="/Users/hongqizhang/workspace/groovytest"
    def url="https://raw.githubusercontent.com/hqzhang/groovytest/master"
    def mf ="ls ${wksp}/releases  ".execute().text
    def out=mf.readLines().collect{ it.split("\\.")[0]}
    def map=[:]
    out.each { 
        map[it]="curl -k ${url}/releases/${it}.xml".execute().text
        if ( map[it].contains('404: Not Found')){
          map[it]="cat ${wksp}/releases/${it}.xml".execute().text 
        }
    }
    def ret= "<textarea name=\"value\"  value  class=\"setting-input  \" type=\"text\">${map[SolutionDetail]}</textarea>"
    return ret
}
//println getFileContent1('config')
//println getFileContent1('solution')
//println getFileContent1('backup')

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
   return '''
      def wksp="/Users/hongqizhang/workspace/ansibletest"
      def url="https://raw.githubusercontent.com/hqzhang/ansibletest/main"
      def mf ="ls ${wksp}/releases  ".execute().text
      def myls = mf.readLines().collect{ it.split()[0].minus('.xml')}
      def map=[:]
      myls.each { map[it]="curl -k ${url}/releases/\${it}.xml".execute().text }  
      return """ <textarea name="value"  value  class="setting-input  " type="text">\${map[SolutionDetail]}</textarea> """
      '''
}
def getContent11(String SolutionDetail ){
  return '''    def wksp="/Users/hongqizhang/workspace/groovytest"
    def url="https://raw.githubusercontent.com/hqzhang/groovytest/master"
    def mf ="ls ${wksp}/releases  ".execute().text
    def out=mf.readLines().collect{ it.split("\\\\.")[0]}
    def map=[:]
    out.each { map[it]="curl -k ${url}/releases/${it}.yaml".execute().text
        if ( map[it].contains('404: Not Found')){ map[it]="cat ${wksp}/releases/${it}.yaml".execute().text } }
    return """ <textarea name="value"  value  class="setting-input  " type="text">\${map[SolutionDetail]}</textarea> """
    '''
}
println "============"
println getContent1('SolutionDetail')
println getContent11('SolutionDetail')
println "============="
SolutionDetail='backup'
def wksp="/Users/hongqizhang/.jenkins/workspace/agroovytest"
    def url="https://raw.githubusercontent.com/hqzhang/groovytest/master"
    def mf ="ls ${wksp}/releases  ".execute().text
    def out=mf.readLines().collect{ it.split("\\.")[0]}
    println out
    def map=[:]
    out.each { map[it]="curl -k ${url}/releases/${it}.yaml".execute().text
        println "curl -k ${url}/releases/${it}.yaml"
        if ( map[it].contains('404: Not Found')){ println "EEEEE"
          map[it]="cat ${wksp}/releases/${it}.yaml".execute().text } 
          println map[it]
          
          }
    println map
    println """ <textarea name="value"  value  class="setting-input  " type="text">${map[SolutionDetail]}</textarea> """
    


System.exit(1)
String buildQuote(List values){
      List mytmp = []
      values.each { mytmp.add('"\\"'+it+'\\""') }
      return mytmp
    }
@groovy.transform.Field
def serversList=['DEV': ['APP': ['s1','s22'],
                      'DB':['s3']],
              'BAT': ['APP': ['s3','s55'],
                      'DB':['s6']],  ]
List getCompTypes(Map myList=serversList ){
   List tmp = []
   myList.each {
      tmp.add(it.key)
   }
   return tmp
}
println getCompTypes()

def getServer(String env){
      def map=[:]
      def lis=[]
      def myList=getCompTypes()
    
      serversList.each { key,val->
            map[key]= buildQuote(val['APP'])
      }
      println "map=$map"
      println "${map[env]}"
      def str=map.toString()
      def item=map[env]
      return """
      |def map=${map}
      |def ret=map[env]
      |return "\${ret}"
      | """.stripMargin()
}

println getServer('dev')
def env='DEV'
 map=[DEV:["\"s1\"", "\"s22\""], BAT:["\"s3\"", "\"s55\""]]
println map
def ret=map[env]
println ret
println "${ret}"



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
