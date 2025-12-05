package org.study.bootcamp.task_37.microsoft_mail.v1.api.cli;

import org.study.bootcamp.task_37.microsoft_mail.v1.application.service.SenderRunnable;

public class Demo {

    private static final int TOTAL_MESSAGES = 1000;
    private static final int THREADS_COUNT = 5;

    public static void main(String[] args) {
        int batchSize = (TOTAL_MESSAGES + THREADS_COUNT - 1) / THREADS_COUNT;
        Thread[] threads = new Thread[THREADS_COUNT];

        for (int i = 0; i < THREADS_COUNT; i++) {
            int start = i * batchSize;
            int end = Math.min(TOTAL_MESSAGES, (i + 1) * batchSize);
            if (start >= TOTAL_MESSAGES) break;

            threads[i] = new Thread(new SenderRunnable(start, end), "sender-" + (i + 1));
            threads[i].start();
        }

        try {
            for (Thread thread : threads) {
                if (thread != null) thread.join();
            }
        } catch (InterruptedException exception) {
            Thread.currentThread().interrupt();
            for (Thread thread : threads) {
                if (thread != null) thread.interrupt();
            }
            System.err.println("ожидание завершения потоков прервано");
            return;
        }

        System.out.println("все письма успешно отправлены");
    }
}
