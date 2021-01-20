#!/usr/bin/env groovy
    import groovy.json.JsonSlurper
    import groovy.json.JsonOutput
    import groovy.yaml.YamlSlurper
    import mylib
    import sayHello
    //example 1
def addProperty(file, directoryName, propName, propValue) {
    def xmlStr = """<properties extends="habs_base">
        <category name="general">
            <property name="config.folder" value="habs"/>
        </category>
        <category name="reporting">
            <property name="reporting.server" value="rptsvr_uapphabdv"/>
        </category>
    </properties> """

    def xmlFile = "/tmp/file.xml"
    def xml = new XmlSlurper().parseText(xmlStr)
    
    xml.category.each { 
        if (it.@name==directoryName ) {
           println it.@name
          it.children().findAll { it.@name == propName }.replaceNode {}
          it.appendNode {  property(name: propName, value: propValue)  }
        }
    }    
    println groovy.xml.XmlUtil.serialize(xml)
}

addProperty("/tmp/file.xml","general","myname","myvalue")
addProperty("/tmp/file.xml","reporting","myname","myvalue")



