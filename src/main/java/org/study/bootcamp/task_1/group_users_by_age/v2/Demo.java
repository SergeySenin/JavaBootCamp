package org.study.bootcamp.task_1.group_users_by_age.v2;

import java.util.*;

public class Demo {
    public static void main(String[] args) {

        // TODO[init-2]: Выполнено замечание из v1.
        List<User> usersv2 = List.of(
                new User("AB", 25, "ABC", "ABCD"),
                new User("BC", 30, "BCD", "BCDE"),
                new User("CD", 30, "CDE", "CDEF"),
                new User("DE", 30, "DEF", "DEFG"),
                new User("EF", 25, "EFG", "EFGH"),
                new User("FG", 45, "FGH", "FGHI")// ,
             // new User("", 50, "A", "B"),
             // new User("A", -5, "B", "C")
        );

        // TODO[readability-2]: Выполнено замечание из v1.
        Map<Integer, List<User>> grouped = User.groupUsersByAge2(usersv2);

        // II ВАРИАНТ РЕШЕНИЯ: обход через keySet() + get.
        for (Integer age : grouped.keySet()) {
            List<User> usersAtThisAgev2 = grouped.get(age);
            System.out.println("Age " + age + ": " + usersAtThisAgev2.size() + " users");
            // II ВАРИАНТ ЗАПИСИ: цикл foreach вместо индексного for.
            for (User user : usersAtThisAgev2) {
                System.out.println("  " + user);
            }
        }
    }
}
