pipeline {
    agent any

    options {
        disableConcurrentBuilds()
    }

    triggers {
        githubPush()
    }

    environment {
        DOCKER_CREDENTIALS = credentials('docker-hub-credentials')
        DOCKER_IMAGE = "${DOCKER_CREDENTIALS_USR}/${env.DOCKER_APP_NAME}"
        JAVA_HOME = '/usr/lib/jvm/java-21-amazon-corretto.x86_64'
        PATH = "/usr/lib/jvm/java-21-amazon-corretto.x86_64/bin:${env.PATH}"
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
                    echo "=== Java Environment ==="
                    echo "JAVA_HOME: $JAVA_HOME"
                    java -version
                    
                    echo "=== Starting Gradle Build ==="
                    chmod +x gradlew
                    
                    # Gradle에게 Java 위치 명시적으로 전달
                    ./gradlew clean build -x test --no-daemon \
                      -Dorg.gradle.java.home=$JAVA_HOME
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
                        ssh -o StrictHostKeyChecking=no ec2-user@${EC2_HOST} "
                            mkdir -p ~/deployment
                            chmod 755 ~/deployment
                        "
                
                        # 파일 전송
                        scp -o StrictHostKeyChecking=no ${WORKSPACE}/docker-compose.yml ec2-user@${EC2_HOST}:~/deployment/
                        scp -o StrictHostKeyChecking=no ${WORKSPACE}/.env ec2-user@${EC2_HOST}:~/deployment/
                
                        # docker-compose 배포 실행
                        ssh -o StrictHostKeyChecking=no ec2-user@${EC2_HOST} "
                            cd ~/deployment
                            sudo docker-compose down || true
                            sudo docker-compose pull
                            sudo docker-compose up -d
                    
                            # 정리 작업
                            sudo docker container prune -f
                            sudo docker image prune -f
                    
                            # 배포 상태 확인
                            sudo docker compose ps
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
