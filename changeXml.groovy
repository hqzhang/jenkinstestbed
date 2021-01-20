#!/usr/bin/env groovy
    import groovy.json.JsonSlurper
    import groovy.json.JsonOutput
    import groovy.yaml.YamlSlurper
    import mylib
    import sayHello
    //example 1
    def xmlStr =  """<properties extends="habs_base">
      <category name="general">
         <property name="config.folder" value="habs"/>
      </category>
      <category name="reporting">
         <property name="reporting.server" value="rptsvr_uapphabdv"/>
      </category>
    </properties> """
   
    println "xmlStr=$xmlStr"
     def xml = new XmlParser().parseText(xmlStr)
 //    xml[0].each {
   //    println it.@id
     //  if( it.@id == "general") {
          xml.category.appendNode{ property(name: "myproperties value", value="hongqi") }
       //}
   //}
    //println groovy.xml.XmlUtil.serialize(xml)
   //new XmlNodePrinter(new PrintWriter(new FileWriter(xmlFile))).print(xml)
   System.exit(1);
    //def ltrData = myxml.'**'.find{it.name() == 'general'}
   // ltrData.replaceBody()
   // ltrData.appendNode {
   //    Salutation('text')
   // }
    //println groovy.xml.XmlUtil.serialize(myxml)
    System.exit(1);


