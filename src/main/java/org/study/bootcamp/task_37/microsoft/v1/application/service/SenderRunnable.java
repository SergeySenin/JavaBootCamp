package org.study.bootcamp.task_37.microsoft.v1.application.service;

public class SenderRunnable implements Runnable {

    private final int startIndex;
    private final int endIndex;

    public SenderRunnable(int startIndex, int endIndex) {
        if (startIndex < 0) {
            throw new IllegalArgumentException("startIndex не может быть отрицательным");
        }
        if (endIndex < startIndex) {
            throw new IllegalArgumentException("endIndex не может быть меньше startIndex");
        }

        this.startIndex = startIndex;
        this.endIndex = endIndex;
    }

    @Override
    public void run() {
        for (int i = startIndex; i < endIndex; i++) {
            System.out.printf("Письмо %d отправлено (поток %s)%n", i, Thread.currentThread().getName());
        }
    }
}
