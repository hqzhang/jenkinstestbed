//Open file
def xml = new XmlSlurper().parse('/tmp/file.xml')

//Edit File e.g. append an element called foo with attribute bar
//xml.appendNode {
//   foo(bar: "bar value")
//}
println("root=$xml")
println("rt2=${xml.foo}")
xml.foo.each { var->
    println  var.@id 
    println var.value 
}

//Save File
def writer = new FileWriter('/tmp/file.xml')

//Option 1: Write XML all on one line
//def builder = new StreamingMarkupBuilder()
//writer << builder.bind {
//  mkp.yield xml
//}

//Option 2: Pretty print XML
XmlUtil.serialize(xml, writer)
