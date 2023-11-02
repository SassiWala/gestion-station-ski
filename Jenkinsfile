pipeline{
  agent any;
   environment {
      SONAR_URL = 'http://192.168.56.2:9000'
      SONAR_LOGIN ="squ_50c80af6ed98d8aa1585ce3a8895f0defb9d5cd7" // Create a secret credential for your SonarQube token in Jenkins
      //NEXUS_REPO_URL = 'https://192.168.56.2:8081' // Replace with your Nexus repository URL
      //NEXUS_REPO_CREDENTIALS = '4814bb06-f719-48be-b8f2-462839c1fb18' // Use a Jenkins credential ID

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
  /* stage("Deploy to Nexus") {
              steps {
                  withCredentials([usernamePassword(credentialsId: '4814bb06-f719-48be-b8f2-462839c1fb18', usernameVariable: 'admin', passwordVariable: 'nexus')]) {
                      sh "mvn deploy:deploy-file -Durl=${NEXUS_REPO_URL} -DrepositoryId=nexus-releases -Dfile=target/EyaFirstartifact.jar -DgroupId=com.example -DartifactId=your-artifact -Dversion=1.0.0 -Dpackaging=jar -DgeneratePom=true -DgeneratePom.description='Your artifact description' -DrepositoryId=nexus-releases -DuniqueVersion=false -DretryFailedDeploymentCount=3 -DrepositoryId=nexus-releases -DgeneratePom.description='Your artifact description'"
                  }
              }
          } */
}