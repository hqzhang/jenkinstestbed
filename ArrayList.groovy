#!/usr/bin/env groovy
    import groovy.json.JsonSlurper
    import groovy.json.JsonOutput
    import groovy.yaml.YamlSlurper
    import mylib
    import sayHello
    //mapping////

   
    //variable key
    //String keyVar = "DC1_SERVER"
    def lt = ['DEV':['APP':['p1','p2'],
                     'DB':['p3']],
              "BAT":['p1','p2']]
    println("hq")
    println lt['DEV']['APP']
    