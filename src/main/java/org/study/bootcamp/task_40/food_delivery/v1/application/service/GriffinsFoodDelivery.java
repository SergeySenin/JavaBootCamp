package org.study.bootcamp.task_40.food_delivery.v1.application.service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadLocalRandom;

public class GriffinsFoodDelivery {

    private final String[] characterNames;

    public GriffinsFoodDelivery(String[] characterNames) {
        if (characterNames == null || characterNames.length == 0) {
            throw new IllegalArgumentException("список персонажей не должен быть пустым");
        }
        this.characterNames = characterNames;
    }

    public void startDelivery() {
        ExecutorService executorService = Executors.newFixedThreadPool(3);
        List<Future<?>> futures = new ArrayList<>();

        try {
            for (String characterName : characterNames) {
                int foodAmount = ThreadLocalRandom.current().nextInt(10, 101);
                futures.add(executorService.submit(new FoodDeliveryTask(characterName, foodAmount)));
            }

            for (Future<?> future : futures) {
                future.get();
            }

            System.out.println("все доставки завершены");

        } catch (InterruptedException exception) {
            Thread.currentThread().interrupt();
            executorService.shutdownNow();
            throw new IllegalStateException("Ожидание доставок прервано", exception);

        } catch (ExecutionException exception) {
            executorService.shutdownNow();
            throw new RuntimeException("Ошибка во время доставки", exception.getCause());

        } finally {
            executorService.shutdown();
        }
    }
}
