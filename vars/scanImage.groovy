// vars/scanImage.groovy
// Usage: scanImage(imageFullName, severityFail = 'HIGH,CRITICAL')
def call(String imageFullName, String severityFail = 'HIGH,CRITICAL') {
    echo "Scanning ${imageFullName} with Trivy (fail on: ${severityFail})"

    sh """
        # prefer installed trivy
        if command -v trivy >/dev/null 2>&1; then
            echo "Using local trivy binary"
            trivy image --exit-code 1 --severity ${severityFail} ${imageFullName}
        else
            echo "Local trivy not found, checking for docker to run trivy container..."
            if command -v docker >/dev/null 2>&1 && docker info >/dev/null 2>&1; then
                echo "Running trivy via Docker image (will mount docker socket to allow local image scanning)"
                docker run --rm -v /var/run/docker.sock:/var/run/docker.sock aquasec/trivy:latest \
                    image --exit-code 1 --severity ${severityFail} ${imageFullName}
            else
                echo "ERROR: trivy is not installed and Docker is not available to run the trivy container." >&2
                echo "Install Trivy on the agent or ensure Docker is available." >&2
                exit 2
            fi
        fi
    """
}
