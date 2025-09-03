package org.acme.getting.started;

import io.quarkus.logging.Log;
import io.roastedroot.quickjs4j.annotations.GuestFunction;
import io.roastedroot.quickjs4j.annotations.Invokables;
import io.roastedroot.quickjs4j.core.Engine;
import io.roastedroot.quickjs4j.core.Runner;
import jakarta.enterprise.context.ApplicationScoped;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@ApplicationScoped
public class JsGreetingService {
    private static final Logger LOG = LoggerFactory.getLogger(JsGreetingService.class);

    private static final String jsAgent;

    static {
        try {
            jsAgent = new String(
                    JsGreetingService.class.getResourceAsStream("/demos/js/greet.js").readAllBytes(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Invokables
    interface JsApi {
        @GuestFunction
        String greet(String name, String lang);
    }

    private final Engine engine =
            Engine.builder().addInvokables(JsApi_Invokables.toInvokables()).build();

    public String greeting(String name, String lang) {
        try (Runner runner = Runner.builder().withEngine(engine).build()) {
            JsApi jsApi = JsApi_Invokables.create(jsAgent, runner);
            String result = jsApi.greet(name, lang);

            if (!runner.stdout().isEmpty()) {
                LOG.info("js stdout: {}", runner.stdout());
            }
            if (!runner.stderr().isEmpty()) {
                LOG.info("js stderr: {}", runner.stderr());
            }

            Log.info("Js agent greeting: " + result);
            return result;
        }
    }

}
