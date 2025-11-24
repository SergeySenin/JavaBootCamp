package org.study.bootcamp.task_1.group_users_by_age.v4;

import java.util.*;

// TODO[design]: Выполнено замечание из v1.
public record User(String name, int age, String workplace, String address) {

    public User {
        validate(name, age, workplace, address);

        name = name.trim();
        workplace = workplace.trim();
        address = address.trim();
    }

    public static Map<Integer, List<User>> groupUsersByAge4(List<User> usersv4) {
        Objects.requireNonNull(usersv4, "Input list 'usersv4' is null!");

        if (usersv4.stream().anyMatch(Objects::isNull)) {
            throw new IllegalArgumentException("List contains null user!");
        }

        // IV ВАРИАНТ РЕШЕНИЯ: Stream API с Collectors.groupingBy.
        Map<Integer, List<User>> grouped =
                usersv4.stream()
                        .collect(java.util.stream.Collectors.groupingBy(
                                User::age,
                                java.util.stream.Collectors.toUnmodifiableList()
                        ));

        return Map.copyOf(grouped);
    }

    private static void validate(String name, int age, String workplace, String address) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Name is null/blank!");
        }
        if (age < 0 || age > 120) {
            throw new IllegalArgumentException("Age must be between 0 and 120!");
        }
        if (workplace == null || workplace.isBlank()) {
            throw new IllegalArgumentException("Workplace is null/blank!");
        }
        if (address == null || address.isBlank()) {
            throw new IllegalArgumentException("Address is null/blank!");
        }
    }
}
