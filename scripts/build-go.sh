#! /bin/bash
set -euxo pipefail

SCRIPT_DIR=$( cd -- "$( dirname -- "${BASH_SOURCE[0]}" )" &> /dev/null && pwd )
PROJECT_ROOT=$( cd -- "${SCRIPT_DIR}/.." &> /dev/null && pwd )

TINYGO_PATH="${SCRIPT_DIR}/tools/tinygo/bin/tinygo"

# Check if tinygo is already available
if [ ! -f "$TINYGO_PATH" ]; then
    echo "TinyGo not found at $TINYGO_PATH"
    echo "Downloading TinyGo using get_tinygo.sh..."
    bash "${SCRIPT_DIR}/get_tinygo.sh"
else
    echo "TinyGo found at $TINYGO_PATH"
fi

# Verify tinygo is now available
if [ ! -f "$TINYGO_PATH" ]; then
    echo "Error: Failed to obtain TinyGo"
    exit 1
fi

RESOURCES_DIR="${PROJECT_ROOT}/src/main/resources"
GO_SOURCE_DIR="${RESOURCES_DIR}/demos/go"

# Create go source directory if it doesn't exist
mkdir -p "${GO_SOURCE_DIR}"

echo "Building Go source files to WASM..."

# Build greet.go to WASM
if [ -f "${GO_SOURCE_DIR}/greet.go" ]; then
    echo "Building greet.go..."
    export GOOS=wasip1
    export GOARCH=wasm
    "${TINYGO_PATH}" build -o "${GO_SOURCE_DIR}/greet.wasm" "${GO_SOURCE_DIR}/greet.go"
    echo "Built greet.wasm successfully"
else
    echo "Warning: greet.go not found, skipping..."
fi

echo "Go WASM build completed successfully!"
