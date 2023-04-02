pipeline {
    agent any
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