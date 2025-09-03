package org.acme.getting.started;

import com.dylibso.chicory.runtime.ExportFunction;
import com.dylibso.chicory.runtime.HostFunction;
import com.dylibso.chicory.runtime.ImportValues;
import com.dylibso.chicory.runtime.Instance;
import com.dylibso.chicory.wasi.WasiOptions;
import com.dylibso.chicory.wasi.WasiPreview1;
import com.dylibso.chicory.wasm.Parser;
import com.dylibso.chicory.wasm.WasmModule;
import com.dylibso.chicory.wasm.types.FunctionType;
import io.trino.wasm.python.PythonMachine;
import io.quarkus.logging.Log;
import io.roastedroot.zerofs.Configuration;
import io.roastedroot.zerofs.ZeroFs;
import io.trino.wasm.python.Python;
import jakarta.enterprise.context.ApplicationScoped;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileSystem;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static com.dylibso.chicory.wasm.types.ValType.I32;
import static java.nio.charset.StandardCharsets.UTF_8;

@ApplicationScoped
public class PythonGreetingService {
    private static final Logger LOG = LoggerFactory.getLogger(PythonGreetingService.class);

    private static final byte[] pythonAgent;

    static {
        try {
            pythonAgent = PythonGreetingService.class.getResourceAsStream("/demos/python/greet.py").readAllBytes();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String greeting(String name, String lang) {
        try (FileSystem fs =
                     ZeroFs.newFileSystem(
                             Configuration.unix().toBuilder()
                                     .setAttributeViews("unix")
                                     .build())) {
                Path guestRoot = fs.getPath("/guest");
                Files.createDirectories(guestRoot);
                Files.writeString(guestRoot.resolve("guest.py"), "print(\"Hello, world!\")");

                WasiOptions options = WasiOptions.builder()
                        .inheritSystem()
                        .withDirectory(guestRoot.toString(), guestRoot)
                        .build();
                WasiPreview1 wasi = WasiPreview1.builder().withOptions(options).build();

                Instance instance = Instance.builder(Python.load())
                        .withMachineFactory(io.trino.wasm.python.PythonMachine::new)
                        .withImportValues(ImportValues.builder()
                                .addFunction(wasi.toHostFunctions())
                                .addFunction(
                                        new HostFunction(
                                                "trino",
                                                "return_error",
                                                FunctionType.of(List.of(I32, I32, I32, I32, I32), List.of()),
                                                (inst, args) -> {
                                                    throw new UnsupportedOperationException("not implemented");
                                                })
                                )
                                .build())
                        .build();

                var allocate = instance.export("allocate");
                var setup = instance.export("setup");
                var execute = instance.export("execute");
                var deallocate = instance.export("deallocate");


                // Log.info("Python agent greeting: " + result);
                return "TODO";
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
    }

}
