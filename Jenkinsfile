pipeline {
  agent any
   environment {
        
        registryCredentials = "NexusCredentials"
        registry = "192.168.56.2:8081/"
       
       
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
        withCredentials([string(credentialsId: 'Sonar_Cred', variable: 'SONAR_TOKEN')]) {
          sh "mvn sonar:sonar -Dsonar.token=$SONAR_TOKEN"
        }
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
        withCredentials([usernamePassword(credentialsId: 'User', usernameVariable: 'DOCKER_USERNAME', passwordVariable: 'DOCKER_PASSWORD')]) {
          sh "docker build -t $DOCKER_USERNAME/waelhcine-5erpbi6-g4-gestion-station-ski:latest --build-arg JAR_URL=http://192.168.56.2:8081/repository/devops/tn/esprit/spring/gestion-station-ski/1.0/gestion-station-ski-1.0.jar ."
        }
      }
    }
  stage('Deploy Docker Image to Nexus') {
    steps {
        script {
            def dockerImageName = "rayzox/waelhcine-5erpbi6-g4-gestion-station-ski"
            def nexusRepositoryUrl = "http://192.168.56.2:8081/repository/devops/"

            // Login to Docker registry (Nexus in this case)
            withCredentials([usernamePassword(credentialsId: 'NexusCredentials', usernameVariable: 'NEXUS_USERNAME', passwordVariable: 'NEXUS_PASSWORD')]) {
                sh "docker login -u $NEXUS_USERNAME -p $NEXUS_PASSWORD $nexusRepositoryUrl"
            }

            // Tag the Docker image with Nexus repository URL
            sh "docker tag $dockerImageName $nexusRepositoryUrl/$dockerImageName"

            // Push the Docker image to Nexus repository
            sh "docker push $nexusRepositoryUrl/$dockerImageName"
        }
    }
}
    stage('Docker Compose') {
      steps {
        sh 'docker-compose up -d'
      }
    }
  }
  post {
    always {
        script {
            currentBuild.result = currentBuild.result ?: 'SUCCESS'
        }
    }
    success {
        mail to: 'wael.hcine@esprit.tn',
             subject: "SUCCESS MSG: Project name -> ${env.JOB_NAME}",
             body: """
                    <b>Example</b><br>
                    Project: ${env.JOB_NAME} <br>
                    Build Number: ${env.BUILD_NUMBER} <br>
                    URL de build: ${env.BUILD_URL}
                  """,
             mimeType: 'text/html',
             charset: 'UTF-8'
    }
    failure {
        script {
            def failedStage = currentBuild.rawBuild.result
            mail to: 'wael.hcine@esprit.tn',
                 subject: "ERROR MSG: Project name -> ${env.JOB_NAME}",
                 body: """
                        <b>Example</b><br>
                        Project: ${env.JOB_NAME} <br>
                        Build Number: ${env.BUILD_NUMBER} <br>
                        URL de build: ${env.BUILD_URL} <br>
                        Failed Stage: ${failedStage}
                      """,
                 mimeType: 'text/html',
                 charset: 'UTF-8'
        }
    }
}

}
