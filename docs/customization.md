# 🎨 **Customization Guide**

This Blueprint is designed to be flexible and easily adaptable to your specific needs. This guide will walk you through some key areas you can customize to make the Blueprint your own.

---

## 📝 **Learn how agents work**

All the agent code is in the service classes and WASM modules. The beauty of this architecture is that everything runs within the JVM, giving you full control over the execution environment.

You can examine how each agent is implemented:
- **Rust Agent**: `RustGreetingService.java` + `/demos/rust/` WASM modules
- **Go Agent**: `GoGreetingService.java` + `/demos/go/` WASM modules  
- **Python Agent**: `PythonGreetingService.java` + `/demos/python/` WASM modules
- **JavaScript Agent**: `JsGreetingService.java` + `/demos/js/` JavaScript files

Each service class shows how to load and interact with WebAssembly modules using different runtimes (Chicory, Extism, QuickJS4j).

## 💡 **Try different models**

The application uses LangChain4j for LLM integration, which supports a wide variety of models and providers:

- **Local Models**: Ollama, LM Studio, Hugging Face
- **Cloud Models**: OpenAI, Anthropic, Azure OpenAI
- **Custom Models**: Any LangChain4j-compatible provider

Experiment with different models to find what works best for your use case:

```properties
# For local models
quarkus.langchain4j.jlama.chat-model.model-id=qwen3:8b
quarkus.langchain4j.jlama.chat-model.base-url=http://localhost:11434

# For OpenAI
quarkus.langchain4j.openai.chat-model.api-key=your-key
quarkus.langchain4j.openai.chat-model.model-name=gpt-4o

# For Anthropic
quarkus.langchain4j.anthropic.chat-model.api-key=your-key
quarkus.langchain4j.anthropic.chat-model.model-name=claude-3-sonnet-20240229
```

## 🛠️ **Build new tools and agents**

### Adding New WASM Agents

1. **Create your WASM module** in the appropriate language
2. **Add a new service class** following the pattern of existing services
3. **Register the service** in `GreetingResource.java`
4. **Add the endpoint** to the REST API

Example for a new C++ agent:
```java
@ApplicationScoped
public class CppGreetingService {
    // Implementation following the same pattern
}
```

### Adding New Tools

Extend the `ChatService` to include additional tools and capabilities:

```java
public String greetWithLLM(String name, String lang, String greeting, String userPrompt) {
    // Add custom tool processing here
    var enhancedPrompt = processWithCustomTools(userPrompt);
    // ... rest of the implementation
}
```

## 🤝 **Contributing to the Blueprint**

Want to help improve or extend this Blueprint? Check out the **[Contributions Guide](https://github.com/mozilla-ai/wasm-java-agents-blueprint/blob/main/CONTRIBUTING.md)** to see how you can contribute your ideas, code, or feedback to make this Blueprint even better!

### Areas for Contribution
- **New Language Support**: Add agents in other languages (C++, C#, Kotlin, etc.)
- **Performance Optimizations**: Improve WASM loading and execution speed
- **Additional Tools**: Create new tools and capabilities for agents
- **Documentation**: Improve guides and examples
- **Testing**: Add comprehensive test coverage
