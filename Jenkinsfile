def workspace

pipeline {

   agent any
   stages {
     stage("mytest") {
        steps{
           script {
           workspace=WORKSPACE
           //def result=readHabsRecipes("${workspace}/habs_recipes")
           readXMLSwitch("${workspace}/manifest_Lynx.xml",workspace)
           def stag=["A","B","C"]
           stag.each{
                 stage(it) {
                 echo "inside stage"
                 }
           }
}}}
}}




