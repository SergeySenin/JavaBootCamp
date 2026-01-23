package org.study.bootcamp.task_47.spacex_program.v1.application.service;

import org.study.bootcamp.task_47.spacex_program.v1.domain.model.RocketLaunch;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class RocketLaunchPlanner {

    private static final long TERMINATION_TIMEOUT = 1;
    private static final TimeUnit TERMINATION_TIME_UNIT = TimeUnit.MINUTES;

    public void planRocketLaunches(List<RocketLaunch> launches) {
        if (launches == null || launches.isEmpty()) {
            throw new IllegalArgumentException("Список запусков не должен быть пустым");
        }

        long methodStartTimeMillis = System.currentTimeMillis();

        List<RocketLaunch> sortedLaunches = new ArrayList<>(launches);
        sortedLaunches.sort(Comparator.comparingLong(RocketLaunch::getLaunchTimeMillis));

        ExecutorService executorService = Executors.newSingleThreadExecutor();
        Future<?> future;

        try {
            future = executorService.submit(() -> runSchedule(sortedLaunches, methodStartTimeMillis));
            shutdownAndAwaitOrFail(executorService);
            validateFutureOrFail(executorService, future);

        } finally {
            executorService.shutdown();
            long methodEndTimeMillis = System.currentTimeMillis();
            System.out.printf(
                    "Время выполнения планирования: %d мс%n",
                    (methodEndTimeMillis - methodStartTimeMillis)
            );
        }
    }

    private void runSchedule(List<RocketLaunch> sortedLaunches, long scheduleStartTimeMillis) {
        for (RocketLaunch launch : sortedLaunches) {
            if (Thread.currentThread().isInterrupted()) {
                return;
            }

            long plannedLaunchTimeMillis = scheduleStartTimeMillis + launch.getLaunchTimeMillis();
            long nowMillis = System.currentTimeMillis();
            long waitMillis = plannedLaunchTimeMillis - nowMillis;

            if (waitMillis > 0) {
                try {
                    Thread.sleep(waitMillis);
                } catch (InterruptedException interruptedException) {
                    Thread.currentThread().interrupt();
                    System.out.printf("Планирование прервано перед запуском: %s%n", launch.getName());
                    return;
                }
            }

            try {
                launch.launch();
            } catch (InterruptedException interruptedException) {
                Thread.currentThread().interrupt();
                System.out.printf("Запуск прерван: %s%n", launch.getName());
                return;
            }
        }
    }

    private void shutdownAndAwaitOrFail(ExecutorService executorService) {
        executorService.shutdown();

        try {
            boolean finished = executorService.awaitTermination(TERMINATION_TIMEOUT, TERMINATION_TIME_UNIT);
            if (!finished) {
                executorService.shutdownNow();
                throw new IllegalStateException("Планирование не завершилось за отведённое время");
            }
        } catch (InterruptedException interruptedException) {
            Thread.currentThread().interrupt();
            executorService.shutdownNow();
            throw new IllegalStateException("Ожидание завершения планирования прервано", interruptedException);
        }
    }

    private void validateFutureOrFail(ExecutorService executorService, Future<?> future) {
        try {
            future.get();
        } catch (InterruptedException interruptedException) {
            Thread.currentThread().interrupt();
            executorService.shutdownNow();
            throw new IllegalStateException("Проверка результата планирования прервана", interruptedException);
        } catch (ExecutionException executionException) {
            executorService.shutdownNow();
            throw new RuntimeException("Ошибка во время планирования запусков", executionException.getCause());
        }
    }
}
