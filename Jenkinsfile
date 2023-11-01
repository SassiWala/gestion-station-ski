pipeline {
  agent any
  
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
       sh "mvn sonar:sonar -Dsonar.login=admin -Dsonar.password=sonar"
      }
    }
    stage("MOCKITO") {
      steps {
        sh "mvn test -Dtest=tn.esprit.spring.services.SkierServiceMockTest"
      }
    }
    stage("NEXUS") {
      steps {
        sh "mvn deploy"
      }
    }
    stage("BUILD DOCKER IMAGE") {
      steps {
        sh 'docker build -t rayzox/gestion-station-ski:latest .'
      }
    }
    stage('Deploy Docker Image') {
      steps {
        withCredentials([string(credentialsId: 'pass', variable: 'DOCKER_PASSWORD')]) {
          sh '''
            docker login -u rayzox -p $DOCKER_PASSWORD
            docker push rayzox/gestion-station-ski
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