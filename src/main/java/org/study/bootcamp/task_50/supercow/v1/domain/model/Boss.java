package org.study.bootcamp.task_50.supercow.v1.domain.model;

import java.util.ArrayList;
import java.util.List;

public class Boss {

    private final Object lock = new Object();

    private final int maxPlayers;
    private int currentPlayers;

    private final List<String> playersInBattle = new ArrayList<>();

    public Boss(int maxPlayers) {
        if (maxPlayers <= 0) {
            throw new IllegalArgumentException("maxPlayers должен быть больше 0");
        }
        this.maxPlayers = maxPlayers;
        this.currentPlayers = 0;
    }

    public void joinBattle(Player player) throws InterruptedException {
        if (player == null) {
            throw new IllegalArgumentException("player не должен быть null");
        }

        synchronized (lock) {
            while (currentPlayers >= maxPlayers) {
                System.out.printf(
                        "%s ждёт слот у босса (занято %d из %d)%n",
                        player.getName(),
                        currentPlayers,
                        maxPlayers
                );
                lock.wait();
            }

            currentPlayers++;
            playersInBattle.add(player.getName());

            System.out.printf(
                    "%s вошёл в бой (занято %d из %d)%n",
                    player.getName(),
                    currentPlayers,
                    maxPlayers
            );
        }
    }

    public void leaveBattle(Player player) {
        if (player == null) {
            throw new IllegalArgumentException("player не должен быть null");
        }

        synchronized (lock) {
            boolean removed = playersInBattle.remove(player.getName());
            if (!removed) {
                throw new IllegalStateException(
                        "Игрок не найден в бою: " + player.getName()
                );
            }
            if (currentPlayers <= 0) {
                throw new IllegalStateException("currentPlayers не может быть меньше 0");
            }

            currentPlayers--;

            System.out.printf(
                    "%s вышел из боя (занято %d из %d)%n",
                    player.getName(),
                    currentPlayers,
                    maxPlayers
            );

            lock.notify(); // будим одного ожидающего игрока
        }
    }

    public int getMaxPlayers() {
        return maxPlayers;
    }

    public int getCurrentPlayers() {
        synchronized (lock) {
            return currentPlayers;
        }
    }
}
