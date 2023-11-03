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
  stage("Nexus"){
        steps{
          sh "mvn deploy -Durl=https://192.168.56.2/repository/maven-releases/ -Drepository.username=admin -Drepository.password=nexus -Dmaven.test.skip"
        }
      }
 stage("Docker Image"){
       steps{
         sh "docker build -t eyasaid/gestion-station-ski-1.0 ."
       }
     }
}
}