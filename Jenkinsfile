//////
import org.json.JSONObject;
import org.json.XML;
import org.json.JSONException;
////import java.nio.file.Files;
//import java.nio.file.Path;
library("my-shared-library@master") _
println cons.envList
println cons.getServers('Environment')
@NonCPS
def parseXML(xmlFile){
    echo "Enter parseXML()..."
    def PRETTY_PRINT_INDENT_FACTOR = 4;
    def mystr = ""
    new File(xmlFile).eachLine { mystr += it }
    JSONObject xmlJSONObj = XML.toJSONObject(mystr);

    String jsonPrettyPrintString = xmlJSONObj.toString(PRETTY_PRINT_INDENT_FACTOR);
    println(jsonPrettyPrintString);
  
    return jsonPrettyPrintString
}
//
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

  

properties([
    pipelineTriggers([githubPush()]),
    parameters([
           choice(name: 'Envir', choices: cons.envList, description: 'input cluster'),
           [$class: 'CascadeChoiceParameter', choiceType: 'PT_SINGLE_SELECT',
            description: 'Active Choices Reactive parameter',
            filterLength: 1, filterable: true,
            name: 'servers', randomName: 'choice-parameter-7601237141171',
            referencedParameters: 'Envir',
            script: [$class: 'GroovyScript',
            fallbackScript: [classpath: [], sandbox: false, script: 'return ["xxx"]'],
            script: [classpath: [], sandbox: false,
            script:  cons.getServers('Envir') ]]],

            extendedChoice(
              name: 'Branches',
              description: '',
              visibleItemCount: 50,
              multiSelectDelimiter: ',',
              type: 'PT_SINGLE_SELECT',
              groovyScript: '''
                 def mf ="ls /Users/hongqizhang/workspace/groovytest/mydir  ".execute().text
                 mf.readLines().collect{ it.split()[0].minus('.xml')}
              ''', ),
             string(name: 'payload', defaultValue: 'Mr Jenkins', description: 'A payload from github webhook'),
])
])
def map
def list
pipeline {
    agent any
    stages {
        stage('Create List') {
            steps {
                script {
                    echo "STAGE: create List..."
                    //echo "payload=$payload"
                    echo "params=$params"
                    echo "targtServer=params.targtServer"
                    workspace=WORKSPACE
                    println "WS=${env.WORKSPACE}"
                    println "WS=${WORKSPACE}"
                    println "pwd="+pwd()
                    println "workspace=$workspace"
                    // you may create your list here, lets say reading from a file after checkout
                    //list = ["Test-1", "Test-2", "Test-3", "Test-4", "Test-5"]
                    list = readXMLList("${workspace}/manifest_Lynx.xml")
                    echo "***************"
                    def jsonText = parseXML("${workspace}/manifest_Lynx.xml")
                    map = readJSON text: jsonText
                    echo "#################"
                     def remote = [:]
                    remote.name = 'test'
                    remote.host = '192.168.2.27'
                    remote.user = 'root'
                    remote.password = 'password'
                    remote.allowAnyHosts = true
    
                    sshCommand remote: remote, command: "ls -lrt"
    
                }
            }
        }
   }
}


