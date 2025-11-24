package org.study.bootcamp.task_21.dictionary_biconsumer.v1.api.cli;

import org.study.bootcamp.task_21.dictionary_biconsumer.v1.application.service.DictionaryProcessor;

import java.util.*;
import java.util.function.*;

public class Demo {
    public static void main(String[] args) {

        Map<String, String> dictionary = new LinkedHashMap<>();

        BiConsumer<String, String> saveEntry =
                (word, translation) -> dictionary.put(word, translation);

        BiConsumer<String, String> logEntry =
                (word, translation) -> System.out.println("Saved entry: [" + word + " -> " + translation + "]");

        BiConsumer<String, String> saveThenLog = saveEntry.andThen(logEntry);

        DictionaryProcessor.processWord("привет", "hello", saveThenLog);
        DictionaryProcessor.processWord("мир", "world", saveThenLog);
        DictionaryProcessor.processWord("программирование", "programming", saveThenLog);

        System.out.println("Dictionary: " + dictionary);
    }
}
