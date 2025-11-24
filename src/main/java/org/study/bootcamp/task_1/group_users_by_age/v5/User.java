package org.study.bootcamp.task_1.group_users_by_age.v5;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.*;
import java.util.stream.Collectors;

// TODO[design]: Выполнено замечание из v1.
@Data
@AllArgsConstructor
public class User {

    private String name;
    private int age;
    private String workplace;
    private String address;

    // TODO[mutable]: Выполнено замечание из v1.
    public static Map<Integer, List<User>> groupUsersByAge5(List<User> usersv5) {
        if (usersv5 == null) {
            return new HashMap<>();
        }

        return usersv5.stream()
                .collect(Collectors.groupingBy(
                        User::getAge,
                        HashMap::new,
                        Collectors.toCollection(ArrayList::new)
                ));
    }
}
