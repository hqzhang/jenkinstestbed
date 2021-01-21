import groovy.xml.StreamingMarkupBuilder
import groovy.xml.XmlUtil
def workspace
@NonCPS
def addProperty(fileName, directoryName, propName, propValue) {
    println("enter *****0****addProperties=$fileName")
    def xml = new XmlSlurper().parse(fileName)
    def a="myname"
    def b="myvalue"
    def str="<property name=${a} value=${b}/>"
    println "str=${str}"
    def node = new XmlSlurper().parseText(str)
    println("enter *****1***")
    xml.category.each {
        if (it['@name']==directoryName ) {
          println it['@name']
          it.children().findAll { it['@name'] == propName }.replaceNode {}
          //it.appendNode  property(name: propName, value: propValue)  }
        println("enter *****2***")  
        it.appendNode(node)
        }
    }
/**    println("enter *****3***")
    def Writer = new StringWriter()
    println("enter *****3***")
    XmlUtil.serialize( xml, Writer )
    println("enter *****4***")
    writeFile file: fileName, text: Writer.toString()
    println("enter *****5***")
**/    println("enter *****2***=${xml}")
    println groovy.xml.XmlUtil.serialize(xml)
    println("enter *****3***")
    def writer = new FileWriter(fileName)
    println("enter *****4***")
    XmlUtil.serialize(xml, writer)
    // TRICKY: FileWriter does NOT work
    //def outWriter = new StringWriter()
    //XmlUtil.serialize( xml, outWriter )
    
    //writeFile file: fileName, text: outWriter.toString()

    println("enter *****6***")
}

def readHabsRecipes(recipeFile){
    File fh = new File(recipeFile)
    def lines = fh.readLines()
    def result = new LinkedHashMap([:])
    def prekey=''
    def key=''
    def val=''
    def flag=1
    def i=1
    lines.each{ line->
    if ( line.contains("[") && line.contains("]") ) {
        key=line.replace('[','').replace(']','')
        if( prekey != '' ) {
            def tmp=[:]
            tmp.sect=prekey
            tmp.cont=val
            result.put(i,tmp)
            prekey=key
            val=''
            i++
        }
    } else if ( line != '' ){ 
         val = val+line+"\n"
    }
    if (flag) {
         prekey=key 
         flag = 1
    }
 }
 if ( prekey != '' ) {
    def tmp=[:]
    tmp.sect=prekey 
    tmp.cont=val
    prekey=key
    result.put(i,tmp)
    val=''
    i++
 }
 return result
}

def dynamicStages( result ,workspace){
 println "enter dynamicStages()"
 result.each { mykey, myval->
   println("sect=${myval.sect}")
   switch(myval.sect) { 
      case 'DB PATHES': 
          println "case DB PATHES" 
          break
      case 'CONFIG UPDATE': 
          println "case CONFIG UPDATE"
          def fileName= myval.cont.split("\n")[0]
          def content= myval.cont.split().drop(1).join("\n")
          def xml = new XmlSlurper().parseText(content)
          def dirName=xml['@name']
          def propName=xml.property[0]['@name']
          def propValue=xml.property[0]['@value']
          println fileName
          println dirName
          println propName
          println propValue
          addProperty("${workspace}/${fileName}",dirName,propName,propValue)     
          break
      case 'HABS': 
          println "case HABS"
          break
      case 'SENS':
          println "case CONFIGUPDATEB"
          break
      case 'REPORTs':
          println "case CONFIGUPDATEB"
          break
      case 'SOA': 
          println "case soa"
          break
      default:
          println "case Default" 
          break
    }  
 }
}
pipeline {

   agent any
   stages {
     stage("mytest") {
        steps{
           script {
           workspace=WORKSPACE
           def result=readHabsRecipes("${workspace}/habs_recipes")
           dynamicStages(result,workspace)
}}}
}}


