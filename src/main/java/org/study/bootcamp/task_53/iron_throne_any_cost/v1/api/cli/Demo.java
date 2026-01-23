package org.study.bootcamp.task_53.iron_throne_any_cost.v1.api.cli;

import org.study.bootcamp.task_53.iron_throne_any_cost.v1.domain.model.House;
import org.study.bootcamp.task_53.iron_throne_any_cost.v1.domain.model.User;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class Demo {

    public static void main(String[] args) {
        House house = new House(
                "Дом Таргариенов",
                List.of("Лорд", "Рыцарь", "Маг")
        );

        User[] users = {
                new User("Игрок-1"),
                new User("Игрок-2"),
                new User("Игрок-3"),
                new User("Игрок-4"),
                new User("Игрок-5"),
                new User("Игрок-6")
        };

        Thread[] threads = new Thread[users.length];

        for (int index = 0; index < users.length; index++) {
            User user = users[index];

            threads[index] = new Thread(
                    () -> runUserScenario(user, house),
                    user.getName()
            );

            threads[index].start();
        }

        try {
            for (Thread thread : threads) {
                thread.join();
            }
        } catch (InterruptedException interruptedException) {
            Thread.currentThread().interrupt();
            for (Thread thread : threads) {
                thread.interrupt();
            }
            throw new IllegalStateException("Ожидание завершения пользователей прервано", interruptedException);
        }

        System.out.println("Все пользователи завершили борьбу за роли.");
    }

    private static void runUserScenario(User user, House house) {
        user.joinHouse(house);

        if (Thread.currentThread().isInterrupted()) {
            return;
        }
        if (user.getAssignedRole() == null) {
            return;
        }

        long stayMillis = ThreadLocalRandom.current().nextLong(600, 1401);
        System.out.printf(
                "%s проводит время в доме '%s' (роль '%s', %d мс)%n",
                user.getName(),
                house.getName(),
                user.getAssignedRole(),
                stayMillis
        );

        try {
            Thread.sleep(stayMillis);
        } catch (InterruptedException interruptedException) {
            Thread.currentThread().interrupt();
            return;
        }

        user.leaveHouse();
    }
}
