pipeline {
  agent any

  stages {
    stage('git') {
           steps {
            echo 'Pulling... ';
            git branch: 'AhmedBranch',
            url : 'https://github.com/SassiWala/gestion-station-ski.git';}
        }
    stage("MAVEN BUILD") {
      steps {
        sh 'mvn clean install -Dmaven.test.skip=true'
      }
    }
    stage('mvn sonarqube') {
            steps {
                // Étape pour compiler le projet avec Maven

                sh 'mvn sonar:sonar -Dsonar.login=squ_b091f54c5e91c59f042a9d961042d6f1bff29bf1'

            }
    }
    stage("MOCKITO") {
      steps {
        sh "mvn test -Dtest=tn.esprit.spring.Services.CourseServiceMockTest -DfailIfNoTests=false"
      }
    }
     stage('Nexus Deployment') {
    steps {
        sh 'mvn deploy -DskipTests'  // Exécutez la commande Maven pour le déploiement en sautant les tests
    }
}
    stage("Docker Image"){
      steps{
        sh "docker build -t pmloikju1998/gestion-station-ski-1.0 ."
      }
    }

     stage('Deploy Docker Image') {
      steps {
        withCredentials([string(credentialsId: 'pass', variable: 'DOCKER_PASSWORD')]) {
          sh '''
            docker login -u pmloikju1998 -p $DOCKER_PASSWORD
            docker push pmloikju1998/gestion-station-ski-1.0
          '''
        }
      }
    }

    stage('Docker Compose') {
      steps {
        sh 'docker-compose up -d'
      }
    }

  }}
