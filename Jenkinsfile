def list
pipeline {
    agent any
    stages {
        stage('Create List') {
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
