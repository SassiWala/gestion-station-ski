pipeline {
  agent any ;
   environment {
      SONAR_URL = 'http://192.168.33.10:9000'
      SONAR_LOGIN ="sqa_0195b8763773ca27eace50fa32c957f987ee7c3f" 
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
       sh "mvn sonar:sonar -Dsonar.host.url=${SONAR_URL} -Dsonar.login=${SONAR_LOGIN}"
      }
    }

    stage("Nexus"){
            steps{
              sh "mvn deploy -Durl=http://192.168.33.10:8081/repository/maven-releases/ -Drepository.username=admin -Drepository.password=nexus -Dmaven.test.skip"
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
            docker push amineBranch/gestion-station-ski
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
