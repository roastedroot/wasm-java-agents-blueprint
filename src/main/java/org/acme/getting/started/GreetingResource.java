package org.acme.getting.started;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/hello")
public class GreetingResource {

    @Inject
    ChatService chatService;

    @Inject
    JsGreetingService jsService;
    @Inject
    GoGreetingService goService;
    @Inject
    RustGreetingService rustService;
    @Inject
    PythonGreetingService pythonService;

    //    invoke with curl with something similar to:
    //    curl -X PUT "http://localhost:8080/hello/js/one/two" \
    //            -H "Content-Type: text/plain" \
    //            --data "additional user input"
    @PUT
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/{pl}/{lang}/{name}")
    public String greeting(
            @PathParam("pl") String pl,
            @PathParam("lang") String lang,
            @PathParam("name") String name,
            String body) {
        String greeting = switch (pl) {
            case "js"   -> jsService.greeting(name, lang);
            case "go"   -> goService.greeting(name, lang);
            case "rust" -> rustService.greeting(name, lang);
            case "py" -> pythonService.greeting(name, lang);
            default     -> throw new UnsupportedOperationException("Language %s is not supported".formatted(pl));
        };

        return chatService.greetWithLLM(name, lang, greeting, body);
    }

}