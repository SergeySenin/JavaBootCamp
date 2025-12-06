package org.study.bootcamp.task_40.food_delivery.v1.application.service;

import org.study.bootcamp.task_40.food_delivery.v1.domain.model.FoodType;

import java.util.concurrent.ThreadLocalRandom;

public class FoodDeliveryTask implements Runnable {

    private final String character;
    private final int foodAmount;

    public FoodDeliveryTask(String character, int foodAmount) {
        if (character == null || character.isBlank()) {
            throw new IllegalArgumentException("имя персонажа не должно быть пустым");
        }
        if (foodAmount <= 0) {
            throw new IllegalArgumentException("количество еды должно быть больше 0");
        }
        this.character = character;
        this.foodAmount = foodAmount;
    }

    @Override
    public void run() {
        String threadName = Thread.currentThread().getName();
        FoodType foodType = getFoodType();

        System.out.printf("[%s] %s получает %d %s%n",
                threadName, character, foodAmount, foodType.getDescription());

        int delaySeconds = ThreadLocalRandom.current().nextInt(1, 6);
        try {
            Thread.sleep(delaySeconds * 1000L);
        } catch (InterruptedException exception) {
            Thread.currentThread().interrupt();
            System.out.printf("[%s] Доставка прервана: %s (заказ: %d %s)%n",
                    threadName, character, foodAmount, foodType.getDescription());
            return;
        }

        System.out.printf("[%s] %s ест %d %s%n",
                threadName, character, foodAmount, foodType.getDescription());
    }

    private FoodType getFoodType() {
        FoodType[] foodTypes = FoodType.values();
        return foodTypes[ThreadLocalRandom.current().nextInt(foodTypes.length)];
    }
}
