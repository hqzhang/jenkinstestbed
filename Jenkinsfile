//import groovy.yaml.YamlSlurper
import groovy.xml.StreamingMarkupBuilder
import groovy.xml.XmlUtil
def mylist=[]
//def rootNode
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
def readXMLSwitch1(fileManifest){
    def result = [:]
    mylist = []
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
    println "Enter readXMLSwitch() file:$fileManifest"
    def recipes = new XmlSlurper().parse(fileManifest)
    println "for loop......"
    //def i=0;
    println recipes.getClass()
    println recipes.name()
    println recipes.children()[0].name()
    println recipes.children()[1].name()
    return rootNode
/*    rootNode.children().each {
      stage(it.toString()){
      switch(it.name() ) {
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
  }
}*/
}
def readXMLSwitch(fileManifest){
    println "Enter readXMLSwitch() file:$fileManifest"
    def recipes = new XmlSlurper().parse(fileManifest)
    println "for loop......"
    //def i=0;
    println recipes.getClass()
    println recipes.name()
    println recipes.children()[0].name()
    println recipes.children()[1].name()
    /*for(int i=0; i < list.size(); i++) {
                     //   stage(list[i]){
                            echo "Element: $i"
                     //   }
    }*/

    rootNode.children().each {
      stage(it.toString()){
      switch(it.name() ) {
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
            /**post {
                cleanup {
                    cleanWs()
                }
            }**/
        }
        stage('Dynamic Stages') {
            steps {
                script {
                    println env.WORKSPAC
                    println WORKSPACE
                    def workspace = pwd() 
                    println workspace
                    def rootNode=readXMLSwitch1("${workspace}/manifest_Lynx.xml")
                    println "rootNode=$rootNode"
                    /**
                    **/

                }
            }
            /**post {
                cleanup {
                    cleanWs()
                }
            }**/
        }
    }
}


