import groovy.xml.StreamingMarkupBuilder
import groovy.xml.XmlUtil

def settingsFile = 'temp.xml'

@NonCPS
def xmlTransform(txt, username, password) {

    def xmlRoot = new XmlSlurper(false, false).parseText(txt)
    echo 'Start tranforming XML'
    /**xmlRoot.servers.server.each { node ->
       node.username = username
       node.password = password
    }**/

    // TRICKY: FileWriter does NOT work
    def outWriter = new StringWriter()
    XmlUtil.serialize( xmlRoot, outWriter )
    return outWriter.toString()
}

pipeline {
   agent any
   stages {
       stage("compile") {
           steps {
               
                   script {
                        def xmlTemplate = readFile( 'habs_base_config.xml' )
                         xmlTemplate = """<?xml version="1.0" encoding="UTF-8"?><properties extends="habs_base">
  <category name="general">
    <property name="config.folder" value="habs"/>
  </category>
  <category name="reporting">
    <property name="reporting.server" value="rptsvr_uapphabdv"/>
  </category>
</properties>"""
                       def xmlFile = xmlTransform(xmlTemplate, env.nexusUsername, env.nexusPassword)
                       writeFile file: "results.xml", text: xmlFile
                     
                   }
             }
           
           post {
           failure {
               echo "Sending email for compile failed (TBD)"
            }
           }
       }
   }
}
