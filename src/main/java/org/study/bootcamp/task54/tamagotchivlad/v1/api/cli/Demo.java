package org.study.bootcamp.task54.tamagotchivlad.v1.api.cli;

import org.study.bootcamp.task54.tamagotchivlad.v1.application.service.VladController;
import org.study.bootcamp.task54.tamagotchivlad.v1.domain.model.TamagotchiVlad;

public class Demo {

    public static void main(String[] args) {
        VladController controller = new VladController();

        controller.addVlad(new TamagotchiVlad("Влад-1"));
        controller.addVlad(new TamagotchiVlad("Влад-2"));
        controller.addVlad(new TamagotchiVlad("Влад-3"));

        Thread feedThread = new Thread(() -> repeat(8, controller::feedAll, 180), "кормление");
        Thread playThread = new Thread(() -> repeat(8, controller::playAll, 200), "игра");
        Thread cleanThread = new Thread(() -> repeat(8, controller::cleanAll, 260), "чистка");
        Thread sleepThread = new Thread(() -> repeat(8, controller::sleepAll, 320), "сон");

        feedThread.start();
        playThread.start();
        cleanThread.start();
        sleepThread.start();

        try {
            feedThread.join();
            playThread.join();
            cleanThread.join();
            sleepThread.join();
        } catch (InterruptedException interruptedException) {
            Thread.currentThread().interrupt();
            feedThread.interrupt();
            playThread.interrupt();
            cleanThread.interrupt();
            sleepThread.interrupt();
            throw new IllegalStateException("Ожидание завершения потоков прервано", interruptedException);
        }

        System.out.println("Тест завершён.");
    }

    private static void repeat(int times, Runnable action, long delayMillis) {
        for (int count = 0; count < times; count++) {
            if (Thread.currentThread().isInterrupted()) {
                return;
            }

            action.run();

            try {
                Thread.sleep(delayMillis);
            } catch (InterruptedException interruptedException) {
                Thread.currentThread().interrupt();
                return;
            }
        }
    }
}
