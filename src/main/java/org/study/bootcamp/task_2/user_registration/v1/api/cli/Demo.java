package org.study.bootcamp.task_2.user_registration.v1.api.cli;

import org.study.bootcamp.task_2.user_registration.v1.domain.model.User;

public class Demo {
    public static void main(String[] args) {

        try {
            User user1 = new User("Xx", 22, "Dc", "Cd");
            System.out.println("User created: " + user1.name() + ", " + user1.age());
        } catch (IllegalArgumentException exception) {
            System.out.println("Validation failed: " + exception.getMessage());
        }

        try {
            User user2 = new User("", 17, "Unknown", "Gh");
            System.out.println("User created: " + user2.name() + ", " + user2.age());
        } catch (IllegalArgumentException exception) {
            System.out.println("Validation failed: " + exception.getMessage());
        }
    }
}
