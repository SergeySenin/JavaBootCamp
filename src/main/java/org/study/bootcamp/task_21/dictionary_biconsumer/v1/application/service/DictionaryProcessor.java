package org.study.bootcamp.task_21.dictionary_biconsumer.v1.application.service;

import lombok.experimental.UtilityClass;

import java.util.*;
import java.util.function.*;

@UtilityClass
public class DictionaryProcessor {

    public static void processWord(String word, String translation, BiConsumer<String, String> wordHandler) {
        Objects.requireNonNull(wordHandler, "wordHandler: must not be null");

        String normalizedWord = requireNonBlank(word, "word").trim();
        String normalizedTranslation = requireNonBlank(translation, "translation").trim();

        wordHandler.accept(normalizedWord, normalizedTranslation);
    }

    private static String requireNonBlank(String value, String name) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException(name + ": must not be null/blank");
        }
        return value;
    }
}
