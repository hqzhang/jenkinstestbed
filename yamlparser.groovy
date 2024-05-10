#!/usr/bin/env groovy
import groovy.yaml.YamlSlurper
import groovy.json.JsonSlurper
import groovy.json.JsonOutput
import ruamel.yaml

mymap1 = [ books: [[ name: 'hong', page: '21']]]
jsonString = '{"books":[{"name":"hong","page":"21"}]}'
ymltag="""books:
- !book
  name: hong
  page: 21
"""
myaml= ruamel.yaml.YAML()
code = myaml.load(ymltag)
code['name']['given'] = 'Bob'

mymap3 = new JsonSlurper().parseText(jsonString)

for(int i in 1..3) {
    tmp="mymap$i"
    println ( this[ tmp ])
}
mymap2['books'].each {
    println(it)
    it.each { k, v ->
         if (v instanceof Map) {
            v.each{ kk, vv ->
                printf("%s : %s\n",kk,vv)
            }
        } else {
            printf("%s : %s\n",k,v)
        }
    } 
}
println(mymap2)



