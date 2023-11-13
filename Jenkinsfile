pipeline{
  agent any;
  environment {
    SONAR_URL = 'http://192.168.33.10:9000' // Replace with your SonarQube URL
    SONAR_LOGIN ="squ_d606c542b447d9f15a8b27a0fa1d7d5cdd96c3a1" // Create a secret credential for your SonarQube token in Jenkins
  }
  stages{
    stage("Git"){
      steps{
        sh 'git checkout WSBranch'
        sh 'git pull origin WSBranch'
      }
    }
    stage("Maven Clean and Compile"){
      steps{
        sh 'mvn clean compile'
        sh 'mvn package -DskipTests'
      }
    }
    stage("SonarQube"){
      steps{
        sh "mvn sonar:sonar -Dsonar.host.url=${SONAR_URL} -Dsonar.login=${SONAR_LOGIN}"
      }

    }
   
    stage("Nexus"){
      steps{
        sh "mvn deploy -Durl=https://192.168.33.10/repository/maven-releases/ -Drepository.username=admin -Drepository.password=nexus -Dmaven.test.skip"
      }
    }
     stage("Docker Image"){
      steps{
        sh "docker build -t wsassi/gestion-station-ski-1.0 ."
      }
    }
    stage("Docker HUB"){
      steps{
        sh "docker login "
        sh " docker push wsassi/gestion-station-ski-1.0 "
      }
    }
      stage("Docker Composer"){
     steps{
       sh "docker compose up -d"
      }
    }
    stage("JUnit and Mockito"){
     steps{
       sh "mvn test"
      }
    }
    
  }
post {
always {
  script {
    if (currentBuild.currentResult == 'FAILURE') {
      step([$class: 'Mailer', notifyEveryUnstableBuild: true, recipients: "sassiwale1999@gmail.com", sendToIndividuals: true])
    }
  }
}
}
