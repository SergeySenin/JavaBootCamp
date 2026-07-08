package org.study.bootcamp.task44.walmartqueue.v1.api.cli;

import org.study.bootcamp.task44.walmartqueue.v1.application.service.WalmartQueueSimulator;

public class Demo {

    public static void main(String[] args) {
        int[][] customers = {
                {120, 70, 200},
                {500, 130},
                {90},
                {45, 60, 75, 80},
                {999, 150, 110}
        };

        int cashiersCount = 3;

        WalmartQueueSimulator simulator = new WalmartQueueSimulator(cashiersCount, customers);
        simulator.start();

        System.out.println("программа завершена");
    }
}
