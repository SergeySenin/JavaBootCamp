package org.study.bootcamp.task_36.recommender.v1.domain.model;

import java.util.*;

public record UserProfile(
        int userId,
        String gender,
        int age,
        String location,
        List<String> interests
) {
    public UserProfile {
        validateUserId(userId);
        validateGender(gender);
        validateAge(age);
        validateLocation(location);

        gender = gender.trim();
        location = location.trim();
        interests = processInterests(interests);
    }

    private static void validateUserId(int userId) {
        if (userId < 0) {
            throw new IllegalArgumentException("userId: must be >= 0");
        }
    }

    private static void validateGender(String gender) {
        if (gender == null || gender.isBlank()) {
            throw new IllegalArgumentException("gender: must not be null/blank");
        }
    }

    private static void validateAge(int age) {
        if (age < 0) {
            throw new IllegalArgumentException("age: must be >= 0");
        }
    }

    private static void validateLocation(String location) {
        if (location == null || location.isBlank()) {
            throw new IllegalArgumentException("location: must not be null/blank");
        }
    }

    private static List<String> processInterests(List<String> interests) {
        if (interests == null) {
            return List.of();
        }

        return interests.stream()
                .filter(Objects::nonNull)
                .map(String::trim)
                .filter(interest -> !interest.isBlank())
                .toList();
    }
}
