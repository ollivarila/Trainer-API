pipeline {
    agent any
    environment {
        JAVA_HOME = "/usr/lib/jdk-17/bin"
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