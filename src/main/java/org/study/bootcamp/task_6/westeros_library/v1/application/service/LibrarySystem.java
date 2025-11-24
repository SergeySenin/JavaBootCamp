package org.study.bootcamp.task_6.westeros_library.v1.application.service;

import org.study.bootcamp.task_6.westeros_library.v1.domain.model.Book;

import java.util.*;

public class LibrarySystem {

    private final Map<Book, String> catalog = new HashMap<>();

    public void addBook(String title, String author, int year, String location) {
        validateLocation(location);
        String normalizedLocation = normalizeLocation(location);

        Book book = bookOf(title, author, year);
        String previous = catalog.put(book, normalizedLocation);

        printAddOutcome(book, previous, normalizedLocation);
    }

    public void removeBook(String title, String author, int year) {
        Book key = bookOf(title, author, year);
        String removed = catalog.remove(key);

        printRemoveOutcome(key, removed);
    }

    public void findBook(String title, String author, int year) {
        Book key = bookOf(title, author, year);
        String location = catalog.get(key);

        printFindOutcome(key, location);
    }

    public void printAllBooks() {
        if (catalog.isEmpty()) {
            println("Library is empty!");
            return;
        }
        println("=== Library catalog ===");
        catalog.forEach((book, location) -> println("%s -> %s", book, location));
    }

    private static void validateLocation(String location) {
        if (location == null || location.isBlank()) {
            throw new IllegalArgumentException("location: must not be null/blank");
        }
    }

    private static String normalizeLocation(String location) {
        return location.trim();
    }

    private static Book bookOf(String title, String author, int year) {
        return new Book(title, author, year);
    }

    private static void printAddOutcome(Book book, String previous, String newLocation) {
        if (previous == null) {
            println("Added: %s -> %s", book, newLocation);
        } else {
            println("Updated: %s | %s -> %s", book, previous, newLocation);
        }
    }

    private static void printRemoveOutcome(Book key, String removedLocation) {
        if (removedLocation == null) {
            println("Not found: %s", key);
        } else {
            println("Removed: %s (was at %s)", key, removedLocation);
        }
    }

    private static void printFindOutcome(Book key, String location) {
        if (location == null) {
            println("Not found: %s", key);
        } else {
            println("Found: %s -> %s", key, location);
        }
    }

    private static void println(String fmt, Object... args) {
        System.out.printf(fmt + "%n", args);
    }
}
