pipeline {
    agent any
    triggers {
    cron('0 2 * * *')
    pollSCM('* * * * *')
        }
    stages {
            stage('Release'){
                 steps {
                     sh 'mvn clean install'
                 }
            }
            stage('Run'){
                  steps {
                       sh 'gradle run --args="10+5/(8-5)"'
                  }
            }
            //should add one more stage for cw
            stage('Test') {
                        steps {
                            sh 'mvn test'
                        }
            }
        }
}