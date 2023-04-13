#!/usr/bin/env groovy
import groovy.yaml.YamlSlurper
import groovy.json.JsonSlurper
import groovy.json.JsonOutput
import org.yaml.snakeyaml.Yaml
//create map:
List mylist=['str1', 'str2']
mylist.each {
    println it
}
  
mymap1 = [ books: [[ name: 'hong', page: '21']]]
jsonString = '{"books":[{"name":"hong","page":"21"}]}'
ymltag="""books:
- !book
  name: hong
  page: 21
"""
myyml=new YamlSlurper()
mymap2= myyml.parseText(ymltag)
println("fryaml=$mymap2")
println myyml.load(mymap2)

mymap3 = new JsonSlurper().parseText(jsonString)
println("frjson=$mymap3")
println new JsonSlurper().dump(mymap3)

for(int i in 1..3) {
    tmp="mymap$i"
    println ( this[ tmp ])
}
mymap3['books'].each {
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
int tt=7
if (tt%2 == 0) {
   println( tt+" is even")
} else { 
   println("odd")
}
