pipeline {
    agent any    // 가능한 모든 Jenkins 에이전트에서 실행 가능

    environment {
        // docker-hub-credentials에서 사용자명/비밀번호를 가져옴
        DOCKER_CREDENTIALS = credentials('docker-hub-credentials')
        // 도커 이미지 이름 설정 (사용자명/앱이름 형식)
        DOCKER_IMAGE = "${DOCKER_CREDENTIALS_USR}/${env.DOCKER_APP_NAME}"
    }
    stages {
        stage('Checkout') {
            steps {
                checkout scm    // Git 저장소에서 코드를 가져옴
            }
        }

        stage('Create ENV file') {
            steps {
                withCredentials([file(credentialsId: 'env-file', variable: 'ENV_FILE')]) {
                    sh '''
                        cp "$ENV_FILE" .env
                    '''
                }
            }
        }

        stage('Check ENV') {
            steps {
                withCredentials([file(credentialsId: 'env-file', variable: 'ENV_FILE')]) {
                    sh '''
                        echo "=== ENV File Contents ==="
                        cat "$ENV_FILE"
                        echo "======================="
                    '''
                }
            }
        }

        stage('Build') {
            steps {
                // Gradle 빌드 실행 (테스트 제외)
                sh '''
                    chmod +x gradlew
                    ./gradlew clean build -x test
                '''
            }
        }

        stage('Docker Build & Push') {
            steps {
                // Docker Hub 로그인, 이미지 빌드, 푸시
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
                    // EC2 서버에 SSH로 접속하여 새 도커 이미지 배포
                    sh '''
                    ssh -o StrictHostKeyChecking=no ubuntu@${EC2_HOST} "
                        sudo docker pull $DOCKER_IMAGE:latest
                        sudo docker stop ${DOCKER_APP_NAME} || true
                        sudo docker rm ${DOCKER_APP_NAME} || true
                        sudo docker run -e TZ=Asia/Seoul -d --name ${DOCKER_APP_NAME} -p 8080:8080 $DOCKER_IMAGE:latest
                        sudo docker container prune -f
                    "
                    '''
                }
            }
        }
    }

    post {
        always {
            cleanWs()    // 작업 완료 후 워크스페이스 정리
        }
    }
}
