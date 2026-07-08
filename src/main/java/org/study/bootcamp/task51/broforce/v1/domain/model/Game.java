package org.study.bootcamp.task51.broforce.v1.domain.model;

import java.util.concurrent.ThreadLocalRandom;

public class Game {

    private final Object scoreLock = new Object();
    private final Object livesLock = new Object();

    private final int maxLostLives;

    private int score;
    private int lives;
    private boolean isGameOver;

    public Game(int maxLostLives) {
        if (maxLostLives <= 0) {
            throw new IllegalArgumentException("Лимит потерянных жизней должен быть больше 0");
        }

        this.maxLostLives = maxLostLives;
    }

    public void update() {
        boolean playerScored = ThreadLocalRandom.current().nextInt(100) < 60;
        boolean playerLostLife = ThreadLocalRandom.current().nextInt(100) < 15;

        int currentScore;
        int currentLostLives;
        boolean isGameOverNow;

        synchronized (scoreLock) {
            synchronized (livesLock) {
                if (isGameOver) {
                    return;
                }

                score += playerScored ? 1 : 0;
                lives += playerLostLife ? 1 : 0;

                isGameOverNow = lives >= maxLostLives;
                isGameOver = isGameOverNow;

                currentScore = score;
                currentLostLives = lives;
            }
        }

        printUpdate(currentScore, currentLostLives, isGameOverNow);
    }

    public boolean isGameOver() {
        synchronized (livesLock) {
            return isGameOver;
        }
    }

    public int getScore() {
        synchronized (scoreLock) {
            return score;
        }
    }

    public int getLostLives() {
        synchronized (livesLock) {
            return lives;
        }
    }

    private void printUpdate(int currentScore, int currentLostLives, boolean isGameOverNow) {
        System.out.printf(
                "[%s] update: очки=%d, потеряно жизней=%d%n",
                Thread.currentThread().getName(),
                currentScore,
                currentLostLives
        );

        if (isGameOverNow) {
            System.out.printf(
                    "GAME OVER: очки=%d, потеряно жизней=%d (лимит %d)%n",
                    currentScore,
                    currentLostLives,
                    maxLostLives
            );
        }
    }
}
