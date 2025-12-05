package org.study.bootcamp.task_39.executor_of_tasks.v1.domain.model;

public class Chore implements Runnable {

    private final String chore;

    public Chore(String chore) {
        if (chore == null || chore.isBlank()) {
            throw new IllegalArgumentException("название задачи не должно быть пустым");
        }
        this.chore = chore;
    }

    @Override
    public void run() {
        String threadName = Thread.currentThread().getName();
        System.out.printf("%s выполняет задачу: %s%n", threadName, chore);

        try {
            Thread.sleep(1000);
        } catch (InterruptedException exception) {
            Thread.currentThread().interrupt();
            System.out.printf("%s прерван во время задачи: %s%n", threadName, chore);
            return;
        }

        System.out.printf("%s завершил задачу: %s%n", threadName, chore);
    }
}
