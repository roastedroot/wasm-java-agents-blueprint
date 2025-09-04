package org.acme.getting.started;

import dev.langchain4j.data.message.UserMessage;
import dev.langchain4j.model.chat.ChatModel;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.util.Map;

@ApplicationScoped
public class ChatService {

    @Inject private ChatModel chatModel;

    private Map<String, String> languageNames =
            Map.of(
                    "en", "English",
                    "fr", "French",
                    "de", "German",
                    "es", "Spanish");

    public String greetWithLLM(String name, String lang, String greeting, String userPrompt) {
        // Construct a context-aware prompt that includes the greeting
        var contextPrompt =
                "The greeting "
                        + greeting
                        + " was generated in "
                        + languageNames.getOrDefault(lang, lang)
                        + " for "
                        + name
                        + ". "
                        + userPrompt;

        var message = new UserMessage("user", contextPrompt);
        return chatModel.chat(message).aiMessage().text();
    }
}
