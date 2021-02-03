//import groovy.yaml.YamlSlurper
import groovy.xml.StreamingMarkupBuilder
import groovy.xml.XmlUtil

///1///
@NonCPS
def addNode(mynode) {
    println("Enter addNode() type: "+mynode.getClass() )

    def propName=mynode.category.property.'@name'
    def fileName = mynode.filename.text()
    def workspace = pwd()
    fileName = "${workspace}/${fileName}"
   
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
    println "Enter readXMLList"
    def result = [:]
    def mylist =[]
    def rootNode = new XmlSlurper().parse(fileManifest)
    def  i=0
    rootNode.children().each {
        def st = it.name()
        println st
        if( st == "release" ) { 
          mylist.add(st+":"+it.text() )
          println "***********${it.text() }" } else {
        mylist.add(st ) }
    }
    println "mylist=$mylist"
    return mylist
}

def readXMLSwitch(mylist,myfile){
    println "Enter readXMLSwitch() "
    
    mylist.each {
       stage(it){ 
          println "STAGE $it" 
          switch (it){
          case 'patches':
              println "case DB PATHES"
              break
          case 'config':
              println "case CONFIG UPDATE"
              configUpdate(myfile)
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
        } //switch
      }//stage
    }//each
}//def

  
def list
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
                    list = readXMLList("${workspace}/manifest_Lynx.xml")
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
                    //addProperty("${workspace}/manifest_Lynx.xml","general","A","B")
                    //def rootNode=readXMLRoot("${workspace}/manifest_Lynx.xml")
                    //println "rootNode=$rootNode"
                    // mylist = ["patches", "config", "Test-3"]
                    //def mylist = readXMLList("${workspace}/manifest_Lynx.xml")
                    readXMLSwitch(list,"${workspace}/manifest_Lynx.xml")
                    /**
                    **/

                }
            }
        }
    }
}


