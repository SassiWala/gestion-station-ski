pipeline{
  agent any;
   environment {
      SONAR_URL = 'http://192.168.56.2:9000'
      SONAR_LOGIN ="squ_50c80af6ed98d8aa1585ce3a8895f0defb9d5cd7" // Create a secret credential for your SonarQube token in Jenkins
    }
  stages{
    stage("Git"){
      steps{
        sh 'git checkout ESaidBranch'
        sh 'git pull origin ESaidBranch'
      }
    }
    stage("Maven Clean and Compile"){
      steps{
        sh 'mvn clean compile'
      }
    }
  }
}