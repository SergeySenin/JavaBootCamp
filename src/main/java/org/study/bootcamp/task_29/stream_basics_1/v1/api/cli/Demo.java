package org.study.bootcamp.task_29.stream_basics_1.v1.api.cli;

import org.study.bootcamp.task_29.stream_basics_1.v1.application.service.StreamOperations;

import java.util.*;
import java.util.function.*;

public class Demo {
    public static void main(String[] args) {

        List<Integer> numbers = new ArrayList<>(Arrays.asList(1, 2, null, 3, 4, 5, 6, 7, 10));
        List<String> strings = new ArrayList<>(Arrays.asList("apple", "banana", "cherry", "date", null, "avocado"));

        System.out.println("Sum of even numbers: " + StreamOperations.sumOfEvenNumbers(numbers));

        try {
            System.out.println("Max number: " + StreamOperations.findMax(numbers));
        } catch (NoSuchElementException exception) {
            System.out.println("Max number: error -> " + exception.getMessage());
        }

        System.out.println("Average: " + StreamOperations.findAverage(numbers));

        System.out.println("Strings starting with 'a': " + StreamOperations.countStringsStartingWith(strings, 'a'));

        System.out.println("Strings containing 'an': " + StreamOperations.filterStringsContainingSubstring(strings, "an"));

        System.out.println("Sorted by length: " + StreamOperations.sortByLength(strings));

        Predicate<Integer> positive = n -> n > 0;
        System.out.println("All numbers > 0: " + StreamOperations.allMatchCondition(numbers, positive));

        try {
            System.out.println("Min number greater than 4: " + StreamOperations.findMinGreaterThan(numbers, 4));
        } catch (NoSuchElementException exception) {
            System.out.println("Min number greater than 4: error -> " + exception.getMessage());
        }

        System.out.println("String lengths: " + StreamOperations.convertToLengths(strings));
    }
}
