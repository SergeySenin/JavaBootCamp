package org.study.bootcamp.mishustin.task22.telegram.filters.v1.api.cli;

import org.study.bootcamp.mishustin.task22.telegram.filters.v1.application.service.MessageProcessor;
import org.study.bootcamp.mishustin.task22.telegram.filters.v1.domain.contract.MessageFilter;

import java.util.*;

public class Demo {
    public static void main(String[] args) {

        MessageProcessor messageProcessor = new MessageProcessor();

        MessageFilter spamBlockFilter = message -> {String normalized = message.toLowerCase(Locale.ROOT);
            return !normalized.contains("spam") && !normalized.contains("спам");
        };
        MessageFilter minLengthFilter = message -> message.trim().length() >= 10;
        MessageFilter emojiBlockFilter = message -> !message.contains("😀");

        List<MessageFilter> filterChain = List.of(spamBlockFilter, minLengthFilter, emojiBlockFilter);

        String[] incomingMessages = {
                "Hi!",
                "This is spam! Subscribe now!",
                "How are you? 😀",
                "A long message without ads or emojis.",
                "  Sufficiently long text.  "
        };

        for (String message : incomingMessages) {
            boolean passedAllFilters = messageProcessor.processMessage(message, filterChain);
            System.out.println("Message: \"" + message + "\" | passed: " + passedAllFilters);
        }
    }
}
