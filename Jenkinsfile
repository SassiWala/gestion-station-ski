pipeline {
  agent any ;
   environment {
     Sonar_Login='admin'
     Sonar_Pwd= 'sonar'
   }
  
  stages {
    stage("GIT") {
      steps {
        sh 'git checkout amineBranch'
        sh 'git pull origin amineBranch'
      }
    }
    stage("Maven Clean and Compile"){
      steps{
        sh 'mvn clean compile'
      }
    }
    stage("SONARQUBE") {
      steps {
       sh "mvn sonar:sonar -Dsonar.host.url='http://192.168.33.10:9000' -Dsonar.login=${Sonar_Login} -Dsonar.password=${Sonar_Pwd}"
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
        sh 'docker build -t amineBranch/gestion-station-ski:latest .'
      }
    }
    stage('Deploy Docker Image') {
      steps {
        withCredentials([string(credentialsId: 'pass', variable: 'DOCKER_PASSWORD')]) {
          sh '''
            docker login -u siboz69 -p $DOCKER_PASSWORD
            docker push siboz69/gestion-station-ski
          '''
        }
      }
    }
    stage('Docker Compose') {
      steps {
        sh 'docker-compose up -d'
      }
    }
  }
}
