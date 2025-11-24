package org.study.bootcamp.task_2.user_registration.v2.easy;

import java.util.Set;

public class Main {
    static class User {
        String name;
        int age;
        String job;
        String address;

        static final Set<String> VALID_JOBS = Set.of("Google", "Uber", "Amazon");
        static final Set<String> VALID_ADDRESS = Set.of("London", "New York", "Amsterdam");

        public User(String name, int age, String job, String address) {
            if (name == null || name.isEmpty()) {
                throw new IllegalArgumentException("Имя не может быть пустым");
            }
            if (age < 18) {
                throw new IllegalArgumentException("Возраст не может быть меньше 18");
            }
            if (!VALID_JOBS.contains(job)) {
                throw new IllegalArgumentException("Недопустимое место работы: " + job);
            }
            if (!VALID_ADDRESS.contains(address)) {
                throw new IllegalArgumentException("Недопустимый адрес: " + address);
            }

            this.name = name;
            this.age = age;
            this.job = job;
            this.address = address;
        }

        @Override
        public String toString () {
            return name + " (" + age + ") - " + job + ", " + address;
        }

        public static void main(String[] args) {
            try {
                User user1 = new User("Анна", 25, "Google", "London");
                System.out.println("Успешно создан: " + user1);

                User user2 = new User("", 20, "Amazon", "New York");
            } catch (IllegalArgumentException illegalArgumentException) {
                System.out.println("Ошибка: " + illegalArgumentException.getMessage());
            }
        }
    }
}



/*
1. Как работает метод Set.of()?
Set.of() - это статический метод, который создаёт неизменяемый (immutable) набор элементов:
java
// Создаёт Set с тремя элементами
Set<String> jobs = Set.of("Google", "Uber", "Amazon");

// Эквивалентно более длинной записи:
Set<String> jobs = new HashSet<>();
jobs.add("Google");
jobs.add("Uber");
jobs.add("Amazon");
jobs = Collections.unmodifiableSet(jobs); // делаем неизменяемым

Особенности:
Set создаётся сразу с готовыми элементами
Нельзя добавить или удалить элементы после создания
Быстрее и компактнее, чем создание через конструктор

2. Чем отличается = от ==?
Это совершенно разные операторы:
= - оператор присваивания:
java
int x = 10;        // присвоить 10 переменной x
String name = "Анна"; // присвоить строку переменной name

== - оператор сравнения:
java
int a = 5, b = 5;
boolean result = (a == b); // true - числа равны

String s1 = "hello";
String s2 = "hello";
boolean result2 = (s1 == s2); // осторожно! для строк так лучше не сравнивать

Простое правило:
= - положить значение в переменную
== - проверить равны ли два значения

3. Что подразумевает null?
null - это специальное значение, которое означает "ничего" или "отсутствие объекта":
java
String name = null; // переменная есть, но она не ссылается ни на какой объект
String name = "";   // пустая строка (объект существует)
String name = "Анна"; // строка с содержимым

// Проверки на null:
if (name == null) {
    System.out.println("Имя не указано");
}
if (name != null) {
    System.out.println("Имя: " + name);
}

4. Что подразумевает !?
! - это оператор НЕ (отрицание). Он инвертирует логическое значение:
java
boolean isAdult = true;
boolean isChild = !isAdult; // false - противоположность true

int age = 16;
boolean canVote = (age >= 18);     // false
boolean cannotVote = !canVote;     // true - отрицание false

// В условиях:
if (!name.isEmpty()) { // если имя НЕ пустое
    System.out.println("Имя заполнено");
}

if (!VALID_JOBS.contains(job)) { // если работа НЕ содержится в допустимых
    throw new IllegalArgumentException("Недопустимая работа");
}

5. Как работает метод isEmpty()?
isEmpty() проверяет, пустая ли строка или коллекция:
java
// Для строк:
String str1 = "";
String str2 = "Hello";
System.out.println(str1.isEmpty()); // true - строка пустая
System.out.println(str2.isEmpty()); // false - строка не пустая

// Эквивалентно проверке:
System.out.println(str1.length() == 0); // true
System.out.println(str2.length() == 0); // false

6. Как работает метод contains() для Set?
contains() проверяет, содержится ли элемент в Set:
java
Set<String> jobs = Set.of("Google", "Uber", "Amazon");

System.out.println(jobs.contains("Google"));  // true
System.out.println(jobs.contains("Microsoft")); // false

// Под капотом Set использует хэш-таблицу для быстрого поиска
// Поиск происходит очень быстро, даже для больших коллекций

Аналогия: Set как список гостей на вечеринке, а contains() как проверка - есть ли имя в списке.
 */
