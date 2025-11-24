package org.study.bootcamp.task_6.westeros_library.v2.easy;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Main {
    static class Book {
        String title;
        String author;
        int year;

        public Book(String title, String author, int year) {
            this.title = title;
            this.author = author;
            this.year = year;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null || getClass() != obj.getClass()) return false;
            Book book = (Book) obj;
            return year == book.year &&
                    Objects.equals(title, book.title) &&
                    Objects.equals(author, book.author);
        }

        @Override
        public int hashCode() {
            return Objects.hash(title, author, year);
        }

        @Override
        public String toString() {
            return title + " (" + author + ", " + year + ")";
        }
    }

    static class LibrarySystem {
        private Map<Book, String> library = new HashMap<>();

        public void addBook(String title, String author, int year, String location) {
            Book book = new Book(title, author, year);
            library.put(book, location);
            System.out.println("Добавлена: " + book + " → полка: " + location);
        }

        public void removeBook(String title, String author, int year) {
            Book bookToRemove = new Book(title, author, year);
            if (library.remove(bookToRemove) != null) {
                System.out.println("Удалена: " + bookToRemove);
            } else {
                System.out.println("Книга не найдена: " + bookToRemove);
            }
        }

        public void findBook(String title, String author, int year) {
            Book bookToFind = new Book(title, author, year);
            String location = library.get(bookToFind);
            if (location != null) {
                System.out.println("Найдена: " + bookToFind + " → полка: " + location);
            } else {
                System.out.println("Книга не найдена: " + bookToFind);
            }
        }

        public void printAllBooks() {
            System.out.println("\nВсе книги в библиотеке:");
            for (Map.Entry<Book, String> entry : library.entrySet()) {
                System.out.println("📖 " + entry.getKey() + " → полка: " + entry.getValue());
            }
        }

        public static void main(String[] args) {
            LibrarySystem library = new LibrarySystem();

            library.addBook("Игра престолов", "Джордж Мартин", 1996, "A1");
            library.addBook("Буря мечей", "Джордж Мартин", 2000, "A2");
            library.addBook("Властелин колец", "Толкиен", 1954, "B1");

            System.out.println();
            library.findBook("Игра престолов", "Джордж Мартин", 1996);
            library.findBook("Несуществующая книга", "Автор", 2020);

            System.out.println();
            library.removeBook("Буря мечей", "Джордж Мартин", 2000);

            library.printAllBooks();
        }
    }
}



/*
1. Зачем нужны equals() и hashCode() в этой задаче?
Проблема без переопределения:

java
Book book1 = new Book("Игра престолов", "Мартин", 1996);
Book book2 = new Book("Игра престолов", "Мартин", 1996);

// Без переопределения equals():
book1.equals(book2) // false! Это РАЗНЫЕ объекты в памяти

// HashMap не найдёт книгу, даже если данные одинаковые
library.get(book2) // вернёт null, хотя книга есть в библиотеке

Решение с переопределением:
Мы говорим Java: "Считай книги одинаковыми, если у них совпадают название, автор и год"

2. Построчный разбор equals():
java
@Override
public boolean equals(Object obj) {
    if (this == obj) return true;                                  // Если это тот же самый объект в памяти - true
    if (obj == null || getClass() != obj.getClass()) return false; // Если obj null или другого класса - false
    Book book = (Book) obj;                                        // Приводим Object к типу Book
    return year == book.year &&                                    // Сравниваем годы
           Objects.equals(title, book.title) &&                    // Сравниваем названия (безопасно для null)
           Objects.equals(author, book.author);                    // Сравниваем авторов (безопасно для null)
}

Пример работы:
java
Book book1 = new Book("Игра престолов", "Мартин", 1996);
Book book2 = new Book("Игра престолов", "Мартин", 1996);
Book book3 = new Book("Другая книга", "Мартин", 1996);

book1.equals(book2) // true - все поля совпадают
book1.equals(book3) // false - названия разные

3. Зачем нужно переопределять hashCode() следом за equals()?
Правило Java: Если два объекта равны по equals(), то их hashCode() должен быть одинаковым.
Что происходит в HashMap без переопределения hashCode():
java
Book book1 = new Book("Игра престолов", "Мартин", 1996);
Book book2 = new Book("Игра престолов", "Мартин", 1996);

// Допустим, мы переопределили только equals():
book1.equals(book2) // true - объекты логически равны

// Но hashCode() разный (по умолчанию возвращает адрес в памяти):
book1.hashCode() // 12345
book2.hashCode() // 67890

// HashMap ищет сначала по hashCode():
library.get(book2) // Ищет в корзине для hashCode=67890, но книга лежит в корзине для 12345
// Результат: null - книга не найдена!

С переопределением hashCode():
java
@Override
public int hashCode() {
    return Objects.hash(title, author, year); // Одинаковый хэш для одинаковых полей
}

book1.hashCode() // 555 (например)
book2.hashCode() // 555 (одинаковые поля → одинаковый хэш)

// HashMap ищет в правильной корзине, затем сравнивает через equals()
library.get(book2) // Находит книгу!

4. Объяснение условия if (library.remove(bookToRemove) != null)
Что делает remove():
Удаляет пару ключ-значение из Map
Возвращает: значение которое было связано с ключом, или null если ключа не было

java
// Пример:
library.put(book1, "A1");  // Добавили книгу на полку A1

String result = library.remove(book1); // result = "A1" (удалили и вернули полку)
String result2 = library.remove(book2); // result2 = null (книги не было)

// Условие проверяет: если remove вернул НЕ null - значит книга была удалена
if (library.remove(bookToRemove) != null) {
    System.out.println("Удалена"); // Книга была найдена и удалена
} else {
    System.out.println("Не найдена"); // Книги не было в библиотеке
}

Мы не меняли логику remove(), просто используем то, что он возвращает null если элемента не было.

4. Как именно работает .get(bookToFind)?
HashMap.get() работает по алгоритму:
java
public String get(Book key) {
    // 1. Вычисляет hashCode() ключа
    int hash = key.hashCode();

    // 2. Находит "корзину" с этим хэш-кодом
    Bucket bucket = findBucket(hash);

    // 3. В корзине ищет ключ через equals()
    for (Entry<Book, String> entry : bucket.entries) {
        if (entry.getKey().equals(key)) { // ← Вот где нужен наш equals()!
            return entry.getValue(); // Нашли - возвращаем значение
        }
    }

    return null; // Не нашли
}
 */
