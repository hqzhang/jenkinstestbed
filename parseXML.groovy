#!/usr/bin/env groovy
//import groovy.yaml.YamlSlurper
import groovy.xml.StreamingMarkupBuilder
import groovy.xml.XmlUtil

///1///
def addNode1(mynode) {
    println("Enter addNode()**11111** type: "+mynode )
    def propName=mynode.category.property.'@name'
    def propValue=mynode.categroy.property.'@value'
    def fileName = mynode.filename.text()
    fileName = "${fileName}"

    def categoryName = mynode.category.'@name'
    def node = mynode.category.property
    def xml = new XmlSlurper().parse(fileName)

    xml.category.each {
        if (it.@name==categoryName ) {
          println it.@name
          def res=it.children().findAll { it.@name == propName  }
          if (res.isEmpty() ) {println "NOT find it"} else {println "Find it"}
          //it.appendNode(node)
        }
    }

    println groovy.xml.XmlUtil.serialize(xml)
    def writer = new FileWriter(fileName)
    XmlUtil.serialize(xml, writer)

}
def addNode(mynode) {
    println("Enter addNode()**11111** type: "+mynode )

    def fileName = mynode.filename.text()
    println "fileName=$fileName"
    def propName=mynode.category.property.'@name'
    println "propName=$propName"
   
    fileName = "${fileName}"
   
    def categoryName = mynode.category.'@name'
    def node = mynode.category.property
    def xml = new XmlSlurper().parse(fileName)

    xml.category.each {
        if (it.@name==categoryName ) {
          println it.@name
          it.children().findAll { it.@name == propName }.replaceNode {}
          it.appendNode(node)
        }
    }

    println groovy.xml.XmlUtil.serialize(xml)
    def writer = new FileWriter(fileName)
    XmlUtil.serialize(xml, writer)
    
}
def configUpdate(fileManifest){
    println "Enter configUpdate() ******111****$fileManifest"
    def result = [:]
    def mylist =[]
    def rootNode = new XmlSlurper().parse(fileManifest)
    def  i=0
    def  mynd = rootNode.children().findAll { it.name() == "Config_Update" }
    def ENV='DEV2'
    mynd.children().each {
        def ss=it.name()
        println "-----------ENV=$ENV vs $ss"
        
           if(ss == 'Prod' ){
               if ( ENV == 'Prod' ){
                  println "addNode to $ss" 
                  addNode(it)
               }
           } else {
               if (ss =='Nonprod' || ss == ENV){
                  println "addNode to $ss"
                  addNode(it)
               }
           }
    }
}

def configUpdate1(fileManifest){
    println "Enter configUpdate() ******111****$fileManifest"
    def result = [:]
    def mylist =[] 
    def rootNode = new XmlSlurper().parse(fileManifest) 
    def subNode = rootNode.children().findAll { it.name() == "HABS" }
    subNode.children().each {
        println "======="
        println it.name()
        println  "-----"
        println it.text()
    }
}
def getValue(fileManifest, section, key){
    println "Enter configUpdate() ******111****$fileManifest"
    def result
    def rootNode = new XmlSlurper().parse(fileManifest)
    def subNode = rootNode.children().findAll { it.name() == section }
    subNode.children().each {
        if ( it.name() == key) {
            result = it.text()
        }
   }
   return result
  
}

println getValue("manifest_Lynx.xml",'HABS','action')
