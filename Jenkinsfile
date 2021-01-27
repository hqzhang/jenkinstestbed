
  def distribution= "a" 

  def tag= "b"
pipeline{
  agent any
  properties([
    parameters([ 
     string(name: 'distribution', defaultValue: "$distribution", description: 'apt distribution'),
     string(name: 'tag', defaultValue: "$tag", description: 'just a tag')
   ])
 ])
   stages {
     stage("mytest") {
        steps{
           script {
           echo "enter mytest"
           workspace=WORKSPACE
}}}
}}

