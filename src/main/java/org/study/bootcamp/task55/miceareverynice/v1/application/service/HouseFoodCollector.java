package org.study.bootcamp.task55.miceareverynice.v1.application.service;

import org.study.bootcamp.task55.miceareverynice.v1.domain.model.House;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class HouseFoodCollector {

    private final int threadsCount;
    private final long collectIntervalSeconds;
    private final long awaitTimeout;
    private final TimeUnit awaitTimeUnit;

    private volatile Throwable taskError;

    public HouseFoodCollector(
            int threadsCount,
            long collectIntervalSeconds,
            long awaitTimeout,
            TimeUnit awaitTimeUnit
    ) {
        if (threadsCount <= 0) {
            throw new IllegalArgumentException("Количество потоков должно быть больше 0");
        }
        if (collectIntervalSeconds <= 0) {
            throw new IllegalArgumentException("Интервал должен быть больше 0 секунд");
        }
        if (awaitTimeout <= 0) {
            throw new IllegalArgumentException("Таймаут ожидания должен быть больше 0");
        }
        if (awaitTimeUnit == null) {
            throw new IllegalArgumentException("Единица времени не должна быть null");
        }

        this.threadsCount = threadsCount;
        this.collectIntervalSeconds = collectIntervalSeconds;
        this.awaitTimeout = awaitTimeout;
        this.awaitTimeUnit = awaitTimeUnit;
    }

    public void collectAllFood(House house, ScheduledExecutorService scheduledExecutorService) {
        validateInputs(house, scheduledExecutorService);

        if (house.isFinished()) {
            return;
        }

        ScheduledFuture<?>[] scheduledFutures = scheduleCollectors(house, scheduledExecutorService);

        scheduledExecutorService.shutdown();
        awaitOrFail(scheduledExecutorService);

        if (taskError != null) {
            cancelAll(scheduledFutures);
            throw new RuntimeException("Ошибка во время сбора еды", taskError);
        }
    }

    private void validateInputs(House house, ScheduledExecutorService scheduledExecutorService) {
        if (house == null) {
            throw new IllegalArgumentException("house не должен быть null");
        }
        if (scheduledExecutorService == null) {
            throw new IllegalArgumentException("scheduledExecutorService не должен быть null");
        }
    }

    private ScheduledFuture<?>[] scheduleCollectors(House house, ScheduledExecutorService scheduledExecutorService) {
        ScheduledFuture<?>[] scheduledFutures = new ScheduledFuture<?>[threadsCount];

        Runnable collectTask = () -> runCollectTask(house, scheduledExecutorService, scheduledFutures);

        for (int index = 0; index < threadsCount; index++) {
            scheduledFutures[index] = scheduledExecutorService.scheduleAtFixedRate(
                    collectTask,
                    0,
                    collectIntervalSeconds,
                    TimeUnit.SECONDS
            );
        }

        return scheduledFutures;
    }

    private void awaitOrFail(ScheduledExecutorService scheduledExecutorService) {
        try {
            boolean finished = scheduledExecutorService.awaitTermination(awaitTimeout, awaitTimeUnit);
            if (!finished) {
                scheduledExecutorService.shutdownNow();
                throw new IllegalStateException("Сбор еды не завершился за отведённое время");
            }
        } catch (InterruptedException interruptedException) {
            Thread.currentThread().interrupt();
            scheduledExecutorService.shutdownNow();
            throw new IllegalStateException("Ожидание завершения сбора еды прервано", interruptedException);
        }
    }

    private void runCollectTask(
            House house,
            ScheduledExecutorService scheduledExecutorService,
            ScheduledFuture<?>[] scheduledFutures
    ) {
        try {
            if (Thread.currentThread().isInterrupted() || house.isFinished()) {
                return;
            }

            house.collectFood();

            if (house.isFinished()) {
                cancelAll(scheduledFutures);
                scheduledExecutorService.shutdown();
            }
        } catch (Throwable throwable) {
            taskError = throwable;
            cancelAll(scheduledFutures);
            scheduledExecutorService.shutdownNow();
        }
    }

    private void cancelAll(ScheduledFuture<?>[] scheduledFutures) {
        for (ScheduledFuture<?> future : scheduledFutures) {
            if (future != null) {
                future.cancel(true);
            }
        }
    }
}
