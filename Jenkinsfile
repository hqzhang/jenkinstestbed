#!/usr/bin/env groovy
List category_list = ["\"Select:selected\"","\"Vegetables\"","\"Fruits\""]
List fruits_list = ["\"Select:selected\"","\"apple\"","\"banana\"","\"mango\""]
List vegetables_list = ["\"Select:selected\"","\"potato\"","\"tomato\"","\"broccoli\""]
List default_item = ["\"Not Applicable\""]
String categories = buildScript(category_list)
//String categories = catagory_list
String vegetables = buildScript(vegetables_list)
String fruits = buildScript(fruits_list)
String items = populateItems(default_item,vegetables_list,fruits_list)
// Methods to build groovy scripts to populate data
String buildScript(List values){
  return "return $values"
}
String populateItems(List default_item, List vegeList, List fruitsList){
return """if(Categories.equals('Vegetables')){
     return $vegeList
     }
     else if(Categories.equals('Fruits')){
     return $fruitsList
     }else{
     return $default_item
     }
     """
}
// Properties step to set the Active choice parameters via 
// Declarative Scripting
node {
  def workspace=WORKSPACE
  def props = readProperties file: "$workspace/jenkinsfile.pipeline.properties"

  def distribution= props['distribution_property'] 

  def tag= props['tag_property']
  properties([
    parameters([ 
     string(name: 'distribution', defaultValue: "$distribution", description: 'apt distribution'),
     string(name: 'tag', defaultValue: "$tag", description: 'just a tag')
   ])
 ])
 echo "distribution: $distribution"
 echo "tag: $tag"

}

