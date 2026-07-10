package org.study.bootcamp.mishustin.task51.broforce.v1.application.service;

import org.study.bootcamp.mishustin.task51.broforce.v1.domain.model.Game;

public class GameRunner {

    private final Game game;

    public GameRunner(Game game) {
        if (game == null) {
            throw new IllegalArgumentException("game не должен быть null");
        }

        this.game = game;
    }

    public void runGame(int threadsCount, int maxUpdatesPerThread, long delayMillis) {
        if (threadsCount <= 0) {
            throw new IllegalArgumentException("threadsCount должен быть больше 0");
        }
        if (maxUpdatesPerThread <= 0) {
            throw new IllegalArgumentException("maxUpdatesPerThread должен быть больше 0");
        }
        if (delayMillis < 0) {
            throw new IllegalArgumentException("delayMillis не может быть отрицательным");
        }

        Thread[] threads = new Thread[threadsCount];

        for (int index = 0; index < threadsCount; index++) {
            int threadNumber = index + 1;
            threads[index] = new Thread(
                    () -> runUpdatesLoop(maxUpdatesPerThread, delayMillis),
                    "объект-игры-" + threadNumber
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
            throw new IllegalStateException("Ожидание завершения игры прервано", interruptedException);
        }
    }

    private void runUpdatesLoop(int maxUpdatesPerThread, long delayMillis) {
        for (int updateNumber = 0; updateNumber < maxUpdatesPerThread; updateNumber++) {
            if (Thread.currentThread().isInterrupted() || game.isGameOver()) {
                return;
            }

            game.update();

            if (delayMillis == 0) {
                continue;
            }

            try {
                Thread.sleep(delayMillis);
            } catch (InterruptedException interruptedException) {
                Thread.currentThread().interrupt();
                return;
            }
        }
    }
}
