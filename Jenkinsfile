pipeline{
  agent any;
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
        sh 'mvn clean verify sonar:sonar -Dsonar.host.url=http://192.168.33.10:9000/ -Dsonar.login=admin -Dsonar.password=sonarqube'
      }
    }
  }
}
