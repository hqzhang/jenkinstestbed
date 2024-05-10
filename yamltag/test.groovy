@Grab('org.yaml:snakeyaml:1.29')
import org.yaml.snakeyaml.DumperOptions
import org.yaml.snakeyaml.Yaml
data="""
- !!Person
  age: 30
  name: John Doe
- !!Person
  age: 25
  name: Jane Smith
"""
import groovy.yaml.YamlSlurper

def data11 = '''
- !!Person
  age: 30
  name: John Doe
- !!Person
  age: 25
  name: Jane Smith
'''

def slurper = new YamlSlurper()
def parsedData = slurper.parseText(data11)
parsedData.each { per->
   per.each { k, v->
   println(k+":"+v)
   }

}


// Define an object
class Person {
    String name
    int age
}
def john = new Person(name: 'John Doe', age: 30)
def hong = new Person(name: 'hong zhang', age: 50)
def data=[john, hong]
data.each{ println it.properties
           }
def options = new DumperOptions()
options.defaultFlowStyle = DumperOptions.FlowStyle.BLOCK
def yaml = new Yaml(options)
def yamlData = yaml.dump(data)

println yamlData
