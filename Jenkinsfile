pipeline {
    agent any

    environment {
//         TOMCAT_WEBAPP_DIR = '/home/rdpuser/Downloads/tomcat/webapps/'
        BUILD_TOOL = 'maven' // Only Maven build tool is configured
        REGISTRY = "localhost:5000"
        IMAGE_NAME = "webapp-spring"
        IMAGE_TAG = "${env.BUILD_NUMBER}"
    }

//     triggers {
//         // Trigger build on push to GitHub repository
//         githubPush()
//     }

    stages {
        stage('Checkout') {
            steps {
                git branch: 'master', url: 'https://github.com/shushill/WebAppLive.git', credentialsId: 'github-token'
            }
        }

        stage('Build') {
            steps {
                dir('project') {
                    sh 'mvn clean'
                    sh 'mvn package'
                }
            }
        }

        stage('Build Docker Image') {
            steps {
                script {
                     dir('project/') {
                        docker.build("${REGISTRY}/${IMAGE_NAME}:${IMAGE_TAG}")
                    }
                }
            }
        }

        stage('Push Docker Image') {
            steps {
                script {
                    docker.withRegistry("http://${REGISTRY}") {
                        docker.image("${REGISTRY}/${IMAGE_NAME}:${IMAGE_TAG}").push()
                    }
                }
            }
        }

        stage('Deploy Docker Container') {
            steps {
                script {
                    sh 'docker stop springboot-app || true'
                    sh 'docker rm springboot-app || true'
                    sh 'docker run -d -p 8081:8081 --name springboot-app ${REGISTRY}/${IMAGE_NAME}:${IMAGE_TAG}'
                }
            }
        }
    }

    post {
        success {
            echo 'Build and deployment successful!'
        }
        failure {
            echo 'Failed Build or deployment'
        }
    }
}
