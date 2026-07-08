package org.study.bootcamp.task44.walmartqueue.v1.application.service;

import java.util.concurrent.ThreadLocalRandom;

public class CashierThread extends Thread {

    private final int cashierId;
    private final int[] customerItems;

    private int totalCost;

    public CashierThread(int cashierId, int[] customerItems, String customerName) {
        if (cashierId < 0) {
            throw new IllegalArgumentException("cashierId не может быть отрицательным");
        }
        if (customerItems == null) {
            throw new IllegalArgumentException("массив товаров не должен быть null");
        }

        // ВАЖНО: в этой задаче каждый поток = один покупатель.
        // cashierId — только метка выбранной кассы.
        // Строгую очередь к одной кассе мы не моделируем:
        // несколько покупателей с одинаковым cashierId могут обслуживаться параллельно.
        this.cashierId = cashierId;
        this.customerItems = customerItems;
        setName(customerName);
    }

    @Override
    public void run() {
        String threadName = Thread.currentThread().getName();
        System.out.printf(
                "Касса %d начинает обслуживание покупателя: %s%n",
                cashierId + 1, threadName
        );

        for (int index = 0; index < customerItems.length; index++) {
            if (Thread.currentThread().isInterrupted()) {
                System.out.printf(
                        "Касса %d: обслуживание покупателя: %s прервано%n",
                        cashierId + 1, threadName
                );
                return;
            }

            int price = customerItems[index];
            totalCost += price;

            System.out.printf(
                    "Касса %d пробивает товар %d (цена %d) для покупателя: %s%n",
                    cashierId + 1, index + 1, price, threadName
            );

            try {
                Thread.sleep(ThreadLocalRandom.current().nextInt(150, 451));
            } catch (InterruptedException exception) {
                Thread.currentThread().interrupt();
                System.out.printf(
                        "Касса %d: покупатель: %s прерван во время пробития товаров%n",
                        cashierId + 1, threadName
                );
                return;
            }
        }

        System.out.printf(
                "Касса %d завершила обслуживание покупателя: %s (товаров %d, сумма %d)%n",
                cashierId + 1, threadName, customerItems.length, totalCost
        );
    }

    public int getCashierId() {
        return cashierId;
    }

    public int getItemsCount() {
        return customerItems.length;
    }

    public int getTotalCost() {
        return totalCost;
    }
}
