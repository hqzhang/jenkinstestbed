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
    def recipes = new XmlSlurper().parse(fileManifest)
    println "for loop......"
    def i=0;
    println recipes.getClass()
    println recipes.name()
    println recipes.children()[0].name()
    println recipes.children()[1].name()
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
     println "exit $i"
  }
}*
}
def workspace
def list
pipeline {
    agent any
    stages {
        stage('Create List') {
            steps {
                script {
                    // you may create your list here, lets say reading from a file after checkout
                    list = ["Test-1", "Test-2", "Test-3", "Test-4", "Test-5"]
                }
            }
            post {
                cleanup {
                    cleanWs()
                }
            }
        }
        stage('Dynamic Stages') {
            steps {
                script {
                    for(int i=0; i < list.size(); i++) {
                        stage(list[i]){
                            echo "Element: $i"
                        }
                    }
                }
            }
            post {
                cleanup {
                    cleanWs()
                }
            }
        }
    }
}
