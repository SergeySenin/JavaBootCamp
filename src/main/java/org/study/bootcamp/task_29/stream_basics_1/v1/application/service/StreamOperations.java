package org.study.bootcamp.task_29.stream_basics_1.v1.application.service;

import java.util.*;
import java.util.function.*;

public final class StreamOperations {

    private StreamOperations() {}

    public static int sumOfEvenNumbers(List<Integer> numbers) {
        requireNonNull(numbers, "numbers");
        return numbers.stream()
                .filter(Objects::nonNull)
                .filter(n -> n % 2 == 0)
                .mapToInt(n -> n)
                .sum();
    }

    public static int findMax(List<Integer> numbers) {
        requireNonNull(numbers, "numbers");
        return numbers.stream()
                .filter(Objects::nonNull)
                .max(Comparator.naturalOrder())
                .orElseThrow(() -> new NoSuchElementException("list is empty or only nulls"));
    }

    public static double findAverage(List<Integer> numbers) {
        requireNonNull(numbers, "numbers");
        return numbers.stream()
                .filter(Objects::nonNull)
                .mapToInt(n -> n)
                .average()
                .orElse(0.0);
    }

    public static long countStringsStartingWith(List<String> strings, char firstChar) {
        requireNonNull(strings, "strings");
        return strings.stream()
                .filter(Objects::nonNull)
                .filter(s -> !s.isEmpty() && s.charAt(0) == firstChar)
                .count();
    }

    public static List<String> filterStringsContainingSubstring(List<String> strings, String substring) {
        requireNonNull(strings, "strings");
        requireNonNull(substring, "substring");
        return strings.stream()
                .filter(Objects::nonNull)
                .filter(s -> s.contains(substring))
                .toList();
    }

    public static List<String> sortByLength(List<String> strings) {
        requireNonNull(strings, "strings");
        return strings.stream()
                .filter(Objects::nonNull)
                .sorted(Comparator.comparingInt(String::length))
                .toList();
    }

    public static boolean allMatchCondition(List<Integer> numbers, Predicate<Integer> predicate) {
        requireNonNull(numbers, "numbers");
        requireNonNull(predicate, "predicate");
        return numbers.stream()
                .allMatch(n -> n != null && predicate.test(n));
    }

    public static int findMinGreaterThan(List<Integer> numbers, int threshold) {
        requireNonNull(numbers, "numbers");
        return numbers.stream()
                .filter(Objects::nonNull)
                .filter(n -> n > threshold)
                .min(Comparator.naturalOrder())
                .orElseThrow(() -> new NoSuchElementException("no elements greater than " + threshold));
    }

    public static List<Integer> convertToLengths(List<String> strings) {
        requireNonNull(strings, "strings");
        return strings.stream()
                .filter(Objects::nonNull)
                .map(String::length)
                .toList();
    }

    private static <T> T requireNonNull(T value, String name) {
        if (value == null) throw new IllegalArgumentException(name + ": must not be null");
        return value;
    }
}
