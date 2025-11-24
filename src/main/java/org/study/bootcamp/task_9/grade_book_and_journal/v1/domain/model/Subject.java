package org.study.bootcamp.task_9.grade_book_and_journal.v1.domain.model;

public record Subject(String name) {

    public Subject {
        validate(name);

        name = name.trim();
    }

    private static void validate(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("subject name: must not be null/blank");
        }
    }
}
