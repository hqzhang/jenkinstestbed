//import groovy.yaml.YamlSlurper
import groovy.xml.StreamingMarkupBuilder
import groovy.xml.XmlUtil

def addProperty(fileName, directoryName, propName, propValue) {
    println("enter *****0****addProperties=$fileName")
    def xmlStr = """<?xml version="1.0" encoding="UTF-8"?><properties extends="habs_base">
                <category name="general">
                       <property name="config.folder" value="habs"/>
                </category>
    </properties>""" 
    def xml = new XmlSlurper().parse(fileName)
    def node = new XmlSlurper().parseText('<property name="a" value="b"/>')
    println("enter *****1***")
    xml.category.each {
        if (it['@name']==directoryName ) {
          println it['@name']
          it.children().findAll { it['@name'] == propName }.replaceNode {}
        println("enter *****2***")  
        it.appendNode(node)
        }
    }
    println("enter *****2***=${xml}")
    println groovy.xml.XmlUtil.serialize(xml)
    println("enter *****3***")
    def writer = new FileWriter(fileName)
    println("enter *****4***")
    XmlUtil.serialize(xml, writer)
    return xml
}
@NonCPS
def addNode( mynode) {
    println("Enter addNode() type: "+mynode.getClass() )
    println "**********111******"
    def propName=mynode.category.property.'@name'
    def fileName = mynode.filename.text()
    def workspace = pwd()
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
//def result = [:]
@NonCPS
def configUpdate(fileManifest){
    def result = [:]
    def mylist =[] 
    def rootNode = new XmlSlurper().parse(fileManifest) 
    def  i=0
    rootNode.children().each {
        def st = it.name() 
        if (st == "config") {
          addNode(it)
        }
    }
    
}
@NonCPS
def readXMLList(fileManifest){
    def result = [:]
    def mylist =[]
    def rootNode = new XmlSlurper().parse(fileManifest)
    def  i=0
    rootNode.children().each {
        def st = it.name()
        mylist.add(st )
    }
    mylist.each { 
          //stage(it) {
                      echo "Element: $it"
         // }
    }
    return mylist
}

def readXMLSwitch(mylist,myfile){
    println "Enter ***************readXMLSwitch() "
    println "for loop......"
    //def i=0;
    mylist.each {
      stage(it){ 

       println "STAGE $it" 
        if(it=="config"){
          configUpdate(myfile)
        } //switch
      }//stage
    }//each
}//def
def readXMLSwitch(mylist,myfile){
    println "Enter ***************readXMLSwitch() "
    println "for loop......"
    //def i=0;
    mylist.each {
      stage(it){ 

          println "STAGE $it" 
          switch(it.name() ) {
          case 'patches':
              println "case DB PATHES"
              break
          case 'config':
              println "case CONFIG UPDATE"
              println "CALLaddnode"
              configUpdate(myfile)
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
      }
   }
}

//def list
pipeline {
    agent any
    stages {
        stage('Create List') {
            steps {
                script {
                    workspace=WORKSPACE
                    println "WS=${env.WORKSPACE}"
                    println "WS=${WORKSPACE}"
                    println "pwd="+pwd()
                    println "workspace=$workspace"
                    // you may create your list here, lets say reading from a file after checkout
                    //list = ["Test-1", "Test-2", "Test-3", "Test-4", "Test-5"]
                }
            }
        }
        stage('Dynamic Stages') {
            steps {
                script {
                    println env.WORKSPAC
                    println WORKSPACE
                    def workspace = pwd() 
                    println workspace
                    addProperty("${workspace}/manifest_Lynx.xml","general","A","B")
                    //def rootNode=readXMLRoot("${workspace}/manifest_Lynx.xml")
                    //println "rootNode=$rootNode"
                    // mylist = ["patches", "config", "Test-3"]
                    def mylist = readXMLList("${workspace}/manifest_Lynx.xml")
                    readXMLSwitch(mylist,"${workspace}/manifest_Lynx.xml")
                    /**
                    **/

                }
            }
        }
    }
}


