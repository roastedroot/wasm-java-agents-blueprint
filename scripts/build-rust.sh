#! /bin/bash
set -euxo pipefail

SCRIPT_DIR=$( cd -- "$( dirname -- "${BASH_SOURCE[0]}" )" &> /dev/null && pwd )
PROJECT_ROOT=$( cd -- "${SCRIPT_DIR}/.." &> /dev/null && pwd )

RESOURCES_DIR="${PROJECT_ROOT}/src/main/resources"
RUST_SOURCE_DIR="${RESOURCES_DIR}/demos/rust"

(
    cd ${RUST_SOURCE_DIR}
    cargo build --release --target wasm32-unknown-unknown
)

rm -f ${RUST_SOURCE_DIR}/hello_agent.wasm
cp ${RUST_SOURCE_DIR}/target/wasm32-unknown-unknown/release/hello_agent.wasm ${RUST_SOURCE_DIR}/hello_agent.wasm

echo "Rust WASM build completed successfully!"
