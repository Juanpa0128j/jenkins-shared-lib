def call(String containerName, String image, String port) {
    sh """
    # Get all containers (running or stopped) with this name
    CID=\$(docker ps -aq --filter "name=${containerName}")
    if [ ! -z "\$CID" ]; then
        echo "Stopping old container ${containerName}..."
        docker stop \$CID || true
        docker rm \$CID || true
    fi

    # Pull the latest image
    docker pull ${image}

    # Run new container
    docker run -d --name ${containerName} -p ${port}:3000 ${image}
    """
}
