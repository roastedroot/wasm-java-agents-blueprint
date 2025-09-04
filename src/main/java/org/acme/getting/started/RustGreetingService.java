package org.acme.getting.started;

import com.dylibso.chicory.runtime.Instance;
import com.dylibso.chicory.wasm.Parser;
import com.dylibso.chicory.wasm.WasmModule;
import io.quarkus.logging.Log;
import jakarta.enterprise.context.ApplicationScoped;
import java.nio.charset.StandardCharsets;

@ApplicationScoped
public class RustGreetingService {

    private static final WasmModule module =
            Parser.parse(
                    RustGreetingService.class.getResourceAsStream("/demos/rust/hello_agent.wasm"));

    private static int writeCString(Instance instance, String str) {
        byte[] strBytes = str.getBytes(StandardCharsets.UTF_8);
        var strPtr = (int) instance.exports().function("malloc").apply(strBytes.length + 1)[0];
        instance.memory().writeCString(strPtr, str);
        return strPtr;
    }

    public String greeting(String name, String lang) {
        Instance instance = Instance.builder(module).withStart(false).build();
        var greetFn = instance.exports().function("greet");

        var namePtr = writeCString(instance, name);
        var langPtr = writeCString(instance, lang);
        var resultPtr = (int) greetFn.apply(namePtr, langPtr)[0];

        var result = instance.memory().readCString(resultPtr);
        Log.info("Rust agent greeting: " + result);
        return result;
    }
}
