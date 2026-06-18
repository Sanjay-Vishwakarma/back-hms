pipeline {
    agent any

    environment {
        IMAGE_NAME = "hotel-management"
        APP_CONTAINER = "hotel-management"
        MONGO_CONTAINER = "hotel-management-mongo"
        DOCKER_NETWORK = "hotel-management-net"
        MONGO_DATABASE = "hotel_db"
        TAG = "${BUILD_NUMBER}"
    }

    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Test') {
            steps {
                sh './mvnw test'
            }
        }

        stage('Build') {
            steps {
                sh './mvnw clean package -DskipTests'
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
                    docker network create ${DOCKER_NETWORK} || true

                    docker rm -f ${APP_CONTAINER} || true

                    docker ps -a --format '{{.Names}}' | grep -qx ${MONGO_CONTAINER} || docker run -d \
                        --name ${MONGO_CONTAINER} \
                        --network ${DOCKER_NETWORK} \
                        --restart unless-stopped \
                        -v ${MONGO_CONTAINER}-data:/data/db \
                        mongo:7

                    docker network connect ${DOCKER_NETWORK} ${MONGO_CONTAINER} || true

                    docker run -d \
                        --name ${APP_CONTAINER} \
                        --network ${DOCKER_NETWORK} \
                        --restart unless-stopped \
                        -e SPRING_DATA_MONGODB_URI=mongodb://${MONGO_CONTAINER}:27017/${MONGO_DATABASE} \
                        -p 8090:8090 \
                        ${IMAGE_NAME}:${TAG}
                '''
            }
        }
    }
}
