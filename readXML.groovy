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
    def  i=0
    rootNode.children().each {
        def st = it.name()
        if (st == "Config_Update") {
          it.children().each { var->
              def ss=var.name()
              println ss
              if (ss == "Dev2" | ss == "nonprod" ) {
                  addNode(var)
             } 
          }
        }
    }
}

configUpdate("manifest_Lynx.xml")
