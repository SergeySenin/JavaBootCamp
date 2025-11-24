package org.study.bootcamp.task_34.job_analyzer.v1.domain.model;

import java.time.LocalDate;
import java.util.*;

public record Job(
        String position,
        List<String> requirements,
        long salary,
        String location,
        LocalDate datePosted
) {
    public Job {
        if (position == null || position.isBlank()) {
            throw new IllegalArgumentException("position: must not be null/blank");
        }
        if (location == null || location.isBlank()) {
            throw new IllegalArgumentException("location: must not be null/blank");
        }
        Objects.requireNonNull(datePosted, "datePosted: must not be null");
        if (salary < 0) {
            throw new IllegalArgumentException("salary: must be >= 0");
        }

        position = position.trim();
        location = location.trim();

        List<String> cleaned = new ArrayList<>();
        if (requirements != null) {
            for (String skill : requirements) {
                if (skill != null && !skill.isBlank()) {
                    cleaned.add(skill.trim());
                }
            }
        }
        requirements = List.copyOf(cleaned);
    }
}
