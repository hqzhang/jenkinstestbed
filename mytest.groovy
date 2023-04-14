#!/usr/bin/env groovy
    import groovy.json.JsonSlurper
    import groovy.json.JsonOutput
    import groovy.yaml.YamlSlurper
    import mylib
    import sayHello
    @groovy.transform.Field
    def comps="ABC,XYZ"
    def compList=comps.split(',')
    println(compList[0]+" is running")
    println( "${compList[0]} is running")
    cmd="curl -k https://raw.githubusercontent.com/hqzhang/ansibletest/main/solution.yaml"
    def resp = cmd.execute().text
    
    println resp

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
