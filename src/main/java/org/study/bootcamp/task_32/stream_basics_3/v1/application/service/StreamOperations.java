package org.study.bootcamp.task_32.stream_basics_3.v1.application.service;

import org.study.bootcamp.task_32.stream_basics_3.v1.domain.model.Employee;
import org.study.bootcamp.task_32.stream_basics_3.v1.domain.model.PersonPair;

import java.util.*;
import java.util.stream.*;

public final class StreamOperations {

    private StreamOperations() {}

    public static List<PersonPair> nonFriendsWithMutualFriends(Map<String, List<String>> rawFriendMap) {
        requireNonNull(rawFriendMap, "rawFriendMap");

        Map<String, Set<String>> friendsByPerson = toBidirectionalFriendGraph(rawFriendMap);

        Set<String> allPeople = new TreeSet<>(friendsByPerson.keySet());
        friendsByPerson.values().forEach(allPeople::addAll);
        List<String> peopleList = new ArrayList<>(allPeople);

        return IntStream.range(0, peopleList.size())
                .boxed()
                .flatMap(i ->
                        IntStream.range(i + 1, peopleList.size())
                                .mapToObj(j -> new AbstractMap.SimpleEntry<>(peopleList.get(i), peopleList.get(j)))
                )
                .filter(entry -> {
                    String left = entry.getKey();
                    String right = entry.getValue();
                    Set<String> leftFriends = friendsByPerson.getOrDefault(left, Set.of());
                    Set<String> rightFriends = friendsByPerson.getOrDefault(right, Set.of());

                    if (leftFriends.contains(right) || rightFriends.contains(left)) return false;

                    return hasIntersection(leftFriends, rightFriends);
                })
                .map(entry -> new PersonPair(entry.getKey(), entry.getValue()))
                .collect(Collectors.toCollection(LinkedHashSet::new))
                .stream()
                .toList();
    }

    public static Map<String, Double> averageSalaryByDepartment(List<Employee> employees) {
        requireNonNull(employees, "employees");

        return employees.stream()
                .filter(Objects::nonNull)
                .collect(Collectors.groupingBy(
                        Employee::department,
                        LinkedHashMap::new,
                        Collectors.averagingDouble(Employee::salary)
                ));
    }

    public static List<Integer> palindromesInRange(int startInclusive, int endInclusive) {
        if (startInclusive > endInclusive) {
            throw new IllegalArgumentException("startInclusive: must be <= endInclusive");
        }
        return IntStream.rangeClosed(startInclusive, endInclusive)
                .filter(number -> number >= 0)
                .filter(StreamOperations::isPalindromeNumber)
                .boxed()
                .toList();
    }

    public static List<String> distinctPalindromicSubstrings(String input) {
        requireNonNull(input, "input");

        int length = input.length();
        return IntStream.range(0, length)
                .boxed()
                .flatMap(startIndex ->
                        IntStream.rangeClosed(startIndex + 1, length)
                                .mapToObj(endExclusive -> input.substring(startIndex, endExclusive))
                )
                .filter(StreamOperations::isPalindromeString)
                .distinct()
                .toList();
    }

    public static List<Integer> perfectNumbersInRange(int startInclusive, int endInclusive) {
        if (startInclusive > endInclusive) {
            throw new IllegalArgumentException("startInclusive: must be <= endInclusive");
        }
        return IntStream.rangeClosed(startInclusive, endInclusive)
                .filter(number -> number > 0)
                .filter(StreamOperations::isPerfect)
                .boxed()
                .toList();
    }

    private static Map<String, Set<String>> toBidirectionalFriendGraph(Map<String, List<String>> rawFriendMap) {
        Map<String, Set<String>> result = new LinkedHashMap<>();

        rawFriendMap.keySet().forEach(name ->
                result.computeIfAbsent(name, key -> new LinkedHashSet<>()));

        rawFriendMap.forEach((person, friends) -> {
            if (person == null || friends == null) return;
            Set<String> personFriends = result.computeIfAbsent(person, key -> new LinkedHashSet<>());
            for (String friend : friends) {
                if (friend == null || friend.equals(person)) continue;
                personFriends.add(friend);
                result.computeIfAbsent(friend, key -> new LinkedHashSet<>()).add(person);
            }
        });

        return result;
    }

    private static boolean hasIntersection(Set<String> setA, Set<String> setB) {
        if (setA.isEmpty() || setB.isEmpty()) return false;
        Set<String> smaller = setA.size() <= setB.size() ? setA : setB;
        Set<String> bigger  = setA.size() >  setB.size() ? setA : setB;
        for (String value : smaller) {
            if (bigger.contains(value)) return true;
        }
        return false;
    }

    private static boolean isPalindromeNumber(int value) {
        String numberAsString = Integer.toString(value);
        return isPalindromeString(numberAsString);
    }

    private static boolean isPalindromeString(String input) {
        int left = 0, right = input.length() - 1;
        while (left < right) {
            if (input.charAt(left++) != input.charAt(right--)) return false;
        }
        return true;
    }

    private static boolean isPerfect(int number) {
        if (number <= 1) return false;

        int sum = 1;
        int sqrt = (int) Math.sqrt(number);

        for (int divisor = 2; divisor <= sqrt; divisor++) {
            if (number % divisor == 0) {
                int pair = number / divisor;

                sum += divisor;
                if (pair != divisor) sum += pair;

                if (sum > number) return false;
            }
        }
        return sum == number;
    }

    private static <T> T requireNonNull(T value, String name) {
        if (value == null) throw new IllegalArgumentException(name + " must not be null");
        return value;
    }
}
