package org.acme.getting.started;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.quarkus.logging.Log;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.nio.charset.StandardCharsets;
import org.extism.sdk.chicory.Manifest;
import org.extism.sdk.chicory.ManifestWasm;
import org.extism.sdk.chicory.Plugin;

@ApplicationScoped
public class PythonGreetingService {

    @Inject private ObjectMapper mapper;

    private final Plugin plugin;

    public PythonGreetingService() {
        try {
            var wasm =
                    ManifestWasm.fromBytes(
                                    PythonGreetingService.class
                                            .getResourceAsStream("/demos/python/greet.wasm")
                                            .readAllBytes())
                            .build();
            var manifest = Manifest.ofWasms(wasm).build();
            this.plugin = Plugin.ofManifest(manifest).build();
        } catch (Exception e) {
            throw new RuntimeException("Failed to load Python WASM module", e);
        }
    }

    public String greeting(String name, String lang) {
        byte[] args;
        try {
            args = mapper.writeValueAsBytes(new String[] {name, lang});
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        var result = new String(plugin.call("greet", args), StandardCharsets.UTF_8);
        Log.info("Python agent greeting: " + result);
        return result;
    }
}
