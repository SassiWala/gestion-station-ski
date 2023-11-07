pipeline {
  agent any
  tools {
    // Define the Maven tool to use
    maven 'M2_HOME'
  }
  environment {
    SONARQUBE_URL = 'http://192.168.56.2:9000'
    NEXUS_URL = 'http://192.168.56.2:8081'
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
        sh "mvn sonar:sonar -Dsonar.login=admin -Dsonar.password=sonar -Dsonar.host.url=$SONARQUBE_URL"
      }
    }
    stage("MOCKITO") {
      steps {
        sh "mvn test -Dtest=tn.esprit.spring.services.SkierServiceMockTest"
      }
    }
      stage("Nexus"){
      steps{
        sh "mvn deploy -Drepository.username=admin -Drepository.password=nexus"
      }
    }
    stage("BUILD DOCKER IMAGE") {
      steps {
        sh 'docker build -t rayzox/WaelHcine-5ERPBI1-G4-gestion-station-ski:latest .'
      }
    }
    stage('Deploy Docker Image') {
      steps {
        withCredentials([string(credentialsId: 'pass', variable: 'DOCKER_PASSWORD')]) {
          sh '''
            docker login -u rayzox -p $DOCKER_PASSWORD
            docker push rayzox/WaelHcine-5ERPBI1-G4-gestion-station-ski
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
