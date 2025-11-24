package org.study.bootcamp.task_1.group_users_by_age.v4;

import java.util.*;

public class Demo {
    public static void main(String[] args) {

        List<User> usersv4 = List.of(
                new User("AB", 25, "ABC", "ABCD"),
                new User("BC", 30, "BCD", "BCDE"),
                new User("CD", 30, "CDE", "CDEF"),
                new User("DE", 30, "DEF", "DEFG"),
                new User("EF", 25, "EFG", "EFGH"),
                new User("FG", 45, "FGH", "FGHI")
        );

        Map<Integer, List<User>> grouped = User.groupUsersByAge4(usersv4);

        // IV ВАРИАНТ ЗАПИСИ: Stream API по entrySet().
        grouped.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .forEach(entry -> {
                    System.out.println("Age " + entry.getKey() + ": " + entry.getValue().size() + " users");
                    entry.getValue().forEach(user -> System.out.println("  " + user));
                });
    }
}
