#!/usr/bin/env groovy
    import groovy.json.JsonSlurper
    import groovy.json.JsonOutput
    import groovy.yaml.YamlSlurper
    import mylib
    import sayHello
    def xmlString = """<Data>    
    </Data>"""      
 def payload = new XmlSlurper().parseText(xmlString)     
 def node = payload.'**'.find() { myNode -> myNode.DataFieldName.text() == 'Field #1' }     
 value = node.DataFieldValue?.text()    
 //println "${value}\n"    
    //example 1
    def xmlStr = """<properties extends="habs_base">
      <category name="general">
         <property name="config.folder" value="habs"/>
         <property name="a" value="habs"/>
      </category>
      <category name="reporting">
         <property name="reporting.server" value="rptsvr_uapphabdv"/>
      </category>
    </properties> """
   
println xmlStr
   //def xmlFile = "/tmp/test.xml"
   //def xml = new XmlSlurper().parseText(xmlStr)
   def xml = new XmlSlurper().parse("habs_base_config.xml")
   println xml
    xml.category.each { var->
        if (var.@name=="general" ) {
          println ("my****= ${var.@name}")
          def res= var.property.find { it.@name='a' }
          println res
        }      
          //println var.property[0].@name
         //println var.property[1].@name
   }
   
   

//println groovy.xml.XmlUtil.serialize(xml)
