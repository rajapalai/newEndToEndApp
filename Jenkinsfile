pipeline {
    agent any
    tools {
        maven 'maven'
    }
    stages {
        stage ('Checkout from git repo'){
            steps {
                checkout scmGit(branches: [[name: '*/main']], extensions: [], userRemoteConfigs: [[credentialsId: 'github-jenkins', url: 'https://github.com/rajapalai/newEndToEndApp']])
                sh 'mvn clean install'
            }
        }
        stage ('Unit testing') {
            steps {
                sh 'mvn clean install'
            }
        }
        stage ('Unit testing') {
            steps {
                sh 'mvn clean install'
            }
        }
        stage ('Unit testing') {
            steps {
                sh 'mvn clean install'
            }
        }
        stage ('Integration testing') {
            steps {
                sh 'mvn clean install'
            }
        }
        stage ('Maven build') {
            steps {
                sh 'mvn clean install'
            }
        }
        stage ('SonarQube analysis') {
            steps {
                sh 'mvn clean install'
            }
        }
        stage ('Quality Gate status') {
            steps {
                sh 'mvn clean install'
            }
        }
        stage ('Upload jar file to Nexus repo') {
            steps {
                sh 'mvn clean install'
            }
        }
        stage ('Docker Image Build') {
            steps {
                sh 'mvn clean install'
            }
        }
        stage('Waiting for Approvals') {
            steps{
                input('Test Completed ? Please provide  Approvals for Prod Release ?')
        	}
        }
        stage ('Deploy Artifacts to K8 Production') {
            steps {
                sh 'mvn clean install'
            }
        }
    }
}