package org.study.bootcamp.task_6.westeros_library.v1.api.cli;

import org.study.bootcamp.task_6.westeros_library.v1.application.service.LibrarySystem;

public class Demo {
    public static void main(String[] args) {

        final LibrarySystem librarySystem = new LibrarySystem();

        librarySystem.addBook("AB", "ABC", 2000, "DD");
        librarySystem.addBook("BC", "BCD", 2005, "EE");
        librarySystem.addBook("CD", "CDE", 2010, "FF");

        librarySystem.addBook("AB", "ABC", 2000, "DD");

        librarySystem.findBook("BC", "BCD", 2005);
        librarySystem.findBook("DE", "DEF", 2030);

        librarySystem.printAllBooks();

        librarySystem.removeBook("CD", "CDE", 2010);
        librarySystem.removeBook("CD", "CDE", 2010);

        librarySystem.printAllBooks();
    }
}
