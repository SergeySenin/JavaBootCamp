package org.study.bootcamp.task_41.big_bang_theory.v1.application.service;

import org.study.bootcamp.task_41.big_bang_theory.v1.domain.model.Task;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class BigBangTheoryProject {

    public void runProject() {
        ExecutorService executorService = Executors.newFixedThreadPool(4);

        List<Task> tasks = List.of(
                new Task("Шелдон", "подготовка теории"),
                new Task("Леонард", "моделирование эксперимента"),
                new Task("Говард", "разработка инструментов"),
                new Task("Раджеш", "анализ данных")
        );

        List<Future<?>> futures = new ArrayList<>();

        try {
            for (Task task : tasks) {
                futures.add(executorService.submit(task));
            }

            for (int i = 0; i < futures.size(); i++) {
                Future<?> future = futures.get(i);
                Task task = tasks.get(i);

                try {
                    future.get();
                    System.out.printf("%s завершил задачу: %s%n", task.getName(), task.getTask());
                } catch (InterruptedException exception) {
                    Thread.currentThread().interrupt();
                    executorService.shutdownNow();
                    throw new IllegalStateException("ожидание выполнения задач прервано", exception);
                } catch (ExecutionException exception) {
                    executorService.shutdownNow();
                    throw new RuntimeException(
                            "ошибка при выполнении задачи: " + task.getName() + " — " + task.getTask(),
                            exception.getCause()
                    );
                }
            }

        } finally {
            executorService.shutdown();
        }
    }
}
