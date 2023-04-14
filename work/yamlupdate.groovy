#!/usr/bin/env groovy
import groovy.yaml.YamlSlurper
import org.yaml.snakeyaml.Yaml
import org.yaml.snakeyaml.DumperOptions
println "Hello world"

def parseConfig(String fileName){
    println("Enter parseConfig() ")
    
    String fileConts = new File('config').text

    Yaml yaml = new Yaml()
    def Map  map = (Map) yaml.load(fileConts)
    map['components'].each { comp ->
       println (comp)
       comp.each{ k, v-> 
          if (v instanceof Map){
            v.each{ kk,vv->
               println(kk+':'+vv)
            }
          } else {
             println(k+':'+v)
          }
       }
      
    }
    return map
}
org.yaml.snakeyaml.DumperOptions

def saveFile(output,data){
  println("INPUT:"+data)
  
  ////
  DumperOptions options = new DumperOptions()
  options.setPrettyFlow(true)
  options.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK)
  yaml = new Yaml(options)
  yaml.dump(data, new FileWriter(output)) 
  
}
def dictUpdate(Map var,Map comp){
    println('Enter dictUpdate() config:'+comp)
    comp.each { k,v->
      println('cp:'+k+' '+v)
      if (v instanceof Map){
          println("object update")
          v.each { kk, vv->
               println("kk,vv:"+kk+' '+vv)
               var[k].each{ x, y-> 
                    println("x,y:"+x+' '+y)
                    if (kk.contains(x) ){
                        println("set "+kk+ " as "+vv)
                        var[k][x]=vv
                    }
               }
          }
      } else {
         println("single update")
         var.each { x, y->
             println("x,k:"+x+' '+k)
             if(k.contains(x)){
                println("set "+k+" as "+v)
                var[x]=v
             }
         }

      }
    
    }
    
    /*comp.each { k,v->
      if (v instanceof Map){
         println("instance update")
         v.each { kk, vv->
              var[k].each{ x, y->
                  if (kk.contains(x) ){
                      println("set "+kk+ " as "+vv)
                      var[k][x]=vv
                  }
              }
          }
      } else {
        println("single update")
        var.each { x,y->
          println("x,k:"+x+k)
          if(k.contains(x)){
            println("set "+k+"as"+v)
            var[x]=v
          }
        }
      }
      
    }*/
    println("END:"+var)
    return var
}

def updateConfiguration(String fileName,String output){
    println("Enter updateConfiguration() "+fileName)
    String fileConts = new File(fileName).text
    fileConts='''
    components:
    - !component
      name: component1
      argv: first
      settings:
        username: hongqi
        password: nopass

    '''
    //println ('fileConts'+fileConts)
    //println  yamlSlurper['components']
    Yaml yaml = new Yaml()
    def Map  data = (Map) yaml.load(fileConts)
    println('inputYaml:'+data)
    data['components'].each { var ->
        println('Loop:'+var)
        //when compoent name is matching
        params['components'].each { comp ->
            println(comp)
            println(var)
            if (comp['name'] == var['name']) {
              println('Do update')
              dictUpdate(var,comp)
            }
        }
    }
    saveFile(output,data)
}




params=parseConfig('config')
updateConfiguration('configuration.yml','configuration_out.yml')
   
   
   
   

