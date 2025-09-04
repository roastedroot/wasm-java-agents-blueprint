#! /bin/bash
set -euxo pipefail

SCRIPT_DIR=$( cd -- "$( dirname -- "${BASH_SOURCE[0]}" )" &> /dev/null && pwd )
PROJECT_ROOT=$( cd -- "${SCRIPT_DIR}/.." &> /dev/null && pwd )

EXTISM_PY_PATH="${SCRIPT_DIR}/tools/extism-py/bin/extism-py"

# Check if tinygo is already available
if [ ! -f "$EXTISM_PY_PATH" ]; then
    echo "Extism Py not found at $EXTISM_PY_PATH"
    echo "Downloading Extism Py using get_extism.sh..."
    bash "${SCRIPT_DIR}/get_extism.sh"
else
    echo "Extism Py found at $EXTISM_PY_PATH"
fi

# Verify tinygo is now available
if [ ! -f "$EXTISM_PY_PATH" ]; then
    echo "Error: Failed to obtain Extism Py"
    exit 1
fi

RESOURCES_DIR="${PROJECT_ROOT}/src/main/resources"
PYTHON_SOURCE_DIR="${RESOURCES_DIR}/demos/python"

# Create go source directory if it doesn't exist
mkdir -p "${PYTHON_SOURCE_DIR}"

echo "Building Python source files to WASM..."

# Build greet.py to WASM
if [ -f "${PYTHON_SOURCE_DIR}/greet.py" ]; then
    echo "Building greet.py..."
    export PATH=${PATH}:${SCRIPT_DIR}/tools/wasi-sdk-25.0-x86_64-linux/bin

    export EXTISM_PYTHON_WASI_DEPS_DIR=${SCRIPT_DIR}/tools/extism-py/share/extism-py

    "${EXTISM_PY_PATH}" "${PYTHON_SOURCE_DIR}/greet.py" -o "${PYTHON_SOURCE_DIR}/greet.wasm"
    echo "Built greet.wasm successfully"
else
    echo "Warning: greet.py not found, skipping..."
fi

echo "Python WASM build completed successfully!"
