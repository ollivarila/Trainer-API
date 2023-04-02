pipeline {
    agent any
    environment {
        JAVA_HOME = "/var/jenkins_home/jdk-17"
    }
    stages {
        stage("Modify gradlew"){
            steps{
                sh 'chmod +x gradlew'
            }
        }

        stage('Build') {
            steps {
                sh './gradlew cleanBuildCache'
            }
        }
        stage('Test') {
            steps {
                sh './gradlew test'
            }
        }
    }
}