package org.study.bootcamp.task_6.westeros_library.v1.domain.model;

public record Book(String title, String author, int year) {

    public Book {
        validate(title, author, year);

        title = title.trim();
        author = author.trim();
    }

    private static void validate(String title, String author, int year) {
        if (title == null || title.isBlank()) {
            throw new IllegalArgumentException("title: must not be null/blank");
        }
        if (author == null || author.isBlank()) {
            throw new IllegalArgumentException("author: must not be null/blank");
        }
        if (year <= 0) {
            throw new IllegalArgumentException("year: must be positive");
        }
    }
}
