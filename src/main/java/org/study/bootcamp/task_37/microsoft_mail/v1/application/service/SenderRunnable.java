package org.study.bootcamp.task_37.microsoft_mail.v1.application.service;

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
            if (Thread.currentThread().isInterrupted()) {
                return;
            }
            System.out.printf(
                    "письмо %d успешно отправлено (поток: %s)%n",
                    i + 1, Thread.currentThread().getName()
            );
        }
    }
}
