// vars/lintDockerfile.groovy
// Usage: lintDockerfile() or lintDockerfile('path/to/Dockerfile')
def call(String dockerfilePath = 'Dockerfile') {
    echo "Linting Dockerfile with hadolint (local preferred, fallback to docker image)..."
    sh """
        # prefer local hadolint if present
        if command -v hadolint >/dev/null 2>&1; then
            hadolint "${dockerfilePath}"
        else
            # if Docker is available, run hadolint container
            if command -v docker >/dev/null 2>&1 && docker info >/dev/null 2>&1; then
                docker run --rm -i hadolint/hadolint < "${dockerfilePath}"
            else
                echo "ERROR: hadolint is not installed and Docker is not available to run the hadolint container." >&2
                echo "Install hadolint on the agent or ensure Docker is available." >&2
                exit 2
            fi
        fi
    """
}
