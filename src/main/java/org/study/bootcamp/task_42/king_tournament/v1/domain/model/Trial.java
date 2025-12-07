package org.study.bootcamp.task_42.king_tournament.v1.domain.model;

import java.util.concurrent.ThreadLocalRandom;

public class Trial implements Runnable {

    private final String knightName;
    private final String trialName;

    public Trial(String knightName, String trialName) {
        if (knightName == null || knightName.isBlank()) {
            throw new IllegalArgumentException("имя рыцаря не должно быть пустым");
        }
        if (trialName == null || trialName.isBlank()) {
            throw new IllegalArgumentException("название испытания не должно быть пустым");
        }

        this.knightName = knightName;
        this.trialName = trialName;
    }

    @Override
    public void run() {
        String threadName = Thread.currentThread().getName();
        System.out.printf("[%s] %s начинает испытание: %s%n", threadName, knightName, trialName);

        int delaySeconds = ThreadLocalRandom.current().nextInt(1, 6);
        try {
            Thread.sleep(delaySeconds * 1000L);
        } catch (InterruptedException exception) {
            Thread.currentThread().interrupt();
            System.out.printf("[%s] %s прерван во время испытания: %s%n", threadName, knightName, trialName);
            return;
        }

        System.out.printf("[%s] %s завершил испытание: %s%n", threadName, knightName, trialName);
    }
}
