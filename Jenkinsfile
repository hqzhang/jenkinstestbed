node {
  def distribution= "a" 

  def tag= "b"
  properties([
    parameters([ 
     string(name: 'distribution', defaultValue: "$distribution", description: 'apt distribution'),
     string(name: 'tag', defaultValue: "$tag", description: 'just a tag')
   ])
 ])
 echo "distribution: $distribution"
 echo "tag: $tag"

}

