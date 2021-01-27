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
properties([
       parameters([
          string(name: 'distribution', defaultValue: "$distribution", description: 'apt distribution'),
          string(name: 'tag', defaultValue: "$tag", description: 'just a tag')
      ])    
 ])       

pipeline{
  agent any

  def workspace=WORKSPACE
  def props = readProperties file: "$workspace/jenkinsfile.pipeline.properties"
  def distribution= props['distribution_property'] 
  def tag= props['tag_property']
  /**properties([
    parameters([ 
        [$class: 'ChoiceParameter', 
          choiceType: 'PT_SINGLE_SELECT',   
          name: 'Categories', 
          script: [$class: 'GroovyScript', 
          fallbackScript: [classpath: [], sandbox: false, script: 'return ["ERROR"]'], script: [classpath: [], sandbox: false,
          script:  categories]]],
        [$class: 'CascadeChoiceParameter', 
         choiceType: 'PT_SINGLE_SELECT',name: 'Items', 
         referencedParameters: 'Categories', 
         script: [$class: 'GroovyScript', 
         fallbackScript: [classpath: [], sandbox: false, 
         script: 'return ["error"]'], script: [classpath: [], sandbox: false, 
         script: items]]],
         string(name: 'distribution', defaultValue: "$distribution", description: 'apt distribution'),
         string(name: 'tag', defaultValue: "$tag", description: 'just a tag')
   ])
 ])**/
 stages{
     stage('init')[
         steps{
    script{
 echo "distribution: $distribution"
 echo "tag: $tag"
}}}}
}
