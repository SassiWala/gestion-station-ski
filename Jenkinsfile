pipeline {
  agent any
  tools {
    // Define the Maven tool to use
    maven 'M2_HOME'
  }
  environment {
    SONARQUBE_URL = 'http://192.168.56.2:9000'
    NEXUS_URL = 'http://192.168.56.2:8081'
    NEXUS_VERSION = "nexus3"
    NEXUS_PROTOCOL = "http"
    NEXUS_CREDENTIAL_ID = "nexus-user"
    NEXUS_REPOSITORY = "maven-releases"
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
    stage("Publish to Nexus Repository Manager") {
      steps {
        script {
          pom = readMavenPom file: "pom.xml"
          filesByGlob = findFiles(glob: "target/*.${pom.packaging}")
          echo "${filesByGlob[0].name} ${filesByGlob[0].path} ${filesByGlob[0].directory} ${filesByGlob[0].length} ${filesByGlob[0].lastModified}"
          artifactPath = filesByGlob[0].path
          artifactExists = fileExists artifactPath
          if (artifactExists) {
            echo "*** File: ${artifactPath}, group: ${pom.groupId}, packaging: ${pom.packaging}, version ${pom.version}"
            nexusArtifactUploader(
              nexusVersion: NEXUS_VERSION,
              protocol: NEXUS_PROTOCOL,
              nexusUrl: NEXUS_URL,
              groupId: pom.groupId,
              version: pom.version,
              repository: NEXUS_REPOSITORY,
              credentialsId: NEXUS_CREDENTIAL_ID,
              artifacts: [
                [artifactId: pom.artifactId,
                 classifier: '',
                 file: artifactPath,
                 type: pom.packaging],
                [artifactId: pom.artifactId,
                 classifier: '',
                 file: "pom.xml",
                 type: "pom"]
              ]
            )
          } else {
            error "*** File: ${artifactPath}, could not be found"
          }
        }
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
