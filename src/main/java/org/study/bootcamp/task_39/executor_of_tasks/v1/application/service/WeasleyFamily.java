package org.study.bootcamp.task_39.executor_of_tasks.v1.application.service;

import org.study.bootcamp.task_39.executor_of_tasks.v1.domain.model.Chore;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class WeasleyFamily {

    private final List<String> chores;

    public WeasleyFamily(List<String> chores) {
        this.chores = chores;
    }

    public void performChores() {
        ExecutorService executorService = Executors.newCachedThreadPool();
        List<Future<?>> futures = new ArrayList<>();

        try {
            for (String task : chores) {
                futures.add(executorService.submit(new Chore(task)));
            }

            for (Future<?> future : futures) {
                future.get();
            }

            System.out.println("программа завершена, семья Уизли справилась с делами");

        } catch (InterruptedException exception) {
            Thread.currentThread().interrupt();
            executorService.shutdownNow();
            throw new IllegalStateException("ожидание выполнения задач прервано", exception);

        } catch (ExecutionException exception) {
            executorService.shutdownNow();
            throw new RuntimeException("ошибка при выполнении домашней задачи", exception.getCause());

        } finally {
            executorService.shutdown();
        }
    }
}
