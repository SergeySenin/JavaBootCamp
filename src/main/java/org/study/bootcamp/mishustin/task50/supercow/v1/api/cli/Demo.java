package org.study.bootcamp.mishustin.task50.supercow.v1.api.cli;

import org.study.bootcamp.mishustin.task50.supercow.v1.domain.model.Boss;
import org.study.bootcamp.mishustin.task50.supercow.v1.domain.model.Player;

public class Demo {

    public static void main(String[] args) {
        Boss boss = new Boss(2);

        Player[] players = {
                new Player("Игрок-1"),
                new Player("Игрок-2"),
                new Player("Игрок-3"),
                new Player("Игрок-4"),
                new Player("Игрок-5")
        };

        Thread[] threads = new Thread[players.length];

        for (int index = 0; index < players.length; index++) {
            Player player = players[index];
            threads[index] = new Thread(
                    () -> player.doBattle(boss),
                    player.getName()
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
            throw new IllegalStateException("Ожидание завершения игроков прервано", interruptedException);
        }

        System.out.println("Все игроки завершили сражения.");
    }
}
