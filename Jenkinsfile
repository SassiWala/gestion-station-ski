pipeline {
    agent any

    environment {
        registryCredentials = "NexusCredentials"
        registry = "192.168.56.2:8081/"
    }

    stages {
        stage("GIT") {
            steps {
                script {
                    // Checkout and pull from the Git repository
                    sh 'git checkout WHBranch'
                    sh 'git pull origin WHBranch'

                    // Check for changes in the Angular project repository
                    if (fileExists('front-gestion-ski')) {
                        echo "Checking for changes in Angular project..."
                        sh 'git -C front-gestion-ski fetch'
                        def hasChanges = sh(script: 'git -C front-gestion-ski rev-list HEAD...origin/main --count', returnStatus: true) != 0
                        if (hasChanges) {
                            echo "Pulling changes in Angular project..."
                            sh 'git -C front-gestion-ski pull origin main'
                        } else {
                            echo "No changes in Angular project. Skipping clone and build."
                        }
                    } else {
                        // Clone the Angular project repository
                        sh 'git clone https://github.com/xRayzox/Front-gestion-ski front-gestion-ski'
                    }
                }
            }
        }

        stage("MAVEN BUILD") {
            steps {
                sh 'mvn clean install'
            }
        }

        stage("SONARQUBE") {
            steps {
                withCredentials([string(credentialsId: 'Sonar_Cred', variable: 'SONAR_TOKEN')]) {
                    sh "mvn sonar:sonar -Dsonar.token=$SONAR_TOKEN"
                }
            }
        }

        stage("MOCKITO") {
            steps {
                sh "mvn test"
            }
        }

        stage("NEXUS") {
            steps {
                sh "mvn deploy"
            }
        }

        stage("Build Angular Project") {
            steps {
                script {
                    // Move to the Angular project directory
                    dir("front-gestion-ski") {
                        sh "docker build -t rayzox/front-waelhcine-5erpbi6-g4:latest ."
                    }
                }
            }
        }

        stage("BUILD DOCKER IMAGE") {
            steps {
                withCredentials([usernamePassword(credentialsId: 'User', usernameVariable: 'DOCKER_USERNAME', passwordVariable: 'DOCKER_PASSWORD')]) {
                    sh "docker build -t $DOCKER_USERNAME/waelhcine-5erpbi6-g4-gestion-station-ski:latest --build-arg JAR_URL=http://192.168.56.2:8081/repository/devops/tn/esprit/spring/gestion-station-ski/1.0/gestion-station-ski-1.0.jar ."
                }
            }
        }

        stage('Deploy Docker Image') {
            steps {
                withCredentials([usernamePassword(credentialsId: 'User', usernameVariable: 'DOCKER_USERNAME', passwordVariable: 'DOCKER_PASSWORD')]) {
                    sh "docker login -u $DOCKER_USERNAME -p $DOCKER_PASSWORD"
                    sh "docker push $DOCKER_USERNAME/waelhcine-5erpbi6-g4-gestion-station-ski"
                }
            }
        }

        stage('Docker Compose') {
            steps {
                sh 'docker-compose up -d'
            }
        }
    }

    post {
        success {
            mail bcc: '', body: "<b>Example</b><br>Project: ${env.JOB_NAME} <br>Build Number: ${env.BUILD_NUMBER} <br> URL de build: ${env.BUILD_URL}", cc: '', charset: 'UTF-8', from: '', mimeType: 'text/html', replyTo: '', subject: "SUCCESS MSG: Project name -> ${env.JOB_NAME}", to: "wael.hcine@esprit.tn";
        }
        failure {
            mail bcc: '', body: "<b>Example</b><br>Project: ${env.JOB_NAME} <br>Build Number: ${env.BUILD_NUMBER} <br> URL de build: ${env.BUILD_URL}", cc: '', charset: 'UTF-8', from: '', mimeType: 'text/html', replyTo: '', subject: "ERROR MSG: Project name -> ${env.JOB_NAME}", to: "wael.hcine@esprit.tn";
        }
    }
}
