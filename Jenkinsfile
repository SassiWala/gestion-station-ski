pipeline {
  agent any
  
  stages {
    stage("GIT") {
      steps {
        sh 'git checkout amineBranch'
        sh 'git pull origin amineBranch'
      }
    }

    stage("SONARQUBE") {
      steps {
       sh "mvn sonar:sonar -Dsonar.host.url='http://192.168.33.10:9000' -Dsonar.login=admin -Dsonar.password=sonar"
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
