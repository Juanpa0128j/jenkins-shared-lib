// Usage: buildAndPushImage(imageFullName, dockerCredsId)
def call(String imageFullName, String dockerCredsId = 'dockerhub-creds') {
    echo "Building and pushing ${imageFullName}"
    withCredentials([usernamePassword(credentialsId: dockerCredsId,
                                     usernameVariable: 'DOCKER_USER',
                                     passwordVariable: 'DOCKER_PASS')]) {
        sh """
            echo "${DOCKER_PASS}" | docker login -u "${DOCKER_USER}" --password-stdin
            docker build -t ${imageFullName} .
            docker push ${imageFullName}
            docker logout
        """
    }
}
