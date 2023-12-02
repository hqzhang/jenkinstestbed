

println GroovySystem.version


def readYamlFileExt(String fileName){
    String fileConts = new File(fileName).text
    println ("-----------parsing-------")
    def myyaml=new groovy.yaml.YamlSlurper()
    return  myyaml.parseText(fileConts)
}

def readYamlFileString(){
    def myyaml=new groovy.yaml.YamlSlurper()
    def data = """
- !comp
   name: abc
- !comp
   Name: elf
"""
    def myfile="/Users/hongqizhang/workspace/groovytest/work/configuration.yml"
    println readYamlFileExt(myfile)
    println  myyaml.parseText(data)
    println GroovySystem.version

}

readYamlFileString()