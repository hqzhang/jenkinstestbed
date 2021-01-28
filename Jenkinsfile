@Library('my-shared-library') _
//library("my-shared-library") _
//YOU SHOULD OPEN CONFIG and DO SAVE 
properties([ 
    parameters([
           choice(name: 'ENVIRONMENT', choices: ['qa1','qa2','qa3'], description: 'input cluster'),         
         
           [$class: 'CascadeChoiceParameter', choiceType: 'PT_SINGLE_SELECT',
            description: 'Active Choices Reactive parameter', 
            filterLength: 1, filterable: true, 
            name: 'choice2', randomName: 'choice-parameter-7601237141171',
            referencedParameters: 'ENVIRONMENT', 
            script: [$class: 'GroovyScript', 
            fallbackScript: [classpath: [], sandbox: false, script: 'return ["ERROR"]'], 
            script: [classpath: [], sandbox: false, 
            script: '''if(ENVIRONMENT.equals("qa1")){ return ["qa1_master"]
                   } else { return ["qa2_master"] } ''']]]
    ])
])

pipeline{
  agent any

 stages{
     stage('init'){
         steps{
           script{
              echo "param: ${Utils.param}"
              Utils.hello()
         }}}
       } 
}


