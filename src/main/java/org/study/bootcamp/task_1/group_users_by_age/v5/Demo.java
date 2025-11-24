package org.study.bootcamp.task_1.group_users_by_age.v5;

import java.util.*;

public class Demo {
    public static void main(String[] args) {

        List<User> usersv5 = List.of(
                new User("AB", 25, "ABC", "ABCD"),
                new User("BC", 30, "BCD", "BCDE"),
                new User("CD", 30, "CDE", "CDEF"),
                new User("DE", 30, "DEF", "DEFG"),
                new User("EF", 25, "EFG", "EFGH"),
                new User("FG", 45, "FGH", "FGHI")
        );

        Map<Integer, List<User>> grouped = User.groupUsersByAge5(usersv5);

        // TODO[mutable]: Выполнено замечание из v1.
        grouped.forEach((age, userList) -> {
            System.out.println("Age " + age + ": " + userList.size() + " users");
            userList.forEach(user -> System.out.println("  " + user));
        });

        // TODO[mutable]: Проверка!
        grouped.get(25).add(new User("ZZ", 50, "ZZZ", "ZZZZ"));
        System.out.println("\nAfter adding mutable user:");
        grouped.forEach((age, userList) -> {
            System.out.println("Age " + age + ": " + userList.size() + " users");
            userList.forEach(user -> System.out.println("  " + user));
        });
    }
}
