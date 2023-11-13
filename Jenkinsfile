pipeline {
  agent any
  environment {
    SONAR_URL = 'http://192.168.33.10:9000'

  }

  stages {
    stage("GIT") {
      steps {
        sh 'git checkout amineBranch'
        sh 'git pull origin amineBranch'
      }
    }
    stage("Maven Clean and Compile") {
      steps {
        sh 'mvn clean compile'
      }
    }

    stage("SONARQUBE") {
        steps {
            withCredentials([string(credentialsId: 'sonar_token_pass', variable: 'SONAR_TOKEN')]) {
                sh '''
                    mvn sonar:sonar -Dsonar.host.url=${SONAR_URL} -Dsonar.login=$SONAR_TOKEN
                '''
            }
        }
    }


    stage("JUnit and Mockito") {
      steps {
        sh "mvn test"
      }
    }

    stage("Nexus") {
      steps {
        sh "mvn deploy -Durl=https://192.168.33.10/repository/maven-releases/ -Drepository.username=admin -Drepository.password=nexus -Dmaven.test.skip"
      }
    }

    /*
    stage("BUILD JAR FILE") {
      steps {
        sh 'mvn package -Dmaven.test.skip'  // Add this step to build the JAR file
      }
    }
    */

    stage("BUILD DOCKER IMAGE") {
      steps {
        sh 'docker build -t siboz69/gestion-station-ski:latest .'
      }
    }
    stage('Deploy Docker Image') {
      steps {
        withCredentials([string(credentialsId: 'docker_hub_pass', variable: 'DOCKER_PASSWORD')]) {
          sh '''
            docker login -u siboz69 -p $DOCKER_PASSWORD
            docker push siboz69/gestion-station-ski
          '''
        }
      }
    }
    stage('Docker Compose') {
      steps {
        sh 'docker compose up -d'
      }
    }
  }
  post {
      success {
          mail to: 'amine.bouzouita@esprit.tn',
               subject: "Build Successful: ${env.JOB_NAME} #${env.BUILD_NUMBER}",
               body: """
                     <html>
                     <body>
                         <h2>Build Successful</h2>
                         <p><b>Project:</b> ${env.JOB_NAME}</p>
                         <p><b>Build Number:</b> ${env.BUILD_NUMBER}</p>
                         <p><b>Build URL:</b> <a href="${env.BUILD_URL}">${env.BUILD_URL}</a></p>
                     </body>
                     </html>
                     """,
               mimeType: 'text/html'
      }
      failure {
          mail to: 'amine.bouzouita@esprit.tn',
               subject: "Build Failed: ${env.JOB_NAME} #${env.BUILD_NUMBER}",
               body: """
                     <html>
                     <body>
                         <h2 style="color: red;">Build Failed</h2>
                         <p><b>Project:</b> ${env.JOB_NAME}</p>
                         <p><b>Build Number:</b> ${env.BUILD_NUMBER}</p>
                         <p><b>Build URL:</b> <a href="${env.BUILD_URL}">${env.BUILD_URL}</a></p>
                     </body>
                     </html>
                     """,
               mimeType: 'text/html'
      }
  }

}
