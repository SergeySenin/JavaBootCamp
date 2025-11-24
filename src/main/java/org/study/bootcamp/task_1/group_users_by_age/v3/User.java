package org.study.bootcamp.task_1.group_users_by_age.v3;

import java.util.*;

public class User {

    private final String name;
    private final int age;
    private final String workplace;
    private final String address;

    public User(String name, int age, String workplace, String address) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Name is null/blank!");
        }
        if (age < 0 || age > 120) {
            throw new IllegalArgumentException("Age must be between 0 and 120!");
        }
        if (workplace == null || workplace.isBlank()) {
            throw new IllegalArgumentException("Workplace is null/blank!");
        }
        if (address == null || address.isBlank()) {
            throw new IllegalArgumentException("Address is null/blank!");
        }

        this.name = name.trim();
        this.age = age;
        this.workplace = workplace.trim();
        this.address = address.trim();
    }

    public int getAge() {
        return age;
    }

    @Override
    public String toString() {
        return "User{\n" +
                "  name='" + name + "',\n" +
                "  age=" + age + ",\n" +
                "  workplace='" + workplace + "',\n" +
                "  address='" + address + "'\n" +
                "}";
    }

    public static Map<Integer, List<User>> groupUsersByAge3(List<User> usersv3) {
        // TODO[null-safety]: Выполнено замечание из v1.
        Objects.requireNonNull(usersv3, "Input list 'usersv3' is null!");

        Map<Integer, List<User>> usersByAgev3 = new HashMap<>();

        // III ВАРИАНТ ЗАПИСИ: List.forEach(...) с лямбдой.
        usersv3.forEach(user -> {
            // TODO[null-safety]: Выполнено замечание из v1.
            if (user == null) {
                throw new IllegalArgumentException("List contains null user!");
            }

            int age = user.getAge();

            // III ВАРИАНТ РЕШЕНИЯ: Map.computeIfAbsent(...).
            usersByAgev3.computeIfAbsent(age, k -> new ArrayList<>()).add(user);
        });

        Map<Integer, List<User>> immutableUsersByAge = new HashMap<>();
        for (Map.Entry<Integer, List<User>> entry : usersByAgev3.entrySet()) {
            immutableUsersByAge.put(entry.getKey(), List.copyOf(entry.getValue()));
        }

        return Map.copyOf(immutableUsersByAge);
    }
}
