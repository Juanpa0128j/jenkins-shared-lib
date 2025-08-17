def call(String containerName, String image, String port) {
    sh """
    # Stop the old container if it exists
    CID=\$(docker ps -q --filter "name=${containerName}")
    if [ ! -z "\$CID" ]; then
        echo "Stopping old container ${containerName}..."
        docker stop \$CID || true
        docker rm \$CID || true
    fi

    # Pull the latest image
    docker pull ${image}

    # Run the new container
    docker run -d --name ${containerName} -p ${port}:3000 ${image}
    """
}
