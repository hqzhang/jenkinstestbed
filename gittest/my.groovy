
import groovy.yaml.YamlSlurper
println GroovySystem.version
def data = """
comp:
-  type: abc
   version: 123
-  type: efg
   version: 456
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

data = """
comp:
- type: daemon
  version: 123
- type: efg
  version: 456
"""
my=
l = [1, 4, 6, 2, 6, 1]

print("List before calling remove function:")
print(l)
for var in l:
   if var==3:
      l.remove(var)

print("List after calling remove function:")
print(l)
