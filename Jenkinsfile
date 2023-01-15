pipeline {
    agent any
    tools {
        maven 'maven'
    }
    stages {
        stage ('Checkout from git repo'){
            steps {
                checkout scmGit(branches: [[name: '*/main']], extensions: [], userRemoteConfigs: [[credentialsId: 'github-jenkins', url: 'https://github.com/rajapalai/newEndToEndApp']])
                sh 'mvn clean install -DskipTests'
            }
        }
    }
}