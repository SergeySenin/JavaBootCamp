package org.study.bootcamp.mishustin.task50.supercow.v1.domain.model;

import java.util.concurrent.ThreadLocalRandom;

public class Player {

    private final String name;

    public Player(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Имя игрока не должно быть пустым");
        }

        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void doBattle(Boss boss) {
        if (boss == null) {
            throw new IllegalArgumentException("boss не должен быть null");
        }

        boolean joined = false;

        try {
            boss.joinBattle(this);
            joined = true;

            long fightMillis = ThreadLocalRandom.current().nextLong(700, 1601);
            System.out.printf("%s сражается (%d мс)%n", name, fightMillis);

            Thread.sleep(fightMillis);

            System.out.printf("%s завершил бой%n", name);

        } catch (InterruptedException interruptedException) {
            Thread.currentThread().interrupt();
            System.out.printf("%s прерван во время боя/ожидания слота%n", name);

        } finally {
            if (joined) {
                boss.leaveBattle(this);
            }
        }
    }
}
