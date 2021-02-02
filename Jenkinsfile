def list
pipeline {
    agent none
    options {buildDiscarder(logRotator(daysToKeepStr: '7', numToKeepStr: '1'))}
    stages {
        stage('Create List') {
            agent {node 'nodename'}
            steps {
                script {
                    // you may create your list here, lets say reading from a file after checkout
                    list = ["Test-1", "Test-2", "Test-3", "Test-4", "Test-5"]
                }
            }
            post {
                cleanup {
                    cleanWs()
                }
            }
        }
        stage('Dynamic Stages') {
            agent {node 'nodename'}
            steps {
                script {
                    for(int i=0; i < list.size(); i++) {
                        stage(list[i]){
                            echo "Element: $i"
                        }
                    }
                }
            }
            post {
                cleanup {
                    cleanWs()
                }
            }
        }
    }
}
