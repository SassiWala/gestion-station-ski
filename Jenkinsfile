pipeline {
  agent any
   environment {
        
        registryCredentials = "NexusCredentials"
        registry = "192.168.56.2:8081/"
       
       
    }
  stages {
    stage("GIT") {
      steps {
        sh 'git checkout WHBranch'
        sh 'git pull origin WHBranch'
      }
    }
    stage("MAVEN BUILD") {
      steps {
        sh 'mvn clean install'
      }
    }
    stage("SONARQUBE") {
      steps {
        withCredentials([string(credentialsId: 'Sonar_Cred', variable: 'SONAR_TOKEN')]) {
          sh "mvn sonar:sonar -Dsonar.token=$SONAR_TOKEN"
        }
      }
    }
    stage("MOCKITO") {
      steps {
        sh "mvn test"
      }
    }
    stage("NEXUS") {
      steps {
        sh "mvn deploy"
      }
    }
    stage("BUILD DOCKER IMAGE") {
      steps {
        withCredentials([usernamePassword(credentialsId: 'User', usernameVariable: 'DOCKER_USERNAME', passwordVariable: 'DOCKER_PASSWORD')]) {
          sh "docker build -t $DOCKER_USERNAME/waelhcine-5erpbi6-g4-gestion-station-ski:latest --build-arg JAR_URL=http://192.168.56.2:8081/repository/devops/tn/esprit/spring/gestion-station-ski/1.0/gestion-station-ski-1.0.jar ."
        }
      }
    }
  stage('Deploy Docker Image to Nexus') {
            steps {
                withCredentials([string(credentialsId: NEXUS_CREDENTIALS_ID, variable: 'NEXUS_CREDENTIALS')]) {
                        sh "docker login -u ${NEXUS_CREDENTIALS_USR} -p ${NEXUS_CREDENTIALS_PSW} $NEXUS_URL"
                        sh "docker tag $DOCKER_IMAGE_NAME:$DOCKER_IMAGE_TAG $NEXUS_URL/$NEXUS_REPOSITORY/$DOCKER_IMAGE_NAME:$DOCKER_IMAGE_TAG"
                        sh "docker push $NEXUS_URL/$NEXUS_REPOSITORY/$DOCKER_IMAGE_NAME:$DOCKER_IMAGE_TAG"
                        sh "docker logout $NEXUS_URL"
            }
        }
    stage('Docker Compose') {
      steps {
        sh 'docker-compose up -d'
      }
    }
  }
  post {
               success {
                   mail bcc: '', body: "<b>Example</b><br>Project: ${env.JOB_NAME} <br>Build Number: ${env.BUILD_NUMBER} <br> URL de build: ${env.BUILD_URL}", cc: '', charset: 'UTF-8', from: '', mimeType: 'text/html', replyTo: '', subject: "SUCCESS MSG: Project name -> ${env.JOB_NAME}", to: "wael.hcine@esprit.tn";
               }
               failure {
                   mail bcc: '', body: "<b>Example</b><br>Project: ${env.JOB_NAME} <br>Build Number: ${env.BUILD_NUMBER} <br> URL de build: ${env.BUILD_URL}", cc: '', charset: 'UTF-8', from: '', mimeType: 'text/html', replyTo: '', subject: "ERROR MSG: Project name -> ${env.JOB_NAME}", to: "wael.hcine@esprit.tn";
               }
           }
}

