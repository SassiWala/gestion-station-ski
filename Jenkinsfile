pipeline {
  agent any
  
  stages {
    stage("GIT") {
      steps {
        sh 'git checkout WHBranch'
        sh 'git pull origin WHBranch'
      }
    }/*
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
    }*/
    stage("Publish to Nexus Repository Manager") {
  steps {
    script {
      def pom = readMavenPom file: "/pom.xml" // Make sure the path is correct
      def filesByGlob = findFiles(glob: "target/*.${pom.packaging}")
      if (filesByGlob.size() > 0) {
        def artifactPath = filesByGlob[0].path
        def artifactExists = fileExists artifactPath
        if (artifactExists) {
          echo "Uploading ${artifactPath} to Nexus Repository Manager"
          nexusArtifactUploader(
            nexusVersion: "nexus3", // Replace with your Nexus version
            protocol: "http",    // Replace with http or https
            nexusUrl: "192.168.56.2:8080",          // Replace with your Nexus URL
            groupId: pom.groupId,
            version: VERSION,             // Replace with your version
            repository: "maven-releases", // Replace with your repository ID
            credentialsId: "189c55d4-781a-3a19-8f5c-350811c7f116", // Replace with your credentials ID
            artifacts: [
              [artifactId: pom.artifactId, classifier: '', file: artifactPath, type: pom.packaging],
              [artifactId: pom.artifactId, classifier: '', file: "pom.xml", type: "pom"]
            ]
          )
        } else {
          error "Artifact file not found: ${artifactPath}"
        }
      } else {
        error "No artifacts found for deployment"
      }
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
  }
}
