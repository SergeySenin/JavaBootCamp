package org.study.bootcamp.task_42.king_tournament.v1.application.service;

import org.study.bootcamp.task_42.king_tournament.v1.domain.model.Knight;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class King {

    private final List<Knight> knights = new ArrayList<>();

    public void addKnight(Knight knight) {
        if (knight == null) {
            throw new IllegalArgumentException("рыцарь не должен быть null");
        }
        knights.add(knight);
    }

    public void startTournament() {
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        List<Future<?>> allFutures = new ArrayList<>();

        try {
            for (Knight knight : knights) {
                allFutures.addAll(knight.startTrials(executorService));
            }

            for (Future<?> future : allFutures) {
                future.get();
            }

            System.out.println("турнир завершён");

        } catch (InterruptedException exception) {
            Thread.currentThread().interrupt();
            executorService.shutdownNow();
            throw new IllegalStateException("Ожидание завершения испытаний прервано", exception);

        } catch (ExecutionException exception) {
            executorService.shutdownNow();
            throw new RuntimeException("Ошибка во время испытаний", exception.getCause());

        } finally {
            executorService.shutdown();
        }
    }
}
