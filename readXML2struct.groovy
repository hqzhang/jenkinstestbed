import org.json.JSONException;
import org.json.JSONObject;
import org.json.XML; 
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

def mystr = ""
new File("in.xml").eachLine { mystr += it }
/*
StringBuilder sb = new StringBuilder();
BufferedReader br = Files.newBufferedReader( Paths.get(“/Users/hongqizhang/workspace/groovytest/in.xml”) )
// read line by line
String line;
while ((line = br.readLine()) != null)
sb.append(line).append("\n")
*/
System.out.println(mystr)

JSONObject json = XML.toJSONObject(mystr);
String jsonPrettyPrintString = json.toString(4);
System.out.println(jsonPrettyPrintString);
