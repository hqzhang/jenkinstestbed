#!/usr/bin/env groovy
//import groovy.yaml.YamlSlurper
import org.yaml.snakeyaml.Yaml
import org.yaml.snakeyaml.DumperOptions

fileName="/Users/hongqizhang/workspace/wavecloud/groovytest/work/configuration.yml"
String str = "persons:\n" +
                "  - !person\n" +
                "    name: 'emily'\n" +
                "    age: 19";
import groovy.yaml.YamlSlurper
def readYamlFile(String fileName){
    return  new YamlSlurper().parseText(new File(fileName).text)
}
def readYamlText(String data){
    return  new YamlSlurper().parseText(data)
}

myfile="/Users/hongqizhang/workspace/groovytest/work/configuration.yml"
println readYamlFile(myfile)
println readYamlText(str)
