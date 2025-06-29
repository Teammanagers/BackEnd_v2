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
        // Checkout, Create ENV file, Build, Docker Build & Push 스테이지는 동일
        stage('Checkout') { steps { checkout scm } }
        stage('Create ENV file') { steps { withCredentials([file(credentialsId: 'env-file', variable: 'ENV_FILE')]) { sh 'cp "$ENV_FILE" .env' } } }
        stage('Build') { steps { sh 'chmod +x gradlew && ./gradlew clean build -x test' } }
        stage('Docker Build & Push') { steps { sh 'docker login -u $DOCKER_CREDENTIALS_USR -p $DOCKER_CREDENTIALS_PSW && docker build -t $DOCKER_IMAGE:latest . && docker push $DOCKER_IMAGE:latest' } }

        // FIX: Deploy 스테이지를 올바르게 수정
        stage('Deploy') {
            steps {
                sshagent(['ec2-ssh-key']) {

                    // 1. Jenkins -> EC2 파일 복사
                    sh 'scp -o StrictHostKeyChecking=no ${WORKSPACE}/docker-compose.yml ubuntu@${EC2_HOST}:~/'
                    sh 'scp -o StrictHostKeyChecking=no ${WORKSPACE}/.env ubuntu@${EC2_HOST}:~/'

                    // 2. EC2에 접속하여 명령어 실행
                    sh '''
                    ssh -o StrictHostKeyChecking=no ubuntu@${EC2_HOST} "

                        # 홈 디렉터리로 이동
                        cd ~/

                        # FIX: `sudo` 명령어 바로 앞에 환경 변수를 선언하여 전달
                        APP_IMAGE_TAG=${DOCKER_IMAGE}:latest sudo docker-compose pull

                        # FIX: `up` 명령어에도 동일하게 적용
                        APP_IMAGE_TAG=${DOCKER_IMAGE}:latest sudo docker-compose --env-file ./.env up -d --force-recreate

                        # 사용하지 않는 리소스 정리
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
