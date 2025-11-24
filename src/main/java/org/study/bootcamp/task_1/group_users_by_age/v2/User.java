package org.study.bootcamp.task_1.group_users_by_age.v2;

import java.util.*;

public class User {

    // TODO[design]: Выполнено замечание из v1.
    private final String name;
    private final int age;
    private final String workplace;
    private final String address;

    // TODO[validation]: Выполнено замечание из v1.
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

    // TODO[printing]: Выполнено замечание из v1.
    @Override
    public String toString() {
        return "User{\n" +
                "  name='" + name + "',\n" +
                "  age=" + age + ",\n" +
                "  workplace='" + workplace + "',\n" +
                "  address='" + address + "'\n" +
                "}";
    }

    public static Map<Integer, List<User>> groupUsersByAge2(List<User> usersv2) {
        // TODO[null-safety]: Выполнено замечание из v1.
        if (usersv2 == null || usersv2.isEmpty()) {
            return Map.of();
        }

        Map<Integer, List<User>> usersByAgev2 = new HashMap<>();

        // II ВАРИАНТ ЗАПИСИ: цикл foreach вместо индексного for.
        for (User user : usersv2) {
            // TODO[null-safety]: Выполнено замечание из v1.
            if (user == null) continue;

            int age = user.getAge();

            // II ВАРИАНТ РЕШЕНИЯ: get + null-проверка и локальная переменная.
            List<User> usersAtThisAge = usersByAgev2.get(age);
            if (usersAtThisAge == null) {
                usersAtThisAge = new ArrayList<>();
                usersByAgev2.put(age, usersAtThisAge);
            }
            usersAtThisAge.add(user);
        }

        // TODO[immutability]: Выполнено замечание из v1.
        Map<Integer, List<User>> immutableUsersByAge = new HashMap<>();
        for (Map.Entry<Integer, List<User>> entry : usersByAgev2.entrySet()) {
            Integer currentAge = entry.getKey();
            List<User> usersAtCurrentAge = entry.getValue();
            immutableUsersByAge.put(currentAge, List.copyOf(usersAtCurrentAge));
        }
        return Map.copyOf(immutableUsersByAge);
    }
}
