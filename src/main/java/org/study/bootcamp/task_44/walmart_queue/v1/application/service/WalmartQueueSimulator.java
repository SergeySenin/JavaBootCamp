package org.study.bootcamp.task_44.walmart_queue.v1.application.service;

import java.util.concurrent.ThreadLocalRandom;

public class WalmartQueueSimulator {

    private final int cashiersCount;
    private final int[][] customers;

    public WalmartQueueSimulator(int cashiersCount, int[][] customers) {
        if (cashiersCount <= 0) {
            throw new IllegalArgumentException("количество касс должно быть больше 0");
        }
        if (customers == null) {
            throw new IllegalArgumentException("массив покупателей не должен быть null");
        }
        this.cashiersCount = cashiersCount;
        this.customers = customers;
    }

    public void start() {
        CashierThread[] customerThreads = createCustomerThreads();

        startAll(customerThreads);
        waitForAll(customerThreads);
        printReport(customerThreads);
    }

    private CashierThread[] createCustomerThreads() {
        CashierThread[] customerThreads = new CashierThread[customers.length];

        for (int index = 0; index < customers.length; index++) {
            int cashierId = ThreadLocalRandom.current().nextInt(cashiersCount);
            String customerName = "покупатель-" + (index + 1);

            // Модель упрощена: каждый поток — отдельный покупатель, а cashierId — просто выбранная касса.
            // Нет общей очереди, как в реальном магазине.
            customerThreads[index] = new CashierThread(
                    cashierId,
                    customers[index],
                    customerName
            );
        }

        return customerThreads;
    }

    private void startAll(CashierThread[] customerThreads) {
        for (CashierThread customerThread : customerThreads) {
            customerThread.start();
        }
    }

    private void waitForAll(CashierThread[] customerThreads) {
        try {
            for (CashierThread customerThread : customerThreads) {
                customerThread.join();
            }
        } catch (InterruptedException exception) {
            Thread.currentThread().interrupt();
            for (CashierThread customerThread : customerThreads) {
                customerThread.interrupt();
            }
            throw new IllegalStateException("ожидание завершения потоков покупателей прервано", exception);
        }
    }

    private void printReport(CashierThread[] customerThreads) {
        System.out.println("итоговый отчёт по покупателям:");
        for (CashierThread customerThread : customerThreads) {
            System.out.printf(
                    "%s → касса %d: товаров %d, сумма %d%n",
                    customerThread.getName(),
                    customerThread.getCashierId() + 1,
                    customerThread.getItemsCount(),
                    customerThread.getTotalCost()
            );
        }
    }
}
