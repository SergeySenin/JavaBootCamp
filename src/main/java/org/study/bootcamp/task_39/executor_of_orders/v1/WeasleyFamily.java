package org.study.bootcamp.task_39.executor_of_orders.v1;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class WeasleyFamily {
    public static void main(String[] args) {

        String[] chores = {
                "полить мандрагоры...",
                "почистить камин...",
                "подмести пол...",
                "покормить сов...",
                "приготовить завтрак...",
                "приготовить обед...",
                "приготовить ужин...",
                "помыть посуду...",
                "прибрать в гостиной..."
        };

        ExecutorService executorService = Executors.newCachedThreadPool();

        for (String task : chores) {
            executorService.execute(new Chore(task));
        }

        try {
            executorService.shutdown();

            if (!executorService.awaitTermination(1, TimeUnit.MINUTES)) {
                System.out.println("Задачи не завершились за 1 минуту, принудительное завершение!");
                executorService.shutdownNow();
            } else {
                System.out.println("Все задачи успешно завершены!");
            }
        } catch (InterruptedException exception) {
            System.out.println("Главный поток был прерван, принудительное завершение!");
            executorService.shutdownNow();
        }

        System.out.println("Программа завершена. Семья Уизли справилась с делами!");
    }
}
