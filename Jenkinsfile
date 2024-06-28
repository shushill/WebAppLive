pipeline {
    agent any

    environment {
//         TOMCAT_WEBAPP_DIR = '/home/rdpuser/Downloads/tomcat/webapps/'
        BUILD_TOOL = 'maven'
        COMPOSE_FILE = 'docker-compose.yml'
        REGISTRY = "localhost:5000"
        IMAGE_NAME = "webapp-spring"
        IMAGE_TAG = "${env.BUILD_NUMBER}"
       POSTGRES_IMAGE = 'custom-postgres'
       POSTGRES_CONTAINER = 'postgres-container'
       POSTGRES_DB='mydb'
       POSTGRES_USER='postgres'
       POSTGRES_PASSWORD='postgres'
       POSTGRES_VOLUME = 'postgres-data'
        CONTAINER_NAME = 'springboot-app'
        SPRING_DATASOURCE_URL = 'jdbc:postgresql://localhost:5432/mydb'
        SPRING_DATASOURCE_USERNAME = 'postgres'
        SPRING_DATASOURCE_PASSWORD = 'postgres'
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

         stage('Database') {
            when {
                changeset "Database/*"
            }
            steps {
                script {
                    dir('Database') {
                      sh 'echo "Database folder"'

                       sh 'docker-compose -f ${COMPOSE_FILE} down'
                      sh 'docker-compose -f ${COMPOSE_FILE} build'
                      sh 'docker-compose -f ${COMPOSE_FILE} up -d'

                   }
//                      def volumeExists = sh(script: "docker volume inspect ${POSTGRES_VOLUME} > /dev/null 2>&1 || echo 'no'", returnStdout: true).trim()
//                     if (volumeExists == 'no') {
//                         sh "docker volume create ${POSTGRES_VOLUME}"
//                     }
//                      // Stop and remove the previous container if it exists
//                    sh "docker stop ${POSTGRES_CONTAINER} || true"
//                    sh "docker rm ${POSTGRES_CONTAINER} || true"
//
//                    // Remove the previous image if it exists
// //                    def imageExists = sh(script: "docker images -q ${POSTGRES_IMAGE} || echo 'no'", returnStdout: true).trim()
// //                   if (imageExists != 'no') {
// //                       sh "docker rmi ${POSTGRES_IMAGE} -f"
// //                   }
//                    sh '''
//                        docker run -d --name ${POSTGRES_CONTAINER} -p 5432:5432 \
//                        --network spring-postgres \
//                        -e POSTGRES_DB=${POSTGRES_DB} \
//                        -e POSTGRES_USER=${POSTGRES_USER} \
//                        -e POSTGRES_PASSWORD=${POSTGRES_PASSWORD} \
//                        -v postgres-data:/var/lib/postgresql/data \
//                        ${POSTGRES_IMAGE}
//                    '''
                        sh 'echo "After database line"'
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

        stage('Remove previous continer and Images') {
            steps {
                script {
                     dir('project/') {
                       sh 'docker stop springboot-app || true'
                       sh 'docker rm springboot-app || true'
                       //sh 'docker image rmi springboot-app:'
                    }
                }
            }
        }

         stage('Build Docker Image and Deploy') {
            steps {
                script {
                     dir('project/') {
                       sh 'docker-compose -f ${COMPOSE_FILE} down || true'
                       sh 'docker-compose -f ${COMPOSE_FILE} build'
                       sh 'docker-compose -f ${COMPOSE_FILE} up -d'
                    }
                }
            }
        }



//          stage('Delete Old Images') {
//             steps {
//                 script {
//                     // Remove old images (tags) from the registry
//                     sh '''
//                     # Get list of all tags
//                     TAGS=$(curl -s http://${REGISTRY}/v2/${IMAGE_NAME}/tags/list | jq -r '.tags[]')
//                     # Delete all tags except the current build tag
//                     for TAG in $TAGS; do
//                         if [ "$TAG" != "${IMAGE_TAG}" ]; then
//                             curl -X DELETE http://${REGISTRY}/v2/${IMAGE_NAME}/manifests/$(curl -s -I -H "Accept: application/vnd.docker.distribution.manifest.v2+json" http://${REGISTRY}/v2/${IMAGE_NAME}/manifests/$TAG | awk '$1 == "Docker-Content-Digest:" { print $2 }' | tr -d $'\\r')
//                         fi
//                     done
//                     '''
//                 }
//             }
//         }

//         stage('Push Docker Image') {
//             steps {
//                 script {
//                     docker.withRegistry("http://${REGISTRY}") {
//                         docker.image("${REGISTRY}/${IMAGE_NAME}:${IMAGE_TAG}").push()
//                     }
//                 }
//             }
//         }

//         stage('Deploy Docker Container') {
//             steps {
//                 script {
//                     sh 'docker stop springboot-app || true'
//                     sh 'docker rm springboot-app || true'
//                     sh "docker rmi -f ${REGISTRY}/${IMAGE_NAME}:${PREVIOUS_IMAGE_TAG} || true"
//                     sh 'docker run -d -p 8081:8081 --name ${CONTAINER_NAME} \
//                     --network spring-postgres \
//                     -e SPRING_DATASOURCE_URL=${SPRING_DATASOURCE_URL} \
//                     -e SPRING_DATASOURCE_USERNAME=${SPRING_DATASOURCE_USERNAME} \
//                     -e SPRING_DATASOURCE_PASSWORD=${SPRING_DATASOURCE_PASSWORD} \
//                     ${REGISTRY}/${IMAGE_NAME}:${IMAGE_TAG}'
//                 }
//             }
//         }
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
