pipeline {
    agent any

    environment {
        // Set your environment variables here
        GIT_REPO_URL = 'https://github.com/your-username/your-repo.git'
        GIT_BRANCH = 'main'
//         TOMCAT_SERVER = 'your-tomcat-server'
//         TOMCAT_USER = 'your-tomcat-user'
//         TOMCAT_PASSWORD = 'your-tomcat-password'
//         TOMCAT_WEBAPP_DIR = '/path/to/tomcat/webapps'
//         BUILD_TOOL = 'maven' // or 'gradle'
    }

    triggers {
        // Trigger build on push to GitHub repository
        githubPush()
    }

    stages {
        stage('Checkout') {
            steps {
                git branch: "${env.GIT_BRANCH}", url: "${env.GIT_REPO_URL}"
            }
        }

        stage('Build') {
            steps {
                script {
//                     if (env.BUILD_TOOL == 'maven') {
//                         sh 'mvn clean package'
//                     } else if (env.BUILD_TOOL == 'gradle') {
//                         sh 'gradle clean build'
//                     }

                        echo 'Hello world'
                }
            }
        }

//         stage('Deploy') {
//             steps {
//                 sshagent(credentials: ['your-ssh-credentials-id']) {
//                     script {
//                         def warFile = findFiles(glob: '**/*.war')[0]
//                         def jarFile = findFiles(glob: '**/*.jar')[0]
//                         if (warFile) {
//                             // Copy WAR file to Tomcat webapp folder
//                             sh """
//                                 scp ${warFile.path} ${env.TOMCAT_USER}@${env.TOMCAT_SERVER}:${env.TOMCAT_WEBAPP_DIR}
//                             """
//                         } else if (jarFile) {
//                             // Copy JAR file to Tomcat webapp folder
//                             sh """
//                                 scp ${jarFile.path} ${env.TOMCAT_USER}@${env.TOMCAT_SERVER}:${env.TOMCAT_WEBAPP_DIR}
//                             """
//                         } else {
//                             error "No WAR or JAR file found"
//                         }
//                     }
//                 }
//             }
//         }
    }

//     post {
//         success {
//             echo 'Build and deployment successful!'
//         }
//         failure {
//             echo 'Build or deployment failed.'
//         }
//     }
}
