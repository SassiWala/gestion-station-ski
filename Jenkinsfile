pipeline {
  agent any
  
  stages {
    stage("GIT") {
      steps {
        sh 'git checkout WHBranch'
        sh 'git pull origin WHBranch'
      }
    }
    /*
    stage("MAVEN BUILD") {
      steps {
        sh 'mvn clean install'
      }
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
    }*/
    stage("NEXUS DEPLOY") {
            steps {
                script {
                    def pom = readMavenPom file: 'pom.xml'
                    def groupId = pom.getGroupId()
                    def artifactId = pom.getArtifactId()
                    def version = pom.getVersion()
                    def artifactFile = "${artifactId}-${version}.jar"
                    def repositoryId = 'maven-releases' // Replace with your Nexus repository ID
                    def nexusUrl = 'https://your-nexus-url/repository/' + repositoryId

                    sh "mvn deploy:deploy-file -DgroupId=${groupId} -DartifactId=${artifactId} -Dversion=${version} -Dpackaging=jar -Dfile=target/${artifactFile} -DrepositoryId=${repositoryId} -Durl=${nexusUrl}"
                }
            }
        }
    stage("BUILD DOCKER IMAGE") {
      steps {
        withCredentials([usernamePassword(credentialsId: 'User', usernameVariable: 'DOCKER_USERNAME', passwordVariable: 'DOCKER_PASSWORD')]) {
          sh "docker build -t $DOCKER_USERNAME/waelhcine-5erpbi6-g4-gestion-station-ski:latest ."
        }
      }
    }
    stage('Deploy Docker Image') {
      steps {
        withCredentials([usernamePassword(credentialsId: 'User', usernameVariable: 'DOCKER_USERNAME', passwordVariable: 'DOCKER_PASSWORD')]) {
          sh "docker login -u $DOCKER_USERNAME -p $DOCKER_PASSWORD"
          sh "docker push $DOCKER_USERNAME/waelhcine-5erpbi6-g4-gestion-station-ski"
        }
      }
    }
    stage('Docker Compose') {
      steps {
        sh 'docker-compose up -d'
      }
    }
     post {
  always {
    echo "Pipeline Status: ${currentBuild.currentResult}"
    mail to: "wael.hcine@esprit.tn",
         subject: "Pipeline Status: ${currentBuild.currentResult}",
         body: "Pipeline Status: ${currentBuild.currentResult}"
         
  }
}}
}
