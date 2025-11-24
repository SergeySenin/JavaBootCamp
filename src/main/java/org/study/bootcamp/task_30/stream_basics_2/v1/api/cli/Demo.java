package org.study.bootcamp.task_30.stream_basics_2.v1.api.cli;

import org.study.bootcamp.task_30.stream_basics_2.v1.application.service.StreamOperations;

import java.util.*;

public class Demo {
    public static void main(String[] args) {

        Set<Integer> uniqueNumbers = new LinkedHashSet<>(Arrays.asList(1, 3, 4, 6, 7, 9, 12));
        int targetSum = 10;
        System.out.println("Pairs summing to " + targetSum + ": " +
                StreamOperations.findPairsSummingTo(uniqueNumbers, targetSum));

        Map<String, String> countries = new LinkedHashMap<>();
        countries.put("France", "Paris");
        countries.put("USA", "Washington");
        countries.put("China", "Beijing");
        System.out.println("Capitals sorted by country: " +
                StreamOperations.getCapitalsSortedByCountry(countries));

        List<String> words = Arrays.asList("apple", "avocado", "banana", "apricot", "ace", "angle");
        System.out.println("Starts with 'a' sorted by length: " +
                StreamOperations.filterStartingWithAndSortByLength(words, 'a'));

        List<Integer> numbers = Arrays.asList(1, 2, 3, 4);
        System.out.println("Binary strings: " + StreamOperations.toBinaryStrings(numbers));

        List<String> fruits = Arrays.asList("apple", "banana", "cherry", "date", "fig", "grape");
        String alphabet = "abcdefghijklmnopqrstuvwxyz";
        System.out.println("Alphabet-only & sorted by length: " +
                StreamOperations.filterByAlphabetAndSortByLength(fruits, alphabet));
    }
}
