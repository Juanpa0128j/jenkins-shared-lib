def call(String containerName, String image, String port) {
    sh """
    CID=\$(docker ps -q --filter "name=${containerName}")
    if [ ! -z "\$CID" ]; then
        echo "Stopping old container ${containerName}..."
        docker stop \$CID || true
        docker rm \$CID || true
    fi

    docker pull ${image}
    docker run -d --name ${containerName} -p ${port}:3000 ${image}
    """
}
