import groovy.json.JsonSlurper
import groovy.json.JsonParserType
def json = new JsonSlurper()
def data='{ "myList": [4, 8, 15, 16, 23, 42] }'
println data.getClass()
data = "{'JIRACHEF': 'PIBEP-2135', 'JIRADEPLOYER': 'PIBEP-2136', 'JIRASINGLEBUILD': 'PIBEP-2137'}"
println data.getClass()
def parsed = json.setType(JsonParserType.LAX).parseText(data)
//def parsed = json.parseText(data)
//println parsed
def data1=new File('file.json')
println data1.getClass()
def obj=json.parse(data1)
//println obj

/*def json = new JsonSlurper()
def jsonSlurper = new JsonSlurper()
def object = jsonSlurper.parseText(data)


println object*/
