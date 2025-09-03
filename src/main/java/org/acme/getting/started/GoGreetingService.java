package org.acme.getting.started;

import com.dylibso.chicory.runtime.ImportValues;
import com.dylibso.chicory.runtime.Instance;
import com.dylibso.chicory.wasi.WasiOptions;
import com.dylibso.chicory.wasi.WasiPreview1;
import com.dylibso.chicory.wasm.Parser;
import com.dylibso.chicory.wasm.WasmModule;
import io.quarkus.logging.Log;
import jakarta.enterprise.context.ApplicationScoped;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@ApplicationScoped
public class GoGreetingService {
    private static final Logger LOG = LoggerFactory.getLogger(GoGreetingService.class);

    private static final WasmModule module = Parser.parse(GoGreetingService.class.getResourceAsStream("/demos/go/greet.wasm"));

    private static int writeCString(Instance instance, String str) {
        byte[] strBytes = str.getBytes(StandardCharsets.UTF_8);
        var strPtr = (int) instance.exports().function("malloc").apply(strBytes.length + 1)[0];
        instance.memory().writeCString(strPtr, str);
        return strPtr;
    }

    public String greeting(String name, String lang) {
        WasiOptions wasiOpts = WasiOptions.builder().inheritSystem().build();
        try (WasiPreview1 wasi = WasiPreview1.builder().withOptions(wasiOpts).build()) {

            Instance instance =
                    Instance.builder(module)
                            .withImportValues(
                                    ImportValues.builder()
                                            .addFunction(wasi.toHostFunctions())
                                            .build())
                            .withStart(false)
                            .build();
            var greetFn = instance.exports().function("greet");

            var namePtr = writeCString(instance, name);
            var langPtr = writeCString(instance, lang);
            var resultPtr = (int) greetFn.apply(namePtr, langPtr)[0];

            var result = instance.memory().readCString(resultPtr);
            Log.info("Go agent greeting: " + result);
            return result;
        }
    }

}
