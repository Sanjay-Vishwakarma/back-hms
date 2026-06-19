pipeline {
agent any


environment {
    IMAGE_NAME = "hotel-management"
    TAG = "${BUILD_NUMBER}"
    DEPLOY_DIR = "/home/sanjay/project/back-end/hotel-management-system"
}

stages {

    stage('Checkout') {
        steps {
            checkout scm
        }
    }

    stage('Build') {
        steps {
            sh 'mvn clean package -DskipTests'
        }
    }

    stage('Prepare Deployment Files') {
        steps {
            sh '''
                mkdir -p ${DEPLOY_DIR}

                cp target/*.jar ${DEPLOY_DIR}/app.jar
                cp Dockerfile ${DEPLOY_DIR}/
            '''
        }
    }

    stage('Docker Build') {
        steps {
            sh '''
                cd ${DEPLOY_DIR}

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
                  --restart unless-stopped \
                  -p 8090:8090 \
                  ${IMAGE_NAME}:${TAG}
            '''
        }
    }
}


}
