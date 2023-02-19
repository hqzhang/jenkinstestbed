#!/usr/bin/env groovy
import groovy.yaml.YamlSlurper
import groovy.json.JsonSlurper
import groovy.json.JsonOutput
//create map:
List mylist=['str1', 'str2']
mylist.each {
    println it
}
  
System.exit(1)
mymap1 = [ books: [[ name: 'hong', page: '21']]]
jsonString = '{"books":[{"name":"hong","page":"21"}]}'
ymltag="""books:
- !book
  name: hong
  page: 21
"""
mymap2= new YamlSlurper().parseText(ymltag)
mymap3 = new JsonSlurper().parseText(jsonString)

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
