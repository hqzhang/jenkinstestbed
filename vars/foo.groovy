

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
import groovy.lang.GroovyClassLoader

def getClasspathEntryPath(Class clazz) {

    def classLoader = clazz.classLoader
    println GroovySystem.version
    if (classLoader instanceof GroovyClassLoader) {
        println classLoader.getURLs()
        def classpath = classLoader.getURLs().find { it.path.endsWith("groovy-all-4.0.11.jar") } // Replace x.y.z with the version
        return classpath ? classpath.path : null
    } else {
        return null
    }
}

def groovyLibraryPath = getClasspathEntryPath(getClass())
println "Groovy Library Path: $groovyLibraryPath"