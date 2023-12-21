import groovy.yaml.YamlSlurper

println GroovySystem.version

def readYamlFileExt(String fileName){
    String fileConts = new File(fileName).text
    println ("-----------parsing-------")
    def myyaml=new YamlSlurper()
    return  myyaml.parseText(fileConts)
}

def readYamlFileString(){
    withGroovy(tool:'4.0.11') {
    sh """groovy --version
        ./rungroovy.groovy 
        """             
    }
}

def justTest(){
    println "Enter justTest()"
    println "abc=$abc"
    println "xyz=$xyz"
}
