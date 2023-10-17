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
        sh 'mvn clean verify sonar:sonar -Dsonar.login=sqa_186ded1457469978ae48bb65ba02511ebfe97762'
      }
    }
  }
}
