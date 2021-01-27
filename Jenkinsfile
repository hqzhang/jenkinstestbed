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
