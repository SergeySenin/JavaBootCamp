package org.study.bootcamp.task_41.big_bang_theory.v1.domain.model;

import java.util.concurrent.ThreadLocalRandom;

public class Task implements Runnable {

    private final String name;
    private final String task;

    public Task(String name, String task) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("имя исполнителя не должно быть пустым");
        }
        if (task == null || task.isBlank()) {
            throw new IllegalArgumentException("название задачи не должно быть пустым");
        }

        this.name = name;
        this.task = task;
    }

    @Override
    public void run() {
        String threadName = Thread.currentThread().getName();
        System.out.printf("[%s] %s начал: %s%n", threadName, name, task);

        int delaySeconds = ThreadLocalRandom.current().nextInt(1, 6);
        try {
            Thread.sleep(delaySeconds * 1000L);
        } catch (InterruptedException exception) {
            Thread.currentThread().interrupt();
            System.out.printf("[%s] %s прерван во время задачи: %s%n", threadName, name, task);
        }
    }

    public String getName() {
        return name;
    }

    public String getTask() {
        return task;
    }
}
