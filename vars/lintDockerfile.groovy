// Usage: lintDockerfile()  (assumes Dockerfile at repo root)
def call() {
    echo "Running Hadolint on Dockerfile"
    // prefer local hadolint if present; fallback to docker image
    def rc = sh(script: 'which hadolint > /dev/null 2>&1 || true', returnStatus: true)
    if (rc == 0) {
        sh 'hadolint Dockerfile'
    } else {
        sh 'docker run --rm -i hadolint/hadolint < Dockerfile'
    }
}
