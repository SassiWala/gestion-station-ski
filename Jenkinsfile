pipeline{
  agent any;
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