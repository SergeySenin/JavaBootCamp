package org.study.bootcamp.task_52.youtube_view_counter.v1.api.cli;

import org.study.bootcamp.task_52.youtube_view_counter.v1.application.service.VideoManager;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Demo {

    private static final int NUM_THREADS = 7;
    private static final int NUM_VIDEOS = 5;

    private static final long TERMINATION_TIMEOUT = 1;
    private static final TimeUnit TERMINATION_TIME_UNIT = TimeUnit.MINUTES;

    public static void main(String[] args) {
        VideoManager videoManager = new VideoManager();

        ExecutorService executorService = Executors.newFixedThreadPool(NUM_THREADS);

        try {
            for (int videoIndex = 1; videoIndex <= NUM_VIDEOS; videoIndex++) {
                String videoId = "видео-" + videoIndex;

                for (int threadIndex = 0; threadIndex < NUM_THREADS; threadIndex++) {
                    executorService.submit(() -> videoManager.addView(videoId));
                }
            }

        } finally {
            shutdownAndAwait(executorService);
        }

        for (int videoIndex = 1; videoIndex <= NUM_VIDEOS; videoIndex++) {
            String videoId = "видео-" + videoIndex;
            System.out.printf(
                    "%s: просмотры=%d%n",
                    videoId,
                    videoManager.getViewCount(videoId)
            );
        }
    }

    private static void shutdownAndAwait(ExecutorService executorService) {
        executorService.shutdown();

        try {
            boolean finished = executorService.awaitTermination(TERMINATION_TIMEOUT, TERMINATION_TIME_UNIT);
            if (!finished) {
                executorService.shutdownNow();
                throw new IllegalStateException("Задачи не завершились за отведённое время");
            }
        } catch (InterruptedException interruptedException) {
            Thread.currentThread().interrupt();
            executorService.shutdownNow();
            throw new IllegalStateException("Ожидание завершения задач прервано", interruptedException);
        }
    }
}
