pipeline {
    agent any

    environment {
        TOMCAT_WEBAPP_DIR = '/home/rdpuser/Downloads/tomcat/webapps/'
        BUILD_TOOL = 'maven' // Only Maven build tool is configured
    }

    triggers {
        // Trigger build on push to GitHub repository
        githubPush()
    }

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

        stage('Deploy') {
                  steps {
                      script {
                          // Find the .war file using shell command
                          def warFile = sh(script: 'find /var/lib/jenkins/workspace/jenkins-webapplive/project/target/ -name "*.war" | head -n 1', returnStdout: true).trim()

                          if (warFile) {
                              // Copy WAR file to Tomcat webapp folder
                              sh """
                                 sudo cp ${warFile} ${env.TOMCAT_WEBAPP_DIR}
                              """
                              echo "Deployed WAR file: ${warFile}"
                          } else {
                              error "No WAR file found"
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
