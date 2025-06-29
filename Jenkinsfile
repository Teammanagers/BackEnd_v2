pipeline {
    agent any

    triggers {
        githubPush()
    }

    environment {
        DOCKER_CREDENTIALS = credentials('docker-hub-credentials')
        DOCKER_IMAGE = "${DOCKER_CREDENTIALS_USR}/${env.DOCKER_APP_NAME}"
    }

    stages {
        stage('Checkout') { steps { checkout scm } }

        stage('Create ENV file') {
            steps {
                withCredentials([file(credentialsId: 'env-file', variable: 'ENV_FILE')]) {
                    sh 'cp "$ENV_FILE" .env'
                }
            }
        }

        stage('Build') {
            steps {
                sh '''
                    chmod +x gradlew
                    ./gradlew clean build -x test
                '''
            }
        }

        stage('Docker Build & Push') {
            steps {
                sh '''
                docker login -u $DOCKER_CREDENTIALS_USR -p $DOCKER_CREDENTIALS_PSW
                docker build -t $DOCKER_IMAGE:latest .
                docker push $DOCKER_IMAGE:latest
                '''
            }
        }

        stage('Deploy') {
            steps {
                sshagent(['ec2-ssh-key']) {
                    sh '''
                    ssh -o StrictHostKeyChecking=no ubuntu@${EC2_HOST} "
                        mkdir -p ~/app/

                        scp -o StrictHostKeyChecking=no ${WORKSPACE}/docker-compose.yml ubuntu@${EC2_HOST}:~/app/
                        scp -o StrictHostKeyChecking=no ${WORKSPACE}/.env ubuntu@${EC2_HOST}:~/app/

                        cd ~/app/

                        export APP_IMAGE_TAG=${DOCKER_IMAGE}:latest

                        sudo docker-compose pull

                        sudo docker-compose --env-file ./.env up -d

                        sudo docker container prune -f
                        sudo docker image prune -f
                    "
                    '''
                }
            }
        }
    }

    post {
        always {
            cleanWs()
        }
    }
}
