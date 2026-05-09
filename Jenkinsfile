pipeline {
    agent {
        label 'agent-1'
    }

    tools {
        jdk 'jdk17'
        maven 'mvn39'
    }

    stages {
        stage('Checkout') {
            steps {
                echo 'Checking out source code'
                cleanWs deleteDirs: true, notFailBuild: true
                checkout([$class: 'GitSCM',
                    branches: [[name: '*/master']],
                    userRemoteConfigs: [[url: 'https://github.com/Devopsprace/grocery-store-java-aws.git']]
                ])
            }
        }

        stage('Build Info') {
            steps {
                sh '''
                    echo "Job Name     : $JOB_NAME"
                    echo "Build Number : $BUILD_NUMBER"
                    echo "Workspace    : $WORKSPACE"
                    echo "Branch       : master"
                '''
            }
        }

        stage('Maven Version') {
            steps {
                sh 'java -version'
                sh 'mvn -version'
            }
        }

        stage('Test') {
            when {
                expression { return !params.SKIP_TESTS }
            }
            steps {
                sh 'mvn test -DskipTests'
            }
        }

        stage('Build') {
            steps {
                sh 'mvn clean package'
            }
        }
    }
    post {
        success {
            echo 'Pipeline completed successfully.'
        }
        failure {
            echo 'Pipeline failed. Please check console logs.'
        }
        cleanup {
            cleanWs deleteDirs: true, notFailBuild: true
        }
    }
}
