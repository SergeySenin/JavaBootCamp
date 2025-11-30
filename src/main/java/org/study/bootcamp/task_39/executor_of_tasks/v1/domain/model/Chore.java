package org.study.bootcamp.task_39.executor_of_tasks.v1.domain.model;

public class Chore implements Runnable {

    private final String chore;

    public Chore(String chore) {
        this.chore = chore;
    }

    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName() + " выполняет задачу: " + chore);
        try {
            Thread.sleep((long) (Math.random() * 2000 + 1000));
        } catch (InterruptedException exception) {
            System.out.println("Задача " + chore + " была прервана...");
            Thread.currentThread().interrupt();
            throw new RuntimeException(exception);
        }
        System.out.println(Thread.currentThread().getName() + " завершил задачу: " + chore);
    }
}
