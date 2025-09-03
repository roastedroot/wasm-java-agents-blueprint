#! /bin/bash
set -euxo pipefail

TINYGO_VERSION="0.39.0"

SCRIPT_DIR=$( cd -- "$( dirname -- "${BASH_SOURCE[0]}" )" &> /dev/null && pwd )

TINYGO_ARCHIVE="tinygo${TINYGO_VERSION}.linux-amd64.tar.gz"
TINYGO_URL="https://github.com/tinygo-org/tinygo/releases/download/v${TINYGO_VERSION}/${TINYGO_ARCHIVE}"

mkdir -p ${SCRIPT_DIR}/tools

(
    cd ${SCRIPT_DIR}/tools
    echo "Downloading tinygo..."
    wget "$TINYGO_URL"
    tar xvf "$TINYGO_ARCHIVE"
    rm $TINYGO_ARCHIVE
)
