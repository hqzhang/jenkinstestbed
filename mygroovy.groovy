import groovy.json.JsonOutput;
import groovy.yaml.YamlSlurper
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory

def map =[ [ name:zhang     ] [name: hongqi   ]]
println("enter main")	
String fileConts = new File('myyaml').text
println fileConts
def yamlSlurper = new YamlSlurper().parseText(fileConts)
println  yamlSlurper
def mapper = new ObjectMapper().writeValueAsString(yamlSlurper)
println "output json..."
println JsonOutput.prettyPrint(mapper)

