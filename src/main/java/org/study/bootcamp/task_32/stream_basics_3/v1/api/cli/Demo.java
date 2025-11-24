package org.study.bootcamp.task_32.stream_basics_3.v1.api.cli;

import org.study.bootcamp.task_32.stream_basics_3.v1.domain.model.Employee;
import org.study.bootcamp.task_32.stream_basics_3.v1.domain.model.PersonPair;
import org.study.bootcamp.task_32.stream_basics_3.v1.application.service.StreamOperations;

import java.util.*;

public class Demo {
    public static void main(String[] args) {

        Map<String, List<String>> friendMap = new LinkedHashMap<>();
        friendMap.put("Alice",   List.of("Bob", "Charlie"));
        friendMap.put("Bob",     List.of("Alice", "David"));
        friendMap.put("Charlie", List.of("Alice", "David"));
        friendMap.put("David",   List.of("Bob", "Charlie"));

        List<PersonPair> suggestedPairs = StreamOperations.nonFriendsWithMutualFriends(friendMap);
        System.out.println("Non-friends with mutual friends: " + suggestedPairs);

        List<Employee> employees = List.of(
                new Employee("Ann",   120_000, "Engineering"),
                new Employee("Bill",  110_000, "Engineering"),
                new Employee("Cara",   90_000, "Marketing"),
                new Employee("Duke",   95_000, "Marketing"),
                new Employee("Evan",  130_000, "Engineering")
        );

        Map<String, Double> avgByDept = StreamOperations.averageSalaryByDepartment(employees);
        System.out.println("Average salary by department: " + avgByDept);

        System.out.println("Palindromes [100..200]: " + StreamOperations.palindromesInRange(100, 200));

        System.out.println("Distinct pal substrings of 'abac': " + StreamOperations.distinctPalindromicSubstrings("abac"));

        System.out.println("Perfect numbers [1..1000]: " + StreamOperations.perfectNumbersInRange(1, 1000));
    }
}
