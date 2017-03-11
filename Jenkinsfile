node {
    stage('SCM') {
        git 'https://github.com/samuelgarciastk/MobileOffice.git'
    }
    stage('QA') {
        sh 'sonar-scanner'
    }
}
