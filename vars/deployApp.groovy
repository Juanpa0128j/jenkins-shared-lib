// Usage: deployApp(envName, imageFullName, dockerCredsId = 'dockerhub-creds')
// envName: 'main' or 'dev'
def call(String envName, String imageFullName, String dockerCredsId = 'dockerhub-creds') {
    def container = (envName == 'main') ? 'myapp-main' : 'myapp-dev'
    def port = (envName == 'main') ? '3000' : '3001'

    echo "Deploying ${imageFullName} to ${envName} as ${container} on port ${port}"

    withCredentials([usernamePassword(credentialsId: dockerCredsId,
                                     usernameVariable: 'DOCKER_USER',
                                     passwordVariable: 'DOCKER_PASS')]) {
        sh """
            echo "${DOCKER_PASS}" | docker login -u "${DOCKER_USER}" --password-stdin

            # stop and remove only the container for this env
            CID=\$(docker ps -q --filter "name=${container}")
            if [ ! -z "\$CID" ]; then
                docker stop \$CID || true
                docker rm \$CID || true
            fi

            docker pull ${imageFullName}
            docker run -d --name ${container} -p ${port}:3000 ${imageFullName}

            docker logout
        """
    }
}
