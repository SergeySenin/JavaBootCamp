package org.study.bootcamp.task_1.group_users_by_age.v3;

import java.util.*;

public class Demo {
    public static void main(String[] args) {

        List<User> usersv3 = List.of(
                new User("AB", 25, "ABC", "ABCD"),
                new User("BC", 30, "BCD", "BCDE"),
                new User("CD", 30, "CDE", "CDEF"),
                new User("DE", 30, "DEF", "DEFG"),
                new User("EF", 25, "EFG", "EFGH"),
                new User("FG", 45, "FGH", "FGHI")
        );

        Map<Integer, List<User>> grouped = User.groupUsersByAge3(usersv3);

        // III ВАРИАНТ РЕШЕНИЯ: Map.forEach(...) с лямбдой.
        grouped.forEach((age, usersAtThisAge) -> {
            System.out.println("Age " + age + ": " + usersAtThisAge.size() + " users");
            // III ВАРИАНТ ЗАПИСИ: List.forEach(...) с лямбдой.
            usersAtThisAge.forEach(user -> System.out.println("  " + user));
        });
    }
}
