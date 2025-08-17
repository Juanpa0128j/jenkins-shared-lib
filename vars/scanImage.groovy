// Usage: scanImage(imageFullName, severityFail = 'HIGH,CRITICAL')
def call(String imageFullName, String severityFail = 'HIGH,CRITICAL') {
    echo "Scanning ${imageFullName} with Trivy"
    // prefer installed trivy; otherwise docker image
    def rc = sh(script: 'which trivy > /dev/null 2>&1 || true', returnStatus: true)
    if (rc == 0) {
        sh "trivy image --exit-code 1 --severity ${severityFail} ${imageFullName}"
    } else {
        sh "docker run --rm -v /var/run/docker.sock:/var/run/docker.sock aquasec/trivy:latest image --exit-code 1 --severity ${severityFail} ${imageFullName}"
    }
}
