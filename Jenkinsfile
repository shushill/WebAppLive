pipeline {
    agent any

    environment {
//         TOMCAT_WEBAPP_DIR = '/home/rdpuser/Downloads/tomcat/webapps/'
        BUILD_TOOL = 'maven' // Only Maven build tool is configured
        REGISTRY = "localhost:5000"
        IMAGE_NAME = "webapp-spring"
        IMAGE_TAG = "${env.BUILD_NUMBER}"
        CONTAINER_NAME = "springboot-app"
         PREVIOUS_IMAGE_TAG = "${env.BUILD_NUMBER.toInteger() - 1}"
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

        stage('Just checking') {
            when {
                changeset "Scripts/*"
            }
            steps {
                script {
                    // Commands to build and deploy App 1
                    sh 'echo "This is working"'
                }
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

         stage('Delete Old Images') {
            steps {
                script {
                    // Remove old images (tags) from the registry
                    sh '''
                    # Get list of all tags
                    TAGS=$(curl -s http://${REGISTRY}/v2/${IMAGE_NAME}/tags/list | jq -r '.tags[]')
                    # Delete all tags except the current build tag
                    for TAG in $TAGS; do
                        if [ "$TAG" != "${IMAGE_TAG}" ]; then
                            curl -X DELETE http://${REGISTRY}/v2/${IMAGE_NAME}/manifests/$(curl -s -I -H "Accept: application/vnd.docker.distribution.manifest.v2+json" http://${REGISTRY}/v2/${IMAGE_NAME}/manifests/$TAG | awk '$1 == "Docker-Content-Digest:" { print $2 }' | tr -d $'\\r')
                        fi
                    done
                    '''
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
                    sh "docker rmi -f ${REGISTRY}/${IMAGE_NAME}:${PREVIOUS_IMAGE_TAG} || true"
                    sh 'docker run -d -p 8081:8081 --name ${CONTAINER_NAME} ${REGISTRY}/${IMAGE_NAME}:${IMAGE_TAG}'
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
