# Getting Started

This Blueprint leverages [Chicory](https://github.com/dylibso/chicory) to run WebAssembly modules directly within the Java Virtual Machine, enabling you to use multi-language AI agents without the need for external runtime dependencies.

## LangChain4j

LangChain4j has proven to be exceptionally effective for Java-based AI applications, providing seamless integration with both local and cloud-based language models. The framework's modular architecture makes it easy to switch between different model providers, while its type-safe API ensures reliable communication with AI services. 

**TinyLlama-1.1B-Chat-v1.0**: A compact model that works well for demo purposes and can run efficiently on development machines.

**JLama Integration**: The integration with JLama (Java implementation of LLaMA) provides optimized inference and memory management specifically designed for the JVM environment. Both JLama and Chicory run entirely within the JVM boundaries, ensuring everything is self-contained within the Java ecosystem.

## Prerequisites

- **System requirements**:
    - OS: Windows, macOS, or Linux
    - Java 21+ (required for Vector API support)

- **Model Access**:
    - A running LLM server compatible with LangChain4j (e.g., [Ollama](https://ollama.com/), [LM Studio](https://lmstudio.ai/))
    - A pre-downloaded model with tool-calling capabilities (tested with: [`qwen3:8b`](https://ollama.com/library/qwen3:8b), [`Devstral Small 2507:24b`](https://lmstudio.ai/models/mistralai/devstral-small-2507))

- **OR**:
    - API key for OpenAI models

## Quick Start

The WebAssembly agents available in this repository use LangChain4j to communicate with any compatible LLM server. Here's how you can run these agents in your Java application:

1. **Clone the repository:**
   ```bash
   git clone https://github.com/mozilla-ai/wasm-java-agents-blueprint.git
   cd wasm-java-agents-blueprint
   ```

2. **Configure your model (required):**
   - For local models, update `src/main/resources/application.properties`:
     ```properties
     quarkus.langchain4j.jlama.chat-model.model-id=your-model-id
     quarkus.langchain4j.jlama.chat-model.base-url=http://localhost:11434
     ```
   - For OpenAI models, set your API key:
     ```properties
     quarkus.langchain4j.openai.chat-model.api-key=your-api-key
     ```

3. **Start serving a local model (if using local models)**
   - For Ollama, run with appropriate context length and CORS:
   ```bash
   OLLAMA_CONTEXT_LENGTH=40000 OLLAMA_ORIGINS="*" ollama serve
   ```
   - For LM Studio, enable CORS in the Developer/model serving section.

4. **Start the application:**
   ```bash
   ./mvnw quarkus:dev
   ```

5. **Test the agents:**
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

---

## Available Agents

### Rust Agent (`/hello/rust/{lang}/{name}`)
High-performance agent built with Rust and compiled to WebAssembly. Optimized for speed and memory efficiency.

### Go Agent (`/hello/go/{lang}/{name}`)
Concurrent agent leveraging Go's goroutines and efficient memory management. Great for handling multiple requests.

### Python Agent (`/hello/py/{lang}/{name}`)
Flexible agent using Python compiled to WebAssembly via PyO3. Ideal for rapid prototyping and complex logic.

### JavaScript Agent (`/hello/js/{lang}/{name}`)
Dynamic agent running JavaScript within the JVM using QuickJS4j. Perfect for runtime flexibility and dynamic behavior.

---

## Building for Production

**JVM Mode:**
```bash
./mvnw package
java -jar target/quarkus-app/quarkus-run.jar
```

**Native Mode (requires GraalVM):**
```bash
./mvnw package -Dnative
./target/getting-started-1.0.0-SNAPSHOT-runner
```

---

## Next Steps

- **[Customization Guide](customization.md)**: Learn how to modify agents, add tools, and integrate new models
- **[Architecture Overview](architecture.md)**: Understand the system design and component interactions

---

## Troubleshooting

- Check the [Troubleshooting](https://github.com/mozilla-ai/wasm-java-agents-blueprint/blob/main/README.md#troubleshooting) section in our repo
- Need more help? Join our [Discord community](https://discord.gg/YuMNeuKStr) for support!
