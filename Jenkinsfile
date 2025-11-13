pipeline {
    agent any

    tools {
        jdk 'JDK21'
    }

    options {
        disableConcurrentBuilds()
    }

    triggers {
        githubPush()
    }

    environment {
        DOCKER_CREDENTIALS = credentials('docker-hub-credentials')
        DOCKER_IMAGE = "${DOCKER_CREDENTIALS_USR}/${env.DOCKER_APP_NAME}"
    }

    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }

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
                        # 이미지 태그 치환
                        sed -i "s|__APP_IMAGE_TAG__|${DOCKER_IMAGE}:latest|g" docker-compose.yml
                
                        # EC2에 배포 디렉토리 생성 및 권한 설정
                        ssh -o StrictHostKeyChecking=no ubuntu@${EC2_HOST} "
                            mkdir -p ~/deployment
                            chmod 755 ~/deployment
                        "
                
                        # 파일 전송
                        scp -o StrictHostKeyChecking=no ${WORKSPACE}/docker-compose.yml ubuntu@${EC2_HOST}:~/deployment/
                        scp -o StrictHostKeyChecking=no ${WORKSPACE}/.env ubuntu@${EC2_HOST}:~/deployment/
                
                        # Docker Compose 배포 실행
                        ssh -o StrictHostKeyChecking=no ubuntu@${EC2_HOST} "
                            cd ~/deployment
                            sudo docker-compose down || true
                            sudo docker-compose pull
                            sudo docker-compose up -d
                    
                            # 정리 작업
                            sudo docker container prune -f
                            sudo docker image prune -f
                    
                            # 배포 상태 확인
                            sudo docker-compose ps
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
