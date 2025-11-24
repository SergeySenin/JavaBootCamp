package org.study.bootcamp.task_1.group_users_by_age.v1;

import java.util.*;

public class User {

    // Поля пользователя. Пока делаем простой mutable-класс без финалов и сеттеров.
    // TODO[design]: Рассмотреть immutable-вариант: сделать поля final + без сеттеров, либо переписать класс в record.
    private String name;
    private int age;
    private String workplace;
    private String address;

    // Простой конструктор без валидации — учимся на «скелете».
    // TODO[validation]: Добавить простую валидацию (null/blank для строк, диапазон возраста).
    public User(String name, int age, String workplace, String address) {
        this.name = name;
        this.age = age;
        this.workplace = workplace;
        this.address = address;
    }

    // Минимально нужный геттер для группировки по возрасту.
    // TODO[printing]: Переопределить toString() для удобного вывода результатов группировки.
    public int getAge() {
        return age;
    }

    public static Map<Integer, List<User>> groupUsersByAgev1(List<User> usersv1) {
        Map<Integer, List<User>> usersByAgev1 = new HashMap<>();

        // Предполагаем, что users != null и внутри нет null-элементов.
        // TODO[null-safety]: Явно обработать users == null (вернуть пустую Map или кинуть NPE).
        // TODO[contract]: Определить поведение для null-элементов списка (пропускать или падать).

        // I ВАРИАНТ ЗАПИСИ: проход по списку индексным for.
        for (int i = 0; i < usersv1.size(); i++) {
            User user = usersv1.get(i);
            // TODO[null-safety]: Если допускаются null-элементы, добавить continue при user == null.
            int age = user.getAge();

            // I ВАРИАНТ РЕШЕНИЯ: containsKey + get.
            if (!usersByAgev1.containsKey(age)) {
                usersByAgev1.put(age, new ArrayList<>());
            }
            usersByAgev1.get(age).add(user);
        }

        // Возвращаем «живую» Map (её можно менять снаружи).
        // TODO[immutability]: Вернуть неизменяемую копию (Map.copyOf(...) или Collections.unmodifiableMap(...)).
        return usersByAgev1;
    }
}
