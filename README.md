<p align="center">
  <picture>
    <!-- When the user prefers dark mode, show the white logo -->
    <source media="(prefers-color-scheme: dark)" srcset="./images/Blueprint-logo-white.png">
    <!-- When the user prefers light mode, show the black logo -->
    <source media="(prefers-color-scheme: light)" srcset="./images/Blueprint-logo-black.png">
    <!-- Fallback: default to the black logo -->
    <img src="./images/Blueprint-logo-black.png" width="35%" alt="Project logo"/>
  </picture>
</p>

<div align="center">

![Java](https://img.shields.io/badge/Java-21%2B-orange)
![Quarkus](https://img.shields.io/badge/Quarkus-3.26.1-blue)
![WebAssembly](https://img.shields.io/badge/WebAssembly-WASM-purple)
[![License](https://img.shields.io/badge/license-Apache%202.0-blue.svg)](LICENSE)
[![](https://dcbadge.limes.pink/api/server/YuMNeuKStr?style=flat)](https://discord.gg/YuMNeuKStr) <br>

[Blueprints Hub](https://developer-hub.mozilla.ai/)
|| [Contributing](CONTRIBUTING.md)

</div>

# WASM Java Agents Blueprint

Inspired by [wasm-agents-blueprint](https://github.com/mozilla-ai/wasm-agents-blueprint) and [wasm-browser-agents-blueprint](https://github.com/hwclass/wasm-browser-agents-blueprint) experimenting with WebAssembly agents, we wanted to explore running these powerful AI systems within an alternative, and extremely popular runtime: the Java Virtual Machine (JVM).
The JVM is one of the most widely used systems in the world, powering everything from enterprise applications to mobile development, making it an ideal platform for deploying AI agents at scale.

This blueprint demonstrates how to build AI agents using WebAssembly modules within a Java application, leveraging the JVM's robust ecosystem and enterprise-grade capabilities.
You can run agents written in multiple languages (Rust, Go, Python, JavaScript) seamlessly without leaving the JVM, combining the performance benefits of WebAssembly with the reliability and maturity of the Java platform.

## Quick Start

### Prerequisites

- **Java 21+** (required for Vector API support)

### Running the Application

1. **Clone the repository:**
   ```bash
   git clone https://github.com/mozilla-ai/wasm-java-agents-blueprint.git
   cd wasm-java-agents-blueprint
   ```

2. **Start the application in development mode:**
   ```bash
   ./mvnw quarkus:dev
   ```

3. **Test the agents:**
   ```bash
   # Test Rust agent
   curl -X PUT "http://localhost:8080/hello/rust/en/Alice" \
        -H "Content-Type: text/plain" \
        --data "Tell me about yourself"

   # Test Go agent  
   curl -X PUT "http://localhost:8080/hello/go/fr/Bob" \
        -H "Content-Type: text/plain" \
        --data "What can you do?"

   # Test Python agent
   curl -X PUT "http://localhost:8080/hello/py/de/Charlie" \
        -H "Content-Type: text/plain" \
        --data "Explain your capabilities"

   # Test JavaScript agent
   curl -X PUT "http://localhost:8080/hello/js/es/Diana" \
        -H "Content-Type: text/plain" \
        --data "How do you work?"
   ```

### Building for Production

**JVM Mode:**
```bash
./mvnw package
java -jar target/quarkus-app/quarkus-run.jar
```

## How It Works

This blueprint showcases a multi-language WebAssembly architecture running within the Java Virtual Machine:

### 1. **Multi-Language WASM Integration**
- **Rust**: High-performance systems programming with memory safety
- **Go**: Efficient concurrent operations and clean syntax
- **Python**: Flexible scripting via PyO3 compilation to WASM
- **JavaScript**: Dynamic scripting with QuickJS integration

### 2. **Java Enterprise Integration**
- **Quarkus Framework**: Modern, cloud-native Java framework with fast startup times
- **LangChain4j**: Java AI framework for LLM integration
- **Chicory**: Pure Java WebAssembly runtime

### 3. **AI Agent Architecture**
- **LLM Integration**: Built-in support for local and cloud-based language models
- **Context-Aware Processing**: Agents receive contextual information about greetings and user prompts
- **Multi-Language Support**: Agents can respond in different languages based on user preferences
- **RESTful API**: Simple HTTP interface for agent interaction

## Project Structure

```
wasm-java-agents-blueprint/
├── src/main/java/org/acme/getting/started/
│   ├── GreetingResource.java          # REST API endpoints
│   ├── ChatService.java               # LLM integration service
│   ├── RustGreetingService.java       # Rust WASM agent
│   ├── GoGreetingService.java         # Go WASM agent
│   ├── PythonGreetingService.java     # Python WASM agent
│   └── JsGreetingService.java         # JavaScript agent
├── src/main/resources/demos/
│   ├── rust/                          # Rust WASM modules
│   ├── go/                            # Go WASM modules
│   ├── python/                        # Python WASM modules
│   └── js/                            # JavaScript modules
└── scripts/                           # Build scripts for WASM modules
```

## Available Agents

### Rust Agent (`/hello/rust/{lang}/{name}`)
Compiling Rust to WebAssembly is just a blissfull experience.

### Go Agent (`/hello/go/{lang}/{name}`)
Compiled to Wasm thanks to [TinyGo](https://tinygo.org/).

### Python Agent (`/hello/py/{lang}/{name}`)
Leveraging [Extism SDK](https://github.com/extism/chicory-sdk) and the [Python PDK](https://github.com/extism/python-pdk) (which uses [PyO3](https://github.com/PyO3/pyo3))

### JavaScript Agent (`/hello/js/{lang}/{name}`)
JavaScript is compiled and executed on the fly thanks to [QuickJs](https://github.com/roastedroot/quickjs4j).

## Building WASM Modules

The project includes the scripts used to build WASM modules from source:

```bash
./scripts/build-rust.sh
./scripts/build-go.sh  
./scripts/build-python.sh
```

There are also scripts to download toolchains and dependencies, you might need to tweak those to match your os/architecture.

## Features

- **Multi-Language Support**: Run agents written in Rust, Go, Python, and JavaScript
- **Enterprise Java**: Built on Quarkus for cloud-native deployment
- **WebAssembly Integration**: Seamless WASM module execution within the JVM
- **LLM Integration**: Built-in support for local and cloud language models
- **RESTful API**: Simple HTTP interface for agent interaction

## License

This project is licensed under the Apache 2.0 License. See the [LICENSE](LICENSE) file for details.

## Contributing

Contributions are welcome! To get started, you can check out the [CONTRIBUTING.md](CONTRIBUTING.md) file.

## Acknowledgments

This Blueprint is built on top of:
- [Quarkus](https://quarkus.io/) - Modern Java framework
- [LangChain4j](https://github.com/langchain4j/langchain4j) - Java AI framework
- [Chicory](https://github.com/dylibso/chicory) - Pure Java WebAssembly runtime
