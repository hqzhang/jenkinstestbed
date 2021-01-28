#!/usr/bin/env groovy
def fruits_list =   ["\"Select:selected\"","\"apple\"","\"banana\"","\"mango\""]
def vegetables_list=["\"Select:selected\"","\"potato\"","\"tomato\"","\"broccoli\""]
def default_item = ["\"Not Applicable\""]
  def  distribution="mydist"
  def  tag="mytag"
properties([
       parameters([
          choice(name: 'Categories', choices: ['Fruits','Vegetables'], description: 'input cluster'),
     
          [$class: 'CascadeChoiceParameter', choiceType: 'PT_SINGLE_SELECT',name: 'Items',
          referencedParameters: 'Categories',
          script: [$class: 'GroovyScript',
          fallbackScript: [classpath: [], sandbox: false,
          script: 'return ["error"]'], script: [classpath: [], sandbox: false, 
          script: 
      """if(Categories.equals('Vegetables')){
     return $vegetables_list
     }
     else if(Categories.equals('Fruits')){
     return $fruits_list
     }else{
     return $default_item
     }
     """
          ]]],

          string(name: 'distribution', defaultValue: "$distribution", description: 'apt distribution'),
          string(name: 'tag', defaultValue: "$tag", description: 'just a tag')
      ])    
 ])       

pipeline{
  agent any

 stages{
     stage('init'){
         steps{
           script{
              echo "distribution: $distribution"
              echo "tag: $tag"
         }}}
       } 
}


/**
properties([
    parameters([
        [$class: 'ChoiceParameter', 
            choiceType: 'PT_SINGLE_SELECT', 
            description: 'Select the Env Name from the Dropdown List', 
            filterLength: 1, 
            filterable: true, 
            name: 'Env', 
            randomName: 'choice-parameter-5631314439613978', 
            script: [
                $class: 'GroovyScript', 
                fallbackScript: [
                    classpath: [], 
                    sandbox: false, 
                    script: 
                        'return [\'Could not get Env\']'
                ], 
                script: [
                    classpath: [], 
                    sandbox: false, 
                    script: 
                        'return ["Dev","QA","Stage","Prod"]'
                ]
            ]
        ], 
        [$class: 'CascadeChoiceParameter', 
            choiceType: 'PT_SINGLE_SELECT', 
            description: 'Select the Server from the Dropdown List', 
            filterLength: 1, 
            filterable: true, 
            name: 'Server', 
            randomName: 'choice-parameter-5631314456178619', 
            referencedParameters: 'Env', 
            script: [
                $class: 'GroovyScript', 
                fallbackScript: [
                    classpath: [], 
                    sandbox: false, 
                    script: 
                        'return [\'Could not get Environment from Env Param\']'
                ], 
                script: [
                    classpath: [], 
                    sandbox: false, 
                    script: 
                        ''' if (Env.equals("Dev")){
                                return ["devaaa001","devaaa002","devbbb001","devbbb002","devccc001","devccc002"]
                            }
                            else if(Env.equals("QA")){
                                return ["qaaaa001","qabbb002","qaccc003"]
                            }
                            else if(Env.equals("Stage")){
                                return ["staaa001","stbbb002","stccc003"]
                            }
                            else if(Env.equals("Prod")){
                                return ["praaa001","prbbb002","prccc003"]
                            }
                        '''
                ]
            ]
        ]
    ])
])

pipeline {
  environment {
         vari = ""
  }
  agent any
  stages {
      stage ("Example") {
        steps {
         script{
          echo 'Hello'
          echo "${params.Env}"
          echo "${params.Server}"
          if (params.Server.equals("Could not get Environment from Env Param")) {
              echo "Must be the first build after Pipeline deployment.  Aborting the build"
              currentBuild.result = 'ABORTED'
              return
          }
          echo "Crossed param validation"
        } }
      }
  }
}
**/
