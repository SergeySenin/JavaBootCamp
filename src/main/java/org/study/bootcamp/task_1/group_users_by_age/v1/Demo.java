package org.study.bootcamp.task_1.group_users_by_age.v1;

import java.util.*;

public class Demo {
    public static void main(String[] args) {

        // TODO[init-capacity-2]: если известен размер (6), задаём ёмкость, чтобы избежать лишних расширений массива.
     // List<User> usersv1 = new ArrayList<>(6);
        List<User> usersv1 = new ArrayList<>();

        // TODO[init-2]: заменить ручные add(...) на более компактный вариант:
        // new ArrayList<>(List.of(new User(...), new User(...), ...))       [мутируемый список]
        // или, если добавлять не планируется: List.of(new User(...), ...)   [немутируемый список]
        usersv1.add(new User("AB", 25, "ABC", "ABCD"));
        usersv1.add(new User("BC", 30, "BCD", "BCDE"));
        usersv1.add(new User("CD", 30, "CDE", "CDEF"));
        usersv1.add(new User("DE", 30, "DEF", "DEFG"));
        usersv1.add(new User("EF", 25, "EFG", "EFGH"));
        usersv1.add(new User("FG", 45, "FGH", "FGHI"));

        // TODO[readability-2]: в реальном коде лучше сохранить Map в переменную grouped для дебага/переиспользования.
        // I ВАРИАНТ РЕШЕНИЯ: Обход через entrySet: сразу получаем и ключ (возраст), и значение (список пользователей).
        for (Map.Entry<Integer, List<User>> entry : User.groupUsersByAgev1(usersv1).entrySet()) {
            Integer age = entry.getKey();
            List<User> usersAtThisAgev1 = entry.getValue();

            System.out.println("Age " + age + ": " + usersAtThisAgev1.size() + " users");

            // I ВАРИАНТ ЗАПИСИ: проход по списку индексным for.
            for (int i = 0; i < usersAtThisAgev1.size(); i++) {
                System.out.println("  " + usersAtThisAgev1.get(i));
            }
        }
    }
}
