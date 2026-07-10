package org.study.bootcamp.mishustin.task48.spotify.lock.v1.application.service;

import org.study.bootcamp.mishustin.task48.spotify.lock.v1.domain.model.Player;

public class MusicSession {

    private final Player player;

    public MusicSession(Player player) {
        if (player == null) {
            throw new IllegalArgumentException("Плеер не должен быть null");
        }
        this.player = player;
    }

    public void runDemo() {
        Thread playUser = new Thread(() -> repeat(5, player::play, 120), "Пользователь-play");
        Thread pauseUser = new Thread(() -> repeat(5, player::pause, 150), "Пользователь-pause");
        Thread skipUser = new Thread(() -> repeat(5, player::skip, 90), "Пользователь-skip");
        Thread previousUser = new Thread(() -> repeat(5, player::previous, 110), "Пользователь-previous");

        playUser.start();
        pauseUser.start();
        skipUser.start();
        previousUser.start();

        try {
            playUser.join();
            pauseUser.join();
            skipUser.join();
            previousUser.join();
        } catch (InterruptedException interruptedException) {
            Thread.currentThread().interrupt();
            playUser.interrupt();
            pauseUser.interrupt();
            skipUser.interrupt();
            previousUser.interrupt();
            throw new IllegalStateException("Ожидание завершения сессии прервано", interruptedException);
        }
    }

    private void repeat(int times, Runnable action, long delayMillis) {
        for (int count = 0; count < times; count++) {
            if (Thread.currentThread().isInterrupted()) {
                return;
            }

            action.run();

            try {
                Thread.sleep(delayMillis);
            } catch (InterruptedException interruptedException) {
                Thread.currentThread().interrupt();
                return;
            }
        }
    }
}
