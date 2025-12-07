package org.study.bootcamp.task_43.people_directory.v1.application.service;

import org.study.bootcamp.task_43.people_directory.v1.domain.model.Person;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class PeopleInfoProcessing {

    private final int threadsCount;
    private final long awaitTimeout;
    private final TimeUnit awaitTimeUnit;

    public PeopleInfoProcessing(int threadsCount, long awaitTimeout, TimeUnit awaitTimeUnit) {
        if (threadsCount <= 0) {
            throw new IllegalArgumentException("количество потоков должно быть больше 0");
        }
        if (awaitTimeout <= 0) {
            throw new IllegalArgumentException("таймаут ожидания должен быть больше 0");
        }
        if (awaitTimeUnit == null) {
            throw new IllegalArgumentException("единица времени не должна быть null");
        }

        this.threadsCount = threadsCount;
        this.awaitTimeout = awaitTimeout;
        this.awaitTimeUnit = awaitTimeUnit;
    }

    public void printPeopleInfo(List<Person> people) {
        if (people == null) {
            throw new IllegalArgumentException("список людей не должен быть null");
        }

        ExecutorService executorService = createExecutorService();
        List<Future<?>> futures = new ArrayList<>();

        try {
            futures = submitPrintTasks(executorService, people);
            shutdownAndAwait(executorService);
            validateTasksResults(executorService, futures);
        } finally {
            executorService.shutdown();
        }
    }

    private ExecutorService createExecutorService() {
        return Executors.newFixedThreadPool(threadsCount);
    }

    private List<Future<?>> submitPrintTasks(ExecutorService executorService, List<Person> people) {
        List<Future<?>> futures = new ArrayList<>();
        int batchSize = calculateBatchSize(people.size());

        for (int i = 0; i < threadsCount; i++) {
            int startIndex = i * batchSize;
            int endIndex = Math.min(people.size(), (i + 1) * batchSize);

            if (startIndex >= endIndex) {
                break;
            }

            futures.add(executorService.submit(
                    new PersonInfoPrinter(people.subList(startIndex, endIndex))
            ));
        }

        return futures;
    }

    private int calculateBatchSize(int peopleSize) {
        return (peopleSize + threadsCount - 1) / threadsCount;
    }

    private void shutdownAndAwait(ExecutorService executorService) {
        executorService.shutdown();

        try {
            boolean finished = executorService.awaitTermination(awaitTimeout, awaitTimeUnit);
            if (!finished) {
                executorService.shutdownNow();
                throw new IllegalStateException("задачи не завершились за отведённое время");
            }
        } catch (InterruptedException exception) {
            Thread.currentThread().interrupt();
            executorService.shutdownNow();
            throw new IllegalStateException("ожидание завершения задач прервано", exception);
        }
    }

    private void validateTasksResults(ExecutorService executorService, List<Future<?>> futures) {
        for (Future<?> future : futures) {
            try {
                future.get();
            } catch (InterruptedException exception) {
                Thread.currentThread().interrupt();
                executorService.shutdownNow();
                throw new IllegalStateException("проверка результатов прервана", exception);
            } catch (ExecutionException exception) {
                executorService.shutdownNow();
                throw new RuntimeException("ошибка в задаче печати", exception.getCause());
            }
        }
    }
}
