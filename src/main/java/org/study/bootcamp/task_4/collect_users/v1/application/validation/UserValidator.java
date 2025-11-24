package org.study.bootcamp.task_4.collect_users.v1.application.validation;

import java.util.*;

public final class UserValidator {

    private static final int MIN_AGE = 0;
    private static final int MAX_AGE = 120;

    private UserValidator() { }

    public static void validate(Long id, String name, int age, Set<String> activities) {
        if (id == null) {
            throw new IllegalArgumentException("id: must not be null");
        }
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("name: must not be null or blank");
        }
        if (age < MIN_AGE || age > MAX_AGE) {
            throw new IllegalArgumentException(
                    "age: must be between " + MIN_AGE + " and " + MAX_AGE + " inclusive"
            );
        }
        if (activities == null || activities.isEmpty()) {
            throw new IllegalArgumentException("activities: must not be null or empty");
        }
        if (activities.stream().anyMatch(Objects::isNull)) {
            throw new IllegalArgumentException("activities: must not contain null elements");
        }
        if (activities.stream().anyMatch(String::isBlank)) {
            throw new IllegalArgumentException("activities: must not contain blank elements");
        }
    }
}
