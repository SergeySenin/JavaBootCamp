package org.study.bootcamp.task_30.stream_basics_2.v1.application.service;

import java.util.*;
import java.util.stream.*;

public final class StreamOperations {

    private StreamOperations() {}

    public static Set<List<Integer>> findPairsSummingTo(Set<Integer> numbers, int targetSum) {
        requireNonNull(numbers, "numbers");

        return numbers.stream()
                .filter(Objects::nonNull)
                .sorted()
                .filter(candidate -> {
                    int counterpart = targetSum - candidate;
                    return numbers.contains(counterpart) && candidate < counterpart;
                })
                .map(left -> List.of(left, targetSum - left))
                .collect(Collectors.toCollection(LinkedHashSet::new));
    }

    public static List<String> getCapitalsSortedByCountry(Map<String, String> countries) {
        requireNonNull(countries, "countries");

        return countries.entrySet().stream()
                .filter(entry -> entry.getKey() != null && entry.getValue() != null)
                .sorted(Map.Entry.comparingByKey())
                .map(Map.Entry::getValue)
                .toList();
    }

    public static List<String> filterStartingWithAndSortByLength(List<String> strings, char firstLetter) {
        requireNonNull(strings, "strings");

        String prefix = String.valueOf(firstLetter);
        return strings.stream()
                .filter(Objects::nonNull)
                .filter(text -> text.startsWith(prefix))
                .sorted(Comparator.comparingInt(String::length))
                .toList();
    }

    public static List<String> toBinaryStrings(List<Integer> numbers) {
        requireNonNull(numbers, "numbers");

        return numbers.stream()
                .filter(Objects::nonNull)
                .map(Integer::toBinaryString)
                .toList();
    }

    public static List<String> filterByAlphabetAndSortByLength(List<String> strings, String alphabet) {
        requireNonNull(strings, "strings");
        requireNonBlank(alphabet, "alphabet");

        String regex = "^[%s]+$".formatted(alphabet);
        return strings.stream()
                .filter(Objects::nonNull)
                .filter(text -> text.matches(regex))
                .sorted(Comparator.comparingInt(String::length))
                .toList();
    }

    private static <T> T requireNonNull(T value, String name) {
        if (value == null) throw new IllegalArgumentException(name + " must not be null");
        return value;
    }

    private static String requireNonBlank(String value, String name) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException(name + " must not be null/blank");
        }
        return value;
    }
}
