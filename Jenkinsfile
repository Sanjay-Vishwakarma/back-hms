pipeline {
    agent any

    stages {

        stage('Build') {
            steps {
                sh 'mvn clean package'
            }
        }

        stage('Docker Build') {
            steps {
                sh 'docker build -t hotel-management .'
            }
        }

        stage('Deploy') {
            steps {
                sh '''
                docker rm -f hotel-management || true

                docker run -d \
                --name hotel-management \
                -p 8090:8090 \
                hotel-management
                '''
            }
        }
    }
}