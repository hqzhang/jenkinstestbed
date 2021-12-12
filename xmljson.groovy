import org.json.JSONObject;
import org.json.XML;
import org.json.JSONException;
import java.nio.file.Files;
import java.nio.file.Path;
import groovy.json.JsonSlurper

def parseXML(xmlFile){
    def PRETTY_PRINT_INDENT_FACTOR = 4;
    def mystr = ""
    new File(xmlFile).eachLine { mystr += it }
    JSONObject xmlJSONObj = XML.toJSONObject(mystr);

    String jsonPrettyPrintString = xmlJSONObj.toString(PRETTY_PRINT_INDENT_FACTOR);
    println(jsonPrettyPrintString);
    def slurper = new groovy.json.JsonSlurper()
    def result = slurper.parseText(jsonPrettyPrintString)
    println result.recipes.release
    println result.recipes.Deploy_HABS.BuildNumber
    System.getProperty("java.class.path", ".").tokenize(File.pathSeparator).each {
        println it                             
    }
    ret=result
    println "**************"
    println result.getClass()
    return result
}
def map = parseXML("manifest_Lynx.xml")
 println "#####################"
 map.each {k,v->
       println k  
       println v }
 println "******######******"
/*             
  println "rm ProcessList.txt".execute().text
  File file = new File("ProcessList.txt")
  file.write  "" 
    def composite =[:]
    composite.name ='hong'
    composite.age=50
    println composite
    map.recpies.BPEL_Processes.each { k,v->
       println k
       println v 
    }
   //println file.text
*/
