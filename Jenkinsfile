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
      }
    }
    stage("SonarQube"){
      steps{
        sh "mvn sonar:sonar -Dsonar.host.url=${SONAR_URL} -Dsonar.login=${SONAR_LOGIN}"
      }

    }
   // stage("JUnit and Mockito"){
     // steps{
       // sh "mvn test"
      //}
    //}
    stage("Nexus"){
      steps{
        sh "mvn deploy -Dmaven.test.skip -Drepository.username=admin -Drepository.password=nexus"
      }
    }
    //  stage("Docker Image"){
    //   steps{
    //     sh "docker build -t wsassi/gestion-station-ski-1.0 ."
    //   }
    // }
    // stage("Docker HUB"){
    //   steps{
    //     sh ' ' '
    //     docker login wsassi 
    //     docker push wsassi/gestion-station-ski-1.0
    //     ' ' '
    //   }
    // }
    
  }
}
