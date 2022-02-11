pipeline {
    agent any
    triggers {
    cron('0 2 * * *')
    pollSCM('* * * * *')
        }
    stages {
            stage('Build'){
                 steps {
                     sh 'mvn package'
                 }
            }
//             stage('Run'){
//                   steps {
//                        sh 'gradle run --args="10+5/(8-5)"'
//                   }
//             }
            stage('Test') {
                        steps {
                            sh 'mvn test'
                        }
            }
        }
}