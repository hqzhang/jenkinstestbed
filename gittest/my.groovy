
import groovy.yaml.YamlSlurper
println GroovySystem.version
def data = """
- !comp
   name: abc
- !comp
   Name: elf
"""

def readYamlFileExt(String fileName){
    String fileConts = new File(fileName).text
    println ("-----------parsing-------")
    def myyaml=new YamlSlurper()
    return  myyaml.parseText(fileConts)
}


myfile="/Users/hongqizhang/workspace/groovytest/work/configuration.yml"
println readYamlFileExt(myfile)
def myyaml=new YamlSlurper()
println  myyaml.parseText(data)
