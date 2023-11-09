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
        withCredentials([string(credentialsId: 'Sonar_Cred', variable: 'SONAR_TOKEN')]) {
          sh "mvn sonar:sonar -Dsonar.login=$SONAR_TOKEN"
        }
      }
    }
    stage("MOCKITO") {
      steps {
        sh "mvn test "
      }
    }
    stage("NEXUS") {
      steps {
        sh "mvn deploy"
      }
    }
    stage("BUILD DOCKER IMAGE") {
      steps {
        script {
          def pomXml = readFile('pom.xml')
          def xml = new XmlSlurper().parseText(pomXml)

          def appName = xml.artifactId.text()
          def appVersion = xml.version.text()

          withCredentials([usernamePassword(credentialsId: 'User', usernameVariable: 'DOCKER_USERNAME', passwordVariable: 'DOCKER_PASSWORD')]) {
            sh "docker build -t $DOCKER_USERNAME/$appName:$appVersion ."
          }
        }
      }
    }
    stage('Deploy Docker Image') {
      steps {
        withCredentials([usernamePassword(credentialsId: 'User', usernameVariable: 'DOCKER_USERNAME', passwordVariable: 'DOCKER_PASSWORD')]) {
          sh "docker login -u $DOCKER_USERNAME -p $DOCKER_PASSWORD"
          sh "docker push $DOCKER_USERNAME/$appName:$appVersion"
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
