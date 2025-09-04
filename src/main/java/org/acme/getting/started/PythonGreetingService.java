package org.acme.getting.started;

import com.dylibso.chicory.runtime.HostFunction;
import com.dylibso.chicory.runtime.ImportValues;
import com.dylibso.chicory.runtime.Instance;
import com.dylibso.chicory.wasi.WasiOptions;
import com.dylibso.chicory.wasi.WasiPreview1;
import com.dylibso.chicory.wasm.Parser;
import com.dylibso.chicory.wasm.WasmModule;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.extism.sdk.chicory.Manifest;
import org.extism.sdk.chicory.ManifestWasm;
import org.extism.sdk.chicory.Plugin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

@ApplicationScoped
public class PythonGreetingService {
    private static final Logger LOG = LoggerFactory.getLogger(PythonGreetingService.class);

    @Inject
    private ObjectMapper mapper;

    private final Plugin plugin;

    public PythonGreetingService() {
        try {
            var wasm = ManifestWasm.fromBytes(
                    PythonGreetingService.class.getResourceAsStream("/demos/python/greet.wasm").readAllBytes())
                    .build();
            var manifest = Manifest.ofWasms(wasm).build();
            this.plugin = Plugin.ofManifest(manifest).build();
            LOG.info("Python code loaded successfully");
        } catch (Exception e) {
            LOG.error("Failed to load Python WASM module", e);
            throw new RuntimeException("Failed to load Python WASM module", e);
        }
    }

    public String greeting(String name, String lang) {
        byte[] args;
        try {
            args = mapper.writeValueAsBytes(new String[]{name, lang});
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        var result = new String(
                plugin.call("greet", args),
                StandardCharsets.UTF_8);
        LOG.info("Python execution succeeded: {}", result);
        return result;
    }
}
