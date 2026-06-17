pipeline {
    agent any

    environment {
        IMAGE_NAME = "hotel-management"
        TAG = "${BUILD_NUMBER}"
    }

    stages {

stage('Checkout') {
    steps {
        checkout scm
    }
}
        stage('Build') {
            steps {
                sh 'mvn clean package'
            }
        }

        stage('Docker Build') {
            steps {
                sh '''
                docker build \
                -t ${IMAGE_NAME}:${TAG} \
                -t ${IMAGE_NAME}:latest .
                '''
            }
        }

        stage('Deploy') {
            steps {
                sh '''
                docker rm -f hotel-management || true

                docker run -d \
                --name hotel-management \
                -p 8090:8090 \
                ${IMAGE_NAME}:${TAG}
                '''
            }
        }
    }
}