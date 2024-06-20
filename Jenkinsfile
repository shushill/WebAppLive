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
                 checkout scm
            }
        }

      stage('Build') {
                  steps {

                   dir('project') {
                         sh 'mvn clean'
                         sh 'mvn install'
                   }
//                       script {
//                           sh 'cd project/'
//                           sh 'mvn clean'
//                           sh 'mvn install'
//                       }
                  }
              }

        stage('Deploy') {
                    steps {
                        script {
                            def warFiles = findFiles(glob: '**/*.war')
                            if (warFiles.length > 0) {
                                // Copy WAR file to Tomcat webapp folder
                                sh """
                                    cp ${warFiles[0].path} ${env.TOMCAT_WEBAPP_DIR}
                                """
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
                echo 'Build or deployment failed.'
            }
        }
}
