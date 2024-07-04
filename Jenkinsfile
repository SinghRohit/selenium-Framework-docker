pipeline{
    agent any
    stages{
        stage('Build Jar'){
            steps{
                bat "mvn clean package -DskipTests"
            }
        }
        stage('Build Image'){
            steps{
                bat "docker build -t=singtelauto/selenium-framework-docker"
            }
        }
        stage('Push Image'){
            steps{
                bat "docker push singtelauto/selenium-framework-docker"
            }
        }
    }
}