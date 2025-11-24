package org.study.bootcamp.task_4.collect_users.v1.api.cli;

import org.study.bootcamp.task_4.collect_users.v1.domain.model.User;

import java.util.*;

public class Demo {
    public static void main(String[] args) {

        Set<String> interestsInPriorityOrder = new LinkedHashSet<>(
                List.of(
                        "abc",
                        "bcd",
                        "cde",
                        "def",
                        "efg")
        );

        List<User> users = List.of(
                new User(1L, "Aa", 21, Set.of("abc", "zzz")),
                new User(2L, "Bb", 22, Set.of("aaa", "bbb")),
                new User(3L, "Cc", 23, Set.of("ccc", "ddd")),
                new User(4L, "Dd", 24, Set.of("bcd", "eee")),
                new User(5L, "Ee", 25, Set.of("cde", "abc")),
                new User(6L, "Ff", 26, Set.of("efg", "fff")),
                new User(7L, "Gg", 27, Set.of("ggg", "hhh")),
                new User(8L, "Hh", 28, Set.of("bcd", "def")),
                new User(9L, "Ii", 29, Set.of("abc", "cde", "def"))
        );

        Map<User, String> hobbyLovers = User.findHobbyLovers(users, interestsInPriorityOrder);

        System.out.println("Target activities by priority: " + interestsInPriorityOrder);

        System.out.println("Matched users (user -> first matched activity):");
        for (Map.Entry<User, String> entry : hobbyLovers.entrySet()) {
            User user = entry.getKey();
            String activity = entry.getValue();
            System.out.println("  " + user.name() + " -> " + activity);
        }
    }
}
