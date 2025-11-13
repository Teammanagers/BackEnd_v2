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
                    # Java 환경 설정 - 실제 경로 사용
                    export JAVA_HOME=/usr/lib/jvm/java-21-amazon-corretto.x86_64
                    export PATH=$JAVA_HOME/bin:$PATH
                    
                    # 환경 확인
                    echo "=== Java Environment ==="
                    echo "JAVA_HOME: $JAVA_HOME"
                    java -version
                    which java
                    
                    # Gradle 빌드
                    echo "=== Starting Gradle Build ==="
                    chmod +x gradlew
                    ./gradlew clean build -x test --no-daemon
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
                        sed -i "s|__APP_IMAGE_TAG__|${DOCKER_IMAGE}:latest|g" docker-compose.yml
                
                        ssh -o StrictHostKeyChecking=no ubuntu@${EC2_HOST} "
                            mkdir -p ~/deployment
                            chmod 755 ~/deployment
                        "
                
                        scp -o StrictHostKeyChecking=no ${WORKSPACE}/docker-compose.yml ubuntu@${EC2_HOST}:~/deployment/
                        scp -o StrictHostKeyChecking=no ${WORKSPACE}/.env ubuntu@${EC2_HOST}:~/deployment/
                
                        ssh -o StrictHostKeyChecking=no ubuntu@${EC2_HOST} "
                            cd ~/deployment
                            sudo docker-compose down || true
                            sudo docker-compose pull
                            sudo docker-compose up -d
                            sudo docker container prune -f
                            sudo docker image prune -f
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
