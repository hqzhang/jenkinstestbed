//import groovy.yaml.YamlSlurper
import groovy.xml.StreamingMarkupBuilder
import groovy.xml.XmlUtil
@NonCPS 
def addNode( mynode) {
    println("Enter addNode() type: "+mynode.getClass() )
    println "**********111******"

    def propName=mynode.category.property.'@name'

    def fileName = mynode.filename.text()
    fileName = "${workspace}/${fileName}"
    println "**********112******"
    println mynode.filename.getClass()
    println "**********113******"
    def categoryName = mynode.category.'@name'
    def node = mynode.category.property
    println "**********114******fileName=${fileName}"
    def xml = new XmlSlurper().parse(fileName)
    println "**********115******"
    xml.category.each {
        if (it.@name==categoryName ) {
          println it.@name
          it.children().findAll { it.@name == propName }.replaceNode {}
          it.appendNode(node)
        }
    }
    println "**********222******"
    println groovy.xml.XmlUtil.serialize(xml)
    println "**********223******"
    def writer = new FileWriter(fileName)
    println "**********224******"
    XmlUtil.serialize(xml, writer)
    println "**********333******"
}

def readXMLSwitch(fileManifest,workspace) {
    println "Enter readXMLSwitch() file:$fileManifest"
    def rootNode = new XmlSlurper().parse(fileManifest)
    println "for loop......"
    def i=0;
    println rootNode.getClass()
    println rootNode.name()
    println rootNode.children()[0].name()
    println rootNode.children()[1].name()
/*    rootNode.children().each {
      stage(it.toString()){
        
      /*switch(it.name() ) {
      case 'patches':
          println "case DB PATHES"
          break
      case 'config':
          println "case CONFIG UPDATE"
          println "CALLaddnode"
          //addNode(it)
          println "AFTERaddnode"
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
      
    }
     println "exit $i"
  }
}*/
}


def workspace

pipeline {

   agent any
   stages {
     stage("mytest") {
        steps{
           script {
           workspace=WORKSPACE
           //def result=readHabsRecipes("${workspace}/habs_recipes")
           readXMLSwitch("${workspace}/manifest_Lynx.xml",workspace)
}}}
}}



