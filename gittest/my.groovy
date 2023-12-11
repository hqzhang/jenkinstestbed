
import groovy.yaml.YamlSlurper
println GroovySystem.version
def data = """
- !comp
   name: abc
- !comp
   Name: elf
"""
def readYamlFile(String fileName){
    println "enter readYamlFile "
    String fileConts = new File(fileName).text
    def myyaml=new YamlSlurper()
    return  myyaml.parseText(fileConts)
}
def readYamlText(String data){
   println "enter readYamlText "
    def myyaml=new YamlSlurper()
    return  myyaml.parseText(data)
}
myfile="/Users/hongqizhang/workspace/groovytest/work/configuration.yml"
// Example usage
// Function to convert seconds since 1970 to date format
def secondsToDate(long seconds) {
    def date = new Date(seconds * 1000) // Convert seconds to milliseconds
    return date
}

def secondsSince1970 = 1639200000 // Replace this with the actual seconds value
def formattedDate = secondsToDate(secondsSince1970)
println "Formatted Date: ${formattedDate}"
