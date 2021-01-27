
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
 //echo "distribution: $distribution"
 //echo "tag: $tag"
 stages{
   stage('init') {
    steps{
echo "distribution: $distribution"
 echo "tag: $tag"
}
}
 }
}

