#!/usr/bin/env groovy
import groovy.yaml.YamlSlurper

def readYamlFileExt(String fileName){
    String fileConts = new File(fileName).text
    println ("-----------parsing-------")
    def myyaml=new YamlSlurper()
    return  myyaml.parseText(fileConts)
}
def readYamlFileString(){
    def myyaml=new YamlSlurper()
    def data = """
- !comp
   name: abc
- !comp
   Name: elf
"""
    def myfile="/Users/hongqizhang/workspace/groovytest/work/configuration.yml"
    println readYamlFileExt(myfile)
    println  myyaml.parseText(data)
    println "Environment version "
    println GroovySystem.version
}
