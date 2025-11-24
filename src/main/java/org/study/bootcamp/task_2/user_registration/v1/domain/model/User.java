package org.study.bootcamp.task_2.user_registration.v1.domain.model;

import java.util.*;

public record User(String name, int age, String job, String address) {

    private static final int MINIMUM_AGE = 18;
    private static final Set<String> VALID_ADDRESSES = Set.of("Ab", "Cd", "Ef");
    private static final Set<String> VALID_JOBS = Set.of("Ba", "Dc", "Fe");

    public User {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Username cannot be empty or blank!");
        }
        if (age < MINIMUM_AGE) {
            throw new IllegalArgumentException("User age cannot be less than " + MINIMUM_AGE + "!");
        }
        if (!VALID_JOBS.contains(job)) {
            throw new IllegalArgumentException("Invalid job. Allowed values: " + VALID_JOBS);
        }
        if (!VALID_ADDRESSES.contains(address)) {
            throw new IllegalArgumentException("Invalid address. Allowed values: " + VALID_ADDRESSES);
        }
    }
}
