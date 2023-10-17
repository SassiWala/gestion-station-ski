pipeline{
  agent any;
  environment {
    SONAR_URL = 'http://192.168.33.10:9000' // Replace with your SonarQube URL
    SONAR_LOGIN ="squ_90839aa46030b9296ab6aeeb0fb0ae0bc26567a9" // Create a secret credential for your SonarQube token in Jenkins
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
