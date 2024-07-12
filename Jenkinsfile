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
         SPRINGBOOT_IMAGE_NAME = "springboot-image"
         SPRINGBOOT_IMAGE_TAG = "${env.BUILD_NUMBER}"
        SPRING_DATASOURCE_URL = 'jdbc:postgresql://postgres_container:5432/mydb'
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

        stage('Check Post') {
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

                      sh 'docker volume create postgres-data || true'
                      sh 'docker volume create pgadmin-data || true'
                      sh 'docker network create spring-postgres || true'

                       sh 'docker-compose -f ${COMPOSE_FILE} down'
                      sh 'docker-compose -f ${COMPOSE_FILE} build'
                      sh 'docker-compose -f ${COMPOSE_FILE} up -d'

                   }
                   sh 'echo "After database line"'
                }
            }
        }

        stage('Build Jar file') {

            steps {
                dir('project') {
                    sh 'mvn clean'
                    sh 'mvn package'
                }
            }
        }

         stage('Build Docker Image and Run') {
            steps {
                script {
                     dir('project') {
                       sh "docker stop ${CONTAINER_NAME} || true"
                       sh "docker rm ${CONTAINER_NAME} || true"
                       sh "docker rmi -f ${SPRINGBOOT_IMAGE_NAME}:${PREVIOUS_IMAGE_TAG} || true"
                     sh "docker build -t ${SPRINGBOOT_IMAGE_NAME}:${SPRINGBOOT_IMAGE_TAG} ."
                     sh 'docker network create spring-postgres || true'
                      sh 'docker run -d -p 8081:8081 --name ${CONTAINER_NAME} \
                          --network spring-postgres \
                          -e SPRING_DATASOURCE_URL=${SPRING_DATASOURCE_URL} \
                          -e SPRING_DATASOURCE_USERNAME=${SPRING_DATASOURCE_USERNAME} \
                          -e SPRING_DATASOURCE_PASSWORD=${SPRING_DATASOURCE_PASSWORD} \
                          ${SPRINGBOOT_IMAGE_NAME}:${SPRINGBOOT_IMAGE_TAG}'
                   }
                }
            }
        }



         stage('Monitoring Tools') {
            when {
                changeset "Observability/*"
            }
            steps {
                script {
                    dir('Observability'){
                        sh 'docker volume create prometheus-data || true'
                        sh 'docker volume create grafana-data || true'
                         sh 'docker-compose -f ${COMPOSE_FILE} down'
                         sh 'docker-compose -f ${COMPOSE_FILE} build'
                         sh 'docker-compose -f ${COMPOSE_FILE} up -d'
                    }
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
