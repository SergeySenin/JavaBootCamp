package org.study.bootcamp.task_37.microsoft_mail.v1.api.cli;

import org.study.bootcamp.task_37.microsoft_mail.v1.application.service.SenderRunnable;

public class Demo {

    private static final int TOTAL_MESSAGES = 1000;
    private static final int THREADS_COUNT = 5;

    public static void main(String[] args) throws InterruptedException {
        int batchSize = TOTAL_MESSAGES / THREADS_COUNT;

        Thread[] threads = new Thread[THREADS_COUNT];
        for (int i = 0; i < THREADS_COUNT; i++) {
            int start = i * batchSize;
            int end = (i == THREADS_COUNT - 1) ? TOTAL_MESSAGES : (i + 1) * batchSize;

            threads[i] = new Thread(new SenderRunnable(start, end), "Sender-" + (i + 1));
            threads[i].start();
        }

        for (Thread thread : threads) {
            thread.join();
        }

        System.out.println("Все письма отправлены!");
    }
}
