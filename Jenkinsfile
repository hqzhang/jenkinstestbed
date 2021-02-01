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

def readManifest(fileManifest) {
    println("enter addProperties() file:$fileManifest")
    def text = '''<list><technology>
                            <name>Groovy</name>
                      </technology></list>'''
    def list = new XmlSlurper().parseText(text) 
    println list.name()
    println list.technology.name
    println list.getClass()
    println "////////"
    list = new XmlParser().parseText(text) 
    println list.getClass()
    assert list instanceof groovy.util.Node 
    assert list.technology.name.text() 
    /////////     

    def rootNode = new XmlSlurper().parse(fileManifest)

    println rootNode.name()
    println rootNode.config.filename
    println rootNode.config.category.'@name'
    println rootNode.config.category.property.'@name'
    println "give you an example"
    assert rootNode.name() == 'recipes'
    assert rootNode.config.filename == 'habs_base_config.xml'
    assert rootNode.config.category.'@name' == 'general'
}

def readXMLSwitch(fileManifest,workspace) {
    println "Enter readXMLSwitch() file:$fileManifest"
    rootNode = new XmlSlurper().parse(fileManifest)
    println "for loop......"
    def i=0;
    rootNode.children().each {
        i++
        println "enter $i ${it.name()}" 
      switch(it.name() ) {
      case 'DB PATHES':
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
     println "exit $i ${it.name()}"
  }
}



def workspace

pipeline {

   agent any
   stages {
     stage("mytest") {
        steps{
           script {
           workspace=WORKSPACE
           def result=readHabsRecipes("${workspace}/habs_recipes")
           readXMLSwitch("${workspace}/manifest_Lynx.xml",workspace)
}}}
}}



