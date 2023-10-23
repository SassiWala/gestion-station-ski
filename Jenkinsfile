pipeline{
  agent any;
  environment {
    SONAR_URL = 'http://192.168.56.2:9000' // Replace with your SonarQube URL
    SONAR_LOGIN ="squ_bd42277fc1fd98a5f0f7dede133282384ba66b94" // Create a secret credential for your SonarQube token in Jenkins
  }
  stages{
    stage("Git"){
      steps{
        sh 'git checkout WHBranch'
        sh 'git pull origin WHBranch'
      }
    }
    stage("Maven Clean and Compile"){
      steps{
        sh 'mvn clean compile'
      }
    }
    stage("SonarQube"){
      steps{
        sh "mvn sonar:sonar -Dsonar.host.url=${SONAR_URL} -Dsonar.login=${SONAR_LOGIN}"
      }

    }
    stage("JUnit and Mockito"){
      steps{
        sh "mvn test"
      }
    }
  }
}