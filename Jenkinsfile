pipeline {
    agent any
    environment {
        JAVA_HOME = "/var/jenkins_home/jdk-17"
        JWT_SECRET = "test"
    }
    stages {
        stage("Modify gradlew"){
            steps{
                sh 'chmod +x gradlew'
            }
        }

        stage('Build') {
            steps {
                sh './gradlew build'
            }
        }
        stage('Test') {
            steps {
                sh './gradlew test'
            }
        }
    }
}