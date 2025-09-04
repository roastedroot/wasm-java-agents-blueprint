#! /bin/bash
set -euxo pipefail

PYTHON_SDK_VERSION="0.1.5"

SCRIPT_DIR=$( cd -- "$( dirname -- "${BASH_SOURCE[0]}" )" &> /dev/null && pwd )

PYTHON_SDK_ARCHIVE="extism-py-x86_64-linux-v${PYTHON_SDK_VERSION}.tar.gz"
PYTHON_SDK_URL="https://github.com/extism/python-pdk/releases/download/v${PYTHON_SDK_VERSION}/${PYTHON_SDK_ARCHIVE}"

mkdir -p ${SCRIPT_DIR}/tools

(
    cd ${SCRIPT_DIR}/tools
    echo "Downloading PYTHON_SDK..."
    wget "$PYTHON_SDK_URL"
    tar xvf "$PYTHON_SDK_ARCHIVE"
    rm $PYTHON_SDK_ARCHIVE
)
