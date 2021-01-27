#!/usr/bin/env groovy
def fruits_list =   ["\"Select:selected\"","\"apple\"","\"banana\"","\"mango\""]
def vegetables_list=["\"Select:selected\"","\"potato\"","\"tomato\"","\"broccoli\""]
//vegetables_list=["\"Select:selected\""]
def default_item = ["\"Not Applicable\""]
//String categories = buildScript(category_list)
//String categories = catagory_list
String vegetables = buildScript(vegetables_list)
String fruits = buildScript(fruits_list)
String items = populateItems(default_item,vegetables_list,fruits_list)
// Methods to build groovy scripts to populate data
String buildScript(List values){
  return "return $values"
}
String populateItems(List default_item, List vegeList, List fruitsList){
return """if(Categories.equals('Vegetables1')){
     return $vegeList
     }
     else if(Categories.equals('Fruits2')){
     return $fruitsList
     }else{
     return $default_item
     }
     """
}
String genItems(List default_item, List vegeList, List fruitsList){
return """if(Categories.equals('qa1')){
       return ["gpi_master"]
     } else if(Categories.equals('qa2')){
       return ["ssr_master"]
     } else if(Categories.equals('qa3')){
       return ["lynx_master"]
     } 
     else {
       return ["Unknown state"]
     }
     """
}   
// Properties step to set the Active choice parameters via 
// Declarative Scripting
  def  distribution="mydist"
  def  tag="mytag"
properties([
       parameters([
          choice(name: 'Categories', choices: ['Fruits','Vegetables'], description: 'input cluster'),
     
          [$class: 'CascadeChoiceParameter', choiceType: 'PT_SINGLE_SELECT',name: 'Items',
          referencedParameters: 'Categories',
          script: [$class: 'GroovyScript',
          fallbackScript: [classpath: [], sandbox: false,
          script: 'return ["error"]'], script: [classpath: [], sandbox: false, script: items]]],

          string(name: 'distribution', defaultValue: "$distribution", description: 'apt distribution'),
          string(name: 'tag', defaultValue: "$tag", description: 'just a tag')
      ])    
 ])       

pipeline{
  agent any

  /**properties([
    parameters([ 
        [$class: 'ChoiceParameter', 
          choiceType: 'PT_SINGLE_SELECT',   
          name: 'Categories', 
          script: [$class: 'GroovyScript', 
          fallbackScript: [classpath: [], sandbox: false, script: 'return ["ERROR"]'], script: [classpath: [], sandbox: false,
          script:  categories]]],
        [$class: 'CascadeChoiceParameter', choiceType: 'PT_SINGLE_SELECT',name: 'Items', 
         referencedParameters: 'Categories', 
         script: [$class: 'GroovyScript', 
         fallbackScript: [classpath: [], sandbox: false, 
         script: 'return ["error"]'], script: [classpath: [], sandbox: false, script: items]]],

         string(name: 'distribution', defaultValue: "$distribution", description: 'apt distribution'),
         string(name: 'tag', defaultValue: "$tag", description: 'just a tag')
   ])
 ])**/
 stages{
     stage('init'){
         steps{
           script{
              echo "distribution: $distribution"
              echo "tag: $tag"
         }}}
       } 
}

